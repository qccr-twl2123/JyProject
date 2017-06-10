package com.tianer.controller.zhihui.shixiang;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.system.Menu;
import com.tianer.entity.Page;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.service.business.processing_issues.Processing_issuesService;

/*
 * 类名称：Processing_issuesController
 * 创建人：刘耀耀（未使用）
 * 创建时间：2016-06-13
 */
@Controller
@RequestMapping(value="/zhihui_processing_issues")
public class ZhihuiProcessing_issuesController extends BaseController {
	
	@Resource(name="processing_issuesService")
	private Processing_issuesService processing_issuesService;
 
	
 
	
	/**
	 * 修改
	 * 刘耀耀
	 * 2016.06.13
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Processing_issues");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		processing_issuesService.edit(pd);
		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.13
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Processing_issues");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
			
			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
			page.setPd(pd);
			List<PageData>	varList = processing_issuesService.list(page);	//列出Processing_issues列表
			this.getHC(); //调用权限
			mv.setViewName("zhihui/shixiang/shixiang1");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
  
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);
		mv.addObject(Const.SESSION_QX,map);	//按钮权限
		List<Menu> menuList = (List)session.getAttribute(Const.SESSION_menuList);
		mv.addObject(Const.SESSION_menuList, menuList);//菜单权限
	}
	/* ===============================权限================================== */

}
