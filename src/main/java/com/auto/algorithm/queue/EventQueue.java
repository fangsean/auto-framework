package com.auto.algorithm.queue;

import lombok.extern.log4j.Log4j;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 执行队列添加释放，
 * ①满了等待添加，空了等待释放，双向处理事件
 * ②FIFO
 */
@Log4j
public class EventQueue {


    private final int maxEvent;

    private final static int DEFAULT_MAX_EVENT = 10;

    private final LinkedList<Event> eventQueue = new LinkedList<>();

    public EventQueue(int maxEvent) {
        this.maxEvent = maxEvent;
    }

    public EventQueue() {
        this(DEFAULT_MAX_EVENT);
    }

    public void offer(Event event) {
        synchronized (eventQueue) {
            if (eventQueue.size() >= this.maxEvent) {
                try {
                    System.err.println("the queue is full, waitting ...");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("new event submitting");
            eventQueue.add(event);
            eventQueue.notify();
        }
    }


    public Event take() {
        synchronized (eventQueue) {
            if (eventQueue.isEmpty()) {
                System.err.println("the queue is null,waitting ...");
                try {
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Event event = eventQueue.removeFirst();
            eventQueue.notify();
            System.out.println("the event " + event + " is handled");
            return event;
        }
    }

    static class Event {
    }


    public static void main(String[] args) {

        final EventQueue eventQueue = new EventQueue();

        new Thread(() -> {
            for (; ; ) {
                eventQueue.offer(new Event());
            }
        }, "producer").start();

        new Thread(()->{
            for (;;){
                eventQueue.take();
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"cusumer").start();

    }

}
