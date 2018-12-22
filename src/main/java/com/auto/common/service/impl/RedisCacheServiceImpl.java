package com.auto.common.service.impl;

import com.auto.common.service.BaseCacheService;
import com.auto.util.JsonUtils;
import com.auto.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
/*@Service("redisCacheService")*/
public class RedisCacheServiceImpl<V extends Serializable> extends BaseCacheService<String,V> {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheServiceImpl.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 毫秒钟为单位,默认为 5分钟
     */
    /*@Value("${jedis.pool.config.expire.timeout}")*/
    private long  MILLISECONDS = 1000*60*5 ;

    @Override
    public void put(String key, V value) {
        put(key,value,MILLISECONDS);
    }

    @Override
    public void put(String key, V value ,long milliseconds){
        Jedis jedis = getJedis();
        if(jedis==null){
            logger.error("["+getClass().getSimpleName()+"][put][get jedis is null]");
            return ;
        }
        try{
            jedis.set(SerializeUtil.serialize(key),SerializeUtil.serialize(value));
            jedis.pexpire(SerializeUtil.serialize(key),milliseconds);
        }catch (Throwable e){
            String msg = String.format("[RedisCacheServiceImpl][put][exception][param][%s][%s]",key, JsonUtils.toJson(value));
            logger.error(msg,e);
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
    }

    @Override
    public void putIfAbsent(String key, V value) {

    }

    @Override
    public void putAll(Collection collection) {

    }

    @Override
    public V get(String key) {
        Jedis jedis = getJedis();
        if(jedis==null){
            logger.error("["+getClass().getSimpleName()+"][get][get jedis is null]");
            return null ;
        }
        try{
            byte[] b = jedis.get(SerializeUtil.serialize(key));
            if(b !=null){
                return (V)SerializeUtil.unserialize(b);
            }
        }catch (Throwable e){
            String msg = String.format("[RedisCacheServiceImpl][get][exception][param][%s]",key);
            logger.error(msg,e);
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
        return  null ;
    }

    @Override
    public void remove(String key) {
        Jedis jedis = getJedis();
        if(jedis==null){
            logger.error("["+getClass().getSimpleName()+"][remove][get jedis is null]");
            return;
        }
        try{
            jedis.del(SerializeUtil.serialize(key));
        }catch (Throwable e){
            String msg = String.format("[RedisCacheServiceImpl][remove][exception][param][%s]",key);
            logger.error(msg,e);
        }finally {
            //返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 每次用完要手动关闭
     * 重试3次
     * @return
     */
    private Jedis getJedis(){
        Jedis redis = null ;
        int times = 5 ;
        while (times>0){
            try{
                redis = jedisPool.getResource();
                return  redis ;
            }catch (Throwable e){
                logger.error("["+getClass().getSimpleName()+"][getJedis][Exception][Retry][times]["+times+"]",e);
                times-- ;
                try {
                    Thread.sleep(5);
                } catch (Exception ex) {
                    logger.error("[getJedis][sleep][Exception]",ex);
                }
            }
        }
        return redis ;
    }

    private  void returnResource(Jedis jedis) {
        try {
            if (null != jedis) {
                jedis.close();
            }
        }catch (Throwable e){
            logger.error("[returnResource][exception]",e);
        }
    }

    @Override
    public void setMap(String key, Map<String, String> map, long milliseconds) {
        Jedis jedis = getJedis();
        try {
            if (jedis.exists(key)) {
                jedis.hincrBy(key, "clickNum",1);
            } else {
                jedis.hmset(key, map);
                jedis.pexpire(key, milliseconds);
            }
        } catch (Throwable e) {
            String msg = String.format("[RedisCacheServiceImpl][setMap][exception][param][%s][%s]", key, JsonUtils.toJson(map));
            logger.error(msg, e);
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
    }

    @Override
    public String getValue(String key) {
        Jedis jedis = getJedis();
        try {
            Map<String, String> valueStr = jedis.hgetAll(key);
            return JsonUtils.toJson(valueStr);
        } catch (Throwable e){
            String msg = String.format("[RedisCacheServiceImpl][getValue][exception][param][%s]",key);
            logger.error(msg,e);
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
        return null;
    }

}

