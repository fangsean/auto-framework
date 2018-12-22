package com.auto.test;

/**
 * @author auto.yin<auto.yin   @   gmail.com>
 * 2018-07-02
 * @Description: <p></p>
 */
public class ClassLoader {


    public static void main(String[] args) throws ClassNotFoundException {


        Class<?> aClass = Class.forName("com.auto.api.UseCase");
        aClass.getCanonicalName();



    }



}
