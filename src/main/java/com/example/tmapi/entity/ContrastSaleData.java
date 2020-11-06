package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ContrastSaleData implements Serializable {

    private Date date;
    private BigDecimal lxamt;
    private Date StartDate;
    private Date endDate;
}
