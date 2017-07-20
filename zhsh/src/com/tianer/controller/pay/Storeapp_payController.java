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
import com.tianer.util.StringUtil;
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
	 * 微信支付
	 *  
	 * total_fee  金额
	 * attach     支付类型  1-支付扣点充值，2-支付服务费，3-充值，4-支付优选编辑费用 
	 * body       商品说明
	 * out_trade_no   订单ID
	 */
	public static Map<String, String> WxPayOrder(String _total_fee,String attach,String out_trade_no,HttpServletRequest request) throws Exception{
		Map<String, String> returnmap=new HashMap<String, String>();
   		try {
   			BigDecimal total_fee = new BigDecimal(Double.parseDouble(_total_fee)*100);
  	    	//开始调用微信支付接口
  			WXPayPath dodo = new WXPayPath("1");
 	    	Map<String, String> reqData=new HashMap<String, String>();
 	    	reqData.put("attach",attach);
 	    	//支付类型  1-支付扣点充值，2-支付服务费，3-充值，4-支付优选编辑费用 
 	    	if(attach.equals("1")){
  				reqData.put("body", "支付扣点充值");
			}else if(attach.equals("2")){
  				reqData.put("body", "支付服务费");//相当于支付宝的subject
			}else if(attach.equals("3")){
				reqData.put("body", "充值");
			}else{
				reqData.put("body", "支付优选编辑费用");
			}
 	    	reqData.put("out_trade_no", out_trade_no);
	    	reqData.put("fee_type", "CNY");
	    	reqData.put("total_fee", String.valueOf(total_fee.intValue()));
	    	reqData.put("spbill_create_ip",  StringUtil.getIp(request));
	    	reqData.put("notify_url", "http://www.jiuyuvip.com/back_sapp/notify.do");
	     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	    	reqData.put("trade_type", "NATIVE");
 	    	Map<String, String> map2=dodo.unifiedOrder(reqData);
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
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, String> wxpaydata = new HashMap<String, String>();
		String paystr="";
   		String result="1";
		String message="充值确认中";
 		PageData pd=new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("store_id") ==null || pd.getString("store_id").equals("")){
				map.put("result", "0");
				map.put("message", "ID参数不能为空");
				map.put("data", "");
		    	return map;
 			}else{
 	 			 	String store_wealthhistory_id=BaseController.getCZUID(pd.getString("store_id"));//充值单号
	 			 	if( pd.getString("store_operator_id") == null ||  pd.getString("store_operator_id").trim().equals("") ){
	 						pd.put("store_operator_id", "jy"+pd.getString("store_id"));
	 				}else{
	 						store_wealthhistory_id=BaseController.getCZUID(pd.getString("store_operator_id")); 
	 				}
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
		   				//支付类型  1-支付扣点充值，2-支付服务费，3-充值，4-支付优选编辑费用 
		   				if(pay_way.contains("wx")){
		   					wxpaydata= WxPayOrder(money, "3", store_wealthhistory_id,request);
		   				}else{
		   					paystr=AlipayConfig.LastpayStrByStore(money,  "3", store_wealthhistory_id);
		   				}
			   	 		map1.put("wxpaydata", wxpaydata);
						map1.put("alipaystr", paystr);
						map1.put("order_id", store_wealthhistory_id);
 	 			}
  			}
  		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
    	return map;
	}
	
	 
	
	
	
	
	

}
