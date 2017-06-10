package com.tianer.util.ErWerMa;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

import javax.swing.JLabel;

import org.jbarcode.util.ImageUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.tianer.util.Logger;
  
/**
 * 
* 类名称：OneEr   
* 类描述：生成二维码
* 创建人：魏汉文  
* 创建时间：2016年8月19日 下午6:36:34
 */
public class OneEr {
    
    protected static Logger logger = Logger.getLogger(OneEr.class);
	private static final String CHARSET = "utf-8";
 	// 二维码尺寸
	private static final int QRCODE_SIZE_width= 500;
	private static final int QRCODE_SIZE_height = 600;
	
   /** 
    * (打印桌号)
   * @param Graphic指明打印的图形环境 
   * @param PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点） 
   * @param pageIndex指明页号 
 * @throws WriterException 
 * imagename:商家ID@桌号
 * 
   **/  
   public static void print(String store_name ,String desk_no , String imageinfor,String path) throws WriterException  {  
	        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
			hints.put(EncodeHintType.MARGIN, 1);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(imageinfor, BarcodeFormat.QR_CODE, QRCODE_SIZE_width, QRCODE_SIZE_height, hints);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	 		for (int x = 0; x < 500; x++) {
				for (int y = 0; y <600; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
				}
			}
	 		//Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog 和 DialogInput  
			Font font = new Font("宋体", Font.PLAIN, 35);  
	 		String text_infor=store_name + " "+ desk_no;
 	 		JLabel label = new JLabel(text_infor); 
	 		FontMetrics metrics = label.getFontMetrics(font); 
	 		int textH = metrics.getHeight(); //字符串的高, 只和字体有关 
	 		int textW = metrics.stringWidth(label.getText()); //字符串的宽
	 		if(textW > width){
	 			font = new Font("宋体", Font.PLAIN, 25);  
	 			label = new JLabel(text_infor); 
		 		metrics = label.getFontMetrics(font); 
		 		textH = metrics.getHeight(); //字符串的高, 只和字体有关 
		 		textW = metrics.stringWidth(label.getText()); //字符串的宽
	 		}
 	 		double x = width/2-textW/2;  
// 	 		double y = 600-textH/2;  
	 	    double y = 575;  
   	 	    Graphics gra = image.createGraphics();
           //转换成Graphics2D  
   	 	   Graphics2D g2 = (Graphics2D) gra ;  
   	 	   //设置打印颜色为黑色  
   	 	   g2.setColor(Color.black);  
 		   g2.setFont(font);//设置字体  
		   g2.drawString(text_infor, (float)x,(float)y);  
	 	   saveToPNG(image,imageinfor,path);
    } 
   
   
   /** 
    * （打印商家ID）
   * @param Graphic指明打印的图形环境 
   * @param PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点） 
   * @param pageIndex指明页号 
 * @throws WriterException 
   **/  
   public static void printStore(String store_infor, String store_name ,String store_id,String path) throws WriterException  {  
	        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
			hints.put(EncodeHintType.MARGIN, 1);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(store_infor, BarcodeFormat.QR_CODE, QRCODE_SIZE_width, QRCODE_SIZE_height, hints);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	 		for (int x = 0; x < 500; x++) {
				for (int y = 0; y <600; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
				}
			}
	 		//Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog 和 DialogInput  
			Font font = new Font("宋体", Font.PLAIN, 35);  
  	 		JLabel label1 = new JLabel(store_name); 
	 		FontMetrics metrics1 = label1.getFontMetrics(font); 
	 		int textH1 = metrics1.getHeight(); //字符串的高, 只和字体有关
	 		int textW1 = metrics1.stringWidth(label1.getText()); //字符串的宽
 	 	    double x1 = width/2-textW1/2;  
// 	 	    System.out.println(textH1);
 	 	    double y1 =45;
//	 	    double y1 = 55;  
	 		String text_id="ID:"+store_id;
	 		JLabel label2 = new JLabel(text_id); 
	 		FontMetrics metrics2 = label2.getFontMetrics(font); 
	 		int textH2 = metrics2.getHeight(); //字符串的高, 只和字体有关
	 		int textW2 = metrics2.stringWidth(label2.getText()); //字符串的宽
	 		double x = width/2-textW2/2;  
//	 		double y = 600-textH2/2;
	 	    double y = 575; 
  	 	    Graphics gra = image.createGraphics();
	//      //转换成Graphics2D  
	        Graphics2D g2 = (Graphics2D) gra ;  
	    //设置打印颜色为黑色  
	       g2.setColor(Color.black);  
   	       g2.setFont(font);//设置字体  
	       g2.drawString(store_name, (float)x1,(float)y1);  
	       g2.drawString(text_id, (float)x,(float)y);  
	       saveToPNG(image,store_id,path);
    }  


	
	public static void main(String[] args) {
		System.out.println("------");
		try {
			String imageinfor="33391593@03";
			printStore( "http://www.yihaomen.com","金溪测萨达大大多撒多试","33391593" ,"c:/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
	
	  public static void saveToPNG(BufferedImage paramBufferedImage, String paramString,String path)
	  {
	    saveToFile(paramBufferedImage, paramString, "png",path);
	  }

	  static void saveToGIF(BufferedImage paramBufferedImage, String paramString,String path)
	  {
	    saveToFile(paramBufferedImage, paramString, "gif",path);
	  }
	  
	  /**
	   * 
	  * 方法名称:：saveToFile 
	  * 方法描述：上传
	  * 创建人：魏汉文
	  * 创建时间：2016年8月19日 下午7:40:29
	   */
	 public  static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2,String path)
	  {
//		  	System.out.println("下载");
		    try
		    {
		      mkdirs(path);
 		      FileOutputStream localFileOutputStream = new FileOutputStream(path+paramString1+"."+paramString2);
		      ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 96, 96);
		      localFileOutputStream.close();
		    }
		    catch (Exception localException)
		    {
		      localException.printStackTrace();
		    }
//			System.out.println("下载结束");
	  }
	 

		/**
		 * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		 * @author lanyuan
		 * Email: mmm333zzz520@163.com
		 * @date 2013-12-11 上午10:16:36
		 * @param destPath 存放目录
		 */
		public static void mkdirs(String destPath) {
			File file =new File(destPath);    
			//当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
		}
}