package com.tianer.controller.memberapp.store_redpackets;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.tianer.service.business.goods_category.Goods_categoryService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.util.Distance;
import com.tianer.util.PageData;
import com.tianer.util.StringUtil;

/** 
 * 类名称：Store_redpacketsController
 * 创建人：刘耀耀
 * 创建时间：2016-06-20
 */
@Controller("appStore_redpacketsController")
@RequestMapping(value="/app_store_redpackets")
public class Store_redpacketsController extends BaseController {
	
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService store_redpacketsService;
	@Resource(name="goods_categoryService")
	private Goods_categoryService goods_categoryService;
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	

 	public DecimalFormat df2=TongYong.df2;
	
	/**
	 * app_store_redpackets/listIsRed.do
	 * 找商家发红包列表
	 * store_name 搜索框信息
	 * member_id 会员ID
	 * city_name 城市名称
 	 */
	@RequestMapping(value="/listIsRed")
	@ResponseBody
	public Object listIsRed(Page page) throws NoClassDefFoundError{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//会员的地址信息
 			double  longitude1=0;
			double latitude1=0;
			//判断是否定位了
			boolean isdingwei=true;
			if(appMemberService.findById(pd) == null || appMemberService.findById(pd).getString("longitude") == null || appMemberService.findById(pd).getString("longitude").equals("")){
				isdingwei=false;
			}else{
				longitude1=Double.parseDouble(appMemberService.findById(pd).getString("longitude"));//用户经度
	 			latitude1=Double.parseDouble(appMemberService.findById(pd).getString("latitude"));//用户纬度
			}
			String currentPage=pd.getString("page");
			if(currentPage == null ||currentPage.equals("") || currentPage.equals("0")){
				currentPage="1";
			}
			page.setCurrentPage(Integer.parseInt(currentPage));
 			page.setPd(pd);
 			List<PageData>	varList = store_redpacketsService.listIsRedlistPage(page);	//获得所有商家
 			for (PageData pageData : varList) {
 				if(pageData != null){
 					double longitude2 = 0;
 					double latitude2 = 0;
 					if(pageData.getString("longitude") != null && !pageData.getString("longitude").equals("") && pageData.getString("latitude") != null && !pageData.getString("latitude").equals("")){
 	 						longitude2=Double.parseDouble(pageData.getString("longitude"));//商家经度
 							latitude2=Double.parseDouble(pageData.getString("latitude"));//商家纬度
 	 				}
 					if(isdingwei){
 						double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
 						pageData.put("distance",  df2.format(overdistance/1000)+"");
 					}else{
 						pageData.put("distance", "--");
 					}
  	 			    //判断是否有红包
 					pageData.put("member_id", pd.getString("member_id"));
 					Map<String,Object> redmap=TongYong.storeAndMemberByRed(pageData);//包括会员id和商家id
					boolean flag=(boolean) redmap.get("flag");//判断是否还有符合的红包
					if(flag){
						pageData.put("haveRed", "1");
					}else{
						pageData.put("haveRed", "0");
					}
 				}
 			}
 			//-----------
 			if(varList.size()==0){
				map.put("data", "");
 			}else{
 				try {
  					//已排序分值排序近到远
	 				Collections.sort(varList,new Comparator<PageData>(){  
	 			        @Override
	 					public int compare(PageData  arg0, PageData arg1) {  
	 			            	String distance1=arg1.getString("distance");
	 			            	String distance2=arg0.getString("distance");
	 			            	int n1=distance1.length();
	 			            	int n2=distance2.length();
	 			            	if(n2 > n1){
	 			            		distance1=StringUtil.buZero(distance1, n2-n1);
	 			            	}else if(n1>n2){
	 			            		distance2=StringUtil.buZero(distance2, n1-n2);
	 			            	}
	 			                return distance2.compareTo(distance1);  
	 			            }  
	 			    });  
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.toString());
				}
	 			map.put("data", varList);
			}
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	
	/**
	 * 用户给商家发送消息发送红包
 	 */
	@RequestMapping(value="/messageForStoreRed")
	@ResponseBody
	public Object messageForStoreRed(){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="发送成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
  			if(appStoreService.findById(pd) != null){
 				//发送短信至商家
				TongYong.sendTuiSong(appStoreService.findById(pd).getString("store_id"), pd.getString("member_id"), "9", appStoreService.findById(pd).getString("store_id"), "1", "", "");
			} 
 		} catch(Exception e){
			result = "0";
			message="发送失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	/**
	 * app_store_redpackets/findAllStorelistPage.do  
	 * 
 	 * 分享链接的搜索商家地址
 	 * store_name
 	 * nowpage  页数(从1开始)分页:1页三十条
 	 * 
	 */
	@RequestMapping(value="/findAllStorelistPage")
	@ResponseBody
	public Object findAllStorelistPage(Page page){
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(pd.getString("store_name") != null && !pd.getString("store_name").trim().equals("")){
				if(pd.getString("nowpage") == null|| pd.getString("nowpage").equals("") || pd.getString("nowpage").equals("0")){
					pd.put("nowpage", "1");
				}
				String currentPage=pd.getString("nowpage");
	 			page.setCurrentPage(Integer.parseInt(currentPage));
				page.setShowCount(30);//每页页数
				page.setShowCount(100);
				page.setPd(pd);
				List<PageData> storeList= store_redpacketsService.findAllStorelistPage(page);
				map.put("data", storeList);
			}else{
				map.put("data", new ArrayList<PageData>());
			}
  		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	
	 
}
