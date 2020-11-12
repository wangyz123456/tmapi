package com.example.tmapi.controller;

import com.example.tmapi.entity.Goods;
import com.example.tmapi.entity.Stock;
import com.example.tmapi.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/Goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "queryPage",method = RequestMethod.POST)
    public PageInfo<Goods> queryByCond(@RequestBody Goods goods){
        PageInfo<Goods>  page = goodsService.queryBuCond(goods);
        return page;
    }

    @RequestMapping(value = "queryCs",method = RequestMethod.POST,consumes = "application/json;charset=utf-8")
    public void queryByCond(@RequestBody Stock stock){
        System.out.println(stock);
    }
}
