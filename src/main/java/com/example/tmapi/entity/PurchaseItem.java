package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class PurchaseItem implements Serializable {

   private Date buyDate;
   private String storeId;
   private String storeName;
   private String machId;
   private int receiptId;
   private short sN;
   private int goodsId;
   private String name;
   private String barCode;
   private BigDecimal qty;
   private BigDecimal price;
   private BigDecimal amount;
   private String sellerId;
   //总销售额
   private BigDecimal todaySumAmount;
   //总成本
   private BigDecimal saleCost;
   //总利润
   private BigDecimal sumProfit;
   private Date startDate;


}
