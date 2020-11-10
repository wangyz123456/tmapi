package com.example.tmapi.entity;

import com.example.tmapi.utils.DataUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class PurchaseItemBak implements Serializable {

    private Date BuyDate;
    private String StoreId;
    private String StoreName;
    private String MachID;
    private int ReceiptId;
    private String BuyTime;
    private BigDecimal MemberId;
    private String CardID;
    private BigDecimal TotalAmount;
    private BigDecimal Amount;
    private BigDecimal Price;
    private String SellerID;
    private int GoodsID;
    private String Name;
    private BigDecimal qty;
    //总销售额
    private BigDecimal TodaySumAmount;
    //总成本
    private BigDecimal SaleCost;
    //总利润
    private BigDecimal sumProfit;
    private Date startDate;
    private String startTime;
    private Date lastInDate;



    @Override
    public String toString() {
        return  StoreName +"：" + SellerID +","+DataUtil.format(BuyDate,"yyyy-MM-dd")+"日,"  + BuyTime + "点,卖出："+ qty +"个"  + Name + ", 价格：" + Price +
                "元， 总金额：" + Amount +"元， 进货日期：" + DataUtil.format(lastInDate,"yyyy-MM-dd") ;
    }
}
