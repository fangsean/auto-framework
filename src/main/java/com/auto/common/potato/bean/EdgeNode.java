package com.auto.common.potato.bean;

/**
 * Author: Potato
 * Create Time: 2020/4/12
 * Description:边（单链表结构）构建AOV网
 */
public final class EdgeNode {
    public int index;//下标
    public EdgeNode next;

    public EdgeNode(int index, EdgeNode next) {
        this.index = index;
        this.next = next;
    }
}
