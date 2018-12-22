package com.auto.concurrence.group;

import java.util.concurrent.TimeUnit;

/**
 * ①在线程被kill-9 的时候，是不会执行hook进程的
 * ②hook进程不要执行耗时的操作
 */
public class ThreadGroupInterrupt {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Hook1");
        }));
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Hook2");
        }));
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Hook3");
        }));

        ThreadGroup threadGroup = new ThreadGroup("");
        Thread thread = new Thread(threadGroup, () -> {
            System.out.println("1");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread1 = new Thread(threadGroup, () -> {
            System.out.println("2");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread1.setDaemon(true);
        thread.start();
        threadGroup.interrupt();
        thread1.start();
//        threadGroup.destroy();

    }


}
