package com.tianer.util.wxpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.github.wxpay.sdk.WXPayConstants.SignType;

/**
 * 微信支付通道
 * @author Administrator
 *
 */
public class WXPayPath {

    private WXPay wxpay;
    private WXPayConfigImpl config;
 
    public WXPayPath(String type) throws Exception {
        config = WXPayConfigImpl.getInstance(type);
        wxpay = new WXPay(config);
    }
    


    public String getAppID() {
        return config.getAppID();
    }

    public String getMchID() {
        return config.getMchID();
    }

    public  String getKey() {
        return config.getKey();
    }
    public  String getSpbill_create_ip() {
    	return "139.196.81.14";
    }

    /**
     * sign验签Md5
     * @return
     * @throws Exception 
     */
    public  boolean YanQian(String xmlStr) throws Exception{
     	return WXPayUtil.isSignatureValid(xmlStr, config.getKey());
    }
    
    
    /**
     * 添加签名--Md5 
     * 生成带有 sign 的 XML 格式字符串
     * @return
     * @throws Exception 
     */
    public  String AddSign(Map<String, String> data) throws Exception{
     	return WXPayUtil.generateSignedXml(data, config.getKey());
    }
    
    /**
     * 添加签名--HMACSHA256 
     * 生成带有 sign 的 XML 格式字符串
     * @return
     * @throws Exception 
     */
    public  String AddSignByHMACSHA256(Map<String, String> data) throws Exception{
     	return WXPayUtil.generateSignedXml(data, config.getKey(),SignType.HMACSHA256);
    }
    
    /**
     * sign验签 -- HMACSHA256
     * @return
     * @throws Exception 
     */
    public  boolean YanQianHMACSHA256(String xmlStr) throws Exception{
    	Map<String, String> data=WXPayUtil.xmlToMap(xmlStr);
    	System.out.println(data.toString());
     	return WXPayUtil.isSignatureValid(data,  config.getKey(), SignType.HMACSHA256);
    }
    
   
   //=================================================================================
   
    /**
     * 作用：统一下单<br>向 Map 中添加 appid、mch_id、nonce_str、sign_type、sign <br>
     * 场景：公共号支付、扫码支付、APP支付
     * @param reqData 向wxpay post的请求数据
     * type :1-b端app，2-c端app，3-公众号
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData) throws Exception {
    	return wxpay.unifiedOrder(reqData);
    }
    
    
    //=================================================================================
    
    
    
//	//补全信息
//	map=dodo.fillRequestData(map);
//	//map转化为字符创
//	String newreqBody=WXPayUtil.mapToXml(map);
// 	System.out.println(newreqBody);
// 	Map<String, String> map2=dodo.payorderByHttps(newreqBody);
    
    /**
     * 向 Map 中添加 appid、mch_id、nonce_str、sign_type、sign <br>--然后开始支付
     * 该函数适用于商户适用于统一下单等接口，不适用于红包、代金券接口
     *
     * @param reqData
     * @return
     * @throws Exception 
     */
    public  Map<String, String> payorderByHttps(Map<String, String> data) throws Exception{
     	return payorderByHttps(WXPayUtil.mapToXml(wxpay.fillRequestData(data)));
    }

    /**
     * 自定义网络连接支付
     * 场景：公共号支付、扫码支付、APP支付
     * @param newreqBody
     * @return
     * @throws Exception
     * 返回结果map
     */
    public Map<String,String> payorderByHttps(String newreqBody) throws Exception{
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
         return map2;
    }
    
    

    /**
     * 小测试
     */
    public  void test001() {
        String xmlStr="<xml><appid><![CDATA[wx62d81eec40f745b4]]></appid><attach><![CDATA[1]]></attach><bank_type><![CDATA[CMB_DEBIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1366844902]]></mch_id><nonce_str><![CDATA[b52d7634b3494a8a]]></nonce_str><openid><![CDATA[owD2DwsxdygwHXxNV75kjGT7Wvlw]]></openid><out_trade_no><![CDATA[CZ20170719122528990166]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[7BD185F3191E6C72182A640A517A851450B878C70AC24D70BFB7AD19D9A5B911]]></sign><time_end><![CDATA[20170719122556]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[4008812001201707191543140374]]></transaction_id></xml>";
        try {
        	 Map<String, String> hm = WXPayUtil.xmlToMap(xmlStr);
        	 System.out.println(hm);
            System.out.println("+++++++++++++++++");
            System.out.println(WXPayUtil.isSignatureValid(hm, config.getKey(),SignType.HMACSHA256));
            
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    

    public static void main(String[] args) throws Exception {
         System.out.println("--------------->");
         WXPayPath dodo = new WXPayPath("3");
         dodo.test001();
          System.out.println("<---------------"); // wx2016112510573077
    }

 
}
