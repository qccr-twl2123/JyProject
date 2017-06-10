package com.tianer.entity.app;



/**
* 类名称:Order
* 描述:生成订单时，一次性付款的订单记录，谨在此应用 
* 创建人 :ckl
* 创建时间 :2015年11月16日
*/
public class Order {
	
	private String order_id;  //唯一编号
	private String orderid;  //订单id
	private String orderPrice; //商品价格
	private String sendTime; //送货时间
	private String freight;  //运费
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	
	
	
	

}
