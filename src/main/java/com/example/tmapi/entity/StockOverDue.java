package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StockOverDue implements Serializable {

    private String storeID;
    private int goodsID;
    private Date maxinDate;

}
