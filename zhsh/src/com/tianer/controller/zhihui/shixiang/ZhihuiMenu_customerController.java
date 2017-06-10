package com.tianer.controller.zhihui.shixiang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/** 
 * 类名称：ZhihuiMenu_customerController
 * 创建人：刘耀耀
 * 创建时间：2016-06-16
 */
@Controller
@RequestMapping(value="/zhihui_menu_customer")
public class ZhihuiMenu_customerController extends BaseController {
	
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.16
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Menu_qx");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
			page.setPd(pd);
			mv.setViewName("zhihui/shixiang/shixiang8");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
