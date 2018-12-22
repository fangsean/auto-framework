package com.auto.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 运营商联系电话枚举
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PhoneEnum {
    CMCC("10086", "中国移动"),
    CUCC("10010", "中国联通"),
    CT("10000", "中国电信");

    private String value;
    private String desc;

    PhoneEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return this.value;
    }

    public String getDesc(){
        return this.desc;
    }
}
