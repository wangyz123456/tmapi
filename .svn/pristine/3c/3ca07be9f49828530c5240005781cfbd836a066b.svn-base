package com.example.tmapi.chart;

import com.example.tmapi.utils.ChartUtils;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;


public class Bar3DLineChart {

    public void createChart(String path) {

        //折线图数据
        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
        //添加数据
        lineDataset.addValue(9, "", "语文");
        lineDataset.addValue(7, "", "数学");
        lineDataset.addValue(6, "", "英语");
        lineDataset.addValue(4, "", "物理");
        lineDataset.addValue(3, "", "化学");
        lineDataset.addValue(2, "", "生物");

        //柱状图数据
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //添加数据
        dataset.addValue(4, "", "语文");
        dataset.addValue(7, "", "数学");
        dataset.addValue(6, "", "英语");
        dataset.addValue(5, "", "物理");
        dataset.addValue(3, "", "化学");
        dataset.addValue(9, "", "生物");

        //生成的柱状图
        JFreeChart chart = ChartFactory.createBarChart3D(
                "科目成绩",
                "科目",//X轴的标签
                "分数",//Y轴的标签
                dataset, //图标显示的数据集合
                PlotOrientation.VERTICAL, //图像的显示形式（水平或者垂直）
                false,//是否显示子标题
                false,//是否生成提示的标签
                false); //是否生成URL链接

        /*
         * 处理图形上的乱码
         */

        //处理主标题的乱码
        chart.getTitle().setFont(new Font("黑体",Font.BOLD,18));

        //获取图表区域对象
        CategoryPlot categoryPlot = (CategoryPlot)chart.getPlot();

        //获取X轴的对象
        CategoryAxis3D categoryAxis3D = (CategoryAxis3D)categoryPlot.getDomainAxis();

        //获取Y轴的对象
        NumberAxis3D numberAxis3D = (NumberAxis3D)categoryPlot.getRangeAxis();

        //处理X轴上的乱码
        categoryAxis3D.setTickLabelFont(new Font("黑体",Font.BOLD,10));

        //处理X轴外的乱码
        categoryAxis3D.setLabelFont(new Font("黑体",Font.BOLD,10));

        //处理Y轴上的乱码
        numberAxis3D.setTickLabelFont(new Font("黑体",Font.BOLD,10));

        //处理Y轴外的乱码
        numberAxis3D.setLabelFont(new Font("黑体",Font.BOLD,10));

        //自定义Y轴上显示的刻度，以10作为1格
        numberAxis3D.setAutoTickUnitSelection(false);
        NumberTickUnit unit = new NumberTickUnit(1);
        numberAxis3D.setTickUnit(unit);

        //获取绘图区域对象
        BarRenderer3D barRenderer3D = (BarRenderer3D)categoryPlot.getRenderer();

        //设置柱形图的宽度
        barRenderer3D.setMaximumBarWidth(0.07);

        //在图形上显示数字
        barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer3D.setBaseItemLabelsVisible(true);
        barRenderer3D.setBaseItemLabelFont(new Font("宋体",Font.BOLD,10));

        /*
         * 放折线图数据
         */
        categoryPlot.setDataset(1, lineDataset);
        //设置折线
        LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
        lineandshaperenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        categoryPlot.setRenderer(1, lineandshaperenderer);
        // 柱状图和纵轴紧靠
        categoryAxis3D.setLowerMargin(0.0);

        categoryAxis3D.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        //折线在柱面前面显示
        categoryPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
//保持到指定位置
        try {
            ChartUtils.saveAsFile(chart,path,500,300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
