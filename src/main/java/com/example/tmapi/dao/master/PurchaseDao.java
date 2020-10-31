package com.example.tmapi.dao.master;

import com.example.tmapi.entity.Purchase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseDao {
    /**
     * 根据手机号查询消费订单
     * @param purchase purchase
     * @return list
     */
    List<Purchase> queryByTel(@Param("purchase")Purchase purchase);

    /**
     * 根据id查询销售总金额（当月1号至当天前一天）
     * @param purchase purchase
     * @return purchase
     */
//    Purchase querySumAmountById(@Param("purchase")Purchase purchase);
}
