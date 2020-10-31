package com.example.tmapi.dao.master;

import com.example.tmapi.entity.Purchase;
import com.example.tmapi.entity.PurchaseBak;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseBakDao {


    /**
     * 天天便利店 查询当天销售额，成本，毛利（前一天晚上9:30到当天）
     *
     * @param purchaseBak purchaseBak
     * @return purchaseBak
     */
    PurchaseBak querySumAmountByStoreId(@Param("purchaseBak")PurchaseBak purchaseBak);

    /**
     * 查询各店会员消费人数及金额
     * @return
     */
    List<PurchaseBak> queryCustomNoAndAmt(@Param("purchaseBak")PurchaseBak purchaseBak);

}
