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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Af {

    @AliasFor("attribute")
    String value() default "";

    @AliasFor("value")
    String attribute() default "";


}
