package com.tianer.controller.zhihui.dangan;

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
import java.util.Set;
import java.util.Map.Entry;

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
import com.tianer.controller.zhihui.payMoney.ChargeExample;
import com.tianer.entity.Page;
 import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.city_file_sort.City_file_sortService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sp_file.Sp_fileService;
 import com.tianer.service.business.subsidiary.SubsidiaryService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;
import com.tianer.util.huanxin.HuanXin;

/** 
 * 
* 类名称：Sp_fileController   
* 类描述：   服务商档案
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:57:29
 */
@Controller
@RequestMapping(value="/zhihui_sp_file")
public class ZhihuiSp_fileController extends BaseController {
	
	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
 
		
	/**
	 * 新增
	 * 魏汉文20160614
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
//		//logBefore(logger, "新增Sp_file");
		ModelAndView mv = this.getModelAndView();
		List<PageData> monthList=new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//System.out.println(pd.toString());
 		PageData pg = new PageData();
		//判断在团队名称不重复的情况下进行保存
		pg = sp_fileService.findByName(pd);
		if(pg == null || pg.equals("")){
			String sp_file_id=pd.getString("sp_file_id");
			//判断服务商是否有数据
//			List<PageData> spList=sp_fileService.listAll(pd);
			String count=sp_fileService.listAllCount(pd);
			if(Integer.parseInt(count) == 0){
					pd.put("sp_file_id", "FW000001");
			}else{
					String id=sp_fileService.getMaxID(pd);
					id=id.substring(2);
					String nextId=StringUtil.getNextId(id);
					pd.put("sp_file_id", "FW"+nextId);
			}
			String allmonth=pd.getString("allmonth_id");
			String[] monthStr=allmonth.split("@");
			for(int i=1;i<monthStr.length ; i++){
				PageData e=new PageData();
				String sp_file_monthly_id=monthStr[i];
				String month=pd.getString(sp_file_monthly_id+"month");
				String user_number_indicator=pd.getString(sp_file_monthly_id+"user_number_indicator");
				String completion_rate=pd.getString(sp_file_monthly_id+"completion_rate");
				String flow_indicators=pd.getString(sp_file_monthly_id+"flow_indicators");
				String water_completion_rate=pd.getString(sp_file_monthly_id+"water_completion_rate");
				String isqualified=pd.getString(sp_file_monthly_id+"isqualified");
				e.put("sp_file_monthly_id", sp_file_monthly_id);
				e.put("month", month);
				e.put("user_number_indicator", user_number_indicator);
				e.put("completion_rate", completion_rate);
				e.put("flow_indicators", flow_indicators);
				e.put("water_completion_rate", water_completion_rate);
				e.put("isqualified", isqualified);
				e.put("sp_file_id", sp_file_id);
				monthList.add(e);
				e=null;
			}
	 		sp_fileService.saveList(pd,monthList);
	 		//注册环信
			HuanXin.regirstHx(sp_file_id, sp_file_id, sp_file_id);
	 		//将之前的城市分类的状态变为0，表示未选中
 			PageData sortpd=new PageData();
  			String[] newstr=pd.getString("allcity_file_sort_id").split(",");
 			sortpd.put("array", newstr);
 			sortpd.put("choose_status", "1");
 			city_fileService.editStatusChoose(sortpd);
		}
		mv.setViewName("redirect:../zhihui_sp_file/list.do");
		return mv;
	}
	
	/**
	 * 删除操作员
 	 */
	@RequestMapping(value="/delete")
 	public ModelAndView delete(){
//		//logBefore(logger, "删除操作员");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
  			sp_fileService.delete(pd);
  			PageData sortpd=new PageData();
 			String[] oldstr=sp_fileService.findById(pd).getString("allcity_file_sort_id").split(",");
 			sortpd.put("array", oldstr);
 			sortpd.put("choose_status", "0");
 			city_fileService.editStatusChoose(sortpd);
		} catch (Exception e) {
			// TODO: handle exception
		}
  		mv.setViewName("redirect:../zhihui_sp_file/list.do?currentPage="+pd.getString("currentPage"));
		return mv;
	}
	
	/**
	 * 判断是否有重复团队名称
	 */
	@RequestMapping(value="/isname")
	@ResponseBody
	public Object isname(){
//		//logBefore(logger, "判断是否有重复团队名称");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			pd = sp_fileService.findByName(pd);
			if(pd != null && !pd.equals("")){
				map.put("result", "01");
			}
 		} catch(Exception e){
 			map.put("result", "00");
			logger.error(e.toString(), e);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 修改Sp_file
	 * 魏汉文20160614
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
//		//logBefore(logger, "修改Sp_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
  			//将之前的城市分类的状态变为0，表示未选中
 			PageData sortpd=new PageData();
 			String[] oldstr=pd.getString("oldallcity_file_sort_id").split(",");
 			sortpd.put("array", oldstr);
 			sortpd.put("choose_status", "0");
 			city_fileService.editStatusChoose(sortpd);
  			sortpd=null;
 			sortpd=new PageData();
 			String[] newstr=pd.getString("allcity_file_sort_id").split(",");
 			sortpd.put("array", newstr);
 			sortpd.put("choose_status", "1");
 			city_fileService.editStatusChoose(sortpd);
 			//修改城市的分类选中状态
 			//System.out.println(pd.getString("sp_address"));
 			sp_fileService.edit(pd);
 			//年份
			String allmonth=pd.getString("allmonth_id");
			String[] monthStr=allmonth.split("@");
			for(int i=1;i<monthStr.length ; i++){
				PageData e=new PageData();
				String sp_file_monthly_id=monthStr[i];
				String month=pd.getString(sp_file_monthly_id+"month");
				String user_number_indicator=pd.getString(sp_file_monthly_id+"user_number_indicator");
				String completion_rate=pd.getString(sp_file_monthly_id+"completion_rate");
				String flow_indicators=pd.getString(sp_file_monthly_id+"flow_indicators");
				String water_completion_rate=pd.getString(sp_file_monthly_id+"water_completion_rate");
				String isqualified=pd.getString(sp_file_monthly_id+"isqualified");
				e.put("sp_file_monthly_id", sp_file_monthly_id);
				e.put("month", month);
				e.put("user_number_indicator", user_number_indicator);
				e.put("completion_rate", completion_rate);
				e.put("flow_indicators", flow_indicators);
				e.put("water_completion_rate", water_completion_rate);
				e.put("isqualified", isqualified);
				e.put("sp_file_id", pd.getString("sp_file_id"));
				PageData monthPd=ServiceHelper.getService_performanceService().findByIdspMonth(e);
				if(monthPd == null){
					ServiceHelper.getService_performanceService().savespMonth(e);
				}else{
					ServiceHelper.getService_performanceService().editspMonth(e);
				}
	 		}
		} catch (Exception e) {
			// TODO: handle exception
		}
  		mv.setViewName("redirect:../zhihui_sp_file/list.do?currentPage="+pd.getString("currentPage"));
		return mv;
	}
	
	
	/**
	 * 修改审核状态
	 */
	@RequestMapping(value="/editSpstatus")
	@ResponseBody
	public Object editSpstatus() throws Exception{
//		//logBefore(logger, "修改Sp_file");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			PageData e=new PageData();
			e=sp_fileService.findById(pd);
//			String principal=e.getString("principal");
			String phone=e.getString("phone");
			String sp_file_id=e.getString("sp_file_id");
			String password=this.get6UID();
			pd.put("password", password);
			sp_fileService.edit(pd);
			//发短信至负责人
			SmsUtil.sendPassSp(phone,password,sp_file_id);
			//新增环信账号
 			HuanXin.regirstHx(sp_file_id, sp_file_id, sp_file_id);
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("修改服务商审核时候出错"+e.toString());
		}
 		map.put("result", result);
 		return  map;
	}
	
	@Resource(name="city_file_sortService")
	private City_file_sortService city_file_sortService;
	
	/**
	 * 服务商列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
//		//logBefore(logger, "列表Sp_file");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(zhlogin != null && zhlogin.getMenu_role_id() != null){
				//判断是否为服务商==&业务员员==2
				pd.put("login_cityname", zhlogin.getCity_name()); 
				pd.put("login_areaname", zhlogin.getArea_name());
				if(zhlogin.getMenu_role_id().equals("1")){
	 				pd.put("sp_file_id", zhlogin.getLogin_id());
 	 			}else if(zhlogin.getMenu_role_id().equals("2")){ 
					pd.put("clerk_file_id", zhlogin.getLogin_id());
 	  			}
				page.setPd(pd);
				List<PageData> provincelist=pcdService.listAll(pd);//省list
				mv.addObject("provincelist", provincelist);
				//列出Sp_file列表
				List<PageData>	varList = sp_fileService.list(page);
				for(int i=0;i<varList.size();i++){
					if(i<3){
						String sort_name="";
						PageData citypd=varList.get(i);
						if(citypd != null){
							if(citypd.getString("allcity_file_sort_id") != null){
								String[] city_file_sort_id=citypd.getString("allcity_file_sort_id").split(",");
								for (int j = 0; j < city_file_sort_id.length; j++) {
									 PageData e=new PageData();
									 e.put("city_file_sort_id", city_file_sort_id[j]);
									 if(city_file_sort_id[j] != ""){
										 e=city_file_sortService.findById(e);
										 if(e != null){
											 sort_name+=" "+e.getString("sort_name")+" ";
										 }
									 }
	 		 					}
								 varList.get(i).put("sort_name", sort_name);
							}
	 					}
	 				}
				}
				this.getHC(); //调用权限
				mv.setViewName("zhihui/dangan/dangan5");
				mv.addObject("varList", varList);
				mv.addObject("pd", pd);
 			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		//logBefore(logger, "去新增Sp_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
  			//获取所有子公司
			List<PageData>	spList = subsidiaryService.listAll(pd);	//列出Subsidiary列表
 			mv.addObject("spList", spList);
			mv.setViewName("zhihui/dangan/dangan6");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	
	/**
	 * 通过子公司ID获取该子公司下的所有省市区
	 */
	@RequestMapping(value="/findSubPcdList")
	@ResponseBody
	public Object findSubPcdList() {
		//logBefore(logger, "通过子公司ID获取该子公司下的所有省市区");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> subsidiarypcd = new ArrayList<PageData>();
		List<PageData> list = new ArrayList<PageData>();
		Map<String , Object> subMap = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> subList=subsidiaryService.findSubPcdList(pd);
			if(subList.size() > 0 ){
				for (int i = 0; i < subList.size(); i++) {
					//区域id为0代表全市
					String area_id = subList.get(i).getString("area_id");
					//在全市的情况下去获取该市的全部区域
					if(area_id.equals("0")){
						String city_id = subList.get(i).getString("city_id");
						list = subsidiaryService.cityAll(city_id);
						//拿到所有为全市的区域
						for (int j = 0; j < list.size(); j++) {
							String area_name = list.get(j).getString("area_name");
							String id = list.get(j).getString("area_id");
							subMap.put(id, area_name);
						}
						list=null;
					}else {
						for (int k = 0; k < subList.size(); k++) {
							String area_name = subList.get(k).getString("area_name");
							String id = subList.get(k).getString("area_id");
							subMap.put(id, area_name);
						}
					}
				}
			}
		
			Set<Entry<String, Object>> entryseSet = subMap.entrySet();
			  for (Entry<String, Object> entry:entryseSet) {
//				   //System.out.println(entry.getKey()+","+entry.getValue());
				    PageData e=new PageData();
				    //e=subsidiarypcd.get(entry.hashCode());
				    String id = entry.getKey();
				    String name = (String) entry.getValue();
				    e.put("area_id", id);
				    e.put("area_name", name);
					e=city_fileService.findById(e);
					if(e == null || e.getString("open_status").equals("0")){
						subsidiarypcd.remove(e);
						//i=i-1;
					}
					subsidiarypcd.add(e);
					e=null;
			  }	
			map.put("subsidiarypcd", subsidiarypcd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return map;
	}
 
	
	
	
	@Resource(name="subsidiaryService")
	private SubsidiaryService subsidiaryService;
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		//logBefore(logger, "去修改Sp_file页面");
		ModelAndView mv = this.getModelAndView();
//		List<PageData> areaList = new ArrayList<PageData>();
 		PageData pd = new PageData();
		pd = this.getPageData();
		try {
  			//获取所有子公司
			List<PageData>	spList = subsidiaryService.listAll(pd);	//列出Subsidiary列表
 			mv.addObject("spList", spList);
 			//当前页
			String currentPage=pd.getString("currentPage");
			if(currentPage == null || currentPage.equals("")){
				currentPage="1";
			}
			pd = sp_fileService.findById(pd);	//根据ID读取
//			//System.out.println("pd=="+pd);
			String allcity_file_sort_id=pd.getString("allcity_file_sort_id");
			if(pd != null && !pd.equals("")){
				pd.put("currentPage", currentPage);
	  			//获取月度审核表
				List<PageData> monthList=ServiceHelper.getService_performanceService().listAllspMonth(pd);
				mv.addObject("monthList", monthList);
				//System.out.println(monthList);
	  			//获取所有的省市区获取分类
				pd.put("sort_parent_id", "0");
 				pd.put("sort_type", "1");
	  			List<PageData> sortlist=city_fileService.listAllCitySort(pd);//1/2级分类
	  			for(int i=0;i<sortlist.size();i++){
	  				PageData e=new PageData();
	  				e=sortlist.get(i);
//	  				//System.out.println("省市区获取分类======="+e.toString());
	  				if(allcity_file_sort_id != null && e.getString("choose_status") != null && e.getString("choose_status").equals("1") && !allcity_file_sort_id.contains(e.getString("city_file_sort_id"))){
	  					sortlist.remove(i);
	  					i=i-1;
	  				}
	  				e=null;
	  			}
	  			mv.addObject("sortlist", sortlist);
	 			mv.setViewName("zhihui/dangan/dangan6");
				mv.addObject("msg", "edit");
				mv.addObject("pd", pd);
			}
 		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去支付保证金界面
	 * 魏汉文20160615
	 */
	@RequestMapping(value="/goPayEarnest_money")
	public ModelAndView goPayEarnest_money(){
//		//logBefore(logger, "去支付保证金界面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
  			PageData e=new PageData();
  			e=sp_fileService.findById(pd);
			mv.setViewName("zhihui/dangan/dangan17");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			mv.addObject("e", e);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	
	/**
	 * 去申请退还保证金
	 * 魏汉文20160615
	 */
	@RequestMapping(value="/returnEarnest_money")
	public ModelAndView returnEarnest_money(){
		//logBefore(logger, "去支付保证金界面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			PageData e=new PageData();
  			e=sp_fileService.findById(pd);
  			mv.addObject("e", e);
			mv.setViewName("zhihui/dangan/dangan18");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
 
	
	  /**
	 	 * 
	 	* 方法名称:：sp_PartyCz 
	 	* 方法描述：服务商支付保证金----------支付宝微信支付
	 	* 创建人：魏汉文
	 	* 创建时间：2016年7月6日 上午10:13:52
	 	 */
		@RequestMapping(value="/sp_PartyCz")
		@ResponseBody
		public Object sp_PartyCz(HttpServletRequest request) throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
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
					//新增支付记录waterrecord_id
					//System.out .println("新增充值记录waterrecord_id========================");
					PageData waterpd=new PageData();
    				waterpd.put("pay_status","0");
    	   			waterpd.put("waterrecord_id",waterrecord_id);
   					waterpd.put("user_id", sp_file_id);
   					waterpd.put("user_type", "3");
    				waterpd.put("withdraw_rate","0");
   					waterpd.put("money_type","4");
   	 				waterpd.put("money", money);
   	 				waterpd.put("reduce_money", "0");
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
						waterpd.put("remittance_name", Const.payjiqi[1]);
	   					waterpd.put("remittance_type","1" );
	   					waterpd.put("bank_money",  money );
					}
   					waterpd.put("remittance_number",pd.getString("phone"));//支付人的支付账号
    				waterpd.put("createtime",DateUtil.getTime());
   					waterpd.put("over_time",DateUtil.getTime());
   	  				waterpd.put("jiaoyi_type","0");
   					waterpd.put("payee_number",Const.jiuyunumber);
   					waterpd.put("jiaoyi_id",waterrecord_id );
    	 			waterpd.put("order_tn",waterrecord_id);
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
	
		
		/**
		 * 
		 * 方法名称:：addPayCz 
		 * 方法描述：服务商支付保证金-------------银联支付
		 * 创建人：魏汉文
		 * 创建时间：2016年7月6日 上午10:13:52
		 */
		@RequestMapping(value="/addPayCz")
		@ResponseBody
		public Object addPayCz() throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
 			String result="1";
			String message="支付完成，等待确认";
 			PageData pd=new PageData();
			try{ 
					pd=this.getPageData();
  					String money=pd.getString("money");
					String sp_file_id=pd.getString("sp_file_id");
					String pay_way=pd.getString("pay_way");
 					pd=sp_fileService.findById(pd);
 					String waterrecord_id=BaseController.getCZUID(sp_file_id);
 					//System.out .println("新增充值记录waterrecord_id========================");
					PageData waterpd=new PageData();
					waterpd.put("pay_status","1");
		   			waterpd.put("waterrecord_id",waterrecord_id);
					waterpd.put("user_id", sp_file_id);
					waterpd.put("user_type", "3");
					waterpd.put("withdraw_rate","0");
					waterpd.put("money_type","4");
	 				waterpd.put("money", money);
	 				waterpd.put("reduce_money", "0");
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
						waterpd.put("remittance_name", Const.payjiqi[1]);
	   					waterpd.put("remittance_type","1" );
	   					waterpd.put("bank_money",  money );
					}
					waterpd.put("remittance_number",pd.getString("phone"));//支付人的支付账号
					waterpd.put("createtime",DateUtil.getTime());
					waterpd.put("over_time",DateUtil.getTime());
	  				waterpd.put("jiaoyi_type","0");
					waterpd.put("payee_number",Const.jiuyunumber);
					waterpd.put("jiaoyi_id",waterrecord_id );
					waterpd.put("order_tn", waterrecord_id);
					waterpd.put("province_name", pd.getString("province_name"));
					waterpd.put("city_name", pd.getString("city_name"));
					waterpd.put("area_name", pd.getString("area_name"));
					ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
					waterpd=null;
					//System.out.println("=======================更新服务商金额以及状态");
 	            	pd.put("earnest_status", "1");
		            sp_fileService.edit(pd);
	            	//System.out.println("服务商保证金结束=============================");
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
		
		
	/**
	 * 新增退还保证金的数据
	 * 魏汉文20160614
	 */
	@RequestMapping(value="/editEarnestStatus")
	public ModelAndView editEarnestStatus() throws Exception{
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
 		try {
					pd = this.getPageData();
					String sp_file_id=pd.getString("sp_file_id");
					String money=pd.getString("money");
 					PageData e1=sp_fileService.findById(pd);//详情
 					//新增退还保证金的数据
					String waterrecord_id=BaseController.getTXUID(sp_file_id);
					PageData waterpd=new PageData();
    				waterpd.put("pay_status","0");
    	   			waterpd.put("waterrecord_id",waterrecord_id);
   					waterpd.put("user_id", sp_file_id);
   					waterpd.put("user_type", "3");
    				waterpd.put("withdraw_rate","0");
   					waterpd.put("money_type","5");
   	 				waterpd.put("money", "-"+money);
   	 				waterpd.put("reduce_money", "0");
   					waterpd.put("arrivalmoney", "-"+ money);
   					waterpd.put("nowuser_money", pd.getString("nowmoney"));
   					waterpd.put("application_channel","6" );
   					waterpd.put("remittance_name",pd.getString("account_name")+" "+pd.getString("withdraw_username"));
   					waterpd.put("remittance_type","1" );
   					waterpd.put("bank_money",  "-"+money );
   					waterpd.put("remittance_number",pd.getString("withdraw_number"));//支付人的支付账号
    				waterpd.put("createtime",DateUtil.getTime());
   					waterpd.put("over_time",DateUtil.getTime());
   	  				waterpd.put("jiaoyi_type","0");
   					waterpd.put("payee_number",Const.jiuyunumber);
   					waterpd.put("jiaoyi_id",waterrecord_id );
    	 			waterpd.put("order_tn", waterrecord_id );
   					waterpd.put("province_name", e1.getString("province_name"));
   					waterpd.put("city_name", e1.getString("city_name"));
   					waterpd.put("area_name", e1.getString("area_name"));
    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
    				waterpd=null;
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
   		mv.setViewName("redirect:goEdit.do?sp_file_id="+pd.getString("sp_file_id")+"&currentPage="+pd.getString("currentPage"));
		return mv;
	}
	
	
//	
//	@Resource(name="sp_file_monthlyService")
//	private Sp_file_monthlyService sp_file_monthlyService;
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
//		//logBefore(logger, "批量删除Sp_file");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				sp_fileService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
 
 
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("3"));
		}
 	}
	/* ===============================权限================================== */
	
	  /**
	 	 * 
	 	 */
		@RequestMapping(value="/gg")
		@ResponseBody
		public Object gg(HttpServletRequest request) throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			DecimalFormat    df   = new DecimalFormat("######0.00"); 
	  		String result="1";
			String message="充值确认中";
			// type代表支付方式
			PageData pd=new PageData();
			pd=this.getPageData();
			try{ }catch(Exception e){
				result="0";
				message="系统异常";
				map.put("data", e.toString());
				logger.error(e.toString(), e);
			}
			map.put("result", result);
			map.put("message", message);
	    	return map;
		}
	
}
