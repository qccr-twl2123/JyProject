package com.tianer.controller.storepc.storepc_wx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pingplusplus.model.Charge;
import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.pay_history.Pay_historyController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.controller.zhihui.payMoney.ChargeExample;
import com.tianer.entity.html.HtmlUser;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.wxpay.WXPayPath;
import com.tianer.util.wxpay.WXPayUtil;

/** 
 * 
* 类名称：Storepc_wxController   
* 类描述：   微信支付相关控制层
 */
@Controller("storepc_wxController")
@RequestMapping(value="/storepc_wx")
public class Storepc_wxController extends BaseController{

	/**
	 * 微信支付
	 * 
	 * total_fee  金额
	 * attach     支付类型  1-支付扣点充值，2-支付服务费，3-充值，4-支付优选编辑费用 
	 * body       商品说明
	 * out_trade_no   订单ID
	 */
	public static Map<String, String> WxPayOrder(String _total_fee,String attach,String body,String out_trade_no) throws Exception{
		Map<String, String> returnmap=new HashMap<String, String>();
   		try {
   			BigDecimal total_fee = new BigDecimal(Double.parseDouble(_total_fee)*100);
  	    	//开始调用微信支付接口
  			WXPayPath dodo = new WXPayPath();
 	    	Map<String, String> map=new HashMap<String, String>();
	    	map.put("body", body);
	    	map.put("attach",attach);
	    	map.put("out_trade_no", out_trade_no);
	    	map.put("fee_type", "CNY");
	    	map.put("total_fee", String.valueOf(total_fee.intValue()));
	    	map.put("spbill_create_ip", dodo.getSpbill_create_ip());
	    	map.put("notify_url", "http://www.jiuyuvip.com/wxback_pc/notify.do");
	     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	    	map.put("trade_type", "NATIVE");
 	    	//补全信息appid、mch_id、nonce_str、sign_type、sign
	    	map=dodo.fillRequestData(map);
	    	//map转化为String
	    	String newreqBody=WXPayUtil.mapToXml(map);
 	    	System.out.println(newreqBody);
 	    	Map<String, String> map2=dodo.payorderByHttps(newreqBody);
 	    	//开始处理结果
  	        if(map2.get("result_code").toString().equals("SUCCESS") && map2.get("return_code").toString().equals("SUCCESS")){
  	        	returnmap.put("code_url", map2.get("code_url").toString());//支付的微信二维码
  	        }
 	        returnmap.put("result_code", map2.get("result_code").toString());
 	        returnmap.put("return_code", map2.get("return_code").toString());
 	        returnmap.put("return_msg", map2.get("return_msg").toString());
 	        returnmap.put("out_trade_no", out_trade_no);//交易订单ID
 		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
		return returnmap;
	}
	
	
	
	 /**
	  * 支付服务费
	  * storepc_wx/transaction_pointsPay.do
	  * 
	  */
	@RequestMapping(value="/store_payEarnest_money")
	@ResponseBody
	public Object store_payEarnest_money(HttpServletRequest request) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		String result="1";
		String message="支付确认中";
 		PageData pd=new PageData();
		try{
			pd = this.getPageData();
  			if(slogin ==null || slogin.getStore_id() == null){
 				result="0";
 				message="id不能为空";
 			}else{
 				pd.put("store_id", slogin.getStore_id());
  				String store_wealthhistory_id=BaseController.getCZUID(pd.getString("store_operator_id"));
 				//添加 next_city_file_fee_id 到商家
	 			ServiceHelper.getAppStoreService().editFeeByCity(pd);
	 			String money=pd.getString("money");
				if( money== null || money.equals("") || Double.parseDouble(money) <= 0 ){
						result="0";
		 				message="金额不能为空且必须大于0";
				}else{
					if( pd.getString("store_operator_id") == null ||  pd.getString("store_operator_id").equals("") ){
							pd.put("store_operator_id", "jy"+pd.getString("store_id"));
					}
					String pay_way=pd.getString("pay_way");//支付方式
 	   				pd.put("wealth_type", "1");
	   				pd.put("profit_type", pd.getString("profit_type"));
	   				pd.put("number", TongYong.df2.format(-Double.parseDouble(money)));
	   				pd.put("store_wealthhistory_id", store_wealthhistory_id);
	   				pd.put("jiaoyi_id", store_wealthhistory_id);
		   			pd.put("store_id", pd.getString("store_id"));
		   			pd.put("pay_type", pay_way);
	   				pd.put("store_operator_id", pd.getString("store_operator_id"));
	   				pd.put("process_status", "0");
	   				pd.put("in_jiqi", "4");
	   				pd.put("last_wealth",ServiceHelper.getAppStoreService().sumStoreWealth(pd));
		   			pd.put("arrivalMoney", TongYong.df2.format(-Double.parseDouble(money)));
		   			ServiceHelper.getAppStoreService().saveWealthhistory(pd);
		   			//获取支付二维码
	   				data=WxPayOrder(money, "2", "服务费-支付", store_wealthhistory_id);
 	 			}
  			}
  		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", data);
    	return map;
	}
	
	
	/**
	 * 交易扣点方式支付服务费
	 * storepc_wx/transaction_pointsPay.do
	 * 
	 * money 金额
	 * 
  	 */
	@RequestMapping(value="/transaction_pointsPay")
	@ResponseBody
	public Object Transaction_pointsPay(HttpServletRequest request) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		String result="1";
		String message="支付确认中";
 		PageData pd=new PageData();
		try{
 			pd = this.getPageData();
  			if(slogin ==null || slogin.getStore_id() == null){
 				result="0";
 				message="id不能为空";
 			}else{
 				pd.put("store_id", slogin.getStore_id());
  				String store_wealthhistory_id=BaseController.getCZUID("");
 				if( pd.getString("store_operator_id") == null ||  pd.getString("store_operator_id").equals("") ){
					pd.put("store_operator_id", "jy"+pd.getString("store_id"));
				}else{
					store_wealthhistory_id=BaseController.getCZUID(pd.getString("store_operator_id")); 
				}
				String money=pd.getString("money");
				if( money== null || money.equals("") || Double.parseDouble(money) <= 0 ){
						result="0";
		 				message="金额不能为空且必须大于0";
				}else{
					String pay_way=pd.getString("pay_way");//支付方式
 	   				pd.put("wealth_type", "1");
	   				pd.put("profit_type", "2");//1-商家收益（提现），2-商家充值积分，3-用户支付的收益金钱/积分，4-现金支付的金额，5-第三方支付的金额，6-抢积分,7-送积分
	   				pd.put("number", money);
	   				pd.put("pay_type", pay_way);
	   				pd.put("store_wealthhistory_id", store_wealthhistory_id);
	   				pd.put("store_id", pd.getString("store_id"));
	   				pd.put("store_operator_id", pd.getString("store_operator_id"));
	   				pd.put("process_status", "0");
	   				pd.put("jiaoyi_id", store_wealthhistory_id);
	   				pd.put("arrivalMoney", money);
	   				pd.put("in_jiqi", "4");
	   				String nowMoney = ServiceHelper.getAppStoreService().sumStoreWealth(pd);
	   				double nom = Double.parseDouble(money);
	   				double nownom = Double.parseDouble(nowMoney);
	   				pd.put("last_wealth", TongYong.df2.format(nownom+nom));
	   				ServiceHelper.getAppStoreService().saveWealthhistory(pd);
	   				//获取支付二维码
	   				data=WxPayOrder(money, "1", "交易扣点-支付", store_wealthhistory_id);
				}
  			}
  		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", data);
    	return map;
	}
	
	 /**
 	 * 
 	* 方法名称:：Store_cz 
 	* 方法描述：充值
 	* storepc_wx/store_cz.do
 	* 
 	 */
	@RequestMapping(value="/store_cz")
	@ResponseBody
	public Object Store_cz(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
   		String result="1";
		String message="充值确认中";
 		PageData pd=new PageData();
		try{
			pd = this.getPageData();
			if(slogin ==null || slogin.getStore_id() == null){
 				result="0";
 				message="id不能为空";
 			}else{
 	 			 	String store_wealthhistory_id=BaseController.getCZUID(pd.getString("store_id"));//充值单号
	 			 	if( pd.getString("store_operator_id") == null ||  pd.getString("store_operator_id").trim().equals("") ){
	 						pd.put("store_operator_id", "jy"+pd.getString("store_id"));
	 				}else{
	 						store_wealthhistory_id=BaseController.getCZUID(pd.getString("store_operator_id")); 
	 				}
					String money=pd.getString("money");
					if( money== null || money.equals("") || Double.parseDouble(money) <= 0 ){
						result="0";
		 				message="金额不能为空且必须大于0";
					}else{
						String pay_way=pd.getString("pay_way");//支付方式
						//新增商家财富历史记录
		   				pd.put("wealth_type", "1");
		   				pd.put("profit_type", "2");//1-商家收益（提现），2-商家充值积分，3-用户支付的收益金钱/积分，4-现金支付的金额，5-第三方支付的金额，6-抢积分,7-送积分
		   				pd.put("number", money);
		   				pd.put("pay_type", pay_way);
		   				pd.put("store_wealthhistory_id", store_wealthhistory_id);
		   				pd.put("store_id", pd.getString("store_id"));
		   				pd.put("store_operator_id", pd.getString("store_operator_id"));
		   				pd.put("process_status", "0");
		   				pd.put("jiaoyi_id", store_wealthhistory_id);
		   				pd.put("arrivalMoney", money);
		   				pd.put("in_jiqi", "4");
		   				String nowMoney = ServiceHelper.getAppStoreService().sumStoreWealth(pd);
		   				double nom = Double.parseDouble(money);
		   				double nownom = Double.parseDouble(nowMoney);
		   				pd.put("last_wealth", TongYong.df2.format(nownom+nom));
		   				ServiceHelper.getAppStoreService().saveWealthhistory(pd);
		   				//获取支付二维码
		   				data=WxPayOrder(money, "3", "九鱼网充值-支付", store_wealthhistory_id);
 	 			}
  			}
  		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", data);
    	return map;
	}
	
	
	
	 /**
	  * 优选的编辑费/上架费
	  * storepc_wx/payyouxuan.do
	  * 
	  */
	@RequestMapping(value="/payyouxuan")
	@ResponseBody
	public Object payyouxuan(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
 		String result="1";
		String message="支付确认中";
 		PageData pd=new PageData();
		try{
					pd = this.getPageData();
					String store_wealthhistory_id=BaseController.getTimeID();
					String youxuangoods_id=pd.getString("youxuangoods_id");
					PageData yxgoodspd=ServiceHelper.getYouXuanService().findByIdGoods(pd);
					String goods_status=yxgoodspd.getString("goods_status");
					String bianji_money=yxgoodspd.getString("bianji_money");
//					String shangjia_money=yxgoodspd.getString("shangjia_money");
					String money="";
					String profit_type="13";
					if(goods_status.equals("1")){
						money=bianji_money;
						profit_type="13";
					}
//						else if(goods_status.equals("2")){
//							money=shangjia_money;
//							profit_type="14";
//						}
					if( pd.getString("store_operator_id") == null ||  pd.getString("store_operator_id").equals("") ){
							pd.put("store_operator_id", "jy"+pd.getString("store_id"));
					}
					String pay_way=pd.getString("pay_way");//支付方式
 	   				pd.put("wealth_type", "1");
	   				pd.put("profit_type",  profit_type);
	   				pd.put("number", TongYong.df2.format(Double.parseDouble(money)));
	   				pd.put("store_wealthhistory_id", store_wealthhistory_id);
	   				pd.put("jiaoyi_id", youxuangoods_id);
	   				pd.put("user_id", "Jiuyu");
		   			pd.put("store_id", pd.getString("store_id"));
		   			pd.put("pay_type", pay_way);
	   				pd.put("store_operator_id", pd.getString("store_operator_id"));
	   				pd.put("process_status", "0");
	   				pd.put("in_jiqi", "4");
		   			pd.put("last_wealth", ServiceHelper.getAppStoreService().sumStoreWealth(pd));
		   			pd.put("arrivalMoney", TongYong.df2.format(Double.parseDouble(money)));
		   			ServiceHelper.getAppStoreService().saveWealthhistory(pd);
		   			//获取支付二维码
	   				data=WxPayOrder(money, "4", "优选商品-编辑支付", store_wealthhistory_id);
  		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", data);
		return map;
	}
	
	
	
	
	
	
	
	

}
