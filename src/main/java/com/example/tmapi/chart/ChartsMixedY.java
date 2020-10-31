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
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
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


public class ChartsMixedY {


    public DefaultCategoryDataset createLineDataset(ChartData chartData , BigDecimal[] shuzuData, String[] categories) {

        Vector<Serie> series = new Vector<Serie>();
        BigDecimal[] shuzuData1 = new BigDecimal[categories.length];
        BigDecimal[] shuzuData2 = new BigDecimal[categories.length];

        for(int i = 0; i < categories.length; i++){
            shuzuData1[i] = (BigDecimal) chartData.getByxsAvgMap().get(categories[0]);

        }
        for(int i = 0; i < categories.length; i++){
            shuzuData2[i] = (BigDecimal) chartData.getTqbyxsAvgMap().get(categories[0]);
        }

        series.add(new Serie("同期销售额",shuzuData));
        series.add(new Serie("本月平均值",shuzuData1));
        series.add(new Serie("同期平均值",shuzuData2));
        // 1：创建数据集合
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }

    public DefaultCategoryDataset createBarDataset(Map<String,Object> map ,String[] categories) {
        // 标注类别
        Vector<Serie> series = new Vector<Serie>();
        BigDecimal[] shuzuData = new BigDecimal[categories.length];
        for(int i = 0; i < categories.length; i++){
            shuzuData[i] = (BigDecimal) (map.get(categories[i]) == null ? new BigDecimal(0) :map.get(categories[i]) )  ;
        }
        series.add(new Serie("本月销售额",shuzuData));

        // 1：创建数据集合
        DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
        return dataset;
    }
    public void createMixedCharts(String path, ChartData chartData) {

        String[] categories = new String[chartData.getTqbyxsMap().size()];
        BigDecimal[] shuzuData = new BigDecimal[chartData.getTqbyxsMap().size()];
        int j = 1;
        for (int i = 0; i < categories.length; i++) {
            categories[i]=j+"";
            shuzuData[i] = (BigDecimal) chartData.getTqbyxsMap().get(categories[i]);
            j++;
        }
//
        CategoryDataset dataSetColumn = createBarDataset(chartData.getByxsMap(),categories);
        CategoryDataset dataSetLine = createLineDataset(chartData,shuzuData,categories);

        createChart(dataSetColumn, dataSetLine, path);
    }


    private void createChart(CategoryDataset dataSetColumn,
                             CategoryDataset dataSetLine, String path) {
        ChartUtils.setChartTheme();
//        Font font = new Font("宋体", Font.BOLD, 5);
        // 创建图形对象
        JFreeChart jfreeChart = ChartFactory.createBarChart(DataUtil.format(new Date(),DataUtil.FORMAT_LONOGRAM_CN)+"  月度零售对比曲线", // 图表标题
                "", // 目录轴的显示标签
                "",// 数值轴的显示标签
                dataSetColumn, // 数据集
                PlotOrientation.VERTICAL,// 图表方向：水平、垂直
                true, // 是否显示图例(对于简单的柱状图必须是false)
                true,// 是否生成工具
                false);// 是否生成URL链接


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
//        NumberAxis numberAxis = (NumberAxis) jfreeChart.getCategoryPlot().getRangeAxis();
        BarRenderer renderer = (BarRenderer) jfreeChart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, Color.orange);

        categoryPlot.setDataset(1, dataSetLine);// 设置数据集索引
        //设置折线
        // 第一个参数指数据集的索引,第二个参数为坐标轴的索引
        LineAndShapeRenderer lineAndShapeRenderer = new LineAndShapeRenderer();
        lineAndShapeRenderer.setBaseItemLabelsVisible(false);

        // 设置折线拐点的形状，圆形
        lineAndShapeRenderer.setSeriesShape(0, new Ellipse2D.Double(-2D, -2D,
                4D, 4D));
        //设置线粗细
        lineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(2.0F));
        lineAndShapeRenderer.setSeriesPaint(0, Color.cyan);//
        lineAndShapeRenderer.setSeriesPaint(1, Color.RED);//红色
        lineAndShapeRenderer.setSeriesPaint(2, Color.GREEN);//
        // 设置某坐标轴索引上数据集的显示样式
        categoryPlot.setRenderer(1, lineAndShapeRenderer);


        ValueAxis valueAxis = categoryPlot.getRangeAxis();
        valueAxis.setLabelFont(new Font("黑体", Font.ITALIC, 18)); // 设置数据字体(纵轴)
        CategoryAxis categoryaxis = categoryPlot.getDomainAxis();
        categoryaxis.setLabelFont(new Font("黑体", Font.ITALIC, 18)); // 设置时字体（横轴）
        renderer.setItemMargin(0.01D);
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴斜45度
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
