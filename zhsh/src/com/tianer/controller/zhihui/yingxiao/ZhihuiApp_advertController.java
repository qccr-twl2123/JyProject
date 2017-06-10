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
import com.tianer.util.ServiceHelper;
import com.tianer.service.business.app_advert.App_advertService;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.pcd.PcdService;

/** 
 * 类名称：App_advertController
 *创建人：刘耀耀
 * 创建时间：2016-06-13
 */
@Controller
@RequestMapping(value="/zhihui_app_advert")
public class ZhihuiApp_advertController extends BaseController {
	
	@Resource(name="app_advertService")
	private App_advertService app_advertService;
	
	/**
	 * 新增
	 * 刘耀耀
	 * 2016.06.14
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(
			@RequestParam("image_url") MultipartFile file,
			@RequestParam String image_name,
			@RequestParam String ranking,
			@RequestParam String starttime,
			@RequestParam String endtime,
			@RequestParam String city_file_id,
			@RequestParam String title,
			@RequestParam String hyperlink_type,
			@RequestParam String hyperlink_url
			) throws Exception{
//		logBefore(logger, "新增App_advert");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ifimage","1");
		pd.put("app_advert_id", BaseController.getTimeID());	//主键
		pd.put("image_name", image_name);
		pd.put("ranking", ranking);
		pd.put("starttime", starttime);
		pd.put("endtime", endtime);
		pd.put("title", title);
		pd.put("hyperlink_type", hyperlink_type);
		pd.put("hyperlink_url", hyperlink_url);
		String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
		String filePath = "/adviceFile";//文件上传路径
		if(!file.isEmpty()){
			String advicefilename =  FileUpload.fileUp(file, currentPath+filePath, BaseController.getTimeID());//字符拼接，上传到服务器上
			String img_url = AppUtil.getuploadRootUrlIp()+filePath+"/"+advicefilename;
//			logBefore(logger, DateUtil.getTime().toString()+":上传文件，文件地址是："+img_url);	
			pd.put("image_url", img_url);
		}else{
			pd.put("image_url", "");
		}
		app_advertService.save(pd);
		mv.setViewName("redirect:list.do?city_file_id="+city_file_id);
		return mv;
	}
	
//	}
	
	/**
	 * 修改
	 * 刘耀耀
	 * 2016.06.14
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			@RequestParam("image_url") MultipartFile file,
			@RequestParam String app_advert_id,
 			@RequestParam String ranking,
			@RequestParam String starttime,
			@RequestParam String endtime,
			@RequestParam String title,
			@RequestParam String city_file_id,
			@RequestParam String hyperlink_type,
			@RequestParam String hyperlink_url
			) throws Exception{
//		logBefore(logger, "修改App_advert");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("app_advert_id", app_advert_id);
 		pd.put("ranking", ranking);
		pd.put("starttime", starttime);
		pd.put("endtime", endtime);
		pd.put("title", title);
		pd.put("hyperlink_type", hyperlink_type);
		pd.put("hyperlink_url", hyperlink_url);
		String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
		String image_name=file.getOriginalFilename();
		pd.put("image_name", image_name);
		String filePath = "/adviceFile";//文件上传路径
		if(!file.isEmpty()){
			String advicefilename =  FileUpload.fileUp(file, currentPath+filePath, BaseController.getTimeID());//字符拼接，上传到服务器上
			String img_url = AppUtil.getuploadRootUrlIp()+filePath+"/"+advicefilename;
//			logBefore(logger, DateUtil.getTime().toString()+":上传文件，文件地址是："+AppUtil.getuploadRootUrlIp());	
			pd.put("image_url", img_url);
		}else{
			pd.put("image_url", "");
		}
		app_advertService.edit(pd);
		mv.setViewName("redirect:list.do?city_file_id="+city_file_id);
		return mv;
	}
	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.13
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
//		logBefore(logger, "列表App_advert");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//获取城市营销ID
			PageData e=city_fileService.findById(pd);
			if(e != null){
				pd = e;
				page.setPd(pd);
				List<PageData>	varList = app_advertService.list(page);	//列出App_advert列表
				mv.addObject("varList", varList);
			} 
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			this.getHC(); //调用权限
			mv.addObject("pd", pd);
			mv.setViewName("zhihui/yingxiao/yingxiao9");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping(value="/findbyid")
	@ResponseBody
	public Object findbyid(){
//		logBefore(logger, "列表Pc_advert");
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "01";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData varList = app_advertService.findById(pd);//列出Pc_advert列表
			map.put("pagedatas", varList);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return map;
	}
	
//	/**
//	 * 去新增页面
//	 */
//	@RequestMapping(value="/goAdd")
//	public ModelAndView goAdd(){
//		logBefore(logger, "去新增App_advert页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			mv.setViewName("business/app_advert/app_advert_edit");
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
//		logBefore(logger, "去修改App_advert页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			pd = app_advertService.findById(pd);	//根据ID读取
//			mv.setViewName("business/app_advert/app_advert_edit");
//			mv.addObject("msg", "edit");
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}						
//		return mv;
//	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
//		logBefore(logger, "批量删除App_advert");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				app_advertService.deleteAll(ArrayDATA_IDS);
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
//		logBefore(logger, "导出App_advert到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("排序位置");	//1
			titles.add("图片名称");	//2
			titles.add("图片url");	//3
			titles.add("跳转至URL");	//4
			titles.add("有效时间：开始");	//5
			titles.add("有效时间：截止");	//6
			titles.add("广告类型");	//7
			dataMap.put("titles", titles);
			List<PageData> varOList = app_advertService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("RANKING"));	//1
				vpd.put("var2", varOList.get(i).getString("IMAGE_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("IMAGE_URL"));	//3
				vpd.put("var4", varOList.get(i).getString("HYPERLINK_URL"));	//4
				vpd.put("var5", varOList.get(i).getString("STARTTIME"));	//5
				vpd.put("var6", varOList.get(i).getString("ENDTIME"));	//6
				vpd.put("var7", varOList.get(i).getString("ADVERT-TYPE"));	//7
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
		if(map != null){
			session.setAttribute("qx", map.get("20"));
		}
 	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	/**
	 * 新增广告数量
	 * zhihui_app_advert/addnumberAdvert.do?addnumber=2
	 */
	@RequestMapping(value="/addnumberAdvert")
	@ResponseBody
	public Object addnumberAdvert() {
		logBefore(logger, "新增广告数量");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			String addnumber=pd.getString("addnumber");
			int n=Integer.parseInt(addnumber);
			//获取所有的city_file_id
			List<PageData> citylist=ServiceHelper.getCity_fileService().listAll(pd);
			for (PageData e : citylist) {
				for (int i = 0; i < n; i++) {
					app_advertService.lastsaveAdvert(e);
				}
			}
			
			 
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return map;
	}
}
