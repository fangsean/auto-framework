package com.auto.cache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 缓存对象
 *
 * @param <K>
 * @param <V>
 */
public class ExpireCache<K, V> extends ConcurrentHashMap<K, V> {
    private static final long serialVersionUID = 1L;

    private static ExpireCache cache;
    // 键值对有效期 毫秒
    private long EXPIRY_MILLIS = 1000 * 60 * 2;
    private LongAdder longAdder = new LongAdder();
    private Map<K, Long> expiryMap = new ConcurrentHashMap<>();

    public static ExpireCache getInstance() {
        if (cache == null) {
            synchronized (ExpireCache.class) {
                if (cache == null) {
                    cache = new ExpireCache();
                }
            }
        }
        return cache;
    }

    private ExpireCache() {
        this(1 << 4);
    }

    private ExpireCache(int initialCapacity) {
        super(initialCapacity);
    }

    private ExpireCache(int initialCapacity, long defaultExpiryTime) {
        this(initialCapacity);
        this.EXPIRY_MILLIS = defaultExpiryTime;
    }

    public V getIfExists(Object key) {
        if (key == null) {
            return null;
        }
        return super.get(key);
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            return null;
        }
        if (checkExpiry(key, true)) {
            return null;
        }
        return super.get(key);
    }


    @Override
    public V put(K key, V value) {
        return this.put(key, value, EXPIRY_MILLIS);
    }


    public V put(K key, V value, long expiryTime) {
        expiryMap.put(key, System.currentTimeMillis() + expiryTime);
        return super.put(key, value);
    }

    @Override
    public boolean containsKey(Object key) {
        return !checkExpiry(key, true) && super.containsKey(key);
    }


    @Override
    public int size() {
        return entrySet().size();
    }

    @Override
    public boolean isEmpty() {
        return entrySet().size() == 0;
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            return Boolean.FALSE;
        }
        Set<java.util.Map.Entry<K, V>> set = super.entrySet();
        Iterator<java.util.Map.Entry<K, V>> iterator = set.iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry<K, V> entry = iterator.next();
            if (value.equals(entry.getValue())) {
                if (checkExpiry(entry.getKey(), false)) {
                    iterator.remove();
                    return Boolean.FALSE;
                } else {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = super.values();
        if (values.size() < 1) {
            return values;
        }
        values.removeIf(next -> !containsValue(next));
        return values;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            expiryMap.put(e.getKey(), System.currentTimeMillis() + EXPIRY_MILLIS);
        }
        super.putAll(m);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<java.util.Map.Entry<K, V>> set = super.entrySet();
        set.removeIf(entry -> checkExpiry(entry.getKey(), false));
        return set;
    }

    private boolean checkExpiry(Object key, boolean isRemoveSuper) {
        if (!expiryMap.containsKey(key)) {
            return Boolean.FALSE;
        }
        long expiryTime = expiryMap.get(key);
        boolean flag = System.currentTimeMillis() > expiryTime;
        if (flag) {
            if (isRemoveSuper) {
                super.remove(key);
            }
            expiryMap.remove(key);
        }
        return flag;
    }

    public static void main(String[] args) throws InterruptedException {
        ExpireCache map = ExpireCache.getInstance();
        map.put("test", "xxx");
        map.put("test2", "ankang", 5000);
        System.out.println("test==" + map.get("test"));
        Thread.sleep(3000);
        System.out.println("test==" + map.get("test"));
        System.out.println("test2==" + map.get("test2"));
        Thread.sleep(3000);
        System.out.println("test2==" + map.getIfExists("test2"));
    }

}
