package com.tianer.controller.storepc.daoliu;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.storepc.daoliu.StoreDaoLiuService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;


/** 
 * 
* 类名称：DaoLiuController   
* 类描述：   提现
* 创建人：邢江涛  
* 创建时间：2016年6月22日 
 */
@Controller("daoLiuController")
@RequestMapping(value = "/storepc_daoliu")
public class DaoLiuController extends BaseController{
 
 	@Resource(name = "storeDaoLiuService")
	private StoreDaoLiuService storeDaoLiuService;
 	
 	
 	
 	public DecimalFormat df2=TongYong.df2;
 	
 	//======================================================总后台==============================
  	/**
	 * 总后台-----------导流收益列表
	 */
	@RequestMapping(value="/daoliuList")
	public ModelAndView daoliuList(Page page){
		logBefore(logger, "发布的导流列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String paixu_type=pd.getString("paixu_type");
			if(paixu_type == null || paixu_type.equals("")){
				pd.put("paixu_type", "1");
			}
			pd.put("nowpage", (page.getCurrentPage()-1)*10);
			page.setPd(pd);
			PageData sumnowpage=storeDaoLiuService.sumdaoliuByMemberNowPage(page);
			mv.addObject("sumnowpage", sumnowpage);
			PageData sumallpage=storeDaoLiuService.allsumdaoliuByMember(page);
			mv.addObject("sumallpage", sumallpage);
			List<PageData>	varList = storeDaoLiuService.daoliuByMemberlistPage(page);
			this.getHC50(); //调用权限
			mv.setViewName("zhihui/baobiao/baobiao6");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
 	
	/* ===============================权限================================== */
	public void getHC50(){
		ModelAndView mv = this.getModelAndView();
		HttpSession session = this.getRequest().getSession();
		Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_MAP);
		if(map != null){
			session.setAttribute("qx", map.get("50"));
		}
 	}
	/* ===============================权限================================== */
 	
	
	
 	
 	//==========================================商家后台=====================================
	/**
	 * 我是广告主，发布广告招商信息/推广需求
	 */
	@RequestMapping(value="/saveDaoliu")
	@ResponseBody
	public Object saveDaoliu( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="操作成功";
		PageData pd = new PageData();
		try{ 
 			pd = this.getPageData();
 			if(pd.getString("role_type").equals("2")){
 				String nowmoney=ServiceHelper.getStorepc_wealthService().sumStoreWealth(pd);
 				if(Double.parseDouble(nowmoney) >= Const.minWealthZhuCe){
 					storeDaoLiuService.saveDaoLiu(pd);
 				}else{
 					result="0";
 					message="积分低于"+Const.minWealthZhuCe+"不能进行操作";
 				}
 			}else{
 				storeDaoLiuService.saveDaoLiu(pd);
 			}
 		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	
 	
 	
	/**
	 * 商家------------我是广告主商家合作/带合作的导流列表
	 */
	@RequestMapping(value="/StoredaoliulistPage")
	public ModelAndView StoredaoliulistPage(Page page){
		logBefore(logger, "发布的导流列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String type=pd.getString("type");
 			if(type == null || type.equals("1")){
				page.setPd(pd);
				List<PageData>	varList = storeDaoLiuService.daoliuByHzStorelistPage(page);
	 			mv.addObject("varList", varList);
	 			varList=null;
				//获取已经添加的推广链接；daoliu_status=1
				pd.put("daoliu_status", "1");
				List<PageData>	hzStoreList = storeDaoLiuService.daoliuByHzStorelistAll(pd);
				int s=hzStoreList.size();
				for (int i = 0; i <12-s ; i++) {
					hzStoreList.add(new PageData());
				}
				mv.addObject("hzStoreList", hzStoreList);
				hzStoreList=null;
			}else if(type.equals("2")){
				
			}else if(type.equals("3")){
				//获取发布推广的列表
				pd.put("role_type", "2");
				page.setPd(pd);
				List<PageData>	varList = storeDaoLiuService.daoliulistPage(page);
	 			mv.addObject("varList", varList);
	 			varList=null;
			}else{
				pd.put("zhu_store_id", pd.getString("store_id"));
				pd.put("nowpage", (page.getCurrentPage()-1)*10);
				page.setPd(pd);
				//广告收入
 				PageData sumnowpage=storeDaoLiuService.sumdaoliuByMemberNowPage(page);
				mv.addObject("sumnowpage", sumnowpage);
				sumnowpage=null;
				PageData sumallpage=storeDaoLiuService.allsumdaoliuByMember(page);
				mv.addObject("sumallpage", sumallpage);
				sumallpage=null;
				List<PageData>	varList = storeDaoLiuService.daoliuByMemberlistPage(page);
 				mv.addObject("varList", varList);
 				varList=null;
 			}
			//统计当前商家已为多少家商家推广,以及收入
 			pd.put("tuiguang_number", storeDaoLiuService.countAllHzStore(pd));
 			pd.put("tuiguang_money", storeDaoLiuService.sumAllHzStore(pd));
			mv.setViewName("storepc/business_homepage9");
 			mv.addObject("pd", pd);
 			pd=null;
		} catch(Exception e){
			logger.error(e.toString(), e);
			System.out.println(e.toString());
		}
		return mv;
	}
	
	
	/**
	 * 添加确认合作的商家信息
	 */
	@RequestMapping(value="/saveHzStore")
	@ResponseBody
	public Object saveHzStore( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="操作成功";
		PageData pd = new PageData();
		try{ 
 			pd = this.getPageData();
 			String store_id=pd.getString("store_id");
 			String daoliu_id=pd.getString("daoliu_id");
 			if(daoliu_id != null){
  				//发布广告、申请推广
 				PageData dlpd=storeDaoLiuService.findByIdDaoLiu(pd);
 				if(dlpd != null){
 					if(dlpd.getString("store_id").equals(store_id)){
 						result="0";
 						message="不能操作自己发布的商品";
  					}else{
  						String role_type=dlpd.getString("role_type");
 	 					if(role_type.equals("2")){
 	 						dlpd.put("zhu_store_id", store_id);
 	 						dlpd.put("ci_store_id", dlpd.getString("store_id"));
 	 					}else{
 	 						dlpd.put("zhu_store_id", dlpd.getString("store_id"));
 	 						dlpd.put("ci_store_id", store_id);
 	 						dlpd.put("startdate", pd.getString("startdate"));
 	 						dlpd.put("enddate", pd.getString("enddate"));
 	 					}
 	 					dlpd.put("daoliu_status", "0");
 	  		  			storeDaoLiuService.saveHzStore(dlpd);
 					}
  				}
   			}else{
 				//编辑推广链接
 				pd.put("daoliu_status", "0");
 	 			PageData ispd=storeDaoLiuService.detailThisHzStore(pd);
 	 			if( ispd == null){
 	 				result="0";
 	 				message="没有当前的申请信息,请重新检查";
 	 			}else{
 	 				pd.put("daoliu_status", "1");
 	 				pd.put("daoliu_id",ispd.getString("daoliu_id"));
 	  				storeDaoLiuService.saveHzStore(pd);
 	 			}
 			}
  		} catch(Exception e){
			result="0";
			map.put("message", "获取异常");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	/**
	 * 清除合作导流记录
	 */
	@RequestMapping(value="/qingchu")
	public void qingchu(PrintWriter out){
		logBefore(logger, "清除导流记录");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			storeDaoLiuService.updateDaoliurecordById(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 商家确认合作的导流列表
	 */
	@RequestMapping(value="/StorehzdaoliuList")
	public ModelAndView StorehzdaoliuList(Page page){
		logBefore(logger, "发布的导流列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = storeDaoLiuService.daoliuByHzStorelistPage(page);
			this.getHC50(); //调用权限
			mv.setViewName("zhihui/baobiao/baobiao6");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
 	
	/**
	 * 会员点击的导流列表
	 */
	@RequestMapping(value="/StoredjdaoliuList")
	public ModelAndView StoredjdaoliuList(Page page){
		logBefore(logger, "会员点击的导流列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData>	varList = storeDaoLiuService.daoliuByMemberlistPage(page);
			this.getHC50(); //调用权限
			mv.setViewName("zhihui/baobiao/baobiao6");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
	
	/**
	 *  
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView mv = this.getModelAndView();
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.setViewName("storepc/business_account3");
 		return mv;
		
	}
	
	
	
	
	/**
	 * 商家------------我是推广商
	 */
	@RequestMapping(value="/StoreTuiGuanglistPage")
	public ModelAndView StoreTuiGuanglistPage(Page page){
		logBefore(logger, "发布的导流列表");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String type=pd.getString("type");
 			if(type == null || type.equals("1")){
 				//获取广告商发布推广的列表
				pd.put("role_type", "1");
				page.setPd(pd);
				List<PageData>	varList = storeDaoLiuService.daoliuTuiGuanglistPage(page);
 	 			mv.addObject("varList", varList);
	 			varList=null;
 			}else if(type.equals("2")){
 				
 			}else{
 				//广告收入
 				pd.put("ci_store_id", pd.getString("store_id"));
 				pd.put("nowpage", (page.getCurrentPage()-1)*10);
 				page.setPd(pd);
 				PageData sumnowpage=storeDaoLiuService.sumdaoliuByMemberNowPage(page);
				mv.addObject("sumnowpage", sumnowpage);
				sumnowpage=null;
				PageData sumallpage=storeDaoLiuService.allsumdaoliuByMember(page);
				mv.addObject("sumallpage", sumallpage);
				sumallpage=null;
				List<PageData>	varList = storeDaoLiuService.daoliuByMemberlistPage(page);
 				mv.addObject("varList", varList);
 				pd.put("click_number", varList.size());
 				varList=null;
  			}
			mv.setViewName("storepc/business_homepage10");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
