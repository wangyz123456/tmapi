package com.example.tmapi.dao.master;

import com.example.tmapi.entity.Stock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StockDao {
    List<Stock> queryByCond(@Param(value = "stock")Stock stock);
}
