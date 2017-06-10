package com.tianer.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 下载文件
 * 创建人：cyr
 * 创建时间：2014年12月23日
 * @version
 */
public class FileDownload {

	/**
	 * @param response 
	 * @param filePath		//文件完整路径(包括文件名和扩展名)
	 * @param fileName		//下载后看到的文件名
	 * @return  文件名
	 */
	public static void fileDownload(final HttpServletResponse response, String filePath, String fileName) throws Exception{  
		     
		    byte[] data = FileUtil.toByteArray2(filePath);  
		    fileName = URLEncoder.encode(fileName, "UTF-8");  
		    response.reset();  
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
		    response.addHeader("Content-Length", "" + data.length);  
		    response.setContentType("application/octet-stream;charset=UTF-8");  
		    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
		    outputStream.write(data);  
		    outputStream.flush();  
		    outputStream.close();
		    response.flushBuffer();
 		} 
	
	/**
	 * 
	* 方法名称:：downpicture 
	* 方法描述：下载图片
	* 创建人：魏汉文
	* 创建时间：2016年8月19日 下午3:52:26
	 * @throws IOException 
	 */
	public static void downpicture(HttpServletResponse response,HttpServletRequest request,String urlString) throws IOException{
		BufferedInputStream dis = null;
        BufferedOutputStream fos = null;
         String fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
         try {
        	  String newurlString=urlString.substring(0,urlString.lastIndexOf('/') + 1)+URLEncoder.encode(fileName, "UTF-8");
              // 创建输出文件的流，也就是response的OutputStream  
          	  URL url = new URL(newurlString);
              response.setContentType("application/x-msdownload;");  
              response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
              response.setHeader("Content-Length", String.valueOf(url.openConnection().getContentLength()));  
             
              // 创建读取文件的流  
//             System.out.println(url.openStream());
             dis = new BufferedInputStream(url.openStream());
             fos = new BufferedOutputStream(response.getOutputStream());  
             
            byte[] buff = new byte[2048];  
            int bytesRead;  
            while (-1 != (bytesRead = dis.read(buff, 0, buff.length))) {  
                fos.write(buff, 0, bytesRead);  
            }  
             
        } catch (Exception e) {
            e.printStackTrace();
        } finally { 
             if (dis != null)  
                dis.close();  
            if (fos != null)  
                fos.close();  
             
        }
	}

}
