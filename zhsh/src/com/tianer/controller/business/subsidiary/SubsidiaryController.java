package com.tianer.controller.business.subsidiary;

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
import com.tianer.entity.Page;
import com.tianer.entity.system.Menu;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.subsidiary.SubsidiaryService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;

/** 
 * 
* 类名称：SubsidiaryController   
* 类描述：   子公司
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:13:48
 */
@Controller
@RequestMapping(value="/subsidiary")
public class SubsidiaryController extends BaseController {
	
	@Resource(name="subsidiaryService")
	private SubsidiaryService subsidiaryService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Subsidiary");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("subsidiary_id", this.get32UUID());	//主键
		subsidiaryService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Subsidiary");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			subsidiaryService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Subsidiary");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		subsidiaryService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.5.27
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Subsidiary");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
			String province = pd.getString("province");
			if(null != province && !"".equals(province)){
				province = province.trim();
				pd.put("province", province);
			}
			String city = pd.getString("city");
			if(null != city && !"".equals(city)){
				pd.put("city", city);
			}
			String area = pd.getString("area");
			if(null != area && !"".equals(area)){
				pd.put("area", area);
			}
			page.setPd(pd);
			List<PageData> list=pcdService.listAll(pd);//省list
			mv.addObject("list", list);
			List<PageData>	varList = subsidiaryService.list(page);	//列出Subsidiary列表
			this.getHC(); //调用权限
			mv.setViewName("business/subsidiary/subsidiary_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 城市列表
	 * 刘耀耀
	 * 2016.5.30 10:17
	 */
	@RequestMapping(value="/citylist")
	@ResponseBody
	public Object citylist(Page page){
		logBefore(logger, "列表city");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> citylist=pcdService.listcity(pd);//市
			map.put("citylist", citylist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
	/**
	 * 区域列表
	 * 刘耀耀
	 * 2016.5.30 10:17
	 */
	@RequestMapping(value="/arealist")
	@ResponseBody
	public Object arealist(Page page){
		logBefore(logger, "列表city");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> arealist=pcdService.listarea(pd);//区
			map.put("arealist", arealist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Subsidiary页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("business/subsidiary/subsidiary_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Subsidiary页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = subsidiaryService.findById(pd);	//根据ID读取
			mv.setViewName("business/subsidiary/subsidiary_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Subsidiary");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				subsidiaryService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Subsidiary到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("子公司名称");	//1
			titles.add("内部公司名称");	//2
			titles.add("子公司工商名称");	//3
			titles.add("省ID");	//4
			titles.add("市ID");	//5
			titles.add("区域/县ID");	//6
			titles.add("唯一标示ID");	//7
			titles.add("创建时间");	//8
			dataMap.put("titles", titles);
			List<PageData> varOList = subsidiaryService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("SUBSIDIARY_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("HOUSE_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("SUBSIDIARY_IC_NAME"));	//3
				vpd.put("var4", varOList.get(i).getString("PROVINCE_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("CITY_ID"));	//5
				vpd.put("var6", varOList.get(i).getString("AREA_ID"));	//6
				vpd.put("var7", varOList.get(i).getString("SUBSIDIARY_ID"));	//7
				vpd.put("var8", varOList.get(i).getString("CREATEDATE"));	//8
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
