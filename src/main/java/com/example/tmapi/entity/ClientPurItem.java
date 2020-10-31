package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class ClientPurItem implements Serializable {
    private String StoreID;
    private String SheetID;
    private BigDecimal SalePrice;
    private BigDecimal StockPrice;
    private BigDecimal SendQty;

    //总金额
    private BigDecimal TotalAmount;
    //总成本
    private BigDecimal SaleCost;
    //总利润
    private BigDecimal sumProfit;
    private Date startDate;
}
