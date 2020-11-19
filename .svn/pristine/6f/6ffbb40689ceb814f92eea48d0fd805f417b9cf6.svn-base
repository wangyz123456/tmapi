package com.example.tmapi.entity;

import com.example.tmapi.utils.DataUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class PurchaseItemBak implements Serializable {

    private Date buyDate;
    private String storeId;
    private String storeName;
    private String machId;
    private int receiptId;
    private String buyTime;
    private BigDecimal memberId;
    private String cardId;
    private BigDecimal totalAmount;
    private BigDecimal amount;
    private BigDecimal oldPrice;
    private BigDecimal price;
    private String sellerId;
    private int goodsId;
    private String name;
    private BigDecimal qty;
    //总销售额
    private BigDecimal todaySumAmount;
    //总成本
    private BigDecimal saleCost;
    //总利润
    private BigDecimal sumProfit;
    private Date startDate;
    private String startTime;
    private String endTime;
    private Date lastInDate;




    @Override
    public String toString() {
        return  storeName +"：" + sellerId +","+DataUtil.format(buyDate,"yyyy-MM-dd")+"日,"  + buyTime + "点,卖出："+ qty +"个"  + name + ", 原价：" + oldPrice + ", 售价：" + price +
                "元， 总金额：" + amount +"元， 最后进货日期：" + DataUtil.format(lastInDate,"yyyy-MM-dd") ;
    }
}
