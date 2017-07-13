package com.tianer.controller.wxback;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.wxpay.WXPayConfig;
import com.tianer.util.wxpay.WXPayConfigImpl;
import com.tianer.util.wxpay.WXPayPath;
import com.tianer.util.wxpay.WXPayUtil;
 



/** 
 * 会员的App的微信的全部回调地址
 * @author Administrator
 *
 */
@Controller("storeAppWxBackUrlController")
@RequestMapping(value="/wxback_sapp")
public class StoreAppWxBackUrlController extends BaseController { 

	private String success= "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> "; 
	private String notsuccess="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[]]></return_msg></xml>";
	private String notsign="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名错误]]></return_msg></xml>";
	private String notorder="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[订单不存在]]></return_msg></xml>";
	private String notmoney="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[金额不一致]]></return_msg></xml>";
 	
	
	 
	/**
	 * 微信支付回调接口
	* 方法名：Notify
	* wxback_sapp/notify.do
	* 
    */
	@RequestMapping(value="/notify")
	@ResponseBody
	public void Notify(PrintWriter out,HttpServletRequest request) throws Exception{
		logBefore(logger, "微信支付回调接口");
		String inputLine;
		StringBuilder sb =new StringBuilder();
		String resXml="";//返回数据
 		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				sb.append(inputLine);
			}
			request.getReader().close();
			String xmlStr = "<xml>"
					+ "<appid><![CDATA[wxb4dc385f953b356e]]></appid>"//公众账号ID
					+ "<bank_type><![CDATA[CCB_CREDIT]]></bank_type>"//付款银行
					+ "<cash_fee><![CDATA[1]]></cash_fee>"//现金支付金额
					+ "<fee_type><![CDATA[CNY]]></fee_type>"//货币种类
					+ "<is_subscribe><![CDATA[Y]]></is_subscribe>"//是否关注公众账号
					+ "<mch_id><![CDATA[1228442802]]></mch_id>"//商户号
					+ "<nonce_str><![CDATA[1002477130]]></nonce_str>"//随机字符串
					+ "<openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid>"//用户标识
					+ "<out_trade_no><![CDATA[0016cacc7b844752a1222e6f3a57c897]]></out_trade_no>"//商户订单号最多32位
					+ "<result_code><![CDATA[SUCCESS]]></result_code>"//业务结果
					+ "<sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign>"//签名
					+ "<time_end><![CDATA[20150324100405]]></time_end>"//支付完成时间
					+ "<total_fee>0</total_fee>"//总金额
					+ "<trade_type><![CDATA[JSAPI]]></trade_type>"//交易类型
					+ "<transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id>"//微信支付订单号
					+" <attach><![CDATA[12123212112014070335681123222222]]></attach>"//商家数据包
			  + "</xml>" ;
			//验签
			WXPayPath dodo = new WXPayPath();
			boolean signflag=dodo.YanQian(xmlStr);
			if(!signflag){
				ServiceHelper.getAppPcdService().saveLog(xmlStr, "回调的订单验签失败","0099");
				resXml=notsign;
			}else{
				Map<String, String> map =  WXPayUtil.xmlToMap( xmlStr );
//				Map<String, String> map =  WXPayUtil.xmlToMap(sb.toString());
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
						 if(attach.equals("3")){
//							 resXml=Transaction_pointsPay(out_trade_no, tradnumber, total_fee, map);
						 }else if(attach.equals("2")){
//							 resXml=Service_Pay(out_trade_no, tradnumber, total_fee, map);
 						 }else if(attach.equals("3")){
							  resXml=Store_cz(out_trade_no, tradnumber, total_fee, map);
						 }else{
//							 resXml=BianjiYouXuan_pay(out_trade_no, tradnumber, total_fee, map);
						 }
			 	}else{
 					ServiceHelper.getAppPcdService().saveLog(out_trade_no, "支付失败"+map.toString(),"0099");
 					resXml=notsuccess;	 
				}		
			}
   		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
 		out.write(resXml);
		out.close();
 	}
	
	
	/**
	 * 商家充值
	 */
	public String Store_cz(String out_trade_no,String tradnumber,String total_fee,Map<String, String>  map){
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
