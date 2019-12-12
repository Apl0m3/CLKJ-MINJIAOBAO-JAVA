/* ============================================= 
 * Created [Aug 24, 2011 10:57:30 AM] by zhangyl 
 * ============================================= 
 * 
 * 文件管理
 * 
 * ============================================= 
 *  Copyright (c) 2006 Dczg, Inc.
 *  All rights reserved
 * ============================================= 
 */ 
package com.lingkj.project.loaddown.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>水印工具类</p> 
 * 
 * @author zhangyl <br/>
 * 版本历史 <br/>
 * 	   version  Date      Author     Content   <br/>
 *     -------- --------- ---------- ------------------------   <br/>
 *     1.0      Aug 24, 2011   fredwang    最初版本   
 */
public class WatermarkUtils {
	/** 
     * 图片水印 xie.fei
     * @param pressImg 水印图片
     * @param targetImg 目标图片
     * @param x 修正值 默认在中间
     * @param y 修正值 默认在中间
     * @param alpha 透明度
     */  
    public final static void pressImage(String pressImg, InputStream targetImg, int x, int y, float alpha, File img) {
        try {
//            File img = new File(targetImg);
            Image src = ImageIO.read(targetImg);
            int wideth = src.getWidth(null);  
            int height = src.getHeight(null);  
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);  
            //水印文件  
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);  
            int height_biao = src_biao.getHeight(null);  
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);  
            //水印文件结束  
            g.dispose();
            //IOUtils.copy(new FileInputStream(image), new FileOutputStream(storeFullPath));
            ImageIO.write((BufferedImage) image, "jpg", img);
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }



    /**
     * 图片水印
     * @param pressImg 水印图片
     * @param targetImg 目标图片
     * @param x 修正值 默认在中间
     * @param y 修正值 默认在中间
     * @param alpha 透明度
     */
    public final static void pressImage(String pressImg, String targetImg, int x, int y, float alpha) {
        try {
            File img = new File(targetImg);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            //水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
            //水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image, "jpg", img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 
     * 文字水印 
     * @param pressText 水印文字 
     * @param targetImg 目标图片 
     * @param fontName 字体名称 
     * @param fontStyle 字体样式 
     * @param color 字体颜色 
     * @param fontSize 字体大小 
     * @param x 修正值 
     * @param y 修正值 
     * @param alpha 透明度 
     */  
    public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
        try {  
            File img = new File(targetImg);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);  
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);  
            g.setColor(color);  
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);  
            g.dispose();  
            ImageIO.write((BufferedImage) image, "jpg", img);
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }  
    /**
     * 缺省打水印（图片与文字）
     * @param targetImgFName
     * @param markImgFName
     * @param markText
     * @param alpha
     * @throws IOException
     */
    public static void pressDefaultImageText(String targetImgFName, String markImgFName, String markText, float alpha) throws IOException {
    	/*
    	 * markImgFName 如为空，不加图片水印
    	 * markText 如为空，不加文字水印
    	 * 图片水印在图片右下角，离右边3像素，下边3像素
    	 * 文字水印在图片左下角，离左边5像素，下边像素
    	 */
    	boolean isDrawImg = (markImgFName!=null || !markImgFName.equals(""))? true:false;
    	boolean isDrawText = (markText!=null || !markText.equals(""))? true:false;
    	if(!isDrawImg&& !isDrawText){
    		return ;
    	}
    	int fontSize = 40;//默认文字大小
    	String fontName = "黑体";
    	int fontStyle = Font.CENTER_BASELINE;
    	Color color = Color.RED;
    	File img = new File(targetImgFName);
        Image src = ImageIO.read(img);
        int width = src.getWidth(null);  
        int height = src.getHeight(null);  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(src, 0, 0, width, height, null);  
        //水印图片 
        Image markImg = ImageIO.read(new File(markImgFName));
        int markImgWidth = markImg.getWidth(null);  
        int markImgHeight = markImg.getHeight(null);  
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        if(isDrawImg){
        	//加图片水印
            g.drawImage(markImg, (width - markImgWidth) -3, (height - markImgHeight) -3, markImgWidth, markImgHeight,null);        //水印文件结束  
            
        }
        if(isDrawText){
        	g.setFont(new Font(fontName, fontStyle, fontSize));
        	FontMetrics   fm=g.getFontMetrics();
        	int txtheight = fm.getHeight();
        	//int   txtwidth=fm.stringWidth(markText); 
        	g.setColor(color);
        	
        	//加文字水印
            g.drawString(markText, 0, (height -35) );  
            
        }
        g.dispose();  
        ImageIO.write((BufferedImage) image, "jpg", img);
    }
    /** 
     * 缩放 
     * @param  //图片路径
     * @param height 高度 
     * @param width 宽度 
     * @param bb 比例不对时是否需要补白 
     */  
    public static void resize(String srcFilePath, String targetFilePath, int height, int width, boolean bb) {
        try {  
            double ratio = 0.0; //缩放比例   
            File f = new File(srcFilePath);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            //计算比例  
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {  
                if (bi.getHeight() > bi.getWidth()) {  
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {  
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }  
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);  
            }  
            if (bb) {  
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);  
                if (width == itemp.getWidth(null))  
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                else  
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                g.dispose();  
                itemp = image;  
            } 
            //如果输出文件没有指定，会覆盖原图
            String outfname=(targetFilePath==null||targetFilePath.equals(""))?srcFilePath:targetFilePath;
            ImageIO.write((BufferedImage) itemp, "jpg", new File(outfname));
        } catch (IOException e) {
            e.printStackTrace();  
        }  
    }  
     
    public static void main(String[] args) throws IOException {
    	//pressImage("D:\\2018\\a.jpg", "D:\\2018\\b.jpg", 0, 0, 0.5f);
       // pressText("我是文字水印", "D:\\2018\\a.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.3f);
        //resize("G:\\imgtest\\test1.jpg", 500, 500, true);  
    	//WatermarkUtils.pressDefaultImageText("E:\\temp\\testhouse.jpg", "E:\\temp\\logo.gif", "王峰提供,电话：133669955544", 1f);
    	//WatermarkUtils.resize("E:\\temp\\testhouse.jpg", 800, 1000, false);
    	/*
    	String[] formnames  = ImageIO.getWriterFormatNames();
    	//,jpg,BMP,bmp,JPG,jpeg,wbmp,png,JPEG,PNG,WBMP,GIF,gif
    	for(String str1 :formnames){
    	}*/
    }  
  
    public static int getLength(String text) {
        int length = 0;  
        for (int i = 0; i < text.length(); i++) {  
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;  
            } else {  
                length += 1;  
            }  
        }  
        return length / 2;  
    }  
}
