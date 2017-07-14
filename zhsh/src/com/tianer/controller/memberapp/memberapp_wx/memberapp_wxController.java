package com.tianer.controller.memberapp.memberapp_wx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.controller.youxuan.YouXuanController;
import com.tianer.entity.html.HtmlUser;
import com.tianer.entity.zhihui.OrderShop;
import com.tianer.entity.zhihui.TihuoTask;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.wxpay.WXPayPath;
import com.tianer.util.wxpay.WXPayUtil;

 
/** 
 * 
* 类名称：Memberapp_wxController   
* 类描述：   微信支付相关控制层
 */
//@Controller("Memberapp_wxController")
//@RequestMapping(value="/app_wx")
public class memberapp_wxController extends BaseController{

	
	/**
	 * APP微信支付
 	 * 
	 * total_fee      金额
	 * attach     	   支付类型  1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值
	 * body       	   商品说明
	 * out_trade_no   订单ID
	 */
	public static Map<String, String> WxPayOrder(String _total_fee,String attach,String body,String out_trade_no) throws Exception{
		Map<String, String> returnmap=new HashMap<String, String>();
   		try {
   			BigDecimal total_fee = new BigDecimal(Double.parseDouble(_total_fee)*100);
  	    	//开始调用微信支付接口
  			WXPayPath dodo = new WXPayPath();
 	    	Map<String, String> reqData=new HashMap<String, String>();
 	    	reqData.put("body", body);
	    	reqData.put("attach",attach);
	    	reqData.put("out_trade_no", out_trade_no);
	    	reqData.put("fee_type", "CNY");
	    	reqData.put("total_fee", String.valueOf(total_fee.intValue()));
	    	reqData.put("spbill_create_ip", dodo.getSpbill_create_ip());
	    	reqData.put("notify_url", "http://www.jiuyuvip.com/wxback_mapp/notify.do");
	     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	    	reqData.put("trade_type", "APP");
	    	//支付处理
 	    	Map<String, String> map2=dodo.unifiedOrder(reqData);
 	    	//开始处理结果
  	        if(map2.get("result_code").toString().equals("SUCCESS") && map2.get("return_code").toString().equals("SUCCESS")){
 	    	  returnmap.put("payment_type_", attach);
 	    	  returnmap.put("appId_",  dodo.getAppID());
 	    	  returnmap.put("timestamp_", String.valueOf(((new Date()).getTime())));
 	    	  returnmap.put("nonceStr_", map2.get("nonce_str").toString());
 	    	  returnmap.put("package_","prepay_id="+ map2.get("prepay_id").toString());
 	    	  returnmap.put("signType_", "MD5");
  	    	  returnmap.put("paySign_", map2.get("sign").toString());
  	    	  returnmap.put("out_trade_no", out_trade_no);
  	       }else{
  	    	  returnmap.put("payment_type_", attach);
	    	  returnmap.put("appId_", "");
	    	  returnmap.put("timestamp_", String.valueOf(((new Date()).getTime())));
	    	  returnmap.put("nonceStr_", map2.get("nonce_str").toString());
	    	  returnmap.put("package_","");
	    	  returnmap.put("signType_", "MD5");
 	    	  returnmap.put("paySign_", "");
 	    	  returnmap.put("out_trade_no", out_trade_no);
  	       }
 	       returnmap.put("result_code", map2.get("result_code").toString());
 	       returnmap.put("return_code", map2.get("return_code").toString());
 	       returnmap.put("return_msg", map2.get("return_msg").toString());
 		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
		return returnmap;
	}
	
	
	
	
	
	
	

	
	
	
	
}
