//package com.tianer.listener;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Timer;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.mysql.fabric.xmlrpc.base.Data;
//import com.tianer.entity.zhihui.MsgTask;
//import com.tianer.util.DateUtil;
//import com.tianer.util.PageData;
//import com.tianer.util.ServiceHelper;
//
// 
//
///**
// * 
//* 类名称：CityFeeTime   
//* 类描述：   超出商品限度的费用定时设置
//* 创建人：魏汉文  
//* 创建时间：2016年7月20日 下午6:32:27
// */
//@Component
//public class CityFeeTime {
//	
//	//开始的方法每天的凌晨
// 	@Scheduled(cron = "0 0 0 * * *")
// 	public  void overGoodsFee() {
//		PageData pd=new PageData();
//		try {
//			 //获取所有的城市营销的收费时间
//			 List<PageData> cityList=ServiceHelper.getCity_marketingService().getCitySevenList(pd);
//			//循环：1.设置定时器定时器传的参数为：省市区名称，以及金额
//			for(PageData e : cityList){
//				Date date=new Date();
//				long l1=date.getTime();
//				String time=e.get("billing_time").toString();
//				String nowday=DateUtil.getDay();
//				time=nowday+" "+time;
//				Date date2=DateUtil.fomatDate1(time);
//				long l2=date2.getTime();
////				System.out.println(time+"$"+(l2-l1)+"$l1="+l1+"&l2="+l2);
//				if(l2-l1 >=0){
//					System.out.println("收费"+time);
//					MsgTask mt=new MsgTask(e.getString("province_name"),e.getString("city_name"),e.getString("area_name"),e.getString("billing_money"));
//					Timer tt=new Timer();
//					tt.schedule(mt, l2-l1);
////					tt.schedule(mt,20000);
//				}
// 			}
// 		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		System.out.println("overGoodsFee城市营销的收费时间");
//	}
// }
