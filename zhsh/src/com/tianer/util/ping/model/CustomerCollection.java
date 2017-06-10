package com.tianer.util.ping.model;

import java.util.HashMap;
import java.util.Map;

import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;

public class CustomerCollection extends PingppCollection<Customer> {
	
	/*
	 * 添加新用户
	 */
	public void addCustomer(){
		Map<String,Object> params = new HashMap<String,Object>();
		Customer c=new Customer();
		try {
			params.put("", "");
			 c.create(params);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException | ChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
