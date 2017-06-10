package com.tianer.util;

 
import java.util.List;
 

/**
 * 
* 类名称：Distance   
* 类描述：   计算距离
* 创建人：魏汉文  
* 创建时间：2016年6月23日 下午2:49:36
 */
public class Distance {
	
 
	
	private static final double EARTH_RADIUS = 6378137;// 地球半径  
    private static double rad(double d) {
	      return d * Math.PI / 180.0;
    
    }
	
	//计算距离
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
    
	//计算经纬度之间的距离--每一个纬度/纬度的距离是20000千米/180度≈111千米/度。
//	public static String getDistanceUser(List<PageData> userlist,String lng,String lat){
//			double r = 6378137; // 地球半径  
//			double dlng = Double.parseDouble(lng);
//			double dlat = Double.parseDouble(lat);
//			double blng,alat,s;
//			double result = 0.0;
//			String user_id="";
//			System.out.println("经度："+lng + ":"+"纬度："+lat);
// 			for(int i=0;i<userlist.size();i++){
// 				PageData data = userlist.get(i);
// 				double ulng = Double.parseDouble(data.getString("longitude"));
//				double ulat = Double.parseDouble(data.getString("latitude"));
//				double a = getResult(dlat,ulat,dlng,ulng);
//				if(i==0){
//					result = a;
//					user_id = data.getString("user_id");
//				}
//				if(a<result){
//					result = a;
//					user_id = data.getString("user_id");
//				}
// 		}
// 		return user_id;
//	}

	
	
	public static void main(String[] args) {
		 double s=getResult(30.229005,30.235328,120.255506,120.261882);
		 System.out.println(s);
	}
}
