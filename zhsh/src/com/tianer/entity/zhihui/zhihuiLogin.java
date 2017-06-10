package com.tianer.entity.zhihui;

import java.util.Map;

public class zhihuiLogin {
	
	private String login_id;//登陆用户ID/登陆账号
	private String login_type;//登陆用户的角色id:0-管理员，1-服务商，2-业务员，3-操作员，4-城市经理，5-子公司，6-其他
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	private String menu_role_id;//登陆用户的角色id:99-管理员，1-服务商，2-业务员，其他的表示其他
	private String login_name;//用户名
	private String login_phone;//手机号码
	private String login_password;//登陆密码	
	private Map<String,Object> map;
	private String province_name;
 	private String city_name;
	private String area_name;//多个区域，用逗号分隔
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
	public String getLogin_phone() {
		return login_phone;
	}
	public void setLogin_phone(String login_phone) {
		this.login_phone = login_phone;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getMenu_role_id() {
		return menu_role_id;
	}
	public void setMenu_role_id(String menu_role_id) {
		this.menu_role_id = menu_role_id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
 
	public String getLogin_password() {
		return login_password;
	}
	public void setLogin_password(String login_password) {
		this.login_password = login_password;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	@Override
	public String toString() {
		return "zhihuiLogin [login_id=" + login_id + ", login_type="
				+ login_type + ", menu_role_id=" + menu_role_id
				+ ", login_name=" + login_name + ", login_phone=" + login_phone
				+ ", login_password=" + login_password + ", map=" + map
				+ ", province_name=" + province_name + ", city_name="
				+ city_name + ", area_name=" + area_name + "]";
	}
	
	
	
}
