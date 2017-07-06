package com.tianer.controller.storeapp.payapp_history;

 
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.service_performance.Service_performanceService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.business.store.StoreService;
import com.tianer.service.business.subsidiary.SubsidiaryService;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.memberapp.AppStorepc_marketingService;
import com.tianer.service.storeapp.Payapp_historyService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
import com.tianer.service.storepc.tableNumber.TablerNumberService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;


/** 
 * 
* 类名称：Payapp_historyController   
* 类描述：   收银记录app
* 创建人：邢江涛
* 创建时间：2016年7月4日 
 */
@Controller("Payapp_historyController")
@RequestMapping(value="/storeapp_payhHstory")
public class Payapp_historyController extends BaseController{
	
	@Resource(name="payapp_historyService")
	private Payapp_historyService payapp_historyService;
	@Resource(name="service_performanceService")
	private Service_performanceService service_performanceService;
	
	//-----------------------------------------
	
 	public DecimalFormat df2=TongYong.df2;
	
	/**
	 * 获取session_orderid
	 * storeapp_payhHstory/getSessionOrder.do
	 */
	@RequestMapping(value="/getSessionOrder")
	@ResponseBody
	public Object getSessionOrder(){
//		logBefore(logger, " 获取sessionorder的唯一标示ID");
		Map<String,Object> map = new HashMap<String,Object>();
		//shiro管理的session
	    Subject currentUser = SecurityUtils.getSubject();  
	    Session session = currentUser.getSession();	
		String result = "1";
		String message="获取成功";
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(session.getAttribute(Const.SESSION_ORDER) == null ){
				String session_orderid=BaseController.getTimeID();
				session.setAttribute(Const.SESSION_ORDER, session_orderid);
//				System.out.println("当前的session="+session_orderid);
				String sessionid =String.valueOf(session.getId());
				map.put("sessionid ", sessionid );
				map.put("data", session_orderid);
			}else{
				map.put("data", String.valueOf(session.getAttribute(Const.SESSION_ORDER)));
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	

	/**
	 * 获取用户的信息通过手机号码
	 * 魏汉文20160620
	 */
	@RequestMapping(value="/getInforByPhone")
	@ResponseBody
	public Object getInforByPhone(){
//		logBefore(logger, " 获取用户的信息通过手机号码");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			pd=appMemberService.getIntegerByPhone(pd);
			if(pd == null){
				pd=new PageData();
				pd.put("member_id", "0");
				pd.put("now_integral", "0");
				pd.put("now_money", "0");
				message="当前手机号未注册，请先注册";
				result="0";
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 获取大类的分类
	 * 魏汉文20160620
	 */
	@RequestMapping(value="/getLeibiePay")
	@ResponseBody
	public Object getLeibiePay(){
//		logBefore(logger, " 获取大类的分类");
		Map<String,Object> map = new HashMap<String,Object>();
//		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
//			//判断是否开通类别积分购买的权限
//			PageData issortjfpd=appStorepc_marketingService.getJfById(pd);
//			if(issortjfpd == null || !issortjfpd.getString("change_type").equals("2") ){
//				 map1.put("issortjf", "0");
//			}else{
//				 map1.put("issortjf", "1");
//			}
//			//获取商家的营销规则明细
//			PageData yxpd=markeingAll(pd);
//			map1.put("yxpd", yxpd);
//			//获取大类
			List<PageData> leibieList=appGoodsService.listAllBigSort(pd);
			for(PageData  e : leibieList){
				e.put("price", "0");
			}
//			map1.put("leibieList", leibieList);
//			map.put("data", map1);
			map.put("data", leibieList);
		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
 		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 获取大类的分类
	 * 魏汉文20160620
	 */
	@RequestMapping(value="/getLeibiePayTwo")
	@ResponseBody
	public Object getLeibiePayTwo(){
//		logBefore(logger, " 获取大类的分类");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		List<PageData> noList = new ArrayList<PageData>();//操作员所对应桌号
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
//			//判断是否开通类别积分购买的权限
			PageData ispd=appStorepc_marketingService.getJfById(pd);
			if(ispd != null && ispd.getString("change_type").equals("3") ){
				 map1.put("issortjf", "3");
			}else if(ispd != null && ispd.getString("change_type").equals("2") ){
				 map1.put("issortjf", "1");
			} else{
				 map1.put("issortjf", "0");
			}
//			//获取商家的营销规则明细
			PageData yxpd=TongYong.markeingAll(pd);
			map1.put("yxpd", yxpd);
			yxpd=null;
//			//获取大类
			List<PageData> leibieList=appGoodsService.listAllBigSort(pd);
			for(PageData  e : leibieList){
				e.put("price", "0");
			}
			map1.put("leibieList", leibieList); 
			leibieList=null;
			//操作员登录
			String store_operator_id = pd.getString("store_operator_id");
			if(store_operator_id != null && !store_operator_id.trim().equals("")){
				PageData pg = new PageData();
				pg = storeManageService.findOperatorById(pd);
				if(pg != null && !pg.equals("")){
					String alldesk_no = pg.getString("alldesk_no");
					if(alldesk_no != null && !alldesk_no.equals("")){
						String[] no = alldesk_no.split(",");
						for (int i = 0; i < no.length; i++) {
							PageData e = new PageData();
							e.put("table_name", no[i]);
							noList.add(e);
						}
					}
				}else{
					result="1";
					message="操作员不存在";
				}
				pg=null;
			}else{
				//商家登录
				String store_id = pd.getString("store_id");
				if(store_id != null && !store_id.equals("")){
					//获取改商家桌号
					noList = tablerNumberService.listAll(pd);
				}
			}
 			map1.put("noList", noList);
 			noList=null;
 		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
 		return map;
	}
	

	/**
	 * 订单的历史收银记录
	 * 魏汉文20160706
	 */
	@RequestMapping(value="/confirmedHistory")
	@ResponseBody
	public Object confirmedHistory(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
				pd = this.getPageData();
				pd.put("order_status", "1");
				pd.put("pay_type", "1");//1-收银，2-优惠买单，3-提货卷 ,4-购买一元夺宝 
  				String nowpage=pd.getString("nowpage");
 				if(nowpage==null || nowpage.equals("")){
 					nowpage="1";
 				}
 				page.setCurrentPage(Integer.parseInt(nowpage));
 	 			page.setPd(pd);
				List<PageData> varList = payapp_historyService.confirmedHistorylistPage(page);
				for(int n=0;n<varList.size() ;n++){
					PageData e=varList.get(n);
  					//优惠项
	 	 			List<PageData> discountListone =new ArrayList<PageData>();
	 	 			String discount_content=e.getString("discount_content");
	 	 			if(discount_content != null && discount_content.contains(",")){
	 	 					String[] str=discount_content.split(",");
		   					for(int i=0;i<str.length ; i++){
		   					    	 PageData  dispd=new PageData();
		   						  	 String[] str1=str[i].split("@");
//		   						  	 System.out.println(str1);
			   						 dispd.put("content", str1[0]);
			   						 dispd.put("number", str1[2]);
			   						 discountListone.add(dispd);
			   						 dispd=null;
		 	 			 }
	 	 			}
	 	 			e.put("discountList", discountListone);
	 	 			discountListone=null;
  				}
				map1.put("oneList", varList);
     	} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
  		return map;
	}
	
	/**
	 * 订单的历史收银详情 魏汉文20160706
	 */
	@RequestMapping(value="/confirmedFindById")
	@ResponseBody
	public Object confirmedFindById(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String order_id=pd.getString("order_id");
			if(order_id==null ||  order_id.equals("") ){
 					map.put("data", "");
  			}else{
				pd = payapp_historyService.confirmedFindById(pd);
				if(pd != null){
					if(pd.getString("name") != null && pd.getString("name").length() == 11){
	 			    	pd.put("name", pd.getString("name").substring(0, 3)+"****"+pd.getString("name").substring(7, 11));
	 			    }
					pd.put("yc_phone", pd.getString("phone").substring(0, 3)+"****"+pd.getString("phone").substring(7, 11));
					List<PageData> varList =new ArrayList<PageData>();
	 	 			if(pd.getString("pay_sort_type").equals("2")){//按总金额购买
		 					//获取类别购买
		 					 varList=null;
		 					 varList = payapp_historyService.orderSortList(pd);
		 			}
	 	 			pd.put("sortList", varList);
	 	 			//优惠项
	 	 			List<PageData> discountList =new ArrayList<PageData>();
	 	 			String discount_content=pd.getString("discount_content");
	 	 			if(discount_content.contains(",")){
	 	 					String[] str=discount_content.split(",");
		   					for(int i=0;i<str.length ; i++){
		   						if(str[i].contains("@")){
		   							PageData  dispd=new PageData();
		   						  	 String[] str1=str[i].split("@");
			   						 dispd.put("content", str1[0]);
			   						 dispd.put("number", str1[2]);
			   						 discountList.add(dispd);
			   						 dispd=null;
		   						}
 		 	 			 }
	 	 			}	
	 	 			pd.put("discountList", discountList);
	 	 			discountList=null;
		 			map.put("data", pd);
				}
 			}
    	} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
			map.put("data", "");
		}
		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	
	
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService appMember_wealthhistoryService;
	
 	@Resource(name="clerk_fileService")
	private Clerk_fileService clerk_fileService;
 	@Resource(name="subsidiaryService")
	private SubsidiaryService subsidiaryService;
	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
 	
 	
	/**
	 * 订单确认收银  魏汉文20160706
	 */
	@RequestMapping(value="/sureConfirmed")
	@ResponseBody
	public synchronized  Object sureConfirmed(){
		Map<String,Object> map = new HashMap<String,Object>();
   		String result = "1";
		String message="收银成功";
		PageData pd = new PageData();
		try{
				//生成新的session
 				Session session = SecurityUtils.getSubject().getSession();	
				String session_orderid=BaseController.getTimeID();
				session.setAttribute(Const.SESSION_ORDER, session_orderid);
				map.put("session_orderid", session_orderid);
				//========
 				pd = this.getPageData();
				String channel="nowpay";
				String apptype=pd.getString("apptype");
 				if(apptype == null || apptype.equals("")){
					apptype="c";//c-客户端
				} 
 				pd.put("apptype", apptype);
  				if(pd.getString("order_id") == null || pd.getString("order_id").equals("")){
			 				System.out.println(apptype+"端进来收银---直接收银============================");
			 				String order_id=BaseController.getTimeID();
 							pd.put("order_id", order_id);
 							if(pd.getString("member_id") != null && !pd.getString("member_id").equals("")){
 								pd.put("payer_id", pd.getString("member_id"));
 							}
 							if(apptype.equals("pc")){
	 							pd.put("in_jiqi", "4");
	 						}else if(apptype.equals("c")){
	 							pd.put("in_jiqi", "2");
	 						}
 							pd.put("look_number", order_id);
 							pd.put("money_pay_type", "2");
 	 						pd.put("channel", channel);
	 						pd.put("pay_way", channel);
			 				PageData mpd=appMemberService.findById(pd); 
			 				PageData spd=appStoreService.findById(pd);
			 				// 处理订单(包括生成订单)
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
							System.out.println(order_id+"收银完成====");
        			    }
        } catch(Exception e){
			logger.error("确认收银异常==============="+e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
  		return map;
	}
	
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	@Resource(name="storeService")
	private StoreService storeService;
	
	/**
	 * 判断提货卷是否存在  魏汉文20160706
	 */
	@RequestMapping(value="/isTiHuo")
	@ResponseBody
	public Object isTiHuo(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			 	pd = this.getPageData();
			 	pd.put("tihuo_id", pd.getString("tihuo_id").trim());
			 	pd=payapp_historyService.tihuoByOrderId(pd);
 			 	if(pd== null ){
	 			 		map.put("result", "0");
						map.put("message", "提货劵不存在");
						map.put("data", "");
				   		return map;
			 	}else{
			 		//判断商家的赠送积分是否充足
					System.out.println("==============================判断商家的赠送积分是否充足");
					double storeintegral=Double.parseDouble(pd.getString("user_integral"))-Double.parseDouble(pd.getString("get_integral"))-Double.parseDouble(pd.getString("get_integral"))*Const.orderShouyiMoney[0];
					PageData spd=appStoreService.findById(pd);
					if(spd != null){
						spd.put("wealth_type", "1");//1-积分，2-金钱
						PageData 	ismoneypd=appStoreService.findWealthById(spd);
						double isnow_wealth=Double.parseDouble(ismoneypd.getString("now_wealth"));
						if(isnow_wealth+storeintegral  < 0){
								map.put("result", "0");
								map.put("message", "商家积分余额不足，请商家充值后再购买");
								map.put("data", "");
						    	return map;
						}
						ismoneypd=null;
					}
					if(pd.getString("tihuo_status") != null && pd.getString("tihuo_status").equals("0")){
			 			 //获取商品信息
		 			    if(pd.getString("name") != null && pd.getString("name").length() == 11){
		 			    	pd.put("name", pd.getString("name").substring(0, 3)+"****"+pd.getString("name").substring(7, 11));
		 			    }
		 			  	pd.put("phone", pd.getString("phone").substring(0, 3)+"****"+pd.getString("phone").substring(7, 11));
		 			  	//获取订单商品以及商家个别信息
		 			  	pd=TongYong.getGoodsListByOrder(pd);
  						map.put("data", pd);
			 		}else{//已提货
				 			result="0";
					 		message="该提货卷已提货";
					 		map.put("data", pd);
			 		}
 			 	}
     	} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
   		return map;
	}
	
	/**
	 * 订单确认提货  魏汉文20160706
	 * storeapp_payhHstory/sureTiHuo.do
	 */
	@RequestMapping(value="/sureTiHuo")
	@ResponseBody
	public Object sureTiHuo(){
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="提货成功";
		PageData pd = new PageData();
		try{
				pd = this.getPageData();
				String login_type=pd.getString("login_type");
				String login_id=pd.getString("login_id");
 				pd=appOrderService.findById(pd);//订单详情
				if(pd == null){
					map.put("result", "0");
					map.put("message", "订单不存在");
					map.put("data", "");
			  		return map;
				}
				if(login_type != null && login_type.equals("2")){//操作员登录
					pd.put("store_operator_id", login_id);
				}
				boolean flag=TongYong.orderIsOkByStore(Double.parseDouble(pd.getString("get_integral")), Double.parseDouble(pd.getString("discount_after_money")), pd.getString("pay_type"), Double.parseDouble(pd.getString("user_integral")) , Double.parseDouble(pd.getString("user_balance")) , Double.parseDouble(pd.getString("actual_money")), ServiceHelper.getAppStoreService().findById(pd));
				if(flag){
					map.put("result", "0");
					map.put("message", "商家积分余额不足，请商家充值后再提货");
					map.put("data", "");
			    	return map;
				}
                 pd.put("member_id", pd.getString("payer_id"));
                String channel=pd.getString("channel");
				String pay_sort_type=pd.getString("pay_sort_type");
				if(pay_sort_type.equals("2")){
					//优选商品
					if(!TongYong.historyByOrder(pd, channel, "5","b",false)){
						message="提货失败,请联平台";
						result="0";
					}
				}else{
 	                //正常商品提货券
					if(!TongYong.historyByOrder(pd, channel, "3","b",false)){
						message="提货失败,请联平台";
						result="0";
					}
 				}
       	} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
  		return map;
	}
	
	/**
	 * 订单退货  魏汉文20160706
	 */
	@RequestMapping(value="/returnOrder")
	@ResponseBody
	public Object returnOrder(){
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="退货成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			pd=TongYong.TiHuoReturnOrder(pd,"1");
	 		result=pd.getString("result");
	 		message=pd.getString("message");
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
  		return map;
	}
 
	@Resource(name="appGoodsService")
	private AppGoodsService appGoodsService;
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService store_redpacketsService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService appMember_redpacketsService;
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService appStore_redpacketsService;
	@Resource(name="appStorepc_marketingService")
	private AppStorepc_marketingService appStorepc_marketingService;
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	@Resource(name = "storepc_marketingtypeService")
	private Storepc_marketingtypeService storepcMarketingTypeService;
	@Resource(name="appOrderService")
	private AppOrderService appOrderService;
	@Resource(name = "tablerNumberService")
	private TablerNumberService tablerNumberService;
	
	/**
	 * 优惠买单，按总金额购买，商家ID，支付金钱,不优惠金额
	 * 魏汉文20160628
	 */
	@RequestMapping(value="/allMoneyByOne")
	@ResponseBody
	public synchronized Object allMoneyByOne(){
 		Map<String,Object> map = new HashMap<String,Object>();
     	List<PageData> noList = new ArrayList<PageData>();//操作员所对应桌号
		String result = "1";
		String message="按总金额购买";
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
 				String paymoney=pd.getString("paymoney");
 				if(paymoney == null || paymoney.equals("")){
					paymoney="0";
				}
				String notmoney=pd.getString("notmoney");
				if(notmoney == null || notmoney.equals("")){
					notmoney="0";
				}
				double youhui_money=Double.parseDouble(paymoney)-Double.parseDouble(notmoney);
				if(youhui_money < 0 || youhui_money < Double.parseDouble(notmoney) ){
					map.put("result", "0");
					map.put("message", "不优惠金额不能大总金额的50%");
					map.put("data", "");
					return map;
				}
	  			//操作员登录
				String store_operator_id = pd.getString("store_operator_id");
				if(store_operator_id != null && !store_operator_id.trim().equals("")){
					PageData pg = new PageData();
					pg = storeManageService.findOperatorById(pd);
					if(pg != null && !pg.equals("")){
						String alldesk_no = pg.getString("alldesk_no");
						if(alldesk_no != null && !alldesk_no.equals("")){
							String[] no = alldesk_no.split(",");
							for (int i = 0; i < no.length; i++) {
								PageData e = new PageData();
								e.put("table_name", no[i]);
								noList.add(e);
							}
						}
					}
				}else{
					//商家登录
					String store_id = pd.getString("store_id");
					if(store_id != null && !store_id.equals("")){
						//获取改商家桌号
						noList = tablerNumberService.listAll(pd);
					}
				}
				//优惠买单信息
				pd.put("pay_sort_type", "1");
				Map<String,Object> yhmdpd=TongYong.YouHuiMaiDanByTwoForStoreMaiDan(pd, youhui_money, Double.parseDouble(notmoney));
				yhmdpd.put("noList", noList);
  				map.put("data",yhmdpd);
 				noList=null;
				yhmdpd=null;
       	} catch(Exception e){
      		map.put("data","" );
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	/**
	 * 优惠买单，按总类别购买，商家ID，支付金钱
	 * 魏汉文20160629
	 */
	@RequestMapping(value="/allMoneyByTwo")
	@ResponseBody
	public synchronized Object allMoneyByTwo(){
 		Map<String,Object> map = new HashMap<String,Object>();
      	List<PageData> noList = new ArrayList<PageData>();//操作员所对应桌号
		String result = "1";
		String message="按总类购买";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
 			//操作员登录
			String store_operator_id = pd.getString("store_operator_id");
			if(store_operator_id != null && !store_operator_id.trim().equals("")){
				PageData pg = new PageData();
				pg = storeManageService.findOperatorById(pd);
				if(pg != null && !pg.equals("")){
					String alldesk_no = pg.getString("alldesk_no");
					if(alldesk_no != null && !alldesk_no.equals("")){
						String[] no = alldesk_no.split(",");
						for (int i = 0; i < no.length; i++) {
							PageData e = new PageData();
							e.put("alldesk_no", no[i]);
							noList.add(e);
						}
					}
				}
			}else{
				//商家登录
				String store_id = pd.getString("store_id");
				if(store_id != null && !store_id.equals("")){
					//获取改商家桌号
					noList = tablerNumberService.listAll(pd);
				}
			}
			pd.put("pay_sort_type", "2");
 			//优惠买单信息
			Map<String,Object> yhmdpd=TongYong.YouHuiMaiDanByTwoForStoreMaiDan(pd, 0, 0);
			yhmdpd.put("noList", noList);
   			map.put("data",yhmdpd );
 			noList=null;
			yhmdpd=null;
   		} catch(Exception e){
   			map.put("data","" );
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
 
 }
