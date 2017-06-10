package com.tianer.controller.storepc.store_way;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.service.storepc.store_way.Storepc_wayService;
import com.tianer.util.AppUtil;
import com.tianer.util.PageData;


/** 
 * 
* 类名称：Storepc_scorewayController   
* 类描述：   消费/支付
* 创建人：邢江涛  
* 创建时间：2016年6月14日 
 */
@Controller("storeStorepc_wayController")
@RequestMapping(value = "/storepc_way")
public class Storepc_wayController extends BaseController{
	
	
	@Resource(name = "storepc_wayService")
	private Storepc_wayService storepcWayService;
	
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(){
		Map<String , Object> map = new HashMap<String, Object>();
		String result = "01";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			pd.put("store_way_id",BaseController.getTimeID());	//主键
			storepcWayService.save(pd);
			map.put("result", result);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	
}
