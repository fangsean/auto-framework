package com.auto.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-17
 * @Description: <p></p>
 */
public interface BinaryTree <E> extends Iterable{

    //查找一个元素是否在树中
    boolean search(E e);

    //给树插入一个元素
    boolean insert(E e);

    //在树中删除一个元素
    boolean remove(E e);

    //打印以中序遍历的结点
    void inorder();

    //打印以前序遍历的结点
    void preorder();

    //打印以后序遍历的节点
    void postorder();

    //返回树中的元素个数
    int getSize();

    //如果树为空则返回true
    boolean isEmpty();

    //返回遍历元素的迭代器
    Iterator<E> iterator();

    //删除树中所有元素
    void clear();

    //返回遍历的路径
    ArrayList<E> path(E e);
}
