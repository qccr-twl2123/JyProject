package com.tianer.entity.zhihui;

import java.text.DecimalFormat;
import java.util.List;
import java.util.TimerTask;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
 
/**
 * 
* 类名称：MsgTask   
* 类描述：   城市营销详情的城市营销超出商品限度的费用表
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class MsgTask extends TimerTask{
 
 	private String province_name;//省
	private String city_name;//市
	private String area_name;//区
	private String money;// 金额
 	
	public MsgTask(){
		
	}
	
  	
	public MsgTask(String province_name, String city_name, String area_name, String money ) {
		super();
		this.province_name = province_name;
		this.city_name = city_name;
		this.area_name = area_name;
		this.money = money;
 	}


	@Override
	public void run() {
			//在定时器到时间的时候：1.获取在当前省市区的所有商家，2，判断商品是否超出，超出的按每天收费收取，更新商家的财富
 		    DecimalFormat    df2   = TongYong.df2; 
		 	PageData pd=new PageData();
 		 	//获取当前城市下的所有商家
		 	try {
		 		synchronized ( this ) { 
		 			pd.put("province_name", province_name);
				 	pd.put("city_name", city_name);
				 	pd.put("area_name", area_name);
					List<PageData> storeList=ServiceHelper.getStore_fileService().listStoreForName(pd);
					for(PageData e : storeList ){
						   String wealthnumber=e.get("wealthnumber").toString();
						   if(!wealthnumber.equals("0")){
							    continue;
						   }
						   System.out.println(e.getString("store_name")+"超过设定的商品数量============扣钱");
						   String number=e.get("goodsnumber").toString();
 						   String goods_max=e.getString("goods_max");
 						   String store_id=e.getString("store_id");
 						   String store_name=e.getString("store_name");
						   if(Double.parseDouble(number) > Double.parseDouble(goods_max)){//如果超过设定的商品数量，扣钱
	  								double now_wealth1=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(e));
									double last_money=now_wealth1-Double.parseDouble(money);
	  								pd.put("now_wealth", df2.format(last_money));
									ServiceHelper.getAppStoreService().editWealthById(pd);
									//发送短信至商家
									SmsUtil.sendFee(e.getString("registertel_phone"), e.getString("store_name"), money);
									//新增商家扣费记录
 									String store_wealthhistory_id=BaseController.getTXUID(store_id);
									pd.put("store_wealthhistory_id", store_wealthhistory_id);
									pd.put("jiaoyi_id", store_wealthhistory_id);
			 			   			pd.put("wealth_type", "1");
		 			   				pd.put("profit_type", "18");
		 			   				pd.put("number","-"+money);
		 			   				pd.put("store_id", store_id);
		 			   				pd.put("pay_type", "7");
 		 			   				pd.put("store_operator_id", "jy"+store_id);
		 			   				pd.put("process_status", "0");
  			 			   			pd.put("last_wealth", df2.format(last_money));
			 			   			pd.put("arrivalMoney", "-"+money);//实际到账金额
 			 			   			pd.put("user_id", Const.jiuyunumber);
 		 			   				ServiceHelper.getAppStoreService().saveWealthhistory(pd);
		 			   				//新增总后台提现充值/记录
			 			   			PageData waterpd=new PageData();
				    				waterpd.put("pay_status","1");
				    	   			waterpd.put("waterrecord_id",store_wealthhistory_id);
				   					waterpd.put("user_id",store_id);
				   					waterpd.put("user_type", "1");
				    				waterpd.put("withdraw_rate","0");
				   					waterpd.put("money_type","18");
				   	 				waterpd.put("money", "-"+money);
				   	 				waterpd.put("reduce_money", "0");
				   					waterpd.put("arrivalmoney",  "-"+money);
				   					waterpd.put("nowuser_money",df2.format(last_money) );
				   					waterpd.put("application_channel","7" );
				    				waterpd.put("remittance_name",store_name+"当前商品"+number+"超过设定的商品"+goods_max+"数量扣钱" );
				    				waterpd.put("remittance_type","7" );
				   					waterpd.put("remittance_number",store_id);//支付人的支付账号
				    				waterpd.put("createtime",DateUtil.getTime());
				   					waterpd.put("over_time",DateUtil.getTime());
				   	  				waterpd.put("jiaoyi_type","0");
				   					waterpd.put("payee_number",Const.jiuyunumber);
 				    	 			waterpd.put("order_tn", pd.getString("order_tn"));
				   					waterpd.put("province_name", e.getString("province_name"));
				   					waterpd.put("city_name", e.getString("city_name"));
				   					waterpd.put("area_name", e.getString("area_name"));
				    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
				    				waterpd=null;
 						   }
	 				}
		 		}
 			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	public static void main(String[] age){
		MsgTask m=new MsgTask("浙江省","杭州市","西湖区","55");
		m.run();
	}

}
