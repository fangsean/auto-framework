package com.auto.algorithm.leetcode;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-11-07
 * @Description: <p>
 * x^2 = y
 * ==> f(x) = x^2 - y
 * 2x^2 = y + x^2
 * 2x = y/x + x
 * x' = y/2x + x/2
 * x' = (y/x + x)/2
 * <p>
 * eg:
 * public static final Double sqrt(double y) {
 * if (y < 0) {
 * return Double.NaN;
 * }
 * double err = 1e-5;
 * double x1 = 1;
 * double x2 = y / (2 * x1) + x1 / 2;
 * //  f(x) = x^2 - y > 0
 * while (Math.abs(x2 - x1) > err) {
 * x1 = x2;
 * x2 = (y / x1 + x1) / 2;
 * }
 * return x2;
 * }
 * </p>
 */
public class Sqrt {

    public static final Double sqrt(double y) {
        double x = y;
        if (y < 0) {
            return Double.NaN;
        }
        double err = 1e-5;
        //  f(x) = x^2 - y > 0
        while (Math.abs(y / x - x) > err) {
            x = (y / x + x) / 2;
        }
        return x;
    }

    public static void main(String[] args) {
    }


}
