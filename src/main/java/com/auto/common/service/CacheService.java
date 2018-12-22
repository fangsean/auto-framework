package com.auto.common.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
public  interface CacheService<K extends Serializable,V extends Serializable>{

    /**
     * put
     * @param k
     * @param v
     * @throws Exception
     */
    public void put(K k, V v)  ;

    /**
     *
     * @param k
     * @param v
     * @param milliseconds 过期秒数
     */
    public void put(K k, V v, long milliseconds);

    /**
     * putIfAbsent
     * @param k
     * @param v
     * @throws Exception
     */
    public void putIfAbsent(K k, V v)  ;

    /**
     * putAll
     * @param collection
     * @throws Exception
     */
    public void putAll(Collection collection) ;

    /**
     * get
     * @param k
     * @return
     */
    public V get(K k) ;

    /**
     * getCachemanager
     * @return
     */
    // public CacheManager getCacheManager() ;

    public void remove(K k);

    void setMap(K k, Map<String, String> map, long milliseconds);

    String getValue(K k);

}