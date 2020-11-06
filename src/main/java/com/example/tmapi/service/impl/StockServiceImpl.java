package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.ItemsDao;
import com.example.tmapi.dao.master.StockDao;
import com.example.tmapi.entity.Items;
import com.example.tmapi.entity.Stock;
import com.example.tmapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockDao stockDao;
    @Autowired
    private ItemsDao itemsDao;
    @Override
    public List<Stock> queryByCond(Stock stock) {
        Items items = new Items();
        List<Stock>  list = new ArrayList<>();
        List<Stock>  stockList = stockDao.queryByCond(stock);
        List<Items>  itemsList = itemsDao.queryByCond(items);
        for (Items itemsDto:itemsList) {
            for (Stock stockDto:stockList) {
                if(itemsDto.getGoodsID()==stockDto.getGoodsID()&&itemsDto.getStoreID().equals(stockDto.getStoreID())){
                    BigDecimal fz = stockDto.getQuantity();
                    BigDecimal fm = itemsDto.getRecQty();
                    stockDto.setJhqty(fm);
                    BigDecimal result = fz.multiply(new BigDecimal(100)).divide(fm, 2, BigDecimal.ROUND_HALF_UP);
                    stockDto.setKczb(result);
                    if(result.compareTo(new BigDecimal(50)) == 1){
                        list.add(stockDto);
                    }

                    break;
                }
            }
        }


        return list;
    }
}
