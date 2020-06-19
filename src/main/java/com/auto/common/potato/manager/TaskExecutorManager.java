package com.auto.common.potato.manager;

import java.util.concurrent.*;

public class TaskExecutorManager {
    private static volatile TaskExecutorManager taskExecutorManager;
    private ThreadPoolExecutor CPUThreadPoolExecutor;
    private ExecutorService IOExecutorService;
    private  final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private  final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5));
    private  final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>();
    public static TaskExecutorManager getInstance() {
        if (taskExecutorManager ==null){
            synchronized (TaskExecutorManager.class){
                if (taskExecutorManager ==null){
                    taskExecutorManager =new TaskExecutorManager();
                }
            }
        }
        return taskExecutorManager;
    }
    //初始化线程池
    private TaskExecutorManager(){
        CPUThreadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, CORE_POOL_SIZE, 15, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        CPUThreadPoolExecutor.allowCoreThreadTimeOut(true);
        IOExecutorService = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
    }
    public ThreadPoolExecutor CPUThreadPoolExecutor() {
        return CPUThreadPoolExecutor;
    }
    public ExecutorService IOExecutorService() {
        return IOExecutorService;
    }
}
