package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.PurchaseDao;
import com.example.tmapi.dao.master.PurchaseItemDao;
import com.example.tmapi.entity.Purchase;
import com.example.tmapi.entity.PurchaseItem;
import com.example.tmapi.service.PurchaseService;
import com.example.tmapi.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private PurchaseItemDao purchaseItemDao;

    /**
     * 查询会员销售记录，以及消费明细
     * @param tel tel
     * @return list
     */
    @Override
    public List<Purchase> queryByTel(String tel,String buyDate) {
        Purchase  purchase =new Purchase();
        purchase.setTel(tel);
        purchase.setBuyDate(DataUtil.parse(buyDate,DataUtil.FORMAT_FULL));
        List<Purchase> list = purchaseDao.queryByTel(purchase);
        if(list!=null && list.size()>0) {
            for (Purchase dto : list) {
                PurchaseItem purchaseItem = new PurchaseItem();
                purchaseItem.setMachId(dto.getMachId());
                purchaseItem.setStoreId(dto.getStoreId());
                purchaseItem.setReceiptId(dto.getReceiptId());
                purchaseItem.setBuyDate(dto.getBuyDate());
                 List<PurchaseItem> listItem = purchaseItemDao.queryByCond(purchaseItem);
                if (listItem != null && listItem.size() > 0) {
                    dto.setListItem(listItem);
                }

            }
        }
        return list;
    }
}
