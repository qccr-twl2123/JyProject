package com.tianer.controller.storepc.store_marketingeffect;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.storepc.store_marketingeffect.Storepc_marketingeffectService;
import com.tianer.util.PageData;



/** 
 * 
* 类名称：Storepc_scorewayController   
* 类描述：   营销效果记录
* 创建人：邢江涛  
* 创建时间：2016年6月13日
 */
@Controller()
@RequestMapping(value="/storepc_marketingeffect")
public class Storepc_marketingeffectController extends BaseController{

	@Resource(name = "storepc_marketingeffectService")
	private   Storepc_marketingeffectService storepcMarketingeffectService;
	
	
	
	/**
	 * 营销效果分析列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = storepcMarketingeffectService.list(page);	//列出Store列表
 			mv.addObject("varList", varList);
 			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.setViewName("storepc/business_marketing18");
		return mv;
	}
	

	
	public static void main(String[] s){
		try {
			//listMarkesss("满100元减20元,满20元赠1元");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
