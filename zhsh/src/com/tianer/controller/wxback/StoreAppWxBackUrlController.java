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
	 *微信充值金额回调接口
	* 方法名：gZHPayForCZ
	* wxback/gZHPayForCZ.do
	* 
  	 */
	@RequestMapping(value="/gZHPayForCZ")
	@ResponseBody
	public void gZHPayForCZ(PrintWriter out,HttpServletRequest request) throws Exception{
		logBefore(logger, "微信充值金额回调接口");
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
				ServiceHelper.getAppPcdService().saveLog(xmlStr, "充值回调的订单验签失败","0099");
				resXml= notsign;
			}else{
				Map<String, String> map =  WXPayUtil.xmlToMap( xmlStr );
//				Map<String, String> map =  WXPayUtil.xmlToMap(sb.toString());
	 	 		//1.订单id
				String out_trade_no=(String)map.get("out_trade_no");
	 			//2.流水单号
				String tradnumber = (String) map.get("transaction_id");
	 			//3.总金额
				String total_fee=(String) map.get("total_fee");
				//4.支付类型  11-商家充值，12-支付优选编辑费用,13-支付服务费
				String attach=(String) map.get("attach");
	 			//是否成功
				Object result_code=map.get("result_code");
				if("SUCCESS".equals(result_code)){

				}else{
 					ServiceHelper.getAppPcdService().saveLog(out_trade_no, "充值失败"+map.toString(),"0099");
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
	 * 编写生成一个map
	 */
	
	
	
}
