package com.auto.algorithm.tree.b;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-17
 * @Description: <p></p>
 */
public class BsTree {

    double data;
    BsTree left;
    BsTree right;

    public BsTree(double data) {
        this.data = data;
        left = null;
        right = null;
    }

    public void insert(BsTree root, double data) {
        if (data > root.data) {
            if (root.right == null) {
                root.right = new BsTree(data);
            } else {
                this.insert(root.right, data);
            }
        } else if (data < root.data) {
            if (root.left == null) {
                root.left = new BsTree(data);
            } else {
                this.insert(root.left, data);
            }
        } else {
            System.out.println("已存在：" + data);
        }
    }

}
