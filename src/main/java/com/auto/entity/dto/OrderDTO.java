package com.auto.entity.dto;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class OrderDTO {

    private Long id;
    private String orderNo;
    private Long buyerId;
    private Long sellerId;
    private Long price;
    private Timestamp mytime;
    private String memo;

}
