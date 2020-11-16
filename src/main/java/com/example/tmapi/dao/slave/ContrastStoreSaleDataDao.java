package com.example.tmapi.dao.slave;

import com.example.tmapi.entity.ContrastStoreSaleData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContrastStoreSaleDataDao {

    List<ContrastStoreSaleData> queryByCond(@Param(value = "contrastStoreSaleData")ContrastStoreSaleData contrastStoreSaleData);

    void insertForeach(@Param(value = "list") List<ContrastStoreSaleData> list);

    void save(@Param(value = "contrastStoreSaleData") ContrastStoreSaleData contrastStoreSaleData);
}
