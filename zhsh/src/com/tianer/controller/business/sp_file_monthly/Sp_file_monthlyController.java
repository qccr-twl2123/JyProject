//package com.tianer.controller.business.sp_file_monthly;
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
//import com.tianer.entity.Page;
//import com.tianer.entity.system.Menu;
//import com.tianer.service.business.sp_file_monthly.Sp_file_monthlyService;
//import com.tianer.util.AppUtil;
//import com.tianer.util.Const;
//import com.tianer.util.ObjectExcelView;
//import com.tianer.util.PageData;
//
///** 
// * 
//* 类名称：Sp_file_monthlyController   
//* 类描述：服务商月度   
//* 创建人：魏汉文  
//* 创建时间：2016年5月25日 下午5:04:25
// */
//@Controller
//@RequestMapping(value="/sp_file_monthly")
//public class Sp_file_monthlyController extends BaseController {
//	
//	@Resource(name="sp_file_monthlyService")
//	private Sp_file_monthlyService sp_file_monthlyService;
//	
//	/**
//	 * 新增
//	 */
//	@RequestMapping(value="/save")
//	public ModelAndView save() throws Exception{
//		logBefore(logger, "新增Sp_file_monthly");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd.put("SP_FILE_MONTHLY_ID", this.get32UUID());	//主键
//		sp_file_monthlyService.save(pd);
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
//		logBefore(logger, "删除Sp_file_monthly");
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			sp_file_monthlyService.delete(pd);
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
//		logBefore(logger, "修改Sp_file_monthly");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		sp_file_monthlyService.edit(pd);
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
//		logBefore(logger, "列表Sp_file_monthly");
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
//			List<PageData>	varList = sp_file_monthlyService.list(page);	//列出Sp_file_monthly列表
//			this.getHC(); //调用权限
//			mv.setViewName("S/sp_file_monthly/sp_file_monthly_list");
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
//		logBefore(logger, "去新增Sp_file_monthly页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			mv.setViewName("S/sp_file_monthly/sp_file_monthly_edit");
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
//		logBefore(logger, "去修改Sp_file_monthly页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			pd = sp_file_monthlyService.findById(pd);	//根据ID读取
//			mv.setViewName("S/sp_file_monthly/sp_file_monthly_edit");
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
//		logBefore(logger, "批量删除Sp_file_monthly");
//		PageData pd = new PageData();		
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			pd = this.getPageData();
//			List<PageData> pdList = new ArrayList<PageData>();
//			String DATA_IDS = pd.getString("DATA_IDS");
//			if(null != DATA_IDS && !"".equals(DATA_IDS)){
//				String ArrayDATA_IDS[] = DATA_IDS.split(",");
//				sp_file_monthlyService.deleteAll(ArrayDATA_IDS);
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
//		logBefore(logger, "导出Sp_file_monthly到excel");
//		ModelAndView mv = new ModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			List<String> titles = new ArrayList<String>();
//			titles.add("唯一标示ID");	//1
//			titles.add("服务商ID");	//2
//			titles.add("月度的月份");	//3
//			titles.add("扩展用户指标数");	//4
//			titles.add("实际扩展用户数");	//5
//			titles.add("扩展完成率");	//6
//			titles.add("流水指标");	//7
//			titles.add("实际流水");	//8
//			titles.add("流水完成率");	//9
//			titles.add("是否合格：默认为2:0-不合格，1-合格，2-未审核");	//10
//			dataMap.put("titles", titles);
//			List<PageData> varOList = sp_file_monthlyService.listAll(pd);
//			List<PageData> varList = new ArrayList<PageData>();
//			for(int i=0;i<varOList.size();i++){
//				PageData vpd = new PageData();
//				vpd.put("var1", varOList.get(i).getString("SP_FILE_MONTHLY_ID"));	//1
//				vpd.put("var2", varOList.get(i).getString("SP_FILE_ID"));	//2
//				vpd.put("var3", varOList.get(i).getString("MONTH"));	//3
//				vpd.put("var4", varOList.get(i).getString("USER_NUMBER_INDICATOR"));	//4
//				vpd.put("var5", varOList.get(i).getString("ACTUAL_USER_NUMBER"));	//5
//				vpd.put("var6", varOList.get(i).getString("COMPLETION_RATE"));	//6
//				vpd.put("var7", varOList.get(i).getString("FLOW_INDICATORS"));	//7
//				vpd.put("var8", varOList.get(i).getString("ACTUAL_WATER"));	//8
//				vpd.put("var9", varOList.get(i).getString("WATER_COMPLETION_RATE"));	//9
//				vpd.put("var10", varOList.get(i).getString("ISQUALIFIED"));	//10
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
