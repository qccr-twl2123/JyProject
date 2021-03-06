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
import com.tianer.util.AppUtil;
import com.tianer.util.FileUpload;

public class MoreTextMarkService extends MarkService {
	
	public String watermark(File image, String imageFileName,String uploadPath, String realUploadPath) {
//		String logoImageName = "logo_"+imageFileName;
		OutputStream out = null;
		//1、创建图片缓存对象
		try{
			//获取原图信息
			Image image2 = ImageIO.read(image);
			int width = image2.getWidth(null);//宽
			int height = image2.getHeight(null);//高
			
			BufferedImage bfImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			
			//2、创建绘图工具对象
			Graphics2D graph = bfImage.createGraphics();
			
			//3、使用绘图工具对象将原图绘制到缓存图像对象中
			graph.drawImage(image2, 0, 0, width, height, null);
			graph.setFont(new Font(FONT_NAME,FONT_STYLE,FONT_SIZE));//设置字体
			graph.setColor(FONT_COLOR);//设置字体颜色
			
			//4、使用绘图工具对象将水印（文字/图片）绘制到缓存图片对象中
			//获取水印文字的宽高
			int textW = FONT_SIZE * getTextLength(MARK_TEXT);
			int textH = FONT_SIZE;
			
			//设置透明度
			graph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			
			//设置旋转角度及旋转中心
			//Math.toDegrees():将用弧度表示的角转换为近似相等的用角度表示的角
			//Math.toRadians():将用角度表示的角转换为近似相等的用弧度表示的角，rotate的第一个参数是用弧度表示的，所以使用toRadians方法
			//相同的设置成30，结果为:toDegrees方法添加倒置的水印，toRadians添加正置的水印
			graph.rotate(Math.toRadians(0),bfImage.getWidth()/2, bfImage.getHeight()/2);
			
			int x = -width/2;
 			while(x < width*1.5){
				//添加水印效果
				graph.drawString(MARK_TEXT, x, height/2);//y保证至少可以显示一个水印的高度
 				x +=textW+10;
			}
			
// 			int x = -width/2;  
//            int y= -height/2;  
//            while(x<width*1.5){  
//                y = -height/2;  
//                while(y<height*1.5){  
//                    //添加水印效果  
//                    graph.drawString(MARK_TEXT, x, y);//y保证至少可以显示一个水印的高度  
//                    y +=textH+200;//200为间隔值，即每个水印之间的间隔  
//                }  
//                x +=textW+200;  
//            }  
			
			
			//释放graph
			graph.dispose();
			
			out = new FileOutputStream(realUploadPath+imageFileName);
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
		return uploadPath+imageFileName;
	}
	
	
	
	
	public static void main(String[] args) {
		MoreTextMarkService m=new MoreTextMarkService();
		File image=new File("c:\\2.jpg");
		String imageFileName="22.jpg";
		String uploadPath="c:\\";
		String realUploadPath="c:\\";
		String url=m.watermark(image, imageFileName, uploadPath, realUploadPath);
		System.out.println(url);
		 
	}
	
	
	
}