package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Items implements Serializable {
    private String StoreID;
    private int  ReceiptID;
    private int GoodsID;
    private BigDecimal RecQty;
}
