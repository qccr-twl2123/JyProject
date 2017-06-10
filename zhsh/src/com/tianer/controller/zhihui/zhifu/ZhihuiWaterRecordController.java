package com.tianer.controller.zhihui.zhifu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.clerk_file.Clerk_fileService;
import com.tianer.service.business.pay_history.Pay_historyService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.sp_file.Sp_fileService;
import com.tianer.service.business.waterrecord.WaterRecordService;
import com.tianer.service.business.withdraw_approval.Withdraw_approvalService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.util.Const;
import com.tianer.util.ObjectExcelView;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;

/** 
 *流水记录表
 */
@Controller
@RequestMapping(value="/zhihuiWaterRecordController")
public class ZhihuiWaterRecordController extends BaseController {
	
	
	
	@Resource(name="waterRecordService")
	private WaterRecordService waterRecordService;
	
	@Resource(name="withdraw_approvalService")
	private Withdraw_approvalService withdraw_approvalService;
	
	@Resource(name="pay_historyService")
	private Pay_historyService pay_historyService;
	
	/**
	 * 新增充值消费流水  zhihuiWaterRecordController/addWater.do
	 */
//	@RequestMapping(value="/addWater")
//	@ResponseBody
	public Object addWater(){
		logBefore(logger, "新增流水 ");
		DecimalFormat    df   = new DecimalFormat("######0.00");
 		PageData pd = new PageData();
		try{ 
			List<PageData> payList=pay_historyService.listAll(pd);
			/*
			 * a.waterrecord_id , a.user_id , a.user_type , a.nowuser_money ,
			a.money_type , a.money , a.withdraw_rate , a.arrivalmoney , a.application_channel ,
			a.remittance_type , a.remittance_name , a.remittance_number , a.pay_status ,
 			a.pass_time , a.pass_operate_id , a.exception_remark , 
 			a.exception_time , a.exception_operator_id , a.chuli_remark ,
			a.over_time , a.over_operator_id , a.dakuan_remark , a.exception_status , 
 			a.payee_number , a.jiaoyi_id , a.jiaoyi_type ,a.order_tn ,
			a.province_name , a.city_name , a.area_name ,
			a.alipay_money , a.wx_money , a.bank_money , a.nowypay_money ,a.apple_money
			 */
			for(PageData e : payList){
				/*
				 * payer_id user_type user_account
					money_type money remittance_type remittance_name
					remittance_number pay_time pay_status order_tn
					payee_number pay_history_id operate_id order_id
				 */
				String money=e.getString("money");
				String money_type=e.getString("money_type");
				String discount_money=e.getString("discount_money");
				//设置金钱
				if(money_type.equals("3")){
		 					pd=new PageData();
							pd.put("waterrecord_id", e.getString("order_id")+1);
							pd.put("user_id", e.getString("payer_id"));
							pd.put("user_type", e.getString("user_type"));
							pd.put("nowuser_money",money);
							pd.put("withdraw_rate","0");
							pd.put("money_type","1" );
			 				pd.put("money",money );
			 				pd.put("reduce_money",discount_money);
		 					pd.put("arrivalmoney", money);
		 					if( e.getString("user_type").equals("1")){
								pd.put("application_channel", "4");
							}else if( e.getString("user_type").equals("2")){
								pd.put("application_channel", "1");
							}else{
								pd.put("application_channel", "6");
							}
							if(e.getString("remittance_type").equals("3")){
								pd.put("remittance_type","3" );
			 					pd.put("alipay_money",money );
							}else if(e.getString("remittance_type").equals("4")){
								pd.put("remittance_type","4" );
								pd.put("wx_money",e.getString("money") );
							}else{
								pd.put("remittance_type","2" );
								pd.put("nowypay_money",money );
							}
							pd.put("remittance_name",e.getString("remittance_name") );
							pd.put("remittance_number",e.getString("remittance_number") );
							pd.put("pay_status",e.getString("pay_status") );
							pd.put("createtime",e.getString("pay_time") );
							pd.put("over_time",e.getString("pay_time") );
							pd.put("pass_time",e.getString("pay_time") );
							pd.put("exception_time",e.getString("pay_time") );
			 				if(e.getString("payee_number").equals("0")){
								pd.put("payee_number",Const.jiuyunumber);
							}else{
								pd.put("payee_number",e.getString("payee_number") );
							}
			 				if(e.getString("pay_type") != null){
								pd.put("jiaoyi_type", e.getString("pay_type"));
			 				}else{
								pd.put("jiaoyi_type", "0");
							}
							pd.put("order_tn", e.getString("order_tn"));
							pd.put("province_name", e.getString("province_name"));
							pd.put("city_name", e.getString("city_name"));
							pd.put("area_name", e.getString("area_name"));
							if(waterRecordService.findByIdWaterRecord(pd) == null){
								waterRecordService.saveWaterRecord(pd);
							}
			 				pd=null;
			 				money="-"+money;
				}
				pd=new PageData();
				pd.put("waterrecord_id", e.getString("order_id"));
				pd.put("user_id", e.getString("payer_id"));
				pd.put("user_type", e.getString("user_type"));
				pd.put("nowuser_money","");
				pd.put("withdraw_rate","0");
				pd.put("money_type",money_type );
				pd.put("reduce_money",discount_money);
 				pd.put("money",money );
   				if(money_type.equals("2")){
  					if(ServiceHelper.getAppOrderService().findById(e) ==null){
  						continue;
  					}
  					pd.put("arrivalmoney", df.format(e.get("allmoney")));
					money=e.getString("actual_money");//支付宝微信实际支付的金额
   				}else{
 					pd.put("arrivalmoney", money);
 				}
				if( e.getString("user_type").equals("1")){
					pd.put("application_channel", "4");
				}else if( e.getString("user_type").equals("2")){
					pd.put("application_channel", "1");
				}else{
					pd.put("application_channel", "6");
				}
				if(e.getString("remittance_type").equals("3")){
					pd.put("remittance_type","3" );
 					pd.put("alipay_money",money );
				}else if(e.getString("remittance_type").equals("4")){
					pd.put("remittance_type","4" );
					pd.put("wx_money",money );
				}else{
					pd.put("remittance_type","2" );
					pd.put("nowypay_money",money );
				}
				pd.put("remittance_name",e.getString("remittance_name") );
				pd.put("remittance_number",e.getString("remittance_number") );
				pd.put("pay_status",e.getString("pay_status") );
				pd.put("createtime",e.getString("pay_time") );
				pd.put("over_time",e.getString("pay_time") );
				pd.put("pass_time",e.getString("pay_time") );
				pd.put("exception_time",e.getString("pay_time") );
 				if(e.getString("payee_number").equals("0")){
					pd.put("payee_number",Const.jiuyunumber);
				}else{
					pd.put("payee_number",e.getString("payee_number") );
				}
 				if(e.getString("pay_type") != null){
					pd.put("jiaoyi_type", e.getString("pay_type"));
 				}else{
					pd.put("jiaoyi_type", "0");
				}
				pd.put("order_tn", e.getString("order_tn"));
				pd.put("province_name", e.getString("province_name"));
				pd.put("city_name", e.getString("city_name"));
				pd.put("area_name", e.getString("area_name"));
				if(waterRecordService.findByIdWaterRecord(pd) == null){
					waterRecordService.saveWaterRecord(pd);
				}
 				pd=null;
 			}
			pd=new PageData();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return pd;
	}
	
	/**
	 * 新增提现流水 
	 */
//	@RequestMapping(value="/addWaterTwo")
//	@ResponseBody
	public Object addWaterTwo(){
		logBefore(logger, "新增流水 ");
 		PageData pd = new PageData();
 		DecimalFormat    df   = new DecimalFormat("######0.00");
		try{ 
			List<PageData> txList=withdraw_approvalService.listAllZiDian(pd);
			/*
			 * a.waterrecord_id , a.user_id , a.user_type , a.nowuser_money ,
			a.money_type , a.money , a.withdraw_rate , a.arrivalmoney , a.application_channel ,
			a.remittance_type , a.remittance_name , a.remittance_number , a.pay_status ,
 			a.pass_time , a.pass_operate_id , a.exception_remark , 
 			a.exception_time , a.exception_operator_id , a.chuli_remark ,
			a.over_time , a.over_operator_id , a.dakuan_remark , a.exception_status , 
 			a.payee_number , a.jiaoyi_id , a.jiaoyi_type ,a.order_tn ,
			a.province_name , a.city_name , a.area_name ,
			a.alipay_money , a.wx_money , a.bank_money , a.nowypay_money ,a.apple_money
			 */
			for(PageData e : txList){
				/*
				 * user_id ,user_type ,money ,withdraw_rate ,arrivalMoney ,
				 * account_name ,account_type ,withdraw_channel ,withdraw_number ,withdraw_username ,
				 * withdraw_type ,withdraw_status ,remark ,
				 * chuli_remark ,exception_remark ,dakuan_remark ,
				 * apply_time ,operator_id ,
				 * province_name ,city_name ,area_name ,
				 * pass_time ,withdraw_approval_id ,nowuser_money ,
				 * passmoney_operator_id ,moneyarrival_time ,exception_status ,exception_operator_id , 
				 */
				pd=new PageData();
				pd.put("waterrecord_id", e.getString("withdraw_approval_id"));
				pd.put("user_id", e.getString("user_id"));
				pd.put("user_type", e.getString("user_type"));
				pd.put("nowuser_money",e.getString("nowuser_money"));
				pd.put("withdraw_rate",e.getString("withdraw_rate"));
				pd.put("money_type","5");
 				pd.put("money", "-"+e.getString("money"));
 				pd.put("reduce_money", df.format(Double.parseDouble(e.getString("money"))-Double.parseDouble(e.getString("arrivalMoney"))));
				pd.put("arrivalmoney", "-"+e.getString("arrivalMoney"));
				if( e.getString("withdraw_channel").equals("1")){
					pd.put("application_channel", "1");
				}else if( e.getString("withdraw_channel").equals("2")){
					pd.put("application_channel", "4");
				}else if( e.getString("withdraw_channel").equals("3")){
					pd.put("application_channel", "4");
				}else if( e.getString("withdraw_channel").equals("4")){
					pd.put("application_channel", "5");
				}else{
					pd.put("application_channel", "6");
				}
				if(e.getString("account_type").equals("3")){
					pd.put("remittance_type","3" );
 					pd.put("alipay_money","-"+e.getString("arrivalMoney") );
				}else if(e.getString("account_type").equals("2")){
					pd.put("remittance_type","2" );
					pd.put("wx_money","-"+e.getString("arrivalMoney") );
				}else if(e.getString("account_type").equals("4")){
					pd.put("remittance_type","4" );
					pd.put("bank_money","-"+e.getString("arrivalMoney") );
				}
				pd.put("remittance_name",e.getString("account_name") + "-" +e.getString("withdraw_username") );
				pd.put("remittance_number",e.getString("withdraw_number") );
				if(e.getString("withdraw_status").equals("3")){
					pd.put("pay_status","1" );
				}else if(e.getString("withdraw_status").equals("1")){
					pd.put("pay_status","3");
				}else{
					pd.put("pay_status",e.getString("withdraw_status") );
				}
 				pd.put("createtime",e.get("apply_time") );
				pd.put("over_time",e.get("moneyarrival_time") );
				pd.put("dakuan_remark",e.getString("dakuan_remark") );
				pd.put("over_operator_id",e.getString("passmoney_operator_id") );
				pd.put("pass_time",e.get("pass_time") );
				pd.put("exception_remark",e.getString("exception_remark") );
				pd.put("pass_operate_id",e.getString("operator_id") );
 				pd.put("exception_time",e.get("pass_time") );
 				pd.put("chuli_remark",e.getString("chuli_remark") );
 				pd.put("exception_operator_id",e.getString("exception_operator_id") );
 				pd.put("jiaoyi_type",e.getString("exception_status"));
				pd.put("payee_number",Const.jiuyunumber);
 				pd.put("jiaoyi_type", "0");
 				pd.put("order_tn", e.getString("withdraw_approval_id"));
				pd.put("province_name", e.getString("province_name"));
				pd.put("city_name", e.getString("city_name"));
				pd.put("area_name", e.getString("area_name"));
				if(waterRecordService.findByIdWaterRecord(pd) == null){
					waterRecordService.saveWaterRecord(pd);
				}
 				pd=null;
 			}
			pd=new PageData();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return pd;
	}
	
	
	
	//--------------------------------支付记录列表-----------------------------------------
	
 
	@Resource(name="pcdService")
	private PcdService pcdService;
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	@Resource(name="clerk_fileService")
	private Clerk_fileService clerk_fileService;
 	@Resource(name="sp_fileService")
	private Sp_fileService sp_fileService;
	
	/**
	 * 流水记录
	 * 魏汉文20160612
	 */
	@RequestMapping(value="/liushuilist")
	public ModelAndView liushuilist(Page page){
//		logBefore(logger, "流水记录");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try{
			//省list
			List<PageData> provincelist=pcdService.listAll(pd);
			mv.addObject("provincelist", provincelist);
			provincelist=null;
 			pd = this.getPageData();
 			//获取登录人信息
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
				String currentPage = pd.getString("currentPage");
	 			if(null != currentPage && !"".equals(currentPage)){
	 				pd.put("nowpage", (Integer.parseInt(currentPage)- 1)*10);
				}else{
					pd.put("nowpage",  0 );
	 			}
	 			pd.put("chuli_ok", "1");
	   			page.setPd(pd);
	 			//列出支付列表
				PageData nowpagesum=waterRecordService.sumNowPageWaterRecord(page);
				mv.addObject("nowpagesum", nowpagesum);
				PageData allpagesum=waterRecordService.sumAllPageWaterRecord(page);
				mv.addObject("allpagesum", allpagesum);
 				List<PageData> paylist=waterRecordService.datalistPageWaterRecord(page);
	 			for( PageData e : paylist){
					PageData e1=new PageData();
	 				String user_type=e.getString("user_type");
					String payer_id=e.getString("user_id");
	 				if(user_type.equals("1")){
						 e1.put("store_id", payer_id);
						 e1=appStoreService.findById(e1);
						 if(e1 != null){
							e.put("name", e1.getString("store_name"));
		 				 }else{
		 					e.put("name", "很抱歉，当前商家不存在");
		 				 }
	  				}else if(user_type.equals("2")){
						e1.put("member_id", payer_id);
						e1=appMemberService.findById(e1);
						if(e1 != null){
							e.put("name",e1.getString("name"));
	 					}else{
	 						e.put("name", "很抱歉，当前会员不存在");
	 					 }
	 				}else if(user_type.equals("3")){
						e1.put("sp_file_id", payer_id);
						e1=sp_fileService.findById(e1);
						if(e1 != null){
							e.put("name", e1.getString("team_name"));
						}else{
	 						e.put("name", "很抱歉，当前服务商不存在");
	 					 }
	  				}else if(user_type.equals("4")){
						e1.put("clerk_file_id", payer_id);
						e1=clerk_fileService.findById(e1);
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
	 					 e.put("payee_type", "0");
						 e.put("payee_name", "九鱼平台");
	 				}else if(payee_number.length()== 17 || payee_number.length() == 32){
	 					 e.put("payee_type", "2"); 
	 					 e1.put("member_id", payer_id);
						 e1=appMemberService.findById(e1);
						 if(e1 != null){
							e.put("payee_name",e1.getString("name"));
	 					 }else{
	 						e.put("payee_name", "很抱歉，当前会员不存在");
	 					 }
	  				}else{
	  					 e.put("payee_type", "1");
	  					 e1.put("store_id", payee_number);
	 					 e1=appStoreService.findById(e1);
	 					 if(e1 != null){
	 						e.put("payee_name", e1.getString("store_name"));
	 					 }else{
	 						e.put("payee_name", "很抱歉，当前商家不存在");
	 					 }
	  				}
	 				e1=null;
				}
	 			this.getHCliushui(); //调用权限
	  			mv.addObject("varList", paylist);
	  			paylist=null;
	 			mv.addObject("pd", pd);
	 			pd=null;
 			}
  			mv.setViewName("zhihui/zhifu/zhifu1");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	 
	
	/**
	 * 商品在线销售明细--提货券、优选提货券
	 * 魏汉文20160612
	 */
	@RequestMapping(value="/orderSolelist")
	public ModelAndView orderSolelist(Page page){
//		logBefore(logger, "销售明细");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String currentPage = pd.getString("currentPage");
 			if(null != currentPage && !"".equals(currentPage)){
 				pd.put("nowpage", (Integer.parseInt(currentPage)- 1)*10);
			}else{
				pd.put("nowpage",  0 );
 			}
 			pd.put("chuli_ok", "2");//处理成功的：购买成功和已退货的
 			pd.put("user_type", "2");//会员
 			pd.put("money_type", "2");//消费 
  			pd.put("jiaoyi_type", "35");//提货券
			page.setPd(pd);
			//省list
			List<PageData> provincelist=pcdService.listAll(pd);
			mv.addObject("provincelist", provincelist);
			provincelist=null;
			//列出支付列表
			PageData nowpagesum=waterRecordService.sumNowPageWaterRecord(page);
			mv.addObject("nowpagesum", nowpagesum);
			PageData allpagesum=waterRecordService.sumAllPageWaterRecord(page);
			mv.addObject("allpagesum", allpagesum);
			List<PageData> paylist=waterRecordService.datalistPageWaterRecord(page);
 			this.getHCXS(); //调用权限
			mv.addObject("varList", paylist);
			paylist=null;
			mv.addObject("pd", pd);
			pd=null;
			mv.setViewName("zhihui/zhifu/zhifu14");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	/**
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/exportExcel")
	public ModelAndView exportExcel(Page page){
 		ModelAndView mv = new ModelAndView();
 		PageData pd = new PageData();
 		try{
			pd = this.getPageData();
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户类型");	//1
			titles.add("用户账号");	//2
			titles.add("ID号");	//3
			titles.add("款项类型");	//4
			titles.add("发生金额");	//5
			titles.add("支付方式");	//6
			titles.add("支付账号");	//7
			titles.add("所在区域");	//8
			titles.add("收款方");	//9
			titles.add("收款方ID");	//10
			titles.add("收款方名称");	//11
			titles.add("支付时间");	//12
			titles.add("订单号");	//13
			dataMap.put("titles", titles);
			pd.put("chuli_ok", "1");
 			page.setPd(pd);
 			List<PageData> varOList = waterRecordService.ExcellistAllHistory(page);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
 				PageData vpd = new PageData();
 				vpd.put("var13", varOList.get(i).getString("waterrecord_id"));//1
				if( varOList.get(i).getString("user_type").equals("1")){
					 vpd.put("var1", "商家");//1
					 PageData e1=new PageData();
					 e1.put("store_id", varOList.get(i).getString("user_id"));
					 e1=appStoreService.findById(e1);
					 if(e1 == null){
						 System.out.println(varOList.get(i).getString("user_id"));;
					 }else{
						 vpd.put("var2", e1.getString("store_name"));//2
						 vpd.put("var3", e1.getString("store_id"));//3
						 e1=null;
					 }
  				}else if( varOList.get(i).getString("user_type").equals("2")){
					 vpd.put("var1", "会员");//1
					 PageData e1=new PageData();
					 e1.put("member_id", varOList.get(i).getString("user_id"));
					 e1=appMemberService.findById(e1);
					 if(e1 != null){
						 vpd.put("var2", e1.getString("name"));//2
						 vpd.put("var3", e1.getString("member_id"));//3
					 }else{
						 vpd.put("var2", "");//2
						 vpd.put("var3", varOList.get(i).getString("user_id"));//3
					 }
 					 e1=null;
				}else if( varOList.get(i).getString("user_type").equals("3")){
					 vpd.put("var1", "服务商");//1
					 PageData e1=new PageData();
					 e1.put("sp_file_id", varOList.get(i).getString("user_id"));
					 e1=sp_fileService.findById(e1);
					 vpd.put("var2", e1.getString("team_name"));//2
					 vpd.put("var3", e1.getString("sp_file_id"));//3
					 e1=null;
				}
				if( varOList.get(i).getString("money_type").equals("1")){
					 vpd.put("var4", "充值");//1
 				}else if( varOList.get(i).getString("money_type").equals("2")){
					 vpd.put("var4", "消费");//1
 				}else if( varOList.get(i).getString("money_type").equals("3")){
					 vpd.put("var4", "商家支付保证金");//1
 				}else if( varOList.get(i).getString("money_type").equals("4")){
					 vpd.put("var4", "服务商支付保证金");//1
				}else if( varOList.get(i).getString("money_type").equals("5")){
					 vpd.put("var4", "提现");//1
				}
  				vpd.put("var5", varOList.get(i).getString("money"));	//4
				vpd.put("var6", varOList.get(i).getString("remittance_name"));	//5
				vpd.put("var7", varOList.get(i).getString("remittance_number"));	//6
				vpd.put("var8", varOList.get(i).getString("province_name") +varOList.get(i).getString("city_name")+varOList.get(i).getString("area_name"));	//7
 				if(varOList.get(i).getString("payee_number").equals("Jiuyu")){
 					vpd.put("var9",Const.jiuyuname );//9
 					vpd.put("var10", Const.jiuyunumber);	//10
					vpd.put("var11",Const.jiuyuname );//11
 				}else{
					vpd.put("var9","商家");//9
					 vpd.put("var10", varOList.get(i).getString("payee_number"));	//10
					 PageData e1= new PageData();
					 e1.put("store_id", varOList.get(i).getString("payee_number"));
					 e1=appStoreService.findById(e1);
					 if(e1 != null){
 						vpd.put("var11",e1.getString("store_name") );//11
					 }else{
 						vpd.put("var11", "很抱歉，当前商家不存在" );//11
					 }
					 e1=null;
 				}
 				vpd.put("var12", varOList.get(i).get("createtime").toString());	//12
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
 	
	/* ===============================流水权限================================== */
	public void getHCliushui(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("10"));
		}
 	}
	/* ===============================权限================================== */
	
 	
	/* ===============================销售明细权限================================== */
	public void getHCXS(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("42"));
		}
 	}
	/* ===============================权限================================== */
	
	
	
	/**
	 *  前往提现列表（0-待审批，1-已处理，支付到账/退款成功，2-转为异常,3-通过，99-提现失败驳回  ）
 	 */
	@RequestMapping(value="/listTxPage")
	public ModelAndView listTxPage(Page page){
//		logBefore(logger, "提现列表");
		ModelAndView mv = this.getModelAndView();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		PageData pd = new PageData();
		try{
			//操作员列表
 			List<PageData> operatorList = ServiceHelper.getOperator_fileService().listAll(pd);
			mv.addObject("operatorList", operatorList);
			operatorList=null;
 			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			provincelist=null;
			pd = this.getPageData();
			String pay_status=pd.getString("pay_status");
			String chuli_ok=pd.getString("chuli_ok");
			String remittance_type=pd.getString("remittance_type");
			//分页
			String currentPage = pd.getString("currentPage");
 			if(null != currentPage && !"".equals(currentPage)){
 				pd.put("nowpage", (Integer.parseInt(currentPage)- 1)*10);
			}else{
				pd.put("nowpage",  0 );
 			}
 			pd.put("money_type", "5");
 			page.setPd(pd);
 			//列出支付列表
			PageData nowpagesum=waterRecordService.sumNowPageWaterRecord(page);
			mv.addObject("nowpagesum", nowpagesum);
			PageData allpagesum=waterRecordService.sumAllPageWaterRecord(page);
			mv.addObject("allpagesum", allpagesum);
			List<PageData> txlist=waterRecordService.datalistPageWaterRecord(page);
			for(PageData e : txlist){
				PageData nowpd=new PageData();
				String w_user_type=e.getString("user_type");
				String w_user_id=e.getString("user_id");
				String w_user_name="";
				String principal="";
				String money="0";
				String phone="0";
				if(w_user_type.equals("1")){
					nowpd.put("store_id", w_user_id);
					nowpd=appStoreService.findById(nowpd);
					if(nowpd != null){
						//获取商家的财富
						money=ServiceHelper.getAppStoreService().sumStoreWealth(nowpd);
						w_user_name=nowpd.getString("store_name");
						principal=nowpd.getString("principal");
						phone=nowpd.getString("registertel_phone");
 					}
  				}else if(w_user_type.equals("2")){
					nowpd.put("member_id", w_user_id);
					nowpd=appMemberService.findById(nowpd);
					if(nowpd != null){
						money=nowpd.getString("now_money");
						w_user_name=nowpd.getString("name");
						principal=nowpd.getString("name");
						phone=nowpd.getString("phone");
					}
				}else if(w_user_type.equals("3")){
					nowpd.put("sp_file_id", w_user_id);
					nowpd=sp_fileService.findById(nowpd);
					if(nowpd != null){
						money=nowpd.getString("nowmoney");
						w_user_name=nowpd.getString("team_name");
						principal=nowpd.getString("team_name");
						phone=nowpd.getString("phone");
					}
				}else if(w_user_type.equals("4")){
					nowpd.put("clerk_file_id", w_user_id);
					nowpd=clerk_fileService.findById(nowpd);
					if(nowpd != null){
						money=nowpd.getString("nowmoney");
						w_user_name=nowpd.getString("clerk_name");
						principal=nowpd.getString("clerk_name");
						phone=nowpd.getString("phone");
					}
				}
				e.put("nowmoney", money);
				e.put("principal", principal);
				e.put("user_name", w_user_name);
				e.put("phone", phone);
				nowpd=null;
			}
			if(chuli_ok.equals("3")){
				mv.setViewName("zhihui/zhifu/zhifu13");
				this.getHCOver();
			}else{
				if(pay_status.equals("0")){
					mv.setViewName("zhihui/zhifu/zhifu9");
					this.getHC1();
				}else if(pay_status.equals("3") && remittance_type != null){
					if(remittance_type.equals("1")){
						mv.setViewName("zhihui/zhifu/zhifu11");
						this.getHC2();
					}else if(remittance_type.equals("3")){
						mv.setViewName("zhihui/zhifu/zhifu10");
						this.getHC3();
					}
				}else if(pay_status.equals("2")){
 					mv.setViewName("zhihui/zhifu/zhifu12");
					this.getHC4();
				} 
			}
  			mv.addObject("varList", txlist);
 			txlist=null;
			mv.addObject("pd", pd);
			pd=null;
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 批量通过或者批量转为异常
	 */
	@RequestMapping(value="/updateAll")
	@ResponseBody
	public Object updateAll() {
//		logBefore(logger, " 批量通过或者批量转为异常");
		PageData pd = new PageData();	
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		List<String> withidlist=new ArrayList<String>();//用来存储商家List
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		String result = "01";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(zhlogin == null){
				map.put("result", "00");
				return map;
			}
			String operator_id=zhlogin.getLogin_id();
			pd = this.getPageData();
			String abno = pd.getString("abno");
			String pay_status = pd.getString("pay_status");
			String exception_status = pd.getString("exception_status");
			if(null != abno && !"".equals(abno)){
				String ArrayApproval_id[] = abno.split(",");
				if(pay_status.equals("3")){//通过
					Collections.addAll(withidlist, ArrayApproval_id);
					pd.put("array", ArrayApproval_id);
					pd.put("pass_operate_id", operator_id);
					waterRecordService.updateAll(pd);
				}else{//转为异常
					for (int i = 0; i < ArrayApproval_id.length; i++) {
						String[] arry_all=ArrayApproval_id[i].split("@");
						withidlist.add(arry_all[1]);
						PageData pg = new PageData();
						pg.put("waterrecord_id", arry_all[1]);
						pg.put("pay_status", pay_status);
						pg.put("exception_status", exception_status);
						if(pay_status.equals("2")){
							pg.put("exception_remark", arry_all[0]);
							pg.put("exception_operator_id", operator_id);
						}else if(pay_status.equals("1")){
							pg.put("dakuan_remark", arry_all[0]);
							pg.put("over_operator_id", operator_id);
						}else if(pay_status.equals("99")){
							pg.put("dakuan_remark", arry_all[0]);
							pg.put("over_operator_id", operator_id);
						}
						waterRecordService.editWaterRecord(pg);
						pg=null;
						arry_all=null;
						
	   				}
				}
				ArrayApproval_id=null;
  			} 
 			for (int i = 0; i < withidlist.size(); i++) {
 				PageData withpd=new PageData();
				withpd.put("waterrecord_id", withidlist.get(i));
				try {
					//获取提现的详情
			 		withpd=waterRecordService.findByIdWaterRecord(withpd);
			 		String user_type=withpd.getString("user_type");
			 		String user_id=withpd.getString("user_id");
			 		String money=withpd.getString("money");
			 		PageData e=new PageData();
					if(user_type.equals("1")){
		 				//获取商家的财富
						e.put("wealth_type", "2");
						e.put("store_id", user_id);
						e=appStoreService.findWealthById(e);
						if(e != null ){
							if(pay_status.equals("1")){
	 							//修改状态
								withpd.put("process_status", "1");
								appStoreService.editWealthHistoryStatusTwo(withpd);
 								//更新提现信息
	 							String tixian_money=e.getString("tixian_money");
	 							if(Double.parseDouble(tixian_money)-Double.parseDouble(money) <= 0){
	 								e.put("tixian_money", "0");
	 							}else{
	 								e.put("tixian_money", df.format(Double.parseDouble(tixian_money)+Double.parseDouble(money)));
	 							}
	 							appStoreService.edit(e);
	 							//通过发送消息
								SmsUtil.TixianPass(appStoreService.findById(e).getString("registertel_phone"), money, appStoreService.findById(e).getString("store_name"));
							}
 						}
					}else if(user_type.equals("2")){
	 						e.put("member_id", user_id);
	 						e=appMemberService.findById(e);
	 						if(e != null ){
	 							if(pay_status.equals("1")){
	 								//通过发送消息
			 						e.put("jiaoyi_id",withidlist.get(i));
			 						e.put("jiaoyi_status", "1");
			 						ServiceHelper.getAppMember_wealthhistoryService().updateWealthhistory(e);
	 								SmsUtil.TixianPass(e.getString("phone"), money,e.getString("name"));
	 							}
	 						}
	 				}else if(user_type.endsWith("3")){
	 					e.put("sp_file_id", user_id);
	 					e=sp_fileService.findById(e);
	 					if(e != null ){
		 					if(pay_status.equals("1")){
		 						//通过发送消息
		 						e.put("service_performance_id",  withidlist.get(i));
		 						e.put("isjihuo", "1");
		 						ServiceHelper.getService_performanceService().edit(e);
 								SmsUtil.TixianPass(e.getString("phone"), money,e.getString("team_name"));
							}
		 				}
	 				}else if(user_type.endsWith("4")){
	 					e.put("clerk_file_id", user_id);
	 					e=clerk_fileService.findById(e);
	 					if(e != null ){
	 						 if(pay_status.equals("1")){
	 							e.put("service_performance_id",  withidlist.get(i));
		 						e.put("isjihuo", "1");
		 						ServiceHelper.getService_performanceService().edit(e);
	 							//通过发送消息
								SmsUtil.TixianPass(e.getString("phone"), money,e.getString("clerk_name"));
							}
	 					}
	 				}
					e=null;
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.toString());
				}	
				withpd=null;
			}
 			withidlist=null;
		} catch (Exception e) {
			result = "00";
			logger.error(e.toString(), e);
		} 
		map.put("result", result);
		return map;
	}
	
	
	/**
	 * 异常数据，通过或是驳回
	 */
	@RequestMapping(value="/updateById")
	@ResponseBody
	public Object updateById() {
//		logBefore(logger, " 异常数据，通过或是驳回 ");
		PageData pd = new PageData();	
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		String result = "01";
		Map<String,Object> map = new HashMap<String,Object>();
		try {
				if(zhlogin == null){
					map.put("result", "00");
					return map;
				}
				String operator_id=zhlogin.getLogin_id();
				pd = this.getPageData();
				String pay_status=pd.getString("pay_status");
				pd.put("pass_operator_id", operator_id);
				waterRecordService.editWaterRecord(pd);
  				try {
		 					//获取提现的详情
//  						String chuli_remark=pd.getString("chuli_remark");
		 			 		pd=waterRecordService.findByIdWaterRecord(pd);
 		 			 		String user_type=pd.getString("user_type");
		 			 		String user_id=pd.getString("user_id");
		 			 		String money=pd.getString("money");
		 			 		PageData e=new PageData();
		 					if(user_type.equals("1")){
			 		 				//获取商家的财富
			 						e.put("wealth_type", "1");
			 						e.put("store_id", user_id);
			 						e=appStoreService.findWealthById(e);
			 						if(e != null ){
			 							if( pay_status.equals("99")){
			 								double now_wealth=Double.parseDouble(e.getString("now_wealth"));
			 								//减少商家积分余额
			 								double n=now_wealth-Double.parseDouble(money);
			 								e.put("now_wealth", df.format(n));
			 								appStoreService.editWealthById(e);
			 								//修改状态
			 								pd.put("process_status", "99");
			 								appStoreService.editWealthHistoryStatusTwo(pd);
			 								//更新提现信息
			 	 							String tixian_money=e.getString("tixian_money");
			 	 							if(Double.parseDouble(tixian_money)+Double.parseDouble(money) <= 0){
			 	 								e.put("tixian_money", "0");
			 	 							}else{
			 	 								e.put("tixian_money", df.format(Double.parseDouble(tixian_money)+Double.parseDouble(money)));
			 	 							}
			 	 							appStoreService.edit(e);
			 								//驳回发送消息
			 								SmsUtil.TixianNotPass(appStoreService.findById(e).getString("registertel_phone"), pd.getString("chuli_remark"));
			 	 						} 
			 						}
			 					}else if(user_type.equals("2")){
				 						e.put("member_id", user_id);
				 						e=appMemberService.findById(e);
				 						if(e != null ){
				 							if(pay_status.equals("99")){
				 								//新增用户积分余额
				 								double now_money=Double.parseDouble(e.getString("now_money"));
				 								double n=now_money-Double.parseDouble(money);
				 								e.put("now_money", df.format(n));
				 								appMemberService.edit(e);
				 								//驳回处理
						 						e.put("jiaoyi_id", pd.getString("jiaoyi_id"));
						 						e.put("jiaoyi_status", "99");
						 						ServiceHelper.getAppMember_wealthhistoryService().updateWealthhistory(e);
 				 								//驳回发送消息
				 								SmsUtil.TixianNotPass(e.getString("phone"), pd.getString("chuli_remark"));
				 	 						} 
				 						}
				 				}else if(user_type.endsWith("3")){
				 					e.put("sp_file_id", user_id);
				 					e=sp_fileService.findById(e);
				 					if(e != null ){
					 					if(pay_status.equals("99")){
					 						//新增服务商积分余额
					 						double now_money=Double.parseDouble(e.getString("nowmoney"));
					 						double n=now_money-Double.parseDouble(money);
					 						e.put("nowmoney", df.format(n));
					 						sp_fileService.edit(e);
					 						//驳回处理
					 						e.put("service_performance_id", pd.getString("jiaoyi_id"));
					 						e.put("isjihuo", "99");
					 						ServiceHelper.getService_performanceService().edit(e);
					 						//驳回发送消息
			 								SmsUtil.TixianNotPass(e.getString("phone"), pd.getString("chuli_remark"));
					 	 				} 
					 				}
				 				}else if(user_type.endsWith("4")){
				 					e.put("clerk_file_id", user_id);
				 					e=clerk_fileService.findById(e);
				 					if(e != null ){
				 						if(pay_status.equals("99")){
				 							//新增业务员余额
				 							double nowmoney=Double.parseDouble(e.getString("nowmoney"));
				 							double n=nowmoney-Double.parseDouble(money);
				 							e.put("nowmoney", df.format(n));
				 							clerk_fileService.edit(e);
				 							//驳回处理
					 						e.put("service_performance_id", pd.getString("jiaoyi_id"));
					 						e.put("isjihuo", "99");
					 						ServiceHelper.getService_performanceService().edit(e);
				 							//驳回发送消息
			 								SmsUtil.TixianNotPass(e.getString("phone"), pd.getString("chuli_remark"));
				 		 				} 
				 					}
				 				}
		 					e=null;
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.toString());
				}
 				pd=null;
    	} catch (Exception e) {
			result = "00";
			logger.error(e.toString(), e);
		} 
		map.put("result", result);
 		return map;
	}
	
	
	
	/**
	 * 支付宝支付导出到excel
	 * @return
	 */
	@RequestMapping(value="/Alipayexcel")
	public ModelAndView Alipayexcel( ){
//		logBefore(logger, "支付宝支付导出到excel");
		ModelAndView mv = new ModelAndView();
 		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("订单号");	//1
			titles.add("省");	//2
			titles.add("市");	//3
			titles.add("区");	//4
			titles.add("申请时间");	//5
			titles.add("用户类型");	//6
			titles.add("提现ID号");	//7
			titles.add("用户名称");	//8
			titles.add("联系方式");	//9
			titles.add("提现金额");	//10
			titles.add("支付宝账号");	//11
			titles.add("真实姓名");	//12
 			dataMap.put("titles", titles);
			//处理sql的区域
			String allwaterrecord_id = pd.getString("allwaterrecord_id");
 			String ArrayApproval_id[] = allwaterrecord_id.split(",");
			pd.put("array", ArrayApproval_id);
 			List<PageData> varOList = waterRecordService.listAllForExcel(pd);
 			for(PageData e : varOList){
				PageData nowpd=new PageData();
				String w_user_type=e.getString("user_type");
				String w_user_id=e.getString("user_id");
 				String w_user_name="";
 				String phone="";
				if(w_user_type.equals("1")){
					nowpd.put("store_id", w_user_id);
 					nowpd=appStoreService.findById(nowpd);
					if(nowpd != null){
						//获取商家的财富
//						money=ServiceHelper.getAppStoreService().sumStoreWealth(nowpd);
 						w_user_name=nowpd.getString("store_name");
 						phone=nowpd.getString("registertel_phone");
  					}
  				}else if(w_user_type.equals("2")){
 					nowpd.put("member_id", w_user_id);
 					nowpd=appMemberService.findById(nowpd);
 					if(nowpd != null){
// 						money=nowpd.getString("now_money");
 						w_user_name=nowpd.getString("name");
 						phone=nowpd.getString("phone");
 					}
 					
				}else if(w_user_type.equals("3")){
					nowpd.put("sp_file_id", w_user_id);
					nowpd=sp_fileService.findById(nowpd);
					if(nowpd != null){
// 						money=nowpd.getString("nowmoney");
 						w_user_name=nowpd.getString("team_name");
 						phone=nowpd.getString("phone");
 					}
					
				}else if(w_user_type.equals("4")){
					nowpd.put("clerk_file_id", w_user_id);
					nowpd=clerk_fileService.findById(nowpd);
					if(nowpd != null){
// 						money=nowpd.getString("nowmoney");
 						w_user_name=nowpd.getString("clerk_name");
 						phone=nowpd.getString("phone");
 					}
				
				}
//				e.put("nowmoney", money);
				e.put("user_name", w_user_name);
				e.put("phone", phone);
				nowpd=null;
			}
			//-----------------
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1",(i+1)+"");	//1
				vpd.put("var2", varOList.get(i).getString("province_name"));	//2
				vpd.put("var3", varOList.get(i).getString("city_name"));	//3
				vpd.put("var4", varOList.get(i).getString("area_name"));	//4
				vpd.put("var5", varOList.get(i).get("createtime").toString());	//5
				if(varOList.get(i).getString("user_type").equals("1")){
					vpd.put("var6", "商家");	//6
				}else if(varOList.get(i).getString("user_type").equals("2")){
					vpd.put("var6", "会员");	//6
				}else if(varOList.get(i).getString("user_type").equals("3")){
					vpd.put("var6", "服务商");	//6
				}else if(varOList.get(i).getString("user_type").equals("4")){
					vpd.put("var6", "业务员");	//6
				}
 				vpd.put("var7", varOList.get(i).getString("waterrecord_id"));	//7
				vpd.put("var8", varOList.get(i).getString("user_name"));	//8
				vpd.put("var9", varOList.get(i).getString("phone"));	//9
				vpd.put("var10", varOList.get(i).getString("money"));	//10
				vpd.put("var11", varOList.get(i).getString("remittance_number"));	//11
				vpd.put("var12", varOList.get(i).getString("remittance_name"));	//12
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
	
	
	/**
	 * 银行卡导出到excel
	 * @return
	 */
	@RequestMapping(value="/Bankexcel")
	public ModelAndView Bankexcel( ){
//		logBefore(logger, "银行卡导出到excel");
		ModelAndView mv = new ModelAndView();
 		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("订单号");	//1
			titles.add("省");	//2
			titles.add("市");	//3
			titles.add("区");	//4
			titles.add("申请时间");	//5
			titles.add("用户类型");	//6
			titles.add("提现ID号");	//7
			titles.add("用户名称");	//8
			titles.add("联系方式");	//9
			titles.add("提现金额");	//10
			titles.add("开户行-开户名");	//11
			titles.add("银行卡账号");	//12
  			dataMap.put("titles", titles);
			//处理sql的区域
			String allwaterrecord_id = pd.getString("allwaterrecord_id");
 			String ArrayApproval_id[] = allwaterrecord_id.split(",");
			pd.put("array", ArrayApproval_id);
  			List<PageData> varOList = waterRecordService.listAllForExcel(pd);
 			for(PageData e : varOList){
				PageData nowpd=new PageData();
				String w_user_type=e.getString("user_type");
				String w_user_id=e.getString("user_id");
 				String w_user_name="";
 				String phone="";
				if(w_user_type.equals("1")){
					nowpd.put("store_id", w_user_id);
 					nowpd=appStoreService.findById(nowpd);
					if(nowpd != null){
						//获取商家的财富
//						money=ServiceHelper.getAppStoreService().sumStoreWealth(nowpd);
 						w_user_name=nowpd.getString("store_name");
 						phone=nowpd.getString("registertel_phone");
  					}
  				}else if(w_user_type.equals("2")){
 					nowpd.put("member_id", w_user_id);
 					nowpd=appMemberService.findById(nowpd);
 					if(nowpd != null){
// 						money=nowpd.getString("now_money");
 						w_user_name=nowpd.getString("name");
 						phone=nowpd.getString("phone");
 					}
 					
				}else if(w_user_type.equals("3")){
					nowpd.put("sp_file_id", w_user_id);
					nowpd=sp_fileService.findById(nowpd);
					if(nowpd != null){
// 						money=nowpd.getString("nowmoney");
 						w_user_name=nowpd.getString("team_name");
 						phone=nowpd.getString("phone");
 					}
					
				}else if(w_user_type.equals("4")){
					nowpd.put("clerk_file_id", w_user_id);
					nowpd=clerk_fileService.findById(nowpd);
					if(nowpd != null){
// 						money=nowpd.getString("nowmoney");
 						w_user_name=nowpd.getString("clerk_name");
 						phone=nowpd.getString("phone");
 					}
				
				}
//				e.put("nowmoney", money);
				e.put("user_name", w_user_name);
				e.put("phone", phone);
				nowpd=null;
			}
			//-----------------
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1",(i+1)+"");	//1
				vpd.put("var2", varOList.get(i).getString("province_name"));	//2
				vpd.put("var3", varOList.get(i).getString("city_name"));	//3
				vpd.put("var4", varOList.get(i).getString("area_name"));	//4
				vpd.put("var5", varOList.get(i).get("createtime").toString());	//5
				if(varOList.get(i).getString("user_type").equals("1")){
					vpd.put("var6", "商家");	//6
				}else if(varOList.get(i).getString("user_type").equals("2")){
					vpd.put("var6", "会员");	//6
				}else if(varOList.get(i).getString("user_type").equals("3")){
					vpd.put("var6", "服务商");	//6
				}else if(varOList.get(i).getString("user_type").equals("4")){
					vpd.put("var6", "业务员");	//6
				}
 				vpd.put("var7", varOList.get(i).getString("waterrecord_id"));	//7
				vpd.put("var8", varOList.get(i).getString("user_name"));	//8
				vpd.put("var9", varOList.get(i).getString("phone"));	//9
				vpd.put("var10", varOList.get(i).getString("money"));	//10
				vpd.put("var11", varOList.get(i).getString("remittance_name"));	//11
				vpd.put("var12", varOList.get(i).getString("remittance_number"));	//12
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
	
	
	
	
	/* ===============================处理完提现的权限================================== */
	public void getHCOver(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("12"));
		}
	
	}
	/* ===============================权限================================== */
	

	
	/* ===============================提现分控权限================================== */
	public void getHC1(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("11"));
		}
 	}
	/* ===============================权限================================== */

	/* ===============================支付宝待支付权限================================== */
	public void getHC2(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("36"));
		}
 	}
	/* ===============================权限================================== */
	
	
	/* ===============================银联待支付权限================================== */
	public void getHC3(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("37"));
		}
 	}
	/* ===============================权限================================== */
	
	
	/* ===============================异常待支付权限================================== */
	public void getHC4(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("38"));
		}
 	}
	/* ===============================权限================================== */
	
	
	
}
