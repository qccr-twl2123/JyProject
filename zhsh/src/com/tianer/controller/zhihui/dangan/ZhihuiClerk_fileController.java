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
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;
import com.tianer.util.huanxin.HuanXin;

/** 
 * 
* 类名称：Clerk_fileController   
* 类描述：业务员档案   
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午5:17:58
 */
@Controller
@RequestMapping(value="/zhihui_clerk_file")
public class ZhihuiClerk_fileController extends BaseController {
	
	@Resource(name="clerk_fileService")
	private Clerk_fileService clerk_fileService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		//logBefore(logger, "新增Clerk_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//获取当前最大值得ID
			String id=clerk_fileService.getMaxId();
			if(id==null || id.equals("")  ){
				id="000001";
				pd.put("clerk_file_id", "YW000001");
			}else{
				//设置新增的ID
				id=id.substring(2);
				String nextId=StringUtil.getNextId(id);
				pd.put("clerk_file_id", "YW"+nextId);
			}
			String phone=pd.getString("phone");
			String clerk_name=pd.getString("clerk_name");
			String open_status=pd.getString("open_status");
			String password=this.get6UID();
			pd.put("password", password);
 	  		clerk_fileService.save(pd);
	  		if(open_status.equals("1")){//开启
	  		    //发送号码至业务永员
	 			SmsUtil.suiJiPassword(phone, password);
	 			//新增环信账号
	 			HuanXin.regirstHx(pd.getString("clerk_file_id"), pd.getString("clerk_file_id"), pd.getString("clerk_file_id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("新增业务员时候出错"+e.toString());
		}
  		mv.setViewName("redirect:../zhihui_clerk_file/list.do");
		return mv;
	}
	
	/**
	 * 删除
	 * 刘耀耀
	 * 2016.6.1
	 * @return 
	 */
	@RequestMapping(value="/delete")
	public ModelAndView delete(PrintWriter out){
		//logBefore(logger, "删除Clerk_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			clerk_fileService.delete(pd);
			mv.setViewName("redirect:../zhihui_clerk_file/list.do?currentPage="+pd.getString("currentPage"));
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		//logBefore(logger, "修改Clerk_file");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String phone=pd.getString("phone");
		String clerk_name=pd.getString("clerk_name");
		String open_status=pd.getString("open_status");//当前要更新的状态
		PageData e=new PageData();
		e=clerk_fileService.findById(pd);//更新前的状态
		clerk_fileService.edit(pd);
 		if(e.getString("open_status").equals("0") && open_status.equals("1")){//开启
 			///发送号码至操作员
 			SmsUtil.suiJiPassword(phone, e.getString("password"));
		}
		mv.setViewName("redirect:../zhihui_clerk_file/list.do?currentPage="+pd.getString("currentPage"));
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		//logBefore(logger, "列表Clerk_file");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//System.out.println(zhlogin.toString());
			//判断是否为服务商==&业务员员==2
			if(zhlogin != null && zhlogin.getMenu_role_id() != null){
				pd.put("login_cityname", zhlogin.getCity_name()); 
				pd.put("login_areaname", zhlogin.getArea_name());
				if(zhlogin.getMenu_role_id().equals("1")){
	 				pd.put("sp_file_id", zhlogin.getLogin_id());
 	 			}else if(zhlogin.getMenu_role_id().equals("2")){ 
					pd.put("clerk_file_id", zhlogin.getLogin_id());
 	  			} 
				page.setPd(pd);
				List<PageData>	varList = clerk_fileService.list(page);	//列出Clerk_file列表
				this.getHC(); //调用权限
				List<PageData> provincelist=pcdService.listAll(pd);//省list
				mv.addObject("provincelist", provincelist);
 				mv.addObject("varList", varList);
				mv.addObject("pd", pd);
 			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("zhihui/dangan/dangan7");
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		//logBefore(logger, "去新增Clerk_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
			//获取所有审核通过的服务商(或者是当前登录的服务商)
			pd.put("sp_status", "1");
			List<PageData> spList=sp_fileService.listAll(pd);
			mv.addObject("spList", spList);
			mv.setViewName("zhihui/dangan/dangan8");
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
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		//logBefore(logger, "去修改Clerk_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String currentPage=pd.getString("currentPage");
			pd = clerk_fileService.findById(pd);	//根据ID读取
			pd.put("currentPage", currentPage);
			//获取所有审核通过的服务商
			pd.put("sp_status", "1");
			List<PageData> spList=sp_fileService.listAll(pd);
			mv.addObject("spList", spList);
			mv.setViewName("zhihui/dangan/dangan8");
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
		//logBefore(logger, "批量删除Clerk_file");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				clerk_fileService.deleteAll(ArrayDATA_IDS);
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
		//logBefore(logger, "导出Clerk_file到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("名字");	//1
			titles.add("身份证号");	//2
			titles.add("电话");	//3
			titles.add("唯一标示ID");	//4
			titles.add("服务商ID");	//5
			titles.add("地址");	//6
			titles.add("开启状态默认0-关闭：0-关闭，1-开启");	//7
			titles.add("用户名");	//8
			titles.add("密码");	//9
			titles.add("创建时间");	//10
			dataMap.put("titles", titles);
			List<PageData> varOList = clerk_fileService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("CLERK_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("IDNUMBER"));	//2
				vpd.put("var3", varOList.get(i).getString("PHONE"));	//3
				vpd.put("var4", varOList.get(i).getString("CLERK_FILE_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("SP_FILE_ID"));	//5
				vpd.put("var6", varOList.get(i).getString("ADDRESS"));	//6
				vpd.put("var7", varOList.get(i).getString("OPEN_STATESTATUS"));	//7
				vpd.put("var8", varOList.get(i).getString("USERNAME"));	//8
				vpd.put("var9", varOList.get(i).getString("PASSWORD"));	//9
				vpd.put("var10", varOList.get(i).getString("CREATEDATE"));	//10
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
			session.setAttribute("qx", map.get("4"));
		}
 	}
	/* ===============================权限================================== */
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
