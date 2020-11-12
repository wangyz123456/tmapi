package com.example.tmapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Stock implements Serializable {
//    @JsonProperty(value = "StoreID")

    private String storeId;
    private String storeName;
    private int goodsId;
    private String name;
    private BigDecimal quantity;
    private BigDecimal jhqty;
    private BigDecimal kczb;
}
