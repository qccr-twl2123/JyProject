//package com.tianer.controller.storepc.app.xjt.payapp_history;
//
//import java.rmi.server.ObjID;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
//import com.tianer.controller.app.tongyongUtil.TongYong;
//import com.tianer.controller.base.BaseController;
//import com.tianer.entity.zhihui.StoreRole;
//import com.tianer.service.app.AppGoodsService;
//import com.tianer.service.app.AppMemberService;
//import com.tianer.service.app.AppMember_redpacketsService;
//import com.tianer.service.app.AppMember_wealthhistoryService;
//import com.tianer.service.app.AppOrderService;
//import com.tianer.service.app.AppPay_historyService;
//import com.tianer.service.app.AppStoreService;
//import com.tianer.service.app.AppStore_redpacketsService;
//import com.tianer.service.app.AppStorepc_marketingService;
//import com.tianer.service.business.clerk_file.Clerk_fileService;
//import com.tianer.service.business.service_performance.Service_performanceService;
//import com.tianer.service.business.sp_file.Sp_fileService;
//import com.tianer.service.business.store.StoreService;
//import com.tianer.service.business.subsidiary.SubsidiaryService;
//import com.tianer.service.memberPc.shopCar.ShopCarService;
//import com.tianer.service.storepc.app.xjt.Payapp_historyService;
//import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
//import com.tianer.service.storepc.store_discountway.Storepc_discountwayService;
//import com.tianer.service.storepc.store_marketingeffect.Storepc_marketingeffectService;
//import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
//import com.tianer.service.storepc.store_redpackets.Storepc_redpacketsService;
//import com.tianer.service.storepc.store_scoreway.Storepc_scorewayService;
//import com.tianer.service.storepc.tableNumber.TablerNumberService;
//import com.tianer.util.AppUtil;
//import com.tianer.util.Const;
//import com.tianer.util.DateUtil;
//import com.tianer.util.MD5;
//import com.tianer.util.PageData;
//import com.tianer.util.ServiceHelper;
//import com.tianer.util.StringUtil;
//
//
///** 
// * 
//* 类名称：Payapp_historyController   
//* 类描述：   收银记录app
//* 创建人：邢江涛
//* 创建时间：2016年7月4日 
// */
//@Controller("Payapp_historyController")
//@RequestMapping(value="/storeapp_payhHstory")
//public class CopyOfPayapp_historyController extends BaseController{
//	
//	@Resource(name="payapp_historyService")
//	private Payapp_historyService payapp_historyService;
//	@Resource(name="appPay_historyService")
//	private AppPay_historyService appPay_historyService;
//	@Resource(name="service_performanceService")
//	private Service_performanceService service_performanceService;
//	
//	//-----------------------------------------
//	
//
//	/**
//	 * 获取用户的信息通过手机号码
//	 * 魏汉文20160620
//	 */
//	@RequestMapping(value="/getInforByPhone")
//	@ResponseBody
//	public Object getInforByPhone(){
//		logBefore(logger, " 获取用户的信息通过手机号码");
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
// 			pd=appMemberService.getIntegerByPhone(pd);
//			if(pd == null){
//				pd=new PageData();
//				pd.put("member_id", "0");
//				pd.put("now_integral", "0");
//				pd.put("now_money", "0");
//				message="手机号不存在";
//			}
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", pd);
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	/**
//	 * 获取大类的分类
//	 * 魏汉文20160620
//	 */
//	@RequestMapping(value="/getLeibiePay")
//	@ResponseBody
//	public Object getLeibiePay(){
//		logBefore(logger, " 获取大类的分类");
//		Map<String,Object> map = new HashMap<String,Object>();
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{ 
//			pd=this.getPageData();
////			//判断是否开通类别积分购买的权限
////			PageData issortjfpd=appStorepc_marketingService.getJfById(pd);
////			if(issortjfpd == null || !issortjfpd.getString("change_type").equals("2") ){
////				 map1.put("issortjf", "0");
////			}else{
////				 map1.put("issortjf", "1");
////			}
////			//获取商家的营销规则明细
////			PageData yxpd=markeingAll(pd);
////			map1.put("yxpd", yxpd);
////			//获取大类
//			List<PageData> leibieList=appGoodsService.listAllBigSort(pd);
//			for(PageData  e : leibieList){
//				e.put("price", "0");
//			}
////			map1.put("leibieList", leibieList);
////			map.put("data", map1);
//			map.put("data", leibieList);
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
// 		return AppUtil.returnObject(pd, map);
//	}
//	
//	
//	/**
//	 * 获取大类的分类
//	 * 魏汉文20160620
//	 */
//	@RequestMapping(value="/getLeibiePayTwo")
//	@ResponseBody
//	public Object getLeibiePayTwo(){
//		logBefore(logger, " 获取大类的分类");
//		Map<String,Object> map = new HashMap<String,Object>();
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		List<PageData> noList = new ArrayList<PageData>();//操作员所对应桌号
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{ 
//			pd=this.getPageData();
////			//判断是否开通类别积分购买的权限
//			PageData ispd=appStorepc_marketingService.getJfById(pd);
//			if(ispd != null && ispd.getString("change_type").equals("3") ){
//				 map1.put("issortjf", "3");
//			}else if(ispd != null && ispd.getString("change_type").equals("2") ){
//				 map1.put("issortjf", "1");
//			} else{
//				 map1.put("issortjf", "0");
//			}
////			//获取商家的营销规则明细
//			PageData yxpd=markeingAll(pd);
//			map1.put("yxpd", yxpd);
//			yxpd=null;
////			//获取大类
//			List<PageData> leibieList=appGoodsService.listAllBigSort(pd);
//			for(PageData  e : leibieList){
//				e.put("price", "0");
//			}
//			map1.put("leibieList", leibieList);
//			leibieList=null;
//			//操作员登录
//			String store_operator_id = pd.getString("store_operator_id");
//			if(store_operator_id != null && !store_operator_id.trim().equals("")){
//				PageData pg = new PageData();
//				pg = storeManageService.findOperatorById(pd);
//				if(pg != null && !pg.equals("")){
//					String alldesk_no = pg.getString("alldesk_no");
//					if(alldesk_no != null && !alldesk_no.equals("")){
//						String[] no = alldesk_no.split(",");
//						for (int i = 0; i < no.length; i++) {
//							PageData e = new PageData();
//							e.put("table_name", no[i]);
//							noList.add(e);
//						}
//					}
//				}
//				pg=null;
//			}else{
//				//商家登录
//				String store_id = pd.getString("store_id");
//				if(store_id != null && !store_id.equals("")){
//					//获取改商家桌号
//					noList = tablerNumberService.listAll(pd);
//				}
//			}
// 			map1.put("noList", noList);
//			noList=null;
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", map1);
// 		return AppUtil.returnObject(pd, map);
//	}
//	
//
//	/**
//	 * 订单的历史收银记录
//	 * 魏汉文20160706
//	 */
//	@RequestMapping(value="/confirmedHistory")
//	@ResponseBody
//	public Object confirmedHistory(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{
//				pd = this.getPageData();
//				if(pd.getString("store_operator_id") != null  && !pd.getString("store_operator_id").equals("")){
//					pd.put("store_operator_id", pd.getString("store_operator_id").trim());
//				}
// 				pd.put("order_status", "1");
//				List<PageData> varList = payapp_historyService.confirmedHistory(pd);
//				for(PageData e: varList){
//					//优惠项
//	 	 			List<PageData> discountListone =new ArrayList<PageData>();
//	 	 			String discount_content=e.getString("discount_content");
//	 	 			if(discount_content.contains(",")){
//	 	 					String[] str=discount_content.split(",");
//		   					for(int i=0;i<str.length ; i++){
//		   					    	 PageData  dispd=new PageData();
//		   						  	 String[] str1=str[i].split("@");
////		   						  	 System.out.println(str1);
//			   						 dispd.put("content", str1[0]);
//			   						 dispd.put("number", str1[2]);
//			   						discountListone.add(dispd);
//			   						dispd=null;
//		 	 			 }
//	 	 			}	
//	 	 			e.put("discountList", discountListone);
//	 	 			discountListone=null;
//				}
//				map1.put("oneList", varList);
//				pd.put("order_status", "2");
//				varList=null;
//				varList = payapp_historyService.confirmedHistory(pd);
//				for(PageData e: varList){
//					//优惠项
//	 	 			List<PageData> discountListtwo =new ArrayList<PageData>();
//	 	 			String discount_content=e.getString("discount_content");
//	 	 			if(discount_content.contains(",")){
//	 	 					String[] str=discount_content.split(",");
//		   					for(int i=0;i<str.length ; i++){
//		   					    	 PageData  dispd=new PageData();
//		   						  	 String[] str1=str[i].split("@");
//			   						 dispd.put("content", str1[0]);
//			   						 dispd.put("number", str1[2]);
//			   						 discountListtwo.add(dispd);
//			   						 dispd=null;
//		 	 			 }
//	 	 			}	
//	 	 			e.put("discountList", discountListtwo);
//	 	 			discountListtwo=null;
//				}
//				map1.put("twoList", varList);
//    	} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", map1);
//  		return map;
//	}
//	
//	/**
//	 * 订单的历史收银详情 魏汉文20160706
//	 */
//	@RequestMapping(value="/confirmedFindById")
//	@ResponseBody
//	public Object confirmedFindById(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			String order_id=pd.getString("order_id");
//			if(order_id==null ||  order_id.equals("") ){
// 					map.put("data", "");
//  			}else{
//				pd = payapp_historyService.confirmedFindById(pd);
//				List<PageData> varList =new ArrayList<PageData>();
//	 			if(pd.getString("pay_sort_type").equals("2")){//按总金额购买
//	 					//获取类别购买
//	 					 varList=null;
//	 					 varList = payapp_historyService.orderSortList(pd);
//	 			}
// 	 			pd.put("sortList", varList);
// 	 			//优惠项
// 	 			List<PageData> discountList =new ArrayList<PageData>();
// 	 			String discount_content=pd.getString("discount_content");
// 	 			if(discount_content.contains(",")){
// 	 					String[] str=discount_content.split(",");
//	   					for(int i=0;i<str.length ; i++){
//	   					    	 PageData  dispd=new PageData();
//	   						  	 String[] str1=str[i].split("@");
//		   						 dispd.put("content", str1[0]);
//		   						 dispd.put("number", str1[2]);
//		   						 discountList.add(dispd);
//		   						dispd=null;
//	 	 			 }
// 	 			}	
// 	 			pd.put("discountList", discountList);
// 	 			discountList=null;
//	 			map.put("data", pd);
//			}
//    	} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//			map.put("data", "");
//		}
//		map.put("result", result);
//		map.put("message", message);
//  		return map;
//	}
//	
//	
//	@Resource(name="appMember_wealthhistoryService")
//	private AppMember_wealthhistoryService appMember_wealthhistoryService;
//	
// 	@Resource(name="clerk_fileService")
//	private Clerk_fileService clerk_fileService;
// 	@Resource(name="subsidiaryService")
//	private SubsidiaryService subsidiaryService;
//	@Resource(name="sp_fileService")
//	private Sp_fileService sp_fileService;
// 	
// 	
//	/**
//	 * 订单确认收银  魏汉文20160706
//	 */
//	@RequestMapping(value="/sureConfirmed")
//	@ResponseBody
//	public Object sureConfirmed(){
//		Map<String,Object> map = new HashMap<String,Object>();
//	    DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		String result = "1";
//		String message="收银成功";
//		PageData pd = new PageData();
//		try{
//				pd = this.getPageData();
//				String channel="nowpay";
//				String redpackage_id=pd.getString("redpackage_id");//红包ID
//				String store_redpackets_id=pd.getString("store_redpackets_id");//赠送红包ID
//				String allgoodsid=pd.getString("allgoodsid");//所有订单里的商品
//				String sale_money=pd.getString("sale_money");//消费的总金额
//				String actual_money=pd.getString("actual_money");//支付的金额（现金或者第三方）
//				String user_balance=pd.getString("user_balance");//使用的余额
//				String user_integral=pd.getString("user_integral");//使用的积分
//				String get_integral=pd.getString("get_integral");//获得的积分
//				String discount_money=pd.getString("discount_money"); 
//				String no_discount_money=pd.getString("no_discount_money"); 
//				if(sale_money.equals("")){
//					sale_money="0";
//					pd.put("sale_money", sale_money);
//				}
//				if(get_integral.equals("")){
//					get_integral="0";
//					pd.put("get_integral", get_integral);
//				}
//				if(user_integral.equals("")){
//					user_integral="0";
//					pd.put("user_integral", user_integral);
//				}
//				if(user_balance.equals("")){
//					user_balance="0";
//					pd.put("user_balance", user_balance);
//				}
//				if(user_balance.equals("")){
//					user_balance="0";
//					pd.put("user_balance", user_balance);
//				}
//				if(discount_money.equals("")){
//					discount_money="0";
//					pd.put("discount_money", discount_money);
//				}
//				if(no_discount_money.equals("")){
//					no_discount_money="0";
//					pd.put("no_discount_money", no_discount_money);
//				}
// 				if(pd.getString("order_id") == null || pd.getString("order_id").equals("")){
//			 				System.out.println("进来收银---直接收银============================");
//			 				 String order_id=this.getTimeID();
// 							pd.put("order_id", order_id);
//							pd.put("look_number", order_id);
//			 				//判断金钱是否符合
//							System.out.println("==============================判断金钱是否符合");
//							if(sale_money.equals("0") || !StringUtil.checkMoney(sale_money) ||  !StringUtil.checkMoney(user_balance) || !StringUtil.checkMoney(user_integral) ){
//									map.put("result", "0");
//									map.put("message", "金钱格式有误/总金额不能为0");
//									map.put("data", "");
//							    	return map;
//							}
//							PageData mpd=new PageData();
//				 			mpd=appMemberService.findById(pd);//用户详情
//				 			if(mpd == null){
//					 				map.put("result", "0");
//					 				map.put("message", "用户id不能为空");
//					 				map.put("data", "");
//					 		    	return map;
//				 			}
//				 			System.out.println("==============================判断余额是否充足");
//				 			//判断余额
//							if(!user_balance.equals("")){
//				   				double now_money=Double.parseDouble(mpd.getString("now_money"));
//								double n=Double.parseDouble(user_balance);
//				 				if(now_money < n){
//				 						map.put("result", "0");
//										map.put("message", "余额不足，当前余额"+now_money);
//										map.put("data", "");
//								    	return map;
//								} 
//				 			}
//							System.out.println("==============================判断积分是否充足");
//							//判断积分
//							if(!user_integral.equals("")){
//				   				double now_integral=Double.parseDouble(mpd.getString("now_integral"));
//				 				double m=Double.parseDouble(user_integral);
//								if(now_integral < m){
//				 						    map.put("result", "0");
//											map.put("message", "积分不足，当前积分"+now_integral);
//											map.put("data", "");
//									    	return map;
//								} 
//				 			}
//							//判断商家的赠送积分是否充足
//							System.out.println("==============================判断商家的赠送积分是否充足");
//							double storeintegral=Double.parseDouble(user_integral)-Double.parseDouble(get_integral)-Double.parseDouble(get_integral)*Const.orderShouyiMoney[0];
//							PageData spd=appStoreService.findById(pd);
//							spd.put("wealth_type", "1");//1-积分，2-金钱
//							PageData 	ismoneypd=appStoreService.findWealthById(spd);
//							double isnow_wealth=Double.parseDouble(ismoneypd.getString("now_wealth"));
//							if(isnow_wealth+storeintegral  < 0){
//									map.put("result", "0");
//									map.put("message", "商家积分余额不足，请商家充值后再购买");
//									map.put("data", "");
//							    	return map;
//							}
//	 						ismoneypd=null;
//	 						//新增订单
// 	 						TongYong.historyByOrder(pd, channel, "1");
// 	 						
// 
////			 				if(mpd != null){
////			 					double now_money=Double.parseDouble(mpd.getString("now_money"));
////			 					double n=Double.parseDouble(user_balance);
////			 					double acmoney=Double.parseDouble(actual_money);
////			 					if(n >0){
////			 							//更新会员个人余额信息消费次数
////			 			   				PageData countpd=new PageData();
////			 			   				countpd.put("pay_way", "nowmoney");
////			 				   			countpd.put("member_id", mpd.getString("member_id"));
////			 			   			    logger.info("更新会员积分信息");
////			 			   				appMemberService.updateMemberById(countpd);  
////			 			   				//个人余额消费历史
////			 			   				now_money=now_money-n;
////			 							PageData moneypd=new PageData();
////			 							moneypd.put("member_id", pd.getString("member_id"));
////			 		 					moneypd.put("wealth_type", "2");
////			 							moneypd.put("consume_type", "2");
////			 							moneypd.put("content", "现金消费支出");
////			 							moneypd.put("number", df.format(n));
////			 							moneypd.put("now_money", df.format(now_money));
////			 							appMember_wealthhistoryService.saveWealthhistory(moneypd);
////			 							//更新金钱
////			 							appMemberService.edit(moneypd);
////			 							//商家更新余额
////			 				 			spd.put("wealth_type", "2");
////			 							PageData spdwithpd=appStoreService.findWealthById(spd);
////			 							double now_wealth=Double.parseDouble(spdwithpd.getString("now_wealth"));
////			 							spdwithpd.put("now_wealth", df.format(now_wealth+n+acmoney));
////			 							appStoreService.editWealthById(spdwithpd);
////			 					}
////			 					//新增用户积分消费记录，新增商家积分
////			 					System.err.println("新增用户积分消费记录，新增商家积分===========================");
////			 					double now_integral=Double.parseDouble(mpd.getString("now_integral"));
////			 					PageData moneypd=new PageData();
////			 				    double m=Double.parseDouble(user_integral);
////			 					if(n  > 0){
////			 							  	now_integral=now_integral-m;
////			 							  	//更新个人积分信息次数
////			 							  	PageData countpd=new PageData();
////			 							  	countpd.put("pay_way", "integralmoney");
////			 							  	countpd.put("member_id",pd.getString("member_id"));
////			 			  					appMemberService.updateMemberById(countpd); 
////			 			  					//更新积分
////			 			  					moneypd.put("member_id", pd.getString("member_id"));
////			 								moneypd.put("now_integral", df.format(now_integral));
////			 								appMemberService.edit(moneypd);
////			 								//新增个人积分消费历史
////			 								  moneypd.put("wealth_type", "1");
////			 								  moneypd.put("consume_type", "2");
////			 								  moneypd.put("content", spd.getString("store_name")+"消费抵用");
////			 								  moneypd.put("number", df.format(m));
////			 								  moneypd.put("now_money", df.format(now_integral));
////			 			 					 appMember_wealthhistoryService.saveWealthhistory(moneypd);
////			 		 					 	//商家更新积分
////			 					 			spd.put("wealth_type", "1");
////			 								PageData spdwithpd=appStoreService.findWealthById(spd);
////			 								double now_wealth=Double.parseDouble(spdwithpd.getString("now_wealth"));
////			 								spdwithpd.put("now_wealth", df.format(now_wealth+m));
////			 								appStoreService.editWealthById(spdwithpd);
////			 					}
////			 					//新增商家财富历史记录----------
////				   				PageData swpd=new PageData();
////				   				swpd.put("wealth_type", "2");
////				   				swpd.put("profit_type", "3");
////				   				swpd.put("number", df.format(acmoney+n+m));
////				   				swpd.put("store_id", spd.getString("store_id"));
////				   				swpd.put("store_operator_id", pd.getString("store_operator_id"));
////				   				swpd.put("process_status", "1");
////				   				swpd.put("pay_type", "nowpay");
////				   				swpd.put("jiaoyi_id",pd.getString("order_id") );
////				   				swpd.put("user_id", mpd.getString("member_id"));
////				   				swpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
////				   				swpd.put("store_wealthhistory_id",this.getTimeID());
////				   				appStoreService.saveWealthhistory(swpd);
//// 					 			System.out.println("新增用户积分获赠记录============================");
////					 			//新增用户积分获赠记录
////								if(get_integral != null &&  !get_integral.equals("") && Double.parseDouble(get_integral) >0 ){
////									//会员获赠积分记录
////									moneypd=null;
////									moneypd=new PageData();
////				 	 				double g=Double.parseDouble(get_integral);
////				 	 				now_integral=now_integral+g;
////				 	 				//更新积分
////				 	 				System.err.println("更新积分 ===========================");
////									moneypd.put("member_id", pd.getString("member_id"));
////									moneypd.put("now_integral", df.format(now_integral));
////									appMemberService.edit(moneypd);
////					 				moneypd.put("wealth_type", "1");
////									moneypd.put("consume_type", "1");
////									moneypd.put("content", spd.getString("store_name")+" 消费获赠");
////									moneypd.put("number", df.format(g));
////									moneypd.put("now_money", df.format(now_integral));
////				 					appMember_wealthhistoryService.saveWealthhistory(moneypd);//新增个人消费历史
////				  					//新增业务员5/服务商20/父级人脉20/爷级人脉20/子公司35财富---------------------------------------------------------
////						   				double xtmoney=0;
////						   				try {
////						   						PageData mmpd=new PageData();
////						   						xtmoney=g*Const.orderShouyiMoney[0];//系统获取的积分
////						   						double lessmoneyxtmoney=xtmoney;
////						   						pd.put("sendxitong_integral", df.format(xtmoney));
////					 			   				//梗新商家积分
////						   						spd.put("wealth_type", "1");
////						 			   			moneypd=appStoreService.findWealthById(spd);
////						 			   			double now_wealth=Double.parseDouble(moneypd.getString("now_wealth"))-g;
////						 			   			moneypd.put("now_wealth", df.format(now_wealth));
////					   							appStoreService.editWealthById(moneypd);
////						 			   			//新增商家赠送积分记录
////					 			   			    PageData wpd=new PageData();
////							 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
////				//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，4-现金支付的金额，5-第三方支付的金额，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
////								 			    wpd.put("profit_type", "7");
////								 			    wpd.put("number",df.format( g));
////								 			    wpd.put("store_id", spd.getString("store_id"));
////									 			wpd.put("store_operator_id", pd.getString("store_operator_id"));
////								 			    wpd.put("process_status", "1");
////								 			    wpd.put("pay_type",channel);
////								 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
////								 			    wpd.put("user_id", mpd.getString("member_id"));
////								 			    wpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
////								 			    wpd.put("store_wealthhistory_id", this.getTimeID());
////						 			   			appStoreService.saveWealthhistory(wpd);
////						 			   			//新增商家返系统积分记录
////						 			   			now_wealth=now_wealth-xtmoney;
////					   			 			   	moneypd.put("now_wealth", df.format(now_wealth));
////					   							appStoreService.editWealthById(moneypd);
////						 			   			wpd=null;
////					 			   			    wpd=new PageData();
////							 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
////				//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
////								 			    wpd.put("profit_type", "11");
////								 			    wpd.put("number",df.format( xtmoney));
////								 			    wpd.put("store_id", spd.getString("store_id"));
////									 			wpd.put("store_operator_id", pd.getString("store_operator_id"));
////								 			    wpd.put("process_status", "1");
////								 			    wpd.put("pay_type",channel);
////								 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
////								 			    wpd.put("user_id", mpd.getString("member_id"));
////								 			    wpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
////								 			    wpd.put("store_wealthhistory_id", this.getTimeID());
////					 			   				appStoreService.saveWealthhistory(wpd);
////					 			   				//服务商
////					 			   				PageData sppd=new PageData();
////					 			   				sppd=sp_fileService.findById(spd);
////							            	    double nowmoney=0;
////							            	    if(sppd != null && sppd.getString("nowmoney") != null && !sppd.getString("nowmoney").equals("")){
////							            	    	nowmoney=Double.parseDouble(sppd.getString("nowmoney"));
////							            	    }
////							            	    pd.put("sp_file_id", sppd.getString("sp_file_id"));
////							            	    pd.put("sp_getmoney", df.format(xtmoney*Const.orderShouyiMoney[2]));
////							            	    if(xtmoney*Const.orderShouyiMoney[2] >0){
////							            		   nowmoney+=xtmoney*Const.orderShouyiMoney[2];
////								            	   sppd.put("nowmoney", df.format(nowmoney));
////								            	   sp_fileService.edit(sppd);
////								            	   lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[2];
////								            	    //添加一个提现记录
////								 	 				mmpd.put("profit_name", sppd.getString("team_name"));//收益对象
////								 	 				mmpd.put("yewu_name", mpd.getString("name"));//业务对象
////								 	 				mmpd.put("yewu_id", mpd.getString("member_id"));//业务对象
////								 	 				mmpd.put("yewu_type", "2");//业务对象
////								 	 				mmpd.put("money",  df.format(xtmoney*Const.orderShouyiMoney[2]));//金额
////								 	 				mmpd.put("money_type", "2");//1、销售提成: 2、积分收益： 3、平台奖励 
////								 	 				mmpd.put("operate_type", "1"); //1-服务商，2-业务员
////								 	 				mmpd.put("operate_id", sppd.getString("sp_file_id")); 
////								 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
////								 	 				mmpd.put("service_performance_id", this.get10UID());//收益对象
////								 	 				service_performanceService.save(mmpd);
////						            	   }
////				 			            	    //业务员
////							            	   PageData clerkpd=new PageData();
////							            	   clerkpd=clerk_fileService.findById(spd);
////							            	   nowmoney=0;
////							            	    if( clerkpd != null && clerkpd.getString("nowmoney") != null &&  !clerkpd.getString("nowmoney").equals("")){
////							            	    	nowmoney=Double.parseDouble(clerkpd.getString("nowmoney"));
////							            	    }
////							            	   pd.put("clerk_file_id", clerkpd.getString("clerk_file_id"));
////							            	   pd.put("clerk_getmoney", df.format(xtmoney*Const.orderShouyiMoney[1]));
////							            	   if(xtmoney*Const.orderShouyiMoney[1] > 0){
////							            		  nowmoney+=xtmoney*Const.orderShouyiMoney[1];
////								            	   clerkpd.put("nowmoney", df.format(nowmoney));
////								            	   clerk_fileService.edit(clerkpd);
////								            	   lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[1];
////								            	  //添加一个提现记录
////											 		mmpd=null;
////								 	 				mmpd=new PageData();
////								 	 				mmpd.put("profit_name", clerkpd.getString("clerk_name"));//收益对象
////								 	 				mmpd.put("yewu_name", mpd.getString("name"));//业务对象
////								 	 				mmpd.put("yewu_id", mpd.getString("member_id"));//业务对象
////								 	 				mmpd.put("yewu_type", "2");//业务对象
////								 	 				mmpd.put("money",  df.format(xtmoney*Const.orderShouyiMoney[1]));//金额
////								 	 				mmpd.put("money_type", "2");//1、销售提成: 2、积分收益： 3、平台奖励 
////									 	 			mmpd.put("operate_type", "2"); //1-服务商，2-业务员
////								 	 				mmpd.put("operate_id", clerkpd.getString("clerk_file_id")); 
////								 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
////								 	 				mmpd.put("service_performance_id", this.get10UID());//收益对象
////								 	 				service_performanceService.save(mmpd);
////							            	   }
////							            	   //会员的父级人脉
////							            	   PageData parentpd=new PageData();
////							            	   String recommended_type=mpd.getString("recommended_type");
////							            	   String recommended=mpd.getString("recommended");
////							            	   if(recommended_type.equals("1")){//商家的积分
////							            		   	xtmoney=xtmoney-xtmoney*Const.orderShouyiMoney[3];
////							            		   	lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[3];
////							            		   	pd.put("onecontacts_id", recommended);
////							            		   	pd.put("onecontacts_type", "1");
////									            	pd.put("onecontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[3]));
////									            	pd.put("twocontacts_id", "0");
////							            		   	pd.put("twocontacts_type", "0");
////									            	pd.put("twocontacts_getmoney", "0");
////									            	//新增商家收益积分记录
////										            now_wealth=now_wealth+xtmoney*Const.orderShouyiMoney[3];
////						   			 			   	moneypd.put("now_wealth", df.format(now_wealth));
////						   							appStoreService.editWealthById(moneypd);
////						 			   				wpd=null;
////						 			   			    wpd=new PageData();
////								 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
////				//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
////									 			    wpd.put("profit_type", "12");
////									 			    wpd.put("number",df.format( xtmoney*Const.orderShouyiMoney[3]));
////									 			    wpd.put("store_id", spd.getString("store_id"));
////										 			wpd.put("store_operator_id", pd.getString("store_operator_id"));
////									 			    wpd.put("process_status", "1");
////									 			    wpd.put("pay_type", channel);
////									 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
////									 			    wpd.put("user_id", mpd.getString("member_id"));
////									 			    wpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
////									 			    wpd.put("store_wealthhistory_id", this.getTimeID());
////						 			   				appStoreService.saveWealthhistory(wpd);
////				 			            	   }else if(recommended_type.equals("2")){
////				 			            		    pd.put("onecontacts_id", recommended);
////				 			            		    pd.put("onecontacts_type", "2");
////				 			            		    pd.put("onecontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[3]));
////									            	double parentIntegral=0;
////									            	//更新会员积分
////							            		    if(xtmoney*Const.orderShouyiMoney[3] >0){
////							            		    	parentpd.put("member_id", recommended);
////								            		    parentpd=appMemberService.findById(parentpd);
////							            		    	parentIntegral=Double.parseDouble(parentpd.getString("now_integral"))+xtmoney*Const.orderShouyiMoney[3];
////								            		    double onecontactintegral=Double.parseDouble(parentpd.getString("onecontactintegral"))+xtmoney*Const.orderShouyiMoney[3];
////								            		    parentpd.put("now_integral", df.format(parentIntegral));
////								            		    parentpd.put("onecontactintegral", df.format(onecontactintegral));
////								   						appMemberService.edit(parentpd);
////								   					    //新增会员积分支出历史
////								   						parentpd.put("wealth_type", "1");//1-积分（送/增），2-金钱（充值提现）
////								   						parentpd.put("consume_type", "1");//获赠1-收益（提现，获赠），支出2-消费（充值余额/送积分）
////								   						parentpd.put("content", "一度人脉消费获益");
////								   						parentpd.put("number", df.format(xtmoney*Const.orderShouyiMoney[3]));
////								   						parentpd.put("now_money", df.format(parentIntegral));
////								   						appMember_wealthhistoryService.saveWealthhistory(parentpd);
////								   					    //获取爷爷
////								   						recommended_type=parentpd.getString("recommended_type");
////						 			            	    recommended=parentpd.getString("recommended");
////						 			            	    if(recommended_type.equals("1")){
////						 			            	    		xtmoney=xtmoney-xtmoney*Const.orderShouyiMoney[4];
////						 			            	    		lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[4];
////						 			            	    		pd.put("twocontacts_id", recommended);
////						 				            		   	pd.put("twocontacts_type", "1");
////						 						            	pd.put("twocontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[4]));
////						 						           //新增商家返系统积分记录
////						 						            	now_wealth=now_wealth+xtmoney*Const.orderShouyiMoney[4];
////						 						            	moneypd.put("now_wealth", df.format(now_wealth));
////						 						            	appStoreService.editWealthById(moneypd);
////									 			   				wpd=null;
////									 			   			    wpd=new PageData();
////											 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
////				//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
////												 			    wpd.put("profit_type", "12");
////												 			    wpd.put("number",df.format( xtmoney*Const.orderShouyiMoney[4]));
////												 			    wpd.put("store_id", spd.getString("store_id"));
////												 			    wpd.put("store_operator_id", pd.getString("store_operator_id"));
////												 			    wpd.put("process_status", "1");
////												 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
////												 			    wpd.put("user_id", mpd.getString("member_id"));
////												 			    wpd.put("pay_type",channel);
////												 			    wpd.put("last_wealth",appStoreService.sumStoreMoney(spd));
////												 			    wpd.put("store_wealthhistory_id", this.getTimeID());
////									 			   				appStoreService.saveWealthhistory(wpd);
////					 	 			            	    }else if(recommended_type.equals("2")){
////					 	 			            	    	pd.put("twocontacts_id", recommended);
////									            		   	pd.put("twocontacts_type", "1");
////											            	pd.put("twocontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[4]));
////											            	if(xtmoney*Const.orderShouyiMoney[4] >0){
////											            		parentpd.put("member_id", recommended);
////							 			            		    parentpd=appMemberService.findById(parentpd);; 
////							 			    				    //更新会员积分
////							 			            		    parentIntegral=Double.parseDouble(parentpd.getString("now_integral"))+xtmoney*Const.orderShouyiMoney[4];
////							 			            		    double twocontactintegral=Double.parseDouble(parentpd.getString("twocontactintegral"))+xtmoney*Const.orderShouyiMoney[4];
////							 			            		    parentpd.put("now_integral", df.format(parentIntegral));
////							 			            		    parentpd.put("twocontactintegral", df.format(twocontactintegral));
////							 			            		    appMemberService.edit(parentpd);
////						 	 			            		    //新增会员积分支出历史
////							 			            		    parentpd.put("wealth_type", "1");//1-积分（送/增），2-金钱（充值提现）
////							 			            		    parentpd.put("consume_type", "1");//获赠1-收益（提现，获赠），支出2-消费（充值余额/送积分）
////							 			            		    parentpd.put("content", "二度人脉消费获益");
////							 			            		    parentpd.put("number", df.format(xtmoney*Const.orderShouyiMoney[4]));
////							 			            		    parentpd.put("now_money", df.format(parentIntegral));
////							 			   						appMember_wealthhistoryService.saveWealthhistory(parentpd);
////							 			   						lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[4];
////											            	}
////						 			            	    }
////							            		    }
////							            	   }
////							            	   if(lessmoneyxtmoney > 0){
////							            		   //子公司--减法运算
////								            	   PageData supd=new PageData();
////								            	   supd=subsidiaryService.findById(sppd);
////						 			            	nowmoney=0;
////								            	    if(supd != null && supd.getString("nowmoney") != null && !supd.getString("nowmoney").equals("")){
////								            	    	nowmoney=Double.parseDouble(supd.getString("nowmoney"));
////								            	    }
////								            	   nowmoney+=lessmoneyxtmoney;
////								            	   supd.put("nowmoney", df.format(nowmoney));
////								            	   subsidiaryService.edit(supd);
////								            	   pd.put("subsidiary_id", supd.getString("subsidiary_id"));
////								            	   pd.put("subsidiary_getmoney", df.format(lessmoneyxtmoney));
////								            	   supd=null;
////							            	   }
////						   				} catch (Exception e) {
////										// TODO: handle exception
////										System.out.println("新增业务员5/服务商20/父级人脉20/爷级人脉20/子公司35财富出错"+e.toString());
////				 					}
////				 	   				//新增商家的综合分值
////						   			    logger.info("==========================更新商家的综合分值");
////						   			    double complex_score=Double.parseDouble(spd.getString("complex_score"));
////						   				n =g;
////						   				PageData compd=new PageData();
////				 		   				if( n<=5 ){
////						   				    complex_score+=Double.parseDouble( Const.complexscore[4]);
////				 		   				}else if(5< n  && n<= 10){
////						   					complex_score+=Double.parseDouble( Const.complexscore[5]);
////				 		   				}else if(10< n  && n<= 30){
////							   				complex_score+=Double.parseDouble( Const.complexscore[6]);
////				 		   				}else if(30< n  && n<= 100){
////							   				complex_score+=Double.parseDouble( Const.complexscore[7]);
////				 		   				}else if(100< n){
////							   				complex_score+=Double.parseDouble( Const.complexscore[8]);
////				 		   				}
////						   				//根据综合分值增加星级
////									    compd=new PageData();
////									    complex_score=complex_score+3;
////										compd.put("store_id", pd.getString("store_id"));
////										compd.put("complex_score", complex_score);
////										if(complex_score >= Const.xingcomplexscore[2]){
////											compd.put("merchant_level", "3");
////											compd.put("goods_max", Const.xingcomplexscoregoodsnumber[2]);
////							   			}else if(complex_score >=  Const.xingcomplexscore[1]){
////							   				compd.put("merchant_level", "2");
////							   				compd.put("goods_max", Const.xingcomplexscoregoodsnumber[1]);
////							   			}else{
////							   				compd.put("merchant_level", "1");
////							   				compd.put("goods_max", Const.xingcomplexscoregoodsnumber[0]);
////							   			}
////						   				storeService.edit(compd);
////						   				//加综合分值结束
////								}
////								//更改红包状态
////				 				System.err.println("删除使用红包状态 ===========================");
////								if(redpackage_id != null && !redpackage_id.equals("")){
////									try {
////										 appMember_redpacketsService.deleteRed(pd);
////										 //更新红包数量
////										 appMemberService.updateMemberRedNumber(mpd);
////									} catch (Exception e) {
////										// TODO: handle exception
////									}
////				 				}
////				 				System.err.println("新增获赠红包 ===========================");
////								//新增获赠红包
////								if(store_redpackets_id != null && !store_redpackets_id.equals("")){
////									 String[] str=store_redpackets_id.split(",");
////									 for (int i = 0; i < str.length; i++) {
////													pd.put("store_redpackets_id", str[i]);
////														PageData e2=new PageData();
////													PageData redpd=appStore_redpacketsService.findRedById(pd);
////									 				if(redpd != null){
////												 					String redpackage_type="";
////												 					String redpackage_content="";
////																	if(redpd.getString("redpackage_type").equals("1")){//现金
////																		redpackage_type="21";
////																		redpackage_content=redpd.getString("srp_usercondition_content")+"减"+redpd.getString("money")+"元";
////																	}else if(redpd.getString("redpackage_type").equals("2")){//折扣
////																		redpackage_type="22";
////																		redpackage_content=redpd.getString("srp_usercondition_content")+"打"+redpd.getString("money")+"折";
////																	}
////																	e2.put("redpackage_id",this.get32UUID());
////																	e2.put("member_id", pd.getString("member_id"));
////																	e2.put("redpackage_money", redpd.getString("money"));
////																	e2.put("redpackage_content", redpackage_content);
////																	e2.put("store_redpackets_id", redpd.getString("store_redpackets_id"));
////																	e2.put("redpackage_type", redpackage_type);
////																	e2.put("enddate", redpd.get("endtime").toString());
////																	e2.put("startdate", redpd.get("starttime").toString());
////																	e2.put("set_id", redpd.getString("store_id"));
////																	e2.put("set_type", "1");
////																	appMemberService.saveRedForMember(e2);//新增红包信息至会员
////															}
////													}
////										//更新红包数量
////										appMemberService.updateMemberRedNumber(mpd);
////									}
////					 				System.out.println("新增订单信息================================");
////					 				pd.put("order_status", "1");
////					 				pd.put("channel", "nowpay");
////					 				if(pd.getString("store_operator_id").trim().equals("")){
////										 pd.put("store_operator_id", "jy"+spd.getString("store_id"));
////									}
////									appOrderService.saveOrder(pd);
////										System.out.println("新增订单============================");
//// 										//获取操作员详情
////										PageData oppd=storeManageService.findOperatorById(pd);
////										if(  oppd != null && oppd.getString("store_shift_id") != null && !oppd.getString("store_shift_id").equals("")){
////											pd.put("store_shift_id", oppd.getString("store_shift_id"));
////										}
////										//新增支付记录tb_pay_history
////										PageData phpd=new PageData();
////										phpd.put("pay_history_id",  this.getXFUID());
////										phpd.put("user_type",  "2");
////										phpd.put("user_account",  mpd.getString("phone"));
////										phpd.put("money_type",  "2");//1-充值（商家或会员充积分），2-消费，3-商家购买保证金，4-服务商支付保证金
////										phpd.put("pay_status",  "1");
////										phpd.put("money",  sale_money);
////										phpd.put("remittance_type",  "1");// 1-会员端现金支付，2- 商家端现金支付，3-支付宝支付，4-微信支付，5-苹果支付
////										phpd.put("remittance_name",  "会员端现金支付");
////										phpd.put("remittance_number", mpd.getString("phone"));
////										phpd.put("order_tn",  this.getTimeID());
////										phpd.put("payee_number",  pd.getString("store_id"));
////										phpd.put("payer_id",  pd.getString("member_id"));
////										phpd.put("operate_id",  pd.getString("member_id"));
////										phpd.put("order_id", pd.getString("order_id"));
////										phpd.put("province_name", mpd.getString("province_name"));
////										phpd.put("city_name", mpd.getString("city_name"));
////										phpd.put("area_name", mpd.getString("area_name"));
////										appPay_historyService.savePayhistory(phpd);
////											//新增营销效果
////											 YingXiao(pd);
////											//更新会员指定商家的vip
////											appMemberService.updateStoreVipById(pd);
////											//
//// 											pd.put("salemoney", df.format(Double.parseDouble(actual_money)+Double.parseDouble(user_integral)+Double.parseDouble(user_balance)));
////											appMemberService.updateMemberById(pd); 
////											//更新商家的综合分值
////											pd.put("number", Const.complexscore[1]);
////											storeService.editComplexscore(pd);
////											//更新商家的交易次数
////											pd.put("transaction_times", "1");
////											appStoreService.edit(pd);
////											//魅力值：0-50一星会员 50-99 二星会员 100-199 三星会员 200-499  四星会员 500-999 五星会员 1000-2000 一钻会员
////											double charm_number=Double.parseDouble(mpd.getString("charm_number"))+Double.parseDouble(Const.charm_number[5]);
////											PageData chpd=new PageData();
////											chpd.put("member_id", mpd.getString("member_id"));
////											chpd.put("charm_number", charm_number);
////											if(charm_number >=0 && charm_number < 50){
////												chpd.put("vip_level", "1");
////											}else if(charm_number >=50 && charm_number < 100){
////												chpd.put("vip_level", "2");
////											}else if(charm_number >=100 && charm_number < 200){
////												chpd.put("vip_level", "3");
////											}else if(charm_number >=200 && charm_number < 500){
////												chpd.put("vip_level", "4");
////											}else if(charm_number >=500 && charm_number < 1000){
////												chpd.put("vip_level", "5");
////											}else if(charm_number >=1000 && charm_number < 2000){
////												chpd.put("vip_level", "6");
////											}
////											appMemberService.edit(chpd);
////			 				}
//   			    }else{
// 				        String  order_status=pd.getString("order_status");
//// 				        String  order_status="1";
// 				        String order_id=pd.getString("order_id");
// 				        if(order_status == null){
//	 				   		map.put("result", "0");
//	 						map.put("message", "订单状态不能为空");
//	 						map.put("data", "");
//	 				  		return map;
// 				        }
// 				        if(order_status.equals("99")){//取消收银
// 				        	payapp_historyService.deleteOrderw(pd);
// 				        }else{
//   							//订单支付
// 			            		System.out .println("=============>>>>>>>获取订单信息");
//			            		pd.put("order_id", order_id);
//			            		pd=appOrderService.findById(pd);//订单详情
//  			            		if(pd != null){
//		  			            			//根据订单id判断是否已经确认收银过了
//		  	 			            		if(pd.getString("order_status").equals("1") || pd.getString("order_status").equals("99")){
//		  	 			            			map.put("result", "0");
//		  	 			            			map.put("message", "当前订单已处理");
//		  	 			            			map.put("data", "");
//		  	 			            	  		return map;
//		  	 			            		} 
//		  	 			            		//开始处理订单。。。。。。。。。。。。。。。。。。。
////    		 			            		redpackage_id=pd.getString("redpackage_id");//红包ID
////				 			       			store_redpackets_id=pd.getString("store_redpackets_id");//赠送红包ID
////				 			       			sale_money=pd.getString("sale_money");//消费的总金额
////				 			       			actual_money=pd.getString("actual_money");//支付的金额（现金或者第三方）
////				 			       			user_balance=pd.getString("user_balance");//使用的余额
////				 			       			user_integral=pd.getString("user_integral");//使用的积分
////				 			       			get_integral=pd.getString("get_integral");//获得的积分
////		 			            			logger.info(pd.toString()+"-----订单信息");
////			 			            		pd.put("member_id", pd.getString("payer_id"));
////		 	 			            		pd.put("store_id", pd.getString("store_id"));
////			 			            		PageData mpd=new PageData();
////				 			       			mpd=appMemberService.findById(pd);//用户详情
////					 			       		PageData spd=new PageData();
////				 			       			spd=appStoreService.findById(pd);//商家详情
////				 			       			if(mpd == null || spd == null){
////						 			       			map.put("result", "0");
////							 			       		map.put("message", "订单有误");
////							 			       		map.put("data", "");
////						 			         		return map;
////				 			       			}
//// 		 	 			            		System.out .println("=============>>>>>>>新增用户余额消费记录");
////		 	 			            		double now_money=Double.parseDouble(mpd.getString("now_money"));//会员余额
////			 			     				double now_integral=Double.parseDouble(mpd.getString("now_integral"));//会员积分
////			 			     				double acmoney=Double.parseDouble(actual_money);
////			 			     				double n=0;
////			 			     				//用户余额消费记录
////					 			   			if(user_balance != null &&!user_balance.equals("") && Double.parseDouble(user_balance) >0 ){
////					 			   						n=Double.parseDouble(user_balance);//使用的余额		
////					 			   						//更新会员个人余额信息消费次数
////							 			   				PageData countpd=new PageData();
////							 			   				countpd.put("pay_way", "nowmoney");
////							 			   				countpd.put("member_id", mpd.getString("member_id"));
//// 							 			   				appMemberService.updateMemberById(countpd);  
////					 			   						//会员
//// 							 			   				now_money=now_money-n;
////							 			   				PageData moneypd=new PageData();
////			 		 			   						moneypd.put("member_id", pd.getString("member_id"));
////					 			    					moneypd.put("wealth_type", "2");
////					 			   						moneypd.put("consume_type", "2");
////					 			   						moneypd.put("content", "余额消费支出");
////					 			   						moneypd.put("number", df.format(n));
////					 			   						moneypd.put("now_money", df.format(now_money));
////					 			   						logger.info("新增个人消费历史");
////					 			   						appMember_wealthhistoryService.saveWealthhistory(moneypd);//新增个人消费历史
////					 			   						//更新金钱
////  					 			   						appMemberService.edit(moneypd);
////					 			   						//商家更新余额
////	  					 			   					spd.put("wealth_type", "2");
////	  			    			   						PageData spdwithpd=appStoreService.findWealthById(spd);
////	  			    			   						double now_wealth=Double.parseDouble(spdwithpd.getString("now_wealth"));
////	  			    			   						spdwithpd.put("now_wealth", df.format(now_wealth+n+acmoney));
////	  			    			   						appStoreService.editWealthById(spdwithpd);
//// 					 			    	}
//// 		 	 			            		System.out .println("=============>>>>>>>新增用户积分消费记录");
////					 			   			//用户积分消费记录
//// 		 	 			            		double m=0;
////					 			   			if(user_integral != null && !user_integral.equals("") && Double.parseDouble(user_integral) >0 ){
////							 			   			 	m=Double.parseDouble(user_integral);		
////							 			   				//更新个人积分信息消费次数
////					 			   						PageData countpd=new PageData();
////					 			   						countpd.put("pay_way", "integralmoney");
////					 			   						countpd.put("member_id", mpd.getString("member_id"));
//// 							 			   				appMemberService.updateMemberById(countpd); 
////							 			   				//会员
////						 			   					PageData moneypd=new PageData();
//// 						 			    				now_integral=now_integral-m;
////						 			    				//更新积分
////						 			    				moneypd.put("member_id", pd.getString("member_id"));
////						 			    				moneypd.put("now_integral", df.format(now_integral));
////					 			   						appMemberService.edit(moneypd);
////					 			   						//新增个人消费历史
//// 					 			    					moneypd.put("wealth_type", "1");
////					 			   						moneypd.put("consume_type", "2");
////					 			   						moneypd.put("content",spd.getString("store_name")+"消费抵用");
////					 			   						moneypd.put("number", df.format(m));
////					 			   						moneypd.put("now_money", df.format(now_integral));
//// 					 			   						appMember_wealthhistoryService.saveWealthhistory(moneypd);
//// 					 			   						//更新商家积分信息
////						 			   					spd.put("wealth_type", "1");
////							 			   				moneypd=appStoreService.findWealthById(spd);
////							 			   				double now_wealth=Double.parseDouble(moneypd.getString("now_wealth"));
////							 			   				moneypd.put("now_wealth", df.format(now_wealth+m));
////							 			   				appStoreService.editWealthById(moneypd);
//// 					 			   				}
////					 			   		 //新增商家财富历史记录----------
////		    			   	   				PageData swpd=new PageData();
////		    			   	   				swpd.put("wealth_type", "2");
////		    			   	   				swpd.put("profit_type", "3");
////		    			   	   				swpd.put("number", df.format(acmoney+n+m));
////		    			   	   				swpd.put("store_id", spd.getString("store_id"));
////		    			   	   				swpd.put("store_operator_id", pd.getString("store_operator_id"));
////		    			   	   				swpd.put("process_status", "1");
////		    			   	   				swpd.put("pay_type",channel);
////		    			   	   				swpd.put("jiaoyi_id",pd.getString("order_id") );
////		    			   	   				swpd.put("user_id", mpd.getString("member_id"));
////		    			   	   				swpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
////		    			   	   				swpd.put("store_wealthhistory_id",this.getTimeID());
////		    			   	   				appStoreService.saveWealthhistory(swpd);
////		    			     				System.err.println("新增用户积分获赠记录 ===========================");
//// 	 	 			            		System.out .println("=============>>>>>>>新增用户积分获赠记录");
////			 		 			   		//新增用户积分获赠记录
//// 					 			   		if(get_integral != null &&  !get_integral.equals("")  && Double.parseDouble(get_integral) >0){
////		     			   					//会员获赠积分记录
//// 					 			   			PageData moneypd=new PageData();
////		     			    	 			double g=Double.parseDouble(get_integral);
////		     			    	 			now_integral=now_integral+g;
////		     			    	 			//更新积分
////		     			    	 			System.err.println("更新积分 ===========================");
////		     			   					moneypd.put("member_id", pd.getString("member_id"));
////		     			   					moneypd.put("now_integral", df.format(now_integral));
////		     			   					appMemberService.edit(moneypd);
////		     			   	 				moneypd.put("wealth_type", "1");
////		     			   					moneypd.put("consume_type", "1");
////		     			   					moneypd.put("content", spd.getString("store_name")+" 消费获赠");
////		     			   					moneypd.put("number", df.format(g));
////		     			   					moneypd.put("now_money", df.format(now_integral));
////		    			    					appMember_wealthhistoryService.saveWealthhistory(moneypd);//新增个人消费历史
////		    			     					//新增业务员5/服务商20/父级人脉20/爷级人脉20/子公司35财富---------------------------------------------------------
////		    			   		   				double xtmoney=0;
////		    			   		   				try {
////		    			   			   						PageData mmpd=new PageData();
////		    			   			   						xtmoney=g*Const.orderShouyiMoney[0];//系统获取的积分
////		    			   			   						double lessmoneyxtmoney=xtmoney;
////		    			   			   						pd.put("sendxitong_integral", df.format(xtmoney));
////		    			   		 			   				//梗新商家积分
////		    			   			   						spd.put("wealth_type", "1");
////		    			   			 			   			moneypd=appStoreService.findWealthById(spd);
////		    			   			 			   			double now_wealth=Double.parseDouble(moneypd.getString("now_wealth"))-g;
////		    			   			 			   			moneypd.put("now_wealth", df.format(now_wealth));
////		    	 			   							appStoreService.editWealthById(moneypd);
////		    			   			 			   			//新增商家赠送积分记录
////		    			   		 			   			    PageData wpd=new PageData();
////		    			   				 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
////		    			   	//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，4-现金支付的金额，5-第三方支付的金额，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
////		    			   					 			    wpd.put("profit_type", "7");
////		    			   					 			    wpd.put("number",df.format( g));
////		    			   					 			    wpd.put("store_id", spd.getString("store_id"));
////		    			   						 			wpd.put("store_operator_id", pd.getString("store_operator_id"));
////		    			   					 			    wpd.put("process_status", "1");
////		    			   					 			    wpd.put("pay_type",channel);
////		    			   					 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
////		    			   					 			    wpd.put("user_id", mpd.getString("member_id"));
////		    			   					 			    wpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
////		    			   					 			    wpd.put("store_wealthhistory_id", this.getTimeID());
////		    			   			 			   			appStoreService.saveWealthhistory(wpd);
////		    			   			 			   			//新增商家返系统积分记录
////		    			   			 			   			now_wealth=now_wealth-xtmoney;
////			    	 			   			 			   	moneypd.put("now_wealth", df.format(now_wealth));
////			    	 			   							appStoreService.editWealthById(moneypd);
////		    			   			 			   			wpd=null;
////		    			   		 			   			    wpd=new PageData();
////		    			   				 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
////		    			   	//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
////		    			   					 			    wpd.put("profit_type", "11");
////		    			   					 			    wpd.put("number",df.format( xtmoney));
////		    			   					 			    wpd.put("store_id", spd.getString("store_id"));
////		    			   						 			wpd.put("store_operator_id", pd.getString("store_operator_id"));
////		    			   					 			    wpd.put("process_status", "1");
////		    			   					 			    wpd.put("pay_type",channel);
////		    			   					 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
////		    			   					 			    wpd.put("user_id", mpd.getString("member_id"));
////		    			   					 			    wpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
////		    			   					 			    wpd.put("store_wealthhistory_id", this.getTimeID());
////		    			   		 			   				appStoreService.saveWealthhistory(wpd);
////		    			   		 			   				//服务商
////		    			   		 			   				PageData sppd=new PageData();
////		    			   		 			   				sppd=sp_fileService.findById(spd);
////		    			   				            	    double nowmoney=0;
////		    			   				            	    if(sppd != null && sppd.getString("nowmoney") != null && !sppd.getString("nowmoney").equals("")){
////		    			   				            	    	nowmoney=Double.parseDouble(sppd.getString("nowmoney"));
////		    			   				            	    }
////		    			   				            	    pd.put("sp_file_id", sppd.getString("sp_file_id"));
////		    			   				            	    pd.put("sp_getmoney", df.format(xtmoney*Const.orderShouyiMoney[2]));
////		    			   				            	    if(xtmoney*Const.orderShouyiMoney[2] >0){
////		    			   				            		   nowmoney+=xtmoney*Const.orderShouyiMoney[2];
////		    			   					            	   sppd.put("nowmoney", df.format(nowmoney));
////		    			   					            	   sp_fileService.edit(sppd);
////		    			   					            	   lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[2];
////		    			   					            	    //添加一个提现记录
////		    			   					 	 				mmpd.put("profit_name", sppd.getString("team_name"));//收益对象
////		    			   					 	 				mmpd.put("yewu_name", mpd.getString("name"));//业务对象
////		    			   					 	 				mmpd.put("yewu_id", mpd.getString("member_id"));//业务对象
////		    			   					 	 				mmpd.put("yewu_type", "2");//业务对象
////		    			   					 	 				mmpd.put("money",  df.format(xtmoney*Const.orderShouyiMoney[2]));//金额
////		    			   					 	 				mmpd.put("money_type", "2");//1、销售提成: 2、积分收益： 3、平台奖励 
////		    			   					 	 				mmpd.put("operate_type", "1"); //1-服务商，2-业务员
////		    			   					 	 				mmpd.put("operate_id", sppd.getString("sp_file_id")); 
////		    			   					 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
////		    			   					 	 				mmpd.put("service_performance_id", this.get10UID());//收益对象
////		    			   					 	 				service_performanceService.save(mmpd);
////		    			   			            	   }
////		    			   	 			            	    //业务员
////		    			   				            	   PageData clerkpd=new PageData();
////		    			   				            	   clerkpd=clerk_fileService.findById(spd);
////		    			   				            	   nowmoney=0;
////		    			   				            	    if( clerkpd != null && clerkpd.getString("nowmoney") != null &&  !clerkpd.getString("nowmoney").equals("")){
////		    			   				            	    	nowmoney=Double.parseDouble(clerkpd.getString("nowmoney"));
////		    			   				            	    }
////		    			   				            	   pd.put("clerk_file_id", clerkpd.getString("clerk_file_id"));
////		    			   				            	   pd.put("clerk_getmoney", df.format(xtmoney*Const.orderShouyiMoney[1]));
////		    			   				            	   if(xtmoney*Const.orderShouyiMoney[1] > 0){
////		    			   				            		  nowmoney+=xtmoney*Const.orderShouyiMoney[1];
////		    			   					            	   clerkpd.put("nowmoney", df.format(nowmoney));
////		    			   					            	   clerk_fileService.edit(clerkpd);
////		    			   					            	   lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[1];
////		    			   					            	  //添加一个提现记录
////		    			   								 		mmpd=null;
////		    			   					 	 				mmpd=new PageData();
////		    			   					 	 				mmpd.put("profit_name", clerkpd.getString("clerk_name"));//收益对象
////		    			   					 	 				mmpd.put("yewu_name", mpd.getString("name"));//业务对象
////		    			   					 	 				mmpd.put("yewu_id", mpd.getString("member_id"));//业务对象
////		    			   					 	 				mmpd.put("yewu_type", "2");//业务对象
////		    			   					 	 				mmpd.put("money",  df.format(xtmoney*Const.orderShouyiMoney[1]));//金额
////		    			   					 	 				mmpd.put("money_type", "2");//1、销售提成: 2、积分收益： 3、平台奖励 
////		    			   						 	 			mmpd.put("operate_type", "2"); //1-服务商，2-业务员
////		    			   					 	 				mmpd.put("operate_id", clerkpd.getString("clerk_file_id")); 
////		    			   					 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
////		    			   					 	 				mmpd.put("service_performance_id", this.get10UID());//收益对象
////		    			   					 	 				service_performanceService.save(mmpd);
////		    			   				            	   }
////		    			   				            	   //会员的父级人脉
////		    			   				            	   PageData parentpd=new PageData();
////		    			   				            	   String recommended_type=mpd.getString("recommended_type");
////		    			   				            	   String recommended=mpd.getString("recommended");
////		    			   				            	   if(recommended_type.equals("1")){//商家的积分
////		    			   				            		   	xtmoney=xtmoney-xtmoney*Const.orderShouyiMoney[3];
////		    			   				            		   	lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[3];
////		    			   				            		   	pd.put("onecontacts_id", recommended);
////		    			   				            		   	pd.put("onecontacts_type", "1");
////		    			   						            	pd.put("onecontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[3]));
////		    			   						            	pd.put("twocontacts_id", "0");
////		    			   				            		   	pd.put("twocontacts_type", "0");
////		    			   						            	pd.put("twocontacts_getmoney", "0");
////		    			   						            	//新增商家收益积分记录
////		     			   						            now_wealth=now_wealth+xtmoney*Const.orderShouyiMoney[3];
////		    		 			   			 			   	moneypd.put("now_wealth", df.format(now_wealth));
////		    		 			   							appStoreService.editWealthById(moneypd);
////		    			   			 			   				wpd=null;
////		    			   			 			   			    wpd=new PageData();
////		    			   					 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
////		    			   	//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
////		    			   						 			    wpd.put("profit_type", "12");
////		    			   						 			    wpd.put("number",df.format( xtmoney*Const.orderShouyiMoney[3]));
////		    			   						 			    wpd.put("store_id", spd.getString("store_id"));
////		    			   							 			wpd.put("store_operator_id", pd.getString("store_operator_id"));
////		    			   						 			    wpd.put("process_status", "1");
////		    			   						 			    wpd.put("pay_type", channel);
////		    			   						 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
////		    			   						 			    wpd.put("user_id", mpd.getString("member_id"));
////		    			   						 			    wpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
////		    			   						 			    wpd.put("store_wealthhistory_id", this.getTimeID());
////		    			   			 			   				appStoreService.saveWealthhistory(wpd);
////		    			   	 			            	   }else if(recommended_type.equals("2")){
////		    			   	 			            		    pd.put("onecontacts_id", recommended);
////		    			   	 			            		    pd.put("onecontacts_type", "2");
////		    			   	 			            		    pd.put("onecontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[3]));
////		    			   						            	double parentIntegral=0;
////		    			   						            	//更新会员积分
////		    			   				            		    if(xtmoney*Const.orderShouyiMoney[3] >0){
////		    			   				            		    	parentpd.put("member_id", recommended);
////		    			   					            		    parentpd=appMemberService.findById(parentpd);
////		    			   				            		    	parentIntegral=Double.parseDouble(parentpd.getString("now_integral"))+xtmoney*Const.orderShouyiMoney[3];
////		    			   					            		    double onecontactintegral=Double.parseDouble(parentpd.getString("onecontactintegral"))+xtmoney*Const.orderShouyiMoney[3];
////		    			   					            		    parentpd.put("now_integral", df.format(parentIntegral));
////		    			   					            		    parentpd.put("onecontactintegral", df.format(onecontactintegral));
////		    			   					   						appMemberService.edit(parentpd);
////		    			   					   					    //新增会员积分支出历史
////		    			   					   						parentpd.put("wealth_type", "1");//1-积分（送/增），2-金钱（充值提现）
////		    			   					   						parentpd.put("consume_type", "1");//获赠1-收益（提现，获赠），支出2-消费（充值余额/送积分）
////		    			   					   						parentpd.put("content", "一度人脉消费获益");
////		    			   					   						parentpd.put("number", df.format(xtmoney*Const.orderShouyiMoney[3]));
////		    			   					   						parentpd.put("now_money", df.format(parentIntegral));
////		    			   					   						appMember_wealthhistoryService.saveWealthhistory(parentpd);
////		    			   					   					    //获取爷爷
////		    			   					   						recommended_type=parentpd.getString("recommended_type");
////		    			   			 			            	    recommended=parentpd.getString("recommended");
////		    			   			 			            	    if(recommended_type.equals("1")){
////		    			   			 			            	    		xtmoney=xtmoney-xtmoney*Const.orderShouyiMoney[4];
////		    			   			 			            	    		lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[4];
////		    			   			 			            	    		pd.put("twocontacts_id", recommended);
////		    			   			 				            		   	pd.put("twocontacts_type", "1");
////		    			   			 						            	pd.put("twocontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[4]));
////		    			   			 						           //新增商家返系统积分记录
////		    			   			 						            	now_wealth=now_wealth+xtmoney*Const.orderShouyiMoney[4];
////		    			   			 						            	moneypd.put("now_wealth", df.format(now_wealth));
////		    			   			 						            	appStoreService.editWealthById(moneypd);
////		    			   						 			   				wpd=null;
////		    			   						 			   			    wpd=new PageData();
////		    			   								 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
////		    			   //1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
////		    			   									 			    wpd.put("profit_type", "12");
////		    			   									 			    wpd.put("number",df.format( xtmoney*Const.orderShouyiMoney[4]));
////		    			   									 			    wpd.put("store_id", spd.getString("store_id"));
////		    			   									 			    wpd.put("store_operator_id", pd.getString("store_operator_id"));
////		    			   									 			    wpd.put("process_status", "1");
////		    			   									 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
////		    			   									 			    wpd.put("user_id", mpd.getString("member_id"));
////		    			   									 			    wpd.put("pay_type",channel);
////		    			   									 			    wpd.put("last_wealth",appStoreService.sumStoreMoney(spd));
////		    			   									 			    wpd.put("store_wealthhistory_id", this.getTimeID());
////		    			   						 			   				appStoreService.saveWealthhistory(wpd);
////		    			   		 	 			            	    }else if(recommended_type.equals("2")){
////		    			   		 	 			            	    	pd.put("twocontacts_id", recommended);
////		    			   						            		   	pd.put("twocontacts_type", "1");
////		    			   								            	pd.put("twocontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[4]));
////		    			   								            	if(xtmoney*Const.orderShouyiMoney[4] >0){
////		    			   								            		parentpd.put("member_id", recommended);
////		    			   				 			            		    parentpd=appMemberService.findById(parentpd);; 
////		    			   				 			    				    //更新会员积分
////		    			   				 			            		    parentIntegral=Double.parseDouble(parentpd.getString("now_integral"))+xtmoney*Const.orderShouyiMoney[4];
////		    			   				 			            		    double twocontactintegral=Double.parseDouble(parentpd.getString("twocontactintegral"))+xtmoney*Const.orderShouyiMoney[4];
////		    			   				 			            		    parentpd.put("now_integral", df.format(parentIntegral));
////		    			   				 			            		    parentpd.put("twocontactintegral", df.format(twocontactintegral));
////		    			   				 			            		    appMemberService.edit(parentpd);
////		    			   			 	 			            		    //新增会员积分支出历史
////		    			   				 			            		    parentpd.put("wealth_type", "1");//1-积分（送/增），2-金钱（充值提现）
////		    			   				 			            		    parentpd.put("consume_type", "1");//获赠1-收益（提现，获赠），支出2-消费（充值余额/送积分）
////		    			   				 			            		    parentpd.put("content", "二度人脉消费获益");
////		    			   				 			            		    parentpd.put("number", df.format(xtmoney*Const.orderShouyiMoney[4]));
////		    			   				 			            		    parentpd.put("now_money", df.format(parentIntegral));
////		    			   				 			   						appMember_wealthhistoryService.saveWealthhistory(parentpd);
////		    			   				 			   						lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[4];
////		    			   								            	}
////		    			   			 			            	    }
////		    			   				            		    }
////		    			   				            	   }
////		    			   				            	   if(lessmoneyxtmoney > 0){
////		    			   				            		   //子公司--减法运算
////		    			   					            	   PageData supd=new PageData();
////		    			   					            	   supd=subsidiaryService.findById(sppd);
////		    			   			 			            	nowmoney=0;
////		    			   					            	    if(supd != null && supd.getString("nowmoney") != null && !supd.getString("nowmoney").equals("")){
////		    			   					            	    	nowmoney=Double.parseDouble(supd.getString("nowmoney"));
////		    			   					            	    }
////		    			   					            	   nowmoney+=lessmoneyxtmoney;
////		    			   					            	   supd.put("nowmoney", df.format(nowmoney));
////		    			   					            	   subsidiaryService.edit(supd);
////		    			   					            	   pd.put("subsidiary_id", supd.getString("subsidiary_id"));
////		    			   					            	   pd.put("subsidiary_getmoney", df.format(lessmoneyxtmoney));
////		    			   					            	   supd=null;
////		    			   				            	   }
////		    			   		   				} catch (Exception e) {
////		    			   						// TODO: handle exception
////		    			   						System.out.println("新增业务员5/服务商20/父级人脉20/爷级人脉20/子公司35财富出错"+e.toString());
////		    			    					}
////		    			    	   				//新增商家的综合分值
////		    			   		   			    logger.info("==========================更新商家的综合分值");
////		    			   		   			    double complex_score=Double.parseDouble(spd.getString("complex_score"));
////		    			   		   				n =g;
////		    			   		   				PageData compd=new PageData();
////		    			    		   				if( n<=5 ){
////		    			   		   				    complex_score+=Double.parseDouble( Const.complexscore[4]);
////		    			    		   				}else if(5< n  && n<= 10){
////		    			   		   					complex_score+=Double.parseDouble( Const.complexscore[5]);
////		    			    		   				}else if(10< n  && n<= 30){
////		    			   			   				complex_score+=Double.parseDouble( Const.complexscore[6]);
////		    			    		   				}else if(30< n  && n<= 100){
////		    			   			   				complex_score+=Double.parseDouble( Const.complexscore[7]);
////		    			    		   				}else if(100< n){
////		    			   			   				complex_score+=Double.parseDouble( Const.complexscore[8]);
////		    			    		   				}
////		    			   		   				//根据综合分值增加星级
////		    			   					    compd=new PageData();
////		    			   					    complex_score=complex_score+3;
////		    			   						compd.put("store_id", pd.getString("store_id"));
////		    			   						compd.put("complex_score", complex_score);
////		    			   						if(complex_score >= Const.xingcomplexscore[2]){
////		    			   							compd.put("merchant_level", "3");
////		    			   							compd.put("goods_max", Const.xingcomplexscoregoodsnumber[2]);
////		    			   			   			}else if(complex_score >=  Const.xingcomplexscore[1]){
////		    			   			   				compd.put("merchant_level", "2");
////		    			   			   				compd.put("goods_max", Const.xingcomplexscoregoodsnumber[1]);
////		    			   			   			}else{
////		    			   			   				compd.put("merchant_level", "1");
////		    			   			   				compd.put("goods_max", Const.xingcomplexscoregoodsnumber[0]);
////		    			   			   			}
////		    			   		   				storeService.edit(compd);
////		    			   		   				//加综合分值结束
////		    			   				}
//// 	 	 			            		System.out .println("=============>>>>>>>更改红包状态");
////					 			   		//更改使用的红包状态
////					 			   		if(redpackage_id != null && !redpackage_id.equals("")){
////								 			   		logger.info("删除红包");
////						 			   				appMember_redpacketsService.deleteRed(pd);
////						 			   				//更新红包数量
////						 							appMemberService.updateMemberRedNumber(mpd);
////					 			   		}
//// 	 	 			            		System.out .println("=============>>>>>>>新增获赠红包");
////					 			   		//会员获赠红包
////					 			   		if(store_redpackets_id != null && !store_redpackets_id.equals("")){
////												String[] str=store_redpackets_id.split(",");
////												for (int i = 0; i < str.length; i++) {
////															pd.put("store_redpackets_id", str[i]);
////	 														PageData e2=new PageData();
////															PageData redpd=appStore_redpacketsService.findRedById(pd);
////											 				if(redpd != null){
////														 					String redpackage_type="";
////														 					String redpackage_content="";
////																			if(redpd.getString("redpackage_type").equals("1")){//现金
////																				redpackage_type="21";
////																				redpackage_content=redpd.getString("srp_usercondition_content")+"减"+redpd.getString("money")+"元";
////																			}else if(redpd.getString("redpackage_type").equals("2")){//折扣
////																				redpackage_type="22";
////																				redpackage_content=redpd.getString("srp_usercondition_content")+"打"+redpd.getString("money")+"折";
////																			}
////																			e2.put("redpackage_id",this.get32UUID());
////																			e2.put("member_id", pd.getString("member_id"));
////																			e2.put("redpackage_money", redpd.getString("money"));
////																			e2.put("redpackage_content", redpackage_content);
////																			e2.put("store_redpackets_id", redpd.getString("store_redpackets_id"));
////																			e2.put("redpackage_type", redpackage_type);
////																			e2.put("enddate", redpd.get("endtime").toString());
////																			e2.put("startdate", redpd.get("starttime").toString());
////																			e2.put("set_id", redpd.getString("store_id"));
////																			e2.put("set_type", "1");
////																			appMemberService.saveRedForMember(e2);//新增红包信息至会员
////																	}
////															}
////												//更新红包数量
////					 							appMemberService.updateMemberRedNumber(mpd);
////											}
////					 			   		System.out .println("新增充值记录tb_pay_history====================》》》》》》");
////					 			   		//新增支付记录tb_pay_history
////					 					PageData phpd=new PageData();
////					 					phpd.put("pay_history_id", this.getXFUID());
////					 					phpd.put("user_type",  "2");
////					 					phpd.put("user_account",  mpd.getString("phone"));
////					 					phpd.put("money_type",  "2");
////					 					phpd.put("pay_status",  "1");
////					 					phpd.put("money",  sale_money);
////					 					phpd.put("remittance_type",  "1");
////					 					phpd.put("remittance_name",  "收银现金支付");
////					 					phpd.put("remittance_number", mpd.getString("phone"));
////					 					phpd.put("order_tn",  this.getTimeID());
////					 					phpd.put("payee_number",  pd.getString("store_id"));
////					 					phpd.put("payer_id",  pd.getString("member_id"));
////					 					phpd.put("operate_id", pd.getString("store_operator_id") );
////					 					phpd.put("order_id", order_id);
////					 					phpd.put("province_name", mpd.getString("province_name"));
////					 					phpd.put("city_name", mpd.getString("city_name"));
////					 					phpd.put("area_name", mpd.getString("area_name"));
////					 					appPay_historyService.savePayhistory(phpd);
////					 			   			//修改订单状态
////					 			   			pd.put("order_status", "1");
////					 			   			logger.info("==========================修改订单状态");
////					 			   			appOrderService.editStatus(pd);
////					 			   		   //营销记录表
////					 			   		 	logger.info("==========================新增营销记录表");
////					 			   		    YingXiao(pd);
//// 					 			   		   //更新商家的综合分值
////					 			   		   logger.info("==========================更新商家的综合分值");
////						 			   		pd.put("number", Const.complexscore[1]);
////						 					storeService.editComplexscore(pd);
////						 					//更新商家的订单交易次数
////						 					logger.info("==========================更新商家的交易");
////					 						pd.put("transaction_times", "1");
////					 						appStoreService.edit(pd);
////					 	 				    //更新个人总消费信息
////					 	 					pd.put("salemoney", df.format(Double.parseDouble(actual_money)+Double.parseDouble(user_integral)+Double.parseDouble(user_balance)));
////					 	 					appMemberService.updateMemberById(pd); 
////					 	 					//更新会员指定商家的vip内容
////					 	 					appMemberService.updateStoreVipById(pd);
////					 	 					//魅力值：0-50一星会员 50-99 二星会员 100-199 三星会员 200-499  四星会员 500-999 五星会员 1000-2000 一钻会员
////					 						double charm_number=Double.parseDouble(mpd.getString("charm_number"))+Double.parseDouble(Const.charm_number[5]);
////					 						PageData chpd=new PageData();
////					 						chpd.put("member_id", mpd.getString("member_id"));
////					 						chpd.put("charm_number", charm_number);
////											if(charm_number >=0 && charm_number < 50){
////												chpd.put("vip_level", "1");
////											}else if(charm_number >=50 && charm_number < 100){
////												chpd.put("vip_level", "2");
////											}else if(charm_number >=100 && charm_number < 200){
////												chpd.put("vip_level", "3");
////											}else if(charm_number >=200 && charm_number < 500){
////												chpd.put("vip_level", "4");
////											}else if(charm_number >=500 && charm_number < 1000){
////												chpd.put("vip_level", "5");
////											}else if(charm_number >=1000 && charm_number < 2000){
////												chpd.put("vip_level", "6");
////											}
////											appMemberService.edit(chpd);
//			            		}else{
//	 			            			logger.info("订单不存在");
//			            		}
// 			            		System.out .println("订单支付的=============>>>>>>>回调结束");
//  						 }
// 			 }
//      	} catch(Exception e){
//			logger.error("确认收银异常==============="+e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");
//  		return map;
//	}
//	
//	@Resource(name="storeManageService")
//	private StoreManageService storeManageService;
//	
//	@Resource(name="storeService")
//	private StoreService storeService;
//	
//	/**
//	 * 判断提货卷是否存在  魏汉文20160706
//	 */
//	@RequestMapping(value="/isTiHuo")
//	@ResponseBody
//	public Object isTiHuo(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{
//			 	pd = this.getPageData();
//			 	pd.put("tihuo_id", pd.getString("tihuo_id").trim());
//			 	pd=payapp_historyService.tihuoByOrderId(pd);
//			 	if(pd== null ){
//	 			 		map.put("result", "0");
//						map.put("message", "提货卷不存在");
//						map.put("data", "");
//				   		return map;
//			 	}else{
//				 		if(pd.getString("tihuo_status").equals("0")){
//					 			 //获取商品信息
//				 			    if(pd.getString("name") != null && pd.getString("name").length() == 11){
//				 			    	pd.put("name", pd.getString("name").substring(0, 3)+"****"+pd.getString("name").substring(7, 11));
//				 			    }
//				 			  	pd.put("phone", pd.getString("phone").substring(0, 3)+"****"+pd.getString("phone").substring(7, 11));
//								List<PageData> goodsList=payapp_historyService.listAllGoodsByOrder(pd);
//								pd.put("goodsList", goodsList);
//								map.put("data", pd);
//				 		}else{//已提货
//					 			result="0";
//						 		message="该提货卷已提货";
//						 		map.put("data", pd);
//				 		}
// 			 	}
//     	} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//   		return map;
//	}
//	
//	/**
//	 * 订单确认提货  魏汉文20160706
//	 */
//	@RequestMapping(value="/sureTiHuo")
//	@ResponseBody
//	public Object sureTiHuo(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		String result = "1";
//		String message="提货成功";
//		PageData pd = new PageData();
//		try{
//				pd = this.getPageData();
//				pd=appOrderService.findById(pd);//订单详情
//				if(pd == null){
//					map.put("result", "0");
//					map.put("message", "订单不存在");
//					map.put("data", "");
//			  		return map;
//				}
//				String channel=pd.getString("channel");
//	 			String redpackage_id=pd.getString("redpackage_id");//红包ID
//		       	String store_redpackets_id=pd.getString("store_redpackets_id");//赠送红包ID
//		       	String sale_money=pd.getString("sale_money");//消费的总金额
//		       	String actual_money=pd.getString("actual_money");//支付的金额（现金或者第三方）
//		       	String user_balance=pd.getString("user_balance");//使用的余额
//		       	String user_integral=pd.getString("user_integral");//使用的积分
//		       	String get_integral=pd.getString("get_integral");//获得的积分
//	      		logger.info(pd.toString()+"-----订单信息");
//	          	pd.put("member_id", pd.getString("payer_id"));
//	           	pd.put("store_id", pd.getString("store_id"));
//	          	PageData mpd=new PageData();
//	       		mpd=appMemberService.findById(pd);//用户详情
//	       		if(mpd == null){
//	       			map.put("result", "0");
//					map.put("message", "会员不存在");
//					map.put("data", "");
//			  		return map;
//	       		}
//		       	PageData spd=new PageData();
//	       		spd=appStoreService.findById(pd);//商家详情
//	       		if(spd == null){
//	       			map.put("result", "0");
//					map.put("message", "商家不存在");
//					map.put("data", "");
//			  		return map;
//	       		}
// 	       		//第三方支付
//     			System.err.println("优惠买单第三方支付===========================");
//  				System.err.println("新增用户余额消费记录===========================");
//  			    double now_money=Double.parseDouble(mpd.getString("now_money"));
// 				double n=Double.parseDouble(user_balance);
// 				double acmoney=Double.parseDouble(actual_money);
// 				//新增用户积分消费记录，新增商家余额
// 				if(n >0){
//  			 			spd.put("wealth_type", "2");
// 						PageData spdwithpd=appStoreService.findWealthById(spd);
// 						double now_wealth=Double.parseDouble(spdwithpd.getString("now_wealth"));
// 						spdwithpd.put("now_wealth", df.format(now_wealth+n+acmoney));
// 						appStoreService.editWealthById(spdwithpd);
// 				}
// 				//新增用户积分消费记录，新增商家积分
// 				System.err.println("新增用户积分消费记录，新增商家积分===========================");
// 				double now_integral=Double.parseDouble(mpd.getString("now_integral"));
// 				PageData moneypd=new PageData();
// 			    double m=Double.parseDouble(user_integral);
// 				if(n  > 0){
//  	 					 	//商家更新积分
// 				 			spd.put("wealth_type", "1");
// 							PageData spdwithpd=appStoreService.findWealthById(spd);
// 							double now_wealth=Double.parseDouble(spdwithpd.getString("now_wealth"));
// 							spdwithpd.put("now_wealth", df.format(now_wealth+m));
// 							appStoreService.editWealthById(spdwithpd);
// 				}
// 				   //新增商家财富历史记录----------
// 	   				PageData swpd=new PageData();
// 	   				swpd.put("wealth_type", "2");
// 	   				swpd.put("profit_type", "3");
// 	   				swpd.put("number", df.format(acmoney+n+m));
// 	   				swpd.put("store_id", spd.getString("store_id"));
// 	   				swpd.put("store_operator_id", pd.getString("store_operator_id"));
// 	   				swpd.put("process_status", "1");
// 	   				swpd.put("pay_type",channel);
// 	   				swpd.put("jiaoyi_id",pd.getString("order_id") );
// 	   				swpd.put("user_id", mpd.getString("member_id"));
// 	   				swpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
// 	   				swpd.put("store_wealthhistory_id",this.get32UUID());
// 	   				appStoreService.saveWealthhistory(swpd);
//   				System.err.println("新增用户积分获赠记录 ===========================");
// 	 			//新增用户积分获赠记录
// 				if(get_integral != null &&  !get_integral.equals("") && Double.parseDouble(get_integral) >0 ){
//	   					//会员获赠积分记录
//	   					moneypd=null;
//	   					moneypd=new PageData();
//	    	 			double g=Double.parseDouble(get_integral);
//	    	 			now_integral=now_integral+g;
//	    	 			//更新积分
//	    	 			System.err.println("更新积分 ===========================");
//	   					moneypd.put("member_id", pd.getString("member_id"));
//	   					moneypd.put("now_integral", df.format(now_integral));
//	   					appMemberService.edit(moneypd);
//	   	 				moneypd.put("wealth_type", "1");
//	   					moneypd.put("consume_type", "1");
//	   					moneypd.put("content", spd.getString("store_name")+" 消费获赠");
//	   					moneypd.put("number", df.format(g));
//	   					moneypd.put("now_money", df.format(now_integral));
//  					appMember_wealthhistoryService.saveWealthhistory(moneypd);//新增个人消费历史
//   					//新增业务员5/服务商20/父级人脉20/爷级人脉20/子公司35财富---------------------------------------------------------
// 		   				double xtmoney=0;
// 		   				try {
// 			   						PageData mmpd=new PageData();
// 			   						xtmoney=g*Const.orderShouyiMoney[0];//系统获取的积分
// 			   						double lessmoneyxtmoney=xtmoney;
// 			   						pd.put("sendxitong_integral", df.format(xtmoney));
// 		 			   				//梗新商家积分
// 			   						spd.put("wealth_type", "1");
// 			 			   			moneypd=appStoreService.findWealthById(spd);
// 			 			   			double now_wealth=Double.parseDouble(moneypd.getString("now_wealth"))-g;
// 			 			   			moneypd.put("now_wealth", df.format(now_wealth));
//		   							appStoreService.editWealthById(moneypd);
// 			 			   			//新增商家赠送积分记录
// 		 			   			    PageData wpd=new PageData();
// 				 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
// 	//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，4-现金支付的金额，5-第三方支付的金额，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
// 					 			    wpd.put("profit_type", "7");
// 					 			    wpd.put("number",df.format( g));
// 					 			    wpd.put("store_id", spd.getString("store_id"));
// 						 			wpd.put("store_operator_id", pd.getString("store_operator_id"));
// 					 			    wpd.put("process_status", "1");
// 					 			    wpd.put("pay_type",channel);
// 					 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
// 					 			    wpd.put("user_id", mpd.getString("member_id"));
// 					 			    wpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
// 					 			    wpd.put("store_wealthhistory_id", this.getTimeID());
// 			 			   			appStoreService.saveWealthhistory(wpd);
// 			 			   			//新增商家返系统积分记录
// 			 			   			now_wealth=now_wealth-xtmoney;
//		   			 			   	moneypd.put("now_wealth", df.format(now_wealth));
//		   							appStoreService.editWealthById(moneypd);
// 			 			   			wpd=null;
// 		 			   			    wpd=new PageData();
// 				 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
// 	//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
// 					 			    wpd.put("profit_type", "11");
// 					 			    wpd.put("number",df.format( xtmoney));
// 					 			    wpd.put("store_id", spd.getString("store_id"));
// 						 			wpd.put("store_operator_id", pd.getString("store_operator_id"));
// 					 			    wpd.put("process_status", "1");
// 					 			    wpd.put("pay_type",channel);
// 					 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
// 					 			    wpd.put("user_id", mpd.getString("member_id"));
// 					 			    wpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
// 					 			    wpd.put("store_wealthhistory_id", this.getTimeID());
// 		 			   				appStoreService.saveWealthhistory(wpd);
// 		 			   				//服务商
// 		 			   				PageData sppd=new PageData();
// 		 			   				sppd=sp_fileService.findById(spd);
// 				            	    double nowmoney=0;
// 				            	    if(sppd != null && sppd.getString("nowmoney") != null && !sppd.getString("nowmoney").equals("")){
// 				            	    	nowmoney=Double.parseDouble(sppd.getString("nowmoney"));
// 				            	    }
// 				            	    pd.put("sp_file_id", sppd.getString("sp_file_id"));
// 				            	    pd.put("sp_getmoney", df.format(xtmoney*Const.orderShouyiMoney[2]));
// 				            	    if(xtmoney*Const.orderShouyiMoney[2] >0){
// 				            		   nowmoney+=xtmoney*Const.orderShouyiMoney[2];
// 					            	   sppd.put("nowmoney", df.format(nowmoney));
// 					            	   sp_fileService.edit(sppd);
// 					            	   lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[2];
// 					            	    //添加一个提现记录
// 					 	 				mmpd.put("profit_name", sppd.getString("team_name"));//收益对象
// 					 	 				mmpd.put("yewu_name", mpd.getString("name"));//业务对象
// 					 	 				mmpd.put("yewu_id", mpd.getString("member_id"));//业务对象
// 					 	 				mmpd.put("yewu_type", "2");//业务对象
// 					 	 				mmpd.put("money",  df.format(xtmoney*Const.orderShouyiMoney[2]));//金额
// 					 	 				mmpd.put("money_type", "2");//1、销售提成: 2、积分收益： 3、平台奖励 
// 					 	 				mmpd.put("operate_type", "1"); //1-服务商，2-业务员
// 					 	 				mmpd.put("operate_id", sppd.getString("sp_file_id")); 
// 					 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
// 					 	 				mmpd.put("service_performance_id", this.get10UID());//收益对象
// 					 	 				service_performanceService.save(mmpd);
// 			            	   }
// 	 			            	    //业务员
// 				            	   PageData clerkpd=new PageData();
// 				            	   clerkpd=clerk_fileService.findById(spd);
// 				            	   nowmoney=0;
// 				            	    if( clerkpd != null && clerkpd.getString("nowmoney") != null &&  !clerkpd.getString("nowmoney").equals("")){
// 				            	    	nowmoney=Double.parseDouble(clerkpd.getString("nowmoney"));
// 				            	    }
// 				            	   pd.put("clerk_file_id", clerkpd.getString("clerk_file_id"));
// 				            	   pd.put("clerk_getmoney", df.format(xtmoney*Const.orderShouyiMoney[1]));
// 				            	   if(xtmoney*Const.orderShouyiMoney[1] > 0){
// 				            		  nowmoney+=xtmoney*Const.orderShouyiMoney[1];
// 					            	   clerkpd.put("nowmoney", df.format(nowmoney));
// 					            	   clerk_fileService.edit(clerkpd);
// 					            	   lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[1];
// 					            	  //添加一个提现记录
// 								 		mmpd=null;
// 					 	 				mmpd=new PageData();
// 					 	 				mmpd.put("profit_name", clerkpd.getString("clerk_name"));//收益对象
// 					 	 				mmpd.put("yewu_name", mpd.getString("name"));//业务对象
// 					 	 				mmpd.put("yewu_id", mpd.getString("member_id"));//业务对象
// 					 	 				mmpd.put("yewu_type", "2");//业务对象
// 					 	 				mmpd.put("money",  df.format(xtmoney*Const.orderShouyiMoney[1]));//金额
// 					 	 				mmpd.put("money_type", "2");//1、销售提成: 2、积分收益： 3、平台奖励 
// 						 	 			mmpd.put("operate_type", "2"); //1-服务商，2-业务员
// 					 	 				mmpd.put("operate_id", clerkpd.getString("clerk_file_id")); 
// 					 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
// 					 	 				mmpd.put("service_performance_id", this.get10UID());//收益对象
// 					 	 				service_performanceService.save(mmpd);
// 				            	   }
// 				            	   //会员的父级人脉
// 				            	   PageData parentpd=new PageData();
// 				            	   String recommended_type=mpd.getString("recommended_type");
// 				            	   String recommended=mpd.getString("recommended");
// 				            	   if(recommended_type.equals("1")){//商家的积分
// 				            		   	xtmoney=xtmoney-xtmoney*Const.orderShouyiMoney[3];
// 				            		   	lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[3];
// 				            		   	pd.put("onecontacts_id", recommended);
// 				            		   	pd.put("onecontacts_type", "1");
// 						            	pd.put("onecontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[3]));
// 						            	pd.put("twocontacts_id", "0");
// 				            		   	pd.put("twocontacts_type", "0");
// 						            	pd.put("twocontacts_getmoney", "0");
// 						            	//新增商家收益积分记录
//	   						            now_wealth=now_wealth+xtmoney*Const.orderShouyiMoney[3];
//			   			 			   	moneypd.put("now_wealth", df.format(now_wealth));
//			   							appStoreService.editWealthById(moneypd);
// 			 			   				wpd=null;
// 			 			   			    wpd=new PageData();
// 					 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
// 	//1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
// 						 			    wpd.put("profit_type", "12");
// 						 			    wpd.put("number",df.format( xtmoney*Const.orderShouyiMoney[3]));
// 						 			    wpd.put("store_id", spd.getString("store_id"));
// 							 			wpd.put("store_operator_id", pd.getString("store_operator_id"));
// 						 			    wpd.put("process_status", "1");
// 						 			    wpd.put("pay_type", channel);
// 						 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
// 						 			    wpd.put("user_id", mpd.getString("member_id"));
// 						 			    wpd.put("last_wealth", appStoreService.sumStoreMoney(spd));
// 						 			    wpd.put("store_wealthhistory_id", this.getTimeID());
// 			 			   				appStoreService.saveWealthhistory(wpd);
// 	 			            	   }else if(recommended_type.equals("2")){
// 	 			            		    pd.put("onecontacts_id", recommended);
// 	 			            		    pd.put("onecontacts_type", "2");
// 	 			            		    pd.put("onecontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[3]));
// 						            	double parentIntegral=0;
// 						            	//更新会员积分
// 				            		    if(xtmoney*Const.orderShouyiMoney[3] >0){
// 				            		    	parentpd.put("member_id", recommended);
// 					            		    parentpd=appMemberService.findById(parentpd);
// 				            		    	parentIntegral=Double.parseDouble(parentpd.getString("now_integral"))+xtmoney*Const.orderShouyiMoney[3];
// 					            		    double onecontactintegral=Double.parseDouble(parentpd.getString("onecontactintegral"))+xtmoney*Const.orderShouyiMoney[3];
// 					            		    parentpd.put("now_integral", df.format(parentIntegral));
// 					            		    parentpd.put("onecontactintegral", df.format(onecontactintegral));
// 					   						appMemberService.edit(parentpd);
// 					   					    //新增会员积分支出历史
// 					   						parentpd.put("wealth_type", "1");//1-积分（送/增），2-金钱（充值提现）
// 					   						parentpd.put("consume_type", "1");//获赠1-收益（提现，获赠），支出2-消费（充值余额/送积分）
// 					   						parentpd.put("content", "一度人脉消费获益");
// 					   						parentpd.put("number", df.format(xtmoney*Const.orderShouyiMoney[3]));
// 					   						parentpd.put("now_money", df.format(parentIntegral));
// 					   						appMember_wealthhistoryService.saveWealthhistory(parentpd);
// 					   					    //获取爷爷
// 					   						recommended_type=parentpd.getString("recommended_type");
// 			 			            	    recommended=parentpd.getString("recommended");
// 			 			            	    if(recommended_type.equals("1")){
// 			 			            	    		xtmoney=xtmoney-xtmoney*Const.orderShouyiMoney[4];
// 			 			            	    		lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[4];
// 			 			            	    		pd.put("twocontacts_id", recommended);
// 			 				            		   	pd.put("twocontacts_type", "1");
// 			 						            	pd.put("twocontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[4]));
// 			 						           //新增商家返系统积分记录
// 			 						            	now_wealth=now_wealth+xtmoney*Const.orderShouyiMoney[4];
// 			 						            	moneypd.put("now_wealth", df.format(now_wealth));
// 			 						            	appStoreService.editWealthById(moneypd);
// 						 			   				wpd=null;
// 						 			   			    wpd=new PageData();
// 								 			   	    wpd.put("wealth_type", "1");//1-积分，2-余额
// //1-商家收益（提现），2-商家消费（充值积分），3-用户支付的收益金钱/积分，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
// 									 			    wpd.put("profit_type", "12");
// 									 			    wpd.put("number",df.format( xtmoney*Const.orderShouyiMoney[4]));
// 									 			    wpd.put("store_id", spd.getString("store_id"));
// 									 			    wpd.put("store_operator_id", pd.getString("store_operator_id"));
// 									 			    wpd.put("process_status", "1");
// 									 			    wpd.put("jiaoyi_id",pd.getString("order_id") );
// 									 			    wpd.put("user_id", mpd.getString("member_id"));
// 									 			    wpd.put("pay_type",channel);
// 									 			    wpd.put("last_wealth",appStoreService.sumStoreMoney(spd));
// 									 			    wpd.put("store_wealthhistory_id", this.getTimeID());
// 						 			   				appStoreService.saveWealthhistory(wpd);
// 		 	 			            	    }else if(recommended_type.equals("2")){
// 		 	 			            	    	pd.put("twocontacts_id", recommended);
// 						            		   	pd.put("twocontacts_type", "1");
// 								            	pd.put("twocontacts_getmoney", df.format(xtmoney*Const.orderShouyiMoney[4]));
// 								            	if(xtmoney*Const.orderShouyiMoney[4] >0){
// 								            		parentpd.put("member_id", recommended);
// 				 			            		    parentpd=appMemberService.findById(parentpd);; 
// 				 			    				    //更新会员积分
// 				 			            		    parentIntegral=Double.parseDouble(parentpd.getString("now_integral"))+xtmoney*Const.orderShouyiMoney[4];
// 				 			            		    double twocontactintegral=Double.parseDouble(parentpd.getString("twocontactintegral"))+xtmoney*Const.orderShouyiMoney[4];
// 				 			            		    parentpd.put("now_integral", df.format(parentIntegral));
// 				 			            		    parentpd.put("twocontactintegral", df.format(twocontactintegral));
// 				 			            		    appMemberService.edit(parentpd);
// 			 	 			            		    //新增会员积分支出历史
// 				 			            		    parentpd.put("wealth_type", "1");//1-积分（送/增），2-金钱（充值提现）
// 				 			            		    parentpd.put("consume_type", "1");//获赠1-收益（提现，获赠），支出2-消费（充值余额/送积分）
// 				 			            		    parentpd.put("content", "二度人脉消费获益");
// 				 			            		    parentpd.put("number", df.format(xtmoney*Const.orderShouyiMoney[4]));
// 				 			            		    parentpd.put("now_money", df.format(parentIntegral));
// 				 			   						appMember_wealthhistoryService.saveWealthhistory(parentpd);
// 				 			   						lessmoneyxtmoney=lessmoneyxtmoney-xtmoney*Const.orderShouyiMoney[4];
// 								            	}
// 			 			            	    }
// 				            		    }
// 				            	   }
// 				            	   if(lessmoneyxtmoney > 0){
// 				            		   //子公司--减法运算
// 					            	   PageData supd=new PageData();
// 					            	   supd=subsidiaryService.findById(sppd);
// 			 			            	nowmoney=0;
// 					            	    if(supd != null && supd.getString("nowmoney") != null && !supd.getString("nowmoney").equals("")){
// 					            	    	nowmoney=Double.parseDouble(supd.getString("nowmoney"));
// 					            	    }
// 					            	   nowmoney+=lessmoneyxtmoney;
// 					            	   supd.put("nowmoney", df.format(nowmoney));
// 					            	   subsidiaryService.edit(supd);
// 					            	   pd.put("subsidiary_id", supd.getString("subsidiary_id"));
// 					            	   pd.put("subsidiary_getmoney", df.format(lessmoneyxtmoney));
// 					            	   supd=null;
// 				            	   }
//  		   				} catch (Exception e) {
// 						// TODO: handle exception
// 						System.out.println("新增业务员5/服务商20/父级人脉20/爷级人脉20/子公司35财富出错"+e.toString());
//  					}
//  	   				//新增商家的综合分值
// 		   			    logger.info("==========================更新商家的综合分值");
// 		   			    double complex_score=Double.parseDouble(spd.getString("complex_score"));
// 		   				n =g;
// 		   				PageData compd=new PageData();
//  		   				if( n<=5 ){
// 		   				    complex_score+=Double.parseDouble( Const.complexscore[4]);
//  		   				}else if(5< n  && n<= 10){
// 		   					complex_score+=Double.parseDouble( Const.complexscore[5]);
//  		   				}else if(10< n  && n<= 30){
// 			   				complex_score+=Double.parseDouble( Const.complexscore[6]);
//  		   				}else if(30< n  && n<= 100){
// 			   				complex_score+=Double.parseDouble( Const.complexscore[7]);
//  		   				}else if(100< n){
// 			   				complex_score+=Double.parseDouble( Const.complexscore[8]);
//  		   				}
// 		   				//根据综合分值增加星级
// 					    compd=new PageData();
// 					    complex_score=complex_score+3;
// 						compd.put("store_id", pd.getString("store_id"));
// 						compd.put("complex_score", complex_score);
// 						if(complex_score >= Const.xingcomplexscore[2]){
// 							compd.put("merchant_level", "3");
// 							compd.put("goods_max", Const.xingcomplexscoregoodsnumber[2]);
// 			   			}else if(complex_score >=  Const.xingcomplexscore[1]){
// 			   				compd.put("merchant_level", "2");
// 			   				compd.put("goods_max", Const.xingcomplexscoregoodsnumber[1]);
// 			   			}else{
// 			   				compd.put("merchant_level", "1");
// 			   				compd.put("goods_max", Const.xingcomplexscoregoodsnumber[0]);
// 			   			}
// 		   				storeService.edit(compd);
// 		   				//加综合分值结束
// 				}
//  				//更改红包状态
//  				System.err.println("删除使用红包状态 ===========================");
// 				if(redpackage_id != null && !redpackage_id.equals("")){
// 					try {
// 						 appMember_redpacketsService.deleteRed(pd);
// 						 //更新红包数量
// 						 appMemberService.updateMemberRedNumber(mpd);
// 					} catch (Exception e) {
// 						// TODO: handle exception
// 					}
//  			}
//  			System.err.println("新增获赠红包 ===========================");
// 				//新增获赠红包
// 				if(store_redpackets_id != null && !store_redpackets_id.equals("")){
// 					 String[] str=store_redpackets_id.split(",");
// 					 for (int i = 0; i < str.length; i++) {
// 									pd.put("store_redpackets_id", str[i]);
// 										PageData e2=new PageData();
// 									PageData redpd=appStore_redpacketsService.findRedById(pd);
// 					 				if(redpd != null){
// 								 					String redpackage_type="";
// 								 					String redpackage_content="";
// 													if(redpd.getString("redpackage_type").equals("1")){//现金
// 														redpackage_type="21";
// 														redpackage_content=redpd.getString("srp_usercondition_content")+"减"+redpd.getString("money")+"元";
// 													}else if(redpd.getString("redpackage_type").equals("2")){//折扣
// 														redpackage_type="22";
// 														redpackage_content=redpd.getString("srp_usercondition_content")+"打"+redpd.getString("money")+"折";
// 													}
// 													e2.put("redpackage_id",this.get32UUID());
// 													e2.put("member_id", pd.getString("member_id"));
// 													e2.put("redpackage_money", redpd.getString("money"));
// 													e2.put("redpackage_content", redpackage_content);
// 													e2.put("store_redpackets_id", redpd.getString("store_redpackets_id"));
// 													e2.put("redpackage_type", redpackage_type);
// 													e2.put("enddate", redpd.get("endtime").toString());
// 													e2.put("startdate", redpd.get("starttime").toString());
// 													e2.put("set_id", redpd.getString("store_id"));
// 													e2.put("set_type", "1");
// 													appMemberService.saveRedForMember(e2);//新增红包信息至会员
// 											}
// 									}
// 						//更新红包数量
// 						appMemberService.updateMemberRedNumber(mpd);
// 					}
//  				System.out.println("新增订单信息================================");
//  				pd.put("order_status", "1");
//  				pd.put("channel", channel);
//	   				appOrderService.editStatus(pd);
//		   			//新增支付记录tb_pay_history
//	   				PageData phpd=new PageData();
//	   				phpd.put("pay_history_id",  this.getXFUID());
//	   				phpd.put("user_type",  "2");
//	   				phpd.put("user_account",  mpd.getString("phone"));
//	   				phpd.put("money_type",  "2");//1-充值（商家或会员充积分），2-消费，3-商家购买保证金，4-服务商支付保证金
//	   				phpd.put("pay_status",  "1");
//	   				phpd.put("money",  sale_money);
//	   				phpd.put("remittance_type",  "1");// 1-会员端现金支付，2- 商家端现金支付，3-支付宝支付，4-微信支付，5-苹果支付
//	   				phpd.put("remittance_name",  "会员端现金支付");
//	   				phpd.put("remittance_number", mpd.getString("phone"));
//	   				phpd.put("order_tn",  this.getTimeID());
//	   				phpd.put("payee_number",  pd.getString("store_id"));
//	   				phpd.put("payer_id",  pd.getString("member_id"));
//	   				phpd.put("operate_id",  pd.getString("member_id"));
//	   				phpd.put("order_id", pd.getString("order_id"));
//	   				phpd.put("province_name", mpd.getString("province_name"));
//	   				phpd.put("city_name", mpd.getString("city_name"));
//	   				phpd.put("area_name", mpd.getString("area_name"));
//	   				appPay_historyService.savePayhistory(phpd);
//	   			    //修改订单状态
//					pd.put("tihuo_status", "1");
//					payapp_historyService.editOrderStatus(pd);
//		   		   //营销记录表
//		   		 	logger.info("==========================新增营销记录表");
//		   		    YingXiao(pd);
//		   		    //更新个人信息
//		   		    logger.info("==========================更新个人次数");
//		   		    mpd.put("pay_way", channel);
//		   		    mpd.put("salemoney", df.format(Double.parseDouble(actual_money)+Double.parseDouble(user_integral)+Double.parseDouble(user_balance)));
//		   		    appMemberService.updateMemberById(mpd); 
//		   		   //更新商家的综合分值
//		   		   logger.info("==========================更新商家的综合分值");
//			   		spd.put("number", Const.complexscore[1]);
//					storeService.editComplexscore(spd);
//					//更新商家的订单交易次数
//					logger.info("==========================更新商家的交易");
//					spd.put("transaction_times", "1");
//					appStoreService.edit(spd);
//					//更新会员指定商家的vip内容
//					appMemberService.updateStoreVipById(pd);
//				   //魅力值：0-50一星会员 50-99 二星会员 100-199 三星会员 200-499  四星会员 500-999 五星会员 1000-2000 一钻会员
//					double charm_number=Double.parseDouble(mpd.getString("charm_number"))+Double.parseDouble(Const.charm_number[5]);
//					PageData chpd=new PageData();
//					chpd.put("member_id", mpd.getString("member_id"));
//					chpd.put("charm_number", charm_number);
//					if(charm_number >=0 && charm_number < 50){
//						chpd.put("vip_level", "1");
//					}else if(charm_number >=50 && charm_number < 100){
//						chpd.put("vip_level", "2");
//					}else if(charm_number >=100 && charm_number < 200){
//						chpd.put("vip_level", "3");
//					}else if(charm_number >=200 && charm_number < 500){
//						chpd.put("vip_level", "4");
//					}else if(charm_number >=500 && charm_number < 1000){
//						chpd.put("vip_level", "5");
//					}else if(charm_number >=1000 && charm_number < 2000){
//						chpd.put("vip_level", "6");
//					}
//					appMemberService.edit(chpd);
//	    			System.out.println("第三方支付=====================================结束");
//	    			String allgoods_id="";
// 					//获取商品信息
//					List<PageData> goodsList=appGoodsService.listAllGoodsByOrder(pd);
//					for(PageData goodspd : goodsList){
//						allgoods_id+=goodspd.getString("goods_id")+"*";
//					}
//					//获取购物车
//					List<PageData> shopList=carService.shopCar(pd);
//					for(PageData shoppd: shopList){
//						if(allgoods_id.contains(shoppd.getString("goods_id"))){
//							//清空购物车
//							carService.delShop(shoppd);
//						}
//					}
//    	} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");
//  		return map;
//	}
//	
//	/**
//	 * 订单退货  魏汉文20160706
//	 */
//	@RequestMapping(value="/returnOrder")
//	@ResponseBody
//	public Object returnOrder(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		String result = "1";
//		String message="退货成功";
//		PageData pd = new PageData();
//		try{ 
//			pd=this.getPageData();
//			pd=appOrderService.findById(pd);//订单详情
//	 		String member_id=pd.getString("payer_id");
//	 		String user_balance=pd.getString("user_balance");
//	 		String user_integral=pd.getString("user_integral");
//	 		String actual_money=pd.getString("actual_money");
//	 		String get_integral=pd.getString("get_integral");
//	 		String tihuo_status=pd.getString("tihuo_status");
//	 		String order_status=pd.getString("order_status");
//	 		if(order_status.equals("1")){//支付完成
//		 			if(tihuo_status.equals("0")){//未提货
//		 				PageData mpd=new PageData();
//		 				mpd.put("member_id", member_id);
//		 				mpd=appMemberService.findById(mpd);
//		 				if(mpd != null){
//		 					double now_money=Double.parseDouble(mpd.getString("now_money"));
//			 				double now_integral=Double.parseDouble(mpd.getString("now_integral"));
//			 				//增加用户余额以及积分
//			 				//个人余额消费历史
//			 				double n=Double.parseDouble(user_balance+actual_money);
//			 				double m=Double.parseDouble(user_integral);
//			   				now_money=now_money+n;
//							PageData moneypd=new PageData();
//							moneypd.put("member_id", pd.getString("member_id"));
//		 					moneypd.put("wealth_type", "2");
//							moneypd.put("consume_type", "2");
//							moneypd.put("content", "未提货退余额");
//							moneypd.put("number", df.format(n));
//							moneypd.put("now_money", df.format(now_money));
//							appMember_wealthhistoryService.saveWealthhistory(moneypd);
//							//更新金钱
//							appMemberService.edit(moneypd);
//								//更新积分
//							now_integral=now_integral+m;
//		  					moneypd.put("member_id", pd.getString("member_id"));
//	 						moneypd.put("now_integral", df.format(now_integral));
//	 						appMemberService.edit(moneypd);
//						     //新增个人积分消费历史
//							  moneypd.put("wealth_type", "1");
//							  moneypd.put("consume_type", "2");
//							  moneypd.put("content",  "未提货退积分");
//							  moneypd.put("number", df.format(m));
//							  moneypd.put("now_money", df.format(now_integral));
//							  appMember_wealthhistoryService.saveWealthhistory(moneypd);
//				 			 //更改提货单状态,订单状态
//		 			   		  pd.put("order_status", "3");
//		 			   		  pd.put("tihuo_status", "2");
//		 			   		  appOrderService.editStatus(pd);
//		 				}
//			 		}
//	 		}
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");
//  		return map;
//	}
//	
//	@Resource(name="shopCarService")
//	private ShopCarService carService;
//	@Resource(name="appGoodsService")
//	private AppGoodsService appGoodsService;
//	@Resource(name="appStore_redpacketsService")
//	private AppStore_redpacketsService store_redpacketsService;
//	@Resource(name="appStoreService")
//	private AppStoreService appStoreService;
//	@Resource(name="appMember_redpacketsService")
//	private AppMember_redpacketsService appMember_redpacketsService;
//	@Resource(name="appStore_redpacketsService")
//	private AppStore_redpacketsService appStore_redpacketsService;
//	@Resource(name="appStorepc_marketingService")
//	private AppStorepc_marketingService appStorepc_marketingService;
//	@Resource(name="appMemberService")
//	private AppMemberService appMemberService;
//	@Resource(name = "storepc_marketingtypeService")
//	private Storepc_marketingtypeService storepcMarketingTypeService;
//	@Resource(name="appOrderService")
//	private AppOrderService appOrderService;
//	@Resource(name = "tablerNumberService")
//	private TablerNumberService tablerNumberService;
//	
//	/**
//	 * 优惠买单，按总金额购买，商家ID，支付金钱,不优惠金额
//	 * 魏汉文20160628
//	 */
//	@RequestMapping(value="/allMoneyByOne")
//	@ResponseBody
//	public Object allMoneyByOne(){
//		logBefore(logger, "优惠买单，按总金额购买");
//		Map<String,Object> map = new HashMap<String,Object>();
//		Map<String,Object> map1 = new HashMap<String,Object>();
//  		List<PageData> yingxiaoList=new ArrayList<PageData>();//用来存储营销List
//  		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//  		List<PageData> noList = new ArrayList<PageData>();//操作员所对应桌号
//		String result = "1";
//		String message="按总金额购买";
//		PageData pd = new PageData();
//		try{ 
//				pd = this.getPageData();
//				//操作员登录
//				String store_operator_id = pd.getString("store_operator_id");
//				if(store_operator_id != null && !store_operator_id.equals("")){
//					PageData pg = new PageData();
//					pg = storeManageService.findOperatorById(pd);
//					if(pg != null && !pg.equals("")){
//						String alldesk_no = pg.getString("alldesk_no");
//						if(alldesk_no != null && !alldesk_no.equals("")){
//							String[] no = alldesk_no.split(",");
//							for (int i = 0; i < no.length; i++) {
//								PageData e = new PageData();
//								e.put("table_name", no[i]);
//								noList.add(e);
//							}
//						}
//					}
//				}else{
//					//商家登录
//					String store_id = pd.getString("store_id");
//					if(store_id != null && !store_id.equals("")){
//						//获取改商家桌号
//						noList = tablerNumberService.listAll(pd);
//					}
//				}
// 				map1.put("noList", noList);
//				noList=null;
//				String paymoney=pd.getString("paymoney");
// 				if(paymoney == null || paymoney.equals("")){
//					paymoney="0";
//				}
//				String notmoney=pd.getString("notmoney");
//				if(notmoney == null || notmoney.equals("")){
//					notmoney="0";
//				}
//				double money=Double.parseDouble(paymoney)-Double.parseDouble(notmoney);
//				if(money < 0){
//					money=0;
//				}
//				//获取营销规则明细
//				PageData yxpd=markeingAll(pd);
//				map1.put("yxpd", yxpd);
//				//1.先获取营销中的折扣设置
//				PageData typepd=new PageData();
// 				typepd.put("marketing_type", "1");
// 				typepd.put("store_id", pd.getString("store_id"));
// 				List<PageData> zklist=appStorepc_marketingService.listAllById(pd);
// 				String zkcontent="";
// 				double zkmoney=0;
// 				String zkid="";
// 				String zktype="";
// 				for(PageData e : zklist){
//		 					String marketing_type=e.getString("marketing_type");
//	 						String grantrule=e.getString("grantrule");
//							String marketing_id=e.getString("marketing_id");
// 							e.put("store_discountway_id", marketing_id);
//							//获取所有启用的折扣
//							PageData zkpd=appStorepc_marketingService.getZKById(e);
//							if(zkpd != null){
// 									String zkgrantrule=zkpd.getString("grantrule");
//									if(zkpd.getString("discount_type").equals("1")){//整店折扣
//											String number=zkpd.getString("onealldiscount_rate");
//											double n=0;
//											if(number.length() == 1){
//												n=1-Double.parseDouble(number)/10.0;
//											}else if(number.length() == 2){
//												n=1-Double.parseDouble(number)/100;
//											}else if(number.length() == 3){
//												n=1-Double.parseDouble(number)/1000;
//											}
//			 								double m=n*money;
//			 								if(m > zkmoney ){
//			 									zkcontent=grantrule;
//			 									zkmoney=m;
//			 									zktype=marketing_type;
//			 									zkid=marketing_id;
//			 								}
//									}else if(zkpd.getString("discount_type").equals("4")){//满多少折多少
//										String[] str=zkgrantrule.split(",");
//		 	 							for(int i=0; i<str.length ;i++){ 
//					 	 							String number1=str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元"));
//			  		  								String number2=str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折"));
//			  		 								double n1=Double.parseDouble(number1);
//			  		 								double n2=0;
//													if(number2.length() == 1){
//														n2=1-Double.parseDouble(number2)/10.0;
//													}else if(number2.length() == 2){
//														n2=1-Double.parseDouble(number2)/100;
//													}else if(number2.length() == 3){
//														n2=1-Double.parseDouble(number2)/1000;
//													}
//			  		 								if(n2*money >zkmoney && money >=n1){
//			  		 									zkcontent=grantrule;
//					 									zkmoney=n2*money;
//					 									zktype=marketing_type;
//					 									zkid=marketing_id;
//			  		 								}
//		 	 							}
//									}else if(zkpd.getString("discount_type").equals("2")){ 
//										System.out.println("直接收银无按类别折扣");
//									}else if(zkpd.getString("discount_type").equals("3")){ 
// 										System.out.println("直接收银无按单品设置");
//									}
//							}
// 				}
//  				//满减折扣
// 				if(!zkcontent.equals("") && zkmoney>0 ){
// 					PageData zkpd=new PageData();
// 					zkpd.put("content", zkcontent);
// 					zkpd.put("id", zkid);
// 					zkpd.put("type", zktype);
// 					zkpd.put("number", "-"+df.format(zkmoney));
// 					yingxiaoList.add(zkpd);
// 				}
// 				
//  				//获取营销规则
// 				List<PageData> marketlist=appStorepc_marketingService.listAllById(pd);
// 				PageData e1=new PageData();
// 				PageData e2=new PageData();
// 				PageData e3=new PageData();
// 				//获得积分
// 				boolean isopenjf=false;
// 				double addjf=0;
//	 			String desc="";
//	 			String addjfid="";
//	 			//优惠内容
//  				String zengcontent="";
//  				String zengid="";
//  				String zengtype="";
//  				String zengcontent2="";
//  				String zengid2="";
//  				String zengtype2="";
// 				String jiancontent="";
// 				String jiantype="";
// 				String jianid="";
//   				double reducemoney=0;
// 				for(PageData e : marketlist){
//										/*
//										 * *1-满赠，*2-满减，3-时段营销，4-买N减N（针对商品），5-累计次数/购买金额--增,6-积分，7-折扣
//										 */
//										String marketing_type=e.getString("marketing_type");
//										String change_type=e.getString("change_type");
// 			 							String grantrule=e.getString("grantrule");
//			 							String marketing_id=e.getString("marketing_id");
//			 							/*
//			 							 * 满赠
//			 							 */
//			 							if(marketing_type.equals("1")){
//			 								    String number=grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元"));
//				 								double n=Double.parseDouble(number);
//				 								if(change_type.equals("2")){
//					 									if(money >= n){
//						 									zengcontent=grantrule;
//						 									zengid=marketing_id;
//						 									zengtype=marketing_type;
//						 								}
//				 								}
// 				 								/*
//				 								 * 满减
//				 								 */
//			  							}else if(marketing_type.equals("2")){
//			  									if(grantrule.contains("折")){
//				  										String number1=grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元"));
//				  		  								String number2=grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折"));
//				  		 								double n1=Double.parseDouble(number1);
//				  		 								double n2=0;
//						 								if(number2.length() == 1){
//						 									n2=1-Double.parseDouble(number2)/10;
//						 								}else if(number2.length() == 2){
//						 									n2=1-Double.parseDouble(number2)/100;
//						 								}else if(number2.length() == 3){
//						 									n2=1-Double.parseDouble(number2)/1000;
//						 								}
//				  		 								if(n2*money >reducemoney && money >=n1){
//				  		 									jiancontent=grantrule;
//				  		 									reducemoney=n2*money;
//				  		 									jiantype=marketing_type;
//				  		 									jianid=marketing_id;
//				  		 								}
//			  									}else{
//				  										String number1=grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元"));
//				  		  								String number2=grantrule.substring(grantrule.indexOf("减")+1, grantrule.lastIndexOf("元"));
//				  		 								double n1=Double.parseDouble(number1);
//				  		 								double n2=Double.parseDouble(number2);
//				  		 								if(n2 >reducemoney && money >=n1){
//				  		 									jiancontent=grantrule;
//				  		 									reducemoney=n2;
//				  		 									jiantype=marketing_type;
//				  		 									jianid=marketing_id;
//				  		 								}
//			  									}
//			  									/*
//			  									 * 积分
//			  									 */
//			   							}else if(marketing_type.equals("6")){ 
// 			   								isopenjf=true;
//			   								addjfid=marketing_id;
// 			   							}else if(marketing_type.equals("7")){ 
// 			   								//在第一步
// 			   							}else if(marketing_type.equals("3")){
//			   							  PageData sdpd=new PageData();
//			   							  sdpd.put("marketing_id", marketing_id);
//			   							  sdpd.put("store_id", pd.getString("store_id"));
//			   							  sdpd=storepcMarketingTypeService.findById(sdpd);
//			   							  if(sdpd != null){
//					   								 String threeachieve_money=sdpd.getString("threeachieve_money");
//						   							  String marketsmall_type=sdpd.getString("marketsmall_type");
//						   							  String threereduce_money=sdpd.getString("threereduce_money");
//						   							  String threediscount_rate=sdpd.getString("threediscount_rate");
//						   							  Date date=new Date();
//						   							  long l1=date.getTime();
//						   							  String nowday=DateUtil.getDay();
//						   							String starttime=sdpd.get("starttime").toString();
//						   							String endtime=sdpd.get("endtime").toString();
//						   							  String str2=nowday+" "+starttime;
//						   							  long l2=DateUtil.fomatDate1(str2).getTime();
//			 			   							  String str3=nowday+" "+endtime;
//						   							  long l3=DateUtil.fomatDate1(str3).getTime();
//						   							  if(  Double.parseDouble(threeachieve_money) <= money &&  l1 > l2 && l1 < l3 ){
//						   								  	 if(marketsmall_type.equals("1")){//折
//					   								  		 	 if(threediscount_rate.length() == 1){
//							   								  		 	double mm=money*(1-Double.parseDouble(threediscount_rate)/10.0);
//							   								  	         if(  mm > reducemoney){
//											   								  	        jiancontent=grantrule;
//											  		 									reducemoney=mm;
//											  		 									jiantype=marketing_type;
//											  		 									jianid=marketing_id;
//							   								  	         }
//					   								  		 	 }else  if(threediscount_rate.length() == 2){
//							   								  		 	double mm=money*(1-Double.parseDouble(threediscount_rate)/100);
//							   								  	         if(  mm > reducemoney){
//											   								  	        jiancontent=grantrule;
//											  		 									reducemoney=mm;
//											  		 									jiantype=marketing_type;
//											  		 									jianid=marketing_id;
//							   								  	         }
//					   								  		 	 }else  if(threediscount_rate.length() == 3){
//							   								  		 	double mm=money*(1-Double.parseDouble(threediscount_rate)/1000);
//							   								  	         if(  mm > reducemoney){
//											   								  	        jiancontent=grantrule;
//											  		 									reducemoney=mm;
//											  		 									jiantype=marketing_type;
//											  		 									jianid=marketing_id;
//							   								  	         }
//					   								  		 	 } 
//					   								  	}else{//钱
//								   								  	if( Double.parseDouble(threereduce_money) > reducemoney){
//										   								  	        jiancontent=grantrule;
//										  		 									reducemoney= Double.parseDouble(threereduce_money);
//										  		 									jiantype=marketing_type;
//										  		 									jianid=marketing_id;
//															  	         }
//						   								  	 }
//						   							  }
//			   							  }
//			   							/*
//				   							 * 累计次数
//				   							 */
//			   						}else if(marketing_type.equals("5")){
//					   						PageData ljpd=new PageData();
//					   						ljpd.put("marketing_id", marketing_id);
//					   						ljpd.put("store_id", pd.getString("store_id"));
//					   						ljpd=storepcMarketingTypeService.findById(ljpd);
//							   				if(ljpd != null){
//							   							String fiveachieve_number=ljpd.getString("fiveachieve_number");
////						   								 String marketsmall_type=ljpd.getString("marketsmall_type");
//						   								 String fiveachieve_money=ljpd.getString("fiveachieve_money");
//						   								Date date=new Date();
//							   							long l1=date.getTime();
//			  				   							String nowday=DateUtil.getDay();
//							   							String starttime=ljpd.get("starttime").toString();
//							   							String endtime=ljpd.get("endtime").toString();
//							   							String str2=nowday+" "+starttime;
//			 	 			   							String str3=nowday+" "+endtime;
//				 	 			   						  long l2=DateUtil.fomatDate1(str2).getTime();
//							   							  long l3=DateUtil.fomatDate1(str3).getTime();
//							   							  if(  l1 > l2 && l1 < l3 ){
//								   								PageData orderpd=new PageData();
//						 	 			   						orderpd.put("starttime", str2);
//						 	 			   						orderpd.put("endtime", str3);
//						 	 			   						orderpd.put("store_id", pd.getString("store_id"));
//						 	 			   						orderpd.put("member_id", pd.getString("member_id"));
//					 				   							orderpd=appOrderService.listhistoryNumberByStore(orderpd);
//					 				   							String number=orderpd.getString("number");
//					 				   							String sumsale_money="0";
//					 				   							if(orderpd.get("sumsale_money") !=null){
//					 				   								sumsale_money=orderpd.get("sumsale_money").toString();
//					 				   							}
//					 				   							//判断是否满足条件
//						 				   						if(Integer.parseInt(number)%Integer.parseInt(fiveachieve_number) == 0 &&  Double.parseDouble(sumsale_money)%Double.parseDouble(fiveachieve_money) >= 0 && Double.parseDouble(sumsale_money)%Double.parseDouble(fiveachieve_money) < 1){
// 					 				   								zengcontent2=grantrule;
//								 									zengid2=marketing_id;
//								 									zengtype2=marketing_type;
//						 				   						}
//					 				   							
//							   							  }
//							   				}
//						   					 
// 			   						}
// 			}
// 				//满赠
// 				if(!zengcontent.equals("")){
// 					e1.put("content", zengcontent);
// 					e1.put("number", "");
// 					e1.put("id", zengid);
// 					e1.put("type", zengtype);
// 					yingxiaoList.add(e1);
// 				}
// 				//累计次数
// 				PageData ldpd=new PageData();
// 				if(!zengcontent2.equals("")){
// 					ldpd.put("content", zengcontent2);
// 					ldpd.put("number", "");
// 					ldpd.put("id", zengid2);
// 					ldpd.put("type", zengtype2);
// 					yingxiaoList.add(ldpd);
// 				}
// 				//满减
// 				if(!jiancontent.equals("") && reducemoney>0 ){
// 					e2.put("content", jiancontent);
// 					e2.put("id", jianid);
// 					e2.put("type", jiantype);
// 					e2.put("number", "-"+df.format(reducemoney));
// 					yingxiaoList.add(e2);
// 				}
// 				int n=yingxiaoList.size();
//				 //使用红包前的优惠金额
//				 double useredbeforMoney=reducemoney+zkmoney;
// 				//红包
//  				PageData redpd=getMaxStoreRedMoney(pd,money,n,useredbeforMoney);
//  				String red_id="";
//  				String redmoney="";
// 				if(redpd != null){
// 					red_id=redpd.getString("id");
// 	 				redmoney=redpd.getString("number");
// 	 				redpd.put("number", "-"+redmoney);
// 	 				redpd.put("type", "0");
// 					yingxiaoList.add(redpd);
//   				}else{
//  					redmoney="0";
//  				}
//  				//使用红包后的优惠后的实际应该支付的金额为
//  				double surepaymoney=money-reducemoney-Double.parseDouble(redmoney)-zkmoney+Double.parseDouble(notmoney);
//  				//总共优惠金额
//  				double surehuiyoumoney=reducemoney+Double.parseDouble(redmoney)+zkmoney;
// 				if(isopenjf){
// 					    double redhuiyoumoney=surepaymoney-Double.parseDouble(notmoney);
// 						PageData e=new PageData();
//						e.put("store_scoreway_id", addjfid);
//						e.put("store_id", pd.getString("store_id"));
//					    //获取所有启用的积分
//						PageData jfpd=appStorepc_marketingService.getJfById(e);
// 						if(jfpd != null){ 
//									//整店
////				 					String jfcontent=jfpd.getString("content");
//				 					String jfgrantrule=jfpd.getString("grantrule");
//				  	 				String jftype=jfpd.getString("change_type");
//				 	 				if(jftype.equals("1")){
//				 	 					String zdrate=jfpd.getString("oneback_rate");
// 				 	 					if(Double.parseDouble(zdrate)/100*redhuiyoumoney >addjf){
//				 	 						addjf=Double.parseDouble(zdrate)/100*redhuiyoumoney ;
//				 	 						desc=jfgrantrule;
// 				 	 					}
//				 	 				}else if(jftype.equals("5")){//满多少送多少积分
//				 	 							String[] str=jfgrantrule.split(",");
//				 	 							for(int i=0; i<str.length ;i++){
//				 	 									String content=str[i];
//				 	 									String number1=content.substring(content.indexOf("满")+1, content.indexOf("元"));
//				  		  								String number2=content.substring(content.indexOf("送")+1, content.lastIndexOf("%"));
//					  		  							double n1=Double.parseDouble(number1);
//					  		  							if(n1 <= redhuiyoumoney){
//	 					  		  							double n2=Double.parseDouble(number2)/100.00;
//					  		 								double n3=n1*n2;
//					  		 								if(n3 >addjf ){
//					  		 									addjf=n3;
//				 					 	 						desc=jfgrantrule;
// 					  		 								}
//					  		  							}
//				 	 							}
//				 	 				}else if(jftype.equals("4")){
//					 	 					String mdjf=jfpd.getString("fourbackintegral_integral");
// 					 	 					if(Double.parseDouble(mdjf) >addjf){
//					 	 						addjf=Double.parseDouble(mdjf);
//					 	 						desc=jfgrantrule;
// 					 	 					}
//				 	 				}  
//						}
//  				}
//  				//积分
//	 			 if(!desc.equals("") && addjf>0){
//	 					e3.put("content", desc);
//		 				e3.put("number", "+"+df.format(addjf));
//		 				e3.put("id", addjfid);
//		 				e3.put("type", "6");
// 	 					yingxiaoList.add(e3);
//	 			}
// 				map1.put("yingxiaoList", yingxiaoList);
// 				PageData countpd=new PageData();
// 				if(surepaymoney <= 0){
//					countpd.put("paymoney", "0");//优惠后的支付金额
//					countpd.put("reducemoney", df.format(money+Double.parseDouble(notmoney)));
//				}else{
//					countpd.put("paymoney", df.format(surepaymoney));
//					countpd.put("reducemoney", df.format(surehuiyoumoney));//优惠金额=优惠的金额+红包优惠的金额+折扣的金额
//				}
// 				//增红包的集合
//				String allzengId="";
//				if(!zengid.equals("")){
//					allzengId=zengid+","+zengid2;
//				}else{
//					allzengId=zengid2;
//				}
// 				countpd.put("zengid", allzengId);
//	 			countpd.put("zengjf", df.format(addjf));//赠送的积分
//	 			countpd.put("red_id", red_id);//使用红包的ID
//	 			countpd.put("allmoney", df.format(money+Double.parseDouble(notmoney)));
//     			map1.put("countpd", countpd);
//     			//判断是否开通类别积分购买的权限
//				PageData ispd=appStorepc_marketingService.getJfById(pd);
//				if(ispd != null && ispd.getString("change_type").equals("3") ){
//					 map1.put("issortjf", "3");
//				}else if(ispd != null && ispd.getString("change_type").equals("2") ){
//					 map1.put("issortjf", "1");
//				} else{
//					 map1.put("issortjf", "0");
//				}
//     		} catch(Exception e){
//			result = "0";
//			message="系统错误";
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data",map1 );
//		return map;
//	}
//	
//	
//	/**
//	 * 优惠买单，按总类别购买，商家ID，支付金钱
//	 * 魏汉文20160629
//	 */
//	@RequestMapping(value="/allMoneyByTwo")
//	@ResponseBody
//	public Object allMoneyByTwo(){
//		logBefore(logger, "优惠买单，按按总类购买");
//		Map<String,Object> map = new HashMap<String,Object>();
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		List<PageData> sortList=new ArrayList<PageData>();//用来存储营销List
//  		List<PageData> yingxiaoList=new ArrayList<PageData>();//用来存储营销List
//  		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//  		List<PageData> noList = new ArrayList<PageData>();//操作员所对应桌号
//		String result = "1";
//		String message="按总类购买";
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
//			//操作员登录
//			String store_operator_id = pd.getString("store_operator_id");
//			if(store_operator_id != null && !store_operator_id.equals("")){
//				PageData pg = new PageData();
//				pg = storeManageService.findOperatorById(pd);
//				if(pg != null && !pg.equals("")){
//					String alldesk_no = pg.getString("alldesk_no");
//					if(alldesk_no != null && !alldesk_no.equals("")){
//						String[] no = alldesk_no.split(",");
//						for (int i = 0; i < no.length; i++) {
//							PageData e = new PageData();
//							e.put("alldesk_no", no[i]);
//							noList.add(e);
//						}
//					}
//				}
//			}
//			//商家登录
//			String store_id = pd.getString("store_id");
//			if(store_id != null && !store_id.equals("")){
//				//获取改商家桌号
//				noList = tablerNumberService.listAll(pd);
//			}
//			map1.put("noList", noList);
//			double money=0;
//			double alljifeng=0;
//			//判断是否开通类别积分
//			PageData ispd=appStorepc_marketingService.getJfById(pd);
//			if(ispd == null || !ispd.getString("change_type").equals("2") ){
//				map.put("result", "0");
//				map.put("message", "暂未开通该通道");
//				map1.put("yingxiaoList", yingxiaoList);
//				map1.put("sortList", sortList);
//				map.put("data",map1 );
//				return map;
//			}
//			//获取商家的营销规则明细
//			PageData yxpd=markeingAll(pd);
//			map1.put("yxpd", yxpd);
//			List<PageData> leibieList=appGoodsService.listAllBigSort(pd);
//			map1.put("sortList", leibieList);
//			//拆分类别（类别ID@金钱@积分率@折扣率）
//			String alllei=pd.getString("allleibie");
//			System.out.println("**********"+alllei);
//			if(alllei != null && !alllei.equals("") && alllei.contains("@")){
//						String[] everylei=alllei.split(",");
//						for(int i=0;i<everylei.length ; i++){
//							  String leimoney=everylei[i].split("@")[1];
//							  String jfrate=everylei[i].split("@")[2];
//							  if(leimoney != null && !leimoney.equals("") && !leimoney.equals("null")){
//								  if(jfrate != null && !jfrate.equals("null") && !jfrate.equals("")){
//									  alljifeng+= Double.parseDouble(leimoney)*Double.parseDouble(jfrate)/100;
//									  money+= Double.parseDouble(leimoney);
//								  }
//							  }
//							  
//						}
//				}
// 				//积分（获取被选中的积分设置）
//				if(alljifeng > 0){
//					PageData jfpd=new PageData();
//	 				jfpd.put("content", "分类赠送积分");
//	 				jfpd.put("number", "+"+df.format( alljifeng ));
//	 				jfpd.put("type", "6");
//	 				jfpd.put("id", ispd.getString("store_scoreway_id"));
//					yingxiaoList.add(jfpd);
//				}
//				//1.先获取营销中的折扣设置
//				PageData typepd=new PageData();
// 				typepd.put("marketing_type", "7");
// 				typepd.put("store_id", pd.getString("store_id"));
// 				List<PageData> zklist=appStorepc_marketingService.listAllById(typepd);
// 				String zkcontent="";
// 				double zkmoney=0;
// 				String zkid="";
// 				String zktype="";
// 				for(PageData e : zklist){
//		 					String marketing_type=e.getString("marketing_type");
//	 						String grantrule=e.getString("grantrule");
//							String marketing_id=e.getString("marketing_id");
// 							e.put("store_discountway_id", marketing_id);
//							//获取所有启用的折扣
//							PageData zkpd=appStorepc_marketingService.getZKById(e);
//							if(zkpd != null){
// 									String zkgrantrule=zkpd.getString("grantrule");
//									if(zkpd.getString("discount_type").equals("1")){//整店折扣
//											String number=zkpd.getString("onealldiscount_rate");
//											double n=0;
//											if(number.length() == 1){
//												n=1-Double.parseDouble(number)/10.0;
//											}else if(number.length() == 2){
//												n=1-Double.parseDouble(number)/100;
//											}else if(number.length() == 3){
//												n=1-Double.parseDouble(number)/1000;
//											}
// 			 								double m=n*money;
//			 								if(m >= zkmoney ){
//			 									zkcontent=grantrule;
//			 									zkmoney=m;
//			 									zktype=marketing_type;
//			 									zkid=marketing_id;
//			 								}
//									}else if(zkpd.getString("discount_type").equals("4")){//满多少折多少
//										String[] str=zkgrantrule.split(",");
//		 	 							for(int i=0; i<str.length ;i++){ 
//					 	 							String number1=str[i].substring(str[i].indexOf("满")+1, str[i].indexOf("元"));
//			  		  								String number2=str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折"));
//			  		 								double n1=Double.parseDouble(number1);
// 			  		 								double n2=0;
//													if(number2.length() == 1){
//														n2=1-Double.parseDouble(number2)/10.0;
//													}else if(number2.length() == 2){
//														n2=1-Double.parseDouble(number2)/100;
//													}else if(number2.length() == 3){
//														n2=1-Double.parseDouble(number2)/1000;
//													}
//			  		 								if(n2*money >zkmoney && money >=n1){
//			  		 									zkcontent=grantrule;
//					 									zkmoney=n2*money;
//					 									zktype=marketing_type;
//					 									zkid=marketing_id;
//			  		 								}
//		 	 							}
//									}else if(zkpd.getString("discount_type").equals("2")){//按类别折扣
//		   			 								double m=0;
//		   			 								if(alllei != null && !alllei.equals("")){
//			   			 								if(alllei.contains("@")){
//				   			 								String[] goods=alllei.split(",");
//	 				   			 							//循环所有商品
//			   			 										for(int i=0 ; i<goods.length ; i++){
//			   			 												PageData e6=new PageData();
//			   			 											    String[] str1=goods[i].split("@");
//			   			 												String goods_id=str1[0];
//			   			 												e6.put("goods_id", goods_id);
//			   			 												e6=appGoodsService.goodsSortById(e6);
//			   			 												if(e6 != null){
//					   			 												double buygoodsmoney=Double.parseDouble(str1[2]);
//					   			 												if(e6.getString("zk_rate").length() == 1){
//						   			 												double zkrate=1-Double.parseDouble(e6.getString("zk_rate"))/10.0;
//						   			 												m+=zkrate*buygoodsmoney;
//					   			 												}else if(e6.getString("zk_rate").length() == 2){
//						   			 												double zkrate=1-Double.parseDouble(e6.getString("zk_rate"))/100;
//						   			 												m+=zkrate*buygoodsmoney;
//					   			 												}else if(e6.getString("zk_rate").length() ==3){
//						   			 												double zkrate=1-Double.parseDouble(e6.getString("zk_rate"))/1000;
//						   			 												m+=zkrate*buygoodsmoney;
//					   			 												}
// 			   			 												}
//			   			 												e6=null;
//				   			 										}
//				   			 							}
//		   			 								}
//	  											  //判断总金钱折扣是否最大
//	  											  if(m > zkmoney){
//	  												zkcontent=grantrule;
//				 									zkmoney=m ;
//				 									zktype=marketing_type;
//				 									zkid=marketing_id;
//	  											  }
//									}else if(zkpd.getString("discount_type").equals("3")){ 
//										System.out.println("无单品购买");
//									}
//							}
// 				}
//  				//满减折扣
// 				if(!zkcontent.equals("") && zkmoney>0 ){
// 					PageData zkpd=new PageData();
// 					zkpd.put("content", zkcontent);
// 					zkpd.put("id", zkid);
// 					zkpd.put("type", zktype);
// 					zkpd.put("number", "-"+df.format(zkmoney));
// 					yingxiaoList.add(zkpd);
// 				}
// 				//2.获取其他的营销规则
//				List<PageData> marketlist=appStorepc_marketingService.listAllById(pd);
//				PageData e1=new PageData();
//				PageData e2=new PageData();
//				String zengcontent="";
//				String zengid="";
//				String zengtype="";
//				String zengcontent2="";
//				String zengid2="";
//				String zengtype2="";
//				String jiancontent="";
//				String jiantype="";
//				String jianid="";
//				double reducemoney=0;
//				for(PageData e : marketlist){
//								/*
//								 * *1-满赠，*2-满减，3-时段营销，4-买N减N（针对商品），5-累计次数/购买金额--增，6-积分收益，*7-折扣设置
//								 */
//									String marketing_type=e.getString("marketing_type");
//		//							String change_type=e.getString("change_type");
//									String grantrule=e.getString("grantrule");
//									String marketing_id=e.getString("marketing_id");
//									if(marketing_type.equals("1")){ 
//											String number=grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元"));
//											double n=Double.parseDouble(number);
//		  									if(money >= n){
//			 									zengcontent=grantrule;
//			 									zengid=marketing_id;
//			 									zengtype=marketing_type;
//			 								}
//		 							}else if(marketing_type.equals("2")){ 
//										if(grantrule.contains("折")){
//												String number1=grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元"));
//				  								String number2=grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折"));
//				 								double n1=Double.parseDouble(number1);
//				 								double n2=0;
//				 								if(number2.length() == 1){
//				 									n2=1-Double.parseDouble(number2)/10;
//				 								}else if(number2.length() == 2){
//				 									n2=1-Double.parseDouble(number2)/100;
//				 								}else if(number2.length() == 3){
//				 									n2=1-Double.parseDouble(number2)/1000;
//				 								}
// 				 								if(n2*money >reducemoney && money >n1){
//				 									jiancontent=grantrule;
//				 									reducemoney=n2*money;
//				 									jiantype=marketing_type;
//				 									jianid=marketing_id;
//				 								}
//										}else{
//												String number1=grantrule.substring(grantrule.indexOf("满")+1, grantrule.indexOf("元"));
//				  								String number2=grantrule.substring(grantrule.indexOf("减")+1, grantrule.lastIndexOf("元"));
//				 								double n1=Double.parseDouble(number1);
//				 								double n2=Double.parseDouble(number2);
//				 								if(n2 >reducemoney && money >=n1){
//				 									jiancontent=grantrule;
//				 									reducemoney=n2;
//				 									jiantype=marketing_type;
//				 									jianid=marketing_id;
//				 								}
//										}
//								}else if(marketing_type.equals("7")){ 
//									System.out.println("第一步先进行折扣");
//								}else if(marketing_type.equals("3")){
//		 							  PageData sdpd=new PageData();
//		 							  sdpd.put("marketing_id", marketing_id);
//		 							  sdpd.put("store_id", pd.getString("store_id"));
//		 							  sdpd=storepcMarketingTypeService.findById(sdpd);
//		 							  if(sdpd != null){
//		 								  String threeachieve_money=sdpd.getString("threeachieve_money");
//			 							  String marketsmall_type=sdpd.getString("marketsmall_type");
//			 							  String threereduce_money=sdpd.getString("threereduce_money");
//			 							  String threediscount_rate=sdpd.getString("threediscount_rate");
//			 							  Date date=new Date();
//			 							  long l1=date.getTime();
//			 							  String nowday=DateUtil.getDay();
//			 							String starttime=sdpd.get("starttime").toString();
//			 							String endtime=sdpd.get("endtime").toString();
//			 							  String str2=nowday+" "+starttime;
//			 							  long l2=DateUtil.fomatDate1(str2).getTime();
//			  							  String str3=nowday+" "+endtime;
//			 							  long l3=DateUtil.fomatDate1(str3).getTime();
//			 							  if(  Double.parseDouble(threeachieve_money) <= money &&  l1 > l2 && l1 < l3 ){
//			 								  	 if(marketsmall_type.equals("1")){//折
//		   								  		 	 if(threediscount_rate.length() == 1){
//				   								  		 	double mm=money*(1-Double.parseDouble(threediscount_rate)/10.0);
//				   								  	         if(  mm > reducemoney){
//								   								  	        jiancontent=grantrule;
//								  		 									reducemoney=mm;
//								  		 									jiantype=marketing_type;
//								  		 									jianid=marketing_id;
//				   								  	         }
//		   								  		 	 }else  if(threediscount_rate.length() == 2){
//				   								  		 	double mm=money*(1-Double.parseDouble(threediscount_rate)/100);
//				   								  	         if(  mm > reducemoney){
//								   								  	        jiancontent=grantrule;
//								  		 									reducemoney=mm;
//								  		 									jiantype=marketing_type;
//								  		 									jianid=marketing_id;
//				   								  	         }
//		   								  		 	 }else  if(threediscount_rate.length() == 3){
//				   								  		 	double mm=money*(1-Double.parseDouble(threediscount_rate)/1000);
//				   								  	         if(  mm > reducemoney){
//								   								  	        jiancontent=grantrule;
//								  		 									reducemoney=mm;
//								  		 									jiantype=marketing_type;
//								  		 									jianid=marketing_id;
//				   								  	         }
//		   								  		 	 } 
//		   								  	}else{//钱
//					   								  	if( Double.parseDouble(threereduce_money) > reducemoney){
//							   								  	        jiancontent=grantrule;
//							  		 									reducemoney= Double.parseDouble(threereduce_money);
//							  		 									jiantype=marketing_type;
//							  		 									jianid=marketing_id;
//												  	         }
//			 								  	 }
//			 							  }
//		 							  }
//		 								/*
//			 							 * 累计次数
//			 							 */
//								} else if(marketing_type.equals("5")){
//			   						PageData ljpd=new PageData();
//			   						ljpd.put("marketing_id", marketing_id);
//			   						ljpd.put("store_id", pd.getString("store_id"));
//			   						ljpd=storepcMarketingTypeService.findById(ljpd);
//				   					if(ljpd != null){
//				   							 String fiveachieve_number=ljpd.getString("fiveachieve_number");
////											 String marketsmall_type=ljpd.getString("marketsmall_type");
//											 String fiveachieve_money=ljpd.getString("fiveachieve_money");
//											Date date=new Date();
//				   							long l1=date.getTime();
//				   							String nowday=DateUtil.getDay();
//				   							String starttime=ljpd.get("starttime").toString();
//				   							String endtime=ljpd.get("endtime").toString();
//				   							String str2=nowday+" "+starttime;
//				   							String str3=nowday+" "+endtime;
//				   						   long l2=DateUtil.fomatDate1(str2).getTime();
//			   							   long l3=DateUtil.fomatDate1(str3).getTime();
//			   							   if(  l1 > l2 && l1 < l3 ){
//				   								PageData orderpd=new PageData();
//			 			   						orderpd.put("starttime", str2);
//			 			   						orderpd.put("endtime", str3);
//			 			   						orderpd.put("store_id", pd.getString("store_id"));
//			 			   						orderpd.put("member_id", pd.getString("member_id"));
//					   							orderpd=appOrderService.listhistoryNumberByStore(orderpd);
//					   							String number=orderpd.getString("number");
//					   							String sumsale_money="0";
//					   							if(orderpd.get("sumsale_money") != null){
//					   								sumsale_money=orderpd.get("sumsale_money").toString();
//					   							};
// 					   							if(Integer.parseInt(number)%Integer.parseInt(fiveachieve_number) == 0 && Double.parseDouble(sumsale_money)%Double.parseDouble(fiveachieve_money) >= 0 && Double.parseDouble(sumsale_money)%Double.parseDouble(fiveachieve_money) < 1){
// 				   									//判断哪个红包适合
//	 				   								zengcontent2=grantrule;
//				 									zengid2=marketing_id;
//				 									zengtype2=marketing_type;
//					   							}
// 			   							  }
//								}
//			   			}
// 				}
//				//满赠
//				if(!zengcontent.equals("")){
//					e1.put("content", zengcontent);
//					e1.put("number", "");
//					e1.put("id", zengid);
//					e1.put("type", zengtype);
//					yingxiaoList.add(e1);
//				}
//				//累计次数
//				PageData ldpd=new PageData();
//				if(!zengcontent2.equals("")){
//					ldpd.put("content", zengcontent2);
//					ldpd.put("number", "");
//					ldpd.put("id", zengid2);
//					ldpd.put("type", zengtype2);
//					yingxiaoList.add(ldpd);
//				}
//				//满减
//				if(!jiancontent.equals("") && reducemoney>0){
//					e2.put("content", jiancontent);
//					e2.put("id", jianid);
//					e2.put("type", jiantype);
//					e2.put("number", "-"+df.format(reducemoney));
//					yingxiaoList.add(e2);
//				}
// 				int n=yingxiaoList.size();
// 				//使用红包钱的优惠金额
// 				double userredbeformoney=reducemoney+zkmoney;
//				//红包
//				PageData redpd=getMaxStoreRedMoney(pd,money,n,userredbeformoney);
//				String red_id="";
//				String redmoney="";
//				if(redpd != null){
//	 				red_id=redpd.getString("id");
//	 				redmoney=redpd.getString("number");
//	 				redpd.put("number", "-"+redmoney);
//	 				redpd.put("type", "0");
//	 				yingxiaoList.add(redpd);
//				}else{
//					redmoney="0";
//				}
//				map1.put("yingxiaoList", yingxiaoList);
//				//使用红包后的优惠金额
// 				double userredaftermoney=reducemoney+zkmoney+Double.parseDouble(redmoney);
//  				//获取个人财富
// 				PageData mpd=appMemberService.findWealthById(pd);
//  				map1.put("mpd", mpd);
//				PageData countpd=new PageData();
//				if(money-userredaftermoney <= 0){
//					countpd.put("paymoney", "0");//优惠后的支付金额
//					countpd.put("reducemoney", df.format(money));
//				}else{
//					countpd.put("paymoney", df.format( money-userredaftermoney));
//					countpd.put("reducemoney", df.format(userredaftermoney));
//				}
//				//增红包的集合
//				String allzengId="";
//				if(!zengid.equals("")){
//					allzengId=zengid+","+zengid2;
//				}else{
//					allzengId=zengid2;
//				}
// 				countpd.put("zengid", allzengId);
//				countpd.put("zengjf", df.format(alljifeng));
//				countpd.put("red_id", red_id);
//				countpd.put("allmoney", df.format(money));
//				map1.put("countpd", countpd);
//				//商家名称
// 				if(appStoreService.findById(pd) != null){
// 					map1.put("store_name", appStoreService.findById(pd).getString("store_name"));
// 				}else{
// 					map1.put("store_name", "");
// 				}
//  		} catch(Exception e){
//			result = "0";
//			message="系统错误";
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data",map1 );
//		return map;
//	}
//	
//	
//	
//	
//	/**
//	 * 
//	* 方法名称:：markeingAll 
//	* 方法描述：获取营销规则
//	* 创建人：魏汉文
//	* 创建时间：2016年7月21日 上午10:48:06
//	 */
//	public PageData markeingAll(PageData pd){
//				PageData yxpd=new PageData();
//				yxpd.put("mz", "" );
//				yxpd.put("mj", "" );
//				yxpd.put("sdyx", "" );
//				yxpd.put("mnjn", "" );
//				yxpd.put("gmcs", "" );
//				yxpd.put("jfsy", "" );
//				yxpd.put("zk", "" );
//				try{
//					//获取营销规则
////			 		   PageData e = new PageData();
//						List<PageData> marketlist=appStorepc_marketingService.listAllById(pd);
//						String add="";
//						String reduce="";
//						String time="";
//						String n="";
//						String number="";
//						String score="";
//						String zk="";
//  						for(PageData e2 : marketlist){
//									/*
//									 * 1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数,6-积分，7折扣
//									 */
//									String marketing_type=e2.getString("marketing_type");
////									String marketing_id=e2.getString("marketing_id");
//									String grantrule=e2.getString("grantrule");
//									if(marketing_type.equals("1")){
//										add+=grantrule+",";
//									}else if(marketing_type.equals("2")){
//										reduce+=grantrule+",";
//									}else if(marketing_type.equals("3")){
//										time+=grantrule+",";
//									}else if(marketing_type.equals("4")){
//										n+=grantrule+",";
//									}else if(marketing_type.equals("5")){
//										number+=grantrule+",";
//									}else if(marketing_type.equals("6")){ 
//										score+=grantrule+",";
//									}else if(marketing_type.equals("7")){
//			 							zk+=grantrule+",";
//			 						}
//						}
//  						if(!add.equals("")){
//  							add=add.substring(0, add.length()-1);
//  						}
//  						if(!reduce.equals("")){
//  							reduce=reduce.substring(0, reduce.length()-1);
//  						}
//  						if(!time.equals("")){
//  							time=time.substring(0, time.length()-1);
//  						}
//  						if(!n.equals("")){
//  							n=n.substring(0, n.length()-1);
//  						}
//  						if(!number.equals("")){
//  							number=number.substring(0, number.length()-1);
//  						}
//  						if(!score.equals("")){
//  							score=score.substring(0, score.length()-1);
//  						}
//  						if(!zk.equals("")){
//  							zk=zk.substring(0, zk.length()-1);
//  						}
//						yxpd.put("mz", add );
//						yxpd.put("mj", reduce );
//						yxpd.put("sdyx", time );
//						yxpd.put("mnjn", n );
//						yxpd.put("gmcs", number );
//						yxpd.put("jfsy", score );
//						yxpd.put("zk", zk );
//				}catch(Exception e){
//					logger.error(e.toString());
//				}
// 				return yxpd;
//	}
//	
//	
//	/**
//	 * 
//	* 方法名称:：getMaxStoreRedMoney 
//	* 方法描述：获取当前商家最优惠的红包
//	* 创建人：魏汉文
//	* 创建时间：2016年7月5日 下午1:00:58
//	 */
//	public  PageData  getMaxStoreRedMoney(PageData pd,double money,int x,double reducemoney){
//		logBefore(logger, "获取当前商家最优惠的红包");
//  		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//  		PageData _pd=new PageData();
// 		try{ 
//  			String id="";
// 			double maxreduce=0;
// 			String content="";
// 			//获取当前用户在当前商家可以用的所有红包
//			List<PageData>	memredList =appMember_redpacketsService.listAllById(pd);
//			for(PageData e : memredList){
//				String redpackage_type=e.getString("redpackage_type");
//				boolean flag=true;
//				if(redpackage_type.equals("22") && x==0){//不可一起使用的红包
//					flag=false;
//					String redpackage_content=e.getString("redpackage_content");
//					double buymoney=Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("满")+1, redpackage_content.indexOf("元")));
//					int start=redpackage_content.indexOf("减");
//					int end=redpackage_content.lastIndexOf("元") ;
//					double n=Double.parseDouble(redpackage_content.substring(start+1, end));
//					if(n>maxreduce  && money >=buymoney){
//						maxreduce=n;
//						id=e.getString("redpackage_id");
//						content=redpackage_content;
//					}
//				}  
//				if(flag){
//						String redpackage_content=e.getString("redpackage_content");
//						if(redpackage_content.contains("满") && redpackage_content.contains("次")){//满多少件的红包
//							  System.out.println("滤过");
//						}else if(redpackage_content.contains("满") && redpackage_content.contains("元")){//满多少元的红包
//								double buymoney=Double.parseDouble(redpackage_content.substring(redpackage_content.indexOf("满")+1, redpackage_content.indexOf("元")));
//								if(redpackage_content.contains("折")){
//									    String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.indexOf("折"));
//									    double zzk=0;
//									    if(number.length() == 1){
//									    	 zzk=1-Double.parseDouble(number)/10;
//									    }else if(number.length() == 2){
//									    	 zzk=1-Double.parseDouble(number)/100;
//									    }else if(number.length() == 3){
//									    	 zzk=1-Double.parseDouble(number)/1000;
//									    }
// 										double n=zzk*money;
//										if(redpackage_type.contains("1")){
//											n=zzk*(money-reducemoney);
//										}
// 										if(n>maxreduce && money >=buymoney){
//											maxreduce=n;
//											id=e.getString("redpackage_id");
//											content=redpackage_content;
//										}
//								}else {
//										int start=redpackage_content.indexOf("减");
//										int end=redpackage_content.lastIndexOf("元") ;
//										double n=Double.parseDouble(redpackage_content.substring(start+1, end));
//										if(n>maxreduce  && money >=buymoney){
//											maxreduce=n;
//											id=e.getString("redpackage_id");
//											content=redpackage_content;
//										}
//				 			}
//						}else if(redpackage_content.contains("首单")){//首单立减的红包
//								if(appOrderService.listhistory(pd).size()==0){
//											if(redpackage_content.contains("折")){
//												String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.indexOf("折"));
//											    double zzk=0;
//											    if(number.length() == 1){
//											    	 zzk=1-Double.parseDouble(number)/10;
//											    }else if(number.length() == 2){
//											    	 zzk=1-Double.parseDouble(number)/100;
//											    }else if(number.length() == 3){
//											    	 zzk=1-Double.parseDouble(number)/1000;
//											    }
// 												double n= zzk*money;
//												if(redpackage_type.contains("1")){
//													n=zzk*(money-reducemoney);
//												}
// 												if(n>maxreduce){
//													maxreduce=n;
//													id=e.getString("redpackage_id");
//													content=redpackage_content;
//												}
//											}else{
//													int start=redpackage_content.indexOf("减");
//													int end=redpackage_content.lastIndexOf("元") ;
//													double n=Double.parseDouble(redpackage_content.substring(start+1, end));
//													if(n>maxreduce ){
//														maxreduce=n;
//														id=e.getString("redpackage_id");
//														content=redpackage_content;
//													}
//							 			}
// 								}
//						}else if(redpackage_content.contains("无条件")){//无条件的红包
//									if(redpackage_content.contains("折")){
//											String number=redpackage_content.substring(redpackage_content.indexOf("打")+1, redpackage_content.indexOf("折"));
//										    double zzk=0;
//										    if(number.length() == 1){
//										    	 zzk=1-Double.parseDouble(number)/10;
//										    }else if(number.length() == 2){
//										    	 zzk=1-Double.parseDouble(number)/100;
//										    }else if(number.length() == 3){
//										    	 zzk=1-Double.parseDouble(number)/1000;
//										    }
// 											double n=zzk*money;
//											if(redpackage_type.contains("1")){
//												n=zzk*(money-reducemoney);
//											}
//												if(n>maxreduce){
//												maxreduce=n;
//												id=e.getString("redpackage_id");
//												content=redpackage_content;
//											}
//									}else{
//											int start=redpackage_content.indexOf("减");
//											int end=redpackage_content.lastIndexOf("元") ;
//											double n=Double.parseDouble(redpackage_content.substring(start+1, end));
//											if(n>maxreduce ){
//												maxreduce=n;
//												id=e.getString("redpackage_id");
//												content=redpackage_content;
//											}
//					 			}
//						}
//				}
//  			}
//			if(id==""){
//				_pd=null;
//			}else{
//				_pd.put("id", id);
//				_pd.put("content", content);
//				_pd.put("number", df.format(maxreduce));
//			}
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
// 		return _pd;
//	}
//	
//	
//
//	@Resource(name = "storepc_marketingeffectService")
//	private   Storepc_marketingeffectService storepcMarketingeffectService;	
//	@Resource(name = "storepc_scorewayService")
//	private Storepc_scorewayService storepcScorewayService; 
//	@Resource(name = "storepc_discountwayService") 
//	private Storepc_discountwayService storepcDiscountwayService;
//	@Resource(name = "storepc_redpacketsService")
//	private Storepc_redpacketsService storepcRedpacketsService;
//	
//	/**
//	 * 
//	* 方法名称:：YingXiao 
//	* 方法描述：营销效果表
//	* 创建人：魏汉文
//	* 创建时间：2016年7月20日 上午10:53:00
//	 */
//	public  void YingXiao(PageData pd){
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		try{
//			/*
//		   	 * 营销效果表数据优惠内容 (content@id@number@type ,content@id@number@type.....)
//		   	 * 0-红包， 1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数，6-积分，7-折扣
//		   	 */
//		   			String discount_content=pd.getString("discount_content");
//		   			if(discount_content.contains(",")){
//		   					String[] str=discount_content.split(",");
//		   					for(int i=0;i<str.length ; i++){
//		   					    	PageData  effectpd=new PageData();
//		   						  	String[] str1=str[i].split("@");
//		   						  	String content=str1[0];
//		   						  	String marketing_id=str1[1];
//		   						  	String number=str1[2];
//		   						  	String marketing_type=str1[3];
//		   						    effectpd.put("store_id", pd.getString("store_id"));
//		   						    effectpd.put("marketing_id", marketing_id);
//		   						    if(marketing_type.equals("1")){
//			   						    	effectpd=storepcMarketingeffectService.findById(effectpd);
//			   						    	String number1=content.substring(content.indexOf("满")+1, content.indexOf("元"));
//			   						    	if(effectpd== null){
//			   						    			effectpd=new PageData();
//			   						    		    effectpd.put("marketing_id", marketing_id);
//			   						    		    effectpd.put("store_id", pd.getString("store_id"));
//			   						    		    effectpd=storepcMarketingTypeService.findById(effectpd);
//			   						    		    effectpd.put("startdate", effectpd.get("startdate").toString());
//			   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
//			   						    		    effectpd.put("marketing_type", marketing_type);
//			   						    		    effectpd.put("marketing_id", marketing_id);
//			   						    		    effectpd.put("content", content);
// 			   						    		    effectpd.put("grant_number", number1);
//			   						    		    effectpd.put("receive_number", "1");
// 			   						    		    storepcMarketingeffectService.save(effectpd);
// 			   						    	}else{
//			   						    		    double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
//			   						    		    double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
//			   						    		    effectpd.put("grant_number", df.format( grant_number+Double.parseDouble(number1) ));
//			   						    		    effectpd.put("receive_number",receive_number+1);
//			   						    		    storepcMarketingeffectService.update(effectpd);
//			   						    	}
//		   						    }else if(marketing_type.equals("2")){
//	 			   						    	effectpd=storepcMarketingeffectService.findById(effectpd);
//	 			   						    	String number1=content.substring(content.indexOf("满")+1, content.indexOf("元"));
// 			   						    	if(effectpd== null){
//			 			   						    	effectpd=new PageData();
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
//	 			   						    		    effectpd=storepcMarketingTypeService.findById(effectpd);
//	 			   						    		    effectpd.put("startdate", effectpd.get("startdate").toString());
//	 			   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
//	 			   						    		    effectpd.put("marketing_type", marketing_type);
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("content", content);
//			 			   						    	    effectpd.put("grant_number", number1);
//	 			   						    		    effectpd.put("receive_number",number.substring(1)+"" );
//		 			   						    		    storepcMarketingeffectService.save(effectpd);
// 			   						    	}else{
// 			   						    		double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
//			   						    		    double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
//			   						    		    effectpd.put("grant_number", df.format( grant_number+Double.parseDouble(number1) ));
//			   						    		    effectpd.put("receive_number",df.format(receive_number+Double.parseDouble(number.substring(1))));
//			   						    		    storepcMarketingeffectService.update(effectpd);
// 			   						    	}
//		   						    }else if(marketing_type.equals("3")){
//	 			   						    effectpd=storepcMarketingeffectService.findById(effectpd);
//		 			   						String number1="0";
//		   						    		    if(content.contains("元")){
//		   						    		    	number1=content.substring(content.indexOf("满")+1, content.indexOf("元"));
//		   						    		    }
// 			   						    	if(effectpd== null){
//			 			   						    	effectpd=new PageData();
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
//	 			   						    		    effectpd=storepcMarketingTypeService.findById(effectpd);
//	 			   						    		    effectpd.put("startdate", effectpd.get("startdate").toString());
//	 			   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
//	 			   						    		    effectpd.put("marketing_type", marketing_type);
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("content", content);
//		 			   						    	    effectpd.put("grant_number", number1);
//	 			   						    		    effectpd.put("receive_number",number.substring(1)+"" );
//		 			   						    		    storepcMarketingeffectService.save(effectpd);
// 			   						    	}else{
//	 			   						    	double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
//			   						    		    double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
//			   						    		    effectpd.put("grant_number", df.format( grant_number+Double.parseDouble(number1) ));
// 			   						    		    effectpd.put("receive_number",df.format(receive_number+Double.parseDouble(number.substring(1))));
//			   						    		    storepcMarketingeffectService.update(effectpd);
// 			   						    	}
//		   						    }else if(marketing_type.equals("4")){
//	 			   						    effectpd=storepcMarketingeffectService.findById(effectpd);
//	 			   						    String number1=content.substring(content.indexOf("满")+1, content.indexOf("件"));
// 			   						    	if(effectpd== null){
//			 			   						    	effectpd=new PageData();
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
//	 			   						    		    effectpd=storepcMarketingTypeService.findById(effectpd);
//	 			   						    		    effectpd.put("startdate", effectpd.get("startdate").toString());
//	 			   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
//	 			   						    		    effectpd.put("marketing_type", marketing_type);
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("content", content);
//			 			   						    	    effectpd.put("grant_number", number1);
//	 			   						    		    effectpd.put("receive_number",number.substring(1)+"" );
//		 			   						    		    storepcMarketingeffectService.save(effectpd);
// 			   						    	}else{
// 			   						    		double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
//			   						    		    double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
//			   						    		    effectpd.put("grant_number", df.format( grant_number+Double.parseDouble(number1) ));
//			   						    		    effectpd.put("receive_number",df.format(receive_number+Double.parseDouble(number.substring(1))));
//			   						    		    storepcMarketingeffectService.update(effectpd);
// 			   						    	}
//		   						    }else if(marketing_type.equals("5")){
//	 			   						    effectpd=storepcMarketingeffectService.findById(effectpd);
//	 			   						    String number1="0";
//	 			   						    if(content.contains("件")){
//			   						    		     number1=content.substring(content.indexOf("满")+1, content.indexOf("件"));
//		   						    		    }
//		   						    		    if(content.contains("元")){
//		   						    		    	 number1=content.substring(content.indexOf("满")+1, content.indexOf("元"));
//		   						    		    }
// 			   						    	if(effectpd== null){
//			 			   						    	effectpd=new PageData();
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
//	 			   						    		    effectpd=storepcMarketingTypeService.findById(effectpd);
//	 			   						    		    effectpd.put("startdate", effectpd.get("startdate").toString());
//	 			   						    		    effectpd.put("enddate", effectpd.get("enddate").toString());
//	 			   						    		    effectpd.put("marketing_type", marketing_type);
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("content", content);
//	 			   						    		    effectpd.put("grant_number", number1);
//		 			   						    		    effectpd.put("receive_number","1" );
//		 			   						    		    storepcMarketingeffectService.save(effectpd);
// 			   						    	}else{
//	 			   						    	double grant_number=Double.parseDouble(effectpd.getString("grant_number"));
//			   						    		    double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
//			   						    		    effectpd.put("grant_number", df.format( grant_number+Double.parseDouble(number1) ));
// 			   						    		   	effectpd.put("receive_number",df.format(receive_number+1));
//			   						    		    storepcMarketingeffectService.update(effectpd);
// 			   						    	}
//		   						    }else if(marketing_type.equals("6")){
//	 			   						    effectpd=storepcMarketingeffectService.findById(effectpd);
// 			   						    	if(effectpd== null){
//			 			   						    	    effectpd=new PageData();
//	 			   						    		        effectpd.put("marketing_id", marketing_id);
//	 			   						    		        effectpd.put("store_id", pd.getString("store_id"));
//		 			   						    		    effectpd.put("startdate", "2016-01-01");
//		 			   						    		    effectpd.put("enddate","2020-01-01");
//		 			   						    		    effectpd.put("marketing_type", marketing_type);
//		 			   						    		    effectpd.put("marketing_id", marketing_id);
//		 			   						    		    effectpd.put("content", content);
//			 			   						    	    effectpd.put("grant_number", "");
//			 			   						    	    effectpd.put("receive_number", Double.parseDouble(number.substring(1)));
//		 			   						    		    storepcMarketingeffectService.save(effectpd);
// 			   						    	}else{
//			   						    		        double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
// 			   						    		    effectpd.put("receive_number",df.format(receive_number+Double.parseDouble(number.substring(1))));
//			   						    		        storepcMarketingeffectService.update(effectpd);
// 			   						    	}
//		   						    }else if(marketing_type.equals("7")){
//	 			   						    effectpd=storepcMarketingeffectService.findById(effectpd);
// 			   						    	if(effectpd== null){
//			 			   						    	effectpd=new PageData();
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
//	 			   						    		    effectpd=storepcDiscountwayService.findById(effectpd);
//	 			   						    		    effectpd.put("startdate", effectpd.get("starttime").toString());
//	 			   						    		    effectpd.put("enddate", effectpd.get("endtime").toString());
//	 			   						    		    effectpd.put("marketing_type", marketing_type);
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("receive_number", Double.parseDouble(number.substring(1)));
//	 			   						    		    effectpd.put("content", content);
//		 			   						    	    effectpd.put("grant_number", "");
//			 			   						    		storepcMarketingeffectService.save(effectpd);
// 			   						    	}else{
//			   						    		    	double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
// 			   						    		    effectpd.put("receive_number",df.format(receive_number+Double.parseDouble(number.substring(1))));
// 			   						    		    storepcMarketingeffectService.update(effectpd);
// 			   						    	}
//		   						    }else if(marketing_type.equals("0")){//红包
//		   						    			//更新商家红包的使用数量
//		   						    			storepcRedpacketsService.update(effectpd);
//		   						    			//操作Marketingeffect表
//		 			   						    effectpd=storepcMarketingeffectService.findById(effectpd);
// 			   						    	if(effectpd== null){
//			 			   						    	effectpd=new PageData();
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("store_id", pd.getString("store_id"));
//	 			   						    		    effectpd=storepcRedpacketsService.findById(effectpd);
//	 			   						    		    effectpd.put("startdate", effectpd.get("starttime").toString());
//	 			   						    		    effectpd.put("enddate", effectpd.get("endtime").toString());
//	 			   						    		    effectpd.put("marketing_type", marketing_type);
//	 			   						    		    effectpd.put("marketing_id", marketing_id);
//	 			   						    		    effectpd.put("content", content);
//		 			   						    	    effectpd.put("grant_number",effectpd.getString("redpackage_number") );
//	 			   						    		    effectpd.put("receive_number","1" );
//	 			   						    		    storepcMarketingeffectService.save(effectpd);
// 			   						    	}else{
//			   						    		    		double receive_number=Double.parseDouble(effectpd.getString("receive_number"));
//			   						    		    		effectpd.put("receive_number",df.format(receive_number+1));
//			   						    		    		storepcMarketingeffectService.update(effectpd);
// 			   						    	}
//		   						    }
//		   						 effectpd=null;
//		   					}
//		   			}
//		}catch(Exception e){
//			logger.error("营销效果表YingXiao出错");
//			logger.error(e.toString());
//			}
//		}
//	
//		
//	
//	
//	
//
//}
