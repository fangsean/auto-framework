package com.auto.common.service.impl;

import com.auto.common.service.BaseCacheService;
import com.auto.util.LRUCacheUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
@Service("memCacheService")
public class MemCacheServiceImpl extends BaseCacheService<String,Serializable> implements InitializingBean {
    /**
     * 内存 lru
     */
    private LRUCacheUtil lruCache = null ;

    private int cacheSie = 5000 ;

    @Override
    public void put(String key, Serializable value) {
        lruCache.put(key,value);
    }

    @Override
    public void putIfAbsent(String key, Serializable value) {
        lruCache.putIfAbsent(key,value);
    }

    @Override
    public void putAll(Collection collection) {

    }

    @Override
    public Serializable get(String key) {
        return (Serializable)lruCache.get(key);
    }

    @Override
    public void remove(String key) {
        lruCache.remove(key) ;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        lruCache = LRUCacheUtil.getInstance(cacheSie);
    }

    @Override
    public String getValue(String s) {
        return null;
    }

    @Override
    public void setMap(String s, Map map, long milliseconds) {

    }
}