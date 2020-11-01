package com.example.tmapi.service.impl;

import com.example.tmapi.dao.master.*;
import com.example.tmapi.dao.slave.*;
import com.example.tmapi.entity.*;
import com.example.tmapi.service.GoalSetService;
import com.example.tmapi.utils.ChartData;
import com.example.tmapi.utils.DataUtil;
import com.example.tmapi.utils.MapSortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GoalSetServiceImpl implements GoalSetService {
    @Autowired
    private GoalSetDao goalSetDao;
    @Autowired
    private GoalDayRateDao goalDayRateDao;
    @Autowired
    private RPTBase0006BDao rPTBase0006BDao;
    @Autowired
    private PurchaseItemDao purchaseItemDao;
    @Autowired
    private PurchaseItemBakDao purchaseItemBakDao;
    @Autowired
    private ClientPurItemDao clientPurItemDao;
    @Autowired
    private PurchaseBakDao purchaseBakDao;
    @Autowired
    private ContrastBDateDao contrastBDateDao;
    @Autowired
    private ContrastSaleDataDao contrastSaleDataDao;
    @Autowired
    private ContrastStoreSaleDataDao contrastStoreSaleDataDao;
    @Autowired
    private StoresDao storesDao;
    @Autowired
    private MemberDao memberDao;




    /**
     * 测试
     * @return
     */
    @Override
    public List<GoalSet> queryByCond() {

        return goalSetDao.queryByCond();

    }

    /**
     * 查询 目标任务
     * @param date date
     * @return list
     */
    @Override
    public ChartData queryByDate(String date) {

        ChartData chartData = new ChartData();
        //获取月初日期 "yyyy-MM-dd"
        date = DataUtil.format(DataUtil.initDateByMonth(),DataUtil.FORMAT_LONOGRAM);
        //查询月任务
        List<GoalSet> list = goalSetDao.queryByDate(date);
        //获取当天日期 "yyyy-MM-dd"
        String s = DataUtil.format(DataUtil.initToDayDateByMonth(),DataUtil.FORMAT_LONOGRAM);
        //查询当天利率
        List<GoalDayRate> goalDayRateList = goalDayRateDao.queryByDate(s);
        //获取月初日期  "yyyy-MM-dd HH:mm:ss.SSS"
        Date sdate = DataUtil.initDateByMonth();
        //查询月初到当天前一天的总销售额，总成本，总毛利（零售+批发）
        List<RPTBase0006B> listDto = rPTBase0006BDao.queryByCond(sdate);
        //获取当天日期 "yyyy-MM-dd HH:mm:ss.SSS"
        Date startDate = DataUtil.initToDayDateByMonth();
        //查询当天实际完成总销售额，总成本，总毛利（零售）
        PurchaseItemBak purchaseItemBak = new PurchaseItemBak();
        purchaseItemBak.setStartDate(startDate);
        List<PurchaseItemBak> purchaseItemBakList = purchaseItemBakDao.queryByStoreId(purchaseItemBak);
        //查询方山餐饮月初到当天前一天的总销售额，总成本，总毛利
        PurchaseItem fscy = new PurchaseItem();
        fscy.setStoreID("118");
        fscy.setSellerID("餐饮");
        fscy.setStartDate(sdate);
        List<PurchaseItem> fscypurchaseItemlist = purchaseItemDao.queryFsAmount(fscy);
        PurchaseItem fsPurchaseItem = new PurchaseItem();
        if(fscypurchaseItemlist!=null && fscypurchaseItemlist.size()>0)
            fsPurchaseItem =   fscypurchaseItemlist.get(0);
        else
            fsPurchaseItem=null;

        //查询方山餐饮当天的总销售额，总成本，总毛利
        PurchaseItemBak fs = new PurchaseItemBak();
        fs.setStoreId("118");
        fs.setSellerID("餐饮");
        fs.setStartDate(startDate);
        List<PurchaseItemBak> fsPurchaseItemList = purchaseItemBakDao.queryByStoreId(fs);
        PurchaseItemBak fsPurchaseItemBak = new PurchaseItemBak();
        if(fsPurchaseItemList!=null&&fsPurchaseItemList.size()>0)
            fsPurchaseItemBak = fsPurchaseItemList.get(0);
       else
            fsPurchaseItemBak=null;
        //查询当天实际完成总销售额，总成本，总毛利（批发）
        ClientPurItem   clientPurItem = new ClientPurItem();
        clientPurItem.setStartDate(startDate);
        List<ClientPurItem> clientPurItemList =  clientPurItemDao.queryByCond(clientPurItem);
        //数据处理 放入当天的目标值
        for (GoalSet goalSet:list) {
            if(goalSet.getStoreId().equals("065")||goalSet.getStoreId().equals("004")){
                for (GoalDayRate goalDayRate:goalDayRateList) {
                    if(goalDayRate.getStore().equals("天天便利店")){
                        goalSet.setTamtLx(goalSet.getAmtLx()*goalDayRate.getLRate());
                        goalSet.setTamtLm(goalSet.getAmtLm()*goalDayRate.getLRate());
                        goalSet.setTamtPx(goalSet.getAmtPx()*goalDayRate.getPRate());
                        goalSet.setTamtPm(goalSet.getAmtPm()*goalDayRate.getPRate());
                    }
                }
            }else{
                for (GoalDayRate goalDayRate:goalDayRateList) {
                    if(!(goalDayRate.getStore().equals("天天便利店"))){
                        goalSet.setTamtLx(goalSet.getAmtLx()*goalDayRate.getLRate());
                        goalSet.setTamtLm(goalSet.getAmtLm()*goalDayRate.getLRate());
                        goalSet.setTamtPx(goalSet.getAmtPx()*goalDayRate.getPRate());
                        goalSet.setTamtPm(goalSet.getAmtPm()*goalDayRate.getPRate());
                    }
                }
            }
        }
        //获取去年今日各店销售额
        List<ContrastStoreSaleData> contrastStoreSaleDataList= getData("year");
        //获取上个月今日各店销售额
        List<ContrastStoreSaleData> contrastStoreSaleDataMonList= getData("month");
        PurchaseBak purchaseBak = new PurchaseBak();
        Date ystDate = DataUtil.initYestDayDateByMonth();
        purchaseBak.setBuyDate(ystDate);
        purchaseBak.setBuyTime("21:00");
        purchaseBak.setStoreId("065");
        PurchaseBak purchaseBak065 =  purchaseBakDao.querySumAmountByStoreId(purchaseBak);


        purchaseBak.setStoreId("004");
        PurchaseBak purchaseBak004 =  purchaseBakDao.querySumAmountByStoreId(purchaseBak);

        //总销售达标率(当天)
         Map<String ,Object> zxsdblMap = new HashMap<>();
        //总毛利达标率(累计)
         Map<String ,Object> zmldblMap = new HashMap<>();
        //今日毛利达标率（今日）
        Map<String ,Object> tmldblMap = new HashMap<>();
        //门店零售 （今日）
        Map<String ,Object> mdlstdMap = new HashMap<>();
        //门店零售 （去年今日）
        Map<String ,Object> mdlstdyMap = new HashMap<>();
        //门店零售同比（今日/去年今日）
        Map<String ,Object> mdlstbMap = new HashMap<>();
        //门店零售环比（今日/上月今日）
        Map<String ,Object> mdlshbMap = new HashMap<>();
        //门店零售 （上月今日）
        Map<String ,Object> mdlstdmMap = new HashMap<>();
         //总部  累计总毛利（目标）批发+零售
        BigDecimal sumfm = new BigDecimal(0);
        //总部   累计总毛利（实际）批发+零售
        BigDecimal sumfz = new BigDecimal(0);

        //今日完零售成度（仪表盘）
        //总零售销售（目标）
        BigDecimal lsxsfm = new BigDecimal(0);
        //总零售销售 （实际）
        BigDecimal lsxsfz = new BigDecimal(0);
        //总批发销售 （目标）
        BigDecimal pfxsfm = new BigDecimal(0);
        //总批发销售 （实际）
        BigDecimal pfxsfz = new BigDecimal(0);
        //总销售  （目标）
        BigDecimal zxsfm = new BigDecimal(0);
        //总销售 （实际）
        BigDecimal zxsfz = new BigDecimal(0);


        //今日毛利完成度（仪表盘）
        //总零售毛利 （目标）
        BigDecimal lsmlfm = new BigDecimal(0);
        //总零售毛利 （实际）
        BigDecimal lsmlfz = new BigDecimal(0);
        //总批发毛利 （目标）
        BigDecimal pfmlfm = new BigDecimal(0);
        //总批发毛利 （实际）
        BigDecimal pfmlfz = new BigDecimal(0);
        //总毛利 （目标）
        BigDecimal zmlfm = new BigDecimal(0);
        //总毛利实际 （实际）
        BigDecimal zmlfz = new BigDecimal(0);


        for (GoalSet goalSet:list) {
            //今日销售额（实际）
            BigDecimal jrfz =  new BigDecimal(0);
            //去年今日各店销售额
            BigDecimal jrfm =  new BigDecimal(0);
            //上个月今日各店销售额
            BigDecimal syjrfm =  new BigDecimal(0);
             //今天总毛利（批发+零售）实际
            BigDecimal tsummlfz = new BigDecimal(0);
            //今日毛利（批发+零售）目标
            BigDecimal tsummlfm = new BigDecimal(0);

            //零售+批发毛利（月）目标
            BigDecimal fm = new BigDecimal(goalSet.getAmtLm()+goalSet.getAmtPm());
            //零售+批发毛利（月）实际
            BigDecimal fz = new BigDecimal(0);
            //今日销售额（批发+零售）目标
            BigDecimal zxstfm = new BigDecimal(goalSet.getTamtLx()+goalSet.getTamtPx());
            //今日总销售额（批发+零售）实际
            BigDecimal zxstfz = new BigDecimal(0);



            lsxsfm = lsxsfm.add(new BigDecimal(goalSet.getTamtLx()));
            lsmlfm = lsmlfm.add(new BigDecimal(goalSet.getTamtLm()));
            pfxsfm = pfxsfm.add(new BigDecimal(goalSet.getTamtPx()));
            pfmlfm = pfmlfm.add(new BigDecimal(goalSet.getTamtPm()));


            tsummlfm = tsummlfm.add(new BigDecimal(goalSet.getTamtLm())).add(new BigDecimal(goalSet.getTamtPm()));


             if(goalSet.getStoreId().equals("FSCY")) {
                 if(fsPurchaseItem!=null)
                     fz = fz.add(fsPurchaseItem.getSumProfit()) ;
                 if(fsPurchaseItemBak!=null){
                     fz = fz.add(fsPurchaseItemBak.getSumProfit());
                     tsummlfz = tsummlfz.add(fsPurchaseItemBak.getSumProfit());
                     jrfz = jrfz.add(fsPurchaseItemBak.getTodaySumAmount());
                     zxstfz = zxstfz.add(fsPurchaseItemBak.getTodaySumAmount());
                     lsxsfz = lsxsfz.add(fsPurchaseItemBak.getTodaySumAmount());
                     lsmlfz = lsmlfz.add(fsPurchaseItemBak.getSumProfit());
                 }
             }
             else{
                 for(RPTBase0006B rPTBase0006B:listDto){
                     if(goalSet.getStoreId().equals(rPTBase0006B.getStoreID())){
                           if(goalSet.getStoreId().equals("118")){
                               if(fsPurchaseItem!=null)
                                  fz = rPTBase0006B.getSumProfit().subtract(fsPurchaseItem.getSumProfit());
                               else
                                   fz = rPTBase0006B.getSumProfit();
                               break;
                           }else{

                               fz = rPTBase0006B.getSumProfit();
                               break;
                           }

                     }

                 }
                 for (PurchaseItemBak purchaseItemBakDto:purchaseItemBakList) {
                     if(goalSet.getStoreId().equals(purchaseItemBakDto.getStoreId())){
                         if(goalSet.getStoreId().equals("118")){
                             if(fsPurchaseItemBak!=null){
                                 fz = fz.add(purchaseItemBakDto.getSumProfit().subtract(fsPurchaseItemBak.getSumProfit()));
                                 tsummlfz = tsummlfz.add(purchaseItemBakDto.getSumProfit().subtract(fsPurchaseItemBak.getSumProfit()));
                                 jrfz = jrfz.add(purchaseItemBakDto.getTodaySumAmount().subtract(fsPurchaseItemBak.getTodaySumAmount()));
                                 zxstfz = zxstfz.add(purchaseItemBakDto.getTodaySumAmount().subtract(fsPurchaseItemBak.getTodaySumAmount()));
                                 lsxsfz = lsxsfz.add(purchaseItemBakDto.getTodaySumAmount().subtract(fsPurchaseItemBak.getTodaySumAmount()));
                                 lsmlfz = lsmlfz.add(purchaseItemBakDto.getSumProfit().subtract(fsPurchaseItemBak.getSumProfit()));
                             } else{
                                 fz = fz.add(purchaseItemBakDto.getSumProfit());
                                 jrfz = jrfz.add(purchaseItemBakDto.getTodaySumAmount());
                                 zxstfz = zxstfz.add(purchaseItemBakDto.getTodaySumAmount());
                             }
                             break;
                         }else{
                             fz = fz.add(purchaseItemBakDto.getSumProfit());

                             if(goalSet.getStoreId().equals("004")){
                                 tsummlfz = tsummlfz.add(purchaseBak004.getSumProfit());
                                 jrfz = jrfz.add(purchaseBak004.getTodaySumAmount());
                                 zxstfz = zxstfz.add(purchaseBak004.getTodaySumAmount());
                                 lsxsfz = lsxsfz.add(purchaseBak004.getTodaySumAmount());
                                 lsmlfz = lsmlfz.add(purchaseBak004.getSumProfit());
                             }else if(goalSet.getStoreId().equals("065")){
                                 tsummlfz = tsummlfz.add(purchaseBak065.getSumProfit());
                                 jrfz = jrfz.add(purchaseBak065.getTodaySumAmount());
                                 zxstfz = zxstfz.add(purchaseBak065.getTodaySumAmount());
                                 lsxsfz = lsxsfz.add(purchaseBak065.getTodaySumAmount());
                                 lsmlfz = lsmlfz.add(purchaseBak065.getSumProfit());

                             }else {
                                 tsummlfz = tsummlfz.add(purchaseItemBakDto.getSumProfit());
                                 jrfz = jrfz.add(purchaseItemBakDto.getTodaySumAmount());
                                 zxstfz = zxstfz.add(purchaseItemBakDto.getTodaySumAmount());
                                 lsxsfz = lsxsfz.add(purchaseItemBakDto.getTodaySumAmount());
                                 lsmlfz = lsmlfz.add(purchaseItemBakDto.getSumProfit());

                             }

                             break;
                         }

                     }

                 }

                 for (ClientPurItem clientPurItemDto:clientPurItemList) {
                     if(goalSet.getStoreId().equals(clientPurItemDto.getStoreID())){
                         fz.add(clientPurItemDto.getSumProfit());
                         tsummlfz = tsummlfz.add(clientPurItemDto.getSumProfit());
                         zxstfz = zxstfz.add(clientPurItemDto.getTotalAmount());
                         pfxsfz =  pfxsfz.add(clientPurItemDto.getTotalAmount());
                         pfmlfz = pfmlfz.add(clientPurItemDto.getSumProfit());
                         break;
                     }

                 }
             }

             for(ContrastStoreSaleData jrContrastStoreSaleData:contrastStoreSaleDataList){
                 if(jrContrastStoreSaleData.getStoreId().equals(goalSet.getStoreId())){
                     jrfm = jrfm.add(new BigDecimal(jrContrastStoreSaleData.getLxamt()));
                     break;
                 }
             }

            for(ContrastStoreSaleData jrContrastStoreSaleDatam:contrastStoreSaleDataMonList){
                if(jrContrastStoreSaleDatam.getStoreId().equals(goalSet.getStoreId())){
                    syjrfm = syjrfm.add(new BigDecimal(jrContrastStoreSaleDatam.getLxamt()));
                    break;
                }
            }


             sumfm = sumfm.add(fm);
             sumfz = sumfz.add(fz);
            mdlstdyMap.put(goalSet.getPrimarySector(),jrfm);
            mdlstdMap.put(goalSet.getPrimarySector(),jrfz);
            mdlstdmMap.put(goalSet.getPrimarySector(),syjrfm);




            if(syjrfm.compareTo(BigDecimal.ZERO)==0) {
                mdlshbMap.put(goalSet.getPrimarySector(),new BigDecimal(0));
            } else {
                mdlshbMap.put(goalSet.getPrimarySector(), jrfz.multiply(new BigDecimal(100)).divide(syjrfm, 2, BigDecimal.ROUND_HALF_UP));
            }

            if(jrfm.compareTo(BigDecimal.ZERO)==0) {
                mdlstbMap.put(goalSet.getPrimarySector(),new BigDecimal(0));
            } else {
                mdlstbMap.put(goalSet.getPrimarySector(), jrfz.multiply(new BigDecimal(100)).divide(jrfm, 2, BigDecimal.ROUND_HALF_UP));
            }

            zxsdblMap.put(goalSet.getPrimarySector(),zxstfz.multiply(new BigDecimal(100)).divide(zxstfm, 2, BigDecimal.ROUND_HALF_UP));
            zmldblMap.put(goalSet.getPrimarySector(),fz.multiply(new BigDecimal(100)).divide(fm, 2, BigDecimal.ROUND_HALF_UP));
            tmldblMap.put(goalSet.getPrimarySector(),tsummlfz.multiply(new BigDecimal(100)).divide(tsummlfm, 2, BigDecimal.ROUND_HALF_UP));


        }


        //今日总销售达标率
        chartData.setZxsdblMap(MapSortUtil.sortByValueDesc(zxsdblMap));
        //今日毛利达标率
        chartData.setTmldblMap(MapSortUtil.sortByValueDesc(tmldblMap));
        //门店零售同比
        chartData.setMdlstbMap(MapSortUtil.sortByValueAsc(mdlstbMap));
        chartData.setMdlstdMap(mdlstdMap);
        chartData.setMdlstdyMap(mdlstdyMap);
        //门店零售环比
        chartData.setMdlstdmMap(mdlstdmMap);
        chartData.setMdlshbMap(MapSortUtil.sortByValueAsc(mdlshbMap));

        zmldblMap.put("总部",sumfz.multiply(new BigDecimal(100)).divide(sumfm, 2, BigDecimal.ROUND_HALF_UP));
        chartData.setZmldblMap(MapSortUtil.sortByValueDesc(zmldblMap));


        //总销售
        zxsfm = lsxsfm.add(pfxsfm);
        zxsfz = lsxsfz.add(pfxsfz);
        chartData.setTodayZXS(zxsfz);
        chartData.setTodayLSXS(lsxsfz);
        chartData.setTodayPFXS(pfxsfz);
        //总毛利
        zmlfm = lsmlfm.add(pfmlfm);
        zmlfz = lsmlfz.add(pfmlfz);
        chartData.setTodayZML(zmlfz);
        chartData.setTodayLSML(lsmlfz);
        chartData.setTodayPFML(pfmlfz);
        //零售销售完成度
        Map<String ,Object> lsxswcdMap = new HashMap<>();
        lsxswcdMap.put("零售销售完成度",lsxsfz.multiply(new BigDecimal(100)).divide(lsxsfm, 2, BigDecimal.ROUND_HALF_UP));
        chartData.setLsxswcdMap(lsxswcdMap);
        //总销售额完成度
        Map<String ,Object> zxsewcdMap = new HashMap<>();
        zxsewcdMap.put("总销售额完成度",zxsfz.multiply(new BigDecimal(100)).divide(zxsfm, 2, BigDecimal.ROUND_HALF_UP));
        chartData.setZxsewcdMap(zxsewcdMap);
        //批发销售完成度
        Map<String ,Object> pfxswcdMap = new HashMap<>();
        pfxswcdMap.put("批发销售完成度",pfxsfz.multiply(new BigDecimal(100)).divide(pfxsfm, 2, BigDecimal.ROUND_HALF_UP));
        chartData.setPfxswcdMap(pfxswcdMap);
        //零售毛利完成度
        Map<String ,Object> lsmlwcdMap = new HashMap<>();
        lsmlwcdMap.put("零售毛利完成度",lsmlfz.multiply(new BigDecimal(100)).divide(lsmlfm, 2, BigDecimal.ROUND_HALF_UP));
        chartData.setLsmlwcdMap(lsmlwcdMap);
        // 总毛利完成度
        Map<String ,Object> zmlwcdMap = new HashMap<>();
        zmlwcdMap.put("总毛利完成度",zmlfz.multiply(new BigDecimal(100)).divide(zmlfm, 2, BigDecimal.ROUND_HALF_UP));
        chartData.setZmlwcdMap(zmlwcdMap);
        //批发毛利完成度
        Map<String ,Object> pfmlwcdMap = new HashMap<>();
        pfmlwcdMap.put("批发毛利完成度",pfmlfz.multiply(new BigDecimal(100)).divide(pfmlfm, 2, BigDecimal.ROUND_HALF_UP));
        chartData.setPfmlwcdMap(pfmlwcdMap);
        //同期当月零售每天
        Map<String ,Object> tqbyxsMap = getSaleDataMap("year");
        //当月零售每天
        Map<String ,Object> byxsMap = getSaleDataMap("month");
        BigDecimal tdSum = MapSortUtil.getSum(mdlstdMap);
        //放入今日零售额
        byxsMap.put(DataUtil.getDay()+"",tdSum);
        BigDecimal mSum = MapSortUtil.getSum(byxsMap);
        //本月平均值
        BigDecimal mAvg = mSum.divide(new BigDecimal(DataUtil.getDay()), 2, BigDecimal.ROUND_HALF_UP);
        Map<String ,Object> byxsAvgMap = delData(DataUtil.getDay(),mAvg);
        //同期本月平均值
        BigDecimal tqmSum = MapSortUtil.getSum(tqbyxsMap);
        BigDecimal tqmAvg = tqmSum.divide(new BigDecimal(DataUtil.getCurrentMonthDay()), 2, BigDecimal.ROUND_HALF_UP);
        Map<String ,Object> tqbyxsAvgMap = delData(DataUtil.getCurrentMonthDay(),tqmAvg);

        chartData.setByxsMap(byxsMap);
        chartData.setByxsAvgMap(byxsAvgMap);
        chartData.setTqbyxsMap(tqbyxsMap);
        chartData.setTqbyxsAvgMap(tqbyxsAvgMap);

        Map<String ,Object> jmdjrxseMap = delJmdXse(purchaseItemBakList);
        Map<String ,Object> jmdjrmlMap =  delJmdMl(purchaseItemBakList);
        chartData.setJmdjrxseMap(MapSortUtil.sortByValueDesc(jmdjrxseMap));
        chartData.setJmdjrmlMap(jmdjrmlMap);


        //门店累计会员数
        Map<String ,Object> mdljhysMap = delHYLJ(list);
        //今日新增会员数
        Map<String ,Object> jrxzhysMap = delNewM(list);

        Map<String ,Object> resultMap = delMem(list,mdlstdMap);
        //今日消费会员数
        Map<String ,Object> jrxfhysMap = (Map<String, Object>) resultMap.get("xf");
        //今日会员客单价
        Map<String ,Object> jrhykdjMap = (Map<String, Object>) resultMap.get("dk");
        //今日会员消费占比
        Map<String ,Object> jrhyxfzbMap = (Map<String, Object>) resultMap.get("zb");

        chartData.setMdljhysMap(MapSortUtil.sortByValueAsc(mdljhysMap));
        chartData.setJrxzhysMap(jrxzhysMap);
        chartData.setJrxfhysMap(jrxfhysMap);
        chartData.setJrhykdjMap(jrhykdjMap);
        chartData.setJrhyxfzbMap(jrhyxfzbMap);

        return chartData;
    }

    private Map<String, Object> delMem(List<GoalSet> list, Map<String, Object> mdlstdMap) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> jrxfhysMap = new HashMap<>();
        Map<String, Object> jrhykdjMap = new HashMap<>();
        Map<String, Object> jrhyjeMap = new HashMap<>();
        Map<String, Object> jrhyxfzbMap = new HashMap<>();
        PurchaseBak purchaseBak = new PurchaseBak();
        purchaseBak.setBuyDate(DataUtil.initToDayDateByMonth());
        List<PurchaseBak> listDto = purchaseBakDao.queryCustomNoAndAmt(purchaseBak);
        for (GoalSet goalSet:list) {
            for (PurchaseBak dto:listDto) {
                if(goalSet.getStoreId().equals(dto.getStoreId())){
                    jrxfhysMap.put(goalSet.getPrimarySector(),dto.getHYS());
                    jrhyjeMap.put(goalSet.getPrimarySector(),dto.getAMT());
                    BigDecimal jg = new BigDecimal(0);
                    if(!(dto.getHYS().compareTo(jg)==0)&&dto.getAMT()!=null){
                        jg = dto.getAMT().divide(dto.getHYS(), 2, BigDecimal.ROUND_HALF_UP);
                    }
                    jrhykdjMap.put(goalSet.getPrimarySector(),jg);
                    break;
                }

            }

        }
        for (Map.Entry<String, Object> entry : mdlstdMap.entrySet()) {
            BigDecimal jg = new BigDecimal(0);
            BigDecimal fm  = (BigDecimal) entry.getValue();
            BigDecimal fz = (BigDecimal) jrhyjeMap.get(entry.getKey());
            if(!(fm.compareTo(jg)==0)&&fz!=null){
               jg = fz.multiply(new BigDecimal(100)).divide(fm, 2, BigDecimal.ROUND_HALF_UP);
            }
            jrhyxfzbMap.put(entry.getKey(),jg);
        }
        jrxfhysMap.put("合计",MapSortUtil.getSum(jrxfhysMap));
        jrhykdjMap.put("合计",MapSortUtil.getSum(jrhykdjMap).divide(new BigDecimal(mdlstdMap.size()), 2, BigDecimal.ROUND_HALF_UP));
        jrhyxfzbMap.put("合计",MapSortUtil.getSum(jrhyxfzbMap).divide(new BigDecimal(mdlstdMap.size()), 2, BigDecimal.ROUND_HALF_UP));
        map.put("xf",jrxfhysMap);
        map.put("dk",jrhykdjMap);
        map.put("zb",jrhyxfzbMap);
        return map;
    }

    private Map<String, Object> delNewM(List<GoalSet> list) {
        Member mem = new Member();
        Map<String, Object> map = new HashMap<>();
        mem.setStartDate(DataUtil.initToDayDateByMonth());
        mem.setEndDate(DataUtil.initToDayDateByMonth1());
        List<Member> memList = memberDao.queryNewMember(mem);
        for (GoalSet dto:list) {
            for (Member memDto:memList) {
                if(dto.getStoreId().equals(memDto.getStoreID())){
                    map.put(dto.getPrimarySector(),memDto.getNewMember());
                    break;
                }
            }
        }
        map.put("合计",MapSortUtil.getSum(map));
        return map;

    }

    private Map<String, Object> delHYLJ(List<GoalSet> list) {
        Map<String, Object> map = new HashMap<>();
        List<Member> memList = memberDao.querySumMember();
        for (GoalSet dto:list) {
            for (Member memDto:memList) {
                if(dto.getStoreId().equals(memDto.getStoreID())){
                    map.put(dto.getPrimarySector(),memDto.getSumNo());
                    break;
                }
            }
        }
        map.put("合计",MapSortUtil.getSum(map));
        return map;
    }


    private Map<String, Object> delJmdMl(List<PurchaseItemBak> purchaseItemBakList) {
        Map<String, Object> map = new HashMap<>();
        for (PurchaseItemBak dto:purchaseItemBakList) {
            if("060".equals(dto.getStoreId())){
                map.put("86加盟会员店",dto.getSumProfit());
            }
            if("015".equals(dto.getStoreId())){
                map.put("78加盟店",dto.getSumProfit());
            }

        }

        return map;
    }

    private Map<String, Object> delJmdXse(List<PurchaseItemBak> purchaseItemBakList) {
        Map<String, Object> map = new HashMap<>();
        for (PurchaseItemBak dto:purchaseItemBakList) {
            if("060".equals(dto.getStoreId())){
                map.put("86加盟会员店",dto.getTodaySumAmount());
            }
            if("015".equals(dto.getStoreId())){
                map.put("78加盟店",dto.getTodaySumAmount());
            }

        }

        return map;
    }


    private Map<String, Object> delData(int day, BigDecimal mAvg) {
        Map<String, Object> map = new HashMap<>();
        int j = 1;
        for (int i = 0; i < day; i++) {
            map.put(j+"",mAvg);
            j++;
        }

        return map;
    }


    public List<ContrastStoreSaleData> getData(String str){
        List<ContrastStoreSaleData>list = new ArrayList<>();
        ContrastBDate contrastBDate = new ContrastBDate();
        ContrastStoreSaleData contrastStoreSaleData = new ContrastStoreSaleData();
        if(str.equals("year")) {
            contrastBDate.setYear(DataUtil.getYear());
            contrastBDate.setMonth(DataUtil.getMonth());
            ContrastBDate contrastBDatedto = contrastBDateDao.queryByCond(contrastBDate);
            if (contrastBDatedto != null) {
                Date date = DataUtil.addDay(contrastBDatedto.getBDate(), DataUtil.getDay() - 1);
                contrastStoreSaleData.setDate(date);
                list = contrastStoreSaleDataDao.queryByCond(contrastStoreSaleData);
            }

        }
        if(str.equals("month")){
            Date mdate = DataUtil.addDay(DataUtil.lastMonth(),DataUtil.getDay() - 1);
            contrastStoreSaleData.setDate(mdate);
            list = contrastStoreSaleDataDao.queryByCond(contrastStoreSaleData);
        }

        return list;
    }


    public Map<String,Object> getSaleDataMap(String str){
        Map<String,Object> map  = new HashMap<>();
        ContrastSaleData contrastSaleData = new ContrastSaleData();
        List<ContrastSaleData> list = new ArrayList<>();
        Date sMDate =  DataUtil.initDateByMonth();
        Date eMDate =  DataUtil.initToDayDateByMonth();
        int days  = DataUtil.getCurrentMonthDay();
        int day = DataUtil.getDay();
        eMDate = DataUtil.addDay(eMDate,day-1);
        Date ysDate = DataUtil.addYear(sMDate,-1);
        Date yeDate = DataUtil.addDay(ysDate,days-1);
        if(str.equals("year")){
            contrastSaleData.setStartDate(ysDate);
            contrastSaleData.setEndDate(yeDate);
        }else if(str.equals("month")){
            contrastSaleData.setStartDate(sMDate);
            contrastSaleData.setEndDate(eMDate);
        }
        list = contrastSaleDataDao.queryByCond(contrastSaleData);
        int i = 1;
        for (ContrastSaleData dto:list) {
            map.put(i+"",new BigDecimal(dto.getLxamt()));
            i++;
        }
        return map;

    }




}
