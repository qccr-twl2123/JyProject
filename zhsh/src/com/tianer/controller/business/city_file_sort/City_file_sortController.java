package com.tianer.controller.business.city_file_sort;

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
import com.tianer.service.business.city_file_sort.City_file_sortService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;

/** 
 * 
* 类名称：City_file_sortController   
* 类描述：城市下的一级二级分类   
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:46:39
 */
@Controller
@RequestMapping(value="/city_file_sort")
public class City_file_sortController extends BaseController {
	
	@Resource(name="city_file_sortService")
	private City_file_sortService city_file_sortService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增City_file_sort");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CITY_FILE_SORT_ID", this.get32UUID());	//主键
		city_file_sortService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除City_file_sort");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			city_file_sortService.delete(pd);
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
		logBefore(logger, "修改City_file_sort");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		city_file_sortService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表City_file_sort");
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
			List<PageData>	varList = city_file_sortService.list(page);	//列出City_file_sort列表
			this.getHC(); //调用权限
			mv.setViewName("s/city_file_sort/city_file_sort_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增City_file_sort页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("s/city_file_sort/city_file_sort_edit");
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
		logBefore(logger, "去修改City_file_sort页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = city_file_sortService.findById(pd);	//根据ID读取
			mv.setViewName("s/city_file_sort/city_file_sort_edit");
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
		logBefore(logger, "批量删除City_file_sort");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				city_file_sortService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出City_file_sort到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("唯一标示ID");	//1
			titles.add("分类名称");	//2
			titles.add("分类图片地址");	//3
			titles.add("父类ID");	//4
			titles.add("开启状态");	//5
			titles.add("排序");	//6
			titles.add("城市档案ID");	//7
			titles.add("所属等级");	//8
			titles.add("创建时间");	//9
			dataMap.put("titles", titles);
			List<PageData> varOList = city_file_sortService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("CITY_FILE_SORT_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("SORT_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("SORT_IMAGEURL"));	//3
				vpd.put("var4", varOList.get(i).getString("SORT_PARENT_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("OPEN_STATESTATUS"));	//5
				vpd.put("var6", varOList.get(i).getString("SEQUENCE"));	//6
				vpd.put("var7", varOList.get(i).getString("CITY_FILE_ID"));	//7
				vpd.put("var8", varOList.get(i).getString("SORT_TYPE"));	//8
				vpd.put("var9", varOList.get(i).getString("CREATEDATE"));	//9
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
