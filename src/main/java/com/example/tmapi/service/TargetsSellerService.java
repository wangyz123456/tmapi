package com.example.tmapi.service;


import com.example.tmapi.entity.GoalSet;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TargetsSellerService {
    Map<String,Object> queryTargetsSellerByCond(String StoreID, String SellerID);

}
