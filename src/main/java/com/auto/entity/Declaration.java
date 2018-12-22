package auto.entity;//package com.auto.entry;
//
///**
// * @author auto.yin[auto.yin@gmail.com]
// * 2018-10-18
// * @Description: <p></p>
// */
//
//import com.nqtown.si.dto.GoodsReg;
//import com.nqtown.si.dto.GoodsRegHead;
//import com.thoughtworks.xstream.annotations.XStreamAlias;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.xml.bind.annotation.*;
//import java.io.Serializable;
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@XmlRootElement
//@XStreamAlias("Declaration")
//@XmlType
////@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
//@XmlAccessorType(XmlAccessType.FIELD)
//public class Declaration implements Serializable {
//    private static final long serialVersionUID = -6754976435582834029L;
//
//    /**
//     * 商品备案
//     */
//    @XmlElement(name = "GoodsRegHead")
//    private GoodsRegHead goodsRegHead;
//    @XmlElementWrapper(name = "GoodsRegList")
//    @XmlElement(name = "GoodsContent")
//    private List<GoodsReg> goodsRegList;
//
//    /**
//     * 电子清单
//     */
//    @XmlElement(name = "ElistHead")
//    private ElistHead elistHead;
//    @XmlElementWrapper(name = "ElistDetailInfoList")
//    @XmlElement(name = "ElistDetailInfo")
//    private List<ElistDetailInfo> ElistDetailInfoList;
//
//    /**
//     * 电子订单
//     */
//    @XmlElement(name = "OrderHead")
//    private OrderHead OrderHead;
//    @XmlElementWrapper(name = "OrderList")
//    @XmlElement(name = "OrderContent")
//    private List<OrderContent> OrderList;
//
//
//    /**
//     * 电子运单
//     */
//    @XmlElement(name = "WaybillHead")
//    private WaybillHead WaybillHead;
//    @XmlElementWrapper(name = "WaybillList")
//    @XmlElement(name = "WaybillDetail")
////    @XmlList
//    private List<WaybillDetail> WaybillDetail;
//
//
//    /**
//     * 支付单
//     */
//    @XmlElement(name = "PaymentHead")
//    private PaymentHead PaymentHead;
//    @XmlElementWrapper(name = "PaymentList")
//    @XmlElement(name = "PaymentDetail")
//    private List<PaymentDetail> PaymentList;
//
//
//    /**
//     * 进区总运单
//     */
//    @XmlElement(name = "LogisticsHead")
//    private LogisticsHead LogisticsHead;
//    @XmlElementWrapper(name = "LogisticsList")
//    @XmlElement(name = "LogisticsContent")
//    private List<LogisticsContent> LogisticsList;
//
//    /**
//     * 装载清单
//     */
//    @XmlElement(name = "LoadHead")
//    private LoadHead loadHead;
//    @XmlElementWrapper(name = "LoadList")
//    @XmlElement(name = "LoadContent")
//    private List<LoadContent> LoadList;
//
//
//
//
//}