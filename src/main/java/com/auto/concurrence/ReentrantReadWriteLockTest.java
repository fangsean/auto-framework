package com.auto.concurrence;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jsen.yin<jsen.yin @ gmail.com>
 * 2018-06-28
 * @Description: <p>读写锁</p>
 */
@Slf4j
public class ReentrantReadWriteLockTest {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    /**
     * 读锁 （可以多个线程同时执行程序）
     */
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    /**
     * 写锁（只能有一个写线程执行程序，）
     */
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    /**
     * 共享资源
     */
    private String shareData = " 共享数据";

    public void write(String str) throws InterruptedException {
        writeLock.lock();
        log.error("ThreadName:write-" + Thread.currentThread().getName() + " locking...");
        try {
            shareData = str;
            log.error("ThreadName:write-" + Thread.currentThread().getName() + " 修改为" + str);
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.error("ThreadName:write-" + Thread.currentThread().getName() + "  unlock...");
            writeLock.unlock();
        }
    }

    public String read() {
        readLock.lock();
        log.info("ThreadName:Read-" + Thread.currentThread().getName() + " lock...");
        try {
            log.info("ThreadName:Read-" + Thread.currentThread().getName() + " 获取为：" + shareData);
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("ThreadName:Read-" + Thread.currentThread().getName() + " unlock...");
            readLock.unlock();
        }
        return shareData;
    }


    public static void main(String[] args) {

        ReentrantReadWriteLockTest reentrantReadWriteLockTest = new ReentrantReadWriteLockTest();
        try {
            reentrantReadWriteLockTest.write("111111111111111111111111");
            reentrantReadWriteLockTest.read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

