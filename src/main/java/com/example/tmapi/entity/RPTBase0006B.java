package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class RPTBase0006B implements Serializable {

    private Date RunDate;
    private String StoreID;
    private String KindCode;
    //总金额
    private BigDecimal TotalAmount;
    //总成本
    private BigDecimal SaleCost;
    //总利润
    private BigDecimal sumProfit;


}
