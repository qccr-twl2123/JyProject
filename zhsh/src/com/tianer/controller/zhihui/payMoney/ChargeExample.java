package com.tianer.controller.zhihui.payMoney;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.App;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import com.tianer.util.Const;
import com.tianer.util.StringUtil;
  

/**
 * Charge 对象相关示例
 * @author sunkai
 * 
 * 该实例程序演示了如何从 ping++ 服务器获得 charge ，查询 charge。
 * 
 * 开发者需要填写 apiKey 和 appId , apiKey 可以在 ping++ 管理平台【应用信息里面查看】
 * 
 * apiKey 有 TestKey 和 LiveKey 两种。 
 * 
 * TestKey 模式下不会产生真实的交易。
 */
public class ChargeExample {

	/**
	 * pingpp 管理平台对应的 API key
	 */
//	public static String apiKey = "sk_test_H0ePmLGa5OWHHivv14zf18OG";
//	public static String appId = "app_XfTmPGG4av9OqD84";
	
	
	//public static String apiKey = "sk_live_ijfbbDnT0WHO1OunT4r9q9qP";
	/**
	 * pingpp 管理平台对应的应用 ID商家端
	 */
	public static String appId2 = "app_nbjfz9GmjbTCCGKO";
	/**
	 * pingpp 管理平台对应的应用 ID会员端
	 */
	public static String appId = "app_ijP8uHLOa98SDWnj";
//	public static String apiKey = "sk_test_fnPy50XbLqbLWzXLuPazXbDC";//测试
	public static String apiKey = "sk_live_KKy1S8vbD4qPnfPGyPOWLaLC";//正式的key

	
    
    //构造方法1
    ChargeExample() { }
    //构造方法2
    ChargeExample(String appId) {
        this.appId = appId;
    }
    
    //运行顺序
//    public static void runDemos(String appId) {
//         ChargeExample chargeExample = new ChargeExample(appId);
//        System.out.println("------- 创建 charge -------");
//        Charge charge = chargeExample.createCharge();
//        System.out.println("------- 查询 charge -------"+charge);
//        chargeExample.retrieve(charge.getId());
//        System.out.println("------- 查询 charge 列表 -------");
//        chargeExample.all();
//    }

    /*
     * 创建 Charge
     *
     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
     * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
     * @return Charge
     */
    public Charge createCharge() {
        Charge charge = null;
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "cny");
        chargeMap.put("subject", "Your Subject");
        chargeMap.put("body", "Your Body");
        String orderNo = new Date().getTime() + Main.randomString(7);
        chargeMap.put("order_no", orderNo);
        chargeMap.put("channel", "alipay");
        chargeMap.put("client_ip", "127.0.0.1"); // 客户端 ip 地址(ipv4)
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        chargeMap.put("app", app);
//        Map<String, Object> extra = new HashMap<String, Object>();
//        extra.put("open_id", "USER_OPENID");
//        chargeMap.put("extra", extra);
        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
            // 传到客户端请先转成字符串 .toString(), 调该方法，会自动转成正确的 JSON 字符串
            String chargeString = charge.toString();
            System.out.println(chargeString);
        } catch (PingppException e) {
            e.printStackTrace();
        }
        return charge;
    }


  


    /**
     * 查询 Charge
     * 
     * 该接口根据 charge Id 查询对应的 charge 。
     * 参考文档：https://pingxx.com/document/api#api-c-inquiry
     * 
     * 该接口可以传递一个 expand ， 返回的 charge 中的 app 会变成 app 对象。
     * 参考文档： https://pingxx.com/document/api#api-expanding
     * @param id
     */
    public void retrieve(String id) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            List<String> expande = new ArrayList<String>();
            expande.add("app");
            param.put("expand", expande);
            //Charge charge = Charge.retrieve(id);
            //Expand app
            Charge charge = Charge.retrieve(id, param);
            if (charge.getApp() instanceof App) {
                //App app = (App) charge.getApp();
                // System.out.println("App Object ,appId = " + app.getId());
            } else {
                // System.out.println("String ,appId = " + charge.getApp());
            }

//            System.out.println(charge);

        } catch (PingppException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页查询Charge
     * 
     * 该接口为批量查询接口，默认一次查询10条。
     * 用户可以通过添加 limit 参数自行设置查询数目，最多一次不能超过 100 条。
     * 
     * 该接口同样可以使用 expand 参数。
     * @return
     */
    public ChargeCollection all() {
        ChargeCollection chargeCollection = null;
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("limit", 3);

//增加此处设施，刻意获取app expande 
//        List<String> expande = new ArrayList<String>();
//        expande.add("app");
//        chargeParams.put("expand", expande);

        try {
            chargeCollection = Charge.all(chargeParams);
//            System.out.println(chargeCollection);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return chargeCollection;
    }
    
    
    
		    /**
		     * 获取私钥路径
		     */
		public static Charge getPay(String orderno,Double amount,String ip,String channel,String msg,String subject){
		 		 Pingpp.apiKey = apiKey;
				 ChargeExample ce = new ChargeExample();
				 String str=StringUtil.getUrl();
		  		 Pingpp.privateKeyPath = str+Const.PSIYAO;//查看路劲下的私钥	
		  		 Charge charge =ce.charge(orderno, amount, ip, channel,msg,subject);
			return charge;
		}
		
		/**
	     * 创建 Charge
	     * 
	     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
	     * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
	     * @return
	     */
	    public Charge charge(String orderno,Double amount,String ip,String channel,String msg,String subject) {
		        Charge charge = null;
		        Map<String, Object> chargeMap = new HashMap<String, Object>();
		        
		        BigDecimal _total_fee = new BigDecimal(amount);
		    	int total_fee = _total_fee.intValue();
		    	
		        chargeMap.put("amount", total_fee);//金钱：以分为单位
		        chargeMap.put("currency", "cny");
		        chargeMap.put("subject",subject);
		        chargeMap.put("body", msg);
		        chargeMap.put("order_no", orderno);
		        chargeMap.put("channel", channel);
		        chargeMap.put("client_ip",ip);
		         Map<String, Object> extra = new HashMap<String, Object>();
		         if(channel.equals("alipay_wap")){
			         			 extra.put("success_url","www.baidu.com");
			     	//	         extra.put("success_url","192.168.5.42/zhsh/app_pay_history/payBackWay.do");
			     		         chargeMap.put("extra",extra);
		         }
 		        Map<String, String> app = new HashMap<String, String>();
		        app.put("id",appId);
		        chargeMap.put("app", app);
	        try {
	            //发起交易请求
	            charge = Charge.create(chargeMap);
//	            System.out.println(charge);
	        } catch (PingppException e) {
	            e.printStackTrace();
	        }
	        return charge;
	    }
		
		

	/*
	 * H5
	 */

/**
	 *微信公众号创建charge
*/
public static Charge getPayThree(String orderno,Double amount,String ip,String channel,String msg,String subject,String openid){
 		 Pingpp.apiKey = apiKey;
		 ChargeExample ce = new ChargeExample();
		 String str=StringUtil.getUrl();
  		 Pingpp.privateKeyPath = str+Const.PSIYAO;//查看路劲下的私钥	
  		 Charge charge =ce.chargeThree(orderno, amount, ip, channel,msg,subject,openid);
	return charge;
}
/**
 *微信公众号创建charge
 */
public Charge chargeThree(String orderno,Double amount,String ip,String channel,String msg,String subject,String openid) {
	Charge charge = null;
	Map<String, Object> chargeMap = new HashMap<String, Object>();
	
	BigDecimal _total_fee = new BigDecimal(amount);
	int total_fee = _total_fee.intValue();
	
	
	chargeMap.put("amount", total_fee);//金钱：以分为单位
	chargeMap.put("currency", "cny");
	chargeMap.put("subject",subject);
	chargeMap.put("body", msg);
	chargeMap.put("order_no", orderno);
	chargeMap.put("channel", channel);
	chargeMap.put("client_ip",ip);
	Map<String, String> extra = new HashMap<String, String>();
		 extra.put("open_id",openid);
		 chargeMap.put("extra", extra);
 	Map<String, String> app = new HashMap<String, String>();
	app.put("id",appId);
	chargeMap.put("app", app);
	try {
		//发起交易请求
		charge = Charge.create(chargeMap);
//		System.out.println(charge);
	} catch (PingppException e) {
		e.printStackTrace();
	}
	return charge;
}



	/**
	 * 获取私钥路径(   支付宝支付====带地址的   )
	 */
	public static Charge getPayTwo(String orderno,Double amount,String ip,String channel,String msg,String  subject,String url){
			 Pingpp.apiKey = apiKey;
		 ChargeExample ce = new ChargeExample();
		 String str=StringUtil.getUrl();
			 Pingpp.privateKeyPath = str+Const.PSIYAO;//查看路劲下的私钥	
			 Charge charge =ce.chargeTwo(orderno, amount, ip, channel,msg,subject,url);
	return charge;
	}

		
	/**
	 * 创建 chargeTwo(   带地址的   )
 	 * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
	 * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
	 * @return
	 */
	public Charge chargeTwo(String orderno,Double amount,String ip,String channel,String msg,String subject,String url) {
		Charge charge = null;
		Map<String, Object> chargeMap = new HashMap<String, Object>();

		BigDecimal _total_fee = new BigDecimal(amount);
		int total_fee = _total_fee.intValue();
		
 		chargeMap.put("amount", total_fee);//金钱：以分为单位
		chargeMap.put("currency", "cny");
		chargeMap.put("subject",subject);
		chargeMap.put("body", msg);
		chargeMap.put("order_no", orderno);
		chargeMap.put("channel", channel);
		chargeMap.put("client_ip",ip);
		Map<String, Object> extra = new HashMap<String, Object>();
		if(channel.equals("alipay_wap") || channel.equals("alipay_pc_direct")){
			extra.put("success_url",url);
 			chargeMap.put("extra",extra);
		}
		if(channel.equals("wx_pub_qr")){
			extra.put("product_id",orderno);
 			chargeMap.put("extra",extra);
		}
		Map<String, String> app = new HashMap<String, String>();
		app.put("id",appId);
		chargeMap.put("app", app);
		try {
			//发起交易请求
			charge = Charge.create(chargeMap);
//			System.out.println(charge);
		} catch (PingppException e) {
			e.printStackTrace();
		}
		return charge;
	}
 

	
	
		
		public static void main(String[] args) {
			System.out.println(getPay("CZ3339159320170610025811962300",100.00,"127.0.0.1","alipay","alipay","充值"));
		}


		
		/*
		 * 商家端的支付
		 */
		/**
		 * 获取私钥路径(   带地址的   )
		 */
		public static Charge getPayTwoStore(String orderno,Double amount,String ip,String channel,String msg,String subject,String url){
			Pingpp.apiKey = apiKey;
			ChargeExample ce = new ChargeExample();
			String str=StringUtil.getUrl();
			Pingpp.privateKeyPath = str+Const.PSIYAO;//查看路劲下的私钥	
			Charge charge =ce.chargeTwoStore(orderno, amount, ip, channel,msg,subject,url);
			return charge;
		}
		
 		/**
		   * 创建 chargeTwo(   带地址的   )
		   * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
		   * map 里面参数的具体说明请参考：https://pingxx.com/document/api#api-c-new
		   * @return
		   */
	public Charge chargeTwoStore(String orderno,Double amount,String ip,String channel,String msg,String subject,String url) {
		Charge charge = null;
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		
		BigDecimal _total_fee = new BigDecimal(amount);
		int total_fee = _total_fee.intValue();
		
		chargeMap.put("amount", total_fee);//金钱：以分为单位
		chargeMap.put("currency", "cny");
		chargeMap.put("subject",subject);
		chargeMap.put("body", msg);
		chargeMap.put("order_no", orderno);
		chargeMap.put("channel", channel);
		chargeMap.put("client_ip",ip);
		Map<String, String> app = new HashMap<String, String>();
		app.put("id",appId2);
		chargeMap.put("app", app);
		try {
			//发起交易请求
			charge = Charge.create(chargeMap);
//			System.out.println(charge);
		} catch (PingppException e) {
			e.printStackTrace();
		}
		return charge;
	}

}
