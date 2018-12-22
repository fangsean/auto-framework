package com.auto.quartz;

import com.auto.common.job.ScanWorkJob;
import com.auto.common.struct.IStartUpClass;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author auto.yin<auto.yin @ gmail.com>
 * @Description: <p></p>
 */

@Slf4j
@Configuration
@Component
@EnableScheduling
public class ScheduleJobs implements IStartUpClass {

    private ScanWorkJob scanWorkJob;

    /** 如果扫描配置文件的扫描时间间隔 */
    private final static int SCAN_INTERVAL = 3 ;

    @Override
//    @Scheduled(cron = "0/4 * * * * ?")
    public void execute() {

        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = schedFact.getScheduler();
            // 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
            //该Job负责定义需要执行任务
            this.scheduleJob(scheduler, ScanWorkJob.class);
            scheduler.start();
        } catch (SchedulerException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    private  void scheduleJob(Scheduler scheduler, Class clazz) throws SchedulerException {

        String canonicalName = clazz.getCanonicalName();

        String _job = canonicalName.concat("_Job");
        String _jobGroup = canonicalName.concat("_JobGroup");

        String _trigger = canonicalName.concat("_Trigger");
        String _triggerGroup = canonicalName.concat("_TriggerGroup");

        JobDetail jobDetail = JobBuilder.newJob(clazz) .withIdentity(_job, _jobGroup).build();
        /*CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/4 * * * * ?");*/
        // 每3s执行一次
        /*CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(_trigger, _triggerGroup) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);*/

//        JobDetail jobDetail001 = JobBuilder.newJob(clazz) .withIdentity(_job.concat("002"), _jobGroup).build();
//        CalendarIntervalScheduleBuilder calendarIntervalScheduleBuilder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule();
//        calendarIntervalScheduleBuilder.withIntervalInSeconds(SCAN_INTERVAL);
//        CalendarIntervalTrigger calendarIntervalTrigger = TriggerBuilder.newTrigger().withIdentity(_trigger.concat("2"), _triggerGroup).withSchedule(calendarIntervalScheduleBuilder).build();
//        scheduler.scheduleJob(jobDetail001,calendarIntervalTrigger);

        Trigger cronTrigger = newTrigger()
                .withIdentity(_trigger, _triggerGroup)
                .startAt(futureDate(0, DateBuilder.IntervalUnit.MINUTE)) // use DateBuilder to create a date in the future
                .forJob(jobDetail) // identify job with its JobKey
                .build();

        scheduler.scheduleJob(jobDetail,cronTrigger);

    }


    public static void main(String[] args) {
        IStartUpClass scheduleJobs = new ScheduleJobs();
        scheduleJobs.execute();
    }


}
