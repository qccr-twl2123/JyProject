//package com.tianer.listener;
//
// 
//import java.text.DecimalFormat;
//import java.util.List;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.sun.media.Log;
//import com.tianer.util.Const;
//import com.tianer.util.PageData;
//import com.tianer.util.SeriviceUrl;
//import com.tianer.util.ServiceHelper;
//
///**
// * 
//* 类名称：YouXuanTimerTest   
//* 类描述：优选  
//* 创建人：魏汉文  
//* 创建时间：2016年6月17日 下午1:39:44
// */
//@Component
//public class YouXuanTimerTest {
// 		 /**
//		  * 启动时后执行一次 
//		  */
//		@Scheduled(cron = "0 0 0  1 * ?")
//		 public void BackJfRed() {
//			 PageData pd=new PageData();
//			 DecimalFormat    df   = new DecimalFormat("######0.00"); 
//			    try{ 
//			    	
//			    	
//			    }catch(Exception e){
//			    	System.out.println(e.toString());
//			    }
//			 System.out.println("执行完成");
//		 }
//		 
//	 
//  }
// 
