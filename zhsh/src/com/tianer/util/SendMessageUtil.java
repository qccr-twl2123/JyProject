package com.tianer.util;
 
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 
 * <P>description:   发送短信验证码</p>
*租赁通
 * @date             2015-8-25 下午3:28:59
 */
public final class SendMessageUtil {

	/**
	 * 用户名
	 */
	private final static String ACCOUNT_STRING = "SDK2557";
	
	/**
	 * 密码
	 */
	private final static String PASSWORD_STRING = "hzjf0123";
	
	/**
	 * 接口路径
	 */
	private final static String POSTURL_STRING = "http://api.bjszrk.com/sdk/BatchSend.aspx";
	
	/**
	 * 短信编码方式
	 */
	private final static String ENCODE = "utf-8";
	
	/**
	 * <p>description: 发送短信验证码</p>
	 * 
	 * @param      mobile_num
	 *					接收验证码手机号
	 *
	 * @param	   dowhat
	 * 					动作：update:修改验证，（默认注册）
	 *
	 * @HttpException   http请求异常
	 * @IOException		流读写异常	
	 *
	 * @author     旭东
	 *
	 * @date       2015年8月25日 下午3:08:49
	 */
	public static int sendMsg(String mobile_num, String dowhat){
		HttpClient httpClient = new HttpClient(); //创建http请求
		PostMethod postMethod = new PostMethod(POSTURL_STRING); //设置请求方式，添加请求地址
		
		httpClient.getParams().setContentCharset("UTF-8"); //设置编码格式
		postMethod.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8"); //设置头信息
		
		//生成随机数验证码
		int mobile_code = (int) ((Math.random()*9+1)*100000);
		
		//编辑短信内容
		String contentString = "尊敬的租赁通用户，您的验证码为:"+ mobile_code +"【租赁通】";
		if(dowhat.equals("update")){
			contentString = "尊敬的用户您好，您正在进行身份验证，验证码为"+ mobile_code +"，若非本人操作，请忽略本条信息【租赁通】";
		}
		
		/**
		 * 在DBConnectionString类中，解析ConnectionString时存储并串联Name/Value对
		 */
		NameValuePair[] data = {
				new NameValuePair("CorpID",ACCOUNT_STRING), //用户名
				new NameValuePair("Pwd",PASSWORD_STRING), //密码
				new NameValuePair("Mobile",mobile_num), //接收验证码手机号
				new NameValuePair("Content",contentString), //短信模版
				new NameValuePair("Cell",""), //子号
				new NameValuePair("SendTime",""), //定时发送
				new NameValuePair("encode",ENCODE) //编码格式
		};
		
		postMethod.setRequestBody(data);
		
		try {
			httpClient.executeMethod(postMethod); //发送请求
			
			String submitString = postMethod.getResponseBodyAsString(); //获取返回数据
			
			if(Long.parseLong(submitString) > 0){
				System.out.println("发送成功，验证码为：" + mobile_code + ",time: " + DateUtil.getTime());
			}
		}catch (HttpException e) {
			System.out.println("sendMsg of SendMessageUtil,the HttpException is: " + e.getMessage());
		}catch (IOException e) {
			System.out.println("sendMsg of SendMessageUtil,the IOException is: " + e.getMessage());
		}
		return mobile_code;
	}
	
	public static void main(String[] args) {
		sendMsg("18357116932", "");
	}
}
