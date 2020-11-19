package com.example.tmapi.controller;


import com.example.tmapi.entity.PurchaseItemBak;
import com.example.tmapi.service.PurchaseItemBakService;
import com.example.tmapi.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/pri/PurchaseItemBak")
public class PurchaseItemBakController {

    @Autowired
    private PurchaseItemBakService purchaseItemBakService;

    /**
     * 查询各店销售商品数量
     * @param goodsId goodsId
     * @return
     */
    @RequestMapping(value = "queryByCond",method = RequestMethod.POST)
    public JsonData queryByConds(@RequestParam(value = "goodsId") int goodsId){
        PurchaseItemBak purchaseItemBak = new PurchaseItemBak();
        purchaseItemBak.setGoodsId(goodsId);
        List<PurchaseItemBak> list=purchaseItemBakService.queryGoodsByDate(purchaseItemBak);
        return JsonData.buildSuccess(list);
    }

}
