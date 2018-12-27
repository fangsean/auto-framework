package com.auto.concurrence.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-25
 * @Description: <p></p>
 */
public class EventMain {

    public static void main(String[] args) {

        /*ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };*/

        int bufferSize = 1024 * 16;//RingBuffer 大小必须是 2 的 N 次方

        ExecutorService executor = Executors.newCachedThreadPool();

        LongEventFactory factory = new LongEventFactory();

        WaitStrategy wait = EventWaitStrategyFactory.getYieldingWait();

        LongEventConsumer handler = new LongEventConsumer();

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, executor /*threadFactory*/, ProducerType.SINGLE, wait);

        EventHandlerGroup<LongEvent> longEventEventHandlerGroup = disruptor.handleEventsWith(handler);

        disruptor.start();

        // 发布事件；
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);

        for (int i=0;;i++){
            producer.onData(i);
        }

    }


}
