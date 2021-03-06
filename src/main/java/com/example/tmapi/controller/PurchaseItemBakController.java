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
    public JsonData queryByConds(String goodsId,String barCode){

        PurchaseItemBak purchaseItemBak = new PurchaseItemBak();
        if(goodsId!=null&&goodsId.trim().length()>0)
            purchaseItemBak.setGoodsId(Integer.parseInt(goodsId));
        purchaseItemBak.setBarCode(barCode);
        List<PurchaseItemBak> list=purchaseItemBakService.queryGoodsByDate(purchaseItemBak);
        return JsonData.buildSuccess(list);
    }

}
