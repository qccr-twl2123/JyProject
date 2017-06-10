//package com.tianer.controller.business.pay_history;
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
//import com.tianer.service.business.pay_history.Pay_historyService;
//
///** 
// * 类名称：Pay_historyController
// * 创建人：cyr
// * 创建时间：2016-05-26
// */
//@Controller
//@RequestMapping(value="/pay_history")
//public class Pay_historyController extends BaseController {
//	
//	@Resource(name="pay_historyService")
//	private Pay_historyService pay_historyService;
//	
//	/**
//	 * 新增
//	 */
//	@RequestMapping(value="/save")
//	public ModelAndView save() throws Exception{
//		logBefore(logger, "新增Pay_history");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd.put("pay_history_id", this.get32UUID());	//主键
////		pay_historyService.save(pd);
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
//		logBefore(logger, "删除Pay_history");
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pay_historyService.delete(pd);
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
//		logBefore(logger, "修改Pay_history");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pay_historyService.edit(pd);
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
//		logBefore(logger, "列表Pay_history");
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
//			List<PageData>	varList = pay_historyService.datalistPage(page);	//列出Pay_history列表
//			this.getHC(); //调用权限
//			mv.setViewName("business/pay_history/pay_history_list");
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
//		logBefore(logger, "去新增Pay_history页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			mv.setViewName("business/pay_history/pay_history_edit");
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
//		logBefore(logger, "去修改Pay_history页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			pd = pay_historyService.findById(pd);	//根据ID读取
//			mv.setViewName("business/pay_history/pay_history_edit");
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
//		logBefore(logger, "批量删除Pay_history");
//		PageData pd = new PageData();		
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			pd = this.getPageData();
//			List<PageData> pdList = new ArrayList<PageData>();
//			String DATA_IDS = pd.getString("DATA_IDS");
//			if(null != DATA_IDS && !"".equals(DATA_IDS)){
//				String ArrayDATA_IDS[] = DATA_IDS.split(",");
//				pay_historyService.deleteAll(ArrayDATA_IDS);
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
//		logBefore(logger, "导出Pay_history到excel");
//		ModelAndView mv = new ModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			List<String> titles = new ArrayList<String>();
//			titles.add("用户类型");	//1
//			titles.add("用户账号");	//2
//			titles.add("款项类型");	//3
//			titles.add("款项金额");	//4
//			titles.add("汇款方式");	//5
//			titles.add("汇款账号");	//6
//			titles.add("支付时间");	//7
//			titles.add("支付状态");	//8
//			titles.add("订单号");	//9
//			titles.add("收款人编号");	//10
//			dataMap.put("titles", titles);
//			List<PageData> varOList = pay_historyService.listAll(pd);
//			List<PageData> varList = new ArrayList<PageData>();
//			for(int i=0;i<varOList.size();i++){
//				PageData vpd = new PageData();
//				vpd.put("var1", varOList.get(i).getString("USER_TYPE"));	//1
//				vpd.put("var2", varOList.get(i).getString("USER_ACCOUNT"));	//2
//				vpd.put("var3", varOList.get(i).getString("MONEY_TYPE"));	//3
//				vpd.put("var4", varOList.get(i).getString("MONEY"));	//4
//				vpd.put("var5", varOList.get(i).getString("REMITTANCE_TYPE"));	//5
//				vpd.put("var6", varOList.get(i).getString("REMITTANCE_NUMBER"));	//6
//				vpd.put("var7", varOList.get(i).getString("PAY_TIME"));	//7
//				vpd.put("var8", varOList.get(i).getString("PAY_STATUS"));	//8
//				vpd.put("var9", varOList.get(i).getString("ORDER_TN"));	//9
//				vpd.put("var10", varOList.get(i).getString("PAYEE_NUMBER"));	//10
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
