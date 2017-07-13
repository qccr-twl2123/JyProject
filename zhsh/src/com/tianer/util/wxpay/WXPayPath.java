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
 
    public WXPayPath() throws Exception {
        config = WXPayConfigImpl.getInstance();
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
     * sign验签
     * @return
     * @throws Exception 
     */
    public  boolean YanQian(String xmlStr) throws Exception{
     	return WXPayUtil.isSignatureValid(xmlStr, config.getKey());
    }
    
    
    /**
     * 添加签名--Md5
     * @return
     * @throws Exception 
     */
    public  String AddSign(Map<String, String> data) throws Exception{
     	return WXPayUtil.generateSignedXml(data, config.getKey(), SignType.MD5);
    }
    
   
   //=================================================================================
   
    /**
     * 作用：统一下单<br>向 Map 中添加 appid、mch_id、nonce_str、sign_type、sign <br>
     * 场景：公共号支付、扫码支付、APP支付
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     * @throws Exception
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData) throws Exception {
    	return wxpay.unifiedOrder(reqData);
    }
    
    
    //=================================================================================
    
    /**
     * 向 Map 中添加 appid、mch_id、nonce_str、sign_type、sign <br>
     * 该函数适用于商户适用于统一下单等接口，不适用于红包、代金券接口
     *
     * @param reqData
     * @return
     * @throws Exception 
     */
    public  Map<String, String> fillRequestData(Map<String, String> data) throws Exception{
     	return wxpay.fillRequestData(data);
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
        String xmlStr="<xml><return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<return_msg><![CDATA[OK]]></return_msg>\n" +
                "<appid><![CDATA[wx273fe72f2db863ed]]></appid>\n" +
                "<mch_id><![CDATA[1228845802]]></mch_id>\n" +
                "<nonce_str><![CDATA[lCXjx3wNx45HfTV2]]></nonce_str>\n" +
                "<sign><![CDATA[68D7573E006F0661FD2A77BA59124E87]]></sign>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<openid><![CDATA[oZyc_uPx_oed7b4q1yKmj_3M2fTU]]></openid>\n" +
                "<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
                "<trade_type><![CDATA[NATIVE]]></trade_type>\n" +
                "<bank_type><![CDATA[CFT]]></bank_type>\n" +
                "<total_fee>1</total_fee>\n" +
                "<fee_type><![CDATA[CNY]]></fee_type>\n" +
                "<transaction_id><![CDATA[4008852001201608221983528929]]></transaction_id>\n" +
                "<out_trade_no><![CDATA[20160822162018]]></out_trade_no>\n" +
                "<attach><![CDATA[]]></attach>\n" +
                "<time_end><![CDATA[20160822202556]]></time_end>\n" +
                "<trade_state><![CDATA[SUCCESS]]></trade_state>\n" +
                "<cash_fee>1</cash_fee>\n" +
                "</xml>";
        try {
            System.out.println(xmlStr);
            System.out.println("+++++++++++++++++");
            System.out.println(WXPayUtil.isSignatureValid(xmlStr, config.getKey()));
             Map<String, String> hm = WXPayUtil.xmlToMap(xmlStr);
            System.out.println("+++++++++++++++++");
            System.out.println(hm);
            System.out.println(hm.get("attach").length());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    

    public static void main(String[] args) throws Exception {
         System.out.println("--------------->");
         WXPayPath dodo = new WXPayPath();
         dodo.test001();
          System.out.println("<---------------"); // wx2016112510573077
    }

 
}
