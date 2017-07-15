package com.tianer.controller.pay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.pingplusplus.model.Charge;
import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.pay_history.Pay_historyController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.controller.zhihui.payMoney.ChargeExample;
import com.tianer.entity.html.HtmlUser;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.alipaypay.AlipayConfig;
import com.tianer.util.wxpay.WXPayPath;
import com.tianer.util.wxpay.WXPayUtil;

/** 
 * 
* 类名称：Storeapp_payController   
* 类描述：   商家APP端的一些相关支付
 */
@Controller("storeapp_payController")
@RequestMapping(value="/storeapp_pay")
public class Storeapp_payController extends BaseController{
	

	/**
	 * 支付宝支付
	 * storeapp_pay/goPayChongZhi.do?total_amount=0.01&body=3&out_trade_no=12141212
	 * @param out
	 * total_amount  支付金额 
	 * body  支付类型  1-支付扣点充值，2-支付服务费，3-充值，4-支付优选编辑费用 
	 * out_trade_no 订单ID
	 */
	@RequestMapping(value="/goPayChongZhi")
 	public void goPayChongZhi(HttpServletRequest httpRequest, HttpServletResponse httpResponse,String total_amount,String body,String out_trade_no){
  		try{
 				AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
				AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
 			    alipayRequest.setNotifyUrl(AlipayConfig.notify_url_app);//在公共参数中设置回跳和通知地址
			   //商户订单号，商户网站订单系统中唯一订单号，必填
   				//订单名称，必填
 				//body商品描述  支付类型  1-支付扣点充值，2-支付服务费，3-充值，4-支付优选编辑费用 
			    String subject ="";
			    if(body.equals("1")){
			    	subject="支付扣点-充值余额";
			    }else if(body.equals("2")){
			    	subject="九鱼网服务费支付";
			    }else if(body.equals("3")){
			    	subject="九鱼网充值-支付";
			    }else{
			    	subject="优选商品-编辑支付";
			    }
				alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
						+ "\"total_amount\":\""+ total_amount +"\"," 
						+ "\"subject\":\""+ subject +"\"," 
						+ "\"body\":\""+ body +"\"," 
						+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
			    String form="";
			    try {
			        form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
			    } catch (AlipayApiException e) {
			        e.printStackTrace();
			    }
			    httpResponse.setContentType("text/html;charset=" + AlipayConfig.charset);
			    httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
			    httpResponse.getWriter().flush();
			    httpResponse.getWriter().close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
   	}
	

	/**
	 * 微信支付
	 *  
	 * total_fee  金额
	 * attach     支付类型  1-支付扣点充值，2-支付服务费，3-充值，4-支付优选编辑费用 
	 * body       商品说明
	 * out_trade_no   订单ID
	 */
	public static Map<String, String> WxPayOrder(String _total_fee,String attach,String body,String out_trade_no) throws Exception{
		Map<String, String> returnmap=new HashMap<String, String>();
   		try {
   			BigDecimal total_fee = new BigDecimal(Double.parseDouble(_total_fee)*100);
  	    	//开始调用微信支付接口
  			WXPayPath dodo = new WXPayPath("1");
 	    	Map<String, String> reqData=new HashMap<String, String>();
 	    	reqData.put("body", body);
	    	reqData.put("attach",attach);
	    	reqData.put("out_trade_no", out_trade_no);
	    	reqData.put("fee_type", "CNY");
	    	reqData.put("total_fee", String.valueOf(total_fee.intValue()));
	    	reqData.put("spbill_create_ip", dodo.getSpbill_create_ip());
	    	reqData.put("notify_url", "http://www.jiuyuvip.com/back_sapp/notify.do");
	     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	    	reqData.put("trade_type", "NATIVE");
 	    	Map<String, String> map2=dodo.unifiedOrder(reqData,"1");
 	    	//开始处理结果
  	        if(map2.get("result_code").toString().equals("SUCCESS") && map2.get("return_code").toString().equals("SUCCESS")){
  	        	returnmap.put("code_url", map2.get("code_url").toString());//支付的微信二维码
  	        }
 	        returnmap.put("result_code", map2.get("result_code").toString());
 	        returnmap.put("return_code", map2.get("return_code").toString());
 	        returnmap.put("return_msg", map2.get("return_msg").toString());
 	        returnmap.put("out_trade_no", out_trade_no);//交易订单ID
 		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
		return returnmap;
	}
	
	 
	 /**
 	 * 
 	* 方法名称:：Store_cz 
 	* 方法描述：充值
 	* storeapp_pay/store_cz.do
 	* 
 	 */
	@RequestMapping(value="/store_cz")
	@ResponseBody
	public Object Store_cz(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
   		String result="1";
		String message="充值确认中";
 		PageData pd=new PageData();
		try{
			pd = this.getPageData();
			if(slogin ==null || slogin.getStore_id() == null){
 				result="0";
 				message="id不能为空";
 			}else{
 	 			 	String store_wealthhistory_id=BaseController.getCZUID(pd.getString("store_id"));//充值单号
	 			 	if( pd.getString("store_operator_id") == null ||  pd.getString("store_operator_id").trim().equals("") ){
	 						pd.put("store_operator_id", "jy"+pd.getString("store_id"));
	 				}else{
	 						store_wealthhistory_id=BaseController.getCZUID(pd.getString("store_operator_id")); 
	 				}
	 			 	pd.put("store_id", slogin.getStore_id());
					String money=pd.getString("money");
					if( money== null || money.equals("") || Double.parseDouble(money) <= 0 ){
						result="0";
		 				message="金额不能为空且必须大于0";
					}else{
						String pay_way=pd.getString("pay_way");//支付方式
						//新增商家财富历史记录
		   				pd.put("wealth_type", "1");
		   				pd.put("profit_type", "2");//1-商家收益（提现），2-商家充值积分，3-用户支付的收益金钱/积分，4-现金支付的金额，5-第三方支付的金额，6-抢积分,7-送积分
		   				pd.put("number", money);
		   				pd.put("pay_type", pay_way);
		   				pd.put("store_wealthhistory_id", store_wealthhistory_id);
		   				pd.put("store_id", pd.getString("store_id"));
		   				pd.put("store_operator_id", pd.getString("store_operator_id"));
		   				pd.put("process_status", "0");
		   				pd.put("jiaoyi_id", store_wealthhistory_id);
		   				pd.put("arrivalMoney", money);
		   				pd.put("in_jiqi", "4");
		   				String nowMoney = ServiceHelper.getAppStoreService().sumStoreWealth(pd);
		   				double nom = Double.parseDouble(money);
		   				double nownom = Double.parseDouble(nowMoney);
		   				pd.put("last_wealth", TongYong.df2.format(nownom+nom));
		   				ServiceHelper.getAppStoreService().saveWealthhistory(pd);
		   				//获取支付二维码
		   				data=WxPayOrder(money, "3", "九鱼网充值-支付", store_wealthhistory_id);
 	 			}
  			}
  		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", data);
    	return map;
	}
	
	 
	
	
	
	
	

}
