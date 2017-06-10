package com.tianer.controller.business.sp_file;

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
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;

/** 
 * 
* 类名称：Sp_fileController   
* 类描述：   服务商档案
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:57:29
 */
@Controller
@RequestMapping(value="/sp_file")
public class Sp_fileController extends BaseController {
	
	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Sp_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("sp_file_id", this.get32UUID());	//主键
		sp_fileService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Sp_file");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			sp_fileService.delete(pd);
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
		logBefore(logger, "修改Sp_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		sp_fileService.edit(pd);
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
		logBefore(logger, "列表Sp_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
			
			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
			String province_id = pd.getString("province_id");
			if(null != province_id && !"".equals(province_id)){
				province_id = province_id.trim();
				pd.put("province_id", province_id);
			}
			String city_id = pd.getString("city_id");
			if(null != city_id && !"".equals(city_id)){
				pd.put("city_id", city_id);
			}
			String area_id = pd.getString("area_id");
			if(null != area_id && !"".equals(area_id)){
				pd.put("area_id", area_id);
			}
			page.setPd(pd);
			List<PageData> list=pcdService.listAll(pd);//省list
			mv.addObject("list", list);
			List<PageData>	varList = sp_fileService.list(page);	//列出Sp_file列表
			this.getHC(); //调用权限
			mv.setViewName("zhihui/dangan/dangan5");
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
		logBefore(logger, "去新增Sp_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> list=pcdService.listAll(pd);//省list
			mv.addObject("list", list);
			mv.setViewName("zhihui/dangan/dangan6");
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
		logBefore(logger, "去修改Sp_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = sp_fileService.findById(pd);	//根据ID读取
			mv.setViewName("business/sp_file/sp_file_edit");
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
		logBefore(logger, "批量删除Sp_file");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				sp_fileService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Sp_file到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("团队名臣");	//1
			titles.add("工商名称");	//2
			titles.add("隶属子公司名称");	//3
			titles.add("隶属省");	//4
			titles.add("隶属市");	//5
			titles.add("隶属区");	//6
			titles.add("密码");	//7
			titles.add("负责人");	//8
			titles.add("固定电话");	//9
			titles.add("手机号码");	//10
			titles.add("e-mail");	//11
			titles.add("wechat");	//12
			titles.add("QQ");	//13
			titles.add("sp_file_id");	//14
			titles.add("签约时间");	//15
			titles.add("启动时间");	//16
			titles.add("保证金");	//17
			titles.add("支付/退还状态");	//18
			titles.add("创建时间");	//19
			dataMap.put("titles", titles);
			List<PageData> varOList = sp_fileService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("TEAM_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("INDUSTRY_COMMERCE_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("SUBSIDIARY_ID"));	//3
				vpd.put("var4", varOList.get(i).getString("PROVINCE_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("CITY_ID"));	//5
				vpd.put("var6", varOList.get(i).getString("AREA_ID"));	//6
				vpd.put("var7", varOList.get(i).getString("PASSWORD"));	//7
				vpd.put("var8", varOList.get(i).getString("PRINCIPAL"));	//8
				vpd.put("var9", varOList.get(i).getString("FIXED_TELEPHONE"));	//9
				vpd.put("var10", varOList.get(i).getString("PHONE"));	//10
				vpd.put("var11", varOList.get(i).getString("EMAIL"));	//11
				vpd.put("var12", varOList.get(i).getString("WECHAT"));	//12
				vpd.put("var13", varOList.get(i).getString("QQ"));	//13
				vpd.put("var14", varOList.get(i).getString("SP_FILE_ID"));	//14
				vpd.put("var15", varOList.get(i).getString("SIGN_TIME"));	//15
				vpd.put("var16", varOList.get(i).getString("START_TIME"));	//16
				vpd.put("var17", varOList.get(i).getString("EARNEST_MONEY"));	//17
				vpd.put("var18", varOList.get(i).getString("SP_STATESTATUS"));	//18
				vpd.put("var19", varOList.get(i).getString("CREATEDATE"));	//19
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
