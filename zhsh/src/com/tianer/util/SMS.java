package com.tianer.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 
* 类名称：SMS   
* 类描述：   长兴生活
* 创建人：魏汉文  
* 创建时间：2016年6月17日 上午9:21:44
 */
public class SMS {
	    // private static final String addr = "http://api.sms.cn/mt/";
		private static final String addr = "http://api.sms.cn/mtutf8/";
		private static final String userId = "cxshapp111";

		/*
		 * 如uid是：test，登录密码是：123123  
		 * 加密后：则加密串为  md5(123123test)=b9887c5ebb23ebb294acab183ecf0769
		 * 
		 * 可用在线生成地址：http://www.sms.cn/password
		 */

		private static final String pwd = "59491d82306bbe4f69a6e0e53c92cb77";

		private static final String encode = "utf8";

		public static void send(String msgContent, String mobile) throws Exception {

			
			// 组建请求
			String straddr = addr + "?uid=" + userId + "&pwd=" + pwd + "&mobile="
					+ mobile + "&encode=" + encode + "&content="
					+ URLEncoder.encode(msgContent, "UTF-8");

			StringBuffer sb = new StringBuffer(straddr);
			System.out.println("URL:" + sb);

			// 发送请求
			URL url = new URL(sb.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));

			// 返回结果
			String inputline = in.readLine();
			System.out.println("Response:" + inputline);
		}

		
		
		public static void main(String[] args) {
			try {
				send("您的验证码是："+123456+",请保密并确认本人操作【长兴生活温馨提示】", "15260282340");
 			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
