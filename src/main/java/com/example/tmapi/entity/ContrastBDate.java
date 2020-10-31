package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ContrastBDate implements Serializable {
    private int year;
    private int month;
    private Date bDate;
}
