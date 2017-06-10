/**
 * 
 */
package com.tianer.entity.storepc.liangqin;

/**
 * 类名称: Order 
 * 类描述: 订单信息的实体类
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-12 下午1:58:50	
 * 版本号: v1.0
 */
public class Order {
	
	//private String out_trade_no;
	private String look_number;
	private String sale_money;
	private String discount_money;
	private String actual_money;
	private String pay_type;
	private String get_integral;
	//private String pay_phone;
	private String store_operator_id;
	private String payer_id;
	private String store_shift_id;
	//private String address_type;
	private String remark;
	private String createtime;
	private String order_id;
	private String order_status;
	private String desk_no;
	private String no_discount_money;
	private String store_id;
	
	public String getNo_discount_money() {
		return no_discount_money;
	}
	public void setNo_discount_money(String no_discount_money) {
		this.no_discount_money = no_discount_money;
	}
//	public String getOut_trade_no() {
//		return out_trade_no;
//	}
//	public void setOut_trade_no(String out_trade_no) {
//		this.out_trade_no = out_trade_no;
//	}
	public String getLook_number() {
		return look_number;
	}
	public void setLook_number(String look_number) {
		this.look_number = look_number;
	}
	public String getSale_money() {
		return sale_money;
	}
	public void setSale_money(String sale_money) {
		this.sale_money = sale_money;
	}
	public String getDiscount_money() {
		return discount_money;
	}
	public void setDiscount_money(String discount_money) {
		this.discount_money = discount_money;
	}
	public String getActual_money() {
		return actual_money;
	}
	public void setActual_money(String actual_money) {
		this.actual_money = actual_money;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getGet_integral() {
		return get_integral;
	}
	public void setGet_integral(String get_integral) {
		this.get_integral = get_integral;
	}
//	public String getPay_phone() {
//		return pay_phone;
//	}
//	public void setPay_phone(String pay_phone) {
//		this.pay_phone = pay_phone;
//	}
	public String getStore_operator_id() {
		return store_operator_id;
	}
	public void setStore_operator_id(String store_operator_id) {
		this.store_operator_id = store_operator_id;
	}
	public String getPayer_id() {
		return payer_id;
	}
	public void setPayer_id(String payer_id) {
		this.payer_id = payer_id;
	}
	public String getStore_shift_id() {
		return store_shift_id;
	}
	public void setStore_shift_id(String store_shift_id) {
		this.store_shift_id = store_shift_id;
	}
//	public String getAddress_type() {
//		return address_type;
//	}
//	public void setAddress_type(String address_type) {
//		this.address_type = address_type;
//	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getDesk_no() {
		return desk_no;
	}
	public void setDesk_no(String desk_no) {
		this.desk_no = desk_no;
	}
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

}
