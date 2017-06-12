package com.tianer.util.huanxin.comm.body;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tianer.util.huanxin.comm.wrapper.BodyWrapper;



public class IMUserBody implements BodyWrapper {
	
	private String userName;
	
	private String password;
	
	private String newpassword;
	
	private String nickName;
	
	public IMUserBody(String userName, String password, String nickName) {
		super();
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
	}
	
	public IMUserBody(String password) {
		super();
 		this.newpassword = password;
 	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public ContainerNode<?> getBody() {
		return JsonNodeFactory.instance.objectNode().put("username", userName).put("password", password).put("nickname", nickName);
	}

	@Override
	public Boolean validate() {
		return StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password);
	}
	
	

}