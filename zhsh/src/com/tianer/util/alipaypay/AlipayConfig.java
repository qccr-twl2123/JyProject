package com.tianer.util.alipaypay;

import java.io.FileWriter;
import java.io.IOException;

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
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2017071207728109";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRtb+lojSVCzFI40SzRLmDoEsl+FArptsWzipRLXFkTYlulbQIAO9UtxdULkxYLssInP5pQfzkc+K0mvKVyLAnJffQFiRCZ2gsYqgbEdABemlKTdW5ZA1FO/d2C3KA/9wnviwSol4cVWLjjg9Ko7oZdjiwKUfG3EeS1zArqDGnVKgozWFU4cz9aUgRaNxW/SxLbHQZAChQpFnTUFTs3ntAY0j3QRDfMacyFPTJVoqVE0sU9vmjXGecFY4jAZ88DsUmSRv9+RTjeerHQOTht0NNnEAkjv8lML65U38RqPGR7+iO7UVIUoLWZ6LeWh8/5i+CMoWdAbPokiy6rmvwdE6tAgMBAAECggEAZpzw9ytRDnlQXv02H21RnCGhan8BrowWFGxiHsL67OEx3L/uhghqg2VhN3ZXa26gq1MsU2IWP6hgykGoftVTQG6bo15EcMMz+LYNnuqfarsTNTG1RpbwRYaike4j2DbXiuGBViokTp3PQ2OC31SINXAtIdogYyoz+XQzxzrupOCHpYM419VV9NPQflAQWDkTYBgwo/D9BdsvsiQ8hstYoOhy7IJLAWK3jDhkuCwijP04HXvlabLw+9rAkZWUFNQgEZdP7rqTu4KIuWfnrQ43BFqIxzia7aXjsuLwD8MO98GaUF/x+ZWfdRmdTffydhJ9WgyCJvRyHf4VLpfsC24tgQKBgQDQxz4rQdKUVKiOBmF/uHtUNMFoVFLdFUe7YsXnkNEhzUnY8Vu71LWU13UE0zxvT7L1oGW1RCeWKnDhTz3AiXRYVruOfEFLXllKKoit+oPimFsS8/R3ijeB0zRsdqKU0lU8DclU4Gohg8tA//g93NyrgZXYGqbCcXLn2JOEzX81DQKBgQCyqrMoaZaKnJ4m5c/5QiEFnY/6rY30b9mGBVr4i2IoO1IVBeIvL6CaY4tWRLHoDpeXE0QXBKoZzA6a3Wpp2kzgUXecxWyrmEBQlXBRGcmvBnY2yBVwkXep2B+T5ZhOCK4BAnTQh/4SxsVZNmWmW6kFjiZR2KYHiJI4Jk69S5pYIQKBgQDLY16wO2aO9E5kVdkD2M/kv6QvI/65vDJuXZIYvh7ORDSN8MuCF31x69fnBHYaplTcLw8GYMgD5YKkLFMnT4EVUiFTdL8TUm2/7+HJQ4TZ6bzHoPtmvTLH+d5XT9ehDarVX0Tkt98MmV0YDIVkltYvCoXUylnskSL0tCs6NAuZvQKBgQCpV9wJ0NKMPNg13BrckU5FSUtfeUKUTbNqMQdBjrMRmPA61uHEgasCypkVwnSxk6sg2XTvME+dteid5oFnJS86al0p7YILvnovJwVXCU89Iq3HTKvtjyNRi3SatMJALD/o2xAnWs55s4b4APQl0rkId+xgJS1w9IRKzhxRlSgMoQKBgCDNbwpfKcO16T5kpdCtzjvgKTIku24RZiHdzb2xZP1W6Oj7mhD8S3Gg1fCmSHKk/2egZWVhwepHdZ3vjZxLBJRut+fA4G3RJHDgqUMZXGEmpUMGqehmfPnv0+JCz4vIUCmuaB/7vaPcq6sQhvazcWGN8t3KgfLsRggjetP8eczu";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx+keey7EdyEJHF/gO6FyRPHclF2Bx+PZQyqtRa1nwHWCnsqJRDKx97U5F1gijcSBxaGGsUEozcaZE0GvljTPvabWBw/Nq2dwxBcX8Cgbxm+SP03sIvK8MORkRrCGj89Gg1ZGd29ZsDeHXYXsH9P/5sL5+EbRGQzLaCrF8sFPwI2oL2U2bY8EJgeugIPLos1P1Sfje75IwXebWPpCORZHcg1wNOH+H3GvklKT7Z8bgPyTV9Y+F7mbo2oL+DCLsyKYeAyCSJ6PIZl8YNZpTsllGdxBF8576A86JMSgCoYtfycnP8VfK4vMSr1EXy6lU/bMixsJ+jgzEj9xx5tZCU6LmwIDAQAB";

	
	public static String notify_url = "https://www.jiuyuvip.com/";// 服务器异步通知页面路径  --回调接口
  	public static String return_url = "https://www.jiuyuvip.com/";//支付完展示的页面
 	
  	// 服务器异步通知页面路径  --PC回调接口
  	public static String notify_url_pc = "https://www.jiuyuvip.com/back_pc/alipaynotify.do";
  	// 服务器异步通知页面路径  --商家端App回调接口
  	public static String notify_url_app = "https://www.jiuyuvip.com/back_sapp/alipaynotify.do";
 	
 	

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	
	// 日志
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

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
}

