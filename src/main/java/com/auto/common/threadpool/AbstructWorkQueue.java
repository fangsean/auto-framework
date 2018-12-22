package com.auto.common.threadpool;

import com.auto.common.struct.IWorkJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * @author auto.yin<auto.yin @ gmail.com>
 * @Description: <p></p>
 */
abstract
public class AbstructWorkQueue<T extends IWorkJob> {

    /**本地异常日志记录对象 */
    private static final Logger log = LoggerFactory.getLogger(AbstructWorkQueue.class);

    /** 并发处理线程数 */
    protected final int nThreads;

    /** 处理线程工作队列 */
    public final LinkedList<T> queue;

    /** 处理线程工作类型——任务 */
    public final static int RUN_TYPE_TASK = 1;

    /**
     *  0 在线式 : 线程一直在线，反复扫描队列，缺省模式；
     *  适用于银行、VC接口等

     *  1 任务式 : 触发任务做完即销毁线程、队列，任务完成后即离线，

     *  适用于前台触发的批量处理，如余额批冲，批销账等
     */
    protected int runType = 0;

    /** 队列增加完毕，仅在任务有用 */
    protected boolean queueAddOver = false;

    /** 允许用户终止线程，仅在任务有用 */
    protected boolean stop = false;

    /**
     * 获取具有线程池的工作队列(缺省是在线式)
     * @param nThreads int 最大并发处理线程
    */
//    public AbstructWorkQueue(int nThreads) {
//        this(nThreads, 0);
//    }

    /**
     * 获取具有线程池的工作队列
     * @param nThreads int 最大并发处理线程
     * @param runType int
     */
    public AbstructWorkQueue(int nThreads, int runType) {
        this.nThreads = nThreads;
        this.runType = runType;
        queue = new LinkedList();
    }

    public void setQueueAddOver() {
        synchronized (queue) {
            log.debug(" Queue Adding is Over ");
            this.queueAddOver = true; //
            this.queue.notifyAll();
        }
    }
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public int getRunType() {
        return this.runType;
    }

    public int getInitThreadSize() {
        return this.nThreads;
    }

    public int getQueueSize() {
        synchronized (queue) {
            return queue.size();
        }
    }

    public boolean isQueueAddOver() {
        return this.queueAddOver;
    }

    public boolean isStop() {
        return stop;
    }

    public void addWorkJobToQueue(T iWorkJob){
        synchronized (queue) {
            queue.addLast(iWorkJob);
            queue.notify();
        }
    }

    /***##########抽象函数 #############*/

    abstract void waitingThreadsOver();

    abstract boolean isQueueDealOver();
}
