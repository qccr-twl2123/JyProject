/**
 * 
 */
package com.tianer.entity.storepc.liangqin;

/**
 * 类名称: Store_Bankcard 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-25 下午3:44:21	
 * 版本号: v1.0
 */
public class Store_Bankcard {
	
	private String store_bankcard_id;
	private String account;
	private String bank_number;
	private String phone;
	private String isdefault;
	public String getBank_number() {
		return bank_number;
	}
	public void setBank_number(String bank_number) {
		this.bank_number = bank_number;
	}
	private String store_id;
	
	
	public String getStore_bankcard_id() {
		return store_bankcard_id;
	}
	public void setStore_bankcard_id(String store_bankcard_id) {
		this.store_bankcard_id = store_bankcard_id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}	
	

}
