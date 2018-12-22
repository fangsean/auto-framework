package com.auto.common.threadpool;

import com.auto.common.struct.IWorkJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * @author auto.yin<auto.yin @ gmail.com>
 * @Description: <p>zi定义多线程</p>
 */
//@Slf4j
public class PoolWorkerThread extends Thread{

    /** 共享的等待处理工作类 */
    private WorkQueue workQueue;

    /** 共享的等待处理队列 */
    private LinkedList queue;

    /**本地异常日志记录对象 */
    private static final Logger log = LoggerFactory.getLogger(AbstructWorkQueue.class);

    /**
     * 构造函数
     */
    public PoolWorkerThread(WorkQueue workQueue) {
        this.workQueue = workQueue;
        this.queue = this.workQueue.queue;
    }

    public void run() {
        IWorkJob iWorkJob;
        while (true) {
            synchronized (this.queue) {
                while (this.queue.isEmpty()) {
                    try {
                        if (this.workQueue.getRunType() == WorkQueue.RUN_TYPE_TASK
                                && this.workQueue.isQueueAddOver()) { //任务式,才判断,队列增加已经完毕
                            log.debug("---PoolWorkerThread Work Over---");
                            return; //返回
                        }
                        log.debug("---PoolWorkerThread Work Wait---");
                        this.queue.wait();
                    } catch (Exception ex) {
                        log.error("---Queue Exception---", ex);
                    }
                }
                log.info("---Queue Size : "+ queue.size());
                /** 按照顺序处理队列 */
                iWorkJob = (IWorkJob)this.queue.removeFirst();
            }
            //用户强制终止
            if(this.workQueue.isStop() && this.workQueue.getRunType() == WorkQueue.RUN_TYPE_TASK){
                return;
            }
            //执行接口方法
            try {
                iWorkJob.doJob();
            } catch (Exception ex) {
                log.error("---PoolWorkerThread Exception---", ex);
            }
        }
    }

}
