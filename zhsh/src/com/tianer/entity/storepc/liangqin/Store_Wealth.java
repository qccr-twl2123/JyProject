/**
 * 
 */
package com.tianer.entity.storepc.liangqin;

/**
 * 类名称: Store_Wealth 
 * 类描述: 商家财富表
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-14 上午1:21:15	
 * 版本号: v1.0
 */
public class Store_Wealth {
	private String store_wealth_id;
	private String wealth_type;
	private String now_wealth;
	private String frozen_wealth;
	private String store_id;

	public String getStore_wealth_id() {
		return store_wealth_id;
	}

	public void setStore_wealth_id(String store_wealth_id) {
		this.store_wealth_id = store_wealth_id;
	}

	public String getWealth_type() {
		return wealth_type;
	}

	public void setWealth_type(String wealth_type) {
		this.wealth_type = wealth_type;
	}

	public String getNow_wealth() {
		return now_wealth;
	}

	public void setNow_wealth(String now_wealth) {
		this.now_wealth = now_wealth;
	}

	public String getFrozen_wealth() {
		return frozen_wealth;
	}

	public void setFrozen_wealth(String frozen_wealth) {
		this.frozen_wealth = frozen_wealth;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}


}
