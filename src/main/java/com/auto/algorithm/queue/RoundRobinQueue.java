package com.auto.algorithm.queue;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @Description: <p>循环、顺序队列</p>
 */
@Slf4j
@Data
final
public class RoundRobinQueue<T> {

    /**
     * 定义队列长度
     */
    private final int length;

    /**
     * 头指针
     */
    private int front;
    /**
     * 尾指针
     */
    private int rear;
    /**
     * 数据队列
     */
    private T[] queueList;

    /**
     * 初始化参数 队列长度
     *
     * @param length
     */
    public RoundRobinQueue(Class<T> type, final int length) {
        this.length = length;
        this.front = 0;
        this.rear = 0;
        this.queueList = (T[]) Array.newInstance(type, length);
//        this.queueList = new Object[length];
    }

    /**
     * 判断为空
     *
     * @return
     */
    public boolean isEmpty() {
        if (front == rear) {
            return true;
        }
        return false;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isFull() {
        if (Math.floorMod((rear + 1), length) == front) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 入队列
     *
     * @param data
     * @return
     */
    public boolean inQueue(T data) {
        if (!isFull()) {
            rear = Math.floorMod((rear + 1), length);
            this.queueList[rear] = data;
            return true;
        } else {
            log.error("Queue is Full");
            return false;
        }
    }

    /**
     * 出队列,获取头指针元素
     *
     * @return
     */
    public T outQueue() {
        if (!isEmpty()) {
            front = Math.floorMod((front + 1), length);
            return this.queueList[front];
        } else {
            log.error("Queue is Empty");
            return null;
        }
    }


    public static void main(String[] args) {

        int length = 12;
        RoundRobinQueue<Integer> integerRoundRobinQueue = new RoundRobinQueue<>(Integer.class, length);

        for (int i = 0; i < length; i++) {
            System.out.println(integerRoundRobinQueue.inQueue(i));
        }
        System.out.println(integerRoundRobinQueue.outQueue());
        integerRoundRobinQueue.inQueue(12);
        System.out.println(Arrays.toString(integerRoundRobinQueue.queueList));
        while (!integerRoundRobinQueue.isEmpty()) {
            System.out.println(integerRoundRobinQueue.outQueue());
        }


    }


}
