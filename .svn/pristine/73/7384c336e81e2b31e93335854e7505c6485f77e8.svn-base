package com.example.tmapi.controller;

import com.example.tmapi.chart.LineChart;
import com.example.tmapi.utils.JsonData;
import org.jfree.chart.ChartPanel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

@RestController
@RequestMapping(value="/pri/Echarts")
public class EchartsController {

    /**
     * 测试生产图表图片
     * @return JsonData
     */
    @RequestMapping(value="creatEcharts", method = RequestMethod.POST)
    public JsonData creatEcharts(){

        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 420);
        frame.setLocationRelativeTo(null);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 创建图形
                ChartPanel chartPanel = new LineChart().createChart("cs");
            }
        });
        return JsonData.buildSuccess("测试！！！");
    }


}
