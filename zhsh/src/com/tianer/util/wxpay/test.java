package com.tianer.util.wxpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.tianer.controller.base.BaseController;
import com.tianer.util.DateUtil;

public class test {

    public static void main(String[] args) throws Exception {
    	test t=new test();
     	t.Apipay();
//    	t.httppay();
     }
    
    public void Apipay() throws Exception{
    	WXPayPath dodo = new WXPayPath("3");
    	String out_trade_no=BaseController.getTimeID();
    	Map<String, String> reqData=new HashMap<String, String>();
    	reqData.put("body", "腾讯充值中心-QQ会员充值");
    	reqData.put("attach", "12");
    	reqData.put("out_trade_no", out_trade_no);
    	reqData.put("fee_type", "CNY");
    	reqData.put("total_fee", "1");
    	reqData.put("time_start", DateUtil.getDayshms());
    	reqData.put("spbill_create_ip", dodo.getSpbill_create_ip());
    	reqData.put("notify_url", "https://test.letiantian.com/wxpay/notify");
     	String  nonce_str=WXPayUtil.generateNonceStr();
     	reqData.put("nonce_str", nonce_str);
//     	JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
//    	MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
    	reqData.put("trade_type", "JSAPI");
    	reqData.put("openid", "owD2DwsxdygwHXxNV75kjGT7Wvlw");
    	
    	Map<String, String> data=new HashMap<String, String>();
    	//开始支付
    	data=dodo.unifiedOrder(reqData);
    	System.out.println(data);
//    	nextpay(nonce_str,data.get("prepay_id").toString());
    }
    
    
    public void nextpay(String nonce_str,String prepayid) throws Exception{
    	WXPayPath dodo = new WXPayPath("2");
    	Map<String, String> returnmap=new HashMap<String, String>();
    	returnmap.put("appid", "wxab8acb865bb1637e" );
   	  	returnmap.put("partnerid", "11473623" );
   	  	returnmap.put("prepayid",prepayid);
    	returnmap.put("timestamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
   	  	returnmap.put("noncestr", nonce_str);//
   	  	returnmap.put("package", "Sign=WXPay");
   	  	//二次签名x
	    String sign=dodo.AddSignByHMACSHA256(returnmap);
	    returnmap=WXPayUtil.xmlToMap(sign);
	    System.out.println(returnmap.toString());
	    
   	  	
    }
    
    /**
     * HTTP自己写的请求接口
     * @throws Exception
     */
    public void httppay() throws Exception{
    	WXPayPath dodo = new WXPayPath("3");
    	String out_trade_no=BaseController.getTimeID();
    	Map<String, String> map=new HashMap<String, String>();
     	map.put("body", "腾讯充值中心-QQ会员充值");
    	map.put("attach", "12");
    	map.put("out_trade_no", out_trade_no);
    	map.put("fee_type", "CNY");
    	map.put("total_fee", "1");
    	map.put("spbill_create_ip", dodo.getSpbill_create_ip());
    	map.put("notify_url", "http://test.letiantian.com/wxpay/notify");
     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
    	map.put("trade_type", "NATIVE");
//    	map.put("openid", "owD2DwsxdygwHXxNV75kjGT7Wvlw");
    	//添加签名
    	Map<String, String> map2=dodo.payorderByHttps(map);
 	    System.out.println(map2.toString());
     }

}
