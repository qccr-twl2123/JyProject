package com.tianer.util.AddWaterMark;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageMarkService extends MarkService {
	public String watermark(File image, String imageFileName,
			String uploadPath, String realUploadPath) {
		String logoImageName = "logo_"+imageFileName;
		OutputStream out = null;
		//用来作为水印的图片位置
		String logoFilePath = realUploadPath+File.separator+LOGO;
		try{
			//1、创建图片缓存对象
			//获取原图信息
			Image image2 = ImageIO.read(image);
			int width = image2.getWidth(null);//宽
			int height = image2.getHeight(null);//高
			
			BufferedImage bfImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			
			//2、创建绘图工具对象
			Graphics2D graph = bfImage.createGraphics();
			
			//3、使用绘图工具对象将原图绘制到缓存图像对象中
			graph.drawImage(image2, 0, 0, width, height, null);
			
			//获取水印图片文件
			File logoFile = new File(logoFilePath);
			Image logoImage = ImageIO.read(logoFile);
			
			//4、使用绘图工具对象将水印（文字/图片）绘制到缓存图片对象中
			//获取水印文字的宽高
			int imageW = logoImage.getWidth(null);
			int imageH = logoImage.getHeight(null);
			
			//获取原图与水印图的宽度、高度之差
			int wDiff = width-imageW;
			int hDiff = height-imageH;
			//设置初始位置
			int x=X;
			int y=Y;
			if(x>wDiff){
				x = wDiff;
			}
			if(y>hDiff){
				y = hDiff;
			}
			//设置透明度
			graph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			//添加水印效果
			graph.drawImage(logoImage, x, y,null);//y保证至少可以显示一个水印的高度
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
			try{
				if(out!=null){
					out.close();//关闭流
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return uploadPath+File.separator+logoImageName;
	}
}