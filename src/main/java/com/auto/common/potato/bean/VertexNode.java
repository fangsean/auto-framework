package com.auto.common.potato.bean;


import com.auto.common.potato.tasks.AbstractTask;

/**
 * Author: Potato
 * Create Time: 2020/4/12
 * Description:顶点，构建AOV网
 */
public final class VertexNode {
    public int in;
    public Class<? extends AbstractTask> task;
    public EdgeNode first;
    public long currentTime;//当前时间，用于排序TreeMap
    public boolean await;//当前任务是否需要等待，false：不等待
    public boolean countDown;//完成任务后，是否需要解锁，false：不解锁

    public VertexNode(int in, Class<? extends AbstractTask> task, EdgeNode first) {
        this.in = in;
        this.task = task;
        this.first = first;
    }
}
