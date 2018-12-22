package com.auto.test;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.spi.nameservice.dns.DNSNameServiceDescriptor;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author auto.yin<auto.yin               @               gmail.com>
 * 2018-07-02
 * @Description: <p></p>
 */
@Slf4j
public class Test {

    protected final static Logger log = LoggerFactory.getLogger(Test.class);

    public static void main1(String[] args) {

        /*IUserDao target = new UserDao();

        log.info(target.getClass().toString());

        //静态代理
        //UserDaoProxy proxy = new UserDaoProxy(target);
        //jdk动态代理
        IUserDao proxy = (IUserDao) new com.auto.common.factory.ProxyFactory(target).getProxyInstance();

        log.info(proxy.getClass().toString());

        proxy.save();*/

    }

    public static void main(String[] args) {

//        sun.net.spi.nameservice.dns.DNSNameServiceDescriptor;

        Class<DNSNameServiceDescriptor> service = DNSNameServiceDescriptor.class;
        ServiceLoader<DNSNameServiceDescriptor> load = ServiceLoader.load(service);

        Iterator<DNSNameServiceDescriptor> iterator = load.iterator();
        while (iterator.hasNext()){
            DNSNameServiceDescriptor next = iterator.next();
            System.out.println(next.getProviderName());
        }
    }


}
