package com.example.tmapi.controller;

import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/pub/dingHR")
public class PythonController {

    @RequestMapping(value = "signin_daily_redo", method = RequestMethod.POST)
    public   void python() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("E:\\PythonProject\\DingHR\\signin_daily_redo.py");
    }
}
