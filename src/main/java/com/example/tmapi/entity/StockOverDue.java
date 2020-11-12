package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StockOverDue implements Serializable {

    private String storeId;
    private int goodsId;
    private Date maxinDate;

}
