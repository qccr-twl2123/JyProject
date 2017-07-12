package com.tianer.util.wxpay;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付通道
 * @author Administrator
 *
 */
public class WXPayPath {

    private WXPay wxpay;
    private WXPayConfigImpl config;
 
    public WXPayPath() throws Exception {
        config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config);
    }

    /**
     * sign验签
     * @return
     * @throws Exception 
     */
    public  boolean YanQian(String xmlStr) throws Exception{
     	return WXPayUtil.isSignatureValid(xmlStr, config.getKey());
    }
    
    
    
//    public void doReport() {
//        HashMap<String, String> data = new HashMap<String, String>();
//        data.put("interface_url", "20160822");
//        data.put("bill_type", "ALL");
//    }

    /**
     * 小测试
     */
    public  void test001() {
        String xmlStr="<xml><return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<return_msg><![CDATA[OK]]></return_msg>\n" +
                "<appid><![CDATA[wx273fe72f2db863ed]]></appid>\n" +
                "<mch_id><![CDATA[1228845802]]></mch_id>\n" +
                "<nonce_str><![CDATA[lCXjx3wNx45HfTV2]]></nonce_str>\n" +
                "<sign><![CDATA[68D7573E006F0661FD2A77BA59124E87]]></sign>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<openid><![CDATA[oZyc_uPx_oed7b4q1yKmj_3M2fTU]]></openid>\n" +
                "<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
                "<trade_type><![CDATA[NATIVE]]></trade_type>\n" +
                "<bank_type><![CDATA[CFT]]></bank_type>\n" +
                "<total_fee>1</total_fee>\n" +
                "<fee_type><![CDATA[CNY]]></fee_type>\n" +
                "<transaction_id><![CDATA[4008852001201608221983528929]]></transaction_id>\n" +
                "<out_trade_no><![CDATA[20160822162018]]></out_trade_no>\n" +
                "<attach><![CDATA[]]></attach>\n" +
                "<time_end><![CDATA[20160822202556]]></time_end>\n" +
                "<trade_state><![CDATA[SUCCESS]]></trade_state>\n" +
                "<cash_fee>1</cash_fee>\n" +
                "</xml>";
        try {
            System.out.println(xmlStr);
            System.out.println("+++++++++++++++++");
            System.out.println(WXPayUtil.isSignatureValid(xmlStr, config.getKey()));
             Map<String, String> hm = WXPayUtil.xmlToMap(xmlStr);
            System.out.println("+++++++++++++++++");
            System.out.println(hm);
            System.out.println(hm.get("attach").length());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    

    public static void main(String[] args) throws Exception {
         System.out.println("--------------->");
         WXPayPath dodo = new WXPayPath();
         dodo.test001();
          System.out.println("<---------------"); // wx2016112510573077
    }

 
}
