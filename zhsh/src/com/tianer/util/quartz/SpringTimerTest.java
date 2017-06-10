package com.tianer.util.quartz;

 
import com.tianer.util.SeriviceUrl;

//@Component
public class SpringTimerTest {
			
 			 /**
			  * 启动时执行一次，之后每隔3秒执行一次
			  */
//			 @Scheduled(fixedRate = 1000 * 3 )
			 public void print() {
				 System.out.println("循环方法");
 			 }
			 
			 /**
			  * 定时启动。每天凌晨 16:19 执行一次
			  */
//			 @Scheduled(cron = "0 35 16 * * *")
			 public void show() {
			  System.out.println("定时器启动...");
			 String s= SeriviceUrl.SMS("","http://121.196.245.163//app_advice/listOfAll.do?page=1");
			 System.out.println(s);
			 }

			 
			 
  }
 
