package com.tianer.controller.htmlpc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.entity.html.HtmlUser;
import com.tianer.service.business.menu_text.Menu_textService;
import com.tianer.service.memberapp.AppBankcardService;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.memberapp.AppStorepc_marketingService;
import com.tianer.service.storeapp.Payapp_historyService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.EbotongSecurity;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;

/** 
 * 
* 类名称：HtmlMemberController   
* 类描述：  h5的页面  我的
* 创建人：邢江涛  
* 创建时间：2016年8月24日 
 */
@Controller("htmlMemberMeController")
@RequestMapping(value="/html_me")
public class HtmlMemberMeController extends BaseController{
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	@Resource(name="appOrderService")
	private AppOrderService orderService;
	@Resource(name="appStorepc_marketingService")
	private AppStorepc_marketingService appStorepc_marketingService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService member_wealthhistoryService;
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService member_redpacketsService;
 
	@Resource(name = "appBankcardService")
	private AppBankcardService bankcardService;
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService appMember_wealthhistoryService;
	@Resource(name="appGoodsService")
	private AppGoodsService appGoodsService;
	@Resource(name="menu_textService")
	private Menu_textService menu_textService;
	
	/**
	 * 前往个人中心
	 * 魏汉文20160620
	 * html_me/goMe.do?type=7
	 */
	@RequestMapping(value="/goMe")
	@ResponseBody
	public ModelAndView goMe() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		//Shiro session
 		Session session = SecurityUtils.getSubject().getSession();
		HtmlUser huser=(HtmlUser) session.getAttribute(Const.SESSION_H5_USER);
		PageData pd = new PageData();
		PageData pg = new PageData();
		try{
			 pd = this.getPageData();
 			 mv.addObject("pd", pd); 
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
	 		String type = pd.getString("type");
	 		pg = appMemberService.findById(pd);
			if(pg != null){
				String phone = pg.getString("phone");
			    String ph = phone.replaceAll("(?<=[\\d]{3})\\d(?=[\\d]{4})", "*");
			    pg.put("ph", ph);
 				if(type == null || type.equals("")){
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/index_my");
				}else if (type.equals("1")){
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/zhxx");
				}else if(type.equals("2")){
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/xgyhm");
				}else if(type.equals("3")){
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/xgmm");
				}else if(type.equals("4")){
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/hbsjh");
				}else if(type.equals("5")){//我的二维码
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/wdtgewm");
				}else if(type.equals("6")){//提货券列表
					mv.addObject("pg", pg);
					List<PageData>  tihuoList=orderService.tihuoList(pd);
					mv.addObject("tihuoList", tihuoList);
					tihuoList=null;
					mv.setViewName("htmlmember/my_thj1");
				}
				else if(type.equals("7")){//订单列表
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/index_order");
				}
				else if(type.equals("79")){//历史订单列表
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/lsdd");
				}
				else if(type.equals("8")){//我的会员卡
					mv.addObject("pg", pg);
					List<PageData>	varList = appMemberService.listAllVipImage(pd);	//列出Member_vipcard列表
		 			mv.addObject("varList", varList);
		 			varList=null;
		 			mv.setViewName("htmlmember/huiyuan");
				}
				else if(type.equals("9")){//前往我要开店界面
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/sqkd1");
				}
				else if(type.equals("10")){//前往退出登录界面
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/sz");
				}
				else if(type.equals("11")){//钱包
					mv.addObject("pg", pg);
					pg.put("wealth_type", "2");
					List<PageData> varList = member_wealthhistoryService.listAll(pg);
					mv.addObject("varList", varList);
					varList=null;
					mv.setViewName("htmlmember/qianbao");
				}
				else if(type.equals("12")){//积分
					mv.addObject("pg", pg);
					pg.put("wealth_type", "1"); 
					List<PageData> varList = member_wealthhistoryService.listAll(pg);
					mv.addObject("varList", varList);
					varList=null;
					mv.setViewName("htmlmember/wdjf");
				}
				else if(type.equals("13")){//红包列表
					mv.addObject("pg", pg);
					mv.setViewName("htmlmember/wdhb");
				}
				else if(type.equals("14")){//红包列表
 					mv.addObject("pg", pg);
 					List<PageData> storeList=new ArrayList<PageData>( );
  					boolean isdingwei=true;
    				if(pd.getString("longitude") == null || pd.getString("longitude").equals("") || pd.getString("longitude").equals("0.000000")  || pd.getString("latitude") == null || pd.getString("latitude").equals("") || pd.getString("latitude").equals("0.000000") ){
  						isdingwei=false;
  					} 
 	 				List<PageData> allstoreList=appStoreService.listCollectionById(pg);//收藏的商家
 	 				PageData e=null;
 	 				int am=allstoreList.size();
 					for (int j = 0; j < am; j++) {
  						    e=allstoreList.get(j);
  							String distance=e.getString("distance");
 							if(!isdingwei){
								e.put("distance", "99+km");
							}else{
								if(Double.parseDouble(distance )-Const.maxjuli > 0 ){
									e.put("distance", Const.maxjuli+"km+");
								}else{
									e.put("distance", distance+"km");
								}
							}
 							//获取营销规则
 							List<PageData> marketlist=appStorepc_marketingService.listAllMarketing(e);
 							e.put("marketlist", marketlist);
 							e.put("new_store_id",BaseController.get4ZMSZ()+EbotongSecurity.ebotongEncrypto(e.getString("store_id")));
	 					}
 	 					mv.addObject("storeList", allstoreList);
 	 					allstoreList=null;
 	 					mv.setViewName("htmlmember/shoucang");
 				}
				pg=null;
 			}else{
 				mv.addObject("pg", new PageData());
				mv.setViewName("htmlmember/index_my");
			}
   		} catch(Exception e){
			logger.error(e.toString(), e);
		}
    	return mv;
	}
	

	
//	/**
//	 * 红包列表
//	 * 刘耀耀	 2016.06.27
//	 * 魏汉文 20160629
//	 */
//	@RequestMapping(value="/listAllRedPackage")
// 	public ModelAndView listAllRedPackage(){
//		logBefore(logger, "红包列表");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/wdhb");
//		return mv;
//	}
//	
//	/**
//	 * 智慧钱包列表
//	 * 刘耀耀
//	 * 2016.06.29
//	 */
//	@RequestMapping(value="/listPurse")
// 	public ModelAndView listPurse(Page page){
//		logBefore(logger, "列表Member_wealthhistory");
//		ModelAndView mv = this.getModelAndView();
// 		PageData pd = new PageData();
//		List<PageData> varList = null;
//		try {
//			pd = this.getPageData();
//			pd = appMemberService.findById(pd);
//			pd.put("wealth_type", "2");//钱包
//			varList = member_wealthhistoryService.listAll(pd);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		mv.addObject("pd", pd);
//		mv.addObject("varList", varList);
//		varList=null;
//		mv.setViewName("htmlmember/qianbao");
//		return mv;
//	}
	
	
//	/**
//	 *去退出登录页面
//	 */
//	@RequestMapping(value="/gologin")
//	public ModelAndView gologin(){
//		logBefore(logger, " go退出登录");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/sz");
//		return mv;
//	}
//	
//	
//	
//	/**
//	 * 前往申请开店页面one
//	 */
//	@RequestMapping(value="/gosqkdone")
//	@ResponseBody
//	public ModelAndView gosqkdone() throws Exception{
//		logBefore(logger, " 前往申请开店页面one");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{ 
//			pd = this.getPageData();
//  		}catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/sqkd1");
//		return mv;
//	}
//	
	
	
//	/**
//	 * 我的vip列表
//	 * 刘耀耀
//	 * 2016.06.27
//	 */
//	@RequestMapping(value="/goMyVip")
// 	public ModelAndView goMyVip(){
//		logBefore(logger, "列表Member_vipcard");
//		ModelAndView mv = this.getModelAndView();
// 		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
// 			List<PageData>	varList = appMemberService.listAllVipImage(pd);	//列出Member_vipcard列表
// 			mv.addObject("varList", varList);
// 			varList=null;
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/huiyuan");
//		return mv;
//	}

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
	
	
//	
//	/**
//	 * 提货卷列表
//	 * 魏汉文20160630
//	 */
//	@RequestMapping(value="/tihuoList")
// 	public ModelAndView tihuoList(){
//		logBefore(logger, "提货卷列表");
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			List<PageData>  tihuoList=orderService.tihuoList(pd);
//			mv.addObject("tihuoList", tihuoList);
//			mv.addObject("pd", pd);
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
// 		mv.setViewName("htmlmember/my_thj1");
//		return mv;
//	}
	
	
//	
//	/**
//	 * 去我的推荐二维码页面
//	 * 2016.06.27
//	 */
//	@RequestMapping(value="/goMyTuiJianErWeiMa")
// 	public ModelAndView goMyTuiJianErWeiMa(){
//		logBefore(logger, "去我的推荐二维码页面");
//		ModelAndView mv = this.getModelAndView();
// 		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//  		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/wdtgewm");
//		return mv;
//	}
//	

//	/**
//	 *  
//	 */
//	@RequestMapping(value="/goOneYuan")
// 	public ModelAndView goOneYuan(){
//		logBefore(logger, "");
//		ModelAndView mv = this.getModelAndView();
// 		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//  		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd", pd);
//		mv.setViewName("htmlmember/wdtgewm");
//		return mv;
//	}
	
	
	/**
	* 修改用户名
	 */
	@RequestMapping(value="/updName")
	@ResponseBody
	public Object updName(){
 		String result = "1";
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			appMemberService.edit(pd);
 		} catch(Exception e){
 			map.put("result", "0");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return map;
	}
 
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="/updPass")
	@ResponseBody
	public Object updPass(){
 		String result = "01";
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			appMemberService.edit(pd);
 		} catch(Exception e){
 			map.put("result", "00");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="/goLand")
	public Object goLand(){
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
		mv.setViewName("htmlmember/Land");
		return mv;
	}
	
	/**
	 * 获取验证码
	 */
	@RequestMapping(value="/code")
	@ResponseBody
	public String code(){
  		PageData pd = new PageData();
		String code=StringUtil.getSixRandomNum();
		try{ 
			pd = this.getPageData();
			String phone = pd.getString("phone");
			String type = pd.getString("type");
				if(type != null && !type.equals("")){
					if(type.equals("1")){
					SmsUtil.inforEdit(phone, code);//换绑手机号码
				}else if(type.equals("2")){
					SmsUtil.ownForCode(phone, code);//银行预留手机号
				}
			}
			
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return code;
	}
	
	
	/**
	 * 修改手机号
	 */
	@RequestMapping(value="/updPhone")
	@ResponseBody
	public Object updPhone() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//判断该手机号是否注册过
			PageData _pd=appMemberService.detailByPhone(pd);
			if(_pd != null ){
				map.put("result", "00");
				return map;
			}
			String member_id=pd.getString("member_id");
			String phone=pd.getString("phone");
 			if(member_id!=null && phone!=null){
				appMemberService.edit(pd);
				//在换绑手机号码成功后给新手机号码发送一条成功提示短信
				
			}else{
				result="02";
			}
 		} catch (Exception e) {
			 result = "02";
			e.printStackTrace();
		}
		map.put("result", result);
		return map;
	}
	
	

	
	/**
	 * 提货卷详情
	 * 魏汉文20160630
	 */
	@RequestMapping(value="/tihuoByOrderId")
 	public ModelAndView tihuoByOrderId(){
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			pd=orderService.tihuoByOrderId(pd);
    		//获取商品信息 以及商家信息
			pd=TongYong.getGoodsListByOrder(pd);
			//更改提货卷显示格式
 			if(pd.getString("tihuo_id") != null && pd.getString("tihuo_id").length() == 10){
				pd.put("tihuo_id", pd.getString("tihuo_id").substring(0, 2)+"-"+pd.getString("tihuo_id").substring(2,6 )+"-"+pd.getString("tihuo_id").substring(6, 10));
			}
  	 		pd.put("daoLiuStoreList", TongYong.daoliuList(pd));
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/my_thj");
		return mv;
	}
	

	
	
	/**
	 * 个人收货地址列表
	 * 魏汉文
	 * 2016.06.24
	 */
	@RequestMapping(value="/goaddress")
 	public ModelAndView goaddress() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
				pd = this.getPageData();
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				}
				List<PageData> addressList=appMemberService.listAllAddressById(pd);
				mv.addObject("addressList", addressList);
				addressList=null;
 		} catch (Exception e) {
			 e.printStackTrace();
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/shdz");
		return mv;
	}
	
	
	/**
	 * 新增收货地址页面
	 */
	@RequestMapping(value="/addaddress")
	public ModelAndView addaddress(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("type", "add");
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
 		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/xzdz");
		return mv;
	}
	
	
	/**
	 * 修改收货地址页面
	 */
	@RequestMapping(value="/editaddress")
	public ModelAndView editaddress(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try {
 			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			pd.put("type", "edit");
			PageData address=appMemberService.findAddressById(pd);
			mv.addObject("address", address);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/xzdz");
		return mv;
	}
	
	/**
	 * 新增个人收货地址
	 * 魏汉文
	 * 2016.06.24
	 */
	@RequestMapping(value="/addAddressById")
	@ResponseBody
	public Object ModelAndView() throws Exception{
 		String result = "01";
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			pd.put("member_address_id", BaseController.getTimeID());
			appMemberService.addAddressById(pd);
 		} catch (Exception e) {
 			 result = "00";
			 e.printStackTrace();
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 退出登陆
	 * 魏汉文20160621
	 */
	@RequestMapping(value="/outLogin")
 	public ModelAndView outLogin(Page page){
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			pd.put("islogin","0");
			pd.put("openid","0");
			appMemberService.edit(pd);
			//移除session
			this.getRequest().getSession().removeAttribute("openid");
 			//Shiro session
 			SecurityUtils.getSubject().getSession().removeAttribute(Const.SESSION_H5_USER);
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("redirect:../html_member/toLogin.do");
		return mv;
	}
	

	
	/**
	 * 收藏列表 
	 *魏汉文20160629
	 */
	@RequestMapping(value="/listCollectionById")
 	public ModelAndView listCollectionById() {
 		ModelAndView mv = this.getModelAndView();
  		List<PageData> storeList=new ArrayList<PageData>( );
		PageData pd = new PageData();
		try {
				pd=this.getPageData();
 				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				}
				pd=appMemberService.findById(pd);
				if(pd == null){
					mv.setViewName("redirect:../html_member/toLogin.do");
 				}else{
 					mv.setViewName("htmlmember/shoucang");
 				}
  		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
		mv.addObject("storeList", storeList);
		mv.addObject("pd", pd);
 		return mv;
	}
	
	/**
	 * 取消收藏:store_id,collection
	 *魏汉文20160629
	 */
	@RequestMapping(value="/deleteCollocation")
	@ResponseBody
	public Object deleteCollocation() {
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="取消收藏成功";
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			String[] collectstr=pd.getString("collectstr").split(",");
			 PageData scpd=null;
			for (int i = 0; i < collectstr.length; i++) {
				  scpd=new PageData();
 				  scpd.put("collect_id", collectstr[i].split("@")[0]);
  				  appStoreService.deleteCollect(scpd);
	  			  //商家ID解密
   				  scpd.put("store_id", EbotongSecurity.ebotongDecrypto( collectstr[i].split("@")[1].substring(4, collectstr[i].split("@")[1].length()-1)));
				  appStoreService.editCollectNumber(pd);
 			}
			scpd=null;
  		} catch (Exception e) {
			result="0";
 			message="取消收藏失败";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	
 
	
	/**
	 * 过期红包列表
	 * 刘耀耀	
	 * 2016.06.27
	 */
	@RequestMapping(value="/listOver")
 	public ModelAndView listOver(){
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
		mv.setViewName("htmlmember/hb_gq");
		return mv;
	}
	
	
	
	/**
	 * 去提现界面
	 */
	@RequestMapping(value="/gotixian")
	public ModelAndView gotixian(){
 		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			pd = appMemberService.findById(pd);
			List<PageData> varList = bankcardService.listAll(pd);
			//获取银行卡号，截取后4位，放进list做前台显示
			for (PageData pageData : varList) {
				String bank_number = pageData.getString("bank_number");
				pageData.put("number", bank_number);
				bank_number = bank_number.substring(bank_number.length()-4, bank_number.length());
				//卡号以kh取值
				pageData.put("kh", bank_number);
 			}
			mv.addObject("varList", varList);
			varList=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/tixian");
		return mv;
	}
 
	
 
	
	@Resource(name="appOrderService")
	private AppOrderService appOrderService;
  	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService appMember_redpacketsService;
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService appStore_redpacketsService;
	
	@Resource(name="payapp_historyService")
	private Payapp_historyService historyService;
	
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
 
	
	/**
	 * 用户新增提现记录魏汉文20160704
	 */
	@RequestMapping(value="/saveWithdraw")
	@ResponseBody
	public Object saveWithdraw() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		String result = "01";
		PageData pd = new PageData();
 		try{
	 			pd = this.getPageData();
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				}
 				PageData e=new PageData();
				double money=Double.parseDouble(pd.getString("money"));
				if(pd.getString("user_type").equals("1")){//商家
					
				}else if(pd.getString("user_type").equals("2")){//会员
						e.put("member_id", pd.getString("user_id"));
						e=appMemberService.findById(e);
						double now_money=Double.parseDouble(e.getString("now_money"));
 						if(now_money < money){
							result="02";
						}else{
							//减少用户积分余额
							double n=now_money-money;
							e.put("now_money", df.format(n));
							appMemberService.edit(e);
  							//	个人余额消费历史
 							String withdraw_approval_id=BaseController.getTXUID(e.getString("show_lookid"));
			   				now_money=now_money-n;
							PageData moneypd=new PageData();
							moneypd.put("member_id", e.getString("member_id"));
		 					moneypd.put("wealth_type", "2");
							moneypd.put("consume_type", "2");
 							moneypd.put("content", "提现");
							moneypd.put("number", df.format(money));
							moneypd.put("now_money", df.format(n));
							moneypd.put("jiaoyi_id", withdraw_approval_id);
							moneypd.put("member_wealthhistory_id", BaseController.getXFUID(e.getString("show_lookid")));
							moneypd.put("jiaoyi_status", "0");
							appMember_wealthhistoryService.saveWealthhistory(moneypd);
							//新增总后台提现充值/记录
	 			   			PageData waterpd=new PageData();
		    				waterpd.put("pay_status","0");
		    	   			waterpd.put("waterrecord_id",withdraw_approval_id);
		   					waterpd.put("user_id", pd.getString("user_id"));
		   					waterpd.put("user_type", "2");
		    				waterpd.put("withdraw_rate","0");
		   					waterpd.put("money_type","5");
		   	 				waterpd.put("money", "-"+df.format(money));
		   	 				waterpd.put("reduce_money","0");
		   					waterpd.put("arrivalmoney",  "-"+df.format(money));
		   					waterpd.put("nowuser_money",df.format(now_money) );
		   					waterpd.put("application_channel", "1" );
		    				waterpd.put("remittance_name",pd.getString("account_name") + "-" + pd.getString("withdraw_username") );
		   					waterpd.put("remittance_type","3" );
		   					if(pd.getString("account_type").equals("3")){//支付宝
		   						waterpd.put("alipay_money",  "-"+df.format(money) );
		   					}else{//银联
		   						waterpd.put("bank_money", "-"+df.format(money) );
		   					}
		   					waterpd.put("remittance_number",pd.getString("withdraw_number"));//支付人的支付账号
		    				waterpd.put("createtime",DateUtil.getTime());
		   					waterpd.put("over_time",DateUtil.getTime());
		   	  				waterpd.put("jiaoyi_type","0");
		   					waterpd.put("payee_number",Const.jiuyunumber);
 		    	 			waterpd.put("order_tn", withdraw_approval_id);
		   					waterpd.put("province_name", e.getString("province_name"));
		   					waterpd.put("city_name", e.getString("city_name"));
		   					waterpd.put("area_name", e.getString("area_name"));
		    				ServiceHelper.getWaterRecordService().saveWaterRecord(waterpd);
		    				waterpd=null;
		    				pd=null;
		    				e=null;
						}
				}
 		}catch(Exception e){
				result="03";
				map.put("data", e.toString());
				logger.error(e.toString(), e);
		}
		
		map.put("result", result);
		return map;
	}
	
	/**
	 * go添加银行卡
	 */
	@RequestMapping(value="/gobank")
	public ModelAndView gobank(){
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
 		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/xzkh");
		return mv;
	}
	
	/**
	 * 新增银行卡
	 * 刘耀耀
	 * 2016.07.11
	 */
	@RequestMapping(value="/savebank")
	@ResponseBody
	public Object savebank() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			List<PageData> varList = bankcardService.listAll(pd);
			for(PageData  e : varList){
				if(e.getString("bank_number").equals(pd.getString("bank_number"))){
						map.put("result", "00");
						map.put("message", " 当前银行卡已存在");
						map.put("data", "");
				 		return map;
				}
			}
			pd.put("member_bankcard_id", BaseController.getTimeID());
			bankcardService.save(pd);			
			     //魅力值：0-50一星会员 50-99 二星会员 100-199 三星会员 200-499  四星会员 500-999 五星会员 1000-2000 一钻会员
			    PageData mpd=appMemberService.findById(pd);
			    if(mpd != null){
			    	double charm_number=Double.parseDouble(mpd.getString("charm_number"))+Double.parseDouble(Const.charm_number[8]);
					PageData chpd=new PageData();
					chpd.put("member_id", mpd.getString("member_id"));
					chpd.put("charm_number", charm_number);
					if(charm_number >=0 && charm_number < 50){
						chpd.put("vip_level", "1");
					}else if(charm_number >=50 && charm_number < 100){
						chpd.put("vip_level", "2");
					}else if(charm_number >=100 && charm_number < 200){
						chpd.put("vip_level", "3");
					}else if(charm_number >=200 && charm_number < 500){
						chpd.put("vip_level", "4");
					}else if(charm_number >=500 && charm_number < 1000){
						chpd.put("vip_level", "5");
					}else if(charm_number >=1000 && charm_number < 2000){
						chpd.put("vip_level", "6");
					}
					appMemberService.edit(chpd);
			    }
 		} catch (Exception e) {
			 result = "02";
			e.printStackTrace();
		}
		map.put("result", result);
 		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * type"各种文本说明：
	 *  1.余额说明
		 2.星级说明
		 3.提现说明
		 4.购买说明
		 5.魅力值说明
		 6.红包说明
		 7.优惠说明
	 * 魏汉文20160623
	 */
	@RequestMapping(value="/textDesc")
	@ResponseBody
	public ModelAndView textDesc() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
				}
				pd=menu_textService.findByType(pd);
 		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/kb");
		return mv;
	}

	
	
	
	/**
	 * 填写门店信息two
	 */
	@RequestMapping(value="/gosqkdtwo")
	@ResponseBody
	public ModelAndView gosqkdtwo() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
			}
			List<PageData> provincelist=ServiceHelper.getPcdService().listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
  		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("htmlmember/sqkd2");
		return mv;
	}
	
	
	
	
}
