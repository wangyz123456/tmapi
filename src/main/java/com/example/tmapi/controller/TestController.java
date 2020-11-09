package com.example.tmapi.controller;

import com.example.tmapi.dao.slave.ContrastSaleDataDao;
import com.example.tmapi.dao.slave.ContrastStoreSaleDataDao;
import com.example.tmapi.entity.ContrastSaleData;
import com.example.tmapi.entity.ContrastStoreSaleData;
import com.example.tmapi.entity.Items;
import com.example.tmapi.service.ItemsService;
import com.example.tmapi.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("test")
public class TestController {
    @Autowired
    private ContrastSaleDataDao contrastSaleDataDao;
    @Autowired
    private ContrastStoreSaleDataDao contrastStoreSaleDataDao;
    @Autowired
    private ItemsService itemsService;
    @RequestMapping("demo")
    public String test(){
        System.out.println("1111");
        return "helloWorld";
    }



    @RequestMapping(value = "insert",method = RequestMethod.POST)
    public void save(){
        ContrastSaleData dto = new ContrastSaleData();
        dto.setDate(DataUtil.getFormat());
        dto.setLxamt(new BigDecimal(123.04));
        contrastSaleDataDao.save(dto);

    }
    @RequestMapping(value = "insertList",method = RequestMethod.POST)
    public void insertList(){
        ContrastStoreSaleData dto = new ContrastStoreSaleData();
        dto.setDate(DataUtil.getFormat());
        dto.setStoreId("111");
        dto.setLxamt(new BigDecimal(456.3));
        ContrastStoreSaleData dto1 = new ContrastStoreSaleData();
        dto1.setDate(DataUtil.getFormat());
        dto1.setStoreId("112");
        dto1.setLxamt(new BigDecimal(1231.3));
        List<ContrastStoreSaleData> list= new ArrayList<>();
        list.add(dto);
        list.add(dto1);
        contrastStoreSaleDataDao.insertForeach(list);

    }
}
