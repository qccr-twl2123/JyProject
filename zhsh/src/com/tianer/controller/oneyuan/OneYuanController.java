package com.tianer.controller.oneyuan;

 import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.tianer.controller.zhihui.payMoney.ChargeExample;
import com.tianer.entity.Page;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.city_marketing.City_marketingService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.business.waterrecord.WaterRecordService;
import com.tianer.service.oneyuan.OneYuanService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
 
/** 
 * 
* 类名称：ZhihuiCity_fileController   
* 类描述：   城市档案
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:45:36
 */
@Controller
@RequestMapping(value="/oneYuan")
public class OneYuanController extends BaseController {
	
	@Resource(name="oneYuanService")
	private OneYuanService oneYuanService;
	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	@Resource(name="city_marketingService")
	private City_marketingService city_marketingService;
	
	
	@Resource(name="waterRecordService")
	private WaterRecordService waterRecordService;
	
 	
	/**
	 * 新增一元购商品
	 * 魏汉文20160621
	 */
	@RequestMapping(value="/saveGoods")
	public ModelAndView saveGoods() throws Exception{
		logBefore(logger, "新增一元购商品");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
 		PageData pd = new PageData();
 		try {
			pd = this.getPageData();
			if(zhlogin != null){
				//获取当前登录人的ID
				String createoprator_id=zhlogin.getLogin_id();
				pd.put("oneyuangoods_id", "one"+BaseController.get10UID());
				pd.put("thistype_id", "only"+BaseController.get6UID());
				pd.put("createoprator_id", createoprator_id);
				oneYuanService.saveGoods(pd);
				mv.setViewName("redirect:datalistPageGoods.do?currentPage=1");
			}else{
				mv.setViewName("redirect:../zhihui_index.do");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
 		return mv;
	}
	
	/**
	 * 修改一元购商品
	 * 魏汉文20160621
	 */
	@RequestMapping(value="/editGoods")
	public ModelAndView editGoods() throws Exception{
		logBefore(logger, "修改一元购商品");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if(zhlogin != null){
				//获取当前登录人的ID
				String createoprator_id=zhlogin.getLogin_id();
				if(oneYuanService.findByIdWithMember(pd) == null){
					pd.put("oneyuangoods_id", "one"+BaseController.get10UID());
					pd.put("createoprator_id", createoprator_id);
	 				oneYuanService.saveGoods(pd);
				}else{
					oneYuanService.editGoods(pd);
				}
	 			mv.setViewName("redirect:datalistPageGoods.do?currentPage="+pd.getString("currentPage"));
			}else{
				mv.setViewName("redirect:../zhihui_index.do");
			}
 		} catch (Exception e) {
			// TODO: handle exception
		}
		return mv;
	}
	
  
	
	/**
	 * 一元商品分页列表
	 */
	@RequestMapping(value="/datalistPageGoods")
	public ModelAndView datalistPageGoods(Page page){
		logBefore(logger, "一元商品分页列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
			}
 			page.setPd(pd);
			List<PageData> goodslist=oneYuanService.datalistPageGoods(page); 
			mv.addObject("goodslist", goodslist);
 			this.getHC(); //调用权限
  			mv.addObject("pd", pd);
  			mv.setViewName("zhihui/yingxiao/yingxiao14");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 * 魏汉文
	 */
	@RequestMapping(value="/goAddGoods")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增City_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
 			mv.addObject("msg", "oneYuan/saveGoods");
 			mv.addObject("type", "add");
			mv.addObject("pd", pd);
			mv.setViewName("zhihui/yingxiao/yingxiao15");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
 
	
	/**
	 * 去修改页面
	 * 魏汉文
	 */
	@RequestMapping(value="/goEditGoods")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改City_file页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try { 
			//获取商品详情
			PageData goodspd=oneYuanService.findByIdWithMember(pd);
			mv.addObject("goodspd", goodspd);
			//获取购买这个商品的所有会员列表
			List<PageData> allMemberBuyGoods=oneYuanService.findAllMemberBuyThisGoods(pd); 
			mv.addObject("allMemberBuyGoods", allMemberBuyGoods);
			mv.addObject("msg", "oneYuan/editGoods");
 			mv.addObject("type", "edit");
			mv.addObject("pd", pd);
			mv.setViewName("zhihui/yingxiao/yingxiao15");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	
	 
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
 
 
	
	
	/* ===============================权限================================== */
	public void getHC(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("41"));
		}
 	}
	/* ===============================权限================================== */
	
	//----------------------------------H5------------------------------------------------------------

	/**
	 * 正在进行的一元商品分页列表 member_id,以及type:1-app,2-h5
	 * http://localhost/zhsh/oneYuan/listAllGoods.do?member_id=jy152602823408869&type=2
	 */
	@RequestMapping(value="/listAllGoods")
	public ModelAndView listAllGoods(   ){
		logBefore(logger, "一元商品分页列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String type=pd.getString("type");//1-app,2-h5
			if(type == null || type.equals("")){
				type="1";
			}
			pd.put("isover", "0");
 			List<PageData> goodslist=oneYuanService.listAll(pd);
			mv.addObject("goodslist", goodslist);
 			this.getHC(); //调用权限
  			mv.addObject("pd", pd);
  			mv.setViewName("oneyuan/alloneyuan");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	 
	
	
	/**
	 * 
	 */
	@RequestMapping(value="/a")
	@ResponseBody
	public Object getSortList(){
		logBefore(logger, "列表城市1/2级分类");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			 
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}
 
	
	/**
   	 * 一元购订单支付
	 */
	@RequestMapping(value="/OneYuanthirdPartyPay")
	@ResponseBody
	public Object OneYuanthirdPartyPay(HttpServletRequest request) throws Exception{
		System.out.println("一元购订单支付");
		DecimalFormat    df   = new DecimalFormat("######0.000"); 
		Map<String, Object> map = new HashMap<String, Object>();
 	  	String result="1";
		String message="操作成功";
		PageData pd=new PageData();
		try{
				pd = this.getPageData();
	 			String pay_way=pd.getString("pay_way");//支付方式:支付宝，微信.....
	 			String ip=Pay_historyController.getIp(request);//当前用户所在IP地址
				String in_jiqi=pd.getString("in_jiqi");//在哪个机器上
				String mylocky_number=pd.getString("mylocky_number");//我的幸运号
				String oneyuangoods_id=pd.getString("oneyuangoods_id");//ID
				String getoneyuan_quantity=pd.getString("getoneyuan_quantity");//数量
				String allpay_money=pd.getString("allpay_money");//总金额
				String member_id=pd.getString("member_id");//会员ID
 				double money=Double.parseDouble(pd.getString("money"));//总金额
				String oneyuanmember_id=BaseController.getTimeID();
				PageData onepd=new PageData();
				onepd.put("oneyuanmember_id", oneyuanmember_id);
				onepd.put("getoneyuan_quantity", getoneyuan_quantity);
				onepd.put("allpay_money", allpay_money);
				onepd.put("oneyuangoods_id", oneyuangoods_id);
				onepd.put("mylocky_number", mylocky_number);
				onepd.put("now_ip", ip);
				onepd.put("member_id", member_id);
				onepd.put("buy_status", "0");
				onepd.put("in_jiqi", in_jiqi);
				oneYuanService.saveMemberBuyGoods(onepd);
 				Charge charge = ChargeExample.getPay(oneyuanmember_id, money*100,ip,pay_way,"15","消费");
				if(charge == null ){
					map.put("result", "0");
					map.put("message", "支付失败，charge出错");
					map.put("data", "");
			    	return map;
				}
 				map.put("data", charge);
				System.out.println("生成订单结束=================over");
 		}catch(Exception e){
			result="0";
			message="系统异常";
			map.put("data", e.toString());
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
    	return map;
	}
		
	
	
	
}
