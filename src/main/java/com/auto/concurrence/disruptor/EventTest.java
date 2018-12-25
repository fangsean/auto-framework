package com.auto.concurrence.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-25
 * @Description: <p></p>
 */
public class EventTest {

    private Disruptor init() {

        int bufferSize = 16;//RingBuffer 大小必须是 2 的 N 次方

        ExecutorService executor = Executors.newCachedThreadPool();

        LongEventFactory factory = new LongEventFactory();

        WaitStrategy wait = EventWaitStrategy.getYieldingWait();

        LongEventHandler handler = new LongEventHandler();

        Disruptor disruptor = new Disruptor<LongEvent>(factory, bufferSize, executor, ProducerType.SINGLE, wait);

        EventHandlerGroup group = disruptor.handleEventsWith(handler);

        /*disruptor.start();*/

        return disruptor;
    }


    public static void main(String[] args) {
        EventTest eventTest = new EventTest();

        Disruptor disruptor = eventTest.init();

        disruptor.start();

        // 发布事件；
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();//请求下一个事件序号；
        try {
            LongEvent event = ringBuffer.get(sequence);//获取该序号对应的事件对象；
//            long data = getEventData();//获取要通过事件传递的业务数据；
            event.setValue(12L);
        } finally {
            ringBuffer.publish(sequence);//发布事件；}
        }
    }


}
