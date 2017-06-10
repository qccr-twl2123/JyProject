
//代码用区，不在项目内
//package com.tianer.controller.wechat.pay;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.Signature;
//import java.security.SignatureException;
//import java.text.SimpleDateFormat;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONObject;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.Header;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.pingplusplus.Pingpp;
//import com.pingplusplus.model.Charge;
//import com.pingplusplus.model.Event;
//import com.pingplusplus.model.EventData;
//import com.pingplusplus.model.Refund;
//import com.pingplusplus.model.Transfer;
//import com.pingplusplus.model.Webhooks;
//import com.tianer.controller.base.BaseController;
//import com.tianer.entity.system.User;
//import com.tianer.service.consult.ConsultService;
//import com.tianer.service.lawer.LawerAccountService;
//import com.tianer.service.lawer.LawerService;
//import com.tianer.service.message.MessageService;
//import com.tianer.service.order.OrderService;
//import com.tianer.service.system.UserService;
//import com.tianer.util.AppUtil;
//import com.tianer.util.Const;
//import com.tianer.util.Constant;
//import com.tianer.util.DateUtil;
//import com.tianer.util.HttpUtil;
//import com.tianer.util.JPushClientUtil;
//import com.tianer.util.PageData;
//import com.tianer.util.PingppUtil;
//
//@Controller
//@RequestMapping("/pay")
//public class PayController extends BaseController{
//
//	@Resource(name="lawerService")
//	private LawerService lawerService;
//	@Resource(name="orderService")
//	private OrderService orderService;
//	@Resource(name="messageService")
//	private MessageService messageService;
//	@Resource(name="consultService")
//	private ConsultService consultService;
//	@Resource(name="userService")
//	private UserService userService;
//	@Resource(name="lawerAccountService")
//	LawerAccountService lawerAccountService;
//	
//	/**
//	 * h5支付，从服务端发起支付请求，获取支付凭据
//	 * @return
//	 * @throws Exception 
//	 */
//	@RequestMapping("/createCharge")
//	@ResponseBody
//	public Object createCharge() throws Exception{
//		logBefore(logger, "h5订单支付，从服务端发起支付请求，获取支付凭据pay.createCharge");
//		Map<String,Object> map = new HashMap<String,Object>();
//		PageData pd =new PageData();
//		pd=this.getPageData();
//		
//		//shiro管理的session
//		Subject currentUser = SecurityUtils.getSubject();  
//		Session session = currentUser.getSession();
//		User user = (User) session.getAttribute(Const.SESSION_USER);
//		String openid = pd.getString("openid");
//		if (user == null && "".equals(openid) && openid != null) {
//			user = userService.getByOpenid(pd);
//			session.setAttribute(Const.SESSION_USER, user);
//		}
//		
//		String channel = pd.getString("channel");	//	支付渠道
//		String trade_type = pd.getString("trade_type");	//交易类型
//		String cons_id = pd.getString("cons_id");
//		if(cons_id == null || "".equals(cons_id)){
//			cons_id = DateUtil.getDayshms() + this.get6UID();
//		}
//		String lawer_id = pd.getString("lawer_id");
//		BigDecimal _amount = new BigDecimal(pd.getString("amount"));
//		int amount = _amount.multiply(new BigDecimal("100")).intValue();
//		String subject = pd.getString("subject");
//		String body = pd.getString("body");
//		Charge charge = null;
//		try{
//			charge = PingppUtil.createCharge(channel,cons_id,lawer_id,amount,subject,body,trade_type);
//			map.put("charge", charge.toString());
//			map.put("result", "1");
//			map.put("message", "支付成功");
//		} catch (Exception e) {
//			logAfter(logger, "支付失败");
//			e.printStackTrace();
//			map.put("result", 0);
//			map.put("message", "支付失败");
// 		}
//		
//		logEnd(logger);
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	/**
//	 * 申请退款，从服务端发起支付请求，获取支付凭据
//	 * @return
//	 */
//	@RequestMapping("/refundCharge")
//	@ResponseBody
//	public Object refundCharge(){
//		logBefore(logger, "申请退款pay.refundCharge");
//		Map<String,Object> map = new HashMap<String,Object>();
//		PageData pd =new PageData();
//		pd=this.getPageData();
//		String cons_id = pd.getString("cons_id");
//		String refund_reason = "";
//		BigDecimal refund_amount = new BigDecimal("0");
//		
//		/** 检查参数 begin-------*/
//		if("".equals(cons_id) || cons_id == null){
//			map.put("result", 0);
//			map.put("message", "咨询id不能为空");
//			return AppUtil.returnObject(pd, map);
//		}
//		/** 检查参数 end---------*/
//		
//		Refund refund = null;
//		try{
//			
//			PageData consData = consultService.getConsultById(pd);
//			if(consData == null){
//				map.put("result", "0");
//				map.put("message", "咨询信息不存在");
//				return AppUtil.returnObject(pd, map);
//			}
//			
//			
//			String lawer_id = consData.get("cons_lawer") + "";
//			
//			pd.put("order_lawerid", lawer_id);
//			pd.put("order_consultid", cons_id);
//			
//			PageData orderData = orderService.getOrderByLaweridAndConsid(pd);
//			if(orderData == null){
//				map.put("result", "0");
//				map.put("message", "订单信息不存在");
//				return AppUtil.returnObject(pd, map);
//			}
//			
//			String order_isrefund = orderData.get("order_isrefund")+"";
//			
//			if("1".equals(order_isrefund)){
//				map.put("result", "0");
//				map.put("message", "该订单已退款，不必重复点击");
//				return AppUtil.returnObject(pd, map);
//			}
//			
//			
//			//获取退款信息
//			PageData refundData = orderService.getRefundInfoByConsid(pd);
//			if( refundData == null ){
//				map.put("resutl","0");
//				map.put("message", "退款信息不存在");
// 				return AppUtil.returnObject(pd, map);
//			}
//			refund_reason = refundData.getString("refund_reason");
//			refund_amount = new BigDecimal(refundData.getString("refund_amount")) ;
//			
//  			List<PageData> _pd = userService.getChargeIdByConsId(pd);
//			if (_pd.size() > 0) {
//				String charge_id = _pd.get(0).getString("pay_trade_no");
//				int amount = refund_amount.multiply(new BigDecimal(100)).intValue();
//				
//				Map<String, String> meta = new HashMap<String, String>();
//		        meta.put("refund_type", Const.REFUND_TYPE_UserRefundApplication);
//		        meta.put("cons_id", cons_id);
//				
//				refund = PingppUtil.refund(charge_id,refund_reason,amount,meta);
//				
//				map.put("refund", refund.toString());
//				map.put("result", "1");
//				map.put("message", "退款成功");
//			}
//		} catch (Exception e) {
//			logAfter(logger, "退款失败");
//			e.printStackTrace();
//			map.put("result", 0);
//			map.put("message", "退款失败");
// 		}
//		
//		logEnd(logger);
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	//支付成功，接收 Webhooks 通知
//	@RequestMapping("/getChargeWebhooks")
//	@ResponseBody
//	public void getChargeWebhooks(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		logBefore(logger, "支付成功，接收 Webhooks 通知");
//		request.setCharacterEncoding("UTF8");
//        //获取头部所有信息
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            System.out.println(key+" "+value);
//        }
//        // 获得 http body 内容
//        BufferedReader reader = request.getReader();
//        StringBuffer buffer = new StringBuffer();
//        String string;
//        while ((string = reader.readLine()) != null) {
//            buffer.append(string);
//        }
//        reader.close();
//        // 解析异步通知数据
//        Event event = Webhooks.eventParse(buffer.toString());
//        logAfter(logger,"解析异步通知数据"+event.getType());
//        if ("charge.succeeded".equals(event.getType())) {
//        	Charge charge = (Charge) event.getData().getObject();
//    		String cons_id = charge.getOrderNo();
//    		int _total_fee = charge.getAmount();
//    		String channel = charge.getChannel();
//    		BigDecimal total_fee = new BigDecimal(_total_fee);
//    		total_fee = total_fee.divide(new BigDecimal(100));
//        	Map<String, String> initialMetadata = charge.getMetadata();
//        	String lawer_id = initialMetadata.get("lawer_id"); 
//        	String trade_type = initialMetadata.get("trade_type"); 
//        	PageData pd = new PageData();
//        	pd.put("cons_id", cons_id);
//        	pd.put("lawer_id", lawer_id);
//        	//订单支付
//        	if ("orderpay".equals(trade_type)) {
//            	logBefore(logger, "订单支付成功");
//            	/****************查询订单详情*************************/
//    			logAfter(logger,"查询订单详情");
//            	PageData consult = consultService.getConsultById(pd);
//            	String cons_user = consult.getString("cons_user");
//            	
//            	/****************增加交易记录*************************/
//    			logAfter(logger,"用户支付成功，添加交易记录");
//    			PageData _pd = new PageData();
//    			_pd.put("pay_consult", charge.getOrderNo());
//    			_pd.put("pay_from", lawer_id);
//    			_pd.put("pay_to", cons_user);
//    			_pd.put("pay_trade_no", charge.getId());
//    			if ("wx_pub".equals(channel)) {
//    				_pd.put("pay_type", 0);	//微信
//    			}else if("alipay_wap".equals(channel)){
//    				_pd.put("pay_type", 1);	//支付宝
//    			}else if ("upacp_wap".equals(channel)) {
//    				_pd.put("pay_type", 2);	//银联
//				}
//    			_pd.put("pay_money", total_fee);
//    			_pd.put("pay_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(charge.getTimePaid()));
//    			_pd.put("create_time", DateUtil.getTime());
//    			_pd.put("update_time", DateUtil.getTime());
//    			userService.addUserPayInfo(_pd);
//    			
//    			/****************更新咨询的状态*************************/
//      			pd.put("cons_status", Constant.CONS_STATUS_2);
//    			pd.put("cons_updatetime", DateUtil.getTime());
//    			logAfter(logger, "更新咨询状态、接单律师");
//    			consultService.updateLawerAndStatus(pd);
//    			
//    			/****************更新订单的状态*************************/
//    			pd.put("order_status", 1);
//    			logAfter(logger, "更新接单律师的订单状态");
//    			orderService.updateOrderStatus(pd);
//    			
//    			/****************更新接单失败的律师的订单状态*************************/
//    			pd.put("order_status", 2);
//    			logAfter(logger, "更新接单失败的律师的订单状态");
//    			orderService.updateOrderStatusForFail(pd);
//    			
//    			/****************消息列表插入一条新信息*************************/
//    			logAfter(logger, "插入留言");
//    			PageData lawerData = lawerService.getById(pd);
//    			String lawerName = lawerData.getString("lawyername");
//    			String msgContent = "系统提示," + lawerName + "您的接单已被用户确认";
//    			pd.put("msg_to", lawer_id);
//    			pd.put("msg_content", msgContent);
//    			pd.put("msg_createdate", DateUtil.getTime());
//    			pd.put("msg_type", 2);//消息类型:'抢的单被用户确认(to律师)'
//    			pd.put("msg_consultid", cons_id);
//    			messageService.addMessage(pd);
//    			//消息通知  极光推送
//    			String content = "咨询人" + lawerName + ",选择你作为接单律师。咨询编号：" + cons_id;
//    			JPushClientUtil.pushMessage("律途", content, lawer_id);
//    			//消息通知  微信推送
//    			logAfter(logger,"消息推送到微信");
//    			pd.put("user_id", cons_user);
//    			PageData user = userService.getByUserId(pd);
//    			String openid = user.getString("openid");
//    			String template_id = Const.WECHAT_TEMPLATE_5;
//    			String url = Const.HOST + "/order/getOrderById.do?cons_id="+cons_id;
//    			String first = "订单已被确认，请等待律师联系您";
//    			String keyword1 = cons_id;
//    			String keyword2 = consult.get("cons_price")+"";
//    			String keyword3 = consult.getString("cons_createdate");
//    			String keyword4 = "";
//    			String keyword5 = "";
//    			String remark = "查看更多，请点击详情。";
//    			PingppUtil.SendTemplateMsg(openid, template_id, url, first, keyword1, keyword2, keyword3, keyword4, keyword5, remark);
//			}else if("rewardpay".equals(trade_type)){
//				//打赏支付
//				/****************查询律师账户余额*************************/
//				logAfter(logger,"查询律师账户余额");
//				PageData _pd = lawerAccountService.getAccountByLawerId(pd);
//				BigDecimal _bal = (BigDecimal) _pd.get("bal");
//				
//				//插入一条账户交易明细
//    			if ("wx_pub".equals(channel)) {
//    				pd.put("trade_type", 0);	//微信
//    			}else if("alipay_wap".equals(channel)){
//    				pd.put("trade_type", 1);	//支付宝
//    			}else if ("upacp_wap".equals(channel)) {
//    				pd.put("trade_type", 2);	//银联
//				}
//				pd.put("pay_method", "5");//打赏
//				pd.put("lawer_id", lawer_id);
//				pd.put("order_id", cons_id);
//				pd.put("amount",  total_fee);
//				pd.put("bal", _bal);
//				pd.put("create_time", DateUtil.getTime());
//				pd.put("update_time",  DateUtil.getTime());
//				logAfter(logger, "添加一条账户收支明细");
//				lawerAccountService.addLawerAccountLog(pd);
//				
//				logAfter(logger,"账户余额修改");
//				total_fee = total_fee.multiply(new BigDecimal("0.9"));	//打赏金额的90%转入律师账户
//				total_fee = total_fee.setScale(2, BigDecimal.ROUND_HALF_UP);	//四舍五入
//				_bal = _bal.add(total_fee);
//				pd.put("bal", _bal);
//				pd.put("update_time", DateUtil.getTime());
//				lawerAccountService.updateLawerBal(pd);
//			}
//            response.setStatus(200);
//        	logEnd(logger);
//        } else if ("refund.succeeded".equals(event.getType())) {
//            response.setStatus(200);
//        } else {
//            response.setStatus(500);
//        }
//		logEnd(logger);
//	}
//	
//	
//	//退款成功，接收 Webhooks 通知
//	@RequestMapping("/getRefundWebhooks")
//	@ResponseBody
//	public void getRefundWebhooks(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		logBefore(logger, "退款成功，接收 Webhooks 通知");
//		request.setCharacterEncoding("UTF8");
//        //获取头部所有信息
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            System.out.println(key+" "+value);
//        }
//        // 获得 http body 内容
//        BufferedReader reader = request.getReader();
//        StringBuffer buffer = new StringBuffer();
//        String string;
//        while ((string = reader.readLine()) != null) {
//            buffer.append(string);
//        }
//        reader.close();
//        // 解析异步通知数据
//        Event event = Webhooks.eventParse(buffer.toString());
//        if ("charge.succeeded".equals(event.getType())) {
//        	response.setStatus(200);
//        } else if ("refund.succeeded".equals(event.getType())) {
//        	logAfter(logger,"退款成功");
//        	Refund refund = (Refund) event.getData().getObject();
//        	String charge_id = refund.getCharge();	//拿到退款的charge_id
//        	
//        	//Charge charge = Charge.retrieve(charge_id);//查询charge对象
//    		//String cons_id = charge.getOrderNo();
//        	
//        	
//			int _total_fee = refund.getAmount();
//			Map<String, String> metadata = refund.getMetadata();
//			
//			String refund_type = metadata.get("refund_type");
//			String cons_id = metadata.get("cons_id");
//			
//    		BigDecimal total_fee = new BigDecimal(_total_fee);
//    		total_fee = total_fee.divide(new BigDecimal(100));
//    		
//        	PageData pd = new PageData();
//        	pd.put("cons_id", cons_id);
//        	logAfter(logger,"订单详情查询");
//			PageData consData = consultService.getConsultById(pd);
//			String lawer_id = consData.getString("cons_lawer");
//			
//			//咨询人请求退款-->律师同意退款 走这里
//			if(Const.REFUND_TYPE_UserRefundApplication.equals(refund_type)){
//	        	pd.put("lawer_id", lawer_id);
//				 //1，更新订单状态--->4   2，更新退款标志--->1
//				pd.put("order_status", 4);
//				logAfter(logger, "更新订单的退款标志和订单状态");
//				orderService.updateOrderRefund(pd);
//				
//				 //3，更新咨询状态--->4 
//				pd.put("cons_status", 4);
//				pd.put("cons_updatetime", DateUtil.getTime());
//				logAfter(logger, "更新咨询状态");
//				consultService.updateConsultStatus(pd);
//				
// 				
//				//判断律师是否已经打款改律师
//				pd.put("order_lawerid", lawer_id);
//				pd.put("order_consultid", cons_id);
//				PageData orderData = orderService.getOrderByLaweridAndConsid(pd);
//				String order_id = orderData.get("order_id")+"";
//				
//				List<PageData> accountLogData = lawerAccountService.getAccountLogByOrderId(pd);
//				if(accountLogData.size() > 0){
//					logAfter(logger,"查询律师账户余额");
//					PageData lawerAccountData = lawerAccountService.getAccountByLawerId(pd);
//					BigDecimal _bal = new BigDecimal(lawerAccountData.get("bal") + "") ;
//					
//					//律师扣款的金额是客户要求退款金额的90%
//					BigDecimal lawer_sub = total_fee.multiply(new BigDecimal(0.9));
//					
//					//更新余额
//					//BigDecimal bal = _bal.subtract(total_fee);
//					BigDecimal bal = _bal.subtract(lawer_sub);
//					
//					//插入一条账户交易明细
//					pd.put("trade_type", 3); //确认退款
//					pd.put("pay_method", "4");//退款
//					pd.put("lawer_id", lawer_id);
//					pd.put("order_id", order_id);
//					pd.put("amount",  lawer_sub.multiply(new BigDecimal(-1)));
//					pd.put("bal", bal);
//					pd.put("create_time", DateUtil.getTime());
//					pd.put("update_time",  DateUtil.getTime());
//					logAfter(logger, "添加一条账户收支明细");
//					lawerAccountService.addLawerAccountLog(pd);
//					
//					logAfter(logger,"账户余额修改");
//					pd.put("bal", bal);
//					pd.put("update_time", DateUtil.getTime());
//					lawerAccountService.updateLawerBal(pd);
//				}
//				//向客户的微信端发送一条消息
//				pd.put("user_id", consData.getString("cons_user"));
//				PageData userData = userService.getByUserId(pd);
//				System.out.println(userData);
//				String openid = userData.getString("openid");
//				String template_id = Const.WECHAT_TEMPLATE_8;//
//				String url = Const.HOST + "/order/getOrderById.do?cons_id=" + consData.get("cons_id").toString();
//				String first = "律师已同意您的退款请求";
//				String keyword1 = total_fee+"";
//				String keyword2 = consData.getString("cons_title")+"(编号："+consData.get("cons_id").toString()+")的咨询已退款";
//				String keyword3 = DateUtil.getTime();
//				String keyword4 = "";
//				String keyword5 = "";
//				String remark = "如有疑问，请联系在线客服";
//				PingppUtil.SendTemplateMsg(openid, template_id, url, first, keyword1, keyword2, keyword3, keyword4, keyword5, remark);
//			}else if(Const.REFUND_TYPE_LawerCancelOrder.equals(refund_type)){
//				//律师直接取消接单
//				pd.putAll(metadata);
//
//				String user_id = pd.getString("user_id");
//				/** 1，更新咨询的状态
//						    0	已发布
//							1	已确认
//							2	用户自己取消
//							4	已完成
//							5	律师放弃订单*/
//				pd.put("cons_status", 5);
//				pd.put("cons_updatetime", DateUtil.getTime());
//				logAfter(logger, "更新咨询状态");
//				consultService.updateConsultStatus(pd);
//				
//				
//				/** 2，更新order的状态
//							0	已抢单
//							1	已被用户确认
//							2	抢单失败
//							3	用户取消订单
//							4	完成订单
//								律师放弃订单  */
//				pd.put("order_status", 5);
//				logAfter(logger, "更新订单的退款标志和订单状态");
//				orderService.updateOrderRefund(pd);
//				
//				/** 3，想lt_message插入一条信息*/
//				
//				String msgContent = "系统提示，您的咨询(编号:" + cons_id + ")已被取消，请重新发布";
//				
//				pd.put("msg_from", lawer_id);
//				pd.put("msg_to", user_id);
//				pd.put("msg_content", msgContent);
//				pd.put("msg_createdate", DateUtil.getTime());
//				pd.put("msg_type", 1);//消息类型待定
//				pd.put("msg_consultid", cons_id);
//				
//				
//				logAfter(logger, "插入留言");
//				messageService.addMessage(pd);
//
//				
//				PageData lawerData = lawerService.getById(pd);
//				String lawerName=lawerData.getString("lawyername");
//				
//				
//				PageData _pd = new PageData();
//				_pd.put("user_id", user_id);
//				logAfter(logger, "查询用户的openid");
//				PageData _user = userService.getByUserId(_pd);
//
//				String openid = _user.getString("openid");
//				String template_id = Const.WECHAT_TEMPLATE_2;//
//				String url = Const.HOST + "/order/getOrderById.do?cons_id=" + cons_id ;
//				String first = lawerName + "律师取消了本次接单（编号：" + cons_id + "）";
//				String keyword1 = "身体不适";
//				String keyword2 = DateUtil.getTime();
//				String keyword3 = "被律师取消";
//				String keyword4 = "";
//				String keyword5 = "";
//				String remark = "如有疑问，请联系在线客服";
//				PingppUtil.SendTemplateMsg(openid, template_id, url, first, keyword1, keyword2, keyword3, keyword4, keyword5, remark);
//
//			}
//			response.setStatus(200);
//        } else {
//            response.setStatus(500);
//        }
//		logEnd(logger);
//	}
//	
//	
//	/**
//	 * 企业付款，从服务端发起支付请求，获取支付凭据
//	 * @return
//	 */
//	@RequestMapping("/createTransfer")
//	@ResponseBody
//	public Object createTransfer(){
//		logBefore(logger, "企业付款pay.createTransfer");
//		Map<String,Object> map = new HashMap<String,Object>();
//		PageData pd =new PageData();
//		pd=this.getPageData();
//		String openid = "";	//wx_pub 微信用到
//		String channel = pd.getString("channel");	//wx_pub 微信 unionpay 企业付款
//		String total_fee = pd.getString("total_fee");
//		total_fee = "1";
//		int amount = new BigDecimal(total_fee).multiply(new BigDecimal("100")).intValue();
//		
//		Transfer transfer = null;
//        Map<String, Object> extra = new HashMap<String, Object>();
//		
//		try{
//			channel = "unionpay";
//			if ("wx_pub".equals(channel)) {
//				openid = pd.getString("openid");
//			}
//			if ("unionpay".equals(channel)) {
//				String card_number = pd.getString("card_number");
//				String user_name = pd.getString("user_name");
//				String open_bank = pd.getString("open_bank");
////				String prov = pd.getString("prov");
////				String city = pd.getString("city");
//				card_number = "6212261202017303919";
//				user_name = "章杨";
//				open_bank = "工商银行";
////				prov = "浙江";
////				city = "杭州";
//				
//	            extra.put("card_number", card_number);// 收款人银行卡号或者存折号
//	            extra.put("user_name", user_name);// 收款人姓名
//	            extra.put("open_bank", open_bank);// 开户银行
////	            extra.put("prov", prov);// 省份
////	            extra.put("city", city);// 城市
//	            extra.put("term_type", "07");// 企业付款（银行卡）业务使用场景, 07:互联网, 08:移动端
//			}
//			transfer = PingppUtil.transfer(openid, amount, channel, extra);
//			
//			if (transfer == null) {
//				map.put("result", 0);
//				map.put("message", "企业付款失败");
//			}else {
////				System.out.println(transfer.getId());
////				transfer = Transfer.retrieve(transfer.getId());
//				
//				logAfter(logger,"律师申请提现成功，修改账户余额");
//				lawerAccountService.updateLawerBal(pd);
//				map.put("transfer", transfer.toString());
//				map.put("result", 1);
//				map.put("message", "企业付款成功");
//			}
//		} catch (Exception e) {
//			logAfter(logger, "企业付款失败");
//			e.printStackTrace();
//			map.put("result", 0);
//			map.put("message", "企业付款失败");
// 		}
//		
//		logEnd(logger);
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	//企业付款成功，接收 Webhooks 通知
//	@RequestMapping("/getTransferWebhooks")
//	@ResponseBody
//	public void getTransferWebhooks(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		logBefore(logger, "企业付款，接收 Webhooks 通知");
//        request.setCharacterEncoding("UTF8");
//        String signature = "";
//        //获取头部所有信息
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            System.out.println(key+" "+value);
//            
//            // 签名数据请从 request 的 header 中获取, key 为 X-Pingplusplus-Signature (请忽略大小写, 建议自己做格式化)
//            signature = request.getHeader("x-pingplusplus-signature");
//        }
//        
//        // 获得 http body 内容
//        BufferedReader reader = request.getReader();
//        StringBuffer buffer = new StringBuffer();
//        String string;
//        while ((string = reader.readLine()) != null) {
//            buffer.append(string);
//        }
//        reader.close();
//
//        boolean result = PingppUtil.verifyData(buffer.toString(), signature, PingppUtil.getPubKey());
//        System.out.println("验签结果：" + (result ? "通过" : "失败"));
//        
//        if (result) {
//        	// 解析异步通知数据
//            Event event = Webhooks.eventParse(buffer.toString());
//            if ("transfer.succeeded".equals(event.getType())) {
//            	logAfter(logger,"提现成功");
//            	Transfer transfer = (Transfer) event.getData().getObject();
//            	PageData pd = new PageData();
//				pd.put("withdraw_handletime", DateUtil.getTime());
//				pd.put("withdraw_transfersid", transfer.getId());
//				lawerAccountService.updateisHandle(pd);
//            	response.setStatus(200);
//            } else {
//                response.setStatus(500);
//            }
//		}
//		logEnd(logger);
//	}
//	
//	
//	/**
//	 * 银行卡身份认证
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("/AuthenticationIdAndCard")
//	@ResponseBody
//	public void AuthenticationIdAndCard(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		logBefore(logger, "银行卡身份认证");
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		//设置 API-Key
//		Pingpp.apiKey = PingppUtil.apiKey_test;
////		Map<String,String> data = new HashMap<String,String>();
//        String type = "bank_card";
//        String app = PingppUtil.appId_upacp;
////        data.put("id_name", pd.getString("id_name"));
////        data.put("id_number", pd.getString("id_number"));
////        data.put("card_number", pd.getString("card_number"));
////        data.put("phone_number", pd.getString("phone_number"));
//		JSONObject data = new JSONObject();
//        data.put("id_name", "张三");
//        data.put("id_number", "340823199101011234");
//        data.put("card_number", "6214666633339999");
//        data.put("phone_number", "18688889999");
//
//		Map<String,Object> postData = new HashMap<String,Object>();
//		postData.put("type", type);
//		postData.put("app", app);
////		postData.put("data", data);
//		
//		String signature = "";
//		JSONObject jsonObject = JSONObject.fromObject(postData);
//		signature = PingppUtil.sign(jsonObject.toString(), PingppUtil.privateKey_pkcs8, "utf-8");
//        String result = HttpUtil.doPostHeader("https://api.pingxx.com/v1/identification", signature, postData);
//        System.out.println(result);
//		logEnd(logger);
//	}
//}
