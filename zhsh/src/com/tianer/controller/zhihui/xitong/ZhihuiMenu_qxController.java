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
import com.tianer.service.business.menu_qx.Menu_qxService;
import com.tianer.service.business.menu_role.Menu_roleService;
import com.tianer.service.business.operator_file.Operator_fileService;

/** 
 * 类名称：Menu_qxController
 * 创建人：刘耀耀
 * 创建时间：2016-06-15
 */
@Controller
@RequestMapping(value="/zhihui_menu_qx")
public class ZhihuiMenu_qxController extends BaseController {
	
	@Resource(name="menu_qxService")
	private Menu_qxService menu_qxService;
	
	/*
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
//		logBefore(logger, "新增Menu_qx");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("menu_role_id", BaseController.getTimeID());	//主键
			menu_qxService.save(pd);
			mv.addObject("msg","success");
		} catch (Exception e) {
			// TODO: handle exception
		}
 		mv.setViewName("save_result");
		return mv;
	}
	
	/*
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
//		logBefore(logger, "删除Menu_qx");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			menu_qxService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
//		logBefore(logger, "批量删除Menu_qx");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				menu_qxService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
//		logBefore(logger, "导出Menu_qx到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("读权限状态");	//1
			titles.add("添加权限状态");	//2
			titles.add("删除权限状态");	//3
			titles.add("修改权限状态");	//4
			titles.add("创建时间");	//5
			titles.add("修改时间");	//6
			dataMap.put("titles", titles);
			List<PageData> varOList = menu_qxService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("QX_READ_STATUS"));	//1
				vpd.put("var2", varOList.get(i).getString("QX_ADD_STATUS"));	//2
				vpd.put("var3", varOList.get(i).getString("QX_DELETE_STATUS"));	//3
				vpd.put("var4", varOList.get(i).getString("QX_EDIT_STATUS"));	//4
				vpd.put("var5", varOList.get(i).getString("CREATEDATE"));	//5
				vpd.put("var6", varOList.get(i).getString("UPDATEDATE"));	//6
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
//		logBefore(logger, "修改Menu_qx");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			operator_fileService.edit(pd);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	
	@Resource(name="operator_fileService")
	private Operator_fileService operator_fileService;
	
	/**
	 * 操作员的角色列表
	 * 魏汉文
	 * 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
//		logBefore(logger, "操作员的角色列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			mv.addObject("pd", pd);
			page.setPd(pd);
			List<PageData> varList=operator_fileService.opRolelistPage(page);
			mv.addObject("varList", varList);
			varList=null;
 			//获取所有角色
			List<PageData>	roleList = menu_roleService.listAll(pd);	//列出Menu_role列表
			mv.addObject("roleList", roleList);
			roleList=null;
			this.getHC(); //调用权限
 			mv.setViewName("zhihui/xitong/xitong3");
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@Resource(name="menu_roleService")
	private Menu_roleService menu_roleService;
	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
//		logBefore(logger, "去修改Menu_qx页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//获取所有角色
			List<PageData>	roleList = menu_roleService.listAll(pd);	//列出Menu_role列表
			mv.addObject("roleList", roleList);
			pd = operator_fileService.findById(pd);
			mv.setViewName("zhihui/xitong/xitong4");
			mv.addObject("msg", "edit");
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
			session.setAttribute("qx", map.get("24"));
		}
 	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	/*
	 * 新增菜单权限   zhihui_menu_qx/addcaidan.do
	 */
	@RequestMapping(value="/addcaidan")
	@ResponseBody
	public Object addcaidan() throws Exception{
//		logBefore(logger, "新增菜单以及权限");
 		PageData pd = new PageData();
 		String message="ok";
		try {
			String menu_name="商家经营分析报表";
			String menu_parent_name="报表";
			pd.put("menu_id", "56");
 			pd.put("menu_name", menu_name);
			pd.put("menu_parent_name", menu_parent_name);
			pd.put("menu_parent_id", "5");
			pd.put("status", "1");
			menu_roleService.saveMenu(pd);
 		} catch (Exception e) {
			// TODO: handle exception
 			message=e.toString();
		}
		return message;
 	}
	/* 
	 * 新增 权限 zhihui_menu_qx/addQuanxian.do?menu_id=
	 */
	@RequestMapping(value="/addQuanxian")
	@ResponseBody
	public Object addQuanxian(String menu_id) throws Exception{
//		logBefore(logger, "权限");
 		PageData pd = new PageData();
 		String message="ok";
		try {
 			//获取所有的权限
			List<PageData> qxList=menu_roleService.listAllRole(pd);
			for (PageData e : qxList) {
				e.put("menu_id", menu_id);
				if(e.getString("menu_role_id").equals("99")){
					e.put("save", "1");
					e.put("del", "1");
					e.put("edit", "1");
					e.put("look", "1");
				}else{
					e.put("save", "0");
					e.put("del", "0");
					e.put("edit", "0");
					e.put("look", "0");
				}
				menu_roleService.saveQx(e);
			}
		} catch (Exception e) {
			// TODO: handle exception
 			message=e.toString();
		}
		return message;
 	}
	 
	
	
}
