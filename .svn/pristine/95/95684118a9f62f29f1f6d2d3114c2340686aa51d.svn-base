package com.example.tmapi.controller;

import com.example.tmapi.utils.JsonData;

import org.python.core.PyFunction;
import org.python.core.PyObject;

import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Properties;


@RestController
@RequestMapping(value="/pub/dingHR")
public class PythonController {

    @RequestMapping(value = "signin_daily_redo", method = RequestMethod.POST)
    public JsonData python() {
//        Properties props = new Properties();
//        props.put("python.home", "path to the Lib folder");
//        props.put("python.console.encoding", "UTF-8");
//
//        props.put("python.security.respectJavaAccessibility", "false");
//
//        props.put("python.import.site", "false");
//
//        Properties preprops = System.getProperties();
//
//        PythonInterpreter.initialize(preprops, props, new String[0]);
//
//        PythonInterpreter interpreter = new PythonInterpreter();
//
//        interpreter.exec("import sys");
//        interpreter.exec("sys.path.append('D:/jython3.7.3/Lib')");//jython自己的
//        interpreter.exec("sys.path.append('D:/jython3.7.3/Lib/site-packages')");//jython自己的
//

//        interpreter.execfile("E:\\PythonProject\\DingHR\\signin_daily_redo.py");
        String result = "";
//        String[] arguments = new String[] {"python", "E:\\PythonProject\\DingHR\\signin_daily_redo.py"};
//        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
//        PyFunction pyFunction = interpreter.get("err_push", PyFunction.class);
//        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
//        PyObject pyobj = pyFunction.__call__();
//        result = pyobj.toString();


        try {
            Process pr = Runtime.getRuntime().exec("cmd /c E:\\PythonProject\\DingHR\\signin_daily_redo.bat");// 执行py文件
//            Process pr = Runtime.getRuntime().exec("python E:\\PythonProject\\DingHR\\signin_daily_redo.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                if(line!=null)
                result = line;
            }
            System.out.println("result="+result);
            in.close();
            pr.waitFor();
        } catch (InterruptedException | IOException e) {
           e.getMessage();
        }


        if("200".equals(result))

             return JsonData.buildSuccess("",200);
        else
            return JsonData.buildError(-1,"运行出错！");
    }
}
