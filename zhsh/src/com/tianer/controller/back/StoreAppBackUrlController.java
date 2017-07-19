package com.tianer.controller.back;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.zhihui.StoreFeeTihuoTask;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.alipaypay.AlipayConfig;
import com.tianer.util.wxpay.WXPayConfig;
import com.tianer.util.wxpay.WXPayConfigImpl;
import com.tianer.util.wxpay.WXPayPath;
import com.tianer.util.wxpay.WXPayUtil;
 



/** 
 * 会员的App的微信的全部回调地址
 * @author Administrator
 *
 */
@Controller("storeAppBackUrlController")
@RequestMapping(value="/back_sapp")
public class StoreAppBackUrlController extends BaseController { 
	
 
	/**
	 * 
	* 方法名：Alipaynotify
 	* 描述：支付宝返回结果（获取流水单号）
	* 返回类型：json
	* back_pc/alipaynotify.do
	 */
	@RequestMapping(value="/alipaynotify")
	@ResponseBody
	public Object Alipaynotify(HttpServletRequest request) throws Exception{
		Map<String, String> paramsMap = new HashMap<String, String>();  
        Map<String, String[]> requestParams = request.getParameterMap();  
   		try {
   			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {  
   	            String name = iter.next();  
   	            String[] values = requestParams.get(name);  
   	            String valueStr = "";  
   	            for (int i = 0; i < values.length; i++) {  
   	                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";  
   	            }  
   	            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化  
   	            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");  
   	            paramsMap.put(name, valueStr);  
   	        }  
    		boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
 			if(signVerified){
				// TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
				//订单状态成功：TRADE_SUCCESS
				String trade_status=paramsMap.get("trade_status");	
				if(trade_status.equals("TRADE_SUCCESS")){
					//trade_no流水单号
					String out_trade_no=paramsMap.get("out_trade_no");	
					//trade_no流水单号
					String trade_no=paramsMap.get("trade_no");	
 					//total_fee总价
					String total_fee=TongYong.df2.format(Double.parseDouble(paramsMap.get("total_amount").trim())*100);//化为分的单位
					//body订单id
					String body=paramsMap.get("body");
					String resXml="";
					if(body.equals("1")){
//						 resXml=Transaction_pointsPay(out_trade_no, trade_no, total_fee, paramsMap,"alipay");
					 }else if(body.equals("2")){
//						 resXml=Service_Pay(out_trade_no, trade_no, total_fee, paramsMap,"alipay");
					 }else if(body.equals("3")){
						  resXml=Store_cz(out_trade_no, trade_no, total_fee, paramsMap,"alipay");
					 }else{
//						 resXml=BianjiYouXuan_pay(out_trade_no, trade_no, total_fee, paramsMap,"alipay");
					 }
					//logger记录
					ServiceHelper.getAppPcdService().saveLog(paramsMap.toString(), "支付宝回调的订单结果","alipay");
 					return "success";
				}
			}else{
				// TODO 验签失败则记录异常日志，并在response中返回failure.
				AlipayConfig.logResult(paramsMap.toString());
				ServiceHelper.getAppPcdService().saveLog(paramsMap.toString(), "支付宝回调的订单验签失败","0099");
				return "failure";
			}
 		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
  			ServiceHelper.getAppPcdService().saveLog(paramsMap.toString(), "系统异常","9999");
			return "success";
		}
 		return "";
	} 
	
	
	

	
	
	private String success= "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> "; 
	private String notsuccess="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[]]></return_msg></xml>";
	private String notsign="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名错误]]></return_msg></xml>";
	private String notorder="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[订单不存在]]></return_msg></xml>";
	private String notmoney="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[金额不一致]]></return_msg></xml>";
 	
	
	 
	/**
	 * 微信支付回调接口
	* 方法名：Notify
	* back_pc/notify.do
	* 
    */
	@RequestMapping(value="/wxnotify")
	@ResponseBody
	public void wxnotify(PrintWriter out,HttpServletRequest request) throws Exception{
 		String inputLine;
		StringBuilder sb =new StringBuilder();
		String resXml="";//返回数据
 		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				sb.append(inputLine);
			}
			request.getReader().close();
			String xmlStr = sb.toString();
 			//验签
			WXPayPath dodo = new WXPayPath("3");
			boolean signflag=dodo.YanQianHMACSHA256(xmlStr);
			if(!signflag){
				ServiceHelper.getAppPcdService().saveLog(xmlStr, "回调的订单验签失败","0099");
				resXml=notsign;
			}else{
				Map<String, String> map =  WXPayUtil.xmlToMap( xmlStr );
 	 	 		//1.订单id
				String out_trade_no=(String)map.get("out_trade_no");
	 			//2.流水单号
				String tradnumber = (String) map.get("transaction_id");
	 			//3.总金额
				String total_fee=(String) map.get("total_fee");
				//4.支付类型  1-支付扣点充值，2-支付服务费，3-充值，4-支付优选编辑费用
				String attach=(String) map.get("attach");
	 			//是否成功
				Object result_code=map.get("result_code");
				if("SUCCESS".equals(result_code)){ 
						 if(attach.equals("1")){
//							 resXml=Transaction_pointsPay(out_trade_no, tradnumber, total_fee, map,"wx");
						 }else if(attach.equals("2")){
//							 resXml=Service_Pay(out_trade_no, tradnumber, total_fee, map,"wx");
 						 }else if(attach.equals("3")){
							  resXml=Store_cz(out_trade_no, tradnumber, total_fee, map,"wx");
						 }else{
//							 resXml=BianjiYouXuan_pay(out_trade_no, tradnumber, total_fee, map,"wx");
						 }
						//logger记录
						ServiceHelper.getAppPcdService().saveLog(xmlStr, "微信回调的订单结果","wx");
 			 	}else{
 					ServiceHelper.getAppPcdService().saveLog(out_trade_no, "支付失败"+map.toString(),"0099");
 					resXml=notsuccess;	 
				}		
			}
   		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			ServiceHelper.getAppPcdService().saveLog(resXml, "系统异常","9999");
		}
 		out.write(resXml);
		out.close();
 	}
	
	
	
	
	
	
	
	
	
	/**
	 * 商家充值
	 */
	public String Store_cz(String out_trade_no,String tradnumber,String total_fee,Map<String, String>  map,String pay_way){
 		try {
 			PageData pd=new PageData();
 			double actionmoney=(new BigDecimal(Double.parseDouble(total_fee)/100)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
 			//商家充值
     		 PageData historypd=new PageData();
    		 historypd.put("store_wealthhistory_id", out_trade_no);
    		 //历史记录详情
    		 historypd=ServiceHelper.getAppStoreService().findWealthHistoryById(historypd);
    		 if(historypd == null){
    			 ServiceHelper.getAppPcdService().saveLog(out_trade_no, "充值订单不存在"+map.toString(),"0099");
     			 return notorder;
    		 }
    		 //判断金额是否一致
    		 double number=Double.parseDouble(historypd.getString("number"));
     		 if(actionmoney != number){
     			 ServiceHelper.getAppPcdService().saveLog(out_trade_no, "充值金额不匹配"+map.toString(),"0099");
  				 return notmoney;
     		 }
     		historypd.put("pay_way", pay_way);
    		 ////更新财富信息
          	PageData spd=ServiceHelper.getAppStoreService().findById(historypd);
	   		double now_wealth=Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(spd));
    		pd.put("now_wealth", TongYong.df2.format(now_wealth+actionmoney));
   			pd.put("store_id", spd.getString("store_id"));
   			ServiceHelper.getAppStoreService().editWealthById(pd);
  			PageData waterpd=new PageData();
    		waterpd.put("pay_status","1");
    	   	waterpd.put("waterrecord_id",out_trade_no);
   			waterpd.put("user_id", spd.getString("store_id"));
   			waterpd.put("user_type", "1");
    		waterpd.put("withdraw_rate","0");
   			waterpd.put("money_type","1");
   	 		waterpd.put("money", TongYong.df2.format(actionmoney));
   	 		waterpd.put("reduce_money","0");
   			waterpd.put("arrivalmoney",  TongYong.df2.format(actionmoney));
   			waterpd.put("nowuser_money","0");
   			waterpd.put("application_channel", historypd.getString("in_jiqi"));
   			waterpd.put("remittance_name",Const.payjiqi[4] );
   			waterpd.put("remittance_type","4" );
   			waterpd.put("wx_money",   TongYong.df2.format(actionmoney) );
   			waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
    		waterpd.put("createtime",DateUtil.getTime());
   			waterpd.put("over_time",DateUtil.getTime());
   	  		waterpd.put("jiaoyi_type","0");
   			waterpd.put("payee_number",Const.jiuyunumber);
    	 	waterpd.put("order_tn", tradnumber);
   			waterpd.put("province_name", spd.getString("province_name"));
   			waterpd.put("city_name", spd.getString("city_name"));
   			waterpd.put("area_name", spd.getString("area_name"));
    		ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
    		waterpd=null; 
			//更新处理状态
			historypd.put("process_status", "1");
			historypd.put("user_id", Const.jiuyunumber);
			ServiceHelper.getAppStoreService().editWealthHistoryStatus(historypd);
			//获取充值次数
			String recharge_times = ServiceHelper.getStoreService().count(spd.getString("store_id"));
			//充值第一次时进入	
			if(recharge_times == null  || recharge_times.equals("") || recharge_times.equals("0")){
				//判断充值金额
			   		if(actionmoney >= 100){
	 			   	double complex_score=Double.parseDouble(spd.getString("complex_score"))+Double.parseDouble(Const.complexscore[3]);
	 			   	//根据综合分值增加星级
		 			complex_score=complex_score+3;
		 			TongYong.complex_scoreAdd(spd.getString("store_id"), TongYong.df0.format(complex_score));
			   		}
			}
			//充值第二次时进入
			if(recharge_times.equals("1")){
				//第二次充值金额为500时增加商家的综合评分
				if(actionmoney >= 500){
						double complex_score=Double.parseDouble(spd.getString("complex_score"))+Double.parseDouble(Const.complexscore[2]);
						//根据综合分值增加星级
		 				complex_score=complex_score+3;
		 				TongYong.complex_scoreAdd(spd.getString("store_id"), TongYong.df0.format(complex_score));
				}
			}
			//更新充值次数
			pd.put("recharge_times", "1");
			pd.put("store_id", spd.getString("store_id"));
			ServiceHelper.getStoreService().edit(pd);
 		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return success;
		}
 		return success;
	}
	
	 
}
