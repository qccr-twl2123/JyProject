//package com.tianer.controller.storepc.shopmanage;
//
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.CellStyle;
//
//
//public class ExcelTest {
//	 @SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
//	public void exportExcel(List list,HttpServletResponse response,HttpServletRequest request){
//		        Map<String, String> map = new HashMap<String, String>();
//		        int length=0;
//			    // 第一步，创建一个webbook，对应一个Excel文件  
//		        HSSFWorkbook wb = new HSSFWorkbook();  
//		        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
//		        HSSFSheet sheet = wb.createSheet("AAAAA");  
//		        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
//		        HSSFRow row = sheet.createRow(0);  
//		        // 第四步，创建单元格，并设置值表头 设置表头居中  
//		        HSSFCellStyle style = wb.createCellStyle();  
//		        style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式  
//		        HSSFCell cell = row.createCell((short) 0);  
//		        //"通过Map.entrySet遍历key和value  推荐，尤其是容量大时"
//		        int j=0;
//		        int m=0;
//		      
//		        for(int i=0;i<list.size();i++){
//		          map= (Map<String, String>) list.get(j);
//		        	   if(m==0){
//		        		     for(Map.Entry<String, String> entry : map.entrySet()) {
//			        		    cell = row.createCell((short) m++);  
//					            cell.setCellValue(entry.getKey());  
//					            cell.setCellStyle(style);  
//			            	     }   
//		        	           }
//		               row = sheet.createRow(j + 1);  
//		              // 第四步，创建单元格，并设置值  
//		               int k=0;
//		               for(Map.Entry<String, String> entry : map.entrySet()) {
//		               row.createCell((short) k++).setCellValue(entry.getValue());  
//		            	}
//		         j++;
//		        }
//		              //第六步，将文件存到指定位置  
//		        try  
//		        {  
//		        	  
//		        	      String str = "a.xls";  
//		                  String path = request.getSession().getServletContext().getRealPath("//WEB-INF//downLoad//a.xls");  
//		                  FileOutputStream fout = new FileOutputStream(path);  
//	                      wb.write(fout);  
//	                      fout.close();  
//		                  download(path, response);  
//		        }  
//		        catch (Exception e)  
//		        {  
//		            e.printStackTrace();  
//		        }  
//		    }  
//	 public void download(String path, HttpServletResponse response) {  
//	        try {  
//	            // path是指欲下载的文件的路径。  
//	            File file = new File(path);  
//	            // 取得文件名。  
//	            String filename = file.getName();  
//	            // 以流的形式下载文件。  
//	            InputStream fis = new BufferedInputStream(new FileInputStream(path));  
//	            byte[] buffer = new byte[fis.available()];  
//	            fis.read(buffer);  
//	            fis.close();  
//	            // 清空response  
//	            response.reset();  
//	            // 设置response的Header  
//	            response.addHeader("Content-Disposition", "attachment;filename="  
//	                    + new String(filename.getBytes()));  
//	            response.addHeader("Content-Length", "" + file.length());  
//	            OutputStream toClient = new BufferedOutputStream(  
//	                    response.getOutputStream());  
//	            response.setContentType("application/vnd.ms-excel;charset=gb2312");  
//	            toClient.write(buffer);  
//	            toClient.flush();  
//	            toClient.close();  
//	        } catch (IOException ex) {  
//	            ex.printStackTrace();  
//	        }  
//	    }  
//}
