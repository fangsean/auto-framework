package com.auto.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 测试枚举
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AgeEnum {
    ONE(1, "一岁"),
    TWO(2, "二岁");

    private Integer value;
    private String desc;

    AgeEnum(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }
}
