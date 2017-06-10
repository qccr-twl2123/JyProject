//package com.tianer.controller.storepc.store;
//
//
//import java.io.BufferedReader;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.json.JSONObject;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.pingplusplus.model.Charge;
//import com.pingplusplus.model.Event;
//import com.pingplusplus.model.Webhooks;
//import com.tianer.controller.base.BaseController;
//import com.tianer.controller.zhihui.payMoney.ChargeExample;
//import com.tianer.controller.zhihui.payMoney.PayAct;
//import com.tianer.entity.Page;
//import com.tianer.util.AppUtil;
//import com.tianer.util.Const;
//import com.tianer.util.DateUtil;
//import com.tianer.util.PageData;
//import com.tianer.service.app.AppMemberService;
//import com.tianer.service.app.AppMember_redpacketsService;
//import com.tianer.service.app.AppMember_wealthhistoryService;
//import com.tianer.service.app.AppOrderService;
//import com.tianer.service.app.AppPay_historyService;
//import com.tianer.service.app.AppStoreService;
//import com.tianer.service.app.AppStore_redpacketsService;
//import com.tianer.service.app.AppWithdraw_approvalService;
//import com.tianer.service.business.pay_history.Pay_historyService;
//import com.tianer.service.storepc.store_bankcard.Storepc_bankcardService;
//import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
//
///** 
// * 
//* 类名称：Pay_historyController   
//* 类描述：   支付记录登记
//* 创建人：魏汉文  
//* 创建时间：2016年7月5日 下午6:13:42
// */
//@Controller("store_PayController")
//@RequestMapping(value="/storepc_pay")
//public class CopyOfStore_PayController extends BaseController {
//	
//    /**
// 	 * 
// 	* 方法名称:：thirdPartyCz 
// 	* 方法描述：充值,商家ID，操作员ID，充值金额
// 	* 创建人：魏汉文
// 	* 创建时间：2016年7月6日 上午10:13:52
// 	 */
//	@RequestMapping(value="/store_PartyCz")
//	@ResponseBody
//	public Object store_PartyCz(HttpServletRequest request) throws Exception{
//		Map<String, Object> map = new HashMap<String, Object>();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//  		String result="1";
//		String message="充值确认中";
//		// type代表支付方式
//		PageData pd=new PageData();
//		try{
//						pd = this.getPageData();
// 						String store_wealthhistory_id=this.getCZUID();//充值单号
//						String url=pd.getString("url");
// 						//商家财富
// 						PageData spd=new PageData();
//  			 			if(pd.getString("store_id")== null || pd.getString("store_id").equals("")){
// 			 				result="0";
// 			 				message="id不能为空";
// 			 			}else{
// 			 				pd.put("wealth_type", "1");//1积分，2金钱
// 			 				spd=appStoreService.findWealthById(pd);
// 				 			String ip=getIp(request);//当前用户所在IP地址
// 							String money=pd.getString("money");
// 							if( money== null || money.equals("")){
// 								map.put("data", "");
// 							}else{
// 								String pay_way=pd.getString("pay_way");//支付方式
// 	 							//新增商家财富历史记录
// 				   				pd.put("wealth_type", "1");
// 				   				pd.put("profit_type", "2");//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，4-现金支付的金额，5-第三方支付的金额，6-抢积分,7-送积分
// 				   				pd.put("number", money);
// 				   				pd.put("store_wealthhistory_id", store_wealthhistory_id);
// 				   				pd.put("store_id", spd.getString("store_id"));
// 				   				pd.put("store_operator_id", pd.getString("store_operator_id"));
// 				   				pd.put("process_status", "0");
// 				   				pd.put("arrivalMoney", money);
//  				   				pd.put("pay_type",pay_way);
// 				   				String nowMoney = spd.getString("now_wealth");
// 				   				double nom = Double.parseDouble(money);
// 				   				double nownom = Double.parseDouble(nowMoney);
// 				   				pd.put("last_wealth", df.format(nownom+nom));
// 	 			   				appStoreService.saveWealthhistory(pd);
// 	 							/*
// 	 							 * 支付方式pay_type:
// 	 							 * 	alipay:支付宝手机支付
// 	 								alipay_wap:支付宝手机网页支付
// 	 								alipay_qr:支付宝扫码支付
// 	 								alipay_pc_direct:支付宝 PC 网页支付
// 	 								bfb:百度钱包移动快捷支付
// 	 								bfb_wap:百度钱包手机网页支付
// 	 								upacp:银联全渠道支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
// 	 								upacp_wap:银联全渠道手机网页支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
// 	 								upacp_pc:银联 PC 网页支付
// 	 								cp_b2b:银联企业网银支付
// 	 								wx:微信支付
// 	 								wx_pub:微信公众账号支付
// 	 								wx_pub_qr:微信公众账号扫码支付
// 	 								yeepay_wap:易宝手机网页支付
// 	 								jdpay_wap:京东手机网页支付
// 	 								cnp_u:应用内快捷支付（银联）
// 	 								cnp_f:应用内快捷支付（外卡）
// 	 								applepay_upacp:Apple Pay
// 	 								fqlpay_wap:分期乐支付
// 	 								qgbc_wap:量化派支付
// 	 								cmb_wallet:招行一网通
// 	 							 */
// 	 							//2.获取charge对象
// 	 			   				Charge  charge=null;
// 	 			   			    if(pay_way.equals("wx_pub_qr") || pay_way.equals("alipay_pc_direct")){//pc
//	 			   					charge = ChargeExample.getPayTwo(store_wealthhistory_id, Double.parseDouble(money)*100,ip,pay_way,"2",1,url);
//	 			   				}else{//app
//	 			   					charge = ChargeExample.getPayTwoStore(store_wealthhistory_id, Double.parseDouble(money)*100,ip,pay_way,"2",1,url);
//	 			   				}
//  	 							if(charge== null){
//	 	 								result="0";
//	 	 								message="充值失败";
//	 	 								map.put("data", "");
// 	 							}else{
// 	 									map.put("data", charge);
// 	 							}
//  			 			}
// 					}
//  		}catch(Exception e){
//			result="0";
//			message="系统异常";
//			map.put("data", e.toString());
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message", message);
//    	return map;
//	}
//	
//	 /**
// 	  * @param request
//	  * @return 支付服务费
//	  * @throws Exception
//	  */
//	@RequestMapping(value="/store_payEarnest_money")
//	@ResponseBody
//	public Object store_payEarnest_money(HttpServletRequest request) throws Exception{
//		Map<String, Object> map = new HashMap<String, Object>();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//  		String result="1";
//		String message="支付确认中";
//		// type代表支付方式
//		PageData pd=new PageData();
//		try{
//						pd = this.getPageData();
// 						String store_wealthhistory_id=this.getCZUID();//充值保证金单号
//						String url=pd.getString("url");
// 						//商家财富
// 						PageData spd=new PageData();
// 			 			pd.put("wealth_type", "2");
// 			 			if(pd.getString("store_id")== null || pd.getString("store_id").equals("")){
// 			 				result="0";
// 			 				message="id不能为空";
// 			 			}else{
// 			 				//添加 city_file_fee_id 到商家
// 			 				appStoreService.editFeeByCity(pd);
// 			 				//开始计算
// 			 				spd=appStoreService.findWealthById(pd);
// 				 			String ip=getIp(request);//当前用户所在IP地址
// 							String money=pd.getString("money");
// 							if( money== null || money.equals("")){
// 								map.put("data", "");
// 							}else{
// 								String pay_way=pd.getString("pay_way");//支付方式
// 	 							//新增商家财富历史记录
// 				   				pd.put("wealth_type", "1");
// 				   				pd.put("profit_type", pd.getString("profit_type"));
// 				   				pd.put("number", df.format(-Double.parseDouble(money)));
// 				   				pd.put("store_wealthhistory_id", store_wealthhistory_id);
// 				   				pd.put("store_id", spd.getString("store_id"));
// 				   				pd.put("store_operator_id", pd.getString("store_operator_id"));
// 				   				pd.put("process_status", "0");
// 				   				pd.put("pay_type",pay_way);
// 				   				String nowMoney = spd.getString("now_wealth");
// 				   				double nom = Double.parseDouble(money);
// 				   				double nownom = Double.parseDouble(nowMoney);
// 				   				pd.put("last_wealth", "0");
// 	 			   				appStoreService.saveWealthhistory(pd);
// 	 							/*
// 	 							 * 支付方式pay_type:
// 	 							 * alipay:支付宝手机支付
// 	 								alipay_wap:支付宝手机网页支付
// 	 								alipay_qr:支付宝扫码支付
// 	 								alipay_pc_direct:支付宝 PC 网页支付
// 	 								bfb:百度钱包移动快捷支付
// 	 								bfb_wap:百度钱包手机网页支付
// 	 								upacp:银联全渠道支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
// 	 								upacp_wap:银联全渠道手机网页支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
// 	 								upacp_pc:银联 PC 网页支付
// 	 								cp_b2b:银联企业网银支付
// 	 								wx:微信支付
// 	 								wx_pub:微信公众账号支付
// 	 								wx_pub_qr:微信公众账号扫码支付
// 	 								yeepay_wap:易宝手机网页支付
// 	 								jdpay_wap:京东手机网页支付
// 	 								cnp_u:应用内快捷支付（银联）
// 	 								cnp_f:应用内快捷支付（外卡）
// 	 								applepay_upacp:Apple Pay
// 	 								fqlpay_wap:分期乐支付
// 	 								qgbc_wap:量化派支付
// 	 								cmb_wallet:招行一网通
// 	 							 */
// 	 							//2.获取charge对象
// 	 							Charge charge = ChargeExample.getPayTwo(store_wealthhistory_id, Double.parseDouble(money)*100,ip,pay_way,"2",5,url);
// 	 							if(charge== null){
//	 	 								result="0";
//	 	 								message="充值失败";
//	 	 								map.put("data", "");
// 	 							}else{
// 	 									map.put("data", charge);
// 	 							}
//  			 			}
// 					}
//  		}catch(Exception e){
//			result="0";
//			message="系统异常";
//			map.put("data", e.toString());
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message", message);
//    	return map;
//	}
//	
//	
//	/**
// 	 * 
// 	* 方法名称:：store_PayCash 
// 	* 方法描述：充值,商家ID，操作员ID，充值金额扫码支付
// 	* 创建人：魏汉文
// 	* 创建时间：2016年7月6日 上午10:13:52
// 	 */
//	@RequestMapping(value="/store_PayCash")
//	@ResponseBody
//	public Object  Store_PayCash(HttpServletRequest request) throws Exception{
//		Map<String, Object> map = new HashMap<String, Object>();
//  		String result="1";
//		String message="充值确认中";
//		// type代表支付方式
//		PageData pd=new PageData();
//		try{
//						 pd = this.getPageData();
//						 String order_id=this.get6UID()+pd.getString("order_id");
//						 String money=pd.getString("money");
//						 String pay_way=pd.getString("pay_way");
//						 String ip=getIp(request);//当前用户所在IP地址
// 	 							/*
// 	 							 * 支付方式pay_type:
// 	 							 * alipay:支付宝手机支付
// 	 								alipay_wap:支付宝手机网页支付
// 	 								alipay_qr:支付宝扫码支付
// 	 								alipay_pc_direct:支付宝 PC 网页支付
// 	 								bfb:百度钱包移动快捷支付
// 	 								bfb_wap:百度钱包手机网页支付
// 	 								upacp:银联全渠道支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
// 	 								upacp_wap:银联全渠道手机网页支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
// 	 								upacp_pc:银联 PC 网页支付
// 	 								cp_b2b:银联企业网银支付
// 	 								wx:微信支付
// 	 								wx_pub:微信公众账号支付
// 	 								wx_pub_qr:微信公众账号扫码支付
// 	 								yeepay_wap:易宝手机网页支付
// 	 								jdpay_wap:京东手机网页支付
// 	 								cnp_u:应用内快捷支付（银联）
// 	 								cnp_f:应用内快捷支付（外卡）
// 	 								applepay_upacp:Apple Pay
// 	 								fqlpay_wap:分期乐支付
// 	 								qgbc_wap:量化派支付
// 	 								cmb_wallet:招行一网通
// 	 							 */
// 	 							//2.获取charge对象
// 	 							Charge charge = ChargeExample.getPay(order_id, Double.parseDouble(money)*100,ip,pay_way,"3",2);
// 	 							if(charge== null){
//	 	 								result="0";
//	 	 								message="充值失败";
//	 	 								map.put("data", "");
// 	 							}else{
// 	 									map.put("data", charge);
// 	 							}
//    		}catch(Exception e){
//			result="0";
//			message="系统异常";
//			map.put("data", e.toString());
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message", message);
//    	return map;
//	}
//	
//	
//
//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
//	}
//	
//	@Resource(name="appOrderService")
//	private AppOrderService appOrderService;
//	@Resource(name="appMember_wealthhistoryService")
//	private AppMember_wealthhistoryService appMember_wealthhistoryService;
//	@Resource(name="appMember_redpacketsService")
//	private AppMember_redpacketsService appMember_redpacketsService;
//	@Resource(name="appStore_redpacketsService")
//	private AppStore_redpacketsService appStore_redpacketsService;
//	@Resource(name="appPay_historyService")
//	private AppPay_historyService appPay_historyService;
//	
//	 
//		
//		
//		/*
//		 * 获取IP
//		 */
//		public static String getIp(HttpServletRequest request) {
//			String ipAddress = null;
//			// ipAddress = this.getRequest().getRemoteAddr();
//			ipAddress = request.getHeader("x-forwarded-for");
//			if (ipAddress == null || ipAddress.length() == 0
//					|| "unknown".equalsIgnoreCase(ipAddress)) {
//				ipAddress = request.getHeader("Proxy-Client-IP");
//			}
//			if (ipAddress == null || ipAddress.length() == 0
//					|| "unknown".equalsIgnoreCase(ipAddress)) {
//				ipAddress = request.getHeader("WL-Proxy-Client-IP");
//			}
//			if (ipAddress == null || ipAddress.length() == 0
//					|| "unknown".equalsIgnoreCase(ipAddress)) {
//				ipAddress = request.getRemoteAddr();
//				if (ipAddress.equals("127.0.0.1")) {
//					// 根据网卡取本机配置的IP
//					InetAddress inet = null;
//					try {
//						inet = InetAddress.getLocalHost();
//					} catch (UnknownHostException e) {
//						e.printStackTrace();
//					}
//					ipAddress = inet.getHostAddress();
//				}
//
//			}
//	 		 //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//			 if(ipAddress!=null && ipAddress.length()>15){
//			 //"***.***.***.***".length() = 15
//			 if(ipAddress.indexOf(",")>0){
//			 ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
//			 }
//			 }
//			return ipAddress;
//		}
//		
//		@Resource(name="appWithdraw_approvalService")
//		private AppWithdraw_approvalService appWithdraw_approvalService;
//		@Resource(name="appMemberService")
//		private AppMemberService appMemberService;
//		@Resource(name="appStoreService")
//		private AppStoreService appStoreService;
//		@Resource(name = "storepc_wealthhistoryService")
//		private Storepc_wealthhistoryService wealthhistoryService;
//		@Resource(name = "storepc_bankcardService")
//		private Storepc_bankcardService bankcardService;
//		
//		
//		/**
//		 * 商家新增提现记录魏汉文20160704
//		 */
//		@RequestMapping(value="/saveWithdraw")
//		@ResponseBody
//		public Object saveWithdraw() throws Exception{
//			logBefore(logger, "新增Withdraw_approval");
//			Map<String,Object> map = new HashMap<String,Object>();
//			DecimalFormat    df   = new DecimalFormat("######0.00"); 
//			String result = "1";
//			String message="提现审批，请等待1至2个工作日";
//			PageData pd = new PageData();
//			pd = this.getPageData();
//			try{
//							//判断今天是否已经申请提现过
//							String pay_type="";
//			 				String day=DateUtil.getDay();
//			 				String account_type=pd.getString("account_type");
//			 				String withdraw_channel=pd.getString("withdraw_channel");//1-会员app端，2-商家app端，3-pc端，4-微信公众号端
//			 				if(withdraw_channel == null || withdraw_channel.equals("")){
//			 					withdraw_channel="2";
//			 				}
//			 				//判断是否有操作员
//			 				if(pd.getString("store_operator_id")==null || pd.getString("store_operator_id").equals("")){
//			 					pd.put("store_operator_id", "jy"+pd.getString("user_id"));
//			 				}
//			 				pd.put("day", day);
//			 				logger.info("&&&&&&提现"+pd.toString());
//			 				logger.info("&&&&&&"+appWithdraw_approvalService.listAll(pd).size());
//							if(appWithdraw_approvalService.listAll(pd).size() != 0){
//								map.put("result", "0");
//								map.put("message", "今日你已提现过");
//								map.put("data", "");		
//								return map;
//							}
////							//获取登录密码
////							String pass = wealthhistoryService.getPass(pd);
////							if(pd.getString("pass") != null && pass != null && !pass.equals(pd.getString("pass"))){
////								map.put("result", "0");
////								map.put("message", "密码错误");
////								map.put("data", "");		
//// 								return map;
////							}
//							PageData e=new PageData();
//							double money=Double.parseDouble(pd.getString("money"));
//  							String store_id=pd.getString("user_id");
//  							e.put("store_id", store_id);
//  							pd.put("store_id", store_id);
// 							//获取商家的银行卡号
// 							if(account_type.equals("4") && pd.getString("store_bankcard_id") != null && !pd.getString("store_bankcard_id").equals("") ){
// 									PageData bankpd=appStoreService.findBankById(pd);
// 									pd.put("account_name", bankpd.getString("account"));
// 									pd.put("withdraw_number", bankpd.getString("bank_number"));
// 									pd.put("withdraw_username", bankpd.getString("bankcard_name"));
// 									pd.put("account_type", account_type);
// 									pay_type="unionpay";
//  							}else if(account_type.equals("3") && pd.getString("store_alipay_id") != null && !pd.getString("store_alipay_id").equals("")){
//									PageData alipaypd=bankcardService.findbyAlipay(pd);
//									pd.put("account_name", "支付宝");
//									pd.put("withdraw_number", alipaypd.getString("alipay_number"));
//									pd.put("withdraw_username", alipaypd.getString("alipay_name"));
//									pd.put("account_type", account_type);
//									pay_type="alipay";
//							}else{
// 								map.put("result", "0");
// 								map.put("message", "请选择银行卡号");
// 								map.put("data", "");		
// 								return AppUtil.returnObject(pd, map);
// 							}
//  							//获取商家总财富
// 							String now_wealth=appStoreService.sumStoreWealth(pd); 
//   							double allnow_wealth=Double.parseDouble(now_wealth);
// 	 						if(allnow_wealth < money){
//									result="0";
//									message="余额不足";
//									map.put("data", "");
//							}else{
//									double n=allnow_wealth-money;
//									PageData e1=new PageData();
//									e1=appStoreService.findById(e);
// 									String jiaoyi_id=this.getTXUID();
// 									//新增提现记录
// 									pd.put("phone", e1.getString("registertel_phone"));
//									pd.put("province_name", e1.getString("province_name"));
//									pd.put("city_name", e1.getString("city_name"));
//									pd.put("area_name", e1.getString("area_name"));
//									pd.put("withdraw_type", "1");
//									//提现费率
//									String  withdraw_rate=e1.getString("withdraw_rate");
//									double koufei=1-Double.parseDouble(withdraw_rate)/100;
//									String arrivalMoney=df.format(koufei*money);
//									pd.put("withdraw_rate", withdraw_rate );
//									pd.put("arrivalMoney", arrivalMoney );
//									pd.put("withdraw_channel", withdraw_channel );
//									pd.put("withdraw_approval_id", jiaoyi_id);
//									pd.put("nowuser_money", df.format(n));
// 		 							appWithdraw_approvalService.saveWithdraw(pd);
//		 							//新增提现次数
//		 							pd.put("withdraw_times", "1");
//		 							appStoreService.edit(pd);
//		 							//更新未到账的金额
//		 							String tixian_money=e1.getString("tixian_money");
//		 							if(tixian_money == null || tixian_money.equals("")){
//		 								tixian_money="0";
//		 							}
//		 							pd.put("tixian_money", df.format(Double.parseDouble(tixian_money)+money));
//		 							appStoreService.edit(pd);
//		 							//新增商家财富历史记录
//			 			   			pd.put("wealth_type", "1");
//		 			   				pd.put("profit_type", "1");
//		 			   				pd.put("number",df.format(-money));
//		 			   				pd.put("store_id", store_id);
//		 			   				pd.put("store_operator_id", pd.getString("store_operator_id"));
//		 			   				pd.put("process_status", "0");
//		 			   				pd.put("pay_type", pay_type);
//			 			   			pd.put("last_wealth", df.format( n));
//			 			   			pd.put("arrivalMoney", arrivalMoney);//实际到账金额
//			 			   			pd.put("jiaoyi_id", jiaoyi_id);
//			 			   			pd.put("user_id", Const.jiuyunumber);
//		 			   				pd.put("store_wealthhistory_id", jiaoyi_id);
//		 			   				appStoreService.saveWealthhistory(pd);
//		 			   				//减少商家积分 
//		 			   				PageData editpd=new PageData();
//		 			   				editpd.put("store_id", store_id);
//		 			   				editpd.put("now_wealth", df.format(allnow_wealth-money));
//		 			   				editpd.put("wealth_type", "1");
//									appStoreService.editWealthById(editpd);
// 	 						}
// 	 		}catch(Exception e){
//					result="0";
//					message="系统异常";
//					map.put("data", "");
//					System.out.println(e.toString());
//					logger.error(e.toString(), e);
//			}
//			map.put("result", result);
//			map.put("message", message);
//			map.put("data", "");		
//			return map;
//		}
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//}
