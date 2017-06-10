package com.tianer.controller.zhihui.zhifu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.util.DateUtil;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.StringUtil;
import com.tianer.service.business.member.MemberService;
import com.tianer.service.business.operator_file.Operator_fileService;
 import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.store.StoreService;
 
/** 
 * 
* 类名称：zhihuiCount_AllMoney   
* 类描述：   支付统计
* 创建人：魏汉文  
* 创建时间：2016年6月12日 下午4:15:39
 */
@Controller
@RequestMapping(value="/zhihuicount_allmoney")
public class ZhihuiCount_AllMoney extends BaseController {
 
 
	
	@Resource(name="pcdService")
	private PcdService pcdService;

	
	@Resource(name="storeService")
	private StoreService storeService;
	
	@Resource(name="memberService")
	private MemberService memberService;
	
	@Resource(name="operator_fileService")
	private Operator_fileService operator_fileService;
	
	/**
	 * 财务统计列表
	 * 魏汉文20160612
	 */
	@RequestMapping(value="/countList")
	public ModelAndView list(Page page){
		//logBefore(logger, "列表Pay_history");
		ModelAndView mv = this.getModelAndView();
		String month=DateUtil.getMonth();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			//文本框搜索
			String content = pd.getString("content");
 			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
			//省
			String province_name = pd.getString("province_name");
			if(null != province_name && !"".equals(province_name)){
				province_name = province_name.trim();
				pd.put("province_name", province_name);
			}
			//市
			String city_name = pd.getString("city_name");
			if(null != city_name && !"".equals(city_name)){
				pd.put("city_name", city_name);
			}
			//区
			String area_name = pd.getString("area_name");
			if(null != area_name && !"".equals(area_name)){
				pd.put("area_name", area_name);
			}
			//用户类型
			String user_type = pd.getString("user_type");
			if(null != user_type && !"".equals(user_type)){
				pd.put("user_type", user_type);
			}
 			//开始月份
			String startmonth = pd.getString("startmonth");
 			if(null != startmonth && !"".equals(startmonth)){
 				pd.put("startmonth", startmonth);
 			}else{
				//获取当前月份
  				pd.put("startmonth", month);
			}
 			pd.put("checked", "1");
 			page.setPd(pd);
 			//汇款总费用
 			List<PageData>	xfList = ServiceHelper.getWaterRecordService().countAllMoney(page);	//总
 			List<PageData>	onexfList = ServiceHelper.getWaterRecordService().countAllMoneyByOne(page);	//个人
  			String day="";
  			for(int i=1 ; i<32;i++ ){
	  				if(i>=10){
	  					day=month+"-"+i;
	  				}else{
	  					day=month+"-0"+i;
	  				}
	  				String number=StringUtil.getNumber(i);
 	  				//获取当日的总汇款
 	  				boolean flag1=false;
   					for(int j=0 ;j<xfList.size() ; j++){
  								PageData e=new PageData();
  								e=xfList.get(j);
  								String paytime=e.get("over_time").toString();
  								if(e.getString("money") != null && !e.getString("money").trim().equals("")){
  									double money=Double.parseDouble(e.getString("money"));
  	  		  		 				if(paytime.contains(day)){
  	  		  		 						flag1=true;
  	  		  		 					    pd.put("all"+number+"hk", money);//当日的总汇款
  	  		  		 				}
  								}
   		  		 				e=null;
  					}
   					if(!flag1){
   						pd.put("all"+number+"hk", 0);//当日的总汇款
   					}
   					boolean flag2=false;
  				//获取当日的个人最高总汇款
   					for(int j=0 ;j<onexfList.size() ; j++){
							PageData e=new PageData();
							e=onexfList.get(j);
							String paytime=e.get("over_time").toString();
							String id=e.getString("user_id");
							String type=e.getString("user_type");
	  		 				double money=Double.parseDouble(e.get("money")+"");
	  		 				if(paytime.contains(day)){
	  		 							flag2=true;
	  		 							PageData _pd=new PageData();
			  		 					if(type.equals("1")){//商家
				  		 						pd.put("store_id", id);
				  		 						_pd=storeService.findById(pd);
				  		 						if(_pd != null ){
				  		 							pd.put(number+"hkname", _pd.getString("store_name")); 
				  		 						}else{
				  		 							pd.put(number+"hkname", ""); 
				  		 						}
 			  		 					}else if(type.equals("2")){//会员
				  		 						pd.put("member_id", id);
				  		 						_pd=memberService.findById(pd);
				  		 						if(_pd != null ){
				  		 							pd.put(number+"hkname", _pd.getString("name")+""); 
				  		 						}else{
				  		 							pd.put(number+"hkname", ""); 
				  		 						}
 			  		 					}else if(type.equals("5")){
				  		 						pd.put("operator_file_id", id);
				  		 						_pd=operator_fileService.findById(pd);
				  		 						if(_pd != null ){
				  		 							pd.put(number+"hkname", _pd.getString("operator_name")+""); 
				  		 						}else{
				  		 							pd.put(number+"hkname", ""); 
				  		 						}
 			  		 					}else if(type.equals("3")){
 			  		 						pd.put("sp_file_id", id);
 			  		 						_pd=ServiceHelper.getSp_fileService().findById(pd);
 			  		 						if(_pd != null ){
 			  		 							pd.put(number+"txname", _pd.getString("team_name")); 
 			  		 						}else{
 			  		 							pd.put(number+"txname", ""); 
 			  		 						}
 		 	  		 					}else if(type.equals("4")){
 			  		 						pd.put("clerk_file_id", id);
 			  		 						_pd=ServiceHelper.getClerk_fileService().findById(pd);
 			  		 						if(_pd != null ){
 			  		 							pd.put(number+"txname", _pd.getString("clerk_name")); 
 			  		 						}else{
 			  		 							pd.put(number+"txname", ""); 
 			  		 						}
 		 	  		 					}
			  		 					pd.put(number+"hk", money);//当日的总汇款
			  		 					_pd=null;
 	  		 				}
	  		 				e=null;
  					}
   					if(!flag2){
   							pd.put(number+"hk", 0);//当日的总汇款
		 					pd.put(number+"hkname", "--"); 
   					}
  					
  			}
  		   //列出提现历史列表
   			pd.remove("checked");
  			pd.put("checked", "2");
 			page.setPd(pd);
  			List<PageData>	txList = ServiceHelper.getWaterRecordService().countAllMoney(page);	//总
  			List<PageData>	onetxList = ServiceHelper.getWaterRecordService().countAllMoneyByOne(page);	//个人
  			day="";
  			for(int i=1 ; i<32;i++ ){
  				String number=StringUtil.getNumber(i);
  				if(i>=10){
  					day=month+"-"+i;
  				}else{
  					day=month+"-0"+i;
  				}
	  				//获取当日的总提现
  					boolean flag1=false;
 					for(int j=0 ;j<txList.size() ; j++){
								PageData e=new PageData();
								e=txList.get(j);
								String paytime=e.get("over_time").toString();
		  		 				double money=Double.parseDouble(e.getString("money"));
		  		 				if(paytime.contains(day)){
		  		 					flag1=true;
		  		 					pd.put("all"+number+"tx", money);//当日的提现
		  		 				}
		  		 				e=null;
					}
 					if(!flag1){
 						pd.put("all"+number+"tx", 0);//当日的提现
					}
 				//获取当日的个人最高提现
 					boolean flag2=false;
					for(int j=0 ;j<onetxList.size() ; j++){
						PageData e=new PageData();
						e=onetxList.get(j);
						String paytime=e.get("over_time").toString();
						String type=e.getString("user_type");
						String id=e.getString("user_id");
  		 				double money=Double.parseDouble(e.get("money")+"");
  		 				if(paytime.contains(day)){
  		 						flag2=true;
  		 						PageData _pd=new PageData();
	  		 					if(type.equals("1")){//商家
	  		 						pd.put("store_id", id);
	  		 						_pd=storeService.findById(pd);
	  		 						if(_pd != null ){
	  		 							pd.put(number+"txname", _pd.getString("store_name")); 
	  		 						}else{
	  		 							pd.put(number+"txname", ""); 
	  		 						}
 	  		 					}else if(type.equals("2")){//会员
	  		 						pd.put("member_id", id);
	  		 						_pd=memberService.findById(pd);
	  		 						if(_pd != null ){
	  		 							pd.put(number+"txname", _pd.getString("name")); 
	  		 						}else{
	  		 							pd.put(number+"txname", ""); 
	  		 						}
 	  		 					}else if(type.equals("5")){
	  		 						pd.put("operator_file_id", id);
	  		 						_pd=operator_fileService.findById(pd);
	  		 						if(_pd != null ){
	  		 							pd.put(number+"txname", _pd.getString("operator_name")); 
	  		 						}else{
	  		 							pd.put(number+"txname", ""); 
	  		 						}
 	  		 					}else if(type.equals("3")){
	  		 						pd.put("sp_file_id", id);
	  		 						_pd=ServiceHelper.getSp_fileService().findById(pd);
	  		 						if(_pd != null ){
	  		 							pd.put(number+"txname", _pd.getString("team_name")); 
	  		 						}else{
	  		 							pd.put(number+"txname", ""); 
	  		 						}
 	  		 					}else if(type.equals("4")){
	  		 						pd.put("clerk_file_id", id);
	  		 						_pd=ServiceHelper.getClerk_fileService().findById(pd);
	  		 						if(_pd != null ){
	  		 							pd.put(number+"txname", _pd.getString("clerk_name")); 
	  		 						}else{
	  		 							pd.put(number+"txname", ""); 
	  		 						}
 	  		 					}
	  		 					pd.put(number+"tx", money);//当日的总提现
	  		 					_pd=null;
  		 				}
  		 				e=null;
					}
					if(!flag2){
							pd.put(number+"tx", 0);//当日的总提现
	 						pd.put(number+"txname", ""); 
					}
 			}
  			//System.out.println(pd);
  			this.getHC();
			mv.setViewName("zhihui/zhifu/zhifu4");
   			mv.addObject("pd", pd);
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}

	
	/**
	 * 汇款体现统计
	 *魏汉文20160617
	 */
	@RequestMapping(value="/countAllMoney")
 	public void countAllMoney() throws Exception{
		//logBefore(logger, "新增App_advert");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		pd = this.getPageData();
		
 		map.put("result", result);
  	}
	
	/**
	 * 每月数据新增
	 *魏汉文20160617
	 */
	@RequestMapping(value="/qingKong")
 	public void qingKong() throws Exception{
		//logBefore(logger, "新增App_advert");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		pd = this.getPageData();
		
 		map.put("result", result);
  	}
	
	
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("14"));
		}
 	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}

