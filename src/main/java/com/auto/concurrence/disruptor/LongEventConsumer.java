package com.auto.concurrence.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-25
 * @Description: <p>事件到消费者</p>
 */
public class LongEventConsumer implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long l, boolean b) throws Exception {
        System.out.println("seq: " + l + " ,boolean: " + b + " ,event " + event);
    }
}
