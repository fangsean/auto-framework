package com.auto.concurrence.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-25
 * @Description:
 * <p>每种策略都具有不同性能和优缺点，根据实际运行环境的 CPU 的硬件特点选择恰当的策略，并配合特定的 JVM 的配置参数，能够实现不同的性能提升</p>
 */
public class EventWaitStrategyFactory {

    //最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
    private static WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
    //性能表现跟 BlockingWaitStrategy 差不多，对 CPU 的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
    private static WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
    //性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于 CPU 逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
    private static WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();

    public static WaitStrategy getBlockingWait() {
        return BLOCKING_WAIT;
    }

    public static WaitStrategy getSleepingWait() {
        return SLEEPING_WAIT;
    }

    public static WaitStrategy getYieldingWait() {
        return YIELDING_WAIT;
    }
}
