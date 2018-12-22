package com.auto.common.interceptor;

import com.auto.common.context.SpringContextHolder;
import com.auto.common.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
@Component("cacheFactory")
public class CacheFactory {

    private static final Logger logger = LoggerFactory.getLogger(CacheFactory.class);

    @Resource(name = "ehCacheService")
    private CacheService ehCacheService;

    @Resource(name = "memCacheService")
    private CacheService memCacheService;

/*    @Resource(name = "redisCacheService")
    private CacheService redisCacheService;*/

    /**
     * 指定某一个cacheService
     */
    @Value("${specify.cache.service}")
    private String specifyCacheService = "memCacheService";

    public CacheService getSpecifyCacheService() {
        CacheService cacheService = SpringContextHolder.getBean(specifyCacheService, CacheService.class);
        if (cacheService == null) {
            logger.error("getSpecifyCacheService is null ,please check variable: specifyCacheService value !");
            return getMemCacheService();
        }
        return cacheService;
    }

    public CacheService getEhCacheService() {
        return ehCacheService;
    }

    public CacheService getMemCacheService() {
        return memCacheService;
    }

    /*public CacheService getRedisCacheService() {
        return redisCacheService;
    }*/

    public void setSpecifyCacheService(String specifyCacheService) {
        this.specifyCacheService = specifyCacheService;
    }


}
