package com.auto.active.mq;

import java.util.LinkedList;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2019-01-20
 * @Description: <p></p>
 */
public class ActiveMessageQueue {

    private final LinkedList<MethodMessage> message = new LinkedList<MethodMessage>();

    public ActiveMessageQueue() {
        new ActiveDeamonThread(this).start();
    }

    public void offer(MethodMessage methodMessage) {
        synchronized (this) {
            message.addLast(methodMessage);
            this.notify();
        }
    }

    protected MethodMessage take() {
        synchronized (this) {
            while (message.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return message.removeFirst();
        }
    }

}
