package com.auto.util;

public class DataUtil {

    /**
     * 字母
     *
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        String reg = "^[a-zA-Z]*$";
        return str.matches(reg);
    }

    /**
     * 数字
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        String reg = "^[0-9]*$";
        return str.matches(reg);
    }

}
