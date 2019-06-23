package com.auto.config;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Create Date Time: 2019-06-14 .
 * Update Date Time:
 */
@Configuration
@EnableConfigurationProperties(HelloServiceProperties.class)
@ConditionalOnClass(name = {"com.auto.config.HelloServiceProperties"})
@ConditionalOnProperty(prefix = "hello", value = "enable", matchIfMissing = true)
public class HelloServiceAutoConfiguration {

    @Autowired
    private HelloServiceProperties helloServiceProperties;

    public String getMsg() {
        if (null == helloServiceProperties) {
            return "null";
        }
        return helloServiceProperties.getMsg();
    }
}
    
    