package com.tianer.util.alipaypay;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.tianer.controller.base.BaseController;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

public class AlipayLogin extends BaseController {
	
	
	/**
	 * 
	* 方法名：loginAlipayYanZheng
 	* 描述：支付宝登录--获取用户ID 
	* 返回类型：json
	* back_mapp/loginAlipayYanZheng.do
	 */
 	public static PageData LoginAlipayYanZheng(String  app_auth_code ) throws Exception{
 		PageData pd=new PageData();
     	try {
    		String privateKey = AlipayConfig.merchant_private_key;
   		    String publicKey = AlipayConfig.alipay_public_key;
   		    
   		    //使用auth_code换取接口access_token及用户userId
   		     AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AlipayConfig.app_id,privateKey,"json","UTF-8",publicKey,"RSA2");//正常环境下的网关
//   		 AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","沙箱环境下的应用APPID",privateKey,"json","UTF-8",publicKey,"RSA2");//沙箱下的网关
   		     
   		    AlipayOpenAuthTokenAppRequest requestLogin1 = new AlipayOpenAuthTokenAppRequest();
   		    requestLogin1.setBizContent("{" +
   		        "\"grant_type\":\"authorization_code\"," +
   		        "\"code\":\""+ app_auth_code +"\"" +
   		        "}");
   		    
   		    //第三方授权
   		    AlipayOpenAuthTokenAppResponse responseToken = alipayClient.execute(requestLogin1);
   		    if(responseToken.isSuccess()){
    		    String app_id=responseToken.getAuthAppId();
   		    	String access_token=responseToken.getAppAuthToken();
   		    	String user_id=responseToken.getUserId();
   		    	System.out.println("调用成功：app_id="+app_id+"&access_token="+access_token+"&user_id="+user_id); 
   		    	pd.put("user_id", user_id);
   		    	pd.put("access_token", access_token);
     		} else {
     			System.out.println("调用失败"); 
   		    }
   		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
  			ServiceHelper.getAppPcdService().saveLog(pd.toString(), "第三方授权系统异常","9999");
 		}
     	return pd;
 	} 
 	
	
}
