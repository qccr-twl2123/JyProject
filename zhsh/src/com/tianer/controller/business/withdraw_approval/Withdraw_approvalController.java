//package com.tianer.controller.business.withdraw_approval;
//
//import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tianer.controller.base.BaseController;
//import com.tianer.entity.system.Menu;
//import com.tianer.entity.Page;
//import com.tianer.util.AppUtil;
//import com.tianer.util.ObjectExcelView;
//import com.tianer.util.Const;
//import com.tianer.util.PageData;
//import com.tianer.service.business.pcd.PcdService;
//import com.tianer.service.business.withdraw_approval.Withdraw_approvalService;
//
///** 
// * 类名称：Withdraw_approvalController
// * 创建人：cyr
// * 创建时间：2016-05-26
// */
//@Controller
//@RequestMapping(value="/withdraw_approval")
//public class Withdraw_approvalController extends BaseController {
//	
//	@Resource(name="withdraw_approvalService")
//	private Withdraw_approvalService withdraw_approvalService;
//	@Resource(name="pcdService")
//	private PcdService pcdService;
//	
//	
//	/**
//	 * 新增
//	 */
//	@RequestMapping(value="/save")
//	public ModelAndView save() throws Exception{
//		logBefore(logger, "新增Withdraw_approval");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd.put("WITHDRAW_APPROVAL_ID", this.get32UUID());	//主键
//		withdraw_approvalService.save(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		return mv;
//	}
//	
//	/**
//	 * 删除
//	 */
//	@RequestMapping(value="/delete")
//	public void delete(PrintWriter out){
//		logBefore(logger, "删除Withdraw_approval");
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			withdraw_approvalService.delete(pd);
//			out.write("success");
//			out.close();
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		
//	}
//	
//	/**
//	 * 修改
//	 */
//	@RequestMapping(value="/edit")
//	public ModelAndView edit() throws Exception{
//		logBefore(logger, "修改Withdraw_approval");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		withdraw_approvalService.edit(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		return mv;
//	}
//	
//	/**
//	 * 列表
//	 * 刘耀耀
//	 * 2016.6.3
//	 */
//	@RequestMapping(value="/list")
//	public ModelAndView list(Page page){
//		logBefore(logger, "列表Withdraw_approval");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			String content = pd.getString("content");
//			
//			if(null != content && !"".equals(content)){
//				content = content.trim();
//				pd.put("content", content);
//			}
//			//省
//			String province_id = pd.getString("province_id");
//			if(null != province_id && !"".equals(province_id)){
//				province_id = province_id.trim();
//				pd.put("province_id", province_id);
//			}
//			//市
//			String city_id = pd.getString("city_id");
//			if(null != city_id && !"".equals(city_id)){
//				pd.put("city_id", city_id);
//			}
//			//区
//			String area_id = pd.getString("area_id");
//			if(null != area_id && !"".equals(area_id)){
//				pd.put("area_id", area_id);
//			}
//			//当前页
//			String currentPage = pd.getString("currentPage");
//			if(null != currentPage && !"".equals(currentPage)){
//				page.setCurrentPage(Integer.parseInt(currentPage));
//			}
//			page.setPd(pd);
//			List<PageData> provicelist=pcdService.listAll(pd);//省list
//			mv.addObject("provicelist", provicelist);
//			List<PageData>	varList = withdraw_approvalService.datalistPage(page);	//列出Withdraw_approval列表
//			List<PageData>	list = withdraw_approvalService.listAll(pd);	//列出Withdraw_approval列表查出时间去掉重复的
//			this.getHC(); //调用权限
//			mv.setViewName("zhihui/zhifu/zhifu2");
//			mv.addObject("varList", varList);
//			mv.addObject("list", list);
//			mv.addObject("pd", pd);
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	/**
//	 * 去新增页面
//	 */
//	@RequestMapping(value="/goAdd")
//	public ModelAndView goAdd(){
//		logBefore(logger, "去新增Withdraw_approval页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			mv.setViewName("business/withdraw_approval/withdraw_approval_edit");
//			mv.addObject("msg", "save");
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}						
//		return mv;
//	}	
//	
//	/**
//	 * 去修改页面
//	 */
//	@RequestMapping(value="/goEdit")
//	public ModelAndView goEdit(){
//		logBefore(logger, "去修改Withdraw_approval页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			pd = withdraw_approvalService.findById(pd);	//根据ID读取
//			mv.setViewName("business/withdraw_approval/withdraw_approval_edit");
//			mv.addObject("msg", "edit");
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}						
//		return mv;
//	}	
//	
//	/**
//	 * 批量删除
//	 */
//	@RequestMapping(value="/deleteAll")
//	@ResponseBody
//	public Object deleteAll() {
//		logBefore(logger, "批量删除Withdraw_approval");
//		PageData pd = new PageData();		
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			pd = this.getPageData();
//			List<PageData> pdList = new ArrayList<PageData>();
//			String DATA_IDS = pd.getString("DATA_IDS");
//			if(null != DATA_IDS && !"".equals(DATA_IDS)){
//				String ArrayDATA_IDS[] = DATA_IDS.split(",");
//				withdraw_approvalService.deleteAll(ArrayDATA_IDS);
//				pd.put("msg", "ok");
//			}else{
//				pd.put("msg", "no");
//			}
//			pdList.add(pd);
//			map.put("list", pdList);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		} finally {
//			logAfter(logger);
//		}
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	/*
//	 * 导出到excel
//	 * @return
//	 */
//	@RequestMapping(value="/excel")
//	public ModelAndView exportExcel(){
//		logBefore(logger, "导出Withdraw_approval到excel");
//		ModelAndView mv = new ModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			List<String> titles = new ArrayList<String>();
//			titles.add("用户编号");	//1
//			titles.add("用户类型ID");	//2
//			titles.add("用户手机账号");	//3
//			titles.add("款项金额");	//4
//			titles.add("提款账号");	//5
//			titles.add("提现方式");	//6
//			titles.add("提现状态");	//7
//			titles.add("备注");	//8
//			titles.add("申请时间");	//9
//			titles.add("操作人编号");	//10
//			dataMap.put("titles", titles);
//			List<PageData> varOList = withdraw_approvalService.listAll(pd);
//			List<PageData> varList = new ArrayList<PageData>();
//			for(int i=0;i<varOList.size();i++){
//				PageData vpd = new PageData();
//				vpd.put("var1", varOList.get(i).getString("USER_NUMBER"));	//1
//				vpd.put("var2", varOList.get(i).getString("USER_TYPE"));	//2
//				vpd.put("var3", varOList.get(i).getString("PHONE"));	//3
//				vpd.put("var4", varOList.get(i).getString("MONEY"));	//4
//				vpd.put("var5", varOList.get(i).getString("WITHDRAW_NUMBER"));	//5
//				vpd.put("var6", varOList.get(i).getString("WITHDRAW_TYPE"));	//6
//				vpd.put("var7", varOList.get(i).getString("WITHDRAW_STATUS"));	//7
//				vpd.put("var8", varOList.get(i).getString("REMARK"));	//8
//				vpd.put("var9", varOList.get(i).getString("APPLY_TIME"));	//9
//				vpd.put("var10", varOList.get(i).getString("OPERATOR_NUMBER"));	//10
//				varList.add(vpd);
//			}
//			dataMap.put("varList", varList);
//			ObjectExcelView erv = new ObjectExcelView();
//			mv = new ModelAndView(erv,dataMap);
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	/* ===============================权限================================== */
//	public void getHC(){
//		ModelAndView mv = this.getModelAndView();
//		HttpSession session = this.getRequest().getSession();
//		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);
//		mv.addObject(Const.SESSION_QX,map);	//按钮权限
//		List<Menu> menuList = (List)session.getAttribute(Const.SESSION_menuList);
//		mv.addObject(Const.SESSION_menuList, menuList);//菜单权限
//	}
//	/* ===============================权限================================== */
//	
//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
//	}
//}
