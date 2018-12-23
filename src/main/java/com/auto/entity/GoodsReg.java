package com.auto.entity;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.auto.common.adapter.DoubleAdapter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;


/**
 * 商品备案清单信息，可循环
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement
@XStreamAlias("GoodsContent")
@XmlType
//@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.FIELD)
@TableName("Goods_Reg")
public class GoodsReg implements Serializable {

    private static final long serialVersionUID = 3520532393269870302L;

    /**
     * 商品序号	001-999	Num(3)
     * id
     */
    @NotNull
    @TableField("Seq")
    private Long Seq;
    /**
     * 企业商品自编号	企业的商品货号，不可重复
     * sn
     */
    @NotNull
    @TableField("Ent_Goods_No")
    private String EntGoodsNo;
    /**
     * 跨境公共平台商品备案申请号
     */
    @TableField("EPort_Goods_No")
    private String EPortGoodsNo;
    /**
     * 检验检疫商品备案编号
     */
    @TableField("CIQ_Goods_No")
    private String CIQGoodsNo;
    /**
     * 海关正式备案编号
     */
    @TableField("Cus_Goods_No")
    private String CusGoodsNo;
    /**
     * 账册号
     */
    @TableField("Ems_No")
    private String EmsNo;
    /**
     * 项号	保税账册里的项号
     */
    @TableField("Item_No")
    private String ItemNo;
    /**
     * 上架品名	在电商平台上的商品名称
     */
    @NotNull
    @TableField("Shelf_G_Name")
    private String ShelfGName;
    /**
     * 行邮税号	商品综合分类表(NCAD)
     */
    @NotNull
    @TableField("Ncad_Code")
    private String NcadCode;
    /**
     * HS编码
     */
    @NotNull
    @TableField("HS_Code")
    private String HSCode;
    /**
     * 商品条形码	允许包含字母和数字
     */
    @TableField("Bar_Code")
    private String BarCode;
    /**
     * 商品名称	商品中文名称
     */
    @NotNull
    @TableField("Goods_Name")
    private String GoodsName;
    /**
     * 型号规格
     */
    @NotNull
    @TableField("Goods_Style")
    private String GoodsStyle;
    /**
     * 品牌	商品品牌
     */
    @NotNull
    @TableField("Brand")
    private String Brand;
    /**
     * 申报计量单位	计量单位代码表(UNIT)
     */
    @NotNull
    @TableField("G_Unit")
    private String GUnit;

    /**
     * 第一法定计量单位	参照公共代码表
     */
    @NotNull
    @TableField("Std_Unit")
    private String StdUnit;
    /**
     * 第二法定计量单位	参照公共代码表
     */
    @NotNull
    @TableField("Sec_Unit")
    private String SecUnit;
    /**
     * 单价	境物品：指无税的销售价格, RMB价格
     */
    @NotNull
    @TableField("Reg_Price")
    @XmlJavaTypeAdapter(value=DoubleAdapter.class, type=Date.class)
    @XmlElement(name = "RegPrice")
    private Double regPrice	;
    /**
     * 是否赠品	0-是，1-否，默认否
     */
    @NotNull
    @TableField("Gift_Flag")
    private String GiftFlag;
    /**
     * 原产国	参照国别代码表
     */
    @NotNull
    @TableField("Origin_Country")
    private String OriginCountry;
    /**
     * 商品品质及说明	用文字描述
     */
    @NotNull
    @TableField("Quality")
    private String Quality;
    /**
     * 品质证明说明	商品品质证明性文字说明
     */
    @NotNull
    @TableField("Quality_Certify")
    private String QualityCertify;
    /**
     * 生产厂家或供应商	此项填生成厂家或供应商名称
     */
    @NotNull
    @TableField("Manufactory")
    private String Manufactory;
    /**
     * 净重	单位KG；	Num(19,5)
     */
    @NotNull
    @TableField("Net_Wt")
    @XmlJavaTypeAdapter(value=DoubleAdapter.class, type=Date.class)
    @XmlElement(name = "NetWt")
    private Double netWt;
    /**
     * 毛重	单位KG；	Num(19,5)
     */
    @NotNull
    @TableField("Gross_Wt")
    @XmlJavaTypeAdapter(value=DoubleAdapter.class, type=Date.class)
    @XmlElement(name = "GrossWt")
    private Double grossWt;
    /**
     * 备注
     */
    @TableField("Notes")
    private String Notes;



}