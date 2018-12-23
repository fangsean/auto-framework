package com.auto.common.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 *
 * @XmlJavaTypeAdapter，使用定制的适配器（即扩展抽象类XmlAdapter并覆盖marshal()和unmarshal()方法），以序列化Java类为XML。 使用方法：
 * 在指定的属性或字段上添加注解
 * @XmlJavaTypeAdapter(value=DoubleAdapter.class, type=Double.class)
 */
public class DoubleAdapter extends XmlAdapter<Double, Double> {
    public DoubleAdapter() {
    }

    public Double unmarshal(Double v) {
        return v;
    }

    public Double marshal(Double v) {
        if (null == v) {
            v = (new BigDecimal("0.00")).doubleValue();
        }

        v = (new BigDecimal(v)).setScale(5, 4).doubleValue();
        return v;
    }
}
