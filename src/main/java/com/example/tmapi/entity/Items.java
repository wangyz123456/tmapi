package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Items implements Serializable {
    private String storeId;
    private int  receiptId;
    private int goodsId;
    private BigDecimal recQty;
    private Date date;
    private String storeName;
    private String name;
    private String kindCode;
    private BigDecimal cBAmount;
    private String barCode;
    private BigDecimal price;
}
