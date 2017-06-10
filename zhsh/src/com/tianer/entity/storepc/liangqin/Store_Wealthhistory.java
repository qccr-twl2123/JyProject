/**
 * 
 */
package com.tianer.entity.storepc.liangqin;

/**
 * 类名称: Store_Wealthhistory 
 * 类描述: 商家财富历史记录表 
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-16 下午5:18:38	
 * 版本号: v1.0
 */
public class Store_Wealthhistory {

	private int store_wealthhistory_id;
	private String wealth_type;
	private String profit_type;
	private String number;
	private String store_id;
	private String store_operator_id;
	private String process_status;
	private String pay_type;
	private String last_wealth;
	private String createtime;
	
	
	
	public int getStore_wealthhistory_id() {
		return store_wealthhistory_id;
	}
	public void setStore_wealthhistory_id(int store_wealthhistory_id) {
		this.store_wealthhistory_id = store_wealthhistory_id;
	}
	public String getWealth_type() {
		return wealth_type;
	}
	public void setWealth_type(String wealth_type) {
		this.wealth_type = wealth_type;
	}
	public String getProfit_type() {
		return profit_type;
	}
	public void setProfit_type(String profit_type) {
		this.profit_type = profit_type;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getStore_operator_id() {
		return store_operator_id;
	}
	public void setStore_operator_id(String store_operator_id) {
		this.store_operator_id = store_operator_id;
	}
	public String getProcess_status() {
		return process_status;
	}
	public void setProcess_status(String process_status) {
		this.process_status = process_status;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getLast_wealth() {
		return last_wealth;
	}
	public void setLast_wealth(String last_wealth) {
		this.last_wealth = last_wealth;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	
	

}
