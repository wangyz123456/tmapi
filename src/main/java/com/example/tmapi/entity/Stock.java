package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Stock implements Serializable {
    private String StoreID;
    private String StoreName;
    private int GoodsID;
    private String Name;
    private BigDecimal Quantity;
}
