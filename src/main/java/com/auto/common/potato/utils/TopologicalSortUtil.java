package com.auto.common.potato.utils;

import com.auto.common.potato.bean.EdgeNode;
import com.auto.common.potato.bean.TaskBeanHelp;
import com.auto.common.potato.bean.VertexNode;
import com.auto.common.potato.tasks.AbstractTask;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Author: Potato
 * Create Time: 2020/4/12
 * Description:拓扑排序
 */
public class TopologicalSortUtil {

    /**
     * @param graphAOV 生成的AOV网
     * @return TreeMap
     */
    public static TreeMap<TaskBeanHelp, Class<? extends AbstractTask>> sort(List<VertexNode> graphAOV) {
        TreeMap<TaskBeanHelp, Class<? extends AbstractTask>> taskMap = new TreeMap<>(new Comparator<TaskBeanHelp>() {
            @Override
            public int compare(TaskBeanHelp t1, TaskBeanHelp t2) {
                return Long.compare(t1.currentTime, t2.currentTime);
            }
        });
        int top = 0;
        //准备栈，大小=顶点数量+1
        int[] stack = new int[graphAOV.size() + 1];
        for (int i = 0; i < graphAOV.size(); i++) {
            if (graphAOV.get(i).in == 0) {
                stack[++top] = i;
            }
        }
        while (top != 0) {
            int index = stack[top--];
            Class<? extends AbstractTask> task = graphAOV.get(index).task;
            TaskBeanHelp taskBeanHelp = new TaskBeanHelp();
            taskBeanHelp.currentTime = graphAOV.get(index).currentTime;
            taskBeanHelp.await = graphAOV.get(index).await;
            taskBeanHelp.countDown = graphAOV.get(index).countDown;
            taskMap.put(taskBeanHelp, task);
            for (EdgeNode e = graphAOV.get(index).first; e != null; e = e.next) {
                //获取顶点后面的边，如果存在边
                int edgeIndex = e.index;
                (graphAOV.get(edgeIndex).in)--;//入度减一
                if (graphAOV.get(edgeIndex).in == 0) {
                    stack[++top] = edgeIndex;
                }
            }
        }
        System.out.println("排序后的任务：" + taskMap);
        return taskMap;
    }
}
