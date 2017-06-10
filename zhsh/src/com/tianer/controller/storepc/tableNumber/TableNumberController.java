package com.tianer.controller.storepc.tableNumber;

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
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.service.storepc.store_way.Storepc_wayService;
import com.tianer.service.storepc.tableNumber.TablerNumberService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.PageData;

/** 
 * 
* 类名称：tableNumberController   
* 类描述：   消费/支付
* 创建人：邢江涛  
* 创建时间：2016年9月11日 
 */
@Controller("tableNumberController")
@RequestMapping(value = "/storepc_tableNumber")
public class TableNumberController extends BaseController{
	
	@Resource(name = "tablerNumberService")
	private TablerNumberService tablerNumberService;
	
	/**
	 * 桌号列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView mv = this.getModelAndView();
		//logBefore(logger, "列表");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData>	varList = tablerNumberService.listAll(pd);	//列出Store列表
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			String jichushezhi=pd.getString("jichushezhi");
  			if(jichushezhi != null && jichushezhi.equals("11111100000")){
  				mv.setViewName("/storepc/shezhi_6");
  			}else{
  				mv.setViewName("/storepc/business_base7");
  			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		return mv;
	}

	
	/**
	 * 删除桌号
	 */
	@RequestMapping(value="/delect")
	public ModelAndView delect(){
		ModelAndView mv = this.getModelAndView();
		//logBefore(logger, "delect");
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		PageData pd = new PageData();
		String store_id="";
		try{
			pd = this.getPageData();
			tablerNumberService.delete(pd);	//列出Store列表
			if(slogin.getStore_id() != null){
				store_id=slogin.getStore_id();
			}else{
				store_id=pd.getString("store_id");
			}
			if(store_id == null){
				store_id="";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("redirect:list.do?store_id="+store_id);
		return mv;
	}
	
	
	/**
	 * 修改桌号
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public Object update(){
		Map<String,Object> map = new HashMap<String,Object>();
		//logBefore(logger, "update");
		String result = "01";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData e = new PageData();
			e = tablerNumberService.findByName(pd);
			if(e == null){
				tablerNumberService.update(pd);	//列出Store列表
			}else {
				 result = "02";
			}
		} catch(Exception e){
			 result = "00";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 添加桌号
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(){
		//logBefore(logger, "save");
		String result = "01";
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
  		try {
 			pd = this.getPageData();
 			pd.put("table_id",BaseController.getTimeID());
 			PageData e = new PageData();
			e = tablerNumberService.findByName(pd);
			if(e == null){
				tablerNumberService.save(pd);	//列出Store列表
			}else {
				 result = "02";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
  		map.put("result", result);
		return map;
	}

}
