package com.auto.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * 线程池配置
 * @author: fang.sheng
 * Version: 1.0
 * Create Date Time: 2019-04-25 .
 * Update Date Time:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.task.pool.config")
public class ThreadPoolConfig {

    @Value("${corePoolSize:50}")
    private int corePoolSize;

    @Value("${maxPoolSize:100}")
    private int maxPoolSize;

    @Value("${keepAliveSeconds:200}")
    private int keepAliveSeconds;

    @Value("${queueCapacity:100}")
    private int queueCapacity;

    @Value("${awaitTerminationSeconds:3000}")
    private int awaitTerminationSeconds;

    @Value("${timeOutSeconds:1000}")
    private int timeOutSeconds;

}

    