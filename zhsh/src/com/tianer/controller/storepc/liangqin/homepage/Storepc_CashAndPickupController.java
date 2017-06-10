package com.tianer.controller.storepc.liangqin.homepage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.service.storeapp.Payapp_historyService;
import com.tianer.service.storepc.liangqin.homepage.CashAndPickupService;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
/**
 * 
 * 类名称: Storepc_CashAndPickupController 
 * 类描述: 收银与取货的后台
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-11 下午2:44:08	
 * 版本号: v1.0
 */
@SuppressWarnings("static-access")
@Controller
@RequestMapping(value="/storepc_cashandpickup")
public class Storepc_CashAndPickupController extends BaseController{	
	
	@Resource(name="cashAndPickupService")
	private CashAndPickupService cashAndPickupService;
	@Resource(name="payapp_historyService")
	private Payapp_historyService historyService;
	
	DateUtil dateUtil = new DateUtil();

	/**
	 * 商家的phone可以通过商家登陆后的session中获得，同时还可以获得store_id,此处默认为1
	* 方法名称:：sureMoney 
	* 方法描述：
	* 创建人：魏汉文
	* 创建时间：2016年7月12日 上午11:56:00
	 */
	@RequestMapping(value="/sureMoney")   
	public ModelAndView sureMoney() throws Exception {
		logBefore(logger, " 进行确认收银");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try { 
			pd=this.getPageData();
 			 pd.put("order_status", "1");
			 historyService.editOrderStatus(pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		mv.setViewName("redirect:../storepc/goShQyStore.do");
		return mv;
	}
//
//	/**
//	 * 判断提货卷是否存在  魏汉文20160706
//	 */
//	@RequestMapping(value="/isTiHuo")
//	@ResponseBody
//	public Object isTiHuo(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{
//			 	pd = this.getPageData();
//			 	pd.put("tihuo_id", pd.getString("tihuo_id").trim());
//			 	pd=historyService.tihuoByOrderId(pd);
//			 	if(pd== null ){
//			 		result="0";
//			 		message="提货卷不存在";
//			 		map.put("data", "");
//			 	}else{
//			 		if(pd.getString("tihuo_status").equals("0")){
//				 			 //获取商品信息
//							List<PageData> goodsList=historyService.listAllGoodsByOrder(pd);
//							pd.put("goodsList", goodsList);
//							map.put("data", pd);
//			 		}else{//已提货
//			 			result="0";
//				 		message="该提货卷已提货";
//				 		map.put("data", "");
//			 		}
// 			 	}
//     	} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//   		return map;
//	}
//	
//	
//	
//	/**
//	 * 订单确认提货  魏汉文20160706
//	 */
//	@RequestMapping(value="/sureTiHuo")
//	@ResponseBody
//	public Object sureTiHuo(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="提货成功";
//		PageData pd = new PageData();
//		try{
//			 pd = this.getPageData();
//			 pd.put("tihuo_status", "1");
//			 historyService.editOrderStatus(pd);
//    	} catch(Exception e){
//			logger.error(e.toString(), e);
//			result="0";
//			message="系统异常";
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");
//  		return map;
//	}
//	
	
	
}