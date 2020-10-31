package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class TargetsSeller implements Serializable {

    private String SellerID;
    private String StoreID;
    private String Date;
    private BigDecimal Goals;
}
