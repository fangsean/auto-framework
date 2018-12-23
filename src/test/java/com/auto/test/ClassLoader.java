package com.auto.test;

/**
 * @author jsen.yin<jsen.yin   @   gmail.com>
 * 2018-07-02
 * @Description: <p></p>
 */
public class ClassLoader {


    public static void main(String[] args) throws ClassNotFoundException {


        Class<?> aClass = Class.forName("com.auto.common.adapter.UseCase");
        aClass.getCanonicalName();



    }



}
