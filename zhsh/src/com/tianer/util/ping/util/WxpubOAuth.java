package com.tianer.util.ping.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.util.FileUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/**
 * 用于微信公众号OAuth2.0鉴权，用户授权后获取授权用户唯一标识openid
 * WxpubOAuth中的方法都是可选的，开发者也可根据实际情况自行开发相关功能，
 * 详细内容可参考http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
 */
public class WxpubOAuth {

    /**
     * URLEncoder charset
     */
    public static final String CHARSET = "UTF-8";

    private static int getopenidnumber=0;
    /**
     * 获取微信公众号授权用户唯一标识
     *
     * @param appId     微信公众号应用唯一标识
     * @param appSecret 微信公众号应用密钥（注意保密）
     * @param code      授权code, 通过调用WxpubOAuth.createOauthUrlForCode来获取
     * @return openid   微信公众号授权用户唯一标识, 可用于微信网页内支付
     */
    public static PageData getOpenId(PageData pd,String appId, String appSecret, String code) throws UnsupportedEncodingException {
         String url = WxpubOAuth.createOauthUrlForOpenid(appId, appSecret, code);
         String ret = WxpubOAuth.httpGet(url);
//         JsonParser jp = new JsonParser();
//         JsonObject respJson = jp.parse(ret).getAsJsonObject();
//         if(respJson.get("errcode") != null && FileUtil.readTxtFile("C:\\openid_errcode.txt").contains(String.valueOf(respJson.get("errcode")))){
//             System.out.println("getOpenId"+String.valueOf(respJson.get("errcode")));
//             return "again";
//          }
//          return respJson.get("openid").getAsString();
         OAuthResult oAuthResult = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create().fromJson(ret, OAuthResult.class);
         pd.put("wxopen_id", oAuthResult.getOpenid());
         pd.put("access_token", oAuthResult.getAccessToken());
         /*
          * OAuthResult [
				accessToken=gRRmK_in0h0UOwzgYaDkd0Q_J_xrlqEDAMpmjjB3lL_MarIof1vEKVIZfof4NRKuJslfVPDgjf4O5f6EKs77uIQgH037xRUnevjMvwlGDGg,
				 expiresIn=7200, 
				 refreshToken=5y88Qhsomq7gn5Fuyk1XCWn18OhFltuQpvUWmw1ZShFkfvnPtl5cSJvvCEZSTlzTVYz7QklsG4ZbF4k4lkkWww9mP9juHhoodAg6tLXH-Fo, 
				openid=owD2DwsxdygwHXxNV75kjGT7Wvlw,
				 scope=snsapi_userinfo]
          */
         return pd;
    }
    
    
//    /**
//     * 获取access_token
//     * @param appId     微信公众号应用唯一标识
//     * @param appSecret 微信公众号应用密钥（注意保密）
//     * @param code      授权code, 通过调用WxpubOAuth.createOauthUrlForCode来获取
//     * @return openid   微信公众号授权用户唯一标识, 可用于微信网页内支付
//     */
//    public static String getAccess_token(String appId, String appSecret, String code) throws UnsupportedEncodingException {
//        String url = WxpubOAuth.createOauthUrlForOpenid(appId, appSecret, code);
//         String ret = WxpubOAuth.httpGet(url);
//        OAuthResult oAuthResult = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .create().fromJson(ret, OAuthResult.class);
//
//        return oAuthResult.getAccessToken();
//    }
//    
//    

    /**
     * 用于获取授权code的URL地址，此地址用于用户身份鉴权，获取用户身份信息，同时重定向到$redirect_url
     *
     * @param appId       微信公众号应用唯一标识
     * @param redirectUrl 授权后重定向的回调链接地址，重定向后此地址将带有授权code参数，
     *                    该地址的域名需在微信公众号平台上进行设置，
     *                    步骤为：登陆微信公众号平台  开发者中心  网页授权获取用户基本信息 修改
     * @param moreInfo    FALSE 不弹出授权页面,直接跳转,这个只能拿到用户openid
     *                    TRUE 弹出授权页面,这个可以通过 openid 拿到昵称、性别、所在地，
     * @return 用于获取授权code的URL地址
     */
    public static String createOauthUrlForCode(String appId, String redirectUrl, boolean moreInfo)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
        sb.append("appid=").append(appId);
        sb.append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, CHARSET));
        sb.append("&response_type=code");
        sb.append("&scope=").append(moreInfo ? "snsapi_userinfo" : "snsapi_base");
        sb.append("&state=pingpp");
        sb.append("#wechat_redirect");

        return sb.toString();
    }

    /**
     * 获取openid的URL地址
     *
     * @param appId     微信公众号应用唯一标识
     * @param appSecret 微信公众号应用密钥（注意保密）
     * @param code      授权code, 通过调用WxpubOAuth.createOauthUrlForCode来获取
     * @return 获取openid的URL地址
     */
    private static String createOauthUrlForOpenid(String appId, String appSecret, String code)
            throws UnsupportedEncodingException {
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appId);
        data.put("secret", appSecret);
        data.put("code", code);
        data.put("grant_type", "authorization_code");
        String queryString = WxpubOAuth.httpBuildQuery(data);

        return "https://api.weixin.qq.com/sns/oauth2/access_token?" + queryString;
    }
    
    

    private static String httpBuildQuery(Map<String, String> queryString) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : queryString.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(URLEncoder.encode(e.getKey(), CHARSET)).append('=').append(URLEncoder.encode(e.getValue(), CHARSET));
        }
         return sb.toString();
    }

    /**
     * Http Get 请求
     * @param urlString
     * @return
     */
    private static String httpGet(String urlString) {
        String result = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
            String line;
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    /**
//     * 获取微信公众号 jsapi_ticket
//     * @param appId
//     * @param appSecret
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public static String getJsapiTicket(String appId, String appSecret) throws UnsupportedEncodingException {
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appid", appId);
//        data.put("secret", appSecret);
//        data.put("grant_type", "client_credential");
//        String queryString = WxpubOAuth.httpBuildQuery(data);
//        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?" + queryString;
//        String resp = httpGet(accessTokenUrl);
//        JsonParser jp = new JsonParser();
//        JsonObject respJson = jp.parse(resp).getAsJsonObject();
//        if (respJson.has("errcode")) {
//            return respJson.toString();
//        }
//
//        data.clear();
//        data.put("access_token", respJson.get("access_token").getAsString());
//        data.put("type", "jsapi");
//        queryString = WxpubOAuth.httpBuildQuery(data);
//        String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?" + queryString;
//        resp = httpGet(jsapiTicketUrl);
//        JsonObject ticket = jp.parse(resp).getAsJsonObject();
//        return ticket.get("ticket").getAsString();
//    }

//    /**
//     * 生成微信公众号 js sdk signature
//     * @param charge
//     * @param jsapiTicket
//     * @param url
//     * @return
//     */
//    public static String getSignature(String charge, String jsapiTicket, String url) {
//        if (null == charge || null == jsapiTicket || charge.isEmpty() || jsapiTicket.isEmpty())
//            return null;
//        JsonParser jp = new JsonParser();
//        JsonObject chargeJson = jp.parse(charge).getAsJsonObject();
//        if (!chargeJson.has("credential")) {
//            return null;
//        }
//        JsonObject credential = chargeJson.get("credential").getAsJsonObject();
//        if (!credential.has("wx_pub")) {
//            return null;
//        }
//
//        JsonObject wx_pub = credential.get("wx_pub").getAsJsonObject();
//
//        String signature = "";
//        //注意这里参数名必须全部小写，且必须有序
//        String string1 = "jsapi_ticket=" + jsapiTicket +
//                "&noncestr=" + wx_pub.get("nonceStr").getAsString() +
//                "&timestamp=" + wx_pub.get("timeStamp").getAsString() +
//                "&url=" + url;
//        try {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//            crypt.reset();
//            crypt.update(string1.getBytes(CHARSET));
//            signature = byteToHex(crypt.digest());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return signature;
//    }
    
    /**
     * 生成微信公众号signature
     * @param charge
     * @param jsapiTicket
     * @param url
     * @return
     */
    public static String getSignatureAjax(String nonceStr, String timeStamp ,String jsapiTicket, String url) {
         String signature = "";
         try {
        	//注意这里参数名必须全部小写，且必须有序
             String string1 = "jsapi_ticket=" + jsapiTicket +
                     "&noncestr=" + nonceStr +
                     "&timestamp=" + timeStamp +
                     "&url=" + url;
//        	System.out.println(string1);
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes(CHARSET));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
         return signature;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    
    
//    /**
//     * 获取基础的access_token 
//     * @return
//     */
//    public static String getAccess_token(){
//    	String access_token="";
//    	try {
//    		 Map<String, String> data = new HashMap<String, String>();
//	    	 data.put("appid", WxUtil.APP_ID);
//	         data.put("secret", WxUtil.APP_SECRET);
//	         data.put("grant_type", "client_credential");
//	         String queryString = WxpubOAuth.httpBuildQuery(data);
//	         String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?" + queryString;
//	         String resp = httpGet(accessTokenUrl);
//	         JsonParser jp = new JsonParser();
//	         JsonObject respJson = jp.parse(resp).getAsJsonObject();
// 	         access_token=respJson.get("access_token").getAsString();
//		} catch (Exception e) {
//			// TODO: handle exception
//			(new TongYong()).dayinerro(e);
//		}
//    	return access_token;
//    }
    
    
    
    /**
     * 获取基础的access_token 
     * @param appId     微信公众号应用唯一标识
     * @param appSecret 微信公众号应用密钥（注意保密）
      */
    public static PageData setJiChuAccess_token() throws UnsupportedEncodingException {
    	PageData pd=new PageData();
    	pd.put("result", "1");
    	try { 
    		 Map<String, String> data = new HashMap<String, String>();
	    	 data.put("appid", WxUtil.APP_ID);
	         data.put("secret", WxUtil.APP_SECRET);
	         data.put("grant_type", "client_credential");
	         String queryString = WxpubOAuth.httpBuildQuery(data);
	         String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?" + queryString;
	         String resp = httpGet(accessTokenUrl);
	         JsonParser jp = new JsonParser();
	         JsonObject respJson = jp.parse(resp).getAsJsonObject();
 	         String access_token=respJson.get("access_token").getAsString();
	         data.clear();
	         data.put("access_token", access_token);
	         data.put("type", "jsapi");
	         queryString = WxpubOAuth.httpBuildQuery(data);
	         String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?" + queryString;
	         resp = httpGet(jsapiTicketUrl);
	         JsonObject ticket = jp.parse(resp).getAsJsonObject();
	         String jsapi_ticket=ticket.get("ticket").getAsString();
 	         pd.put("access_token", access_token);
	         pd.put("jsapi_ticket", jsapi_ticket);
 			 ServiceHelper.getAppMemberService().editWx(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			(new TongYong()).dayinerro(e);
		}
         return pd;
     }
    
    private static int n=0;
     
    /**
     * 获取用户的基本信息
     * @param openid     微信公众号应用唯一标识
     * @param access_token 微信公众号应用密钥（注意保密）
      */
    public static PageData getWxInformation(PageData pd,String openid,String access_token) throws UnsupportedEncodingException {
     	try { 
      		 Map<String, String> data = new HashMap<String, String>();
 	    	 data.put("access_token",access_token);
	         data.put("openid", openid);
	         data.put("lang", "zh_CN");
 	         String queryString = WxpubOAuth.httpBuildQuery(data);
	         String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/user/info?" + queryString;
	         String resp = httpGet(accessTokenUrl);
	         JsonParser jp = new JsonParser();
	         JsonObject respJson = jp.parse(resp).getAsJsonObject();
   	         if(respJson.has("errcode")) {//access_token 为空41001 过期40001 42001,40003
   	        	 System.out.println("getWxInformation=====access_token错误"+resp.toString());
   	        	 if(n < 10 && FileUtil.readTxtFile("C:\\access_tokenerror.txt").contains(String.valueOf(respJson.get("errcode")))){
   	        		 ++n;
   	        		 access_token=  setJiChuAccess_token().getString("access_token");
	        		 return  getWxInformation(pd, openid,access_token);
   	        	 }else{
   	        		 n=0;
	        		 pd.put("result", "0");
	        		 return pd;
	        	 }
 	         }
   	         n=0;
 	        String subscribe=respJson.get("subscribe").getAsString();
 	        pd.put("subscribe", subscribe);
 	        if(subscribe.equals("0")){
 	        	pd.put("wxunionid", respJson.get("unionid").getAsString());
 		        pd.put("wxopen_id", openid);
  	        	return pd;
 	        }
	        pd.put("sex", respJson.get("sex").getAsString());
  	        pd.put("image_url", respJson.get("headimgurl").getAsString());
	        pd.put("name", respJson.get("nickname").getAsString());
	        pd.put("province_name", respJson.get("province").getAsString());
	        pd.put("city_name", respJson.get("city").getAsString());
	        pd.put("wxunionid", respJson.get("unionid").getAsString());
	        pd.put("wxopen_id", openid);
 		} catch (Exception e) {
			// TODO Auto-generated catch block
 			(new TongYong()).dayinerro(e);
 		}
         return pd;
     }
    
    
    /**
     * 获取未关注的用户的基本信息
     * @param openid     微信公众号应用唯一标识
     * @param access_token 微信公众号应用密钥（注意保密）
      */
    public static PageData getUserInforForNotGuanZhu(PageData pd,String openid,String access_token) throws UnsupportedEncodingException {
     	try { 
      		 Map<String, String> data = new HashMap<String, String>();
 	    	 data.put("access_token",access_token);
	         data.put("openid", openid);
	         data.put("lang", "zh_CN");
 	         String queryString = WxpubOAuth.httpBuildQuery(data);
	         String accessTokenUrl = "https://api.weixin.qq.com/sns/userinfo?" + queryString;
 	         String resp = httpGet(accessTokenUrl);
	         JsonParser jp = new JsonParser();
	         JsonObject respJson = jp.parse(resp).getAsJsonObject();
 	         pd.put("sex", respJson.get("sex").getAsString());
  	         pd.put("image_url", respJson.get("headimgurl").getAsString());
	         pd.put("name", respJson.get("nickname").getAsString());
	         pd.put("province_name", respJson.get("province").getAsString());
	         pd.put("city_name", respJson.get("city").getAsString());
  		} catch (Exception e) {
			// TODO Auto-generated catch block
 			(new TongYong()).dayinerro(e);
 		}
         return pd;
     }
    
    
    public static void main(String[] args) {
    	try {
    		String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token=gRRmK_in0h0UOwzgYaDkd0Q_J_xrlqEDAMpmjjB3lL_MarIof1vEKVIZfof4NRKuJslfVPDgjf4O5f6EKs77uIQgH037xRUnevjMvwlGDGg&openid=owD2DwsxdygwHXxNV75kjGT7Wvlw&lang=zh_CN";
    		System.err.println(httpGet(url));
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
    
    
    
    
    
    

    
    class OAuthResult {
        String accessToken;
        int expiresIn;
        String refreshToken;
        String openid;
        String scope;
 
        public String getAccessToken() {
            return accessToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public String getOpenid() {
            return openid;
        }

        public String getScope() {
            return scope;
        }

		@Override
		public String toString() {
			return "OAuthResult [accessToken=" + accessToken + ", expiresIn="
					+ expiresIn + ", refreshToken=" + refreshToken
					+ ", openid=" + openid + ", scope=" + scope + "]";
		}
        
        
    }
}
