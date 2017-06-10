package com.tianer.controller.memberapp.member_wealthhistory;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.util.AppUtil;
import com.tianer.util.PageData;

/** 
 * 类名称：Member_wealthhistoryController
 * 创建人：刘耀耀
 * 创建时间：2016-06-24
 */
@Controller("appMember_wealthhistoryController")
@RequestMapping(value="/app_member_wealthhistory")
public class Member_wealthhistoryController extends BaseController {
	
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService member_wealthhistoryService;
	
	
	/**
	 * 积分列表
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(Page page){
//		logBefore(logger, "积分列表");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String nowpage=pd.getString("nowpage");
			if(nowpage==null || nowpage.equals("")){
				nowpage="1";
			}
  			pd.put("wealth_type", "1");//积分
  			page.setPd(pd);
  			page.setCurrentPage(Integer.parseInt(nowpage));
			List<PageData> varList=member_wealthhistoryService.datalistPage(page);
 			if(varList.size()==0){
 				map.put("data","");
 			}else{
 				map.put("data", varList);
 			}
		} catch(Exception e){
			result = "0";
			map.put("data", "");
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 智慧钱包列表
	 */
	@RequestMapping(value="/listPurse")
	@ResponseBody
	public Object listPurse(Page page){
//		logBefore(logger, "列表Member_wealthhistory");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String nowpage=pd.getString("nowpage");
			if(nowpage==null || nowpage.equals("")){
				nowpage="1";
			}
  			pd.put("wealth_type", "2");
  			page.setPd(pd);
  			page.setCurrentPage(Integer.parseInt(nowpage));
			List<PageData> varList=member_wealthhistoryService.datalistPage(page);
 			if(varList.size()==0){
				result = "0";
				message="暂无数据";
				map.put("data","");
 			}else{
				map.put("data", varList);
			}
		} catch(Exception e){
			result = "0";
			map.put("data", "");
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return AppUtil.returnObject(pd, map);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
