package com.auto.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 最后数据结果类
 */
public class FinalResult<T> implements Serializable {

    private static final long serialVersionUID = 997793637403500672L;

    /**
     * 代码
     */
    private Integer code;

    /**
     * 原因
     */
    private String reason;

    /**
     * 状态
     */
    private Boolean status = false;

    /**
     * 数据
     */
    private T data;

    /**
     * 请求时间
     */
    private String requestTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public FinalResult(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public static FinalResult success() {
        FinalResult result = new FinalResult();
        result.setStatus(true);
        return result;
    }

    public static FinalResult of(boolean status, Integer code, String reason) {
        FinalResult result = new FinalResult();
        result.setStatus(status);
        result.setCode(code);
        result.setReason(reason);
        return result;
    }

    public void setCodeReason(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public FinalResult() {

    }

    public FinalResult(Integer code, String reason, Boolean status) {
        this.code = code;
        this.reason = reason;
        this.status = status;
    }

    public FinalResult(Integer code, String reason, Boolean status, T data) {
        this.code = code;
        this.reason = reason;
        this.status = status;
        this.data = data;
    }

    @Override
    public String toString() {
        return "FinalResult [code=" + code + ", reason=" + reason + ", status=" + status + ", data=" + data
                + ", requestTime=" + requestTime + "]";
    }
}
