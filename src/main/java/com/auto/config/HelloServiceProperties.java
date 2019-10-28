package com.auto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Create Date Time: 2019-06-14 .
 * Update Date Time:
 */
@Data
//@Configuration
@ConfigurationProperties(prefix = "service.properties")
public class HelloServiceProperties {


    private static final String SERVICE_NAME = "test-service";

    private String msg = SERVICE_NAME;



}
    
    