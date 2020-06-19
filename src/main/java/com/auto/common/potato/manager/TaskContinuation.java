package com.auto.common.potato.manager;


import com.auto.common.potato.tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Potato
 * Create Time: 2020/4/12
 * Description:继续执行任务
 */
public final class TaskContinuation {
    //所有顶点
    private static List<Class<? extends AbstractTask>> aClassList;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private CountDownLatch countDownLatch;

    private static final TaskContinuation ourInstance = new TaskContinuation();

    public static TaskContinuation getInstance(List<Class<? extends AbstractTask>> list) {
        aClassList = list;
        return ourInstance;
    }

    private TaskContinuation() {
    }

    /**
     * 下一个任务
     *
     * @param task 任务
     * @return this
     */
    private List<Class<? extends AbstractTask>> taskCache = new ArrayList<>();

    /**
     * 生成一个单链表
     *
     * @param task 任务
     * @return ourInstance
     */
    public TaskContinuation then(Class<? extends AbstractTask> task) {
        //aClassList:start current
        //真实结构：start-current
        if (aClassList.size() == 0) return ourInstance;
        if (taskCache.contains(task)) return ourInstance;
        taskCache.add(task);
        aClassList.add(task);
        atomicInteger.getAndIncrement();
        return ourInstance;
    }

    public void enqueue() {
        if (aClassList.size() == 0) throw new NullPointerException("SuperStart:TaskNotFound!!!");
        countDownLatch = new CountDownLatch(atomicInteger.get());
        for (int i = 0; i < aClassList.size(); i++) {
            if (i == 0) {
                IOExecute(aClassList.get(0), false);
            } else {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                IOExecute(aClassList.get(i), (i == aClassList.size() - 1));
            }
        }
    }

    /**
     * 真正执行任务
     *
     * @param aClass 任务
     */
    private void IOExecute(Class<? extends AbstractTask> aClass, boolean isLast) {
//        TaskExecutorManager.getInstance().IOExecutorService().execute(new CompatRunnable(aClass, countDownLatch, isLast));
    }

}
