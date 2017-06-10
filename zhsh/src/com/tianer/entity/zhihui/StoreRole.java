package com.tianer.entity.zhihui;

import java.util.Map;

import com.tianer.util.PageData;

//pc端商家登陆权限管理
public class StoreRole {
		private String store_id;
		private String oprator_name;
		private String store_name;
		private String login_id;
		private String password;
		private String phone;
		private String type;//登陆类型：1-商家，2-操作员
		private Map<String,Object> map;
		
		public String getStore_id() {
			return store_id;
		}
		public String getOprator_name() {
			return oprator_name;
		}
		public void setOprator_name(String oprator_name) {
			this.oprator_name = oprator_name;
		}
		public String getStore_name() {
			return store_name;
		}
		public void setStore_name(String store_name) {
			this.store_name = store_name;
		}
		@Override
		public String toString() {
			return "StoreRole [store_id=" + store_id + ", login_id=" + login_id
					+ ", password=" + password + ", phone=" + phone + ", type="
					+ type + ", map=" + map + "]";
		}
		public void setStore_id(String store_id) {
			this.store_id = store_id;
		}
		public String getLogin_id() {
			return login_id;
		}
		public void setLogin_id(String login_id) {
			this.login_id = login_id;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Map<String, Object> getMap() {
			return map;
		}
		public void setMap(Map<String, Object> map) {
			this.map = map;
		}
		
}
