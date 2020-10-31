package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class ClientPur implements Serializable {

    private String StoreID;
    private String SheetID;
    private int SheetType;
    private int Status;
    private int PurchaseType;
    private int ClientID;


}
