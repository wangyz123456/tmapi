package com.example.tmapi.dao.slave;

import com.example.tmapi.entity.StockOverDue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockOverDueDao {

    List<StockOverDue> queryByCond(@Param(value = "stockOverDue") StockOverDue stockOverDue);

}
