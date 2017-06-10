package com.tianer.controller.zhihui.dangan;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.operator_file.Operator_fileService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.business.store.StoreService;
import com.tianer.service.business.store_file.Store_fileService;
import com.tianer.service.storepc.stotr.StorepcService;
import com.tianer.service.storepc.tableNumber.TablerNumberService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.FileUpload;
import com.tianer.util.MD5;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.ErWerMa.OneEr;
import com.tianer.util.huanxin.HuanXin;

/** 
 * 
* 类名称：ZhihuiStore_fileController   
* 类描述：商家档案   
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午5:45:24
 */
@Controller
@RequestMapping(value="/zhihuiz_store_file")
public class ZhihuiStore_fileController extends BaseController {
	
	@Resource(name="store_fileService")
	private Store_fileService store_fileService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
 
	
	/**
	 * 删除
	 * 魏汉文：20160606
 	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
//		//logBefore(logger, "删除商家档案");
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
	 * 魏汉文：20160606
	 * 
	 * 
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public Object edit() throws Exception{
//		//logBefore(logger, "修改商家档案");
		Map< String , Object> map=new HashMap<>();
		PageData pd = new PageData();
		String message="修改成功";
		String result="1";
//		String ok="1";
		try {
			pd = this.getPageData();
//			ok=pd.getString("ok");
			String store_name=pd.getString("store_name");
			if(!store_name.equals(store_fileService.findById(pd).getString("store_name"))){
				//生成二维码图片
				String imagename=pd.getString("store_id");
				String tuiguangUrl="http://www.jiuyuvip.com/zhsh/html_member/goRegister.do?recommended="+pd.getString("store_name")+"&recommended_type=1&recommended_phone="+pd.getString("store_name");
	 			OneEr.printStore(tuiguangUrl,  pd.getString("store_name") , imagename,Const.ErWeiMa);
	 			String path_url=Const.ErWeiMa+ "/"+imagename+".png";
//	 			是否需要将图片上传至云服务器
	 			String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
				String filePath = "/storeErFile";//文件上传路径
				String cityFilename =  FileUpload.fileUpFile(path_url, currentPath+filePath, imagename);//字符拼接，上传到服务器上
				path_url= AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
			}
 			store_fileService.edit(pd);
    	} catch (Exception e) {
			// TODO: handle exception
    		result="0";
    		message="系统错误";
		}
		map.put("result", result);
		map.put("message",  message);
		map.put("data", pd);
  		return map;
	}
	
	
	
	@Resource(name="storeService")
	private StoreService storeService;
	
	/**
	 * 修改审核状态
	 * 魏汉文：20160616
	 * 
	 */
	@RequestMapping(value="/checkedOk")
	@ResponseBody
	public Object checkedOk() throws Exception{
//		//logBefore(logger, "修改商家档案");
 		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
 		PageData pd = new PageData();
		String store_id="";
 		String result="1";
		try{
				pd = this.getPageData();
				String check_status=pd.getString("check_status");
   		 		pd=store_fileService.findById(pd);
 		 		if(pd != null  && zhlogin != null){
 		 				store_id=pd.getString("store_id");
		 				//选择商品的上限
  		 				String merchant_level=pd.getString("merchant_level");//星级
		 				PageData sortpd=store_fileService.getStartNumberForStore(pd);
		 				if(sortpd != null && !sortpd.equals("")){
		 					String onexing_money=sortpd.getString("onexing_money");
			 				String twoxing_money=sortpd.getString("twoxing_money");
			 				String threexing_money=sortpd.getString("threexing_money");
			 				if(merchant_level.equals("1")){
			 					pd.put("goods_max", onexing_money);
			 				}
			 				if(merchant_level.equals("2")){
			 					pd.put("goods_max", twoxing_money);
			 				}
			 				if(merchant_level.equals("3")){
			 					pd.put("goods_max", threexing_money);
			 				}
		 				}
		 				sortpd=null;
		 				//更新审核的操作人员ID以及商品上限，以及审核状态
		 				String password=BaseController.get6UID();
		 				pd.put("password", MD5.md5(password));
		 				pd.put("operate_id", zhlogin.getLogin_id());
		 				pd.put("check_status", check_status);
		 				storeService.edit(pd);
 		 				//注册环信
		 				HuanXin.regirstHx(pd.getString("store_id"), pd.getString("store_id"), pd.getString("store_id"));
 				 		//通过审核发送短信值商家的负责人
						SmsUtil.sendStore(pd.getString("registertel_phone"),pd.getString("store_id"),password);
		 		}
 		}catch(Exception e){
			logger.error(e.toString());
			result="0";
			ServiceHelper.getAppPcdService().saveLog(store_id, e.toString(),"商家审核出错");
		}
 		return result;
	}
	
	/**
	 * 列表
	 * 魏汉文2016.06.06
	 * 
	 * 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
//		//logBefore(logger, "列表商家档案");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			if(zhlogin != null && zhlogin.getMenu_role_id() != null){
				//登陆用户的角色id:0-管理员，1-服务商，2-业务员，3-操作员，4-其他
				pd.put("login_provincename", zhlogin.getProvince_name()); 
				pd.put("login_cityname", zhlogin.getCity_name()); 
				pd.put("login_areaname", zhlogin.getArea_name());
				if(zhlogin.getMenu_role_id().equals("1")){
	 				pd.put("sp_file_id", zhlogin.getLogin_id());
 	 			}else if(zhlogin.getMenu_role_id().equals("2")){ 
					pd.put("clerk_file_id", zhlogin.getLogin_id());
 	  			} 
				pd.put("check_status", "1");
				//设置分页
				String currentPage=pd.getString("currentPage");
				if(currentPage != null && !currentPage.equals("")){
					page.setCurrentPage(Integer.parseInt(currentPage));
				}
	 			page.setPd(pd);
 				List<PageData>	varList = store_fileService.list(page);	//列出Store_file列表
				this.getHC(); //调用权限
				mv.addObject("varList", varList);
			}
 			mv.setViewName("zhihui/dangan/dangan11");
 			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 *未审核的商家 列表
 	 */
	@RequestMapping(value="/notlist")
	public ModelAndView notlist(Page page){
//		//logBefore(logger, "列表商家档案");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
  		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			if(zhlogin != null && zhlogin.getMenu_role_id() != null){
				//登陆用户的角色id:0-管理员，1-服务商，2-业务员，3-操作员，4-其他
				pd.put("login_cityname", zhlogin.getCity_name()); 
				pd.put("login_areaname", zhlogin.getArea_name());
				pd.put("check_status", "0");
				String currentPage=pd.getString("currentPage");
				if(currentPage != null && !currentPage.equals("")){
					page.setCurrentPage(Integer.parseInt(currentPage));
				}
 	 			page.setPd(pd);
 				List<PageData>	varList = store_fileService.list(page);	//列出Store_file列表
				this.getHC3(); //调用权限
				mv.addObject("varList", varList);
			}
 			mv.setViewName("zhihui/dangan/dangan11_1");
 			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	
//	/*
//	 * 去新增页面
//	 * 魏汉文：20160606
//	 * 
//	 */
//	@RequestMapping(value="/goAdd")
//	public ModelAndView goAdd(){
//		//logBefore(logger, "去新增商家档案页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			//获取所有省档案的省
//			List<PageData> provincelist=city_fileService.listAllProvince(pd);
//			mv.addObject("provincelist", provincelist);
// 			mv.setViewName("zhihui/dangan/dangan12");
//			mv.addObject("msg", "save");
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}						
//		return mv;
//	}	
	
	@Resource(name="clerk_fileService")
	private Clerk_fileService clerk_fileService;
	
	@Resource(name = "tablerNumberService")
	private TablerNumberService tablerNumberService;
	
	
	/**
	 * 去修改页面
	 * 魏汉文：20160606
	 * 
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
//		//logBefore(logger, "去修改商家档案页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
			pd = this.getPageData();
			PageData beforpd=pd;
			mv.addObject("beforpd", beforpd);
 			pd = store_fileService.findById(pd);	//根据ID读取
 			//1级分类
 			pd.put("sort_type", "1");
			pd.put("sort_parent_id", "0");
			List<PageData> sortlist=city_fileService.listAllCitySort(pd);
			mv.addObject("sortlist", sortlist);
			sortlist=null;
			//服务商
			pd.put("sp_status", "1");
			List<PageData> splist=sp_fileService.listAll(pd);
			mv.addObject("splist", splist);
			splist=null;
			//获取当前商家的桌号
			List<PageData>	deskList = tablerNumberService.listAll(pd);//列出Store列表
			mv.addObject("deskList", deskList);
  			mv.setViewName("zhihui/dangan/dangan12");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	

	/**
	 * 获取业务员集合
	 */
	@RequestMapping(value="/getClerkList")
	@ResponseBody
	public Object getClerkList() {
//		//logBefore(logger, "获取业务员集合");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try { 
			//获取所有业务员
			pd=this.getPageData();
			pd.put("open_status", "1");
			List<PageData> clerklist=clerk_fileService.listAll(pd);
			map.put("clerklist", clerklist);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		//logBefore(logger, "批量删除Store_file");
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
//		//logBefore(logger, "导出Store_file到excel");
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
				vpd.put("var22", varOList.get(i).getString("//System_SERVICE_FEE"));	//22
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
 
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	//---------------------------------------------------------------------商家关系调整------------------------------------------------------------------
	/**
	 * 商家关系调整列表
	 * 魏汉文2016.06.07
	 * 
	 * 
	 */
	@RequestMapping(value="/listStoreRelations")
	public ModelAndView listStoreRelations(Page page){
//		//logBefore(logger, "列表商家档案");
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
				//登陆用户的角色id:0-管理员，1-服务商，2-业务员，3-操作员，4-其他
				pd.put("login_provincename", zhlogin.getProvince_name()); 
				pd.put("login_cityname", zhlogin.getCity_name()); 
				pd.put("login_areaname", zhlogin.getArea_name());
				if(zhlogin.getMenu_role_id().equals("1")){
	 				pd.put("sp_file_id", zhlogin.getLogin_id());
 	 			}else if(zhlogin.getMenu_role_id().equals("2")){ 
					pd.put("clerk_file_id", zhlogin.getLogin_id());
 	  			} 
				//当前页
				String currentPage = pd.getString("currentPage");
				if(null != currentPage && !"".equals(currentPage)){
					page.setCurrentPage(Integer.parseInt(currentPage));
				}
				page.setPd(pd);
 				List<PageData>	varList = store_fileService.StoreRelationslistPage(page);	//列出Store_file列表
				this.getHC2(); //调用权限
				mv.setViewName("zhihui/dangan/dangan14");
				mv.addObject("varList", varList);
				mv.addObject("pd", pd);
 			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 去商店所属服务商调整页面
	 * 魏汉文：20160606
	 * 
	 */
	@RequestMapping(value="/goUpdateStoreRelations")
	public ModelAndView goUpdateStoreRelations(){
//		//logBefore(logger, "去新增商家档案页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//获取所有省
			List<PageData> provincelist=pcdService.listAll(pd);
			mv.addObject("provincelist", provincelist);
 			mv.setViewName("zhihui/dangan/dangan15");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
   	
	
	/**
	 * 通过区获取所有服务商
	 * 魏汉文：20160608
	 * 
	 */
	@RequestMapping(value="/getSpListBycity")
	@ResponseBody
	public Object getSpListBycity(){
//		//logBefore(logger, " 通过区获取所有服务商");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		String result="01";
		pd = this.getPageData();
		try {
			List<PageData> spList=sp_fileService.getSpListBycity(pd);
			map.put("spList", spList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}		
		map.put("result", result);
 		return map;
	}	
	
 
	@Resource(name="operator_fileService")
	private Operator_fileService operator_fileService;
	

	/**
	 * 修改商家的服务商
	 * 魏汉文：20160608
	 */
	@RequestMapping(value="/editStoreSp")
	public ModelAndView editStoreSp() throws Exception{
//		//logBefore(logger, " 修改商家的服务商");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String allstoreid=pd.getString("allstoreid");//商店的Id
		String newsp_id=pd.getString("newsp_id");//新的服务商ID
 		String[] str=allstoreid.split("@");
		int n=str.length;
 		for(int i=0; i<str.length ; i++){
			//更新商家的服务商
//			logger.info("更新商家的服务商");
			pd.put("sp_file_id",newsp_id);
			pd.put("store_file_id", str[i]);
			store_fileService.editStoreSp(pd);
 		}
  		//获取当前服务商的最近的一个季度
		pd.put("sp_file_id", newsp_id);
		List<PageData> maxMonth=ServiceHelper.getService_performanceService().getMaxMonthBySpId(pd);
		if(maxMonth.size() >0){
			pd=maxMonth.get(0);
			if(pd.getString("actual_user_number") != null && !pd.getString("actual_user_number").equals("")){
				n=Integer.parseInt(pd.getString("actual_user_number"))+n;
			}
	 		pd.put("actual_user_number", n+"");
//			logger.info("更新实际用户指标");
			ServiceHelper.getService_performanceService().editspMonth(pd);
		}
 		mv.setViewName("redirect:listStoreRelations.do");
		return mv;
	}
	
	@Resource(name="storepcService")
	private StorepcService storepcService;
	
	/**
	 * 生成桌号二维码
	 */
	@RequestMapping(value="/isdesk_no")
	@ResponseBody
	public Object isdesk_no(){
//		//logBefore(logger, "判断桌号");
		Map< String , Object> map=new HashMap<>();
		String result="1";
		String message="桌号可生成";
		PageData pd = new PageData();
		try {
			   pd = this.getPageData();
 			   String desk_no=pd.getString("desk_no").trim();
			   String store_id=pd.getString("store_id");
			   PageData spd=storepcService.findById(pd);
			   //生成桌号二维码
				String store_name=spd.getString("store_name");
	 			String path_url=Const.ErWeiMa;
	 			String imageinfor=store_id+"@"+desk_no;//用于扫一扫
	 			OneEr.print(store_name,desk_no, imageinfor,path_url);
	 			path_url=path_url+ "/"+imageinfor+".png";
//	 			是否需要将图片上传至云服务器
	 			String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
				String filePath = "/opratorZFile";//文件上传路径
				String cityFilename =  FileUpload.fileUpFile(path_url, currentPath+filePath, imageinfor);//字符拼接，上传到服务器上
				String path_url2= AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
//				//System.out.println(path_url2);
		 		map.put("data", path_url2);
		 		//删除本地桌号
		 		FileUpload.deleteFile(path_url);
	 			//------------------------------------------------------------------
   		} catch (Exception e) {
			logger.error(e.toString(), e);
		}			
		map.put("result", result);
		map.put("message", message);
 		return map;
	}	
	
	
	/**
	 *重新定位
 	 */
	@RequestMapping(value="/dingwei")
	public ModelAndView dingwei(){
//		//logBefore(logger, "重新定位");
		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			mv.setViewName("zhihui/dangan/ditudingwei");
 			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	 
	/**
	 *修改定位
	 */
	@RequestMapping(value="/updatedingwei")
	public ModelAndView updatedingwei(){
//		//logBefore(logger, "修改定位");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			storeService.edit(pd);
 			mv.addObject("msg","success");
 			mv.setViewName("save_result");
 			mv.addObject("pd", pd);
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
			session.setAttribute("qx", map.get("6"));
		}
 	}
	/* ===============================权限================================== */
	
	

	/* ===============================权限================================== */
	public void getHC3(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("40"));
		}
 	}
	/* ===============================权限================================== */
	
	
	/* ===============================权限================================== */
	public void getHC2(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("8"));
		}
 	}
	/* ===============================权限================================== */
	
	

	
}
