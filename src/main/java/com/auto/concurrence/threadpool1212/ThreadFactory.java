package com.auto.concurrence.threadpool1212;

@FunctionalInterface
public interface ThreadFactory {

    Thread createThread(Runnable runnable);

}
