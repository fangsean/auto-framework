package com.auto.common.interceptor;

import com.auto.common.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
public class WebInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebInterceptor.class);

    private CacheFactory cacheFactory = SpringContextHolder.getBean("cacheFactory") ;

    private EhCacheCacheManager cacheManager = SpringContextHolder.getBean("cacheManager") ;

    private String  cacheName1 = "authorizationCache" ;

    private String  cacheName2 = "authenticationCache" ;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {

        //logger.info("----preHandle---");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {

        /*logger.info("----postHandle---");
        Serializable id = SecurityUtils.getSubject().getSession().getId();
        long timeout = SecurityUtils.getSubject().getSession().getTimeout();
        logger.info("session id: "+id+" timeout :"+timeout);
        logger.info("session id: "+id+" startTimestamp :"+DateUtil.formatDate(SecurityUtils.getSubject().getSession().getStartTimestamp()) );
        logger.info("session id: "+id+" lastAccessTime :"+DateUtil.formatDate(SecurityUtils.getSubject().getSession().getLastAccessTime()) );

        List<String> keys = cacheManager.getCacheManager().getCache(cacheName1).getRecords();
        logger.info("kyes:"+keys);

        for(String key: keys){
            Serializable value = cacheManager.getCacheManager().getCache(cacheName1).get(key);
            logger.info("value:"+value);
        }
        logger.info("----------------------------------------------------------------------");
        keys = cacheManager.getCacheManager().getCache(cacheName2).getRecords();
        logger.info("kyes:"+keys);

        for(String key: keys){
            Serializable value = cacheManager.getCacheManager().getCache(cacheName2).get(key);
            logger.info("value:"+value);
        }*/
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        //logger.info("----afterCompletion---");


    }

}
