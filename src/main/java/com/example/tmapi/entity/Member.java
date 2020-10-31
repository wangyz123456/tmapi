package com.example.tmapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class Member implements Serializable {
    private BigDecimal MemberID;
    private String CardID;
    private String StoreID;
    private String FirstName;
    private String MobileNO;
    private String Sex;
    private Date BirthDay;
    private BigDecimal NewMember;
    private BigDecimal sumNo;
    private Date startDate;
    private Date endDate;
}
