package com.tianer.controller.zhihui.yingxiao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.system.Menu;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.entity.Page;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.red_platform.Red_platformService;

/** 
 * 
* 类名称：Red_platformController   
* 类描述：   平台红包
* 创建人：魏汉文  
* 创建时间：2016年6月14日 下午3:55:38
 */
@Controller
@RequestMapping(value="/zhihuired_platform")
public class ZhihuiRed_platformController extends BaseController {
	
	@Resource(name="red_platformService")
	private Red_platformService red_platformService;
	
	/**
	 * 新增
	 * 魏汉文20160614
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Red_platform");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
 		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(zhlogin != null ){
				pd.put("operation_id", zhlogin.getLogin_id());
			}
	 		red_platformService.save(pd);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	
	/**
	 * 修改
	 * 魏汉文20160616
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Red_platform");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		 zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(zhlogin != null ){
				pd.put("review_id", zhlogin.getLogin_id());
			}
	 		red_platformService.edit(pd);
		} catch (Exception e) {
			// TODO: handle exception
		}
  		mv.setViewName("redirect:listNRreview.do");
		return mv;
	}
	
	/**
	 * 平台红包列表
	 * 魏汉文20160613
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Red_platform");
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
			List<PageData>	varList = red_platformService.list(page);	//列出Red_platform列表
			this.getHC(); //调用权限
			mv.setViewName("zhihui/yingxiao/yingxiao4");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	/**
	 * 去新增页面
	 * 魏汉文20160613
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Red_platform页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String red_platform_id=this.getTimeID();
			pd.put("red_platform_id", red_platform_id);//时间的唯一标示
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
 			//使用条件
			List<PageData>	conditionList = red_platformService.listAllUserCondition(pd);	//列出使用条件
			mv.addObject("conditionList", conditionList);
			mv.setViewName("zhihui/yingxiao/yingxiao5");
 			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	

	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("17"));
		}
 	}
	/* ===============================权限================================== */
	

	/**
	 * 未审核的红包列表
	 * 魏汉文20160616
	 */
	@RequestMapping(value="/listNRreview")
	public ModelAndView listNRreview(Page page){
		logBefore(logger, "列表Red_platform");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
 			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
 			pd.put("review_status", "0");
			page.setPd(pd);
			List<PageData>	varList = red_platformService.list(page);	//列出Red_platform列表
			this.getHCred(); //调用权限
			mv.setViewName("zhihui/shixiang/shixiang1");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/* ===============================权限================================== */
	public void getHCred(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("31"));
		}
 	}
	/* ===============================权限================================== */
	
}
