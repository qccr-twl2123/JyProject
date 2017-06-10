/**
 * 
 */
package com.tianer.entity.storepc.liangqin;

import java.util.List;

/**
 * 类名称: Store 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-14 下午1:31:24	
 * 版本号: v1.0
 */
public class Store {
	private String store_id;
	private String store_name;
	private String registertel_phone;
	private String password;
	private String store_vip_id;
	private String merchant_level;
	private String logintime;
	private String integral_rate;
	private String service_rate;
	private String withdraw_rate;
	private String lowest_score;
	private String remind_integral;
	private String sort_score;
	private String complex_score;
	private String recharge_times;
	private String transaction_times;
	private String withdraw_times;
	private String zan_times;
	private String bigtype_max;
	private String smalltype_min;
	private String store_file_id;
	private String check_status;
	private String merchant_status;
	private String operate_id;
	private String registertime;
	private String pictrue_url;
	private String latitude;
	private String islogin;
	public String getIslogin() {
		return islogin;
	}
	public void setIslogin(String islogin) {
		this.islogin = islogin;
	}
	public String getRegistertime() {
		return registertime;
	}
	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}
	public String getPictrue_url() {
		return pictrue_url;
	}
	public void setPictrue_url(String pictrue_url) {
		this.pictrue_url = pictrue_url;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	private String longitude;
	private List<Store_Union> store_UnionList; 

	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	
	public String getRegistertel_phone() {
		return registertel_phone;
	}
	public void setRegistertel_phone(String registertel_phone) {
		this.registertel_phone = registertel_phone;
	}

	public String getCheck_status() {
		return check_status;
	}
	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}
	public String getStore_vip_id() {
		return store_vip_id;
	}
	public void setStore_vip_id(String store_vip_id) {
		this.store_vip_id = store_vip_id;
	}
	public String getMerchant_status() {
		return merchant_status;
	}
	public void setMerchant_status(String merchant_status) {
		this.merchant_status = merchant_status;
	}
	public String getMerchant_level() {
		return merchant_level;
	}
	public void setMerchant_level(String merchant_level) {
		this.merchant_level = merchant_level;
	}
	
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	public String getIntegral_rate() {
		return integral_rate;
	}
	public void setIntegral_rate(String integral_rate) {
		this.integral_rate = integral_rate;
	}
	public String getService_rate() {
		return service_rate;
	}
	public void setService_rate(String service_rate) {
		this.service_rate = service_rate;
	}
	public String getWithdraw_rate() {
		return withdraw_rate;
	}
	public void setWithdraw_rate(String withdraw_rate) {
		this.withdraw_rate = withdraw_rate;
	}
	public String getLowest_score() {
		return lowest_score;
	}
	public void setLowest_score(String lowest_score) {
		this.lowest_score = lowest_score;
	}
	public String getRemind_integral() {
		return remind_integral;
	}
	public void setRemind_integral(String remind_integral) {
		this.remind_integral = remind_integral;
	}
	public String getSort_score() {
		return sort_score;
	}
	public void setSort_score(String sort_score) {
		this.sort_score = sort_score;
	}
	public String getComplex_score() {
		return complex_score;
	}
	public void setComplex_score(String complex_score) {
		this.complex_score = complex_score;
	}
	public String getRecharge_times() {
		return recharge_times;
	}
	public void setRecharge_times(String recharge_times) {
		this.recharge_times = recharge_times;
	}
	public String getTransaction_times() {
		return transaction_times;
	}
	public void setTransaction_times(String transaction_times) {
		this.transaction_times = transaction_times;
	}
	public String getWithdraw_times() {
		return withdraw_times;
	}
	public void setWithdraw_times(String withdraw_times) {
		this.withdraw_times = withdraw_times;
	}
	public String getZan_times() {
		return zan_times;
	}
	public void setZan_times(String zan_times) {
		this.zan_times = zan_times;
	}
	public String getBigtype_max() {
		return bigtype_max;
	}
	public void setBigtype_max(String bigtype_max) {
		this.bigtype_max = bigtype_max;
	}
	public String getSmalltype_min() {
		return smalltype_min;
	}
	public void setSmalltype_min(String smalltype_min) {
		this.smalltype_min = smalltype_min;
	}
	public String getOperate_id() {
		return operate_id;
	}
	public void setOperate_id(String operate_id) {
		this.operate_id = operate_id;
	}
	
	public List<Store_Union> getStore_UnionList() {
		return store_UnionList;
	}
	public void setStore_UnionList(List<Store_Union> store_UnionList) {
		this.store_UnionList = store_UnionList;
	}
	/**
	 * @return the store_file_id
	 */
	public String getStore_file_id() {
		return store_file_id;
	}
	/**
	 * @param store_file_id the store_file_id to set
	 */
	public void setStore_file_id(String store_file_id) {
		this.store_file_id = store_file_id;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
