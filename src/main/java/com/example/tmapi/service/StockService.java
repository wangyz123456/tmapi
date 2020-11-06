package com.example.tmapi.service;

import com.example.tmapi.entity.Stock;

import java.util.List;

public interface StockService {
    List<Stock> queryByCond(Stock stock);
}
