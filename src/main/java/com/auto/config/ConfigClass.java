package com.auto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-25
 * @Description: <p></p>
 */
@Configuration
@ImportResource(locations={"classpath*:spring/spring-*.xml", /*"classpath*:spring/dubbo/*.xml"*/})
//@ImportResource(locations={"classpath*:spring/dubbo/*.xml"})
public class ConfigClass {

}