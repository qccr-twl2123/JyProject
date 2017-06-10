package com.tianer.controller.storepc.store_image;

 

import javax.annotation.Resource;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
import com.tianer.controller.base.BaseController;
 
import com.tianer.service.business.store_shift.Store_shiftService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
 


/** 
 * 
* 类名称：Storepc_scorewayController   
* 类描述：   班次汇总
* 创建人：邢江涛  
* 创建时间：2016年6月22日 
 */
@Controller("storeStorepc_imageController")
@RequestMapping(value = "/storepc_image")
public class Storepc_imageController extends BaseController{
	
//	@Resource(name = "storepc_imageService")
//	private Storepc_imageService imageService;
	
//	/**
//	 * 班次汇总列表
//	 */
//	@RequestMapping(value="/list")
//	public ModelAndView list(Page page){
//		ModelAndView mv = this.getModelAndView();
//		DecimalFormat    df   = new DecimalFormat("######0.00");
//		List<PageData> tjList=new ArrayList<PageData>();
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			//获取所有操作员列表
//			List<PageData> splist = storeManageService.listAll(pd);
// 			mv.addObject("splist", splist);
// 			splist=null;
//			//获取所有班次
//			List<PageData> bcList=store_shiftService.listAll(pd);
//			mv.addObject("bcList", bcList);
//			bcList=null;
//			//获取操作员
//			List<PageData> soplist=storeManageService.listAllOperator(pd);
//			for(PageData e : soplist){
//				if(imageService.listAll(e).size()>0){
//						 e=imageService.listAll(e).get(0);
//						 String allmoney=e.getString("allmoney");
//						 if(allmoney != null && !allmoney.equals("")){
//							 String xjmoney=e.getString("sumxjmoney");
//							 if(xjmoney == null || xjmoney.equals("")){
//								xjmoney="0";
//							 }
// 							 String threemoney=df.format(Double.parseDouble(allmoney)-Double.parseDouble(xjmoney));
//							 e.put("sumthreemoney", threemoney);
//							 tjList.add(e);
//						}
// 				}
// 			}
//			mv.addObject("tjList", tjList);
//  		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd",pd);
//		mv.setViewName("storepc/business_account7");
//	
//		return mv;
//		
//	}
	@Resource(name="store_shiftService")
	private Store_shiftService store_shiftService;
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
//	
//	/**
//	 * 班次销售明细列表
//	 */
//	@RequestMapping(value="/listImage")
//	public ModelAndView listImage(Page page){
//		ModelAndView mv = this.getModelAndView();
//		DecimalFormat    df   = new DecimalFormat("######0.00");
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			if(pd.getString("store_id") != null){
//				//获取所有操作员列表
//				List<PageData> splist = storeManageService.listAll(pd);
//				mv.addObject("splist", splist);
//				splist=null;
//				//获取所有班次
//				List<PageData> bcList=store_shiftService.listAll(pd);
//				mv.addObject("bcList", bcList);
//				bcList=null;
//				//列出所有的汇总信息
//				page.setPd(pd);
//				List<PageData>	mingxiList = imageService.listImage(page);
//	 			mv.addObject("mingxiList", mingxiList);
//	 			mingxiList=null;
//	 			//列出所有的统计信息
//	 			PageData sumpd=imageService.imagelistSum(page);
//  	 			mv.addObject("sumpd", sumpd);
//			}
// 		} catch(Exception e){
//			logger.error(e.toString(), e);
//		}
//		mv.addObject("pd",pd);
//		mv.setViewName("storepc/business_account6");
// 		return mv;
// 	}
//	
	

}
