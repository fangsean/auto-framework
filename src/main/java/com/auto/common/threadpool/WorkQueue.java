package com.auto.common.threadpool;

import lombok.Getter;
import org.slf4j.LoggerFactory;

/**
 * @author jsen.yin<jsen.yin @ gmail.com>
 * @Description:
 * <p>通过调用workqueue工作队列就能创建内核线程</p>
 */
//@Slf4j
public class WorkQueue extends AbstructWorkQueue {


    /** 自定义Thread子线程类 */
    @Getter
    private Thread[] threads;

    /**本地异常日志记录对象 */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WorkQueue.class);

    public WorkQueue(int nThreads, int runType) {
        super(nThreads, runType);

        threads = new PoolWorkerThread[this.nThreads];
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorkerThread(this);
            threads[i].start();
            log.debug("nThreads " + i + " is working");
        }
    }

    public WorkQueue(int nThreads) {
        this(nThreads, 0);
    }

    @Override
    public boolean isQueueDealOver() {
        if (this.queue.isEmpty()){
            for (int i = 0; i < this.threads.length; i++) {
                if (this.threads[i].isAlive()){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void waitingThreadsOver() {
        if(runType == AbstructWorkQueue.RUN_TYPE_TASK){
            for (int i = 0; i < this.threads.length; i++) {
                try {
                    this.threads[i].join();
                } catch (InterruptedException e) {
                    log.error("nThreads " + i + " is Interrupted Exception");
                }
            }
        }
    }

}
