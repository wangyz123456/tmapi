package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class PurchaseBak implements Serializable {

    private Date BuyDate;
    private String StoreId;
    private String StoreName;
    private String MachID;
    private int ReceiptId;
    private String BuyTime;
    private BigDecimal MemberId;
    private String CardID;
    private BigDecimal TotalAmount;
    private BigDecimal SumAmount;
    private String SellerID;
    private Date startDate;
    private String Tel;
    private List<PurchaseItem> listItem;
    //总销售额
    private BigDecimal TodaySumAmount;
    //总成本
    private BigDecimal SaleCost;
    //总利润
    private BigDecimal sumProfit;

    //会员消费金额
    private BigDecimal AMT;
    //会员消费人数
    private BigDecimal HYS;


}
