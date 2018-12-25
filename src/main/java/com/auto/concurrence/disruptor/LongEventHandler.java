package com.auto.concurrence.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-25
 * @Description: <p></p>
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long l, boolean b) throws Exception {
        System.out.println("event " + event);
    }
}
