package com.tianer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

 

/**
 * 
* 类名称：StringUtil   
* 类描述：   字符串相关方法
* 创建人：魏汉文  
* 创建时间：2016年5月30日 下午2:51:57
 */
public class StringUtil {
	
	//map转换成lis
		public static List  mapTransitionList(Map map) {
			List list = new ArrayList();
			Iterator iter = map.entrySet().iterator();  //获得map的Iterator
			while(iter.hasNext()) {
				Entry entry = (Entry)iter.next();
				list.add(entry.getValue());
			}
			return list;
		}
		
		//字母排序
		public static void  ziMuPaiXu() { // TODO Auto-generated method stub    
		        Comparator<Object> com=Collator.getInstance(java.util.Locale.CHINA);    
		        String[] newArray={"中山","汕头","广州","安庆","阳江","南京","武汉","北京","安阳","北方"};    
		        List<String> list = Arrays.asList(newArray);  
		        Collections.sort(list, com);   
		        for(String i:list){    
		            System.out.print(i+"  ");    
		        }  
	        }
	

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
		int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	
	/**
	 * 去除字符前的0
	 */
	public static String  getMoveUpZero(String str) {
		int len = str.length();//取得字符串的长度
		int index = 0;//预定义第一个非零字符串的位置
		char strs[] = str.toCharArray();// 将字符串转化成字符数组
		for(int i=0; i<len; i++){
				if('0'!=strs[i]){
						index=i;// 找到非零字符串并跳出
						break;
				}
		}
		String strLast = str.substring(index, len);// 截取字符串
		return strLast;// 得到结果 strLast 
	}
	
	/**
	 * 去尾数0
	 */
	public static String  getMoveLastZero(String str) {
		if(str != null && !str.equals("0") ){
			while(str.length() != 1  && !str.equals("0") && !str.equals("0.")  && !str.equals(".") &&  (str.substring(str.length()-1).equals("0") || str.substring(str.length()-1).equals("."))){
				str=str.substring(0, str.length()-1);
			}
		}
 		return str; 
	}
	
	/**
	 * 去小数后的0
	 */
	public static String  getMoveFloatLastZero(String str) {
		if(str != null && !str.equals("0") ){
			while(str.contains(".") && (str.substring(str.length()-1).equals("0") || str.substring(str.length()-1).equals("."))){
				str=str.substring(0, str.length()-1);
			}
		}
 		return str; 
	}
	
	/**
	 * 得到最值然后补N个0
	 */
	public static String getNextId(String number){
		int  numberlength=number.length();
			//去掉0再补0
			number=getMoveUpZero(number);
			number=Integer.parseInt(number)+1+"";
			int m=numberlength-number.length();
 			for(int n=0;n<m ; n++){
					number="0"+number;
			}
 		return number;
 	}
	
	
	/**
	 * 补N个0
	 */
	public static String buZero(String number,int n){
 			for(int i=0;i<n ; i++){
					number="0"+number;
			}
 		return number;
 	}
	

	
	/**
	 * 随机生成四位数验证码 
	 * @return
	 */
	public static String getSixRandomNum(){
		 Random r = new Random();
		 int n=r.nextInt(9000)+1000;
		 return n+"" ;//(Math.random()*(999999-100000)+100000)
	}

	
	/**
	 * 得到32位的uuid
	 * @return
	 */
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	
	/**
	  * 验证邮箱
	  * @param email
	  * @return
	  */
	 public static boolean checkEmail(String email){
	  boolean flag = false;
	  try{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	
	 

	    /** 
	     * 大陆号码或香港号码均可 
	     */  
	    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {  
	        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);  
	    }  
	  
	    /** 
	     * 大陆手机号码11位数，匹配格式： ^1[3|4|5|7|8][0-9]\d{8}$
	     *              前三位固定格式:(13[0-9])|(15[^4])|(18[0,1,2,3,5-9])|(17[0-8])|(147))
	     *              +后8位任意数 :\\d{8}$
	     * 此方法中前三位格式有： 
	     * 13+任意数 
	     * 15+除4的任意数 
	     * 18+除1和4的任意数 
	     * 17+除9的任意数 
	     * 147 
	     */  
	    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
	        String regExp = "^((13[0-9])|(15[^4])|(18[0,1,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
	        Pattern p = Pattern.compile(regExp);  
	        Matcher m = p.matcher(str);  
	        return m.matches();  
	    }  
	  
	    /** 
	     * 香港手机号码8位数，5|6|8|9开头+7位任意数 
	     */  
	    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {  
	        String regExp = "^(5|6|8|9)\\d{7}$";  
	        Pattern p = Pattern.compile(regExp);  
	        Matcher m = p.matcher(str);  
	        return m.matches();  
	    }  
	 
	 
	 /**
	  * 验证整数
	  * @param mobiles
	  * @return
	  */
	 public static boolean checkNumber(String number){
	  boolean flag = false;
	  try{
 	    flag =   number.matches("^[0-9]*$");
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	 
	 /**
	  * 验证小数
	  * @param mobiles
	  * @return
	  */
	 public static boolean checkDouble(String number){
		 boolean flag = false;
		 try{
			 flag =   number.matches("^[0.0-9.0]+$");
		 }catch(Exception e){
			 flag = false;
		 }
		 return flag;
	 }
	 
	 /**
	  * 验证字母
	  * @param mobiles
	  * @return
	  */
	 public static boolean checkZiMu(String number){
		 boolean flag = false;
		 try{
			 flag =   number.matches(".*[a-zA-Z]+.");
		 }catch(Exception e){
			 flag = false;
		 }
		 return flag;
	 }
	 
	 /**
	  * 包含是否为一个金钱数
	  * @param mobiles
	  * @return
	  */
	 public static boolean checkMoney(String number){
		 boolean flag = false;
		 try{
			 if(number.contains(".")){
				 flag=checkDouble(number);
				 if(number.indexOf(".") != number.lastIndexOf(".")){
					 flag=false;
				 }
			 }else{
				 flag=checkNumber(number);
			 }
 		 }catch(Exception e){
			 flag = false;
		 }
		 return flag;
	 }
	 
	 
	
		/**
		 * 获取本机ip
		 * @return
		 */
		public static String getIp(){
			String ip = "";
			try {
				InetAddress inet = InetAddress.getLocalHost();
				ip = inet.getHostAddress();
	 		} catch (UnknownHostException e) {
				e.printStackTrace();
			}
 			return ip;
		}
		
		/*
		 * 获取IP
		 */
		public static String getIp(HttpServletRequest request) {
			String ipAddress = null;
			// ipAddress = this.getRequest().getRemoteAddr();
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0
					|| "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0
					|| "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0
					|| "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}

			}
	 		 //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			 if(ipAddress!=null && ipAddress.length()>15){
			 //"***.***.***.***".length() = 15
			 if(ipAddress.indexOf(",")>0){
			 ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
			 }
			 }
			return ipAddress;
		}
		
		
		/**
		 * 
		* 方法名称:：getNumber 
		* 方法描述：将阿拉伯数字转为英语
		* 创建人：魏汉文
		* 创建时间：2016年6月17日 下午7:21:50
		 */
		public static String getNumber(int i){
				String number="";
				Map<Integer, String> map = new HashMap<Integer, String>() {};
		                map.put(0, "zero");
		                map.put(1, "one");
		                map.put(2, "two");
		                map.put(3, "three");
		                map.put(4, "four");
		                map.put(5, "five");
		                map.put(6, "six");
		                map.put(7, "seven");
		                map.put(8, "eight");
		                map.put(9, "nine");
		                map.put(10, "ten");
		                map.put(11, "eleven");
		                map.put(12, "twelve");
		                map.put(13, "thirteen");
		                map.put(14, "fourteen");
		                map.put(15, "fifteen");
		                map.put(16, "sixteen");
		                map.put(17, "seventeen");
		                map.put(18, "eighteen");
		                map.put(19, "nineteen");
		                map.put(20, "twenty");
		                map.put(21, "twentyone");
		                map.put(22, "twentytwo");
		                map.put(23, "twentythree");
		                map.put(24, "twentyfour");
		                map.put(25, "twentyfive");
		                map.put(26, "twentysix");
		                map.put(27, "twentyseven");
		                map.put(28, "twentyeight");
		                map.put(29, "twentynine");
		                map.put(30, "thirty");
		                map.put(31, "thirtyone");
		                number=map.get(i);
			return number;
		}
		
		/**
		 * 一个范围内的随机数
		* 方法名称:：getSuiJi 
		* 方法描述：
		* 创建人：魏汉文
		* 创建时间：2016年6月30日 上午10:30:09
		 */
		public static double getSuiJi(double a,double b){
			       Random rnd=new Random();
			       double c=b-rnd.nextDouble()*(b-a);
				   return c;
		}
		

		
		 /** 
	     * 判断str1中包含str2的个数 
	      * @param str1 
	     * @param str2 
	     * @return counter 
	     */  
		private static int counter = 0; 
	    public static int countStr(String str1, String str2) {  
	        if (str1.indexOf(str2) == -1) {  
	            return 0;  
	        } else if (str1.indexOf(str2) != -1) {  
	            counter++;  
	            countStr(str1.substring(str1.indexOf(str2) +  
	                   str2.length()), str2);  
	               return counter;  
	        }  
	            return 0;  
	    }  
		
		/**
		 * 
		* 方法名称:：getUrl 
		* 方法描述：获取项目路径：class下
		* 创建人：魏汉文
		* 创建时间：2016年7月2日 下午1:12:11
		 */
		public static String getUrl(){
			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""));	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			return filePath;
		}
		
		/**
		 * 通过地址获取经纬度
		 */
		public static Map<String, String> getLatAndLngByAddress(String addr){
	        String address = "";
	        String lat = "";
	        String lng = "";
	        try {  
	            address = java.net.URLEncoder.encode(addr,"UTF-8");  
	        } catch (UnsupportedEncodingException e1) {  
	            e1.printStackTrace();  
	        } 
	        String url = String.format("http://api.map.baidu.com/geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&output=json&address=%s",address);
	        URL myURL = null;
	        URLConnection httpsConn = null;  
	        //进行转码
	        try {
	            myURL = new URL(url);
	        } catch (MalformedURLException e) {

	        }
	        try {
	            httpsConn = (URLConnection) myURL.openConnection();
	            if (httpsConn != null) {
	                InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
	                BufferedReader br = new BufferedReader(insr);
	                String data = null;
	                if ((data = br.readLine()) != null) {
	                    lat = data.substring(data.indexOf("\"lat\":") 
	                    + ("\"lat\":").length(), data.indexOf("},\"precise\""));
	                    lng = data.substring(data.indexOf("\"lng\":") 
	                    + ("\"lng\":").length(), data.indexOf(",\"lat\""));
	                }
	                insr.close();
	            }
	        } catch (IOException e) {

	        }
	        Map<String, String> map = new HashMap<String, String>();
	        map.put("lat",  lat );
	        map.put("lng",  lng );
	        return map;
	}
		
//		/**
//		* 获取指定IP对应的经纬度（为空返回当前机器经纬度）第一种方法
// 		* @param ip
//		* @return
//		 * @throws MalformedURLException 
//		*/
//		public static PageData getIPXY(String ip)  throws IOException, JSONException {
//			   PageData pd=new PageData();
////			   String ak = "KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9";
//			   if (null == ip ) {
//						ip = "";
//				}
//			   //高精确
//			    URL url = new URL("https://api.map.baidu.com/highacciploc/v1?qcip=&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll");
//			    InputStream is = url.openStream();  
// 			    try {  
// 				      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));  
//				      String jsonText = readAll(rd); 
//				      System.out.println("jsonText="+jsonText);
//				      JSONObject json = new JSONObject(jsonText);  
//				      int resultstatus=(int) ((JSONObject) json.get("result")).get("error");
//				      if(resultstatus == 161){
//				    	  double lng=(double) ((JSONObject) json.get("content")).getJSONObject("location").get("lng");
//					      double lat=(double) ((JSONObject) json.get("content")).getJSONObject("location").get("lat");
//					      System.out.println("经度："+lng);  
//						  System.out.println("维度："+lat);  
//						  pd.put("lng", lng+"");
//						  pd.put("lat", lat+"");
//				      }else{
//				    	  //普通精确
//				    	  url = new URL("https://api.map.baidu.com/location/ip?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coor=bd09ll&ip="+ip);
//				    	  is = url.openStream(); 
//				    	  rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))); 
//				    	  jsonText = readAll(rd); 
//				    	  System.out.println("jsonText="+jsonText);
//				    	  json = new JSONObject(jsonText); 
//				    	  String lng= (String) ((JSONObject) json.get("content")).getJSONObject("point").get("x");
//				    	  String lat=(String) ((JSONObject) json.get("content")).getJSONObject("point").get("y");
//					      System.out.println("经度："+lng);  
//						  System.out.println("维度："+lat);  
//						  pd.put("lng", lng);
//						  pd.put("lat", lat);
// 				      }
//	 			      return pd;  
//			    } finally {  
//			      is.close();  
//			     // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");  
//			    }  
//		  }
		/**
		 * 获取指定IP对应的经纬度（为空返回当前机器经纬度）第一种方法
		 * @param ip
		 * @return
		 * @throws MalformedURLException 
		 */
		public static PageData getIPXY(String ip)  throws IOException, JSONException {
			PageData pd=new PageData();
//			   String ak = "KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9";
			if (null == ip ) {
				ip = "";
			}
 			try {  
 				Socket s = new Socket("api.map.baidu.com",80);    
		        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));    
		        OutputStream  out= s.getOutputStream();   
		        //https://api.map.baidu.com/highacciploc/v1?qcip=&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll
		        StringBuffer sb = new StringBuffer("GET /highacciploc/v1?qcip=&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll HTTP/1.1\r\n");    
		        sb.append("User-Agent: Java/1.6.0_20\r\n");    
		        sb.append("Host: api.map.baidu.com:80\r\n");    
		        sb.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");    
		        sb.append("Connection: Close\r\n");    
		        sb.append("\r\n");    
		        out.write(sb.toString().getBytes());    
		        String jsonText ="";  
		        String tmp = "";    
		        while((tmp = br.readLine())!=null){    
//		            System.out.println(tmp); 
		            if(tmp.contains("location")){
		            	jsonText=tmp; 
		            }
 		        }    
  		        System.out.println(jsonText); 
  		        JSONObject json = new JSONObject(jsonText);
  		        int resultstatus=(int) ((JSONObject) json.get("result")).get("error");
		        if(resultstatus == 161){
			    	  double lng=(double) ((JSONObject) json.get("content")).getJSONObject("location").get("lng");
				      double lat=(double) ((JSONObject) json.get("content")).getJSONObject("location").get("lat");
				      System.out.println("经度："+lng);  
					  System.out.println("维度："+lat);  
					  pd.put("lng", lng+"");
					  pd.put("lat", lat+"");
		        } 
		        out.close();    
		        br.close(); 
 				return pd;  
			} finally {  
 				 System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");  
			}  
		}
		
		
		
//		/**
//		* 获取指定金纬度获取省市区街道 
// 		* @param ip
//		* @return
//		 * @throws MalformedURLException 
//		*/
//		public static PageData getPcaXY(String lng,String lat)  throws IOException, JSONException {
//			    PageData pd=new PageData();
//			    String ak = "KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9";
//				URL url = new URL("https://api.map.baidu.com/geocoder/v2/?ak="+ak+"&callback=renderReverse&location="+lat+","+lng+"&output=json&pois=1"); //jsonp格式
//				//URL url = new URL("https://api.map.baidu.com/geocoder/v2/?ak="+ak+"&callback_type=json&location="+lat+","+lng+"&output=json&pois=1"); JSON格式
//	 		    InputStream is = url.openStream();  
//			    try {  
//			      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));  
//			      String jsonText = readAll(rd);  
//			      System.out.println(jsonText);
//			      String status=jsonText.substring(jsonText.indexOf("status")+8, jsonText.lastIndexOf("result")-2)+"";
//			      System.out.println("status="+status);
// 				  if(status.equals("0")){
//					  jsonText=jsonText.substring(jsonText.indexOf("addressComponent")+18, jsonText.lastIndexOf("pois")-2);
////	   			      System.out.println(jsonText);
//	 			      JSONObject json = new JSONObject(jsonText);  
//	 			      String province_name=json.get("province")+"";
//				      String city_name=json.get("city")+"";
//				      String area_name=json.get("district")+"";
//				      String street=json.get("street")+"";
////	 			      System.out.println("province_name："+province_name);  
////					  System.out.println("city_name："+city_name);  
////					  System.out.println("area_name："+area_name);  
////					  System.out.println("street："+street); 
//					  pd.put("province_name", province_name+"");
//					  pd.put("city_name", city_name+"");
//					  pd.put("area_name", area_name+"");
//					  pd.put("street", street+"");
//					  pd.put("address", city_name+area_name+street);
//				  }else{
//					  pd.put("province_name",  "");
//					  pd.put("city_name",  "");
//					  pd.put("area_name",  "");
//					  pd.put("street",  "");
//					  pd.put("address",  "");
//				  }
// 			      return pd;  
//			    } finally {  
//			      is.close();  
//			     // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");  
//			    }  
//		  }
		/**
		 * 获取指定金纬度获取省市区街道 
		 * @param ip
		 * @return
		 * @throws MalformedURLException 
		 */
		public static PageData getPcaXY(String lng,String lat)  throws IOException, JSONException {
			PageData pd=new PageData();
    			try {  
 				Socket s = new Socket("api.map.baidu.com",80);    
		        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));    
		        OutputStream  out= s.getOutputStream();   
		        //https://api.map.baidu.com/highacciploc/v1?qcip=&qterm=pc&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&coord=bd09ll
		        StringBuffer sb = new StringBuffer("GET /geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&callback_type=json&location="+lat+","+lng+"&output=json&pois=1 HTTP/1.1\r\n");    
		        sb.append("User-Agent: Java/1.6.0_20\r\n");    
		        sb.append("Host: api.map.baidu.com:80\r\n");    
		        sb.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");    
		        sb.append("Connection: Close\r\n");    
		        sb.append("\r\n");    
		        out.write(sb.toString().getBytes());    
		        String jsonText ="";  
		        String tmp = "";    
		        while((tmp = br.readLine())!=null){    
//		            System.out.println(tmp); 
		            if(tmp.contains("location")){
		            	jsonText=tmp; 
		            }
 		        }    
  		        System.out.println(jsonText); 
				String status=jsonText.substring(jsonText.indexOf("status")+8, jsonText.lastIndexOf("result")-2)+"";
 				if(status.equals("0")){
					jsonText=jsonText.substring(jsonText.indexOf("addressComponent")+18, jsonText.lastIndexOf("pois")-2);
//	   			      System.out.println(jsonText);
					JSONObject json = new JSONObject(jsonText);  
					String province_name=json.get("province")+"";
					String city_name=json.get("city")+"";
					String area_name=json.get("district")+"";
					String street=json.get("street")+"";
//	 			      System.out.println("province_name："+province_name);  
//					  System.out.println("city_name："+city_name);  
//					  System.out.println("area_name："+area_name);  
//					  System.out.println("street："+street); 
					pd.put("province_name", province_name+"");
					pd.put("city_name", city_name+"");
					pd.put("area_name", area_name+"");
					pd.put("street", street+"");
					pd.put("address", city_name+area_name+street);
				}else{
					pd.put("province_name",  "");
					pd.put("city_name",  "");
					pd.put("area_name",  "");
					pd.put("street",  "");
					pd.put("address",  "");
				}
				return pd;  
			} finally {  
 				// System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");  
			}  
		}
		
		/**
		 * 获取指定IP对应的经纬度  第二种方法
		 */
		 private static String readAll(Reader rd) throws IOException {  
		    StringBuilder sb = new StringBuilder();  
		    int cp;  
		    while ((cp = rd.read()) != -1) {  
		      sb.append((char) cp);  
		    }  
		    return sb.toString();  
		  }  
		  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {  
		    InputStream is = new URL(url).openStream();  
		    try {  
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));  
		      String jsonText = readAll(rd);  
		      JSONObject json = new JSONObject(jsonText);  
		      return json;  
		    } finally {  
		      is.close();  
		     // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");  
		    }  
		  } 
		  
		  
		
		/**
		 * 计算距离
		 */
 		private static final double EARTH_RADIUS = 6378137;// 地球半径  
	    private static double rad(double d) {
		      return d * Math.PI / 180.0;
	    
	    }
		public static double getResult(double lat1,double lat2,double lng1,double lng2) {
			   double radLat1 = rad(lat1);  
		       double radLat2 = rad(lat2);  
		       double a = radLat1 - radLat2;  
		       double b = rad(lng1) - rad(lng2);  
		       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
		       s = s * EARTH_RADIUS;  
		       s = Math.round(s * 10000) / 10000;  
		       return s;
		}
		
		
		/**
		 * 判断当前IP是否已经被禁用
		 * @param ip
		 * @return
		 */
		public static boolean NowIPisJY(String ip){
			//禁用ip
//			String ip=Pay_historyController.getIp(this.getRequest());
 			System.out.println("ip========="+ip);
			String jinyong = Tools.readTxtFile(Const.JYIP);	
			boolean flag=true;
			//读取短信1配置
			if(null != jinyong && !"".equals(jinyong)){
				String[] jinyongip= jinyong.split(",");
				for (int i = 0; i < jinyongip.length; i++) {
					if(ip.equals(jinyongip[i].trim())){
						flag=false;
						break;
					}
				}
			}
 			System.out.println(flag);
 			return flag;
		}
		
		
		
		/**
		 * 保留两位小数,去零
		 */
		public static String baoliuTwoDouble(String str){
			DecimalFormat    df   = new DecimalFormat("######0.00"); 
		    int n=str.length();
		    int m=str.indexOf(".")+1;
		    if(n-m >2){
		    	str=df.format(Double.parseDouble(str));
		    }
  		    boolean flag=true;
		    while (flag) {
		    	n=str.length();
 				if(n >1 && (str.substring(n-1, n).equals("0") || str.substring(n-1, n).equals("."))){
 					str=str.substring(0, n-1);
				}else{
  					flag=false;
				}
			}
  			return str;
		}
		
		
		/*
		 * 主方法
		 */
 		public static void main(String[] args) throws IOException, JSONException {  
// 			System.out.println(isChinaPhoneLegal("18379455545"));
// 			Map<String, String> map =getLatAndLngByAddress("浙江省杭州市余杭区五常大道210号");
// 			System.out.println(StringUtil.getMoveFloatLastZero("2000")); 
//		 	PageData pd =getPcaXY("120.124885","30.342306");
//		 	System.out.println(pd.toString());
		    //这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm  
//		    String ip =getIp();  
//		    String url = "http://api.map.baidu.com/location/ip?ak=32f38c9491f2da9eb61106aaab1e9739&ip="+ip+"&coor=bd09ll";   
// 		    JSONObject json = readJsonFromUrl(url);  
//		    System.out.println(json.toString());  
//		    System.out.println("经度："+((JSONObject) json.get("content")).getJSONObject("point").get("x"));  
//		    System.out.println("维度："+((JSONObject) json.get("content")).getJSONObject("point").get("y"));  
//		    String city =(String) ((JSONObject) json.get("content")).getJSONObject("address_detail").get("city");  
//		    city = city.replace("市","");  
// 		    System.out.println(city);  
// 			System.out.println(isPhoneLegal("18436993226"));
// 			List  list = new ArrayList (); 
// 			list.add(0, "aaa"); 
// 			list.add(1, "bbbb"); 
// 			list.add(1, "ccccc"); 
// 			list.add(3, "ddddd"); 
// 			System.out.println(list.get(2)); 
// 			
// 			 Map<String,Double> map=new HashMap<String,Double>();    
// 			 map.put("1", 99.00009);    
// 			 map.put("2", 100.00);    
// 			 map.put("3", 33.00);
// 			List<Entry<String,Double>> list =new ArrayList<Entry<String,Double>>(map.entrySet());
// 			Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
//			    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
//			        return (int)(o2.getValue() - o1.getValue());//降序
//			    }
//			});
// 			for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.get(i).getValue());
//			}
 			System.out.println(getIp());
  			
		} 
 		
 		
 		
 		 /**
 	     * Http Get 请求
 	     * @param urlString
 	     * @return
 	     */
 	    public static String httpGet(String urlString) {
 	        String result = "";
 	        try {
 	            URL url = new URL(urlString);
 	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 	            conn.setRequestMethod("GET");
 	            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
 		
 		
 		
 		
 		//路径
 		public String getCurrentPath(){  
 	       //取得根目录路径  
 	       String rootPath=getClass().getResource("/").getFile().toString();  
 	       //当前目录路径  
 	       String currentPath1=getClass().getResource(".").getFile().toString();  
 	       String currentPath2=getClass().getResource("").getFile().toString();  
 	       //当前目录的上级目录路径  
 	       String parentPath=getClass().getResource("../").getFile().toString();  
 	          
 	       return rootPath;         
 	   
 	   }
		 
}
