package com.tianer.controller.zhihui.shixiang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.util.PageData;
import com.tianer.service.business.pcd.PcdService;

/** 
 * 类名称：ZhihuiMenu_recordController
 * 创建人：刘耀耀
 * 创建时间：2016-06-16
 */
@Controller
@RequestMapping(value="/zhihui_menu_record")
public class ZhihuiMenu_recordController extends BaseController {
	
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
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

			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
			String province_id = pd.getString("province_id");
			if(null != province_id && !"".equals(province_id)){
				province_id = province_id.trim();
				pd.put("province_id", province_id);
			}
			String city_id = pd.getString("city_id");
			if(null != city_id && !"".equals(city_id)){
				pd.put("city_id", city_id);
			}
			String area_id = pd.getString("area_id");
			if(null != area_id && !"".equals(area_id)){
				pd.put("area_id", area_id);
			}
			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
			}
			page.setPd(pd);
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			mv.setViewName("zhihui/shixiang/shixiang4");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去详情页面
	 * 魏汉文
	 */
	@RequestMapping(value="/goDetail")
	public ModelAndView goDetail(){
		logBefore(logger, "去详情页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("zhihui/shixiang/shixiang5");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
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
