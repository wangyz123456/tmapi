package com.example.tmapi.chart;

import com.example.tmapi.utils.ChartData;
import com.example.tmapi.utils.ChartUtils;
import com.example.tmapi.utils.DataUtil;
import com.example.tmapi.utils.Serie;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Vector;

public class BarChart3DChartHJMD {
    public BarChart3DChartHJMD(){}
    public DefaultCategoryDataset createDataset() {
        // 标注类别
        String[] categories = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        Vector<Serie> series = new Vector<Serie>();
        // 柱子名称：柱子所有的值集合
        series.add(new Serie("Tokyo", new Double[] { 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4 }));
//        series.add(new Serie("New York", new Double[] { 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3 }));
//        series.add(new Serie("London", new Double[] { 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2 }));
//        series.add(new Serie("Berlin", new Double[] { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1 }));
//        // 1：创建数据集合
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }
    public DefaultCategoryDataset createDataset(ChartData chartData) {
        // 标注类别
        String[] categories = new String[chartData.getJmdjrxseMap().size()];
        BigDecimal[] shuzuData = new BigDecimal[chartData.getJmdjrxseMap().size()];
        BigDecimal[] shuzuData1 = new BigDecimal[chartData.getJmdjrxseMap().size()];
        int i = 0;
        for (Map.Entry<String, Object> entry : chartData.getJmdjrxseMap().entrySet()) {
            categories[i]=entry.getKey();
            shuzuData[i] = (BigDecimal) entry.getValue();
            i++;
        }
        for (int j = 0; j < categories.length; j++) {
            shuzuData1[j] = (BigDecimal) chartData.getJmdjrmlMap().get(categories[j]);
        }


        Vector<Serie> series = new Vector<Serie>();
        // 柱子名称：柱子所有的值集合
        series.add(new Serie("今日毛利额",shuzuData1 ));
        series.add(new Serie("今日销售额",shuzuData ));
//        series.add(new Serie("New York", new Double[] { 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3 }));
//        series.add(new Serie("London", new Double[] { 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2 }));
//        series.add(new Serie("Berlin", new Double[] { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1 }));
//
//     1：创建数据集合
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }
    public ChartPanel createChart(String path, ChartData chartData, String title) {
        //2：创建Chart[创建不同图形]
        JFreeChart chart = ChartFactory.createBarChart(DataUtil.getNow()+"   "+title, "", "", createDataset(chartData),PlotOrientation.HORIZONTAL,true,false, false);

        // 3:设置抗锯齿，防止字体显示不清楚
        ChartUtils.setAntiAlias(chart);// 抗锯齿
        // 4:对柱子进行渲染[[采用不同渲染]]
        ChartUtils.setBarRenderer(chart.getCategoryPlot(),true);//
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setItemMargin(0.01D);
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesPaint(1, new Color(31, 121, 170 ));
        // 5:对其他部分进行渲染
        ChartUtils.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
        ChartUtils.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
        // 设置标注无边框
        chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
        //保持到指定位置
        try {
            ChartUtils.saveAsFile(chart,path,800,500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 6:使用chartPanel接收
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }
}
