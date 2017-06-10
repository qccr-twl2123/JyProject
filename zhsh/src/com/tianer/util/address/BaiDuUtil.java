package com.tianer.util.address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.tianer.util.PageData;

 

/**
 * 
* 类名称：StringUtil   
* 类描述：   字符串相关方法
* 创建人：魏汉文  
* 创建时间：2016年5月30日 下午2:51:57
 */
public class BaiDuUtil {
	  
		private static String ak="KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9";
		
		/**
		 * 百度的普通定位
		 * 获取定位的经纬度
 		 */
		public static PageData getIPXYByPuTong(PageData pd)  throws IOException, JSONException {
   			try {  
  				String url="/location/ip?ak="+ak+"&coor=bd09ll";
  				Socket s = new Socket("api.map.baidu.com",80);    
		        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(),"utf-8"));    
		        OutputStream  out= s.getOutputStream();   
 		        StringBuffer sb = new StringBuffer("GET "+url+" HTTP/1.1\r\n");    
		        sb.append("User-Agent: Java/1.6.0_20\r\n");    
		        sb.append("Host: api.map.baidu.com:80\r\n");    
		        sb.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");    
		        sb.append("Connection: Close\r\n");    
		        sb.append("\r\n");    
		        out.write(sb.toString().getBytes()); 
		        String jsonText ="";  
		        String tmp = "";    
		        while((tmp = br.readLine())!=null){    
		        	jsonText=tmp; 
 		        } 
//		        System.out.println(jsonText);
    		    JSONObject json = new JSONObject(jsonText);
   		        String city=(String) ((JSONObject) json.get("content")).getJSONObject("address_detail").get("city");
   		        String province=(String) ((JSONObject) json.get("content")).getJSONObject("address_detail").get("province");
   		        String lng=(String) ((JSONObject) json.get("content")).getJSONObject("point").get("x");
   		        String lat=(String) ((JSONObject) json.get("content")).getJSONObject("point").get("y");
   		        pd.put("province_name", province);
   		        pd.put("city_name", city);
   		        pd.put("lng", lng);
   		        pd.put("lat", lat);
    		    out.close();    
		        br.close();
		        s.close();
//		        System.out.println(pd.toString());
 				return pd;  
			} finally {  
// 				 System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");  
			}  
		}
		
	
		/**
		 * 百度的精确定位
		 * 获取定位的经纬度
 		 */
		public static PageData getIPXY(PageData pd)  throws IOException, JSONException {
   			try {  
  				String url="/highacciploc/v1?qcip=&callback_type=jsonp&qterm=pc&ak="+ak+"&coord=bd09ll";
 				Socket s = new Socket("api.map.baidu.com",80);    
		        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));    
		        OutputStream  out= s.getOutputStream();   
 		        StringBuffer sb = new StringBuffer("GET "+url+" HTTP/1.1\r\n");    
		        sb.append("User-Agent: Java/1.6.0_20\r\n");    
		        sb.append("Host: api.map.baidu.com:80\r\n");    
		        sb.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");    
		        sb.append("Connection: Close\r\n");    
		        sb.append("\r\n");    
		        out.write(sb.toString().getBytes());    
		        String jsonText ="";  
		        String tmp = "";    
		        while((tmp = br.readLine())!=null){    
 		            if(tmp.contains("location")){
		            	jsonText=tmp; 
		            }
 		        }    
//  		    System.out.println(jsonText); 
  		        JSONObject json = new JSONObject(jsonText);
  		        int resultstatus=(int) ((JSONObject) json.get("result")).get("error");
		        if(resultstatus == 161){
			    	  double lng=(double) ((JSONObject) json.get("content")).getJSONObject("location").get("lng");
				      double lat=(double) ((JSONObject) json.get("content")).getJSONObject("location").get("lat");
  					  pd.put("lng", lng+"");
					  pd.put("lat", lat+"");
					  pd=getPcaXY(pd,lng,lat);
		        }else{
		        	//进行普通定位
		        	pd=getIPXYByPuTong(pd);
		        }
		        out.close();    
		        br.close();
		        s.close();
 			}catch(Exception e){
				e.printStackTrace();
			}finally {  
 // 				 System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");  
			}  
   			return pd;  
		}
		
		
 
		/**
		 * 获取指定金纬度获取省市区街道 
		 * @param ip
		 * @return
		 * @throws MalformedURLException 
		 */
		public static PageData getPcaXY(PageData pd,double lng,double lat)  throws IOException, JSONException {
     			try {  
     			String url="/geocoder/v2/?ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9&callback_type=json&location="+lat+","+lng+"&output=json&pois=1";
 				Socket s = new Socket("api.map.baidu.com",80);    
		        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));    
		        OutputStream  out= s.getOutputStream();   
 		        StringBuffer sb = new StringBuffer("GET "+url+" HTTP/1.1\r\n");    
		        sb.append("User-Agent: Java/1.6.0_20\r\n");    
		        sb.append("Host: api.map.baidu.com:80\r\n");    
		        sb.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");    
		        sb.append("Connection: Close\r\n");    
		        sb.append("\r\n");    
		        out.write(sb.toString().getBytes());    
		        String jsonText ="";  
		        String tmp = "";    
		        while((tmp = br.readLine())!=null){    
 		            if(tmp.contains("location")){
		            	jsonText=tmp; 
		            }
 		        }    
				String status=jsonText.substring(jsonText.indexOf("status")+8, jsonText.lastIndexOf("result")-2)+"";
 				if(status.equals("0")){
					jsonText=jsonText.substring(jsonText.indexOf("addressComponent")+18, jsonText.lastIndexOf("pois")-2);
 					JSONObject json = new JSONObject(jsonText);  
					String province_name=json.get("province")+"";
					String city_name=json.get("city")+"";
					String area_name=json.get("district")+"";
					String street=json.get("street")+"";
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
 				out.close();    
 		        br.close();
 		        s.close();
				return pd;  
			} finally {  
 				// System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");  
			}  
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
 		
 	    
 	   /*
		 * 主方法
		 */
 		public static void main(String[] args) throws IOException, JSONException { 
 			getIPXY(new PageData());
   		} 
 		
 		 
		 
}
