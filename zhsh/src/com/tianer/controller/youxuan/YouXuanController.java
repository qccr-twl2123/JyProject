package com.tianer.controller.youxuan;

 import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

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
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.controller.zhihui.payMoney.ChargeExample;
import com.tianer.entity.Page;
import com.tianer.entity.html.HtmlUser;
import com.tianer.entity.zhihui.OrderShop;
import com.tianer.entity.zhihui.StoreRole;
import com.tianer.entity.zhihui.TihuoTask;
import com.tianer.entity.zhihui.YouXuanTask;
import com.tianer.entity.zhihui.zhihuiLogin;
import com.tianer.service.business.city_file.City_fileService;
import com.tianer.service.business.city_marketing.City_marketingService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_wealthhistoryService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.youxuan.YouXuanService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.StringUtil;
 
/** 
 * 
* 类名称：YouXuanController   
* 类描述：   城市档案
* 创建人：魏汉文  
* 创建时间：2016年5月25日 下午4:45:36
 */
@Controller
@RequestMapping(value="/youxuan")
public class YouXuanController extends BaseController {
	
	@Resource(name="youXuanService")
	private YouXuanService youXuanService;
	
	@Resource(name="city_fileService")
	private City_fileService city_fileService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	@Resource(name="city_marketingService")
	private City_marketingService city_marketingService;
	
	
	@Resource(name="appOrderService")
	private AppOrderService appOrderService;
	@Resource(name="appMember_wealthhistoryService")
	private AppMember_wealthhistoryService appMember_wealthhistoryService;
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
 	public DecimalFormat df2=TongYong.df2;
	
 	
	/**
	 * 优选参数管理
	 * 魏汉文20160613
	 */
	@RequestMapping(value="/youxuancsgl")
	public ModelAndView youxuancsgl(Page page){
		//logBefore(logger, "优选参数管理");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
			}
			pd.put("open_status", "1");
			page.setPd(pd);
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
			List<PageData>	varList = city_fileService.list(page);	//列出City_marketing列表
			this.getHC46(); //调用权限
			mv.setViewName("zhihui/yingxiao/yingxiao18");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去优选参数修改页面
	 * 魏汉文
	 */
	@RequestMapping(value="/gocsglEdit")
	public ModelAndView gocsglEdit(){
		//logBefore(logger, "去优选参数修改页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
 		try { 
			pd = this.getPageData();
			String currentPage=pd.getString("currentPage");
			//获取当前城市档案的详情
			pd=ServiceHelper.getCity_fileService().findById(pd);
			pd.put("currentPage", currentPage);
			//获取当前城市档案的所有优选参数
 			List<PageData> listdangqi=youXuanService.listAllDangqi(pd);
 			String dangqistr="";
 			for(PageData e : listdangqi){
 				dangqistr+=e.getString("youxuandq_id")+",";
 			}
 			mv.addObject("dangqistr", dangqistr);
			mv.addObject("listdangqi", listdangqi);
 			mv.setViewName("zhihui/yingxiao/yingxiao19");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		mv.addObject("pd", pd);
		pd=null;
		return mv;
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping(value="/detailyouxuandq")
	@ResponseBody
	public Object detailyouxuandq(){
		//logBefore(logger, "查看详情");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		String result="1";
		try{
			pd = this.getPageData();
			pd=youXuanService.finddetailDangqi(pd);
		} catch(Exception e){
			result="0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("data", pd);
		return map;
	}
	/**
	 * 保存修改的优选档期参数
	 */
	@RequestMapping(value="/saveyouxuandq")
	@ResponseBody
	public Object saveyouxuandq(){
		//logBefore(logger, "保存修改的优选档期参数");
		Map<String,Object> map = new HashMap<String,Object>();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		String result="1";
		try{
			pd = this.getPageData();
 			String youxuandq_id="";
  			String enddate=pd.getString("enddate");
			String opentime=pd.getString("opentime");
			String city_file_id=pd.getString("city_file_id");
 			PageData isfalsepd=youXuanService.finddetailDangqi(pd);//需要档期ID以及城市ID
			String startdate=pd.getString("startdate");
  			if(isfalsepd == null){
  				if(startdate.equals(DateUtil.getDay())){
					pd.put("isover", "1");
				}else{
					pd.put("isover", "0");
				}
   				pd.put("createoprator_id", zhlogin.getLogin_id());
				youXuanService.saveDangQi(pd);
 			}else{
				youxuandq_id=pd.getString("youxuandq_id");
				pd.put("editoprator_id", zhlogin.getLogin_id());
				youXuanService.editDangqi(pd);
			}
 			//设置定时任务
 			long n1=(new Date()).getTime();
 			String kqtime=enddate+" "+opentime;
 			long n2=DateUtil.fomatDate1(kqtime).getTime();
 			YouXuanTask yp=new YouXuanTask(youxuandq_id,city_file_id);
			Timer tt=new Timer();
			if(n2-n1 > 0){
				tt.schedule(yp, n2-n1);
			}
   		} catch(Exception e){
 			result="0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return map;
	}
	
	
	/**
	 * 保存修改的优选费用参数
	 */
	@RequestMapping(value="/edityouxuanfee")
	@ResponseBody
	public Object edityouxuanfee(){
		//logBefore(logger, "保存修改的优选费用参数");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		String result="1";
		try{
			pd = this.getPageData();
  			ServiceHelper.getCity_fileService().edit(pd);
 		} catch(Exception e){
 			result="0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return map;
	}
	
	
	/**
	 * 获取优选
	 */
	@RequestMapping(value="/getDangqiByPcd")
	@ResponseBody
	public Object getDangqiByPcd(){
		//logBefore(logger, "获取优选");
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		String result="1";
		try{
			pd = this.getPageData();
			pd=ServiceHelper.getCity_fileService().findById(pd);
			map.put("pd", pd);
			List<PageData> listAllDangqi=youXuanService.listAllDangqi(pd);
			map.put("listAllDangqi", listAllDangqi);
			listAllDangqi=null;
 			pd=null;
 		} catch(Exception e){
			result="0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return map;
	}
	
	 
	//设置优选商品
  	
	/**
	 * 修改优选商品
	 * 魏汉文20160621
	 */
	@RequestMapping(value="/editGoods")
	@ResponseBody
	public Object editGoods() throws Exception{
		//logBefore(logger, "修改一元购商品");
 		Map<String,Object> map = new HashMap<String,Object>();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
		PageData pd = new PageData();
		String result="1";
		String url="zhihui_index.do";
		try {
			pd = this.getPageData();
			String small_images=pd.getString("small_images");
			if(small_images.contains(",") && small_images.split(",").length >0){
				pd.put("image_url", small_images.split(",")[0]);
			}
			String currentPage=pd.getString("currentPage");
			if(zhlogin != null){
				//获取当前登录人的ID
				String zhihuioprator_id=zhlogin.getLogin_id();
				String youxuangoods_id=pd.getString("youxuangoods_id");
				pd.put("zhihuioprator_id", zhihuioprator_id);
				//处理规格
				if(pd.getString("ggliststr").contains(",") && pd.getString("ggliststr").contains("!")){
					String[] ggliststr=pd.getString("ggliststr").split(",");
					for (int i = 0; i < ggliststr.length; i++) {
						String ss=ggliststr[i];
						String[] gg1=ss.split("!");
						String[] gg2=gg1[1].split("@");
						String youxuangg_id=gg1[0];
						PageData e=new PageData();
						e.put("youxuangg_id", youxuangg_id);
 						e.put("gg_imageurl", gg2[0]);
						e.put("youxuangoods_id", youxuangoods_id);
						e.put("gg_miaosu", gg2[1]);
						e.put("sale_money", gg2[2]);
						e.put("gudingsale_number", gg2[3]);
						e.put("needsale_number", gg2[4]);
						if(youXuanService.finddetailgg(e) != null){
							youXuanService.editGoodsgg(e);
						}else{
							youXuanService.saveGoodsgg(e);
						}
					}
				}
    			//处理简介
				if(pd.getString("jjliststr").contains(",") && pd.getString("jjliststr").contains("!")){
					String[] jjliststr=pd.getString("jjliststr").split(",");
					for (int i = 0; i < jjliststr.length; i++) {
						String[] jj1=jjliststr[i].split("!");
						String[] jj2=jj1[1].split("@");
						String youxuanjj_id=jj1[0];
						PageData e=new PageData();
						e.put("youxuanjj_id", youxuanjj_id);
						e.put("youxuangoods_id", youxuangoods_id);
						e.put("title", jj2[0]);
						e.put("text", jj2[1]);
	 					if(youXuanService.finddetailjj(e) != null){
							youXuanService.editGoodsjj(e);
						}else{
							youXuanService.saveGoodsjj(e);
						}
					}
				}
  				youXuanService.editGoods(pd);
//	 			mv.setViewName("redirect:datalistPageGoods.do?goods_check=2&currentPage="+currentPage);
			} else{
				result="0";
			}
 		} catch (Exception e) {
   			logger.error(e.toString(), e);
		}
		pd=null;
		map.put("result", result);
		map.put("url", url);
		return map;
	}
  	
	/**
	 * 优选商品分页列表
	 */
	@RequestMapping(value="/datalistPageGoods")
	public ModelAndView datalistPageGoods(Page page){
		//logBefore(logger, "优选商品分页列表");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
 		PageData pd = new PageData();
		try{
			//获取所有开通城市的档案的城市
			List<PageData> provincelist=ServiceHelper.getCity_fileService().listAllCityOpen(pd);
			mv.addObject("provincelist", provincelist);
			provincelist=null;
			pd = this.getPageData();
			PageData iscitypd=new PageData();
			if(zhlogin != null && zhlogin.getMenu_role_id() != null){
				//登陆用户的角色id:0-管理员，1-服务商，2-业务员，3-操作员，4-其他
 				if( zhlogin.getLogin_type().equals("1") || zhlogin.getLogin_type().equals("2")){
 					iscitypd.put("city_name", zhlogin.getCity_name().trim());
 					iscitypd.put("area_name", zhlogin.getArea_name().trim());
 					iscitypd=ServiceHelper.getCity_fileService().findById(iscitypd);
 					if(iscitypd == null){
 						pd.put("city_file_id", "");
 					}else{
 						pd.put("city_file_id", iscitypd.getString("city_file_id"));
 					}
   				} 
				mv.addObject("login_type", zhlogin.getLogin_type());
				PageData cpd=ServiceHelper.getCity_fileService().findById(pd);
				if(cpd != null){
					pd.put("city_file_id", cpd.getString("city_file_id"));
				}
 				mv.addObject("cpd", cpd);
				//档期
 				List<PageData> listAllDangqi=youXuanService.listAllDangqi(pd);
				mv.addObject("listAllDangqi", listAllDangqi);
				String goods_check = pd.getString("goods_check"); 
				if(goods_check == null){
					goods_check="0";
				}
	 			String currentPage = pd.getString("currentPage");
				if(null != currentPage && !"".equals(currentPage)){
					page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
				}
 	  			page.setPd(pd);
				List<PageData> goodslist=youXuanService.datalistPageGoods(page); 
				for(PageData e : goodslist){
					PageData citypd=new PageData();
					citypd=ServiceHelper.getCity_fileService().findById(e);
					if(citypd != null){
						e.put("city_address", citypd.getString("province_name")+"/"+citypd.getString("city_name")+"/"+citypd.getString("area_name"));
					}
					citypd=null;
					PageData dqpd=new PageData();
					dqpd=youXuanService.finddetailDangqi(e);
					if(dqpd != null){
						e.put("youxuandq_name", "第"+dqpd.getString("youxuandq_id")+"期"+dqpd.getString("startdate")+"-"+dqpd.getString("enddate"));
					}
					dqpd=null;
				}
				mv.addObject("goodslist", goodslist);
				goodslist=null;
 	   			//1-商家编辑/等待审核，2-审核通过/等待支付上线钱，3-支付完成等待上线，
				if(goods_check.equals("0")){
					this.getHC43(); //优选商品上线审核 ：状态：0和97驳回
					mv.setViewName("zhihui/yingxiao/yingxiao20");
				}else if(goods_check.equals("1")){
					this.getHC44(); //优选商品审核通过待支付 状态 ： 1,2
					mv.setViewName("zhihui/yingxiao/yingxiao21");
				}else if(goods_check.equals("2")){
					this.getHC45(); //优选商品审核通过/驳回  状态 ：4
					mv.setViewName("zhihui/yingxiao/yingxiao22");
				}
   			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		pd=null;
		return mv;
	}
	

	/**
	 * 改变商品状态
	 */
	@RequestMapping(value="/changestatus")
	public void changestatus(PrintWriter out){
		//logBefore(logger, "改变商品状态");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String goods_status=pd.getString("goods_status");
			if(goods_status.equals("2")){
				//获取当前城市一期的优选
				PageData dqpd=youXuanService.finddetailDangqi(youXuanService.findByIdGoods(pd));
				if(dqpd.getString("isover").equals("0")){
					pd.put("goods_status", "2");
				}else if(dqpd.getString("isover").equals("1")){
					pd.put("goods_status", "4");
				}else if(dqpd.getString("isover").equals("99")){
					pd.put("goods_status", "99");
				}
			}
			youXuanService.editGoods(pd);
 			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	

	/**
	 * 删除简介
	 */
	@RequestMapping(value="/deletejj")
	public void deletejj(PrintWriter out){
		//logBefore(logger, "删除简介");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			youXuanService.deletejj(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	
	
	/**
	 * 删除规格
	 */
	@RequestMapping(value="/deletegg")
	public void deletegg(PrintWriter out){
		//logBefore(logger, "删除规格");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			youXuanService.deletegg(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
 	
	/**
	 * 去修改页面
	 * 魏汉文
	 */
	@RequestMapping(value="/goEditGoods")
	public ModelAndView goEditGoods(){
		//logBefore(logger, "去修改页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try { 
			//获取商品详情
			PageData goodspd=youXuanService.findByIdGoods(pd);
			String[] small_imagesstr=goodspd.getString("small_images").split(",");
 			List<String> smalllist=new ArrayList<String>();
			for(int i=0;i<5;i++){
				if(small_imagesstr.length-1 >= i){
					smalllist.add(small_imagesstr[i]);
				}else{
					smalllist.add("img/imgadd.png");
				}
			}
			mv.addObject("smalllist", smalllist);
			String[] big_imagesstr=goodspd.getString("big_images").split(",");
			List<String> biglist=new ArrayList<String>();
			for (int i = 0; i < big_imagesstr.length; i++) {
				biglist.add(big_imagesstr[i]);
			}
			mv.addObject("biglist", biglist);
			//档期
			PageData dqpd=new PageData();
			dqpd=youXuanService.finddetailDangqi(goodspd);
			if(dqpd != null){
				goodspd.put("youxuandq_name", "第"+dqpd.getString("youxuandq_id")+"期："+dqpd.getString("startdate")+"/"+dqpd.getString("enddate"));
			}
			dqpd=null;
			mv.addObject("goodspd", goodspd);
 			goodspd=null;
 			//获取规格
			List<PageData> goodsgglist=youXuanService.listAllGoodsgg(pd); 
			mv.addObject("goodsgglist", goodsgglist);
			goodsgglist=null;
			//获取简介
			List<PageData> goodsjjlist=youXuanService.listAllGoodsjj(pd);
			mv.addObject("goodsjjlist", goodsjjlist);
			goodsjjlist=null;
			
 			mv.addObject("msg", "youxuan/editGoods");
 			mv.addObject("type", "edit");
 			mv.setViewName("zhihui/yingxiao/yingxiao23");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		mv.addObject("pd", pd);
		pd=null;
		return mv;
	}	
	
	

	/**
	 * 前往发送推送页面
	 */
	@RequestMapping(value="/gosendyouxuan")
	public ModelAndView gosendyouxuan(){
		//logBefore(logger, "优选推送");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			PageData cpd=youXuanService.findByIdGoods(pd);
			mv.addObject("cpd",cpd);
   			mv.addObject("pd",pd);
			mv.setViewName("zhihui/yingxiao/youxuantuisong");
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	/**
	 * 确认推送
	 */
	@RequestMapping(value="/sure_tuisong")
	public ModelAndView sure_tuisong() throws Exception{
		//logBefore(logger, " 确认推送");
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		zhihuiLogin zhlogin = (zhihuiLogin)session.getAttribute(Const.SESSION_USER);//获得登陆用户信息
 		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 			String title="爆品";
			String content=pd.getString("content");
			String youxuangoods_id=pd.getString("youxuangoods_id");
			String city_name=pd.getString("city_name");
			String area_name=pd.getString("area_name");
			String currentPage=pd.getString("currentPage");
			if(area_name != null && area_name != ""){
				//获取这个区域的所有会员
				List<PageData> memberList=ServiceHelper.getAppMemberService().listAllMemberByCity(pd);
				for (PageData e : memberList) {
					TongYong.sendTuiSong(e.getString("member_id"), youxuangoods_id, "1",e.getString("member_id"), "2", content, ""); 
 				}
 			}
			youXuanService.editGoods(pd);
			//添加一条记录
			pd.put("tuisong_type", "1");
			pd.put("oprator_id", zhlogin.getLogin_id());
			ServiceHelper.getSend_notificationsService().saveTuisong(pd);
 	 		mv.setViewName("redirect:datalistPageGoods.do?goods_check=2&currentPage="+currentPage);
  		} catch (Exception e) {
			// TODO: handle exception
 			//System.out.println(e.toString());
  			logger.error(e.toString(), e);
		}
		pd=null;
		return mv;
	}
	 
	
	
	/**
	 * 优选商品销售明细
	 */
	@RequestMapping(value="/datalistPageGoodsSaleInFor")
	public ModelAndView datalistPageGoodsSaleInFor(Page page){
		//logBefore(logger, " 优选商品销售明细");
		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{
 			pd = this.getPageData();
  			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
			}
			//获取city_file_id
			PageData cpd=ServiceHelper.getCity_fileService().findById(pd);
			if(cpd != null){
				pd.put("city_file_id", cpd.getString("city_file_id"));
				List<PageData> listAllDangqi=youXuanService.listAllDangqi(cpd);
				mv.addObject("listAllDangqi", listAllDangqi);
			}
			pd.put("nowpage", (page.getCurrentPage()-1)*10);
 			page.setPd(pd);
 			//获取当前页的费用总和
 			PageData nowpagesum=youXuanService.sumNowPageGoodsgg(page);
 			mv.addObject("nowpagesum", nowpagesum);
 			//获取全部的费用总和
 			PageData allpagesum=youXuanService.sumallGoodsgg(page);
 			mv.addObject("allpagesum", allpagesum);
			List<PageData> goodslist=youXuanService.datalistPageGoodsgg(page);
			for(PageData e : goodslist){
				PageData citypd=new PageData();
				citypd=ServiceHelper.getCity_fileService().findById(e);
				if(citypd != null){
					e.put("city_address", citypd.getString("province_name")+"/"+citypd.getString("city_name")+"/"+citypd.getString("area_name"));
				}
				citypd=null;
				PageData dqpd=new PageData();
				dqpd=youXuanService.finddetailDangqi(e);
				if(dqpd != null){
					e.put("youxuandq_name", "第"+dqpd.getString("youxuandq_id")+"期"+dqpd.getString("startdate")+"/"+dqpd.getString("enddate"));
				}
				dqpd=null;
			}
			mv.addObject("goodslist", goodslist);
			goodslist=null;
			//获取所有的城市
			List<PageData> provincelist=pcdService.listAll(pd);//省list
			mv.addObject("provincelist", provincelist);
 			mv.setViewName("zhihui/baobiao/baobiao4");
			this.getHC47();  
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		pd=null;
		return mv;
	}
	
	/**
	 * 收取商品编辑费用 
	 */
	@RequestMapping(value="/datalistPageGoodsFee")
	public ModelAndView datalistPageGoodsFee(Page page){
		//logBefore(logger, "收取商品编辑费用");
		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{
 			pd = this.getPageData();
 			pd.put("goods_check", "2");
 			pd.put("bianji_type", "2");
  			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
			}
			pd.put("nowpage", (page.getCurrentPage()-1)*10);
 			page.setPd(pd);
 			//获取当前页的费用总和
 			PageData nowpagesum=youXuanService.sumNowPageGoods(page);
 			mv.addObject("nowpagesum", nowpagesum);
 			//获取全部的费用总和
 			PageData allpagesum=youXuanService.sumallGoods(page);
 			mv.addObject("allpagesum", allpagesum);
			List<PageData> goodslist=youXuanService.datalistPageGoods(page);
			mv.addObject("goodslist", goodslist);
			goodslist=null;
			mv.setViewName("zhihui/baobiao/baobiao5");
			this.getHC48();  
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		pd=null;
		return mv;
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
 
 
	
	
	/* ===============================优选正式商品权限================================== */
	public void getHC43(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("43"));
		}
 	}
	/* ===============================权限================================== */
	
	
	
	/* ===============================优选待审核商品权限================================== */
	public void getHC44(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("44"));
		}
 	}
	/* ===============================权限================================== */
	
	
	/* ===============================优选待审核商品权限================================== */
	public void getHC45(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("45"));
		}
	}
	/* ===============================权限================================== */
	
	
	/* ===============================优选参数设置权限================================== */
	public void getHC46(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("46"));
		}
	}
	/* ===============================权限================================== */
	
	
	/* ===============================商品销售明细设置权限================================== */
	public void getHC47(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("47"));
		}
	}
	/* ===============================权限================================== */
	
	
	/* ===============================商品收费设置权限================================== */
	public void getHC48(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("48"));
		}
	}
	/* ===============================权限================================== */
	
	//----------------------------------H5---------------------------------------

	/**
	 * 正在进行优选商品分页列表 member_id,以及type:1-app,2-h5
	 * http://localhost/zhsh/oneYuan/listAllGoods.do?member_id=jy152602823408869&type=2
	 */
	@RequestMapping(value="/listAllGoods")
	public ModelAndView listAllGoods(   ){
		//logBefore(logger, "一元商品分页列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String type=pd.getString("type");//1-app,2-h5
			if(type == null || type.equals("")){
				type="1";
			}
			pd.put("isover", "0");
 			List<PageData> goodslist=youXuanService.listAll(pd);
			mv.addObject("goodslist", goodslist);
			goodslist=null;
    		mv.setViewName("youxuan/allyouxuan");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		pd=null;
		return mv;
	}
  	
	
	
	
	//----------------------------------商家端---------------------------------------
 	
	/**
	 * 去添加商品的页面 store_id
	 */
	@RequestMapping(value="/store_gosaveGoods")
	public ModelAndView store_gosaveGoods() throws Exception{
		//logBefore(logger, "去添加商品的页面");
		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
 		try {
			pd = this.getPageData();
			pd=appStoreService.findById(pd);
			//获取城市营销详情
 			PageData citypd=ServiceHelper.getCity_fileService().findById(pd);
 			mv.addObject("citypd", citypd);
  			//未过期档期
 			citypd.put("checked_status", "99");
 			List<PageData> listAllDangqi=youXuanService.listAllDangqi(citypd);
 			mv.addObject("listAllDangqi", listAllDangqi);
			listAllDangqi=null;
			citypd=null;
  			//获取所有开通城市的档案的城市
			List<String> smalllist=new ArrayList<String>();
			for(int i=0;i<5;i++){
				smalllist.add("img/imgadd.png");
			}
			mv.addObject("smalllist", smalllist);
			smalllist=null;
			//获取所有上线档期俺的集合
			mv.addObject("msg", "youxuan/store_saveGoods");
   			mv.setViewName("storepc/business_shop6");
 		} catch (Exception e) {
  			logger.error(e.toString(), e);
		}
 		mv.addObject("pd", pd);
 		mv.addObject("look_type", "add");
 		pd=null;
 		return mv;
	}
	
	/**
	 * 去查看详情
	 * 魏汉文
	 */
	@RequestMapping(value="/store_godetailGoods")
	public ModelAndView store_godetailGoods(){
		//logBefore(logger, "去查看详情");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try { 
			//获取商品详情
			PageData goodspd=youXuanService.findByIdGoods(pd);
			//获取城市营销详情
 			PageData citypd=ServiceHelper.getCity_fileService().findById(goodspd);
 			mv.addObject("citypd", citypd);
 			//获取所有的档期
 			List<PageData> listAllDangqi=youXuanService.listAllDangqi(citypd);
 			mv.addObject("listAllDangqi", listAllDangqi);
			listAllDangqi=null;
 			//档期
			PageData dqpd=new PageData();
			dqpd=youXuanService.finddetailDangqi(goodspd);
			if(dqpd != null){
				goodspd.put("youxuandq_name", "第"+dqpd.getString("youxuandq_id")+"期"+dqpd.getString("startdate")+"-"+dqpd.getString("enddate"));
			}
			dqpd=null;
			String[] small_imagesstr=goodspd.getString("small_images").split(",");
 			List<String> smalllist=new ArrayList<String>();
			for(int i=0;i<5;i++){
				if(small_imagesstr.length-1 >= i){
					smalllist.add(small_imagesstr[i]);
				}else{
					smalllist.add("img/imgadd.png");
				}
			}
			mv.addObject("smalllist", smalllist);
			String[] big_imagesstr=goodspd.getString("big_images").split(",");
			List<String> biglist=new ArrayList<String>();
			for (int i = 0; i < big_imagesstr.length; i++) {
				biglist.add(big_imagesstr[i]);
			}
			mv.addObject("biglist", biglist);
  			//获取规格
			List<PageData> goodsgglist=youXuanService.listAllGoodsgg(pd); 
			mv.addObject("goodsgglist", goodsgglist);
			goodsgglist=null;
			//获取简介
			List<PageData> goodsjjlist=youXuanService.listAllGoodsjj(pd);
			mv.addObject("goodsjjlist", goodsjjlist);
			goodsjjlist=null;
 			mv.addObject("goodspd", goodspd);
 			goodspd=null;
 			mv.addObject("msg", "youxuan/store_editGoods");
  			mv.setViewName("storepc/business_shop6");
  		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		mv.addObject("pd", pd);
		mv.addObject("look_type", "detail");
		pd=null;
		return mv;
	}	
	
	/**
	 * 将现有的商品添加至新的优选商品
	 */
	@RequestMapping(value="/store_goaddNowGoods")
	public ModelAndView store_goaddNowGoods(){
		//logBefore(logger, "去查看详情");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try { 
			//获取商品详情
			PageData goodspd=youXuanService.findByIdGoods(pd);
			//获取城市营销详情
 			PageData citypd=ServiceHelper.getCity_fileService().findById(goodspd);
 			mv.addObject("citypd", citypd);
 			//获取所有的档期
 			List<PageData> listAllDangqi=youXuanService.listAllDangqi(citypd);
 			mv.addObject("listAllDangqi", listAllDangqi);
			listAllDangqi=null;
 			//档期
			PageData dqpd=new PageData();
			dqpd=youXuanService.finddetailDangqi(goodspd);
			if(dqpd != null){
				goodspd.put("youxuandq_name", "第"+dqpd.getString("youxuandq_id")+"期"+dqpd.getString("startdate")+"-"+dqpd.getString("enddate"));
			}
			dqpd=null;
			String[] small_imagesstr=goodspd.getString("small_images").split(",");
 			List<String> smalllist=new ArrayList<String>();
			for(int i=0;i<5;i++){
				if(small_imagesstr.length-1 >= i){
					smalllist.add(small_imagesstr[i]);
				}else{
					smalllist.add("img/imgadd.png");
				}
			}
			mv.addObject("smalllist", smalllist);
			String[] big_imagesstr=goodspd.getString("big_images").split(",");
			List<String> biglist=new ArrayList<String>();
			for (int i = 0; i < big_imagesstr.length; i++) {
				biglist.add(big_imagesstr[i]);
			}
			mv.addObject("biglist", biglist);
  			//获取规格
			List<PageData> goodsgglist=youXuanService.listAllGoodsgg(pd); 
			mv.addObject("goodsgglist", goodsgglist);
			goodsgglist=null;
			//获取简介
			List<PageData> goodsjjlist=youXuanService.listAllGoodsjj(pd);
			mv.addObject("goodsjjlist", goodsjjlist);
			goodsjjlist=null;
 			mv.addObject("goodspd", goodspd);
 			goodspd=null;
 			mv.addObject("msg", "youxuan/store_saveGoods");
  			mv.setViewName("storepc/business_shop6");
  		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		mv.addObject("pd", pd);
		mv.addObject("look_type", "add");
		pd=null;
		return mv;
	}	
	
 	/**
	 * 添加商品
	 */
	@RequestMapping(value="/store_saveGoods")
	@ResponseBody
	public Object store_saveGoods() throws Exception{
		//logBefore(logger, " 添加商品的页面");
		Map<String,Object> map = new HashMap<String,Object>();
		//shiro管理的session
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		StoreRole storelogin = (StoreRole)session.getAttribute(Const.SESSION_STORE_USER);//获得登陆用户信息
  		PageData pd = new PageData();
  		String result="1";
  		String message="新增商品成功，可前往优选上线申请查看";
 		try {
 			//获取当前登录人的ID
 			pd=this.getPageData();
			String createoprator_id=storelogin.getLogin_id();
			String city_file_id=pd.getString("city_file_id");
 			//获取当前最大值得ID
 			String youxuangoods_id=youXuanService.getMaxId(pd);
			if(youxuangoods_id==null || youxuangoods_id.equals("")  ){
				youxuangoods_id=city_file_id+"00001";
				pd.put("youxuangoods_id", youxuangoods_id);
			}else{
				youxuangoods_id=youxuangoods_id.substring(4);
				//设置新增的ID
				youxuangoods_id=city_file_id+StringUtil.getNextId(youxuangoods_id);
 				pd.put("youxuangoods_id", youxuangoods_id);
			}
			//处理规格
			if(pd.getString("ggliststr").contains(",") && pd.getString("ggliststr").contains("!")){
				String[] ggliststr=pd.getString("ggliststr").split(",");
				for (int i = 0; i < ggliststr.length; i++) {
					String ss=ggliststr[i];
					String[] gg1=ss.split("!");
					String[] gg2=gg1[1].split("@");
					String youxuangg_id=gg1[0];
					PageData e=new PageData();
					e.put("youxuangg_id", youxuangg_id);
					e.put("youxuangoods_id", youxuangoods_id);
 					e.put("gg_imageurl", gg2[0]);
					e.put("gg_miaosu", gg2[1]);
					e.put("sale_money", gg2[2]);
					e.put("gudingsale_number", gg2[3]);
					e.put("needsale_number", gg2[4]);
					youXuanService.saveGoodsgg(e);
				}
			}
			//处理简介
			if(pd.getString("jjliststr").contains(",") && pd.getString("jjliststr").contains("!")){
				String[] jjliststr=pd.getString("jjliststr").split(",");
				for (int i = 0; i < jjliststr.length; i++) {
					String[] jj1=jjliststr[i].split("!");
					String[] jj2=jj1[1].split("@");
					String youxuanjj_id=jj1[0];
					PageData e=new PageData();
					e.put("youxuanjj_id", youxuanjj_id);
					e.put("youxuangoods_id", youxuangoods_id);
					e.put("title", jj2[0]);
					e.put("text", jj2[1]);
 					youXuanService.saveGoodsjj(e);
 				}
			}
			String small_images=pd.getString("small_images");
			if(small_images.contains(",") && small_images.split(",").length >0){
				pd.put("image_url", small_images.split(",")[0]);
			}
			pd.put("thistype_id", "only"+BaseController.get6UID());
			pd.put("storeoprator_id", createoprator_id);
			pd.put("goods_status", "0");
			if(pd.getString("limit_day") ==null || pd.getString("limit_day").equals("")){
				pd.put("limit_day", Const.youxuanendnumberdate);
			}
			youXuanService.saveGoods(pd);
   		} catch (Exception e) {
   			result="0";
   			message="系统异常";
			// TODO: handle exception
			//System.out.println(e.toString());
   			logger.error(e.toString(), e);
		}
 		map.put("result", result);
 		map.put("message", message);
 		pd=null;
 		return map;
	}
	
	/**
	 * 修改商品
	 */
	@RequestMapping(value="/store_editGoods")
	@ResponseBody
	public Object store_editGoods() throws Exception{
		//logBefore(logger, " 添加商品的页面");
		Map<String,Object> map = new HashMap<String,Object>();
      	PageData pd = new PageData();
  		String result="1";
  		String message="恭喜你，修改商品成功";
 		try {
 			//获取当前登录人的ID
 			pd=this.getPageData();
 			String youxuangoods_id=pd.getString("youxuangoods_id");
 			int kucunnumber=0;
  			//处理规格
			if(pd.getString("ggliststr").contains(",") && pd.getString("ggliststr").contains("!")){
				String[] ggliststr=pd.getString("ggliststr").split(",");
				for (int i = 0; i < ggliststr.length; i++) {
					String ss=ggliststr[i];
					String[] gg1=ss.split("!");
					String[] gg2=gg1[1].split("@");
					String youxuangg_id=gg1[0];
					PageData e=new PageData();
					e.put("youxuangg_id", youxuangg_id);
					e.put("youxuangoods_id", youxuangoods_id);
 					e.put("gg_imageurl", gg2[0]);
					e.put("gg_miaosu", gg2[1]);
					e.put("sale_money", gg2[2]);
					e.put("gudingsale_number", gg2[3]);
					kucunnumber=Integer.parseInt(gg2[3])+kucunnumber;
					e.put("needsale_number", gg2[4]);
 					if(youXuanService.finddetailgg(e) == null){
						youXuanService.saveGoodsgg(e);
					}else{
						youXuanService.editGoodsgg(e);
					}
 				}
			}
			//修改库存状态
			PageData statuspd=new PageData();
			statuspd.put("youxuangoods_id", youxuangoods_id);
			if(kucunnumber == 0 && ServiceHelper.getYouXuanService().findByIdGoods(pd).getString("goods_status").equals("4") ){
				statuspd.put("goods_status", "98");
				ServiceHelper.getYouXuanService().editGoods(statuspd);
 			}else if(kucunnumber > 0 && ServiceHelper.getYouXuanService().findByIdGoods(pd).getString("goods_status").equals("98")){
 				statuspd.put("goods_status", "4");
 				ServiceHelper.getYouXuanService().editGoods(statuspd);
			}else{
				//不修改
			}
 			statuspd=null;
 			//处理简介
			if(pd.getString("jjliststr").contains(",") && pd.getString("jjliststr").contains("!")){
				String[] jjliststr=pd.getString("jjliststr").split(",");
				for (int i = 0; i < jjliststr.length; i++) {
					String[] jj1=jjliststr[i].split("!");
					String[] jj2=jj1[1].split("@");
					String youxuanjj_id=jj1[0];
					PageData e=new PageData();
					e.put("youxuanjj_id", youxuanjj_id);
					e.put("youxuangoods_id", youxuangoods_id);
					e.put("title", jj2[0]);
					e.put("text", jj2[1]);
					if(youXuanService.finddetailjj(e) == null){
						youXuanService.saveGoodsjj(e);
					}else{
						youXuanService.editGoodsjj(e);
					}
 					
 				}
			}
			//替换第一张为主图片
			String small_images=pd.getString("small_images");
			if(small_images.contains(",") && small_images.split(",").length >0){
				pd.put("image_url", small_images.split(",")[0]);
			}
  			youXuanService.editGoods(pd);
   		} catch (Exception e) {
   			result="0";
   			message="系统异常";
    			logger.error(e.toString(), e);
		}
 		map.put("result", result);
 		map.put("message", message);
 		pd=null;
 		return map;
	}
	
	
	/**
	 * 商家的优选商品分页列表
	 */
	@RequestMapping(value="/store_datalistPageGoods")
	public ModelAndView store_datalistPageGoods(Page page){
		//logBefore(logger, "商家的优选商品分页列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String goods_check = pd.getString("goods_check"); //0-待审核，1-待支付，3-审核通过/驳回
			if(null == goods_check||  "".equals(goods_check)){
				goods_check="0";
				pd.put("goods_check", goods_check);
			}
 			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
			}
 			page.setPd(pd);
			List<PageData> goodslist=youXuanService.datalistPageGoods(page); 
			for(PageData e : goodslist){
				PageData citypd=new PageData();
				citypd=ServiceHelper.getCity_fileService().findById(e);
				if(citypd != null){
					e.put("city_address", citypd.getString("province_name")+"/"+citypd.getString("city_name")+"/"+citypd.getString("area_name"));
				}
				citypd=null;
				PageData dqpd=new PageData();
				dqpd=youXuanService.finddetailDangqi(e);
				if(dqpd != null){
					e.put("dangqi", "第"+dqpd.getString("youxuandq_id")+"期"+dqpd.getString("startdate")+"-"+dqpd.getString("enddate"));
				}
				dqpd=null;
			}
			mv.addObject("goodslist", goodslist);
			goodslist=null;
			mv.setViewName("storepc/business_shop7");
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		pd=null;
		return mv;
	}
  
	/**
	 * 商家的优选商品的销售查询
	 */
	@RequestMapping(value="/store_pageggGoods")
	public ModelAndView store_pageggGoods(Page page){
		//logBefore(logger, "商家的优选商品的销售查询");
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd=appStoreService.findById(pd);
			//获取城市营销详情
 			PageData citypd=ServiceHelper.getCity_fileService().findById(pd);
 			mv.addObject("citypd", citypd);
  			List<PageData> listAllDangqi=youXuanService.listAllDangqi(citypd);
 			mv.addObject("listAllDangqi", listAllDangqi);
   			String currentPage = pd.getString("currentPage");
			if(null != currentPage && !"".equals(currentPage)){
				page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
			}
			pd.put("nowpage", (page.getCurrentPage()-1)*10);
 			page.setPd(pd);
 			PageData nowpagesum=youXuanService.sumNowPageGoodsgg(page);
 			mv.addObject("nowpagesum", nowpagesum);
 			PageData allpagesum=youXuanService.sumallGoodsgg(page);
 			mv.addObject("allpagesum", allpagesum);
			List<PageData> goodslist=youXuanService.datalistPageGoodsgg(page);
			for(PageData e : goodslist){
 				PageData dqpd=new PageData();
				dqpd=youXuanService.finddetailDangqi(e);
				if(dqpd != null){
					e.put("youxuandq_name", "第"+dqpd.getString("youxuandq_id")+"期"+dqpd.getString("startdate")+"-"+dqpd.getString("enddate"));
				}
				dqpd=null;
 			}
			mv.addObject("goodslist", goodslist);
			goodslist=null;
 			mv.setViewName("storepc/business_shop8");
  		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		pd=null;
		return mv;
	}
	
	
	/**
	 * 去优选支付页面
	 */
	@RequestMapping(value="/goyouxuanpay")
	public ModelAndView goyouxuanpay() throws Exception{
 		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
    		mv.setViewName("storepc/youxuanpay_new");
//    		mv.setViewName("storepc/youxuanpay");
		} catch (Exception e) {
  			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		pd=null;
		return mv;
	}
 	
			 /**
			  * 优选的编辑费/上架费
			  */
			@RequestMapping(value="/payyouxuan")
			@ResponseBody
			public Object payyouxuan(HttpServletRequest request) throws Exception{
				Map<String, Object> map = new HashMap<String, Object>();
 		 		String result="1";
				String message="支付确认中";
				// type代表支付方式
				PageData pd=new PageData();
				try{
							pd = this.getPageData();
							String store_wealthhistory_id=BaseController.getTimeID();
							String youxuangoods_id=pd.getString("youxuangoods_id");
							PageData yxgoodspd=youXuanService.findByIdGoods(pd);
							String goods_status=yxgoodspd.getString("goods_status");
							String bianji_money=yxgoodspd.getString("bianji_money");
//							String shangjia_money=yxgoodspd.getString("shangjia_money");
							String money="";
							String profit_type="13";
							if(goods_status.equals("1")){
								money=bianji_money;
								profit_type="13";
							}
	//						else if(goods_status.equals("2")){
	//							money=shangjia_money;
//								profit_type="14";
	//						}
							String url=pd.getString("url");
							String in_jiqi=pd.getString("in_jiqi");
							if(in_jiqi == null || in_jiqi.equals("")){
								pd.put("in_jiqi", "2");
							}
 							if( pd.getString("store_operator_id") == null ||  pd.getString("store_operator_id").equals("") ){
									pd.put("store_operator_id", "jy"+pd.getString("store_id"));
							}
							String pay_way=pd.getString("pay_way");//支付方式
							//新增商家财富历史记录
			   				pd.put("wealth_type", "1");
			   				pd.put("profit_type",  profit_type);
			   				pd.put("number", df2.format(Double.parseDouble(money)));
			   				pd.put("store_wealthhistory_id", store_wealthhistory_id);
			   				pd.put("jiaoyi_id", youxuangoods_id);
			   				pd.put("user_id", "Jiuyu");
				   			pd.put("store_id", pd.getString("store_id"));
				   			pd.put("pay_type", pay_way);
			   				pd.put("store_operator_id", pd.getString("store_operator_id"));
			   				pd.put("process_status", "0");
				   			pd.put("last_wealth", ServiceHelper.getAppStoreService().sumStoreWealth(pd));
				   			pd.put("arrivalMoney", df2.format(Double.parseDouble(money)));
				   			appStoreService.saveWealthhistory(pd);
			   				String ip=Pay_historyController.getIp(request);//当前用户所在IP地址
							/*
							 * 支付方式pay_type:
							 * alipay:支付宝手机支付
								alipay_wap:支付宝手机网页支付
								alipay_qr:支付宝扫码支付
								alipay_pc_direct:支付宝 PC 网页支付
								bfb:百度钱包移动快捷支付
								bfb_wap:百度钱包手机网页支付
								upacp:银联全渠道支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
								upacp_wap:银联全渠道手机网页支付（2015 年 1 月 1 日后的银联新商户使用。若有疑问，请与 Ping++ 或者相关的收单行联系）
								upacp_pc:银联 PC 网页支付
								cp_b2b:银联企业网银支付
								wx:微信支付
								wx_pub:微信公众账号支付
								wx_pub_qr:微信公众账号扫码支付
								yeepay_wap:易宝手机网页支付
								jdpay_wap:京东手机网页支付
								cnp_u:应用内快捷支付（银联）
								cnp_f:应用内快捷支付（外卡）
								applepay_upacp:Apple Pay
								fqlpay_wap:分期乐支付
								qgbc_wap:量化派支付
								cmb_wallet:招行一网通
							 */
							//2.获取charge对象
							Charge charge = ChargeExample.getPayTwo(store_wealthhistory_id, Double.parseDouble(money)*100,ip,pay_way,"23","商品上架费",url);
							if(charge== null){
	 								result="0";
	 								message="支付失败";
	 								map.put("data", "");
							}else{
									map.put("data", charge);
							}
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
			
			
			
			
			
			 
			
			
			//----------------------------------------------------App接口---------------------------------------
		 	/**
			 * 获取优选商品的列表：
			 * youxuan/getAllYouxuanList.do
			 * 参数：currentPage页数   area_name 区名称  city_name 城市名称
			 *    单降序，双升序
			 *    paixu_number=1,2  折扣
			 *    paixu_number=3,4  价格
			 *   
			 *   筛选分类：city_file_sort_id
			 */
			@RequestMapping(value="/getAllYouxuanList")
			@ResponseBody
			public Object getAllYouxuanList(Page page){
				//logBefore(logger, "获取优选商品的列表");
				Map<String,Object> map = new HashMap<String,Object>();
				Map<String,Object> map1 = new HashMap<String,Object>();
				List<PageData> firstList=new ArrayList<PageData>();
				PageData pd = new PageData();
				String result="1";
				String message="获取成功";
				try{
					pd = this.getPageData();
					//判断是否为H5页面
					if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
							pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
					}
					String paixu_number = pd.getString("paixu_number");
					if(paixu_number == null || paixu_number.equals("")){
						pd.put("paixu_number", "4");
					}
					String currentPage = pd.getString("currentPage");
					if(null != currentPage && !"".equals(currentPage)){
						page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
					}
					//通过省市区获取当前的城市city_file_id
					PageData citypd=ServiceHelper.getCity_fileService().findById(pd);
					if(citypd != null){
						//获取当前城市的所有一级分类
						PageData flpd1 = new PageData();
						flpd1.put("sort_name", "全部分类");
						flpd1.put("city_file_sort_id", "");
						flpd1.put("sort_type", "0");
						firstList.add(flpd1);
						citypd.put("sort_parent_id", "0");
						citypd.put("sort_type", "1");
						List<PageData> sortList=ServiceHelper.getAppCity_fileService().listAllCitySort(citypd);
						for(PageData e1 : sortList){
							firstList.add(e1);
						}
						map1.put("firstList", firstList);
						firstList=null;
	 					//获取当前城市一期的优选
						PageData dqpd=youXuanService.getnowDq(citypd);
						if(dqpd != null){
							String enddate=dqpd.get("enddate").toString();
							String opentime=dqpd.get("opentime").toString();
							String endtime=enddate+" "+opentime;
							long endtimestamp=DateUtil.fomatDate1(endtime).getTime();
							long n=new Date().getTime();
							if(n > endtimestamp){
								map.put("result", "0");
								map.put("message", "暂未开通档期，敬请期待！");
								map.put("data", map1);
								return map;
							}
							pd.put("endtimestamp", endtimestamp+"");
							pd.put("endtime", endtime);
	 						pd.put("youxuandq_id", dqpd.getString("youxuandq_id"));
							dqpd=null;
		  		 			page.setPd(pd);
							List<PageData> goodslist=youXuanService.AppdatalistPageGoods(page); 
							for(PageData e : goodslist){
								String goods_zkrate=e.getString("goods_zkrate");
								e.put("goods_zkrate", goods_zkrate+"折");//折扣率
		 						e.put("jxjd", youXuanService.jxjdByGoods(e));//揭晓进度
 		 						e.put("lesssale_number", youXuanService.lesssale_number(e)+"");//获取当前商品还剩下多少件衣服
							}
							map1.put("goodslist", goodslist);
		  					goodslist=null;
						}else{
							result="0";
							message="暂未开通档期，敬请期待！";
							map1.put("goodslist", (new ArrayList<PageData>()));
						}
  	  					map1.put("pd", pd);
					}else{
						result="0";
						message="暂未开通档期，敬请期待！";
					}
   					citypd=null;
  					pd=null;
 				} catch(Exception e){
 					result="0";
 					message="系统异常";
					logger.error(e.toString(), e);
				}
				map.put("result", result);
				map.put("message", message);
				map.put("data", map1);
				return map;
			}
			
			/**
			 * 获取优选商品详情:youxuangoods_id,member_id
			 */
			@RequestMapping(value="/findDetailByYouxuan")
			@ResponseBody
			public Object findDetailByYouxuan(){
 				Map<String,Object> map = new HashMap<String,Object>();
 				PageData pd = new PageData();
				String result="1";
				String message="获取成功";
				try{ 
 						pd=this.getPageData();
 						//判断是否为H5页面
 						if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
 							pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
 						}
						//获取商品详情
						PageData goodspd=youXuanService.findByIdGoods(pd);
						if(goodspd != null){
 							String youxuangoods_id=goodspd.getString("youxuangoods_id");
							//获取当前城市一期的优选
							PageData dqpd=youXuanService.getnowDq(goodspd);
							if(dqpd != null){
								String enddate=dqpd.get("enddate").toString();
								String opentime=dqpd.get("opentime").toString();
								String endtime=enddate+" "+opentime;
								long endtimestamp=DateUtil.fomatDate1(endtime).getTime();
								goodspd.put("endtimestamp", endtimestamp+"");
								goodspd.put("endtime", endtime);
 								dqpd=null;
							}
 							goodspd.put("goods_zkrate", goodspd.getString("goods_zkrate")+"折");//折扣率
							//积分值
// 							String isxiangou=goodspd.getString("isxiangou");//0-否，1-是
 							String goods_jfrate=goodspd.getString("goods_jfrate");
							String xiangou_number=goodspd.getString("xiangou_number");
							String bp_salemoney=goodspd.getString("bp_salemoney");
							String jfnumber=df2.format(Double.parseDouble(goods_jfrate)/100*Double.parseDouble(bp_salemoney));
 							//------
							goodspd.put("jifen_text", goods_jfrate+"%，提货后可获赠"+jfnumber+"积分");
							goodspd.put("xiangou_text", "每个会员ID号限购"+xiangou_number+"份");
 							//缩略图
							String[] small_imagesstr=goodspd.getString("small_images").split(",");
				 			List<PageData> smalllist=new ArrayList<PageData>();
							for(int i=0;i<small_imagesstr.length;i++){
								PageData smallpd=new PageData();
								if(!small_imagesstr[i].equals("img/imgadd.png")){
									smallpd.put("small_url", small_imagesstr[i]);
									smalllist.add(smallpd);
								}
								smallpd=null;
 							}
							goodspd.put("smalllist", smalllist);
							smalllist=null;
 							PageData ggpd=new PageData();
							ggpd.put("look_imageurl", goodspd.getString("image_url"));//展示图片
							ggpd.put("ggcount_number", youXuanService.lesssale_number(goodspd)+"");//数量
 					  		//规格列表
							List<PageData> goodsgglist=youXuanService.listAllGoodsgg(pd); 
							ggpd.put("goodsgglist", goodsgglist);
							if(goodsgglist.size() >1){
								ggpd.put("ggsale_qujian","￥"+youXuanService.minSaleMoney(goodspd)+"-"+youXuanService.maxSaleMoney(goodspd));//价格区间
							}else{
								ggpd.put("ggsale_qujian","￥"+youXuanService.maxSaleMoney(goodspd));//价格区间
							}
							goodsgglist=null;
							goodspd.put("ggpd", ggpd);
							ggpd=null;
 							//是否收藏过当前商家收藏
							PageData  collectpd=new PageData();
							pd.put("store_id", goodspd.getString("store_id"));
							collectpd=appStoreService.getCollectionById(pd);
							if(collectpd == null){
								goodspd.put("iscollect", "0");
							}else{
								goodspd.put("iscollect", "1");
							}
							//最后剩余时间戳
							if( ServiceHelper.getShopCarService().getproximityBuyTime(pd) != null){
								long l=(new Date()).getTime();
								long lasttime=DateUtil.fomatDate1(ServiceHelper.getShopCarService().getproximityBuyTime(pd).getString("createdate")).getTime();
  								lasttime=lasttime+(long) (Double.parseDouble(Const.youxuangoods_times)*60*60*1000);
								if((l-lasttime) < 0){
									goodspd.put("lasttime", -(l-lasttime)  +"");
								}else{
									goodspd.put("lasttime", "0");
								}
 							}else{
								goodspd.put("lasttime", "0");
							}
							goodspd.put("goods_imageinfor", "https://www.jiuyuvip.com/html_member/goYouXuanDescInfor.do?show_type=1&type=1&youxuangoods_id="+youxuangoods_id);//图文详情
							goodspd.put("goods_textinfor", "https://www.jiuyuvip.com/html_member/goYouXuanDescInfor.do?show_type=2&type=1&youxuangoods_id="+youxuangoods_id);//产品参数
							goodspd.put("goods_storetuijian", "www.baidu.com");//商家
							goodspd.put("yybp_text", "https://www.jiuyuvip.com/jsp/youxuan/qgsm.html");//商家
							if(ServiceHelper.getAppMemberService().findById(pd) != null  ){
								goodspd.put("fengxiang", "https://www.jiuyuvip.com/html_member/goMyYouXuanfengxaingDetail.do?recommended="+ServiceHelper.getAppMemberService().findById(pd).getString("phone")+"&recommended_type=2&youxuangoods_id="+youxuangoods_id);//商家
							}else{
								goodspd.put("fengxiang", "https://www.jiuyuvip.com/html_member/goMyYouXuanfengxaingDetail.do?recommended=0&recommended_type=0&youxuangoods_id="+youxuangoods_id);//商家
							}
							goodspd.put("sk_shop", BaseController.jiami(goodspd.getString("store_id")));
							if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
								goodspd.remove("store_id");
								pd.remove("member_id");
							}
  							map.put("data", goodspd);
				 			goodspd=null;
						}
     		  	} catch(Exception e){
 					result="0";
 					message="系统异常";
 					map.put("data", pd);
					logger.error(e.toString(), e);
				}
				map.put("result", result);
				map.put("message", message);
 				return map;
			}
			
			
			
			/**
		   	 * 我的购物车列表
		   	 * member_id 
 		   	 */
			@RequestMapping(value="/MyShopGoodsList")
			@ResponseBody
			public Object MyShopGoodsList( ) throws Exception{
 				Map<String, Object> map = new HashMap<String, Object>();
 				Map<String, Object> map1 = new HashMap<String, Object>();
 			  	String result="1";
				String message="获取成功";
				PageData pd=new PageData();
				try{ 
					pd = this.getPageData();
					//判断是否为H5页面
					if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
						pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
					}
					//购物车清空倒计时
					if( ServiceHelper.getShopCarService().getproximityBuyTime(pd) != null){
						long l=(new Date()).getTime();
						long lasttime=DateUtil.fomatDate1(ServiceHelper.getShopCarService().getproximityBuyTime(pd).getString("createdate")).getTime();
						lasttime=lasttime+(long) (Double.parseDouble(Const.youxuangoods_times)*60*60*1000);
						if((l-lasttime) < 0){
							map1.put("lasttime", -(l-lasttime)  +"");
						}else{
							map1.put("lasttime", "0");
						}
					}else{
						map1.put("lasttime", "0");
					}
 					//
					List<PageData> storeList= ServiceHelper.getShopCarService().getShopStoreIdByMember(pd);
					for (PageData e : storeList) {
						//获取购物车列表
						e.put("sk_shop", BaseController.jiami(e.getString("store_id")));
						e.put("goods_type", "2");
 						e.put("member_id", pd.getString("member_id"));
						//获取我的购物车的所有的商品
	 		 			List<PageData> goodsList= ServiceHelper.getShopCarService().MyShopCarList(e);
	 		 			e.put("goodsList", goodsList);
			 			goodsList=null;
			 			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
							e.remove("store_id");
							e.remove("member_id");
						}
					}
					map1.put("storeList", storeList);	
				}catch(Exception e){
					result="0";
					message="系统异常";
					map.put("data", e.toString());
					logger.error(e.toString(), e);
				}
				map.put("result", result);
				map.put("message", message);
				map.put("data", map1);
 				pd=null;
		    	return map;
			}
			
			
			
			/**
		   	 * 计算总价已省金额：购物车的ID
		   	 * allshopcart_id格式 shopcart_id,shopcart_id......
 		   	 */
			@RequestMapping(value="/countAllShopMoney")
			@ResponseBody
			public Object countAllShopMoney( ) throws Exception{
 				Map<String, Object> map = new HashMap<String, Object>();
  			  	String result="1";
				String message="获取成功";
				PageData pd=new PageData();
				try{ 
					pd = this.getPageData();
					double allmoney=0;
					double allsendJf=0;
					double youhuimoney=0;
					String[] allshopcart_id=pd.getString("allshopcart_id").split(",");
					for (int i = 0; i < allshopcart_id.length; i++) {
  						PageData e=new PageData();
						e.put("shopcart_id", allshopcart_id[i]);
						e.put("goods_type", "2");
						e=ServiceHelper.getShopCarService().findById(e);
						if(e != null){
							allmoney+=Double.parseDouble(e.getString("goods_number"))*Double.parseDouble(e.getString("sale_money"));
							youhuimoney+=Double.parseDouble(e.getString("goods_number"))*(Double.parseDouble(e.getString("gf_salemoney"))-Double.parseDouble(e.getString("sale_money")));
							allsendJf+=Double.parseDouble(e.getString("goods_number"))*Double.parseDouble(e.getString("sale_money"))*Double.parseDouble(e.getString("goods_jfrate"))/100;
 						}
 					}
					pd.put("allmoney", StringUtil.baoliuTwoDouble(allmoney+""));
					pd.put("allsendJf", StringUtil.baoliuTwoDouble(allsendJf+""));
					pd.put("youhuimoney", StringUtil.baoliuTwoDouble(youhuimoney+""));
				}catch(Exception e){
					result="0";
					message="系统异常";
					map.put("data", e.toString());
					logger.error(e.toString(), e);
				}
				map.put("result", result);
				map.put("message", message);
				map.put("data", pd);
 				pd=null;
		    	return map;
			}
 			
		
			
			/**
			 * 去确认订单的界面
			 * goodsinfor : goods_id@goods_number,goods_id@goods_number 
			 * gopay_type :1-直接结算，2-购物车结算
			 * member_id : app端必须得传
			 */
			@RequestMapping(value="/gosurePayOrder")
			@ResponseBody
			public Object gosurePayOrder(){
				Map<String,Object> map = new HashMap<String,Object>();
				Map<String,Object> map1 = new HashMap<String,Object>();
 				List<PageData> storeList=new ArrayList<PageData>();
 				List<PageData> goodsList=new ArrayList<PageData>();
				String result = "1";
				String message="";
  				PageData pd = new PageData();
				try{ 
 					pd = this.getPageData();
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
  					double moneyCount=0;//总和计金额
 					double jfCount=0;//总和计积分
 					double shenMoneyCount=0;//总合计省了多少
 					//开始如下
 					String storeidstr="";
 					String gopay_type=pd.getString("gopay_type");
    				String[] goodsstr=pd.getString("goodsinfor").split(",");
   					for (int i = 0; i < goodsstr.length; i++) {
   						if( goodsstr[i].split("@").length != 2){
   							result="0";
   							message="请选择商品";
   							break;
   						}
  						String goods_id=goodsstr[i].split("@")[0];
						String goods_number=goodsstr[i].split("@")[1];
 						if(gopay_type.equals("1")){
							pd.put("goods_id", goods_id);
							pd.put("goods_number", goods_number);
							pd.put("goods_type", "2");
							//判断下库存
							PageData isokpd=iskuncun(pd);
							if(isokpd.getString("result").equals("0")){
								result="0";
								message=isokpd.getString("message")+" "+message;
 							}
						}
   						PageData ggpd=new PageData();
						ggpd.put("youxuangg_id", goods_id);
						ggpd=youXuanService.finddetailgg(ggpd);
						if(ggpd == null ){
							result="0";
							message="购买数量为："+goods_number+",商品不存在 " +message;
 						}
						String store_id=ggpd.getString("store_id");
						String brand_name=ggpd.getString("brand_name");
						String goods_name=ggpd.getString("goods_name");
						String goods_jfrate=ggpd.getString("goods_jfrate");
						String gg_imageurl=ggpd.getString("gg_imageurl");
						String gg_miaosu=ggpd.getString("gg_miaosu");
						String sale_money=ggpd.getString("sale_money");
						String gf_salemoney=ggpd.getString("gf_salemoney");
 						double money=Double.parseDouble(goods_number)*Double.parseDouble(sale_money);//总金额
 						moneyCount+=money;
 						shenMoneyCount+=Double.parseDouble(goods_number)*Double.parseDouble(gf_salemoney)-money;
						//判断商家的赠送积分是否充足
 	 					double storesendjf=Double.parseDouble(df2.format(money*Double.parseDouble(goods_jfrate)/100));
	 					jfCount+=storesendjf;
 						if(TongYong.orderIsOkByStore(storesendjf, money, "3", 0, 0, money, appStoreService.findById(ggpd))){
							result="0";
							message=appStoreService.findById(ggpd).getString("store_name")+"-积分余额不足，请商家充值后再购买"+" "+message;
 						}
						PageData goodspd=new PageData();//商品信息
   						goodspd.put("goods_id", goods_id);
   						goodspd.put("store_id", store_id);
						goodspd.put("goods_number", goods_number);
						goodspd.put("goods_jfrate", goods_jfrate);
 						goodspd.put("sale_money", sale_money);
						goodspd.put("brand_name", brand_name);
						goodspd.put("goods_name", goods_name);
						goodspd.put("gf_salemoney", gf_salemoney);
						goodspd.put("gg_miaosu", gg_miaosu);
 						goodspd.put("gg_imageurl", gg_imageurl);
 						goodspd.put("thismoney", df2.format(money));
 						goodspd.put("thisjf", df2.format(storesendjf));
 						goodsList.add(goodspd);
 						if(!storeidstr.contains(store_id)){
 							storeidstr+=store_id+",";
 						}
  					}
   					map1.put("moneyCount", df2.format(moneyCount));
   					map1.put("jfCount", jfCount);
   					map1.put("shenMoneyCount", df2.format(shenMoneyCount));
   					//根据商家排序
   					String[] store=storeidstr.split(",");
   					for (int i = 0; i < store.length; i++) {
   						List<PageData> storeGoodsList=new ArrayList<PageData>();
   						PageData storepd=new PageData();
						String store_id=store[i];
						storepd.put("store_id", store_id);
						if( appStoreService.findById(storepd) != null){
 							double allmoney=0;
							double alljifen=0;
							double allshenmoney=0;
							int allnumber=0;
							for(PageData e: goodsList){
								if(e.getString("store_id").equals(store_id)){
									double gf=Double.parseDouble(e.getString("gf_salemoney"));
									double m=Double.parseDouble(e.getString("thismoney"));
									double n=Double.parseDouble(e.getString("thisjf"));
									double z=Double.parseDouble(e.getString("goods_number"));
									allmoney+=m;
									alljifen+=n;
									allnumber+=z;
									allshenmoney+=(gf-m)*z;
									storeGoodsList.add(e);
								}
	 						}
							storepd.put("store_name", appStoreService.findById(storepd).getString("store_name"));
							storepd.put("pictrue_url", appStoreService.findById(storepd).getString("pictrue_url"));
							storepd.put("allmoney", df2.format(allmoney));
							storepd.put("alljifen", df2.format(alljifen));
							storepd.put("allnumber", allnumber);
							storepd.put("allshenmoney", df2.format(allshenmoney));
							storepd.put("goodsList", storeGoodsList);
 							storeGoodsList=null;
							storeList.add(storepd);
							storepd=null;
						}
  					}
   					map1.put("storeList", storeList);
   					//当前用户的积分余额
   					map1.put("now_integral", ServiceHelper.getAppMemberService().findById(pd).getString("now_integral"));
  				} catch(Exception e){
					result="0";
					message="获取异常";
					logger.error(e.toString(), e);
				}
				map.put("result", result);
				map.put("message",message);
				map.put("data",map1);
 				pd=null;
				return map;
			}
			

			/**
			 * 支付
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
			@RequestMapping(value="/PayOrder")
			@ResponseBody
			public synchronized Object PayOrder(HttpServletRequest request){
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
								PageData isokpd=iskuncun(pd);
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
// 						System.out.println(list.get(mapi).getKey());
// 						System.out.println(list.get(mapi).getValue());
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
									PageData isokpd=iskuncun(pd);
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
								gpd.put("price", df2.format(goods_money));
								gpd.put("order_id", order_id);
								gpd.put("goods_type", "2");
								appOrderService.saveOrderGoods(gpd);
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
						orderpd.put("sale_money", df2.format(money));
						orderpd.put("discount_after_money", df2.format(money));
						//看看有没有使用积分
						if(money-user_integral_last <=0 ){//支付的钱
  							orderpd.put("actual_money", "0");
							orderpd.put("user_integral", df2.format(money));
							user_integral_last=user_integral_last-money;
						}else{
 							orderpd.put("actual_money", df2.format(money-user_integral_last));
							orderpd.put("user_integral", df2.format(user_integral_last));
							user_integral_last=0;
						}
 						orderpd.put("get_integral", df2.format(jfCount));
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
					appOrderService.saveguanlian(glpd);
 					//判断是否需要调用ping++接口
					double lastpaymoney=allmoney-user_integral;
					BigDecimal _amount = new BigDecimal(lastpaymoney);
					lastpaymoney =   _amount.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
 					if(lastpaymoney >0){
						//支付返回charge
						String url="https://www.jiuyuvip.com/html_member/payOkGoJsp.do?orderno="+guanlian_id+"&pay_name="+pay_name+"&money="+TongYong.df2.format(lastpaymoney);
						String openid=ServiceHelper.getAppMemberService().findById(pd).getString("wxopen_id");//微信支付的专用openid
						String ip=Pay_historyController.getIp(request);//当前用户所在IP地址
						Charge charge=new Charge()  ;
						if(in_jiqi.equals("5")){//公众号上
							if(pay_way.equals("wx_pub")){
								charge = ChargeExample.getPayThree(guanlian_id, lastpaymoney*100,ip,pay_way,"14","消费",openid);
							}else if(pay_way.equals("alipay_wap")){
								charge = ChargeExample.getPayTwo(guanlian_id, lastpaymoney*100,ip,pay_way,"14","消费",url);
							}
	 					}else{
							charge = ChargeExample.getPay(guanlian_id, lastpaymoney*100,ip,pay_way,"14","消费");
						}
						if(charge == null ){
							map.put("result", "0");
							map.put("message", "支付失败，charge出错");
							map.put("data", "");
					    	return map;
						}
						map.put("data", charge);
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
			
			/**
			 * 支付失败掉的接口
			 */
			@RequestMapping(value="/failPayOrder")
			@ResponseBody
			public void failPayOrder() {
 	 		 	PageData pd=new PageData();
	 		 	try { 
	 		 			pd=this.getPageData();
	 		 			String orderno=pd.getString("orderno");
	 		 			//更改下状态
	 		 			PageData glpd=new PageData();
			       		glpd.put("guanlian_id", orderno);
			       		glpd.put("status", "0");//0-未处理，1-已处理
			       		glpd=appOrderService.getguanlianById(glpd);
			       		if(glpd != null){
			       			String[] allbeguanlian_id=glpd.getString("beguanlian_id").split(",");
			       				for (int i = 0; i < allbeguanlian_id.length; i++) {
 	 			   					//马上启动定时器
 	 	 		 			 		OrderShop op=new OrderShop( allbeguanlian_id[i]);
 	 			 					Timer tt=new Timer();
 	 			 					tt.schedule(op, 1000);
 			       				}
			       		}
	    		 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			
			
			
			
			
			/**
			 * 新增/减少/删除   商品进购物车购物车数据---按+的时候
			 * goods_id,caozuo_number(总数量),member_id，goods_type  
			 */
			@RequestMapping(value="/isOKadd")
			@ResponseBody
			public Object isOKadd(){
				Map<String,Object> map = new HashMap<String,Object>();
				String result = "1";
				String message="操作成功";
				String number="";
 				PageData pd = new PageData();
				try{
					pd = this.getPageData();
 					//判断是否为H5页面
					if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
						pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
					}
					pd.put("goods_number", pd.getString("caozuo_number"));
					PageData isokpd=iskuncun(pd);
					if(isokpd != null && isokpd.getString("result").equals("0")){
						message=isokpd.getString("message");
						result=isokpd.getString("result");
						number=isokpd.getString("data");
					}
				} catch(Exception e){
					result="0";
					message="获取异常";
					logger.error(e.toString(), e);
				}
				map.put("result", result);
				map.put("message",message);
				map.put("data",number);
				pd=null;
				return map;
			}
				
			/**
			 * 判断是否存在库存
			 * @param goods_id
			 * @param goods_number
			 * @return
			 */
			public synchronized  static PageData iskuncun(PageData pd){
				PageData flagpd=new PageData();
				String  result="1";
				String message="";
				String nowkucun="0";
				try {
 					String goods_type=pd.getString("goods_type");
					int caozuo_number=Integer.parseInt(pd.getString("goods_number"));
					//判断是否已经新增过该商品
					PageData e=ServiceHelper.getShopCarService().findById(pd);
					boolean flag=false;
					if(e == null){
		 				flag=true;
		   			}
					//1-直接购买，2-购物车购买
					if(goods_type.equals("1")){
 						PageData goodspd=ServiceHelper.getAppGoodsService().findById(pd);
						if(goodspd != null){
							String goods_name=goodspd.getString("goods_name");
							int stock_number=Integer.parseInt(goodspd.getString("stock_number"));//库存
							nowkucun=(stock_number-caozuo_number)+"";
							if(stock_number < caozuo_number){
			 	 					flagpd.put("result", "0");
			 	 					flagpd.put("message",goods_name+"库存仅剩"+stock_number);
			 	 					flagpd.put("data",stock_number+"");
 									return flagpd;
		 					} 
 		 				}
						goodspd=null;
					}else if(goods_type.equals("2")){
						pd.put("youxuangg_id", pd.getString("goods_id"));
						PageData goodspd=ServiceHelper.getYouXuanService().finddetailgg(pd);
		 				if(goodspd != null){
		 					String goods_name=goodspd.getString("goods_name");
		 					//库存
		 					int needsale_number=Integer.parseInt(goodspd.getString("needsale_number"));
		 					nowkucun=(needsale_number-caozuo_number)+"";
		 					//线判断当前用户限购
		 					String isxiangou=goodspd.getString("isxiangou");
		 					if(goodspd.getString("xiangou_number") == null || goodspd.getString("xiangou_number").equals("")){
		 						goodspd.put("xiangou_number", "100");
		 					}
		 					int xiangou_number=Integer.parseInt(goodspd.getString("xiangou_number"));
 		 					if(isxiangou.equals("1")){
		 						int havebuy=0;
		 						//订单中
		 						if(ServiceHelper.getAppOrderService().getOrderNumberByGoods(pd) != null){
		 							havebuy=(int)(Double.parseDouble(ServiceHelper.getAppOrderService().getOrderNumberByGoods(pd).getString("shop_number")));
		 						}
		 						if(!flag){
		 							havebuy+=Integer.parseInt(e.getString("goods_number"));
		  						}
		 						if(havebuy+caozuo_number > xiangou_number){
		 							flagpd.put("result", "0");
		 							flagpd.put("message",goods_name+"为限购商品" );
		 							flagpd.put("data",needsale_number+"");
 		 							return flagpd;
		 						}
		 					}
 	  		 				if((needsale_number <   caozuo_number) || (needsale_number == 0 && caozuo_number > 0)){
	  		 					flagpd.put("result", "0");
	  		 					flagpd.put("message",goods_name+"库存仅剩"+needsale_number);
	  		 					flagpd.put("data",""+needsale_number+"");
 	 							return flagpd;
		 					} 
 	  		 				
 	  		 			    //判断商家的赠送积分是否充足
  		 					String goods_jfrate=goodspd.getString("goods_jfrate");
  		 					String sale_money=goodspd.getString("sale_money");
 		 					double money=caozuo_number*Double.parseDouble(sale_money);//总金额
 		 					double storesendjf=Double.parseDouble(TongYong.df2.format(money*Double.parseDouble(goods_jfrate)/100));
    	  					if(TongYong.orderIsOkByStore(storesendjf, money, "3", 0, 0, money, ServiceHelper.getAppStoreService().findById(goodspd))){
 								flagpd.put("result", "0");
 								flagpd.put("message", ServiceHelper.getAppStoreService().findById(goodspd).getString("store_name")+"-的积分余额不足，请等待商家充值后再购买");
 								flagpd.put("data", "");
 						    	return flagpd;
 							}
		  				}
						goodspd=null;
					}
 					e=null;
				
				} catch (Exception e2) {
					// TODO: handle exception
					//System.out.println(e2.toString());
					(new TongYong()).dayinerro(e2);
 				}
				flagpd.put("result", result);
				flagpd.put("message",message);
				flagpd.put("data",nowkucun);
 				return flagpd;
			}
			
			
			
			
			/**
			 * 支付成功之后的详情接口
			 * youxaun/successPayOrderDeTail.do
			 * orderno
			 */
			@RequestMapping(value="/successPayOrderDeTail")
			@ResponseBody
			public Object successPayOrderDeTail() {
 				Map<String,Object> map = new HashMap<String,Object>();
 				List<PageData> allgoodsList=new ArrayList<PageData>();
				String result = "1";
				String message="操作成功";
  				PageData pd = new PageData();
				try{
						pd = this.getPageData();
	 		 			String orderno=pd.getString("orderno");
 	 		 			//更改下状态
	 		 			PageData glpd=new PageData();
			       		glpd.put("guanlian_id", orderno);
			       		glpd.put("status", "0");//0-未处理，1-已处理
			       		glpd=appOrderService.getguanlianById(glpd);
			       		if(glpd != null){
 			       				String[] allbeguanlian_id=glpd.getString("beguanlian_id").split(",");
			       				for (int i = 0; i < allbeguanlian_id.length; i++) {
 	 			   					String order_id=allbeguanlian_id[i];
 	 			   					PageData storepd=new PageData();
 	 			   					storepd.put("order_id", order_id);
 	 			   					storepd=ServiceHelper.getAppOrderService().findByPayOverId(storepd);
 	 			   					if(storepd != null){
 	 			   						//获取商品列表以及商家个别信息
		 	 			   				storepd=TongYong.getGoodsListByOrder(storepd);	
 	 			   						allgoodsList.add(storepd);
			 	 				 		storepd=null;
 	 			   					}
 	 			       			}
			       		}
	    		 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result="0";
					message="系统异常";
					logger.error(e.toString(), e);
				}
				map.put("result", result);
				map.put("message", message);
				map.put("data", allgoodsList);
				return map;
		}
		
			
		
			/**
			 * 去优选商品的预览界面
			 */
			@RequestMapping(value="/goYouxuanGoodsYuLan")
			public ModelAndView goYouxuanGoodsYuLan() throws Exception{
				//logBefore(logger, "去优选商品的预览界面");
				ModelAndView mv = this.getModelAndView();
				PageData pd = new PageData();
				try {
					pd = this.getPageData();
					//System.out.println(pd);
// 					String isxiangou=pd.getString("isxiangou");//0-否，1-是
 					String goods_jfrate=pd.getString("goods_jfrate");
 					if(goods_jfrate == null || goods_jfrate.equals("")){
 						goods_jfrate="0";
 					}
					String xiangou_number=pd.getString("xiangou_number");
					String bp_salemoney=pd.getString("bp_salemoney");
					if(bp_salemoney == null || bp_salemoney.equals("")){
						bp_salemoney="0";
 					}
					String goods_zkrate=pd.getString("goods_zkrate");
					String jfnumber=df2.format(Double.parseDouble(goods_jfrate)/100*Double.parseDouble(bp_salemoney));
    				pd.put("jifen_text", goods_jfrate+"%，提货后可获赠"+jfnumber+"积分");
					pd.put("xiangou_text", "每个会员ID号限购"+xiangou_number+"份");
					//缩略图
					String[] small_imagesstr=pd.getString("smallliststr").split(",");
		 			List<PageData> smalllist=new ArrayList<PageData>();
					for(int i=0;i<small_imagesstr.length;i++){
						PageData smallpd=new PageData();
						if(!small_imagesstr[i].equals("img/imgadd.png")){
							pd.put("image_url", small_imagesstr[0]);
							pd.put("look_imageurl", small_imagesstr[0]);
							smallpd.put("small_url", small_imagesstr[i]);
							smalllist.add(smallpd);
						}
						smallpd=null;
					}
					mv.addObject("smalllist", smalllist);
					smalllist=null;
					//详情图
					String[] big_imagesstr=pd.getString("bigliststr").split(",");
		 			List<PageData> biglist=new ArrayList<PageData>();
					for(int i=0;i<big_imagesstr.length;i++){
						PageData bigpd=new PageData();
						bigpd.put("big_image", big_imagesstr[i]);
  						biglist.add(bigpd);
  						bigpd=null;
					}
					mv.addObject("biglist", biglist);
					biglist=null;
					double max=0;
					double min=0;
					int number=0;
 					//处理规格
					List<PageData> gglist=new ArrayList<PageData>();
					int ggnumber=0; 
					if(pd.getString("ggliststr").contains(",") && pd.getString("ggliststr").contains("!")){
						String[] ggliststr=pd.getString("ggliststr").split(",");
						ggnumber=ggliststr.length;
 						for (int i = 0; i < ggliststr.length; i++) {
							String ss=ggliststr[i];
							String[] gg1=ss.split("!");
							String[] gg2=gg1[1].split("@");
 							PageData e=new PageData();
  		 					e.put("gg_imageurl", gg2[0]);
							e.put("gg_miaosu", gg2[1]);
							e.put("sale_money", gg2[2]);
							e.put("needsale_number", gg2[3]);
							gglist.add(e);
							if(Double.parseDouble(gg2[2]) > max ){
								max=Double.parseDouble(gg2[2]);
							}
							if(Double.parseDouble(gg2[2]) < min || min == 0 ){
								min=Double.parseDouble(gg2[2]);
							}
							number+=Integer.parseInt(gg2[3]);
						}
					}
					pd.put("ggcount_number", number+"");//数量
					if(ggnumber ==0){
						pd.put("ggsale_qujian",max); 
					}else{
						pd.put("ggsale_qujian",min+"-"+max);//价格区间
					}
 					mv.addObject("gglist", gglist);
					//处理简介
					List<PageData> jjlist=new ArrayList<PageData>();
					if(pd.getString("jjliststr").contains(",") && pd.getString("jjliststr").contains("!")){
						String[] jjliststr=pd.getString("jjliststr").split(",");
						for (int i = 0; i < jjliststr.length; i++) {
							String[] jj1=jjliststr[i].split("!");
							String[] jj2=jj1[1].split("@");
 							PageData e=new PageData();
  							e.put("title", jj2[0]);
							e.put("text", jj2[1]);
							jjlist.add(e);
		 				}
					}
					mv.addObject("jjlist", jjlist);
  		    		mv.setViewName("youxuan/yxyulan");
				} catch (Exception e) {
					// TODO: handle exception
					//System.out.println(e.toString());
					logger.error(e.toString(), e);
				}
				if(ServiceHelper.getAppStoreService().findById(pd) != null){
					pd.put("store_name", ServiceHelper.getAppStoreService().findById(pd).getString("store_name"));
					pd.put("pictrue_url", ServiceHelper.getAppStoreService().findById(pd).getString("pictrue_url"));
					pd.put("store_address", ServiceHelper.getAppStoreService().findById(pd).getString("address"));
				}else{
					pd.put("store_name", "暂无当前商家");
					pd.put("picture_url", "");
					pd.put("store_address", "");
				}
 				mv.addObject("pd", pd);
				pd=null;
				return mv;
			}
			 
			
			
			
 }
