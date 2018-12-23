package com.auto.concurrence;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jsen.yin<jsen.yin @ gmail.com>
 * 2018-06-28
 * @Description: <p>使用可重入锁</p>
 */
@Slf4j
public class ReentrantLockTest {

    private ReentrantLock lock = new ReentrantLock();
    public void sayHello() {
        /**
         * 当一条线程不释放锁的时候，第二个线程走到这里的时候就阻塞掉了
         */
        try {
            lock .lock();
            log.info(Thread. currentThread ().getName()+ " locking ..." );
            log.info( "Hello world!" );
            log.info(Thread. currentThread ().getName()+ " unlocking ..." );
        } finally {
            lock .unlock();
        }
    }

}
