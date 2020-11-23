package com.example.tmapi.dao.master;

import com.example.tmapi.entity.RPTBase0005;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RPTBase0005Dao {
    List<RPTBase0005> queryByCond(@Param(value = "rPTBase0005")RPTBase0005 rPTBase0005);
}
