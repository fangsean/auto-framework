package com.auto.test;



import static java.lang.Thread.currentThread;


public class ThreadTest {

    public static void main(String[] args) {

        System.out.println(currentThread().getContextClassLoader());

    }



}
