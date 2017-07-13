package com.tianer.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.EbotongSecurity;
import com.tianer.util.FileUtil;
  

 /**
 * LoginHandlerInterceptor
 * 接口拦截=处理
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
   		String path = request.getServletPath();
  		//进行ip拦截
		if(FileUtil.readTxtFile("C:\\ipxz.txt").contains(getIp(request)) ){
			return false;
		}
		System.out.println(DateUtil.getTime()+"（"+getIp(request)+"）接口地址："+path);
//   	String sign=request.getParameter("jy_sign");
//		if(sign != null && !sign.equals("")){
//			String zhuansing=EbotongSecurity.ebotongDecrypto(sign);
//			System.out.println("参数sign"+zhuansing); 
// 		}
  		if(!Const.NOTLANJIEJIEKOU.contains(path) &&  Const.STORELANJIEJIEKOU.contains(path) ){//商家端登录
 			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();	
 			if(session.getAttribute(Const.SESSION_STORE_USER) == null){
  				response.sendRedirect(request.getContextPath()+"/storepc/goLogin.do");
 				return false;
			}
 		}else if( !Const.NOTLANJIEJIEKOU.contains(path) &&  Const.ZHIHUILANJIEJIEKOU.contains(path) ){//总后台登录
  			Subject currentUser = SecurityUtils.getSubject();  
 			Session session = currentUser.getSession();	
 			if(session.getAttribute(Const.SESSION_USER) == null){
  				response.sendRedirect(request.getContextPath()+"/zhihui_index.do");
 				return false;
 			}
		}else if(!Const.NOTLANJIEJIEKOU.contains(path) && path.matches(Const.WEIXINLANJIEJIEKOU)  ){//微信端登录
//  			Subject currentUser = SecurityUtils.getSubject();  
// 			Session session = currentUser.getSession();	
// 			if(session.getAttribute(Const.SESSION_H5_USER) == null){
//   				response.sendRedirect(request.getContextPath()+"/html_member/toLoginWx.do");
// 				return false;
// 			}
		}else if(path.contains("storeapp_payhHstory/sureConfirmed.do") || path.contains("app_pay_history/thirdPartyPay")){//收银,公众号支付
			String session_orderid=request.getParameter("session_orderid");
 			String in_jiqi=request.getParameter("in_jiqi");
 			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();	
			Object seesion_onlyorder=session.getAttribute(Const.SESSION_ORDER);//订单号
 			//1-C会员端，2-B商家端，3会员pc端，4-商家pc端，5-微信公众号端，6-PC总后台 ，7-总后台端
			if(in_jiqi != null && "4".contains(in_jiqi)){
				System.out.println("传的session="+session_orderid+"***服务器session="+seesion_onlyorder);
				if(seesion_onlyorder == null){
					System.out.println("pc端收银 session_orderid为空");
 					return false;
				}else if(!String.valueOf(seesion_onlyorder).equals(session_orderid)){
					System.out.println("pc端收银 session_orderid不相等");
					return false;
				}else{
					//移除sessionorderid
					SecurityUtils.getSubject().getSession().removeAttribute(Const.SESSION_ORDER);
				}
			}
 		}else if(path.contains("goShQyStore.do")){
				String session_orderid=BaseController.getTimeID();
 				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();	
			    session.setAttribute(Const.SESSION_ORDER, session_orderid);
 		}else if(path.contains("goPay.do")){
				String session_orderid=BaseController.getTimeID();
 				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();	
				session.setAttribute(Const.SESSION_ORDER, session_orderid);
 		}else if(Const.ONLYPOSTURL.contains(path)){
			//判断是否为post请求
 			String method = request.getMethod();
 			if("POST".equals(method)){
 				return true;
			}else{
				response.sendRedirect(request.getContextPath()+Const.notform);//跳转到报错页面
				return false;
			}
 			
		}else if(path.matches(Const.NO_INTERCEPTOR_PATH)){ 
 			return true;
 		}else{
 			response.sendRedirect("https://www.jiuyuvip.com");
			return false;
  		}
 		return true;
 	}
	
	
	/*
	 * 获取IP
	 */
	public static String getIp(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
 		 //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		 if(ipAddress!=null && ipAddress.length()>15){
		 //"***.***.***.***".length() = 15
		 if(ipAddress.indexOf(",")>0){
		 ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
		 }
		 }
		return ipAddress;
	}
}
