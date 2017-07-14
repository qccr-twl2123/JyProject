package com.tianer.util.wxpay;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WXPayConfigImpl extends WXPayConfig{

    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl() throws Exception{
    	 String certPath = "C://wx/apiclient_cert.p12";
    	 InputStream certStream=null;
    	 try {
            File file = new File(certPath);
            certStream = new FileInputStream(file);
            this.certData = new byte[(int) file.length()];
            certStream.read(this.certData);
            certStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
     }

    public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
//        return "wxab8acb865bb1637e";
        return "wxad8522fd764ed46a";
    }

    public String getMchID() {
//        return "11473623";
        return "1358805002";
    }

    public  String getKey() {
//        return "2ab9071b06b9f739b950ddb41db2690d";
        return "dkhfJICFdjDY6dlJ5dw2JKLHJDfdhwjs";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}
