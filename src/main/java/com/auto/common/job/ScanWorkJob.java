package com.auto.common.job;

import com.auto.common.threadpool.WorkQueue;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author jsen.yin<jsen.yin @ gmail.com>
 * @Description: <p></p>
 */
@Slf4j
@Configuration
@Component
public class ScanWorkJob implements StatefulJob {

    private final static int threadCount = 60;

    /**本地异常日志记录对象 */
    private static final Logger log = LoggerFactory.getLogger(ScanWorkJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info(String.format(">>>>>>>>>>>>>>>>>>>>>>>>>>线程开始：%s", System.currentTimeMillis()));

        ScanWorkJobProcess scanWorkJobProcess = new ScanWorkJobProcess();

        scanWorkJobProcess.begin();

        final WorkQueue workQueue = new WorkQueue(threadCount,1);//任务式
        for(int i = 0; i<this.threadCount; i++){
            ReflexWorkJob processWorkJob =
                    new ReflexWorkJob(
                            ScanWorkJobProcess.class,
                            "process00"+(i%3+1),
                            (i%2==0)?true:false
                    );

            workQueue.addWorkJobToQueue(processWorkJob);
        }
        // 通知队列任务增加完毕, 注意改方法必须被调用，否则，线程不会停止工作
        workQueue.setQueueAddOver();
        //等待队列处理完毕
        workQueue.waitingThreadsOver();
        boolean flag = true;
        while (!workQueue.isQueueDealOver()){
            try {
                log.info("等待了...");
//                System.out.println(workQueue.getThreads().length=0);
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                workQueue.queue.clear();
                flag = false;
                break;
            }//就引用下扫描间隔时间，不另外加配置了
        }
        if(flag) {
            scanWorkJobProcess.over();
        }
        log.info(String.format("<<<<<<<<<<<<<<<<<<<<<<<<<<线程结束：%s", System.currentTimeMillis()));

    }


    public static void main(String[] args) throws JobExecutionException {
        ScanWorkJob scanWorkJob = new ScanWorkJob();
        scanWorkJob.execute(null);
    }

}
