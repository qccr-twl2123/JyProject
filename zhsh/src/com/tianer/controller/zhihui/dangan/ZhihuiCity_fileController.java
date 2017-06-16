package com.tianer.controller.zhihui.dangan;

import java.io.File;
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

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.city_file_sort.City_file_sortService;
import com.tianer.service.business.city_marketing.City_marketingService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.FileUpload;
import com.tianer.util.PageData;
import com.tianer.util.StringUtil;
import com.tianer.util.AddWaterMark.MoreTextMarkService;

/** 
 * 
* 类名称：ZhihuiCity_fileController   
* 类描述：   城市档案
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:45:36
 */
@Controller
@RequestMapping(value="/zhihui_city_file")
public class ZhihuiCity_fileController extends BaseController {
	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	@Resource(name="city_marketingService")
	private City_marketingService city_marketingService;
	
 	
	/**
	 * 新增
	 * 魏汉文20160621
	 */ 
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		List<PageData> sortlist=new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查看是否已经注
		PageData e=city_fileService.findById(pd);
		if(e != null){
			mv.setViewName("redirect:list.do");
			return mv;
		}
		//判断该表是否有值
		List<PageData> cityList=city_fileService.listAll(pd);
		if(cityList.size() == 0){
			pd.put("city_file_id", "0001");	//主键
		}else{
			String id=city_fileService.getMaxID(pd);
			String nextId=StringUtil.getNextId(id);
			pd.put("city_file_id", nextId);
		}
 		
		pd.put("province_id", pd.getString("province_id"));
		pd.put("city_id", pd.getString("city_id"));
		pd.put("area_id", pd.getString("area_id"));
		pd.put("open_status", pd.getString("open_status"));
 		//新增相关的一级.二级分类
		String allcity_file_sort_id=pd.getString("allcity_file_sort_id");
		String[] str=allcity_file_sort_id.split("@");
		for(int i=1 ; i<str.length ; i++){
			PageData e1=new PageData();
			e1.put("city_file_id", pd.getString("city_file_id"));//城市ID
 			String city_file_sort_id=str[i];//一级分类ID
			String sort_name=pd.getString(city_file_sort_id+"sort_name");//分类名称
			String sort_imageurl=pd.getString(city_file_sort_id+"sort_imageurl");//分类图片
			String sequence=pd.getString(city_file_sort_id+"sequence");//排序
			String open_status=pd.getString(city_file_sort_id+"open_status");//开启状态
			e1.put("city_file_sort_id", city_file_sort_id);
			e1.put("sort_name", sort_name);
			e1.put("sort_imageurl", sort_imageurl);
			e1.put("sort_parent_id", "0");
			e1.put("sequence", sequence);
			e1.put("open_status", open_status);
			e1.put("sort_type", "1");
			sortlist.add(e1);
			//一级下的二级分类ID
			String alltwocity_file_sort_id=pd.getString("allsort"+city_file_sort_id);
			String[] str2=alltwocity_file_sort_id.split("@");
			for(int n=1 ; n<str2.length ; n++){
				PageData e2=new PageData();
				e2.put("city_file_id", pd.getString("city_file_id"));
				String twocity_file_sort_id=str2[n];//一级分类ID
				String twosort_name=pd.getString(twocity_file_sort_id+"sort_name");
				String twosort_imageurl=pd.getString(twocity_file_sort_id+"sort_imageurl");
				String twosequence=pd.getString(twocity_file_sort_id+"sequence");
				String twoopen_status=pd.getString(twocity_file_sort_id+"open_status");
				e2.put("city_file_sort_id", twocity_file_sort_id);
				e2.put("sort_name", twosort_name);
				e2.put("sort_imageurl", twosort_imageurl);
				e2.put("sort_parent_id", city_file_sort_id);
				e2.put("sequence", twosequence);
				e2.put("open_status", twoopen_status);
				e2.put("sort_type", "2");
				sortlist.add(e2);
				e2=null;
			}
			e1=null;
		}
  		city_fileService.saveList(pd,sortlist);
  		
  		/*
		 * 新增城市营销参数
		 */
		PageData yingxiaocity=new PageData();
		yingxiaocity.put("city_marketing_id", BaseController.getTimeID());	//主键
		yingxiaocity.put("city_file_id", pd.getString("city_file_id"));	
		city_marketingService.saveYinxiao(yingxiaocity);
		
		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			city_fileService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 修改
	 * 魏汉文20160621
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
//		logBefore(logger, "修改City_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String currentPage=pd.getString("currentPage");
			pd.put("province_id", pd.getString("province_id"));
			pd.put("city_id", pd.getString("city_id"));
			pd.put("area_id", pd.getString("area_id"));
			pd.put("open_status", pd.getString("open_status"));
			city_fileService.edit(pd);
	 		//新增相关的一级.二级分类
			String allcity_file_sort_id=pd.getString("allcity_file_sort_id");
			String[] str=allcity_file_sort_id.split("@");
			for(int i=1 ; i<str.length ; i++){
				PageData e1=new PageData();
				e1.put("city_file_id", pd.getString("city_file_id"));//城市ID
	 			String city_file_sort_id=str[i];//一级分类ID
	 			String sort_name=pd.getString(city_file_sort_id+"sort_name");//分类名称
	 			String sort_imageurl=pd.getString(city_file_sort_id+"sort_imageurl");//分类图片
				String sequence=pd.getString(city_file_sort_id+"sequence");//排序
				String open_status=pd.getString(city_file_sort_id+"open_status");//开启状态
				e1.put("city_file_sort_id", city_file_sort_id);
				e1.put("sort_name", sort_name);
				e1.put("sort_imageurl", sort_imageurl);
				e1.put("sort_parent_id", "0");
				e1.put("sequence", sequence);
				e1.put("open_status", open_status);
				e1.put("sort_type", "1");
	 			//判断是否存在该一级分类
				PageData onepd=city_file_sortService.findById(e1);
	 			if(onepd == null){
	 				city_file_sortService.save(e1);
	 			}else{
	 				city_file_sortService.edit(e1);
	 			}
	  			//一级下的二级分类ID
				String alltwocity_file_sort_id=pd.getString("allsort"+city_file_sort_id);
				String[] str2=alltwocity_file_sort_id.split("@");
				for(int n=1 ; n<str2.length ; n++){
					PageData e2=new PageData();
					e2.put("city_file_id", pd.getString("city_file_id"));
//					System.out.println(n+"&&"+str2[n]);
					String twocity_file_sort_id=str2[n];//一级分类ID
					String twosort_name=pd.getString(twocity_file_sort_id+"sort_name");
					String twosort_imageurl=pd.getString(twocity_file_sort_id+"sort_imageurl");
					String twosequence=pd.getString(twocity_file_sort_id+"sequence");
					String twoopen_status=pd.getString(twocity_file_sort_id+"open_status");
					e2.put("city_file_sort_id", twocity_file_sort_id);
					e2.put("sort_name", twosort_name);
					e2.put("sort_imageurl", twosort_imageurl);
					e2.put("sort_parent_id", city_file_sort_id);
					e2.put("sequence", twosequence);
					e2.put("open_status", twoopen_status);
					e2.put("sort_type", "2");
					//判断是否存在该二级分类
					PageData twopd=city_file_sortService.findById(e2);
		 			if(twopd == null){
		 				city_file_sortService.save(e2);
		 			}else{
		 				city_file_sortService.edit(e2);
		 			}
		 			
	 			}
			}
			mv.setViewName("redirect:list.do?currentPage="+currentPage);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		return mv;
	}
	
	/**
	 * 列表
	 * 刘耀耀 2016.5.30
	 *魏汉文20160531
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
//		logBefore(logger, "列表City_file");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try{
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			pd = this.getPageData();
			if(zhlogin != null && zhlogin.getMenu_role_id() != null){
				//0-管理员，1-服务商，2-业务员，3-操作员，4-城市经理，5-子公司，6-其他
				pd.put("login_cityname", zhlogin.getCity_name()); 
				pd.put("login_areaname", zhlogin.getArea_name());
  	 			String currentPage = pd.getString("currentPage");
				if(null != currentPage && !"".equals(currentPage)){
					page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
				}
	 			page.setPd(pd);
	 			List<PageData>	varList = city_fileService.list(page);	//列出City_file列表
				this.getHC(); //调用权限
				mv.setViewName("zhihui/dangan/dangan2");
				mv.addObject("varList", varList);
				mv.addObject("pd", pd); 
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 * 魏汉文
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
//		logBefore(logger, "去新增City_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			mv.setViewName("zhihui/dangan/dangan3");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	@Resource(name="city_file_sortService")
	private City_file_sortService city_file_sortService;
	
	/**
	 * 去修改页面
	 * 魏汉文
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
//		logBefore(logger, "去修改City_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
 			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			String currentPage=pd.getString("currentPage");
			//获取成绩档案详情
			pd = city_fileService.findById(pd);	//根据ID读取
 			//获取城市档案相关的分类
			//1、获得一级分类
			pd.put("sort_type", "1");
			List<PageData> firstList=city_file_sortService.listAll(pd);
			for(PageData e : firstList){
				//2、将二级分类添加到一级分类
				PageData e1=new PageData();
				e1.put("sort_parent_id", e.getString("city_file_sort_id"));
				e1.put("city_file_id", pd.getString("city_file_id"));
				e1.put("sort_type", "2");
				List<PageData> twoList=city_file_sortService.listAll(e1);
				e.put("twoList", twoList);
				twoList=null;
				e1=null;
			}
 			mv.addObject("firstList", firstList);
 			pd.put("currentPage", currentPage);
 			mv.setViewName("zhihui/dangan/dangan3");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	
	/**
	 * 城市列表
	 *魏汉文20160606
	 * 
	 */
	@RequestMapping(value="/citylist")
	@ResponseBody
	public Object citylist(   ){
//		logBefore(logger, "列表city");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData> citylist=city_fileService.listAllCity(pd);//市
			map.put("citylist", citylist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
	
	
	/**
	 * 区域列表
	 * 魏汉文20160606
	 * 
	 */
	@RequestMapping(value="/arealist")
	@ResponseBody
	public Object arealist(Page page){
//		logBefore(logger, "列表city");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> arealist=city_fileService.listAllArea(pd);//区
			map.put("arealist", arealist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
 
	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
	
	/**
	 * 1/2级分类
	 * 魏汉文20160606
	 * 
	 */
	@RequestMapping(value="/citySortList")
	@ResponseBody
	public Object citySortList(Page page){
//		logBefore(logger, "列表城市1/2级分类");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData> sortlist=city_fileService.listAllCitySort(pd);//1/2级分类
 			map.put("sortlist", sortlist);
 			List<PageData> splist=sp_fileService.listAll(pd);
 			map.put("splist", splist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
	
	
	/**
	 * 根据city_file_sort_id获取1/2级分类
	 * 魏汉文20160606
	 * 
	 */
	@RequestMapping(value="/getSortList")
	@ResponseBody
	public Object getSortList(){
//		logBefore(logger, "列表城市1/2级分类");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData> sortlist=city_file_sortService.listAll(pd);//1/2级分类
			map.put("sortlist", sortlist);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
	
	
	/**
	 *获取当前等级的星级费用
	 * 魏汉文20160621
	 * 
	 */
	@RequestMapping(value="/getServiceFee")
	@ResponseBody
	public Object getServiceFee(Page page){
//		logBefore(logger, "获取当前等级的星级费用");
		Map<String,Object> map = new HashMap<String,Object>();
		String result="01";
		PageData pd = new PageData();
		try{
				pd = this.getPageData();
				String merchant_level=pd.getString("merchant_level");
				pd=city_file_sortService.findById(pd);
				if(merchant_level.equals("1")){
					map.put("serviceFee", pd.getString("onexing_money"));
				}else if(merchant_level.equals("2")){
					map.put("serviceFee", pd.getString("twoxing_money"));
				}else{
					map.put("serviceFee", pd.getString("threexing_money"));
				}
 		} catch(Exception e){
			logger.error(e.toString(), e);
			result="00";
		}
		map.put("result", result);
		return map;
	}
	
	
	/**
	 *设置/修改星级的默认费用
	 * 魏汉文20160621
	 * 
	 */
	@RequestMapping(value="/setMoRengXingFee")
	@ResponseBody
	public Object setMoRengXingFee(){
//		logBefore(logger, "获取当前等级的星级费用");
		Map<String,Object> map = new HashMap<String,Object>();
		String result="01";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			city_file_sortService.setMoRengXingFee(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
			result="00";
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 *删除当前星级费用设置还原为默认星级费用(即修改)
	 * 魏汉文20160621
	 * 
	 */
	@RequestMapping(value="/delFee")
	@ResponseBody
	public Object delFee(){
//		logBefore(logger, "删除当前星级费用设置还原为默认星级费用(即修改)");
		Map<String,Object> map = new HashMap<String,Object>();
		String result="01";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			city_file_sortService.setMoRengXingFee(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
			result="00";
		}
		map.put("result", result);
		return map;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	/**
	 * 获取ID
	 */
	@RequestMapping(value="/get32Id")
	@ResponseBody
	public Object get32Id() throws Exception{
//		logBefore(logger, "获取ID");
		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		pd = this.getPageData();
		String id=BaseController.getTimeID();
		map.put("id", id);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 上传城市分类的图片
	 */
	@RequestMapping(value="/uploadheadimage")
	@ResponseBody
	public Object uploadheadimage(
				@RequestParam(value="uploanImage",required=false) MultipartFile file
				) throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="上传成功";
 		if(file != null){
			String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
			String filePath = "/cityFile";//文件上传路径
			String cityFilename =  FileUpload.fileUp(file, currentPath+filePath,BaseController.getTimeID());//字符拼接，上传到服务器上
			String m_img = AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
 			map.put("url", m_img);
 		}
		map.put("result", result);
 		map.put("message", message);
		return  map;
	}
	
	
	
	
	
	
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("2"));
		}
 	}
	/* ===============================权限================================== */
	
	
}
