package com.example.tmapi.service;

import com.example.tmapi.entity.Purchase;

import java.util.List;

public interface PurchaseService {
      List<Purchase> queryByTel(String tel,String byDate);
}
