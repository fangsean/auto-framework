package com.auto.concurrence.threadpool1212;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue{
    private final int limit;

    private final DenyPolicy denyPolicy;

    private final LinkedList<Runnable> queue = new LinkedList();

    private final ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }


    @Override
    public void offer(Runnable runnable) {

        if (queue.size() >= limit){
            denyPolicy.reject(runnable, this.threadPool);
        }else{
            queue.add(runnable);
            queue.notify();
        }
    }

    @Override
    public Runnable take() throws InterruptedException {

        synchronized (queue){
            while (queue.isEmpty()){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    throw e;
                }
            }
            return queue.removeFirst();
        }
    }

    @Override
    public int size() {
        return 0;
    }
}
