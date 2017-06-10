//package com.tianer.controller.zhihui.zhifu;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import sun.security.krb5.internal.PAData;
//
//import com.tianer.controller.base.BaseController;
//import com.tianer.entity.system.Menu;
//import com.tianer.entity.zhihui.zhihuiLogin;
//import com.tianer.entity.Page;
//import com.tianer.util.AppUtil;
//import com.tianer.util.Const;
//import com.tianer.util.ObjectExcelView;
//import com.tianer.util.PageData;
//import com.tianer.util.ServiceHelper;
//import com.tianer.util.SmsUtil;
//import com.tianer.service.app.AppMemberService;
//import com.tianer.service.app.AppStoreService;
//import com.tianer.service.business.clerk_file.Clerk_fileService;
//import com.tianer.service.business.pcd.PcdService;
//import com.tianer.service.business.sp_file.Sp_fileService;
//import com.tianer.service.business.withdraw_approval.Withdraw_approvalService;
//import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
//
///** 
// * 类名称：提现审批
// * 创建人：刘耀耀
// * 创建时间：2016-05-26
// */
//@Controller
//@RequestMapping(value="/zhihui_withdraw_approval")
//public class ZhihuiWithdraw_approvalController extends BaseController {
//	
//	@Resource(name="withdraw_approvalService")
//	private Withdraw_approvalService withdraw_approvalService;
//	@Resource(name="pcdService")
//	private PcdService pcdService;
//	@Resource(name="appMemberService")
//	private AppMemberService appMemberService;
//	@Resource(name="appStoreService")
//	private AppStoreService appStoreService;
//	@Resource(name="storeManageService")
//	private StoreManageService storeManageService;
// 
//	/**
//	 * 支付宝待支付
//	 * 20160612
//	 * 
//	 */
//	@RequestMapping(value="/listAlipay")
//	public ModelAndView listAlipay(Page page){
//		logBefore(logger, "支付宝待支付");
//		ModelAndView mv = this.getModelAndView();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
// 			pd.put("account_type", "3");
//			pd.put("withdraw_status", "1");
//  			page.setPd(pd);
//			List<PageData> provicelist=pcdService.listAll(pd);//省list
//			this.getHC2();
//			mv.addObject("provicelist", provicelist);
//			provicelist=null;
//			String allMoney=withdraw_approvalService.sunMoney(page);
//			pd.put("allMoney", allMoney);
//			String allMoneyTwo=withdraw_approvalService.sunMoneyTwo(page);
//			pd.put("allMoneyTwo", allMoneyTwo);
//			List<PageData>	varList = withdraw_approvalService.datalistPage(page);	//列出Withdraw_approval列表
// 			for(PageData e : varList){
//				PageData nowpd=new PageData();
//				String w_user_type=e.getString("user_type");
//				String w_user_id=e.getString("user_id");
//				String money="0";
//				String w_user_name="";
//				if(w_user_type.equals("1")){
//					nowpd.put("store_id", w_user_id);
//					//获取商家的财富
//					nowpd.put("wealth_type", "2");
//					nowpd=appStoreService.findWealthById(nowpd);
//					if(nowpd != null){
//						double now_wealth=Double.parseDouble(nowpd.getString("now_wealth"));
//						nowpd=null;
//						nowpd=new PageData();
//						nowpd.put("wealth_type", "1");
//						nowpd.put("store_id", w_user_id);
//						nowpd=appStoreService.findWealthById(nowpd);
//						if(nowpd != null){
//							now_wealth+=Double.parseDouble(nowpd.getString("now_wealth"));
//							w_user_name=nowpd.getString("store_name");
//						}
//						money=df.format(now_wealth);
// 					}
// 				}else if(w_user_type.equals("2")){
// 					nowpd.put("member_id", w_user_id);
// 					nowpd=appMemberService.findById(nowpd);
// 					if(nowpd != null){
// 						money=nowpd.getString("now_money");
// 						w_user_name=nowpd.getString("name");
// 					}
// 					
//				}else if(w_user_type.equals("3")){
//					nowpd.put("sp_file_id", w_user_id);
//					nowpd=sp_fileService.findById(nowpd);
//					if(nowpd != null){
// 						money=nowpd.getString("nowmoney");
// 						w_user_name=nowpd.getString("team_name");
// 					}
//					
//				}else if(w_user_type.equals("4")){
//					nowpd.put("clerk_file_id", w_user_id);
//					nowpd=clerk_fileService.findById(nowpd);
//					if(nowpd != null){
// 						money=nowpd.getString("nowmoney");
// 						w_user_name=nowpd.getString("clerk_name");
// 					}
//				
//				}
//				e.put("nowmoney", money);
//				e.put("user_name", w_user_name);
//				nowpd=null;
//			}
//  			mv.setViewName("zhihui/zhifu/zhifu10");
//			mv.addObject("varList", varList);
//			varList=null;
// 			mv.addObject("pd", pd);
// 			pd=null;
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	/**
//	 * 银行卡待支付
//	 * 20160612
//	 * 
//	 */
//	@RequestMapping(value="/listBank")
//	public ModelAndView listBank(Page page){
//		logBefore(logger, "银行卡待支付");
//		ModelAndView mv = this.getModelAndView();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pd.put("account_type", "4");
//			pd.put("withdraw_status", "1");
//			page.setPd(pd);
//			List<PageData> provicelist=pcdService.listAll(pd);//省list
//			this.getHC3();
//			mv.addObject("provicelist", provicelist);
//			provicelist=null;
//			String allMoney=withdraw_approvalService.sunMoney(page);
//			pd.put("allMoney", allMoney);
//			String allMoneyTwo=withdraw_approvalService.sunMoneyTwo(page);
//			pd.put("allMoneyTwo", allMoneyTwo);
//			List<PageData>	varList = withdraw_approvalService.datalistPage(page);	//列出Withdraw_approval列表
// 			for(PageData e : varList){
//				PageData nowpd=new PageData();
//				String w_user_type=e.getString("user_type");
//				String w_user_id=e.getString("user_id");
//				String w_user_name="";
//				String money="0";
//				if(w_user_type.equals("1")){
//					nowpd.put("store_id", w_user_id);
//					//获取商家的财富
//					nowpd.put("wealth_type", "2");
//					nowpd=appStoreService.findWealthById(nowpd);
//					if(nowpd != null){
//						double now_wealth=Double.parseDouble(nowpd.getString("now_wealth"));
//						nowpd=null;
//						nowpd=new PageData();
//						nowpd.put("wealth_type", "1");
//						nowpd.put("store_id", w_user_id);
//						nowpd=appStoreService.findWealthById(nowpd);
//						if(nowpd != null){
//							now_wealth+=Double.parseDouble(nowpd.getString("now_wealth"));
//							w_user_name=nowpd.getString("store_name");
//						}
//						money=df.format(now_wealth);
//					}
//				}else if(w_user_type.equals("2")){
//					nowpd.put("member_id", w_user_id);
//					nowpd=appMemberService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("now_money");
//						w_user_name=nowpd.getString("name");
//					}
//				}else if(w_user_type.equals("3")){
//					nowpd.put("sp_file_id", w_user_id);
//					nowpd=sp_fileService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("nowmoney");
//						w_user_name=nowpd.getString("team_name");
//					}
//				}else if(w_user_type.equals("4")){
//					nowpd.put("clerk_file_id", w_user_id);
//					nowpd=clerk_fileService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("nowmoney");
//						w_user_name=nowpd.getString("clerk_name");
//					}
//				}
//				e.put("nowmoney", money);
//				e.put("user_name", w_user_name);
//				nowpd=null;
//			}
//			mv.setViewName("zhihui/zhifu/zhifu11");
//			mv.addObject("varList", varList);
//			varList=null;
//			mv.addObject("pd", pd);
//			pd=null;
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	/**
//	 * 转异常待支付
//	 * 20160612
//	 * 
//	 */
//	@RequestMapping(value="/listException")
//	public ModelAndView listException(Page page){
//		logBefore(logger, "银行卡待支付");
//		ModelAndView mv = this.getModelAndView();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
// 			pd.put("withdraw_status", "2");
//			page.setPd(pd);
// 			List<PageData> operatorList = storeManageService.operatorList(pd);
//			mv.addObject("operatorList", operatorList);
//			operatorList=null;
//			this.getHC4();
//			List<PageData> provicelist=pcdService.listAll(pd);//省list
//			mv.addObject("provicelist", provicelist);
//			provicelist=null;
//			String allMoney=withdraw_approvalService.sunMoney(page);
//			pd.put("allMoney", allMoney);
//			String allMoneyTwo=withdraw_approvalService.sunMoneyTwo(page);
//			pd.put("allMoneyTwo", allMoneyTwo);
//			List<PageData>	varList = withdraw_approvalService.datalistPage(page);	//列出Withdraw_approval列表
//			for(PageData e : varList){
//				PageData nowpd=new PageData();
//				String w_user_type=e.getString("user_type");
//				String w_user_id=e.getString("user_id");
//				String w_user_name="";
//				String money="0";
//				if(w_user_type.equals("1")){
//					nowpd.put("store_id", w_user_id);
//					//获取商家的财富
//					nowpd.put("wealth_type", "2");
//					nowpd=appStoreService.findWealthById(nowpd);
//					if(nowpd != null){
//						double now_wealth=Double.parseDouble(nowpd.getString("now_wealth"));
//						nowpd=null;
//						nowpd=new PageData();
//						nowpd.put("wealth_type", "1");
//						nowpd.put("store_id", w_user_id);
//						nowpd=appStoreService.findWealthById(nowpd);
//						if(nowpd != null){
//							now_wealth+=Double.parseDouble(nowpd.getString("now_wealth"));
//							w_user_name=nowpd.getString("store_name");
//						}
//						money=df.format(now_wealth);
//					}
//				}else if(w_user_type.equals("2")){
//					nowpd.put("member_id", w_user_id);
//					nowpd=appMemberService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("now_money");
//						w_user_name=nowpd.getString("name");
//					}
//				}else if(w_user_type.equals("3")){
//					nowpd.put("sp_file_id", w_user_id);
//					nowpd=sp_fileService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("nowmoney");
//						w_user_name=nowpd.getString("team_name");
//					}
//				}else if(w_user_type.equals("4")){
//					nowpd.put("clerk_file_id", w_user_id);
//					nowpd=clerk_fileService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("nowmoney");
//						w_user_name=nowpd.getString("clerk_name");
//					}
//				}
//				e.put("nowmoney", money);
//				e.put("user_name", w_user_name);
//				nowpd=null;
//			}
// 			mv.setViewName("zhihui/zhifu/zhifu12");
//			mv.addObject("varList", varList);
//			varList=null;
//			mv.addObject("pd", pd);
//			pd=null;
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	
//	@Resource(name="clerk_fileService")
//	private Clerk_fileService clerk_fileService;
//	
//	@Resource(name="sp_fileService")
//	private Sp_fileService sp_fileService;
//	
//	
//	/**
//	 * 提现分控审核魏汉文
//	 * 20160612
//	 */
//	@RequestMapping(value="/list")
//	public ModelAndView list(Page page){
//		logBefore(logger, "提现分控审核");
//		ModelAndView mv = this.getModelAndView();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pd.put("withdraw_status", "0");
// 			page.setPd(pd);
//			List<PageData> provicelist=pcdService.listAll(pd);//省list
//			mv.addObject("provicelist", provicelist);
//			provicelist=null;
//			this.getHC();
//			String allMoney=withdraw_approvalService.sunMoney(page);
//			pd.put("allMoney", allMoney);
//			String allMoneyTwo=withdraw_approvalService.sunMoneyTwo(page);
//			pd.put("allMoneyTwo", allMoneyTwo);
//			List<PageData>	varList = withdraw_approvalService.datalistPage(page);	//列出Withdraw_approval列表
// 			for(PageData e : varList){
//				PageData nowpd=new PageData();
//				String w_user_type=e.getString("user_type");
//				String w_user_id=e.getString("user_id");
//				String w_user_name="";
//				String money="0";
//				if(w_user_type.equals("1")){
//					nowpd.put("store_id", w_user_id);
//					//获取商家的财富
//					nowpd.put("wealth_type", "2");
//					nowpd=appStoreService.findWealthById(nowpd);
//					if(nowpd != null){
//						double now_wealth=Double.parseDouble(nowpd.getString("now_wealth"));
//						nowpd=null;
//						nowpd=new PageData();
//						nowpd.put("wealth_type", "1");
//						nowpd.put("store_id", w_user_id);
//						nowpd=appStoreService.findWealthById(nowpd);
//						if(nowpd != null){
//							now_wealth+=Double.parseDouble(nowpd.getString("now_wealth"));
//							w_user_name=nowpd.getString("store_name");
//						}
//						money=df.format(now_wealth);
//					}
//				}else if(w_user_type.equals("2")){
//					nowpd.put("member_id", w_user_id);
//					nowpd=appMemberService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("now_money");
//						w_user_name=nowpd.getString("name");
//					}
//				}else if(w_user_type.equals("3")){
//					nowpd.put("sp_file_id", w_user_id);
//					nowpd=sp_fileService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("nowmoney");
//						w_user_name=nowpd.getString("team_name");
//					}
//				}else if(w_user_type.equals("4")){
//					nowpd.put("clerk_file_id", w_user_id);
//					nowpd=clerk_fileService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("nowmoney");
//						w_user_name=nowpd.getString("clerk_name");
//					}
//				}
//				e.put("nowmoney", money);
//				e.put("user_name", w_user_name);
//				nowpd=null;
//			}
//			mv.setViewName("zhihui/zhifu/zhifu9");
//			mv.addObject("varList", varList);
//			varList=null;
//			mv.addObject("pd", pd);
//			pd=null;
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	
//	/**
//	 * 魏汉文
//	 * 20160612
//	 * 提现处理完毕
//	 */
//	@RequestMapping(value="/listCash")
//	public ModelAndView listCash(Page page){
//		logBefore(logger, "列表Withdraw_approval");
//		ModelAndView mv = this.getModelAndView();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pd.put("chuli", "1");
//			page.setPd(pd);
//			List<PageData> provicelist=pcdService.listAll(pd);//省list
//			String allMoney=withdraw_approvalService.sunMoney(page);
//			pd.put("allMoney", allMoney);
//			String allMoneyTwo=withdraw_approvalService.sunMoneyTwo(page);
//			pd.put("allMoneyTwo", allMoneyTwo);
//			this.getHC1();
//			mv.addObject("provicelist", provicelist);
//			provicelist=null;
//			List<PageData>	varList = withdraw_approvalService.datalistPage(page);	//列出Withdraw_approval列表
//			for(PageData e : varList){
//				PageData nowpd=new PageData();
//				String w_user_type=e.getString("user_type");
//				String w_user_id=e.getString("user_id");
//				String w_user_name="";
//				String money="0";
//				if(w_user_type.equals("1")){
//					nowpd.put("store_id", w_user_id);
//					//获取商家的财富
//					nowpd.put("wealth_type", "2");
//					nowpd=appStoreService.findWealthById(nowpd);
//					if(nowpd != null){
//						double now_wealth=Double.parseDouble(nowpd.getString("now_wealth"));
//						nowpd=null;
//						nowpd=new PageData();
//						nowpd.put("wealth_type", "1");
//						nowpd.put("store_id", w_user_id);
//						nowpd=appStoreService.findWealthById(nowpd);
//						if(nowpd != null){
//							now_wealth+=Double.parseDouble(nowpd.getString("now_wealth"));
//							w_user_name=nowpd.getString("store_name");
//						}
//						money=df.format(now_wealth);
//					}
//				}else if(w_user_type.equals("2")){
//					nowpd.put("member_id", w_user_id);
//					nowpd=appMemberService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("now_money");
//						w_user_name=nowpd.getString("name");
//					}
//				}else if(w_user_type.equals("3")){
//					nowpd.put("sp_file_id", w_user_id);
//					nowpd=sp_fileService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("nowmoney");
//						w_user_name=nowpd.getString("team_name");
//					}
//				}else if(w_user_type.equals("4")){
//					nowpd.put("clerk_file_id", w_user_id);
//					nowpd=clerk_fileService.findById(nowpd);
//					if(nowpd != null){
//						money=nowpd.getString("nowmoney");
//						w_user_name=nowpd.getString("clerk_name");
//					}
//				}
//				e.put("nowmoney", money);
//				e.put("user_name", w_user_name);
//				nowpd=null;
//			}
//  			mv.setViewName("zhihui/zhifu/zhifu13");
//			mv.addObject("varList", varList);
//			varList=null;
// 			mv.addObject("pd", pd);
// 			pd=null;
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	
//	/**
//	 * 批量通过或者批量转为异常
//	 */
//	@RequestMapping(value="/updateAll")
//	@ResponseBody
//	public Object updateAll() {
//		logBefore(logger, " 批量通过或者批量转为异常");
//		PageData pd = new PageData();	
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		List<String> withidlist=new ArrayList<String>();//用来存储商家List
// 		//shiro管理的session
//		Subject currentUser = SecurityUtils.getSubject();  
//		Session session = currentUser.getSession();
//		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
//		String result = "01";
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			if(zhlogin == null){
//				map.put("result", "00");
//				return map;
//			}
//			String operator_id=zhlogin.getLogin_id();
//			pd = this.getPageData();
// 			String abno = pd.getString("abno");
//			String withdraw_status = pd.getString("withdraw_status");
//			String exception_status = pd.getString("exception_status");
// 			if(null != abno && !"".equals(abno)){
//				String ArrayApproval_id[] = abno.split(",");
// 				if(withdraw_status.equals("1")){//通过
// 					Collections.addAll(withidlist, ArrayApproval_id);
//					pd.put("array", ArrayApproval_id);
//					withdraw_approvalService.updateAll(pd);
// 				}else{//转为异常
//					for (int i = 0; i < ArrayApproval_id.length; i++) {
//						String[] arry_all=ArrayApproval_id[i].split("@");
//						withidlist.add(arry_all[1]);
//						PageData pg = new PageData();
// 						pg.put("withdraw_approval_id", arry_all[1]);
//						pg.put("withdraw_status", withdraw_status);
//						pg.put("exception_status", exception_status);
//						if(withdraw_status.equals("2")){
//							pg.put("exception_remark", arry_all[0]);
//							pg.put("operator_id", operator_id);
//						}else if(withdraw_status.equals("3")){
//							pg.put("dakuan_remark", arry_all[0]);
//							pg.put("passmoney_operator_id", operator_id);
//						}else if(withdraw_status.equals("99")){
//							pg.put("remark", arry_all[0]);
//							pg.put("passmoney_operator_id", operator_id);
//						}
// 						withdraw_approvalService.updateForId(pg);
//						pg=null;
//						arry_all=null;
//						
//	   				}
// 				}
//				ArrayApproval_id=null;
//   			} 
//  			for (int i = 0; i < withidlist.size(); i++) {
//  				PageData withpd=new PageData();
//				withpd.put("withdraw_approval_id", withidlist.get(i));
// 				try {
// 					//获取提现的详情
//			 		withpd=withdraw_approvalService.findById(withpd);
//			 		String user_type=withpd.getString("user_type");
//			 		String user_id=withpd.getString("user_id");
//			 		String money=withpd.getString("money");
//			 		PageData e=new PageData();
//					if(user_type.equals("1")){
// 		 				//获取商家的财富
// 						e.put("wealth_type", "2");
// 						e.put("store_id", user_id);
// 						e=appStoreService.findWealthById(e);
// 						if(e != null ){
// 							if(withdraw_status.equals("3")){
// 	 							//修改状态
// 								withpd.put("process_status", "1");
// 								appStoreService.editWealthHistoryStatusTwo(withpd);
//  								//更新提现信息
// 	 							String tixian_money=e.getString("tixian_money");
// 	 							if(Double.parseDouble(tixian_money)-Double.parseDouble(money) <= 0){
// 	 								e.put("tixian_money", "0");
// 	 							}else{
// 	 								e.put("tixian_money", df.format(Double.parseDouble(tixian_money)-Double.parseDouble(money)));
// 	 							}
// 	 							appStoreService.edit(e);
// 	 							//通过发送消息
// 								SmsUtil.TixianPass(appStoreService.findById(e).getString("registertel_phone"), money, appStoreService.findById(e).getString("store_name"));
// 							}
//  						}
// 					}else if(user_type.equals("2")){
//	 						e.put("member_id", user_id);
//	 						e=appMemberService.findById(e);
//	 						if(e != null ){
//	 							if(withdraw_status.equals("3")){
//	 								//通过发送消息
//			 						e.put("jiaoyi_id",withidlist.get(i));
//			 						e.put("jiaoyi_status", "1");
//			 						ServiceHelper.getAppMember_wealthhistoryService().updateWealthhistory(e);
// 	 								SmsUtil.TixianPass(e.getString("phone"), money,e.getString("name"));
//	 							}
//	 						}
//	 				}else if(user_type.endsWith("3")){
//	 					e.put("sp_file_id", user_id);
//	 					e=sp_fileService.findById(e);
//	 					if(e != null ){
//		 					if(withdraw_status.equals("3")){
//		 						//通过发送消息
//		 						e.put("service_performance_id",  withidlist.get(i));
//		 						e.put("isjihuo", "1");
//		 						ServiceHelper.getService_performanceService().edit(e);
//  								SmsUtil.TixianPass(e.getString("phone"), money,e.getString("team_name"));
// 							}
//		 				}
//	 				}else if(user_type.endsWith("4")){
//	 					e.put("clerk_file_id", user_id);
//	 					e=clerk_fileService.findById(e);
//	 					if(e != null ){
//	 						 if(withdraw_status.equals("3")){
//	 							e.put("service_performance_id",  withidlist.get(i));
//		 						e.put("isjihuo", "1");
//		 						ServiceHelper.getService_performanceService().edit(e);
//	 							//通过发送消息
// 								SmsUtil.TixianPass(e.getString("phone"), money,e.getString("clerk_name"));
// 							}
//	 					}
//	 				}
//					e=null;
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println(e.toString());
//				}	
// 				withpd=null;
// 			}
//  			withidlist=null;
// 		} catch (Exception e) {
//			result = "00";
//			logger.error(e.toString(), e);
//		} 
//		map.put("result", result);
//		return map;
//	}
//	
//	/**
//	 * 异常数据，通过或是驳回
//	 */
//	@RequestMapping(value="/updateById")
//	@ResponseBody
//	public Object updateById() {
//		logBefore(logger, " 异常数据，通过或是驳回 ");
//		PageData pd = new PageData();	
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		//shiro管理的session
//		Subject currentUser = SecurityUtils.getSubject();  
//		Session session = currentUser.getSession();
//		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
//		String result = "01";
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//				if(zhlogin == null){
//					map.put("result", "00");
//					return map;
//				}
//				String operator_id=zhlogin.getLogin_id();
//				pd = this.getPageData();
//				String withdraw_status=pd.getString("withdraw_status");
//				pd.put("exception_operator_id", operator_id);
//				withdraw_approvalService.updateForId(pd);
//  				try {
//		 					//获取提现的详情
////  							String chuli_remark=pd.getString("chuli_remark");
//		 			 		pd=withdraw_approvalService.findById(pd);
// 		 			 		String user_type=pd.getString("user_type");
//		 			 		String user_id=pd.getString("user_id");
//		 			 		String money=pd.getString("money");
//		 			 		PageData e=new PageData();
//		 					if(user_type.equals("1")){
//			 		 				//获取商家的财富
//			 						e.put("wealth_type", "1");
//			 						e.put("store_id", user_id);
//			 						e=appStoreService.findWealthById(e);
//			 						if(e != null ){
//			 							if( withdraw_status.equals("99")){
//			 								double now_wealth=Double.parseDouble(e.getString("now_wealth"));
//			 								//减少商家积分余额
//			 								double n=now_wealth+Double.parseDouble(money);
//			 								e.put("now_wealth", df.format(n));
//			 								appStoreService.editWealthById(e);
//			 								//修改状态
//			 								pd.put("process_status", "99");
//			 								appStoreService.editWealthHistoryStatusTwo(pd);
//			 								//更新提现信息
//			 	 							String tixian_money=e.getString("tixian_money");
//			 	 							if(Double.parseDouble(tixian_money)-Double.parseDouble(money) <= 0){
//			 	 								e.put("tixian_money", "0");
//			 	 							}else{
//			 	 								e.put("tixian_money", df.format(Double.parseDouble(tixian_money)-Double.parseDouble(money)));
//			 	 							}
//			 	 							appStoreService.edit(e);
//			 								//驳回发送消息
//			 								SmsUtil.TixianNotPass(appStoreService.findById(e).getString("registertel_phone"), pd.getString("chuli_remark"));
//			 	 						} 
//			 						}
//			 					}else if(user_type.equals("2")){
//				 						e.put("member_id", user_id);
//				 						e=appMemberService.findById(e);
//				 						if(e != null ){
//				 							if(withdraw_status.equals("99")){
//				 								//新增用户积分余额
//				 								double now_money=Double.parseDouble(e.getString("now_money"));
//				 								double n=now_money+Double.parseDouble(money);
//				 								e.put("now_money", df.format(n));
//				 								appMemberService.edit(e);
//				 								//驳回处理
//						 						e.put("jiaoyi_id", pd.getString("withdraw_approval_id"));
//						 						e.put("jiaoyi_status", "99");
//						 						ServiceHelper.getAppMember_wealthhistoryService().updateWealthhistory(e);
// 				 								//驳回发送消息
//				 								SmsUtil.TixianNotPass(e.getString("phone"), pd.getString("chuli_remark"));
//				 	 						} 
//				 						}
//				 				}else if(user_type.endsWith("3")){
//				 					e.put("sp_file_id", user_id);
//				 					e=sp_fileService.findById(e);
//				 					if(e != null ){
//					 					if(withdraw_status.equals("99")){
//					 						//新增服务商积分余额
//					 						double now_money=Double.parseDouble(e.getString("nowmoney"));
//					 						double n=now_money+Double.parseDouble(money);
//					 						e.put("now_money", df.format(n));
//					 						sp_fileService.edit(e);
//					 						//驳回处理
//					 						e.put("service_performance_id", pd.getString("withdraw_approval_id"));
//					 						e.put("isjihuo", "99");
//					 						ServiceHelper.getService_performanceService().edit(e);
//					 						//驳回发送消息
//			 								SmsUtil.TixianNotPass(e.getString("phone"), pd.getString("chuli_remark"));
//					 	 				} 
//					 				}
//				 				}else if(user_type.endsWith("4")){
//				 					e.put("clerk_file_id", user_id);
//				 					e=clerk_fileService.findById(e);
//				 					if(e != null ){
//				 						if(withdraw_status.equals("99")){
//				 							//新增业务员余额
//				 							double nowmoney=Double.parseDouble(e.getString("nowmoney"));
//				 							double n=nowmoney+Double.parseDouble(money);
//				 							e.put("nowmoney", df.format(n));
//				 							clerk_fileService.edit(e);
//				 							//驳回处理
//					 						e.put("service_performance_id", pd.getString("withdraw_approval_id"));
//					 						e.put("isjihuo", "99");
//					 						ServiceHelper.getService_performanceService().edit(e);
//				 							//驳回发送消息
//			 								SmsUtil.TixianNotPass(e.getString("phone"), pd.getString("chuli_remark"));
//				 		 				} 
//				 					}
//				 				}
//		 					e=null;
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println(e.toString());
//				}
// 				pd=null;
//    	} catch (Exception e) {
//			result = "00";
//			logger.error(e.toString(), e);
//		} 
//		map.put("result", result);
// 		return map;
//	}
//	
//	
//	/* ===============================权限================================== */
//	public void getHC(){
//		ModelAndView mv = this.getModelAndView();
//		HttpSession session = this.getRequest().getSession();
//		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
//		if(map != null){
//			session.setAttribute("qx", map.get("12"));
//		}
//	
//	}
//	/* ===============================权限================================== */
//	
//
//	
//	/* ===============================提现分控权限================================== */
//	public void getHC1(){
//		ModelAndView mv = this.getModelAndView();
//		HttpSession session = this.getRequest().getSession();
//		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
//		if(map != null){
//			session.setAttribute("qx", map.get("13"));
//		}
// 	}
//	/* ===============================权限================================== */
//
//	/* ===============================支付宝待支付权限================================== */
//	public void getHC2(){
//		ModelAndView mv = this.getModelAndView();
//		HttpSession session = this.getRequest().getSession();
//		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
//		if(map != null){
//			session.setAttribute("qx", map.get("36"));
//		}
// 	}
//	/* ===============================权限================================== */
//	
//	
//	/* ===============================银联待支付权限================================== */
//	public void getHC3(){
//		ModelAndView mv = this.getModelAndView();
//		HttpSession session = this.getRequest().getSession();
//		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
//		if(map != null){
//			session.setAttribute("qx", map.get("37"));
//		}
// 	}
//	/* ===============================权限================================== */
//	
//	
//	/* ===============================异常待支付权限================================== */
//	public void getHC4(){
//		ModelAndView mv = this.getModelAndView();
//		HttpSession session = this.getRequest().getSession();
//		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
//		if(map != null){
//			session.setAttribute("qx", map.get("38"));
//		}
// 	}
//	/* ===============================权限================================== */
//	
//	
//	/**
//	 * 支付宝支付导出到excel
//	 * @return
//	 */
//	@RequestMapping(value="/Alipayexcel")
//	public ModelAndView Alipayexcel(){
//		logBefore(logger, "支付宝支付导出到excel");
//		ModelAndView mv = new ModelAndView();
////		DecimalFormat    df   = new DecimalFormat("######0.00");
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			List<String> titles = new ArrayList<String>();
//			titles.add("订单号");	//1
//			titles.add("省");	//2
//			titles.add("市");	//3
//			titles.add("区");	//4
//			titles.add("申请时间");	//5
//			titles.add("用户类型");	//6
//			titles.add("提现ID号");	//7
//			titles.add("用户名称");	//8
//			titles.add("联系方式");	//9
//			titles.add("提现金额");	//10
//			titles.add("支付宝账号");	//11
//			titles.add("真实姓名");	//12
//			titles.add("异常标注");	//13
//			dataMap.put("titles", titles);
//			//处理sql的区域
//			String allwithdraw_approval_id = pd.getString("allwithdraw_approval_id");
// 			String ArrayApproval_id[] = allwithdraw_approval_id.split(",");
//			pd.put("array", ArrayApproval_id);
// 			List<PageData> varOList = withdraw_approvalService.listAllForExcel(pd);
// 			for(PageData e : varOList){
//				PageData nowpd=new PageData();
//				String w_user_type=e.getString("user_type");
//				String w_user_id=e.getString("user_id");
//				String money="0";
//				String w_user_name="";
//				if(w_user_type.equals("1")){
//					nowpd.put("store_id", w_user_id);
//					//获取商家的财富
//					nowpd.put("wealth_type", "2");
//					nowpd=appStoreService.findWealthById(nowpd);
//					if(nowpd != null){
////						double now_wealth=Double.parseDouble(nowpd.getString("now_wealth"));
////						nowpd=null;
////						nowpd=new PageData();
////						nowpd.put("wealth_type", "1");
////						nowpd.put("store_id", w_user_id);
////						nowpd=appStoreService.findWealthById(nowpd);
//						if(nowpd != null){
// 							w_user_name=nowpd.getString("store_name");
//						}
////						money=df.format(now_wealth);
// 					}
// 				}else if(w_user_type.equals("2")){
// 					nowpd.put("member_id", w_user_id);
// 					nowpd=appMemberService.findById(nowpd);
// 					if(nowpd != null){
//// 						money=nowpd.getString("now_money");
// 						w_user_name=nowpd.getString("name");
// 					}
// 					
//				}else if(w_user_type.equals("3")){
//					nowpd.put("sp_file_id", w_user_id);
//					nowpd=sp_fileService.findById(nowpd);
//					if(nowpd != null){
//// 						money=nowpd.getString("nowmoney");
// 						w_user_name=nowpd.getString("team_name");
// 					}
//					
//				}else if(w_user_type.equals("4")){
//					nowpd.put("clerk_file_id", w_user_id);
//					nowpd=clerk_fileService.findById(nowpd);
//					if(nowpd != null){
//// 						money=nowpd.getString("nowmoney");
// 						w_user_name=nowpd.getString("clerk_name");
// 					}
// 				}
////				e.put("nowmoney", money);
//				e.put("user_name", w_user_name);
//				nowpd=null;
//			}
//			//-----------------
//			List<PageData> varList = new ArrayList<PageData>();
//			for(int i=0;i<varOList.size();i++){
//				PageData vpd = new PageData();
//				vpd.put("var1",(i+1)+"");	//1
//				vpd.put("var2", varOList.get(i).getString("province_name"));	//2
//				vpd.put("var3", varOList.get(i).getString("ciry_name"));	//3
//				vpd.put("var4", varOList.get(i).getString("area_name"));	//4
//				vpd.put("var5", varOList.get(i).get("apply_time").toString());	//5
//				if(varOList.get(i).getString("user_type").equals("1")){
//					vpd.put("var6", "商家");	//6
//				}else if(varOList.get(i).getString("user_type").equals("2")){
//					vpd.put("var6", "会员");	//6
//				}else if(varOList.get(i).getString("user_type").equals("3")){
//					vpd.put("var6", "服务商");	//6
//				}else if(varOList.get(i).getString("user_type").equals("4")){
//					vpd.put("var6", "业务员");	//6
//				}
// 				vpd.put("var7", varOList.get(i).getString("withdraw_approval_id"));	//7
//				vpd.put("var8", varOList.get(i).getString("user_name"));	//8
//				vpd.put("var9", varOList.get(i).getString("phone"));	//9
//				vpd.put("var10", varOList.get(i).getString("money"));	//10
//				vpd.put("var11", varOList.get(i).getString("withdraw_number"));	//11
//				vpd.put("var12", varOList.get(i).getString("withdraw_username"));	//12
//				vpd.put("var13", varOList.get(i).getString("exception_remark"));	//13
//				varList.add(vpd);
//			}
//			dataMap.put("varList", varList);
//			ObjectExcelView erv = new ObjectExcelView();
//			mv = new ModelAndView(erv,dataMap);
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
// 		return mv;
//	}
//	
//	
//	/**
//	 * 银行卡导出到excel
//	 * @return
//	 */
//	@RequestMapping(value="/Bankexcel")
//	public ModelAndView Bankexcel(){
//		logBefore(logger, "银行卡导出到excel");
//		ModelAndView mv = new ModelAndView();
////		DecimalFormat    df   = new DecimalFormat("######0.00");
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try{
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			List<String> titles = new ArrayList<String>();
//			titles.add("订单号");	//1
//			titles.add("省");	//2
//			titles.add("市");	//3
//			titles.add("区");	//4
//			titles.add("申请时间");	//5
//			titles.add("用户类型");	//6
//			titles.add("提现ID号");	//7
//			titles.add("用户名称");	//8
//			titles.add("联系方式");	//9
//			titles.add("提现金额");	//10
//			titles.add("开户行");	//11
//			titles.add("银行卡账号");	//12
//			titles.add("开户名");	//13
//			titles.add("异常标注");	//14
//			titles.add("打款标注");	//15
//			dataMap.put("titles", titles);
//			//处理sql的区域
//			String allwithdraw_approval_id = pd.getString("allwithdraw_approval_id");
// 			String ArrayApproval_id[] = allwithdraw_approval_id.split(",");
//			pd.put("array", ArrayApproval_id);
// 			List<PageData> varOList = withdraw_approvalService.listAllForExcel(pd);
// 			for(PageData e : varOList){
//				PageData nowpd=new PageData();
//				String w_user_type=e.getString("user_type");
//				String w_user_id=e.getString("user_id");
////				String money="0";
//				String w_user_name="";
//				if(w_user_type.equals("1")){
//					nowpd.put("store_id", w_user_id);
//					//获取商家的财富
//					nowpd.put("wealth_type", "2");
//					nowpd=appStoreService.findWealthById(nowpd);
//					if(nowpd != null){
////						double now_wealth=Double.parseDouble(nowpd.getString("now_wealth"));
////						nowpd=null;
////						nowpd=new PageData();
////						nowpd.put("wealth_type", "1");
////						nowpd.put("store_id", w_user_id);
////						nowpd=appStoreService.findWealthById(nowpd);
//						if(nowpd != null){
// 							w_user_name=nowpd.getString("store_name");
//						}
////						money=df.format(now_wealth);
// 					}
// 				}else if(w_user_type.equals("2")){
// 					nowpd.put("member_id", w_user_id);
// 					nowpd=appMemberService.findById(nowpd);
// 					if(nowpd != null){
//// 						money=nowpd.getString("now_money");
// 						w_user_name=nowpd.getString("name");
// 					}
// 					
//				}else if(w_user_type.equals("3")){
//					nowpd.put("sp_file_id", w_user_id);
//					nowpd=sp_fileService.findById(nowpd);
//					if(nowpd != null){
//// 						money=nowpd.getString("nowmoney");
// 						w_user_name=nowpd.getString("team_name");
// 					}
//					
//				}else if(w_user_type.equals("4")){
//					nowpd.put("clerk_file_id", w_user_id);
//					nowpd=clerk_fileService.findById(nowpd);
//					if(nowpd != null){
//// 						money=nowpd.getString("nowmoney");
// 						w_user_name=nowpd.getString("clerk_name");
// 					}
//				
//				}
////				e.put("nowmoney", money);
//				e.put("user_name", w_user_name);
//				nowpd=null;
//			}
//			//-----------------
//			List<PageData> varList = new ArrayList<PageData>();
//			for(int i=0;i<varOList.size();i++){
//				PageData vpd = new PageData();
//				vpd.put("var1",(i+1)+"");	//1
//				vpd.put("var2", varOList.get(i).getString("province_name"));	//2
//				vpd.put("var3", varOList.get(i).getString("ciry_name"));	//3
//				vpd.put("var4", varOList.get(i).getString("area_name"));	//4
//				vpd.put("var5", varOList.get(i).get("apply_time").toString());	//5
//				if(varOList.get(i).getString("user_type").equals("1")){
//					vpd.put("var6", "商家");	//6
//				}else if(varOList.get(i).getString("user_type").equals("2")){
//					vpd.put("var6", "会员");	//6
//				}else if(varOList.get(i).getString("user_type").equals("3")){
//					vpd.put("var6", "服务商");	//6
//				}else if(varOList.get(i).getString("user_type").equals("4")){
//					vpd.put("var6", "业务员");	//6
//				}
// 				vpd.put("var7", varOList.get(i).getString("withdraw_approval_id"));	//7
//				vpd.put("var8", varOList.get(i).getString("user_name"));	//8
//				vpd.put("var9", varOList.get(i).getString("phone"));	//9
//				vpd.put("var10", varOList.get(i).getString("money"));	//10
//				vpd.put("var11", varOList.get(i).getString("account_name"));	//11
//				vpd.put("var12", varOList.get(i).getString("withdraw_number"));	//12
//				vpd.put("var13", varOList.get(i).getString("withdraw_username"));	//13
//				vpd.put("var14", varOList.get(i).getString("exception_remark"));	//14
//				vpd.put("var15", varOList.get(i).getString("dakuan_remark"));	//15
//				varList.add(vpd);
//			}
//			dataMap.put("varList", varList);
//			ObjectExcelView erv = new ObjectExcelView();
//			mv = new ModelAndView(erv,dataMap);
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
// 		return mv;
//	}
//	
//	
//}
