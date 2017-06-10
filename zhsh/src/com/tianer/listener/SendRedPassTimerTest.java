//package com.tianer.listener;
//
// 
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.tianer.util.PageData;
//import com.tianer.util.ServiceHelper;
//
///**
// * 
//* 类名称：SpringTimerTest   
//* 类描述：积分红包到期定时器   
//* 创建人：魏汉文  
//* 创建时间：2016年6月17日 下午1:39:44
// */
//@Component
//public class SendRedPassTimerTest {
// 		 /**
//		  * 启动时执行一次，之后每隔一小时执行一次
//		  */
//		 @Scheduled(fixedRate = 1000*60 )
//		 public void BackJfRed() {
//			    PageData pd=new PageData();
//			    DecimalFormat    df   = new DecimalFormat("######0.00");
// 			    try{
// 				    	//查询所有状态未过期但是时间是已过期的积分红包
//			    	List<PageData> passJfRedList=ServiceHelper.getAppPcdService().allPassTimeJfRed(pd);
// 						for(PageData e : passJfRedList){
//							if(e != null){
//								String user_id=e.getString("user_id");
//								String user_type=e.getString("user_type");
//								if(e.getString("overget_money") == null || e.getString("overget_money").equals("")){
//									e.put("overget_money","0");
//								}
//								if(e.getString("redpackage_money") == null || e.getString("redpackage_money").equals("")){
//									e.put("redpackage_money","0");
//								}
//			 					double overget_money=Double.parseDouble(e.getString("overget_money"));
//								double redpackage_money=Double.parseDouble(e.getString("redpackage_money"));
//								PageData e1=new PageData();
//								if( user_type != null && user_type.equals("1")){//商家
//										e1.put("store_id",user_id);
//										e1.put("wealth_type", "1");
//										e1=ServiceHelper.getAppStoreService().findWealthById(e1);
//										if(e1 != null){
//											double now_wealth=Double.parseDouble(e1.getString("now_wealth"));
//				  							//减少用户积分余额
//											double n=now_wealth+(redpackage_money-overget_money);
//											e1.put("now_wealth", df.format(n));
//											ServiceHelper.getAppStoreService().editWealthById(e1);
// 										}
//	 		  					}else if(user_type != null && user_type.equals("2")){//会员
//										e1.put("member_id", user_id);
//										e1=ServiceHelper.getAppMemberService().findById(e1);
//										if(e1 != null){
//											double now_integral=Double.parseDouble(e1.getString("now_integral"));
//											double n=now_integral+(redpackage_money-overget_money);
//											e1.put("now_integral", df.format(n));
//											ServiceHelper.getAppMemberService().edit(e1);
// 										}
//	 							}
//								ServiceHelper.getAppPcdService().updatePassTimeJfRed(e);
//							}
// 		 				}
// 						 System.out.println("执行完成");
//			    }catch(Exception e){
//			    	System.out.println("查询所有状态未过期但是时间是已过期的积分红包启动时执行一次，之后每隔一小时执行一次"+e.toString());
//			    }
// 		 }
//		 
//	 
//		 public static void main(String[] args) {
//			 (new SendRedPassTimerTest()).BackJfRed();
//		}
//  }
// 
