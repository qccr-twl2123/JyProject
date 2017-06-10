package com.tianer.entity.html;

/**
 * 
* 类名称：HtmlUser   
* 类描述： H5的用户信息  
* 创建人：魏汉文  
* 创建时间：2016年8月29日 下午3:36:34
 */
public class HtmlUser {
     private  String member_id;
     private  String wxunionid;//微信唯一标示ID
     private  String open_id;//微信唯一标示ID
     private String province_name;
     private String city_name;
     private String area_name;
     private String address;
     private String lng;//当前所在位置的经纬度
     private String lat;
     private String wechat_number;//微信号
 
	public String getWechat_number() {
		return wechat_number;
	}
	public void setWechat_number(String wechat_number) {
		this.wechat_number = wechat_number;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getWxunionid() {
		return wxunionid;
	}
	public void setWxunionid(String wxunionid) {
		this.wxunionid = wxunionid;
	}
      
}
