package com.example.tmapi.dao.slave;

import com.example.tmapi.entity.StockRobot;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRobotDao {

    List<StockRobot> queryByCond(@Param(value = "stockRobot")StockRobot stockRobot);
}
