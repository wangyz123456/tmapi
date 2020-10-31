package com.example.tmapi.springtask;

import com.example.tmapi.chart.*;
import com.example.tmapi.email.EmailServiceImp;
import com.example.tmapi.email.RobotSendServiceImpl;
import com.example.tmapi.service.GoalSetService;
import com.example.tmapi.utils.ChartData;
import com.example.tmapi.utils.DrawTableImgs;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling

public class TestSpringTask {

    public Map<String,Object> map = new HashMap();
    //邮件计数器
    private static  int count=1;
    @Autowired
    private GoalSetService goalSetService;
    @Autowired
    EmailServiceImp emailServiceImp;
    @Value("${spring.mail.touser}")
    private String user;
    @Value("${spring.mail.filepath}")
    private String filepath;
    //每30秒发送一次
//    @Async
//    @Scheduled(cron = "0/50 * * * * ? ")
    public void sendEmil(){
        System.out.println(user);
        System.out.println(filepath);
//      emailServiceImp.sendHtmlMail(user,"这是邮件主题","这是springboot第"+count+"封邮件");
         SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                emailServiceImp.sendAttachmentsMail(user,"这是包含邮件的邮箱主题","这是springboot第"+count+"封邮件",filepath);
                count++;

                System.out.println(8);
            }
        });
    }

    @Async
    @Scheduled(cron = "0 30 21 * * ?")
    public void dsrwTask(){
        ChartData chartData = goalSetService.queryByDate("");

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new ChartsMixed().createMixedCharts(creatPath("门店零售同比"),chartData);
                RobotSendServiceImpl robot= new RobotSendServiceImpl();
                robot.sendMsg(map.get("门店零售同比").toString(),"门店零售同比","每天进步一点点！");

            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new ChartsMixedH().createMixedCharts(creatPath("门店零售环比"),chartData);
                RobotSendServiceImpl robot= new RobotSendServiceImpl();
                robot.sendMsg(map.get("门店零售环比").toString(),"门店零售环比","每天进步一点点！");

            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new BarChart3DChartH().createChart(creatPath("总毛利达标率"),chartData.getZmldblMap(),"总毛利达标率");
                RobotSendServiceImpl robot= new RobotSendServiceImpl();
                robot.sendMsg(map.get("总毛利达标率").toString(),"总毛利达标率","加油！必胜！");
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new BarChart3DChartH().createChart(creatPath("今日总毛利达标率"),chartData.getTmldblMap(),"今日总毛利达标率");
                String top ="";
                for (Map.Entry<String, Object> m : chartData.getTmldblMap().entrySet()) {
                     top = m.getKey();
                     break;
                }
                RobotSendServiceImpl robot= new RobotSendServiceImpl();
                robot.sendMsg(map.get("今日总毛利达标率").toString(),"今日总毛利达标率","目前完成度最高的是:"+top+"\n");
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("零售销售完成度",creatPath("零售销售完成度"),chartData.getLsxswcdMap());

            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("总销售额完成度",creatPath("总销售额完成度"),chartData.getZxsewcdMap());

            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("批发销售完成度",creatPath("批发销售完成度"),chartData.getPfxswcdMap());

            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("零售毛利完成度",creatPath("零售毛利完成度"),chartData.getLsmlwcdMap());

            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("总毛利完成度",creatPath("总毛利完成度"),chartData.getZmlwcdMap());

            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("批发毛利完成度",creatPath("批发毛利完成度"),chartData.getPfmlwcdMap());

            }
        });

    }

    @Async
    @Scheduled(cron = "${timer.cron}")
    public void testTask() {
        ChartData chartData = goalSetService.queryByDate("");

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                creatTask(chartData);
            }
        });
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new BarChart3DChartHJMD().createChart(creatPath("加盟店业绩排名"),chartData,"加盟店业绩排名");
            }
        });

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ChartsMixedY().createMixedCharts(creatPath("月度零售对比曲线"),chartData);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new BarChart3DChartH().createChart(creatPath("总销售达标率"),chartData.getZxsdblMap(),"总销售达标率");
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new ChartsMixed().createMixedCharts(creatPath("门店零售同比"),chartData);
//                RobotSendServiceImpl robot= new RobotSendServiceImpl();
//                robot.sendMsg(map.get("门店零售同比").toString(),"门店零售同比","每天进步一点点！");

            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new ChartsMixedH().createMixedCharts(creatPath("门店零售环比"),chartData);
//                RobotSendServiceImpl robot= new RobotSendServiceImpl();
//                robot.sendMsg(map.get("门店零售环比").toString(),"门店零售环比","每天进步一点点！");

            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new BarChart3DChartH().createChart(creatPath("总毛利达标率"),chartData.getZmldblMap(),"总毛利达标率");
//                RobotSendServiceImpl robot= new RobotSendServiceImpl();
//                robot.sendMsg(map.get("总毛利达标率").toString(),"总毛利达标率","加油！必胜！");
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                new BarChart3DChartH().createChart(creatPath("今日总毛利达标率"),chartData.getTmldblMap(),"今日总毛利达标率");
//                RobotSendServiceImpl robot= new RobotSendServiceImpl();
//                String top ="";
//                for (Map.Entry<String, Object> m : chartData.getTmldblMap().entrySet()) {
//                    top = m.getKey();
//                    break;
//                }
//                robot.sendMsg(map.get("今日总毛利达标率").toString(),"今日总毛利达标率","目前完成度最高的是:"+top+"\n");
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("零售销售完成度",creatPath("零售销售完成度"),chartData.getLsxswcdMap());

            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("总销售额完成度",creatPath("总销售额完成度"),chartData.getZxsewcdMap());

            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("批发销售完成度",creatPath("批发销售完成度"),chartData.getPfxswcdMap());

            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("零售毛利完成度",creatPath("零售毛利完成度"),chartData.getLsmlwcdMap());

            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("总毛利完成度",creatPath("总毛利完成度"),chartData.getZmlwcdMap());

            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GaugeChart().createChart("批发毛利完成度",creatPath("批发毛利完成度"),chartData.getPfmlwcdMap());

            }
        });

//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new PieChart().createChart(creatPath("饼状图"));
//                System.out.println(4);
//            }
//        });
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new GaugeChart().createChart("cs",creatPath("仪表盘"));
//                System.out.println(5);
//            }
//        });
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Bar3DLineChart().createChart(creatPath("直柱图"));
//                System.out.println(6);
//            }
//        });



    }

    public String creatPath(String s1){
//        String ss = "D:\\echarts\\"+ new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(new Date())+".jpg";
        String ss = "E:\\PHPCUSTOM\\wwwroot\\ribaobiao\\dailyPic\\"+ new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(new Date())+".jpg";
        map.put(s1,ss);
         return ss;
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

                    cg.myGraphicsGeneration(tableData2, creatPath("会员经营日报表"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

    }


//    public void creatTask() {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                DrawTableImgs cg = new DrawTableImgs();
//                try {
//                    String[][] tableData2 = {{"门店名称","门店累计会员数","今日新增会员数","今日消费会员数","今日会员客单价","今日会员消费占比"},
//                    };
//
//
//                    cg.myGraphicsGeneration(tableData2, creatPath("会员经营日报表"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(7);
//            }
//        });
//    }
}
