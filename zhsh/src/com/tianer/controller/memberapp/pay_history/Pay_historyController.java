package com.tianer.controller.memberapp.pay_history;


import java.io.BufferedReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.tianer.controller.base.BaseController;
 import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.controller.youxuan.YouXuanController;
import com.tianer.controller.zhihui.payMoney.ChargeExample;
import com.tianer.entity.html.HtmlUser;
import com.tianer.entity.zhihui.OrderShop;
import com.tianer.entity.zhihui.StoreFeeTihuoTask;
import com.tianer.entity.zhihui.TihuoTask;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.business.store.StoreService;
import com.tianer.service.business.store_file.Store_fileService;
import com.tianer.service.business.subsidiary.SubsidiaryService;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.storeapp.Payapp_historyService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.store_discountway.Storepc_discountwayService;
import com.tianer.service.storepc.store_marketingeffect.Storepc_marketingeffectService;
import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
import com.tianer.service.storepc.store_redpackets.Storepc_redpacketsService;
import com.tianer.service.storepc.store_scoreway.Storepc_scorewayService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.StringUtil;

/** 
 * 
* 类名称：Pay_historyController   
* 类描述：   支付记录登记
* 创建人：魏汉文  
* 创建时间：2016年7月5日 下午6:13:42
 */
@Controller("appPay_historyController")
@RequestMapping(value="/app_pay_history")
public class Pay_historyController extends BaseController {
	
	@Resource(name="storeService")
	private StoreService storeService;
	@Resource(name="store_fileService")
	private Store_fileService store_fileService;
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	@Resource(name="appGoodsService")
	private AppGoodsService appGoodsService;
	@Resource(name="payapp_historyService")
	private Payapp_historyService payapp_historyService;
	
	
 	public DecimalFormat df2=TongYong.df2;
	
	
   	/**
   	 * ping++订单交易支付接口
   	* 方法名称:：thirdPartyPay 
   	* 方法描述：订单支付接口
   	* 创建人：魏汉文-- B端和公众号支付时需要
   	* 创建时间：2016年7月4日 下午2:11:35
   	* 
   	* app_pay_history/thirdPartyPay.do
   	* 
   	 */
	@RequestMapping(value="/thirdPartyPay")
	@ResponseBody
	public synchronized  Object thirdPartyPay(HttpServletRequest request) throws Exception{
  					Map<String, Object> map = new HashMap<String, Object>();
					Map<String, Object> map1 = new HashMap<String, Object>();
 			  		String result="1";
					String message="支付成功";
  					PageData pd=new PageData();
					try{
 						pd = this.getPageData();
 						//判断是否为H5页面
 						if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
 							pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
    						}
						String in_jiqi=pd.getString("in_jiqi");
 						if(in_jiqi == null || in_jiqi.equals("")){
 							in_jiqi="1";
 							pd.put("in_jiqi", "1");
 						}
    					String ip=getIp(request);//当前用户所在IP地址
						String order_id=BaseController.getTimeID();//支付历史记录
 						map1.put("order_id", order_id);
						//新增订单信息tb_order
 						pd.put("order_id", order_id);
						pd.put("look_number", order_id);
						PageData mpd=appMemberService.findById(pd);//用户详情
						PageData spd=appStoreService.findById(pd);
						// 处理订单
						PageData orderpd=TongYong.chuliOrder(pd,mpd,spd);
						if(orderpd.getString("result").equals("0")){
							ServiceHelper.getAppPcdService().saveLog(order_id, "订单支付出错","10");
							map.put("result", orderpd.getString("result"));
							map.put("message", orderpd.getString("message"));
						    map.put("data", "");
  					    	return map;
						}else{
							pd=(PageData) orderpd.get("data");
						}
						String url=pd.getString("url");//跳转地址
			 			String wxopen_id=mpd.getString("wxopen_id");//微信支付的专用openid
  						String pay_way=pd.getString("pay_way");
 						String pay_type=pd.getString("pay_type");
    					double actual_money=Double.parseDouble(pd.getString("actual_money"));
   						String _money=TongYong.df2.format(actual_money );
   						map1.put("order_id", order_id);
 						if(pay_type.equals("1")){
							System.out.println("暂无现金支付");
						}else if(pay_type.equals("2")){
 							 if(actual_money == 0){
  								    map1.put("charge", "");
 							}else{
 									Charge charge =null;
 									if(in_jiqi.equals("1")){
 										 System.out.println("C端优惠买单支付....");
 										 charge = ChargeExample.getPay(order_id, Double.parseDouble(_money)*100,ip,pay_way,"13","消费");
 									}else if(in_jiqi.equals("5")){
 										 System.out.println("公众号端优惠买单支付....");
 										if(pay_way.equals("wx_pub")){
 											charge = ChargeExample.getPayThree(order_id, Double.parseDouble(_money)*100,ip,pay_way,"13","消费",wxopen_id);
 										}else if(pay_way.equals("alipay_wap")){
 											charge = ChargeExample.getPayTwo(order_id, Double.parseDouble(_money)*100,ip,pay_way,"13","消费",url);
 										}
 									}
 									if(charge == null ){
		 								map.put("result", "0");
										map.put("message", "支付失败，charge出错");
										map.put("data", "");
								    	return map;
									}
									map1.put("charge", charge);
  							}
 						}else if(pay_type.equals("3")){
  							map.put("tihuo_id", pd.getString("tihuo_id"));
 							if(actual_money == 0){
								 map1.put("charge", "");
 							}else{
   									Charge charge =null;
									if(in_jiqi.equals("1")){
										System.out.println("C端提货卷支付....");
										charge = ChargeExample.getPay(order_id, Double.parseDouble(_money)*100,ip,pay_way,"12","消费");
									}else if(in_jiqi.equals("5")){
										System.out.println("公众号端提货卷支付....");
										if(pay_way.equals("wx_pub")){
											charge = ChargeExample.getPayThree(order_id, Double.parseDouble(_money)*100,ip,pay_way,"12","消费",wxopen_id);
										}else if(pay_way.equals("alipay_wap")){
											charge = ChargeExample.getPayTwo(order_id, Double.parseDouble(_money)*100,ip,pay_way,"12","消费",url);
										}
									}
  								if(charge == null ){
	 								map.put("result", "0");
									map.put("message", "支付失败，charge出错");
									map.put("data", "");
							    	return map;
								}
								map1.put("charge", charge);
 							}
 							map1.put("id", pd.getString("id"));
 						}
   			System.out.println(order_id+"生成订单结束=======");
 		}catch(Exception e){
			result="0";
			message="系统异常";
			map.put("data", e.toString());
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
    	return map;
 	}

 	
 	/**
 	 * 
 	* 方法名称:：thirdPartyCz 
 	* 方法描述：会员充值
 	* 创建人：魏汉文
 	* 创建时间：2016年7月6日 上午10:13:52
 	 */
	@RequestMapping(value="/thirdPartyCz")
	@ResponseBody
	public Object thirdPartyCz(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
  		String result="1";
		String message="充值确认中";
		// type代表支付方式
		PageData pd=new PageData();
		try{
 			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			String in_jiqi=pd.getString("in_jiqi");
			if(in_jiqi == null || in_jiqi.equals("")){
				in_jiqi="1";
				pd.put("in_jiqi", "1");
			}
			PageData mpd=new PageData();
 			mpd=appMemberService.findById(pd);//用户详情
 			if(mpd == null){
 				map.put("result", "0");
 				map.put("message", "用户id不能为空");
 				map.put("data", "");
 		    	return map;
 			}
 			String waterrecord_id=BaseController.getCZUID(mpd.getString("show_lookid"));
 			String openid=mpd.getString("wxopen_id");//微信支付的专用openid
			String pay_way=pd.getString("pay_way");//支付方式
			String url=pd.getString("url");//跳转地址
			String money=pd.getString("money");//当前金钱
			String ip=getIp(request);//当前用户所在IP地址
			if(money == null || money.equals("")){
 				map.put("result", "0");
 				map.put("message", "money不能为空");
 				map.put("data", "");
 		    	return map;
 			}
			//判断金钱是否符合
			if(!StringUtil.checkMoney(money) ){
				map.put("result", "0");
				map.put("message", "金钱格式有误");
				map.put("data", "");
		    	return map;
			}
			System.out .println("新增充值记录waterrecord_id========================");
			PageData waterpd=new PageData();
			waterpd.put("pay_status","0");
   			waterpd.put("waterrecord_id",waterrecord_id);
			waterpd.put("user_id", mpd.getString("member_id"));
			waterpd.put("user_type", "2");
			waterpd.put("withdraw_rate","0");
			waterpd.put("money_type","1");
			waterpd.put("money", money);
			waterpd.put("reduce_money","0");
			waterpd.put("arrivalmoney",  money);
			waterpd.put("nowuser_money","0");
			waterpd.put("application_channel",in_jiqi);
			if(pay_way.contains("wx")){
				waterpd.put("remittance_name",Const.payjiqi[4] );
				waterpd.put("remittance_type","4" );
				waterpd.put("wx_money",  money );
			}else if(pay_way.contains("alipay")){
				waterpd.put("remittance_name", Const.payjiqi[3]);
				waterpd.put("remittance_type","3" );
				waterpd.put("alipay_money",  money );
			}else{
				waterpd.put("remittance_name", Const.payjiqi[3]);
				waterpd.put("remittance_type","1" );
				waterpd.put("bank_money",  money );
			}
			waterpd.put("remittance_number",mpd.getString("phone"));//支付人的支付账号
			waterpd.put("createtime",DateUtil.getTime());
			waterpd.put("over_time",DateUtil.getTime());
 			waterpd.put("jiaoyi_type","0");
			waterpd.put("payee_number",Const.jiuyunumber);
	 		waterpd.put("order_tn", waterrecord_id );
			waterpd.put("province_name", mpd.getString("province_name"));
			waterpd.put("city_name", mpd.getString("city_name"));
			waterpd.put("area_name", mpd.getString("area_name"));
			ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
			waterpd=null;
			/*
		 * 支付方式pay_type:
		 * alipay:支付宝手机支付
			alipay_wap:支付宝手机网页支付
			alipay_qr:支付宝扫码支付
			alipay_pc_direct:支付宝 PC 网页支付
			bfb:百度钱包移动快捷支付
			bfb_wap:百度钱包手机网页支付
			upacp:银联全渠道支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
			upacp_wap:银联全渠道手机网页支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
			upacp_pc:银联 PC 网页支付
			cp_b2b:银联企业网银支付
			wx:微信支付
			wx_pub:微信公众账号支付
			wx_pub_qr:微信公众账号扫码支付
			yeepay_wap:易宝手机网页支付
			jdpay_wap:京东手机网页支付
			cnp_u:应用内快捷支付（银联）
			cnp_f:应用内快捷支付（外卡）
			applepay_upacp:Apple Pay
			fqlpay_wap:分期乐支付
			qgbc_wap:量化派支付
			cmb_wallet:招行一网通
		 */
		//2.获取charge对象
			Charge charge = null;
			if(in_jiqi.equals("1")){
				charge = ChargeExample.getPay(waterrecord_id, Double.parseDouble(money)*100,ip,pay_way,"11","消费");
			}else if(in_jiqi.equals("5")){
				if(pay_way.equals("wx_pub")){
					charge = ChargeExample.getPayThree(waterrecord_id, Double.parseDouble(money)*100,ip,pay_way,"11","消费",openid);
				}else if(pay_way.equals("alipay_wap")){
					charge = ChargeExample.getPayTwo(waterrecord_id, Double.parseDouble(money)*100,ip,pay_way,"11","消费",url);
				}
			}
			if(charge == null ){
				result="0";
				message="支付失败，charge出错";
				map.put("data", "");
			}else{
				map.put("data", charge);
			}
 		}catch(Exception e){
			result="0";
			message="系统异常";
			map.put("data", e.toString());
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
    	return map;
	}
 
	
	@Resource(name="appOrderService")
	private AppOrderService appOrderService;
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService appMember_wealthhistoryService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService appMember_redpacketsService;
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService appStore_redpacketsService;
	
	/**
	 * 
	* 方法名称:：cashPay 
	* 方法描述：现金支付
	* 创建人：魏汉文
	* 创建时间：2016年7月6日 上午10:12:06
	 */
	@RequestMapping(value="/cashPay")
	@ResponseBody
	public Object cashPay() throws Exception{
//		logBefore(logger, "现金支付");
		Map<String,Object> map = new HashMap<String,Object>();
  		String result = "1";
		String message="支付成功";
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
  				PageData mpd=new PageData();
	 			mpd=appMemberService.findById(pd);//用户详情
	 			if(mpd == null){
		 				map.put("result", "0");
		 				map.put("message", "用户id不能为空");
		 				map.put("data", "");
		 		    	return map;
	 			}
//	 			String pay_way=pd.getString("pay_way");//支付方式
//				String allgoodsid=pd.getString("allgoodsid");//所有订单里的商品
				String sale_money=pd.getString("sale_money");//订单金额
//	 			String actual_money=pd.getString("actual_money");//支付的金额（现金或者第三方）
				String user_balance=pd.getString("user_balance");//使用的余额
				String user_integral=pd.getString("user_integral");//使用的积分
				String discount_money=pd.getString("discount_money"); 
				String no_discount_money=pd.getString("no_discount_money"); 
				String get_integral=pd.getString("get_integral");//使用的积分
				//判断商家积分是否充足
				if(sale_money.equals("")){
					sale_money="0";
					pd.put("sale_money", sale_money);
				}
				if(get_integral.equals("")){
					get_integral="0";
					pd.put("get_integral", get_integral);
				}
				if(user_integral.equals("")){
					user_integral="0";
					pd.put("user_integral", user_integral);
				}
				if(user_balance.equals("")){
					user_balance="0";
					pd.put("user_balance", user_balance);
				}
				if(user_balance.equals("")){
					user_balance="0";
					pd.put("user_balance", user_balance);
				}
				if(discount_money.equals("")){
					discount_money="0";
					pd.put("discount_money", discount_money);
				}
				if(no_discount_money.equals("")){
					no_discount_money="0";
					pd.put("no_discount_money", no_discount_money);
				}
 	 			System.out.println("判断金钱是否符合");
				//判断金钱是否符合
				if(!StringUtil.checkMoney(sale_money) || !StringUtil.checkMoney(user_balance) || !StringUtil.checkMoney(user_integral) ){
						map.put("result", "0");
						map.put("message", "金钱格式有误"+user_balance+"&"+user_integral);
						map.put("data", "");
				    	return map;
				}
				//判断支付金额是否大于赠送积分
				System.out.println("==============================判断支付金额是否大于赠送积分");
				if(Double.parseDouble(sale_money) < Double.parseDouble(get_integral)){
						map.put("result", "0");
						map.put("message", "支付金额不能小于"+get_integral);
						map.put("data", "");
				    	return map;
				}
				System.out.println("==============================判断余额是否充足");
	 			//判断余额
				if(!user_balance.equals("")){
	   				double now_money=Double.parseDouble(mpd.getString("now_money"));
					double n=Double.parseDouble(user_balance);
	 				if(now_money < n){
 							map.put("result", "0");
							map.put("message", "余额不足，当前余额"+now_money);
							map.put("data", "");
					    	return map;
					} 
	 			}
				System.out.println("==============================判断积分是否充足");
				//判断积分
				if(!user_integral.equals("")){
	   				double now_integral=Double.parseDouble(mpd.getString("now_integral"));
	 				double m=Double.parseDouble(user_integral);
					if(now_integral < m){
 							    map.put("result", "0");
								map.put("message", "积分不足，当前积分"+now_integral);
								map.put("data", "");
						    	return map;
					} 
	 			}
				//判断商家的积分是否充足
				double storeintegral=Double.parseDouble(user_integral)-Double.parseDouble(get_integral)-Double.parseDouble(get_integral)*Const.orderShouyiMoney[0];
				PageData spd=appStoreService.findById(pd);
				spd.put("wealth_type", "1");//1-积分，2-金钱
				PageData 	ismoneypd=appStoreService.findWealthById(spd);
				double isnow_wealth=Double.parseDouble(ismoneypd.getString("now_wealth"));
				if(isnow_wealth+storeintegral  < 0){
						map.put("result", "0");
						map.put("message", "商家积分余额不足，请商家充值后再购买");
						map.put("data", "");
				    	return map;
				}
 				//分配操作员
				String desk_no=pd.getString("desk_no");
					List<PageData> varList = storeManageService.listAll(pd);
				for (int i = 0; i < varList.size(); i++) {
					   PageData e= varList.get(i);
					   if(desk_no != null){
						   if(e.getString("alldesk_no") != null && e.getString("alldesk_no").contains(desk_no)){
							   pd.put("store_operator_id", e.getString("store_operator_id"));
							   pd.put("store_shift_id", e.getString("store_shift_id"));
							   break;
						   }
					   }
					   e=null;
				}
				if(pd.getString("store_operator_id").equals("")){
					 pd.put("store_operator_id", "jy"+spd.getString("store_id"));
				}
				//新增订单信息tb_order
				String order_id=BaseController.getTimeID(); 
 				pd.put("order_id", order_id);
				pd.put("look_number", order_id);
				pd.put("order_status", "2");//待确认
				pd.put("tihuo_status", "1");//待确认
 				appOrderService.saveOrder(pd);
				System.out.println("生成订单结束=================over");
 		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error("++++++++++++++++++"+e.toString(), e);
			System.out.println("++++++++++++++++++"+e.toString());
		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
  		return map;
	}
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	@Resource(name="clerk_fileService")
	private Clerk_fileService clerk_fileService;
 	@Resource(name="subsidiaryService")
	private SubsidiaryService subsidiaryService;
 
	
	/**
	 * 第三方支付或者充值回调
	* 方法名称:：payBackWay 
	* 方法描述：
	* 创建人：魏汉文
	* 创建时间：2016年7月5日 下午6:30:51
	 */
		@RequestMapping(value = "payBackWay")
		@ResponseBody
 		public synchronized void payBackWay(HttpServletRequest request, HttpServletResponse response) throws Exception {
				 System.out.println("Ping++进来回调了=======================>>>>>>>");
				 request.setCharacterEncoding("UTF8");
  				 PageData pd=new PageData();
 				 try{
 		 		        // 获得 http body 内容
		 		        BufferedReader reader = request.getReader();
				        StringBuffer buffer = new StringBuffer();
				        String string;
			 	        while ((string = reader.readLine()) != null) {
				            buffer.append(string);
				        }
				        reader.close();
			  	        // 解析异步通知数据
		 		        Event event = Webhooks.eventParse(buffer.toString());
		 		        if(event != null){
		 		        	 ServiceHelper.getAppPcdService().saveLog(event.toString(), "回调接口参数","00");
		 		        }
 			 	        if (event.getType() != null && !event.getType().equals("" ) && "charge.succeeded".equals(event.getType())) { //支付成功
//								System.out.println("进入支付成功=============>>>>>>>>>>>>>>>>>"+event.toString());
								JSONObject s=new JSONObject(buffer.toString());
		 						JSONObject obj=s.optJSONObject("data");
								JSONObject jv=obj.optJSONObject("object");
								String body=jv.optString("body"); 
 								String orderno=jv.optString("order_no");//支付记录号
								String channel=jv.optString("channel");//支付方式
								String trade=jv.optString("transaction_no");//流水单号
						 		Double price=jv.optDouble("amount");//支付金额（消费/充值金额）
 					 	 		//body:
//						 		System.out.println("回调进来的信息================="+jv.toString());
						 		if(body.equals("11")){//会员充值
 	 		 			            //新增用户余额充值记录
 	 		 			            PageData waterpd=new PageData();
			  	 			        waterpd.put("waterrecord_id", orderno);
	  		  	 			        waterpd=ServiceHelper.getWaterRecordService().findByIdWaterRecord(waterpd);
 		 		 			        PageData mpd=new PageData();
	 		 			            mpd.put("member_id", waterpd.getString("user_id"));
		 			 			    mpd=appMemberService.findById(mpd);//用户详情
 			 		 			    double now_money=Double.parseDouble(mpd.getString("now_money"));
			 		 			  	double m=price/100;
 			 		 			  	PageData moneypd=new PageData();
 		 			  				moneypd.put("member_id", mpd.getString("member_id"));
 		 			   				moneypd.put("wealth_type", "2");//1-积分，2-金钱
 		 			  				moneypd.put("consume_type", "1");//1-收益，2-支出
 		 			  				moneypd.put("content", "充值");
 		 			  				moneypd.put("number", df2.format(m));
 		 			  				moneypd.put("now_money", df2.format(now_money+m));
	 		 			  			moneypd.put("jiaoyi_id", orderno);
	 		 			  			moneypd.put("jiaoyi_status", "1");
	 		 			  			moneypd.put("member_wealthhistory_id", orderno);
	 		 			  			appMember_wealthhistoryService.saveWealthhistory(moneypd); 
//	 		 			  			System.out .println("更新会员充值次数=========================");
	 		 			  			int cz_number=Integer.parseInt(mpd.getString("cz_number"))+1;
	 		 			  			moneypd.put("cz_number", cz_number);
 		 			  				appMemberService.edit(moneypd);//更新金钱
	 		 			  			if(cz_number == 1){
//					 		 			System.out.println("新增会员的魅力值=====================================");
					 		 			TongYong.charm_numberAdd(mpd.getString("member_id"), Const.charm_number[9]); 
 		 			  				}
	 		 			  			//更新支付信息
  		 			  				waterpd.put("pay_status", "1");
 		 			  				waterpd.put("order_tn", trade);
	 		 			  			ServiceHelper.getWaterRecordService().editWaterRecord(waterpd);
	 		 			  			System.out .println("会员充值结束=========================");
 						 		}else if(body.equals("12") || body.equals("13") ){//12-购买提货券商品，13-优惠买单支付
 	 	 			            	pd.put("order_id", orderno);
	 			            		pd=appOrderService.findById(pd);//订单详情
	 			            		System.out .println("=================回调开始获取订单信息=============>>>>>>>订单号="+orderno);
	 			            		if(pd != null && !pd.getString("order_status").equals("1")){
  					 			            pd.put("member_id", pd.getString("payer_id"));
			 	 			            	pd.put("store_id", pd.getString("store_id"));
				 			            	PageData mpd=new PageData();
					 			       		mpd=appMemberService.findById(pd);//用户详情
						 			       	PageData spd=new PageData();
					 			       		spd=appStoreService.findById(pd);//商家详情
					 			       		if(mpd == null || spd == null){
						 			       			  return;
					 			       		}
					 			       		if(body.equals("12")){//提货卷
					 			       			System.out.println("进来提货券回============================");
					 			       			//修改订单状态
 						 			   			pd.put("order_status", "1");
				 			    				pd.put("channel", channel);
 				 			    				pd.put("order_tn", trade);
					 			   				appOrderService.editStatus(pd);
					 			       			//新增会员历史记录以及红包状态以及总后台记录
					 			   				pd.put("pay_way", channel);
												TongYong.saveMemberHistory(pd, mpd, spd);
 						 	    				//发送推送
											    TongYong.sendTuiSong(pd.getString("store_id"), orderno, "3", pd.getString("store_id"), "1", pd.getString("sale_money"),"");
	 						 			    }else if(body.equals("13")){ 
							 			       		System.err.println("进入优惠买单回调===========================");
						 			       			pd.put("member_id", pd.getString("payer_id"));
						 			       			pd.put("order_tn", trade);
						 			       			TongYong.historyByOrder(pd, channel, "2","b",true);
							 			       		if(pd.getString("store_operator_id").contains("jy")){
							 			       			//发送推送
													    TongYong.sendTuiSong(pd.getString("store_id"), orderno, "2", pd.getString("store_id"), "1", pd.getString("sale_money"),"");
													}else{
														//发送推送
													    TongYong.sendTuiSong(pd.getString("store_operator_id"), orderno, "2", pd.getString("store_operator_id"), "11", pd.getString("sale_money"),"");
													}
	  						 			    }
		 			            	}else{
		 			            		logger.info("购买的订单不存在"+orderno);
		 			            	}
  						 		}else if(body.equals("14")){
  						 			//优选提货券
   						 			TongYong.youxuanOkOrder(orderno, trade);
     						 	}else if(body.equals("15")){
     						 		//一元夺宝
 			 			       		pd.put("oneyuanmember_id", orderno);
	 			            		pd=ServiceHelper.getOneYuanService().findMemberOrderById(pd);
	 			            		if(pd != null){
	 			            			//新增平台记录
					 	    				PageData waterpd=new PageData();
					 	    				waterpd.put("pay_status","1");
					 	    	   			waterpd.put("waterrecord_id", orderno);
					 	   					waterpd.put("user_id", pd.getString("payer_id"));
					 	   					waterpd.put("user_type", "2");
					 	    				waterpd.put("withdraw_rate","0");
					 	   					waterpd.put("money_type","2");
					 	   	 				waterpd.put("money", pd.getString("allpay_money"));
					 	   	 				waterpd.put("reduce_money","0");
					 	   					waterpd.put("arrivalmoney", pd.getString("allpay_money"));
					 	   					waterpd.put("nowuser_money", appMemberService.findById(pd).getString("now_money"));
					 	    				waterpd.put("application_channel", pd.getString("in_jiqi") ); 
					 	   					if(pd.getString("channel").contains("alipay")){
					 	   						waterpd.put("remittance_type","3" );
					 	    	 				waterpd.put("alipay_money",pd.getString("allpay_money") );
					 	    	 				waterpd.put("remittance_name",Const.payjiqi[3] );
					 	   					}else if(pd.getString("channel").contains("wx")){
					 	   						waterpd.put("remittance_type","4" );
					 	   						waterpd.put("wx_money",pd.getString("allpay_money") );
					 	   						waterpd.put("remittance_name",Const.payjiqi[4] );
					 	   					}else if(pd.getString("channel").contains("nowpay")){
					 	   						waterpd.put("remittance_type","2" );
					 	   						waterpd.put("nowypay_money",pd.getString("allpay_money") );
					 	   						if(pd.getString("in_jiqi").equals("1")){
					 	   							waterpd.put("remittance_name",Const.payjiqi[0] );
					 	   						}else if(pd.getString("in_jiqi").equals("4")){
					 	   							waterpd.put("remittance_name",Const.payjiqi[6] );
					 	   						}else if(pd.getString("in_jiqi").equals("2")){
					 	   							waterpd.put("remittance_name",Const.payjiqi[2] );
					 	   						}
					 	   					}else if(pd.getString("channel").contains("pple")){
					 	   						waterpd.put("remittance_type","5" );
					 	   						waterpd.put("apple_money",pd.getString("allpay_money") );
					 	   						waterpd.put("remittance_name",Const.payjiqi[5] );
					 	   					}else{
					 	   						waterpd.put("remittance_type","5" );
					 	   						waterpd.put("bank_money",pd.getString("allpay_money"));
					 	    					waterpd.put("remittance_name",Const.payjiqi[1] );
					 	   					}
						 	   				waterpd.put("integral_money",pd.getString("user_integral")); 
					 	   					waterpd.put("balance_money",pd.getString("user_balance")); 
					 	   					waterpd.put("remittance_number",appMemberService.findById(pd).getString("phone"));//支付人的支付账号
					 	    				waterpd.put("createtime",DateUtil.getTime());
					 	   					waterpd.put("over_time",DateUtil.getTime());
					 	   					waterpd.put("jiaoyi_type","4");
					 	    				waterpd.put("payee_number","Jiuyu");
					 	     	 			waterpd.put("order_tn", trade);
					 	   					waterpd.put("province_name", appMemberService.findById(pd).getString("province_name"));
					 	   					waterpd.put("city_name", appMemberService.findById(pd).getString("city_name"));
					 	   					waterpd.put("area_name", appMemberService.findById(pd).getString("area_name"));
					 	    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
					 	    				waterpd=null;
					 	    				//新增订单
					 	    				PageData orderpd=new PageData();
						 	  				orderpd.put("order_id", pd.getString("oneyuanmember_id"));
						 	  				orderpd.put("look_number",  pd.getString("oneyuanmember_id"));
						 	  				orderpd.put("tihuo_status", "1");
						 	  				orderpd.put("order_status", "1");
						 	  				orderpd.put("sale_money", pd.getString("allpay_money"));
						 	  				orderpd.put("discount_after_money",  pd.getString("allpay_money"));
						 	  				orderpd.put("actual_money",  pd.getString("allpay_money"));
						 	  				if(channel.contains("alipay")){
						 	  					pd.put("money_pay_type", "3");
						 	  				}else if(channel.contains("wx")){
						 	  					pd.put("money_pay_type", "4");
						 	  				} 
						 	  				orderpd.put("channel",channel);
						 	  				orderpd.put("store_id", "Jiuyu");
						 	   				orderpd.put("pay_type", "4");
						 	   				orderpd.put("payer_id", pd.getString("member_id"));
						 	   				orderpd.put("in_jiqi", pd.getString("in_jiqi"));
						 	   				orderpd.put("order_tn", trade);
						 	  				ServiceHelper.getOneYuanService().saveOneYuanOrder(orderpd);
						 	  				orderpd=null;
						 	  				PageData gpd=new PageData();
						 	  				gpd.put("goods_id",pd.getString("oneyuangoods_id"));
						 	  				gpd.put("shop_number",pd.getString("getoneyuan_quantity"));
						 	  				gpd.put("price", pd.getString("allpay_money"));
						 	  				gpd.put("order_id", pd.getString("oneyuanmember_id"));
						 	  				gpd.put("goods_type", "3");
						 	  				ServiceHelper.getAppOrderService().saveOrderGoods(gpd);
						 	  				gpd=null;
						 	  				//更新状态
						 	  				pd.put("buy_status", "1");
						 	  				ServiceHelper.getOneYuanService().editMemberGoods(pd);
						 	  				pd=null;
						 	  				//一元夺宝支付记录
						 	  				
 	 			            		}
    						 	}
    						 	else if(body.equals("21")){//商家充值
 	 			            		 logger.info("商家充值回调开始=============================");
	 			            		 PageData historypd=new PageData();
	 			            		 historypd.put("store_wealthhistory_id", orderno);
	 			            		 //历史记录详情
	 			            		 historypd=appStoreService.findWealthHistoryById(historypd);
	 			            		 ////更新财富信息
  		 			            	PageData spd=appStoreService.findById(historypd);
   			 			   			double now_wealth=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(spd));
			 			   			double m=price/100;
			 			   			pd.put("now_wealth", df2.format(now_wealth+m));
			 			   			pd.put("store_id", spd.getString("store_id"));
				 			   		appStoreService.editWealthById(pd);
				 			   		//新增支付记录tb_pay_history
//				 					System.out .println("新增充值记录waterrecord_id========================");
				 					PageData waterpd=new PageData();
				 		    		waterpd.put("pay_status","1");
				 		    	   	waterpd.put("waterrecord_id",orderno);
				 		   			waterpd.put("user_id", spd.getString("store_id"));
				 		   			waterpd.put("user_type", "1");
				 		    		waterpd.put("withdraw_rate","0");
				 		   			waterpd.put("money_type","1");
				 		   	 		waterpd.put("money", df2.format(m));
				 		   	 		waterpd.put("reduce_money","0");
				 		   			waterpd.put("arrivalmoney",  df2.format(m));
				 		   			waterpd.put("nowuser_money","0");
				 		   			waterpd.put("application_channel", historypd.getString("in_jiqi"));
				 			   		if(channel.contains("wx")){
				 				   		waterpd.put("remittance_name",Const.payjiqi[4] );
				 			   			waterpd.put("remittance_type","4" );
				 			   			waterpd.put("wx_money",  df2.format(m) );
				 					}else if(channel.contains("alipay")){
				 						waterpd.put("remittance_name", Const.payjiqi[3]);
				 			   			waterpd.put("remittance_type","3" );
				 			   			waterpd.put("alipay_money",  df2.format(m) );
				 					}else{
				 						waterpd.put("remittance_name", Const.payjiqi[3]);
				 			   			waterpd.put("remittance_type","1" );
				 			   			waterpd.put("bank_money",  df2.format(m) );
				 					}
				 		   			waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
				 		    		waterpd.put("createtime",DateUtil.getTime());
				 		   			waterpd.put("over_time",DateUtil.getTime());
				 		   	  		waterpd.put("jiaoyi_type","0");
				 		   			waterpd.put("payee_number",Const.jiuyunumber);
				 		    	 	waterpd.put("order_tn", trade);
				 		   			waterpd.put("province_name", spd.getString("province_name"));
				 		   			waterpd.put("city_name", spd.getString("city_name"));
				 		   			waterpd.put("area_name", spd.getString("area_name"));
				 		    		ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
				 		    		waterpd=null; 
				 					//更新处理状态
 				 					historypd.put("process_status", "1");
					 				historypd.put("user_id", Const.jiuyunumber);
					 				appStoreService.editWealthHistoryStatus(historypd);
					 				//获取充值次数
				 					String recharge_times = storeService.count(spd.getString("store_id"));
  				 					//充值第一次时进入	
				 					if(recharge_times == null  || recharge_times.equals("") || recharge_times.equals("0")){
				 						//判断充值金额
				 	 			   		if(m >= 100){
						 	 			   	double complex_score=Double.parseDouble(spd.getString("complex_score"))+Double.parseDouble(Const.complexscore[3]);
						 	 			   	//根据综合分值增加星级
		 						 			complex_score=complex_score+3;
		 						 			TongYong.complex_scoreAdd(spd.getString("store_id"), TongYong.df0.format(complex_score));
				 	 			   		}
				 					}
				 					//充值第二次时进入
				 					if(recharge_times.equals("1")){
				 						//第二次充值金额为500时增加商家的综合评分
					 					if(m >= 500){
						 						double complex_score=Double.parseDouble(spd.getString("complex_score"))+Double.parseDouble(Const.complexscore[2]);
						 						//根据综合分值增加星级
		 						 				complex_score=complex_score+3;
		 						 				TongYong.complex_scoreAdd(spd.getString("store_id"), TongYong.df0.format(complex_score));
					 					}
					 				}
				 					//更新充值次数
				 					pd.put("recharge_times", "1");
				 					pd.put("store_id", spd.getString("store_id"));
				 					appStoreService.edit(pd);
					 				System.out .println("商家充值回调结束=================================");
    						 	}
    						 	else if(body.equals("211")){//商家充值-交易扣点的充值
		 			            		 logger.info("商家交易扣点的充值回调开始=============================");
		 			            		 PageData historypd=new PageData();
		 			            		 historypd.put("store_wealthhistory_id", orderno);
		 			            		 historypd.put("process_status", "1");
							 			 historypd.put("user_id", Const.jiuyunumber);
							 			 appStoreService.editWealthHistoryStatus(historypd);
		 			            		 //历史记录详情
		 			            		 historypd=appStoreService.findWealthHistoryById(historypd);
		 			            		//更新订单处理状态
 		 			            		PageData spd=appStoreService.findById(historypd);
	  			 			   			double now_wealth=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(spd));
				 			   			double m=price/100;
				 			   			pd.put("now_wealth", df2.format(now_wealth+m));
				 			   			pd.put("store_id", spd.getString("store_id"));
					 			   		appStoreService.editWealthById(pd);
					 			   		//新增支付记录tb_pay_history
//					 					System.out .println("新增充值记录waterrecord_id========================");
					 					PageData waterpd=new PageData();
					 		    		waterpd.put("pay_status","1");
					 		    	   	waterpd.put("waterrecord_id",orderno);
					 		   			waterpd.put("user_id", spd.getString("store_id"));
					 		   			waterpd.put("user_type", "1");
					 		    		waterpd.put("withdraw_rate","0");
					 		   			waterpd.put("money_type","1");
					 		   	 		waterpd.put("money", df2.format(m));
					 		   	 		waterpd.put("reduce_money","0");
					 		   			waterpd.put("arrivalmoney",  df2.format(m));
					 		   			waterpd.put("nowuser_money","0");
					 		   			waterpd.put("application_channel", historypd.getString("in_jiqi"));
					 			   		if(channel.contains("wx")){
					 				   		waterpd.put("remittance_name",Const.payjiqi[4] );
					 			   			waterpd.put("remittance_type","4" );
					 			   			waterpd.put("wx_money",  df2.format(m) );
					 					}else if(channel.contains("alipay")){
					 						waterpd.put("remittance_name", Const.payjiqi[3]);
					 			   			waterpd.put("remittance_type","3" );
					 			   			waterpd.put("alipay_money",  df2.format(m) );
					 					}else{
					 						waterpd.put("remittance_name", Const.payjiqi[3]);
					 			   			waterpd.put("remittance_type","1" );
					 			   			waterpd.put("bank_money",  df2.format(m) );
					 					}
					 		   			waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
					 		    		waterpd.put("createtime",DateUtil.getTime());
					 		   			waterpd.put("over_time",DateUtil.getTime());
					 		   	  		waterpd.put("jiaoyi_type","0");
					 		   			waterpd.put("payee_number",Const.jiuyunumber);
					 		    	 	waterpd.put("order_tn", trade);
					 		   			waterpd.put("province_name", spd.getString("province_name"));
					 		   			waterpd.put("city_name", spd.getString("city_name"));
					 		   			waterpd.put("area_name", spd.getString("area_name"));
					 		    		ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
					 		    		waterpd=null; 
 						 				//获取充值次数
					 					String recharge_times = storeService.count(spd.getString("store_id"));
						 				//更新充值次数
					 					pd.put("recharge_times", "1");
					 					appStoreService.edit(pd);
 					 					//充值第一次时进入	
					 					if(recharge_times == null  || recharge_times.equals("") || recharge_times.equals("0")){
					 						//判断充值金额
					 	 			   		if(m >= 100){
							 	 			   	double complex_score=Double.parseDouble(spd.getString("complex_score"))+Double.parseDouble(Const.complexscore[3]);
							 	 			   	//根据综合分值增加星级
			 						 			complex_score=complex_score+3;
			 						 			TongYong.complex_scoreAdd(spd.getString("store_id"), TongYong.df0.format(complex_score));
					 	 			   		}
					 					}
					 					//充值第二次时进入
					 					if(recharge_times.equals("1")){
					 						//第二次充值金额为500时增加商家的综合评分
						 					if(m >= 500){
							 						double complex_score=Double.parseDouble(spd.getString("complex_score"))+Double.parseDouble(Const.complexscore[2]);
							 						//根据综合分值增加星级
			 						 				complex_score=complex_score+3;
			 						 				TongYong.complex_scoreAdd(spd.getString("store_id"), TongYong.df0.format(complex_score));
						 					}
						 				}
					 					//更新交易扣点是否完成
					 					pd.put("biaozhun_status", "1");
					 					pd.put("biaozhun_content", "交易扣点处理");
					 					pd.put("starttime",  DateUtil.getDay());
					 					pd.put("endtime",  DateUtil.getAfterMonthDate( DateUtil.getDay(), "36"));
    	  	 						 	appStoreService.editEarnestType(pd); 
 						 				System.out .println("商家交易扣点的充值结束=================================");
	   						 	}
    						 	else if(body.equals("22")){//商家购买服务费
   	 			            		logger.info("商家支付保证金====服务费金额"+price/100);
  	 			            		double fw_money=price/100;
   	 			     		 	    //商家历史账单详情
  	 			     		 		PageData historypd=new PageData();
  	 			     		 		historypd.put("store_wealthhistory_id", orderno);
  	 			     		 		historypd=appStoreService.findWealthHistoryById(historypd);
  	 			     		 		//商家的详情
  	 			     		 		PageData spd=new PageData();
	 			     		 		spd.put("store_id", historypd.getString("store_id"));
	 				       			spd=appStoreService.findById(spd);
	 				       			//=====
	  	 		 					String city_file_fee_id=spd.getString("city_file_fee_id");
  	 								String biaozhun_content=spd.getString("biaozhun_content");
  	 								String endtime=spd.get("endtime").toString();
  	 								double fw_sp_getmoney=0;//服务商会获取的金钱
  	 								double send_jf=0;//购买服务费赠送的积分
  	 							   //查看续费或是服务费的详情
  	 								PageData feepd=new PageData();
  	 								feepd.put("city_file_fee_id", spd.getString("next_city_file_fee_id"));
  	 								feepd=city_fileService.getCityFeeDetail(feepd);
  	 								if(feepd != null){//存在当前服务费
  	 										String send_integer=feepd.getString("send_integer");
  	 										String purchase_type=feepd.getString("purchase_type");
  	 										String buying_time=feepd.getString("buying_time");
  	 										if(send_integer == null || send_integer.equals("")){
  	 											send_integer="0";
  	 										}
  	 										if(buying_time == null || buying_time.equals("")){
  	 											buying_time="0";
  	 										}
  	 										String present_time=feepd.getString("present_time");
  	 										if(present_time == null || present_time.equals("")){
  	 											present_time="0";
  	 										}
  	 										String purchase_price=feepd.getString("purchase_price");
  	 									    String str_fee="";
  	 									    //==
  	 									    send_jf=Double.parseDouble(send_integer);
  	 									    //==
  	 										if(purchase_type.equals("1")){
  	 											str_fee="首次购买";
	  	 									}else{
  	 											str_fee="续费";
  	 										}
  	 										str_fee=str_fee+buying_time+"个月，赠送"+present_time+"个月,价格"+purchase_price+"元，内含赠送积分"+send_integer;
   	 										int allmonth=Integer.parseInt(present_time)+Integer.parseInt(buying_time);
   	 										//==
   	 										endtime=DateUtil.getAfterMonthDate(endtime,allmonth+"");
   	 										//==
   	 										spd.put("endtime", endtime);
  	 										spd.put("biaozhun_content",biaozhun_content+","+str_fee);
  	 										spd.put("city_file_fee_id", city_file_fee_id+","+feepd.getString("city_file_fee_id"));
	  	 							}else{
	  	 								return;
	  	 							}
  	 								//城市营销的：提成与补贴
 	  	 							PageData cpd=store_fileService.getCityForName(spd);
  	 								if(cpd != null){
  	 									String service_provider_commission=cpd.getString("service_provider_commission");
  	 						 			if(service_provider_commission == null || service_provider_commission.equals("")){
  	 						 				service_provider_commission="0";
  	 									}
  	 						 		    fw_sp_getmoney=(fw_money-send_jf)*(Double.parseDouble(service_provider_commission)/100);//服务费
  	  	 						 		PageData sppd=sp_fileService.findById(spd);
	  	 						 		if(sppd != null && fw_sp_getmoney >0 ){
	  	 						 			String now_balance=df2.format(Double.parseDouble(sppd.getString("nowmoney"))+fw_sp_getmoney);
	  	 						 			sppd.put("nowmoney", now_balance);
	  	 							 		sp_fileService.edit(sppd);
	  	 							 		//添加一个支付记录
	  	 				 	 				PageData mmpd=new PageData();
 	  	 				 	 				mmpd.put("yewu_id", spd.getString("store_id"));//业务对象
	  	 					 	 			mmpd.put("yewu_type", "1");//业务对象
	  	 				 	 				mmpd.put("money", df2.format(fw_sp_getmoney));//金额
	  	 				 	 				mmpd.put("money_type", "1");//1、销售提成: 2、积分收益： 3、平台奖励 
	  	 					 	 			mmpd.put("operate_type", "1"); //1-服务商，2-业务员
	  	 				 	 				mmpd.put("operate_id", sppd.getString("sp_file_id")); 
	  	 				 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
	  	 				 	 				mmpd.put("isjihuo", "1");//0-未激活，1-已激活
	  	 				 	 				mmpd.put("now_balance", now_balance);//余额
	  	 				 	 				mmpd.put("service_performance_id", orderno);//收益对象
	  	 				 	 				ServiceHelper.getService_performanceService().save(mmpd);
//  	  	 							 		System.out.println("服务商提成"+df2.format(fw_sp_getmoney));
 	  	 						 		}
   	 								}
  	 								//新增支付保证金的记录
 				    				PageData waterpd=new PageData();
				    				waterpd.put("pay_status","1");
				    	   			waterpd.put("waterrecord_id",orderno);
				   					waterpd.put("user_id", spd.getString("store_id"));
				   					waterpd.put("user_type", "1");
				    				waterpd.put("withdraw_rate","0");
				   					waterpd.put("money_type","3");
				   	 				waterpd.put("money",  df2.format(-fw_money));
				   	 				waterpd.put("reduce_money","0");
				   					waterpd.put("arrivalmoney",   df2.format(-fw_money));
				   					waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(spd));
				   					waterpd.put("application_channel", historypd.getString("in_jiqi") );
					   				if(channel.contains("wx")){
	 					   				waterpd.put("remittance_name",Const.payjiqi[4] );
					   					waterpd.put("remittance_type","4" );
					   					waterpd.put("wx_money",  df2.format(-fw_money) );
					   				}else if(channel.contains("alipay")){
					   					waterpd.put("remittance_name", Const.payjiqi[3]);
					   					waterpd.put("remittance_type","3" );
					   					waterpd.put("alipay_money",  df2.format(-fw_money) );
	  	 							}else{
	  	 								waterpd.put("remittance_name", Const.payjiqi[3]);
					   					waterpd.put("remittance_type","1" );
					   					waterpd.put("bank_money",  df2.format(-fw_money) );
	  	 							}
				   					waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
				    				waterpd.put("createtime",DateUtil.getTime());
				   					waterpd.put("over_time",DateUtil.getTime());
				   	  				waterpd.put("jiaoyi_type","0");
				   					waterpd.put("payee_number",Const.jiuyunumber);
				    	 			waterpd.put("order_tn",trade);
				   					waterpd.put("province_name", spd.getString("province_name"));
				   					waterpd.put("city_name", spd.getString("city_name"));
				   					waterpd.put("area_name", spd.getString("area_name"));
				    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
				    				waterpd=null;
		  	 			     		
   	 				       			/*
   	 				       			 * profit_type：1-商家 （提现），2-商家 （充值积分），3-用户支付的收益金钱/积分，4-现金支付的金额，
  	 			 			   	     *5-第三方支付的金额，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
   	 				       			 */
  	 				   			    PageData wpd=new PageData();
  	 			 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
   	 				 			    wpd.put("profit_type", "2");
  	 				 			    wpd.put("pay_type", channel);
  	 				 			    wpd.put("number",df2.format( fw_money ));
  	 				 			    wpd.put("store_id", spd.getString("store_id"));
  	 				 			    wpd.put("in_jiqi", historypd.getString("in_jiqi"));
  	 					 			wpd.put("store_operator_id","jy"+spd.getString("store_id"));
  	 				 			    wpd.put("process_status", "1");
	  	 				 			wpd.put("user_id",Const.jiuyunumber);
	  	 				 			wpd.put("jiaoyi_id", "FWCZ"+ orderno);
  	 				 			    wpd.put("last_wealth", df2.format(Double.parseDouble(appStoreService.sumStoreWealth(spd))));
  	 				 			    wpd.put("store_wealthhistory_id","FWCZ"+ orderno);
  	 				 			    appStoreService.saveWealthhistory(wpd);
  	 				 			    wpd=null;
  			 			   			waterpd=new PageData();
				    				waterpd.put("pay_status","1");
				    	   			waterpd.put("waterrecord_id","FWCZ"+ orderno);
				   					waterpd.put("user_id", spd.getString("store_id"));
				   					waterpd.put("user_type", "1");
				    				waterpd.put("withdraw_rate","0");
				   					waterpd.put("money_type","1");
				   	 				waterpd.put("money",  df2.format(fw_money));
				   	 				waterpd.put("reduce_money","0");
				   					waterpd.put("arrivalmoney",   df2.format(fw_money));
				   					waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(spd));
				   					waterpd.put("application_channel", historypd.getString("in_jiqi") );
					   				if(channel.contains("wx")){
	 					   				waterpd.put("remittance_name",Const.payjiqi[4] );
					   					waterpd.put("remittance_type","4" );
					   					waterpd.put("wx_money",  df2.format(fw_money) );
					   				}else if(channel.contains("alipay")){
  	 									waterpd.put("remittance_name", Const.payjiqi[3]);
					   					waterpd.put("remittance_type","3" );
					   					waterpd.put("alipay_money",  df2.format(fw_money) );
	  	 							}else{
	  	 								waterpd.put("remittance_name", Const.payjiqi[3]);
					   					waterpd.put("remittance_type","1" );
					   					waterpd.put("bank_money",  df2.format(fw_money) );
	  	 							}
				   					waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
				    				waterpd.put("createtime",DateUtil.getTime());
				   					waterpd.put("over_time",DateUtil.getTime());
				   	  				waterpd.put("jiaoyi_type","0");
				   					waterpd.put("payee_number",Const.jiuyunumber);
				    	 			waterpd.put("order_tn",trade);
				   					waterpd.put("province_name", spd.getString("province_name"));
				   					waterpd.put("city_name", spd.getString("city_name"));
				   					waterpd.put("area_name", spd.getString("area_name"));
				    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
				    				waterpd=null;
 				    				//购买年费是否赠送积分
				    				if(send_jf > 0){
										//如果是首次购买者有赠送积分记录
	 			 				   		spd.put("money", "-"+send_jf);
			 				   			appStoreService.editJFWealthById(spd);//更新商家财富信息
										PageData zspd=new PageData();
										zspd.put("wealth_type", "1");//1-积分，2-余额
										zspd.put("jiaoyi_id", "ZSJF"+orderno);
										zspd.put("profit_type", "2");
										zspd.put("pay_type", Const.payjiqi[7]);
										zspd.put("number",send_jf);
										zspd.put("store_id", spd.getString("store_id"));
										zspd.put("in_jiqi", historypd.getString("in_jiqi"));
										zspd.put("store_operator_id","jy"+spd.getString("store_id"));
										zspd.put("process_status", "1");
										zspd.put("user_id",Const.jiuyunumber);
										zspd.put("last_wealth", df2.format(Double.parseDouble(appStoreService.sumStoreWealth(spd))));
										zspd.put("store_wealthhistory_id", "ZSJF"+orderno);
		  	 					   		appStoreService.saveWealthhistory(zspd);
		  	 					   		zspd=null;
 	 				 			   		waterpd=new PageData();
 					    				waterpd.put("pay_status","1");
 					    	   			waterpd.put("waterrecord_id", "ZSJF"+orderno);
 					   					waterpd.put("user_id", spd.getString("store_id"));
 					   					waterpd.put("user_type", "1");
 					    				waterpd.put("withdraw_rate","0");
 					   					waterpd.put("money_type","1");
 					   	 				waterpd.put("money",  send_jf);
 					   	 				waterpd.put("reduce_money","0");
 					   					waterpd.put("arrivalmoney",   send_jf);
 					   					waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(spd));
 					   					waterpd.put("application_channel", historypd.getString("in_jiqi") );
	 					   				waterpd.put("remittance_name", Const.payjiqi[7]);
 					   					waterpd.put("remittance_type","6" );
 					   					waterpd.put("jiuyu_money",  send_jf );
	 					   				waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
 					    				waterpd.put("createtime",DateUtil.getTime());
 					   					waterpd.put("over_time",DateUtil.getTime());
 					   	  				waterpd.put("jiaoyi_type","0");
 					   					waterpd.put("payee_number",Const.jiuyunumber);
	 					    	 		waterpd.put("order_tn",trade);
 					   					waterpd.put("province_name", spd.getString("province_name"));
 					   					waterpd.put("city_name", spd.getString("city_name"));
 					   					waterpd.put("area_name", spd.getString("area_name"));
 					    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
 					    				waterpd=null;
									}
//				    				System.out.println("更新tb_store_wealthhistory状态======================="+historypd);
  	 								historypd.put("process_status", "1");
  	 								historypd.put("sp_getmoney", df2.format(fw_sp_getmoney));
  	 								historypd.put("send_jf", df2.format(send_jf));
	  	 		 					historypd.put("user_id", Const.jiuyunumber);
	  	 		 					System.out.println(historypd.toString());
	  	 		 					appStoreService.editWealthHistoryStatus(historypd);
//   	 						 	System.out.println("更新tb_store_file状态====================================");
  	 						 		spd.put("biaozhun_status", "1");
  	 	 							spd.put("earnest_status", "1");
  	 	 							spd.put("service_status", "1");
  	 	 							spd.put("em_statestatus", "1");
  	 	 							spd.put("sf_statestatus", "1");
  	 						 		appStoreService.editEarnestType(spd); 
				    				//设置服务费到期的定时器
 							 		long l1=DateUtil.fomatDate(DateUtil.getDay()).getTime();
 									long l2=DateUtil.fomatDate(endtime).getTime();
 									StoreFeeTihuoTask feeth=new StoreFeeTihuoTask(spd.getString("store_id"));
 									Timer tt=new Timer();
 									tt.schedule(feeth, l2-l1);
 									//-----------------------------------------------------------------------
   						 		}
    						 	else if(body.equals("23")){//商家支付优选编辑费
  			        				System.out.println("****************优选商品的一切相关支付***********************");
 			        				double bianji_money=price/100;
 			        				//更新财富信息
 			        				PageData historypd=new PageData();
			            		 	historypd.put("store_wealthhistory_id", orderno);
 			            		 	historypd=appStoreService.findWealthHistoryById(historypd);
// 			            		 	System.out.println("historypd="+historypd);
 			            		 	String profit_type=historypd.getString("profit_type");
 			            		 	String jiaoyi_id=historypd.getString("jiaoyi_id");
		            		 		//商家详情
	 			            		PageData spd=new PageData();
		 			       			spd=appStoreService.findById(historypd);
//		 			       			System.out.println("spd="+spd);
		 			       			PageData sppd=sp_fileService.findById(spd);
	 						 		if(sppd != null){
	 						 			String nowmoney=df2.format(Double.parseDouble(sppd.getString("nowmoney"))+bianji_money);
	 						 			sppd.put("nowmoney", nowmoney);
	 							 		sp_fileService.edit(sppd);
	 							 		//添加一个支付记录
	 				 	 				PageData mmpd=new PageData();
  	 				 	 				mmpd.put("yewu_id", spd.getString("store_id"));//业务对象
	 					 	 			mmpd.put("yewu_type", "1");//业务对象
	 				 	 				mmpd.put("money", df2.format(bianji_money));//金额
	 				 	 				mmpd.put("money_type", "6");//1、销售提成: 2、积分收益： 3、平台奖励，4-保证金，5-提现 ,6-优选编辑费用
	 					 	 			mmpd.put("operate_type", "1"); //1-服务商，2-业务员
	 				 	 				mmpd.put("operate_id", sppd.getString("sp_file_id")); 
	 				 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
	 				 	 				mmpd.put("isjihuo", "1");//0-未激活，1-已激活
	 				 	 				mmpd.put("now_balance", nowmoney);//余额
	 				 	 				mmpd.put("service_performance_id", orderno);//收益对象
	 				 	 				ServiceHelper.getService_performanceService().save(mmpd);
 	 						 		}
// 		 							System.out .println("新增支付优选编辑费tb_waterrecord========================");
		 							PageData waterpd=new PageData();
		 		    				waterpd.put("pay_status","1");
		 		    	   			waterpd.put("waterrecord_id",orderno);
		 		   					waterpd.put("user_id", spd.getString("store_id"));
		 		   					waterpd.put("user_type", "1");
		 		    				waterpd.put("withdraw_rate","0");
		 		    				if(profit_type.equals("13")){
		 		    					waterpd.put("money_type","7");
		 		    				}else if(profit_type.equals("14")){
		 		    					waterpd.put("money_type","8");
		 		    				}
 		 		   	 				waterpd.put("money", df2.format(bianji_money));
 		 		   	 				waterpd.put("reduce_money","0");
		 		   					waterpd.put("arrivalmoney",  df2.format(bianji_money));
		 		   					waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(spd));
		 		   					waterpd.put("application_channel", historypd.getString("in_jiqi"));
		 			   				if(channel.contains("wx")){
		 				   				waterpd.put("remittance_name",Const.payjiqi[4] );
		 			   					waterpd.put("remittance_type","4" );
		 			   					waterpd.put("wx_money",  df2.format(bianji_money) );
		 							}else if(channel.contains("alipay")){
		 								waterpd.put("remittance_name", Const.payjiqi[3]);
		 			   					waterpd.put("remittance_type","3" );
		 			   					waterpd.put("alipay_money",  df2.format(bianji_money) );
		 							}else{
		 								waterpd.put("remittance_name", Const.payjiqi[3]);
		 			   					waterpd.put("remittance_type","1" );
		 			   					waterpd.put("bank_money",  df2.format(bianji_money) );
		 							}
		 		   					waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
		 		    				waterpd.put("createtime",DateUtil.getTime());
		 		   					waterpd.put("over_time",DateUtil.getTime());
		 		   	  				waterpd.put("jiaoyi_type","0");
//		 		   					waterpd.put("payee_number",spd.getString("sp_file_id"));
		 		   					waterpd.put("payee_number", Const.jiuyunumber);
		 		    	 			waterpd.put("order_tn", trade);
		 		   					waterpd.put("province_name", spd.getString("province_name"));
		 		   					waterpd.put("city_name", spd.getString("city_name"));
		 		   					waterpd.put("area_name", spd.getString("area_name"));
		 		    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
		 		    				waterpd=null; 
		 						   //更新处理状态
		 						   logger.info("更新记录状态");
		 						   historypd.put("process_status", "1");
		 						   historypd.put("sp_getmoney", df2.format(bianji_money));
			 					   historypd.put("user_id", Const.jiuyunumber);
			 					   appStoreService.editWealthHistoryStatus(historypd);
 			        			    //更改优选商品的状态
				 				    PageData yxgoodspd=new PageData(); 
				 				    yxgoodspd.put("youxuangoods_id", jiaoyi_id);
								    yxgoodspd=ServiceHelper.getYouXuanService().findByIdGoods(yxgoodspd);
								    System.out.println("yxgoodspd="+yxgoodspd);
									String goods_status=yxgoodspd.getString("goods_status");
  									if(goods_status.equals("1")){
 										yxgoodspd.put("goods_status", "4");
									}else if(goods_status.equals("2")){
 										yxgoodspd.put("goods_status", "4");
									}
  									ServiceHelper.getYouXuanService().editGoods(yxgoodspd);
   						 		}else if(body.equals("31")){//服务商支付保证金
 	  	 			            	System.out.println("服务商保证金开始=============================");
 	  	 			            	double baozheng_mongy=price/100;
  	 			            		PageData waterpd=new PageData();
  	 			            		waterpd.put("waterrecord_id", orderno);
	  	 			            	waterpd=ServiceHelper.getWaterRecordService().findByIdWaterRecord(waterpd);
//			  	 			        System.out.println("============================waterpd="+waterpd);
	  	 			            	//更新支付状态
//  	 			            	System.out .println("更新总后台的充值信息=========================");
  	 			            		waterpd.put("pay_status", "1");
  	 			            		waterpd.put("order_tn", trade);
  	 			            		ServiceHelper.getWaterRecordService().editWaterRecord(waterpd);
	  	 			            	PageData sppd=new PageData();
	  	 			            	sppd.put("sp_file_id", waterpd.getString("user_id"));
	  	 			            	sppd=sp_fileService.findById(sppd);
//  	 			            	System.out.println("服务商详情====================="+sppd);
//   	 			            	System.out.println("=======================更新服务商金额以及状态");
  	 			            		sppd.put("earnest_money", df2.format(baozheng_mongy));
  	 			            		sppd.put("earnest_status", "1");
	  	 			            	sp_fileService.edit(sppd);
	  	 			            	System.out.println("服务商保证金结束=============================");
    						 	}
   					 	 	 	response.setStatus(200);
 		 	 	        }else if ("refund.succeeded".equals(event.getType())) {
 		 	 	        		response.setStatus(200);
					    } else {
					         	response.setStatus(500);
					    }
			   }catch(Exception e){
				   System.out.println("出错===============》》"+e.toString());
				   logger.error("出错===============》》"+e.toString(), e);
				   ServiceHelper.getAppPcdService().saveLog(pd.toString(), e.toString()+"回调接口参数","00");
				   response.setStatus(500);
			   }
 			   System.out.println("Ping++结束回调了=======================>>>>>>>");
 		}
		
		
		
		@Resource(name="sp_fileService")
		private Sp_fileService sp_fileService;
		
		
		
		
		
		
		
		
		/*
		 * 获取IP
		 */
		public static String getIp(HttpServletRequest request) {
			String ipAddress = null;
			// ipAddress = this.getRequest().getRemoteAddr();
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0
					|| "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0
					|| "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0
					|| "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}

			}
	 		 //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			 if(ipAddress!=null && ipAddress.length()>15){
			 //"***.***.***.***".length() = 15
			 if(ipAddress.indexOf(",")>0){
			 ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
			 }
			 }
			return ipAddress;
		}
		
 

		@Resource(name = "storepc_marketingeffectService")
		private   Storepc_marketingeffectService storepcMarketingeffectService;	

		@Resource(name = "storepc_scorewayService")
		private Storepc_scorewayService storepcScorewayService; 
		
		@Resource(name = "storepc_discountwayService") 
		private Storepc_discountwayService storepcDiscountwayService;
		
		@Resource(name = "storepc_redpacketsService")
		private Storepc_redpacketsService storepcRedpacketsService;
		
		
		@Resource(name = "storepc_marketingtypeService")
		private Storepc_marketingtypeService storepcMarketingTypeService;
		
  
		
		@Resource(name="city_fileService")
		private City_fileService city_fileService;
		
		 
		
	 
  }
