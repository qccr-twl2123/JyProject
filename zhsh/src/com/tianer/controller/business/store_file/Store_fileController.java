package com.tianer.controller.business.store_file;

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
import com.tianer.service.business.store_file.Store_fileService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;

/** 
 * 
* 类名称：Store_fileController   
* 类描述：商家档案   
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午5:45:24
 */
@Controller
@RequestMapping(value="/store_file")
public class Store_fileController extends BaseController {
	
	@Resource(name="store_fileService")
	private Store_fileService store_fileService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Store_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("store_file_id", this.get32UUID());	//主键
		store_fileService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Store_file");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			store_fileService.delete(pd);
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
		logBefore(logger, "修改Store_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		store_fileService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.5.30
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Store_file");
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
			List<PageData>	varList = store_fileService.list(page);	//列出Store_file列表
			this.getHC(); //调用权限
			mv.setViewName("business/store_file/store_file_list");
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
		logBefore(logger, "去新增Store_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("business/store_file/store_file_edit");
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
		logBefore(logger, "去修改Store_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = store_fileService.findById(pd);	//根据ID读取
			mv.setViewName("business/store_file/store_file_edit");
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
		logBefore(logger, "批量删除Store_file");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				store_fileService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Store_file到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("商家名称");	//1
			titles.add("商家简称");	//2
			titles.add("商家工商执照全程");	//3
			titles.add("省ID");	//4
			titles.add("市ID");	//5
			titles.add("区ID");	//6
			titles.add("详细地址");	//7
			titles.add("经营品项目介绍");	//8
			titles.add("负责人");	//9
			titles.add("手机号码");	//10
			titles.add("e-mail");	//11
			titles.add("商家联系电话");	//12
			titles.add("开启状态");	//13
			titles.add("用户名");	//14
			titles.add("密码");	//15
			titles.add("工商执照");	//16
			titles.add("许可证执照");	//17
			titles.add("许可证执照");	//18
			titles.add("许可证执照");	//19
			titles.add("保证金");	//20
			titles.add("保证金状态");	//21
			titles.add("系统服务费");	//22
			titles.add("服务费状态");	//23
			titles.add("服务费开始有效时间");	//24
			titles.add("服务费结束有效时间");	//25
			titles.add("唯一标示编号ID");	//26
			titles.add("添加商家的服务商ID（操作人）");	//27
			titles.add("创建时间");	//28
			dataMap.put("titles", titles);
			List<PageData> varOList = store_fileService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("STORE_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("STORE_ABBREVIATION_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("BUSINESS LICENSES_NAME"));	//3
				vpd.put("var4", varOList.get(i).getString("PROVINCE_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("CITY_ID"));	//5
				vpd.put("var6", varOList.get(i).getString("AREA_ID"));	//6
				vpd.put("var7", varOList.get(i).getString("ADDRESS"));	//7
				vpd.put("var8", varOList.get(i).getString("MANAGEMENT PROJECTS_DESC"));	//8
				vpd.put("var9", varOList.get(i).getString("PRINCIPAL"));	//9
				vpd.put("var10", varOList.get(i).getString("PHONE"));	//10
				vpd.put("var11", varOList.get(i).getString("EMAIL"));	//11
				vpd.put("var12", varOList.get(i).getString("STORE_PHONE"));	//12
				vpd.put("var13", varOList.get(i).getString("OPEN_STATESTATUS"));	//13
				vpd.put("var14", varOList.get(i).getString("USERNAME"));	//14
				vpd.put("var15", varOList.get(i).getString("PASSWORD"));	//15
				vpd.put("var16", varOList.get(i).getString("BUSINESS LICENSES_IMAGE"));	//16
				vpd.put("var17", varOList.get(i).getString("LICENSE_IMAGE_ONE"));	//17
				vpd.put("var18", varOList.get(i).getString("LICENSE_IMAGE_TWO"));	//18
				vpd.put("var19", varOList.get(i).getString("LICENSE_IMAGE_THREE"));	//19
				vpd.put("var20", varOList.get(i).getString("EARNEST_MONEY"));	//20
				vpd.put("var21", varOList.get(i).getString("EM_STATESTATUS"));	//21
				vpd.put("var22", varOList.get(i).getString("SYSTEM_SERVICE_FEE"));	//22
				vpd.put("var23", varOList.get(i).getString("SF_STATESTATUS"));	//23
				vpd.put("var24", varOList.get(i).getString("SF_STARTTIME"));	//24
				vpd.put("var25", varOList.get(i).getString("SF_ENDTIME"));	//25
				vpd.put("var26", varOList.get(i).getString("STORE_FILE_ID"));	//26
				vpd.put("var27", varOList.get(i).getString("SP_FILE_ID"));	//27
				vpd.put("var28", varOList.get(i).getString("CREATEDATE"));	//28
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
