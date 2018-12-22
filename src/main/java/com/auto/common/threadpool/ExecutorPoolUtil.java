package com.auto.common.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */


public final class ExecutorPoolUtil {

    /**
     * 固定大小线程池
     */
    private final static ExecutorService pool = Executors.newFixedThreadPool(3);

    private static ExecutorPoolUtil epu = new ExecutorPoolUtil();

    private ExecutorPoolUtil() {}

    /**
     *
     * @Title: getInstance
     * @Description 获取对象实例
     * @return
     * @date: 2018年7月4日--下午5:29:28
     */
    public static ExecutorPoolUtil getInstance() {
        return epu;
    }

    /**
     *
     * @Title: getExecutorPool
     * @Description 获取固定大小的线程池
     * @return
     * @date: 2018年7月4日--下午5:29:11
     */
    public static ExecutorService getExecutorPool() {
        return pool;
    }
}

