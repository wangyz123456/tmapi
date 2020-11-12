package com.example.tmapi.entity;
import lombok.Data;
import java.io.Serializable;
@Data
public class Goods implements Serializable {
    private int pageNum;
    private int pageSize;
    private int goodsId;
    private String name;
    private String barCode;

}
