package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ContrastSaleData implements Serializable {

    private Date date;
    private float lxamt;
    private Date StartDate;
    private Date endDate;
}
