package com.tianer.controller.memberapp.withdraw_approval;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/** 
 * 
* 类名称：Withdraw_approvalController   
* 类描述：   提现接口
* 创建人：魏汉文  
* 创建时间：2016年7月4日 上午10:01:41
 */
@Controller("appWithdraw_approvalController")
@RequestMapping(value="/app_withdraw_approval")
public class Withdraw_approvalController extends BaseController {
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	

 	public DecimalFormat df2=TongYong.df2;
	
	/**
	 * 用户新增提现记录魏汉文20160704
	 */
	@RequestMapping(value="/saveWithdraw")
	@ResponseBody
	public Object save() throws Exception{
//		logBefore(logger, "新增Withdraw_approval");
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="提现审批，请等待1至2个工作日";
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
 				//判断今天是否已经申请提现过
// 				String day=DateUtil.getDay();
//  				pd.put("day", day);
//  				if(!ServiceHelper.getWaterRecordService().isTiXianForDay(pd).equals("0")){
//					map.put("result", "0");
//					map.put("message", "今日你已提现过");
//					map.put("data", "");		
//					return AppUtil.returnObject(pd, map);
//				}
 				PageData e=new PageData();
				double money=Double.parseDouble(pd.getString("money"));
				if(pd.getString("user_type").equals("1")){ }else if(pd.getString("user_type").equals("2")){//会员
						e.put("member_id", pd.getString("user_id"));
						e=appMemberService.findById(e);
						double now_money=Double.parseDouble(e.getString("now_money"));
 						if(now_money < money){
							result="0";
							message="余额不足";
							map.put("data", "");
						}else{
 							//减少用户余额
							double n=now_money-money;
							e.put("now_money", df2.format(n));
							appMemberService.edit(e);
							//	个人余额消费历史
							String withdraw_approval_id=BaseController.getTXUID(e.getString("show_lookid"));
			   				now_money=now_money-n;
							PageData moneypd=new PageData();
							moneypd.put("member_id", e.getString("member_id"));
		 					moneypd.put("wealth_type", "2");
							moneypd.put("consume_type", "2");
 							moneypd.put("content", "提现");
							moneypd.put("number", df2.format(-money));
							moneypd.put("now_money", df2.format(n));
							moneypd.put("jiaoyi_id", withdraw_approval_id);
 	   						moneypd.put("member_wealthhistory_id",withdraw_approval_id);
							moneypd.put("jiaoyi_status", "0");
							appMember_wealthhistoryService.saveWealthhistory(moneypd);
							//新增总后台提现充值/记录
	 			   			PageData waterpd=new PageData();
		    				waterpd.put("pay_status","0");
		    	   			waterpd.put("waterrecord_id",withdraw_approval_id);
		   					waterpd.put("user_id", pd.getString("user_id"));
		   					waterpd.put("user_type", "2");
		    				waterpd.put("withdraw_rate","0");
		   					waterpd.put("money_type","5");
		   	 				waterpd.put("money", "-"+df2.format(money));
		   	 				waterpd.put("reduce_money","0");
		   					waterpd.put("arrivalmoney",  "-"+df2.format(money));
		   					waterpd.put("nowuser_money",df2.format(now_money) );
		   					waterpd.put("application_channel", "1" );
		    				waterpd.put("remittance_name",pd.getString("account_name") + "-" + pd.getString("withdraw_username") );
 		   					if(pd.getString("account_type").equals("3")){
 		   						waterpd.put("remittance_type","3" );
		   						waterpd.put("alipay_money",  "-"+df2.format(money) );
		   					}else{
		   						waterpd.put("remittance_type","1" );
		   						waterpd.put("bank_money", "-"+df2.format(money) );
		   					}
		   					waterpd.put("remittance_number",pd.getString("withdraw_number"));//支付人的支付账号
		    				waterpd.put("createtime",DateUtil.getTime());
		   					waterpd.put("over_time",DateUtil.getTime());
		   	  				waterpd.put("jiaoyi_type","0");
		   					waterpd.put("payee_number",Const.jiuyunumber);
 		    	 			waterpd.put("order_tn", withdraw_approval_id);
		   					waterpd.put("province_name", e.getString("province_name"));
		   					waterpd.put("city_name", e.getString("city_name"));
		   					waterpd.put("area_name", e.getString("area_name"));
		    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
		    				waterpd=null;
		    				pd=null;
		    				e=null;
						}
				}
 		}catch(Exception e){
				result="0";
				message="系统异常";
				map.put("data", e.toString());
				logger.error(e.toString(), e);
		}
		
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");		
		return map;
	}
	
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService appMember_wealthhistoryService;
	
	/**
	 * 提现记录魏汉文20160706
	 */
	@RequestMapping(value="/withdrawList")
	@ResponseBody
	public Object withdrawList() throws Exception{
//		logBefore(logger, "提现记录");
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			pd.put("checked_type", "2");
			List<PageData> withdrawdList=ServiceHelper.getWaterRecordService().listAllWaterRecord(pd);
			if(withdrawdList.size() == 0){
				map.put("data", "");		
			}else{
				map.put("data", withdrawdList);		
			}
 		}catch(Exception e){
 			result="0";
			message="系统异常";
			map.put("data", e.toString());
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
  
 
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
