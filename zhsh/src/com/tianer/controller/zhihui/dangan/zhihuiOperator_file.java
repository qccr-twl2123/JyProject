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
import com.tianer.entity.system.Menu;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.operator_file.Operator_fileService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.business.subsidiary.SubsidiaryService;
import com.tianer.service.business.system.SystemService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.MD5;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;
import com.tianer.util.huanxin.HuanXin;

/** 
 * 
* 类名称：zhihuiOperator_file   
* 类描述：操作员   
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午5:26:57
 */
@Controller
@RequestMapping(value="/zhihui_operator_file")
public class zhihuiOperator_file extends BaseController {
	
	@Resource(name="operator_fileService")
	private Operator_fileService operator_fileService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	/**
	 * 新增
	 * 魏汉文：20160606
	 * 
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增操作员");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String password=pd.getString("password");
	  		operator_fileService.save(pd);
	  		String operator_name=pd.getString("operator_name");
	 		String open_status=pd.getString("open_status");
	 		String phone=pd.getString("phone");
	  		if(open_status.equals("1")){
	 			//发送号码至操作员
	 			SmsUtil.suiJiPassword(phone, password);
	 			//新增环信账号
	 			HuanXin.regirstHx(pd.getString("operator_file_id"), pd.getString("operator_file_id"), pd.getString("operator_file_id"));
	 		}
	 		//--------------
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("修改操作员时候出错"+e.toString());
		}
 		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	/**
	 * 删除
	 * 魏汉文：20160606
	 * 
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除操作员");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			operator_fileService.delete(pd);
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
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改操作员");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();//修改后的信息
  			operator_fileService.edit(pd);
// 			PageData e=new PageData();
//			e=operator_fileService.findById(pd);//修改前的信息
//			String password=pd.getString("password");
//			String operator_name=pd.getString("operator_name");
//	  		String phone=pd.getString("phone");
//	  		if(e.getString("open_status").equals("0") && pd.getString("open_status").equals("1")){
//	  		//发送号码至操作员
//	 			SmsUtil.suiJiPassword(phone, password);
//	 		}
		} catch (Exception e) {
			// TODO: handle exception
		}
 		mv.setViewName("redirect:list.do?currentPage="+pd.getString("currentPage"));
		return mv;
	}
	
	/**
	 * 列表
	 * 魏汉文20160606
	 * 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表操作员");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			if(zhlogin != null && zhlogin.getMenu_role_id() != null){
				//0-管理员，1-服务商，2-业务员，3-操作员，4-城市经理，5-子公司，6-其他
 				pd.put("login_provincename", zhlogin.getProvince_name()); 
				pd.put("login_cityname", zhlogin.getCity_name()); 
				pd.put("login_areaname", zhlogin.getArea_name());
				if(zhlogin.getMenu_role_id().equals("1")){
	 				pd.put("operator_file_id", zhlogin.getLogin_id());
	 				pd.put("role_type", "2");
 	 			}else if(zhlogin.getMenu_role_id().equals("2")){
 	 				pd.put("role_type", "2");
 	 			}else if(zhlogin.getMenu_role_id().equals("4")){
 	 				pd.put("role_type", "2");
 	 			}else{
 	 				pd.put("role_type", "1");
 	 			}
				//当前页
				String currentPage = pd.getString("currentPage");
				if(null != currentPage && !"".equals(currentPage)){
					page.setCurrentPage(Integer.parseInt(currentPage));
				}
				page.setPd(pd);
				List<PageData>	varList = operator_fileService.list(page);	//列出Operator_file列表
				this.getHC(); //调用权限
				mv.setViewName("zhihui/dangan/dangan9");
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
	 * 魏汉文：20160606
	 * 
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增操作员页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
  		try {
  			pd = this.getPageData();
  		   //获取所有省档案的省
  			pd.put("open_status", "1");
			List<PageData> provincelist=city_fileService.listAllProvince(pd);
			mv.addObject("provincelist", provincelist);
			//获取所有角色
			List<PageData> roleList=systemService.roleListAll(pd);
			mv.addObject("roleList", roleList);
  			//获取当前最大值得ID
			String id=operator_fileService.getMaxId();
			if(id==null || id.equals("")  ){
				id="000001";
				pd.put("operator_file_id", "CZ"+id);
			}else{
				id=id.substring(2);
				//设置新增的ID
				String nextId=StringUtil.getNextId(id);
 				pd.put("operator_file_id", "CZ"+nextId);
			}
  			mv.setViewName("zhihui/dangan/dangan10");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	@Resource(name="systemService")
	private SystemService systemService;
	
	/**
	 * 去修改页面
	 * 魏汉文：20160606
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改操作员页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
 			 //获取所有省档案的省
  			pd.put("open_status", "1");
			List<PageData> provincelist=city_fileService.listAllProvince(pd);
			mv.addObject("provincelist", provincelist);
 			//获取所有角色
			List<PageData> roleList=systemService.roleListAll(pd);
			mv.addObject("roleList", roleList);
 			PageData oppd = operator_fileService.findById(pd);	//根据ID读取
 			mv.setViewName("zhihui/dangan/dangan10");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject("oppd", oppd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	

	
	/**
	 * 通过角色ID 获取分类
	 */
	@RequestMapping(value="/getPostByRole")
	@ResponseBody
	public Object getPostByRole() {
		logBefore(logger, " 通过角色ID 获取分类");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> list=new ArrayList<PageData>( );
		pd=this.getPageData();
		try { 
			String role_name=pd.getString("role_name");
			String role_id=pd.getString("role_id");
			if(role_name.contains(Const.role1) || Const.role1.contains(role_name)){
				
			}else if(role_name.contains(Const.role2) || Const.role2.contains(role_name)){
				//获取所有子公司
				list=subsidiaryService.listAllSub(pd);
 			}else if(role_name.contains(Const.role3) || Const.role3.contains(role_name)){
				//获取所有城市经理
				 
 			}else if(role_name.contains(Const.role4) || Const.role4.contains(role_name)){
				//获取所有服务商
				list=sp_fileService.listAllSp(pd);
 			}else if(role_name.contains(Const.role5) || Const.role5.contains(role_name)){
				//获取所有业务员
				list=clerk_fileService.listAllCr(pd);
 			}
  		} catch (Exception e) {
			logger.error(e.toString(), e);
		} 
		map.put("postlist", list);
		map.put("pd", pd);
		return map;
	}
	
	@Resource(name="subsidiaryService")
	private SubsidiaryService subsidiaryService;
	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
 	@Resource(name="clerk_fileService")
	private Clerk_fileService clerk_fileService;
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Operator_file");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				operator_fileService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Operator_file到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("操作员名称");	//1
			titles.add("身份证号");	//2
			titles.add("手机号");	//3
			titles.add("唯一标示ID");	//4
			titles.add("角色ID");	//5
			titles.add("职务");	//6
			titles.add("开启状态");	//7
			titles.add("初始密码");	//8
			titles.add("创建时间");	//9
			dataMap.put("titles", titles);
			List<PageData> varOList = operator_fileService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("OPERATOR_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("IDNUMBER"));	//2
				vpd.put("var3", varOList.get(i).getString("PHONE"));	//3
				vpd.put("var4", varOList.get(i).getString("OPERATOR_FILE_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("ROLE_ID"));	//5
				vpd.put("var6", varOList.get(i).getString("POST"));	//6
				vpd.put("var7", varOList.get(i).getString("OPEN_STATESTATUS"));	//7
				vpd.put("var8", varOList.get(i).getString("PASSWORD"));	//8
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
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("5"));
		}
 	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
