package com.auto.entity;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 */

import com.auto.api.DateAdapter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * 电子清单详情信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XStreamAlias("ElistDetailInfo")
@XmlRootElement(name = "ElistDetailInfo")
@XmlType
//@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.FIELD)
public class ElistDetailInfo implements Serializable {

    private String EntEListNo;//	清单编号	企业内部的清单编号	Var(20)	必填
    private String EPortEListNo;//	跨境平台清单编号	新增申报时候为空，修改删除时候需要填写	Var(18)	可空
    private String PreNo;//	预录入号	新增申报时候为空，修改时候需要填写	Var(18)	条件选填
    private String CusEListNo;//	海关清单编号	新增申报时候为空，修改时候需要填写	Var(18)	条件选填
    private String CIQEListNo;//	检验检疫清单编号	新增申报时候为空，修改删除时候需要填写	Var(36)	可空
    private String AssureCode;//	担保企业编号	电商企业的单一窗口注册编号	Var(30)	必填
    private String IEPort;//	进出口岸代码	商品实际出我国关境口岸海关的关区代码 JGS/T 18《海关关区代码》	Var(4)	必填
    private String SvPCode;//	监管场所	针对同一申报地海关下有多个跨境电子商务的监管场所,需要填写区分	Var(10)	必填
    @XmlJavaTypeAdapter(value = DateAdapter.class, type = Date.class)
    @XmlElement(name = "IEDate")
    private Date iEDate;//	进出口日期	进出口日期	Datetime	必填
    private String EmsNo;//	备案号/账册编号	保税模式填写具体账号，用于保税进口业务在特殊区域辅助系统记账（二线出区核减）	Var(30)	可空
    private String AreaCode;//区内企业代码	保税业务区内仓储企业代码，应各地保税进出口和杭州建议，增加区内企业核扣账册	Var(18)	可空
    private String AreaName;//区内企业名称	保税业务区内仓储企业名称	Var(100)	可空
    private String LicenseNo;// 	许可证号	商务主管部门及其授权发证机关签发的进出口货物许可证的编号	Var(19)	可空
    private String TransMode;//	成交方式	参照海关成交方式代码表	Var(2)	必填
    private String UseTo;//	生成厂家/用途	参照海关用途代码表	Var(3)	可空
    private String BillNo;//	提运单号/转关单号	直购进口必填。货物提单或运单的编号，直购必填。	Var(37)	可空
    private String PackEntName;//	组货单位名称	市场采购组货单位名称	　	可空
    private String WrapType;//	外包装种类代码 	海关包装代码	Var(4)	必填
    private Integer PackQuantity;//	包裹件数	　	int(10)	必填
    private String TransType;//	(运输工具类型)	1 飞机2 卡车3 火车4 船9 其他	Var(1)	必填
    private String TransCode;//	运输方式代码	进境货物的运输方式是按货物运抵我国关境第一个口岸时的运输方式填报；出境货物的运输方式是按货物运离我国关境最后一个口岸时的运输方式填报	Var(1)	必填
    private String TransNo;//	运输工具编号	直购进口必填。货物进出境的运输工具的名称或运输工具编号。填报内容应与运输部门向海关申报的载货清单所列相应内容一致；同报关单填制规范。	Var(100)	必填
    private String VoyageNo;//航班航次号	直购进口必填。货物进出境的运输工具的航次编号	Var(32)	可空
    private String DestinationCountry;//	起运国/运抵国	进口填起运地国家代码，出口填运抵地国家代码，一份报文里要求都填一样的不一样退单。	Var(3)	必填
    private String DestinationPort;//	起运港/抵运港	进口填起运地港口代码，出口填运抵地港口代码，一份报文里要求都填一样的不一样退单。	Var(4)	必填
    @XmlJavaTypeAdapter(value = DateAdapter.class, type = Date.class)
    @XmlElement(name = "EdestDate")
    private Date edestDate;//	拟到达时间或出发时间	直购进口为拟抵达监管场所时间。保税进口为拟发货时间。出口为抵达监管场所时间。	Datetime	必填
    private String RouteCountry;//经停地区或国家	参考国家代码 	Var(3)	可空
    private String ConsigneeName;//	消费者名称	海关监控对象的姓名	Var(60)	必填
    private String ConsigneeIdNum;//	消费者证件号码	海关监控对象的身份证号	Var(30)	必填
    private String ConsigneeCardType;//	消费者证件类型代码	1-身份证；2-其它	Var(1)	必填
    private String ConsigneeCountry;//消费者所在国家（地区）代码	进口填中国（142）出口填目的国海关代码	Var(3)	必填
    private String ConsigneeTel;//消费者联系电话号码	海关监管对象的电话	Var(30)	必填
    private String Notes;//	备注	　	Var(1000)	可空

}