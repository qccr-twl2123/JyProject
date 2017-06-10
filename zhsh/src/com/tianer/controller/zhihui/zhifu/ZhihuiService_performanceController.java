package com.tianer.controller.zhihui.zhifu;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pingplusplus.model.Charge;
import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.controller.zhihui.payMoney.ChargeExample;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.operator_file.Operator_fileService;
import com.tianer.service.business.service_performance.Service_performanceService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
 
/** 
 * 类名称：业绩
 * 创建人：刘耀耀
 * 创建时间：2016-06-07
 */
@Controller
@RequestMapping(value="/zhihui_service_performance")
public class ZhihuiService_performanceController extends BaseController {
	
	@Resource(name="service_performanceService")
	private Service_performanceService service_performanceService;
 
 
	
	@Resource(name="operator_fileService")
	private Operator_fileService operator_fileService;

	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
	
	
	
	/**
	 * 列表服务商业绩
	 *  魏汉文20160612
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public ModelAndView list(Page page){
		//logBefore(logger, "列表服务商业绩");
		ModelAndView mv = this.getModelAndView();
		List<PageData>	varList = new ArrayList<PageData>();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
 		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		PageData sppd=new PageData();
		try{
			pd = this.getPageData();
			String first=pd.getString("first");
			if(first != null && first.equals("first")){
				//刷新服务商当月的月统计
				 TongYong.spMonth_number(0);
			}
			//1-服务商月报表，2-服务商销售明细，3-服务商积分收入明细，4-服务商广告收入明细，5-服务商提现申请明细
			String look_type=pd.getString("look_type");
			if(look_type == null || look_type.equals("")){
				look_type="1";
				pd.put("look_type", "1");
			}
			if(zhlogin != null){
				String login_type=zhlogin.getLogin_type();
				String login_id=zhlogin.getLogin_id();
				String login_name=zhlogin.getLogin_name();
				mv.addObject("login_type", login_type);
				mv.addObject("login_id", login_id);
				mv.addObject("login_name", login_name);
				if(login_type.equals("1")){//登陆用户的角色id:0-管理员，1-服务商，2-业务员，3-操作员，4-其他
 					sppd.put("sp_file_id",login_id);
 					sppd.put("nowmoney", sp_fileService.findById(sppd).getString("nowmoney"));//当前余额
 					sppd.put("notmoney", service_performanceService.getSumNotJiHuoOptrator(login_id));//未激活余额
 					pd.put("sp_file_id", login_id);
				} 
  				page.setPd(pd);
  				if(pd.getString("sp_file_id") != null && !pd.getString("sp_file_id").equals("")){
  					if(look_type.equals("1")){
  						varList=service_performanceService.datalistPageOne(page);
  					}else if(look_type.equals("2")){
  						varList=service_performanceService.datalistPageTwo(page);
  					}else if(look_type.equals("3")){
  						varList=service_performanceService.datalistPageThree(page);
  					}else if(look_type.equals("4")){
  						varList=service_performanceService.datalistPageFour(page);
  					}else if(look_type.equals("5")){
  						varList=service_performanceService.datalistPageFive(page);
  					}else if(look_type.equals("6")){
  						varList=service_performanceService.datalistPageSix(page);
  					}
  				}
  				mv.addObject("varList", varList);
   				//获取服务商列表
 				sppd.put("sp_status", "1");
 				List<PageData> spList=sp_fileService.listAll(sppd);
 				mv.addObject("spList", spList);
 				mv.addObject("sppd", sppd);
 				this.getHCfw();
 	 			mv.setViewName("zhihui/zhifu/zhifu5");
 			}else{
 				mv.setViewName("redirect:../zhihui_index.do");
 			}
  			mv.addObject("pd", pd);
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@Resource(name="clerk_fileService")
	private Clerk_fileService clerk_fileService;
	
	/**
	 * 列表业务员业绩
	 *  魏汉文20160612
	 */
	@RequestMapping(value="/listServiceClerk")
	public ModelAndView listServiceClerk(Page page){
 		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
 		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String first=pd.getString("first");
			if(first != null && first.equals("first")){
				//刷新服务商当月的月统计
				 TongYong.clerkMonth_number(0);
			}
 			if(zhlogin != null){
				String login_type=zhlogin.getLogin_type();
				String login_id=zhlogin.getLogin_id();
 				mv.addObject("login_type", login_type);
  				if(login_type.equals("1")){//登陆用户的角色id:0-管理员，1-服务商，2-业务员，3-操作员，4-其他
  					pd.put("sp_file_id", login_id);
				}else if(login_type.equals("2")){
					pd.put("clerk_file_id", login_id);
				}
  				if((pd.getString("content") != null && !pd.getString("content").equals("")) || pd.getString("clerk_file_id") != null){
  					page.setPd(pd);
  					List<PageData> varList=service_performanceService.listPageAllClerkMonth(page);
   	  				mv.addObject("varList", varList);
  				}else{
  					mv.addObject("varList", new ArrayList<PageData>());
  				}
   				this.getHCyw();
  	 			mv.setViewName("zhihui/zhifu/zhifu6");
  			}else{
  				this.getHCfw();
  	 			mv.setViewName("redirect:../zhihui_index.do");
  			}
  			mv.addObject("pd", pd);
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 修改备注
	 */
	@RequestMapping(value="/editRemark")
	public void editRemark(PrintWriter out){
		logBefore(logger, "修改开取发票状态");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			service_performanceService.editClerkMonth(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
//	
//	/**
//	 * 去新增页面
//	 */
//	@RequestMapping(value="/goAdd")
//	public ModelAndView goAdd(){
//		//logBefore(logger, "去新增Service_performance页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			mv.setViewName("business/service_performance/service_performance_edit");
//			mv.addObject("msg", "save");
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}						
//		return mv;
//	}	
	
//	/**
//	 * 去修改页面
//	 */
//	@RequestMapping(value="/goEdit")
//	public ModelAndView goEdit(){
//		//logBefore(logger, "去修改Service_performance页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			pd = service_performanceService.findById(pd);	//根据ID读取
//			mv.setViewName("business/service_performance/service_performance_edit");
//			mv.addObject("msg", "edit");
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}						
//		return mv;
//	}	
	
//	/**
//	 * 批量删除
//	 */
//	@RequestMapping(value="/deleteAll")
//	@ResponseBody
//	public Object deleteAll() {
//		//logBefore(logger, "批量删除Service_performance");
//		PageData pd = new PageData();		
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			pd = this.getPageData();
//			List<PageData> pdList = new ArrayList<PageData>();
//			String DATA_IDS = pd.getString("DATA_IDS");
//			if(null != DATA_IDS && !"".equals(DATA_IDS)){
//				String ArrayDATA_IDS[] = DATA_IDS.split(",");
//				service_performanceService.deleteAll(ArrayDATA_IDS);
//				pd.put("msg", "ok");
//			}else{
//				pd.put("msg", "no");
//			}
//			pdList.add(pd);
//			map.put("list", pdList);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		} finally {
//			logAfter(logger);
//		}
//		return AppUtil.returnObject(pd, map);
//	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		//logBefore(logger, "导出Service_performance到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("时间");	//1
			titles.add("收益对象");	//2
			titles.add("金额");	//3
			titles.add("支付时间");	//4
			dataMap.put("titles", titles);
			List<PageData> varOList = service_performanceService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TIME"));	//1
				vpd.put("var2", varOList.get(i).getString("PROFIT"));	//2
				vpd.put("var3", varOList.get(i).getString("MONEY"));	//3
				vpd.put("var4", varOList.get(i).getString("PAY_TIME"));	//4
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/* ===============================权限================================== */
	public void getHCfw(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("14"));
		}
		
	}
	/* ===============================权限================================== */
	
	
	/* ===============================权限================================== */
	public void getHCyw(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("15"));
		}
		
	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
 
	
	/**
	 * 服务商-3/业务员员-4新增提现记录魏汉文20160704
	 * 魏汉文
	 * 20160705
	 */
	@RequestMapping(value="/saveWithdraw")
 	public ModelAndView saveWithdraw(){
		//logBefore(logger, "提现");
		ModelAndView mv = this.getModelAndView();
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		String message="";
		String txid="";
		boolean flag=false;
		try{ 
			pd=this.getPageData();
			String money=pd.getString("money");
 			double txmoney=Double.parseDouble(money);
 			if(zhlogin != null){
 				txid=BaseController.getTXUID(zhlogin.getLogin_id());
				pd.put("user_id", zhlogin.getLogin_id());
				if( zhlogin.getLogin_id().contains("FW")){
					pd.put("user_type", "3");
 					pd.put("phone",zhlogin.getLogin_phone());
 					mv.setViewName("redirect:list.do");
  					pd.put("sp_file_id", zhlogin.getLogin_id());
 	 				PageData sppd=sp_fileService.findById(pd);//操作员所对应服务商的信息
 	 				if(sppd != null){
 	 					pd.put("province_name", sppd.getString("province_name"));
 	 	 				pd.put("city_name", sppd.getString("city_name"));
 	 	 				pd.put("area_name", sppd.getString("area_name"));
 	  	 				//判断type为1的时候是退还保证金，并修改保证金金额
 	 	 				String type = pd.getString("type");
 	 	 				if(type != null && type.equals("1")){
 	 	 					sppd.put("earnest_status", "2");
 	 	 					sp_fileService.edit(sppd);
 	  	 				}else{
 	 	 					double nowmoney=Double.parseDouble(sppd.getString("nowmoney"));
 	 	 	 				if (txmoney <= nowmoney-service_performanceService.getSumNotJiHuoOptrator(zhlogin.getLogin_id())) {
 	 	 	 					nowmoney=nowmoney-txmoney;
 	 	 	 					sppd.put("nowmoney", df.format(nowmoney));
  	 	 	 					sp_fileService.edit(sppd);
  	 	 	 				    //添加一个提现记录
 	 	 		 				PageData mmpd=new PageData();
   	 	 		 				mmpd.put("yewu_id", "Jiuyu");//业务对象
 	 	 		 	 			mmpd.put("yewu_type", "0");//业务对象
 	 	 		 				mmpd.put("money", money);//金额
 	 	 		 				mmpd.put("money_type", "5");//1、销售提成: 2、积分收益： 3、平台奖励，4-保证金，5-提现 
 	 	 		 	 			mmpd.put("operate_type", "1"); //1-服务商，2-业务员
 	 	 		 				mmpd.put("operate_id", sppd.getString("sp_file_id")); 
 	 	 		 				mmpd.put("isshouyi", "1");//0-收益，1-提现
 	 	 		 				mmpd.put("isjihuo", "0");//0-未激活（未处理），1-已激活（已处理）
 	 	 		 				mmpd.put("now_balance",  df.format(nowmoney));//余额
 	 	 		 				mmpd.put("service_performance_id", txid);//收益对象
 	 	 		 				ServiceHelper.getService_performanceService().save(mmpd);
 	 	 		 				flag=true;
 	 	 		 				pd.put("nowuser_money",  df.format(nowmoney));
 	 						}else{
 								message="余额不足，最多提现"+ df.format(nowmoney);
 							}
  	  	 				}
 	 				}
  				}
//				else if( zhlogin.getLogin_id().contains("YW")){
//					pd.put("user_type", "4");
//					pd.put("phone",zhlogin.getLogin_phone());
//					mv.setViewName("redirect:listService.do");
// 					pd.put("clerk_file_id", zhlogin.getLogin_id());
//					PageData clPd=clerk_fileService.findById(pd);
// 					if(clPd != null){
//						if( sp_fileService.findById(clPd) != null){
//							pd.put("province_name", sp_fileService.findById(clPd).getString("province_name"));
//		 	 				pd.put("city_name", sp_fileService.findById(clPd).getString("city_name"));
//		 	 				pd.put("area_name", sp_fileService.findById(clPd).getString("area_name"));
//						}
//						double nowmoney=Double.parseDouble(clPd.getString("nowmoney"));
//	 	 				if (txmoney <= nowmoney-service_performanceService.getSumNotJiHuoOptrator(zhlogin.getLogin_id())) {
//	 	 					nowmoney=nowmoney-txmoney;
//	 	 					clPd.put("nowmoney", nowmoney);
//	 	 					
//	 	 					clerk_fileService.edit(clPd);
//	 	 					//添加一个提现记录
//	 		 				PageData mmpd=new PageData();
//	 		 				mmpd.put("profit_name", clPd.getString("clerk_name"));//收益对象
//	 		 				mmpd.put("yewu_name", "九鱼平台");//业务对象
//	 		 				mmpd.put("yewu_id", "Jiuyu");//业务对象
//	 		 	 			mmpd.put("yewu_type", "0");//业务对象
//	 		 				mmpd.put("money", money);//金额
//	 		 				mmpd.put("money_type", "5");//1、销售提成: 2、积分收益： 3、平台奖励，4-保证金，5-提现 
//	 		 	 			mmpd.put("operate_type", "2"); //1-服务商，2-业务员
//	 		 				mmpd.put("operate_id", clPd.getString("clerk_file_id")); 
//	 		 				mmpd.put("isshouyi", "1");//0-收益，1-提现
//	 		 				mmpd.put("isjihuo", "0");//0-未激活（未处理），1-已激活（已处理）
//	 		 				mmpd.put("service_performance_id", txid);//收益对象
//	 		 				ServiceHelper.getService_performanceService().save(mmpd);
//	 		 				flag=true;
//	 		 				pd.put("nowuser_money", nowmoney);
//						}else{
//							message="余额不足，最多提现"+nowmoney;
//						}
//					}
//   				}
 			}else{
				mv.setViewName("zhihui/admin/login");
			}
 			if(flag){
  				PageData waterpd=new PageData();
 				waterpd.put("user_id",pd.getString("user_id"));
 				waterpd.put("user_type",pd.getString("user_type"));
 				waterpd.put("pay_status","0");
 	   			waterpd.put("waterrecord_id",txid);
 	 			waterpd.put("withdraw_rate","0");
 				waterpd.put("money_type","5");
 				waterpd.put("money","-"+ money);
 				waterpd.put("reduce_money", "0");
 				waterpd.put("arrivalmoney",  "-"+ money);
 				waterpd.put("nowuser_money", pd.get("nowuser_money").toString());
 				waterpd.put("application_channel","6" );
 				waterpd.put("remittance_name",pd.getString("account_name")+" "+pd.getString("withdraw_username"));
 				waterpd.put("remittance_type","1" );
 				waterpd.put("bank_money", "-"+ money );
 				waterpd.put("remittance_number",pd.getString("withdraw_number"));//支付人的支付账号
 				waterpd.put("createtime",DateUtil.getTime());
 				waterpd.put("over_time",DateUtil.getTime());
 	 			waterpd.put("jiaoyi_type","0");
 				waterpd.put("payee_number",Const.jiuyunumber);
 				waterpd.put("jiaoyi_id",txid );
 	 			waterpd.put("order_tn", txid );
 				waterpd.put("province_name", pd.getString("province_name"));
 				waterpd.put("city_name", pd.getString("city_name"));
 				waterpd.put("area_name", pd.getString("area_name"));
 				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
 				waterpd=null;
 			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
  		mv.addObject("pd", pd);
  		mv.addObject("message", message);
		return mv;
	}
	
	
 
	/**
	 *  去提现页面
	 * 魏汉文
	 * 20160705
	 */
	@RequestMapping(value="/goWithdraw")
 	public ModelAndView goWithdraw(){
		//logBefore(logger, "列表Withdraw_approval");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			if(zhlogin != null){
 				if( zhlogin.getLogin_id().contains("FW")){
 					mv.setViewName("zhihui/zhifu/zhifu7");
 				}else if( zhlogin.getLogin_id().contains("YW")){
					mv.setViewName("zhihui/zhifu/zhifu8");
				}else{
					mv.setViewName("zhihui/admin/login");
				}
 			}else{
				mv.setViewName("zhihui/admin/login");
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
  		mv.addObject("pd", pd);
		return mv;
	}
	
	
//	/**
//	 * 退还保证金跳转页面
//	 */
///*	@RequestMapping(value="/tiaozhuan")
//	@ResponseBody
//	public ModelAndView tiaozhuan(){
//		//logBefore(logger, "支付成功");
//		ModelAndView mv = this.getModelAndView();
//		mv.setViewName("zhihui/dangan/dangan18");
//		return mv;
//	}
//	*/
 
	  /**
 	 * 
 	* 方法名称:：thirdPartyCz 
 	* 方法描述：支付
 	* 创建人：魏汉文
 	* 创建时间：2016年7月6日 上午10:13:52
 	 */
	@RequestMapping(value="/partyCz")
	@ResponseBody
	public Object sp_PartyCz(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
  		String result="1";
		String message="充值确认中";
		// type代表支付方式
		PageData pd=new PageData();
		try{ 
			pd=this.getPageData();
 			String url=pd.getString("url");
			String money=pd.getString("money");
			String sp_file_id=pd.getString("sp_file_id");
 			String pay_way=pd.getString("pay_way");
			String ip=getIp(request);
			pd=sp_fileService.findById(pd);
			if(pd == null){
				map.put("result", "0");
				map.put("message", "当前服务商不存在");
		    	return map;
			}
			String waterrecord_id=BaseController.getCZUID(sp_file_id);
 			//System.out .println("新增充值记录waterrecord_id========================");
			PageData waterpd=new PageData();
			waterpd.put("pay_status","0");
   			waterpd.put("waterrecord_id",waterrecord_id);
				waterpd.put("user_id", sp_file_id);
				waterpd.put("user_type", "3");
				waterpd.put("withdraw_rate","0");
				waterpd.put("money_type","4");
				waterpd.put("money", money);
				waterpd.put("arrivalmoney",  money);
				waterpd.put("nowuser_money","0");
				waterpd.put("application_channel","6" );
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
				waterpd.put("remittance_number",pd.getString("phone"));//支付人的支付账号
				waterpd.put("createtime",DateUtil.getTime());
				waterpd.put("over_time",DateUtil.getTime());
	 			waterpd.put("jiaoyi_type","0");
				waterpd.put("payee_number",Const.jiuyunumber);
				waterpd.put("jiaoyi_id",waterrecord_id );
	 			waterpd.put("order_tn", pd.getString("order_tn"));
				waterpd.put("province_name", pd.getString("province_name"));
				waterpd.put("city_name", pd.getString("city_name"));
				waterpd.put("area_name", pd.getString("area_name"));
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
				Charge charge = ChargeExample.getPayTwo(waterrecord_id, Double.parseDouble(money)*100,ip,pay_way,"31","消费",url);
				if(charge== null){
						result="0";
						message="充值失败";
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
	
	
//	/**
//	 * 支付成功跳转页面
//	 */
//	@RequestMapping(value="/zhifu")
//	@ResponseBody
//	public ModelAndView zhifu(){
//		//logBefore(logger, "支付成功");
//		ModelAndView mv = this.getModelAndView();
//		mv.setViewName("zhihui/dangan/dangan19");
//		return mv;
//	}
//	
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
	
	
	/*
	 * 月份统计更新
	 * zhihui_service_performance/getMonthBynumber.do?number=0&type=2
	 */
	@RequestMapping(value="/getMonthBynumber")
	@ResponseBody
	public String getMonthBynumber(String number,String type){
		logBefore(logger, "统计N个月的报表");
		String ok="ok";
		try {
			//0-当月，，往上推，减
			int n=Integer.parseInt(number);
			if(type.equals("1")){
				TongYong.spMonth_number(n);
			}else{
				TongYong.clerkMonth_number(n);
			}
		} catch (Exception e) {
			// TODO: handle exception
			ok="error";
		}
		return ok;
	}
	
	
}
