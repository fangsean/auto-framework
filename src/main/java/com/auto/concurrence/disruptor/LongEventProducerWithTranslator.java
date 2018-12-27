package com.auto.concurrence.disruptor;

import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-26
 * @Description: <p>使用translator方式到事件生产者发布事件</p>
 */
public class LongEventProducerWithTranslator extends LongEventProducer{


    private static final EventTranslatorVararg<LongEvent> eventTranslatorVararg = new EventTranslatorVararg<LongEvent>() {
        @Override
        public void translateTo(LongEvent longEvent, long seq, Object... objects) {
            longEvent.setValue((long) objects[0]);
        }
    };

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        super(ringBuffer);
    }
}
