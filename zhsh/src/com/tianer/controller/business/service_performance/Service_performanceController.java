//package com.tianer.controller.business.service_performance;
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
//import com.tianer.service.business.service_performance.Service_performanceService;
//
///** 
// * 类名称：Service_performanceController
// * 创建人：cyr
// * 创建时间：2016-06-07
// */
//@Controller
//@RequestMapping(value="/service_performance")
//public class Service_performanceController extends BaseController {
//	
//	@Resource(name="service_performanceService")
//	private Service_performanceService service_performanceService;
//	
//	/**
//	 * 新增
//	 */
//	@RequestMapping(value="/save")
//	public ModelAndView save() throws Exception{
//		logBefore(logger, "新增Service_performance");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd.put("SERVICE_PERFORMANCE_ID", this.get32UUID());	//主键
//		service_performanceService.save(pd);
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
//		logBefore(logger, "删除Service_performance");
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			service_performanceService.delete(pd);
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
//		logBefore(logger, "修改Service_performance");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		service_performanceService.edit(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		return mv;
//	}
//	
//	/**
//	 * 列表
//	 */
//	@RequestMapping(value="/list")
//	public ModelAndView list(Page page){
//		logBefore(logger, "列表Service_performance");
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
//			page.setPd(pd);
//			List<PageData>	varList = service_performanceService.list(page);	//列出Service_performance列表
//			this.getHC(); //调用权限
//			mv.setViewName("business/service_performance/service_performance_list");
//			mv.addObject("varList", varList);
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
//		logBefore(logger, "去新增Service_performance页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			mv.setViewName("business/service_performance/service_performance_edit");
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
//		logBefore(logger, "去修改Service_performance页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			pd = service_performanceService.findById(pd);	//根据ID读取
//			mv.setViewName("business/service_performance/service_performance_edit");
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
//		logBefore(logger, "批量删除Service_performance");
//		PageData pd = new PageData();		
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			pd = this.getPageData();
//			List<PageData> pdList = new ArrayList<PageData>();
//			String DATA_IDS = pd.getString("DATA_IDS");
//			if(null != DATA_IDS && !"".equals(DATA_IDS)){
//				String ArrayDATA_IDS[] = DATA_IDS.split(",");
//				service_performanceService.deleteAll(ArrayDATA_IDS);
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
//		logBefore(logger, "导出Service_performance到excel");
//		ModelAndView mv = new ModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			List<String> titles = new ArrayList<String>();
//			titles.add("时间");	//1
//			titles.add("收益对象");	//2
//			titles.add("金额");	//3
//			titles.add("支付时间");	//4
//			dataMap.put("titles", titles);
//			List<PageData> varOList = service_performanceService.listAll(pd);
//			List<PageData> varList = new ArrayList<PageData>();
//			for(int i=0;i<varOList.size();i++){
//				PageData vpd = new PageData();
//				vpd.put("var1", varOList.get(i).getString("TIME"));	//1
//				vpd.put("var2", varOList.get(i).getString("PROFIT"));	//2
//				vpd.put("var3", varOList.get(i).getString("MONEY"));	//3
//				vpd.put("var4", varOList.get(i).getString("PAY_TIME"));	//4
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
