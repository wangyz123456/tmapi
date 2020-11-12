package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.GoodsDao;
import com.example.tmapi.entity.Goods;
import com.example.tmapi.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public PageInfo<Goods> queryBuCond(Goods goods) {
        PageHelper.startPage(goods.getPageNum(), goods.getPageSize()<=0?10:goods.getPageSize());
        List<Goods> list = goodsDao.queryByCond(goods);
        PageInfo<Goods> page = new PageInfo<Goods>(list);
        return page;
    }
}
