package com.tianer.controller.zhihui.payMoney;

import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import com.tianer.controller.base.BaseController;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.util.PageData;
 
/**
 * 
* 类名称：PayAct   
* 类描述：   支付充值
* 创建人：魏汉文  
* 创建时间：2016年7月4日 下午2:25:07
 */
@Controller
@RequestMapping(value="/app_pay")
public class PayAct extends BaseController {
	
	
	@Resource(name="appOrderService")
	private AppOrderService orderService;
	
   	/**
   	 * ping++订单交易支付接口
   	* 方法名称:：payOrder 
   	* 方法描述：订单支付接口
   	* 创建人：魏汉文
   	* 创建时间：2016年7月4日 下午2:11:35
   	 */
	@RequestMapping(value="/payOrder")
	@ResponseBody
	public Object payOrder(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
 		String result="1";
		String message="支付确认中";
		// type代表支付方式
		PageData pd=new PageData();
		try{
			pd=this.getPageData();
			//1.生成一个订单信息
			String order_id=BaseController.getTimeID();
			String look_number=BaseController.getTimeID();
			
		
			
			
			
 			String pay_money=pd.getString("money");
			String ip=getIp(request);//当前用户所在IP地址
			String pay_way=pd.getString("pay_way");
 			/*
			 * 支付方式pay_type:
			 * alipay:支付宝手机支付
				alipay_wap:支付宝手机网页支付
				alipay_qr:支付宝扫码支付
				alipay_pc_direct:支付宝 PC 网页支付
				bfb:百度钱包移动快捷支付
				bfb_wap:百度钱包手机网页支付
				upacp:银联全渠道支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
				upacp_wap:银联全渠道手机网页支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
				upacp_pc:银联 PC 网页支付
				cp_b2b:银联企业网银支付
				wx:微信支付
				wx_pub:微信公众账号支付
				wx_pub_qr:微信公众账号扫码支付
				yeepay_wap:易宝手机网页支付
				jdpay_wap:京东手机网页支付
				cnp_u:应用内快捷支付（银联）
				cnp_f:应用内快捷支付（外卡）
				applepay_upacp:Apple Pay
				fqlpay_wap:分期乐支付
				qgbc_wap:量化派支付
				cmb_wallet:招行一网通
			 */
			//2.获取charge对象
//			Charge charge = ChargeExample.getPay(order_id, Double.parseDouble(pay_money)*100,ip,pay_way,"body里面的信息",2);
//			map.put("data", charge);
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
	
	/**
   	 * ping++订单交易支付接口
   	* 方法名称:：payCz 
   	* 方法描述：充值接口
   	* 创建人：魏汉文
   	* 创建时间：2016年7月4日 下午2:11:35
   	 */
	@RequestMapping(value="/payCz")
	@ResponseBody
	public Object payCz(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
 		String result="1";
		String message="充值确认中";
		// type代表支付方式
		PageData pd=new PageData();
		try{
			pd=this.getPageData();
			String order_id=pd.getString("order_id");//订单编号：32为随机字符串
 			String pay_money=pd.getString("money");
			String ip=getIp(request);//当前用户所在IP地址
			String pay_way=pd.getString("pay_way");
			String payer_type=pd.getString("payer_type");//用来区分是商家还是会员1-商家，2-会员
			/*
			 * 支付方式pay_type:
			 * alipay:支付宝手机支付
				alipay_wap:支付宝手机网页支付
				alipay_qr:支付宝扫码支付
				alipay_pc_direct:支付宝 PC 网页支付
				bfb:百度钱包移动快捷支付
				bfb_wap:百度钱包手机网页支付
				upacp:银联全渠道支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
				upacp_wap:银联全渠道手机网页支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
				upacp_pc:银联 PC 网页支付
				cp_b2b:银联企业网银支付
				wx:微信支付
				wx_pub:微信公众账号支付
				wx_pub_qr:微信公众账号扫码支付
				yeepay_wap:易宝手机网页支付
				jdpay_wap:京东手机网页支付
				cnp_u:应用内快捷支付（银联）
				cnp_f:应用内快捷支付（外卡）
				applepay_upacp:Apple Pay
				fqlpay_wap:分期乐支付
				qgbc_wap:量化派支付
				cmb_wallet:招行一网通
			 */
//			Charge ce = ChargeExample.getPay(order_id, Double.parseDouble(pay_money)*100,ip,pay_way,"body里面的信息",Integer.parseInt(payer_type));
//			map.put("data", ce);
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
	
	

	
	// 第三方支付或者充值回调
	@RequestMapping(value = "payBackWay")
	@ResponseBody
	public void pingSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
 			Enumeration headerNames = request.getHeaderNames();
	        while (headerNames.hasMoreElements()) {
	            String key = (String) headerNames.nextElement();
	            String value = request.getHeader(key);
	            System.out.println(key+" "+value);
	        }
	        // 获得 http body 内容
	        BufferedReader reader = request.getReader();
	        StringBuffer buffer = new StringBuffer();
	        String string;
 	        while ((string = reader.readLine()) != null) {
	            buffer.append(string);
	        }
	        reader.close();
  	        // 解析异步通知数据
	        Event event = Webhooks.eventParse(buffer.toString());
 	        if ("charge.succeeded".equals(event.getType())) { //支付成功
		 	        	System.out.println(event.getData().getObject());
			        	JSONObject s=new JSONObject(buffer.toString());
			            JSONObject obj=s.optJSONObject("data");
			            JSONObject jv=obj.optJSONObject("object");
			            String body=jv.optString("body");//subject
 			            int sub=jv.optInt("subject");
			            String orderno=jv.optString("order_no");//订单号
			            String trade=jv.optString("transaction_no");//流水单号
 			            Double price=jv.optDouble("amount");
 			            if(sub == 1){//商家
 			            	
 			            }else if(sub ==2){//会员
 			            	
 			            }
 			           response.setStatus(200);
	        } else if ("refund.succeeded".equals(event.getType())) {
	            response.setStatus(200);
	        } else {
	            response.setStatus(500);
	        }
  	}

	
	/*
	 * 获取IP
	 */
	public static String getIp(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
 		 //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		 if(ipAddress!=null && ipAddress.length()>15){
		 //"***.***.***.***".length() = 15
		 if(ipAddress.indexOf(",")>0){
		 ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
		 }
		 }
		return ipAddress;
	}
}
