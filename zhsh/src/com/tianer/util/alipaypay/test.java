package com.tianer.util.alipaypay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alipay.api.internal.util.AlipaySignature;
import com.tianer.controller.base.BaseController;

public class test {

    public static void main(String[] args) throws Exception {
    	test t=new test();
    	t.Apipay();
      }
    
    public void Apipay() throws Exception{ 
     	String inforStr=AlipayConfig.buildAuthInfoMap();
     	System.out.println(inforStr);
     }
    
     
}
