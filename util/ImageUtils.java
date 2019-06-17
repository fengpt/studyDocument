package com.app.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * @author Administrator
 */
public class ImageUtils {


    /**
     * 几种常见的图片格式
     */
    public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop


    /**
     * 程序入口：用于测试
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	String imgPath="D:/20180612/1.jpg";
    	String outPath="D:/20180612/2.jpg";

    	/*Image srcImage = javax.imageio.ImageIO.read(new File("E:/tempPic/3.png")); // 构造Image对象
		int old_w = srcImage.getWidth(null); // 得到源图宽
		int old_h = srcImage.getHeight(null);*/
    	
		/*// 这样会避免出现：图片压缩后有黑色线条存在
		ImageUtils.scale3("E:/tempPic/1.png", "E:/tempPic/15.png", 400, 300);//测试OK
		ImageUtils.scale2("E:/tempPic/15.png", "E:/tempPic/16.png", 400, 300, true);*/
		
        //ImageUtils.pressText("我是水印文字",imgPath,outPath,"宋体",Font.BOLD,Color.red,40, 0, 0, 0.5f);//测试OK
    	
    	
    	//ImageUtils.addWaterImage("D:/20180612/3.jpg", "D:/20180612/1.pdf","D:/20180612/6.pdf",-150f,-100f);
    	//ImageUtils.addPdfWaterText("中国人民大学附属中学", "D:/20180612/1.pdf","D:/20180612/6.pdf",Color.RED,50f,-100f,20f);
    	//ImageUtils.addPdfWaterImage("D:/20180612/3.jpg", "D:/20180612/6.pdf","D:/20180612/6.pdf",-50f,-100f);

    	//ImageUtils.addPdfWaterTextImage("中国人民大学附属中学", "D:/20180612/3.jpg", "D:/20180612/1.pdf", "D:/20180612/6.pdf", Color.RED);
    	ImageUtils.addPdfWaterImageForScale("D:/20180612/3.jpg", "D:/20180612/1.pdf",  "D:/20180612/6.pdf", 100, 100,0.5f, 0.8f);
    	/*
        // 1-缩放图像：
        // 方法一：按比例缩放
        ImageUtils.scale("e:/abc.jpg", "e:/abc_scale.jpg", 2, true);//测试OK
        // 方法二：按高度和宽度缩放
        ImageUtils.scale2("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300, true);//测试OK


        // 2-切割图像：
        // 方法一：按指定起点坐标和宽高切割
        ImageUtils.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400 );//测试OK
        // 方法二：指定切片的行数和列数
        ImageUtils.cut2("e:/abc.jpg", "e:/", 2, 2 );//测试OK
        // 方法三：指定切片的宽度和高度
        ImageUtils.cut3("e:/abc.jpg", "e:/", 300, 300 );//测试OK


        // 3-图像类型转换：
        ImageUtils.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");//测试OK


        // 4-彩色转黑白：
        ImageUtils.gray("e:/abc.jpg", "e:/abc_gray.jpg");//测试OK


        // 5-给图片添加文字水印：
        // 方法一：
        ImageUtils.pressText("我是水印文字","e:/abc.jpg","e:/abc_pressText.jpg","宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);//测试OK
        // 方法二：
        ImageUtils.pressText2("我也是水印文字", "e:/abc.jpg","e:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.5f);//测试OK
        
        // 6-给图片添加图片水印：
        ImageUtils.pressImage("e:/abc2.jpg", "e:/abc.jpg","e:/abc_pressImage.jpg", 0, 0, 0.5f);//测试OK
         */    
	}


    /** 
     * 缩放图像（按比例缩放） 
     * @param srcImageFile 源图像文件地址 
     * @param result 缩放后的图像地址 
     * @param scale 缩放比例 
     * @param flag 缩放选择:true 放大; false 缩小; 
     */  
    public final static void scale(String srcImageFile, String result, int scale, boolean flag) {  
        try {  
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件  
            int width = src.getWidth(); // 得到源图宽  
            int height = src.getHeight(); // 得到源图长  
            if (flag) {// 放大  
                width = width * scale;  
                height = height * scale;  
            } else {// 缩小  
                width = width / scale;  
                height = height / scale;  
            }  
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);  
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
            Graphics g = tag.getGraphics();  
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
            g.dispose();  
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * 缩放图像（按比例缩放） 
     * @param srcImageFile 源图像文件地址 
     * @param result 缩放后的图像地址 
     * @param scale 缩放比例 
     * @param flag 缩放选择:true 放大; false 缩小; 
     */  
    public final static void scale(String srcImageFile, String result, float scale, boolean flag) {  
        try {  
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件  
            int width = src.getWidth(); // 得到源图宽  
            int height = src.getHeight(); // 得到源图长  
            if (flag) {// 放大  
                width = (int)(width * scale);  
                height = (int)(height * scale);  
            } else {// 缩小  
                width = (int)(width / scale);  
                height =(int)( height / scale);  
            }  
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);  
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
            Graphics g = tag.getGraphics();  
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
            g.dispose();  
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * 注意：★★★★★
     * 如果 使用该方法压缩图片时，可能会有一条黑线。所以请先使用方法 scale3，然后再使用该方法
     * 缩放图像（按高度和宽度缩放） 
     * @param srcImageFile 源图像文件地址 
     * @param result 缩放后的图像地址 
     * @param height 缩放后的高度 
     * @param width 缩放后的宽度 
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白; 
     */  
    public final static void scale2(String srcImageFile, String result, int width, int height, boolean bb) {  
        try {  
            double ratio = 0.0; // 缩放比例  
            File f = new File(srcImageFile);  
            BufferedImage bi = ImageIO.read(f);  
            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);  
            // 计算比例  
            if ((bi.getHeight() >= height) || (bi.getWidth() >= width)) {  
                if (bi.getHeight() > bi.getWidth()) {  
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();  
                } else {  
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();  
                }  
                AffineTransformOp op = new AffineTransformOp(AffineTransform  
                        .getScaleInstance(ratio, ratio), null);  
                itemp = op.filter(bi, null);  
            }  
            if (bb) {//补白  
                BufferedImage image = new BufferedImage(width, height,  
                        BufferedImage.TYPE_INT_RGB);  
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
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    
    
    /**
     * 缩放图像【其中宽为 w 或者 高 h】
     * 注意：对图片进行压缩，不足使用空白填补时，请先使用该方法，然后在使用方法 scale2  ★★★★★
     * @param srcImageFile	源图像文件地址
     * @param result	缩放后的图像地址
     * @param w		压缩后可能出现的宽
     * @param h		压缩后可能出现的高
     */
    public final static void scale3(String srcImageFile, String result, int w, int h) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            double d = 0.0;
            if(height > width){						// 以高为基础 ★★★★★
            	d = (h+ 0.0) / height;
            	width = (int) (width * d);
            	height = h;			// (int) (height * d)
            }else{
            	d = (w+ 0.0) / width;				// 以宽为基础 ★★★★★
            	width = w;			// (int) (width * d)
            	height = (int) (height * d);
            }
            
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 图像切割(按指定起点坐标和宽高切割)
     * @param srcImageFile 源图像地址
     * @param result 切片后的图像地址
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param width 目标切片宽度
     * @param height 目标切片高度
     */
    public final static void cut(String srcImageFile, String result, int x, int y, int width, int height) {
        try {
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 图像切割（指定切片的行数和列数）
     * @param srcImageFile 源图像地址
     * @param descDir 切片目标文件夹
     * @param rows 目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param cols 目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    public final static void cut2(String srcImageFile, String descDir, int rows, int cols) {
        try {
            if(rows<=0||rows>20) rows = 2; // 切片行数
            if(cols<=0||cols>20) cols = 2; // 切片列数
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getHeight(); // 源图宽度
            int srcHeight = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                int destWidth = srcWidth; // 每张切片的宽度
                int destHeight = srcHeight; // 每张切片的高度
                // 计算切片的宽度和高度
                if (srcWidth % cols == 0) {
                    destWidth = srcWidth / cols;
                } else {
                    destWidth = (int) Math.floor(srcWidth / cols) + 1;
                }
                if (srcHeight % rows == 0) {
                    destHeight = srcHeight / rows;
                } else {
                    destHeight = (int) Math.floor(srcWidth / rows) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 图像切割（指定切片的宽度和高度）
     * @param srcImageFile 源图像地址
     * @param descDir 切片目标文件夹
     * @param destWidth 目标切片宽度。默认200
     * @param destHeight 目标切片高度。默认150
     */
    public final static void cut3(String srcImageFile, String descDir, int destWidth, int destHeight) {
        try {
            if(destWidth<=0) destWidth = 200; // 切片宽度
            if(destHeight<=0) destHeight = 150; // 切片高度
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            if (srcWidth > destWidth && srcHeight > destHeight) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                int cols = 0; // 切片横向数量
                int rows = 0; // 切片纵向数量
                // 计算切片的横向和纵向数量
                if (srcWidth % destWidth == 0) {
                    cols = srcWidth / destWidth;
                } else {
                    cols = (int) Math.floor(srcWidth / destWidth) + 1;
                }
                if (srcHeight % destHeight == 0) {
                    rows = srcHeight / destHeight;
                } else {
                    rows = (int) Math.floor(srcHeight / destHeight) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     * @param srcImageFile 源图像地址
     * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImageFile 目标图像地址
     */
    public final static void convert(String srcImageFile, String formatName, String destImageFile) {
        try {
            File f = new File(srcImageFile);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 彩色转为黑白 
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     */
    public final static void gray(String srcImageFile, String destImageFile) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(destImageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText(String pressText,
            String srcImageFile, String destImageFile, String fontName,
            int fontStyle, Color color, int fontSize,int x,
            int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName 字体名称
     * @param fontStyle 字体样式
     * @param color 字体颜色
     * @param fontSize 字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText2(String pressText, String srcImageFile,String destImageFile,
            String fontName, int fontStyle, Color color, int fontSize, int x,
            int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 给图片添加图片水印
     * @param pressImg 水印图片
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param x 修正值。 默认在中间
     * @param y 修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressImage(String pressImg, String srcImageFile,String destImageFile,
            int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image,  "JPEG", new File(destImageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @author ZhangLiang
     * @param srcf	源图像地址
     * @param dest	目标图像地址
     * @param w		缩放后的宽度
     * @param h		缩放后的高度
     * @param per	压缩质量【最高为 1.0】
     */
    public final static void zoomImage(String srcf, String dest, int w, int h, float per) {
		Image src;
		try {
			src = javax.imageio.ImageIO.read(new File(srcf)); // 构造Image对象

			String img_midname = dest;
			int old_w = src.getWidth(null); // 得到源图宽
			int old_h = src.getHeight(null);
			int new_w = 0;
			int new_h = 0; // 得到源图长

			double w2 = (old_w * 1.00) / (w * 1.00);
			double h2 = (old_h * 1.00) / (h * 1.00);

			// 图片调整为方形结束
			if (old_w > w){
				new_w = (int) Math.round(old_w / w2);
			}else{
				new_w = old_w;
			}
			if (old_h > h){
				new_h = (int) Math.round(old_h / h2);// 计算新图长宽
			}else{
				new_h = old_h;
			}
			BufferedImage tag = new BufferedImage(new_w, new_h,BufferedImage.TYPE_INT_RGB);
			// tag.getGraphics().drawImage(src,0,0,new_w,new_h,null); //绘制缩小后的图
			tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
			FileOutputStream newimage = new FileOutputStream(img_midname); // 输出到文件流
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/* 压缩质量 */
			jep.setQuality(per, true);
			encoder.encode(tag, jep);
			// encoder.encode(tag); //近JPEG编码
			newimage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 
     * @author ZhangLiang
     * @param src	源图像地址
     * @param dest	目标图像地址
     * @param w		缩放后的宽度
     * @param h		缩放后的高度
     * @throws IOException
     */
    public static void zoomImage1(String src, String dest, int w, int h) throws IOException {
		double wr = 0, hr = 0;
		File srcFile = new File(src);
		File destFile = new File(dest);
		BufferedImage bufImg = ImageIO.read(srcFile);
		Image Itemp = bufImg.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH);
		wr = w * 1.0 / bufImg.getWidth();
		hr = h * 1.0 / bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bufImg, null);
		try {
			ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}


    /**
     * 计算text的长度（一个中文算两个字符）
     * @param text
     * @return
     */
    public final static int getLength(String text) {
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
    
    /***
     * 增加pdf的文字和图片水印(水印图片和文字居中)
     * @param text 水印文字
     * @param imagePath 水印图片
     * @param filePath 源文件
     * @param outPath 输出文件
     * @param color 水印文字颜色
     */
    public static void addPdfWaterTextImage(String text,String imagePath ,String filePath,String outPath,Color color){
    	addPdfWaterTextImage(text, imagePath, filePath, outPath, color, 0, 0, 60, 0, 0);
    }
    
    /***
     * 增加pdf的文字和图片水印
     * @param text 水印文字
     * @param imagePath 水印图片
     * @param filePath 源文件
     * @param outPath 输出文件
     * @param color  水印文字颜色
     * @param tx 文字的横向偏移量
     * @param ty 文字的纵向偏移量
     * @param tz 文字的旋转度数
     * @param ix 图片的横向偏移量
     * @param iy 图片的纵向偏移量
     */
    public static void addPdfWaterTextImage(String text,String imagePath ,String filePath,String outPath,Color color,float tx,float ty,float tz,float ix,float iy){
    	InputStream is=null;
    	PdfReader reader=null;
    	OutputStream os=null;
    	PdfStamper stamper=null;
    	BaseFont base =null;
    	com.lowagie.text.Image logo=null;
    	try{
    		base =BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
    		is=new FileInputStream(filePath);
    		reader=new PdfReader(is);
    		os=new FileOutputStream(outPath);
    		stamper=new PdfStamper(reader, os);
    		PdfContentByte contentByte=null;
    		int n=reader.getNumberOfPages();
    		PdfGState gs=new PdfGState();
    		gs.setFillOpacity(0.5f);
    		gs.setStrokeOpacity(0.5f);	
    		if(StringUtils.isNotBlank(imagePath)){
    			 logo=com.lowagie.text.Image.getInstance(imagePath);
    		}
    		for (int i = 1; i <=n; i++) {
    			contentByte=stamper.getUnderContent(i);
    			contentByte.setGState(gs);
    			Rectangle rectangle=reader.getPageSize(i);
    			float width=rectangle.getWidth();
    			float height=rectangle.getHeight();
    			if(logo!=null){
    				logo.setAbsolutePosition(width/2-logo.getWidth()/2+ix, height/2-logo.getHeight()/2+iy);
        			contentByte.addImage(logo);
    			}
    			if(StringUtils.isNotBlank(text)){
        			contentByte.saveState();
        			contentByte.setGState(gs);
        			width=width/2-tx;
        		    height=height/2-ty;
        			contentByte.restoreState();
        			contentByte.beginText();
        			contentByte.setFontAndSize(base, 60);
        			contentByte.setColorFill(color);
        			contentByte.showTextAligned(Element.ALIGN_CENTER, text, width, height, tz);
        			contentByte.endText();
        			contentByte.setLineWidth(1f);
        			contentByte.stroke();
    			}
			}
    	}catch (Exception e) {
    		e.printStackTrace();
		}finally{
			reader.close();
			try{
				stamper.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			org.apache.commons.io.IOUtils.closeQuietly(is);
			org.apache.commons.io.IOUtils.closeQuietly(os);
		}
    }
    /***
     * 增加pdf的文字水印
     * @param text 水印文字
     * @param filePath 源文件
     * @param outpath 输出文件
     * @param x 横轴偏移量   为0是为中间位置
     * @param y 纵轴偏移量   为0为中间位置
     * @param z  旋转度数
     * @param color
     */
    public static void addPdfWaterText(String text,String filePath,String outpath,Color color,float x,float y,float z){
    	InputStream is=null;
    	PdfReader reader=null;
    	OutputStream os=null;
    	PdfStamper stamper=null;
    	BaseFont base =null;
    	try{
    		base =BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
    		is=new FileInputStream(filePath);
    		reader=new PdfReader(is);
    		os=new FileOutputStream(outpath);
    		stamper=new PdfStamper(reader, os);
    		PdfContentByte contentByte=null;
    		int n=reader.getNumberOfPages();
    		PdfGState gs=new PdfGState();
    		gs.setFillOpacity(1f);
    		gs.setStrokeOpacity(1f);
    		for (int i = 1; i <=n; i++) {
    			contentByte=stamper.getUnderContent(i);
    			contentByte.saveState();
    			contentByte.setGState(gs);
    			Rectangle rectangle=reader.getPageSize(i);
    			float width=rectangle.getWidth()/2-x;
    			float height=rectangle.getHeight()/2-y;
    			contentByte.restoreState();
    			contentByte.beginText();
    			contentByte.setFontAndSize(base, 60);
    			contentByte.setColorFill(color);
    			contentByte.showTextAligned(Element.ALIGN_CENTER, text, width, height, z);
    			contentByte.endText();
    			contentByte.setLineWidth(1f);
    			contentByte.stroke();
			}
    	}catch (Exception e) {
    		e.printStackTrace();
		}finally{
			reader.close();
			try{
				stamper.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			org.apache.commons.io.IOUtils.closeQuietly(is);
			org.apache.commons.io.IOUtils.closeQuietly(os);
		}
    }
    /****
     * 增加pdf的图片水印
     * @param imagePath 水印图片
     * @param filePath  源文件
     * @param outpath 输出文件
     * @param x 横轴偏移量   为0是为中间位置
     * @param y 纵轴偏移量   为0为中间位置
     */
    public static void  addPdfWaterImage(String imagePath,String filePath,String outpath,float x,float y){
    	addPdfWaterImage(imagePath, filePath, outpath, x, y,0.5f);
    }
    
    /****
     * 增加pdf的图片水印
     * @param imagePath 水印图片
     * @param filePath  源文件
     * @param outpath 输出文件
     * @param x 横轴偏移量   为0是为中间位置
     * @param y 纵轴偏移量   为0为中间位置
     * @param transparency 透明度  
     */
    public static void addPdfWaterImageForScale(String imagePath,String filePath,String outpath,float x,float y,float transparency){
    	addPdfWaterImageForScale(imagePath, filePath, outpath, x, y, transparency, 1);
    }
    
    /****
     * 增加pdf的图片水印
     * @param imagePath 水印图片
     * @param filePath  源文件
     * @param outpath 输出文件
     * @param x 横轴偏移量   为0是为中间位置
     * @param y 纵轴偏移量   为0为中间位置
     * @param transparency 透明度  
     * @param scale 水印图片的放大与缩小
     */
    public static void addPdfWaterImageForScale(String imagePath,String filePath,String outpath,float x,float y,float transparency,float scale){
    	InputStream is=null;
    	PdfReader reader=null;
    	OutputStream os=null;
    	PdfStamper stamper=null;
    	String result=imagePath;
    	try{
    		is=new FileInputStream(filePath);
    		reader=new PdfReader(is);
    		os=new FileOutputStream(outpath);
    		stamper=new PdfStamper(reader, os);
    		PdfContentByte contentByte=null;
    		int n=reader.getNumberOfPages();
    		PdfGState gs=new PdfGState();
    		gs.setFillOpacity(transparency);
    		gs.setStrokeOpacity(transparency);
    		com.lowagie.text.Image logo=null;
    		if(scale!=1 ){
    			int num=imagePath.lastIndexOf(".");
    			result=imagePath.substring(0, num);
    			String last=imagePath.substring(num+1, imagePath.length());
    			result=result+"1."+last;
        		scale(imagePath, result, scale, true);
    		}
   	     	logo=com.lowagie.text.Image.getInstance(result);
    		for (int i = 1; i <=n; i++) {
    			contentByte=stamper.getUnderContent(i);
    			contentByte.setGState(gs);
    			Rectangle rectangle=reader.getPageSize(i);
    			float width=rectangle.getWidth();
    			float height=rectangle.getHeight();
    			logo.setAbsolutePosition(width/2-logo.getWidth()/2+x, height/2-logo.getHeight()/2+y);
    			contentByte.addImage(logo);
			}
    		
    	}catch (Exception e) {
    		e.printStackTrace();
		}finally{
			reader.close();
			try{
				stamper.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				if(scale!=1&&!imagePath.equals(result)){
					File file=new File(result);
					if(file.exists()){
						file.delete();
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			org.apache.commons.io.IOUtils.closeQuietly(is);
			org.apache.commons.io.IOUtils.closeQuietly(os);
		}
    }
    
    /****
     * 增加pdf的图片水印
     * @param imagePath 水印图片
     * @param filePath  源文件
     * @param outpath 输出文件
     * @param x 横轴偏移量   为0是为中间位置
     * @param y 纵轴偏移量   为0为中间位置
     * @param transparency 透明度  
     * @param scale 水印图片的放大与缩小
     */
    public static void addPdfWaterImageForScale100(String imagePath,String filePath,String outpath,float x,float y,float transparency,float scale){
    	transparency=transparency/100;
    	scale=scale/100;
    	InputStream is=null;
    	PdfReader reader=null;
    	OutputStream os=null;
    	PdfStamper stamper=null;
    	String result=imagePath;
    	try{
    		is=new FileInputStream(filePath);
    		reader=new PdfReader(is);
    		os=new FileOutputStream(outpath);
    		stamper=new PdfStamper(reader, os);
    		PdfContentByte contentByte=null;
    		int n=reader.getNumberOfPages();
    		PdfGState gs=new PdfGState();
    		gs.setFillOpacity(transparency);
    		gs.setStrokeOpacity(transparency);
    		com.lowagie.text.Image logo=null;
    		if(scale!=1 ){
    			int num=imagePath.lastIndexOf(".");
    			result=imagePath.substring(0, num);
    			String last=imagePath.substring(num+1, imagePath.length());
    			result=result+"1."+last;
        		scale(imagePath, result, scale, true);
    		}
   	     	logo=com.lowagie.text.Image.getInstance(result);
    		for (int i = 1; i <=n; i++) {
    			contentByte=stamper.getUnderContent(i);
    			contentByte.setGState(gs);
    			Rectangle rectangle=reader.getPageSize(i);
    			float width=rectangle.getWidth();
    			float height=rectangle.getHeight();
    			logo.setAbsolutePosition(width/2-logo.getWidth()/2+x, height/2-logo.getHeight()/2+y);
    			contentByte.addImage(logo);
			}
    		
    	}catch (Exception e) {
    		e.printStackTrace();
		}finally{
			reader.close();
			try{
				stamper.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				if(scale!=1&&!imagePath.equals(result)){
					File file=new File(result);
					if(file.exists()){
						file.delete();
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			org.apache.commons.io.IOUtils.closeQuietly(is);
			org.apache.commons.io.IOUtils.closeQuietly(os);
		}
    }
    
    /****
     * 增加pdf的图片水印
     * @param imagePath 水印图片
     * @param filePath  源文件
     * @param outpath 输出文件
     * @param x 横轴偏移量   为0是为中间位置
     * @param y 纵轴偏移量   为0为中间位置
     * @param transparency 透明度
     */
    public static void addPdfWaterImage(String imagePath,String filePath,String outpath,float x,float y,float transparency){
    	InputStream is=null;
    	PdfReader reader=null;
    	OutputStream os=null;
    	PdfStamper stamper=null;
    	try{
    		is=new FileInputStream(filePath);
    		reader=new PdfReader(is);
    		os=new FileOutputStream(outpath);
    		stamper=new PdfStamper(reader, os);
    		PdfContentByte contentByte=null;
    		int n=reader.getNumberOfPages();
    		PdfGState gs=new PdfGState();
    		gs.setFillOpacity(transparency);
    		gs.setStrokeOpacity(transparency);
    		com.lowagie.text.Image logo=com.lowagie.text.Image.getInstance(imagePath);
    		for (int i = 1; i <=n; i++) {
    			contentByte=stamper.getUnderContent(i);
    			contentByte.setGState(gs);
    			Rectangle rectangle=reader.getPageSize(i);
    			float width=rectangle.getWidth();
    			float height=rectangle.getHeight();
    			logo.setAbsolutePosition(width/2-logo.getWidth()/2+x, height/2-logo.getHeight()/2+y);
    			contentByte.addImage(logo);
			}
    	}catch (Exception e) {
    		e.printStackTrace();
		}finally{
			reader.close();
			try{
				stamper.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			org.apache.commons.io.IOUtils.closeQuietly(is);
			org.apache.commons.io.IOUtils.closeQuietly(os);
		}
    }
}
