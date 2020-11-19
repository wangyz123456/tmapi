package com.example.tmapi.utils;

import lombok.Data;

@Data
public class JsonData {
    private int code;
    private Object data;
    private String msg;

    public JsonData(){

    }
    public JsonData(int code, Object data){
        this.code = code;
        this.data = data;
    }
    public JsonData(int code, Object data, String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    public static JsonData buildSuccess(Object data){
        return new JsonData(200, data, null);
    }
    public static JsonData buildSuccess(Object data, int code){
        return new JsonData(code,data,null);
    }
    public static JsonData buildError(String msg){
        return new JsonData(-1,"",msg);
    }
    public static JsonData buildError(int code, String msg){
        return new JsonData(code,"", msg);
    }
}


