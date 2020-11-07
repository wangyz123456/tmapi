package com.example.tmapi.springtask;

import com.example.tmapi.chart.*;
import com.example.tmapi.dao.slave.ContrastSaleDataDao;
import com.example.tmapi.dao.slave.ContrastStoreSaleDataDao;
import com.example.tmapi.email.EmailServiceImp;
import com.example.tmapi.email.RobotSendServiceImpl;
import com.example.tmapi.entity.ContrastSaleData;
import com.example.tmapi.entity.ContrastStoreSaleData;
import com.example.tmapi.entity.Items;
import com.example.tmapi.service.GoalSetService;
import com.example.tmapi.service.ItemsService;
import com.example.tmapi.utils.ChartData;
import com.example.tmapi.utils.DataUtil;
import com.example.tmapi.utils.DrawTableImgs;

import com.example.tmapi.utils.MapSortUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@EnableScheduling
public class TestSpringTask {

    public Map<String,Object> map = new HashMap();

    @Autowired
    private GoalSetService goalSetService;
    @Autowired
    EmailServiceImp emailServiceImp;
    @Value("${spring.mail.touser}")
    private String user;
    @Autowired
    private ContrastSaleDataDao contrastSaleDataDao;
    @Autowired
    private ContrastStoreSaleDataDao contrastStoreSaleDataDao;
    @Autowired
    private ItemsService itemsService;

    //共享资源
    static int count =0;
    /**
     * synchronized 修饰实例方法
     */
    public synchronized void increase(){
        count++;
    }


    @Async
    @Scheduled(cron = "0 0 21 * * ?")
    public void zxspTask(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Items items =new Items();
                List<Items> list = itemsService.queryByDate(items);
                String[] storeIds = {"070", "064", "065", "030", "121", "055", "003", "004", "005", "006", "007", "106", "008","118","329"};
                for (String storeID:storeIds) {
                    creatTaskZX(list,storeID);
                }
            }
        });

    }


    @Async
    @Scheduled(cron = "0 30 21 * * ?")
    public void dsrwTask(){
        ChartData chartData = goalSetService.queryByDate("");
        map.clear();

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new GaugeChart().createChart("零售销售完成度",creatPath("零售销售完成度"),chartData.getLsxswcdMap());

                new GaugeChart().createChart("总销售额完成度",creatPath("总销售额完成度"),chartData.getZxsewcdMap());

                new GaugeChart().createChart("批发销售完成度",creatPath("批发销售完成度"),chartData.getPfxswcdMap());

                new GaugeChart().createChart("零售毛利完成度",creatPath("零售毛利完成度"),chartData.getLsmlwcdMap());

                new GaugeChart().createChart("总毛利完成度",creatPath("总毛利完成度"),chartData.getZmlwcdMap());

                new GaugeChart().createChart("批发毛利完成度",creatPath("批发毛利完成度"),chartData.getPfmlwcdMap());

                new BarChart3DChartH().createChart(creatPath("总销售达标率"),chartData.getZxsdblMap(),"总销售达标率");

                new BarChart3DChartH().createChart(creatPath("今日总毛利达标率"),chartData.getTmldblMap(),"今日总毛利达标率");
                RobotSendServiceImpl robot= new RobotSendServiceImpl();
                String top ="";
                for (Map.Entry<String, Object> m : chartData.getTmldblMap().entrySet()) {
                    top = m.getKey();
                    break;
                }
                robot.sendMsg(map.get("今日总毛利达标率").toString(),"今日总毛利达标率","目前完成度最高的是:"+top+"\n");

                new ChartsMixedY().createMixedCharts(creatPath("月度零售对比曲线"),chartData);

                new ChartsMixed().createMixedCharts(creatPath("门店零售同比"),chartData);
                robot.sendMsg(map.get("门店零售同比").toString(),"门店零售同比","每天进步一点点！");

                new ChartsMixedH().createMixedCharts(creatPath("门店零售环比"),chartData);

                new BarChart3DChartH().createChart(creatPath("总毛利达标率"),chartData.getZmldblMap(),"总毛利达标率");
                robot.sendMsg(map.get("总毛利达标率").toString(),"总毛利达标率","加油！必胜！");

                new BarChart3DChartHJMD().createChart(creatPath("加盟店业绩排名"),chartData,"加盟店业绩排名");

                //会员经营日报表
                creatTask(chartData);

                //添加当日零售销售记录
                List<ContrastStoreSaleData> list= new ArrayList<>();
                for (Map.Entry<String, Object> entry : chartData.getMdlstdMapID().entrySet()) {
                    ContrastStoreSaleData dto = new ContrastStoreSaleData();
                    dto.setDate(DataUtil.getFormat());
                    dto.setStoreId(entry.getKey());
                    dto.setLxamt((BigDecimal) entry.getValue());
                    list.add(dto);
                }
                contrastStoreSaleDataDao.insertForeach(list);
                ContrastSaleData contrastSaleData = new ContrastSaleData();
                contrastSaleData.setDate(DataUtil.getFormat());
                contrastSaleData.setLxamt(chartData.getTodayLSXS().setScale(2, BigDecimal.ROUND_HALF_UP));
                contrastSaleDataDao.save(contrastSaleData);

                //发送邮件
                String content = "总销售额："+chartData.getTodayZXS().setScale(2, BigDecimal.ROUND_HALF_UP)+"元，总毛利额："+chartData.getTodayZML().setScale(2, BigDecimal.ROUND_HALF_UP)+"元，综合毛利率："+delShuju(chartData.getTodayZML(),chartData.getTodayZXS())+"；"+"\n"
                        +"零售额："+chartData.getTodayLSXS().setScale(2, BigDecimal.ROUND_HALF_UP)+"元，零售毛利："+chartData.getTodayLSML().setScale(2, BigDecimal.ROUND_HALF_UP)+"元，零售毛利率："+delShuju(chartData.getTodayLSML(),chartData.getTodayLSXS())+"；"+"\n"
                        +"批发额："+chartData.getTodayPFXS().setScale(2, BigDecimal.ROUND_HALF_UP)+"元，批发毛利："+chartData.getTodayPFML().setScale(2, BigDecimal.ROUND_HALF_UP)+"元，批发毛利率:"+delShuju(chartData.getTodayPFML(),chartData.getTodayPFXS())+"；"+"\n"
                        +"\n"+"\n"
                        +"总销售额达标率："+chartData.getZxsewcdMap().get("总销售额完成度")+"%，总毛利额达标率："+chartData.getZmlwcdMap().get("总毛利完成度")+"%；"+"\n"
                        +"零售销售达标率："+chartData.getLsxswcdMap().get("零售销售完成度")+"%，零售毛利达标率："+chartData.getLsmlwcdMap().get("零售毛利完成度")+"%；"+"\n"
                        +"批发销售达标率："+chartData.getPfxswcdMap().get("批发销售完成度")+"%，批发毛利达标率："+chartData.getPfmlwcdMap().get("批发毛利完成度")+"%；";
                String[] fileNames = new String[map.size()];
                        int m=0;
                        for (Map.Entry<String, Object> entry : MapSortUtil.sortByValueAsc(map).entrySet()) {
                            fileNames[m] = entry.getValue().toString();
                            m++;
                        }
                emailServiceImp.sendAttachmentMail(user, DataUtil.getNow()+"销售日报表",content,fileNames);

            }
        });


    }

    @Async
    @Scheduled(cron = "${timer.cron}")
    public void testTask() {
        ChartData chartData = goalSetService.queryByDate("");

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new ChartsMixed().createMixedCharts(creatPath("门店零售同比"),chartData);
                RobotSendServiceImpl robot= new RobotSendServiceImpl();
                robot.sendMsg(map.get("门店零售同比").toString(),"门店零售同比","每天进步一点点！");

                new BarChart3DChartH().createChart(creatPath("总毛利达标率"),chartData.getZmldblMap(),"总毛利达标率");
                robot.sendMsg(map.get("总毛利达标率").toString(),"总毛利达标率","加油！必胜！");

                new BarChart3DChartH().createChart(creatPath("今日总毛利达标率"),chartData.getTmldblMap(),"今日总毛利达标率");
                String top ="";
                for (Map.Entry<String, Object> m : chartData.getTmldblMap().entrySet()) {
                    top = m.getKey();
                    break;
                }
                robot.sendMsg(map.get("今日总毛利达标率").toString(),"今日总毛利达标率","目前完成度最高的是:"+top+"\n");

            }
        });


    }

    public String creatPath(String s1){
//        String ss = "D:\\echarts\\"+ new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(new Date())+".jpg";
        String ss = "E:\\PHPCUSTOM\\wwwroot\\ribaobiao\\dailyPic\\"+ new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(new Date())+".jpg";
        map.put(s1,ss);
         return ss;
    }

    public String delShuju(BigDecimal x,BigDecimal y){
        String str = "0%";
        if(x==null||y==null||x.equals(new BigDecimal(0))||y.equals(new BigDecimal(0))){
            str = "0%";
        }else {
            str =x.multiply(new BigDecimal(100)).divide(y, 2, BigDecimal.ROUND_HALF_UP)+"%";
        }
        return str;

        
    }


    public void creatTaskZX(List<Items> list,String storeId) {
        List<Items> newList = new ArrayList<>();
        String stroeName = "";
        for (Items items:list) {
            if(storeId.equals(items.getStoreID())){
                newList.add(items);
                stroeName = items.getStoreName();
            }

        }
        if(newList.size()>0) {
            String[][] tableData2 = new String[newList.size() + 1][7];
            String[] tt = {"分店名称", "大类编码", "条码", "名称", "售价", "进货数量", "进货金额"};

            DrawTableImgs cg = new DrawTableImgs();
            try {
                for (int i = 0; i < tt.length; i++) {
                    tableData2[0][i] = tt[i];
                }

                for (int i = 0; i < newList.size(); i++) {
                    for (int j = 0; j < tt.length; j++) {
                        switch (j) {
                            case 0:
                                tableData2[i + 1][j] = newList.get(i).getStoreName();
                                break;
                            case 1:
                                tableData2[i + 1][j] = newList.get(i).getKindCode();
                                break;
                            case 2:
                                tableData2[i + 1][j] = newList.get(i).getBarCode();
                                break;
                            case 3:
                                tableData2[i + 1][j] = newList.get(i).getName();
                                break;
                            case 4:
                                tableData2[i + 1][j] = newList.get(i).getPrice().setScale(2, BigDecimal.ROUND_HALF_UP)+ "";
                                break;
                            case 5:
                                tableData2[i + 1][j] = newList.get(i).getRecQty().setScale(2, BigDecimal.ROUND_HALF_UP)+""  ;
                                break;
                            case 6:
                                tableData2[i + 1][j] = newList.get(i).getCBAmount().setScale(2, BigDecimal.ROUND_HALF_UP)+"" ;
                                break;
                            default:
                                System.out.println("default");
                                break;
                        }


                    }
                }

                cg.myGraphicsGeneration(tableData2, creatPath(stroeName+"：15天未销售商品报表"),stroeName+"：15天未销售商品报表，进货日期："+DataUtil.format(DataUtil.addDay(DataUtil.getFormat(),-15)));
                RobotSendServiceImpl robot= new RobotSendServiceImpl();
                robot.sendMsg(map.get(stroeName+"：15天未销售商品报表").toString(),stroeName+"：15天未销售商品报表","请及时处理！");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void creatTask(ChartData chartData) {
                String[][] tableData2 = new String[chartData.getMdljhysMap().size()+1][6];
                String[] tt = {"门店名称","门店累计会员数","今日新增会员数","今日消费会员数","今日会员客单价","今日会员消费占比"};
                String[] categories = new String[chartData.getMdljhysMap().size()];

                int m = 0;
                for (Map.Entry<String, Object> entry : chartData.getMdljhysMap().entrySet()) {
                    categories[m]=entry.getKey();
                    m++;
                }
                DrawTableImgs cg = new DrawTableImgs();
                try {
                    for (int i = 0; i < tt.length; i++) {
                        tableData2[0][i]=tt[i];
                    }

                    for (int i = 0; i < chartData.getMdljhysMap().size(); i++) {
                        for (int j = 0; j < tt.length; j++) {
                            switch(j){
                                case 0:
                                    tableData2[i+1][j]=categories[i];break;
                                case 1:
                                    tableData2[i+1][j]=chartData.getMdljhysMap().get(categories[i])==null?0+"":chartData.getMdljhysMap().get(categories[i])+"";
                                    break;
                                case 2:
                                    tableData2[i+1][j]=chartData.getJrxzhysMap().get(categories[i])==null?0+"":chartData.getJrxzhysMap().get(categories[i])+"";
                                    break;
                                case 3:
                                    tableData2[i+1][j]=chartData.getJrxfhysMap().get(categories[i])==null?0+"":chartData.getJrxfhysMap().get(categories[i])+"";
                                    break;
                                case 4:
                                    tableData2[i+1][j]=chartData.getJrhykdjMap().get(categories[i])==null?0+"":chartData.getJrhykdjMap().get(categories[i])+"";
                                    break;
                                case 5:
                                    tableData2[i+1][j]=chartData.getJrhyxfzbMap().get(categories[i])==null?0+"%":chartData.getJrhyxfzbMap().get(categories[i])+"%";
                                    break;
                                default:
                                    System.out.println("default");break;
                            }



                        }
                    }

                    cg.myGraphicsGeneration(tableData2, creatPath("会员经营日报表"),"会员经营日报表");
                } catch (Exception e) {
                    e.printStackTrace();
                }

    }



}
