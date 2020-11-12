package com.example.tmapi.service;

import com.example.tmapi.entity.Goods;
import com.github.pagehelper.PageInfo;

public interface GoodsService {
    PageInfo<Goods> queryBuCond(Goods goods);
}
