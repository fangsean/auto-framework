package com.auto.common.adapter;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * java日期数据类型适配器
 *
 * @XmlJavaTypeAdapter，使用定制的适配器（即扩展抽象类XmlAdapter并覆盖marshal()和unmarshal()方法），以序列化Java类为XML。 使用方法：
 * 在指定的属性或字段上添加注解
 * @XmlJavaTypeAdapter(value=DateAdapter.class, type=Date.class)
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    private static SimpleDateFormat dsf = new SimpleDateFormat("YYYYMMDDHHMMSS");

    public Date unmarshal(String v) throws Exception {
        if (v == null || v.length() != 14) {
            return null;
        }
        return dsf.parse(v);
    }

    public String marshal(Date v) throws Exception {
        if (v == null) {
            return "";
        }
        return dsf.format(v);
    }
}