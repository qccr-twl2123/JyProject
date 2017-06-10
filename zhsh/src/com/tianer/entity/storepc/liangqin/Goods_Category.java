/**
 * 
 */
package com.tianer.entity.storepc.liangqin;

/**
 * 类名称: Goods_Category 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-23 上午10:47:19	
 * 版本号: v1.0
 */
public class Goods_Category {
	private String goods_category_id;
	private String name;
	private String category_parent_id;
	private int sort;
	
	public String getGoods_category_id() {
		return goods_category_id;
	}
	public void setGoods_category_id(String goods_category_id) {
		this.goods_category_id = goods_category_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory_parent_id() {
		return category_parent_id;
	}
	public void setCategory_parent_id(String category_parent_id) {
		this.category_parent_id = category_parent_id;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}


}
