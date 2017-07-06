package com.tianer.entity.zhihui;

import java.util.Date;
import java.util.TimerTask;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
 
/**
 * 
* 类名称：积分红包过期处理
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class JFredTask extends TimerTask{
 
  	private String ms_redpackage_id;//
    

	public   JFredTask(){
		
	}

	public   JFredTask(String ms_redpackage_id ) {
 		this.ms_redpackage_id = ms_redpackage_id;
 	}

	
	@Override
	public synchronized void run() {
			/*
			 * 在定时器到时间的时候 
 			 */
  		 	PageData ms_redpackagepd=new PageData();
 		 	try {
  		 		ms_redpackagepd.put("ms_redpackage_id", ms_redpackage_id);
	 		 	if(ServiceHelper.getAppPcdService().JfRedDetailById(ms_redpackagepd) != null){
	 		 			ms_redpackagepd=ServiceHelper.getAppPcdService().JfRedDetailById(ms_redpackagepd);
	 		 			System.out.println("发送的积分红包处理时间=================ms_redpackage_id="+ms_redpackage_id+"======time="+new Date().getTime());
	 		 			ServiceHelper.getAppPcdService().updatePassTimeJfRed(ms_redpackagepd);
	 					String user_id=ms_redpackagepd.getString("user_id");
	 					String user_type=ms_redpackagepd.getString("user_type");
	 					if(ms_redpackagepd.getString("overget_money") == null || ms_redpackagepd.getString("overget_money").equals("")){
	 						ms_redpackagepd.put("overget_money","0");
	 					}
	 					if(ms_redpackagepd.getString("redpackage_money") == null || ms_redpackagepd.getString("redpackage_money").equals("")){
	 						ms_redpackagepd.put("redpackage_money","0");
	 					}
	 					double overget_money=Double.parseDouble(ms_redpackagepd.getString("overget_money"));
	 					double redpackage_money=Double.parseDouble(ms_redpackagepd.getString("redpackage_money"));
	 					if(redpackage_money-overget_money>0){
	 						PageData mmpd=new PageData();
	  	 					if( user_type != null && user_type.equals("1")){//商家
	  	 							mmpd.put("store_id",user_id);
	  	 							mmpd=ServiceHelper.getAppStoreService().findById(mmpd);
	  	  							if( mmpd != null){
	  	 								double now_wealth=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(mmpd));
	  	 	  							//减少用户积分余额
	  	 								double lessmoney=(redpackage_money-overget_money);
	  	 								double n=now_wealth+lessmoney;
	  	 								mmpd.put("now_wealth", TongYong.df2.format(n));
	  	 								ServiceHelper.getAppStoreService().editWealthById(mmpd);
	  	 								//新增商家财富历史记录
	  	 								PageData pd=new PageData();
	  									String store_wealthhistory_id=BaseController.getTXUID(user_id);
	  									pd.put("store_wealthhistory_id", store_wealthhistory_id);
	  									pd.put("jiaoyi_id", store_wealthhistory_id);
	  			 			   			pd.put("wealth_type", "1");
	  		 			   				pd.put("profit_type", "8");
	  		 			   				pd.put("number",TongYong.df2.format(lessmoney));
	  		 			   				pd.put("store_id", lessmoney);
	  		 			   				pd.put("pay_type", Const.jiuyupay);
	  			 			   			pd.put("store_operator_id", "jy"+user_id);
	  		 			   				pd.put("process_status", "1");
	  				 			   		pd.put("last_wealth", ServiceHelper.getAppStoreService().sumStoreWealth(mmpd));
	  			 			   			pd.put("arrivalMoney", TongYong.df2.format(lessmoney));//实际到账金额
	  				 			   		pd.put("user_id", Const.jiuyunumber);
		  				 			   	pd.put("jiaoyi_id", store_wealthhistory_id);
		  								pd.put("in_jiqi", "2");
	  			 			   			ServiceHelper.getAppStoreService().saveWealthhistory(pd);
	  			 			   			pd=null;
	  		 			   				//新增总后台提现充值/记录
	  			 			   			PageData waterpd=new PageData();
	  				    				waterpd.put("pay_status","1");
	  				    	   			waterpd.put("waterrecord_id",store_wealthhistory_id);
	  				   					waterpd.put("user_id", user_id);
	  				   					waterpd.put("user_type", "1");
	  				    				waterpd.put("withdraw_rate","0");
	  				   					waterpd.put("money_type","12");
	  				   	 				waterpd.put("money", TongYong.df2.format(lessmoney));
	  				   	 				waterpd.put("reduce_money", "0");
	  				   					waterpd.put("arrivalmoney",  TongYong.df2.format(lessmoney));
	  				   					waterpd.put("nowuser_money",  ServiceHelper.getAppStoreService().sumStoreWealth(mmpd) );
	  				   					waterpd.put("application_channel", "2" );
	  				    				waterpd.put("remittance_name", "积分红包到时间，回馈到账号" );
	  				    				waterpd.put("remittance_type","7" );
	  			   						waterpd.put("integral_money", TongYong.df2.format(lessmoney) );
	  				   					waterpd.put("remittance_number",user_id);//支付人的支付账号
	  				    				waterpd.put("createtime",DateUtil.getTime());
	  				   					waterpd.put("over_time",DateUtil.getTime());
	  				   	  				waterpd.put("jiaoyi_type","6");
	  				   					waterpd.put("payee_number",user_id);
	  					    	 		waterpd.put("order_tn", store_wealthhistory_id);
	  				   					waterpd.put("province_name", mmpd.getString("province_name"));
	  				   					waterpd.put("city_name", mmpd.getString("city_name"));
	  				   					waterpd.put("area_name", mmpd.getString("area_name"));
	  				    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
	  				    				waterpd=null;
	  	 							}
	  	  						}else if(user_type != null && user_type.equals("2")){//会员
	  	  							mmpd.put("member_id", user_id);
	  	  							mmpd=ServiceHelper.getAppMemberService().findById(mmpd);
	  	 							if(mmpd != null){
	  	 								double lessmoney=(redpackage_money-overget_money);
	  	 								double now_integral=Double.parseDouble(mmpd.getString("now_integral"));
	  	 								mmpd.put("now_integral", TongYong.df2.format(now_integral+lessmoney));
	  	 								ServiceHelper.getAppMemberService().edit(mmpd);
	  	 								//新增财富历史记录
	  	 								String member_wealthhistory_id=BaseController.getXFUID(mmpd.getString("show_lookid"));
	  									PageData moneypd=new PageData();
	  									moneypd.put("member_id", user_id);
	  			 						moneypd.put("wealth_type", "1");
	  									moneypd.put("consume_type", "1");
	  									moneypd.put("content", "积分红包到时间，回馈到账号");
	  									moneypd.put("number", TongYong.df2.format(lessmoney));
	  									moneypd.put("now_money", TongYong.df2.format(now_integral+lessmoney));
	  									moneypd.put("jiaoyi_id", ms_redpackage_id);
	  									moneypd.put("in_jiqi", "1");
	  									moneypd.put("member_wealthhistory_id", member_wealthhistory_id);
	  									moneypd.put("jiaoyi_status", "1");
	  									ServiceHelper.getAppMember_wealthhistoryService().saveWealthhistory(moneypd); 
 	  									//新增总流水记录
	  					 	   			PageData waterpd=new PageData();
	  						   			waterpd.put("pay_status","1");
	  					   				waterpd.put("waterrecord_id",member_wealthhistory_id);
	  									waterpd.put("user_id", user_id);
	  									waterpd.put("user_type", "2");
	  									waterpd.put("withdraw_rate","0");
	  									waterpd.put("money_type","12");
	  									waterpd.put("money", TongYong.df2.format(lessmoney));
	  									waterpd.put("reduce_money", "0");
	  									waterpd.put("arrivalmoney", TongYong.df2.format(lessmoney));
	  									waterpd.put("nowuser_money",TongYong.df2.format(now_integral+lessmoney) );
	  									waterpd.put("application_channel","2" );
	  									waterpd.put("remittance_name","积分红包到时间，回馈到账号");
	  									waterpd.put("remittance_number",user_id);//支付人的支付账号
	  									waterpd.put("remittance_type","7" );
	  									waterpd.put("integral_money", TongYong.df2.format(redpackage_money-overget_money) );
	  					 				waterpd.put("createtime",DateUtil.getTime());
	  									waterpd.put("over_time",DateUtil.getTime());
	  					 				waterpd.put("jiaoyi_type","6");
	  									waterpd.put("payee_number",Const.jiuyunumber);
	  						 			waterpd.put("order_tn",  member_wealthhistory_id);
	  									waterpd.put("province_name", mmpd.getString("province_name"));
	  									waterpd.put("city_name", mmpd.getString("city_name"));
	  									waterpd.put("area_name", mmpd.getString("area_name"));
	  									ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
	  									waterpd=null;
	  	 							}
	   	 						}
	  	 					mmpd=null;
	 					}
	  		 		}
    		 	} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
    
	public static void main(String[] age){
		String n="003";
		System.out.println(Integer.parseInt(n)+1);
	}

}
