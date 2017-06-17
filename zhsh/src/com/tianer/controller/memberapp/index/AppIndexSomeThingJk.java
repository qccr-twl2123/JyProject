package com.tianer.controller.memberapp.index;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.entity.Page;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.Distance;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.StringUtil;

/** 
 * 类名称：AppShouyeController
 * 创建人：魏汉文(首页所需的接口)
 * 创建时间：2016-06-20
 */
@Controller("appIndexSomeThingJk")
@RequestMapping(value="/app_index")
public class AppIndexSomeThingJk extends BaseController {
	
	
	/**
	 * 首页展示列表
	 * app_index/fLI.do
	 * 
	 * member_id 
	 * 经度
	 * 纬度
	 * 省市区名称，
 	 * @return 
	 * 
	 */
	@RequestMapping(value="/fLI")
	@ResponseBody
	public Object FirstLoginIndex(){
 		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1= new HashMap<String,Object>();
		List<PageData> redpackageList=new ArrayList<PageData>();//用来存储红包list
		List<PageData> allstoreList=new ArrayList<PageData>();//用来存储商家List
 		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			
			
		} catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
		return map;
	}
	
	 
	
	/**
	 * 获得所有商家/商品
	 * app_index/nSySxSG.do
	 * 
	 * city_file_sort_id 分类类别ID
	 * sort_type  类别（1-大类，2-小类）
 	 * px_number:  (商家) 1-智能排序（综合分值为基数的），2-距离近到远，3-人气高到低，4-积分率从高到底，5-销售量从高到底
 	 * 			   (商品) 1-销售量高到底（默认）
 	 * change_type: 1-商家/2-商品（默认商家）
 	 * nowpage 分页参数（默认从1开始）
 	 */
	@RequestMapping(value="/nSySxSG")
	@ResponseBody
	public Object newShouyeShaiXuanStoreOrGoods(Page page) throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		Map<String,Object> map1 = new HashMap<String,Object>();
		List<PageData> allstoreList=new ArrayList<PageData>();//用来存储商家List
		List<PageData> allgoodsList=new ArrayList<PageData>();//用来存储商品List
 		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			String nowpage=(pd.getString("nowpage") == null?"1":pd.getString("nowpage"));
			page.setCurrentPage(Integer.parseInt(nowpage));
			if(pd.getString("change_type") == null || pd.getString("change_type").equals("1")){
				allstoreList=ServiceHelper.getAppStoreService().getStorelistPage(page);
 			}else{
 				allgoodsList=ServiceHelper.getAppGoodsService().getGoodslistPage(page);
			}
			map1.put("storeList", allstoreList);
			map1.put("allgoodsList", allgoodsList);
		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
  		return map;
	}
	
	 
	
	
	
}
