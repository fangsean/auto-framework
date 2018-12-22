package com.auto.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
@Component("springContextHolder")
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.context = applicationContext;
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null == context) {
            return null;
        }
        return context.getBean(beanName, clazz);
    }

    public static <T> T getBean(String beanName) {
        if (null == context) {
            return null;
        }
        return (T)context.getBean(beanName);
    }

    public static boolean containsBean(String beanName) {
        if (null == context) {
            return false;
        }
        return context.containsBean(beanName);
    }
}
