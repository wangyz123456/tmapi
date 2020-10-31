package com.example.tmapi.controller;

import com.example.tmapi.service.GoalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/pri/GoalSet")
public class GoalSetController {
    @Autowired
    private GoalSetService goalSetService;
  @RequestMapping(value = "getAll",method = RequestMethod.POST)
    public void queryByCond(){
       int i = goalSetService.queryByCond().size();
      goalSetService.queryByDate("");

    }
}
