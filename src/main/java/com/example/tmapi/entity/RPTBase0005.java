package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class RPTBase0005 implements Serializable {

    private Date runDate;
    private String storeId;
    private String storeName;
    private String kindCode;
    //总金额
    private BigDecimal totalAmount;
    //销售占比
    private BigDecimal lxzb;
    //总利润
    private BigDecimal sumProfit;
    //毛利占比
    private BigDecimal mlzb;

    private String startTime;
    private String endTime;



}
