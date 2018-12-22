package com.auto.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */
public class NumUtil {

    public static Integer getNum(Integer threadNum) {
        Double abs = Math.abs(Math.sin(0.017453292519943295769236907684886 * threadNum) * (threadNum / 10));
        return abs.intValue();
    }

    public static long ipv42num(String ip) {
        String[] v4 = {};
        if (StringUtils.isBlank(ip) ||
                (v4 = ip.split("\\.")).length != 4) {
            return 0;
        }
        long v0 = (Long.valueOf(v4[0]) << 24);
        long v1 = (Long.valueOf(v4[1]) << 16);
        long v2 = (Long.valueOf(v4[2]) << 8);
        long v3 = Long.valueOf(v4[3]);
        return (v0 | v1 | v2 | v3);
    }

    public static String num2ipv4(long v) {
        StringBuilder ip = new StringBuilder();
        ip.append((v >> 24) & 0xff)
                .append(".").append((v >> 16) & 0xff)
                .append(".").append((v >> 8) & 0xff)
                .append(".").append(v & 0xff);
        return ip.toString();
    }

    public static void main(String[] args) {

        String ip = "255.255.255.254";
        long l = ipv42num(ip);
        String s = num2ipv4(l);
        System.out.println(s);

    }

}
