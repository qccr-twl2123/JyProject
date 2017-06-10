package com.tianer.controller.storepc.store_marketingtype;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.service.storepc.store_marketing.Storepc_marketingService;
import com.tianer.service.storepc.store_marketingtype.Storepc_marketingtypeService;
import com.tianer.service.storepc.store_redpackets.Goods_categoryService;
import com.tianer.service.storepc.store_redpackets.Storepc_redpacketsService;
import com.tianer.util.AppUtil;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;


/** 
 * 
* 类名称：Storepc_marketingtypeController   
* 类描述：   营销类型（买n减1）
* 创建人：邢江涛
* 创建时间：2016年6月12日 下午4:37
 */
@Controller("storeStorepc_marketingtypeController")
@RequestMapping(value="/storepc_marketingtype")
public class Storepc_marketingtypeController extends BaseController{
	
	@Resource(name = "storepc_marketingtypeService")
	private Storepc_marketingtypeService storepcMarketingTypeService;
	
	@Resource(name = "goodsCategoryService")
	private Goods_categoryService goodsService;

	@Resource(name = "storepc_marketingService")
	private Storepc_marketingService storepcMarketingService;
	@Resource(name = "storepc_redpacketsService")
	private Storepc_redpacketsService storepcRedpacketsService;
	/**
	 * 新增营销
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(){
		logBefore(logger, "新增营销");
		Map<String , Object> map = new HashMap<String, Object>();
		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String store_marketingtype_id=BaseController.getTimeID();
			String content=pd.getString("content");
			//判断是否为满赠
 			if(pd.getString("marketing_type").equals("1")){
 					 if(!pd.getString("marketsmall_type").equals("3")){
 						  pd.put("onesend_redpackage", store_marketingtype_id);
 		 				  String timenumber=pd.getString("timenumber");
 		 				 if(timenumber == null || timenumber.equals("")){
 	  						timenumber="0";
 	  					  }
 		 				  PageData redpd=new PageData();
 		 				  redpd.put("store_redpackets_id", store_marketingtype_id);
 		 				  redpd.put("money", pd.getString("w_money"));
 		 				  redpd.put("starttime",DateUtil.getDay());
 		 				  redpd.put("endtime", DateUtil.getAfterDayDate(DateUtil.getDay(),timenumber));
 		 				  redpd.put("store_id", pd.getString("store_id"));
 		  				  redpd.put("redpackage_number", "1");
 		  				  redpd.put("redpackage_status", "5");
 		  				  redpd.put("srp_opentype_id", "0");
 		 				  redpd.put("srp_usercondition_id", "2");//满XX元
  		  				 if(pd.getString("marketsmall_type").equals("1")){ //折扣
  		  					  redpd.put("srp_usercondition_content", "满"+pd.getString("zkovermoney")+"元");//满XX元
 		 					  redpd.put("redpackage_type", "2");
 		 					  redpd.put("choice_type", "1");
 		 					  redpd.put("reduce_content",pd.getString("redcontent"));
 		    			  }else if(pd.getString("marketsmall_type").equals("2")){ //满减
 		    				  	redpd.put("srp_usercondition_content", "满"+pd.getString("rdovermoney")+"元");//满XX元
	 		 					redpd.put("redpackage_type", "1");
	 		 					redpd.put("choice_type", pd.getString("onereduce_zktype"));
	 		 					redpd.put("reduce_content", pd.getString("redcontent"));
 		   				 }
 		 				storepcRedpacketsService.save(redpd);
 		 				redpd=null;
 				  }
  			}else  if(pd.getString("marketing_type").equals("5") ){//判断是否为累计次数
  				 if(!pd.getString("marketsmall_type").equals("3")){
	  					  pd.put("fiveredpackage_type", store_marketingtype_id);
	  					  String timenumber=pd.getString("timenumber");
	  					  if(timenumber == null || timenumber.equals("")){
	  						timenumber="0";
	  					  }
		 				  PageData redpd=new PageData();
		 				  redpd.put("store_redpackets_id", store_marketingtype_id);
		 				  redpd.put("money", pd.getString("w_money"));
		 				  redpd.put("starttime",DateUtil.getDay());
		 				  redpd.put("endtime", DateUtil.getAfterDayDate(DateUtil.getDay(),timenumber));
		 				  redpd.put("store_id", pd.getString("store_id"));
		  				  redpd.put("redpackage_number", "1");
		  				  redpd.put("redpackage_status", "5");
		  				  redpd.put("srp_opentype_id", "0");
		 				  redpd.put("srp_usercondition_id", "2");//满XX元
 		 				 if(pd.getString("marketsmall_type").equals("1")){ //折扣
		  					 	redpd.put("srp_usercondition_content", "满"+pd.getString("zkovermoney")+"元");//满XX元
		 					  redpd.put("redpackage_type", "2");
		 					  redpd.put("choice_type", "1");
		 					  redpd.put("reduce_content",pd.getString("redcontent"));
		    			  }else if(pd.getString("marketsmall_type").equals("2")){ //满减
		    				  	redpd.put("srp_usercondition_content", "满"+pd.getString("rdovermoney")+"元");//满XX元
	 		 					redpd.put("redpackage_type", "1");
	 		 					redpd.put("choice_type", pd.getString("onereduce_zktype"));
	 		 					redpd.put("reduce_content", pd.getString("redcontent"));
		   				 }
		 				storepcRedpacketsService.save(redpd);
		 				redpd=null;
  				 }
 	 				 
 	 			}
			pd.put("store_marketingtype_id",store_marketingtype_id);	//主键
			//本表新增数据值
			storepcMarketingTypeService.save(pd);
			//2、我的营销数据表新增
			String stratdate = pd.getString("startdate");
			String enddate = pd.getString("enddate");
			pd.put("starttime", stratdate);
			pd.put("endtime", enddate);
			pd.put("grantrule", content);
  			pd.put("marketing_id", store_marketingtype_id);
			pd.put("store_marketing_id", BaseController.getTimeID());	
 			storepcMarketingService.save(pd);
 			
 		} catch (Exception e) {
			result="00";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		return AppUtil.returnObject(pd, map);
	}
	
	
	
	/**
	 * 去满减列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String type = pd.getString("marketing_type");
			List<PageData>	varList = storepcMarketingTypeService.listAllByType(pd);	//列出Store列表
 			List<PageData> categoryList = goodsService.goodsCategoryList(pd);//商品类别
			mv.addObject("categoryList", categoryList);
 			mv.addObject("varList", varList);
 			//判断type type为2的时候跳转到满减页面 type为3跳转到时段营销页面
			if(type.equals("2")){
				mv.setViewName("storepc/business_marketing13");//满减
			}else if(type.equals("3")){
				mv.setViewName("storepc/business_marketing14");//时间营销
			}else if(type.equals("4")){
				mv.setViewName("storepc/business_marketing15");//买n减1
			}else{
				mv.setViewName("storepc/business_marketing16");//累计次数
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		
 		return mv;
		
	}
	
	
	/**
	 * 商品
	 */
	@RequestMapping(value="/goodsList")
	@ResponseBody
	public List<PageData> goodsList(Page page){
		PageData pd = new PageData();
		List<PageData> goodsList  =null;
		try{
			pd = this.getPageData();
			goodsList = goodsService.listgoods(pd);
			for(int i =0;i<goodsList.size() ; i++){
//				 pd=goodsList.get(i);
				 pd=goodsList.get(0);
				 if(pd.getString("promotion_type").equals("3")){
					 goodsList.remove(i);
					 i=i-1;
				 }
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return goodsList;
	}
    /**
	 *
	 *	修改过期红包状态 
	 * 	刘耀耀
	 * 	2016.07.19
	 */
	@RequestMapping(value="/editTime")
	@ResponseBody
	public Object editTime(Page page){
		logBefore(logger, "修改红包状态");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="修改成功";
		PageData pd = new PageData();
		try{
			SimpleDateFormat splt=new SimpleDateFormat("yyyy-MM-dd");
			String data=splt.format(new Date());//当天时间
			pd.put("data",data);
			String store_marketingtype_id=pd.getString("store_marketingtype_id");
			pd.put("store_marketingtype_id",store_marketingtype_id);
			storepcMarketingTypeService.updateTime(pd);
		} catch(Exception e){
			result="0";
			message="修改异常";
			logger.error(e.toString(), e);
		}
		map.put("data", "");
		map.put("result", result);
		map.put("message",message);
		return map;
	}
 

}
