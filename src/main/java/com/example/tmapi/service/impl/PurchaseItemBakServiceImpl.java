package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.PurchaseItemBakDao;
import com.example.tmapi.dao.slave.StockOverDueDao;
import com.example.tmapi.entity.PurchaseItemBak;
import com.example.tmapi.entity.StockOverDue;
import com.example.tmapi.service.PurchaseItemBakService;
import com.example.tmapi.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseItemBakServiceImpl implements PurchaseItemBakService {

    @Autowired
    private PurchaseItemBakDao purchaseItemBakDao;
    @Autowired
    private StockOverDueDao stockOverDueDao;
    @Override
    public List<PurchaseItemBak> queryByDateTime(PurchaseItemBak purchaseItemBak) {
        List<PurchaseItemBak>rusultList = new ArrayList<>();
//        PurchaseItemBak dto= new PurchaseItemBak();

        purchaseItemBak.setStartDate(DataUtil.initToDayDateByMonth());
        int time  = DataUtil.getHour()-1;
        purchaseItemBak.setStartTime(time<10?"0"+time+":00":time+":00");
        List<PurchaseItemBak>list = purchaseItemBakDao.queryByDateTime(purchaseItemBak);
        StockOverDue sdto = new StockOverDue();
        List<StockOverDue> sList = stockOverDueDao.queryByCond(sdto);
        for (PurchaseItemBak purchaseItemBakDto:list) {
            for (StockOverDue stockOverDue:sList) {
                if(purchaseItemBakDto.getStoreId().equals(stockOverDue.getStoreID())&&
                        purchaseItemBakDto.getGoodsID()==stockOverDue.getGoodsID()){
                    purchaseItemBakDto.setLastInDate(stockOverDue.getMaxinDate());
                    rusultList.add(purchaseItemBakDto);
                    break;
                }

            }

        }

        return rusultList;
    }
}
