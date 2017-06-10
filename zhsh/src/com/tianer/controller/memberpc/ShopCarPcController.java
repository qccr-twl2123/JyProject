package com.tianer.controller.memberpc;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianer.controller.base.BaseController;
import com.tianer.service.memberPc.PcShopCarService;

/** 
 * 
* 类名称：ShopCarPcController   
* 创建人：邢江涛
* 创建时间：2016年8月01日 
 */
@Controller("shopCarPcController")
@RequestMapping(value = "/membershopCarPc")
public class ShopCarPcController extends BaseController{
	
	@Resource(name="pcShopCarService")
	private PcShopCarService pcShopCarService;
// 	
//	/**
//	 * 我的购物车列表--会员PC端
//	 */
//	@RequestMapping(value="/MyshopCar")
// 	public ModelAndView MyshopCar(){
//		ModelAndView mv = this.getModelAndView();
//		DecimalFormat   df   = new DecimalFormat("######0.00"); 
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			if(appMemberService.findById(pd) == null){
//				mv.setViewName("redirect:../memberpc/goMemberLogin.do");
//			}else{
//				pd.put("goods_type", "1");
//				List<PageData> shopList = carService.shopCarList(pd);
//				for(PageData e :shopList){
//					double money=Double.parseDouble(e.getString("sale_money"))*Double.parseDouble(e.getString("goods_number"));
//					e.put("money", df.format(money));
//				}
//				mv.addObject("shopList", shopList);
//				shopList=null;
//				mv.setViewName("memberpc/business_Buyers5");
//			}
//			
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
// 		return mv;
// 	}
//  
//	
//	/**
//	 * 批量删除购物车里面的数据
//	 */
//	@RequestMapping(value="/deleteAll")
//	@ResponseBody
//	public Object deleteAll() {
//		logBefore(logger, "批量删除购物车里面的数据");
//		PageData pd = new PageData();	
//		HttpServletRequest request=this.getRequest();
//		HttpSession pcsession=request.getSession();
//		String result = "01";
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			pd = this.getPageData();
//			List<PageData> pdList = new ArrayList<PageData>();
//			String shopcart_id = pd.getString("shopcart_id");
//			if(null != shopcart_id && !"".equals(shopcart_id)){
//				String ArrayShopcart_id[] = shopcart_id.split(",");
//				carService.deleteAll(ArrayShopcart_id);
//				pd.put("msg", "ok");
//				result = "01";
//			}else{
//				pd.put("msg", "no");
//			}
//			pdList.add(pd);
//			map.put("list", pdList);
//			//统计购物车的数量
//			pd=(PageData) pcsession.getAttribute("memberpd");
//			String number=carService.shopCarCount(pd);
//			pcsession.setAttribute("shopnumber", number);
//		} catch (Exception e) {
//			result = "00";
//			logger.error(e.toString(), e);
//		} finally {
//			logAfter(logger);
//		}
//		map.put("result", result);
//		return map;
//	}
//	
//	
//	/**
//	 * 获取商品id
//	 * @return
//	 */
//	public Map<String, List<PageData>> map(PageData pd){
//		Map<String, List<PageData>> map = new HashMap<String, List<PageData>>();
//		
//		try {
//			List<PageData> list = pcShopCarService.goodsList(pd);
//			for (int i = 0; i < list.size(); i++) {
//				String goodsId = list.get(i).getString("goods_id");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//		}
//		
//		return null;
//	}
//	
//	
//	/**
//	 * 支付成功跳转页面
//	 */
//	@RequestMapping(value="/tiaozhuan")
//	public ModelAndView tiaozhuan(){
//		logBefore(logger, "支付成功");
//		ModelAndView mv = this.getModelAndView();
//		mv.setViewName("memberpc/business_Buyers7");
//		return mv;
//	}
//	
//	
//	/**
//	 * 去支付页面
//	 */
//	@RequestMapping(value="/goBuy")
// 	public ModelAndView goBuy(){
//		ModelAndView mv = this.getModelAndView();
//		logBefore(logger, "新增saveShop");
//		PageData pd = new PageData();
//		try {
//			pd = this.getPageData();
//  			System.out.println(pd.toString());
//  			
//		} catch (Exception e) {
// 			logger.error(e.toString(), e);
//		}
//		mv.setViewName("memberpc/business_Buyers7");
// 		return mv;
//	}
//	
//	@Resource(name="appOrderService")
//	private AppOrderService appOrderService;
//	@Resource(name="appMember_wealthhistoryService")
//	private AppMember_wealthhistoryService appMember_wealthhistoryService;
//	@Resource(name="appMember_redpacketsService")
//	private AppMember_redpacketsService appMember_redpacketsService;
//	@Resource(name="appStore_redpacketsService")
//	private AppStore_redpacketsService appStore_redpacketsService;
// 
//	@Resource(name="payapp_historyService")
//	private Payapp_historyService historyService;
//	
//	/**
//	 * 
//	* 方法名称:：thirdPartyPay 
//	* 方法描述：购物车购买
//	* 创建人：魏汉文
//	* 创建时间：2016年8月9日 上午11:06:56
//	 */
//	@RequestMapping(value="/thirdPartyPay")
//	@ResponseBody
//	public Object thirdPartyPay(HttpServletRequest request) throws Exception{
//		Map<String, Object> map = new HashMap<String, Object>();
//		Map<String, Object> map1 = new HashMap<String, Object>();
//  		String result="1";
//		String message="支付确认中";
//		String id="";
//		// type代表支付方式
//		PageData pd=new PageData();
//		try{
//			pd = this.getPageData();
//			String order_id=BaseController.getTimeID();//支付历史记录
//			boolean flag=true;
//			PageData mpd=new PageData();
// 			mpd=appMemberService.findById(pd);//用户详情
// 			if(mpd == null){
// 				map.put("result", "01");
// 				//map.put("message", "用户id不能为空");
// 				//map.put("data", "");
// 		    	return map;
// 			}
//			String ip=getIp(request);//当前用户所在IP地址
//			String pay_way=pd.getString("pay_way");//支付方式
//			String url=pd.getString("url");//支付方式
//			String allgoodsid=pd.getString("allgoodsid");//所有订单里的商品
// 			String actual_money=pd.getString("actual_money");//支付的金额（现金或者第三方）
//			String user_balance=pd.getString("user_balance");//使用的余额
//			String user_integral=pd.getString("user_integral");//使用的积分
//			System.out.println("判断金钱是否符合");
//			//判断金钱是否符合
//			if(!StringUtil.checkMoney(user_balance) || !StringUtil.checkMoney(user_integral) ){
//				map.put("result", "0");
//				map.put("message", "==================================金钱格式有误");
//				map.put("data", "");
//		    	return map;
//			}
//			System.out.println("==============================判断余额是否充足");
// 			//判断余额
//			if(!user_balance.equals("")){
//   				double now_money=Double.parseDouble(mpd.getString("now_money"));
//				double n=Double.parseDouble(user_balance);
// 				if(now_money < n){
//						flag=false;
//				} 
// 			}
//			System.out.println("==============================判断积分是否充足");
//			//判断积分
//			if(!user_integral.equals("")){
//   				double now_integral=Double.parseDouble(mpd.getString("now_integral"));
// 				double m=Double.parseDouble(user_integral);
//				if(now_integral < m){
//						flag=false;
//				} 
// 			}
//			System.out.println("==============================开始准备生成订单"+flag);
// 			//判断是否财富是否充足
//			if(flag){
//					//新增订单信息tb_order
// 					String look_number=this.getTimeID();
//					pd.put("order_id", order_id);
//					//缺少分配操作员的一个步骤
// 					pd.put("store_operator_id", "");
// 					pd.put("look_number", look_number);
//					pd.put("order_status", "0");//待确认
//					//生成一个提货卷
//					boolean istihuo=true;
//					while(istihuo){
//						String tihuo_id=this.get10UID();
//						PageData e=new PageData();
//						e.put("tihuo_id", tihuo_id);
//						e=historyService.tihuoByOrderId(e);
//						if(e==null){
//							istihuo=false;
//							pd.put("startdate", DateUtil.getDay());
//							String time=DateUtil.getAfterDayDate(DateUtil.getDay(),Const.endnumberdate);
//							pd.put("enddate", time);
//							//设置定时器
//							long l1=(new Date()).getTime();
//							long l2=DateUtil.fomatDate(time).getTime();
//							TihuoTask th=new TihuoTask(tihuo_id);
//							Timer tt=new Timer();
//							tt.schedule(th, l2-l1);
//							//----------------------
// 							pd.put("tihuo_id", tihuo_id);
//							id=tihuo_id.substring(0, 2)+"  "+tihuo_id.substring(2, 6)+"  "+tihuo_id.substring(6, 10);
//							map1.put("id", id);
//							map.put("tihuo_id", tihuo_id);
//						}
//					}
//					appOrderService.saveOrder(pd);
//					//新增订单关联的商品
//					if(!allgoodsid.equals("")){
//						String[] allgoods=allgoodsid.split(",");
//						for(int i=0 ; i< allgoods.length ; i++){
//								PageData gpd=new PageData();
//								String[] goodsstr=allgoods[i].split("@");
//								gpd.put("goods_id", goodsstr[0]);
//								gpd.put("shop_number", goodsstr[1]);
//								gpd.put("price", goodsstr[2]);
//								gpd.put("order_id", order_id);
//								gpd.put("goods_type", "1");
//								appOrderService.saveOrderGoods(gpd);
//								gpd=null;
//						}
//					}
//					/*
//					 * 支付方式pay_type:
//					 * alipay:支付宝手机支付
//						alipay_wap:支付宝手机网页支付
//						alipay_qr:支付宝扫码支付
//						alipay_pc_direct:支付宝 PC 网页支付
//						bfb:百度钱包移动快捷支付
//						bfb_wap:百度钱包手机网页支付
//						upacp:银联全渠道支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
//						upacp_wap:银联全渠道手机网页支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
//						upacp_pc:银联 PC 网页支付
//						cp_b2b:银联企业网银支付
//						wx:微信支付
//						wx_pub:微信公众账号支付
//						wx_pub_qr:微信公众账号扫码支付
//						yeepay_wap:易宝手机网页支付
//						jdpay_wap:京东手机网页支付
//						cnp_u:应用内快捷支付（银联）
//						cnp_f:应用内快捷支付（外卡）
//						applepay_upacp :Apple Pay
//						fqlpay_wap:分期乐支付
//						qgbc_wap:量化派支付
//						cmb_wallet:招行一网通
//					 */
//					//2.获取charge对象
//					Charge charge = ChargeExample.getPayTwo(order_id, Double.parseDouble(actual_money)*100,ip,pay_way,"12","提货券支付",url);
//					if(charge == null ){
//						result="0";
//						message="支付失败，charge出错";
//						map1.put("charge", "");
//					}else{
//						map1.put("charge", charge);
//					}
//					map.put("data", map1);
//   			}else{
//				result="0";
//				message="余额不足";
//				map.put("data", map1);
//			}
// 		}catch(Exception e){
//			result="0";
//			message="系统异常";
//			map.put("data", e.toString());
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message", message);
//    	return map;
//	}
//	
//	/*
//	 * 获取IP
//	 */
//	public static String getIp(HttpServletRequest request) {
//		String ipAddress = null;
//		// ipAddress = this.getRequest().getRemoteAddr();
//		ipAddress = request.getHeader("x-forwarded-for");
//		if (ipAddress == null || ipAddress.length() == 0
//				|| "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("Proxy-Client-IP");
//		}
//		if (ipAddress == null || ipAddress.length() == 0
//				|| "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (ipAddress == null || ipAddress.length() == 0
//				|| "unknown".equalsIgnoreCase(ipAddress)) {
//			ipAddress = request.getRemoteAddr();
//			if (ipAddress.equals("127.0.0.1")) {
//				// 根据网卡取本机配置的IP
//				InetAddress inet = null;
//				try {
//					inet = InetAddress.getLocalHost();
//				} catch (UnknownHostException e) {
//					e.printStackTrace();
//				}
//				ipAddress = inet.getHostAddress();
//			}
//
//		}
// 		 //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
//		 if(ipAddress!=null && ipAddress.length()>15){
//		 //"***.***.***.***".length() = 15
//		 if(ipAddress.indexOf(",")>0){
//		 ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
//		 }
//		 }
//		return ipAddress;
//	}
//	
}
