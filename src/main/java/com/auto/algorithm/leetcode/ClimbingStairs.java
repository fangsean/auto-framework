package com.auto.algorithm.leetcode;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-13
 * @since JDK 1.8
 */
public class ClimbingStairs {

    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int temp1 = 1, temp2 = 2, sum = 0;
        for (int i = 2; i < n; i++) {
            sum = temp1 + temp2;
            temp1 = temp2;
            temp2 = sum;
        }
        return sum;
    }

    public int climbStairs1(int n) {
        int c = 0;
        if (n <= 2) {
            return n;
        }
        return climbStairs1(n - 1) + climbStairs1(n - 2);
    }


    public static void main(String[] args) {
        System.out.println(new ClimbingStairs().climbStairs(10));
    }


}
    
    