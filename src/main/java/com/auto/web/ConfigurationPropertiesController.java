package com.auto.web;

import com.auto.config.HelloServiceAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Create Date Time: 2019-06-14 .
 * Update Date Time:
 */
@RestController
public class ConfigurationPropertiesController {

    @Autowired
    private HelloServiceAutoConfiguration helloServiceAutoConfiguration;

    @RequestMapping("/getObjectProperties")
    public Object getObjectProperties() {
        System.out.println(helloServiceAutoConfiguration.getMsg());
        return helloServiceAutoConfiguration.getMsg();
    }

}
    
    