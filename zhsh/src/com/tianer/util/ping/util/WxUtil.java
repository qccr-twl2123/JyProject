package com.tianer.util.ping.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tianer.util.ping.util.WxpubOAuth.OAuthResult;

/**
 * 
* 类名称：WxUtil   
* 类描述：   微信的工具类
* 创建人：魏汉文  
* 创建时间：2016年8月29日 下午3:29:05
 */
public class WxUtil {
	public static final String APP_ID = "wx62d81eec40f745b4";//微信公众号应用唯一标识
 	public static final String APP_SECRET = "982e0086749ae44c25825de2f1a3b083";//微信公众号应用密钥（注意保密）
	public static final String HOST = "https://www.jiuyuvip.com";
	public static final String CHESHIHOST = "http://192.168.1.12";
	
	
 }
