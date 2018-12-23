package com.auto.common.job;

import com.auto.common.struct.IWorkJob;

import java.lang.reflect.Method;

/**
 * @author jsen.yin<jsen.yin @ gmail.com>
 * @Description: <p></p>
 */
public class ReflexWorkJob implements IWorkJob {

    private Class<?> clazz;
    private String method;
    private boolean flag;

    public ReflexWorkJob(Object clazz, Object method, Object flag) {
        super();
        this.clazz = (Class<?>) clazz;
        this.method = (String) method;
        this.flag = (Boolean) flag;
    }

    @Override
    public void doJob() {
        try {
            Method method  = this.clazz.getDeclaredMethod(this.method,boolean.class);
            method.invoke(this.clazz.newInstance(),this.flag);
        } catch (Exception e) {

        }

    }
}
