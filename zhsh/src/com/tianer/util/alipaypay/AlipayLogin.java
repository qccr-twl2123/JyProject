package com.tianer.util.alipaypay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.tianer.controller.base.BaseController;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

public class AlipayLogin extends BaseController {
	
	
	/**
	 * 
	* 方法名：loginAlipayYanZheng
 	* 描述：支付宝登录--获取用户ID 
	* 返回类型：json
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
   		    	//获取用户信息
   		    	pd=getAplipayUserInfor(pd, access_token);
     		} else {
     			System.out.println("调用失败"); 
     			pd=new PageData();
   		    }
    	} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			pd=new PageData();
  		}
     	System.out.println("支付宝："+pd.toString());
     	return pd;
 	} 
 	
 	
 	/**
 	 * 
 	 * 方法名：getAplipayUserInfor
  	 * 返回类型：pd
  	 * 获取用户信息
  	 */
 	public static PageData getAplipayUserInfor(PageData pd,String  access_token ) throws Exception{
 		try {
 			String privateKey = AlipayConfig.merchant_private_key;
   		    String publicKey = AlipayConfig.alipay_public_key;
 			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.app_id,privateKey,"json","UTF-8",publicKey,"RSA2");  //获得初始化的AlipayClient
		    AlipayUserUserinfoShareRequest request = new AlipayUserUserinfoShareRequest();//创建API对应的request类
		    AlipayUserUserinfoShareResponse response = alipayClient.execute(request, access_token);//在请求方法中传入上一步获得的access_token
 		    String image_url=response.getAvatar();
		    String name=response.getNickName();
		    String gender=response.getGender();//性别（F：女性；M：男性）
		    pd.put("image_url", image_url);
		    pd.put("name", name);
		    pd.put("sex", gender.equals("F")?"2":"1");
		    System.out.print(response.toString());
  		}catch(Exception e){
 			e.printStackTrace();
  		}
 		return pd;
 	} 
 	
	
}
