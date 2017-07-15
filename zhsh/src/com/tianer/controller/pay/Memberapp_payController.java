package com.tianer.controller.pay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
 import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.controller.youxuan.YouXuanController;
import com.tianer.entity.zhihui.OrderShop;
 import com.tianer.entity.zhihui.TihuoTask;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.youxuan.YouXuanService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
 import com.tianer.util.wxpay.WXPayPath;
 
/** 
 * 
* 类名称：Memberapp_payController   
* 类描述：   商家APP端的一些相关支付
 */
@Controller("memberapp_payController")
@RequestMapping(value="/app_pay")
public class Memberapp_payController extends BaseController{

	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	@Resource(name="youXuanService")
	private YouXuanService youXuanService;
	
	@Resource(name="appOrderService")
	private AppOrderService orderService;
	

	/**
	 * 公众号微信支付
 	 * 
	 * total_fee  金额
	 * attach     支付类型  1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值商品
	 * body       商品说明
	 * out_trade_no   订单ID
	 */
	public static Map<String, String> WxPayOrder(String _total_fee,String attach,String body,String out_trade_no) throws Exception{
		Map<String, String> returnmap=new HashMap<String, String>();
   		try {
   			BigDecimal total_fee = new BigDecimal(Double.parseDouble(_total_fee)*100);
  	    	//开始调用微信支付接口
  			WXPayPath dodo = new WXPayPath("2");
 	    	Map<String, String> reqData=new HashMap<String, String>();
 	    	reqData.put("body", body);
	    	reqData.put("attach",attach);
	    	reqData.put("out_trade_no", out_trade_no);
	    	reqData.put("fee_type", "CNY");
	    	reqData.put("total_fee", String.valueOf(total_fee.intValue()));
	    	reqData.put("spbill_create_ip", dodo.getSpbill_create_ip());
	    	reqData.put("notify_url", "http://www.jiuyuvip.com/back_mapp/wxnotify.do");
	     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	    	reqData.put("trade_type", "APP");
 
 	    	Map<String, String> map2=dodo.unifiedOrder(reqData,"2");
 	    	//开始处理结果
  	        if(map2.get("return_code").toString().equals("SUCCESS") && map2.get("result_code").toString().equals("SUCCESS")){
 	    	  returnmap.put("payment_type_", attach);
 	    	  returnmap.put("appId_", map2.get("appid").toString() );
 	    	  returnmap.put("timestamp_", String.valueOf(((new Date()).getTime())));
 	    	  returnmap.put("nonceStr_", map2.get("nonce_str").toString());
 	    	  returnmap.put("package_","prepay_id="+ map2.get("prepay_id").toString());
 	    	  returnmap.put("signType_", "MD5");
  	    	  returnmap.put("paySign_", map2.get("sign").toString());
  	    	  returnmap.put("out_trade_no", out_trade_no);
  	       }
 	       returnmap.put("result_code", map2.get("result_code").toString());
 	       returnmap.put("return_code", map2.get("return_code").toString());
 	       returnmap.put("return_msg", map2.get("return_msg").toString());
 		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
		return returnmap;
	}
	
	

	/**
	 * 优选订单支付:YxPayOrder
	 * 
	 * app_pay/yxPayOrder.do
	 * 
	 * goodsinfor : goods_id@goods_number,goods_id@goods_number,
 	 * pay_way :  nowpay,alipay,wx
   	 * member_id
   	 * goods_type  1-正常商品，2-优选商品
   	 * url：url
   	 * gopay_type :1-直接结算，2-购物车结算
   	 *添加积分支付的话：user_integral:先从金额最大的商家开始抵用，依次减
   	 * 
	 */
	@RequestMapping(value="/yxPayOrder")
	@ResponseBody
	public Object YxPayOrder(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,String> wxpaydata = new HashMap<String,String>();
		Map<String,String> alipaydata = new HashMap<String,String>();
		String result = "1";
		String message="支付成功";
		PageData pd = new PageData();
 		double user_integral=0;
		double user_integral_last=0;
		try{
			pd = this.getPageData();
			String pay_way=pd.getString("pay_way");
			//判断是否使用了积分支付
			String memberpay_integral=pd.getString("user_integral");
			if(memberpay_integral != null && !memberpay_integral.equals("")){
					user_integral=Double.parseDouble(memberpay_integral);
					user_integral_last=Double.parseDouble(memberpay_integral);
			}
			if(ServiceHelper.getAppMemberService().findById(pd) == null){
				map.put("result", "0");
				map.put("message","请先前往登录");
 				map.put("data", "");
 				map.put("wxpaydata", wxpaydata);
				map.put("alipaydata", alipaydata);
				return map;
			}
			//判断当前会员的积分是否充足
			if(Double.parseDouble(ServiceHelper.getAppMemberService().findById(pd).getString("now_integral")) < user_integral){
				map.put("result", "0");
				map.put("message","积分不足，请充值后购买");
 				map.put("data", "");
				return map;
				}
			String gopay_type=pd.getString("gopay_type");
 			String guanlian_id=BaseController.getTimeID();//关联ID
			String beguanlian_id="";
			//开始如下1-根据商家区分商品，2-根据商家的商品的提货期限区分商品，3-根据价格降序排序，4-再生成订单
			String in_jiqi="3";
			String[] goodsstr=pd.getString("goodsinfor").split(",");
			PageData ggpd=null;
			Map<String,String> store_map = new HashMap<String,String>();//KEY=STORE_ID ,VALUE=GOODS_ID@GOODS_NUMBER,....
			List<String> storestr = new ArrayList<String>();
				for (int i = 0; i < goodsstr.length; i++) {
   					if( goodsstr[i].split("@").length != 2){
							result="0";
							message="请选择商品";
							break;
						}
						if(gopay_type.equals("1")){
						pd.put("goods_id", goodsstr[i].split("@")[0]);
						pd.put("goods_number", goodsstr[i].split("@")[1]);
						pd.put("goods_type", "2");
						//判断下库存
						PageData isokpd=YouXuanController.iskuncun(pd);
						if(isokpd.getString("result").equals("0")){
							map.put("result",isokpd.getString("result"));
							map.put("message", isokpd.getString("message"));
							map.put("data", "");
					    	return map;
						}
					}
						ggpd=new PageData();
					ggpd.put("youxuangg_id", goodsstr[i].split("@")[0]);
					ggpd=ServiceHelper.getYouXuanService().finddetailgg(ggpd);
					if(ggpd == null ){
						map.put("result", "0");
						map.put("message", "当前商品不存在");
						map.put("data", "");
				    	return map;
					}
					//获取商家集合
					String store_id=ggpd.getString("store_id");
					if(!storestr.contains(store_id)){
						String _str=store_map.get(store_id);
						if(_str == null){
							_str=goodsstr[i].split("@")[0]+"@"+goodsstr[i].split("@")[1];
						}else{
							_str=_str+","+goodsstr[i].split("@")[0]+"@"+goodsstr[i].split("@")[1];
						}
						store_map.put(store_id, _str);
						}else{
							storestr.add(store_id);
						}
				}
				
				 //根据商家的集合再根据提货时间分配商品集合
				Map<String,String> store_goods_map = new HashMap<String,String>();//KEY=STORE_ID和LIMIT_DAY拼接,VALUE=GOODS_ID@GOODS_NUMBER,....
   			for (Map.Entry<String, String> sm : store_map.entrySet())  {  
   				  String store_id=sm.getKey();
   				  String store_goods=sm.getValue();
   				  String[] _goodsstr=store_goods.split(",");
	   				  for (int i = 0; i < _goodsstr.length; i++) {
	   					if( _goodsstr[i].split("@").length != 2){
   							result="0";
   							message="请选择商品";
   							break;
   						}
						ggpd=new PageData();
						ggpd.put("youxuangg_id", _goodsstr[i].split("@")[0]);
						ggpd=ServiceHelper.getYouXuanService().finddetailgg(ggpd);
						if(ggpd == null ){
							continue;
						}
						String limit_day=ggpd.getString("limit_day");
						String key=store_id+limit_day;
						if(store_goods_map.get(key) != null){
							store_goods_map.put(key, store_goods_map.get(key)+","+_goodsstr[i].split("@")[0]+"@"+_goodsstr[i].split("@")[1]);
							}else{
							store_goods_map.put(key, _goodsstr[i].split("@")[0]+"@"+_goodsstr[i].split("@")[1]);
							}
							ggpd=null;
	   				  }
	   			  }
   			  
   			//循环生成新的map为排序做准备
   			Map<String,Double> store_goodsmoneymapbefore = new HashMap<String,Double>();//排序前：KEY=STORE_ID和LIMIT_DAY拼接,VALUE=总金额
	   		for (Map.Entry<String, String> m : store_goods_map.entrySet())  {  
	   		    	String storeandgoodslimitday=m.getKey();
	   		    	String store_goods=m.getValue();
	   		    	double money=0;
	   		    	String[] _goodsstr=store_goods.split(",");
	   				for (int i = 0; i < _goodsstr.length; i++) {
   					if( _goodsstr[i].split("@").length != 2){
							result="0";
							message="请选择商品";
							break;
						}
   					ggpd=new PageData();
					ggpd.put("youxuangg_id", _goodsstr[i].split("@")[0]);
					ggpd=ServiceHelper.getYouXuanService().finddetailgg(ggpd);
					if(ggpd != null){
						money=money+Double.parseDouble(_goodsstr[i].split("@")[1])*Double.parseDouble(ggpd.getString("sale_money"));//总金额
					}else{
						continue;
					}
	   				}
   				store_goodsmoneymapbefore.put(storeandgoodslimitday,money);
	   		    }
				//开始排序根据订单进行分配排序
	   		    List<Entry<String,Double>> list =new ArrayList<Entry<String,Double>>(store_goodsmoneymapbefore.entrySet());
				Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
			        return (int)(o2.getValue() - o1.getValue());//降序
			    }
			});
				//循环生成订单：从金额高的订单开始
				double allmoney=0;//总需要支付的金额
				for (int mapi = 0; mapi < list.size(); mapi++) {
					double jfCount=0;
		            double money=0;
 					String store_id=list.get(mapi).getKey().substring(0,8);
		            String limit_day=list.get(mapi).getKey().substring(8,list.get(mapi).getKey().length());
		            PageData spd=new PageData();
		            spd.put("store_id", store_id);
		            spd=ServiceHelper.getAppStoreService().findById(spd);
		            //新增订单
					PageData orderpd=new PageData();
		            String order_id=BaseController.getTimeID();
		            orderpd.put("order_id", order_id);
		            orderpd.put("tihuolimit_day",limit_day);
		            String store_goods=store_goods_map.get(list.get(mapi).getKey());
		            String[] _goodsstr=store_goods.split(",");
	   				for (int i = 0; i < _goodsstr.length; i++) {
   						if( _goodsstr[i].split("@").length != 2){
   							result="0";
   							message="请选择商品";
   							break;
   						}
						if(gopay_type.equals("1")){
							pd.put("goods_id", _goodsstr[i].split("@")[0]);
							pd.put("goods_number", _goodsstr[i].split("@")[1]);
							pd.put("goods_type", "2");
							//判断下库存
							PageData isokpd=YouXuanController.iskuncun(pd);
							if(isokpd.getString("result").equals("0")){
								map.put("result",isokpd.getString("result"));
								map.put("message", isokpd.getString("message"));
								map.put("data", "");
						    	return map;
							}
						}
	 					ggpd=new PageData();
						ggpd.put("youxuangg_id", _goodsstr[i].split("@")[0]);
						ggpd=ServiceHelper.getYouXuanService().finddetailgg(ggpd);
						if(ggpd == null ){
							map.put("result", "0");
							map.put("message", "当前商品不存在");
							map.put("data", "");
					    	return map;
						}
						double goods_money=Double.parseDouble(_goodsstr[i].split("@")[1])*Double.parseDouble(ggpd.getString("sale_money"));//总金额
						money+=goods_money;
						double storesendjf=money*Double.parseDouble(ggpd.getString("goods_jfrate"))/100;
	 					jfCount+=storesendjf;
		   				//==========================
	 					//添加订单关联的商品
						PageData gpd=new PageData();
						gpd.put("goods_id",ggpd.get("youxuangg_id").toString());
						gpd.put("shop_number",_goodsstr[i].split("@")[1]);
						gpd.put("price", TongYong.df2.format(goods_money));
						gpd.put("order_id", order_id);
						gpd.put("goods_type", "2");
						ServiceHelper.getAppOrderService().saveOrderGoods(gpd);
						gpd=null;
						//冻结库存==================
						if(gopay_type.equals("1")){
							 ggpd.put("goods_number", "-"+_goodsstr[i].split("@")[1]);
		 					 ServiceHelper.getYouXuanService().updateYouXuanGoodsBuyNumber(ggpd);
		 					 //设置定时器
 		 			 		 OrderShop op=new OrderShop(order_id);
		 					 Timer tt=new Timer();
		 					 tt.schedule(op, 1000*60*3);
		 					 //保存
		 					  ServiceHelper.getAppOrderService().savekuncunOrder(orderpd);
		 					 //判断库存改状态
			 				  PageData goodsggpd=ServiceHelper.getYouXuanService().finddetailgg(ggpd);
			 				  if(goodsggpd.getString("needsale_number").equals("0")){
			 						PageData xpd=new PageData();
			 						xpd.put("goods_status", "98");
			 						xpd.put("youxuangoods_id", goodsggpd.getString("youxuangoods_id"));
			 						ServiceHelper.getYouXuanService().editGoods(xpd);
			 				  }
						}
	  				}
					//判断商家的赠送积分是否充足
   				if(TongYong.orderIsOkByStore(jfCount, money, "3", 0, 0, money, spd)){
					map.put("result", "0");
					map.put("message", spd.getString("store_name")+"-积分余额不足，请等待商家充值后再购买");
					map.put("data", "");
			    	return map;
				}
   				allmoney+=money;//开始计算
				orderpd.put("order_id", order_id);
				orderpd.put("look_number", order_id);
				orderpd.put("tihuo_status", "0");
				orderpd.put("order_status", "0");
				orderpd.put("sale_money", TongYong.df2.format(money));
				orderpd.put("discount_after_money", TongYong.df2.format(money));
				//看看有没有使用积分
				if(money-user_integral_last <=0 ){//支付的钱
					orderpd.put("actual_money", "0");
					orderpd.put("user_integral", TongYong.df2.format(money));
					user_integral_last=user_integral_last-money;
				}else{
					orderpd.put("actual_money", TongYong.df2.format(money-user_integral_last));
					orderpd.put("user_integral", TongYong.df2.format(user_integral_last));
					user_integral_last=0;
				}
				orderpd.put("get_integral", TongYong.df2.format(jfCount));
				orderpd.put("discount_content", "购买优选商品赠送积分@@+"+TongYong.df2.format(jfCount)+"@6,");
 				if(orderpd.getString("actual_money").equals("0")){
						orderpd.put("channel","nowpay"); 
						orderpd.put("money_pay_type", "2");
				}else{
						if(pay_way.contains("alipay")){
							orderpd.put("money_pay_type", "3");
 						}else if(pay_way.contains("wx")){
							orderpd.put("money_pay_type", "4");
 						}else if(pay_way.contains("nowpay")){
							orderpd.put("money_pay_type", "2");
 						}else{
							map.put("result", "0");
							map.put("message", "支付方式有误");
					    	return map;
						}
						orderpd.put("channel",pay_way); 
				}
				orderpd.put("store_id", spd.getString("store_id"));
				orderpd.put("store_operator_id", "jy"+store_id);//分配操作员
				orderpd.put("pay_type", "3");
				orderpd.put("pay_sort_type", "2");
				orderpd.put("payer_id", pd.getString("member_id"));
				//生成一个提货卷
				boolean istihuo=true;
				while(istihuo){
							String tihuo_id=BaseController.get10UID();
							PageData thpd=new PageData();
							thpd.put("tihuo_id", tihuo_id);
							thpd=ServiceHelper.getPayapp_historyService().tihuoByOrderId(thpd);
							if(thpd==null){
								istihuo=false;
								orderpd.put("startdate", DateUtil.getTime());
								String time=DateUtil.getAfterTimeDate(DateUtil.getTime(),limit_day);
								orderpd.put("enddate", time);
								//设置定时器
								long l1=DateUtil.fomatDate1(DateUtil.getTime()).getTime();
								long l2=DateUtil.fomatDate1(time).getTime();
								TihuoTask th=new TihuoTask(tihuo_id);
								Timer tt=new Timer();
								tt.schedule(th, l2-l1);
								//----------------------
								orderpd.put("tihuo_id", tihuo_id);
							}
							thpd=null;
				}	
				orderpd.put("in_jiqi", in_jiqi);
				orderpd.put("order_tn", order_id);
				ServiceHelper.getYouXuanService().saveYouxuanOrder(orderpd);
	   			//添加联系
				beguanlian_id+=order_id+",";
 		    }  
			//新增关联
			PageData glpd=new PageData();
			glpd.put("guanlian_id", guanlian_id);
			glpd.put("beguanlian_id", beguanlian_id);
			ServiceHelper.getAppOrderService().saveguanlian(glpd);
			//判断是否需要调用微信接口
			double lastpaymoney=allmoney-user_integral;
			BigDecimal _amount = new BigDecimal(lastpaymoney);
			lastpaymoney =   _amount.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
			if(lastpaymoney >0){
				if(pay_way.contains("wx")){
					wxpaydata=WxPayOrder(TongYong.df2.format(lastpaymoney), "3", "九鱼优选-购买商品",guanlian_id);
				}else{
					
				}
			}else{
					TongYong.youxuanOkOrder(guanlian_id, "");
			}
			map1.put("wxpay", wxpaydata);
			map1.put("alipay", alipaydata);
			map1.put("order_id", guanlian_id);
		} catch(Exception e){
			result="0";
			message="获取异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data", map1);
		pd=null;
		return map;
	}
	
	
	
	/**
 	 * 
 	*  充值接口:：paychongzhi 
 	*  app_pay/paychongzhi.do
 	*  
 	*  member_id  
  	*  money 充值金额
 	*  pay_way   
 	*  
 	 */
	@RequestMapping(value="/paychongzhi")
	@ResponseBody
	public Object paychongzhi(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, String> wxpaydata = new HashMap<String, String>();
		Map<String, String> alipaydata = new HashMap<String, String>();
  		String result="1";
		String message="充值确认中";
 		PageData pd=new PageData();
		try{
 			pd = this.getPageData();
 			if(ServiceHelper.getAppMemberService().findById(pd) == null){
				map.put("result", "0");
				map.put("message","请先前往登录");
 				map.put("data", "");
				return map;
			}
 			String pay_way=pd.getString("pay_way");
   			String waterrecord_id=BaseController.getCZUID("");
			String money=pd.getString("money");//当前金钱
 			if(money == null || money.equals("") || Double.parseDouble(money) <= 0 ){
 				map.put("result", "0");
 				map.put("message", "money不能为空/必须大于0");
 				map.put("data", "");
 		    	return map;
 			}
 			PageData waterpd=new PageData();
			waterpd.put("pay_status","0");
   			waterpd.put("waterrecord_id",waterrecord_id);
			waterpd.put("user_id", pd.getString("member_id"));
			waterpd.put("user_type", "2");
			waterpd.put("withdraw_rate","0");
			waterpd.put("money_type","1");
			waterpd.put("money", money);
			waterpd.put("reduce_money","0");
			waterpd.put("arrivalmoney",  money);
			waterpd.put("nowuser_money","0");
			waterpd.put("application_channel","5");
			if(pay_way.contains("wx")){
				waterpd.put("remittance_name",Const.payjiqi[4] );
				waterpd.put("remittance_type","4" );
				waterpd.put("wx_money",  money );
			}else if(pay_way.contains("alipay")){
				waterpd.put("remittance_name", Const.payjiqi[3]);
				waterpd.put("remittance_type","3" );
				waterpd.put("alipay_money",  money );
			}else{
				waterpd.put("remittance_name", Const.payjiqi[3]);
				waterpd.put("remittance_type","1" );
				waterpd.put("bank_money",  money );
			}
 			waterpd.put("remittance_number",ServiceHelper.getAppMemberService().findById(pd).getString("phone"));//支付人的支付账号
			waterpd.put("createtime",DateUtil.getTime());
			waterpd.put("over_time",DateUtil.getTime());
 			waterpd.put("jiaoyi_type","0");
			waterpd.put("payee_number",Const.jiuyunumber);
	 		waterpd.put("order_tn", waterrecord_id );
			waterpd.put("province_name", ServiceHelper.getAppMemberService().findById(pd).getString("province_name"));
			waterpd.put("city_name", ServiceHelper.getAppMemberService().findById(pd).getString("city_name"));
			waterpd.put("area_name", ServiceHelper.getAppMemberService().findById(pd).getString("area_name"));
			ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
			waterpd=null;
			String  attach="4";//支付类型  1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值商品
			String  body="九鱼网-充值余额";
			if(pay_way.contains("wx")){
				wxpaydata= WxPayOrder(money, attach, body,waterrecord_id);
			}else{
				
			}
			map1.put("wxpay", wxpaydata);
			map1.put("alipay", alipaydata);
			map1.put("order_id", waterrecord_id);
		} catch(Exception e){
			result="0";
			message="获取异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data", map1);;
	    	return map;
	}
 
	
	/**
   	 * 订单交易支付接口
   	* 方法名称:：payorder 
   	* 方法描述：订单支付接口
   	* app_pay/payorder.do
   	* 
   	* member_id
   	* store_id
   	* pay_way 
   	* url  ：  url
   	 */
	@RequestMapping(value="/payorder")
	@ResponseBody
	public  Object PayOrder(HttpServletRequest request) throws Exception{
 		Map<String, Object> map = new HashMap<String, Object>();
 		Map<String, Object> map1 = new HashMap<String, Object>();
 		Map<String, String> wxpaydata = new HashMap<String, String>();
		Map<String, String> alipaydata = new HashMap<String, String>();
	  	String result="1";
		String message="支付成功";
		PageData pd=new PageData();
			try{
			pd = this.getPageData();
			if(ServiceHelper.getAppMemberService().findById(pd) == null){
				map.put("result", "0");
				map.put("message","请先前往登录");
 				map.put("data", "");
 				map.put("wxpaydata", wxpaydata);
				map.put("alipaydata", alipaydata);
				return map;
			}
			String pay_way=pd.getString("pay_way");
			String order_id=BaseController.getTimeID();//支付历史记录
			pd.put("order_id", order_id);
			pd.put("look_number", order_id);
			// 处理订单
			PageData orderpd=TongYong.chuliOrder(pd,ServiceHelper.getAppMemberService().findById(pd),ServiceHelper.getAppStoreService().findById(pd));
			if(orderpd.getString("result").equals("0")){
				ServiceHelper.getAppPcdService().saveLog(order_id, "订单支付出错","10");
				map.put("result", orderpd.getString("result"));
				map.put("message", orderpd.getString("message"));
			    map.put("data", "");
			    return map;
			}
			pd=(PageData) orderpd.get("data");
			String pay_type=pd.getString("pay_type");////1-收银，2-优惠买单，3-提货卷  
			String attach="";//支付类型  1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值商品
			String body="";
			if(pay_type.equals("2")){
				attach="1";
				body="优惠买单-购买商品";
			}else{
				attach="2";
				body="提货券-购买商品";
			}
				double actual_money=Double.parseDouble(pd.getString("actual_money"));
			if(actual_money > 0 ){
				if(pay_way.contains("wx")){
					wxpaydata= WxPayOrder(TongYong.df2.format(actual_money), attach, body,order_id);
				}else{
					
				}
 			}
			map1.put("wxpay", wxpaydata);
			map1.put("alipay", alipaydata);
				map.put("order_id", order_id);
			}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
			return map;
		}

	
	
	

}
