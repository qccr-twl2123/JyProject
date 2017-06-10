package com.tianer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	
	private final static SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	
	private final static SimpleDateFormat sdfDayshms = new SimpleDateFormat("yyyyMMddhhmmss");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	
	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}
	
	/**
	 * 获取yyyyMMddhhmmss格式
	 * 
	 * @return
	 */
	public static String getDayshms(){
		return sdfDayshms.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}
	
	/**
	 * 获取YYYY-MM格式
	 * 
	 * @return
	 */
	public static String getMonth() {
		return sdfMonth.format(new Date());
	}
	
	/**
	 * 当前时间加一秒
	 */
	public static String getTimeAddone(int i) {
		Date now=new Date();
		Date afterDate = new Date(now .getTime() + 1000*i);
		return sdfTime.format(afterDate);
	}
	
	/**
	 * 当前时间加n个小时
	 */
	public static String getTimeAddThree(int i) {
		Date now=new Date();
		Date afterDate = new Date(now .getTime() +3600*1000*i);
		return sdfTime.format(afterDate);
	}
	

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate1(s)==null||fomatDate1(e)==null){
			return false;
		}
		return fomatDate1(s).getTime() >=fomatDate1(e).getTime();
	}
	
	
	/**
	* @Title: compareDate
	* @Description: TODO(时间比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author luguosui
	 */
	public static int compareDateTime(String s, String e) throws ParseException {
		if(sdfTime.parse(s).getTime()>sdfTime.parse(e).getTime()){
			return 1;
		}else if(sdfTime.parse(s).getTime()==sdfTime.parse(e).getTime()){
			return 0;
		}else{
			return -1;
		}
	}
	
	/**
	 * 格式化日期(将yyyyMMdd变成yyyy-MM-ddd)
	 * 
	 * @return
	 */
	public static String formatDateChange(String str){
		  SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
		     SimpleDateFormat sf2 =new SimpleDateFormat("yyyy-MM-dd");
		     String sfstr = "";
		     try {
		      sfstr = sf2.format(sf1.parse(str));
		  } catch (ParseException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  return sfstr;
		 }
	

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate1(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate2(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	
    public static void main(String[] args) {
    	try {
			System.out.println(getAfterMonthDate("2016-09-11" ,"3"));
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	
	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	  /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 得到n月之后的日期
     * @param days
     * @return
     */
    public static String getAfterMonthDate(String beginDate,String month) {
    	int daysInt = Integer.parseInt(month);
    	
    	Calendar calendar = new GregorianCalendar(); 
        calendar.setTime(fomatDate(beginDate)); 
    	
        calendar.add(Calendar.MONTH, daysInt); // 日期减 如果不够减会将月变动
        Date date = calendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    
    
    
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String beginDate,String days) {
    	int daysInt = Integer.parseInt(days);
    	
    	Calendar calendar = new GregorianCalendar(); 
        calendar.setTime(fomatDate(beginDate)); 
    	
        calendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = calendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterTimeDate(String beginDate,String days) {
    	int daysInt = Integer.parseInt(days);
    	
    	Calendar calendar = new GregorianCalendar(); 
    	calendar.setTime(fomatDate1(beginDate)); 
    	
    	calendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
    	Date date = calendar.getTime();
    	
    	SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String dateStr = sdfd.format(date);
     	return dateStr;
    }
    
    /**
     * 得到n天之后的时间
     * @param days
     * @return
     */
    public static String getAfterDayTime(String beginDate,String days) {
    	int daysInt = Integer.parseInt(days);
     	Calendar calendar = new GregorianCalendar(); 
        calendar.setTime(fomatDate1(beginDate)); 
        calendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = calendar.getTime();
        return sdfTime.format(date);
    }
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
         return dateStr;
    }
    
    /**
     * 得到n年之后是周几
     * @param days
     * @return
     */
    public static String getAfterYear(String years) {
    	int yearsInt = Integer.parseInt(years);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.YEAR, yearsInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        String dateStr = sdfTime.format(date);
        
        return dateStr;
    }

}
