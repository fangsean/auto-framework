package com.auto.concurrence;

/**
 * @author auto.yin<auto.yin   @   gmail.com>
 * 2018-06-28
 * @Description: <p></p>
 */
public class Test {
    ReentrantReadWriteLockTest reentrantReadWriteLockTest = new ReentrantReadWriteLockTest();

    class WriteLock implements Runnable {
        public void run() {
            try {
                reentrantReadWriteLockTest.write(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadLock implements Runnable {
        public void run() {
            reentrantReadWriteLockTest.read();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        Test test = new Test();
        WriteLock writer = test.new WriteLock();
        ReadLock reader = test.new ReadLock();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(writer);
            thread.start();
            thread.join();
            thread = new Thread(reader);
            thread.start();
            thread.join();
        }

    }

    /***
     * 读取 可以多个线程同时执行程序
     16:58:14.876 [Thread-17] INFO com.auto.concurrence.ReentrantReadWriteLockTest - ThreadName:Read-Thread-17 lock...
     16:58:14.876 [Thread-19] INFO com.auto.concurrence.ReentrantReadWriteLockTest - ThreadName:Read-Thread-19 lock...
     16:58:14.876 [Thread-17] INFO com.auto.concurrence.ReentrantReadWriteLockTest - ThreadName:Read-Thread-17 获取为：Thread-18
     16:58:14.876 [Thread-19] INFO com.auto.concurrence.ReentrantReadWriteLockTest - ThreadName:Read-Thread-19 获取为：Thread-18
     16:58:14.877 [Thread-19] INFO com.auto.concurrence.ReentrantReadWriteLockTest - ThreadName:Read-Thread-19 unlock...
     16:58:14.877 [Thread-17] INFO com.auto.concurrence.ReentrantReadWriteLockTest - ThreadName:Read-Thread-17 unlock...
     */
}
