package com.auto.common.factory;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author auto.yin<auto.yin   @   gmail.com>
 * 2018-07-02
 * @Description: <p></p>
 */
@Slf4j
public class ProxyFactory {
    protected final static Logger log = LoggerFactory.getLogger(ProxyFactory.class);

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance() {

        log.info(target.getClass().getClassLoader().toString());
        log.info(target.getClass().getInterfaces().toString());
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        log.info("开始事务2");
                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        log.info("提交事务2");
                        return returnValue;
                    }
                });

    }

}
