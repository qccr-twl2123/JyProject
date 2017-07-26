package com.tianer.util.alipaypay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.tianer.controller.base.BaseController;
import com.tianer.util.PageData;

public class AlipayLogin extends BaseController {
	
	
	/**
	 * 
	* 方法名：loginAlipayYanZheng
 	* 描述：支付宝登录--获取用户ID 
	* 返回类型：json
 	 */
 	public static PageData LoginAlipayYanZheng(PageData pd,String  app_auth_code ) throws Exception{
      	try {
    		String privateKey = AlipayConfig.merchant_private_key;
   		    String publicKey = AlipayConfig.alipay_public_key;
    		 //使用auth_code换取接口access_token及用户userId
   		     AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AlipayConfig.app_id,privateKey,"json","UTF-8",publicKey,"RSA2");//正常环境下的网关
	    	 AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();//创建API对应的request类
	   		 request.setGrantType("authorization_code");
	   		 request.setCode(app_auth_code);
	   		 AlipaySystemOauthTokenResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
//	   		 System.out.print(response.getBody());
	   		 String access_token=response.getAccessToken();
 	   		 pd=getAplipayUserInfor(pd, access_token);
    	} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
  		}
//     	System.out.println("支付宝："+pd.toString());
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
//		    System.out.print(response.toString());
  		}catch(Exception e){
 			e.printStackTrace();
  		}
 		return pd;
 	} 
 	
 	
 	public static void main(String[] args) {
  			try {
//  			LoginAlipayYanZheng("");
			} catch (Exception e) {
				// TODO: handle exception
			}
  	}
 	
	
}
