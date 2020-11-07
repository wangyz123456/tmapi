package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.ItemsDao;
import com.example.tmapi.dao.master.PurchaseItemBakDao;
import com.example.tmapi.entity.Items;
import com.example.tmapi.entity.PurchaseItemBak;
import com.example.tmapi.service.ItemsService;
import com.example.tmapi.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ItemsServiceImpl implements ItemsService {
    @Autowired
    private ItemsDao itemsDao;
    @Autowired
    private PurchaseItemBakDao purchaseItemBakDao;
    @Override
    public List<Items> queryByDate(Items items) {
        items = new Items();
        Date date =DataUtil.addDay(DataUtil.getFormat(),-15) ;
        items.setDate(DataUtil.getFormat(date));

        List<Items> resultList = new ArrayList<>();
        List<Items> itemList = itemsDao.queryByDate(items);
        PurchaseItemBak bak = new PurchaseItemBak();
        bak.setStartDate(DataUtil.getFormat(date));
        List<PurchaseItemBak> bakList = purchaseItemBakDao.queryByDate(bak);

//        long startTime=System.currentTimeMillis();
        //执行方法

        for (Items itemsDto:itemList) {
            Boolean flag = false;
            for (PurchaseItemBak purchaseItemBakDto:bakList) {
                if(itemsDto.getStoreID().equals(purchaseItemBakDto.getStoreId())&&itemsDto.getGoodsID()== purchaseItemBakDto.getGoodsID()){
                    if(purchaseItemBakDto.getQty().compareTo(new BigDecimal(0))==1)
                        flag =true;
                    break;
                }
            }
            if(flag==false){
                resultList.add(itemsDto);
            }

        }
//        long endTime=System.currentTimeMillis();
//        float excTime=(float)(endTime-startTime)/1000;
//        System.out.println("执行时间："+excTime+"s");

        return resultList;
    }
}
