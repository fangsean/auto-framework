package com.auto.common.annotation.adapters;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface LogWapper {

    boolean isSave() default false;

    String value() default "{}";

    String[] fields()  default {};

}
