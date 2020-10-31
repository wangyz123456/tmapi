package com.example.tmapi.dao.master;


import com.example.tmapi.entity.Purchase;
import com.example.tmapi.entity.PurchaseItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseItemDao {
    /**
     * 根据订单查询消费明细记录
     * @param purchaseItem  purchaseItem
     * @return list
     */
    List<PurchaseItem> queryByCond(@Param(value = "purchaseItem")PurchaseItem purchaseItem);

    /**
     * 根据id查询销售总金额（当月1号至当天前一天）
     * @param purchase purchase
     * @return purchase
     */
    Purchase querySumAmountById(@Param("purchase")Purchase purchase);

    /**
     * 查询方山餐饮销售总金额（当月1号至当天前一天）
     * @param purchaseItem purchaseItem
     * @return list
     */
    List<PurchaseItem>  queryFsAmount(@Param("purchaseItem")PurchaseItem purchaseItem);
}
