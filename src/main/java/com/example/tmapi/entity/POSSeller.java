package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class POSSeller implements Serializable {

  private String StoreID;
  private String SellerID;
  private String SellerName;

}
