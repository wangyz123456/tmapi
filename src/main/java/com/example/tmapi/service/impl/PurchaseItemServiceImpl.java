package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.PurchaseItemDao;
import com.example.tmapi.entity.PurchaseItem;
import com.example.tmapi.service.PurchaseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PurchaseItemServiceImpl implements PurchaseItemService {
    @Autowired
    private PurchaseItemDao purchaseItemDao;

    /**
     * 查询会员消费明细
     * @param purchaseItem purchaseItem
     * @return list
     */
    @Override
    public List<PurchaseItem> queryByCond(PurchaseItem purchaseItem) {

        return purchaseItemDao.queryByCond(purchaseItem);
    }
}
