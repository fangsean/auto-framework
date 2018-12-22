package com.auto.concurrence;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author auto.yin<auto.yin@gmail.com>
 * 2018-06-28
 * @Description:
 * <p>
 * 创建Callable接口的实现类并实现clall() 方法；
 * FutureTask对象作为Thread对象的Target来创建线程
 * </p>
 */
@Slf4j
public class CallableTest implements Callable<Integer> {

    private int i = 0;

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            sum++;
        }
        return sum;
    }


    public static void main(String[] args) {

        Callable<Integer> myCallabel = new CallableTest();
        FutureTask<Integer> ft = new FutureTask<Integer>(myCallabel);
        Thread th = new Thread(ft);
        th.start();
        try {
            int sum = ft.get(); //取得新创建的线程中的call()方法返回的
            log.info("sum = " + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


}
