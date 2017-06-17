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
	 * 省市区名称
	 * change_type 1-商家（默认），2-商品
 	 * @return 
	 * 
	 */
	@RequestMapping(value="/fLI")
	@ResponseBody
	public Object FirstLoginIndex(Page page){
 		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1= new HashMap<String,Object>();
    	String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			//获取当省市区的name获取城市档案ID 
	        PageData citypd=ServiceHelper.getAppCity_fileService().findIdByPcd(pd);
	        if(citypd != null ){
	        	//获取轮播图
	        	citypd.put("advert_type", "1");
				List<PageData> advertList=ServiceHelper.getApp_advertService().listAllAdvert(citypd);
				map1.put("advertList", advertList);
				advertList=null;
 				//获取城市一级分类数据
				pd.put("sort_parent_id", "0");
				pd.put("sort_type", "1");
				List<PageData>	cityList = ServiceHelper.getAppCity_fileService().listAllCitySort(pd);//列出City_file列表
 				map1.put("cityList", cityList);
 	        	pd.remove("sort_parent_id");
 	        	pd.remove("sort_type");
   	        }
 		   map1=getStoreOrGoods(map1, pd, page);//获取商家集合
	       map1=getCitySortList(map1, pd);//获取大小类集合
	       map1=getShaiXuan(map1,pd.getString("change_type"));//获取筛选集合
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
 	 * storepx_number:  (商家) 1-智能排序（综合分值为基数的），2-距离近到远，3-人气高到低，4-积分率从高到底，5-销售量从高到底
 	 * goodspx_number	(商品) 1-销售量高到底（默认）  2-价格从高到底，3-价格从低到高 
 	 * change_type: 1-商家/2-商品（默认商家）
 	 * nowpage 分页参数（默认从1开始）
 	 */
	@RequestMapping(value="/nSySxSG")
	@ResponseBody
	public Object newShouyeShaiXuanStoreOrGoods(Page page) throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		Map<String,Object> map1 = new HashMap<String,Object>();
  		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			map1=getStoreOrGoods(map1, pd, page);
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
	
	/**
	 * 获取商家/商品的集合
	 * @param map
	 * @return
	 */
	public static Map<String,Object> getStoreOrGoods(Map<String,Object> map1,PageData pd,Page page){
		List<PageData> allstoreList=new ArrayList<PageData>();//用来存储商家List
		List<PageData> allgoodsList=new ArrayList<PageData>();//用来存储商品List
		try { 
 			String nowpage=(pd.getString("nowpage") == null?"1":pd.getString("nowpage"));
			page.setCurrentPage(Integer.parseInt(nowpage));
			if(pd.getString("change_type") == null || pd.getString("change_type").equals("1")){
				allstoreList=ServiceHelper.getAppStoreService().getStorelistPage(page);
 			}else{
 				allgoodsList=ServiceHelper.getAppGoodsService().getGoodslistPage(page);
			}
			map1.put("storeList", allstoreList);
			map1.put("allgoodsList", allgoodsList);
 		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 		return map1;
	}
	/**
	 * 获取排序的集合
	 * @param map
	 * @return
	 */
	public static Map<String,Object> getShaiXuan(Map<String,Object> map1,String change_type){
		try {
			//设置排序集合
	        List<PageData> paixuList=new ArrayList<PageData>();
	        if(change_type == null || change_type.equals("1")){
	        	PageData paixupd=new PageData();
	 	        paixupd.put("storepx_number", "1");
	 	        paixupd.put("name", "智能排序");
		        paixuList.add(paixupd);
		        paixupd.clear();
		        paixupd.put("storepx_number", "2");
		        paixupd.put("name", "距离从近到远");
		        paixuList.add(paixupd);
		        paixupd.clear();
		        paixupd.put("storepx_number", "3");
		        paixupd.put("name", "人气从高到低");
		        paixuList.add(paixupd);
		        paixupd.clear();
		        paixupd.put("storepx_number", "4");
		        paixupd.put("name", "积分率从高到低");
		        paixuList.add(paixupd);
		        paixupd.clear();
		        paixupd.put("storepx_number", "5");
		        paixupd.put("name", "销售量从高到低");
		        paixuList.add(paixupd);
		        paixupd=null;
		        map1.put("paixuList", paixuList);
		        paixuList=null;
	        }else{
	        	PageData paixupd=new PageData();
	 	        paixupd.put("goodspx_number", "1");
	 	        paixupd.put("name", "销售数量");
		        paixuList.add(paixupd);
		        paixupd.clear();
		        paixupd.put("goodspx_number", "2");
		        paixupd.put("name", "价格从高到底");
		        paixuList.add(paixupd);
		        paixupd.clear();
		        paixupd.put("goodspx_number", "3");
		        paixupd.put("name", "价格从低到高");
		        paixuList.add(paixupd);
			    paixupd=null;
			    map1.put("paixuList", paixuList);
		        paixuList=null;
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 		return map1;
	}
	/**
	 * 获取大小类的集合
	 * @param map
	 * @return
	 */
	public static Map<String,Object> getCitySortList(Map<String,Object> map1,PageData pd){
		try {
   			//获得一级分类
			pd.put("sort_parent_id", "0");
			pd.put("sort_type", "1");
			List<PageData> firstList=ServiceHelper.getAppCity_fileService().listAllCitySort(pd);
			pd.remove("sort_parent_id");
			pd.remove("sort_type");
			for(PageData e : firstList){
	 				//获得二级分类
					pd.put("sort_parent_id", e.getString("city_file_sort_id"));
					pd.put("sort_type", "2");
					List<PageData> twoList=ServiceHelper.getAppCity_fileService().listAllCitySort(pd);
					PageData flpd = new PageData();
					flpd.put("sort_name", "全部");
					flpd.put("city_file_sort_id", e.getString("city_file_sort_id"));
					flpd.put("sort_type", "1");
 					List<PageData> slist=new ArrayList<PageData>();
					twoList.add(flpd);
					for(PageData e1 : twoList){
						slist.add(e1);
					}
	  				e.put("twoList", slist);
	  				twoList=null;
	  				slist=null;
	  				flpd=null;
			}
			PageData flpd1 = new PageData();
			flpd1.put("sort_name", "全部分类");
			flpd1.put("city_file_sort_id", "");
			flpd1.put("sort_type", "0");
			flpd1.put("twoList", new ArrayList<PageData>());
			List<PageData> sslist=new ArrayList<PageData>();
 			sslist.add(flpd1);
 			for(PageData e1 : firstList){
 				sslist.add(e1);
			}
			map1.put("data", sslist);
			sslist=null;
			firstList=null;
			flpd1=null;
			pd=null;
 		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
 		return map1;
	}
	
	
	
}
