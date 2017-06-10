package com.tianer.controller.zhihui.xitong;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.system.Menu;
import com.tianer.entity.Page;
import com.tianer.util.AppUtil;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.service.business.menu_role.Menu_roleService;

/** 
 * 类名称：Menu_roleController
 * 创建人：刘耀耀
 * 创建时间：2016-06-15
 */
@Controller
@RequestMapping(value="/zhihui_menu_role")
public class ZhihuiMenu_roleController extends BaseController {
	
	@Resource(name="menu_roleService")
	private Menu_roleService menu_roleService;
	
	/**
	 * 删除角色
	 * 刘耀耀
	 * 2016.06.15
	 */
	@RequestMapping(value="/delete")
	public ModelAndView delete() throws Exception{
		ModelAndView mv = this.getModelAndView();
//		logBefore(logger, "删除Menu_role");
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String currentPage=pd.getString("currentPage");
			if(pd.getString("menu_role_id") != null && !pd.getString("menu_role_id").equals("")){
				menu_roleService.delete(pd);
			}
	 		mv.setViewName("redirect:list.do?currentPage="+currentPage);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		return mv;
	}
	
	/**
	 * 判断是否有重复角色名称
	 */
	@RequestMapping(value="/isname")
	@ResponseBody
	public Object isname(){
//		logBefore(logger, "判断是否有重复角色名称");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			pd = menu_roleService.findByName(pd);
			if(pd != null && !pd.equals("")){
				map.put("result", "01");
			}
 		} catch(Exception e){
 			map.put("result", "00");
			logger.error(e.toString(), e);
		}
		return map;
	}
	
	/**
	 * 修改
	 * 刘耀耀
	 * 2016.06.15
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
//		logBefore(logger, "修改Menu_role");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String currentPage=pd.getString("currentPage");
			menu_roleService.edit(pd);
			mv.setViewName("redirect:list.do?currentPage="+currentPage);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		return mv;
	}
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.15
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
//		logBefore(logger, "列表Menu_role");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
			
			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
			String currentPage=pd.getString("currentPage");
			pd.put("currentPage", currentPage);
			page.setPd(pd);
			List<PageData>	varList = menu_roleService.list(page);	//列出Menu_role列表
			this.getHC(); //调用权限
			mv.setViewName("zhihui/xitong/xitong1");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增角色页面
	*魏汉文0714
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
//		logBefore(logger, " 去新增角色页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//获取所有最高级菜单
			List<PageData> listAllParentMenu=menu_roleService.listAllParentMenu(pd);
   			//获取所有基本菜单
			List<PageData> listAllMenu=menu_roleService.listAllMenu(pd);
			for(PageData e : listAllParentMenu){
					List<PageData> sonList=new ArrayList<PageData>();
					for(PageData e1: listAllMenu){
						e1.put("look", "0");
						e1.put("save", "0");
						e1.put("del", "0");
						e1.put("edit", "0");
						 if(e1.getString("menu_parent_name").equals(e.getString("menu_parent_name"))){
							 sonList.add(e1);
						 }
					}
					e.put("sonList", sonList);
			}
			mv.setViewName("zhihui/xitong/xitong2");
			mv.addObject("msg", "save");
			mv.addObject("allList", listAllParentMenu);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
 	
	/**
	 * 新增 角色
	 * 魏0714
	 */
	@RequestMapping(value="/saveRole")
	@ResponseBody
	public Object saveRole() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		//返给页面的数据
		List<PageData> listAll=new ArrayList<PageData>();
		String menu_role_id="";
		String result = "1";
		String message="添加成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
//			System.out.println(pd);
			if(pd.getString("menu_role_id") == null || pd.getString("menu_role_id").equals("")){
				 menu_role_id=BaseController.getTimeID();
				 String role_type=pd.getString("role_type");
				 pd.put("role_type", role_type);
				 pd.put("menu_role_id", menu_role_id);
				//获取所有基本菜单
					List<PageData> listAllMenu=menu_roleService.listAllMenu(pd);
					for(PageData e1 : listAllMenu){
						PageData e=new PageData();
						String menu_id=e1.getString("menu_id");
						e.put("menu_role_id", menu_role_id);
						e.put("menu_id",menu_id );
						e.put("save", pd.getString(menu_id+"save"));
						e.put("del", pd.getString(menu_id+"del"));
						e.put("edit", pd.getString(menu_id+"edit"));
						e.put("look", pd.getString(menu_id+"look"));
						menu_roleService.saveQx(e);//新增当前角色的权限
					}
		 			//新增角色
					menu_roleService.saveRole(pd);
			}else{
				menu_role_id=pd.getString("menu_role_id");
 				menu_roleService.edit(pd);
  				//获取所有基本菜单
				List<PageData> listAllMenu=menu_roleService.listAllMenu(pd);
				for(PageData e1 : listAllMenu){
					PageData e=new PageData();
					String menu_id=e1.getString("menu_id");
//					System.out.println(menu_id);
					e.put("menu_role_id", menu_role_id);
					e.put("menu_id",menu_id );
					e.put("save", pd.getString(menu_id+"save"));
					e.put("del", pd.getString(menu_id+"del"));
					e.put("edit", pd.getString(menu_id+"edit"));
					e.put("look", pd.getString(menu_id+"look"));
					menu_roleService.updateQx(e);//新增当前角色的权限
				}
			}
  			//获取所有最高级菜单
			List<PageData> listAllParentMenu=menu_roleService.listAllParentMenu(pd);
			for(PageData e : listAllParentMenu){
					List<PageData> sortAll=new ArrayList<PageData>();
 					List<PageData> listAllQx=menu_roleService.listAllQx(pd);//获取当前角色的权限
					boolean flag=false;
					for(PageData e1 : listAllQx){
						if(e.getString("menu_parent_name").equals(e1.getString("menu_parent_name"))){
							if(e1.getString("look").equals("1")){
									flag=true;
									sortAll.add(e1);
							} 
						}
					}
					if(flag){
						e.put("sortAll", sortAll);
						listAll.add(e);
 					}
  			}
			
		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			logger.error(e.toString());
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", listAll);
 		return AppUtil.returnObject(pd, map);
	}
	

	/**
	 *删除权限
	 * 魏0714
	 */
	@RequestMapping(value="/delQx")
	@ResponseBody
	public Object delQx() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="添加成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String qx_id=pd.getString("qx_id");
			if(qx_id != null && !qx_id.equals("")){
				menu_roleService.delQx(pd);
			}
 		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
 		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 去修改页面
	 * 魏汉文0714
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
//		logBefore(logger, "去修改页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd=this.getPageData();//获取到menu_role_id角色ID
			pd=menu_roleService.findById(pd);
			String currentPage=pd.getString("currentPage");
			//获取所有最高级菜单
			List<PageData> listAllParentMenu=menu_roleService.listAllParentMenu(pd);
   			//获取所有基本菜单
			List<PageData> listAllMenu=menu_roleService.listAllQx(pd);
			for(PageData e : listAllParentMenu){
					List<PageData> sonList=new ArrayList<PageData>();
					for(PageData e1: listAllMenu){
						String menu_name=e1.getString("menu_name");
 						 if(e1.getString("menu_parent_name").equals(e.getString("menu_parent_name"))){
							 sonList.add(e1);
						 }
					}
					e.put("sonList", sonList);
			}
  			mv.setViewName("zhihui/xitong/xitong2");
			mv.addObject("msg", "edit");
			mv.addObject("currentPage", currentPage);
			mv.addObject("allList", listAllParentMenu);
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
			session.setAttribute("qx", map.get("23"));
		}
		
	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
