package com.auto;

import com.auto.concurrence.atomic.AtomicObject;
import com.auto.concurrence.conf.ThreadAsyncConfigurer;
import com.auto.concurrence.conf.ThreadPoolConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private ThreadAsyncConfigurer configurer;

    @Test
    public void contextLoads() {

        AtomicObject atomicObject = new AtomicObject(0);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicStampedReference<Integer> number = new AtomicStampedReference<Integer>(19, 0);

        /*Executor*/
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) configurer.getAsyncExecutor();
        for (int i = 0; i <= 100; i++) {
            executor.submit(() -> {
                System.out.println(atomicObject.getAndIncrement());
                System.out.println(atomicInteger.getAndIncrement());
            });
        }

        executor.submit(() -> {
            for (int i = 0; i < 100; i++) {
                int stamp = number.getStamp();
                Integer reference = number.getReference();
                while (true) {
                    if (reference < 20) {
                        if (number.compareAndSet(reference, reference + 20, stamp, stamp + 1)) {
                            System.out.println("消费10元，余额:" + number.getReference());
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        });

        executor.submit(() -> {
            for (int i = 0; i < 100; i++) {
                while (true) {
                    int timestamp = number.getStamp();
                    Integer reference = number.getReference();
                    if (reference > 10) {
                        if (number.compareAndSet(reference, reference - 10, timestamp, timestamp + 1)) {
                            System.out.println("消费10元，余额:" + number.getReference());
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        });

        executor.shutdown();
        System.out.println(atomicObject.get());
        System.out.println("=========================:" + atomicInteger.get());

    }

}

