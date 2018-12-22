package com.auto.common.service;

import java.io.Serializable;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
public abstract  class BaseCacheService<K extends  String,V extends Serializable> implements CacheService<K,V> {


    @Override
    public void put(K k,V v ,long milliseconds){

    }
}
