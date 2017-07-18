package com.tianer.controller.back;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.internal.util.AlipaySignature;
import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.zhihui.StoreFeeTihuoTask;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.alipaypay.AlipayConfig;
import com.tianer.util.wxpay.WXPayPath;
import com.tianer.util.wxpay.WXPayUtil;
 



/** 
 * PC端的回调地址
 * @author Administrator
 *
 */
@Controller("pcBackUrlController")
@RequestMapping(value="/back_pc")
public class PcBackUrlController extends BaseController { 
	
 
	/**
	 * 
	* 方法名：Alipaynotify
 	* 描述：支付宝返回结果（获取流水单号）
	* 返回类型：json
	* back_pc/alipaynotify.do
	 */
	@RequestMapping(value="/alipaynotify")
	@ResponseBody
	public Object Alipaynotify() throws Exception{
  		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
			Map<String, String> paramsMap =(Map<String, String>)pd ;//将异步通知中收到的所有参数都存放到map中
			boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
			if(signVerified){
				// TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
				//订单状态成功：TRADE_SUCCESS
				String trade_status=pd.getString("trade_status");	
				if(trade_status.equals("TRADE_SUCCESS")){
					//trade_no流水单号
					String out_trade_no=pd.getString("out_trade_no");	
					//trade_no流水单号
					String trade_no=pd.getString("trade_no");	
 					//total_fee总价
					String total_fee=TongYong.df2.format(Double.parseDouble(pd.getString("total_fee").trim())*100);//化为分的单位
					//body订单id
					String body=pd.getString("body");
					String resXml="";
					if(body.equals("1")){
						 resXml=Transaction_pointsPay(out_trade_no, trade_no, total_fee, pd,"alipay");
					 }else if(body.equals("2")){
						 resXml=Service_Pay(out_trade_no, trade_no, total_fee, pd,"alipay");
					 }else if(body.equals("3")){
						  resXml=Store_cz(out_trade_no, trade_no, total_fee, pd,"alipay");
					 }else{
						 resXml=BianjiYouXuan_pay(out_trade_no, trade_no, total_fee, pd,"alipay");
					 }
					//logger记录
					ServiceHelper.getAppPcdService().saveLog(pd.toString(), "支付宝回调的订单结果","alipay");
 					return "success";
				}
			}else{
				// TODO 验签失败则记录异常日志，并在response中返回failure.
				AlipayConfig.logResult(pd.toString());
				ServiceHelper.getAppPcdService().saveLog(pd.toString(), "支付宝回调的订单验签失败","0099");
			}
 		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
			System.out.println("最后得到的XMl格式参数"+xmlStr);
			//验签
			WXPayPath dodo = new WXPayPath("2");
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
							 resXml=Transaction_pointsPay(out_trade_no, tradnumber, total_fee, map,"wx");
						 }else if(attach.equals("2")){
							 resXml=Service_Pay(out_trade_no, tradnumber, total_fee, map,"wx");
 						 }else if(attach.equals("3")){
							  resXml=Store_cz(out_trade_no, tradnumber, total_fee, map,"wx");
						 }else{
							 resXml=BianjiYouXuan_pay(out_trade_no, tradnumber, total_fee, map,"wx");
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
	
	
	
	/**
	 * 交易扣点支付
	 */
	public String Transaction_pointsPay(String out_trade_no,String tradnumber,String total_fee,Map<String, String>  map,String pay_way){
		PageData pd=new PageData();
		double actionmoney=(new BigDecimal(Double.parseDouble(total_fee)/100)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		try {
      		 PageData historypd=new PageData();
    		 historypd.put("store_wealthhistory_id", out_trade_no);
    		 historypd.put("process_status", "1");
			 historypd.put("user_id", Const.jiuyunumber);
			 ServiceHelper.getAppStoreService().editWealthHistoryStatus(historypd);
    		 //历史记录详情
    		 historypd=ServiceHelper.getAppStoreService().findWealthHistoryById(historypd);
    		 if(historypd == null){
    			 ServiceHelper.getAppPcdService().saveLog(out_trade_no, "交易扣点订单不存在"+map.toString(),"0099");
     			 return notorder;
    		 }
    		 //判断金额是否一致
    		 double number=Double.parseDouble(historypd.getString("number"));
     		 if(actionmoney != number){
     			 ServiceHelper.getAppPcdService().saveLog(out_trade_no, "交易扣点金额不匹配"+map.toString(),"0099");
  				 return notmoney;
     		 }
     		historypd.put("pay_way", pay_way);
    		//更新订单处理状态
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
   			if(historypd.getString("pay_way").contains("alipay")){
					waterpd.put("remittance_type","3" );
	 				waterpd.put("alipay_money",historypd.getString("number") );
	 				waterpd.put("remittance_name",Const.payjiqi[3] );
			}else if(historypd.getString("pay_way").contains("wx")){
					waterpd.put("remittance_type","4" );
					waterpd.put("wx_money",historypd.getString("number") );
					waterpd.put("remittance_name",Const.payjiqi[4] );
			}else if(historypd.getString("pay_way").contains("nowpay")){
					waterpd.put("remittance_type","2" );
					waterpd.put("nowypay_money",historypd.getString("number") );
					if(historypd.getString("in_jiqi").equals("1")){
						waterpd.put("remittance_name",Const.payjiqi[0] );
					}else if(historypd.getString("in_jiqi").equals("4")){
						waterpd.put("remittance_name",Const.payjiqi[6] );
					}else if(historypd.getString("in_jiqi").equals("2")){
						waterpd.put("remittance_name",Const.payjiqi[2] );
					}
			}else if(historypd.getString("pay_way").contains("pple")){
					waterpd.put("remittance_type","5" );
					waterpd.put("apple_money",historypd.getString("number") );
					waterpd.put("remittance_name",Const.payjiqi[5] );
			}else{
					waterpd.put("remittance_type","5" );
					waterpd.put("bank_money",historypd.getString("number"));
					waterpd.put("remittance_name",Const.payjiqi[1] );
			}
   			waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
    		waterpd.put("createtime",DateUtil.getTime());
   			waterpd.put("over_time",DateUtil.getTime());
   	  		waterpd.put("jiaoyi_type","0");
   			waterpd.put("payee_number",Const.jiuyunumber);
    	 	waterpd.put("order_tn", out_trade_no);
   			waterpd.put("province_name", spd.getString("province_name"));
   			waterpd.put("city_name", spd.getString("city_name"));
   			waterpd.put("area_name", spd.getString("area_name"));
    		ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
    		waterpd=null; 
			//获取充值次数
			String recharge_times = ServiceHelper.getStoreService().count(spd.getString("store_id"));
			//更新充值次数
			pd.put("recharge_times", "1");
			ServiceHelper.getAppStoreService().edit(pd);
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
			//更新交易扣点是否完成
			pd.put("biaozhun_status", "1");
			pd.put("biaozhun_content", "交易扣点处理");
			pd.put("starttime",  DateUtil.getDay());
			pd.put("endtime",  DateUtil.getAfterMonthDate( DateUtil.getDay(), "36"));
			ServiceHelper.getAppStoreService().editEarnestType(pd); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return success;
		}
 		return success;
	}
	
	
	/**
	 * 优选编辑费用
	 */
	public String BianjiYouXuan_pay(String out_trade_no,String tradnumber,String total_fee,Map<String, String>  map,String pay_way){
 		double bianji_money=(new BigDecimal(Double.parseDouble(total_fee)/100)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		try { 
	 			PageData historypd=new PageData();
			 	historypd.put("store_wealthhistory_id", out_trade_no);
	 		 	historypd=ServiceHelper.getAppStoreService().findWealthHistoryById(historypd);
	 		 	if(historypd == null){
	    			 ServiceHelper.getAppPcdService().saveLog(out_trade_no, "优选订单不存在"+map.toString(),"0099");
	     			 return notorder;
	    		 }
	    		 //判断金额是否一致
	    		 double number=Double.parseDouble(historypd.getString("number"));
	     		 if(bianji_money != number){
	     			 ServiceHelper.getAppPcdService().saveLog(out_trade_no, "优选金额不匹配"+map.toString(),"0099");
	  				 return notmoney;
	     		 }
	     		historypd.put("pay_way", pay_way);
	 		 	String profit_type=historypd.getString("profit_type");
	 		 	String jiaoyi_id=historypd.getString("jiaoyi_id");
      			PageData spd=ServiceHelper.getAppStoreService().findById(historypd);//商家详情
     			PageData sppd=ServiceHelper.getSp_fileService().findById(spd);//服务商详情
		 		if(sppd != null){
		 			String nowmoney=TongYong.df2.format(Double.parseDouble(sppd.getString("nowmoney"))+bianji_money);
		 			sppd.put("nowmoney", nowmoney);
			 		ServiceHelper.getSp_fileService().edit(sppd);
			 		//添加一个支付记录
	 				PageData mmpd=new PageData();
	 				mmpd.put("yewu_id", spd.getString("store_id"));//业务对象
	 	 			mmpd.put("yewu_type", "1");//业务对象
	 				mmpd.put("money", TongYong.df2.format(bianji_money));//金额
	 				mmpd.put("money_type", "6");//1、销售提成: 2、积分收益： 3、平台奖励，4-保证金，5-提现 ,6-优选编辑费用
	 	 			mmpd.put("operate_type", "1"); //1-服务商，2-业务员
	 				mmpd.put("operate_id", sppd.getString("sp_file_id")); 
	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
	 				mmpd.put("isjihuo", "1");//0-未激活，1-已激活
	 				mmpd.put("now_balance", nowmoney);//余额
	 				mmpd.put("service_performance_id", out_trade_no);//收益对象
	 				ServiceHelper.getService_performanceService().save(mmpd);
		 		}
 				PageData waterpd=new PageData();
				waterpd.put("pay_status","1");
	   			waterpd.put("waterrecord_id",out_trade_no);
				waterpd.put("user_id", spd.getString("store_id"));
				waterpd.put("user_type", "1");
				waterpd.put("withdraw_rate","0");
				if(profit_type.equals("13")){
					waterpd.put("money_type","7");
				}else if(profit_type.equals("14")){
					waterpd.put("money_type","8");
				}
	 			waterpd.put("money", TongYong.df2.format(bianji_money));
	 			waterpd.put("reduce_money","0");
				waterpd.put("arrivalmoney",  TongYong.df2.format(bianji_money));
				waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(spd));
				waterpd.put("application_channel", historypd.getString("in_jiqi"));
				if(historypd.getString("pay_way").contains("alipay")){
					waterpd.put("remittance_type","3" );
	 				waterpd.put("alipay_money",historypd.getString("number") );
	 				waterpd.put("remittance_name",Const.payjiqi[3] );
				}else if(historypd.getString("pay_way").contains("wx")){
						waterpd.put("remittance_type","4" );
						waterpd.put("wx_money",historypd.getString("number") );
						waterpd.put("remittance_name",Const.payjiqi[4] );
				}else if(historypd.getString("pay_way").contains("nowpay")){
						waterpd.put("remittance_type","2" );
						waterpd.put("nowypay_money",historypd.getString("number") );
						if(historypd.getString("in_jiqi").equals("1")){
							waterpd.put("remittance_name",Const.payjiqi[0] );
						}else if(historypd.getString("in_jiqi").equals("4")){
							waterpd.put("remittance_name",Const.payjiqi[6] );
						}else if(historypd.getString("in_jiqi").equals("2")){
							waterpd.put("remittance_name",Const.payjiqi[2] );
						}
				}else if(historypd.getString("pay_way").contains("pple")){
						waterpd.put("remittance_type","5" );
						waterpd.put("apple_money",historypd.getString("number") );
						waterpd.put("remittance_name",Const.payjiqi[5] );
				}else{
						waterpd.put("remittance_type","5" );
						waterpd.put("bank_money",historypd.getString("number"));
						waterpd.put("remittance_name",Const.payjiqi[1] );
				}
 				waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
				waterpd.put("createtime",DateUtil.getTime());
				waterpd.put("over_time",DateUtil.getTime());
  				waterpd.put("jiaoyi_type","0");
 				waterpd.put("payee_number", Const.jiuyunumber);
	 			waterpd.put("order_tn", tradnumber);
				waterpd.put("province_name", spd.getString("province_name"));
				waterpd.put("city_name", spd.getString("city_name"));
				waterpd.put("area_name", spd.getString("area_name"));
				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
				waterpd=null; 
			   //更新处理状态
 			   historypd.put("process_status", "1");
			   historypd.put("sp_getmoney", TongYong.df2.format(bianji_money));
			   historypd.put("user_id", Const.jiuyunumber);
			   ServiceHelper.getAppStoreService().editWealthHistoryStatus(historypd);
			    //更改优选商品的状态
			    PageData yxgoodspd=new PageData(); 
			    yxgoodspd.put("youxuangoods_id", jiaoyi_id);
			    yxgoodspd=ServiceHelper.getYouXuanService().findByIdGoods(yxgoodspd);
			    System.out.println("yxgoodspd="+yxgoodspd);
				String goods_status=yxgoodspd.getString("goods_status");
				if(goods_status.equals("1")){
						yxgoodspd.put("goods_status", "4");
				}else if(goods_status.equals("2")){
						yxgoodspd.put("goods_status", "4");
				}
				ServiceHelper.getYouXuanService().editGoods(yxgoodspd);
 		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return success;
		}
		return success;
	}
	
	
	/**
	 * 
	 * 购买服务费
	 */
	public String Service_Pay(String out_trade_no,String tradnumber,String total_fee,Map<String, String>  map,String pay_way){
		double fw_money=(new BigDecimal(Double.parseDouble(total_fee)/100)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		try { 
 		 		PageData historypd=new PageData();
		 		historypd.put("store_wealthhistory_id", out_trade_no);
		 		historypd=ServiceHelper.getAppStoreService().findWealthHistoryById(historypd);
		 		 if(historypd == null){
	    			 ServiceHelper.getAppPcdService().saveLog(out_trade_no, "服务费订单不存在"+map.toString(),"0099");
	     			 return notorder;
	    		 }
	    		 //判断金额是否一致
	    		 double number=Double.parseDouble(historypd.getString("number"));
	     		 if(fw_money != number){
	     			 ServiceHelper.getAppPcdService().saveLog(out_trade_no, "服务费金额不匹配"+map.toString(),"0099");
	  				 return notmoney;
	     		 }
	     		historypd.put("pay_way", pay_way);
 		 		PageData spd=ServiceHelper.getAppStoreService().findById(historypd);
 				String city_file_fee_id=spd.getString("city_file_fee_id");
				String biaozhun_content=spd.getString("biaozhun_content");
				String endtime=spd.get("endtime").toString();
				double fw_sp_getmoney=0;//服务商会获取的金钱
				double send_jf=0;//购买服务费赠送的积分
			   //查看续费或是服务费的详情
				PageData feepd=new PageData();
				feepd.put("city_file_fee_id", spd.getString("next_city_file_fee_id"));
				feepd=ServiceHelper.getCity_fileService().getCityFeeDetail(feepd);
				if(feepd != null){//存在当前服务费
						String send_integer=feepd.getString("send_integer");
						String purchase_type=feepd.getString("purchase_type");
						String buying_time=feepd.getString("buying_time");
						if(send_integer == null || send_integer.equals("")){
							send_integer="0";
						}
						if(buying_time == null || buying_time.equals("")){
							buying_time="0";
						}
						String present_time=feepd.getString("present_time");
						if(present_time == null || present_time.equals("")){
							present_time="0";
						}
						String purchase_price=feepd.getString("purchase_price");
					    String str_fee="";
					    //==
					    send_jf=Double.parseDouble(send_integer);
					    //==
						if(purchase_type.equals("1")){
							str_fee="首次购买";
						}else{
							str_fee="续费";
						}
						str_fee=str_fee+buying_time+"个月，赠送"+present_time+"个月,价格"+purchase_price+"元，内含赠送积分"+send_integer;
						int allmonth=Integer.parseInt(present_time)+Integer.parseInt(buying_time);
						//==
						endtime=DateUtil.getAfterMonthDate(endtime,allmonth+"");
						//==
						spd.put("endtime", endtime);
						spd.put("biaozhun_content",biaozhun_content+","+str_fee);
						spd.put("city_file_fee_id", city_file_fee_id+","+feepd.getString("city_file_fee_id"));
				}else{
					 ServiceHelper.getAppPcdService().saveLog(out_trade_no, "当前服务费方式不存在"+map.toString(),"0099");
	  				 return success;
				}
				//城市营销的：提成与补贴
				PageData cpd=ServiceHelper.getStore_fileService().getCityForName(spd);
				if(cpd != null){
					String service_provider_commission=cpd.getString("service_provider_commission");
		 			if(service_provider_commission == null || service_provider_commission.equals("")){
		 				service_provider_commission="0";
					}
		 		    fw_sp_getmoney=(fw_money-send_jf)*(Double.parseDouble(service_provider_commission)/100);//服务费
				 	PageData sppd=ServiceHelper.getSp_fileService().findById(spd);
			 		if(sppd != null && fw_sp_getmoney >0 ){
			 			String now_balance=TongYong.df2.format(Double.parseDouble(sppd.getString("nowmoney"))+fw_sp_getmoney);
			 			sppd.put("nowmoney", now_balance);
			 			ServiceHelper.getSp_fileService().edit(sppd);
 	 	 				PageData mmpd=new PageData();
	 	 				mmpd.put("yewu_id", spd.getString("store_id"));//业务对象
		 	 			mmpd.put("yewu_type", "1");//业务对象
	 	 				mmpd.put("money", TongYong.df2.format(fw_sp_getmoney));//金额
	 	 				mmpd.put("money_type", "1");//1、销售提成: 2、积分收益： 3、平台奖励 
		 	 			mmpd.put("operate_type", "1"); //1-服务商，2-业务员
	 	 				mmpd.put("operate_id", sppd.getString("sp_file_id")); 
	 	 				mmpd.put("isshouyi", "0");//0-收益，1-消费
	 	 				mmpd.put("isjihuo", "1");//0-未激活，1-已激活
	 	 				mmpd.put("now_balance", now_balance);//余额
	 	 				mmpd.put("service_performance_id", out_trade_no);//收益对象
	 	 				ServiceHelper.getService_performanceService().save(mmpd);
			 		}
				}
 				PageData waterpd=new PageData();
				waterpd.put("pay_status","1");
				waterpd.put("waterrecord_id",out_trade_no);
				waterpd.put("user_id", spd.getString("store_id"));
				waterpd.put("user_type", "1");
				waterpd.put("withdraw_rate","0");
				waterpd.put("money_type","3");
				waterpd.put("money",  TongYong.df2.format(-fw_money));
				waterpd.put("reduce_money","0");
				waterpd.put("arrivalmoney",   TongYong.df2.format(-fw_money));
				waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(spd));
				waterpd.put("application_channel", historypd.getString("in_jiqi") );
				if(historypd.getString("pay_way").contains("alipay")){
						waterpd.put("remittance_type","3" );
		 				waterpd.put("alipay_money",historypd.getString("number") );
		 				waterpd.put("remittance_name",Const.payjiqi[3] );
				}else if(historypd.getString("pay_way").contains("wx")){
						waterpd.put("remittance_type","4" );
						waterpd.put("wx_money",historypd.getString("number") );
						waterpd.put("remittance_name",Const.payjiqi[4] );
				}else if(historypd.getString("pay_way").contains("nowpay")){
						waterpd.put("remittance_type","2" );
						waterpd.put("nowypay_money",historypd.getString("number") );
						if(historypd.getString("in_jiqi").equals("1")){
							waterpd.put("remittance_name",Const.payjiqi[0] );
						}else if(historypd.getString("in_jiqi").equals("4")){
							waterpd.put("remittance_name",Const.payjiqi[6] );
						}else if(historypd.getString("in_jiqi").equals("2")){
							waterpd.put("remittance_name",Const.payjiqi[2] );
						}
				}else if(historypd.getString("pay_way").contains("pple")){
						waterpd.put("remittance_type","5" );
						waterpd.put("apple_money",historypd.getString("number") );
						waterpd.put("remittance_name",Const.payjiqi[5] );
				}else{
						waterpd.put("remittance_type","5" );
						waterpd.put("bank_money",historypd.getString("number"));
						waterpd.put("remittance_name",Const.payjiqi[1] );
				}
 				waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
 				waterpd.put("createtime",DateUtil.getTime());
				waterpd.put("over_time",DateUtil.getTime());
 				waterpd.put("jiaoyi_type","0");
				waterpd.put("payee_number",Const.jiuyunumber);
				waterpd.put("order_tn",tradnumber);
				waterpd.put("province_name", spd.getString("province_name"));
				waterpd.put("city_name", spd.getString("city_name"));
				waterpd.put("area_name", spd.getString("area_name"));
				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
				waterpd=null;
	     		
       			/*
       			 * profit_type：1-商家 （提现），2-商家 （充值积分），3-用户支付的收益金钱/积分，4-现金支付的金额，
		   	     *5-第三方支付的金额，6-抢积分红包,7-消费返积分，8-发送积分红包，9-首次购买服务，10-服务续费 11，消费返推送积分，12-人脉推广收益
       			 */
  			    PageData wpd=new PageData();
		   	    wpd.put("wealth_type", "1");
 			    wpd.put("profit_type", "2");
			    wpd.put("pay_type", historypd.getString("pay_type"));
			    wpd.put("number",TongYong.df2.format( fw_money ));
			    wpd.put("store_id", spd.getString("store_id"));
			    wpd.put("in_jiqi", historypd.getString("in_jiqi"));
	 			wpd.put("store_operator_id","jy"+spd.getString("store_id"));
			    wpd.put("process_status", "1");
	 			wpd.put("user_id",Const.jiuyunumber);
	 			wpd.put("jiaoyi_id", "FWCZ"+ out_trade_no);
			    wpd.put("last_wealth", TongYong.df2.format(Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(spd))));
			    wpd.put("store_wealthhistory_id","FWCZ"+ out_trade_no);
			    ServiceHelper.getAppStoreService().saveWealthhistory(wpd);
			    wpd=null;
	   			waterpd=new PageData();
	   			waterpd.put("pay_status","1");
	   			waterpd.put("waterrecord_id","FWCZ"+ out_trade_no);
				waterpd.put("user_id", spd.getString("store_id"));
				waterpd.put("user_type", "1");
				waterpd.put("withdraw_rate","0");
				waterpd.put("money_type","1");
				waterpd.put("money",  TongYong.df2.format(fw_money));
				waterpd.put("reduce_money","0");
				waterpd.put("arrivalmoney",   TongYong.df2.format(fw_money));
				waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(spd));
				waterpd.put("application_channel", historypd.getString("in_jiqi") );
				if(historypd.getString("pay_way").contains("alipay")){
						waterpd.put("remittance_type","3" );
		 				waterpd.put("alipay_money",historypd.getString("number") );
		 				waterpd.put("remittance_name",Const.payjiqi[3] );
				}else if(historypd.getString("pay_way").contains("wx")){
						waterpd.put("remittance_type","4" );
						waterpd.put("wx_money",historypd.getString("number") );
						waterpd.put("remittance_name",Const.payjiqi[4] );
				}else if(historypd.getString("pay_way").contains("nowpay")){
						waterpd.put("remittance_type","2" );
						waterpd.put("nowypay_money",historypd.getString("number") );
						if(historypd.getString("in_jiqi").equals("1")){
							waterpd.put("remittance_name",Const.payjiqi[0] );
						}else if(historypd.getString("in_jiqi").equals("4")){
							waterpd.put("remittance_name",Const.payjiqi[6] );
						}else if(historypd.getString("in_jiqi").equals("2")){
							waterpd.put("remittance_name",Const.payjiqi[2] );
						}
				}else if(historypd.getString("pay_way").contains("pple")){
						waterpd.put("remittance_type","5" );
						waterpd.put("apple_money",historypd.getString("number") );
						waterpd.put("remittance_name",Const.payjiqi[5] );
				}else{
						waterpd.put("remittance_type","5" );
						waterpd.put("bank_money",historypd.getString("number"));
						waterpd.put("remittance_name",Const.payjiqi[1] );
				}
 				waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
 				waterpd.put("createtime",DateUtil.getTime());
				waterpd.put("over_time",DateUtil.getTime());
 				waterpd.put("jiaoyi_type","0");
				waterpd.put("payee_number",Const.jiuyunumber);
				waterpd.put("order_tn",tradnumber);
				waterpd.put("province_name", spd.getString("province_name"));
				waterpd.put("city_name", spd.getString("city_name"));
				waterpd.put("area_name", spd.getString("area_name"));
				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
				waterpd=null;
				//购买年费是否赠送积分
				if(send_jf > 0){
					//如果是首次购买者有赠送积分记录
			   		spd.put("money", "-"+send_jf);
			   		ServiceHelper.getAppStoreService().editJFWealthById(spd);//更新商家财富信息
					PageData zspd=new PageData();
					zspd.put("wealth_type", "1");//1-积分，2-余额
					zspd.put("jiaoyi_id", "ZSJF"+out_trade_no);
					zspd.put("profit_type", "2");
					zspd.put("pay_type", Const.payjiqi[7]);
					zspd.put("number",send_jf);
					zspd.put("store_id", spd.getString("store_id"));
					zspd.put("in_jiqi", historypd.getString("in_jiqi"));
					zspd.put("store_operator_id","jy"+spd.getString("store_id"));
					zspd.put("process_status", "1");
					zspd.put("user_id",Const.jiuyunumber);
					zspd.put("last_wealth", TongYong.df2.format(Double.parseDouble(ServiceHelper.getAppStoreService().sumStoreWealth(spd))));
					zspd.put("store_wealthhistory_id", "ZSJF"+out_trade_no);
					ServiceHelper.getAppStoreService().saveWealthhistory(zspd);
			   		zspd=null;
			   		waterpd=new PageData();
			   		waterpd.put("pay_status","1");
			   		waterpd.put("waterrecord_id", "ZSJF"+out_trade_no);
					waterpd.put("user_id", spd.getString("store_id"));
					waterpd.put("user_type", "1");
					waterpd.put("withdraw_rate","0");
					waterpd.put("money_type","1");
	 				waterpd.put("money",  send_jf);
	 				waterpd.put("reduce_money","0");
					waterpd.put("arrivalmoney",   send_jf);
					waterpd.put("nowuser_money",ServiceHelper.getAppStoreService().sumStoreWealth(spd));
					waterpd.put("application_channel", historypd.getString("in_jiqi") );
	   				waterpd.put("remittance_name", Const.payjiqi[7]);
					waterpd.put("remittance_type","6" );
					waterpd.put("jiuyu_money",  send_jf );
	   				waterpd.put("remittance_number",spd.getString("registertel_phone"));//支付人的支付账号
	   				waterpd.put("createtime",DateUtil.getTime());
					waterpd.put("over_time",DateUtil.getTime());
	  				waterpd.put("jiaoyi_type","0");
					waterpd.put("payee_number",Const.jiuyunumber);
	    	 		waterpd.put("order_tn",tradnumber);
					waterpd.put("province_name", spd.getString("province_name"));
					waterpd.put("city_name", spd.getString("city_name"));
					waterpd.put("area_name", spd.getString("area_name"));
					ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
					waterpd=null;
				}
 				historypd.put("process_status", "1");
				historypd.put("sp_getmoney", TongYong.df2.format(fw_sp_getmoney));
				historypd.put("send_jf", TongYong.df2.format(send_jf));
				historypd.put("user_id", Const.jiuyunumber);
 				ServiceHelper.getAppStoreService().editWealthHistoryStatus(historypd);
 		 		spd.put("biaozhun_status", "1");
				spd.put("earnest_status", "1");
				spd.put("service_status", "1");
				spd.put("em_statestatus", "1");
				spd.put("sf_statestatus", "1");
		 		ServiceHelper.getAppStoreService().editEarnestType(spd); 
		 		//设置服务费到期的定时器
		 		long l1=DateUtil.fomatDate(DateUtil.getDay()).getTime();
				long l2=DateUtil.fomatDate(endtime).getTime();
				StoreFeeTihuoTask feeth=new StoreFeeTihuoTask(spd.getString("store_id"));
				Timer tt=new Timer();
				tt.schedule(feeth, l2-l1);
				//-----------------------------------------------------------------------
	 		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return success;
		}
		return success;
	}
	
}
