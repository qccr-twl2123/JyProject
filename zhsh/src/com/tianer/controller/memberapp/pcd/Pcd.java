package com.tianer.controller.memberapp.pcd;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppPcdService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.util.AppUtil;
import com.tianer.util.PageData;

/** 
 * 类名称：appPcdController
 * 创建人：刘耀耀
 * 创建时间：2016-06-16
 */
@Controller("appPcdController")
@RequestMapping(value="/app_pcd")
public class Pcd extends BaseController {
	
	@Resource(name="appPcdService")
	private AppPcdService pcdService;
	

	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.21
	 */
	@RequestMapping(value="/listCity")
	@ResponseBody
	public Object listcity(Page page){
		//logBefore(logger, "列表市");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			pd.put("name", pd.getString("name"));
			pd.put("name", "杭州市");//固定死的 完了要删除
			List<PageData>	varList = pcdService.listcity(pd);//列出Collect列表
			if(varList.size()==0){
				message="获取失败";
				map.put("data","");
			}
			map.put("data", varList);
		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.21
	 */
	@RequestMapping(value="/listArea")
	@ResponseBody
	public Object listarea(Page page){
		//logBefore(logger, "列表区");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = pcdService.listarea(pd);//列出Collect列表
			if(varList.size()==0){
				message="获取失败";
				map.put("data", "");
			}
			map.put("data", varList);
		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return AppUtil.returnObject(pd, map);
	}
	
	@Resource(name="appOrderService")
	private AppOrderService appOrderService;
	
	
 
    /**
	 *
	 *	修改过期红包状态(商家红报表，会员红包表，折扣表，营销类型表，营销记录表)
	 *魏0720
	 */
	@RequestMapping(value="/editTime")
	@ResponseBody
	public Object editTime(){
		//logBefore(logger, "修改商家/会员红包状态");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try{
			pcdService.edit(pd);
		} catch(Exception e){
			result="0";
			message="修改异常";
			logger.error(e.toString(), e);
		}
		map.put("data", "");
		map.put("result", result);
		map.put("message",message);
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
 	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService member_redpacketsService;
	
	
	   /**
		 *
		 *	发送的积分红包12小时过后退还至用户余额中
		 *魏0720
		 */
		@RequestMapping(value="/BackJfRed")
		@ResponseBody
		public Object BackJfRed(){
			//logBefore(logger, "发送的积分红包12小时过后退还至用户余额中");
			Map<String,Object> map = new HashMap<String,Object>();
			DecimalFormat    df   = new DecimalFormat("######0.00"); 
			String result = "1";
			String message="修改成功";
			PageData pd = new PageData();
			try{
				//查询所有已过期的积分红包
				List<PageData>passJfRedList=pcdService.allPassTimeJfRed(pd);
				for(PageData e : passJfRedList){
					String user_id=e.getString("user_id");
					String user_type=e.getString("user_type");
 					double overget_money=Double.parseDouble(e.getString("overget_money"));
					double redpackage_money=Double.parseDouble(e.getString("redpackage_money"));
					PageData e1=new PageData();
					if(user_type.equals("1")){//商家
							e1.put("store_id",user_id);
							e1.put("wealth_type", "1");
							e1=appStoreService.findWealthById(e1);
							double now_wealth=Double.parseDouble(e1.getString("now_wealth"));
  							//减少用户积分余额
							double n=now_wealth+(redpackage_money-overget_money);
							e1.put("now_wealth", df.format(n));
							appStoreService.editWealthById(e1);
							pcdService.updatePassTimeJfRed(e);
  					}else{//会员
							e1.put("member_id", user_id);
							e1=appMemberService.findById(e1);
							double now_integral=Double.parseDouble(e1.getString("now_integral"));
							double n=now_integral+(redpackage_money-overget_money);
							e1.put("now_integral", df.format(n));
							appMemberService.edit(e1);
							pcdService.updatePassTimeJfRed(e);
					}
 				}
 			} catch(Exception e){
				result="0";
				message="修改异常";
				logger.error(e.toString(), e);
			}
			map.put("data", "");
			map.put("result", result);
			map.put("message",message);
			return AppUtil.returnObject(pd, map);
		}
		
		@Resource(name="city_fileService")
		private City_fileService city_fileService;
		
		@Resource(name="sp_fileService")
		private Sp_fileService sp_fileService;
		
		@Resource(name="clerk_fileService")
		private Clerk_fileService clerk_fileService;
		
		  /**
		 *
		 *	月统计商家的收益
		 *魏0720
		 */
		@RequestMapping(value="/MonthCount")
		@ResponseBody
		public Object MonthCount(){
			//logBefore(logger, "月统计商家的收益");
			Map<String,Object> map = new HashMap<String,Object>();
			DecimalFormat    df   = new DecimalFormat("######0.00"); 
			String result = "1";
			String message="修改成功";
			PageData pd = new PageData();
			try{ 
					 Calendar c = Calendar.getInstance();
				     c.add(Calendar.MONTH, -1);
				     String prevmonth=new SimpleDateFormat("yyyy-MM").format(c.getTime());
	 		         //System.out.println("上个月是："+prevmonth);
	 		         pd.put("month", prevmonth);
	 		    	List<PageData>monthList=pcdService.listAllStoreByOrderForFY(pd);
	 		    	for(PageData e : monthList){
	 		    			String number=e.get("number").toString();
	 		    			pd=pcdService.getCityForName(e);
	 		    			if(pd != null ){
	 		    				String monthly_sales=pd.getString("monthly_sales");
		 		    			String send_money=pd.getString("send_money");
		 		    			if(Double.parseDouble(number) > Double.parseDouble(monthly_sales)){
		 		    				pd=sp_fileService.findById(e);
		 		    				pd.put("nowmoney", df.format(Double.parseDouble(pd.getString("nowmoney"))+Double.parseDouble(send_money)));
		 		    				sp_fileService.edit(pd);//修改服务商余额
		 		    			}
	 		    			}
 	 		    	}
			} catch(Exception e){
				result="0";
				message="修改异常";
				logger.error(e.toString(), e);
			}
			map.put("data", "");
			map.put("result", result);
			map.put("message",message);
			return AppUtil.returnObject(pd, map);
		}
		
		
		
		  /**
		 *
		 *	月统计商家的收益
		 *魏0720
		 */
		@RequestMapping(value="/oneTime")
		@ResponseBody
		public Object oneTime(){
			//logBefore(logger, "月统计商家的收益");
			Map<String,Object> map = new HashMap<String,Object>();
			DecimalFormat    df   = new DecimalFormat("######0.00"); 
			String result = "1";
			String message="修改成功";
			PageData pd = new PageData();
			try{ 
				//System.out.println("一分钟执行一次");
			} catch(Exception e){
				result="0";
				message="修改异常";
				logger.error(e.toString(), e);
			}
			map.put("data", "");
			map.put("result", result);
			map.put("message",message);
			return AppUtil.returnObject(pd, map);
		}


		
		public static void main(String[] str ){
			 Calendar c = Calendar.getInstance();
		     c.add(Calendar.MONTH, -1);
		     String prevmonth=new SimpleDateFormat("yyyy-MM").format(c.getTime());
 		      //System.out.println("上个月是："+prevmonth);
		}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
}
