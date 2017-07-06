package com.tianer.controller.memberapp.bankcard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.memberapp.AppBankcardService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;


/** 
* 类名称：bankcardController
* 创建人：刘耀耀  app银行卡详情
* 创建时间：2016-07-11 
*/
@Controller("app_bankcardController")
@RequestMapping(value="/app_bankcard")
public class bankcardController extends BaseController{
	
	@Resource(name = "appBankcardService")
	private AppBankcardService bankcardService;

	
	/**
	 * 获取银行卡信息
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public Object listbank( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData> varList = bankcardService.listAll(pd);
			if(varList.size()==0){
				message="获取失败";
				map.put("data", "");
			}else{
				//获取银行卡号，截取后4位，放进list做前台显示
				for (PageData pageData : varList) {
					String bank_number = pageData.getString("bank_number");
					pageData.put("number", bank_number);
					bank_number = bank_number.substring(bank_number.length()-4, bank_number.length());
					//卡号以kh取值
					pageData.put("bank_number", bank_number);
	 			}
				map.put("data", varList);
			}
 			varList=null;
		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	/**
	 * 新增银行卡
	 * 刘耀耀
	 * 2016.07.11
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="添加成功";
		PageData pd = new PageData();
		try {
				pd = this.getPageData();
				List<PageData> varList = bankcardService.listAll(pd);
				for(PageData  e : varList){
					if(e.getString("bank_number").equals(pd.getString("bank_number"))){
							map.put("result", "0");
							map.put("message", "当前银行卡已存在，请重新添加！");
							map.put("data", "");
					 		return map;
					}
				}
				varList=null;
				pd.put("member_bankcard_id", BaseController.getTimeID());
				bankcardService.save(pd);			
			     //魅力值：0-50一星会员 50-99 二星会员 100-199 三星会员 200-499  四星会员 500-999 五星会员 1000-2000 一钻会员
			    PageData mpd=appMemberService.findById(pd);
				double charm_number=Double.parseDouble(mpd.getString("charm_number"))+Double.parseDouble(Const.charm_number[8]);
				PageData chpd=new PageData();
				chpd.put("member_id", mpd.getString("member_id"));
				chpd.put("charm_number", charm_number);
				if(charm_number >=0 && charm_number < 50){
					chpd.put("vip_level", "1");
				}else if(charm_number >=50 && charm_number < 100){
					chpd.put("vip_level", "2");
				}else if(charm_number >=100 && charm_number < 200){
					chpd.put("vip_level", "3");
				}else if(charm_number >=200 && charm_number < 500){
					chpd.put("vip_level", "4");
				}else if(charm_number >=500 && charm_number < 1000){
					chpd.put("vip_level", "5");
				}else if(charm_number >=1000 && charm_number < 2000){
					chpd.put("vip_level", "6");
				}
				appMemberService.edit(chpd);
				chpd=null;
				pd=null;
		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			 e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
 		return map;
	}
	
	
	/**
	 * 新增支付宝
	 */
	@RequestMapping(value="/saveAliay")
	@ResponseBody
	public Object saveAliay() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="添加成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(pd.getString("alipay_number") == null || pd.getString("alipay_number").equals("") || pd.getString("alipay_name") == null || pd.getString("alipay_name").equals("")){
				result="0";
				message="支付宝账户信息不全";
			}else{
				List<PageData> varList = bankcardService.listAlipay(pd);
				if(varList.size() == 0){
					pd.put("member_alipay_id", BaseController.getTimeID());
					bankcardService.saveAlipay(pd);
				}else{
					result="0";
					message="当前支付宝账号已添加";
				}
			}
			
 		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		return map;
	}
	/**
	 * 删除支付宝账号
	 */
	@RequestMapping(value="/deleteAlipay")
	@ResponseBody
	public Object deleteAlipay() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="添加成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(pd.getString("member_alipay_id") != null){
				bankcardService.deleteAlipay(pd);
			}else{
				result="0";
				message="支付宝id不能为空";
			}
 		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		return map;
	}
	
	/**
	 * 支付宝集合
	 */
	@RequestMapping(value="/AliayList")
	@ResponseBody
	public Object AliayList() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//支付宝列表
			List<PageData> alipayList = bankcardService.listAlipay(pd);
			for (PageData pageData : alipayList) {
				String alipay_number_xx = pageData.getString("alipay_number");
				if(alipay_number_xx.length() == 11){
					alipay_number_xx=alipay_number_xx.substring(0, 4)+"****"+alipay_number_xx.substring(7, 11);
				}
				pd.put("alipay_number_xx", alipay_number_xx);
  			}
			map.put("data", alipayList);
			alipayList=null;
		} catch (Exception e) {
			result = "0";
			message="系统异常";
			map.put("data", "");
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	/**
	 * 获取提现时所需的信息
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/listAllTXInfor")
	@ResponseBody
	public Object listAllTXInfor(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData> bankList = bankcardService.listAll(pd);
 			if(bankList.size() > 0 ){
 				//获取银行卡号，截取后4位，放进list做前台显示
 				for (PageData pageData : bankList) {
 					String kahao = pageData.getString("bank_number");
 					if(kahao.length() > 4){
 						String kh = kahao.substring(kahao.length()-4, kahao.length());
 						//卡号以kh取值
 						pageData.put("kh", kh);
 					}
 	  			}
 			}
 			map1.put("bankList", bankList);
 			bankList=null;
 			//支付宝列表
			List<PageData> alipayList = bankcardService.listAlipay(pd);
			if(alipayList.size() > 0 ){
				for (PageData pageData : alipayList) {
					String alipay_number_xx = pageData.getString("alipay_number");
					if(alipay_number_xx.length() == 11){
						alipay_number_xx=alipay_number_xx.substring(0, 4)+"****"+alipay_number_xx.substring(7, 11);
					}
					pageData.put("alipay_number_xx", alipay_number_xx);
	  			}
			}
			map1.put("alipayList", alipayList);
			alipayList=null;
		} catch(Exception e){
			result="0";
			message="获取异常";
			map.put("data", "");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data",map1);
		return map;
	}
	
	
	/**
	 * 会员获取绑定卡片（支付宝/银行卡）和解除绑定卡片的验证码
	 * app_bankcard/findCodeByCard.do?in_position=c&type=7&phone= 
	 * 
	 * in_position 传 c
	 * type 7-绑定 ，10-解绑
	 * phone 手机号
	 * 
 	 */
	@RequestMapping(value="/findCodeByCard")
	@ResponseBody
	public Object findCodeByCard(HttpServletRequest request){
 		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		String code=StringUtil.getSixRandomNum();
		String result="1";
		String message="验证码已发至你手机，注册查收";
		try{
			pd=this.getPageData();
			String phone=pd.getString("phone");//手机号
			String type=pd.getString("type");//获取验证码的类型
 			String in_position=pd.getString("in_position");//所在位置：b,c,pc,wx 
 			 //验证是否可以发送验证码---倒计时60秒
 			 PageData issend=TongYong.Okmessage(phone,"2");
 			 if(issend.get("result").equals("0")){
 				map.put("result", "0");
		 		map.put("message",issend.getString("message"));
		 		map.put("data", "");
		 		return map;
 			 }
 			if(type.equals("7")){//绑定银行卡,支付宝
 				 SmsUtil.TXNumberCode(phone, code);
      	  	}else if(type.equals("10")){//删除绑定的支付宝/银行卡
 				 SmsUtil.JbTXNumberCode(phone, code);
   	  		}
  			SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_MEMBER_CARDCODE , code);
  			//=======================================
 			 if(in_position.equals("c")){
				 map.put("data", code);
			 }else{
				 map.put("data", "");
			 }
		} catch(Exception e){
			logger.error(e.toString(), e);
			 result="0";
			 message= "系统错误";
 		}
		map.put("result", result);
 		map.put("message", message);
		return map;
	}
	
	
	

}
