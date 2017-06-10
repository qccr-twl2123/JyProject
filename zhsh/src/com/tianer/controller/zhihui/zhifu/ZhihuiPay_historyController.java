//package com.tianer.controller.zhihui.zhifu;
//
//import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tianer.controller.base.BaseController;
//import com.tianer.entity.system.Menu;
//import com.tianer.entity.Page;
//import com.tianer.util.AppUtil;
//import com.tianer.util.ObjectExcelView;
//import com.tianer.util.Const;
//import com.tianer.util.PageData;
//import com.tianer.service.app.AppMemberService;
//import com.tianer.service.app.AppStoreService;
//import com.tianer.service.business.clerk_file.Clerk_fileService;
//import com.tianer.service.business.pay_history.Pay_historyService;
//import com.tianer.service.business.pcd.PcdService;
//import com.tianer.service.business.sp_file.Sp_fileService;
//
///** 
// * 类名称：支付记录
// * 创建人：刘耀耀
// * 创建时间：2016-05-26
// */
//@Controller
//@RequestMapping(value="/zhihui_pay_history")
//public class ZhihuiPay_historyController extends BaseController {
//	
//	@Resource(name="pay_historyService")
//	private Pay_historyService pay_historyService;
//	@Resource(name="pcdService")
//	private PcdService pcdService;
//	@Resource(name="appMemberService")
//	private AppMemberService appMemberService;
//	@Resource(name="appStoreService")
//	private AppStoreService appStoreService;
//	@Resource(name="clerk_fileService")
//	private Clerk_fileService clerk_fileService;
// 	@Resource(name="sp_fileService")
//	private Sp_fileService sp_fileService;
//	
//	/**
//	 * 支付记录列表
//	 * 魏汉文20160612
//	 */
//	@RequestMapping(value="/list")
//	public ModelAndView list(Page page){
//		logBefore(logger, "支付记录列表");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
// 			page.setPd(pd);
// 			//省list
//			List<PageData> provicelist=pcdService.listAll(pd);
//			mv.addObject("provicelist", provicelist);
//			//列出支付列表
//			String 	allMoney = pay_historyService.dataSumMoney(page);
//			pd.put("allMoney", allMoney);
//			List<PageData>	varList = pay_historyService.datalistPage(page);
//			for( PageData e : varList){
//				PageData e1=new PageData();
// 				String user_type=e.getString("user_type");
//				String payer_id=e.getString("payer_id");
// 				if(user_type.equals("1")){
//					 e1.put("store_id", payer_id);
//					 e1=appStoreService.findById(e1);
//					 e.put("name", e1.getString("store_name"));
// 				}else if(user_type.equals("2")){
//					e1.put("member_id", payer_id);
//					e1=appMemberService.findById(e1);
//					if(e1 != null){
//						e.put("name",e1.getString("name"));
// 					}
// 				}else if(user_type.equals("3")){
//					e1.put("sp_file_id", payer_id);
//					e1=sp_fileService.findById(e1);
//					e.put("name", e1.getString("team_name"));
// 				}
// 				e1=null;
// 				e1=new PageData();
// 				String payee_number=e.getString("payee_number");//收款方
// 				if(payee_number.equals("0")){
//					 e.put("payee_name", "九鱼平台");
//					 e.put("payee_number", "jiuyu");
//				}else{
// 					 e1.put("store_id", payee_number);
// 					 e1=appStoreService.findById(e1);
// 					 if(e1 != null){
// 						e.put("payee_name", e1.getString("store_name"));
// 					 }else{
// 						e.put("payee_name", "很抱歉，当前商家不存在");
// 					 }
//  				}
// 				e1=null;
//			}
// 			this.getHC(); //调用权限
//  			mv.addObject("varList", varList);
//			varList=null;
// 			mv.addObject("pd", pd);
// 			pd=null;
// 			mv.setViewName("zhihui/zhifu/zhifu1");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
//	 
//	
//	
//	/*
//	 * 导出到excel
//	 * @return
//	 */
//	@RequestMapping(value="/exportExcel")
//	public ModelAndView exportExcel(Page page){
//		logBefore(logger, "导出Pay_history到excel");
//		ModelAndView mv = new ModelAndView();
//		PageData pd = new PageData();
// 		try{
//			pd = this.getPageData();
//			Map<String,Object> dataMap = new HashMap<String,Object>();
//			List<String> titles = new ArrayList<String>();
//			titles.add("用户类型");	//1
//			titles.add("用户账号");	//2
//			titles.add("ID号");	//3
//			titles.add("款项类型");	//4
//			titles.add("发生金额");	//5
//			titles.add("支付方式");	//6
//			titles.add("支付账号");	//7
//			titles.add("所在区域");	//8
//			titles.add("收款方");	//9
//			titles.add("收款方ID");	//10
//			titles.add("收款方名称");	//11
//			titles.add("支付时间");	//12
//			titles.add("订单号");	//13
//			dataMap.put("titles", titles);
//			page.setPd(pd);
//			List<PageData> varOList = pay_historyService.ExcellistAllHistory(page);
//			List<PageData> varList = new ArrayList<PageData>();
//			for(int i=0;i<varOList.size();i++){
// 				PageData vpd = new PageData();
// 				vpd.put("var13", varOList.get(i).getString("pay_history_id"));//1
//				if( varOList.get(i).getString("user_type").equals("1")){
//					 vpd.put("var1", "商家");//1
//					 PageData e1=new PageData();
//					 e1.put("store_id", varOList.get(i).getString("payer_id"));
//					 e1=appStoreService.findById(e1);
//					 vpd.put("var2", e1.getString("store_name"));//2
//					 vpd.put("var3", e1.getString("store_id"));//3
//					 e1=null;
// 				}else if( varOList.get(i).getString("user_type").equals("2")){
//					 vpd.put("var1", "会员");//1
//					 PageData e1=new PageData();
//					 e1.put("member_id", varOList.get(i).getString("payer_id"));
//					 e1=appMemberService.findById(e1);
//					 if(e1 != null){
//						 vpd.put("var2", e1.getString("name"));//2
//						 vpd.put("var3", e1.getString("member_id"));//3
//					 }else{
//						 vpd.put("var2", "");//2
//						 vpd.put("var3", varOList.get(i).getString("payer_id"));//3
//					 }
// 					 e1=null;
//				}else if( varOList.get(i).getString("user_type").equals("3")){
//					 vpd.put("var1", "服务商");//1
//					 PageData e1=new PageData();
//					 e1.put("sp_file_id", varOList.get(i).getString("payer_id"));
//					 e1=sp_fileService.findById(e1);
//					 vpd.put("var2", e1.getString("team_name"));//2
//					 vpd.put("var3", e1.getString("sp_file_id"));//3
//					 e1=null;
//				}
//				if( varOList.get(i).getString("money_type").equals("1")){
//					 vpd.put("var4", "充值");//1
// 				}else if( varOList.get(i).getString("money_type").equals("2")){
//					 vpd.put("var4", "消费");//1
// 				}else if( varOList.get(i).getString("money_type").equals("3")){
//					 vpd.put("var4", "商家支付保证金");//1
// 				}else if( varOList.get(i).getString("money_type").equals("4")){
//					 vpd.put("var4", "服务商支付保证金");//1
//				}
//  				vpd.put("var5", varOList.get(i).getString("money"));	//4
//				vpd.put("var6", varOList.get(i).getString("remittance_name"));	//5
//				vpd.put("var7", varOList.get(i).getString("remittance_number"));	//6
//				vpd.put("var8", varOList.get(i).getString("province_name") +varOList.get(i).getString("city_name")+varOList.get(i).getString("area_name"));	//7
// 				if(varOList.get(i).getString("payee_number").equals("0")){
//					vpd.put("var9","九鱼平台" );//9
//				}else{
//					vpd.put("var9","商家" );//9
//				}
// 				if(varOList.get(i).getString("payee_number").equals("0")){
//					vpd.put("var11","九鱼平台" );//11
//					vpd.put("var10", Const.jiuyunumber);	//10
//				}else{
//					 vpd.put("var10", varOList.get(i).getString("payee_number"));	//10
//					 PageData e1= new PageData();
//					 e1.put("store_id", varOList.get(i).getString("payee_number"));
//					 e1=appStoreService.findById(e1);
//					 if(e1 != null){
// 						vpd.put("var11",e1.getString("store_name") );//11
//					 }else{
// 						vpd.put("var11", "很抱歉，当前商家不存在" );//11
//					 }
//					 e1=null;
// 				}
// 				vpd.put("var12", varOList.get(i).get("pay_time").toString());	//12
// 				varList.add(vpd);
//// 				//-----------新增
//// 				/*
//// 				 * 1-商家收益（提现），2-商家消费（充值积分），3-消费收款（现金，积分，第三方，提货卷，余额），4-用户积分或余额支付的金钱，5-第三方支付的金额，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费
//// 	11，消费返推送积分，12-人脉推广收益
//// 				 */
//// 				//新增商家财富历史记录
//// 		   			pd.put("wealth_type", "1");
//// 					pd.put("profit_type", "9");
//// 					pd.put("number", "-"+ vpd.getString("var5"));
//// 					pd.put("store_id", vpd.getString("var3"));
//// 					pd.put("store_operator_id","jy"+vpd.getString("var3"));
//// 					pd.put("process_status", "1");
//// 					pd.put("pay_type",  vpd.getString("var6"));//alipay_pc_direct,wx
//// 		   			pd.put("last_wealth", "0");
//// 		   			pd.put("arrivalMoney", "0");//实际到账金额
//// 		   			pd.put("jiaoyi_id",  vpd.getString("var13"));
//// 		   			pd.put("user_id", Const.jiuyunumber);
//// 					pd.put("store_wealthhistory_id", this.getXFUID());
//// 					appStoreService.saveWealthhistory(pd);
// 			}
//			dataMap.put("varList", varList);
//			ObjectExcelView erv = new ObjectExcelView();
//			mv = new ModelAndView(erv,dataMap);
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
// 	
//	/* ===============================权限================================== */
//	public void getHC(){
//		ModelAndView mv = this.getModelAndView();
//		HttpSession session = this.getRequest().getSession();
//		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
//		if(map != null){
//			session.setAttribute("qx", map.get("10"));
//		}
// 	}
//	/* ===============================权限================================== */
//	
//	
//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
//	}
// 
//}
