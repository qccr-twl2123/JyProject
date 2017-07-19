package com.tianer.util.alipaypay;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.tianer.util.DateUtil;

/**
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
 

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2017071207728109";
	
	// 商户签约的pid
	public static String pid = "";
	
	// 商户唯一标识的target_id
	public static String target_id = "";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRtb+lojSVCzFI40SzRLmDoEsl+FArptsWzipRLXFkTYlulbQIAO9UtxdULkxYLssInP5pQfzkc+K0mvKVyLAnJffQFiRCZ2gsYqgbEdABemlKTdW5ZA1FO/d2C3KA/9wnviwSol4cVWLjjg9Ko7oZdjiwKUfG3EeS1zArqDGnVKgozWFU4cz9aUgRaNxW/SxLbHQZAChQpFnTUFTs3ntAY0j3QRDfMacyFPTJVoqVE0sU9vmjXGecFY4jAZ88DsUmSRv9+RTjeerHQOTht0NNnEAkjv8lML65U38RqPGR7+iO7UVIUoLWZ6LeWh8/5i+CMoWdAbPokiy6rmvwdE6tAgMBAAECggEAZpzw9ytRDnlQXv02H21RnCGhan8BrowWFGxiHsL67OEx3L/uhghqg2VhN3ZXa26gq1MsU2IWP6hgykGoftVTQG6bo15EcMMz+LYNnuqfarsTNTG1RpbwRYaike4j2DbXiuGBViokTp3PQ2OC31SINXAtIdogYyoz+XQzxzrupOCHpYM419VV9NPQflAQWDkTYBgwo/D9BdsvsiQ8hstYoOhy7IJLAWK3jDhkuCwijP04HXvlabLw+9rAkZWUFNQgEZdP7rqTu4KIuWfnrQ43BFqIxzia7aXjsuLwD8MO98GaUF/x+ZWfdRmdTffydhJ9WgyCJvRyHf4VLpfsC24tgQKBgQDQxz4rQdKUVKiOBmF/uHtUNMFoVFLdFUe7YsXnkNEhzUnY8Vu71LWU13UE0zxvT7L1oGW1RCeWKnDhTz3AiXRYVruOfEFLXllKKoit+oPimFsS8/R3ijeB0zRsdqKU0lU8DclU4Gohg8tA//g93NyrgZXYGqbCcXLn2JOEzX81DQKBgQCyqrMoaZaKnJ4m5c/5QiEFnY/6rY30b9mGBVr4i2IoO1IVBeIvL6CaY4tWRLHoDpeXE0QXBKoZzA6a3Wpp2kzgUXecxWyrmEBQlXBRGcmvBnY2yBVwkXep2B+T5ZhOCK4BAnTQh/4SxsVZNmWmW6kFjiZR2KYHiJI4Jk69S5pYIQKBgQDLY16wO2aO9E5kVdkD2M/kv6QvI/65vDJuXZIYvh7ORDSN8MuCF31x69fnBHYaplTcLw8GYMgD5YKkLFMnT4EVUiFTdL8TUm2/7+HJQ4TZ6bzHoPtmvTLH+d5XT9ehDarVX0Tkt98MmV0YDIVkltYvCoXUylnskSL0tCs6NAuZvQKBgQCpV9wJ0NKMPNg13BrckU5FSUtfeUKUTbNqMQdBjrMRmPA61uHEgasCypkVwnSxk6sg2XTvME+dteid5oFnJS86al0p7YILvnovJwVXCU89Iq3HTKvtjyNRi3SatMJALD/o2xAnWs55s4b4APQl0rkId+xgJS1w9IRKzhxRlSgMoQKBgCDNbwpfKcO16T5kpdCtzjvgKTIku24RZiHdzb2xZP1W6Oj7mhD8S3Gg1fCmSHKk/2egZWVhwepHdZ3vjZxLBJRut+fA4G3RJHDgqUMZXGEmpUMGqehmfPnv0+JCz4vIUCmuaB/7vaPcq6sQhvazcWGN8t3KgfLsRggjetP8eczu";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx+keey7EdyEJHF/gO6FyRPHclF2Bx+PZQyqtRa1nwHWCnsqJRDKx97U5F1gijcSBxaGGsUEozcaZE0GvljTPvabWBw/Nq2dwxBcX8Cgbxm+SP03sIvK8MORkRrCGj89Gg1ZGd29ZsDeHXYXsH9P/5sL5+EbRGQzLaCrF8sFPwI2oL2U2bY8EJgeugIPLos1P1Sfje75IwXebWPpCORZHcg1wNOH+H3GvklKT7Z8bgPyTV9Y+F7mbo2oL+DCLsyKYeAyCSJ6PIZl8YNZpTsllGdxBF8576A86JMSgCoYtfycnP8VfK4vMSr1EXy6lU/bMixsJ+jgzEj9xx5tZCU6LmwIDAQAB" ;
 
//	public static String notify_url = "https://www.jiuyuvip.com/";// 服务器异步通知页面路径  --回调接口
//  public static String return_url = "https://www.jiuyuvip.com/";//支付完展示的页面
 	
  	// 服务器异步通知页面路径  --PC回调接口
  	public static String notify_url_pc = "https://www.jiuyuvip.com/back_pc/alipaynotify.do";
  	// 服务器异步通知页面路径  --商家端App回调接口
  	public static String notify_url_app = "https://www.jiuyuvip.com/back_sapp/alipaynotify.do";
 	
 	

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "UTF-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	
	// 日志
	public static String log_path = "C:\\";

 

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    

	
	/**
	 * 构造授权参数列表
	 * 
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	public static Map<String, String> buildAuthInfoMap() {
		Map<String, String> keyValues = new HashMap<String, String>();

		// 商户签约拿到的app_id，如：2013081700024223
		keyValues.put("app_id", app_id);

		// 商户签约拿到的pid，如：2088102123816631
		keyValues.put("pid", pid);

		// 服务接口名称， 固定值
		keyValues.put("apiname", "com.alipay.account.auth");

		// 商户类型标识， 固定值
		keyValues.put("app_name", "mc");

		// 业务类型， 固定值
		keyValues.put("biz_type", "openservice");

		// 产品码， 固定值
		keyValues.put("product_id", "APP_FAST_LOGIN");

		// 授权范围， 固定值
		keyValues.put("scope", "kuaijie");

		// 商户唯一标识target_id，如：kkkkk091125
		keyValues.put("target_id", target_id);

		// 授权类型， 固定值
		keyValues.put("auth_type", "AUTHACCOUNT");

		// 签名类型
		keyValues.put("sign_type", sign_type);

		return keyValues;
	}
    
    
    
    /**
	 * 构造支付订单--验签sign--拼接字符创--utf-8编码格式按 
	 * @param total_amount 订单金额
	 * @param subject 标题
	 * @param body 1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值商品
	 * @param out_trade_no 订单号
	 * @return
	 */
	public static String LastpayStr(String total_amount,String body,String out_trade_no) {
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", app_id);
 		String subject="";
		if(body.equals("1")){
			subject="优惠买单-购买商品";
		}else if(body.equals("2")){
			subject= "提货券-购买商品";//相当于支付宝的subject
		}else if(body.equals("3")){
			subject="九鱼优选-购买商品";
		}else{
			subject="九鱼网-充值余额";
		}
 		keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"" + total_amount+  "\",\"subject\":\"" + subject+  "\",\"body\":\"" + body+  "\",\"out_trade_no\":\"" + out_trade_no +  "\"}");
 		keyValues.put("charset", "utf-8");
 		keyValues.put("method", "alipay.trade.app.pay");
 		keyValues.put("sign_type", sign_type);
 		keyValues.put("timestamp", DateUtil.getTime());
 		keyValues.put("version", "1.0");
		keyValues.put("notify_url", notify_url_app);//app回调地址
  		String str=AlipayConfig.buildOrderParam(keyValues);//进行utf-8编码
		String sign=AlipayConfig.getSign(keyValues);
 		str=str+"&sign="+sign;
  		return str;
	}

	/**
	 * 构造支付订单参数列表
	 * @param total_amount 订单金额
	 * @param subject 标题
	 * @param body 1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值商品
	 * @param out_trade_no 订单号
	 * @return
	 */
	public static Map<String, String> buildOrderParamMap(String total_amount,String subject,String body,String out_trade_no) {
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", app_id);
 		keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"" + total_amount+  "\",\"subject\":\"" + subject+  "\",\"body\":\"" + body+  "\",\"out_trade_no\":\"" + out_trade_no +  "\"}");
 		keyValues.put("charset", "utf-8");
 		keyValues.put("method", "alipay.trade.app.pay");
 		keyValues.put("sign_type", sign_type);
 		keyValues.put("timestamp", DateUtil.getTime());
 		keyValues.put("version", "1.0");
		keyValues.put("notify_url", notify_url_app);//app回调地址
		System.out.println(keyValues.toString());
		
		String sign=AlipayConfig.getSign(keyValues);
		keyValues.put("sign", sign);
		
		return keyValues;
	}
	
	/**
	 * 构造支付订单参数信息
	 * @param map
	 * @return
	 * map转String并用&拼接
	 */
	public static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		 
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}
	
	/**
	 * 拼接键值对-和上个方法并用
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 对支付参数信息进行签名,获取sign签名
	 * @param map      map
	 * @param rsaKey   私钥
	 * @return
	 */
	public static String getSign(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

 		String encodedSign = AlipaySignUtils.sign(authInfo.toString(), merchant_private_key, true);
 		try {
			encodedSign = URLEncoder.encode(encodedSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
 		
		return encodedSign;
	}
 
}

