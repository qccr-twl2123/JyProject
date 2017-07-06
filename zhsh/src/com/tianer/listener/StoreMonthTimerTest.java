package com.tianer.listener;

 
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/**
 * 
* 类名称：StoreMonthTimerTest   
* 类描述：商家月统计定时器   
* 创建人：魏汉文  
* 创建时间：2016年6月17日 下午1:39:44
 */
@Component
public class StoreMonthTimerTest {
		
			 /**
			  * 每月月销售数量和金额，服务商获益记录
			  * 魏汉文20160617
			  */
			 @Scheduled(cron = "1 0 0  1 * ?")
			 public void QingKong() {
				 System.err.println("每月月销售数量和金额，服务商获益记录**************************************************");
				 PageData pd=new PageData();
				 DecimalFormat    df   =  TongYong.df2; 
				 try{
					 synchronized ( this ) { 
						 Calendar c = Calendar.getInstance();
					     c.add(Calendar.MONTH, -1);
					     String prevmonth=new SimpleDateFormat("yyyy-MM").format(c.getTime());
//		 		         System.out.println("上个月是："+prevmonth);
		 		         pd.put("month", prevmonth);
		 		    	 List<PageData>monthList=ServiceHelper.getAppPcdService().listAllStoreByOrderForFY(pd);
 		 		    	 for(PageData e : monthList){
		 		    			String number=e.get("number").toString();
		 		    			pd=ServiceHelper.getAppPcdService().getCityForName(e);
		 		    			if(pd != null ){
		 		    				String monthly_sales=pd.getString("monthly_sales");
			 		    			String send_money=pd.getString("send_money");
			 		    			if(Double.parseDouble(number) >= Double.parseDouble(monthly_sales)){
			 		    				pd=ServiceHelper.getSp_fileService().findById(e);
			 		    				if(pd != null && Double.parseDouble(send_money) > 0){
			 		    					//判断服务商这个月是否已经生成销售记录
			 		    					if(ServiceHelper.getAppPcdService().CountSpMonthGetMoney(pd) != null && ServiceHelper.getAppPcdService().CountSpMonthGetMoney(pd).equals("0")){
			 		    						String nowmoney=df.format(Double.parseDouble(pd.getString("nowmoney"))+Double.parseDouble(send_money));
					 		    				pd.put("nowmoney", nowmoney);
					 		    				ServiceHelper.getSp_fileService().edit(pd);//修改服务商余额
		 			 		    				//添加一个收益记录
					 		    				String service_performance_id="MSP"+BaseController.get10UID();
		  	 				 	 				PageData mmpd=new PageData();
		  	 				 	 				mmpd.put("profit_name", pd.getString("team_name"));//收益对象
		  	 				 	 				mmpd.put("yewu_name", "九鱼平台");//业务对象
		  	 				 	 				mmpd.put("yewu_id", "Jiuyu");//业务对象
		  	 					 	 			mmpd.put("yewu_type", "0");//业务对象
		  	 				 	 				mmpd.put("money", send_money);//金额
		  	 				 	 				mmpd.put("money_type", "3");//1、销售提成: 2、积分收益： 3、平台奖励，4-保证金，5-提现 
		  	 					 	 			mmpd.put("operate_type", "1"); //1-服务商，2-业务员
		  	 				 	 				mmpd.put("operate_id", pd.getString("sp_file_id")); 
		  	 				 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
		  	 				 	 				mmpd.put("isjihuo", "1");//0-未激活，1-已激活
		  	 				 	 				mmpd.put("service_performance_id", service_performance_id);//收益对象
		  	 				 	 				ServiceHelper.getService_performanceService().save(mmpd);
		  	 				 	 				//添加流水记录
		  	 						   			PageData waterpd=new PageData();
		  	 				    				waterpd.put("pay_status","1");
		  	 				    	   			waterpd.put("waterrecord_id",service_performance_id);
		  	 				   					waterpd.put("user_id", pd.getString("sp_file_id"));
		  	 				   					waterpd.put("user_type", "3");
		  	 				    				waterpd.put("withdraw_rate","0");
		  	 				   					waterpd.put("money_type","19");
		  	 				   	 				waterpd.put("money",  send_money);
		  	 				   	 				waterpd.put("reduce_money","0");
		  	 				   					waterpd.put("arrivalmoney",  send_money);
		  	 				   					waterpd.put("nowuser_money",nowmoney);
		  	 				   					waterpd.put("application_channel", "7" );
		  	 				   					waterpd.put("remittance_name", Const.payjiqi[7]);
		  	 				   					waterpd.put("remittance_type","6" );
		  	 				   					waterpd.put("jiuyu_money",  send_money);
		  	 				   					waterpd.put("remittance_number",pd.getString("phone"));//支付人的支付账号
		  	 				    				waterpd.put("createtime",DateUtil.getTime());
		  	 				   					waterpd.put("over_time",DateUtil.getTime());
		  	 				   	  				waterpd.put("jiaoyi_type","0");
		  	 				   					waterpd.put("payee_number",Const.jiuyunumber);
		  	 				    	 			waterpd.put("order_tn",service_performance_id);
		  	 				   					waterpd.put("province_name", pd.getString("province_name"));
		  	 				   					waterpd.put("city_name", pd.getString("city_name"));
		  	 				   					waterpd.put("area_name", pd.getString("area_name"));
		  	 				    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
		  	 				    				waterpd=null;
			 		    					}
 			 		    				}
 			 		    			}
		 		    			}
			 		    	}
 		 		    	 pd=null;
					 }
 				 }catch(Exception e){
					 System.out.println("每月月销售数量和金额，服务商获益记录"+e.toString());
					 (new TongYong()).dayinerro(e);
				 }
// 				 System.out.println("商家每月一号开始定时器统计完成");
			 }
 
			 
			 public static void main(String[] args){
				 new StoreMonthTimerTest().QingKong();
			 }
			 
  }
 
