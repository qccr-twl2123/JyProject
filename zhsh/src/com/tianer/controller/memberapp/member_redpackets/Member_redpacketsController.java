package com.tianer.controller.memberapp.member_redpackets;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.zhihui.JFredTask;
import com.tianer.entity.zhihui.ZZredTask;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/** 
 * 类名称：Store_redpacketsController
 * 创建人：刘耀耀
 * 创建时间：2016-06-27
 */
@Controller("appMember_redpacketsController")
@RequestMapping(value="/app_member_redpackets")
public class Member_redpacketsController extends BaseController {
	
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService member_redpacketsService;
	
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	

 	public DecimalFormat df2=TongYong.df2;
	
	/**
	 * 未过期红包列表
	 * 刘耀耀	 2016.06.27
	 * 魏汉文 20160629
	 */
	@RequestMapping(value="/listAllRedPackage")
	@ResponseBody
	public Object listAllRedPackage(){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("isguoqi", "0");
 			List<PageData>	varList = member_redpacketsService.list(pd);	//列出Friend列表
 			for(PageData e : varList){
 				String redpackage_type=e.getString("redpackage_type");
 				if(redpackage_type.equals("2")){
 					e.put("redpackage_type_name", "折");
 				}else{
 					e.put("redpackage_type_name", "元");
 				}
 			}
			if(varList.size()==0){
				map.put("data", "");
 				message="暂无红包";
			}else{
			map.put("data", varList);
			}
		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 过期红包列表
	 * 刘耀耀	
	 * 2016.06.27
	 */
	@RequestMapping(value="/listOver")
	@ResponseBody
	public Object listOver(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("isguoqi", "1");
			List<PageData>	varList = member_redpacketsService.list(pd);	//列出Friend列表
			for(PageData e : varList){
 				String redpackage_type=e.getString("redpackage_type");
 				if(redpackage_type.equals("2")){
 					e.put("redpackage_type_name", "折");
 				}else{
 					e.put("redpackage_type_name", "元");
 				}
 			}
			if(varList.size()==0){
				map.put("data", "");
 				message="暂无红包";
			}else{
			map.put("data", varList);
			}
		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 发送积分红包
	 * app_member_redpackets/sendRedPackage.do
	 *  
	 *  user_id
	 *  user_type
	 *  redpackage_money
	 *  redpackage_number
	 *  redpackage_type
	 */
	@RequestMapping(value="/sendRedPackage")
	@ResponseBody
	public Object SendJFRedPackage(){
 		Map<String,Object> map = new HashMap<String,Object>();
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		String result = "1";
		String message="发送成功";
		PageData pd = new PageData();
		try{ 
				pd=this.getPageData();
				String user_id=pd.getString("user_id");
				String user_type=pd.getString("user_type");
				String _redpackage_money=pd.getString("redpackage_money"); //积分红包金额
				String redpackage_number=pd.getString("redpackage_number");  
//				String redpackage_type=pd.getString("redpackage_type");   //随机，平均
				if(_redpackage_money == null || _redpackage_money.equals("") || Double.parseDouble(_redpackage_money) <= 0){
					map.put("result", "0");
					map.put("message", "金额必须大于0");
					map.put("data", "");
					return map;
				}
				if(redpackage_number == null || redpackage_number.equals("") ||  Integer.parseInt(redpackage_number) <= 0){
					map.put("result", "0");
					map.put("message", "数量必须大于0");
					map.put("data", "");
					return map;
				}
				pd.put("redpackage_number", String.valueOf(Integer.parseInt(redpackage_number)));
 				//对积分红包进行判断
  				PageData e = new PageData();
				if(user_type.equals("1")){
					
				}else if(user_type.equals("2")){//会员
						e.put("member_id", user_id);
						e=appMemberService.findById(e);
						if(e != null){
							String ms_redpackage_id=BaseController.getREDUID(e.getString("show_lookid"));
							double now_integral=Double.parseDouble(e.getString("now_integral"));
							double redpackage_money=Double.parseDouble(_redpackage_money);
							if(now_integral < redpackage_money){
								result="0";
								message="积分不足";
								map.put("data", "");
							}else{
								pd.put("ms_redpackage_id",  ms_redpackage_id);
								member_redpacketsService.saveSendRed(pd);
								//减少用户积分余额
								double n=now_integral-redpackage_money;
								e.put("now_integral", df.format(n));
								appMemberService.edit(e);
								map.put("data", ms_redpackage_id);
								//新增财富历史记录
  								PageData moneypd=new PageData();
								moneypd.put("member_wealthhistory_id",ms_redpackage_id );
								moneypd.put("member_id", user_id);
		 						moneypd.put("wealth_type", "1");
								moneypd.put("consume_type", "2");
								moneypd.put("content", "发送积分红包");
								moneypd.put("number", df.format(-redpackage_money));
								moneypd.put("now_money", df.format(n));
								moneypd.put("jiaoyi_id", ms_redpackage_id);
								moneypd.put("jiaoyi_status", "1");
								moneypd.put("in_jiqi", "1");
								appMember_wealthhistoryService.saveWealthhistory(moneypd); 
  								//新增总流水记录
				 	   			PageData waterpd=new PageData();
					   			waterpd.put("pay_status","1");
				   				waterpd.put("waterrecord_id",ms_redpackage_id);
								waterpd.put("user_id", user_id);
								waterpd.put("user_type", "2");
								waterpd.put("withdraw_rate","0");
								waterpd.put("money_type","10");
								waterpd.put("money", TongYong.df2.format(-redpackage_money));
								waterpd.put("reduce_money", "0");
								waterpd.put("arrivalmoney", TongYong.df2.format(-redpackage_money));
								waterpd.put("nowuser_money",TongYong.df2.format(-redpackage_money) );
								waterpd.put("application_channel","2" );
								waterpd.put("remittance_name","发送积分红包:"+redpackage_money+"积分");
								waterpd.put("remittance_number",user_id);//支付人的支付账号
								waterpd.put("remittance_type","7" );
								waterpd.put("integral_money", TongYong.df2.format(-redpackage_money) );
				 				waterpd.put("createtime",DateUtil.getTime());
								waterpd.put("over_time",DateUtil.getTime());
				 				waterpd.put("jiaoyi_type","6");
								waterpd.put("payee_number",Const.jiuyunumber);
					 			waterpd.put("order_tn",  ms_redpackage_id);
								waterpd.put("province_name", e.getString("province_name"));
								waterpd.put("city_name", e.getString("city_name"));
								waterpd.put("area_name", e.getString("area_name"));
								ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
								waterpd=null;
								//新增定时器
								JFredTask jf=new JFredTask(ms_redpackage_id);
	  	 						Timer tt=new Timer();
	  	 						tt.schedule(jf, Const.jfguoqi);
							}
						}
 				}
  		} catch(Exception e){
			result = "0";
			message="系统错误";
			map.put("data", "");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService appMember_wealthhistoryService;
	
	/**
	 * 
	 * 旧接口不使用 ----转增商家红包拥有者魏汉文20160701
	 * 
	 * newmember_id
	 * redpackage_id
	 * member_id
	 * 
	 * app_member_redpackets/editRedPackageForId.do
	 */
	@RequestMapping(value="/editRedPackageForId")
	@ResponseBody
	public synchronized Object editRedPackageForId(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="领取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			//判断红包是否已经被抢了
			PageData e=new PageData();
 			e=member_redpacketsService.findById(pd);
 			if(e== null){
 				result="0";
 				message="当前红包已被领取";
 				pd.remove("member_id");
 				e=member_redpacketsService.findById(pd);
 				if(e==null){
 					message="当前红包不存在";
 				}
 			}else{
 				String isshiyong=e.getString("isshiyong");
  				if(isshiyong.equals("0")){
 					if(pd.getString("member_id").equals(pd.getString("newmember_id"))){
 	 					//自己点自己的红包
 						message="当前红包未被领取";
 	  				}else{
 	 					member_redpacketsService.editRedPackageForId(pd);
 	 	 				//更新红包数量
 	 	 				ServiceHelper.getAppMemberService().updateMemberRedNumber(pd);
 	 	 				pd.put("member_id", pd.getString("newmember_id"));
 	 	 				ServiceHelper.getAppMemberService().updateMemberRedNumber(pd);
 	 				}
 				}else{
 					message="该红包已被使用，无法领取";
 				}
   			}
  			map.put("data",e);
 		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	/**
 	 * 转增商家红包修改红包状态 (发送红包的时候调接口，多个redpackage_id用,拼接)
  	 * redpackage_id
 	 * 
	 * app_member_redpackets/editRedPackageStatus.do
	 * 
	 */
	@RequestMapping(value="/editRedPackageStatus")
	@ResponseBody
	public synchronized Object editRedPackageStatus(){
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="转增进行中";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String redpackage_id=pd.getString("redpackage_id");
			//查看红包详情
			pd.put("isshiyong", "1");
			pd.put("iszhuanzeng", "1");
			member_redpacketsService.editRedStatus(pd);
 			ServiceHelper.getAppMemberService().updateMemberRedNumber(member_redpacketsService.findById(pd));
			//设置定时器
			ZZredTask zz=new ZZredTask(redpackage_id);
			Timer tt=new Timer();
			tt.schedule(zz, Const.zzredpakagetime);
   		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
 		return map;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 20170504
	 * 转增商家红包拥有者魏汉文 
	 * 
	 * newmember_id
	 * redpackage_id
	 * member_id
	 * 
	 * app_member_redpackets/editRedPackageForIdTwo.do
	 * 
	 * 返回结果：
	 * red_status 0-未被领取，1-已领取，99-红包已过期（时间到了未领取/重启项目了）
	 * sendpd  发送红包人的信息 
	 * redpd 接收红包任的信息 
	 */
	@RequestMapping(value="/editRedPackageForIdTwo")
	@ResponseBody
	public synchronized Object editRedPackageForIdTwo(){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="领取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//获取发红包人的详情
			PageData sendpd=ServiceHelper.getAppMemberService().contactMember(pd);
			map1.put("sendpd", sendpd); 
  			//判断红包是否已经被抢了
			PageData e=new PageData();
 			e=member_redpacketsService.findById(pd);
 			if(e== null){
  				message="当前红包已被领取";
 				map1.put("red_status", "1"); 
 				pd.remove("member_id");
 				e=member_redpacketsService.findById(pd);
 			}else{
 				String iszhuanzeng=e.getString("iszhuanzeng");
 				if(iszhuanzeng.equals("1")){
 					if(pd.getString("member_id").equals(pd.getString("newmember_id"))){
 	 					//自己点自己的红包
 	 					map1.put("red_status", "0");
 	 					message="当前红包未被领取";
 	  				}else{
 	 					member_redpacketsService.editRedPackageForId(pd);
 	 	 				//更新红包数量
 	 	 				ServiceHelper.getAppMemberService().updateMemberRedNumber(pd);
 	 	 				pd.put("member_id", pd.getString("newmember_id"));
 	 	 				ServiceHelper.getAppMemberService().updateMemberRedNumber(pd);
 	 	 				//=======
 	 	 				map1.put("red_status", "1");
 	 	 				e=member_redpacketsService.findById(pd);
 	 				}
 				}else{
 					map1.put("red_status", "99");
 					message="转增时间已到期，未被领取";
 				}
   			}
 			if(e != null && e.getString("name") != null && e.getString("name").length() == 11){
			    e.put("name", e.getString("name").substring(0, 3)+"****"+e.getString("name").substring(7, 11));
			} 
 			map1.put("redpd",e);
 		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
 		return map;
	}
	
	
	
	

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
