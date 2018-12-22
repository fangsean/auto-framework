package com.auto.concurrence;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author auto.yin<auto.yin@gmail.com>
 * 2018-06-28
 * @Description: <p></p>
 */
@Slf4j
public class CountDownExecutorTest {
    static CountDownLatch cdl = new CountDownLatch(100);
    static AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
//        ExecutorService exec = Executors.newSingleThreadExecutor();//单线程
//        ExecutorService exec = Executors.newCachedThreadPool();//可缓存的线程池
//        ExecutorService exec = Executors.newScheduledThreadPool(100);//定时以及周期性执行任务
//        ExecutorService exec = Executors.newWorkStealingPool(100);//jdk1.8支持的线程池
        ExecutorService exec = Executors.newFixedThreadPool(100);//固定大小的线程池
        for (int i = 1; i <= 100; i++) {
            int finalI = i;
           /* exec.schedule(() -> {
                //
            }, 10, TimeUnit.SECONDS);*/

            exec.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + ai.getAndAdd(finalI));
                    cdl.countDown();
                }
            );
        }
        cdl.await();
        System.out.println("" + ai.get());
        exec.shutdown();
    }

}
