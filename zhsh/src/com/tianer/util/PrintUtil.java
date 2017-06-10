package com.tianer.util;

import java.awt.BasicStroke;  
import java.awt.Color;  
import java.awt.Component;  
import java.awt.Font;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.Toolkit;  
import java.awt.RenderingHints;  
import java.awt.font.FontRenderContext;  
import java.awt.font.LineBreakMeasurer;  
import java.awt.font.TextAttribute;  
import java.awt.font.TextLayout;  
import java.awt.geom.Point2D;  
  
import java.awt.image.BufferedImage;  
  
import java.awt.print.Book;  
  
import java.awt.print.PageFormat;  
  
import java.awt.print.Paper;  
  
import java.awt.print.Printable;  
  
import java.awt.print.PrinterException;  
  
import java.awt.print.PrinterJob;  
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
  
import java.text.AttributedString;  

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.JApplet;  

import org.jbarcode.JBarcode;
import org.jbarcode.encode.CodabarEncoder;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.Code93Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.EAN8Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.EAN8TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
  
public class PrintUtil {
    
    protected static Logger logger = Logger.getLogger(PrintUtil.class);
	private static String CODE = "123445213";
	private static String NAME = "aaaaa";
	private static String ID = "ddddd";
	
   /** 
   * @param Graphic指明打印的图形环境 
   * @param PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点） 
   * @param pageIndex指明页号 
   * 生成一维码
   **/  
   public static void print()  {  
//       logger.info("pageIndex="+pageIndex);  
       Component c = null;  
      //print string  
      BufferedImage localBufferedImage = null;
      try{
	      JBarcode localJBarcode = new JBarcode(CodabarEncoder.getInstance(), WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
	      localBufferedImage = localJBarcode.createBarcode(CODE);
	   }
   catch (Exception localException)
   {
     localException.printStackTrace();
   }

	   Graphics gra = localBufferedImage.createGraphics();
      //转换成Graphics2D  
      Graphics2D g2 = (Graphics2D) gra ;  
      //设置打印颜色为黑色  
      g2.setColor(Color.black);  
      //打印起点坐标  
      double x = 10;  
      double y = 10;   
      //Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog 和 DialogInput  
      Font font = new Font("新宋体", Font.PLAIN, 9);  
      g2.setFont(font);//设置字体  
      //BasicStroke   bs_3=new   BasicStroke(0.5f);     
      float[]   dash1   =   {2.0f};   
      //设置打印线的属性。  
      //1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量  
      g2.setStroke(new   BasicStroke(0.5f,   BasicStroke.CAP_BUTT,   BasicStroke.JOIN_MITER,   2.0f,   dash1,   0.0f));    
      //g2.setStroke(bs_3);//设置线宽  
      float heigth = font.getSize2D();//字体高度  
      // -1- 用Graphics2D直接输出  
      //首字符的基线(右下部)位于用户空间中的 (x, y) 位置处  
      //g2.drawLine(10,10,200,300);   
//      g2.drawString("站点名称： "+NAME, (float)(x+1*heigth+11), (float)y+1*heigth);  
//      g2.drawString("站点编号： "+ID, (float)(x+1*heigth+11), (float)y+2*heigth);  
//      g2.drawString(CODE, (float)(x+1*heigth+11), (float)y+3*heigth);  
//      g2.drawLine((int)x,(int)(y+1*heigth+img_Height+10),(int)x+200,(int)(y+1*heigth+img_Height+10));  
//      g2.drawImage(localBufferedImage,(int)x,(int)(y+1*heigth+11),c);  
      System.out.println(g2.create());
      saveToPNG(localBufferedImage,"z");
   }  
   
//   /**
//    * 
//   * 方法名称:：printBarCode 
//   * 方法描述：设置纸片的大小
//   * 创建人：魏汉文
//   * 创建时间：2016年8月11日 下午4:49:40
//    */
//	public static void printBarCode(String code, String id, String name) { 
//		CODE = code;
//		NAME = name;
//		ID = id;
//	    //    通俗理解就是书、文档  
//	    Book book = new Book();  
//	    //    设置成竖打  
//	    PageFormat pf = new PageFormat();  
//	    pf.setOrientation(PageFormat.PORTRAIT);  
//	    //    通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
//	    Paper p = new Paper();  
//	    p.setSize(180,140);//纸张大小   
//	    p.setImageableArea(10,0, 180,140);//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72  
//	    pf.setPaper(p);  
//	    //    把 PageFormat 和 Printable 添加到书中，组成一个页面  
//	    book.append(new PrintUtil(), pf);  
//	     //获取打印服务对象  
//	     PrinterJob job = PrinterJob.getPrinterJob();        
//	     // 设置打印类  
//	     job.setPageable(book);  
//	     try {  
//	         //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印  
//	//         boolean a=job.printDialog();  
//	//         if(a)  
//	//         {          
//	         job.print();  
// 	//         }  
//	     } catch (PrinterException e) {  
//	         e.printStackTrace();  
//	     }  
//	   }  
	
	public static void main(String[] args) {
		System.out.println("------");
		print();
	}
	
	  public static void saveToPNG(BufferedImage paramBufferedImage, String paramString)
	  {
	    saveToFile(paramBufferedImage, paramString, "png");
	  }

	  static void saveToGIF(BufferedImage paramBufferedImage, String paramString)
	  {
	    saveToFile(paramBufferedImage, paramString, "gif");
	  }
	  
	  
	 public  static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2)
	  {
		  	System.out.println("下载");
		    try
		    {
		      FileOutputStream localFileOutputStream = new FileOutputStream("d:/zhuohao/" + paramString1+"."+paramString2);
		      ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 96, 96);
		      localFileOutputStream.close();
		    }
		    catch (Exception localException)
		    {
		      localException.printStackTrace();
		    }
	  }
}