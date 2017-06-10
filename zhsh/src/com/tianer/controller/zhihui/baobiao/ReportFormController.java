package com.tianer.controller.zhihui.baobiao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.business.baobiao.ReportFormService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;


/** 
 * 
* 类名称：zhihuiCount_AllMoney   
* 类描述：   支付统计
* 创建人：魏汉文  
* 创建时间：2016年6月12日 下午4:15:39
 */
@Controller
@RequestMapping(value="/zhihuiReportForm")
public class ReportFormController extends BaseController{
	
	@Resource(name="reportFormService")
	private ReportFormService reportFormService;
	
	/**
	 * go积分收入查询
	 */
	@RequestMapping(value="/integralIncome")
	public ModelAndView IntegralIncome(Page page){
		//logBefore(logger, " go积分收入查询");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
			pd=this.getPageData();
			pd.put("nowpage", (page.getCurrentPage()-1)*10);
			page.setPd(pd);
			List<PageData> integraList = reportFormService.listIntegra(page);
			mv.addObject("integraList", integraList);
			integraList=null;
			PageData nowsumpd=reportFormService.nowintegraSumMoney(page);
			mv.addObject("nowsumpd", nowsumpd);
			nowsumpd=null;
			PageData allsumpd=reportFormService.allintegraSumMoney(page);
			mv.addObject("allsumpd", allsumpd);
			allsumpd=null;
			this.getHC27();
 		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		mv.setViewName("zhihui/baobiao/baobiao1");
		return mv;
	}
	
	
	
	/**
	 * go商家服务费
	 */
	@RequestMapping(value="/storeBond")
	public ModelAndView storeBond(Page page){
		//logBefore(logger, " go商家保障金");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd=this.getPageData();
		try {
			pd.put("nowpage", (page.getCurrentPage()-1)*10);
			page.setPd(pd);
			List<PageData> storeBondList = reportFormService.listService(page);
 			mv.addObject("storeBondList", storeBondList);
 			storeBondList=null;
 			PageData nowsumpd=reportFormService.nowsumserviceMoney(page);
 			mv.addObject("nowsumpd", nowsumpd);
 			nowsumpd=null;
 			PageData allsumpd=reportFormService.allserviceSumMoney(page);
			mv.addObject("allsumpd", allsumpd);
			allsumpd=null;
			this.getHC28();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		mv.setViewName("zhihui/baobiao/baobiao2");
		return mv;
	}
	
	
	
	/**
	 * go服务商保障金
	 */
	@RequestMapping(value="/serviceProviderBond")
	public ModelAndView serviceProviderBond(Page page){
		//logBefore(logger, " go服务商保障金");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd=this.getPageData();
		try {
			pd.put("nowpage", (page.getCurrentPage()-1)*10);
			page.setPd(pd);
			List<PageData> providerBondList = reportFormService.list(page);
 			mv.addObject("providerBondList", providerBondList);
 			providerBondList=null;
 			PageData nowsumpd=reportFormService.nowdatalistPage(page);
 			mv.addObject("nowsumpd", nowsumpd);
 			nowsumpd=null;
 			PageData allsumpd=reportFormService.alldataSumMoney(page);
			mv.addObject("allsumpd", allsumpd);
			allsumpd=null;
			this.getHC29();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		mv.setViewName("zhihui/baobiao/baobiao3");
		return mv;
	}
	
	
	/**
	 * 商家人脉推广收益报表
	 */
	@RequestMapping(value="/renmaiByStoreList")
	public ModelAndView renmaiByStoreList(Page page){
		//logBefore(logger, "商家人脉推广收益报表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd=this.getPageData();
		try {
			page.setPd(pd);
			List<PageData> systoreList=reportFormService.datalistPageStoreRenMai(page);
			mv.addObject("systoreList", systoreList);
			this.getHC55();
 		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		mv.setViewName("zhihui/baobiao/baobiao7");
		return mv;
	}
	
	
	/**
	 *商家人脉收益的详情
	 */
	@RequestMapping(value="/look_detail")
	public ModelAndView look_detail(){
		//logBefore(logger, "商家人脉收益的详情");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData> orderlist=reportFormService.allRenMaiJf_orderById(pd);
 			for( PageData e : orderlist){
				PageData e1=new PageData();
 				String user_type=e.getString("user_type");
				String payer_id=e.getString("user_id");
 				if(user_type.equals("1")){
					 e1.put("store_id", payer_id);
					 e1=ServiceHelper.getAppStoreService().findById(e1);
					 if(e1 != null){
						e.put("name", e1.getString("store_name"));
	 				 }else{
	 					e.put("name", "很抱歉，当前商家不存在");
	 				 }
					 
 				}else if(user_type.equals("2")){
					e1.put("member_id", payer_id);
					e1=ServiceHelper.getAppMemberService().findById(e1);
					if(e1 != null){
						e.put("name",e1.getString("name"));
 					}else{
 						e.put("name", "很抱歉，当前会员不存在");
 					 }
 				}else if(user_type.equals("3")){
					e1.put("sp_file_id", payer_id);
					e1=ServiceHelper.getSp_fileService().findById(e1);
					if(e1 != null){
						e.put("name", e1.getString("team_name"));
					}else{
 						e.put("name", "很抱歉，当前服务商不存在");
 					 }
  				}else if(user_type.equals("4")){
					e1.put("clerk_file_id", payer_id);
					e1=ServiceHelper.getClerk_fileService().findById(e1);
					if(e1 != null){
						e.put("name", e1.getString("clerk_name"));
					}else{
 						e.put("name", "很抱歉，当前业务员不存在");
 					 }
  				}
 				e1=null;
 				e1=new PageData();
 				String payee_number=e.getString("payee_number");//收款方
 				if(payee_number.equals("Jiuyu")){
					 e.put("payee_name", "九鱼平台");
 				}else{
 					 e1.put("store_id", payee_number);
 					 e1=ServiceHelper.getAppStoreService().findById(e1);
 					 if(e1 != null){
 						e.put("payee_name", e1.getString("store_name"));
 					 }else{
 						e.put("payee_name", "很抱歉，当前商家不存在");
 					 }
  				}
 				e1=null;
			}
    			mv.addObject("varList", orderlist);
 			mv.setViewName("zhihui/baobiao/baobiao7_detail");
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 商家经营分析报表
	 */
	@RequestMapping(value="/jingYingFenXiByStore")
	public ModelAndView jingYingFenXiByStore(Page page){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd=this.getPageData();
			page.setPd(pd);
			List<PageData> jystoreList=reportFormService.datalistPageStoreJJFX(page);
			int n=jystoreList.size();
			PageData e=null;
			for (int i = 0; i < n; i++) {
				e=jystoreList.get(i);
				if(pd.getString("starttime") != null && pd.getString("endtime") != null){
					pd.put("store_id", e.getString("store_id"));
					e.put("qjxxsale_money", reportFormService.timeByStartEndForXxSaleMoney(pd));
					e.put("qjxssale_money", reportFormService.timeByStartEndForXSSaleMoney(pd));
					e.put("qjsy_integer", reportFormService.timeByStartEndForIntegerSy(pd));
					e.put("qjzc_integer", reportFormService.timeByStartEndForIntegerZc(pd));
					e.put("qjcomment_number", reportFormService.timeByStartEndForCommentNumber(pd));
 					e.put("qjonecontact_number", reportFormService.timeByStartEndForContactNumber(pd));
 					e.put("qjcz_money", reportFormService.timeByStartEndForCzMoney(pd));
 					e.put("qjtxok_money", reportFormService.timeByStartEndForTxOkMoney(pd));
 					e.put("qjtxcheck_money", reportFormService.timeByStartEndForReadyTxMoney(pd));
				}
 			}
			mv.addObject("jystoreList", jystoreList);
			this.getHC56();
 		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		mv.setViewName("zhihui/baobiao/baobiao8");
		return mv;
	}
	
	
	
	
	/* ===============================异常待支付权限================================== */
	public void getHC56(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("56"));
		}
	}
	/* ===============================权限================================== */

	/* ===============================异常待支付权限================================== */
	public void getHC55(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("55"));
		}
 	}
	/* ===============================权限================================== */
	


	/* ===============================异常待支付权限================================== */
	public void getHC27(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("55"));
		}
 	}
	/* ===============================权限================================== */
	
	


	/* ===============================异常待支付权限================================== */
	public void getHC28(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("55"));
		}
 	}
	/* ===============================权限================================== */
	
	


	/* ===============================异常待支付权限================================== */
	public void getHC29(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("55"));
		}
 	}
	/* ===============================权限================================== */
	
	
	
	
	

}
