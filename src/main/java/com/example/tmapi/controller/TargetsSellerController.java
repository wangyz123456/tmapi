package com.example.tmapi.controller;

import com.example.tmapi.service.TargetsSellerService;
import com.example.tmapi.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
