package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.*;
import com.example.tmapi.entity.Purchase;
import com.example.tmapi.entity.PurchaseItemBak;
import com.example.tmapi.entity.TargetsSeller;
import com.example.tmapi.service.TargetsSellerService;
import com.example.tmapi.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TargetsSellerServiceImpl implements TargetsSellerService {
    @Autowired
    private TargetsSellerDao targetsSellerDao;

    @Autowired
    private PurchaseItemDao purchaseItemDao;

    @Autowired
    private PurchaseItemBakDao purchaseItemBakDao;



    /**
     * 查询店员销售业绩，以及销售金额
     * @param StoreID  StoreID
     * @param SellerID SellerID
     * @return map
     */
    @Override
    public Map<String,Object> queryTargetsSellerByCond(String StoreID,String SellerID) {
        Map<String,Object> map = new HashMap<>();
        Purchase purchase = new Purchase();
        PurchaseItemBak purchaseBak = new PurchaseItemBak();
        TargetsSeller targetsSeller = new TargetsSeller();
        targetsSeller.setStoreID(StoreID);
        targetsSeller.setSellerID(SellerID);
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
        //查询当月销售目标
        String Date = ft.format(dNow);
        targetsSeller.setDate(Date);
        TargetsSeller targetsSellerDto = targetsSellerDao.queryTargetsSellerByCond(targetsSeller);

        //查询当月第一天到当天前一天销售业绩
        purchase.setStoreId(StoreID);
        purchase.setSellerID(SellerID);
        purchase.setStartDate(DataUtil.initDateByMonth());
        Purchase purchaseDto = purchaseItemDao.querySumAmountById(purchase);
        //查询当天销售额
        purchaseBak.setStoreId(StoreID);
        purchaseBak.setSellerID(SellerID);
        purchaseBak.setStartDate(DataUtil.initToDayDateByMonth());
        PurchaseItemBak  purchaseBakDto = purchaseItemBakDao.querySumAmountTodayById(purchaseBak);
        //封装返回参数
        if(targetsSellerDto!=null){
            map.put("Goals",targetsSellerDto.getGoals());
        }else{
            map.put("Goals",0);
        }
        if(purchaseDto!=null){
            map.put("SumAmount",purchaseDto.getSumAmount());
        }else{
            map.put("SumAmount",0);
        }
        if(purchaseBakDto!=null){
            map.put("TodaySumAmount",purchaseBakDto.getTodaySumAmount());
        }else{
            map.put("TodaySumAmount",0);
        }

        return map;
    }


}
