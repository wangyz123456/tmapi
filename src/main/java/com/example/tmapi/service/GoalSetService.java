package com.example.tmapi.service;

import com.example.tmapi.entity.GoalSet;
import com.example.tmapi.utils.ChartData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoalSetService {
    List<GoalSet> queryByCond();
    ChartData queryByDate(String date);
}
