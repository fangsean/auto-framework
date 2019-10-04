package com.auto.leetcode;

import com.google.common.collect.Lists;

import java.util.LinkedList;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-18
 * @since JDK 1.8
 */
public class MinStack {

    int min = Integer.MAX_VALUE;

    private LinkedList<Integer> objects = null;
    private LinkedList<Integer> minList = null;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        objects = Lists.newLinkedList();
        minList = Lists.newLinkedList();
    }

    public void push(int x) {
        objects.addFirst(x);
    }

    public void pop() {
        objects.removeLast();
    }

    public int top() {
        int first = objects.getFirst();
        return first;
    }

    public int getMin() {
        return min;
    }

    private void checkMin() {
        int first = objects.getFirst();
    }

    public static void main(String[] args) {

        /**
         * Your MinStack object will be instantiated and called as such:
         */

        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin();   //--> 返回 -3.
        minStack.pop();
        minStack.top();      //--> 返回 0.
        minStack.getMin();   //--> 返回 -2.

    }

}

