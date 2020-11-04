package com.example.tmapi.dao.master;

import com.example.tmapi.entity.Items;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsDao {
    List<Items> queryByCond(@Param(value = "items")Items items);
}
