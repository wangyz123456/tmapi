package com.example.tmapi.chart;

import com.example.tmapi.utils.ChartData;
import com.example.tmapi.utils.ChartUtils;
import com.example.tmapi.utils.DataUtil;
import com.example.tmapi.utils.Serie;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;

import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;


public class ChartsMixed {

    public DefaultCategoryDataset createBarDataset(Map<String, Object> map , BigDecimal[] shuzuData, String[] categories) {
        // 标注类别
//        String[] categories = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        Vector<Serie> series = new Vector<Serie>();
        BigDecimal[] shuzuData1 = new BigDecimal[categories.length];
        for(int i = 0; i < categories.length; i++){
            shuzuData1[i] = (BigDecimal) map.get(categories[i]);
        }
        series.add(new Serie("同期销售额",shuzuData));
        series.add(new Serie("今日销售额",shuzuData1));


        // 柱子名称：柱子所有的值集合
//        series.add(new Serie("Tokyo", new Double[] { 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4 }));
//        series.add(new Serie("New York", new Double[] { 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3 }));
//        series.add(new Serie("London", new Double[] { 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2 }));
//        series.add(new Serie("Berlin", new Double[] { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1 }));
        // 1：创建数据集合
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }

    public DefaultCategoryDataset createLineDataset(Map<String,Object> map ,String[] categories) {
        // 标注类别
//        String[] categories = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
//        Vector<Serie> series = new Vector<Serie>();
        Vector<Serie> series = new Vector<Serie>();
        BigDecimal[] shuzuData = new BigDecimal[categories.length];
        for(int i = 0; i < categories.length; i++){
            shuzuData[i] = (BigDecimal) map.get(categories[i]);
        }
        series.add(new Serie("同期占比",shuzuData));

        // 柱子名称：柱子所有的值集合
//        series.add(new Serie("Tokyo", new Double[] { 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4 }));
//        series.add(new Serie("New York", new Double[] { 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3 }));
//        series.add(new Serie("London", new Double[] { 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2 }));
//        series.add(new Serie("Berlin", new Double[] { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1 }));
        // 1：创建数据集合
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }
    public void createMixedCharts(String path, ChartData chartData) {

        String[] categories = new String[chartData.getMdlstdyMap().size()];
        BigDecimal[] shuzuData = new BigDecimal[chartData.getMdlstdyMap().size()];
        int i = 0;
        for (Map.Entry<String, Object> entry : chartData.getMdlstdyMap().entrySet()) {
            categories[i]=entry.getKey();
            shuzuData[i] = (BigDecimal) entry.getValue();
            i++;
        }


        CategoryDataset dataSetColumn = createBarDataset(chartData.getMdlstdMap(),shuzuData,categories);
        CategoryDataset dataSetLine = createLineDataset(chartData.getMdlstbMap(),categories);

        createChart(dataSetColumn, dataSetLine, path);
    }


    private void createChart(CategoryDataset dataSetColumn,
                             CategoryDataset dataSetLine, String path) {
        ChartUtils.setChartTheme();
//        Font font = new Font("宋体", Font.BOLD, 5);
        // 创建图形对象
        JFreeChart jfreeChart = ChartFactory.createBarChart(DataUtil.format(new Date(),DataUtil.FORMAT_LONOGRAM_CN)+"  门店零售同比", // 图表标题
                "", // 目录轴的显示标签
                "",// 数值轴的显示标签
                dataSetColumn, // 数据集
                PlotOrientation.VERTICAL,// 图表方向：水平、垂直
                true, // 是否显示图例(对于简单的柱状图必须是false)
                true,// 是否生成工具
                false);// 是否生成URL链接
//         jfreeChart.getTitle().setFont(font);
//        // 图表的背景色(默认为白色)
//        jfreeChart.setBackgroundPaint(Color.white);
//        // 设置图片背景色
//        GradientPaint gradientPaint = new GradientPaint(0, 1000, Color.WHITE,
//                0, 0, Color.WHITE, false);
//        jfreeChart.setBackgroundPaint(gradientPaint);

        CategoryPlot categoryPlot = (CategoryPlot) jfreeChart.getPlot();

        // 设置图形的背景色
        categoryPlot.setBackgroundPaint(Color.WHITE);
        // 设置图形上竖线是否显示
        categoryPlot.setDomainGridlinesVisible(false);
        // 设置图形上竖线的颜色
        categoryPlot.setDomainGridlinePaint(Color.GRAY);
        // 设置图形上横线的颜色
        categoryPlot.setRangeGridlinePaint(Color.GRAY);

        // 4:对柱子进行渲染[[采用不同渲染]]
        ChartUtils.setBarRenderer(jfreeChart.getCategoryPlot(), false);//
        // 设置柱状图的X轴显示样式
        ChartUtils.setXAixs(jfreeChart.getCategoryPlot());// X坐标轴渲染
        NumberAxis numberAxis = (NumberAxis) jfreeChart.getCategoryPlot().getRangeAxis();
        BarRenderer renderer = (BarRenderer) jfreeChart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(1, Color.orange);

        renderer.setItemMargin(0.01D);
        // 设置柱状图的Y轴显示样式

        //setNumberAxisToColumn(categoryPlot);
        CategoryAxis categoryaxis = categoryPlot.getDomainAxis();
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴斜45度

        // 设置折线图的Y轴显示样式
        setNumberAxisLine(categoryPlot);
        // 第一个参数指数据集的索引,第二个参数为坐标轴的索引
        LineAndShapeRenderer lineAndShapeRenderer = new LineAndShapeRenderer();
        // 数据点被填充即不是空心点
        lineAndShapeRenderer.setShapesFilled(true);
        // 数据点间连线可见
        lineAndShapeRenderer.setLinesVisible(true);
        //设置数据可见

        lineAndShapeRenderer.setBaseItemLabelsVisible(true);
        lineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING,
                NumberFormat.getInstance()));
        lineAndShapeRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));// weizhi

        // 设置折线拐点的形状，圆形
        lineAndShapeRenderer.setSeriesShape(0, new Ellipse2D.Double(-2D, -2D,
                4D, 4D));
        //设置线粗细
        lineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(2.0F));
        lineAndShapeRenderer.setSeriesPaint(0, Color.RED);//红色
        // 设置某坐标轴索引上数据集的显示样式
        categoryPlot.setRenderer(1, lineAndShapeRenderer);

        categoryPlot.setDataset(1, dataSetLine);// 设置数据集索引
        categoryPlot.mapDatasetToRangeAxis(1, 1);// 将该索引映射到axis



        // 设置两个图的前后顺序
        // ，DatasetRenderingOrder.FORWARD表示后面的图在前者上面，DatasetRenderingOrder.REVERSE表示
        // 表示后面的图在前者后面
        categoryPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        try {
            ChartUtils.saveAsFile(jfreeChart,path,800,500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置折线图的Y轴显示样式
     *
     * @param categoryplot
     * @return
     */
    private CategoryPlot setNumberAxisLine(CategoryPlot categoryplot) {
        ValueAxis numberaxis = new NumberAxis("");
//        numberaxis.setUpperBound(200.00D); // 纵轴上限
        numberaxis.setLowerBound(0.00D); // 纵轴下限
        numberaxis.setAutoRange(true);
        categoryplot.setRangeAxis(1, numberaxis);
        return categoryplot;
    }

    /**
     * 设置柱状图的Y轴显示样式,NumberAxis为整数格式
     *
     * @param categoryplot
     * @return
     */
    private CategoryPlot setNumberAxisToColumn(CategoryPlot categoryplot) {
        // 获取纵轴
        NumberAxis numberAxis = (NumberAxis) categoryplot.getRangeAxis();
        // 设置纵轴的刻度线
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // 数据轴的数据标签是否自动确定（默认为true）
        numberAxis.setAutoTickUnitSelection(true);
        // 数据轴的数据标签
        numberAxis.setStandardTickUnits(numberAxis.getStandardTickUnits());
        numberAxis.setLowerBound(0); // 数据轴上的显示最小值;
        numberAxis.setAutoRangeMinimumSize(1);// 1为一个间隔单位
        categoryplot.setRangeAxis(numberAxis);
        LayeredBarRenderer layeredBarRenderer = new LayeredBarRenderer();
        // 设置柱子的边框是否显示
        layeredBarRenderer.setDrawBarOutline(false);
        // 设置柱体宽度
        layeredBarRenderer.setMaximumBarWidth(0.08);
        // 设置柱体颜色
        layeredBarRenderer.setSeriesPaint(0, new Color(198, 219, 248));
        categoryplot.setRenderer(layeredBarRenderer);

        return categoryplot;
    }

    /**
     * 设置图表主题样式
     */
    private void setChartTheme() {
        // 创建主题样式
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        // 设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("宋书", Font.BOLD, 12));
        // 设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 12));
        // 设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 12));
        // 应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);
    }



    /**
     * 创建图表数据集
     *
     * @param data
     * @param rowKeys
     * @param columnKeys
     * @return
     */
    public CategoryDataset createDataset(double[][] data, String[] rowKeys,
                                         String[] columnKeys) {
        CategoryDataset dataset = getData(data, rowKeys, columnKeys);
        return dataset;
    }

    /**
     * 获取数据集
     *
     * @param data
     * @param rowKeys
     * @param columnKeys
     * @return
     */
    private CategoryDataset getData(double[][] data, String[] rowKeys,
                                    String[] columnKeys) {

        return DatasetUtilities
                .createCategoryDataset(rowKeys, columnKeys, data);
    }

}
