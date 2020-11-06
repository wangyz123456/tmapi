package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Items implements Serializable {
    private String StoreID;
    private int  ReceiptID;
    private int GoodsID;
    private BigDecimal RecQty;
    private Date date;
    private String StoreName;
    private String Name;
    private String KindCode;
}
