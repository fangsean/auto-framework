package com.auto.util;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
public class LRUCacheUtil<K extends Serializable, V extends Serializable> extends LinkedHashMap<K , V> {

    private static LRUCacheUtil lruCache = null ;

    private LRUCacheUtil(){
    }

    public static LRUCacheUtil getInstance(int cacheSize){
        if(lruCache != null){
            return  lruCache ;
        }
        if(lruCache == null){
            synchronized (LRUCacheUtil.class){
                lruCache = new LRUCacheUtil(cacheSize);
            }
        }

        return  lruCache ;

    }

    private int cacheSize;

    private LRUCacheUtil(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;

    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}