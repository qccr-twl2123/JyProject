package com.tianer.util.huanxin.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tianer.util.huanxin.api.AuthTokenAPI;
import com.tianer.util.huanxin.comm.body.AuthTokenBody;
import com.tianer.util.huanxin.comm.constant.HTTPMethod;
import com.tianer.util.huanxin.comm.helper.HeaderHelper;
import com.tianer.util.huanxin.comm.wrapper.BodyWrapper;
import com.tianer.util.huanxin.comm.wrapper.HeaderWrapper;

 

 

public class EasemobAuthToken extends EasemobRestAPI implements AuthTokenAPI{
	
	public static final String ROOT_URI = "/token";
	
	private static final Logger log = LoggerFactory.getLogger(EasemobAuthToken.class);
	
	@Override
	public String getResourceRootURI() {
		return ROOT_URI;
	}

	public Object getAuthToken(String clientId, String clientSecret) {
		String url = getContext().getSeriveURL() + getResourceRootURI();
		BodyWrapper body = new AuthTokenBody(clientId, clientSecret);
		HeaderWrapper header = HeaderHelper.getDefaultHeader();
		
		return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
	}
}
