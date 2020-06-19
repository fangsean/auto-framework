package com.auto.common.potato;

import com.auto.common.potato.bean.TaskBeanHelp;
import com.auto.common.potato.manager.TaskManager;
import com.auto.common.potato.tasks.AbstractTask;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

/**
 * Author: Potato
 * Create Time: 2020/4/12
 * Description: 具体任务执行
 */
public final class CompatRunnable implements Runnable {

    private volatile boolean countDown;
    private TreeMap<TaskBeanHelp, Class<? extends AbstractTask>> taskMap;
    private Class<? extends AbstractTask> singleTask;
    private CountDownLatch mCountDownLatch;
    private CountDownLatch mCountDownLatchTime;
    private Class<? extends AbstractTask> aClass;

    public CompatRunnable(Class<? extends AbstractTask> single, CountDownLatch countDownLatch, CountDownLatch countDownLatchTime, boolean countDown) {
        singleTask = single;
        this.mCountDownLatch = countDownLatch;
        this.mCountDownLatchTime = countDownLatchTime;
        this.countDown = countDown;
    }

    public CompatRunnable(TreeMap<TaskBeanHelp, Class<? extends AbstractTask>> taskMap) {
        this.taskMap = taskMap;
    }

    @Override
    public void run() {
        //一次性执行所有任务
        if (taskMap != null && taskMap.size() > 0) {
            for (Map.Entry<TaskBeanHelp, Class<? extends AbstractTask>> entry : taskMap.entrySet()) {
                Class<? extends AbstractTask> aClass = entry.getValue();
                AbstractTask task = null;
                try {
                    task = aClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                Objects.requireNonNull(task).doTask();
            }
        } else if (singleTask != null) {//单独执行任务
            AbstractTask mTask = null;
            try {
                mTask = singleTask.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!TaskManager.taskCacheList.contains(singleTask)) {
                TaskManager.taskCacheList.add(singleTask);
//                long start = System.currentTimeMillis();
                Objects.requireNonNull(mTask).doTask();
//                long diff = System.currentTimeMillis() - start;
//                System.out.println("耗时："+ diff);
//                log();
                if (countDown) {//当前任务需要解锁
                    mCountDownLatch.countDown();
                }
                mCountDownLatchTime.countDown();
            }
        }
    }

    private void log() {
        String str = "";
        if (countDown) {
            str = "需要解锁";
        } else {
            str = "不需要解锁";
        }
        System.out.println("singleTask:" + singleTask + " --" + str);
    }

}
