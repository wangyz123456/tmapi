package com.example.tmapi.dao.slave;

import com.example.tmapi.entity.ContrastSaleData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContrastSaleDataDao {

    List<ContrastSaleData> queryByCond(@Param(value = "contrastSaleData")ContrastSaleData contrastSaleData);
}
