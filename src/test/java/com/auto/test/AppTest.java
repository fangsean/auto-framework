package com.auto.test;

/**
 * Created by jobob on 17/5/16.
 */
public class AppTest {

    public static void main(String[] args) {

        for (int i=0;i<10000000;i++){
            new Thread(()->{
                System.out.println(System.currentTimeMillis());
            }).start();
        }
    }

}
