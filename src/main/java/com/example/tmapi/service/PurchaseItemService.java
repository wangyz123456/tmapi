package com.example.tmapi.service;

import com.example.tmapi.entity.Purchase;
import com.example.tmapi.entity.PurchaseItem;

import java.util.List;

public interface PurchaseItemService {
     public List<PurchaseItem> queryByCond(PurchaseItem purchaseItem);
}
