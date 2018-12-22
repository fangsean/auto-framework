package com.auto.concurrence.threadpool1212;

/**
 * p134
 */
public class BasicThreadPool extends Thread implements ThreadPool{

    private final int initSize;

    private final int coreSize;

    private final int maxSize;

    private int activeCount;

    private int keepAliveSeconds;

    private int queueCapacity;

    private int awaitTerminationSeconds;

    private final ThreadFactory threadFactory;

    private final RunnableQueue runnableQueue;

    private volatile boolean isShutDown = false;

    public BasicThreadPool(ThreadGroup group, Runnable target, String name, long stackSize, int initSize, int coreSize, int maxSize, ThreadFactory threadFactory, RunnableQueue runnableQueue) {
        super(group, target, name, stackSize);
        this.initSize = initSize;
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void execute(Runnable runnable) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public int getInitSize() {
        return 0;
    }

    @Override
    public int getMaxSize() {
        return 0;
    }

    @Override
    public int getCoreZise() {
        return 0;
    }

    @Override
    public int getQueueSize() {
        return 0;
    }

    @Override
    public int getActiveCount() {
        return 0;
    }

    @Override
    public boolean isShutDown() {
        return false;
    }
}
