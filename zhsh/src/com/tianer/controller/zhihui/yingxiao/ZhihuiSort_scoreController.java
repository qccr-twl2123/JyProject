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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sort_score.Sort_scoreService;
import com.tianer.service.business.store.StoreService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;

/** 
 * 类名称：Sort_scoreController
 * 创建人：刘耀耀
 * 创建时间：2016-06-08
 */
@Controller
@RequestMapping(value="/zhihui_sort_score")
public class ZhihuiSort_scoreController extends BaseController {
	
	@Resource(name="sort_scoreService")
	private Sort_scoreService sort_scoreService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	@Resource(name="storeService")
	private StoreService storeService;

	
	/**
	 * 新增
	 * 刘耀耀	
	 * 2016.06.08
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		
		logBefore(logger, "新增Sort_score");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("now_score", Integer.parseInt(pd.getString("now_score"))+Integer.parseInt(pd.getString("add_score")));
 	 		pd.put("review_status", "1");
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
	 		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
	 		if(zhlogin != null){
		 			pd.put("operation_id", zhlogin.getLogin_id());
		 			sort_scoreService.save(pd);
	 			   //更新商家的排序分值
		 			PageData e=new PageData();
		 			e.put("store_id", pd.getString("store_id"));
		 			e.put("sort_score", Integer.parseInt(pd.getString("now_score"))+Integer.parseInt(pd.getString("add_score")));
		 			storeService.edit(e);
	 		}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
 		
 		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Sort_score");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			sort_scoreService.delete(pd);
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
		logBefore(logger, "修改Sort_score");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		sort_scoreService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.08
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Sort_score");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String content = pd.getString("content");
			
			if(null != content && !"".equals(content)){
				content = content.trim();
				pd.put("content", content);
			}
			//省
			String province_name = pd.getString("province_name");
			if(null != province_name && !"".equals(province_name)){
				province_name = province_name.trim();
				pd.put("province_name", province_name);
			}
			//市
			String city_name = pd.getString("city_name");
			if(null != city_name && !"".equals(city_name)){
				pd.put("city_name", city_name);
			}
			//区
			String area_name= pd.getString("area_name");
			if(null != area_name && !"".equals(area_name)){
				pd.put("area_name", area_name);
			}
			//开始时间
			String starttime = pd.getString("starttime");
			if(null != starttime && !"".equals(starttime)){
				pd.put("starttime", starttime);
			}
			//截止时间
			String endtime = pd.getString("endtime");
			if(null != endtime && !"".equals(endtime)){
				pd.put("endtime", endtime);
			}
 			page.setPd(pd);
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			List<PageData>	varList = sort_scoreService.list(page);	//列出Sort_score列表
			this.getHC(); //调用权限
			mv.setViewName("zhihui/yingxiao/yingxiao6");
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
	 * 2016.06.08
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Sort_score页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("zhihui/yingxiao/yingxiao7");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 搜素
	 * 刘耀耀
	 * 2016.06.08
	 */
	@RequestMapping(value="/findName")
	@ResponseBody
	public Object findName() throws Exception{
		logBefore(logger, "修改City_file_sort");
		Map<String,Object> map = new HashMap<String,Object>();
		try {
 			PageData pd = new PageData();
			pd = this.getPageData();
			PageData date =  storeService.findByName(pd);
			if(date != null && !date.equals("")){
				map.put("now_score", date.getString("sort_score"));
				map.put("store_id", date.getString("store_id" ));
				map.put("complex_score", date.getString("complex_score"));
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return map;
	}	
	
	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Sort_score页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = sort_scoreService.findById(pd);	//根据ID读取
			mv.setViewName("business/sort_score/sort_score_edit");
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
		logBefore(logger, "批量删除Sort_score");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				sort_scoreService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Sort_score到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("商家ID");	//1
			titles.add("增加分值");	//2
			titles.add("开始时间");	//3
			titles.add("截止时间");	//4
			titles.add("花费费用");	//5
			titles.add("审核状态");	//6
			titles.add("操作员");	//7
			titles.add("审核人员");	//8
			dataMap.put("titles", titles);
			List<PageData> varOList = sort_scoreService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("STORE_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("ADD_SCORE"));	//2
				vpd.put("var3", varOList.get(i).getString("STARTTIME"));	//3
				vpd.put("var4", varOList.get(i).getString("ENDTIME"));	//4
				vpd.put("var5", varOList.get(i).getString("SPEND_MONEY"));	//5
				vpd.put("var6", varOList.get(i).getString("REVIEW_STATUS"));	//6
				vpd.put("var7", varOList.get(i).getString("OPERATION_ID"));	//7
				vpd.put("var8", varOList.get(i).getString("REVIEW_ID"));	//8
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
			session.setAttribute("qx", map.get("18"));
		}
 	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
