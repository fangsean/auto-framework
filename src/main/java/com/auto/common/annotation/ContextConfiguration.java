package com.auto.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Create Date Time: 2019-05-11 .
 * Update Date Time:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface ContextConfiguration {

    @AliasFor(value = "locations")
    String value() default "";

    @AliasFor(value = "value")
    String locations() default "";

}
