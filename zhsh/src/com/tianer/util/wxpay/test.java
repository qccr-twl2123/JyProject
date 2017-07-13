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

public class test {

    public static void main(String[] args) throws Exception {
    	test t=new test();
    	t.Apipay();
//    	t.httppay();
     }
    
    public void Apipay() throws Exception{
    	WXPayPath dodo = new WXPayPath();
    	String out_trade_no=BaseController.getTimeID();
    	Map<String, String> reqData=new HashMap<String, String>();
    	reqData.put("body", "腾讯充值中心-QQ会员充值");
    	reqData.put("attach", "12");
    	reqData.put("out_trade_no", out_trade_no);
    	reqData.put("fee_type", "CNY");
    	reqData.put("total_fee", "1");
    	reqData.put("spbill_create_ip", "123.12.12.123");
    	reqData.put("notify_url", "http://test.letiantian.com/wxpay/notify");
     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
    	reqData.put("trade_type", "NATIVE");
    	Map<String, String> data=new HashMap<String, String>();
    	//开始支付
    	data=dodo.unifiedOrder(reqData);
    	System.out.println(data);
    }
    
    
    /**
     * HTTP自己写的请求接口
     * @throws Exception
     */
    public void httppay() throws Exception{
          // HostnameVerifier hnv = new HostnameVerifier() {
        //     public boolean verify(String hostname, SSLSession session) {
        //         // Always return true，接受任意域名服务器
        //         return true;
        //     }
        // };
        // HttpsURLConnection.setDefaultHostnameVerifier(hnv);
    	WXPayPath dodo = new WXPayPath();
    	String out_trade_no=BaseController.getTimeID();
    	Map<String, String> map=new HashMap<String, String>();
     	map.put("body", "腾讯充值中心-QQ会员充值");
    	map.put("attach", "12");
    	map.put("out_trade_no", out_trade_no);
    	map.put("fee_type", "CNY");
    	map.put("total_fee", "1");
    	map.put("spbill_create_ip", "123.12.12.123");
    	map.put("notify_url", "http://test.letiantian.com/wxpay/notify");
     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
    	map.put("trade_type", "NATIVE");
//    	map.put("openid", "owD2DwsxdygwHXxNV75kjGT7Wvlw");
    	//添加签名
    	map=dodo.fillRequestData(map);
     	System.out.println(map);
    	String newreqBody=WXPayUtil.mapToXml(map);
    	
        String UTF8 = "UTF-8";
        URL httpUrl = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
        HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
        httpURLConnection.setRequestProperty("Host", "api.mch.weixin.qq.com");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(10*1000);
        httpURLConnection.setReadTimeout(10*1000);
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(newreqBody.getBytes(UTF8));

        //获取内容
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        final StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        String resp = stringBuffer.toString();
        if (stringBuffer!=null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream!=null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream!=null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, String> map2=WXPayUtil.xmlToMap(resp);
	    System.out.println(map2.toString());
     }

}
