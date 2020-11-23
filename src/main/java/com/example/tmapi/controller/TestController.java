package com.example.tmapi.controller;

import com.example.tmapi.dao.slave.ContrastSaleDataDao;
import com.example.tmapi.dao.slave.ContrastStoreSaleDataDao;
import com.example.tmapi.dao.slave1.CsDao;
import com.example.tmapi.entity.SigninRules;
import com.example.tmapi.service.ItemsService;
import com.example.tmapi.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private CsDao csDao;

    @RequestMapping("demo")
    public String test(){
        System.out.println("1111");
        return "helloWorld";
    }
    @RequestMapping(value = "cs",method = RequestMethod.POST)
    public JsonData queryByCond(){
        return JsonData.buildSuccess(csDao.queryByCond());
    }

}
