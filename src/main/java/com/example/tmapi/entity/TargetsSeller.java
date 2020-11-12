package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class TargetsSeller implements Serializable {

    private String sellerId;
    private String storeId;
    private String date;
    private BigDecimal goals;
}
