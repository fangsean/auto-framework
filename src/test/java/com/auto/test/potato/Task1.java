package com.auto.test.potato;


import com.auto.common.potato.tasks.AbstractTask;

/**
 * Author: Potato
 * Create Time: 2020/4/11
 * Description:
 */
public class Task1 extends AbstractTask {
    @Override
    public void doTask() {
        System.out.println("任务一");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
