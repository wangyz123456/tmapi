package com.example.tmapi.dao.master;

import com.example.tmapi.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsDao {
    List<Goods> queryByCond(@Param(value = "goods")Goods goods);
}
