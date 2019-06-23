package com.auto.common.annotation.adapters;


import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Documented
public @interface ParamsWapper {

    @AliasFor("value")
    String key() default "";

    @AliasFor("key")
    String value() default "";

    Autowire setParams() default Autowire.NO;

    Autowire getParams() default Autowire.NO;

}
