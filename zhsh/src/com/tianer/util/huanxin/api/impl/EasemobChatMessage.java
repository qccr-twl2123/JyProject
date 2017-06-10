package com.tianer.util.huanxin.api.impl;

import com.tianer.util.huanxin.api.ChatMessageAPI;
import com.tianer.util.huanxin.comm.constant.HTTPMethod;
import com.tianer.util.huanxin.comm.helper.HeaderHelper;
import com.tianer.util.huanxin.comm.wrapper.HeaderWrapper;
import com.tianer.util.huanxin.comm.wrapper.QueryWrapper;

 

public class EasemobChatMessage extends EasemobRestAPI implements ChatMessageAPI {

    private static final String ROOT_URI = "chatmessages";

    public Object exportChatMessages(Long limit, String cursor, String query) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        QueryWrapper queryWrapper = QueryWrapper.newInstance().addLimit(limit).addCursor(cursor).addQueryLang(query);

        return getInvoker().sendRequest(HTTPMethod.METHOD_DELETE, url, header, null, queryWrapper);
    }

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }
}
