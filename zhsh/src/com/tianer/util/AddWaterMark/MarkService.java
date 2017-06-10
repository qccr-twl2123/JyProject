package com.tianer.util.AddWaterMark;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import com.tianer.util.AppUtil;

public abstract class MarkService {
	public static final String MARK_TEXT="九鱼网-每笔消费，必有优惠";//添加水印的文字
	
	public static final String FONT_NAME="微软雅黑";//所用字体
	public static final int FONT_STYLE = Font.BOLD;//文字样式，这里设置为加粗
	public static final  int FONT_SIZE = 25;//文字大小
	public static final  Color FONT_COLOR = Color.GRAY;//文字颜色
	
	//文字/图片位置
	public static final int X=10;//横坐标
	public static final int Y=10;//纵坐标
	public static final float ALPHA=0.3F;//透明度
	
	//作为水印添加到图片文件中的图片名称
	public static final String LOGO_URL=AppUtil.getuploadRootUrlIp()+"/logo.png"; 
	public static final String LOGO_URL1=AppUtil.getuploadRootUrlIp()+"/logo1.png"; 
	public static final String LOGO_URL2=AppUtil.getuploadRootUrlIp()+"/logo2.png"; 
	public static final String LOGO_URL3=AppUtil.getuploadRootUrlIp()+"/logo3.png"; 
	public static final String LOGO="logo.png"; 
 	
	/**
	 * 添加水印功能
	 * @param image:需要添加水印的图片文件
	 * @param imageFileName：图片文件名称
	 * @param uploadPath：文件上传的相对路径
	 * @param realUploadPath:处理后的文件所在的绝对路径（目前上传的文件与处理后的文件在同一目录下）
	 * */
	public abstract String watermark(File image,String imageFileName,String uploadPath,String realUploadPath);
	
	//获取文本长度
	public int getTextLength(String text){
		int length = text.length();//获取所有文本的长度
		for(int i=0;i<text.length();i++){
			String s = String.valueOf(text.charAt(i));//获取指定的字符
			if(s.getBytes().length>1){//字节长度大于1，说明是中文，那么需要延长文本长度
				length++;
			}
		}
		//计算总共有多少个字节，也就是有多少个字
		length = (length%2==0)?length/2:length/2+1;
		return length;
	}
}