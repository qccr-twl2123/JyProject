package com.tianer.controller.memberapp.order;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.util.PageData;

/** 
 * 类名称：OrderController
 * 创建人：刘耀耀
 * 创建时间：2016-06-20
 */
@Controller("appOrderController")
@RequestMapping(value="/app_order")
public class OrderController extends BaseController {
	
	@Resource(name="appOrderService")
	private AppOrderService orderService;
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	/**
	 * 个人订单列表
 	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(Page page){
//		logBefore(logger, "列表Order");
		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String nowpage=pd.getString("nowpage");
			if(nowpage==null || nowpage.equals("")){
				nowpage="1";
			}
			page.setCurrentPage(Integer.parseInt(nowpage));
 			page.setPd(pd);
 			List<PageData>	varList = orderService.datalistPageByOrder(page);	//列出Order列表
 			map.put("data", varList);
 			varList=null;
 		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	

	
	
	
	/**
	 * 历史订单信息
	 * 刘耀耀
	 * 2016.06.21
	 */
	@RequestMapping(value="/listhistory")
	@ResponseBody
	public Object listhistory(   ){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			List<PageData>	varList = orderService.listhistory(pd);	//列出Order列表
 			map.put("data", varList);
 			varList=null;
		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	/**
	 * 删除订单
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("order_id")==null||pd.getString("order_id").equals("")){
				message="删除失败";
				result="0";
			}
			orderService.delete(pd);
 			map.put("data", "");
		} catch(Exception e){
			result="0";
			message="删除失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	
	
	/**
	 * 更多历史订单信息
 	 */
	@RequestMapping(value="/listhistorym")
	@ResponseBody
	public Object listhistorym(){
//		logBefore(logger, "列表Order");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			List<PageData>	varList = orderService.listhistorym(pd);	//列出Order列表
			if(varList.size()==0){
 				map.put("data", "");
			}
			map.put("data", varList);
			varList=null;
		} catch(Exception e){
			result = "0";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	
	/**
	 * 提货卷列表
	 * 魏汉文20160630
	 */
	@RequestMapping(value="/tihuoList")
	@ResponseBody
	public Object tihuoList(Page page){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String nowpage=pd.getString("nowpage");
			if(nowpage==null || nowpage.equals("")){
				nowpage="1";
			}
			page.setCurrentPage(Integer.parseInt(nowpage));
 			page.setPd(pd);
			List<PageData>  tihuoList=orderService.datalistPageByTiHuo(page);
			map.put("data", tihuoList);
			tihuoList=null;
		} catch(Exception e){
			result="0";
			message="获取失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}
	
	@Resource(name="appGoodsService")
	private AppGoodsService goodsService;
	
	
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	@Resource(name="appGoodsService")
	private AppGoodsService appGoodsService;
	
	
	/**
	 * 提货卷详情包括商品
	 * 魏汉文20160630
	 */
	@RequestMapping(value="/tihuoByOrderId")
	@ResponseBody
	public Object tihuoByOrderId(){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
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
			result="0";
			message="获取失败";
			map.put("data","");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		map.put("data",pd);
 		return map;
	}
	

	/**
	 * 根据提货券id或者订单id查询订单详情（不包括商品）
	 * app_order/findById.do?order_id=20170428022415388320
	 */
	@RequestMapping(value="/findById")
	@ResponseBody
	public Object findById(){
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			pd = orderService.findByOrderId(pd);  
 			pd=TongYong.getGoodsListByOrder(pd);
 			//更改提货卷显示格式
 			if(pd.getString("tihuo_id") != null && pd.getString("tihuo_id").length() == 10){
				pd.put("tihuo_id", pd.getString("tihuo_id").substring(0, 2)+"-"+pd.getString("tihuo_id").substring(2,6 )+"-"+pd.getString("tihuo_id").substring(6, 10));
			}
 			//导流列表
	 		pd.put("daoLiuStoreList", TongYong.daoliuList(pd));
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
	
 	

	@RequestMapping(value="/csget")
	@ResponseBody
	public Object csget(){
		Map<String,Object> map = new HashMap<String,Object>();
			try {
				System.out.println("开始删除购物车=====================================");
				//获取商品信息
				PageData pd=new PageData();
				pd.put("pay_sort_type", "2");
				List<PageData> goodsList=appGoodsService.listAllGoodsByOrder(pd);
				map.put("goodsList", goodsList);
				for(PageData goodspd : goodsList){
						System.out.println(goodspd.toString());
		//			goodspd.put("member_id", "jy135754774426x98");
		//			//清空购物车
		//			ServiceHelper.getShopCarService().delShop(goodspd);
				}
				System.out.println("结束删除购物车=====================================");
			} catch (Exception e) {
				// TODO: handle exception
			}
			return map;
 		}
	 
}
