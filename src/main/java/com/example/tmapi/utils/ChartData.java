package com.example.tmapi.utils;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class ChartData implements Serializable {
    //总销售达标率
    private  Map<String ,Object> zxsdblMap;
    //总毛利达标率
    private  Map<String ,Object> zmldblMap;
    //今日毛利达标率
    private  Map<String ,Object> tmldblMap;
    //零售销售完成度
    private  Map<String ,Object> lsxswcdMap;
    //总销售额完成度
    private  Map<String ,Object> zxsewcdMap;
    //批发销售完成度
    private  Map<String ,Object> pfxswcdMap;
    //零售毛利完成度
    private  Map<String ,Object> lsmlwcdMap;
    // 总毛利完成度
    private  Map<String ,Object> zmlwcdMap;
    //批发毛利完成度
    private  Map<String ,Object> pfmlwcdMap;
    //门店零售同比
    private  Map<String ,Object> mdlstbMap;
    //门店零售今日
    private  Map<String ,Object> mdlstdMap;
    //门店零售去年今日
    private  Map<String ,Object> mdlstdyMap;
    //门店零售环比（今日/上月今日）
    private Map<String ,Object> mdlshbMap;
    //门店零售 （上月今日）
    private Map<String ,Object> mdlstdmMap;
    //本月销售
    private Map<String ,Object> byxsMap;
    //本月销售平均
    private Map<String ,Object> byxsAvgMap;
    //同期本月销售
    private Map<String ,Object> tqbyxsMap;
    //同期本月销售平均
    private Map<String ,Object> tqbyxsAvgMap;
    //加盟店销售业绩
    private Map<String ,Object> jmdjrxseMap;
    //加盟店毛利
    private Map<String ,Object> jmdjrmlMap;
     //以下是会员经营日报表
    //门店累计会员数
    private Map<String ,Object> mdljhysMap;
    //今日新增会员数
    private Map<String ,Object> jrxzhysMap;
    //今日消费会员数
    private Map<String ,Object> jrxfhysMap;
    //今日会员客单价
    private Map<String ,Object> jrhykdjMap;
    //今日会员消费占比
    private Map<String ,Object> jrhyxfzbMap;
    //今日总销售
    private BigDecimal todayZXS;
    //今日总毛利
    private BigDecimal todayZML;
    //今日零售销售
    private BigDecimal todayLSXS;
    //今日零售毛利
    private BigDecimal todayLSML;
    //今日批发销售
    private BigDecimal todayPFXS;
    //今日批发毛利
    private BigDecimal todayPFML;








}
