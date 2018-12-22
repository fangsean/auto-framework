package com.auto.concurrence.threadpool1212;

public interface ThreadPool {

    void execute(Runnable runnable);

    void shutdown();

    int getInitSize();

    int getMaxSize();

    int getCoreZise();

    int getQueueSize();

    int getActiveCount();

    boolean isShutDown();

}
