package com.example.tmapi.dao.master;


import com.example.tmapi.entity.TargetsSeller;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TargetsSellerDao {
    /**
     * 查询店员销售目标金额（默认当月）
     * @param  targetsSeller targetsSeller
     * @return targetsSeller
     */
    TargetsSeller queryTargetsSellerByCond(@Param(value="targetsSeller") TargetsSeller targetsSeller);

    List<TargetsSeller> queryByStoreId(@Param(value="targetsSeller") TargetsSeller targetsSeller);
}
