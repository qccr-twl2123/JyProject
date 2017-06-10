package com.tianer.controller.storepc.store_bankcard;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.service.storeapp.Storeapp_bankcardService;
import com.tianer.service.storepc.store_bankcard.Storepc_bankcardService;
import com.tianer.util.Const;
import com.tianer.util.GetBankNameForNumber;
import com.tianer.util.PageData;


/** 
 * 
* 类名称：Storepc_bankcardController   
* 类描述：   账户设置
* 创建人：邢江涛  
* 创建时间：2016年6月22日 
 */
@Controller("storeStorepc_bankcardController")
@RequestMapping(value = "/storepc_bankcard")
public class Storepc_bankcardController extends BaseController{
 

	
	@Resource(name = "storeapp_bankcardService")
	private Storeapp_bankcardService storeapp_bankcardService;
	
	

	/**
	 * 获取开户行名称
	 * storepc_bankcard/getBankName.do?cardNumber=
	 */
	@RequestMapping(value="/getBankName")
	public void GetBankName(PrintWriter out,String cardNumber){
 		try{
 			cardNumber=cardNumber.replaceAll(" ", "");
			String bankname=GetBankNameForNumber.getNameOfBank(cardNumber);
			out.print(bankname);
 			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 	}

	
	/**
	 * 新增银行卡信息
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(){
 		Map<String,Object> map = new HashMap<String,Object>();
		Session shirosession = SecurityUtils.getSubject().getSession();
		PageData pd = new PageData();
		String message="新增成功";
		String result="1";
		try {
			pd = this.getPageData();
			String code=pd.getString("code");
			String messagecode=(String) shirosession.getAttribute(Const.SESSION_STORE_CARDCODE);
			if(!code.equals(messagecode)){
				result="0";
				message="验证码错误";
			}else{
				List<PageData> varList = pcbankcardService.listAll(pd);
				if(varList.size() == 0){
					pd.put("single_payment_limit", "1000");
					pd.put("daily_payment_limit", "1000");
					pd.put("store_bankcard_id", BaseController.getTimeID());	//主键
					pcbankcardService.save(pd);
				}
				shirosession.removeAttribute(Const.SESSION_STORE_CARDCODE);
			}
  		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	

	@Resource(name = "storepc_bankcardService")
	private Storepc_bankcardService pcbankcardService;
	
	
	/**
	 * 新增支付宝信息
	 */
	@RequestMapping(value="/saveAliay")
	@ResponseBody
	public Object saveAliay(){
 		Map<String,Object> map = new HashMap<String,Object>();
		Session shirosession = SecurityUtils.getSubject().getSession();
		PageData pd = new PageData();
		String message="新增成功";
		String result="1";
		try {
			pd = this.getPageData();
			String code=pd.getString("code");
			String messagecode=(String) shirosession.getAttribute(Const.SESSION_STORE_CARDCODE);
			if(!code.equals(messagecode)){
				result="0";
				message="验证码错误";
			}else{
				List<PageData> varList = pcbankcardService.listAlipay(pd);
				if(varList.size() == 0){
					pd.put("store_alipay_id", BaseController.getTimeID());
					pcbankcardService.saveAlipay(pd);
				}
				shirosession.removeAttribute(Const.SESSION_STORE_CARDCODE);
			}
  		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
//	/**
//	 * 新增支付宝账号
//	 */
//	@RequestMapping(value="/saveAliay")
//	public ModelAndView saveAliay(){
//		ModelAndView mv = this.getModelAndView();
//		
//		PageData pd = new PageData();
//		try {
//			pd = this.getPageData();
//			List<PageData> varList = bankcardService.listAlipay(pd);
//			if(varList.size() == 0){
//				pd.put("store_alipay_id", this.get32UUID());
//				bankcardService.saveAlipay(pd);
//			}
// 		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//		mv.setViewName("redirect:list.do?save_type=0&store_id="+pd.getString("store_id"));
//		return mv;
//	}
	
	

	
	/**
	 * 银行卡/支付宝列表
 	 */
	@RequestMapping(value="/list")
	public ModelAndView listAllBankAlipay(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<PageData>	bankList = pcbankcardService.list(pd);	//列出Store列表
			//获取银行卡号，截取后4位，放进list做前台显示
			for (PageData e : bankList) {
				String kahao = e.getString("bank_number");
				if(kahao.length() > 4){
					String kh = kahao.substring(kahao.length()-4, kahao.length());
					//卡号以kh取值
					e.put("kh", kh);
				}
  			}
			mv.addObject("bankList", bankList);
			bankList=null;
			//支付宝列表
			List<PageData> alipayList = pcbankcardService.listAlipay(pd);
			for (PageData e : alipayList) {
				String alipay_number = e.getString("alipay_number");
				if(alipay_number.length() == 11){
					alipay_number=alipay_number.substring(0, 4)+"****"+alipay_number.substring(7, 11);
					e.put("alipay_number", alipay_number);
				}
   			}
			mv.addObject("alipayList", alipayList);
			alipayList=null;
 			mv.addObject("pd", pd);
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.setViewName("storepc/business_account8");
		return mv;
		
	}
	
	
	/**
	 * 删除银行卡信息
	 */
	@RequestMapping(value="/deleteCard")
	@ResponseBody
	public Object deleteCard(){
		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		String message="删除成功";
		String result="1";
		try {
			pd = this.getPageData();
			String delcode=pd.getString("delcode");
			String type=pd.getString("type");
			String messagecode=(String) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_STORE_CARDCODE);
			if(!delcode.equals(messagecode)){
				result="0";
				message="验证码错误";
			}else{
				if(type.equals("1")){
					pcbankcardService.deleteBank(pd);
				}else{
					pcbankcardService.deleteAlipay(pd);
				}
				SecurityUtils.getSubject().getSession().removeAttribute(Const.SESSION_STORE_CARDCODE);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	 
 


}
