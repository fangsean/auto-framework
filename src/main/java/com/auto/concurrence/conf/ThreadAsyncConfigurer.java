package com.auto.concurrence.conf;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */


@Slf4j
@Configuration
@EnableAsync//开启对异步任务的支持
public class ThreadAsyncConfigurer implements AsyncConfigurer {
    private static final Logger log = LoggerFactory.getLogger(ThreadAsyncConfigurer.class);

    public ThreadPoolConfig getConfig() {
        return config;
    }

    @Autowired
    @Getter
    private ThreadPoolConfig config;  // 配置属性类

    @Getter
    private static ThreadPoolTaskExecutor executor;

    public ThreadAsyncConfigurer setConfig(ThreadPoolConfig config) {
        this.config = config;
        return this;
    }

    public static void setExecutor(ThreadPoolTaskExecutor executor) {
        ThreadAsyncConfigurer.executor = executor;
    }

    private void getExecutor() {

        executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(config.getCorePoolSize());
        //设置最大线程数
        executor.setMaxPoolSize(config.getMaxPoolSize());
        //线程池所使用的缓冲队列
        executor.setQueueCapacity(config.getQueueCapacity());
        //等待任务在关机时完成--表明等待所有线程执行完true
        executor.setWaitForTasksToCompleteOnShutdown(false);
        //等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        /*executor.setAwaitTerminationSeconds(config.getAwaitTerminationSeconds());*/
        executor.setAllowCoreThreadTimeOut(true);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        //线程名称前缀
        executor.setThreadNamePrefix("Tradeweb-Anno-Executor-");

        // 当pool已经达到max size的时候，设置拒绝策略处理新任务 rejection-policy，
        /** 使用预定义的异常处理类 解释 **
         *AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
         *CallerRunsPolicy:不在新线程中执行任务，而是由调用者所在的线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
         *DiscardOldestPolicy:抛弃旧的任务、暂不支持；会导致被丢弃的任务无法再次被执行
         *DiscardPolicy:抛弃当前任务、暂不支持；会导致被丢弃的任务无法再次被执行
         **/
        executor.setRejectedExecutionHandler(
                /*new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        // .....
                    }
                }*/
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // 初始化线程
        executor.initialize();

    }


    @Override
    @Bean
    public Executor getAsyncExecutor() {

        if (null == executor) {
            synchronized (ThreadAsyncConfigurer.class) {
                if (null == executor) {
                    this.getExecutor();
                }
            }
        }

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        // 异步任务中异常处理
        return new AsyncUncaughtExceptionHandler() {

            @Override
            public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
                log.error("==========================" + arg0.getMessage() + "=======================", arg0);
                log.error("exception method:" + arg1.getName());
            }
        };
    }


}