package com.tianer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tianer.controller.memberapp.tongyongUtil.TongYong;

public class ObjectExcelOperate {

    public static void main(String[] args) throws Exception {
         List<PageData> result = ObjectExcelOperate.getData("C://","ExcelDemo.xlsx", 1);
       int rowLength = result.size();
       for(int i=0;i<rowLength;i++) {
               System.out.println(result.get(i).toString());
         }

    }
    /**
     * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
     * @param file 读取数据的源Excel
     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
     * @return 读出的Excel中数据的内容
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<PageData> getData(String filepath,String  filename, int ignoreRows)
           throws FileNotFoundException, IOException {
       List<PageData> result = new ArrayList<PageData>();
       int rowSize = 0;
       
       File target = new File(filepath,filename);
       FileInputStream fi = new FileInputStream(target);
		//HSSFWorkbook wb = new HSSFWorkbook(fi);
	   Workbook wb = null;
       if(filename.toLowerCase().endsWith("xls")){    
			wb = new HSSFWorkbook(fi);    
       }else if(filename.toLowerCase().endsWith("xlsx")){  
       		wb = new XSSFWorkbook(fi);
       }
       Cell cell = null;
      try {
    	  for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
              Sheet st = wb.getSheetAt(sheetIndex); 
             // 第一行为标题，不取
             for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                Row row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                String[] values = new String[rowSize];
                Arrays.fill(values, "");
                PageData e=new PageData();
                for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                     String value = "";
                    cell = row.getCell(Short.parseShort(columnIndex + "")); 
                    if (cell != null) {
                       // 注意：一定要设成这个，否则可能会出现乱码
                       switch (cell.getCellType()) {
                       case HSSFCell.CELL_TYPE_STRING:
                           value = cell.getStringCellValue();
                           break;
                       case HSSFCell.CELL_TYPE_NUMERIC:
                           if (HSSFDateUtil.isCellDateFormatted(cell)) {
                              Date date = cell.getDateCellValue();
                              if (date != null) {
                                  value = new SimpleDateFormat("yyyy-MM-dd")
                                         .format(date);
                              } else {
                                  value = "";
                              }
                           } else {
                              value = new DecimalFormat("0").format(cell
                                     .getNumericCellValue());
                           }
                           break;
                       case HSSFCell.CELL_TYPE_FORMULA:
                           // 导入时如果为公式生成的数据则无值
                           if (!cell.getStringCellValue().equals("")) {
                              value = cell.getStringCellValue();
                           } else {
                              value = cell.getNumericCellValue() + "";
                           }
                           break;
                       case HSSFCell.CELL_TYPE_BLANK:
                           break;
                       case HSSFCell.CELL_TYPE_ERROR:
                           value = "";
                           break;
                       case HSSFCell.CELL_TYPE_BOOLEAN:
                           value = (cell.getBooleanCellValue() == true ? "Y"
                                  : "N");
                           break;
                       default:
                           value = "";
                       }
                    }
                    if (columnIndex == 0 && value.trim().equals("")) {
                       break;
                    }
                     e.put("var"+columnIndex,  rightTrim(value));
                 }
                result.add(e);
                e=null;
             }
         }
	} catch (Exception e) {
		// TODO: handle exception
		new TongYong().dayinerro(e);
	}
       fi.close();
       return result;
    }

    /**
     * 去掉字符串右边的空格
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
     public static String rightTrim(String str) {
       if (str == null) {
           return "";
       }
       int length = str.length();
       for (int i = length - 1; i >= 0; i--) {
           if (str.charAt(i) != 0x20) {
              break;
           }
           length--;
       }
       return str.substring(0, length);
    }
}