package com.tianer.controller.storepc.store_wealthhistory;

import java.io.PrintWriter;
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
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.service.business.store_shift.Store_shiftService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storeapp.Payapp_historyService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.store_bankcard.Storepc_bankcardService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;

/** 
 * 
* 类名称：Storepc_scorewayController   
* 类描述：   商家财富记录
* 创建人：邢江涛  
* 创建时间：2016年6月21日 
 */
@Controller("storeStorepc_wealthhistoryController")
@RequestMapping(value = "/storepc_wealthhistory")
public class Storepc_wealthhistoryController extends BaseController {
	
	@Resource(name = "storepc_bankcardService")
	private Storepc_bankcardService bankcardService;
	@Resource(name = "storepc_wealthhistoryService")
	private Storepc_wealthhistoryService storepc_wealthhistoryService;
	
	@Resource(name="store_shiftService")
	private Store_shiftService store_shiftService;
	
 	public DecimalFormat df2=TongYong.df2;
	
	/*
	 * 修改数据/删除重复的数据 
	 * 
	 * storepc_wealthhistory/csWeath.do
	 * 
	    update tb_store_wealthhistory
		set in_jiqi='4'
		where profit_type in ('1','2','9','10');
		
		update tb_store_wealthhistory
		set in_jiqi='1'
		where profit_type in ('3');
		
		update tb_store_wealthhistory
		set in_jiqi='2'
		where profit_type in ('6','8');
		
		update tb_store_wealthhistory
		set store_wealthhistory_id=jiaoyi_id
		where jiaoyi_id != '' and profit_type in('3','6','8');
		
		update tb_store_wealthhistory
		set arrivalMoney=number;
		
		update tb_store_wealthhistory
		set store_wealthhistory_id=CONCAT(jiaoyi_id,1)
		where  profit_type='9';
		 
 		update tb_order
		set money_pay_type='3'
		where channel='alipay';
		update tb_order
		set money_pay_type='4'
		where channel='wx';
		update tb_order
		set money_pay_type='2'
		where channel='nowpay';

		update tb_order
		set in_jiqi='1'
		where pay_type in ('2','3');
		update tb_order
		set in_jiqi='2'
		where pay_type in ('1');

		
		
		
		
	 */
	@RequestMapping(value="/csWeath")
	public ModelAndView csWeath( ){
		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//获取所有的历史消息
			List<PageData>	varList = storepc_wealthhistoryService.listAll(pd);
			for(int i=0;i<varList.size();i++){
				PageData e=new PageData();
				e=varList.get(i);
				if(e.getString("jiaoyi_id") != null){
					int n=storepc_wealthhistoryService.findByIdCS(e);
					if(e.getString("store_wealthhistory_id").equals("201610220232285044")){
						System.out.println("重复");
					}
					if(  n > 1 ){
						 //删除
						storepc_wealthhistoryService.deleteThis(e);
	 				}
				}
 			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
  		return mv;
 	}
	
	/**
	 * 流水明细列表/销售明细列表/在线销售明细列表（提货券）
	 */
	@RequestMapping(value="/list")
	public ModelAndView liushuiList(Page page){
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//获取当前商家的班次 
 			List<PageData> shiftList=store_shiftService.listAll(pd);
			mv.addObject("shiftList", shiftList);
			if(slogin != null && slogin.getType().equals("2")){
				pd.put("store_operator_id", slogin.getLogin_id());
				pd.put("login_type", slogin.getType());
			}
			//获取所有操作员列表
			List<PageData> splist = storeManageService.listAll(pd);
			mv.addObject("splist", splist);
  			//==============================
			String chuli_type = pd.getString("chuli_type");
   			//列出支付列表nowpage
		    pd.put("nowpage", (page.getCurrentPage()-1)*10);
			page.setPd(pd);
			PageData nowpagesum=storepc_wealthhistoryService.sumNowPageWeath(page);
			mv.addObject("nowpagesum", nowpagesum);
			PageData allpagesum=storepc_wealthhistoryService.sumAllPageWeath(page);
			mv.addObject("allpagesum", allpagesum);
			List<PageData>	varList = storepc_wealthhistoryService.weallistPage(page);	//列出Store列表
 			int n=varList.size();
			PageData e=null;
			PageData mmpd=null;
			for (int i = 0; i <n ; i++) {
				e=varList.get(i);
 	 			if(e.getString("tihuo_id") != null && !e.getString("tihuo_id").equals("")){
	 					String s=e.getString("tihuo_id").substring(0, 2)+"****"+e.getString("tihuo_id").substring(6, 10);
	 					e.put("tihuo_id", s);
	 			}
 	 			String user_type=e.getString("user_type");
 	 			if(user_type == null){
 	 				e.put("show_id", "不存在");
 	 				e.put("user_name", "不存在");
					e.put("user_phone", "不存在");
					e.put("user_type", "1");
 	 			}else if(user_type.equals("2")){
 	 				mmpd=new PageData();
 	 				mmpd.put("member_id", e.getString("user_id"));
 					if(ServiceHelper.getAppMemberService().findById(mmpd) != null){
 						mmpd=ServiceHelper.getAppMemberService().findById(mmpd);
						String user_phone=mmpd.getString("phone");
 			 			if(user_phone.length() == 11){
			 				user_phone=user_phone.substring(0, 3)+"****"+user_phone.substring(7, 11);
			 			}
		 				String user_name=mmpd.getString("name");
						if(user_name.length() == 11){
							user_name=user_name.substring(0, 3)+"****"+user_name.substring(7, 11);
						}
 						e.put("user_name", user_name);
						e.put("user_phone", user_phone);
						e.put("show_id", mmpd.getString("show_lookid"));
						e.put("user_type", "2");
					}else{
						e.put("show_id", "不存在");
						e.put("user_name", "不存在");
						e.put("user_phone", "不存在");
						e.put("user_type", "2");
					}
				}else if(user_type.equals("1")){
					mmpd=new PageData();
					mmpd.put("store_id",  e.getString("user_id"));
					mmpd=ServiceHelper.getAppStoreService().findById(mmpd);
					if(mmpd != null){
						e.put("show_id", "--");
						e.put("user_name", mmpd.getString("store_name"));
						e.put("user_phone",mmpd.getString("registertel_phone"));
						e.put("user_type", "1");
					}else{
						e.put("show_id", "不存在");
						e.put("user_name", "不存在");
						e.put("user_phone", "不存在");
						e.put("user_type", "2");
					}
				}else {
					e.put("show_id", "Jiuyu");
					e.put("user_name", "");
					e.put("user_phone", "");
					e.put("user_type", "0");
				}
				if(e.getString("profit_type") != null && !e.getString("profit_type").equals("")){
					//类型
 					e.put("profit_name", Const.storeorderprofit_type[Integer.parseInt(e.getString("profit_type"))]);
				}
				String kahao = e.getString("remittance_number");
				if(kahao != null && !kahao.equals("")){
					if(kahao.length() > 4){
						String kh = kahao.substring(kahao.length()-4, kahao.length());
						//卡号以kh取值
						e.put("kh", kh);
					}else{
						e.put("kh", kahao);
					}
				}
 			}
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
    		if(chuli_type.equals("1")){
  				mv.setViewName("storepc/business_account2");
  			}
//  			else if(chuli_type.equals("2")){
//  				mv.setViewName("storepc/business_account4");
//  			}
  			else if(chuli_type.equals("3")){
  				mv.setViewName("storepc/business_account5");
  			}else if(chuli_type.equals("4")){
  				mv.setViewName("storepc/business_account6");
  			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
  		return mv;
 	}
	
	
	
	
	/**
	 * 查看流水详情
	 */
	@RequestMapping(value="/liuShuiDetailById")
	@ResponseBody
	public Object liuShuiDetailById(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
 		try{ 
			pd=this.getPageData();
			PageData e=new PageData();
			e=storepc_wealthhistoryService.liuShuiDetailById(pd);
			if(e == null){
				e=new PageData();
			}else{
				//优惠项
 	 			List<PageData> discountList =new ArrayList<PageData>();
 	 			String discount_content=e.getString("discount_content");
 	 			if(discount_content != null && discount_content.contains(",")){
 	 				String[] str=discount_content.split(",");
 	 				PageData  dispd=null;
 	 				for(int i=0;i<str.length ; i++){
	   					dispd=new PageData();
	   					if(str[i].contains("@")){
	   						dispd.put("content", str[i].split("@")[0]);
	   						dispd.put("number", str[i].split("@")[2]);
 	   						discountList.add(dispd);
	   					}
 		   				dispd=null;
	 	 			}
 	 			}	
 	 			e.put("discountList", discountList);
 	 			if(e.getString("tihuo_id") != null && !e.getString("tihuo_id").equals("")){
	 					String s=e.getString("tihuo_id").substring(0, 2)+"****"+e.getString("tihuo_id").substring(6, 10);
	 					e.put("tihuo_id", s);
	 			}
	 			String user_type=e.getString("user_type");
	 			if(user_type == null){
	 				e.put("show_id","不存在");
	 				e.put("user_name", "不存在");
					e.put("user_phone", "不存在");
					e.put("user_type", "不存在");
	 			}else if(user_type.equals("2")){
					PageData mpd=new PageData();
					mpd.put("member_id", e.getString("user_id"));
					mpd=ServiceHelper.getAppMemberService().findById(mpd);
					if(mpd != null){
						String user_phone=mpd.getString("phone");
						user_phone=user_phone.substring(0, 3)+"****"+user_phone.substring(7, 11);
						String user_name=mpd.getString("name");
						if(user_name.length() == 11){
							user_name=user_name.substring(0, 3)+"****"+user_name.substring(7, 11);
						}
						e.put("show_id", mpd.getString("show_lookid"));
						e.put("user_name", user_name);
						e.put("user_phone", user_phone);
						e.put("user_type", "会员");
					}else{
						e.put("show_id","不存在");
						e.put("user_name", "不存在");
						e.put("user_phone", "不存在");
						e.put("user_type", "不存在");
					}
				}else if(user_type.equals("1")){
					PageData spd=new PageData();
					spd.put("store_id",  e.getString("user_id"));
					spd=ServiceHelper.getAppStoreService().findById(spd);
					if(spd != null){
						e.put("show_id","--");
						e.put("user_name", spd.getString("store_name"));
						e.put("user_phone",spd.getString("registertel_phone"));
						e.put("user_type_name", "商家");
					}else{
						e.put("show_id","不存在");
						e.put("user_name", "不存在");
						e.put("user_phone", "不存在");
						e.put("user_type", "不存在");
					}
				}else {
					e.put("show_id","不存在");
					e.put("user_name", "不存在");
					e.put("user_phone", "不存在");
					e.put("user_type_name", "不存在");
				}
				if(e.getString("profit_type") != null && !e.getString("profit_type").equals("")){
					//类型
					e.put("profit_name", Const.storeorderprofit_type[Integer.parseInt(e.getString("profit_type"))]);
				}
  			}
			map.put("data", e);
		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
			map.put("data", e);
		}
		map.put("result", result);
		map.put("message", message);
    	return map;
	}
	
	
	
	
	/**
	 * 在线销售明细表
	 * 未提货以及已提货的订单
	 */
	@RequestMapping(value="/orderlistPage")
	public ModelAndView orderlistPage(Page page){
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(slogin != null && slogin.getType().equals("2")){
				pd.put("store_operator_id", slogin.getLogin_id());
				pd.put("login_type", slogin.getType());
			}
			page.setPd(pd);
			List<PageData>	orderList = storepc_wealthhistoryService.orderlistPage(page);	//列出Store列表
 			int n=orderList.size();
			PageData e=null;
 			for (int i = 0; i <n ; i++) {
 				e=orderList.get(i);
	 			if(e.getString("tihuo_id") != null && !e.getString("tihuo_id").equals("")){
	 					String s=e.getString("tihuo_id").substring(0, 2)+"****"+e.getString("tihuo_id").substring(6, 10);
	 					e.put("tihuo_id", s);
	 			}
	 			String user_phone=e.getString("phone");
	 			if(user_phone.length() == 11){
	 				user_phone=user_phone.substring(0, 3)+"****"+user_phone.substring(7, 11);
	 			}
 				String user_name=e.getString("name");
				if(user_name.length() == 11){
					user_name=user_name.substring(0, 3)+"****"+user_name.substring(7, 11);
				}
				e.put("name", user_name);
				e.put("phone", user_phone);
  			}
			mv.addObject("varList", orderList);
			mv.setViewName("storepc/business_account4");
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
  		return mv;
 	}
	
	
	
	
 
	
	/**
	 * 班次汇总列表
	 */
	@RequestMapping(value="/BanCiHuiZonglist")
	public ModelAndView BanCiHuiZonglist(){
		ModelAndView mv = this.getModelAndView();
 		List<PageData> tjList=new ArrayList<PageData>();
 		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();	
 		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//获取所有班次
			List<PageData> shiftList=store_shiftService.listAll(pd);
			mv.addObject("shiftList", shiftList);
			shiftList=null;
			if(slogin != null && slogin.getType().equals("2")){
				pd.put("store_operator_id", slogin.getLogin_id());
				pd.put("login_type", slogin.getType());
			}
			//获取所有操作员列表
			List<PageData> splist = storeManageService.listAll(pd);
 			mv.addObject("splist", splist);
 			splist=null;
 			//获取操作员
			List<PageData> soplist=storeManageService.listAllOperator(pd);
			PageData sumoptator=null;
			int n=soplist.size();
			PageData e=null;
 			for (int i = 0; i <n ; i++) {
 				 e=soplist.get(i);
  				 sumoptator=storepc_wealthhistoryService.BanCiHuiZongByOprator(e);
 				 sumoptator.put("ordernumber", ServiceHelper.getStorepc_wealthhistoryService().countOrderNumberByOprator(e));
 				 sumoptator.put("logintime", e.getString("logintime"));
 				 sumoptator.put("downtime", e.getString("downtime"));
 				 sumoptator.put("operator_name", e.getString("operator_name"));
   				 tjList.add(sumoptator);
				 sumoptator=null;
   			}
			soplist=null;
			mv.addObject("tjList", tjList);
			tjList=null;
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd",pd);
		mv.setViewName("storepc/business_account7");
 		return mv;
 	}
	
	
	
	/**
	 * 编辑备注
	 */
	@RequestMapping(value="/updRemark")
	@ResponseBody
	public Object updRemark(){
 		PageData pd = new PageData();
 		Map<String,Object> map = new HashMap<String,Object>();
 		try {
			pd = this.getPageData();
			storepc_wealthhistoryService.updRemark(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return map;
 	}
	
	
	
	/**
	 * 订单退款操作
	 */
	@RequestMapping(value="/returnBackOrder")
	public void delete(PrintWriter out){
		logBefore(logger, "订单退款操作");
 		PageData pd = new PageData();
		try{
 	 		pd=this.getPageData();
 	 		pd=TongYong.TiHuoReturnOrder(pd, "1");
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}finally{
			out.write("success");
			out.close();
		}
 	}
	
	
	/**
	 * 提现申请撤回
	 */
	@RequestMapping(value="/txReturn")
	public void TxReturn(PrintWriter out){
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//处理为处理的的提现申请数据
			pd.put("process_status", "0");
			PageData weathpd=storepc_wealthhistoryService.findByWealthId(pd);
			pd.put("store_id", weathpd.getString("store_id"));
			double now_wealth=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(pd))-Double.parseDouble(weathpd.getString("number"));
			pd.put("now_wealth", TongYong.df2.format(now_wealth));
			appStoreService.editWealthById(pd);	
			//更新提现信息
 			pd.put("tixian_money", TongYong.df2.format(Double.parseDouble(weathpd.getString("tixian_money"))+Double.parseDouble(weathpd.getString("number"))));
 			appStoreService.edit(pd);
			//删除订单 
			pd.put("waterrecord_id", weathpd.getString("store_wealthhistory_id"));
			ServiceHelper.getStorepc_wealthhistoryService().deleteThis(pd);
			ServiceHelper.getWaterRecordService().deleteWater(pd);
  			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 	}
	
	
	@Resource(name="payapp_historyService")
	private Payapp_historyService historyService;
	
	 
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
 	 
	
	/**
	 * 判断提货卷是否存在  魏汉文20160706
	 */
	@RequestMapping(value="/findOrderDetail")
	@ResponseBody
	public Object findOrderDetail(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			 	pd = this.getPageData();
 			 	pd=historyService.tihuoByOrderId(pd);
 			 	//获取订单商品以及商家个别信息
 			  	pd=TongYong.getGoodsListByOrder(pd);
 			  	//获取商品信息
 			  	if(pd.getString("name") != null && pd.getString("name").equals("") &&  pd.getString("name").length() == 11){
 			    	pd.put("name", pd.getString("name").substring(0, 3)+"****"+pd.getString("name").substring(7, 11));
 			    }
 			    String s="";
 			    if(pd.getString("tihuo_id") != null && !pd.getString("tihuo_id").equals("")){
 					s=pd.getString("tihuo_id").substring(0, 2)+"****"+pd.getString("tihuo_id").substring(6, 10);
 				}
 			    if(pd.getString("phone") != null &&  pd.getString("phone").length() == 11){
 			    	pd.put("phone", pd.getString("phone").substring(0, 3)+"****"+pd.getString("phone").substring(7, 11));
 			    }
 			  	pd.put("tihuo_id", s);
      	} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
   		return map;
	}
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	/**
	 * 添加商家历史订单记录 localhost/zhsh/storepc_wealthhistory/addstoreHistory.do
	 */
//	@RequestMapping(value="/addstoreHistory")
//	@ResponseBody
	public Object addstoreHistory(){
 		PageData pd = new PageData();
 		Map<String,Object> map = new HashMap<String,Object>();
 		try {
			pd = this.getPageData();
			/*
			 * 1-商家收益（提现），2-商家消费（充值积分），3-消费收款（现金，积分，第三方，提货卷，余额），
			 * 4-用户积分或余额支付的金钱，5-第三方支付的金额，6-抢积分红包,7-消费返积分，
			 * 8-发送积分红包，9-首次购买服务，10-服务续费
				11，消费返推送积分，12-人脉推广收益
			 */
			//新增商家财富历史记录
	   			pd.put("wealth_type", "1");
				pd.put("profit_type", "2");
				pd.put("number", "300");
				pd.put("store_id", "56801458");
				pd.put("store_operator_id","jy56801458");
				pd.put("process_status", "1");
				pd.put("pay_type", "nowpay");//alipay_pc_direct,wx
	   			pd.put("last_wealth", "0");
	   			pd.put("arrivalMoney", "0");//实际到账金额
	   			pd.put("jiaoyi_id", "CZ2016091711322913901");
	   			pd.put("user_id", "Jiuyu");
				pd.put("store_wealthhistory_id", BaseController.getXFUID("56801458"));
				appStoreService.saveWealthhistory(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return map;
 	}
	
	/**
	 * 添加商家历史订单记录 localhost/zhsh/storepc_wealthhistory/updatemoeny.do
	 */
//	@RequestMapping(value="/updatemoeny")
//	@ResponseBody
	public Object updatemoeny(){
 		PageData pd = new PageData();
 		Map<String,Object> map = new HashMap<String,Object>();
 		try {
			pd = this.getPageData();
			List<PageData> wathlist=storepc_wealthhistoryService.listAll(pd);
			for (PageData pageData : wathlist) {
				pageData.put("arrivalmoney", pageData.getString("number"));
				pageData.put("waterrecord_id", pageData.getString("store_wealthhistory_id"));
				ServiceHelper.getWaterRecordService().editWaterRecord(pageData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return map;
 	}
	
	/**
	 * 添加商家历史订单记录 localhost/zhsh/storepc_wealthhistory/addstoreLiuShuiHistory.do
	 */
//	@RequestMapping(value="/addstoreLiuShuiHistory")
//	@ResponseBody
	public Object addstoreLiuShuiHistory(){
 		PageData pd = new PageData();
  		Map<String,Object> map = new HashMap<String,Object>();
 		try {
			pd = this.getPageData();
			//获取所有商家
			List<PageData> storelist=appStoreService.listAllOkStore(pd);
			for (PageData e : storelist) {
					e=appStoreService.findById(e);
					if(e != null){
						String store_id=e.getString("store_id");
						//新增金额200
						String money="200";
						if(store_id.equals("56801458")){
							money="300";
						}else if(store_id.equals("83891014")){
							money="0";
						}
	 					double now_money=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(e));
			   			PageData editpd=new PageData();
			   			editpd.put("now_wealth", df2.format(now_money+Double.parseDouble(money)));
			   			editpd.put("store_id", store_id); 
 			   			appStoreService.editWealthById(editpd);
					/*
					 * 1-商家收益（提现），2-商家消费（充值积分），3-消费收款（现金，积分，第三方，提货卷，余额），
					 * 4-用户积分或余额支付的金钱，5-第三方支付的金额，6-抢积分红包,7-消费返积分，
					 * 8-发送积分红包，9-首次购买服务，10-服务续费
						11，消费返推送积分，12-人脉推广收益
					 */
					//新增商家财富历史记录
						String store_wealthhistory_id=BaseController.getFanKuiID();
			   			pd.put("wealth_type", "1");
						pd.put("profit_type", "2");
						pd.put("number", money);
						pd.put("store_id", store_id);
						pd.put("store_operator_id","jy"+store_id);
						pd.put("process_status", "1");
						pd.put("pay_type", "alipay"); 
			   			pd.put("last_wealth", ServiceHelper.getAppStoreService().sumStoreWealth(e));
			   			pd.put("arrivalMoney", money);//实际到账金额
			   			pd.put("jiaoyi_id", store_wealthhistory_id);
			   			pd.put("user_id", "Jiuyu");
						pd.put("store_wealthhistory_id", store_wealthhistory_id);
						appStoreService.saveWealthhistory(pd);
						//添加流水记录
							System.out .println("新增充值记录tb_pay_history========================");
				   			PageData waterpd=new PageData();
		    				waterpd.put("pay_status","1");
		    	   			waterpd.put("waterrecord_id",store_wealthhistory_id);
		   					waterpd.put("user_id", store_id);
		   					waterpd.put("user_type", "1");
		    				waterpd.put("withdraw_rate","0");
		   					waterpd.put("money_type","1");
		   	 				waterpd.put("money",  money);
		   	 				waterpd.put("reduce_money","0");
		   					waterpd.put("arrivalmoney",  money);
		   					waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(e));
		   					waterpd.put("application_channel", "" );
		   					waterpd.put("remittance_name", Const.payjiqi[3]);
		   					waterpd.put("remittance_type","3" );
		   					waterpd.put("alipay_money",  money);
		   					waterpd.put("remittance_number",e.getString("registertel_phone"));//支付人的支付账号
		    				waterpd.put("createtime",DateUtil.getTime());
		   					waterpd.put("over_time",DateUtil.getTime());
		   	  				waterpd.put("jiaoyi_type","0");
		   					waterpd.put("payee_number",Const.jiuyunumber);
		    	 			waterpd.put("order_tn",store_wealthhistory_id);
		   					waterpd.put("province_name", e.getString("province_name"));
		   					waterpd.put("city_name", e.getString("city_name"));
		   					waterpd.put("area_name", e.getString("area_name"));
		    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
		    				waterpd=null;
					}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return map;
 	}
	


}
