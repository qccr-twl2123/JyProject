package com.tianer.util.huanxin.api;

 

import java.io.File;

import com.tianer.util.huanxin.comm.wrapper.BodyWrapper;
import com.tianer.util.huanxin.comm.wrapper.HeaderWrapper;
import com.tianer.util.huanxin.comm.wrapper.QueryWrapper;
import com.tianer.util.huanxin.comm.wrapper.ResponseWrapper;

public interface RestAPIInvoker {
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, BodyWrapper body, QueryWrapper query);
	ResponseWrapper uploadFile(String url, HeaderWrapper header, File file);
    ResponseWrapper downloadFile(String url, HeaderWrapper header, QueryWrapper query);
}
