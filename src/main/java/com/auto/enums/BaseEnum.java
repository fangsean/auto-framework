package com.auto.enums;

public enum BaseEnum {


    SUCCESS(2000, "成功"),
    REQUEST_PARAM_IS_NULL(2001, "缺少请求参数"),
    NO_DATA(2002, "未查询到数据"),
    ALREADY_EXIST(2003, "此数据已存在"),
    IMG_FORMAT_ERROR(2004, "图片格式错误"),
    PWD_IS_DISAFFINITY(2005, "两次密码不相同"),
    FILE_FORMAT_ERROR(2006, "文件格式错误"),
    EMAIL_FORMAT_ERROR(12001, "邮箱格式错误"),
    ORIGINAL_PWD_ERROR(12002, "原始密码与登录密码不一致"),
    USER_NOT_EXIST(12003, "用户不存在,请重新登陆"),
    PWD_ERROR(12004, "登录密码错误"),
    WAIT_CHECK(12005, "等待审核"),
    CHECK_NOT_PASS(12006, "审核未通过"),
    USER_NOT_SHOP(12007, "当前会员未注册店铺"),
    TWO_PWD_IS_INCONSISTENT(12008, "两次密码不一致"),
    TEL_FORMAT_ERROR(12009, "请输入正确的手机号码"),
    PWD_FORMAT_ERROR(12010, "请输入8-20位由字母，数字组成的密码"),
    INVALID(12013, "无效"),
    ERROR(12014, "错误"),
    SYSTEM_ERROR(4000, "系统繁忙，请稍后再试！");


    private Integer code;
    private String reason;

    private BaseEnum(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
