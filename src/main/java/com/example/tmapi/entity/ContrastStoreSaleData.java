package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ContrastStoreSaleData implements Serializable {
    private Date date;
    private String storeId;
    private float lxamt;
}
