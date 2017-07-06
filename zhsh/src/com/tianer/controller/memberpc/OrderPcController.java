package com.tianer.controller.memberpc;

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
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.memberPc.OrderPcService;
import com.tianer.service.memberPc.PcTongYongService;
import com.tianer.util.PageData;

/** 
 * 
* 类名称：orderPcController   
* 我的订单列表
 */
@Controller("orderPcController")
@RequestMapping(value = "/memberorderPc")
public class OrderPcController extends BaseController{

	@Resource(name="orService")
	private OrderPcService orderPcService;
	

	@Resource(name="pcTongYongService")
	private PcTongYongService pcTongYongService;
 
	
	/**
	 * 订单列表
	 */
	@RequestMapping(value="/orderInfoList")
	public ModelAndView orderInfoList(Page page){
		ModelAndView mv = this.getModelAndView();
		Session session=SecurityUtils.getSubject().getSession();
  		String member_id=(String)session.getAttribute("jylogin_id");
		PageData pd=new PageData();
 		try{
 			pd=this.getPageData();
  			//获取所有的市
    		List<PageData> cityList=pcTongYongService.listAllCity(pd);
   			mv.addObject("cityList",cityList);
   			//获取城市档案ID以及城市营销参数
  			PageData citypd=pcTongYongService.getCityMarketingForPCD(pd);
  			if(citypd != null ){
  				//获取当前地级市下的所有开通的区域
	  	   		List<PageData> areaList=pcTongYongService.listAllArea(citypd);
   	   			mv.addObject("areaList",areaList);
   	   			areaList=null;
   	   			//获得一级分类
  	   			citypd.put("sort_parent_id", "0");
  	   			citypd.put("sort_type", "1");
  	   			List<PageData> firstList=pcTongYongService.listAllCitySort(citypd);
   	   			citypd.remove("sort_parent_id");
  	   			citypd.remove("sort_type");
  	   			PageData e=null;
  	   			PageData twopd=new PageData();
  	   			int n=firstList.size();
  	   			for (int i = 0; i < n; i++) {
					e=firstList.get(i);
		 			//获得二级分类
					twopd.put("city_file_id", e.getString("city_file_id"));
					twopd.put("sort_parent_id", e.getString("city_file_sort_id"));
					twopd.put("sort_type", "2");
					List<PageData> twoList=pcTongYongService.listAllCitySort(twopd);
 	  				e.put("twoList", twoList);
 	  				twoList=null;
  	   			}
  	   			mv.addObject("firstList",firstList);
  			}
 			if(member_id == null || member_id.equals("")){
				mv.setViewName("redirect:../memberpc/goMemberLogin.do");
			}else{
				pd.put("member_id", member_id);
				if(pd.getString("order_status") == null){
					pd.put("order_status", "1");
				}
				page.setPd(pd);
				List<PageData> orderList = orderPcService.datalistPage(page);
				PageData e=null;
				int n=orderList.size();
				for (int i = 0; i < n; i++) {
					e=orderList.get(i);
 					e=TongYong.getGoodsListByOrder(e);
  					e.remove("payer_id");
 					e.remove("store_id");
 					e.remove("member_id");
 				}
	 			mv.addObject("orderList", orderList);
	 			orderList=null;
	 			mv.addObject("pd",pd);
	 			pd=null;
	 	 		mv.setViewName("memberpc/business_Buyers4");
			}
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		return mv;
 	}
	
	/**
	 * 删除订单
	 */
	@RequestMapping(value="/delOrder")
	@ResponseBody
	public Object delOrder(){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			orderPcService.delOrder(pd);
		} catch(Exception e){
			result="0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
 		return map;
	}
	 
	
	
	/**
	 * 根据提货券id或者订单id查询订单详情
	 */
	@RequestMapping(value="/findById")
	@ResponseBody
	public Object findById(){
		logBefore(logger, "列表Order");
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			pd = orderPcService.findByOrderId(pd);	//列出Order列表
 			if(pd == null || pd.equals("")){
 				result="0";
 				message="订单不存在！";
 				pd=new PageData();
  			}else {
  				pd=TongYong.getGoodsListByOrder(pd);
  				pd.remove("payer_id");
  				pd.remove("store_id");
  				pd.remove("member_id");
  			}
 		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
 		return map;
	}
}
