package com.auto.test;


import com.auto.concurrence.lock.BooleanLock;
import com.auto.concurrence.lock.Lock;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

public class BooleanLockTest {


    private final Lock lock = new BooleanLock();

    public void syncMethod() {
        try {
            lock.lock();
            int time = current().nextInt(10);
            System.out.println("this current Thread " + currentThread().getName());
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        BooleanLockTest booleanLock = new BooleanLockTest();
        IntStream.range(0, 10).mapToObj(i -> new Thread(booleanLock::syncMethod)).forEach(Thread::start);

        new Thread(() -> {
            booleanLock.syncMethod();
        }, "t1").start();

        TimeUnit.MILLISECONDS.sleep(10);
        Thread thread = new Thread(() -> {
            booleanLock.syncMethod();
        }, "t2");

        thread.start();
//        thread.interrupt();

    }


}
