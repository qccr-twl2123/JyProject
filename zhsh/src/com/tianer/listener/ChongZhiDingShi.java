//package com.tianer.listener;
//
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Timer;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
// 
//
//
//
//
//
//import com.tianer.entity.zhihui.StoreFeeTihuoTask;
//import com.tianer.entity.zhihui.TihuoTask;
//import com.tianer.util.Const;
//import com.tianer.util.DateUtil;
//import com.tianer.util.PageData;
//import com.tianer.util.ServiceHelper;
//
//public class ChongZhiDingShi implements ServletContextListener{	
// 
// 	
//	public void contextDestroyed(ServletContextEvent sce){
//		
//	}
//	
//	//已启动即运行
//	public void contextInitialized(ServletContextEvent sce){	
//			//设置商家购买服务费的定时器
//  			PageData pd=new PageData();
//			try { 
//				List<PageData> storeList=ServiceHelper.getAppStoreService().list(pd);
//				for (PageData e : storeList) {
//					String endtime=e.get("endtime").toString();
//					//设置定时器
//			 		long l1=DateUtil.fomatDate(DateUtil.getDay()).getTime();
//					long l2=DateUtil.fomatDate(endtime).getTime();
//					StoreFeeTihuoTask feeth=new StoreFeeTihuoTask(e.getString("store_id"));
//					Timer tt=new Timer();
//					tt.schedule(feeth, l2-l1);
//				}
// 			} catch (Exception e) {
//					e.printStackTrace();
//			} 
//			//设置提货卷的定时器
//  			PageData tihuopd=new PageData();
//			try { 
//				List<PageData> tihuoList=ServiceHelper.getAppOrderService().tihuoListByendTime(tihuopd);
//				for (PageData e : tihuoList) {
//					 String time=e.getString("enddate");
//					 String tihuo_id=e.getString("tihuo_id");
//					//设置定时器
//					long l1=DateUtil.fomatDate1(DateUtil.getTime()).getTime();
//					long l2=DateUtil.fomatDate1(time).getTime();
//					TihuoTask th=new TihuoTask(tihuo_id);
//					Timer tt=new Timer();
//					tt.schedule(th, l2-l1);
//				}
// 			} catch (Exception e) {
//					e.printStackTrace();
//			} 
// 			 
// 	}
// 
//
//}
