package com.tianer.controller.memberapp.member;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.memberapp.AppFriendService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storepc.liangqin.homepage.VIPService;
import com.tianer.service.storepc.stotr.StorepcService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.FileUpload;
import com.tianer.util.MD5;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;
import com.tianer.util.alipaypay.AlipayConfig;

/** 
 * 
* 类名称：MemberController   
* 类描述：   会员（备注自己的名字和时间）
* 创建人：魏汉文  
* 创建时间：2016年5月26日 下午3:46:49
 */
@Controller("appMemberController")
@RequestMapping(value="/app_member")
public class MemberController extends BaseController {
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService appMember_wealthhistoryService;
	
	@Resource(name="vipService")
	private VIPService vipService;
	
	
	@Resource(name="appFriendService")
	private AppFriendService appFriendService;
	
	

	/**
	 * 判断短信验证码是否正确
	 * app_member/IsOKMessageCode.do?
	 * 
	 * messagecode 验证码
	 * codetype 1-注册验证，2-绑卡/解绑验证
	 * 
	 */
	@RequestMapping(value="/IsOKMessageCode")
	@ResponseBody
	public Object IsOKMessageCode(){
 		Map<String,Object> map = new HashMap<String,Object>();
     	String result="1";
  		String message="支付成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String messagecode=pd.getString("messagecode");
			String codetype=pd.getString("codetype");
			//1-会员注册，2-绑定卡片/解绑卡片银行卡，。。。。
			if(codetype.equals("1")){
				String sessioncode=(String) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_MEMBER_ZHUCECODE);
				if(!messagecode.equals(sessioncode)){
					result="0";
					message="验证码错误";
				}
 			}else if(codetype.equals("2")){
 				String sessioncode=(String) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_MEMBER_CARDCODE);
				if(!messagecode.equals(sessioncode)){
					result="0";
					message="验证码错误";
				}
			}
 			
    	} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	/**
	 * 会员手机号注册获取验证码
	 * app_member/findPhoneByZhuCe.do
	 * 
	 * tuxingcode 图形验证码
	 * phone 手机号
 	 * 
	 */
	@RequestMapping(value="/findPhoneByZhuCe") 
	@ResponseBody
	public Object FindPhoneByZhuCe(HttpServletRequest request){
 		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		String result="1";
		String message="验证码已发至你手机，请注意查收";
		String code=StringUtil.getSixRandomNum();
		String validate = (String)request.getSession().getAttribute("validateCode");  //获取缓存验证码
		try{
			pd=this.getPageData();
			if(appMemberService.detailByPhone(pd) != null){
 				map.put("result", "0");
 				map.put("message", "当前手机已注册，请前往登陆");
 				map.put("data", "");
  		 		return map;
 			}
  			 //进行图形验证验证toLowerCase()不区分大小写
			if(pd.getString("tuxingcode") == null || pd.getString("tuxingcode").equals("") || validate == null || !pd.getString("tuxingcode").equalsIgnoreCase(validate)){
 				map.put("result", "0");
		 		map.put("message", "图形验证码不能为空/错误");
		 		map.put("data", "");
		  		return map;
			} 
			//验证是否可以发送验证码---
  			String phone=pd.getString("phone");
   			PageData issend=TongYong.Okmessage(phone,"1");
  			if(issend.get("result").equals("0")){
  				map.put("result", "0");
		 		map.put("message",issend.getString("message"));
		 		map.put("data", "");
		 		return map;
  			}
  		    SmsUtil.sendZcCode(phone, code);
 		    map.put("data", code);
 		   try {
 	  			PageData _pd=new PageData();
	  			_pd.put("phone", phone);
	  			_pd.put("code", code);
	  			_pd.put("content", "注册获取验证码");
 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
	  			_pd=null;
			} catch (Exception e) {
				// TODO: handle exception
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统错误";
		}
		map.put("message", message);
	    map.put("result", result);
		return map;
	}
	
	
	
	/**
	 * 会员快速登录获取验证码
	 * app_member/quickLoginGetCode.do
	 * 
	 * phone   手机号码
	 * token  加密字符串（  kuaijie#*jiuyuvip^&20171001@#(%  +时间戳）
	 * time   加密时用到的的时间戳
 	 */
	@RequestMapping(value="/quickLoginGetCode") 
	@ResponseBody
	public Object QuickLoginGetCode(HttpServletRequest request){
 		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		String result="1";
 		String message="验证码已发至你手机，请注意查收";
		String code=StringUtil.getSixRandomNum();
 		try{
			pd=this.getPageData();
			String apptoken=pd.getString("token");
			String time=pd.getString("time");
			String phone=pd.getString("phone");
 			String javatoken=MD5.md5("kuaijie#*jiuyuvip^&20171001@#(%"+time);
 			long n=((new Date()).getTime()-Long.parseLong(time))/1000;
 			if(javatoken.equals(apptoken)  &&  n <= Const.tokentime ){
				 //验证是否可以发送验证码---
	    		 PageData issend=TongYong.Okmessage(phone,"1");
	  			 if(issend.get("result").equals("0")){
	  				map.put("result", "0");
			 		map.put("message",issend.getString("message"));
			 		map.put("data", "");
			 		return map;
	  			 }
				 SmsUtil.loginCode(phone, code);
				 map.put("data",code);
				 try {
		 	  			PageData _pd=new PageData();
			  			_pd.put("phone", phone);
			  			_pd.put("code", code);
			  			_pd.put("content", "快捷短信登录获取验证码");
		 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
			  			_pd=null;
				 } catch (Exception e) {
						// TODO: handle exception
				 }
			}else{
				map.put("result", "0");
 				map.put("message", "操作过于频繁，请稍后再尝试一下");
 				map.put("data", "");
  		 		return map;
			}
      	} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统错误";
		}
		map.put("message", message);
	    map.put("result", result);
 		return map;
	}
	
	
	
	/**
 	 * 忘记密码获取验证码，每个电话获取验证码次数有限制
 	 * app_member/forgotPasswordGetCode.do
	 * 
	 * phone  忘记密码的手机号
	 * token  加密字符串（ jiuyu.*654321)*^!bjgya%^~ +  时间戳）
	 * time   加密时用到的的时间戳
	 * 
	 */
	@RequestMapping(value="/forgotPasswordGetCode")
	@ResponseBody
	public Object ForgotPasswordGetCode() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="验证码已发送，注意查收";
		String code=StringUtil.getSixRandomNum();
  		PageData pd = new PageData();
 		try {
 			pd=this.getPageData();
 			String apptoken=pd.getString("token");
			String time=pd.getString("time");
			String phone=pd.getString("phone");
  			if(ServiceHelper.getAppMemberService().detailByPhone(pd) == null){
 				map.put("result", "0");
 				map.put("message", "当前手机未注册，请前往注册");
 				map.put("data", "");
  		 		return map;
  			}else{
   				String javatoken=MD5.md5("jiuyu.*654321)*^!bjgya%^~"+time);
   				long n=((new Date()).getTime()-Long.parseLong(time))/1000;
   				if(javatoken.equals(apptoken)  &&  n <= Const.tokentime ){
  					//验证是否可以发送验证码：1-有次数限制
  	 	    		PageData issend=TongYong.Okmessage(phone,"1");
  	 	  			if(issend.get("result").equals("0")){
  	 	  				map.put("result", "0");
  	 			 		map.put("message",issend.getString("message"));
  	 			 		map.put("data", "");
  	 			 		return map;
  	 	  			}
	  	 	  		SmsUtil.sendXgCode(phone, code);
	 	  			map.put("data", code);
	 	  			try {
		 	  			PageData _pd=new PageData();
			  			_pd.put("phone", phone);
			  			_pd.put("code", code);
			  			_pd.put("content", "忘记密码获取验证码");
		 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
			  			_pd=null;
					 } catch (Exception e) {
							// TODO: handle exception
					 }
  	 	  			
  				}else{
  					map.put("result", "0");
  	 				map.put("message", "操作过于频繁，请稍后再尝试一下");
  	 				map.put("data", "");
  	  		 		return map;
  				}
   			}
		} catch (Exception e) {
 			logger.error(e.toString(), e);
			result="0";
			message="系统错误";
		}
 		map.put("message", message);
	    map.put("result", result);
	    pd=null;
 		return map;
	}
	 
	
	
	/**
	 * 手机号码注册会员
	 * app_member/register.do
	 * 
	 * phone 注册手机号
	 * password 注册密码
	 * recommended 推荐人ID
	 * recommended_type 推荐人类型
  	 */
	@RequestMapping(value="/register")
	@ResponseBody
	public Object Register() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="注册成功";
		String member_id="";
 		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
 			//判断手机号码是否为空
  	 		String phone=pd.getString("phone"); 
 	 		String password=pd.getString("password"); 
  			if(phone == null || phone.trim().equals("") || password == null || password.trim().equals("")){
  				map.put("result", "0");
		 		map.put("message", "手机号码不能为空");
		 		map.put("data", "");
		 		return map;
  			} 
  			if( password == null || password.trim().equals("") || password.length() <6){
  				map.put("result", "0");
		 		map.put("message", "密码至少为6位！");
		 		map.put("data", "");
		 		return map;
  			} 
  			pd.put("name", phone.substring(0, 3)+"****"+phone.substring(7, 11));
//  		String recommended=pd.getString("recommended");//推荐人ID
// 	 		String recommended_type=pd.getString("recommended_type");//推荐人类型:1-商家，2-会员
 	 		pd.put("zhuce_shebei", "1");
    		pd=TongYong.saveMember(pd,pd.getString("recommended"), pd.getString("recommended_type"));
  			if(pd.getString("flag").equals("false")){
 				map.put("result", "0");
 				map.put("message", pd.getString("message"));
 				map.put("data", "");
  		 		return map;
 			}
  			member_id=pd.getString("member_id");
 		} catch (Exception e) {
  			logger.error("注册"+e.toString());
			member_id=BaseController.getMemberID(pd.getString("phone"));
		}
   		map.put("result", result);
		map.put("message", message);
		map.put("data", member_id);
		pd=null;
 		return map;
	}
 	
	
	/**
	 * 获取推荐过当前手机号码的所有手机
	 *  app_member/getTuijianPhone.do 
	 *  
 	 * be_phone  被推荐人的手机号码
  	 */
	@RequestMapping(value="/getTuijianPhone")
	@ResponseBody
	public Object GetTuijianPhone() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			 List<PageData> varList=appMemberService.listAllTuiJian(pd);
			 if(varList.size() == 0){
				 map.put("data", "");
			 }else{
				 for(PageData e : varList){
					 if(e != null && e.getString("type") != null && e.getString("id") != null){
						 if(e.getString("type").equals("1")){
							 pd.put("store_id", e.getString("id"));
							 pd=appStoreService.findById(pd);
							 if(pd != null){
 								 e.put("phone", pd.getString("store_name"));
							 }else{
								 pd=new PageData();
							 }
	 					 }else  if(e.getString("type").equals("2")){
							 pd.put("member_id", e.getString("id"));
							 pd=appMemberService.findById(pd);
							 if(pd != null){
								 e.put("phone", pd.getString("phone"));
							 }else{
								 pd=new PageData();
							 }
 						 }
					 }
 				 }
				 map.put("data", varList);
			 }
			 varList=null;
 		}catch(Exception e){
			logger.error(e.toString(), e);
			message="获取失败";
			result="0";
		}
  		map.put("result", result);
 		map.put("message", message);
 		pd=null;
  		return map;
	}
	
	@Resource(name="storepcService")
	private StorepcService storepcService;
	
	/**
	 * 获取注册的验证码
 	 *			获取图形验证码http://www.jiuyuvip.com/zhsh/verifyCodeServlet，使用httpssession缓存
 	 *app_member/getCode.do
 	 *
 	 *phone 手机号码
 	 *type  验证码类型
 	 *user_type 获取用户类型
 	 *tuxingcode  图形验证码
 	 *in_position 所在机器
	 */
	@RequestMapping(value="/getCode")
	@ResponseBody
	public Object GetCode(HttpServletRequest request) throws Exception{
  		Map<String,Object> map = new HashMap<String,Object>();
   	    String validate = (String)request.getSession().getAttribute("validateCode");  //获取缓存验证码
 		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		String code=StringUtil.getSixRandomNum();
 		try{
			pd = this.getPageData();
   			String phone=pd.getString("phone");//注册手机号
			String type=pd.getString("type");//获取验证码的类型
			String user_type=pd.getString("user_type");//用户类型
			String in_position=pd.getString("in_position");//所在机器
			String tuxingcode=pd.getString("tuxingcode");//图形验证码
			if(type == null || phone ==null ){
 					map.put("result", "0");
			 		map.put("message", "参数不能为空");
			 		map.put("data", "");
			 		return map;
  			} 
			if(type.equals("1")){//注册
					//验证是否可以发送验证码---
		  			PageData issend=TongYong.Okmessage(phone,"1");
		  			if(issend.get("result").equals("0")){
		  				map.put("result", "0");
				 		map.put("message",issend.getString("message"));
				 		map.put("data", "");
				 		return map;
		  			}
		  			//--
				    boolean zhuceflag=false;
 				    if(user_type != null && !user_type.equals("")){
				    	   if(user_type.equals("1")){
//					    		    pd.put("registertel_phone", phone);
//									PageData store = storepcService.listNamePwd(pd);//判断商家
//					 				if(store == null){
//					 					zhuceflag=true;
//					 				} 
//					 				store=null;
				    		   //将验证码放到session里
//								shirosession.setAttribute(Const.SESSION_STORE_CARDCODE , code);
				    		   
				    	   }else  if(user_type.equals("2")){
				    		   pd=appMemberService.findSMSLogin(pd);
				    		   if(pd == null ){
				    			   zhuceflag=true;
   					 			} 
				    		    pd=null;
				    	   }
				    	   if(zhuceflag){
 							    //进行图形验证验证toLowerCase()不区分大小写
 								if(tuxingcode == null || tuxingcode.equals("") || validate == null || !tuxingcode.equalsIgnoreCase(validate)){
 									//获取电话集合--移除当前的电话
						 			for(int i=0;i<Const.xzphone.size();i++){  
							            if(Const.xzphone.get(i).equals(phone)){  
							            	Const.xzphone.remove(i);  
							            }  
							        }
 									map.put("result", "0");
							 		map.put("message", "图形验证码不能为空/错误");
							 		map.put("data", "");
							  		return map;
 								}else{
// 									 SecurityUtils.getSubject().getSession().removeAttribute("validateCode");
								}
								//图形码验证结束
				    		    SmsUtil.sendZcCode(phone, code);
				    		    map.put("data", code);
				    		    //将验证码放到session里
 				    		    SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_MEMBER_ZHUCECODE , code);
 				    		   try {
 					 	  			PageData _pd=new PageData();
 						  			_pd.put("phone", phone);
 						  			_pd.put("code", code);
 						  			_pd.put("content", "注册获取验证码");
 					 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
 						  			_pd=null;
 								 } catch (Exception e) {
 										// TODO: handle exception
 								 }
				    	   }else{
				    		    //获取电话集合--移除当前的电话
					 			for(int i=0;i<Const.xzphone.size();i++){  
						            if(Const.xzphone.get(i).equals(phone)){  
						            	Const.xzphone.remove(i);  
						            }  
						        }
				    		    map.put("result", "0");
						 		map.put("message", "当前手机已注册，请前往登陆！");
						 		map.put("data", "");
						  		return map;
 				    	   } 
 				    }
   			}
			else if(type.equals("2")){//忘记密码
  				//验证是否可以发送验证码---
  	  			PageData issend=TongYong.Okmessage(phone,"1");
  	  			if(issend.get("result").equals("0")){
  	  				map.put("result", "0");
  			 		map.put("message",issend.getString("message"));
  			 		map.put("data", "");
  			 		return map;
  	  			}
  	  			//--
 				 //system.out.println("获取验忘记密码");
 				 boolean zhuceflag=false;
 				 if(user_type != null && !user_type.equals("")){
			    	   if(user_type.equals("1")){
			    		    pd.put("registertel_phone", phone);
							PageData store = storepcService.listNamePwd(pd);//判断商家
			 				if(store != null){
			 					zhuceflag=true;
  			 				}else{
			 					store=storepcService.getOperateLogin(pd);//获取操作员的信息
			 					if(store != null){
			 						zhuceflag=true;
 			 					} 
 				 			}
			 				store=null;
		    	   }else{
		    		   pd=appMemberService.findSMSLogin(pd);
		    		   if(pd != null ){
		    			   zhuceflag=true;
 			 			} 
		    		    pd=null;
		    	   }
			    } 
 				if(zhuceflag){
 					SmsUtil.sendXgCode(phone, code);
 					map.put("data", code);
 					try {
			 	  			PageData _pd=new PageData();
				  			_pd.put("phone", phone);
				  			_pd.put("code", code);
				  			_pd.put("content", "忘记密码获取验证码");
			 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
				  			_pd=null;
					} catch (Exception e) {
								// TODO: handle exception
					}
 				}else{
 					//获取电话集合--移除当前的电话
		 			for(int i=0;i<Const.xzphone.size();i++){  
			            if(Const.xzphone.get(i).equals(phone)){  
			            	Const.xzphone.remove(i);  
			            }  
			        }
 					map.put("result", "0");
				 	map.put("message", "当前手机未注册，请前往注册！");
				 	map.put("data", "");
				  	return map;
  				}
    		}
			else if(type.equals("3")){//提现
   				//system.out.println("提现获取验证码");
				SmsUtil.ownForCode(phone, code);
				map.put("data", code);
				try {
	 	  			PageData _pd=new PageData();
		  			_pd.put("phone", phone);
		  			_pd.put("code", code);
		  			_pd.put("content", "提现获取验证码");
	 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
		  			_pd=null;
				} catch (Exception e) {
							// TODO: handle exception
				}
			}else if(type.equals("4")){//充值
				//system.out.println("充值获取验证码");
				SmsUtil.ownForCode(phone, code);
				map.put("data", code);
				try {
	 	  			PageData _pd=new PageData();
		  			_pd.put("phone", phone);
		  			_pd.put("code", code);
		  			_pd.put("content", "充值获取验证码");
	 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
		  			_pd=null;
				} catch (Exception e) {
							// TODO: handle exception
				}
			}else if(type.equals("5")){//修改密码
				 //system.out.println("修改密码获取验证码");
				 SmsUtil.sendXgCode(phone, code);
				 map.put("data", code);
				 try {
		 	  			PageData _pd=new PageData();
			  			_pd.put("phone", phone);
			  			_pd.put("code", code);
			  			_pd.put("content", "修改密码获取验证码");
		 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
			  			_pd=null;
					} catch (Exception e) {
								// TODO: handle exception
					}
			}
			else if(type.equals("6")){//修改手机号
				 //system.out.println("修改手机号获取验证码");
  				 if(user_type.equals("2")){
					pd = appMemberService.findByPhone(pd);//会员
					if(pd != null){
						phone = pd.getString("phone");
 						SmsUtil.inforEdit(phone, code);//换绑手机号码
 						map.put("data", code);
 						try {
 			 	  			PageData _pd=new PageData();
 				  			_pd.put("phone", phone);
 				  			_pd.put("code", code);
 				  			_pd.put("content", "修改手机号获取验证码");
 			 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
 				  			_pd=null;
 						} catch (Exception e) {
 									// TODO: handle exception
 						}
					}else{
						map.put("data", "");
					}
 				}else if(user_type.equals("1")){
					String storeid = pd.getString("store_id");
					pd = storepcService.findByStorephone(storeid);//商家
					if(pd != null){
						phone = pd.getString("registertel_phone");
 						SmsUtil.inforEdit(phone, code);//换绑手机号码
 						map.put("data", code);
 						try {
 			 	  			PageData _pd=new PageData();
 				  			_pd.put("phone", phone);
 				  			_pd.put("code", code);
 				  			_pd.put("content", "修改手机号获取验证码");
 			 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
 				  			_pd=null;
 						} catch (Exception e) {
 									// TODO: handle exception
 						}
					}else{
						map.put("data", "");
					}
 				}else{
 					map.put("data", "");
 				}
  				pd=null;
 			}
			else if(type.equals("7")){//绑定银行卡,支付宝
				 //验证是否可以发送验证码---
	  			 PageData issend=TongYong.Okmessage(phone,"2");
	  			 if(issend.get("result").equals("0")){
	  				map.put("result", "0");
			 		map.put("message",issend.getString("message"));
			 		map.put("data", "");
			 		return map;
	  			 }
 				 //system.out.println("绑定银行卡获取验证码");
 				 SmsUtil.TXNumberCode(phone, code);
				 if(in_position == null){
					 map.put("data", code);
				 } 
				 	try {
		 	  			PageData _pd=new PageData();
			  			_pd.put("phone", phone);
			  			_pd.put("code", code);
			  			_pd.put("content", "绑定银行卡获取验证码");
		 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
			  			_pd=null;
					} catch (Exception e) {
								// TODO: handle exception
					}
				 message="验证码已发至注册手机号上";
				//将验证码放到session里
				 SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_MEMBER_CARDCODE , code);
   	  		}
			else if(type.equals("8")){//短信登录 
	  		    //验证是否可以发送验证码---
	  			PageData issend=TongYong.Okmessage(phone,"1");
	  			if(issend.get("result").equals("0")){
	  				map.put("result", "0");
			 		map.put("message",issend.getString("message"));
			 		map.put("data", "");
			 		return map;
	  			}
	  			//--
	  			 //system.out.println("短信登录获取验证码");
	  			 boolean zhuceflag=false;
	  			 if(user_type != null && !user_type.equals("")){
			    	   if(user_type.equals("1")){
				    		   pd.put("registertel_phone", phone);
								PageData store = storepcService.listNamePwd(pd);//判断商家
				 				if(store != null){
				 					zhuceflag=true;
 				 				}else{
				 					store=storepcService.getOperateLogin(pd);//获取操作员的信息
				 					if(store != null){
				 						zhuceflag=true;
 				 					} 
 				 				}
				 				store=null;
			    	   }else{
			    		   pd=appMemberService.findSMSLogin(pd);
			    		   if(pd != null ){
			    			   zhuceflag=true;
 				 			} 
 			    	   }
			    } 
	  			if(zhuceflag){
	  				SmsUtil.loginCode(phone, code);
	  				map.put("data", code);
	  				try {
			 	  			PageData _pd=new PageData();
				  			_pd.put("phone", phone);
				  			_pd.put("code", code);
				  			_pd.put("content", "短信登录获取验证码");
			 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
				  			_pd=null;
						} catch (Exception e) {
									// TODO: handle exception
						}
 				}else{
 					//获取电话集合--移除当前的电话
		 			for(int i=0;i<Const.xzphone.size();i++){  
			            if(Const.xzphone.get(i).equals(phone)){  
			            	Const.xzphone.remove(i);  
			            }  
			        }
 					map.put("result", "0");
				 	map.put("message", "当前手机未注册，请前注册！！");
				 	map.put("data", "");
				  	return map;
  				}
	  			pd=null;
    	 	}
	  		else if(type.equals("9")){//收银时验证的验证码
   	 			//system.out.println("收银时验证的验证码=="+code);
   	 			SmsUtil.payShouYinCode(phone, code);
    	 		map.put("data", code);
    	 		try {
	 	  			PageData _pd=new PageData();
		  			_pd.put("phone", phone);
		  			_pd.put("code", code);
		  			_pd.put("content", "收银时获取验证码");
	 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
		  			_pd=null;
				} catch (Exception e) {
							// TODO: handle exception
				}
   	 		}
	  		else if(type.equals("10")){//删除绑定的支付宝/银行卡
				 //验证是否可以发送验证码---
	  			 PageData issend=TongYong.Okmessage(phone,"2");
	  			 if(issend.get("result").equals("0")){
	  				map.put("result", "0");
			 		map.put("message",issend.getString("message"));
			 		map.put("data", "");
			 		return map;
	  			 }
 	  			 System.out.println("11"+code);
				 SmsUtil.TXNumberCode(phone, code);
				 if(in_position == null){
					 map.put("data", code);
				 } 
				   try {
		 	  			PageData _pd=new PageData();
			  			_pd.put("phone", phone);
			  			_pd.put("code", code);
			  			_pd.put("content", "删除绑定的支付宝/银行卡获取验证码");
		 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
			  			_pd=null;
					} catch (Exception e) {
								// TODO: handle exception
					}
				 message="验证码已发至注册手机号上";
				//将验证码放到session里
				 SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_MEMBER_CARDCODE , code);
  	  		}
			
 		}catch(Exception e){
			logger.error(e.toString(), e);
			message="获取失败";
			result="0";
		}
   		map.put("result", result);
 		map.put("message", message);
    	return map;
	}
	
	
	
	/**
	 * 判断该手机号是否注册过
	 * 魏汉文20160620
	 */
	@RequestMapping(value="/isRegister")
	@ResponseBody
	public Object IsRegister(){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="可以注册";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			pd=appMemberService.detailByPhone(pd);
			if(pd != null){
				result="0";
				message="当前手机已注册，请前往登陆";
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		pd=null;
		return map;
	}
	
	
	/**
	 * 手机号码登陆
	 * app_member/login.do?
	 * phone 登录账号
	 * password 密码
 	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Object login(){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="登陆成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(appMemberService.detailByPhone(pd) == null){
				map.put("data", "");
				map.put("result", "0");
				map.put("message", "当前手机未注册，请前往注册");
		  		return map;
			}
 			pd.put("loginpassword", MD5.md5(pd.getString("password")));
			pd=appMemberService.findByLogin(pd);
			if(pd == null ){
				map.put("data", "");
				map.put("result", "0");
				map.put("message", "账号或密码错误");
		  		return map;
 			}else{
				//更新登陆时间
 				pd.put("islogin","1");
				appMemberService.edit(pd);
				pd.remove("logindate");
				pd.remove("islogin");
				map.put("data", appMemberService.findById(pd));
				pd=null;
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	
	
	
	
	
	
//
//	/**
//	 * 会员手机号快捷短信登录获取验证码
//	 * app_member/kuaiJieLoginGEetCode.do
//	 * 
// 	 * phone 手机号
//	 * appuuid app的唯一标示ID
//	 * 
//	 */
//	@RequestMapping(value="/kuaiJieLoginGEetCode") 
//	@ResponseBody
//	public Object KuaiJieLoginGEetCode(String phone,String appuuid){
// 		Map<String,Object> map = new HashMap<String,Object>();
// 		String result="1";
//		String message="验证码已发至你手机，请注意查收";
//		String code=StringUtil.getSixRandomNum();
// 		try{ 
// 			PageData issend=TongYong.Okmessage(phone,"1");
//  			if(issend.get("result").equals("0")){
//  				map.put("result", "0");
//		 		map.put("message",issend.getString("message"));
//		 		map.put("data", "");
//		 		return map;
//  			}
//  			SmsUtil.sendZcCode(phone, code);
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统错误";
//		}
//		map.put("message", message);
//	    map.put("result", result);
//	    map.put("data", code);
//		return map;
//	}
	
	
	
	
	
	/**
	 * 短信快捷登录登陆
	 * 
	 * app_member/SMSlogin.do
	 * 
	 * phone  手机号码
	 * 未注册的默认注册
 	 */
	@RequestMapping(value="/SMSlogin")
	@ResponseBody
	public Object SMSlogin(){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="登陆成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String phone =pd.getString("phone");
			pd.put("phone", phone);
			pd=appMemberService.findSMSLogin(pd);
			if(pd == null ){
				 //直接注册
				 pd=new PageData();
				 String password=BaseController.getMiMaNumber();
				 pd.put("password", MD5.md5(password));
				 pd.put("phone", phone);
 				 pd.put("zhuce_shebei", "1");
				 pd=TongYong.saveMember(pd,"0", "0");//注册
				 //发送短信
				 SmsUtil.ZhuCeForPassword(phone, password);
			}
			//更新登陆时间
			pd.put("islogin","1");
			appMemberService.edit(pd);
			pd.remove("logindate");
			pd.remove("islogin");
			map.put("data", pd);
			pd=null;
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	
	/**
	 * 前往个人中心
	 * 魏汉文20160620
	 */
	@RequestMapping(value="/goPersonalCenter")
	@ResponseBody
	public Object goPersonalCenter() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd=appMemberService.findById(pd);
			if(pd == null){
					result="0";
					message="账号ID不能为空";
					map.put("data", "");
			}else{
				map.put("data", pd);
				pd=null;
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	
	/**
	 * 退出登陆
	 * 魏汉文20160621
	 */
	@RequestMapping(value="/outLogin")
	@ResponseBody
	public Object outLogin(Page page){
//		//logBefore(logger, "退出登陆");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="退出成功";
		PageData pd = new PageData();
		try{
				pd = this.getPageData();
 				pd.put("islogin","0");
				appMemberService.edit(pd);
				pd=null;
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统异常";
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
  		return map;
	}
	/**
	 * 修改手机号
	 * 刘耀耀
	 * 2016.06.24
	 */
	@RequestMapping(value="/editTel")
	@ResponseBody
	public Object editTel() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//判断该手机号是否注册过
			PageData _pd=appMemberService.detailByPhone(pd);
			if(_pd != null ){
				map.put("result", "0");
				map.put("message", "当前手机已注册");
				map.put("data", "");
				_pd=null;
				return map;
			}
			String member_id=pd.getString("member_id");
			String phone=pd.getString("phone");
 			if(member_id!=null && phone!=null){
				appMemberService.edit(pd);
			}else{
				result="0";
				message="修改失败";
			}
 		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		pd=null;
		return map;
	}
	
	/**
	 * 修改密码
	 * 刘耀耀
	 * 2016.06.24
	 */
	@RequestMapping(value="/editPossword")
	@ResponseBody
	public Object editPossword() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String member_id=pd.getString("member_id");
			String password=pd.getString("password");//输入的新密码
			String oldPassword=pd.getString("oldpassword");//输入的原密码
 			pd.put("password", MD5.md5(password));
			String pawd=appMemberService.findPowd(pd);//查出数据库里的密码
			if(member_id!=null&&password!=null&&oldPassword!=null){
					if(MD5.md5(oldPassword).equals(pawd)){
						appMemberService.edit(pd);
						map.put("data", "");
					}else{
						 result = "0";
 						 message="原密码不正确";
					}
			}else{
				 result = "0";
				message="修改失败";
			}
			pd=null;
		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data","");
		return map;
	}
	
	/**
	 * 忘记密码
	 * 刘耀耀
	 * 2016.06.24
	 */
	@RequestMapping(value="/editPawd")
	@ResponseBody
	public Object editPawd() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 			String password=pd.getString("password");//输入的新密码
			pd=appMemberService.findSMSLogin(pd);
			if(pd == null ){
				map.put("data", "");
				map.put("result", "0");
				map.put("message", "当前手机未注册，请前往注册");
		  		return map;
 			}else{
 				pd.put("password", MD5.md5(password));
				appMemberService.editPawd(pd);
 				pd=null;
			}
 		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			 e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data","");
		return map;
	}
	
	/**
	 * 查询用户账号信息
	 * 刘耀耀
	 * 2016.06.24
	 */
	@RequestMapping(value="/findAccount")
	@ResponseBody
	public Object findAccount() throws Exception{
//		//logBefore(logger, "查询用户账号信息");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			List<PageData> varList=appMemberService.findAccount(pd);
			if(varList.size()==0){
				map.put("data","");
				message="查询失败";
			}
			map.put("data",varList);
			varList=null;
		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 查询我的信息
	 * 刘耀耀
	 * 2016.06.27
	 */
	@RequestMapping(value="/listPersonal")
	@ResponseBody
	public Object listPersonal() throws Exception{
//		//logBefore(logger, "查询我的信息");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			List<PageData> varList=appMemberService.listPersonal(pd);
 			if(varList.size()==0){
				map.put("data","");
				message="查询失败";
			}
			map.put("data",varList);
			varList=null;
		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			 e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 修改用户名
	 * 刘耀耀
	 * 2016.06.24
	 */
	@RequestMapping(value="/editName")
	@ResponseBody
	public Object editName() throws Exception{
//		//logBefore(logger, "修改用户名称");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String member_id=pd.getString("member_id");
			String name=pd.getString("name");
			if(member_id!=null && name!=null){
					appMemberService.edit(pd);
					map.put("data", "");
			}else{
				message="修改失败";
			}
			pd=null;
		} catch (Exception e) {
			 result = "0";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	
	/**
	 * 个人收货地址列表
	 * 魏汉文
	 * 2016.06.24
	 */
	@RequestMapping(value="/listAllAddressById")
	@ResponseBody
	public Object listAllAddressById() throws Exception{
//		//logBefore(logger, "个人收货地址列表");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try {
				pd = this.getPageData();
				List<PageData> addressList=appMemberService.listAllAddressById(pd);
				if(addressList.size() == 0){
					map.put("data", "");
				}else{
					map.put("data", addressList);
				}
				addressList=null;
 		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			 e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	/**
	 * 新增个人收货地址
	 * 魏汉文
	 * 2016.06.24
	 */
	@RequestMapping(value="/addAddressById")
	@ResponseBody
	public Object addAddressById() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="新增成功";
		PageData pd = new PageData();
		try {
				pd = this.getPageData();
				pd.put("member_address_id", BaseController.getTimeID());
				appMemberService.addAddressById(pd);
 		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			 e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		pd=null;
 		return map;
	}
	
	/**
	 * 查看个人收货地址详情
	 * 魏汉文
	 * 2016.06.24
	 */
	@RequestMapping(value="/findAddressById")
	@ResponseBody
	public Object findAddressById() throws Exception{
//		//logBefore(logger, "查看个人收货地址详情");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="新增成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 			pd=appMemberService.findAddressById(pd);
		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	/**
	 * 修改个人收货地址
	 * 魏汉文
	 * 2016.06.24
	 */
	@RequestMapping(value="/editAddressById")
	@ResponseBody
	public Object editAddressById() throws Exception{
//		//logBefore(logger, "个人收货地址修改");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 			appMemberService.editAddressById(pd);
		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		return map;
	}
	
	/**
	 * 我的vip列表
	 * 刘耀耀
	 * 2016.06.27
	 */
	@RequestMapping(value="/listAllVipImage")
	@ResponseBody
	public Object listAllVipImage(){
//		//logBefore(logger, "列表Member_vipcard");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功 ";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			List<PageData>	varList = appMemberService.listAllVipImage(pd);	//列出Member_vipcard列表
			if(varList.size()==0){
  				map.put("data", "");
			}else{
				map.put("data", varList);
			}
 		} catch(Exception e){
			result = "0";
 			message="查询失败 ";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		
		return map;
	}
	
	
	/**
	 * 删除vip列表（memmber_id和store_id）
	 */
	@RequestMapping(value="/deleteVipImage")
	@ResponseBody
	public Object deleteVipImage(){
//		//logBefore(logger, "删除vip列表");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除成功 ";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			appMemberService.deleteVIPTwo(pd);
 		} catch(Exception e){
			result = "0";
 			message="删除失败 ";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
 		return map;
	}
	
	/**
	 * 修改头像
	 * 魏汉文20160630
	 */
	@RequestMapping(value="/editImgae_url")
	@ResponseBody
	public Object editImgae_url(
			@RequestParam(value="image_url",required=false) MultipartFile file,
			@RequestParam  String member_id 
 			){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="新增成功";
		String image_url="";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			if(file != null){
					String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
					String filePath = "/userFile";//文件上传路径
 					String userFilename =  FileUpload.fileUp(file, currentPath+filePath, member_id);//字符拼接，上传到服务器上
				    image_url = AppUtil.getuploadRootUrlIp()+filePath+"/"+userFilename;
 					pd.put("member_id", member_id);
					pd.put("image_url", image_url);
					appMemberService.edit(pd);
			}else{
   	 			result="0";
   	 			message="上传失败[file]不能为空";
   	 		}
 			
		} catch(Exception e){
			result = "0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", image_url);
 		return map;
	}
	
	/**
	 * 获取用户聊天名称------id
	 * app_member/imgae_urlById.do?id=jy15260282340c811
	 */
	@RequestMapping(value="/imgae_urlById")
	@ResponseBody
	public Object imgae_urlById(){
//		//logBefore(logger, "聊天名称/头像/id--通过ID");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
 		try{
				pd = this.getPageData();
				PageData pdid = new PageData();
  				pdid.put("store_id",pd.getString("id") );
 				pdid=appStoreService.contactStore(pdid);
 				if(pdid==null){
 						pdid=new PageData();
						pdid.put("member_id",pd.getString("id") );
						pdid=appMemberService.contactMember(pdid);
						if(pdid==null){
 							map.put("result", "0");
							map.put("message","查询失败");
							map.put("data", "");
							return map;
 	 					}
						pd=pdid;
				} 
 				pd=pdid;
				pdid=null;
 				if(pd != null && pd.getString("name") != null && pd.getString("name").length() == 11){
 					pd.put("name", pd.getString("name").substring(0, 3)+"****"+pd.getString("name").substring(7, 11));
 				}
		} catch(Exception e){
			result = "0";
			message="查询失败";
  			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		map.put("url", "imgae_urlById.do");	
		pd=null;
		return map;
	}
	
	
	/**
	 * 获取用户聊天头像-----手机号
	 */
	@RequestMapping(value="/imgae_urlByPhone")
	@ResponseBody
	public Object imgae_urlByPhone(){
		//logBefore(logger, "获取用户聊天头像-----手机号");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		String image_url="";
		PageData pd = new PageData();
 		try{
				pd = this.getPageData();
 				if(!pd.getString("phone").equals("") && appMemberService.detailByPhone(pd) != null){
 					image_url=appMemberService.detailByPhone(pd).getString("image_url");
  				}else{
  					result="0";
  					message="只要电话参数不能为空";
  				}
		} catch(Exception e){
			result = "0";
			message="查询失败";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("image_url", image_url);
		map.put("data","");
 		return map;
	}
	
	/**
	 * 判断积分红包是否还有积分
	 * 魏汉文20160702
	 * app_member/isOverJFRed.do?user_id=&user_type=&ms_redpackage_id=
	 */
	@RequestMapping(value="/isOverJFRed")
	@ResponseBody
	public  synchronized Object isOverJFRed(){
 		Map<String,Object> map = new HashMap<String,Object>();
  		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			map=TongYong.getJfRedstatic(pd);
		} catch(Exception e){
 			logger.error(e.toString(), e);
		}
 		return map;
	}
	
	/**
	 * 用户领取积分红包获取的积分列表
	 * 魏汉文20160702
	 */
	@RequestMapping(value="/getJFForUserList")
	@ResponseBody
	public synchronized Object getJFForUserList(){
 		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1= new HashMap<String,Object>();
 		String result = "1";
		String message="领取成功";
		PageData pd = new PageData();
		try{
					pd = this.getPageData();
					PageData mpd=new PageData();
					pd=appMemberService.findJfRedById(pd);//发红包的个人信息
					if( pd != null){
						if(pd.getString("user_type").equals("1")){//商家
							mpd.put("store_id", pd.getString("user_id"));
							mpd=appStoreService.contactStore(mpd);
							pd.put("name", mpd.getString("name"));
							pd.put("image_url", mpd.getString("image_url"));
					   }else if(pd.getString("user_type").equals("2")){//会员
								mpd.put("member_id", pd.getString("user_id"));
								mpd=appMemberService.contactMember(mpd);
								if(mpd != null){
									pd.put("name", mpd.getString("name"));
									pd.put("image_url", mpd.getString("image_url"));
								}else{
									mpd=new PageData();
								}
	 					}
						map1.put("mpd", pd);
						List<PageData> historyList=appMemberService.findJfRedHistoreList(pd);
						for(PageData e : historyList){
									if(e.getString("user_type").equals("1")){//商家
										mpd.put("store_id", e.getString("user_id"));
										mpd=appStoreService.contactStore(mpd);
										e.put("name", mpd.getString("name"));
										e.put("image_url", mpd.getString("image_url"));
									}else if(e.getString("user_type").equals("2")){//会员
										mpd.put("member_id", e.getString("user_id"));
										mpd=appMemberService.contactMember(mpd);
										e.put("name", mpd.getString("name"));
										e.put("image_url", mpd.getString("image_url"));
									}
						}
						map1.put("list", historyList);
						historyList=null;
					}
   		} catch(Exception e){
			result = "0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
		return map;
	}
	
	
	/**
	 * 查出用户余额
	 * 刘耀耀201607011
	 */
	@RequestMapping(value="/findBalance")
	@ResponseBody
	public synchronized Object findBalance(){
//		//logBefore(logger, "用户余额");
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="领取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String balance=appMemberService.findBalance(pd);//用户余额
			if(balance==null){
				map.put("data","");
			}else{
				pd.put("now_money", balance);
				map.put("data", pd);
			}
  		} catch(Exception e){
			result = "0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	/**
	 * 修改推送
	 * member_id,istuisong:1-可推送，0-不可推送
	 * zhsh/app_member/editTuiSong.do
	 */
	@RequestMapping(value="/editTuiSong")
	@ResponseBody
	public Object editTuiSong() throws Exception{
//		//logBefore(logger, "修改推送");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			appMemberService.edit(pd);
 		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		pd=null;
		return map;
	}
	
	

	
	/**
	 *删除推送
	 *allsendtuisong_id:逗号区分
	 *app_member/deleteTuisong.do
	 */
	@RequestMapping(value="/deleteTuisong")
	@ResponseBody
	public Object deleteTuisong(){
//		//logBefore(logger, "删除推送");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			String allsendtuisong_id=pd.getString("allsendtuisong_id");
			if(allsendtuisong_id != null){
				String[] array=allsendtuisong_id.split(",");
				ServiceHelper.getSend_notificationsService().deleteTuisong(array);
			}else{
				result="0";
				message="缺少必要参数";
			}
 		} catch(Exception e){
			result = "0";
			message="删除失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	
	/**
	 *获取推送列表
 	 *login_id ::当前登录人的ID
 	 *app_member/listAllTuisong.do
	 */
	@RequestMapping(value="/listAllTuisong")
	@ResponseBody
	public Object listAllTuisong(Page page){
//		//logBefore(logger, "获取推送列表");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			String login_id=pd.getString("login_id");
			pd.put("deletestatus", "0");
			pd.put("oprator_id", login_id);
			List<PageData> tuisongList=ServiceHelper.getSend_notificationsService().listAllTuisong(pd);
//			if(tuisongList.size() == 0){
//				map.put("data", "");
//			}else{
//				map.put("data", tuisongList);
//			}
			map.put("data", tuisongList);
  		} catch(Exception e){
			result = "0";
			message="获取失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 更新展示的会员ID以及密码
 	 * app_member/addshow_lookid.do
	 */
	@RequestMapping(value="/addshow_lookid")
	@ResponseBody
	public  Object addshow_lookid(){
		 PageData _pd=new PageData();
 		 try{ 
 			 List<PageData> allmemberList=appMemberService.listAllMember(_pd);
 			 for (PageData e : allmemberList) {
 				if(e.getString("show_lookid") == null || e.getString("show_lookid").equals("")){
  					e.put("show_lookid",  BaseController.getShowKookId() );
  				}
  	 			while (ServiceHelper.getAppMemberService().detailByShowLookId(e) > 0 ) {
 	  				e.put("show_lookid",BaseController.getShowKookId());
  	 			}	
 	 			if(ServiceHelper.getAppMemberService().detailByPhone(e).getString("password").length() < 32){
	  					e.put("password", MD5.md5(ServiceHelper.getAppMemberService().detailByPhone(e).getString("password")));
	  			}
 	 			appMemberService.edit(e);
			}
 		 }catch(Exception e){
 			 logger.error(e.toString(), e);
		 }
 		 return _pd;
	 }
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	public static void main(String[] args){
		 
	}
	
	//新的版本所采用的接口=========================================================================
	
	/**
	 *支付宝登录之前需要获取的InforStr字符串
	 *app_member/getInforStr.do
	 */
	@RequestMapping(value="/getInforStr")
	@ResponseBody
	public Object getInforStr() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="获取成功";
		String inforStr="";
   		try {
   			inforStr=AlipayConfig.buildAuthInfoMap();
  		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result="0";
			message="系统错误";
		}
  		map.put("result", result);
  		map.put("message", message);
  		map.put("data", inforStr);
  		return map;
	}
	
	
	
	/**
	 *使用微信登录 
	 *第一步
	 * app_member/threeNumberIsZhuCe.do
	 * open_id 微信的唯一标示ID
	 * unionid(支付宝对应的user_id) 账号的唯一标示
	 * type    登录类型：1-微信登录，2-QQ登录，3-微博登录,4-支付宝
	 * 
	 * （支付宝专用：auth_code  ）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/threeNumberIsZhuCe")
	@ResponseBody
	public Object ThreeNumberIsZhuCe(String open_id,String unionid,String type,String auth_code) throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="登录成功";
   		PageData pd = new PageData();
  		try {
   			if(type.equals("1")){
  				pd.put("wxunionid", unionid);
  	 			if(appMemberService.getByUnionid(pd) ==null ){
  	 				result="0";
  	 				message="当前账号未注册，前往获取验证码";
    	    	}else{
  	 				pd=appMemberService.getByUnionid(pd);
  	 			}
  			}else if(type.equals("2")){
  				pd.put("qqunionid", unionid);
  	 			if(appMemberService.getByUnionid(pd) ==null ){
  	 				result="0";
  	 				message="当前账号未注册，前往获取验证码";
    	    	}else{
  	 				pd=appMemberService.getByUnionid(pd);
  	 			}
  			}else if(type.equals("3")){
  				pd.put("wbunionid", unionid);
  	 			if(appMemberService.getByUnionid(pd) ==null ){
  	 				result="0";
  	 				message="当前账号未注册，前往获取验证码";
    	    	}else{
  	 				pd=appMemberService.getByUnionid(pd);
  	 			}
  			}else if(type.equals("4")){
  				//调用方法获取用户id
  				
  				pd.put("alipayunionid", unionid);
  	 			if(appMemberService.getByUnionid(pd) ==null ){
  	 				result="0";
  	 				message="当前账号未注册，前往获取验证码";
    	    		}else{
  	 				pd=appMemberService.getByUnionid(pd);
  	 			}
  			}
 		} catch (Exception e) {
			// TODO: handle exception
			logger.error("判断微信是否注册"+e.toString());
			result="0";
			message="系统错误";
		}
  		map.put("result", result);
  		map.put("message", message);
  		map.put("data", pd);
  		return map;
	}
	
	/**
	 * 微信注册手机获取验证码
	 * app_member/findThreePhoneByZhuCe.do?phone=
	 * 
 	 * phone 手机号
 	 */
	@RequestMapping(value="/findThreePhoneByZhuCe") 
	@ResponseBody
	public Object findThreePhoneByZhuCe(HttpServletRequest request){
 		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		String result="1";
		String message="验证码已发至你手机，请注意查收";
		String code=StringUtil.getSixRandomNum();
 		try{
			pd=this.getPageData();
  			//验证是否可以发送验证码---时间60秒
  			String phone=pd.getString("phone");
   			PageData issend=TongYong.Okmessage(phone,"1");
  			if(issend.get("result").equals("0")){
  				map.put("result", "0");
		 		map.put("message",issend.getString("message"));
		 		map.put("data", "");
		 		return map;
  			}
  			SmsUtil.TongYongCode(phone, code);
//  			System.out.println(code);
 		    map.put("data", code);
  		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统错误";
		}
		map.put("message", message);
	    map.put("result", result);
		return map;
	}
	
	
	
  	
	/**
	 * 使用第三方登录
	 * 第三步
	 * app_member/threeLogin.do?
	 * 参数如下：
	 * open_id 唯一标示ID
	 * phone   登录获取验证码手机号
	 * type    登录类型：1-微信登录，2-QQ登录，3-微博登录，4-支付宝登录
	 * unionid  账号的唯一标示
	 * image_url 头像
	 * name 姓名
	 * sex 性别
 	 */
	@RequestMapping(value="/threeLogin")
	@ResponseBody
	public Object ThreeLogin(String open_id,String phone,String type,String unionid,String image_url,String name,String sex) throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="登录成功";
  		PageData pd = new PageData();
  		try {
//  			System.out.println(this.getPageData().toString());
	  			try {
	  				//存储app的第三方登录信息
		  			PageData _pd=new PageData();
		  			_pd.put("unionid", unionid);
		  			_pd.put("open_id", open_id);
		  			_pd.put("phone", phone);
		  			_pd.put("type", type);
		  			ServiceHelper.getTYAllSortService().saveThreeLogin(_pd);
		  			_pd=null;
				} catch (Exception e) {
					// TODO: handle exception
				}
  			
  			
  				//获取用户的一些信息
				if(type.equals("1")){
					pd.put("wxunionid",unionid);
				}else if(type.equals("2")){
					pd.put("qqunionid",unionid);
				}else if(type.equals("3")){
					pd.put("wbunionid",unionid);
				}else if(type.equals("4")){
					pd.put("alipayunionid",unionid);
				}
   				//判断当前手机号是否注册过
  	 			pd.put("phone", phone);
   	 			if(appMemberService.detailByPhone(pd) == null){
   	 				String password=BaseController.getMiMaNumber();
					pd.put("password",password);
					pd.put("image_url",image_url);
					pd.put("name",name);
					pd.put("sex",sex);
					pd.put("zhuce_shebei","1");
    	 			pd=TongYong.saveMember(pd,"0", "0");//注册
					//发送短信
					SmsUtil.ZhuCeForPassword(phone, password);
   	 			} 
   	 			//将微信和当前的手机号码合并
	 			pd.put("member_id", appMemberService.detailByPhone(pd).getString("member_id"));
	 			pd.put("islogin", "1");
	 			appMemberService.edit(pd);
	 			//获取会员信息
	 			pd=appMemberService.getByUnionid(pd); 
  		} catch (Exception e) {
  			logger.error("微信登录"+e.toString());
 		}
   		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
 		pd=null;
 		return map;
	}
 	
	 
	
	
	 
}
