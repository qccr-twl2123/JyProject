package com.tianer.controller.zhihui.yingxiao;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.system.Menu;
import com.tianer.entity.Page;
import com.tianer.util.AppUtil;
import com.tianer.util.DateUtil;
import com.tianer.util.FileUpload;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sort_chain.Sort_chainService;

/** 
 * 类名称：Sort_chainController
 * 创建人：刘耀耀
 * 创建时间：2016-06-14
 */
@Controller
@RequestMapping(value="/zhihui_sort_chain")
public class ZhihuiSort_chainController extends BaseController {
	
	@Resource(name="sort_chainService")
	private Sort_chainService sort_chainService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	/**
	 * 新增
	 * 刘耀耀
	 * 2016.06.14
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(
			@RequestParam("image_url") MultipartFile file,
			@RequestParam String ranking,
			@RequestParam String website,
			@RequestParam String advertising,
			@RequestParam String show_position,
			@RequestParam String province_id,
			@RequestParam String city_id,
			@RequestParam String area_id
			) throws Exception{
		logBefore(logger, "新增Sort_chain");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ranking", ranking);
		pd.put("website", website);
		pd.put("advertising", advertising);
		pd.put("show_position", show_position);
		pd.put("province_id", province_id);
		pd.put("city_id", city_id);
		pd.put("area_id", area_id);
		pd.put("sort_chain_id", BaseController.getTimeID());	//主键
		String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
		String filePath = "path/advice";//文件上传路径
		if(!file.isEmpty()){
			String advicefilename =  FileUpload.fileUp(file, currentPath+filePath, BaseController.getTimeID());//字符拼接，上传到服务器上
			String img_url = AppUtil.getuploadRootUrlIp()+filePath+"/"+advicefilename;
			logBefore(logger, DateUtil.getTime().toString()+":上传文件，文件地址是："+AppUtil.getuploadRootUrlIp());	
			pd.put("image_url", img_url);
		}else{
			pd.put("image_url", "");
		}
		sort_chainService.save(pd);
		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	/**
	 * 删除
	 * 刘耀耀
	 * 2016.06.15 
	 */
	@RequestMapping(value="/delete")
	public ModelAndView delete(PrintWriter out) throws Exception{
			logBefore(logger, "删除Sort_chain");
			PageData pd = new PageData();
			ModelAndView mv = this.getModelAndView();
			try {
				pd = this.getPageData();
				String currentPage=pd.getString("currentPage");
				sort_chainService.delete(pd);
				mv.setViewName("redirect:list.do?currentPage="+currentPage);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return mv;
	}
	
	/**
	 * 修改
	 * 刘耀耀
	 * 2016.06.14
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			@RequestParam("image_url") MultipartFile file,
			@RequestParam String currentPage,
			@RequestParam String sort_chain_id,
			@RequestParam String ranking,
			@RequestParam String website,
			@RequestParam String advertising,
			@RequestParam String show_position,
			@RequestParam String province_id,
			@RequestParam String city_id,
			@RequestParam String area_id
			) throws Exception{
		logBefore(logger, "修改Sort_chain");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ranking", ranking);
		pd.put("website", website);
		pd.put("advertising", advertising);
		pd.put("show_position", show_position);
		pd.put("province_id", province_id);
		pd.put("city_id", city_id);
		pd.put("area_id", area_id);
		pd.put("sort_chain_id", sort_chain_id);
		String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
		String filePath = "path/advice";//文件上传路径
		if(!file.isEmpty()){
			String advicefilename =  FileUpload.fileUp(file, currentPath+filePath, BaseController.getTimeID());//字符拼接，上传到服务器上
			String img_url = AppUtil.getuploadRootUrlIp()+filePath+"/"+advicefilename;
			logBefore(logger, DateUtil.getTime().toString()+":上传文件，文件地址是："+AppUtil.getuploadRootUrlIp());	
			pd.put("image_url", img_url);
		}else{
			pd.put("image_url", "");
		}
		sort_chainService.edit(pd);
		mv.setViewName("redirect:list.do?currentPage="+currentPage);
		return mv;
	}
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.14
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Sort_chain");
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
			List<PageData>	varList = sort_chainService.list(page);	//列出Sort_chain列表
			this.getHC(); //调用权限
			mv.setViewName("zhihui/yingxiao/yingxiao10");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 * 刘耀耀
	 * 2016.06.14
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Sort_chain页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> provicelist=pcdService.listAll(pd);//省list
			mv.addObject("provicelist", provicelist);
			mv.setViewName("zhihui/yingxiao/yingxiao11");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 * 刘耀耀
	 * 2016.06.14
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Sort_chain页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String currentPage=pd.getString("currentPage");
			List<PageData> provicelist=pcdService.listAll(pd);//省list
			mv.addObject("provicelist", provicelist);
			pd = sort_chainService.findById(pd);	//根据ID读取
			mv.setViewName("zhihui/yingxiao/yingxiao11");
			mv.addObject("msg", "edit");
			mv.addObject("currentPage", currentPage);
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
		logBefore(logger, "批量删除Sort_chain");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				sort_chainService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Sort_chain到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("图片");	//1
			titles.add("排序");	//2
			titles.add("网址链接");	//3
			titles.add("区域");	//4
			titles.add("商家ID");	//5
			titles.add("广告位置");	//6
			titles.add("显示位置");	//7
			dataMap.put("titles", titles);
			List<PageData> varOList = sort_chainService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("IMAGE_URL"));	//1
				vpd.put("var2", varOList.get(i).getString("RANKING"));	//2
				vpd.put("var3", varOList.get(i).getString("WEBSITE"));	//3
				vpd.put("var4", varOList.get(i).getString("ADDRESS"));	//4
				vpd.put("var5", varOList.get(i).getString("STORE_ID"));	//5
				vpd.put("var6", varOList.get(i).getString("ADVERTISING"));	//6
				vpd.put("var7", varOList.get(i).getString("SHOW"));	//7
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
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		session.setAttribute("qx", map.get("21"));
	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
