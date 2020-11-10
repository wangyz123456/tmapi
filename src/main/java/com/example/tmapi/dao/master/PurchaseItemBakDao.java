package com.example.tmapi.dao.master;

import com.example.tmapi.entity.PurchaseItemBak;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseItemBakDao {
    /**
     * 根据id查询销售总金额（当天）
     * @param purchaseItemBak PurchaseItemBak
     * @return PurchaseItemBak
     */
    PurchaseItemBak querySumAmountTodayById(@Param("purchaseItemBak") PurchaseItemBak purchaseItemBak);

    /**
     * 根据门店ID查询各门店当天零售总额
     * @param purchaseItemBak  PurchaseItemBak
     * @return list
     */

    List<PurchaseItemBak> queryByStoreId(@Param("purchaseItemBak") PurchaseItemBak purchaseItemBak);

    List<PurchaseItemBak> queryByDate(@Param("purchaseItemBak") PurchaseItemBak purchaseItemBak);

    List<PurchaseItemBak> queryByDateTime(@Param("purchaseItemBak") PurchaseItemBak purchaseItemBak);
}
