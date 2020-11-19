package com.example.tmapi.controller;

import com.example.tmapi.entity.Goods;
import com.example.tmapi.entity.Stock;
import com.example.tmapi.service.GoodsService;
import com.example.tmapi.utils.JsonData;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/pri/Goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 测试
     * @param goods goods
     * @return
     */
    @RequestMapping(value = "queryPage",method = RequestMethod.POST)
    public PageInfo<Goods> queryByCond(@RequestBody Goods goods){
        PageInfo<Goods>  page = goodsService.queryByCond(goods);
        return page;
    }

    /**
     * 测试
     * @param stock stock
     */
    @RequestMapping(value = "queryCs",method = RequestMethod.POST,consumes = "application/json;charset=utf-8")
    public void queryByCond(@RequestBody Stock stock){
        System.out.println(stock);
    }

    /**
     * 根据商品名称模糊查询
     * @param goodsName goodsName
     * @return
     */
    @RequestMapping(value = "queryCond",method = RequestMethod.POST)
    public JsonData queryByConds( String goodsName){
        if(goodsName==null||goodsName.isEmpty()){
            return JsonData.buildError("参数不能为空！");
        }
        Goods goods = new Goods();
        if(goodsName.trim().contains(" ")){
            goods.setLikeName("%"+goodsName.replace(" ","%")+"%");
        }else{
            goods.setName(goodsName);
        }

        List<Goods> list=goodsService.queryByConds(goods);
        if(list.size()>=100){
            return JsonData.buildError("请输入精准信息进行重新！");
        }
        if(list.size()==0){
            return JsonData.buildError(-2,"没有查到相关数据！");
        }
        return JsonData.buildSuccess(list);
    }
}
