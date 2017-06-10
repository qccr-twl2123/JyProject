package com.tianer.listener;

 
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tianer.controller.memberapp.tongyongUtil.TongYong;

/**
 * 
* 类名称：SpFileMonthTimerTest   
* 类描述：服务商月统计   
* 创建人：魏汉文  
* 创建时间：2016年6月17日 下午1:39:44
 */
@Component
public class SpFileMonthTimerTest {
	
		
			 /**
			  * 每月一号开始定时器
 			  */
			 @Scheduled(cron = "0 0 0  1 * ?")
			 public void QingKong() {
				 System.err.println("服务商每月一号开始定时器**************************************************");
				 TongYong.spMonth_number(-1);
				 System.err.println("业务员每月一号开始定时器**************************************************");
				 TongYong.clerkMonth_number(-1);
			 }
 
			 
			 public static void main(String[] args){
				 Calendar c = Calendar.getInstance();
			     c.add(Calendar.DATE, -1);
			     String prevmonth=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
 		         System.out.println("上一天是："+prevmonth);
			 }
			 
  }
 
