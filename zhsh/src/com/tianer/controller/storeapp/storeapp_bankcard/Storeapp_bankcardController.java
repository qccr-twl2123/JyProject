package com.tianer.controller.storeapp.storeapp_bankcard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.business.store.StoreService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storeapp.Storeapp_bankcardService;
import com.tianer.service.storepc.store_bankcard.Storepc_bankcardService;
import com.tianer.util.PageData;


/** 
* 类名称：Storeapp_wealthhistoryController
* 创建人：邢江涛  app银行卡详情
* 创建时间：2016-06-29 
*/
@Controller("storeapp_bankcardController")
@RequestMapping(value="/storeapp_bankcard")
public class Storeapp_bankcardController extends BaseController{
	
	@Resource(name = "storeapp_bankcardService")
	private Storeapp_bankcardService bankcardService;
	
	/**
	 * 获取银行卡/支付宝详情
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findbankOrAlipayById")
	@ResponseBody
	public Object findbankOrAlipayById(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			PageData bankpd = bankcardService.findbankById(pd);
 			if(bankpd==null){
 				pd=bankcardpcService.findbyAlipay(pd);
 				if(pd != null){
 					String alipay_number_xx = pd.getString("alipay_number");
 					if(alipay_number_xx.length() == 11){
 						alipay_number_xx=alipay_number_xx.substring(0, 4)+"****"+alipay_number_xx.substring(7, 11);
 					}
 					pd.put("alipay_number_xx", alipay_number_xx);
 				}else{
 					pd = new PageData();
 				}
 			}else{
 				//获取list中的卡号
				String kahao = bankpd.getString("bank_number");
				//取得卡号后四位
				if(kahao.length() >5){
					String kh = kahao.substring(kahao.length()-4, kahao.length());
					bankpd.put("kh",kh);
				}else{
					bankpd.put("kh",kahao);
				}
  				pd=bankpd;
  			}
    	} catch(Exception e){
			result="0";
			message="系统异常";
			map.put("data", "");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data", pd);
		return map;
	}
	
	
	
	
	
	/**
	 * 获取银行卡详情
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findbankById")
	@ResponseBody
	public Object findbankById(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			pd = bankcardService.findbankById(pd);
    		} catch(Exception e){
			result="0";
			message="系统异常";
			map.put("data", "");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data", pd);
		return map;
	}
	
	

	/**
	 * 获取银行卡详情
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/listinfo")
	@ResponseBody
	public Object listbankInfo( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			//获取银行卡号码
			String bank_number = pd.getString("bank_number");
			pd.put("bank_number", bank_number);
			List<PageData> varList = bankcardService.listbank(pd);
			if(varList.size()==0){
				message="获取成功";
				map.put("data", "");
			}else{
				//获取list中的卡号
				String kahao = varList.get(0).getString("bank_number");
				//取得卡号后四位
				String kh = kahao.substring(kahao.length()-4, kahao.length());
				pd.put("kh",kh);
				varList.add(pd);
				map.put("data", varList);
			}
 		} catch(Exception e){
			result="0";
			message="系统异常";
			map.put("data", "");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	
	
	
	
	/**
	 * 获取银行卡列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/listAll")
	@ResponseBody
	public Object listbank(Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> varList = bankcardService.listAll(pd);
			if(varList.size()==0){
				message="获取成功";
				map.put("data", "");
			}
			//获取银行卡号，截取后4位，放进list做前台显示
			for (PageData pageData : varList) {
				String kahao = pageData.getString("bank_number");
				if(kahao.length() > 4){
					String kh = kahao.substring(kahao.length()-4, kahao.length());
					//卡号以kh取值
					pageData.put("kh", kh);
				}
  			}
 			map.put("data", varList);
		} catch(Exception e){
			result="0";
			message="获取异常";
			map.put("data", "");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	/**
	 * 新增银行卡
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
			if(varList.size()!=0){
				 result = "0";
				 message="当前银行卡你已添加过";
			}else{
 				//商家id
				String store_id = pd.getString("store_id");
				pd.put("store_id", store_id);
				//银行卡id
				pd.put("store_bankcard_id", BaseController.getTimeID());	
				//账户名
				String bankcard_name = pd.getString("bankcard_name");
				pd.put("bankcard_name", bankcard_name);
				//银行卡号
				String bank_number = pd.getString("bank_number");
				pd.put("bank_number", bank_number);
 				//开户行
				String account = pd.getString("account");
				pd.put("account", account);
				//手机号
				String phone = pd.getString("phone");
				pd.put("phone", phone);
				//是否綁定为默认
				String isdefault = pd.getString("isdefault");
				pd.put("isdefault", isdefault);
				//单笔支付限额
				String single_payment_limit = "1000";
				pd.put("single_payment_limit", single_payment_limit);
				//每日支付限额
				String daily_payment_limit = "1000";
				pd.put("daily_payment_limit", daily_payment_limit);
				//网点
				String dot = pd.getString("dot");
				pd.put("dot", dot);
				bankcardService.save(pd);
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
	 
	

	@Resource(name = "storepc_bankcardService")
	private Storepc_bankcardService bankcardpcService;
	
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
				List<PageData> varList = bankcardpcService.listAlipay(pd);
				if(varList.size() == 0){
					pd.put("store_alipay_id", BaseController.getTimeID());
					bankcardpcService.saveAlipay(pd);
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
		String message="解绑成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(pd.getString("store_alipay_id") != null){
				bankcardpcService.deleteAlipay(pd);
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
	 * 解除银行卡
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/deletebank")
	@ResponseBody
	public Object deletebank( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="解除成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			bankcardService.deleteBank(pd);
		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data","");
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
			List<PageData> alipayList = bankcardpcService.listAlipay(pd);
			for (PageData pageData : alipayList) {
				String alipay_number_xx = pageData.getString("alipay_number");
				if(alipay_number_xx.length() == 11){
					alipay_number_xx=alipay_number_xx.substring(0, 4)+"****"+alipay_number_xx.substring(7, 11);
				}
				pageData.put("alipay_number_xx", alipay_number_xx);
  			}
			map.put("data", alipayList);
			alipayList=null;
		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
			map.put("data", "");
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	/**
	 * 支付宝详情 store_alipay_id
	 */
	@RequestMapping(value="/findbyAlipay")
	@ResponseBody
	public Object findbyAlipay() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 			pd=bankcardpcService.findbyAlipay(pd);
			if(pd != null){
				String alipay_number_xx = pd.getString("alipay_number");
				if(alipay_number_xx.length() == 11){
					alipay_number_xx=alipay_number_xx.substring(0, 4)+"****"+alipay_number_xx.substring(7, 11);
				}
				pd.put("alipay_number_xx", alipay_number_xx);
			}
  		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
			map.put("data", "");
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
 		return map;
	}
	
	@Resource(name="storeService")
	private StoreService storeService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	/**
	 * 我的银行卡数量+账户余额
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/bankCount")
	@ResponseBody
	public Object bankCount(){
		Map<String,Object> map = new HashMap<String,Object>();
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			//提现费率
			if(storeService.findById(pd) != null){
				String withdraw_rate=storeService.findById(pd).getString("withdraw_rate");
				pd.put("withdraw_rate", withdraw_rate);
			}else{
				pd.put("withdraw_rate", "0");
			}
			//商家id
			String store_id = pd.getString("store_id");
			pd.put("store_id", store_id);
			//我的银行卡数量
			String count = bankcardService.bankCount(pd);
			//我的支付宝数量
			String alipayCount = bankcardService.AlipayCount(pd);
			//账户余额
			String allnow_wealth=appStoreService.sumStoreWealth(pd); 
 			pd.put("count", count);//银行卡数量
			pd.put("alipayCount", alipayCount);//支付宝数量
			pd.put("yue", allnow_wealth);//当前的商家余额
 		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data",pd);
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
			//获取银行卡号，截取后4位，放进list做前台显示
			for (PageData pageData : bankList) {
				String kahao = pageData.getString("bank_number");
				if(kahao.length() > 4){
					String kh = kahao.substring(kahao.length()-4, kahao.length());
					//卡号以kh取值
					pageData.put("kh", kh);
				}
  			}
 			map1.put("bankList", bankList);
 			bankList=null;
 			//支付宝列表
			List<PageData> alipayList = bankcardpcService.listAlipay(pd);
			for (PageData pageData : alipayList) {
				String alipay_number_xx = pageData.getString("alipay_number");
				if(alipay_number_xx.length() == 11){
					alipay_number_xx=alipay_number_xx.substring(0, 4)+"****"+alipay_number_xx.substring(7, 11);
				}
				pageData.put("alipay_number_xx", alipay_number_xx);
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

}
