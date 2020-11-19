package com.example.tmapi.dao.slave;

import com.example.tmapi.entity.GoalSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalSetDao {
    List<GoalSet> queryByCond();
    List<GoalSet> queryByDate(@Param(value = "date")String date);
}
