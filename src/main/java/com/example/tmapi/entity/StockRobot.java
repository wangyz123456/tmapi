package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockRobot implements Serializable {

    private String storeID;
    private String addURL;
    private String minusURL;


}
