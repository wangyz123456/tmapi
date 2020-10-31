package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class PurchaseItem implements Serializable {

   private Date BuyDate;
   private String StoreID;
   private String StoreName;
   private String MachId;
   private int ReceiptID;
   private short SN;
   private int GoodsID;
   private String Name;
   private String BarCode;
   private BigDecimal Qty;
   private BigDecimal Price;
   private BigDecimal Amount;
   private String SellerID;
   //总销售额
   private BigDecimal TodaySumAmount;
   //总成本
   private BigDecimal SaleCost;
   //总利润
   private BigDecimal sumProfit;
   private Date startDate;


}
