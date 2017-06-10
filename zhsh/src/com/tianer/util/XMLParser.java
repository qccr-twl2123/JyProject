package com.tianer.util;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;







import com.tianer.util.quartz.RefundOrderData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: rizenguo
 * Date: 2014/11/1
 * Time: 14:06
 */
public class XMLParser {

    /**
     * 从RefunQueryResponseString里面解析出退款订单数据
     * @param refundQueryResponseString RefundQuery API返回的数据
     * @return 因为订单数据有可能是多个，所以返回一个列表
     */
    public static List<RefundOrderData> getRefundOrderList(String refundQueryResponseString) throws IOException, SAXException, ParserConfigurationException {
        List list = new ArrayList();

        Map<String,Object> map = XMLParser.getMapFromXML(refundQueryResponseString);

       int count = Integer.parseInt((String) map.get("refund_count"));
       Util.log("count:" + count);

        if(count<1){
            return list;
        }

        RefundOrderData refundOrderData;

        for(int i=0;i<count;i++){
            refundOrderData = new RefundOrderData();

            refundOrderData.setOutRefundNo(Util.getStringFromMap(map,"out_refund_no_" + i,""));
            refundOrderData.setRefundID(Util.getStringFromMap(map,"refund_id_" + i,""));
            refundOrderData.setRefundChannel(Util.getStringFromMap(map,"refund_channel_" + i,""));
            refundOrderData.setRefundFee(Util.getIntFromMap(map,"refund_fee_" + i));
            refundOrderData.setCouponRefundFee(Util.getIntFromMap(map,"coupon_refund_fee_" + i));
            refundOrderData.setRefundStatus(Util.getStringFromMap(map,"refund_status_" + i,""));
            list.add(refundOrderData);
        }

        return list;
    }

    public static Map<String,Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is =  Util.getStringStream(xmlString);
        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;

    }

    
    public static void main(String[] args){
    	int i=0;
    	while(i<5){
    		try {
    			Thread.sleep(1000);
    		} catch (InterruptedException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		System.out.println(DateUtil.getTime()+"&&"+i++);
        	String xml = "<xml>"
    				+ "<appid><![CDATA[wxb4dc385f953b356e]]></appid>"//公众账号ID
    				+ "<bank_type><![CDATA[CCB_CREDIT]]></bank_type>"//付款银行
    				+ "<cash_fee><![CDATA[1]]></cash_fee>"//现金支付金额
    				+ "<fee_type><![CDATA[CNY]]></fee_type>"//货币种类
    				+ "<is_subscribe><![CDATA[Y]]></is_subscribe>"//是否关注公众账号
    				+ "<mch_id><![CDATA[1228442802]]></mch_id>"//商户号
    				+ "<nonce_str><![CDATA[1002477130]]></nonce_str>"//随机字符串
    				+ "<openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid>"//用户标识
    				+ "<out_trade_no><![CDATA[1da49c18e24c4629af5b642a1007b98d]]></out_trade_no>"//商户订单号
    				+ "<result_code><![CDATA[SUCCESS]]></result_code>"//业务结果
    				+ "<sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign>"//签名
    				+ "<time_end><![CDATA[20150324100405]]></time_end>"//支付完成时间
    				+ "<total_fee>0</total_fee>"//总金额
    				+ "<trade_type><![CDATA[JSAPI]]></trade_type>"//交易类型
    				+ "<transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id>"//微信支付订单号
    				+" <attach><![CDATA[12123212112014070335681123222222]]></attach>"//商家数据包
    		  + "</xml>";
        	
        	
        	try {
    			Map<String, Object> map = XMLParser.getMapFromXML(xml);
    			System.out.println(map);
    		} catch (ParserConfigurationException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SAXException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
}
