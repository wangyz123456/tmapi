package com.example.tmapi.dao.slave1;

import com.example.tmapi.entity.SigninRules;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsDao {
    List<SigninRules> queryByCond();
}
