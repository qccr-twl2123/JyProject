package com.tianer.controller.memberapp.city_file;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.tianer.service.memberapp.AppCity_fileService;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/** 
 * 
* 类名称：City_fileController   
* 类描述：   城市档案
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:45:23
 */
@Controller("appCity_fileController")
@RequestMapping(value="/app_city_file")
public class City_fileController extends BaseController {
	
	@Resource(name="appCity_fileService")
	private AppCity_fileService appCity_fileService;
	
	
	/**
	 * 列表获取区
	 * 刘耀耀
	 * app_city_file/list.do
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Object listAllArea(Page page){
//		logBefore(logger, "列表获取区");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("open_status", "1");
 			List<PageData>	varList = appCity_fileService.listAllArea(pd);//列出City_file列表
			if(varList.size()==0){
				map.put("data", "");
				message="查询失败";
			}else{
				map.put("data", varList);
			}
			varList=null;
			pd=null;
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	/**
	 * 列表获取省
	 * 刘耀耀
	 * 2016.06.22
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public Object listAll(Page page){
//		logBefore(logger, "列表City_file");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
 			List<PageData>	varList = appCity_fileService.listAll(pd);//列出City_file列表
			if(varList.size()==0){
				map.put("data", "");
				message="查询失败";
			}else{
				map.put("data", varList);
			}
			varList=null;
			pd=null;
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		return map;
	}

	/**
	 * 列表获取省下市
	 * 刘耀耀
	 * 2016.06.22
	 */
	@RequestMapping(value="/listCity")
	@ResponseBody
	public Object listCity(Page page){
//		logBefore(logger, "列表City_file");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("open_status", "1");
 			List<PageData>	varList = appCity_fileService.listAllCity(pd);//列出City_file列表
			if(varList.size()==0){
				map.put("data", "");
				message="查询失败";
			}else{
				map.put("data", varList);
			}
			varList=null;
			pd=null;
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		return map;
	}
	
	/**
	 * 所趋所有开通服务的区域
	 */
	@RequestMapping(value="/listAllpcd")
	@ResponseBody
	public Object listAllpcd(){
//		logBefore(logger, "列表City_file");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("open_status", "1");
			List<PageData>	varList = appCity_fileService.listAll(pd);
			for(PageData e : varList){
				e.put("open_status", "1");
				List<PageData>	cityList = appCity_fileService.listAllCity(e);
 				for(PageData e1 : cityList){
 					e1.put("open_status", "1");
					List<PageData>	areaList = appCity_fileService.listAllArea(e1);
					e1.put("areaList", areaList);
					areaList=null;
 				}
 				e.put("cityList", cityList);
 				cityList=null;
			}
			if(varList.size()==0){
				map.put("data", "");
				message="查询失败";
			}else{
				map.put("data", varList);
			}
			varList=null;
			pd=null;
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		return map;
	}
	
	/**
	 * 所趋所有开通服务的区域
	 */
	@RequestMapping(value="/listAllpcd1")
	@ResponseBody
	public Object listAllpcd1(){
//		logBefore(logger, "列表City_file");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("open_status", "1");
			List<PageData>	varList = appCity_fileService.listAll(pd);
			for(PageData e : varList){
				e.put("open_status", "1");
				List<PageData>	cityList = appCity_fileService.listAllCity(e);
 				for(PageData e1 : cityList){
 					e1.put("open_status", "1");
					List<PageData>	areaList = appCity_fileService.listAllArea(e1);
					e1.put("areaList", areaList);
					areaList=null;
 				}
 				List<PageData> slist=new ArrayList<PageData>();
 				PageData ss=new PageData();
 				ss.put("city_name", "全部");
  				List<PageData> ssslist=new ArrayList<PageData>();
 				PageData sss=new PageData();
 				sss.put("area_name", "全部");
 				ssslist.add(sss);
 				ss.put("areaList", ssslist);
 				slist.add(ss);
  				for(PageData e1 : cityList){
  					slist.add(e1);
 				}
 				e.put("cityList", slist);
 				cityList=null;
			}
			if(varList.size()==0){
				map.put("data", "");
				message="查询失败";
			}else{
				map.put("data", varList);
			}
			varList=null;
			pd=null;
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		return map;
	}
	

	/**
	 *更新ios会员版本
	 */
	@RequestMapping(value="/updatememberapp")
	@ResponseBody
	public Object updatememberapp(){
//		logBefore(logger, "更新会员版本");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{ 
			pd.put("app_id", "1");//1-c端，2-b端
			pd=ServiceHelper.getAppPcdService().getappbanbenhao(pd);
 		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	

	/**
	 *更新iso商家版本
	 */
	@RequestMapping(value="/updatestoreapp")
	@ResponseBody
	public Object updatestoreapp(){
//		logBefore(logger, "更新商家版本");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{ 
			pd.put("app_id", "2");//1-c端，2-b端
			pd=ServiceHelper.getAppPcdService().getappbanbenhao(pd);
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	
	
	/**
	 *更新安卓会员版本
	 */
	@RequestMapping(value="/updateanzhuomemberapp")
	@ResponseBody
	public Object updateanzhuomemberapp(){
//		logBefore(logger, "更新会员版本");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{ 
			pd.put("app_id", "3");//1-c端，2-b端
//			pd=ServiceHelper.getAppPcdService().getappbanbenhao(pd);
 		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	

	/**
	 *更新安卓商家版本
	 */
	@RequestMapping(value="/updateanzhuostoreapp")
	@ResponseBody
	public Object updateanzhuostoreapp(){
//		logBefore(logger, "更新商家版本");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{ 
			pd.put("app_id", "4");//1-c端，2-b端
//			pd=ServiceHelper.getAppPcdService().getappbanbenhao(pd);
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	
 
	
	

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
