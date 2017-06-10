package com.tianer.entity.system;

import java.util.Map;

public class AppValidation {
	private String required;//是否必填 true/false
	private Integer maxlength;//最大长度
	private Integer minlength;//最小长度
	private String min;//最小值
	private String max;//最大值
	private String id;
	private Map<String,String> enumeration;//枚举值
	private String defaultValue;//默认值
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public Integer getMaxlength() {
		return maxlength;
	}
	public void setMaxlength(Integer maxlength) {
		this.maxlength = maxlength;
	}
	public Integer getMinlength() {
		return minlength;
	}
	public void setMinlength(Integer minlength) {
		this.minlength = minlength;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, String> getEnumeration() {
		return enumeration;
	}
	public void setEnumeration(Map<String, String> enumeration) {
		this.enumeration = enumeration;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
