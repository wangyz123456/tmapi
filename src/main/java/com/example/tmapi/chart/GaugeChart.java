package com.example.tmapi.chart;

import com.example.tmapi.utils.ChartUtils;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.RectangleEdge;

import java.awt.*;
import java.math.BigDecimal;

import java.util.Map;

public class GaugeChart  {

  public void createChart(String title, String path, Map<String,Object> map) {

        BigDecimal ss = (BigDecimal) map.get(title);
        Double dd = ss.doubleValue();
        if(Double.doubleToLongBits(dd) > Double.doubleToLongBits(100D) ){
            dd = 100D;
        }
        DefaultValueDataset dataset1 = new DefaultValueDataset(dd);
        DialPlot plot = new DialPlot();

        plot.setView(0.0, 0.0, 1.0, 1.0);

        plot.setDataset(0, dataset1);

        StandardDialFrame dialFrame = new StandardDialFrame();
        dialFrame.setBackgroundPaint(new Color(140, 182, 231));
        dialFrame.setForegroundPaint(new Color(140, 182, 231));
        plot.setDialFrame(dialFrame);

        StandartDiaRangSelf standarddialrange =new StandartDiaRangSelf(0D,30D, Color.red);
        standarddialrange.setInnerRadius(0.83000000000000002D);
        standarddialrange.setOuterRadius(0.89000000000000004D);
        plot.addLayer(standarddialrange);

        StandartDiaRangSelf standarddialrange1 =new StandartDiaRangSelf(30D,70D, Color.orange);
        standarddialrange1.setInnerRadius(0.83000000000000002D);

        standarddialrange1.setOuterRadius(0.89000000000000004D);

        plot.addLayer(standarddialrange1);
        StandardDialRange standarddialrange2 =new StandartDiaRangSelf(70D,100D, Color.blue);

        standarddialrange2.setInnerRadius(0.83000000000000002D);
        standarddialrange2.setOuterRadius(0.89000000000000004D);

        plot.addLayer(standarddialrange2);

        StandardDialScale scale = new StandardDialScale();
        scale.setLowerBound(0); // 最底表盘刻度
        scale.setUpperBound(100); // 最高表盘刻度
        scale.setStartAngle(-120); // 弧度为120,刚好与人的正常视觉对齐
        scale.setExtent(-300); // 弧度为300,刚好与人的正常视觉对齐
        scale.setTickRadius(0.88);
        scale.setTickLabelOffset(0.15);
        scale.setTickLabelFont(new Font("Dialog", Font.PLAIN, 14));
        scale.setTickLabelPaint(Color.black);
        scale.setMajorTickPaint(Color.black);
        scale.setMinorTickPaint(Color.black);
        plot.addScale(0, scale);

        DialPointer.Pointer  needle = new DialPointer.Pointer();
        needle.setRadius(0.8);
        needle.setFillPaint(Color.red);
        needle.setOutlinePaint(Color.red);
        plot.addLayer(needle);

        DialTextAnnotation annotation1 = new DialTextAnnotation("完成度"+ss+"%");
        plot.addLayer(annotation1);

        JFreeChart chart1 = new JFreeChart(plot);
        chart1.setBackgroundPaint(new Color(255, 255, 255));
        chart1.setTitle(title);
        chart1.getTitle().setPosition(RectangleEdge.BOTTOM);
        //保持到指定位置
        try {
            ChartUtils.saveAsFile(chart1,path,500,500);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
