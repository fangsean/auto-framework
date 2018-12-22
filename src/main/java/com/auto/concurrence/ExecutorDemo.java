package com.auto.concurrence;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorDemo {

    public static void main(String[] args) {


        System.out.println("================================= start ");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i=0;i<10;i++){
            int finalI = i;
            executorService.submit(()->{
                try {
                    System.out.println(String.format("{%s}process start {%s}",Thread.currentThread().getName() , finalI));
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(String.format("{%s}process over {%s}",Thread.currentThread().getName() , finalI));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            TimeUnit.SECONDS.sleep(30);
            System.out.println("================================= over ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



}
