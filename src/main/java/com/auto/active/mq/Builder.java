package com.auto.active.mq;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2019-01-20
 * @Description: <p></p>
 */
@Data
@lombok.Builder
public class Builder {

    private Object[] objects;

    private Method method;

    private ActiveFuture<Object> future;

    private Object service;


}
