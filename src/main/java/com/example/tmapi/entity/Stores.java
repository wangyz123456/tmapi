package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Stores implements Serializable {

    private String storeId;
    private String storeName;

}
