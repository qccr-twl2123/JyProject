/**
 * 
 */
package com.tianer.entity.storepc.liangqin;

/**
 * 类名称: Store_Union 
 * 类描述: 联盟相关表
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-14 下午6:24:58	
 * 版本号: v1.0
 */
public class Store_Union{
	/**
	 * serialVersionUID: TODO
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private String store_union_id;
	private String name;
	private String starttime;
	private String endtime;
	private String invite_desc;
	private String fk_store_id;
	private String leader_store_id;
	private String type;
	private String union_status;
	private String addunion_time;
	private Store store;
	private Store_File store_file;
	
	
	
	
	
	
	
	

	public Store_File getStore_file() {
		return store_file;
	}
	public void setStore_file(Store_File store_file) {
		this.store_file = store_file;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStore_union_id() {
		return store_union_id;
	}
	public void setStore_union_id(String store_union_id) {
		this.store_union_id = store_union_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getInvite_desc() {
		return invite_desc;
	}
	public void setInvite_desc(String invite_desc) {
		this.invite_desc = invite_desc;
	}
	public String getFk_store_id() {
		return fk_store_id;
	}
	public void setFk_store_id(String fk_store_id) {
		this.fk_store_id = fk_store_id;
	}
	public String getLeader_store_id() {
		return leader_store_id;
	}
	public void setLeader_store_id(String leader_store_id) {
		this.leader_store_id = leader_store_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnion_status() {
		return union_status;
	}
	public void setUnion_status(String union_status) {
		this.union_status = union_status;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	/**
	 * @return the addunion_time
	 */
	public String getAddunion_time() {
		return addunion_time;
	}
	/**
	 * @param addunion_time the addunion_time to set
	 */
	public void setAddunion_time(String addunion_time) {
		this.addunion_time = addunion_time;
	}
	@Override
	public String toString() {
		return "Store_Union [store_union_id=" + store_union_id + ", name="
				+ name + ", starttime=" + starttime + ", endtime=" + endtime
				+ ", invite_desc=" + invite_desc + ", fk_store_id="
				+ fk_store_id + ", leader_store_id=" + leader_store_id
				+ ", type=" + type + ", union_status=" + union_status
				+ ", addunion_time=" + addunion_time + ", store=" + store + "]";
	}
	
	

}
