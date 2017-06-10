//package com.tianer.listener;
//
// 
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
//* 类名称：PassTimerTest   
//* 类描述：过期定时器   
//* 创建人：魏汉文  
//* 创建时间：2016年6月17日 下午1:39:44
// */
//@Component
//public class OwnRedPassTimerTest {
//   
// 			 /**
//			  * 每天凌晨清空
//			  * 魏汉文20160617
//			  */
// 			@Scheduled(cron = "0 0 0 * * *")
//			 public void ZanDelete() {
//				 PageData pd=new PageData();
//				    try{
//				    		ServiceHelper.getAppPcdService().edit(pd);
//				    		//获取所有的会员过期红包减为三十个，更新会员的可用红包
//				    		//1.获取所有会员
//				    		List<PageData> memList=ServiceHelper.getAppMemberService().listAllMember(pd);
//				    		for (int i = 0; i < memList.size(); i++) {
//								PageData mpd=memList.get(i);
//								//更新用户的可用红包
//								ServiceHelper.getAppMemberService().updateMemberRedNumber(mpd);
//								//去掉过期的三十天外的红包
//								mpd.put("isguoqi", "1");
//								List<PageData> guoqiredList=ServiceHelper.getAppMember_redpacketsService().listRedId(mpd);
//								for (int j = 0; j < guoqiredList.size(); j++) {
//									  PageData guoqipd=guoqiredList.get(j);
//									  if(j>=30){
//										   ServiceHelper.getAppMember_redpacketsService().deleteGuoqiRed(guoqipd);//删除
//									  }
//								}
//							}
//				    }catch(Exception e){
//				    	new Log().error(e.toString());
//				    }
//				 System.out.println("ZanDelete清空完成");
//			 }
// 			 
//  }
// 
