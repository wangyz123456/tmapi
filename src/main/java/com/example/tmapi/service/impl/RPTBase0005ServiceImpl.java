package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.RPTBase0005Dao;
import com.example.tmapi.entity.RPTBase0005;
import com.example.tmapi.service.RPTBase0005Service;
import com.example.tmapi.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class RPTBase0005ServiceImpl implements RPTBase0005Service {
    @Autowired
    private RPTBase0005Dao rPTBase0005Dao;

    /**
     * 查询销售、毛利同比
     * @param rPTBase0005
     * @return
     */
    @Override
    public List<RPTBase0005> queryByCond(RPTBase0005 rPTBase0005) {
        //查询各店今年当月月初至当天（不含当天）销售额、毛利
        String startTime = "";
        if(DataUtil.getDay()==1){//如果是1号，统计上个月1号到上个月月末
            startTime = DataUtil.format(DataUtil.lastMonth(),"yyyy-MM-dd HH:mm:ss.SSS");
        }else{
           startTime = DataUtil.format(DataUtil.initDateByMonth(),"yyyy-MM-dd HH:mm:ss.SSS");
        }
        String endTime = DataUtil.format(DataUtil.initYestDayDateByMonth(),"yyyy-MM-dd HH:mm:ss.SSS");
        rPTBase0005.setStartTime(startTime);
        rPTBase0005.setEndTime(endTime);
        List<RPTBase0005> list = rPTBase0005Dao.queryByCond(rPTBase0005);



        //查询各店去年当月月初至当天（不含当天）销售额、毛利
        String startTime1 = "";
        if(DataUtil.getDay()==1){//如果是1号，统计上个月1号到上个月月末
            startTime1 = DataUtil.format(DataUtil.addYear(DataUtil.lastMonth(),-1),"yyyy-MM-dd HH:mm:ss.SSS");
        }else{
            startTime1 = DataUtil.format(DataUtil.addYear(DataUtil.initDateByMonth(),-1),"yyyy-MM-dd HH:mm:ss.SSS");
        }
//        String startTime1 = DataUtil.format(DataUtil.addYear(DataUtil.initDateByMonth(),-1),"yyyy-MM-dd HH:mm:ss.SSS");
        String endTime1 = DataUtil.format(DataUtil.addYear(DataUtil.initYestDayDateByMonth(),-1),"yyyy-MM-dd HH:mm:ss.SSS");
        rPTBase0005.setStartTime(startTime1);
        rPTBase0005.setEndTime(endTime1);
        List<RPTBase0005> list1 = rPTBase0005Dao.queryByCond(rPTBase0005);


        //查询方山服装上月月初至当天（不含当天）销售额、毛利
        String startTime2 = "";
        if(DataUtil.getDay()==1){//如果是1号，统计上个月1号到上个月月末
            startTime2 = DataUtil.format(DataUtil.addMonth(DataUtil.lastMonth(),-1),"yyyy-MM-dd HH:mm:ss.SSS");
        }else{
            startTime2 = DataUtil.format(DataUtil.lastMonth(),"yyyy-MM-dd HH:mm:ss.SSS");
        }

//        String startTime2 = DataUtil.format(DataUtil.lastMonth(),"yyyy-MM-dd HH:mm:ss.SSS");
        String endTime2 = DataUtil.format(DataUtil.addMonth(DataUtil.initYestDayDateByMonth(),-1),"yyyy-MM-dd HH:mm:ss.SSS");
        rPTBase0005.setStartTime(startTime2);
        rPTBase0005.setEndTime(endTime2);
        rPTBase0005.setStoreId("121");
        List<RPTBase0005> list2 = rPTBase0005Dao.queryByCond(rPTBase0005);

        for (RPTBase0005 rPTBase0005Dto:list) {
            for (RPTBase0005 rPTBase0005Dto1:list1) {
                if(rPTBase0005Dto.getStoreId().equals(rPTBase0005Dto1.getStoreId())){
                    if(rPTBase0005Dto.getTotalAmount()==null||rPTBase0005Dto1.getTotalAmount()==null||rPTBase0005Dto1.getTotalAmount().compareTo(new BigDecimal(0))==0){
                        rPTBase0005Dto.setLxzb(new BigDecimal(0));
                    }else{
                        rPTBase0005Dto.setLxzb(rPTBase0005Dto.getTotalAmount().multiply(new BigDecimal(100)).divide(rPTBase0005Dto1.getTotalAmount(),1,BigDecimal.ROUND_HALF_UP));
                    }
                    if(rPTBase0005Dto.getSumProfit()==null||rPTBase0005Dto1.getSumProfit()==null||rPTBase0005Dto1.getSumProfit().compareTo(new BigDecimal(0))==0){
                        rPTBase0005Dto.setMlzb(new BigDecimal(0));
                    }else{
                        rPTBase0005Dto.setMlzb(rPTBase0005Dto.getSumProfit().multiply(new BigDecimal(100)).divide(rPTBase0005Dto1.getSumProfit(),1,BigDecimal.ROUND_HALF_UP));
                    }

                }
            }
        }
        for (RPTBase0005 rPTBase0005Dto:list) {
            if(rPTBase0005Dto.getStoreId().equals("121")&&rPTBase0005Dto.getLxzb()==null){
                RPTBase0005 dto = list2.get(0);
                rPTBase0005Dto.setLxzb(rPTBase0005Dto.getTotalAmount().multiply(new BigDecimal(100)).divide(dto.getTotalAmount(),1,BigDecimal.ROUND_HALF_UP));
                rPTBase0005Dto.setMlzb(rPTBase0005Dto.getSumProfit().multiply(new BigDecimal(100)).divide(dto.getSumProfit(),1,BigDecimal.ROUND_HALF_UP));
            }

        }

        return list;
    }
}
