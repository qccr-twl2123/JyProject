package com.tianer.entity.zhihui;

public class Qx {
	private String add;
	private String delete;
	private String edit;
	private String look;
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public String getEdit() {
		return edit;
	}
	public void setEdit(String edit) {
		this.edit = edit;
	}
	public String getLook() {
		return look;
	}
	public void setLook(String look) {
		this.look = look;
	}
	@Override
	public String toString() {
		return "Qx [add=" + add + ", delete=" + delete + ", edit=" + edit
				+ ", look=" + look + "]";
	}
	
	
}
