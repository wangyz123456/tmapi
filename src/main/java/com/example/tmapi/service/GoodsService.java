package com.example.tmapi.service;

import com.example.tmapi.entity.Goods;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface GoodsService {
    PageInfo<Goods> queryByCond(Goods goods);

    List<Goods> queryByConds(Goods goods);
}
