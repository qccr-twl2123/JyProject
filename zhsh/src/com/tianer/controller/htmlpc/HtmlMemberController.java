package com.tianer.controller.htmlpc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pingplusplus.model.Charge;
import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.pay_history.Pay_historyController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.controller.youxuan.YouXuanController;
import com.tianer.controller.zhihui.payMoney.ChargeExample;
import com.tianer.entity.Page;
import com.tianer.entity.html.HtmlUser;
import com.tianer.entity.zhihui.OrderShop;
import com.tianer.entity.zhihui.TihuoTask;
import com.tianer.service.business.app_advert.App_advertService;
import com.tianer.service.business.cm_all.Cm_allService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.htmlwx.HtmlWxService;
import com.tianer.service.memberapp.AppCity_fileService;
import com.tianer.service.memberapp.AppFriendService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppShopCarService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.memberapp.AppStorepc_marketingService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.youxuan.YouXuanService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.Distance;
import com.tianer.util.GetFirstZiMu;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;
import com.tianer.util.ping.util.WxUtil;
import com.tianer.util.ping.util.WxpubOAuth;
import com.tianer.util.wxpay.WXPayPath;
 
/** 
 * 
* 类名称：HtmlMemberController   
* 类描述：  h5的页面
* 创建人：魏汉文  
* 创建时间：2016年5月26日 下午3:46:49
 */
@Controller("htmlMemberController")
@RequestMapping(value="/html_member")
public class HtmlMemberController extends BaseController {
	
	
	/**
	 * 判断短信验证码是否正确
	 * html_member/IsOKMessageCode.do?
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
	 * html_member/findPhoneByZhuCe.do
	 * 
	 * tuxingcode 图形验证码
	 * phone 手机号
  	 * 
	 */
	@RequestMapping(value="/findPhoneByZhuCe") 
	@ResponseBody
	public Object findPhoneByZhuCe(HttpServletRequest request){
 		Map<String,Object> map = new HashMap<String,Object>();
 		PageData pd = new PageData();
		String result="1";
		String message="验证码已发至你手机，请注意查收";
		String code=StringUtil.getSixRandomNum();
		String validate = (String)request.getSession().getAttribute("validateCode");  //获取缓存验证码
		try{
			pd=this.getPageData();
			String tuxingcode=pd.getString("tuxingcode");
			 //进行图形验证验证toLowerCase()不区分大小写
			if(tuxingcode == null || tuxingcode.equals("") || validate == null || !tuxingcode.equalsIgnoreCase(validate)){
 				map.put("result", "0");
		 		map.put("message", "图形验证码不能为空/错误");
		 		map.put("data", "");
		  		return map;
			} 
  			if(appMemberService.detailByPhone(pd) != null){
 				map.put("result", "0");
 				map.put("message", "当前手机已注册，请前往登陆");
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
 		    //将验证码放到session里
 		    SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_MEMBER_ZHUCECODE , code);
   		} catch(Exception e){
			logger.error(e.toString(), e);
			result="0";
			message="系统错误";
		}
		map.put("message", message);
	    map.put("result", result);
	    map.put("data", "");
		return map;
	}
	
	
	
	
	
	/**
	 * getSignatureAjax
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/getSignatureAjax")
	@ResponseBody
	public Object getSignatureAjax( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String noncestr=pd.getString("noncestr");
			String timestamp=pd.getString("timestamp");
			String url=pd.getString("now_url");
			String jsapi_ticket=appMemberService.getWxAccess(pd).getString("jsapi_ticket");
 			if(jsapi_ticket.equals("")){
				jsapi_ticket=WxpubOAuth.setJiChuAccess_token().getString("jsapi_ticket");
			}
 			map.put("jsapi_ticket", jsapi_ticket);
			String signature=WxpubOAuth.getSignatureAjax(noncestr, timestamp, jsapi_ticket, url);
			map.put("data", signature);
 			map.put("appId", "wx62d81eec40f745b4");
		} catch(Exception e){
			result="0";
			message="系统异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}

	
//	
//	/**
//	 * 访问登录页
//	 * @return
//	 */
//	@RequestMapping(value="/getOpenid")
//	public ModelAndView getOpenid()throws Exception{
//		ModelAndView mv = this.getModelAndView();
// 		HtmlUser huser=new HtmlUser();
// 		PageData pd = new PageData();
//  		try {
//  				pd = this.getPageData();
//				//获取用户的openid
//				String code = pd.getString("code");
//				if(code != null){
//					String wxopen_id = WxpubOAuth.getOpenId(WxUtil.APP_ID, WxUtil.APP_SECRET, code);
//    				pd=WxpubOAuth.getWxInformation(pd,wxopen_id,appMemberService.getWxAccess(pd).getString("access_token") );
//				}
// 				//根据openid判断是否存在用户
// 				if(appMemberService.getByOpenid(pd)== null){
//					pd.put("ok", "0");
// 				}else{
//					huser.setMember_id(appMemberService.getByOpenid(pd).getString("member_id"));
//					pd.put("ok", "1");
//					//账号信息更新
//					pd.put("member_id",appMemberService.getByOpenid(pd).getString("member_id"));
// 					pd.put("islogin","1");
//					appMemberService.editHtmlLogin(pd);
//					this.getRequest().getSession().setAttribute("wxlogin_id",appMemberService.getByOpenid(pd).getString("member_id"));
// 				}
// 				huser.setOpen_id(pd.getString("wxopen_id"));
// 				huser.setWxunionid(pd.getString("wxunionid"));
//				SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_H5_USER, huser);
// 				mv.addObject("pd", pd);
//   		} catch (Exception e) {
//				// TODO: handle exception
// 				logger.error(e.toString(), e);
// 		}
//		mv.setViewName("htmlmember/Land");
//		return mv;
//	}
//	
//	
//	
//	/**
//	 * 访问登录页
//	 * @return 
//	 * @return 
//	 * @return
//	 * 
//	 */
//	@RequestMapping(value="/toLogin")
//	public void toLogin(HttpServletRequest request,HttpServletResponse response){
//		String code = "";
//		try {
//			code = WxpubOAuth.createOauthUrlForCode(WxUtil.APP_ID, WxUtil.HOST+"/html_member/getOpenid.do", true);
////			System.out.println(code);
//			response.sendRedirect(code); 
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	
//	
//	/**
//	 *  去注册页面two
//	 */
//	@RequestMapping(value="/twoRegister")
//	public ModelAndView twoRegister(){
////		logBefore(logger, " 去注册页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		String openid="";
//		try{ 
//			pd = this.getPageData();
//  			//获取用户的openid
//			String code = pd.getString("code");
//			if(code != null){
//				openid = WxpubOAuth.getOpenId(WxUtil.APP_ID, WxUtil.APP_SECRET, code);
//	 			//通过openid获取相关信息
// 				mv.addObject("wxopen_id", openid);
// 			} 
//			//判断是否注册过/绑定过。是的话直接前往首页
//			pd.put("wxopen_id", openid);
//			if(appMemberService.getByOpenid(pd) == null){
//				mv.setViewName("htmlmember/wxregister");
//			}else{
//				mv.setViewName("redirect:toLogin.do");
//			}
//   		} catch(Exception e){
//			logger.error(e.toString(), e);
//			mv.setViewName("htmlmember/wxregister");
//		}
//		mv.addObject("pd", pd);
// 		return mv;
//	}
	
	
	
//	/**
//	 * 去html登陆页
//	 */
//	@RequestMapping(value="/gouhtmlLogin")
//	public ModelAndView gouhtmlLogin(){
////		logBefore(logger, "去html登陆页");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			 pd = this.getPageData();
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/Land");
//		return mv;
//	}
	
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	/**
	 * 去首页
	 */
	@RequestMapping(value="/gouShouYe")
	public ModelAndView gouShouYe(Page page){
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{ 
			 pd = this.getPageData();
			 pd=HtmlMemberController.loginSomeInfor(pd);
			 if(pd.getString("flag").equals("false")){//判断是否登录过
				 	mv.setViewName("redirect:toLoginWx.do");
					return mv;
			 }
 			 //通过城市city_name查找省
			PageData propd=pcdService.findProvinceByIdByCity(pd);
			if(propd !=null){
					pd.put("province_name", propd.getString("name"));
 			}
			propd=null;
			//获取当省市区的name获取城市档案ID，再获取营销参数
	        PageData citypd=appCity_fileService.findIdByPcd(pd);
			if(citypd == null){
				mv.addObject("cityflag", "false");
			}else{
 				//获取城市一级分类数据
				citypd.put("sort_parent_id", "0");
				citypd.put("sort_type", "1");
				List<PageData>	firstList = appCity_fileService.listAllCitySort(citypd);//列出City_file列表
				mv.addObject("firstList", firstList);
 				firstList=null;
				citypd.put("advert_type", "1");
				List<PageData> advertList=app_advertService.listAllAdvert(citypd);
				for (PageData e : advertList) {
					if(e.getString("hyperlink_type").equals("2")){
						e.put("hyperlink_url",BaseController.jiami( e.getString("hyperlink_url") ));
					}
				}
				mv.addObject("advertList", advertList);
				advertList=null;
 			}
      	} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/index");
		return mv;
	}
	
	
	/**
	 * 去商家商家搜索页面
	 */
	@RequestMapping(value="/gSSS")
	public ModelAndView gouStoreSouSuo(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			 pd = this.getPageData();
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/sousuo");
		return mv;
	}
	
	
	/**
	 * 去商家商家筛选页面
	 * html_member/gSSX.do
	 * 
	 */
	@RequestMapping(value="/gSSX")
	public ModelAndView gouStoreShaiXuan(Page page){
 		ModelAndView mv = this.getModelAndView();
 		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
				//获取当省市区的name获取城市档案ID，再获取营销参数
			    PageData citypd=appCity_fileService.findIdByPcd(pd);
				if(citypd == null){
					mv.addObject("cityflag", "false");
				}else{
	 				//获取城市一级分类数据
					citypd.put("sort_parent_id", "0");
					citypd.put("sort_type", "1");
					List<PageData>	firstList = appCity_fileService.listAllCitySort(citypd);//列出City_file列表
					mv.addObject("firstList", firstList);
					PageData firstpd=null;
					int firstn=firstList.size();
					for (int i = 0; i < firstn; i++) {
	  						firstpd=firstList.get(i);
			 				//获得二级分类
	  						PageData twpopd=new PageData();
	  						twpopd.put("sort_parent_id", firstpd.getString("city_file_sort_id"));
	  						twpopd.put("sort_type", "2");
							List<PageData> twoList=appCity_fileService.listAllCitySort(twpopd);
	 						firstpd.put("twoList", twoList);
			  				twoList=null;
			  				twpopd=null;
	 				}
	 				mv.addObject("firstList", firstList);
	 				firstList=null;
	 				citypd=null;
 	 			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/index_fl");
		return mv;
	}
	
	
	
	
	/**
	 * 获取商家集合--必须是post集合
	 * html_member/gSLPage.do
	 * 参数
	 * paixu    排序参数
	 * shaixuan   筛选参数
	 * city_file_sort_id 分类id
 	 * sort_type   分类类型
	 * sort_name   分类名称
	 * content   搜索框内容
	 * member_id  
	 * @return
	 */
	@RequestMapping(value="/gSLPage")
	@ResponseBody
	public Object GetStoreListPage(Page page){
 		Map<String,Object> map = new HashMap<String,Object>();
     	String result="1";
  		String message="获取成功";
		PageData pd = new PageData();
 		List<PageData> allstoreList=new ArrayList<PageData>();//用来存储商家List
		List<PageData> allgoodsList=new ArrayList<PageData>();//用来存储商品List
		try { 
			pd=this.getPageData();
 // 		String nowpage=(pd.getString("nowpage") == null?"1":pd.getString("nowpage"));
//			page.setCurrentPage(Integer.parseInt(nowpage));
//			page.setPd(pd);
//			if(pd.getString("change_type") == null || pd.getString("change_type").equals("1")){
//				allstoreList=ServiceHelper.getAppStoreService().getStorelistPage(page);
//				PageData e=null;
//				int n=allstoreList.size();
//	  			for(int i=0 ; i< n  ;i++){
//	 							e=allstoreList.get(i);
//								//判断是否开通zk
//	 							if(ServiceHelper.getAppStorepc_marketingService().getZKById(e) == null){
//									e.put("zkstatus", "0");
//								}else{
//									e.put("zkstatus", "1");
//								}
//								//判断是否有红包
//								pd.put("store_id", e.getString("store_id"));
//								Map<String,Object> redmap=TongYong.storeAndMemberByRed(pd);//包括会员id和商家id
//								boolean flag=(boolean) redmap.get("flag");//判断是否还有符合的红包
//								if(flag){
//									e.put("haveRed", "1");
//								}else{
//									e.put("haveRed", "0");
//								}
//								//定位处理
//								if(Double.parseDouble(e.getString("distance") )-Const.maxjuli > 0 ){
//									e.put("distance", Const.maxjuli+"km+");
//								}else{
//									e.put("distance", e.getString("distance")+"km");
//								}
//	  							//获取营销规则
//								List<PageData> marketlist=ServiceHelper.getAppStorepc_marketingService().listAllMarketing(e);
//								e.put("marketlist", marketlist);
//								e.put("sk_shop",BaseController.jiami(e.getString("store_id")));
//	 			}
// 			}else{
// 				allgoodsList=ServiceHelper.getAppGoodsService().getGoodslistPage(page);
//			}
//			map.put("storeList", allstoreList);
//			map.put("allgoodsList", allgoodsList);
//			if(pd.getString("change_type") == null || pd.getString("change_type").equals("")){
//				pd.put("change_type", "1");
//			}
//			map=AppIndexSomeThingJk.getShaiXuan(map, pd.getString("change_type"));
			//原版
			map=GetStoreList(pd, page);
 		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	/**
	 * 获取商家集合-- 
 	 * 参数
	 * paixu    排序参数
	 * shaixuan   筛选参数
	 * city_file_sort_id 分类id
 	 * sort_type   分类类型
	 * sort_name   分类名称
	 * content   搜索框内容
	 * @return
	 */
	public static Map<String,Object>  GetStoreList(PageData pd,Page page){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
   			String nowpage=pd.getString("nowpage");
			if(nowpage == null){
				nowpage="1";
			}
   			page.setCurrentPage(Integer.parseInt(nowpage));
   			page.setPd(pd);
			List<PageData> storeList=ServiceHelper.getHtmlWxService().getStorelistPage(page);
			PageData e=null;
			int n=storeList.size();
  			for(int i=0 ; i< n  ;i++){
 							e=storeList.get(i);
							//判断是否开通zk
 							if(ServiceHelper.getAppStorepc_marketingService().getZKById(e) == null){
								e.put("zkstatus", "0");
							}else{
								e.put("zkstatus", "1");
							}
							//判断是否有红包
							pd.put("store_id", e.getString("store_id"));
							Map<String,Object> redmap=TongYong.storeAndMemberByRed(pd);//包括会员id和商家id
							boolean flag=(boolean) redmap.get("flag");//判断是否还有符合的红包
							if(flag){
								e.put("haveRed", "1");
							}else{
								e.put("haveRed", "0");
							}
							//定位处理
							if(Double.parseDouble(e.getString("distance") )-Const.maxjuli > 0 ){
								e.put("distance", Const.maxjuli+"km+");
							}else{
								e.put("distance", e.getString("distance")+"km");
							}
  							//获取营销规则
							List<PageData> marketlist=ServiceHelper.getAppStorepc_marketingService().listAllMarketing(e);
							e.put("marketlist", marketlist);
							e.put("sk_shop",BaseController.jiami(e.getString("store_id")));
 			}
  			map.put("storeList", storeList);
    		storeList=null;
 		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
		return map;
	}
 
	
	/**
	 *  去获取区
	 *  html_member/goArea.do
	 */
	@RequestMapping(value="/goArea")
	public ModelAndView GoArea(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
 			pd.put("open_status", "1");
			//获取所有区域
			List<PageData>	varList = appCity_fileService.listAllArea(pd);//列出City_file列表
			mv.addObject("varList", varList);
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/index_dress");
		return mv;
	}
	
	/**
	 *  去所有城市
	 */
	@RequestMapping(value="/goAllCityone")
	public ModelAndView goAllCityone(){
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/index_dress1");
		return mv;
	}
	
	/**
	 * 列表获取所有市ajax
	 */
	@RequestMapping(value="/listCity")
	@ResponseBody
	public Object listCity(){
 		Map<String,Object> map = new HashMap<String,Object>();
 		GetFirstZiMu obj1 = new GetFirstZiMu();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("open_status", "1");
 			List<PageData>	varList = appCity_fileService.listAllCity(pd);//列出City_file列表
 			GetFirstZiMu gfz = new GetFirstZiMu();
			Map<String,List<PageData>> mapTwo=gfz.sortTwo(varList,"city_name");
 			List<Map<String,List<PageData>> >	endList=new ArrayList<Map<String,List<PageData>> >();
 			endList.add(mapTwo);
 			map.put("data", endList);
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("messgae", message);
		return map;
	}
	
	
	/**
	 * 前往抢附近红包
	 *  html_member/goFuJinRed.do
	 *  
	 */
	@RequestMapping(value="/goFuJinRed")
	public ModelAndView GoFuJinRed() throws Exception{
 		ModelAndView mv = this.getModelAndView();
     	PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
		}catch(Exception e){
 			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
 		mv.setViewName("htmlmember/qhb");
		return mv;
	}
	
	/**
	 *  抢附近红包随机四个
	 *  html_member/getFuJinRed.do
	 *  
	 */
	@RequestMapping(value="/ajaxGetFuJinRed")
	@ResponseBody
	public Object AjaxGetFuJinRed() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
 		List<PageData> allredList=new ArrayList<PageData>();//用来存储红包List
		List<PageData> fourredList=new ArrayList<PageData>();//用来存储红包List
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			double  longitude1=0;
			double latitude1=0;
			//判断是否定位了
 			if(pd.getString("longitude") == null || pd.getString("longitude").equals("") || pd.getString("latitude") == null || pd.getString("latitude").equals("")){
				map.put("result", "1");
		    	map.put("message","请先定位");
		    	map.put("data", "0");
		    	return map;
			}else{
				longitude1=Double.parseDouble(pd.getString("longitude"));//用户经度
		 		latitude1=Double.parseDouble(pd.getString("latitude"));//用户纬度
  			}
 			//获取当省市区的name获取城市档案ID，再获取营销参数：1.会员领开机红包的频数和刷新时间，2.会员获取开机红包的来源
 			PageData citypd=appCity_fileService.findIdByPcd(pd);
	        if(citypd == null){
		        	map.put("result", "1");
		    		map.put("message","暂无当前营销城市");
		    		map.put("data", "");
		    		return map;
	        } 
			String number="4";
 			//获取营销中的商家红包：0-普通红包，1-开机红包，2-附近红包
	        citypd.put("redpackage_status", Const.fujin_redpackage_status);
	    	List<PageData> oneList=appStore_redpacketsService.listAllFjRed(citypd);
			//获取当前用户的所有红包
			List<PageData>	memredList =member_redpacketsService.listAllById(pd);
 			//除去已领取的红包
			for(int i=0 ; i<oneList.size() ;i++ ){
				 PageData e=oneList.get(i);
				 String store_redpackets_id=e.getString("store_redpackets_id");
				 boolean flag=false;
 				 for(int j=0  ;  j<memredList.size() ;j++ ){
					 PageData f=memredList.get(j);
					 if(store_redpackets_id.equals(f.getString("store_redpackets_id"))){
						 flag=true;
						 break;
					 }
					 f=null;
				 }
 				 if(flag){
 					 oneList.remove(i);
 					 --i;
					 continue;
 				 }else{
 					 //判断是否符合条件
 					if(!TongYong.getRedByMemberIsOk(ServiceHelper.getAppMemberService().findById(pd),e)){
 						 oneList.remove(i);
 						 --i;
 						 continue;
 					}
 				 }
  				e=null;
			}
			//-----------
  			for(int i=0;i<oneList.size() ;i++){
  						boolean isred=false;//用来判断是否符合
						PageData e=oneList.get(i);
						//判断红包是否符合要求
						double money=Double.parseDouble(e.getString("money"));//总金钱
						int redpackage_number=Integer.parseInt(e.getString("redpackage_number"));//总红包
						double overget_money=Double.parseDouble(e.getString("overget_money"));//已领金钱总金钱
						int overget_number=Integer.parseInt(e.getString("overget_number"));//已领红包
						String redpackage_type=e.getString("redpackage_type");
						String choice_type=e.getString("choice_type");
						String everymoney="";
						if(overget_number == redpackage_number  ){//判断是否还有红包
							isred=true;
					   }else{
							   if(redpackage_number-overget_number == 1){
								   	    everymoney=TongYong.df2.format(money-overget_money);
								}else{
 										if(redpackage_type.equals("1")){
												if(choice_type.equals("1")){//随机金额
													//获取平均值
													double pjmoney=(money-overget_money)/(redpackage_number-overget_number);
													double minpjmoney=pjmoney/2;
													double maxpjmoney=pjmoney/2+pjmoney;
													 everymoney=TongYong.df2.format( StringUtil.getSuiJi(minpjmoney, maxpjmoney) );
												}else if(choice_type.equals("2")){
													 everymoney=TongYong.df2.format(money/redpackage_number);//每个用户可获取金钱money
												}
										}else if(redpackage_type.equals("2")){
											everymoney=money+"";
										}
 								}
					   }
 						//判断商家是否符合要求
						String overdistance="0";
						if(e.getString("longitude") == null || e.getString("longitude").equals("")){
							overdistance="--";
							isred=true;
						}else{
							double longitude2=Double.parseDouble(e.getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(e.getString("latitude"));//商家纬度
							 double juli=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
							 overdistance=TongYong.df2.format(juli/1000);//千米为单位
						}
  						if(isred){
							oneList.remove(i);
							i=i-1;
						}else{
							PageData redpd=appStore_redpacketsService.findJJRedById(e);
							redpd.put("money", StringUtil.getMoveLastZero(everymoney));
							redpd.put("distance", overdistance);
							allredList.add(redpd);
						}
  						e=null;
 			}
			
			//处理排序
 			int rednumber=allredList.size();
			if(rednumber > 4){
 				for (int j = 0; j < rednumber+10; j++) {
					int h=(int)(Math.random()*rednumber);
					if(h<=rednumber-1){
						PageData redpd=allredList.get(h);
						String store_id=redpd.getString("store_id");
						Object o=map2.get(store_id);
						if(o == null){
							map2.put(store_id, redpd);
							fourredList.add(redpd);
						}
						if(fourredList.size() == 4){
							break;
						}
					}
 				}
    				 
			}else{
				for (PageData redpd : allredList) {
					String store_id=redpd.getString("store_id");
					Object o=map2.get(store_id);
					if(o == null){
						map2.put(store_id, redpd);
						fourredList.add(redpd);
					}
				}
   			}
 			if(number.equals("4")){
				Collections.sort(fourredList,new Comparator<PageData>(){  
 		            @Override
 		            public int compare(PageData  arg0, PageData arg1) {  
 		            	String distance1=arg1.getString("distance");
 		            	String distance2=arg0.getString("distance");
 		            	int n1=distance1.length();
 		            	int n2=distance2.length();
 		            	if(n2 > n1){
 		            		distance1=StringUtil.buZero(distance1, n2-n1);
 		            	}else if(n1>n2){
 		            		distance2=StringUtil.buZero(distance2, n1-n2);
 		            	}
 		                return distance1.compareTo(distance2);  
 		            }  
 		        });  
			}
				map1.put("redList", fourredList);
 				//获取广告位的集合
 	        	citypd.put("advert_type", "2");
				List<PageData> advertList=app_advertService.listAllAdvert(citypd);
				map1.put("advertList", advertList);
				if(advertList.size() > 0){
					map1.put("advertpd", advertList.get(0));
				}else{
					map1.put("advertpd", new PageData());
				}
				citypd=null;
				advertList=null;
				fourredList=null;
				pd=null;
 		}catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
		return map;
	}
	
	/**
	 * 领取现金折扣红包针对--商家详情红包，附近红包
	 * html_member/getRedPackage.do
	 * 
  	 * store_redpackets_id
	 * money 
 	 */
	@RequestMapping(value="/ajaxGetRedPackage")
	@ResponseBody
	public  Object AjaxGetRedPackage() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
  		String result = "1";
		String message="领取成功";
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				}else{
					map.put("result", "0");
					map.put("message", "请先前往登录");
					map.put("data", "");
			  		return map;
				}
   				BaseController.lock.lock();
 				//判断是否已经领取过当前红包
				pd.put("isok", "0");
				PageData redpd=appMemberService.findRePackagedById(pd);
				if(redpd != null){
					map.put("result", "0");
					map.put("message", "很抱歉，你已领取过当前红包");
					map.put("data", "");
			  		return map;
				}
  				//-----------------------------------------------
				String getmoney=pd.getString("money");//领取的红包金额/折扣数
				//获取红包ID
				PageData e=appStore_redpacketsService.findRedById(pd);
				if(e != null && getmoney != null){
					getmoney=StringUtil.getMoveFloatLastZero(getmoney);//去除小数点后的0
					//判断当前红包是否充足
 					double overget_money=Double.parseDouble(e.getString("overget_money"));//已领金钱总金钱
					int redpackage_number=Integer.parseInt(e.getString("redpackage_number"));//总红包
					int overget_number=Integer.parseInt(e.getString("overget_number"));//已领红包
					if(redpackage_number == overget_number  ){
						  result="0";
						  message="很抱歉，你晚了一步，红包已被领完";
					}else{
 						String srp_usercondition_id=e.getString("srp_usercondition_id");
						String srp_usercondition_content=e.getString("srp_usercondition_content");
						if( srp_usercondition_content == null || srp_usercondition_content.equals("") ){
							srp_usercondition_content="";
						}
						String redpackage_type=e.getString("redpackage_type");
						String choice_type=e.getString("choice_type");
 						if(redpackage_type.equals("1")){
 							srp_usercondition_content=srp_usercondition_content+"减"+getmoney+"元";
 						}else if(redpackage_type.equals("2")){
 							srp_usercondition_content=srp_usercondition_content+"打"+getmoney+"折";
 							if(choice_type.equals("2")){
								srp_usercondition_id=srp_usercondition_id+"1";
							}
 						}
 						//新增红包---会员
 						PageData e2=new PageData();
						e2.put("redpackage_id","member"+BaseController.getTimeID());
						e2.put("member_id", pd.getString("member_id"));
						e2.put("redpackage_money", getmoney);
						e2.put("redpackage_content", srp_usercondition_content);
						e2.put("store_redpackets_id", e.getString("store_redpackets_id"));
						e2.put("redpackage_type", srp_usercondition_id);
						e2.put("enddate", e.get("endtime").toString());
						e2.put("startdate", e.get("starttime").toString());
						e2.put("set_id", e.getString("store_id"));
						e2.put("set_type", "1");//商家
						appMemberService.saveRedForMember(e2);
						//更新会员拥有的红包数量
						ServiceHelper.getAppMemberService().updateMemberRedNumber(e2);
						e2=null;
					   //修改红包数量以及金钱
					    e.put("overget_number", Integer.parseInt(e.getString("overget_number"))+1+"");
						e.put("overget_money",overget_money+Double.parseDouble(getmoney)+"");
						appStore_redpacketsService.edit(e);
 	 				}
				}
				e=null;
  		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}finally{
			BaseController.lock.unlock();
 		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
  		return map;
	}
	
	
	
	
	/**
	 * 推荐好友注册
	 * 魏汉文20160623
	 */
	@RequestMapping(value="/goTuiJianRegister")
 	public ModelAndView GoTuiJianRegister() throws Exception{
  		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			
 		}catch(Exception e){
 			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
   		mv.setViewName("htmlmember/tjhy");
		return mv;
	}
	
	/**
	 * 推荐好友注册
	 * html_member/ajaxTuiJian.do
	 * 
	 * be_phone 被推荐人号码
	 * content 
	 */
	@RequestMapping(value="/ajaxTuiJian")
	@ResponseBody
	public Object ajaxTuiJian() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="推荐等待确认中";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
 			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}else{
				map.put("result", "99");
				map.put("message", "请前往登录");
				map.put("data", "");
 		 		return map;
			}
  			String content=pd.getString("content");
			String be_phone=pd.getString("be_phone");
			pd.put("phone", be_phone);
 			///判断是否注册过
 			if(appMemberService.detailByPhone(pd) != null){
				map.put("result", "0");
				map.put("message", "当前推荐手机号已注册，请重新选择");
				map.put("data", "");
 		 		return map;
			}
  			if(appMemberService.findById(pd) != null){
				SmsUtil.TjFrinendSave(be_phone, appMemberService.findById(pd).getString("phone"),content);
				//魅力值：0-50一星会员 50-99 二星会员 100-199 三星会员 200-499  四星会员 500-999 五星会员 1000-2000 一钻会员
				TongYong.charm_numberAdd(pd.getString("member_id"), Const.charm_number[2]);
 	 			//将邀请信息存入数据库中
				pd.put("type", "2");
				appMemberService.saveTuiJian(pd);
			}
 		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
   		return map;
	}
	
	
	
	

	
//	/**
//	 *  去注册页面
//	 */
//	@RequestMapping(value="/goRegister")
//	public ModelAndView goRegister(){
//		logBefore(logger, " 去注册页面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
//  		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/register");
//		return mv;
//	}
//	
	
//	/**
//	 * 去最后一步注册
//	 */
//	@RequestMapping(value="/gouZhuceNext")
//	public ModelAndView gouZhuceNext(){
// 		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			 pd = this.getPageData();
//   		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/register1");
//		return mv;
//	}
	
	
	
	
	
	@Resource(name="appCity_fileService")
	private AppCity_fileService appCity_fileService;
	
	
	

	
	
	
	
	
 
	@Resource(name="appStorepc_marketingService")
	private AppStorepc_marketingService appStorepc_marketingService;
	
	/**
	 * 去商家的详情页面
	 * html_member/goStoreDetail.do
	 * 
	 * sk_shop 加密的商家ID
	 * 
	 */
	@RequestMapping(value="/goStoreDetail")
	public ModelAndView goStoreDetail(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
 				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
 				}
  				//============================
 				//判断是否开通类别积分购买的权限
				PageData issortjfpd=appStorepc_marketingService.getJfById(pd);
				if(issortjfpd != null && issortjfpd.getString("change_type").equals("3") ){
					pd.put("issortjf", "3");
				}else if(issortjfpd != null && issortjfpd.getString("change_type").equals("2") ){
					pd.put("issortjf", "1");
				}else{
					pd.put("issortjf", "0");
				}
				//情空购物车，session存储
				pd.put("goods_type", "1");
				ServiceHelper.getShopCarService().delShopByMs(pd);
				this.getRequest().getSession().removeAttribute(pd.getString("member_id")+"canUseList");
				this.getRequest().getSession().removeAttribute(pd.getString("member_id")+"notUseList");
 				pd.remove("store_id");
	   			pd.remove("member_id");
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/sj");
		return mv;
	}
	
	
	
	
	/**
	 * 去商家的优惠买单界面
	 */
	@RequestMapping(value="/goStoreYouHuiMaiDan")
	public ModelAndView goStoreYouHuiMaiDan(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			 	pd = this.getPageData();
			 	//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				}
				//判断是否开通类别积分购买的权限
				PageData issortjfpd=appStorepc_marketingService.getJfById(pd);
				if(issortjfpd != null && issortjfpd.getString("change_type").equals("3") ){
					pd.put("issortjf", "3");
				}else if(issortjfpd != null && issortjfpd.getString("change_type").equals("2") ){
					pd.put("issortjf", "1");
				}else{
					pd.put("issortjf", "0");
				}
				
   		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/yhmd");
		return mv;
	}
	
	/**
	 * 去第三方支付页面
	 */
	@RequestMapping(value="/goPay")
	public ModelAndView goPay(){
 		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
		try{ 
			 pd = this.getPageData();
			 //判断是否为H5页面
			 if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			 }
 			 //防止表单重复提交
	 		 String session_orderid=String.valueOf(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_ORDER));
	  		 mv.addObject("session_orderid", session_orderid);
   		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/threezhifu");
		return mv;
	}
	
	
	/**
	 * 去商家详情图界面H5
	 */
	@RequestMapping(value="/goStoreInforByH5")
	public ModelAndView goStoreInforByH5(){
 		ModelAndView mv = this.getModelAndView();
		List<PageData> three=new ArrayList<PageData>();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
  			PageData spd=appStoreService.findByIdOne(pd);
			mv.addObject("spd", spd);
			//获取详情图以及文字
  			List<PageData> imageList=storeManageService.findImage(spd);
			if(imageList.size() != 0){
				PageData imgpd=new PageData();
				imgpd=imageList.get(0);
 				//获取商家详情图片
				String address1=imgpd.getString("address1");
				if(address1 != null && !address1.equals("")){
					String[] addre1=address1.split(",");
					for(int i=0 ; i<addre1.length ;i++){
						PageData adpd=new PageData();
						String[] str=addre1[i].split("@");
  						adpd.put("image_url", str[0]);
						if(str.length == 2){
							adpd.put("text", str[1]);
						}else{
							adpd.put("text", "");
						}
						three.add(adpd);
						adpd=null;
  					}
					for (int j = 0; j < three.size(); j++) {
						if(three.get(j).getString("image_url").equals("img/base_tp.png")){
							String text=three.get(j).getString("text");
							if(j>0 && !text.equals("")) {
								PageData jpd=three.get(j-1);
								String text2=jpd.getString("text");
								text2=text2+text;
								jpd.put("text", text2);
								//替换集合的指定
								three.set(j-1, jpd);
								jpd=null;
							}
 							three.remove(j);
							j=j-1;
						}
 					}
					mv.addObject("three", three);
 				}
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		pd.remove("store_id");
		pd.remove("member_id");
		mv.addObject("pd", pd);
 		mv.setViewName("htmlmember/sjxqth5");
		return mv;
	}
	

	/**
	 * 去商家详情图界面app
	 *html_member/goStoreInforByApp.do
	 */
	@RequestMapping(value="/goStoreInforByApp")
	public ModelAndView goStoreInforByApp(){
 		ModelAndView mv = this.getModelAndView();
		List<PageData> three=new ArrayList<PageData>();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			PageData spd=appStoreService.findByIdOne(pd);
			mv.addObject("spd", spd);
			//获取详情图以及文字
  			List<PageData> imageList=storeManageService.findImage(spd);
			if(imageList.size() != 0){
				PageData imgpd=new PageData();
				imgpd=imageList.get(0);
 				//获取商家详情图片
				String address1=imgpd.getString("address1");
				if(address1 != null && !address1.equals("")){
					String[] addre1=address1.split(",");
					for(int i=0 ; i<addre1.length ;i++){
						PageData adpd=new PageData();
						String[] str=addre1[i].split("@");
  						adpd.put("image_url", str[0]);
						if(str.length == 2){
							adpd.put("text", str[1]);
						}else{
							adpd.put("text", "");
						}
						three.add(adpd);
						adpd=null;
  					}
					for (int j = 0; j < three.size(); j++) {
						if(three.get(j).getString("image_url").equals("img/base_tp.png")){
							String text=three.get(j).getString("text");
							if(j>0 && !text.equals("")){
								PageData jpd=three.get(j-1);
								String text2=jpd.getString("text");
								text2=text2+text;
								jpd.put("text", text2);
								//替换集合的指定
								three.set(j-1, jpd);
								jpd=null;
							}
 							three.remove(j);
							j=j-1;
						}
 					}
					mv.addObject("three", three);
 				}
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
 		mv.setViewName("htmlmember/sjxqtapp");
		return mv;
	}
	
	
	
	/**
	 * 去商家的营业执照界面
	 */
	@RequestMapping(value="/goStoreImage")
	public ModelAndView goStoreImage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/yezz");
		return mv;
	}
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	
	/**
	 * 去充值界面充值
	 */
	@RequestMapping(value="/gochongzhi")
	public ModelAndView gochongzhi(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd=this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				//防止表单重复提交
				 Session session = SecurityUtils.getSubject().getSession();	
				 if(session.getAttribute(Const.SESSION_ORDER) == null ){
						String session_orderid=BaseController.getTimeID();
						session.setAttribute(Const.SESSION_ORDER, session_orderid);
						String sessionid =String.valueOf(session.getId());
						mv.addObject("session_orderid ", sessionid );
	 			}else{
	 					mv.addObject("session_orderid", String.valueOf(session.getAttribute(Const.SESSION_ORDER)));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/chongzhi");
		mv.setViewName("htmlmember/chongzhi_new");
		return mv;
	}

	
	@Resource(name="appShopCarService")
	private AppShopCarService appShopCarService;
	
	
//	/**
//	 * 去商家的商品界面
//	 */
//	@RequestMapping(value="/goStoreGoods")
//	public ModelAndView goStoreGoods(){
// 		ModelAndView mv = this.getModelAndView();
// 		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
// 			//获取购物车
//			List<PageData> shopList=appShopCarService.shopCarList(pd);
//			int n=0;
//			double m=0;
//			for(PageData e : shopList){
//				n+=Integer.parseInt(e.getString("goods_number"));
//				m+=Integer.parseInt(e.getString("goods_number"))*Double.parseDouble(e.getString("sale_money"));
//			}
//			if(shopList.size() == 0){
//				shopList=null;
//			}
//			mv.addObject("shopList", shopList);
//			shopList=null;
//			pd.put("n", n+"");
//			pd.put("m",TongYong.df2.format(m));
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/sp");
//		pd=null;
//		return mv;
//	}
	
	
	
	/**
	 * 去使用红包界面
	 */
	@RequestMapping(value="/goUseRed")
	public ModelAndView goUseRed(){
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
  			}
  			List<PageData> canUseList=(List<PageData>) this.getRequest().getSession().getAttribute(pd.getString("member_id")+"canUseList");
  			List<PageData> notUseList=(List<PageData>) this.getRequest().getSession().getAttribute(pd.getString("member_id")+"notUseList");
 			mv.addObject("canUseList", canUseList);
			mv.addObject("notUseList", notUseList);
			pd.remove("member_id");
			mv.addObject("pd", pd);
			pd=null;
			canUseList=null;
			notUseList=null;
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.setViewName("htmlmember/usered");
 		return mv;
	}
	
	
	/**
	 * 添加进购物车去商品结算页面
	 * html_member/goStoreGoodsOverBuy.do
	 * 
	 * sk_shop       商家加密ID
	 * redpackage_id 红包ID
	 */
	@RequestMapping(value="/goStoreGoodsOverBuy")
	public ModelAndView goStoreGoodsOverBuy(){
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				 //防止表单重复提交
				 Session session = SecurityUtils.getSubject().getSession();	
				 if(session.getAttribute(Const.SESSION_ORDER) == null ){
						String session_orderid=BaseController.getTimeID();
						session.setAttribute(Const.SESSION_ORDER, session_orderid);
						String sessionid =String.valueOf(session.getId());
						mv.addObject("session_orderid ", sessionid );
	 			}else{
	 					mv.addObject("session_orderid", String.valueOf(session.getAttribute(Const.SESSION_ORDER)));
				}
			}
			//营销开始：先判断折扣设置，折扣完的金额计算积分值，接下来是判断折扣后的金额是否满足红包及其它优惠条件；最后的金额是本次应买单的金额；
			double youhui_money=0;
			double notyouhui_money=0;
			//获取购物车
			pd.put("goods_type", "1");
			List<PageData> goodsList=new ArrayList<PageData>();
 			List<PageData> shopList=appShopCarService.shopCarList(pd);
			String allgoods="";
			PageData goodspd=null;
			for(PageData e : shopList){//所有商品：商品id@数量@总金额，商品id@数量@总金额
  				double one=Integer.parseInt(e.getString("goods_number"))*Double.parseDouble(e.getString("sale_money"));
   				if(ServiceHelper.getAppGoodsService().findById(e).getString("goods_type").equals("1")){//今日特价商品不参与优惠--相当于不优惠金额
					notyouhui_money+=one;
				}else{
					youhui_money+=one;
				}
   				goodspd=new PageData();
  				goodspd.put("goods_name", e.getString("goods_name"));
				goodspd.put("shop_number", e.getString("goods_number"));
				goodspd.put("summoney", TongYong.df2.format(one));
				goodsList.add(goodspd);
				goodspd=null;
 				String strgoods=e.getString("goods_id")+"@"+e.getString("goods_number")+"@"+TongYong.df2.format(one );
				allgoods+=strgoods+",";
  			}
 			pd.put("allgoods", allgoods);
  			//优惠买单信息
			Map<String,Object> yhmdpd=TongYong.YouHuiMaiDanByTwoForMember(pd, youhui_money, notyouhui_money);
			yhmdpd.put("goodsList", goodsList);
			yhmdpd.put("sk_shop", pd.getString("sk_shop"));
 			mv.addObject("yhmdpd",yhmdpd );
 			this.getRequest().getSession().removeAttribute(pd.getString("member_id")+"canUseList");
			this.getRequest().getSession().removeAttribute(pd.getString("member_id")+"notUseList");
 			PageData useRedPd = (PageData) yhmdpd.get("useRedPd");
 			this.getRequest().getSession().setAttribute(pd.getString("member_id")+"canUseList", (List<PageData>) useRedPd.get("canUseList"));
 			this.getRequest().getSession().setAttribute(pd.getString("member_id")+"notUseList", (List<PageData>) useRedPd.get("notUseList"));
  			yhmdpd=null;
  			useRedPd=null;
//  			mv.setViewName("htmlmember/shopcatpay");
  			mv.setViewName("htmlmember/shopcatpay_new");
  			pd=null;
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		return mv;
	}
	
	/**
	 * 去商家的评论界面
	 */
	@RequestMapping(value="/goStoreComment")
	public ModelAndView goStoreComment(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/pj");
		return mv;
	}
	
	
//	
//	/**
//	 * 去我的订单界面
//	 */
//	@RequestMapping(value="/goMyOrder")
//	public ModelAndView goMyOrder(){
//		logBefore(logger, "去我的订单界面");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
//			System.out.println(pd.toString());
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/index_order");
//		return mv;
//	}
	
	
	
	/**
	 * 去我的订单评价
	 */
	@RequestMapping(value="/goMyOrderAddComment")
	public ModelAndView goMyOrderAddComment(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/khpj");
		return mv;
	}
	
	
 
	
	@Resource(name="appFriendService")
	private AppFriendService appFriendService;
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	@Resource(name="htmlWxService")
	private HtmlWxService htmlWxService;
	
	/**
	 * 去人脉圈
	 * html_member/gouRenMai.do?type=
	 */
	@RequestMapping(value="/gouRenMai")
	public ModelAndView gouRenMai(){
 		ModelAndView mv = this.getModelAndView();
  		Session session =  SecurityUtils.getSubject().getSession();
    		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
 			if(session.getAttribute(Const.SESSION_H5_USER) == null){
 				HtmlUser huser=new HtmlUser();
				 String member_id=pd.getString("member_id");
				 if(member_id == null || member_id.equals("")){
					 member_id="";
				 }
				 String latitude=pd.getString("latitude");
				 String longitude=pd.getString("longitude");
				 String province_name=pd.getString("province_name");
				 String city_name=pd.getString("city_name");
				 String area_name=pd.getString("area_name");
				 huser.setLat(latitude);
				 huser.setLng(longitude);
				 huser.setProvince_name(province_name);
				 huser.setCity_name(city_name);
				 huser.setArea_name(area_name);
				 huser.setMember_id(member_id);
				 session.setAttribute(Const.SESSION_H5_USER, huser);
 			 }else{
 				 HtmlUser huser=(HtmlUser) session.getAttribute(Const.SESSION_H5_USER);
				 String province_name=huser.getProvince_name();
				 String city_name=huser.getCity_name();
				 String area_name=huser.getArea_name();
				 String longitude=huser.getLng();
				 String latitude=huser.getLat();
				 String member_id=huser.getMember_id();
	 			 pd.put("province_name", province_name);
				 pd.put("city_name", city_name);
				 pd.put("area_name",area_name );
				 pd.put("longitude",longitude );
				 pd.put("latitude",latitude );
				 pd.put("member_id", member_id);
			 }
			String type=pd.getString("type");
			if(type == null || type.equals("")){
				mv.setViewName("htmlmember/index_rm");
			}
			else if(type.equals("1")){//去我的人脉
				mv.setViewName("htmlmember/rm_me");
			}
			else if(type.equals("2")){//找商家发红包
				mv.setViewName("htmlmember/zsjfhb");
			}
			else if(type.equals("3")){//推荐手机联系人
				mv.setViewName("htmlmember/tjsjlxr");
			}
			else if(type.equals("4")){//群聊
				mv.setViewName("htmlmember/qunliao");
			}
			else if(type.equals("5")){//客服
				mv.setViewName("htmlmember/index_rm");
			}
			else if(type.equals("6")){//最近聊天记录
				mv.setViewName("htmlmember/ltjl");
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
 		return mv;
	}
	
	
//	/**
//	 * 找商家发红包
//	 */
//	@RequestMapping(value="/gouRenMaiFindStoreRed")
//	public ModelAndView gouRenMaiFindStoreRed(){
//		logBefore(logger, "找商家发红包");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/zsjfhb");
//		return mv;
//	}
	
//	/**
//	 * 去我的人脉
//	 */
//	@RequestMapping(value="/gouMyRenMai")
//	public ModelAndView gouMyRenMai(){
//		logBefore(logger, "去我的人脉");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/rm_me");
//		return mv;
//	}
	
//	/**
//	 * 去推荐手机联系人
//	 */
//	@RequestMapping(value="/gouRenMaiTuiJianPhone")
//	public ModelAndView gouRenMaiTuiJianPhone(){
//		logBefore(logger, "去推荐手机联系人");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/tjsjlxr");
//		return mv;
//	}
//	
//
//	
//	/**
//	 * 群聊
//	 */
//	@RequestMapping(value="/gouRenMaiQunLiao")
//	public ModelAndView gouRenMaiQunLiao(){
//		logBefore(logger, "群聊");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/qunliao");
//		return mv;
//	}
	
	

//	
//	/**
//	 * 通知客服建议
//	 */
//	@RequestMapping(value="/gouRenMaiTongZhi")
//	public ModelAndView gouRenMaiTongZhi(){
//		logBefore(logger, "找商家发红包");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/index_rm");
//		return mv;
//	}
	
	
//	/**
//	 * 最近聊天记录
//	 */
//	@RequestMapping(value="/gouRenMaiZuiJinChat")
//	public ModelAndView gouRenMaiZuiJinChat(){
//		logBefore(logger, "最近聊天记录");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/ltjl");
//		return mv;
//	}
	
	/**
	 * 去聊天
	 */
	@RequestMapping(value="/gouRenMailiaotian")
	public ModelAndView gouRenMailiaotian(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/liaotian");
		return mv;
	}
	
	
	
//	
//
//	
//	/**
//	 * 我的好友集合（人脉圈）
//	 */
//	@RequestMapping(value="/myFriendList")
//	@ResponseBody
//	public  Object myFriendList(){
//		logBefore(logger, "我的好友列表");
//		Map<String,Object> map = new HashMap<String,Object>();
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		List<PageData>	allList=new ArrayList<PageData>();
//  		String result = "1";
//		String message="查询成功";
//		PageData pd = new PageData();
//		try{ 
//			pd=this.getPageData();
//			//当本人是邀请人时
//			PageData friendpd=new PageData();
//			friendpd.put("invite_id", pd.getString("member_id"));
//			friendpd.put("friend_status", "2");
//			List<PageData>	be_invite_List = appFriendService.myFriendList(friendpd);	//获得所有被邀请好友的列表
//			for(PageData e : be_invite_List ){
//				 if(e.getString("be_invite_type").equals("2")){//会员
//					 e.put("member_id", e.getString("be_invite_id"));
//					 e=appMemberService.contactMember(e);
//					 if(e != null){
//						 e.put("type", "2");
//						 allList.add(e);
//						 String name=e.getString("name");
//		 				 if(name != null &&  name.length()==11){
//								e.put("name", name.substring(0, 3)+"****"+name.substring(7, 11));
//						 }
//					 }
//  				 }else  if(e.getString("be_invite_type").equals("1")){
//					 e.put("store_id", e.getString("be_invite_id"));
//					 e=appStoreService.contactStore(e);
//					 if(e != null){
//						 e.put("type", "1");
//						 allList.add(e);
//					 }
// 				 }
//			}
//			//当本人是被邀请人时
//			friendpd=null;
//			friendpd=new PageData();
//			friendpd.put("be_invite_id", pd.getString("member_id"));
//			friendpd.put("friend_status", "2");
//			List<PageData>	invite_List = appFriendService.myFriendList(friendpd);	//获得所有被邀请好友的列表
//			for(PageData e : invite_List ){
//				 if(e.getString("invite_type").equals("2")){//会员
//					 e.put("member_id", e.getString("invite_id"));
//					  e=appMemberService.contactMember(e);
//					  if(e != null){
//						  e.put("type", "2");
//						  allList.add(e);
//						  String name=e.getString("name");
//			 			  if(name != null && name.length()==11){
//									e.put("name", name.substring(0, 3)+"****"+name.substring(7, 11));
//						 }
//					  }
// 				 }else if(e.getString("invite_type").equals("1")){
//					 e.put("store_id", e.getString("invite_id"));
//					 e=appStoreService.contactStore(e);
//					 if(e != null){
//						 e.put("type", "1");
//						 allList.add(e);
//					 }
// 				 }
//			}
// 			Map<String,List<PageData>> mapTwo=GetFirstZiMu.sortTwo(allList,"name");
// 			List<Map<String,List<PageData>> >	endList=new ArrayList<Map<String,List<PageData>> >();
// 			endList.add(mapTwo);
//			map1.put("alllist", endList);
//			mapTwo=null;
//			allList=null;
//			//获取推荐人信息(判断是否有登录)
//			String member_id = pd.getString("member_id");
//			if(member_id == null || member_id.equals("")){
//				result="0";
//				message="请先登录！";
//				map.put("data", "");
//				map.put("result", result);
//				map.put("message", message);
//				return map;
//			}else{
//					pd=appMemberService.findById(pd);
//					if(pd != null ){
//						    PageData mpd2=new PageData();
//							if(pd.getString("recommended_type").equals("1")){
//								mpd2.put("store_id", pd.getString("recommended"));
//								mpd2=appStoreService.contactStore(mpd2);
//								if(mpd2 != null){
//									mpd2.put("type", "1");
//									map1.put("user", mpd2);
//								}
// 							}else if(pd.getString("recommended_type").equals("2")){
// 								mpd2.put("member_id", pd.getString("recommended"));
// 								mpd2=appMemberService.contactMember(pd);
//								if(mpd2 != null){
//									mpd2.put("type", "2");
//									map1.put("user", mpd2);
//								}
// 							}else{
// 								mpd2.put("id", pd.getString("member_id"));
// 								mpd2.put("image_url", pd.getString("image_url"));
// 								mpd2.put("name", pd.getString("name"));
// 								mpd2.put("type", "2");
//								map1.put("user", mpd2);
//							}
//							mpd2=null;
// 					}
//			}
//   		} catch(Exception e){
//			result = "0";
//			message="查询失败";
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", map1);
//		return map;
//	}

	@Resource(name="cm_allService")
	private Cm_allService cm_allService;
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService member_redpacketsService;
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService appStore_redpacketsService;
	@Resource(name="app_advertService")
	private App_advertService app_advertService;
	
	
	@Resource(name="youXuanService")
	private YouXuanService youXuanService;
	
	
	
	
	@Resource(name="appOrderService")
	private AppOrderService orderService;
	
	/**
	 * 根据提货券id或者订单id查询订单详情
	 */
	@RequestMapping(value="/findById")
 	public ModelAndView findById(){
 		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
 		} catch(Exception e){
 			logger.error(e.toString(), e);
		}
  		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/ddxq");
		return mv;
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	public static void main(String[] args){
		try {
			
 		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	

	/**
	 * 去我的优选爆品界面列表
	 */
	@RequestMapping(value="/goMyYouXuan")
	public ModelAndView goMyYouXuan(Page page){
 		ModelAndView mv = this.getModelAndView();
		//Shiro session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		HtmlUser huser=(HtmlUser) session.getAttribute(Const.SESSION_H5_USER);
 		PageData pd = new PageData();
		try{ 
			 pd = this.getPageData();
			 if(huser == null){
				 huser=new HtmlUser();
				 String member_id=pd.getString("member_id");
				 if(member_id == null || member_id.equals("")){
					 member_id="";
				 }
				 String latitude=pd.getString("latitude");
				 String longitude=pd.getString("longitude");
				 String province_name=pd.getString("province_name");
				 String city_name=pd.getString("city_name");
				 String area_name=pd.getString("area_name");
				 huser.setLat(latitude);
				 huser.setLng(longitude);
				 huser.setProvince_name(province_name);
				 huser.setCity_name(city_name);
				 huser.setArea_name(area_name);
				 huser.setMember_id(member_id);
				 session.setAttribute(Const.SESSION_H5_USER, huser);
 			 }else{
				 String province_name=huser.getProvince_name();
				 String city_name=huser.getCity_name();
				 String area_name=huser.getArea_name();
				 String longitude=huser.getLng();
				 String latitude=huser.getLat();
				 String member_id=huser.getMember_id();
	 			 pd.put("province_name", province_name);
				 pd.put("city_name", city_name);
				 pd.put("area_name",area_name );
				 pd.put("longitude",longitude );
				 pd.put("latitude",latitude );
				 pd.put("member_id", member_id);
			 }
   		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("youxuan/yxbp");
		return mv;
	}
	
	/**
	 * 去我的优选爆品界面详情 youxuangoods_id 
	 */
	@RequestMapping(value="/goMyYouXuanDetail")
	public ModelAndView goMyYouXuanDetail(   ){
//		logBefore(logger, "去我的优选爆品界面详情");
		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			//通过省市区获取当前的城市city_file_id
			PageData citypd=ServiceHelper.getCity_fileService().findById(youXuanService.findByIdGoods(pd));
			if(citypd != null){
				pd.put("province_name", citypd.getString("province_name"));
				pd.put("city_name", citypd.getString("city_name"));
				pd.put("area_name", citypd.getString("area_name"));
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("youxuan/yxbpd");
		return mv;
	}
	
	/**
	 * 去我的优选爆品分享界面 youxuangoods_id 
	 */
	@RequestMapping(value="/goMyYouXuanfengxaingDetail")
	public ModelAndView goMyYouXuanfengxaingDetail(   ){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			//通过省市区获取当前的城市city_file_id
			PageData citypd=ServiceHelper.getCity_fileService().findById(youXuanService.findByIdGoods(pd));
			if(citypd != null){
				pd.put("province_name", citypd.getString("province_name"));
				pd.put("city_name", citypd.getString("city_name"));
				pd.put("area_name", citypd.getString("area_name"));
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("youxuan/fengxiangyxbpd");
		return mv;
	}
	

	/**
	 * 去我的购物车界面
	 */
	@RequestMapping(value="/goMyYouXuanShopCart")
	public ModelAndView goMyYouXuanShopCart(   ){
//		logBefore(logger, "去我的购物车界面");
		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("youxuan/yxshopcart");
		return mv;
	}
	
	

	

	/**
	 * 去我的优选说明界面 type:1-app，2-微信
	 */
	@RequestMapping(value="/goMyYouXuanText")
	public ModelAndView goMyYouXuanText(){
//		logBefore(logger, "去我的优选说明界面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("youxuan/yxbptext");
		return mv;
	}
	
	/**
	 * show_type：1-展示图文，2-展示参数说明 
	 * type:1-app，2-微信,3-安卓显示
	 * youxuangoods_id
	 * 去优选商品图文详情以及参数说明界面   
	 */
	@RequestMapping(value="/goYouXuanDescInfor")
	public ModelAndView goYouXuanDescInfor(){
//		logBefore(logger, "去优选商品图文详情以及参数说明界面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
 			String type=pd.getString("type");
 			//需要获取商品详情
 			PageData goodspd=youXuanService.findByIdGoods(pd);
			if(goodspd != null){
 				//详情图
				String[] big_imagesstr=goodspd.getString("big_images").split(",");
				List<String> biglist=new ArrayList<String>();
				for (int i = 0; i < big_imagesstr.length; i++) {
					biglist.add(big_imagesstr[i]);
				}
				mv.addObject("biglist", biglist);
 				biglist=null;
 				//简介列表
				List<PageData> goodsjjlist=youXuanService.listAllGoodsjj(goodspd);
				mv.addObject("goodsjjlist", goodsjjlist);
 				goodsjjlist=null;
  				//获取当前城市一期的优选
 				PageData dqpd=youXuanService.getnowDq(goodspd);
 				if(dqpd != null){
 					String enddate=dqpd.get("enddate").toString();
 					String opentime=dqpd.get("opentime").toString();
 					String endtime=enddate+" "+opentime;
 					long endtimestamp=DateUtil.fomatDate1(endtime).getTime();
 					pd.put("endtimestamp", endtimestamp+"");
  				}
   				goodspd=null;
			}
			if(type.equals("3")){
				mv.setViewName("youxuan/yxdescinforapp");
			}else{
				mv.setViewName("youxuan/yxdescinfor");
			}
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
 		return mv;
	}
	
	
 
	
	
	/**
	 * 待支付订单界面--结算界面
	 */
	@RequestMapping(value="/goReadyPayOrder")
	public ModelAndView goReadyPayOrder(   ){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
//		mv.setViewName("youxuan/yxreadyPayOrder");
		mv.setViewName("youxuan/yxreadyPayOrder_new");
		return mv;
	}
	
	
	
	
	/**
	 * 确认订单选择支付方式---支付
	 */
	@RequestMapping(value="/surePayMoney")
	public ModelAndView surePayMoney(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("youxuan/yxpaytype");
		return mv;
	}
	

	/**
	 * 支付成功后跳转的页面
	 */
	@RequestMapping(value="/payOkGoJsp")
	public ModelAndView payOkGoJsp(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
  				//根据订单统计使用积分情况
				pd=ServiceHelper.getAppOrderService().sumguanlianById(pd);
				mv.addObject("pd", pd);
				mv.setViewName("youxuan/yxpayok");
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		return mv;
	}
	
	

	/**
	 * 支付成功后的详情页面
	 */
	@RequestMapping(value="/payOkDetailOrder")
	public ModelAndView payOkDetailOrder(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				mv.addObject("pd", pd);
				mv.setViewName("youxuan/yxpayokgodetail");
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		return mv;
	}
	
	
	
	/**
	 * 前往扫一扫优惠买单界面
	 */
	@RequestMapping(value="/sysyhmd")
	public ModelAndView sysyhmd(){
		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
   		try{
			 pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				 pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				 //防止表单重复提交
				 Session session = SecurityUtils.getSubject().getSession();	
				 if(session.getAttribute(Const.SESSION_ORDER) == null ){
						String session_orderid=BaseController.getTimeID();
						session.setAttribute(Const.SESSION_ORDER, session_orderid);
						String sessionid =String.valueOf(session.getId());
						mv.addObject("session_orderid ", sessionid );
	 			}else{
	 					mv.addObject("session_orderid", String.valueOf(session.getAttribute(Const.SESSION_ORDER)));
				}
				//获取个人财富
				 mv.addObject("mpd", ServiceHelper.getAppMemberService().findWealthById(pd));
	 	  		//判断是否开通类别积分购买的权限
		 		if(ServiceHelper.getAppStorepc_marketingService().getJfById(pd) != null){
		 				if( ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("3") ){
		 					mv.addObject("issortjf", "3");
		 				}else if( ServiceHelper.getAppStorepc_marketingService().getJfById(pd).getString("change_type").equals("2") ){
		 					mv.addObject("issortjf", "1");
		 				} else{
		 					mv.addObject("issortjf", "0");
		 				}
		 			}
		 		if(pd.getString("sk_shop") != null && !pd.getString("sk_shop").equals("")){
		 			pd.put("store_id", BaseController.jiemi(pd.getString("sk_shop")));
		 		}else{
		 			pd.put("sk_shop",BaseController.jiami(pd.getString("store_id")));
		 		}
		 		//商家名称
		 		mv.addObject("store_name", ServiceHelper.getAppStoreService().findById(pd).getString("store_name"));
				//获取营销规则
				List<PageData> marketlist=appStorepc_marketingService.listAllById(pd);
	   			mv.addObject("marketlist", marketlist); 
	   			marketlist=null;
// 	   			mv.setViewName("htmlmember/sysyhmd");
 	   			mv.setViewName("htmlmember/sysyhmd_new");
	   			pd.remove("store_id");
	   			pd.remove("member_id");
 			}else{
				mv.setViewName("redirect:toLoginWx.do");
			}
     	} catch(Exception e){
			logger.error(e.toString(), e);
 		}
   		mv.addObject("pd", pd); 
		pd=null;
  		return mv;
 	}
	
	/**
	 * 访问注册页one
	 * @return 
	 * @return 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goRegister")
	public void goRegister(HttpServletRequest request,HttpServletResponse response,String recommended,String recommended_type,String recommended_phone) throws Exception{
		PageData pd=new PageData();
		String code = "";
		try {
			pd=this.getPageData();
 			if(recommended != null && !recommended.equals("")){
				if(recommended_type.equals("1")){
 					recommended=ServiceHelper.getAppPcdService().getrecommendedByStoreId(pd).getString("store_id");
					recommended_phone=ServiceHelper.getAppPcdService().getrecommendedByStoreId(pd).getString("store_name");
				}else if(recommended_type.equals("2")){
					recommended=ServiceHelper.getAppPcdService().getrecommendedByMemberId(pd).getString("member_id");
					recommended_phone=ServiceHelper.getAppPcdService().getrecommendedByMemberId(pd).getString("phone");
				}else{
					 
				}
			}
 			request.getSession().setAttribute("recommended", recommended);
 			request.getSession().setAttribute("recommended_type", recommended_type);
 			request.getSession().setAttribute("recommended_phone", recommended_phone);
 			code = WxpubOAuth.createOauthUrlForCode(WxUtil.APP_ID, WxUtil.HOST+"/html_member/htmlWxLogin.do", true);//简单的注册步骤
 			response.sendRedirect(code); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//新版微信登录=================================================================================
	
	/**
	 * 微信登录授权页面
	 * html_member/toLoginWx.do 
 	 */
	@RequestMapping(value="/toLoginWx")
	public void toLoginWx(HttpServletRequest request,HttpServletResponse response){
		String code = "";
		try {
			code = WxpubOAuth.createOauthUrlForCode(WxUtil.APP_ID, WxUtil.HOST+"/html_member/htmlWxLogin.do", true);
  			response.sendRedirect(code); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * html_member/htmlWxLogin.do?code=
	 *  授权完直接登录
	 *  1：未注册过的先得获取手机验证码
	 *  2：已注册直接前往首页
	 *  
	 *  返回值：用户信息  
	 *  session存储 wxlogin_id   shiro存储 HtmlUser
	 *  
	 */
	@RequestMapping(value="/htmlWxLogin")
	public ModelAndView HtmlWxLogin(HttpServletRequest request)throws Exception{
		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
 		String member_id="";
   		try {
   				pd=this.getPageData();
   				String code=pd.getString("code");
     			pd = WxpubOAuth.getOpenId(pd,WxUtil.APP_ID, WxUtil.APP_SECRET, code);
    			if(pd.getString("wxopen_id") == null || pd.getString("wxopen_id").equals("")){
    				mv.setViewName("redirect:goErrorJsp.do");
   			 		return mv;
   				} 
//   			String wxopen_id ="owD2DwsxdygwHXxNV75kjGT7Wvlw";
   				//获取用户的一些信息
    			pd=WxpubOAuth.getUserInforForNotGuanZhu(pd,pd.getString("wxopen_id"),pd.getString("access_token"));//获取未关注的用户信息
  				pd=WxpubOAuth.getWxInformation(pd,pd.getString("wxopen_id"),appMemberService.getWxAccess(pd).getString("access_token") );//对于已经关注所获取的信息
   				//根据openid判断是否存在用户
  				boolean flag=(appMemberService.getByOpenid(pd) != null || appMemberService.getByUnionid(pd) != null);
   				if( flag  ){
 					if(appMemberService.getByOpenid(pd) != null ){
 						member_id=appMemberService.getByOpenid(pd).getString("member_id");
 					}else{
 						member_id=appMemberService.getByUnionid(pd).getString("member_id");
 					}
 					pd.put("member_id", member_id);
  					pd.put("islogin", "1");
 					appMemberService.editHtmlLogin(pd);
 					HtmlUser huser=new HtmlUser();
  		 			huser.setMember_id(member_id);
  		 			huser.setOpen_id(pd.getString("wxopen_id"));
 					huser.setWxunionid(pd.getString("wxunionid"));
 					SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_H5_USER, huser);
 					this.getRequest().getSession().setAttribute("wxlogin_id",member_id);
 				}else{
					member_id="";
   				}
   				request.getSession().setAttribute("okgetcode","1");
     			if(request.getSession().getAttribute("recommended") != null){
   					pd.put("recommended", (String) request.getSession().getAttribute("recommended"));
   					pd.put("recommended_type", (String) request.getSession().getAttribute("recommended_type"));
   					pd.put("recommended_phone", (String) request.getSession().getAttribute("recommended_phone"));
   					request.getSession().removeAttribute("recommended");
   					request.getSession().removeAttribute("recommended_type");
   					request.getSession().removeAttribute("recommended_phone");
     			}
				mv.addObject("pd", pd);
				mv.addObject("member_id", member_id);
    		} catch (Exception e) {
   				(new TongYong()).dayinerro(e);
 		}
   		mv.setViewName("htmlmember/wxLogin");
 		return mv;
	}
	
	/**
	 * 会员手机号注册获取验证码
	 * html_member/htmlFindCode.do
	 * 
 	 * phone 手机号
 	 */
	@RequestMapping(value="/htmlFindCode") 
	@ResponseBody
	public Object HtmlFindCode(HttpServletRequest request,String phone){
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result="1";
		String message="验证码已发至你手机，请注意查收";
		String code=StringUtil.getSixRandomNum();
 		try{
 			Object okgetcode=request.getSession().getAttribute("okgetcode");
 			if(okgetcode == null || !String.valueOf(okgetcode).equals("1")){
 				map.put("result", "0");
		 		map.put("message","获取失败，请重新加载页面");
		 		map.put("data", "");
		 		return map;
 			}
   			//验证是否可以发送验证码
    		PageData issend=TongYong.Okmessage(phone,"1");
  			if(issend.get("result").equals("0")){
  				map.put("result", "0");
		 		map.put("message",issend.getString("message"));
		 		map.put("data", "");
		 		return map;
  			}
 		    SmsUtil.TongYongCode(phone, code);
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
 		    //将验证码放到session里
 		    SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_MEMBER_ZHUCECODE , code);
   		} catch(Exception e){
   			(new TongYong()).dayinerro(e);
			result="0";
			message="系统错误";
		}
		map.put("message", message);
	    map.put("result", result);
		return map;
	}
	
	
	/**
	 * 使用微信登录
	 * 第三步
	 * html_member/ajaxYanZhengWxLogin.do
	 * 参数如下：
	 * province_name  省
	 * city_name  市
	 * image_url  头像
	 * sex  性别
	 * name 姓名
	 * wxunionid 微信的唯一标示ID
	 * wxopen_id 微信的公众号唯一标示ID
	 * phone 登录获取验证码手机号
	 * code 验证码
	 */
	@RequestMapping(value="/ajaxYanZhengWxLogin")
	@ResponseBody
	public Object AjaxYanZhengWxLogin() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="登录成功";
  		PageData pd = new PageData();
  		try {
  			pd=this.getPageData();
  			String sessionCode=String.valueOf(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_MEMBER_ZHUCECODE));
			if(!sessionCode.equals(pd.getString("code"))){
				map.put("result", "0");
				map.put("message", "验证码错误");
				map.put("data", "");
 		 		return map;
			} 
			try {
	  				//存储app的第三方登录信息
		  			PageData _pd=new PageData();
		  			_pd.put("unionid", pd.getString("wxunionid"));
		  			_pd.put("open_id", pd.getString("wxopen_id"));
		  			_pd.put("phone", pd.getString("phone"));
		  			_pd.put("type", "1");
		  			ServiceHelper.getTYAllSortService().saveThreeLogin(_pd);
		  			_pd=null;
			} catch (Exception e) {
					// TODO: handle exception
	 		}
  			//判断当前手机号是否注册过
  			String member_id="";
  			if(appMemberService.detailByPhone(pd) == null){
 				String password=BaseController.getMiMaNumber();
				pd.put("password", password);
				pd.put("zhuce_shebei","3");
				pd=TongYong.saveMember(pd,pd.getString("recommended"), pd.getString("recommended_type"));//注册
				//发送短信
				SmsUtil.ZhuCeForPassword(pd.getString("phone"), password);
				member_id=pd.getString("member_id");
  			}else{
 				member_id= appMemberService.detailByPhone(pd).getString("member_id");
 			}
 			if(result.equals("1")){
 				//将微信和当前的手机号码合并
 				pd.put("member_id",member_id);
  				pd.put("islogin","1");
 				appMemberService.editHtmlLogin(pd);
 	 			SecurityUtils.getSubject().getSession().removeAttribute(Const.SESSION_MEMBER_ZHUCECODE);
 	 			this.getRequest().getSession().removeAttribute("okgetcode");
 	 			this.getRequest().getSession().setAttribute("wxlogin_id",member_id);
 	 			HtmlUser huser=new HtmlUser();
 	 			huser.setMember_id(member_id);
 	 			huser.setOpen_id(pd.getString("wxopen_id"));
 				huser.setWxunionid(pd.getString("wxunionid"));
 				SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_H5_USER, huser);
 			} 
  		} catch (Exception e) {
  			logger.error("微信登录"+e.toString());
 		}
   		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
 		pd=null;
 		return map;
	}
	
	/**
	 * 手机号码注册会员
	 *  html_member/register.do
	 *  
	 *  phone 注册手机号码
	 *  password 注册设置的密码
	 *  recommended 推荐人ID
	 *  recommended_type 推荐人类型
	 *  
	 */
	@RequestMapping(value="/register")
	@ResponseBody
	public Object register() throws Exception{
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
  			String recommended=pd.getString("recommended");//推荐人ID
 	 		String recommended_type=pd.getString("recommended_type");//推荐人类型:1-商家，2-会员
 	 		pd.put("zhuce_shebei", "3");
     		pd=TongYong.saveMember(pd,recommended, recommended_type);
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
 	 * 
	 * html_member/getTuijianPhone.do? 
	 * 
	 * be_phone  被推荐人的手机号码
	 */
	@RequestMapping(value="/getTuijianPhone")
	@ResponseBody
	public Object getTuijianPhone() throws Exception{
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
	
	
	

	/**
	 * 更新Access_token和jsapi_ticket
	 * html_member/setJiChuAccess_token.do 
 	 */
	@RequestMapping(value="/setJiChuAccess_token")
	public PageData setJiChuAccess_token(){
		PageData pd=new PageData();
		try {
			pd=WxpubOAuth.setJiChuAccess_token();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return pd;
	}
	
	/**
	 * 前往错误页面
	 * @return
	 * @throws Exception
	 * html_member/goErrorJsp.do
	 */
	@RequestMapping(value="/goErrorJsp")
	public ModelAndView goErrorJsp()throws Exception{
		ModelAndView mv = this.getModelAndView();
     	mv.setViewName("htmlerror");
 		return mv;
	}
	
	
	
	/**
	 * 一些登录信息进行记录
	 * @param pd
	 * @return
	 */
	public static PageData loginSomeInfor(PageData pd){
		pd.put("flag", "true");
 		try {
 			Session session = SecurityUtils.getSubject().getSession();
 			Object obj= session.getAttribute(Const.SESSION_H5_USER);
  			String city_name=pd.getString("city_name");
			String area_name=pd.getString("area_name");
			String address=(pd.getString("address")==null?"":pd.getString("address"));
 			String longitude=pd.getString("longitude");
			String latitude=pd.getString("latitude");
			String member_id=pd.getString("member_id");
 			if(obj == null){
 				HtmlUser huser=new HtmlUser();
  				if(member_id == null || member_id.equals("")){
 					pd.put("flag", "false");
 				}else{
 					huser.setMember_id(member_id);
 					huser.setCity_name(city_name);
 					huser.setArea_name(area_name);
 					huser.setAddress(address);
 					huser.setLng(longitude);
 					huser.setLat(latitude);
 					session.setAttribute(Const.SESSION_H5_USER, huser);
 				}
 			}else{
 				HtmlUser huser=(HtmlUser) session.getAttribute(Const.SESSION_H5_USER);//登录者信息
 				member_id=huser.getMember_id();
 				String huserarea_name=huser.getArea_name();
 				String huserlng=huser.getLng();
 				if(area_name != null && !area_name.equals("") ){
 					if(huserarea_name != null){
 						if(area_name.equals(huserarea_name)){
 							//不操作
 						}else{
 							huser.setArea_name(area_name);
 	 						huser.setCity_name(city_name);
 	 						huser.setAddress(address);
 						}
 					}else{
 						huser.setArea_name(area_name);
 						huser.setCity_name(city_name);
 						huser.setAddress(address);
 					}
 				}else{
 					area_name=huserarea_name;
 					city_name=huser.getCity_name();
 					address=huser.getAddress();
 				}
 				if(longitude != null && !longitude.equals("") ){
 					if(huserlng != null){
 						if(huserlng.equals(longitude)){
 							//不操作
 						}else{
 							huser.setLat(latitude);
 							huser.setLng(longitude);
 						}
 					}else{
 						huser.setLat(latitude);
						huser.setLng(longitude);
 					}
 				}else{
 					longitude=huserlng;
 					latitude=huser.getLat();
 				}
  			}
 			pd.put("city_name", city_name);
 			pd.put("area_name", area_name);
 			pd.put("longitude", longitude);
 			pd.put("latitude", latitude);
 			pd.put("member_id", member_id);
 			pd.put("address", address);
  		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pd;
	}
	 
	
	// 开始转微信支付的一些接口操作======================================================================
	
	/**
	 * 公众号微信支付
	 * html_member/wxpayorder.do?total_fee=0.01&attach=1&body=购买商品
	 * 
	 * total_fee  金额
	 * attach     支付类型  1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值商品
	 * body       商品说明
	 * out_trade_no   订单ID
	 */
	public static Map<String, String> WxPayOrder(String _total_fee,String attach,String body,String out_trade_no) throws Exception{
		Map<String, String> returnmap=new HashMap<String, String>();
   		try {
  			PageData pd=new PageData();
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
  			BigDecimal total_fee = new BigDecimal(Double.parseDouble(_total_fee)*100);
  	    	//开始调用微信支付接口
  			WXPayPath dodo = new WXPayPath("3");
 	    	Map<String, String> reqData=new HashMap<String, String>();
 	    	reqData.put("body", body);
	    	reqData.put("attach",attach);
	    	reqData.put("out_trade_no", out_trade_no);
	    	reqData.put("fee_type", "CNY");
	    	reqData.put("total_fee", String.valueOf(total_fee.intValue()));
	    	reqData.put("spbill_create_ip", dodo.getSpbill_create_ip());
	    	reqData.put("notify_url", "http://www.jiuyuvip.com/wxback_chat/notify.do");
	     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	    	reqData.put("trade_type", "JSAPI");
	    	reqData.put("openid", ServiceHelper.getAppMemberService().findMemberThreeById(pd).getString("wxopen_id"));

 	    	Map<String, String> map2=dodo.unifiedOrder(reqData);
 	    	//开始处理结果
  	        if(map2.get("return_code").toString().equals("SUCCESS") && map2.get("result_code").toString().equals("SUCCESS")  ){
 	    	  returnmap.put("payment_type_", attach);
 	    	  returnmap.put("appId_", map2.get("appid").toString());
 	    	  returnmap.put("timestamp_", String.valueOf(((new Date()).getTime())));
 	    	  returnmap.put("nonceStr_", map2.get("nonce_str").toString());
 	    	  returnmap.put("package_","prepay_id="+ map2.get("prepay_id").toString());
 	    	  returnmap.put("signType_", "HMACSHA256");
  	    	  returnmap.put("paySign_", map2.get("sign").toString());
  	    	  returnmap.put("out_trade_no", out_trade_no);
  	    	  returnmap.put("result_code", map2.get("result_code").toString());
  	       }else{
  	    	  returnmap.put("payment_type_", "");
	    	  returnmap.put("appId_", "" );
	    	  returnmap.put("timestamp_", "");
	    	  returnmap.put("nonceStr_", "");
	    	  returnmap.put("package_","");
	    	  returnmap.put("signType_", "");
	    	  returnmap.put("paySign_", "");
	    	  returnmap.put("out_trade_no", "");
	    	  returnmap.put("result_code", "");
   	       }
  	      returnmap.put("return_code", map2.get("return_code").toString());
	      returnmap.put("return_msg", map2.get("return_msg").toString());
   		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
		return returnmap;
	}
	
	
	

   	/**
   	 * 微信支付的订单交易支付接口
   	* 方法名称:：payorder 
   	* 方法描述：订单支付接口
   	* html_member/payorder.do
   	* 
   	 */
	@RequestMapping(value="/payorder")
	@ResponseBody
	public  Object PayOrder(HttpServletRequest request) throws Exception{
 		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
	  	String result="1";
		String message="支付成功";
		PageData pd=new PageData();
		try{
				pd = this.getPageData();
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
 				}else{
 					map.put("result", "0");
 					map.put("message", "请先前往登录");
 					map.put("data", "");
 					return map;
 				}
 				String order_id=BaseController.getTimeID();//支付历史记录
 				//新增订单信息tb_order
				pd.put("order_id", order_id);
				pd.put("look_number", order_id);
 				// 处理订单
				PageData orderpd=TongYong.chuliOrder(pd,appMemberService.findById(pd),appStoreService.findById(pd));
				if(orderpd.getString("result").equals("0")){
					ServiceHelper.getAppPcdService().saveLog(order_id, "订单支付出错","10");
					map.put("result", orderpd.getString("result"));
					map.put("message", orderpd.getString("message"));
				    map.put("data", "");
				    return map;
				}
				pd=(PageData) orderpd.get("data");
// 	 			String wxopen_id=appMemberService.findById(pd).getString("wxopen_id");//微信支付的专用openid
//				String pay_way=pd.getString("pay_way");//nowpay,wx,alipay
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
					data=(Map<String, String>) WxPayOrder(TongYong.df2.format(actual_money), attach, body,order_id);
 				}
				data.put("order_id", order_id);
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
 	*  方法名称:：paychongzhi 
 	*  html_member/paychongzhi.do
 	*  
 	*  in_jiqi 所在机器
 	*  money 充值金额
 	*  pay_way  wx_pub
 	*  
 	 */
	@RequestMapping(value="/paychongzhi")
	@ResponseBody
	public Object paychongzhi(HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
  		String result="1";
		String message="充值确认中";
 		PageData pd=new PageData();
		try{
 			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}else{
				map.put("result", "0");
				map.put("message", "请先前往登录");
				map.put("data", "");
				return map;
			}
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
			waterpd.put("remittance_name",Const.payjiqi[4] );
			waterpd.put("remittance_type","4" );
			waterpd.put("wx_money",  money );
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
			//支付类型  1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值商品
 			data= WxPayOrder(money, "4", "九鱼网-充值余额",waterrecord_id);
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
	 * 优选订单支付:YxPayOrder
	 * 
	 * html_member/yxPayOrder.do
	 * 
	 * goodsinfor : goods_id@goods_number,goods_id@goods_number,
	 * in_jiqi     1-app会员端，2-app商家端，3会员pc端，4-商家pc端，5-微信公众号端，6-总后台
	 * pay_way : nowpay,alipay,wx
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
		String result = "1";
		String message="支付成功";
		PageData pd = new PageData();
		String pay_name="";
		double user_integral=0;
		double user_integral_last=0;
		try{
			pd = this.getPageData();
			//判断是否使用了积分支付
				String memberpay_integral=pd.getString("user_integral");
				if(memberpay_integral != null && !memberpay_integral.equals("")){
					user_integral=Double.parseDouble(memberpay_integral);
					user_integral_last=Double.parseDouble(memberpay_integral);
				}
				//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			if(ServiceHelper.getAppMemberService().findById(pd) == null){
				map.put("result", "0");
				map.put("message","请先前往登录");
 				map.put("data", "");
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
				String pay_way=pd.getString("pay_way");
				String guanlian_id=BaseController.getTimeID();//关联ID
				String beguanlian_id="";
				//开始如下1-根据商家区分商品，2-根据商家的商品的提货期限区分商品，3-根据价格降序排序，4-再生成订单
				String in_jiqi=pd.getString("in_jiqi");//在哪个机器上
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
					ggpd=youXuanService.finddetailgg(ggpd);
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
						ggpd=youXuanService.finddetailgg(ggpd);
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
					ggpd=youXuanService.finddetailgg(ggpd);
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
		            spd=appStoreService.findById(spd);
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
						ggpd=youXuanService.finddetailgg(ggpd);
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
							pay_name="支付宝支付";
						}else if(pay_way.contains("wx")){
							orderpd.put("money_pay_type", "4");
							pay_name="微信支付";
						}else if(pay_way.contains("nowpay")){
							orderpd.put("money_pay_type", "2");
							pay_name="现金支付";
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
				youXuanService.saveYouxuanOrder(orderpd);
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
 				Map<String, String> data=WxPayOrder(TongYong.df2.format(lastpaymoney), "3", "九鱼优选-购买商品",guanlian_id);
 				map.put("data", data);
			}else{
				TongYong.youxuanOkOrder(guanlian_id, "");
				map.put("data", guanlian_id);
			}
		 	} catch(Exception e){
			result="0";
			message="获取异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
			pd=null;
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
