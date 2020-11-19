package com.example.tmapi.controller;

import com.example.tmapi.entity.Goods;
import com.example.tmapi.entity.TargetsSeller;
import com.example.tmapi.service.TargetsSellerService;
import com.example.tmapi.utils.JsonData;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/pri/TMStockSale/target")
public class TargetsSellerController {
    @Autowired
    private TargetsSellerService targetsSellerService;

    /**
     * 查询店员目标销售业绩，销售额
     * @param StoreID StoreID
     * @param SellerID SellerID
     * @return JsonData
     */
    @RequestMapping(value="getResult", method = RequestMethod.POST)
    public JsonData checkMember(String StoreID,String SellerID){
        if(StoreID==null||StoreID.isEmpty()||SellerID==null||SellerID.isEmpty()){
            return JsonData.buildError("参数不能为空！");
        }

        Map<String, Object> map = targetsSellerService.queryTargetsSellerByCond(StoreID,SellerID);
        return JsonData.buildSuccess(map);
    }

    /**
     * 查询当天销售业绩完成度
     * @param storeId storeId
     * @param sellerId sellerId
     * @return
     */
    @RequestMapping(value = "queryTargetsSellerDay",method = RequestMethod.POST)
    public JsonData queryByCondDay(String storeId,String sellerId){
        if(storeId==null||storeId.isEmpty()||sellerId==null||sellerId.isEmpty()){
            return JsonData.buildError("参数不能为空！");
        }
        TargetsSeller targetsSeller = new TargetsSeller();
        targetsSeller.setStoreId(storeId);
        targetsSeller.setSellerId(sellerId);
        List<TargetsSeller> list = targetsSellerService.queryByCond(targetsSeller);
        return JsonData.buildSuccess(list);
    }

    /**
     * 查询当月销售业绩完成度
     * @param storeId storeId
     * @param sellerId sellerId
     * @return
     */
    @RequestMapping(value = "queryTargetsSellerMonth",method = RequestMethod.POST)
    public JsonData queryByCondMonth(String storeId,String sellerId){
        if(storeId==null||storeId.isEmpty()||sellerId==null||sellerId.isEmpty()){
            return JsonData.buildError("参数不能为空！");
        }
        TargetsSeller targetsSeller = new TargetsSeller();
        targetsSeller.setStoreId(storeId);
        targetsSeller.setSellerId(sellerId);
        List<TargetsSeller> list = targetsSellerService.queryByCondMonth(targetsSeller);
        return JsonData.buildSuccess(list);
    }

}
