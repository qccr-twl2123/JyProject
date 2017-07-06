package com.tianer.listener;

 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.zhihui.MsgTask;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/**
 * 
* 类名称：PassTimerTest   
* 类描述：过期定时器   
* 创建人：魏汉文  
* 创建时间：2016年6月17日 下午1:39:44
 */
@Component
public class PassTimerTest {
	
	        private  static boolean flag=true;
 
			 /**
			  * 每天凌晨清空
			  * 魏汉文20160617
			  */
 			@Scheduled(cron = "1 0 0 * * *")
			 public void QingKong() {
 					System.err.println("每天凌晨清空********开始");
				    PageData pd=new PageData();
				    try{
//				    		jfredchuli(pd);
				    	    changestatus(pd);
							redtimepass(pd);
							cityfee(pd);
							daoliuTime(pd);
 							Const.numberphone=new HashMap<String,Integer>();//清空发送验证码次数
 							storerenmaishouyi(pd);//统计商家的人脉收益
 							tuisongChangeStatus(pd);//修改订单推送状态和会员红包的推送状态
    				}catch(Exception e){
				    	System.out.println("每天凌晨清空"+e.toString());
				    }
				    System.out.println("每天凌晨清空*******结束");
			 }
  			
 			
 			/**
 			 * 未支付的订单/赞/桌号
 			 * @param pd
 			 */
 			public synchronized void changestatus(PageData pd){
 				try {
 		    		//清空赞
		    	 	ServiceHelper.getAppStoreService().delete();
		    	 	//获取历史支付记录的状态为0的记录tb_waterrecord
				    ServiceHelper.getWaterRecordService().deleteStatusZero(pd);
				   //清空未支付成功的商家订单tb_store_wealthhistory
					ServiceHelper.getStorepc_wealthhistoryService().deleteZeroStoreWealthAll(pd);
					//获取所有一天后的订单为0的订单，删除商品
					List<PageData> goodsList=ServiceHelper.getAppOrderService().getStatusZero(pd);
					int length=goodsList.size();
		 			PageData e=null;
		 			for (int i = 0; i <length; i++) {
		 				e=goodsList.get(i);
						ServiceHelper.getAppOrderService().deleteStatusZero(e);
						ServiceHelper.getAppOrderService().deleteOrderGoods(e);
						e=null;
					}
		 			//清空没有订单关联的订单商品表
					List<PageData> notgoodsList=ServiceHelper.getAppOrderService().getAllOrderGoods(pd);
					int length2=notgoodsList.size();
 		 			for (int i = 0; i <length2; i++) {
		 				e=notgoodsList.get(i);
						if(ServiceHelper.getAppOrderService().findById(e) == null){
							ServiceHelper.getAppOrderService().deleteOrderGoods(e);
						}
						e=null;
					}
  					//清空操作员的桌号
					pd.put("alldesk_no", "");
					ServiceHelper.getStoreManageService().updatealldesk_no(pd);
					//清空关联表
					ServiceHelper.getAppOrderService().deleteguanlian(pd);
 					//删除一元夺宝的记录
					ServiceHelper.getOneYuanService().deleteoneyuanmember(pd);
 				} catch (Exception e) {
					// TODO: handle exception
 					System.out.println("未支付的订单/赞/桌号。。。"+e.toString());
 					(new TongYong()).dayinerro(e);
				}
// 				System.out.println("未支付的订单/赞/桌号。。。。。处理结束");
 			}
 			
 			/**
 			 *  超出商品限度的费用定时设置
 			 * @param pd
 			 */
 			public synchronized  void cityfee(PageData pd){
  				try {
  					//获取所有的城市营销的收费时间
  	 				 List<PageData> cityList=ServiceHelper.getCity_marketingService().getCitySevenList(pd);
  	 				//循环：1.设置定时器定时器传的参数为：省市区名称，以及金额
  	 				int length=cityList.size();
		 			PageData e=null;
		 			for (int i = 0; i <length; i++) {
		 				e=cityList.get(i);
  	 					Date date=new Date();
  	 					long l1=date.getTime();
  	 					if(e.get("billing_time") != null && !e.get("billing_time").equals("") && e.getString("billing_money") != null && !e.getString("billing_money").equals("")){
  	 						String time=e.get("billing_time").toString();
  	  	 					String nowday=DateUtil.getDay();
  	  	 					time=nowday+" "+time;
  	  	 					Date date2=DateUtil.fomatDate1(time);
  	  	 					long l2=date2.getTime();
   	  	 					if(l2-l1 >=0){
   	  	 						MsgTask mt=new MsgTask(e.getString("province_name"),e.getString("city_name"),e.getString("area_name"),e.getString("billing_money"));
  	  	 						Timer tt=new Timer();
  	  	 						tt.schedule(mt, l2-l1);
   	  	 					}
  	 					}
  	 					e=null;
  	 	 			}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("超出商品限度的费用定时设置"+e.toString());
					(new TongYong()).dayinerro(e);
				}
  				System.out.println("超出商品限度的费用定时设置处理结束");
 			}
 			
 			/**
 			 * 现金折扣红包时间到期的状态处理
 			 * @param pd
 			 */
 			public synchronized   void redtimepass(PageData pd){
 				try {
 					//清空到期的状态：.......
					ServiceHelper.getAppPcdService().edit(pd);
		    		//获取所有的会员过期红包减为三十个，更新会员的可用红包
		    		//1.获取所有会员
		    		List<PageData> memList=ServiceHelper.getAppMemberService().listAllMember(pd);
		    		int length=memList.size();
		 			PageData mpd=null;
		 			for (int i = 0; i <length; i++) {
 						mpd=memList.get(i);
						//更新用户的可用红包
						ServiceHelper.getAppMemberService().updateMemberRedNumber(mpd);
						//去掉过期的三十天外的红包
						mpd.put("isguoqi", "1");
						List<PageData> guoqiredList=ServiceHelper.getAppMember_redpacketsService().listRedId(mpd);
						int length2=guoqiredList.size();
						PageData guoqipd=null;
						for (int j = 0; j < length2; j++) {
							  guoqipd=guoqiredList.get(j);
							  if(j>=30){
								   ServiceHelper.getAppMember_redpacketsService().deleteGuoqiRed(guoqipd);//删除
							  }
							  guoqipd=null;
						}
						mpd=null;
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("红包时间到期的状态处理"+e.toString());
					(new TongYong()).dayinerro(e);
				}
// 				System.out.println("红包时间到期的状态处理结束");
 			}
 		 
 			
 			/**
 			 * 导流时间到期的处理
 			 * @param pd
 			 */
 			public synchronized  void daoliuTime(PageData pd){
 				try {
  					//1.更新所有时间过期的合作导流列表
 					ServiceHelper.getStoreDaoLiuService().updateDaoliurecordTime(pd);
 				} catch (Exception e) {
 					// TODO: handle exception
 					System.out.println("导流时间到期的处理"+e.toString());
 					(new TongYong()).dayinerro(e);
 				}
  			}
			 
// 			/**
// 			 * 积分红包到期处理
// 			 */
// 			public  void jfredchuli(PageData pd){
//  			    try{
// 				    	//查询所有状态未过期但是时间是已过期的积分红包
// 			    		List<PageData> passJfRedList=ServiceHelper.getAppPcdService().allPassTimeJfRed(pd);
// 						for(PageData e : passJfRedList){
//							if(e != null){
//								String ms_redpackage_id=e.getString("ms_redpackage_id");
//								//判断时间是否满足处理
//								Date now=new Date();
//								String createtime=e.get("createtime").toString();
// 								long n3=Const.jfguoqi;
//								long n1=DateUtil.fomatDate1(createtime).getTime()+n3;
//								long n2=now.getTime();
//								long n4=n1-n2;
// 								if(n4 < 0){
// 									n4 = 6000;
//								} 
// 								JFredTask jf=new JFredTask(ms_redpackage_id);
//	  	 						Timer tt=new Timer();
//	  	 						tt.schedule(jf, n4);
// 							}
// 		 				}
// 			    }catch(Exception e){
//			    	System.out.println("查询所有状态未过期并设置定时器"+e.toString());
//			    	(new TongYong()).dayinerro(e);
//			    }
////  			    System.out.println("查询所有状态未过期积分红包并设置定时器结束");
//  			}
// 			
 			
 			/**
 			 * 商家的人脉收益处理(凌晨统计)
 			 */
 			public synchronized  void storerenmaishouyi(PageData _pd){
  				try{ 
  	 				//查询所有状态未过期但是时间是已过期的积分红包
  					Calendar c = Calendar.getInstance();
  				    c.add(Calendar.DATE, -1);
  				    String prevmonth=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
  			        System.out.println("上一天是："+prevmonth);
  			        String now_datetime=DateUtil.getDay();
  			        List<PageData> allstore=ServiceHelper.getAppStoreService().listAllOkStore(_pd);
  			        int length=allstore.size();
		 			PageData e=null;
		 			PageData moneypd=null;
		 			PageData o1=null;
		 			for (int i = 0; i <length; i++) {
						e=allstore.get(i);
						moneypd=new PageData();
  			        	String store_id=e.getString("store_id");
  			        	PageData pd=new PageData();
  			        	pd.put("store_id", store_id);
  	 	 		        pd.put("time_day", prevmonth);
  	 	 		        pd.put("now_datetime", now_datetime);
  	 	 		        //判断是否已经统计了
  	 	 		        PageData nowpd=ServiceHelper.getAppStoreService().findByStoreRenMaiJf(pd);
  	 	 		        if(nowpd != null){
  	 	 		        	nowpd=null;
  	 	 		        	continue;
  	 	 		        }else{
	  	 	 		        pd.put("onecontacts_id", store_id);
	  		    	  		String firstcontact_money=ServiceHelper.getStorepc_wealthhistoryService().getContantSumMoneyTwo(pd).get("sumonecontacts_getmoney").toString();
	  		    	  		String firstsale_money=ServiceHelper.getStorepc_wealthhistoryService().getContantSumMoneyTwo(pd).get("sumsale_money").toString();
	  		    	  		List<PageData> firstallorder=ServiceHelper.getStorepc_wealthhistoryService().listallContantOrder(pd);
	  		    	  		pd.remove("onecontacts_id");
	  		     	  		pd.put("twocontacts_id", store_id);
	  		    	  		String twocontact_money=ServiceHelper.getStorepc_wealthhistoryService().getContantSumMoneyTwo(pd).get("sumtwocontacts_getmoney").toString();
	  		    	  		String twosale_money=ServiceHelper.getStorepc_wealthhistoryService().getContantSumMoneyTwo(pd).get("sumsale_money").toString();
	  		    	  		List<PageData> twoallorder=ServiceHelper.getStorepc_wealthhistoryService().listallContantOrder(pd);
	  		    	  		pd.remove("twocontacts_id");
	  		    	  		double oneday_renmaijifen=Double.parseDouble(firstcontact_money)+Double.parseDouble(twocontact_money);
	  		 		        double oneday_renmaimoney=Double.parseDouble(firstsale_money)+Double.parseDouble(twosale_money);
	  		    	  		if(oneday_renmaijifen >0){
	  		    	  		 //商家修改财务（减赠送积分） onecontacts_id  twocontacts_id
		  		 		        String nowmoney=ServiceHelper.getAppStoreService().sumStoreWealth(pd);
		  		 		        if(nowmoney == null){
		  		 		        	System.err.println(pd.toString());
		  		 		        }
 		  				   		moneypd.put("now_wealth", TongYong.df2.format(Double.parseDouble(nowmoney)+oneday_renmaijifen));
		  				   		moneypd.put("store_id", store_id);
		  				   		ServiceHelper.getAppStoreService().editWealthById(moneypd);
		  				   		//新增商家财务记录（减赠送积分）
		  				   		String store_wealthhistory_id="JL"+DateUtil.getDayshms()+store_id;
		  				   		moneypd=null;
		  	 					moneypd=new PageData();
		  	 					moneypd.put("wealth_type", "1");
		  	 					moneypd.put("profit_type", "17");
		  	 					moneypd.put("arrivalMoney", "");
		  	 					moneypd.put("number",   "");
		  	 					moneypd.put("store_id",store_id);
		  	 					moneypd.put("pay_type", "7");
		  	 					moneypd.put("store_operator_id", store_id);
		  	 					moneypd.put("process_status", "1");
		  	 					moneypd.put("in_jiqi", "");
		  	 					moneypd.put("jiaoyi_id","");
		  	 					moneypd.put("sp_money","");
		  	 					moneypd.put("user_id", "");
		  	 					moneypd.put("last_wealth", ServiceHelper.getAppStoreService().sumStoreWealth(pd));
		  	 					moneypd.put("store_wealthhistory_id",store_wealthhistory_id);
		  	 					moneypd.put("oneday_renmaijifen", TongYong.df2.format(oneday_renmaijifen ));
		  	 					moneypd.put("oneday_renmaimoney", TongYong.df2.format(oneday_renmaimoney ));
		  		 	   			ServiceHelper.getAppStoreService().saveWealthhistory(moneypd);
		  		 	   			moneypd=null;
		  		 	   			//新增tb_store_renmaijf  商家的人脉收益积分
		  		 	   			moneypd=null;
		  						moneypd=new PageData();
		  						moneypd.put("store_renmaijf_id", store_wealthhistory_id);
		  						moneypd.put("store_id", store_id);
		  						moneypd.put("firstcontact_money", firstcontact_money);
		  						moneypd.put("twocontact_money", twocontact_money);
		  						moneypd.put("allmoney", TongYong.df2.format(oneday_renmaijifen ));
		  						ServiceHelper.getAppStoreService().saveStoreRenMaiJf(moneypd);
		  						int length2=firstallorder.size();
 		  			 			for (int j = 0;j <length2; j++) {
		  			 				o1=firstallorder.get(i);
 		  							o1.put("store_renmaijf_id", store_wealthhistory_id);
		  							ServiceHelper.getAppStoreService().saveStoreRenMaiJf_order(o1);
		  							o1=null;
		  			 			}
 		  			 			int length3=twoallorder.size();
 		  			 			for (int j = 0;j <length3; j++) {
		  			 				o1=twoallorder.get(i);
 		  		 	   				o1.put("store_renmaijf_id", store_wealthhistory_id);
		  							ServiceHelper.getAppStoreService().saveStoreRenMaiJf_order(o1);
		  							o1=null;
		  			 			}
 	  		    	  		}
   	 	 		        }
   						pd=null;
   						e=null;
   						moneypd=null;
  					}
  	 		 }catch(Exception e){
 					System.out.println("商家的人脉收益处理(凌晨统计)"+e.toString());
 					(new TongYong()).dayinerro(e);
 			}
//  			System.out.println("商家的人脉收益处理(凌晨统计)结束");
 		}
 			
 			
 			
 			
 			
 			 /**
			  * 每天早上10点处理推送
			  * 魏汉文20160617
			  */
			@Scheduled(cron = "0 0 10 * * *")
			 public synchronized void tuisong() {
//					System.err.println("每天早上10点处理推送进来了**************************************************");
				    PageData pd=new PageData();
				    try{
						//红包三天未使用
				    	List<PageData> redthreelist=ServiceHelper.getAppPcdService().countNotUserRed1(pd);
				    	for (PageData e : redthreelist) {
							TongYong.sendTuiSong(e.getString("member_id"),"", "4", e.getString("member_id"), "2",  e.get("n").toString(), "");
						}
				    	//红包还有一天到期
				    	List<PageData> redonelist=ServiceHelper.getAppPcdService().countNotUserRed2(pd);
				    	for (PageData e : redonelist) {
							TongYong.sendTuiSong(e.getString("member_id"),"", "5", e.getString("member_id"), "2",  e.get("n").toString(), "");
						}
				    	//提货券四天未领取
				    	List<PageData> orderfourlist=ServiceHelper.getAppPcdService().countNotUserOrder(pd);
				    	for (PageData e : orderfourlist) {
							TongYong.sendTuiSong(e.getString("member_id"),"", "6", e.getString("member_id"), "2",  e.get("n").toString(), e.getString("enddate"));
						}
 				    	
 				    }catch(Exception e){
				    	System.out.println("每天早上10点"+e.toString());
				    	(new TongYong()).dayinerro(e);
				    }
//				    System.err.println("每天早上10点处理推送**************************************************结束");
			 }
			
			
			 /**
			  * 修改推送的状态
			  * 订单以及会员红包
			  */
 			 public void tuisongChangeStatus(PageData pd) {
					System.err.println("修改推送的状态**************************************************");
 				    try{
						ServiceHelper.getAppPcdService().ChangeTuiSongSatatus(pd);
 				    }catch(Exception e){
				    	System.out.println("修改推送的状态/订单以及会员红包"+e.toString());
				    	(new TongYong()).dayinerro(e);
				    }
 			 }
 			 
  			
 			public static void main(String[] args) {
 				(new PassTimerTest()).redtimepass((new PageData()));
			}
 			
  }
 
