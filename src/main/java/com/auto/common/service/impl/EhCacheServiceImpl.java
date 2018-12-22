package com.auto.common.service.impl;

import com.auto.common.service.BaseCacheService;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
@Service("ehCacheService")
public class EhCacheServiceImpl extends BaseCacheService<String,Serializable> {

    /**
     * ehcache，以后要封装一层，可以替换成其他的cache
     */
    @Resource(name="cacheManager")
    private EhCacheCacheManager cacheManager ;

    @Value("${confirm.order.cache.name}")
    private String cacheName = "confirmOrderCache" ;


    @Override
    public void put(String key, Serializable value) {
        Element element = new Element(key,value);
        cacheManager.getCacheManager().getCache(cacheName).put(element);
    }

    @Override
    public void putIfAbsent(String key, Serializable value) {
        Element element = new Element(key,value);
        cacheManager.getCacheManager().getCache(cacheName).putIfAbsent(element);
    }

    @Override
    public void putAll(Collection collection) {
        cacheManager.getCacheManager().getCache(cacheName).putAll(collection);
    }

    @Override
    public Serializable get(String key) {
        return cacheManager.getCacheManager().getCache(cacheName).get(key);
    }

    @Override
    public void remove(String key) {
        cacheManager.getCacheManager().getCache(cacheName).remove(key);
    }

    @Override
    public String getValue(String s) {
        return null;
    }

    @Override
    public void setMap(String s, Map map, long milliseconds) {

    }
}