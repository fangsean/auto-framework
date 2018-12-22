package com.auto.util;

import java.io.Serializable;

public class EasyResult<T> implements Serializable {
    private static final long serialVersionUID = -8701323301214812423L;

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 数据
     */
    private T rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public EasyResult() {

    }

    public EasyResult(Integer total, T rows) {
        this.total = total;
        this.rows = rows;
    }

    public EasyResult(T rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "EasyResult [total=" + total + ", rows=" + rows + "]";
    }
}
