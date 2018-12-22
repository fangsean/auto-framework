package com.auto.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;

@Data
public class SkuSolrModel implements Serializable {

    private static final long serialVersionUID = 970509129877509020L;

    @Field
    private Long id;

    @Field
    private String name;	// 商品名

    @Field
    private String sn;	// 货号

    @Field
    private Integer isMainSku; // 是否是主商品

    @Field
    private Integer status;	// 上架状态

    @Field
    private Long brandId; // 品牌Id

    @Field
    private String brandName; // 品牌名

    @Field
    private Long tagId; // 标签名

    @Field
    private Long categoryId; // 一级类目ID

    @Field
    private String categoryName; // 一级类目名

    @Field
    private Long itemCatId;	//三级分类id

    @Field
    private Integer tradeType; // 贸易类型

    @Field
    private String countryName; // 国家名称

    @Field
    private Integer sold_quantity; // 销量

    @Field
    private Integer isHot;//热搜排序

    @Field
    private Date createTime;//创建时间


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getIsMainSku() {
        return isMainSku;
    }

    public void setIsMainSku(Integer isMainSku) {
        this.isMainSku = isMainSku;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getItemCatId() {
        return itemCatId;
    }

    public void setItemCatId(Long itemCatId) {
        this.itemCatId = itemCatId;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(Integer sold_quantity) {
        this.sold_quantity = sold_quantity;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
