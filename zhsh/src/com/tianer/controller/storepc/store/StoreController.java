package com.tianer.controller.storepc.store;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.zhihui.Qx;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.pc_advert.Pc_advertService;
import com.tianer.service.business.store_shift.Store_shiftService;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.memberapp.AppStorepc_marketingService;
import com.tianer.service.storeapp.Payapp_historyService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.store_marketingeffect.Storepc_marketingeffectService;
import com.tianer.service.storepc.stotr.StorepcService;
import com.tianer.service.storepc.tableNumber.TablerNumberService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.FileUpload;
import com.tianer.util.MD5;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;
import com.tianer.util.Tools;
import com.tianer.util.ErWerMa.OneEr;

/** 
 * 
* 类名称：StoreController   
* 类描述： 
* 创建人：刘耀耀
* 创建时间：2016年5月26日 下午3:50:40
 */
@Controller("storepcStoreController")
@RequestMapping(value="/storepc")
public class StoreController extends BaseController {
	
	@Resource(name="storepcService")
	private StorepcService storepcService;
	
	@Resource(name = "storepc_marketingeffectService")
	private Storepc_marketingeffectService storepcMarketingeffectService;
	
	
	
	/**
	 * 判断短信验证码是否正确
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
			String phone=pd.getString("phone");
			String messagecode=pd.getString("messagecode");
			String codetype=pd.getString("codetype");
			//1-注册，2-绑定卡片/解绑卡片银行卡，。。。。
			if(codetype.equals("1")){
				String sessioncode=(String) SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_STORE_ZHUCECODE);
				if(!messagecode.equals(sessioncode)){
					result="0";
					message="验证码错误";
				}else{
					this.getRequest().getSession().setAttribute("storezcphone", phone);
				}
			}else if(codetype.equals("2")){
				
			}
 			
    	} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	
	/**
	 * 商家验证手机号是否注册了N个
	 * storepc/findCodeByZhuCe.do
	 */
	@RequestMapping(value="/findCodeByZhuCe")
	@ResponseBody
	public Object findCodeByZhuCe(HttpServletRequest request){
 		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		String result="1";
		String message="验证码已发至你手机，请注意查收";
		String code=StringUtil.getSixRandomNum().toString();
		try{
			pd=this.getPageData();
 			String registertel_phone=pd.getString("registertel_phone");
			String in_position=pd.getString("in_position");//所在位置：b,c,pc,wx 
			if(in_position == null){
				in_position="pc";
			}
			//只针对pc端
			if(in_position.equals("pc")){
				String txcode=pd.getString("txcode");//图形验证码 
 		        String validate = (String) request.getSession().getAttribute("validateCode");  //获取缓存验证码
		        if(!(Tools.notEmpty(validate) && validate.equalsIgnoreCase(txcode))){
		        	map.put("result", "0");
			 		map.put("message","图形码错误");
			 		map.put("data", "");
			 		return map;
		        }
			}
  			//获取当前注册商家的数量
			int number=storepcService.getNumberForStore(pd);
			if(number == Const.maxStorenumber){
				   result="0";
				   message="当前号码商家注册数量已达到最大";
 			}else{
 				//验证是否可以发送验证码---
 				PageData issend=TongYong.Okmessage(registertel_phone,"1");
 		  		if(issend.get("result").equals("0")){
 		  				map.put("result", "0");
 				 		map.put("message",issend.getString("message"));
 				 		map.put("data", "");
 				 		return map;
 		  		}
 				SmsUtil.sendZcCode(registertel_phone,code );
  				SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_STORE_ZHUCECODE , code);
  				try {
  	 	  			PageData _pd=new PageData();
  		  			_pd.put("phone", registertel_phone);
  		  			_pd.put("code", code);
  		  			_pd.put("content", "商家注册获取验证码");
  	 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
  		  			_pd=null;
  				} catch (Exception e) {
  					// TODO: handle exception
  				}
 			}
			if(!(in_position.equals("pc") ||  in_position.equals("wx"))){
				map.put("data", code);
			}else{
				map.put("data", "");
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
	 * 商家验证手机号是否注册了N个
	 * storepc/findPhone.do
	 */
	@RequestMapping(value="/findPhone")
	@ResponseBody
	public Object findPhone(HttpServletRequest request){
 		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		String result="1";
		String message="验证码已发至你手机，请注意查收";
		try{
			pd=this.getPageData();
 			String registertel_phone=pd.getString("registertel_phone");
			//获取验证码
			String code=StringUtil.getSixRandomNum().toString();
  			//获取当前注册商家的数量
			int number=storepcService.getNumberForStore(pd);
			if(number == Const.maxStorenumber){
				   result="0";
				   message="当前号码商家注册数量已达到最大";
 			}else{
 				//验证是否可以发送验证码---
 				PageData issend=TongYong.Okmessage(registertel_phone,"1");
 		  		if(issend.get("result").equals("0")){
 		  				map.put("result", "0");
 				 		map.put("message",issend.getString("message"));
 				 		map.put("data", "");
 				 		return map;
 		  		}
 				 SmsUtil.sendZcCode(registertel_phone,code );
  				 SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_STORE_CARDCODE , code);
  				 try {
   	 	  			PageData _pd=new PageData();
   		  			_pd.put("phone", registertel_phone);
   		  			_pd.put("code", code);
   		  			_pd.put("content", "商家注册获取验证码");
   	 	  			ServiceHelper.getTYAllSortService().saveMessageCode(_pd);
   		  			_pd=null;
   				} catch (Exception e) {
   					// TODO: handle exception
   				}
 			}
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
	 * 商家获取绑定卡片和解除绑定卡片的验证码
	 * storepc/findCodeByCard.do
	 * phone 
	 * type  7-绑定，10-解绑
	 * in_position=b
	 */
	@RequestMapping(value="/findCodeByCard")
	@ResponseBody
	public Object findCodeByCard(HttpServletRequest request){
 		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		String code=StringUtil.getSixRandomNum();
		String result="1";
		String message="验证码已发至你手机，注册查收";
		try{
			pd=this.getPageData();
			String phone=pd.getString("phone");//注册手机号
			String type=pd.getString("type");//获取验证码的类型
 			String in_position=pd.getString("in_position");//所在位置：b,c,pc,wx 
 			if(in_position == null){
 				in_position="b";
 			}
 			 //验证是否可以发送验证码---倒计时60秒
 			 PageData issend=TongYong.Okmessage(phone,"2");
 			 if(issend.get("result").equals("0")){
 				map.put("result", "0");
		 		map.put("message",issend.getString("message"));
		 		map.put("data", "");
		 		return map;
 			 }
 			if(type.equals("7")){//绑定银行卡,支付宝
 				 SmsUtil.TXNumberCode(phone, code);
      	  	}else if(type.equals("10")){//删除绑定的支付宝/银行卡
 				 SmsUtil.JbTXNumberCode(phone, code);
   	  		}
  			SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_STORE_CARDCODE , code);
  			//=======================================
 			 if(in_position.equals("b")){
				 map.put("data", code);
				 System.out.println("验证码"+code);
			 }else{
				 map.put("data", "");
			 }
		} catch(Exception e){
			logger.error(e.toString(), e);
			 result="0";
			 message= "系统错误";
 		}
		map.put("result", result);
 		map.put("message", message);
		return map;
	}
	
	
	
	/**
	 * 到首页面
	 * 刘耀耀
	 * 2016.07.09
	 * 
	 */
	@RequestMapping(value="/goIndex")
	public ModelAndView goIndex() throws Exception{
		//logBefore(logger, "到首页");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			mv.setViewName("storepc/business_index");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	@Resource(name="pc_advertService")
	private Pc_advertService pc_advertService;
	
	/**
	 * 到登陆页面
	 * 刘耀耀
	 * 2016.07.09
	 * 
	 */
	@RequestMapping(value="/goLogin")
	public ModelAndView goLogin() throws Exception{
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			//商家获取广告
   			pd.put("pc_type", "1");
  			List<PageData> pcadvert=pc_advertService.listAll(pd);
  			if(pcadvert.size() > 0 ){
  				mv.addObject("pcadvert",pcadvert.get(0));
  			}
  			mv.setViewName("storepc/business_shopsignin");
			mv.addObject("pd", pd);
 			
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * pdf
	 * 
	 */
	@RequestMapping(value="/gopdf")
	public ModelAndView gopdf() throws Exception{
		//logBefore(logger, "登录成功跳转到商家中心");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			mv.setViewName("storepc/xy");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 到开店页面
	 * 刘耀耀
	 * 2016.07.09
	 * 
	 */
	@RequestMapping(value="/goRegister")
	public ModelAndView goRegister() throws Exception{
		//logBefore(logger, "登录成功跳转到商家中心");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			mv.setViewName("storepc/business_apply");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 注册商家0606
	 * storepc/sS_P.do
	 * 
 	 */
	@RequestMapping(value="/sS_P")
	@ResponseBody
	public  Map<String,Object> SS_P() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		String result="1";
		String message="";
 		try {
 			pd = this.getPageData();
 			String registertel_phone=pd.getString("registertel_phone");
 			String getcodephone=(String) this.getRequest().getSession().getAttribute("storezcphone");
 			if(registertel_phone.equals(getcodephone)){
 				pd=TongYong.saveStore(pd);
 			}else{
 				result="0";
 				message="请先前往获取验证码";
 			}
    	} catch (Exception e) {
			// TODO: handle exception
  			e.printStackTrace();
  		}
 		map.put("result", result);
 		map.put("message", message);
		return map;
	}
  	
	
//	/**
//	 * 短信验证码验证之后。。。。提交申请
//	 * 魏汉文0711
//	 * 
//	 */
//	@RequestMapping(value="/upShenQing")
//	public ModelAndView upShenQing() throws Exception{
//		//logBefore(logger, "登录成功跳转到商家中心");
//		ModelAndView mv = this.getModelAndView();
//  		PageData pd = new PageData();
// 		try {
//			pd=this.getPageData();
// 			mv.setViewName("storepc/business_apply2");
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
	
//	/**
//	 * 到开店页面最后一步
//	 * 魏汉文0711
//	 */
//	@RequestMapping(value="/goRegisterlast")
//	public ModelAndView goRegisterlast() throws Exception{
//		//logBefore(logger, "到开店页面最后一步");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try {
//			pd=this.getPageData();
//			mv.setViewName("storepc/business_apply3");
//			mv.addObject("pd", pd);
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}

//	/**
//	 * 注册商家
//	 * 刘耀耀
//	 * 2016.07.07
//	 */
//	@RequestMapping(value="/save")
//	public  ModelAndView save() throws Exception{
// 		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
// 		try {
// 			pd = this.getPageData();
//			pd=TongYong.saveStore(pd);
//  		} catch (Exception e) {
//			// TODO: handle exception
// 			//system.out.println(e.toString());
//		}
// 		mv.setViewName("storepc/business_shopsignin");
//		return mv;
//	}
	
	
	
	/**
	 * 修改商家状态
 	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public Object edit(String merchant_status){
 		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
 			StoreRole slogin=(StoreRole)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_STORE_USER);
			String store_id=slogin.getStore_id();
			pd.put("merchant_status", merchant_status);
			pd.put("store_id", store_id);
			storepcService.editType(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}

	@Resource(name="payapp_historyService")
	private Payapp_historyService historyService;
	
	/**
	 * 跳转至收货取银
	 * 魏汉文0707
	 * pay_sort_type:1-总金额收银，2-分类收银
	 */
	@RequestMapping(value="/goShQyStore")
	public ModelAndView goShQyStore() throws Exception{
		//logBefore(logger, " 跳转至收货取银");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Session session = SecurityUtils.getSubject().getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			//获取当前登录人的桌号
			List<PageData>	noList=new ArrayList<PageData>();
 			if(slogin != null ){
					 if(slogin.getType().equals("1")){
	  					  pd.put("store_id", slogin.getStore_id());
	  					  //获取改商家桌号
	  					  noList = tablerNumberService.listAll(pd);
					 }else{
						 pd.put("store_id", slogin.getStore_id());
						 pd.put("store_operator_id", slogin.getLogin_id());
						 PageData pg = new PageData();
						 pg = storeManageService.findOperatorById(pd);
						 if(pg != null && !pg.equals("")){
								String alldesk_no = pg.getString("alldesk_no");
								if(alldesk_no != null && !alldesk_no.equals("")){
									String[] no = alldesk_no.split(",");
									for (int i = 0; i < no.length; i++) {
										PageData e = new PageData();
										e.put("table_name", no[i]);
										noList.add(e);
									}
								}
						}
						pg=null;
	 				 }
				 	mv.addObject("deskList", noList);
		 			noList=null;
				 	List<PageData> leibieList=appGoodsService.listAllBigSort(pd);
					pd.put("sortList", leibieList);
					//判断是否开通类别积分购买的权限
					PageData issortjfpd=appStorepc_marketingService.getJfById(pd);
					if(issortjfpd != null && issortjfpd.getString("change_type").equals("3") ){
						pd.put("issortjf", "3");
					}else if(issortjfpd != null && issortjfpd.getString("change_type").equals("2") ){
						pd.put("issortjf", "1");
					}else{
						pd.put("issortjf", "0");
					}
					//获取商家的营销规则明细
					PageData yxpd=TongYong.markeingAll(pd);
					//按字母排序
					List<String> listStr=StringUtil.mapTransitionList(yxpd);
					for(int i=0 ;i<listStr.size() ;i++){
						String str=listStr.get(i);
						if(str.equals("")){
							listStr.remove(i);
							i=i-1;
						}
					}
					pd.put("yxlist", listStr);
					listStr=null;
					
   			}
 			//防止表单重复提交
 			if(session.getAttribute(Const.SESSION_ORDER) == null ){
				String session_orderid=BaseController.getTimeID();
				session.setAttribute(Const.SESSION_ORDER, session_orderid);
				//system.out.println("当前的session="+session_orderid);
				mv.addObject("session_orderid", session_orderid);
			}else{
				mv.addObject("session_orderid", String.valueOf(session.getAttribute(Const.SESSION_ORDER)));
			}
 			
  			mv.addObject("pd", pd);
			mv.setViewName("storepc/business_homepage1");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	@Resource(name="appGoodsService")
	private AppGoodsService appGoodsService;
	@Resource(name="appStorepc_marketingService")
	private AppStorepc_marketingService appStorepc_marketingService;
	
	/**
	 * 登录验证手机号和密码
	 * 刘耀耀
	 * 2016.07.07
	 */
	@RequestMapping(value="/pcLogin")
	@ResponseBody
	public Object pcLogin(HttpServletRequest request){
		//logBefore(logger, "登录判断手机号和密码");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		StoreRole slogin=new StoreRole();
 		Qx qx=new Qx();
		//shiro管理的session
  		Session session = SecurityUtils.getSubject().getSession();
 		PageData pd = new PageData();
		String type="1";//1-商家，2-操作员
		String gologin_id="";
		String result="1";
		String message="登录成功，正在跳转";
		try{
			pd = this.getPageData();
			String pwd=pd.getString("password");//输入的密码
			String phone=pd.getString("registertel_phone");//输入的手机号
	        String validate = (String) request.getSession().getAttribute("validateCode");  //获取缓存验证码
			String code = pd.getString("code");
 			boolean flag=(Tools.notEmpty(validate) && validate.equalsIgnoreCase(code));//判断验证码
 			//获取电话集合--包含
			int number=0;
			if(Const.xzloginnumber.contains(phone)){
				if(flag){
					result="1";
 				}else{
					result="0";
					message="图形码错误";
					number=Const.clicklogin_number.get(phone);
				}
 			}
 			if(result.equals("1")){
  						String store_id="";
  						String 	registertel_phone="";
 						String 	check_status="";
  						//判断是商家还是操作员的账号和密码
 						pd.put("password", MD5.md5(pd.getString("password")));
 						PageData store = storepcService.listNamePwdById(pd);//判断手机号和密码是否正确
 						if(store == null ){
							type = "2";
							store=storepcService.getOperateLogin(pd);//获取操作员的信息
							if(store != null){
								slogin.setType("2");
								slogin.setStore_name(store.getString("store_name"));
								slogin.setOprator_name(store.getString("operator_name"));
								store_id=store.getString("store_id");
								gologin_id=store.getString("store_operator_id");
 					 			registertel_phone=store.getString("store_operator_id");//数据库的ID号
								check_status=store.getString("operator_status");//数据库的商家审批状态
 							}
						}else{
							slogin.setType("1");
							slogin.setStore_name(store.getString("store_name"));
							slogin.setOprator_name("");
							store_id=store.getString("store_id");
							gologin_id=store_id;
 				 			if(phone.length() == 8){
				 				registertel_phone=store.getString("store_id");//数据库的手机号
				 			}else if (phone.length() == 11){
				 				registertel_phone=store.getString("registertel_phone");//数据库的手机号
				 			}
  							check_status=store.getString("check_status");//数据库的商家审批状态
 						}
 						if(store != null ){
	 								//设置session
	 								slogin.setStore_id(store_id);
 								    slogin.setPassword(pwd);
								    slogin.setPhone(registertel_phone);
									if(slogin.getType().equals("1")){//商家
 									    qx.setAdd("1");
									    qx.setDelete("1");
									    qx.setEdit("1");
									    qx.setLook("1");
									    map1.put("sy", qx);
									    map1.put("yx", qx);
									    map1.put("cw", qx);
									    map1.put("sp", qx);
									    map1.put("hd", qx);
									    slogin.setMap(map1);
										slogin.setLogin_id(store_id);
										if(check_status.equals("1")){
											result="1";
 										}else if(check_status.equals("0")){
											result="0";
											message="对不起，暂不允许登录，请等待后台审核完成后短信通知";
 										}else if(check_status.equals("99")){
 											result="0";
											message="对不起，后台没有通过审批";
 										}else{
 											result="0";
											message="对不起，后台没有通过审批";
 										}
									}else{//操作员
										String[] syStr=store.getString("sy_competence").split(",");
										  qx.setAdd(syStr[0]);
										  qx.setDelete(syStr[1]);
										  qx.setEdit(syStr[2]);
										  qx.setLook(syStr[3]);
										  map1.put("sy", qx);
										String[] yxStr=store.getString("yx_competence").split(",");
										 qx=null;
										 qx=new Qx();
										 qx.setAdd(yxStr[0]);
										  qx.setDelete(yxStr[1]);
										  qx.setEdit(yxStr[2]);
										  qx.setLook(yxStr[3]);
										  map1.put("yx", qx);
										String[] spStr=store.getString("sp_competence").split(",");
										qx=null;
										 qx=new Qx();
										 qx.setAdd(spStr[0]);
										  qx.setDelete(spStr[1]);
										  qx.setEdit(spStr[2]);
										  qx.setLook(spStr[3]);
										  map1.put("sp", qx);
										String[] hdStr=store.getString("hd_competence").split(",");
										qx=null;
										 qx=new Qx();
										 qx.setAdd(hdStr[0]);
										  qx.setDelete(hdStr[1]);
										  qx.setEdit(hdStr[2]);
										  qx.setLook(hdStr[3]);
										  map1.put("hd", qx);
										  String[] cwStr=store.getString("cw_competence").split(",");
										  qx=null;
										  qx=new Qx();
										  qx.setAdd(cwStr[0]);
										  qx.setDelete(cwStr[1]);
										  qx.setEdit(cwStr[2]);
										  qx.setLook(cwStr[3]);
										  map1.put("cw", qx);
										  slogin.setLogin_id(store.getString("store_operator_id"));
										  if(check_status.equals("1")){
											   result="1";
										  }else if(check_status.equals("0")){
											    result="0";
												message="对不起，该员工账号未启用";
 										  } 
 									}
									slogin.setMap(map1);
  									map.put("store_id", store_id);
									map.put("password", pwd);
									map.put("gologin_id", gologin_id);
									map.put("type", type);
									session.setAttribute(Const.SESSION_STORE_USER, slogin);
									//清除当前账号的登录信息
									TongYong.clearLoginNumber(phone);
									//移除验证码
									request.getSession().removeAttribute("validateCode");
						}else{
							result="0";
							message="用户名/密码错误";
 
   							//更新登录次数
    						if(Const.clicklogin_number.get(phone) == null){
 								number=1;
  			 				}else{
  			 					number=Const.clicklogin_number.get(phone)+1;
  							} 
 							Const.clicklogin_number.put(phone, number);
 							if(number == Const.limit_loginerrornumber){
 								Const.xzloginnumber.add(phone);
 								map.put("changetxcode", "1");
 							}
  						}
 			}
 			
 			//显示不显示图形码
 			if(number >= Const.limit_loginerrornumber){
 					map.put("open_txcode", "1");
			}else{
					map.put("open_txcode", "0");
			}
 			
 		} catch(Exception e){
 			result="0";
 			message="系统错误";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	/**
	 * 登录成功跳转到商家中心页面
	 * 刘耀耀
	 * 2016.07.07
 	 */
	@RequestMapping(value="/goStore")
	public ModelAndView goStore() throws Exception{
		//logBefore(logger, "登录成功跳转到商家中心页面");
 		ModelAndView mv = this.getModelAndView();
		HttpServletRequest request=this.getRequest();
		HttpSession pcsession=request.getSession();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			if(slogin != null){
					String store_id=slogin.getStore_id();
					String store_name=slogin.getStore_name();
					String oprator_name=slogin.getOprator_name();
					String operator_id="";
 					if(slogin.getType().equals("1")){
						operator_id="";
						pd.put("islogin","1");
						pd.put("store_id",slogin.getStore_id());
 						storepcService.updateStatus(pd);
					}else{
						operator_id=slogin.getLogin_id()+"";
						pd.put("login","1");
						pd.put("store_operator_id",slogin.getLogin_id());
 		 				storepcService.updateTime(pd);
					}
	 				Map<String,Object> map=slogin.getMap();
 					pd.put("store_id", store_id);
 					pd=storepcService.findById(pd);
  					pd.put("oprator_id", operator_id);
  					pd.put("store_name", store_name);
					pd.put("oprator_name", oprator_name);
 					pd.put("login_type", slogin.getType());
					pd.put("login_password", slogin.getPassword());
					pd.put("login_id", slogin.getStore_id());
 					pcsession.setAttribute("storepd", pd);
	 				Qx qx=new Qx();
					qx=(Qx) map.get("sy");
 					pcsession.setAttribute("storeqx", qx);
	 				mv.addObject("qx", qx);
	 				//判断办证金是否已经交了
//					String earnest_money=pd.getString("earnest_money");
//					String //system_service_fee=pd.getString("//system_service_fee");
	 				String isopen_points=pd.getString("isopen_points");
	 				String transaction_points=pd.getString("transaction_points");
					String biaozhun_status=pd.getString("biaozhun_status");
					String jichushezhi=pd.getString("jichushezhi");
					String endtime=pd.get("endtime").toString();
//					double allmoney=Double.parseDouble(earnest_money)+Double.parseDouble(//system_service_fee);
 					if(biaozhun_status.equals("0")){
 							pd.put("purchase_type", "1");
	 						List<PageData> feeList=city_fileService.listAllCityFee(pd);
	 						mv.addObject("feeList", feeList);
	 						if(isopen_points != null && isopen_points.equals("1")){
	 							mv.setViewName("storepc/transactionpayment");
	 						}else{
	 							mv.setViewName("storepc/payment");
	 						}
    				}else if(biaozhun_status.equals("1")){
   						if( jichushezhi != null && jichushezhi.equals("11111111111")){
   							mv.setViewName("storepc/business_homepage");
    					}else{
   							if(jichushezhi == null){
   								jichushezhi="00000000000";
   							}
   							mv.setViewName("redirect:goSheZhiOne.do?jichushezhi="+jichushezhi+"&store_id="+store_id);
    					}
 					}else if(biaozhun_status.equals("2")){
 						if(isopen_points != null && isopen_points.equals("1")){
 							mv.setViewName("storepc/business_homepage");
 						}else{
 							pd.put("purchase_type", "2");
 	  						List<PageData> feeList=city_fileService.listAllCityFee(pd);
 	 						mv.addObject("feeList", feeList);
 	 						mv.setViewName("storepc/paymentOne");
 						}
 	 				}
  			}else{
				mv.setViewName("storepc/business_shopsignin");
			}
 			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	

	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/loginOut")
	public ModelAndView loginOut(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();	
			StoreRole slogin=(StoreRole)session.getAttribute(Const.SESSION_STORE_USER);
			if(slogin.getType().equals("1")){
				pd.put("islogin","0");
				pd.put("store_id",slogin.getStore_id());
				storepcService.updateStatus(pd);
			}else if(slogin.getType().equals("2")){
				pd.put("login","2");
				pd.put("store_operator_id",slogin.getLogin_id());
 				storepcService.updateTime(pd);
			}
 			session.removeAttribute(Const.SESSION_STORE_USER);
 			//shiro销毁登录
			Subject subject = SecurityUtils.getSubject(); 
			subject.logout();
 		} catch (Exception e) {
			// TODO: handle exception
		}
  		mv.setViewName("redirect:goLogin.do");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="/editpassword")
	public ModelAndView editpassword(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 			String login_type=pd.getString("login_type");
 			if(login_type.equals("1")){
				pd.put("password", MD5.md5(pd.getString("newpassword")));
				storepcService.edit(pd);
			}else{
				pd.put("operator_password",  MD5.md5(pd.getString("newpassword")));
				storeManageService.updateOperator(pd);
			}
 		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("redirect:loginOut.do");
 		return mv;
	}
	/**
	 * 去修改密码页面
	 */
	@RequestMapping(value="/goEditpassword")
	public ModelAndView goEditpassword(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.addObject("pd", pd);
		mv.setViewName("storepc/editpassword");
		return mv;
	}
 
	
	/**
	 * 判断商家是否已经审核通过
	 */
	@RequestMapping(value="/isStore")
	@ResponseBody
	public Object isStore(){
 		Map<String,Object> map = new HashMap<String,Object>();
    	String result="0";
  		String message="当前账号不存在";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String phone=pd.getString("registertel_phone");
			if(phone.length() >= 8){
				String 	check_status="0";
				//判断是商家还是操作员的账号和密码
				if(pd.getString("password") != null){
					pd.put("password", MD5.md5(pd.getString("password")));
				}
				PageData store = storepcService.listNamePwd(pd);//判断手机号和密码是否正确
				if(store == null ){
					store=storepcService.getOperateLogin(pd);//获取操作员的信息
					if(store != null){
						check_status=store.getString("operator_status");//数据库的商家审批状态
						if(check_status.equals("1")){
			 				result="1";
			 				message="登录成功";
			 			}else if(check_status.equals("0")){
			 				message="对不起，该员工账号未启用";
			 			}
					}
				}else{
					check_status=store.getString("check_status");//数据库的商家审批状态
					if(check_status.equals("1")){
		 				result="1";
		 				message="登录成功";
		 			}else if(check_status.equals("0")){
		 				message="对不起，暂不允许登录，请等待后台审核完成后短信通知";
		 			}else if(check_status.equals("99")){
		  				message="对不起，后台没有通过审批";
					} 
				}
				if(Const.xzloginnumber.contains(phone)){
					map.put("open_txcode", "1");
	 			}else{
	 				map.put("open_txcode", "0");
	 			}
			}
      	}catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统错误";
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	@Resource(name="store_shiftService")
	private Store_shiftService store_shiftService;

	@Resource(name = "tablerNumberService")
	private TablerNumberService tablerNumberService;
	
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	/**
	 * 去设置页面
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/goshezhi")
	public ModelAndView goshezhi(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String store_id=pd.getString("store_id");
			String type=pd.getString("type");
 			pd.put("url", "storepc/goXuanBanCiZhouHao.do?store_id="+store_id+"&type="+type);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("storepc/shezhi");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 去选择班次以及桌号的位置
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/goXuanBanCiZhouHao")
	public ModelAndView goXuanBanCiZhouHao(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//获取当前商家的班次 
			List<PageData> shiftList=store_shiftService.listAll(pd);
			mv.addObject("shiftList", shiftList);
			//获取当前商家的桌号
			List<PageData>	deskList = tablerNumberService.listAll(pd);//列出Store列表
			mv.addObject("deskList", deskList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("storepc/loginTwo");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 *选择玩桌号以及班次后的跳转
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/overBanCiZhouHao")
	public ModelAndView overBanCiZhouHao(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
//			//system.out.println(pd);
			String type=pd.getString("type");
			//store_shift_id班次，alldesk_no桌号
			if(type.equals("2")){
				storeManageService.updateOperator(pd);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
  		mv.setViewName("redirect:goStore.do");
		mv.addObject("pd",pd);
		return mv;
	}
	

	/**
	 * 判断商家是否已经支付服务费
	 */
	@RequestMapping(value="/isPayBaoZhengJin")
	@ResponseBody
	public Object isPayBaoZhengJin(){
		//logBefore(logger, "判断商家是否已经支付服务费");
		Map<String,Object> map = new HashMap<String,Object>();
    	String result="1";
  		String message="支付成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			pd=storepcService.findById(pd);
  			if(pd!=null){
  				//判断办证金是否已经交了
				String biaozhun_status=pd.getString("biaozhun_status");
				if(biaozhun_status.equals("0") || biaozhun_status.equals("2")){
						 result="0";
						 message="请先支付服务费";
				}
   			}else{
  				result="0";
  				message="账号不存在";
  			}
    	} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	/**
	 * 判断当前订单是否已经支付完成
	 */
	@RequestMapping(value="/isPayThisOrder")
	@ResponseBody
	public Object isPayThisOrder(){
		//logBefore(logger, "判断商家是否已经支付服务费");
		Map<String,Object> map = new HashMap<String,Object>();
    	String result="1";
  		String message="支付成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			String waterrecord_id=pd.getString("waterrecord_id");
  			pd=ServiceHelper.getWaterRecordService().findWaterRecordIsPayOk(pd);
  			if(pd == null){
  				result="0";
  				message="未支付成功";
  			}
    	} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
	
	/**
	 *前往服务续费页面
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/goFeeNextNumber")
	public ModelAndView goFeeNextNumber(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 			pd=storepcService.findById(pd);
			String endtime=pd.get("endtime").toString();
			String nowday=DateUtil.getDay();
			long s=DateUtil.getDaySub(nowday, endtime);
			pd.put("day", s+"");
			pd.put("purchase_type", "2");
			List<PageData> feeList=city_fileService.listAllCityFee(pd);
			mv.addObject("feeList", feeList);
  		} catch (Exception e) {
			// TODO: handle exception
		}
		mv.setViewName("storepc/paymentTwo");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 确认首次支付后前往商家步骤设置页面
 	 */
	@RequestMapping(value="/goSheZhiOne")
	public ModelAndView goSheZhiOne() throws Exception{
		//logBefore(logger, "确认支付后前往设置页面");
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
 		String jichushezhi="00000000000";//表示从第三个步骤开始的
 		String store_id="";
		try { 
			pd=this.getPageData();
			String chongzhiok=pd.getString("chongzhiok");
			if(chongzhiok != null && chongzhiok.contains("-")){
				store_id=chongzhiok.split("-")[0];
				jichushezhi=chongzhiok.split("-")[1];
			}else{
				store_id=pd.getString("store_id");
				jichushezhi=pd.getString("jichushezhi");
			}
    		if(jichushezhi.equals("00000000000")){
 				mv.setViewName("storepc/shezhi_1");
 			}else if(jichushezhi.equals("10000000000")){
 				mv.setViewName("redirect:../storepc_StoreManageController/goInformation.do?jichushezhi=11000000000&store_id="+store_id);//到基础信息
			}else if(jichushezhi.equals("11000000000")){
  				mv.setViewName("redirect:../storepc_StoreManageController/goImage.do?jichushezhi=11100000000&store_id="+store_id);//到图片地址
			}else if(jichushezhi.equals("11100000000")){
				mv.setViewName("redirect:../storepcOperator_file/findOperator.do?jichushezhi=11110000000&store_id="+store_id);//到操作员
			}else if(jichushezhi.equals("11110000000")){
				mv.setViewName("redirect:../storepcOperator_file/list.do?jichushezhi=11111000000&store_id="+store_id);//到班次
			}else if(jichushezhi.equals("11111000000")){
				mv.setViewName("redirect:../storepc_tableNumber/list.do?jichushezhi=11111100000&store_id="+store_id);//到班次
 			}else if(jichushezhi.equals("11111100000")){
 				mv.setViewName("redirect:../storepc_marketing/goZhifu.do?jichushezhi=11111110000&store_id="+store_id);//到消费场景
 			}else if(jichushezhi.equals("11111110000")){
  				mv.setViewName("redirect:../storepc_marketing/goIntegral.do?jichushezhi=11111111000&store_id="+store_id);//到积分设置
			}else if(jichushezhi.equals("11111111000")){
				mv.setViewName("redirect:../storepc_wealth/list.do?jichushezhi=11111111100&store_id="+store_id);//到充值积分
 			}else if(jichushezhi.equals("11111111100")){
  				mv.setViewName("redirect:../storepc_StoreManageController/showSenior.do?jichushezhi=11111111110&store_id="+store_id);//到高级信息页面
 			}else if(jichushezhi.equals("11111111110")){
				mv.setViewName("redirect:../storepc_redpackets/list.do?jichushezhi=11111111111&store_id="+store_id);//到发红包界面
 			}else if(jichushezhi.equals("11111111111")){
				mv.setViewName("storepc/shezhi_12");//到最后一个页面
			}
  			pd.put("jichushezhi", jichushezhi);
  			storeManageService.editJiuchushezhi(pd);//修改基础信息
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
 		return mv;
	}
	
	
	/**
	 *更新商家的推广二维码
	 *storepc/gengxin_erweima.do
	 */
	@RequestMapping(value="/gengxin_erweima")
	@ResponseBody
	public Object gengxin_erweima(){
		//logBefore(logger, "判断商家是否已经支付服务费");
		Map<String,Object> map = new HashMap<String,Object>();
    	String result="1";
  		String message="更新商家的推广二维码";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			List<PageData> storeList=ServiceHelper.getAppStoreService().listAllStore(pd);
  			for (PageData e : storeList) {
  				String store_id=e.getString("store_id");
  				String store_name=e.getString("store_name");
  			//生成二维码图片
  				String imagename=store_id;
  				String tuiguangUrl="https://www.jiuyuvip.com/html_member/goRegister.do?recommended="+store_id+"&recommended_type=1&recommended_phone="+store_id;
  	 			OneEr.printStore(tuiguangUrl, store_name , imagename,Const.ErWeiMa);
  	 			String path_url=Const.ErWeiMa+ "/"+imagename+".png";
//  		 		是否需要将图片上传至云服务器
  	 			String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
  				String filePath = "/storeErFile";//文件上传路径
  				String cityFilename =  FileUpload.fileUpFile(path_url, currentPath+filePath, imagename);//字符拼接，上传到服务器上
  				String path_url2= AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
  				System.out.println("商家推广图片地址:"+path_url2);
  				//删除本地商家号
  		 		FileUpload.deleteFile(path_url);
			}
    	} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	 
	
	/**
	 * 更新展示的会员ID以及密码
 	 * www.jiuyuvip.com/storepc/mimaMd5.do
	 */
	@RequestMapping(value="/mimaMd5")
	@ResponseBody
	public  Object MimaMd5(){
		 PageData _pd=new PageData();
 		 try{ 
 			 List<PageData> allstore=ServiceHelper.getAppStoreService().listPasswordStore(_pd);
 			 for (PageData e : allstore) {
  				if(e.getString("password") != null && e.getString("password").length() < 32){
  					e.put("password", MD5.md5(e.getString("password")));
 				}	
  				if(e.getString("only_store_id") == null || e.getString("only_store_id").equals("")){
  					e.put("only_store_id", BaseController.getOnlyStoreID(e.getString("store_id")));
  				}
  				ServiceHelper.getAppStoreService().edit(e);
			 }
 			 List<PageData> allstoreoparator=ServiceHelper.getAppStoreService().listPasswordStoreOparator(_pd);
 			 for (PageData e : allstoreoparator) {
  				if(e.getString("operator_password") != null &&  e.getString("operator_password").length() < 32){
  					e.put("operator_password", MD5.md5(e.getString("operator_password")));
 				}	
 				ServiceHelper.getStoreManageService().updateOperator(e);
			}
 			 
 		 }catch(Exception e){
 			 logger.error(e.toString(), e);
		 }
 		 return _pd;
	 }
}
