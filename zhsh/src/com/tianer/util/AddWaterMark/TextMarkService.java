package com.tianer.util.AddWaterMark;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class TextMarkService extends MarkService {
	public String watermark(File image, String imageFileName,
			String uploadPath, String realUploadPath) {
		String logoImageName = "logo_"+imageFileName;
		OutputStream out = null;
		try{
			//1、创建图片缓存对象
			//获取原图信息
			Image image2 = ImageIO.read(image);
			int width = image2.getWidth(null);//宽
			int height = image2.getHeight(null);//高
			
			//构造一个类型为预定义图像类型之一的 BufferedImage
			//TYPE_INT_RGB:表示一个图像，它具有合成整数像素的 8 位 RGB 颜色分量
			BufferedImage bfImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			
			//2、创建绘图工具对象(有点类似于画布)
			Graphics2D graph = bfImage.createGraphics();
			
			//3、使用绘图工具对象将原图绘制到缓存图像对象中
			graph.drawImage(image2, 0, 0, width, height, null);
			graph.setFont(new Font(FONT_NAME,FONT_STYLE,FONT_SIZE));//设置字体
			graph.setColor(FONT_COLOR);//设置字体颜色
			
			//4、使用绘图工具对象将水印（文字/图片）绘制到缓存图片对象中
			//获取水印文字的宽高
			int textW = FONT_SIZE * getTextLength(MARK_TEXT);
			int textH = FONT_SIZE;
			
			//获取原图与水印图的宽度、高度之差
			int wDiff = width-textW;
			int hDiff = height-textH;
			//设置初始位置
			int x=X;
			int y=Y;
			//保证当前图片至少可以放下一个水印文字
			if(x>wDiff){
				x = wDiff;
			}
			if(y>hDiff){
				y = hDiff;
			}
			//设置透明度
			//AlphaComposite.getInstance:创建一个 AlphaComposite 对象，
			//它具有指定的规则和用来乘源色 alpha 值的常量 alpha 值。
			//在将源色与目标色合成前，要将源色乘以指定的 alpha 值。
			graph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			//添加水印效果
			graph.drawString(MARK_TEXT, x, y+FONT_SIZE);//y保证至少可以显示一个水印的高度
			//释放graph
			graph.dispose();
			
			out = new FileOutputStream(realUploadPath+File.separator+logoImageName);
			//5、创建图像编码工具类
			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(out);
			//6、使用图像编码工具类输出缓存图像到目标文件中
			en.encode(bfImage);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//关闭流
			try{
				if(out!=null){
					out.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return uploadPath+File.separator+logoImageName;
	}
}