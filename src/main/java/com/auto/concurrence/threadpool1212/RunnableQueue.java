package com.auto.concurrence.threadpool1212;

public interface RunnableQueue {

    void offer(Runnable runnable);

    Runnable take() throws InterruptedException;

    int size();

}
