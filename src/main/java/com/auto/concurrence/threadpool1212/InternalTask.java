package com.auto.concurrence.threadpool1212;

/**
 *
 */
public class InternalTask implements Runnable {

    private final RunnableQueue runnableQueue;

    private volatile boolean isRunning = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        //当前任务未中断 && 获取queue 执行线程
        while (isRunning && Thread.currentThread().isInterrupted()) {
            try {
                Runnable thread = runnableQueue.take();
                thread.run();
            } catch (Exception e) {
                this.stop();
                break;
            }
        }

    }

    private void stop() {
        this.isRunning = false;
    }

}
