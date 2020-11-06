package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class PurchaseItemBak implements Serializable {

    private Date BuyDate;
    private String StoreId;
    private String StoreName;
    private String MachID;
    private int ReceiptId;
    private String BuyTime;
    private BigDecimal MemberId;
    private String CardID;
    private BigDecimal TotalAmount;
    private String SellerID;
    private int GoodsID;
    private BigDecimal qty;
    //总销售额
    private BigDecimal TodaySumAmount;
    //总成本
    private BigDecimal SaleCost;
    //总利润
    private BigDecimal sumProfit;
    private Date startDate;
}
