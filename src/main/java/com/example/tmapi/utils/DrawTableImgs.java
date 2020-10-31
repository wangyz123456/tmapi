package com.example.tmapi.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawTableImgs {
    /**
     * @param cellsValue cellsValue
     * @param path path
     */
    public void myGraphicsGeneration(String cellsValue[][], String path) {
        // 字体大小
        int fontTitileSize = 15;
        // 横线的行数
        int totalrow = cellsValue.length+1;
        // 竖线的行数
        int totalcol = 0;
        if (cellsValue[0]  != null) {
            totalcol = cellsValue[0].length;
        }
        // 图片宽度
        int imageWidth = 900;
        // 行高
        int rowheight = 30;
        // 图片高度
        int imageHeight = totalrow*rowheight+50;
        // 起始高度
        int startHeight = 10;
        // 起始宽度
        int startWidth = 10;
        // 单元格宽度
        int colwidth = (int)((imageWidth-20)/totalcol);
        BufferedImage image = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0, imageWidth, imageHeight);
        graphics.setColor(new Color(220,240,240));

        //画横线
        for(int j=0;j<totalrow; j++){
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth, startHeight+(j+1)*rowheight, startWidth+colwidth*totalcol, startHeight+(j+1)*rowheight);
        }
        //画竖线
        for(int k=0;k<totalcol+1;k++){
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth+k*colwidth, startHeight+rowheight, startWidth+k*colwidth, startHeight+rowheight*totalrow);
        }
        //设置字体
        Font font = new Font("微软雅黑",Font.BOLD,fontTitileSize);
        graphics.setFont(font);
        //写标题
        String title = "会员经营日报表";
        graphics.drawString(title, startWidth, startHeight+rowheight-10);
        //写入日期 yyyy-MM-dd HH:mm:ss
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        graphics.drawString("日期:"+date, imageWidth/2, startHeight+rowheight-10);
        //写入内容
        for(int n=0;n<cellsValue.length;n++){
            for(int l=0;l<cellsValue[n].length;l++){
                if (n == 0) {
                    font = new Font("微软雅黑",Font.BOLD,fontTitileSize);
                    graphics.setFont(font);
                }else if (n > 0 && l >0) {
                    font = new Font("微软雅黑",Font.PLAIN,fontTitileSize);
                    graphics.setFont(font);
                    graphics.setColor(Color.black);
                } else {
                    font = new Font("微软雅黑",Font.PLAIN,fontTitileSize);
                    graphics.setFont(font);
                    graphics.setColor(Color.BLACK);
                }
                graphics.drawString(cellsValue[n][l].toString(), startWidth+colwidth*l+5, startHeight+rowheight*(n+2)-10);
//                drawString(graphics, cellsValue[n][l].toString(), startWidth+colwidth*l+5, startHeight+rowheight*(n+2)-10);
            }
        }
        // 保存图片
        createImage(image, path);
    }

    public void drawString(Graphics g, String str, int xPos, int yPos) {
        int strWidth = g.getFontMetrics().stringWidth(str);
        g.drawString(str, xPos + strWidth / 2, yPos);
    }
    /**
     * 将图片保存到指定位置
     * @param image 缓冲文件类
     * @param fileLocation 文件位置
     */
    public void createImage(BufferedImage image, String fileLocation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
