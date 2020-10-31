package com.example.tmapi.dao.slave;

import com.example.tmapi.entity.GoalDayRate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalDayRateDao {
    List<GoalDayRate> queryByDate(@Param(value = "date") String date);
}
