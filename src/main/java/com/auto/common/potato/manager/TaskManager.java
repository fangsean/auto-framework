package com.auto.common.potato.manager;


import com.auto.common.potato.CompatRunnable;
import com.auto.common.potato.bean.EdgeNode;
import com.auto.common.potato.bean.TaskBeanHelp;
import com.auto.common.potato.bean.VertexNode;
import com.auto.common.potato.tasks.AbstractTask;
import com.auto.common.potato.utils.TopologicalSortUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

/**
 * Author: Potato
 * Create Time: 2020/4/11
 * Description: 任务管理者
 */
public class TaskManager {
    //执行过的任务缓存，避免重复执行。如果缓存里有该任务的执行记录，就不再执行
    public static volatile List<Class<? extends AbstractTask>> taskCacheList = new ArrayList<>();

    private CountDownLatch countDownLatch;//目的：阻塞任务执行
    private CountDownLatch countDownLatchTime;//目的：当所有任务执行完成后，打印耗时

    private static TaskManager instance = new TaskManager();

    public static TaskManager getInstance() {
        return instance;
    }

    private TaskManager() {
    }

    //AOV网
    private List<VertexNode> graphAOV = new ArrayList<>();
    private List<Class<? extends AbstractTask>> list = new ArrayList<>();//单链表任务，无需排序
    private List<Class<? extends AbstractTask>> taskCache = new ArrayList<>();//任务缓存

    public TaskManager add(Class<? extends AbstractTask> task) {
        VertexNode vertexNode = new VertexNode(0, task, null);
        vertexNode.currentTime = System.currentTimeMillis();
        if (!taskCache.contains(task)) {//避免重复添加任务
            graphAOV.add(vertexNode);
            taskCache.add(task);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    private boolean isDependOn;
    private long extraBlock;//额外耗时

    //单依赖
    public TaskManager dependOn(Class<? extends AbstractTask> task) {
        if (graphAOV.size() == 0) return this;
        if (graphAOV.get(graphAOV.size() - 1).task == task) {
            return this;//A dependOn A，不做处理
        }
        if (taskCache.contains(task)) {//如果已经存在（例如：add(A).add(B).dependOnAll(A)）
            check(task);
        } else {
            VertexNode current = new VertexNode(0, task, null);
            VertexNode last = graphAOV.get(graphAOV.size() - 1);
            last.in += 1;
            current.first = new EdgeNode(graphAOV.size() - 1, null);
            current.currentTime = last.currentTime;
            last.currentTime = System.currentTimeMillis();
            last.await = true;//任务需要等待
            current.countDown = true;//任务需要解锁
            graphAOV.add(current);
        }
        isDependOn = true;
        countDownLatch = new CountDownLatch(1);//每次使用dependOn就新建一把锁
        try {
            Thread.sleep(100);
            extraBlock += 100;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 全依赖（前面所有add的任务，都要被阻塞）
     *
     * @param task 新任务
     * @return
     */
    public TaskManager dependOnAll(Class<? extends AbstractTask> task) {
        if (graphAOV.size() == 0) return this;
        if (graphAOV.get(graphAOV.size() - 1).task == task) {
            return this;//A dependOn A，不做处理
        }
        if (taskCache.contains(task)) {//如果已经存在（例如：add(A).add(B).dependOnAll(A)）
            check(task);
        } else {
            //将add的任务全部阻塞add(A).add(B).dependOnAll(C),执行顺序是C-B-A，或者C-A-B
            VertexNode current = new VertexNode(0, task, null);
            VertexNode first = graphAOV.get(0);
            VertexNode last = graphAOV.get(graphAOV.size() - 1);
            last.in += 1;
            current.first = new EdgeNode(graphAOV.size() - 1, null);
            for (int i = 0; i < graphAOV.size(); i++) {
                graphAOV.get(i).await = true;//任务需要等待
            }
            current.countDown = true;//任务需要解锁
            graphAOV.add(current);
        }
        isDependOn = true;
        countDownLatch = new CountDownLatch(1);//每次使用dependOn就新建一把锁
        try {
            Thread.sleep(100);
            extraBlock += 100;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 任务检查
     *
     * @param task 依赖任务
     */
    private void check(Class<? extends AbstractTask> task) {
        for (int i = 0; i < graphAOV.size(); i++) {
            VertexNode vertexNode = graphAOV.get(i);
            if (vertexNode.task == task && vertexNode.in > 0) {
                vertexNode.first = new EdgeNode(i, null);//在已存在的顶点上加一条边
                vertexNode.countDown = true;//任务需要解锁
                VertexNode last = graphAOV.get(graphAOV.size() - 1);
                last.await = true;//任务需要等待
                break;
            }
//            if (vertexNode.task == task) {
//                throw new RuntimeException(task + " is repetitive!!! please delete the function：add(Task.class),before the function:dependOn(Task.class)");
//            }
        }
    }

    private long start;//开始时间

    public void enqueue() {
        if (graphAOV.size() == 0)
            throw new NullPointerException("SuperStart:TaskNotFound!!!");
        //拓扑排序后的所有任务
        TreeMap<TaskBeanHelp, Class<? extends AbstractTask>> taskMap = TopologicalSortUtil.sort(graphAOV);
        start = System.currentTimeMillis();
        countDownLatchTime = new CountDownLatch(taskMap.size());
        if (isDependOn) {//用于阻塞任务
            for (Map.Entry<TaskBeanHelp, Class<? extends AbstractTask>> entry : taskMap.entrySet()) {
                IOExecute(entry.getValue(), entry.getKey().await, entry.getKey().countDown);
            }
        } else {//非阻塞任务
            TaskExecutorManager.getInstance().IOExecutorService().execute(new CompatRunnable(taskMap));
        }
        try {
            countDownLatchTime.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AllTime();
    }

    /**
     * 任务总耗时
     */
    private void AllTime() {
        long diff = System.currentTimeMillis() - start - extraBlock;
        String time = diff > 1000 ? (diff / 1000) + "秒" : diff + "毫秒";
        System.out.println("总耗时：" + time);
        extraBlock = 0;
    }

    /**
     * 真正执行任务
     */
    private void IOExecute(Class<? extends AbstractTask> single, boolean await, boolean countDown) {
        if (await) {
            try {
//                System.out.println("阻塞任务：" + single);
                countDownLatch.await();//阻塞任务
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        TaskExecutorManager.getInstance().IOExecutorService().execute(new CompatRunnable(single, countDownLatch, countDownLatchTime, countDown));
    }

    public TaskContinuation startWith(Class<? extends AbstractTask> task) {
        list.add(task);
        return TaskContinuation.getInstance(list);
    }
}
