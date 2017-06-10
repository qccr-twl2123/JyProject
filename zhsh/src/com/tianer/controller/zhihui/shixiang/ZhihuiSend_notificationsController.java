package com.tianer.controller.zhihui.shixiang;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import com.tianer.entity.system.Menu;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.entity.Page;
import com.tianer.util.AppUtil;
import com.tianer.util.JPushClientUtil;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.member.MemberService;
import com.tianer.service.business.operator_file.Operator_fileService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.send_notifications.Send_notificationsService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.business.store_file.Store_fileService;
import com.tianer.service.business.store_operator.Store_operatorService;
import com.tianer.service.business.subsidiary.SubsidiaryService;

/** 
 * 类名称：Send_notificationsController
 * 创建人：刘耀耀
 * 创建时间：2016-06-13
 */
@Controller
@RequestMapping(value="/zhihui_send_notifications")
public class ZhihuiSend_notificationsController extends BaseController {
	
	@Resource(name="send_notificationsService")
	private Send_notificationsService send_notificationsService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	@Resource(name="store_operatorService")
	private Store_operatorService store_operatorService;
	@Resource(name="subsidiaryService")
	private SubsidiaryService subsidiaryService;
	@Resource(name="store_fileService")
	private Store_fileService store_fileService;
	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
	@Resource(name="operator_fileService")
	private Operator_fileService operator_fileService;
	@Resource(name="clerk_fileService")
	private Clerk_fileService clerk_fileService;
	@Resource(name="memberService")
	private MemberService memberService;
	
	/**
	 * 新增
	 * 刘耀耀
	 * 2016.06.13
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Send_notifications");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		pd = this.getPageData();
		if(zhlogin != null){
			pd.put("operator_id", zhlogin.getLogin_id());
		}
		String content=pd.getString("content");
		//用户类型
 		String user_type1=pd.getString("user_type1");
		if(null != user_type1 && !"".equals(user_type1)){//子公司
			pd.put("user_type", user_type1);
			pd.put("send_notifications_id", BaseController.getTimeID());
			send_notificationsService.save(pd);
			//发送短信
			List<PageData> slist=subsidiaryService.listAll(pd);
			for(PageData e : slist){
//				SmsUtil.sendDx(e.getString("phone"), content);
			}
		}
		String user_type2=pd.getString("user_type2");
		if(null != user_type2 && !"".equals(user_type2)){//服务商
			pd.put("send_notifications_id", BaseController.getTimeID());
			pd.put("user_type", user_type2);
			send_notificationsService.save(pd);
			//发送短信
			List<PageData> slist=sp_fileService.listAll(pd);
			for(PageData e : slist){
//				SmsUtil.sendDx(e.getString("phone"), content);
			}
		}
		String user_type3=pd.getString("user_type3");
		if(null != user_type3 && !"".equals(user_type3)){//业务员
			pd.put("user_type", user_type3);
			pd.put("send_notifications_id", BaseController.getTimeID());
			send_notificationsService.save(pd);
			//发送短信
			List<PageData> slist=clerk_fileService.listAll(pd);
			for(PageData e : slist){
//				SmsUtil.sendDx(e.getString("phone"), content);
			}
		}
		String user_type4=pd.getString("user_type4");
		if(null != user_type4 && !"".equals(user_type4)){//商家
			pd.put("user_type", user_type4);
			pd.put("send_notifications_id", BaseController.getTimeID());
			send_notificationsService.save(pd);
			//发送短信
			List<PageData> slist=store_fileService.listAll(pd);
			for(PageData e : slist){
//				SmsUtil.sendDx(e.getString("phone"), content);
			}
		}
		String user_type5=pd.getString("user_type5");
		if(null != user_type5 && !"".equals(user_type5)){//会员
			pd.put("user_type", user_type5);
			pd.put("send_notifications_id",BaseController.getTimeID());
			send_notificationsService.save(pd);
			//发送短信
			List<PageData> slist=memberService.listAll(pd);
			for(PageData e : slist){
//				SmsUtil.sendDx(e.getString("phone"), content);
			}
		}
 		mv.setViewName("redirect:list.do");
		return mv;
	}
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Send_notifications");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			send_notificationsService.delete(pd);
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
		logBefore(logger, "修改Send_notifications");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		send_notificationsService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 审核
	 */
	@RequestMapping(value="/toExamine")
	public ModelAndView toExamine() throws Exception{
		logBefore(logger, "审核ToExamine");
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin review_id = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		if(review_id != null){
			pd = this.getPageData();
			pd.put("review_id", review_id.getLogin_id());
			send_notificationsService.toExamine(pd);
		}
		mv.setViewName("redirect:list.do");
		return mv;
	}
	
	/**
	 * 列表
	 * 刘耀耀
	 * 2016.06.13
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Send_notifications");
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
			String province_id = pd.getString("province_id");
			if(null != province_id && !"".equals(province_id)){
				province_id = province_id.trim();
				pd.put("province_id", province_id);
			}
			//市
			String city_id = pd.getString("city_id");
			if(null != city_id && !"".equals(city_id)){
				pd.put("city_id", city_id);
			}
			//区
			String area_id = pd.getString("area_id");
			if(null != area_id && !"".equals(area_id)){
				pd.put("area_id", area_id);
			}
			//开始时间
			String send_startdate = pd.getString("send_startdate");
			if(null != send_startdate && !"".equals(send_startdate)){
				pd.put("send_startdate", send_startdate);
			}
			//结束时间
			String send_enddate = pd.getString("send_enddate");
			if(null != send_enddate && !"".equals(send_enddate)){
				pd.put("send_enddate", send_enddate);
			}
			//开始时间段
			String send_starttime = pd.getString("send_starttime");
			if(null != send_starttime && !"".equals(send_starttime)){
				pd.put("send_starttime", send_starttime);
			}
			//结束时间段
			String send_endtime = pd.getString("send_endtime");
			if(null != send_endtime && !"".equals(send_endtime)){
				pd.put("send_endtime", send_endtime);
			}
			page.setPd(pd);
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			List<PageData>	varList = send_notificationsService.list(page);	//列出Send_notifications列表
			this.getHC(); //调用权限
			mv.setViewName("zhihui/shixiang/shixiang2");
			mv.addObject("provincelist", provincelist);
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 去新增页面(发送通知)
	 * 刘耀耀
	 * 2016.06.13
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Send_notifications页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.setViewName("zhihui/shixiang/shixiang3");
			mv.addObject("provincelist", provincelist);
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
		logBefore(logger, "去修改Send_notifications页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = send_notificationsService.findById(pd);	//根据ID读取
			mv.setViewName("business/send_notifications/send_notifications_edit");
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
		logBefore(logger, "批量删除Send_notifications");
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				send_notificationsService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Send_notifications到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户类型");	//1
			titles.add("精确用户（手机号）");	//2
			titles.add("是否是精确用户发送");	//3
			titles.add("省ID");	//4
			titles.add("市ID");	//5
			titles.add("区/县ID");	//6
			titles.add("发送开始时间");	//7
			titles.add("发送结束时间");	//8
			titles.add("发送开始时段");	//9
			titles.add("发送结束时段");	//10
			titles.add("发送内容");	//11
			titles.add("操作员ID");	//12
			titles.add("审核人员");	//13
			titles.add("创建时间");	//14
			dataMap.put("titles", titles);
			List<PageData> varOList = send_notificationsService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("USER_TYPE"));	//1
				vpd.put("var2", varOList.get(i).getString("PHONE"));	//2
				vpd.put("var3", varOList.get(i).getString("SEND"));	//3
				vpd.put("var4", varOList.get(i).getString("PROVINCE_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("CITY_ID"));	//5
				vpd.put("var6", varOList.get(i).getString("AREA_ID"));	//6
				vpd.put("var7", varOList.get(i).getString("SEND_STARTDATE"));	//7
				vpd.put("var8", varOList.get(i).getString("SEND_ENDDATE"));	//8
				vpd.put("var9", varOList.get(i).getString("SEND_STARTTIME"));	//9
				vpd.put("var10", varOList.get(i).getString("SEND_ENDTIME"));	//10
				vpd.put("var11", varOList.get(i).getString("CONTENT"));	//11
				vpd.put("var12", varOList.get(i).getString("OPERATOR_ID"));	//12
				vpd.put("var13", varOList.get(i).getString("REVIEW_ID"));	//13
				vpd.put("var14", varOList.get(i).getString("CREATEDATE"));	//14
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
		session.setAttribute("qx", map.get("32"));
	}
	/* ===============================权限================================== */
	
	

	 
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
