package com.tianer.controller.alipayback;

import java.text.DecimalFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.util.PageData;



/** 
 * 支付宝的全部回调地址
 * @author Administrator
 *
 */
@Controller("alipayBackUrlController")
@RequestMapping(value="/alipayback")
public class AlipayBackUrlController extends BaseController { 
	

	/**
	 * APP
	 */
	
	/**
	 * 
	* 方法名：AlipayorderPay
	 */
	@RequestMapping(value="/AlipayorderPay")
	@ResponseBody
	public Object AlipayorderPay() throws Exception{
		logBefore(logger, "支付宝返回结果（获取流水单号）");
 		PageData pd = new PageData();
		pd = this.getPageData();
 		//订单状态成功：TRADE_SUCCESS
		String trade_status=pd.getString("trade_status");	
		if(trade_status.equals("TRADE_SUCCESS")){
			//trade_no流水单号
			String trade_no=pd.getString("trade_no");	
 			//total_fee总价
			String total_fee=pd.getString("total_fee").trim();
			//body订单id
			String body=pd.getString("body");
 
  			return "success";
		}
		return "";
	} 
	
	/**
	 * PC
	 */
	
	
	
}
