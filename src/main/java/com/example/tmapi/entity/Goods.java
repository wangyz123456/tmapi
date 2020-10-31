package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Goods implements Serializable {

    private int GoodsID;
    private String Name;
    private String BarCode;
}
