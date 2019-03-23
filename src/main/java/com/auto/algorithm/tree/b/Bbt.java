package com.auto.algorithm.tree.b;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-17
 * @Description: <p></p>
 */
public class Bbt {

    private double data;
    private int bf;
    Bbt left;
    Bbt right;

    public Bbt(double data) {
        this.data = data;
        this.bf = 0;
        left = null;
        right = null;
    }

    public Bbt rotationRight(Bbt root) {
        Bbt left = root.left;
        root.left = left.right;
        left.right = root;

        return left;
    }

}
