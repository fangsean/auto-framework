package com.auto.concurrence;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author auto.yin<auto.yin   @   gmail.com>
 * 2018-06-28
 * @Description: <p></p>
 */
@Slf4j
public class RunnableTest {
    //公共变量
    @Getter
    int count = 0;

    public static void main(String[] args) {
        //new一个实现Runnable的类
        RunnableTest test = new RunnableTest();
     /*
        //创建5个任务
        MyRunnable myRunnable1 = test.new MyRunnable();
        MyRunnable myRunnable2 = test.new MyRunnable();
        MyRunnable myRunnable3 = test.new MyRunnable();
        MyRunnable myRunnable4 = test.new MyRunnable();
        MyRunnable myRunnable5 = test.new MyRunnable();
        //创建5个线程
        new Thread(myRunnable1).start();
        new Thread(myRunnable2).start();
        new Thread(myRunnable3).start();
        new Thread(myRunnable4).start();
        new Thread(myRunnable5).start();
    */

        //创建1个任务
        MyRunnable myRunnable1 = test.new MyRunnable();
        //创建5个线程
        for (int i = 0; i < 4; i++) {
            new Thread(myRunnable1).start();
        }

    }

    //创建一个实现Runnable的类
    class MyRunnable implements Runnable {
        public void run() {
            while (true) {
                //MyRunnable.class 锁住的是整个MyRunnable类
                //this 锁住同一个对象
                synchronized (this) {
                    if (count >= 1000) {
                        break;
                    }
                    log.info(Thread.currentThread().getName() + ":count:" + (++count));
                    //测试时，线程更容易切换
                    //Thread.yield();
                }

            }

        }

    }

}