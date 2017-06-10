package com.tianer.controller.storepc.store_discountway;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.tianer.service.storepc.liangqin.shopmanage.CategoryManageService;
import com.tianer.service.storepc.store_discountway.Storepc_discountwayService;
import com.tianer.service.storepc.store_marketing.Storepc_marketingService;
import com.tianer.service.storepc.store_scoreway.Storepc_scorewayService;
import com.tianer.util.AppUtil;
import com.tianer.util.PageData;


/** 
 * 
* 类名称：Storepc_discountwayController   
* 类描述：   折扣方式
* 创建人：邢江涛  
* 创建时间：2016年6月12日 下午5:07
 */
@Controller()
@RequestMapping(value="/storepc_discountway")
public class Storepc_discountwayController extends BaseController{
	
	@Resource(name = "storepc_discountwayService") 
	private Storepc_discountwayService storepcDiscountwayService;
	@Resource(name = "storepc_marketingService")
	private Storepc_marketingService storepcMarketingService;
	@Resource(name = "storepc_scorewayService")
	private Storepc_scorewayService storepcScorewayService; 
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(){
		logBefore(logger, "新增storepc_discountway");
		Map<String , Object> map = new HashMap<String, Object>();
		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//判断是否为积分的分类营销,不是营销的吧所有积分率为0
			if(!pd.getString("change_type").equals("2")){
				PageData e=new PageData();
				e.put("store_id", pd.getString("store_id"));
				e.put("zk_rate", "0");
				categoryManageService.updateJfRate(pd);
			}
			//1.新增到本表数据库
			String store_discountway_id=BaseController.getTimeID();
			pd.put("store_discountway_id",store_discountway_id);	//主键
 			storepcDiscountwayService.save(pd);
			//把获取到的值存放在管理我的营销数据表的字段里
    		pd.put("marketing_type", "7");
    		//删除我的营销表里的折扣方式数据
			storepcMarketingService.deleteByType(pd);
			//2、我的营销数据表新增
//			String grantrule=pd.getString("grantrule");
    		pd.put("marketing_id", store_discountway_id);
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
	 * 件数
	 */
	@RequestMapping(value="/goNumberDiscount")
	public ModelAndView goNumberDiscount(){
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, "件数折扣");
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd=storepcDiscountwayService.findById(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("pd", pd);
		mv.setViewName("storepc/business_marketing10");
		return mv;
	}
	
	/**
	 * 金额阶梯
	 */
	@RequestMapping(value="/goAmountLadderDiscount")
	public ModelAndView goAmountLadderDiscount(){
		ModelAndView mv = this.getModelAndView();
		logBefore(logger, "金额阶梯");
		List<PageData> zkList=new ArrayList<PageData>();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pd.put("discount_type", "4");
			pd=storepcDiscountwayService.findById(pd);
			String[] content=null;
			if(pd != null){ 
				content=pd.getString("grantrule").split(",");
			}
 			for(int i=0;i<3; i++){
				PageData e=new PageData();
				if(content !=  null && content.length-1 >= i){
					int n1=content[i].indexOf("满");
					int n2=content[i].indexOf("元");
					int n3=content[i].indexOf("打");
					int n4=content[i].indexOf("折");
					e.put("money", content[i].subSequence(n1+1, n2));
					e.put("rate", content[i].subSequence(n3+1, n4));
				}else{
					e.put("money", "");
					e.put("rate", "0");
				}
				zkList.add(e);
				e=null;
			}
			mv.addObject("pd", pd);
			mv.addObject("zkList", zkList);
			zkList=null;
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("storepc/business_marketing11");
		return mv;
	}
	
	
	@Resource(name = "/categoryManageService")
	private CategoryManageService categoryManageService;
	
	
	/**
	 * 小类折扣显示列表
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public ModelAndView list(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			List<PageData>	varList = categoryManageService.findAllBig(pd);
			mv.addObject("varList", varList);
			pd=storepcDiscountwayService.findById(pd);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
 		mv.setViewName("storepc/business_marketing9");
		return mv;
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
			String store_discountway_id=pd.getString("store_discountway_id");
			pd.put("store_discountway_id", store_discountway_id);
			storepcDiscountwayService.updateTime(pd);
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
