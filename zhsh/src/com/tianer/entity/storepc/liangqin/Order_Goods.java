/**
 * 
 */
package com.tianer.entity.storepc.liangqin;

/**
 * 类名称: Order_Goods 
 * 类描述: 订单关联商品信息表
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-14 下午2:15:49	
 * 版本号: v1.0
 */
public class Order_Goods {
	private String order_goods_id;
	private String goods_id;
	private String order_id;
	private String shop_number;
	private String price;
	private String fk_store_id;
	
	public String getFk_store_id() {
		return fk_store_id;
	}
	public void setFk_store_id(String fk_store_id) {
		this.fk_store_id = fk_store_id;
	}
	public String getOrder_goods_id() {
		return order_goods_id;
	}
	public void setOrder_goods_id(String order_goods_id) {
		this.order_goods_id = order_goods_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getShop_number() {
		return shop_number;
	}
	public void setShop_number(String shop_number) {
		this.shop_number = shop_number;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}


}
