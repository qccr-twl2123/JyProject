package com.tianer.controller.zhihui.dangan;

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
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.subsidiary.SubsidiaryService;
import com.tianer.service.business.subsidiary_posts.Subsidiary_postsService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.StringUtil;

/** 
 * 
* 类名称：SubsidiaryController   
* 类描述：   子公司
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:13:48
 */
@Controller
@RequestMapping(value="/zhihui_subsidiary")
public class ZhihuiSubsidiaryController extends BaseController {
	
	@Resource(name="subsidiaryService")
	private SubsidiaryService subsidiaryService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		//logBefore(logger, "新增Subsidiary");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("number", "0");
			String subsidiary_id=pd.getString("subsidiary_id");
			String password=BaseController.get6UID();
			pd.put("password", password);
	 		String house_name=pd.getString("house_name");//qu
			pd.put("house_name", house_name);//cun
	  		subsidiaryService.save(pd);
	 		//新增职务
	 		String allpostid=pd.getString("allpostid");
			String[] str=allpostid.split("@");
			for(int n=0; n<str.length ; n++){
				PageData e=new PageData();
	 			String subsidiary_posts_id=str[n];//一级分类ID
				String post=pd.getString(subsidiary_posts_id+"post");
				String fixed_telephone=pd.getString(subsidiary_posts_id+"fixed_telephone");
				String phone=pd.getString(subsidiary_posts_id+"phone");
				String email=pd.getString(subsidiary_posts_id+"email");
				String qq=pd.getString(subsidiary_posts_id+"qq");
				String wechat=pd.getString(subsidiary_posts_id+"wechat");
				e.put("subsidiary_id", pd.getString("subsidiary_id"));
	 			e.put("post", post);
				e.put("fixed_telephone", fixed_telephone);
				e.put("phone", phone);
				e.put("email", email);
				e.put("wechat", wechat);
				e.put("qq", qq);
				if(n == 0){
					e.put("isfirst", "1");
				}else{
					e.put("isfirst", "0");
				}
				subsidiary_postsService.save(e);
				e=null;
			}
			//新增地域
			String allpcd=pd.getString("allpcd");
			if(allpcd.contains("@")){
					List<PageData> allpcdlist=new ArrayList<PageData>();
					String[] one=allpcd.split(",");
					for (int i = 0; i < one.length; i++) {
						String[] two=one[i].split("@");
						PageData e=new PageData();
						e.put("province_id", two[0]);
						e.put("city_id", two[1]);
						e.put("area_id", two[2]);
						e.put("subsidiary_id", subsidiary_id);
						allpcdlist.add(e);
						PageData da = new PageData();
						//添加城市档案数据
						if(e.getString("area_id").equals("0")){
							String city_id = e.getString("city_id");
							List<PageData> list = subsidiaryService.cityAll(city_id);
							//拿到所有为全市的区域
							for (int j = 0; j < list.size(); j++) {
								String area_id = list.get(j).getString("area_id");
								da.put("area_id", area_id);
								da.put("province_id", e.getString("province_id"));
								da.put("city_id", e.getString("city_id"));
								//查看是否已经注册过
								PageData pg=city_fileService.findById(da);
								if(pg != null){
									continue;
								}
								//判断该表是否有值
								List<PageData> cityList=city_fileService.listAll(pd);
								if(cityList.size() == 0){
									da.put("city_file_id", "0001");	//主键
								}else{
									String id=city_fileService.getMaxID(pd);
									String nextId=StringUtil.getNextId(id);
									da.put("city_file_id", nextId);
								}
								da.put("open_status", '0');
								subsidiaryService.saveCity(da);
								cityList=null;
								pg=null;
							}
							list=null;
						}else{
							//查看是否已经注册过
							PageData pg=city_fileService.findById(e);
							if(pg == null || pg.equals("")){
								//判断该表是否有值
								List<PageData> cityList=city_fileService.listAll(pd);
								if(cityList.size() == 0){
									e.put("city_file_id", "0001");	//主键
								}else{
									String id=city_fileService.getMaxID(pd);
									String nextId=StringUtil.getNextId(id);
									e.put("city_file_id", nextId);
								}
								e.put("open_status", '0');
								subsidiaryService.saveCity(e);
								cityList=null;
							}
							pg=null;
						}
						e=null;
						da=null;
	 				}
					subsidiaryService.savePcd(pd,allpcdlist);
	 		}
		} catch (Exception e) {
			// TODO: handle exception
		}
 		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		//logBefore(logger, "删除Subsidiary");
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
		//logBefore(logger, "修改Subsidiary");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
					pd = this.getPageData();
					String subsidiary_id=pd.getString("subsidiary_id");
					subsidiaryService.edit(pd);
					//更新或新增职务
			 		String allpostid=pd.getString("allpostid");
					String[] str=allpostid.split("@");
					for(int n=0; n<str.length ; n++){
							PageData e=new PageData();
				 			String subsidiary_posts_id=str[n];//一级分类ID
							String post=pd.getString(subsidiary_posts_id+"post");
							String fixed_telephone=pd.getString(subsidiary_posts_id+"fixed_telephone");
							String phone=pd.getString(subsidiary_posts_id+"phone");
							String email=pd.getString(subsidiary_posts_id+"email");
							String qq=pd.getString(subsidiary_posts_id+"qq");
							String wechat=pd.getString(subsidiary_posts_id+"wechat");
							e.put("subsidiary_id", pd.getString("subsidiary_id"));
							e.put("subsidiary_posts_id", pd.getString("subsidiary_posts_id"));
				 			e.put("post", post);
							e.put("fixed_telephone", fixed_telephone);
							e.put("phone", phone);
							e.put("email", email);
							e.put("wechat", wechat);
							e.put("qq", qq);
							if(n == 0){
								e.put("isfirst", "1");
							}else{
								e.put("isfirst", "0");
							}
	 						subsidiary_postsService.save(e);
	 						e=null;
 			 		}
				//新增地域
					String allpcd=pd.getString("allpcd");
					if(allpcd.contains("@")){
							List<PageData> allpcdlist=new ArrayList<PageData>();
							String[] one=allpcd.split(",");
							for (int i = 0; i < one.length; i++) {
								String[] two=one[i].split("@");
								PageData e=new PageData();
								e.put("province_id", two[0]);
								e.put("city_id", two[1]);
								e.put("area_id", two[2]);
								e.put("subsidiary_id", subsidiary_id);
								allpcdlist.add(e);
								e=null;
			 				}
							subsidiaryService.savePcd(pd,allpcdlist);
							allpcdlist=null;
			 		}
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println(e.toString());
		}
 		mv.setViewName("redirect:list.do?currentPage="+pd.getString("currentPage"));
		return mv;
	}
	
	/**
	 * 列表
	 * 刘耀耀 2016.5.27
	 * 魏汉文20160531
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		//logBefore(logger, "列表Subsidiary");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			pd = this.getPageData();
 			//当前页
			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(currentPage));
			}
			page.setPd(pd);
 			List<PageData>	varList = subsidiaryService.list(page);	//列出Subsidiary列表
 			mv.addObject("varList", varList);
 			this.getHC();
			mv.setViewName("zhihui/index/index");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 城市列表
 	 * 
	 */
	@RequestMapping(value="/citylist")
	@ResponseBody
	public Object citylist(   ){
		//logBefore(logger, "城市列表");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData> citylist=pcdService.listcity(pd);//市
			map.put("citylist", citylist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
	/**
	 * 区域列表
 	 * 
	 */
	@RequestMapping(value="/arealist")
	@ResponseBody
	public Object arealist(   ){
		//logBefore(logger, "区域列表");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData> arealist=pcdService.listarea(pd);//区
			map.put("arealist", arealist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
	
	/**
	 * 去新增页面
	 * 魏汉文20160531
	 * 
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		//logBefore(logger, "去新增Subsidiary页面");
		ModelAndView mv = this.getModelAndView();
		List<PageData> subsidiarypcd=new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			subsidiarypcd.add(pd);
			mv.addObject("subsidiarypcd", subsidiarypcd);//新增一个地域
			String subsidiary_posts_id=BaseController.getTimeID();
			pd.put("subsidiary_posts_id", subsidiary_posts_id);
			//获取当前最大值得ID
			String id=subsidiaryService.getMaxId();
			if(id==null || id.equals("") ){
				id="ZGS0001";
				pd.put("subsidiary_id", id);
			}else{
				//设置新增的ID
				id=id.substring(3);
				String nextId=StringUtil.getNextId(id);
				pd.put("subsidiary_id", "ZGS"+nextId);
			}
 			List<PageData> provicelist=pcdService.listAll(pd);//省list
			mv.addObject("provicelist", provicelist);
			mv.setViewName("zhihui/dangan/dangan1");
			mv.addObject("msg", "save");
		
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	@Resource(name="subsidiary_postsService")
	private Subsidiary_postsService subsidiary_postsService;
	
	/**
	 * 去修改页面
	 * 魏汉文20160531
	 * 
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		//logBefore(logger, "去修改Subsidiary页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String subsidiary_posts_id=BaseController.getTimeID();
			List<PageData> subsidiarypcd=subsidiaryService.findSubPcdList(pd);
			if(subsidiarypcd.size() == 0){
				subsidiarypcd.add(pd);
			}
			mv.addObject("subsidiarypcd", subsidiarypcd);//地域列表
 			String currentPage=pd.getString("currentPage");
			//获取该子公司信息
			pd = subsidiaryService.findById(pd);	//根据ID读取
			//获取该子公司的职务信息
			List<PageData> postList=subsidiary_postsService.listAll(pd);
			mv.addObject("postList", postList);
			if(postList.size() == 0){
				pd.put("subsidiary_posts_id", subsidiary_posts_id);
			}
			List<PageData> provicelist=pcdService.listAll(pd);//省list
			mv.addObject("provicelist", provicelist);
			pd.put("currentPage", currentPage);
			mv.setViewName("zhihui/dangan/dangan1");
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
		//logBefore(logger, "批量删除Subsidiary");
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
		//logBefore(logger, "导出Subsidiary到excel");
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
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("1"));
		}
 	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
