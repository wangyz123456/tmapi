package com.example.tmapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("demo")
    public String test(){
        System.out.println("1111");
        return "helloWorld";
    }



}
