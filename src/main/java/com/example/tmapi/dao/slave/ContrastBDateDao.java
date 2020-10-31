package com.example.tmapi.dao.slave;

import com.example.tmapi.entity.ContrastBDate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContrastBDateDao {

    ContrastBDate queryByCond(@Param(value = "contrastBDate")ContrastBDate contrastBDate);

}
