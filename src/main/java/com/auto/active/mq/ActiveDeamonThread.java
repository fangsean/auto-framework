package com.auto.active.mq;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2019-01-20
 * @Description: <p></p>
 */
public class ActiveDeamonThread extends Thread {

    private final ActiveMessageQueue queue;

    public ActiveDeamonThread(ActiveMessageQueue queue) {
        super(ActiveDeamonThread.class.getName());
        this.queue = queue;
        //ActiveDeamonThread
        setDaemon(true);
    }

    @Override
    public void run() {
        for (; ; ) {
            MethodMessage methodMessage = this.queue.take();
            methodMessage.execute();
        }
    }


}
