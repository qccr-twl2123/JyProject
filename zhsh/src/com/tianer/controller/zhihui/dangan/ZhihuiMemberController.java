 package com.tianer.controller.zhihui.dangan;

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
import com.tianer.entity.system.Menu;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.entity.Page;
import com.tianer.util.AppUtil;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.service.business.member.MemberService;
import com.tianer.service.business.pcd.PcdService;

/** 
 * 类名称：MemberController
 * 创建人：cyr
 * 创建时间：2016-05-25
 */
@Controller
@RequestMapping(value="/zhihui_member")
public class ZhihuiMemberController extends BaseController {
	
	@Resource(name="memberService")
	private MemberService memberService;
	@Resource(name="pcdService")
	private PcdService pcdService;
	
//	/**
//	 * 新增
//	 */
//	@RequestMapping(value="/save")
//	public ModelAndView save() throws Exception{
//		logBefore(logger, "新增Member");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		pd.put("member_id", BaseController.getTimeID());	//主键
//		memberService.save(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		return mv;
//	}
	
//	/**
//	 * 删除
//	 */
//	@RequestMapping(value="/delete")
//	public ModelAndView delete(Page page){
//		ModelAndView mv = this.getModelAndView();
//		logBefore(logger, "删除Member");
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			memberService.delete(pd);
//			//当前页
//			mv.setViewName("redirect:list.do?currentPage="+pd.getString("currentPage"));
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
	
//	/**
//	 * 修改
//	 */
//	@RequestMapping(value="/edit")
//	public ModelAndView edit() throws Exception{
//		logBefore(logger, "修改Member");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		memberService.edit(pd);
//		mv.addObject("msg","success");
//		mv.setViewName("save_result");
//		return mv;
//	}
	
	/**
	 * 列表
	 * 刘耀耀
	 * 20160606
	 * 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
//		logBefore(logger, "列表Member");
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
				//登陆用户的角色id:0-管理员，1-服务商，2-业务员，3-操作员，4-城市经理，5-子公司，6-其他
				if(zhlogin.getMenu_role_id().equals("1")){
					pd.put("area_name", zhlogin.getArea_name());
 					pd.put("isshenhe", "0");
				}else if(zhlogin.getMenu_role_id().equals("2")){
					pd.put("area_name", zhlogin.getArea_name());
 					pd.put("isshenhe", "0");
				}else{
					pd.put("isshenhe", "1");
				}
				//当前页
				String currentPage = pd.getString("currentPage");
				if(null != currentPage && !"".equals(currentPage)){
					page.setCurrentPage(Integer.parseInt(currentPage));
				}
				page.setPd(pd);
 				List<PageData>	varList = ServiceHelper.getAppMemberService().list(page);	//列出Member列表
 				PageData mmpd=null;
 				PageData e=null;
 				int n=varList.size();
 				for (int i = 0; i< n ;i++) {
  					e=varList.get(i);
					e.put("money_pay_number",ServiceHelper.getAppMemberService().nowpaynumber(e));
					e.put("wechat_pay_number",ServiceHelper.getAppMemberService().wxpaynumber(e));
					e.put("alipay_pay_number",ServiceHelper.getAppMemberService().alipaynumber(e));
					e.put("integral_pay_number",ServiceHelper.getAppMemberService().integralpaynumber(e));
					e.put("balance_pay_number",ServiceHelper.getAppMemberService().balancepaynumber(e));
					e.put("consumption",ServiceHelper.getAppMemberService().allsalemoneybyid(e));
 					String recommended_type=e.getString("recommended_type");
					String recommended=e.getString("recommended");
					mmpd=new PageData();
					if(recommended_type.equals("2")){
 						mmpd.put("member_id", recommended);
 						e.put("recommended_phone", ServiceHelper.getAppMemberService().findById(mmpd).getString("phone"));
 						e.put("recommended_name", ServiceHelper.getAppMemberService().findById(mmpd).getString("name"));
  					}else if(recommended_type.equals("1")){
  						mmpd.put("store_id", recommended);
 						e.put("recommended_phone", ServiceHelper.getAppStoreService().findById(mmpd).getString("registertel_phone"));
 						e.put("recommended_name", ServiceHelper.getAppStoreService().findById(mmpd).getString("store_name"));
   					}else{
   						e.put("recommended_phone", "/");
 						e.put("recommended_name","/");
   					}
				}
				this.getHC(); //调用权限
				mv.setViewName("zhihui/dangan/dangan13");
				mv.addObject("varList", varList);
				mv.addObject("pd", pd);
				mmpd=null;
				e=null;
				pd=null;
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
//	/**
//	 * 去新增页面
//	 */
//	@RequestMapping(value="/goAdd")
//	public ModelAndView goAdd(){
//		logBefore(logger, "去新增Member页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			mv.setViewName("business/member/member_edit");
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
//		logBefore(logger, "去修改Member页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			memberService.edit(pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}	
//		mv.setViewName("redirect:list.do");
//		return mv;
//	}	
	
//	
//	/**
//	 * 批量删除
//	 */
//	@RequestMapping(value="/deleteAll")
//	@ResponseBody
//	public Object deleteAll() {
//		logBefore(logger, "批量删除Member");
//		PageData pd = new PageData();		
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			pd = this.getPageData();
//			List<PageData> pdList = new ArrayList<PageData>();
//			String DATA_IDS = pd.getString("DATA_IDS");
//			if(null != DATA_IDS && !"".equals(DATA_IDS)){
//				String ArrayDATA_IDS[] = DATA_IDS.split(",");
//				memberService.deleteAll(ArrayDATA_IDS);
//				pd.put("msg", "ok");
//			}else{
//				pd.put("msg", "no");
//			}
//			pdList.add(pd);
//			map.put("list", pdList);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		} finally {
//			logAfter(logger);
//		}
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	/*
//	 * 导出到excel
//	 * @return
//	 */
//	@RequestMapping(value="/excel")
//	public ModelAndView exportExcel(){
//		logBefore(logger, "导出Member到excel");
//		ModelAndView mv = new ModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			List<String> titles = new ArrayList<String>();
//			titles.add("用户头像");	//1
//			titles.add("用户名称");	//2
//			titles.add("用户绑定电话号码");	//3
//			titles.add("用户会员等级");	//4
//			titles.add("用户魅力值");	//5
//			titles.add("用户余额");	//6
//			titles.add("用户积分");	//7
//			dataMap.put("titles", titles);
//			List<PageData> varOList = memberService.listAll(pd);
//			List<PageData> varList = new ArrayList<PageData>();
//			for(int i=0;i<varOList.size();i++){
//				PageData vpd = new PageData();
//				vpd.put("var1", varOList.get(i).getString("IMAGE_URL"));	//1
//				vpd.put("var2", varOList.get(i).getString("NAME"));	//2
//				vpd.put("var3", varOList.get(i).getString("PHONE"));	//3
//				vpd.put("var4", varOList.get(i).getString("VIP_LEVEL"));	//4
//				vpd.put("var5", varOList.get(i).getString("CHARM_NUMBER"));	//5
//				vpd.put("var6", varOList.get(i).getString("NOW_MONEY"));	//6
//				vpd.put("var7", varOList.get(i).getString("NOW_INTEGRAL"));	//7
//				varList.add(vpd);
//			}
//			dataMap.put("varList", varList);
//			ObjectExcelView erv = new ObjectExcelView();
//			mv = new ModelAndView(erv,dataMap);
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("3"));
		}
 	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
