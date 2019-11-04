package com.auto.test;

import java.util.HashMap;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-11-01
 * @Description: <p></p>
 */
public class HashMapTest {
    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static void main(String[] args) {
//        int i1 = 1 << 30;
//        System.out.println(i1);
//        int i = tableSizeFor(i1);
//        System.out.println(i);

        System.out.println(4<<2);



    }

}
