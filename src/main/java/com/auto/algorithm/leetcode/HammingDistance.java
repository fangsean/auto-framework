package com.auto.algorithm.leetcode;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-13
 * @since JDK 1.8
 */
public class HammingDistance {

    public static int hammingDistance(int x, int y) {
        //int count = Integer.toBinaryString(x ^ y).replaceAll("0", "").length();
        int count = Integer.bitCount(x + y);
        return count;

    }

    public static void main(String[] args) {
        System.out.println(hammingDistance(1, 4));
    }

}
    
    