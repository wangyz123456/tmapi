package com.example.tmapi.controller;

import com.example.tmapi.entity.Purchase;
import com.example.tmapi.service.MemberService;
import com.example.tmapi.service.PurchaseService;
import com.example.tmapi.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/pri/xttCRM")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 查询是否是会员
     * @param tel tel
     * @return JsonData
     */
    @RequestMapping(value="checkMember", method = RequestMethod.POST)
     public JsonData checkMember(String tel){
       if(tel==null||tel.isEmpty()){
           return JsonData.buildError("参数不能为空！");
       }
        boolean result = memberService.queryByCond(tel);
       return JsonData.buildSuccess(result);
   }

    /**
     * 查询会员消费明细
     * @param tel tel
     * @return JsonData
     */
    @RequestMapping(value="queryPurchaseItemByTel", method = RequestMethod.POST)
    public JsonData queryByTel(String tel,String buyDate){
        if(tel==null||tel.isEmpty()||buyDate==null||buyDate.isEmpty()){
            return JsonData.buildError("参数不能为空！");
        }
        List<Purchase> list = purchaseService.queryByTel(tel,buyDate);
        return JsonData.buildSuccess(list);
    }
}
