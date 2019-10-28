package com.auto.active.mq;

import java.lang.reflect.Method;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2019-01-20
 * @Description: <p></p>
 */
public class ActiveMessage {


    private final Object[] objects;

    private final Method method;

    private final Object service;

    private final ActiveFuture<Object> future;

    private ActiveMessage(Builder builder) {
        this.objects = builder.getObjects();
        this.method = builder.getMethod();
        this.service = builder.getService();
        this.future = builder.getFuture();
    }

    public void execute(){
        try {

        }catch (Exception e){}


    }


}
