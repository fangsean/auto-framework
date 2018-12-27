package com.auto.concurrence.disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-26
 * @Description: <p>发布事件</p>
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;


    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long val) {
        //请求下一个事件序号:ringBuffer类似一个队列，next就是下一个槽
        long next = ringBuffer.next();
        //获取该序号对应的事件对象:用seq索引取出一个空到事件用于填充
        LongEvent longEvent = ringBuffer.get(next);
        longEvent.setValue(val);
        ringBuffer.publish(next);//发布事件
    }


}
