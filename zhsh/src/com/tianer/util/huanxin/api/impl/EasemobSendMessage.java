package com.tianer.util.huanxin.api.impl;

import com.tianer.util.huanxin.api.SendMessageAPI;
import com.tianer.util.huanxin.comm.constant.HTTPMethod;
import com.tianer.util.huanxin.comm.helper.HeaderHelper;
import com.tianer.util.huanxin.comm.wrapper.BodyWrapper;
import com.tianer.util.huanxin.comm.wrapper.HeaderWrapper;

 
public class EasemobSendMessage extends EasemobRestAPI implements SendMessageAPI {
    private static final String ROOT_URI = "/messages";

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object sendMessage(Object payload) {
        String  url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        BodyWrapper body = (BodyWrapper) payload;

        return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
    }
}
