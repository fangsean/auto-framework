package com.auto.algorithm.queue;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: <p>链式队列</p>
 */
@Slf4j
public class LinkedQueue<T> {

    /**
     * 计数器
     */
    private int count;
    /**
     * 头指针节点
     */
    private Node<T> front;
    /**
     * 尾指针节点
     */
    private Node<T> rear;

    public LinkedQueue() {
        this.count = 0;
        this.front = null;
        this.rear = null;
    }

    public int getCount() {
        return this.count;
    }

    public Node<T> getFront() {
        return front;
    }

    public Node<T> getRear() {
        return rear;
    }

    public LinkedQueue<T> setRear(Node<T> rear) {
        this.rear = rear;
        return this;
    }

    public boolean isEmpty() {
        if (getCount() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 入队列
     *
     * @param data
     * @return
     */
    public boolean append(T data) {

        Node<T> node = new Node<T>(data, null);

        //标识头节点
        if (this.front == null) {
            this.front = node;
        }
        //下一个节点指针指向新增节点数据
        if (this.rear != null) {
            this.rear.next = node;
        }
        //标识尾节点
        this.rear = node;

        ++this.count;
        return true;
    }

    /**
     * @return
     */
    public Node<T> delete() {

        if (isEmpty()) return null;

        Node<T> value = this.front;
        this.front = this.front.next;
        --this.count;
        return value;

    }


    /**
     * 节点类
     *
     * @param <T>
     */
    static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(Node<T> next) {
            this.next = next;
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public final T setValue(T newValue) {
            T oldValue = value;
            value = newValue;
            return oldValue;
        }

        public T getValue() {
            return value;
        }


        public Node<T> setNext(Node<T> next) {
            this.next = next;
            return this;
        }

        public Node getNext() {
            return next;
        }

        public String toString() {
            return value.toString();
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Node) {
                Node<T> tmp = (Node<T>) o;
                if (tmp.value.equals(value) &&
                        tmp.next.equals(next))
                    return true;
            }
            return false;
        }
    }


    public static void main(String[] args) {

        LinkedQueue<String> stringLinkedQueue = new LinkedQueue<>();
        stringLinkedQueue.append("1111111111");
        stringLinkedQueue.append("22222222222222");
        stringLinkedQueue.append("2223333333333");
        stringLinkedQueue.append("555555555555");
        stringLinkedQueue.append("666666666");
        stringLinkedQueue.append("444444444");

        Node<String> front = stringLinkedQueue.getFront();
        Node<String> rear = stringLinkedQueue.getRear();
        while (!stringLinkedQueue.isEmpty()) {
            Node<String> delete = stringLinkedQueue.delete();
            boolean equals = delete.equals(front);
            boolean equals1 = delete.equals(rear);
            System.out.println(equals + "/" + equals1);
        }

    }

}

