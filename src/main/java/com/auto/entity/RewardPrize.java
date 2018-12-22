package com.auto.entity;

import lombok.Data;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */
@Data
public class RewardPrize {

    private Long id;
    private String supplierNo;
    private Integer status;////权重状态
    private Integer weights;////每个奖品的权重都设置成1，也就是每个奖品被抽到的概率相同（可根据情况自行设置权重）

    public RewardPrize(Long id, String supplierNo, Integer weights, Integer status) {
        this.id = id;
        this.supplierNo = supplierNo;
        this.status = status;
        this.weights = weights;
    }

    public RewardPrize(String supplierNo, Integer status, Integer weights) {
        this.supplierNo = supplierNo;
        this.status = status;
        this.weights = weights;
    }

    public RewardPrize(String supplierNo, Integer weights) {
        this.supplierNo = supplierNo;
        this.weights = weights;
    }

    public Integer getStatus() {
        return status;
    }

    public RewardPrize setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getWeights() {
        return weights;
    }

    public RewardPrize setWeights(Integer weights) {
        this.weights = weights;
        return this;
    }

    public Long getId() {
        return id;
    }

    public RewardPrize setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public RewardPrize setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
        return this;
    }
}

