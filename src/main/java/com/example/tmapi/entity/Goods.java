package com.example.tmapi.entity;
import lombok.Data;
import java.io.Serializable;
@Data
public class Goods implements Serializable {

    private int goodsId;
    private String name;
    private String likeName;
    private String barCode;

}
