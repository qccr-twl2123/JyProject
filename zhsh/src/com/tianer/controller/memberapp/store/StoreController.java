package com.tianer.controller.memberapp.store;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.entity.html.HtmlUser;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppOrderService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.memberapp.AppStorepc_marketingService;
import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.service.storepc.stotr.StorepcService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;

/** 
 * 类名称：StoreController
 * 创建人：刘耀耀
 * 创建时间：2016-06-217
 */
@Controller("appStoreController")
@RequestMapping(value="/app_store")
public class StoreController extends BaseController {
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	
	/**
	 * 成为商店的VIP会员
	 * 魏汉文20160629
	 */
	@RequestMapping(value="/getVipForStore")
	@ResponseBody
	public Object getVipForStore() {
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="领取VIP成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//判断是否为H5页面
			if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
				pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
 			}
			if(pd.getString("member_id") == null || pd.getString("member_id").equals("")){
  					map.put("result", "0");
					map.put("message", "请前往登陆");
					map.put("data", "");
					return map;
 			}else{
				List<PageData> vipList=appMemberService.getStoreVipById(pd);
				//判断是否已是当前商店的会员
				if(vipList.size() != 0){
	 				message="你已经是该商店的VIP";
				}else{
					//获取商店的VIP图片地址
					PageData e=new PageData();
					e= appStoreService.findVipImage(pd);
					if(e != null){
						pd.put("font_color", e.getString("font_color"));
						pd.put("image_url", e.getString("image_url"));
						appMemberService.addStoreVip(pd);
					}
 				}
			}
 		} catch (Exception e) {
			result="0";
 			message="系统错误";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		return AppUtil.returnObject(pd, map);
	}
	
	
	@Resource(name="appStorepc_marketingService")
	private AppStorepc_marketingService appStorepc_marketingService;
	@Resource(name="storeManageService")
	private StoreManageService storeManageService;
	
	/**
	 * 商家详情
	 * app_store/findById.do?store_id=33391593&member_id=
	 *  
 	 * daoliurecord_id
	 * member_id
	 * store_id
	 * 
	 */
	@RequestMapping(value="/findById")
	@ResponseBody
	public Object findById(){
 		Map<String,Object> map = new HashMap<String,Object>();
   		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
 		try{
				pd = this.getPageData();
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
  				}
				//处理导流
 				String member_id=pd.getString("member_id");
  	 		    pd= appStoreService.findByIdOne(pd);	//列出Store列表
	 		    if(pd== null ){
	 		    	map.put("result", "0");
	 				map.put("message","商家信息不存在" );
	 				map.put("data", "");
	 				return map;
	 		    }
  	 		    //获取支付方式
	 		    List<PageData> way_typeList=new ArrayList<PageData>();
	 		    List<PageData> way_statusList=new ArrayList<PageData>();
	 		    PageData waypd=appStoreService.getPayWayById(pd);
	 		    if(waypd != null){
	 		    	 String[] str1=waypd.getString("way_type").split(",");
	 		    	 String pay_type="";
	 		    	 for(int i=0;i<str1.length ;i++){
	 		    		if(!str1[i].equals("0")){
	 		    				 pay_type+= Const.paytype[Integer.parseInt(str1[i])]+",";
	 		    		}
	  	 		     }
	 		    	 if(!pay_type.equals("")){
	 		    	 	pay_type=pay_type.substring(0, pay_type.length()-1);
	 		    	 }
	 		    	 pd.put("paytype", pay_type);
	 		    	 //新的
	 		    	 for(int i=0;i<str1.length ;i++){
		 		    		 PageData numberpd=new PageData();
		 		    		 numberpd.put("way_type", str1[i]);
		 		    		 way_typeList.add(numberpd);
		 		    		 numberpd=null;
		  	 		 }
 	 		    	 String pay_way=waypd.getString("way_status");
	 	 		     String[] str=pay_way.split(",");
	 	 		     pay_way="";
	 	 		     for(int i=0;i<str.length ;i++){
	 	 		    	if(!str[i].equals("0")){
	 	 		    		pay_way+= Const.payway[ Integer.parseInt(str[i])]+",";
	  	 		    	}
	  	 		     }
	 	 		     if(!pay_way.equals("")){
	 	 		    	 pay_way=pay_way.substring(0, pay_way.length()-1);
			    	 }
	 	 		     pd.put("pay_way", pay_way);
	 	 		     //新的
 	 		    	 for(int i=0;i<str.length ;i++){
		 		    		 PageData numberpd=new PageData();
		 		    		 numberpd.put("way_status", str[i]);
		 		    		 way_statusList.add(numberpd);
		 		    		 numberpd=null;
		  	 		 }
 	 		    	 pd.put("way_typeList",way_typeList);
	 		    	 pd.put("way_statusList", way_statusList);
	 		    }else{
	 		    	 pd.put("way_typeList",way_typeList);
	 		    	 pd.put("way_statusList", way_statusList);
	 		    }
	 		   way_typeList=null;way_statusList=null;
 	 		   //获取商家的大小图片
	 			List<PageData> imageList=storeManageService.findImage(pd);
	 			List<PageData> one=new ArrayList<PageData>();
	 			List<PageData> three=new ArrayList<PageData>();
				if(imageList.size() != 0){
					PageData imgpd=imageList.get(0);
					String address=imgpd.getString("address");
					if(address != null){
						String[] addressstr=address.split(",");
						for(int i=0 ; i<addressstr.length ;i++){
							PageData smallpd=new PageData();
							smallpd.put("small_url", addressstr[i]);
							one.add(smallpd);
		 				}
					}
 					pd.put("smallList", one);
 					one=null;
					String address1=imgpd.getString("address1");
					if(address1 != null && !address1.equals("")){
						String[] addre1=address1.split(",");
						for(int i=0 ; i<addre1.length ;i++){
							String[] str=addre1[i].split("@");
							if(!str[0].equals("img/base_tp.png") && !str[0].equals("") && str.length>0 ){
									PageData adpd=new PageData();
									adpd.put("image_url", str[0]);
									if(str.length == 2){
										adpd.put("text", str[1]);
									}else{
										adpd.put("text", "");
									}
 									three.add(adpd);
 									adpd=null;
							}
	 					}
 					}
					pd.put("imageList", three);
					three=null;
				}
				imageList=null;
	   			//获取营销规则
	 		    PageData e = new PageData();
				List<PageData> marketlist=appStorepc_marketingService.listAllById(pd);
				pd.put("marketlist", marketlist);
				//统计最少最高的折扣/积分率
 				double minjfrate=0;
				String minjfratestr="";
				double minzkrate=0;
				for(PageData e2 : marketlist){
					/*
					 * 1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数,6-积分，7折扣
					 */
					String marketing_type=e2.getString("marketing_type");
					String marketing_id=e2.getString("marketing_id");
					String grantrule=e2.getString("grantrule");
					if(marketing_type.equals("1")){
 					}else if(marketing_type.equals("2")){
 					}else if(marketing_type.equals("3")){
 						if(grantrule.contains("折")){
 							String number2=grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折"));
 	 	 					double zk1=Double.parseDouble(number2);
 	 	 					if(zk1 < minzkrate || minzkrate==0){
 	 	 						minzkrate=zk1;
 	 	 					}
 						} 
 					}else if(marketing_type.equals("4")){
 					}else if(marketing_type.equals("5")){
 					}else if(marketing_type.equals("6")){
 									e2.put("store_scoreway_id", marketing_id);
									PageData jfpd=new PageData();
									jfpd=appStorepc_marketingService.getJfById(e2);
									double jf1=0;
									if(jfpd != null){
			 		  	 				String jftype=jfpd.getString("change_type");
			 		  	 				String jfgrantrule=jfpd.getString("grantrule");
					 	 				if(jftype.equals("1")){
								 	 					String zdrate=jfpd.getString("oneback_rate");
								 	 					jf1=Double.parseDouble(zdrate);
								 	 					if(jf1 < minjfrate || minjfrate==0){
								 	 						minjfrate=jf1;
								 	 					}
								 	 					minjfratestr=minjfrate+"%";
					 	 				}else if(jftype.equals("5")){//满多少增多少
						 	 					String[] str=jfgrantrule.split(",");
					 	 							for(int i=0; i<str.length ;i++){
					 	 									String content=str[i];
						  		  							String number2=content.substring(content.indexOf("送")+1, content.lastIndexOf("%"));
					  		  								jf1=Double.parseDouble(number2);
	  		 					  		  					if(jf1 < minjfrate || minjfrate==0){
									 	 						minjfrate=jf1;
									 	 					}
					 	 							}
					 	 							minjfratestr=minjfrate+"%";
			 		 	 				}else if(jftype.equals("4")){
 								 	 					minjfratestr=jfgrantrule;
					 	 				}else if(jftype.equals("3")){
					 	 					String mdjf=jfpd.getString("threemin_rate");
					 	 					jf1=Double.parseDouble(mdjf);
					 	 					if(jf1 < minjfrate || minjfrate==0){
					 	 						minjfrate=jf1;
					 	 					}
					 	 					minjfratestr=minjfrate+"%";
					 	 				}else if(jftype.equals("2")){//满多少增多少
					 	 					String number2=jfgrantrule.substring(jfgrantrule.indexOf("：")+1, jfgrantrule.lastIndexOf("-"));
					 	 					jf1=Double.parseDouble(number2);
					 	 					if(jf1 < minjfrate || minjfrate==0){
					 	 						minjfrate=jf1;
					 	 					}
					 	 					minjfratestr=minjfrate+"%";
					 	 				}
									}
									jfpd=null;
						}else if(marketing_type.equals("7")){
 								e.put("store_discountway_id", marketing_id);
								//获取所有启用的折扣
 								PageData zkpd=appStorepc_marketingService.getZKById(e);
								if(zkpd != null){
										String zkgrantrule=zkpd.getString("grantrule");
 									    double zk1=0;
										if(zkpd.getString("discount_type").equals("1")){//整店折扣
	   											zk1=Double.parseDouble(zkpd.getString("onealldiscount_rate"));
	   			 								if(minzkrate > zk1 || minzkrate==0 ){
	   			 									 minzkrate=zk1;
	   			 								}
										}else if(zkpd.getString("discount_type").equals("4")){//满多少折多少
													String[] str=zkgrantrule.split(",");
					 	 							for(int i=0; i<str.length ;i++){ 
					  		  								 String number2=str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折"));
					  		 								 zk1=Double.parseDouble(number2);
				  		 								 if(minzkrate > zk1 || minzkrate==0){
			   			 									 minzkrate=zk1;
			   			 								 }
					 	 							}
										}else if(zkpd.getString("discount_type").equals("2")){//分类折扣
											if(zkpd.getString("twoproductdiscount_rate") != null){
												String[] minzks=zkpd.getString("twoproductdiscount_rate").split("-");
												if(minzks.length >0){
													zk1=Double.parseDouble(minzks[0]);
					 								if(minzkrate > zk1 || minzkrate==0){
					 									 minzkrate=zk1;
					 								}
												}
 											}
 									   }
								}
								zkpd=null;
 					}
							
				}
				if(minjfratestr.equals("")){
					minjfratestr="0";
				}
				marketlist=null;
  	 			pd.put("minjfrate",  minjfratestr);
				pd.put("minzkrate",  minzkrate+"折");
				pd.put("member_id", member_id);
 	 		    pd.put("transaction_times",ServiceHelper.getStorepc_wealthhistoryService().getallCountOrder(pd));//所有交易笔数
 				if(appStoreService.getCollectionById(pd) == null){
					pd.put("iscollect", "0");
				}else{
					pd.put("iscollect", "1");
				}
 				//判断是否已是当前商店的会员
				if(appMemberService.getStoreVipById(pd).size() != 0){
					pd.put("vip", "1");
				}else{
					pd.put("vip", "0");
				}
				//导流列表
		 		pd.put("daoLiuStoreList", TongYong.daoliuList(pd));
				//========
		 		if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
		 			pd.remove("store_id");
					pd.remove("member_id");
				}
 		} catch(Exception e1){
			result = "0";
			message="系统错误";
			logger.error(e1.toString(), e1);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	
	
//	/**
//	 * 点赞
//   	 */
//	@RequestMapping(value="/praise")
//	@ResponseBody
//	public Object praise() {
//		logBefore(logger, "点赞");
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="成功点赞";
//		PageData pd = new PageData();
//		try {
//			pd=this.getPageData();
//			String number=appStoreService.findNumber(pd);//获取商家点赞次数
//			List<PageData> str=appStoreService.findZan(pd);
//			if(str.size()!=0){
//				result="1";
//				message="您已经点过赞了";
//			}else{
//				pd.put("zan_id", pd.getString("member_id"));
//				pd.put("be_zan_id", pd.getString("store_id"));
//				appStoreService.saveZan(pd);
// 				pd.put("zan_times", Integer.parseInt(number)+1+"");
//				appStoreService.edit(pd);
//			}
// 		} catch (Exception e) {
//			result="0";
// 			message="点赞失败";
//			e.printStackTrace();
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");
//		return map;
//	}

	@Resource(name="appOrderService")
	private AppOrderService appOrderService;
	

	
	/**
	 * 取消收藏/新增收藏:store_id,member_id
 	 *	         /app_store/iscolloctByMS.do?member_id=&store_id=
	 */
	@RequestMapping(value="/iscolloctByMS")
	@ResponseBody
	public Object iscolloctByMS() {
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="操作成功";
		PageData pd = new PageData();
		String collect_id="";
		try {
				pd=this.getPageData();	
				//判断是否为H5页面
				if(SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER) != null){
					pd.put("member_id", ((HtmlUser)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_H5_USER)).getMember_id());
 				}
				//判断是否有当前会员
	 			if(appMemberService.findById(pd) == null){
					map.put("result", "0");
					map.put("message", "请前往登陆");
					map.put("data", "");
					return map;
				}
	 			//
	 			PageData  collectpd=appStoreService.getCollectionById(pd);
	 			if(collectpd != null ){
	  					appStoreService.deleteCollect(pd);
	  					appStoreService.editCollectNumber(pd);
	  					message="取消收藏成功";
	  					map.put("data", "0");
	 			}else{
						collect_id= BaseController.getTimeID();
						pd.put("collect_id",collect_id);
		 				appStoreService.saveCollect(pd);
		 				appStoreService.editCollectNumber(pd);
		 				message="收藏成功";
		 				map.put("data", "1");
				}
	 	} catch (Exception e) {
				result="0";
	 			message="系统失败";
				e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
 		return map;
	}
	
	
	
//	/**
//	 * 新增收藏:store_id,member_id
//	 *魏汉文20160629
//	 */
//	@RequestMapping(value="/collocation")
//	@ResponseBody
//	public Object collocation() {
//		logBefore(logger, "收藏");
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="成功收藏";
//		PageData pd = new PageData();
//		String collect_id="";
//		try {
//			pd=this.getPageData();	
//			if(pd.getString("member_id") == null || pd.getString("member_id").equals("")){
//				map.put("result", "0");
//				map.put("message", "请前往登陆");
//				map.put("data", "");
//				return AppUtil.returnObject(pd, map);
//			}
// 			PageData  collectpd=appStoreService.getCollectionById(pd);
// 			if(collectpd != null ){
//				result="0";
//				collect_id=collectpd.getString("collect_id");
//				/*message="您已经收藏了";*/
//			}else{
//				collect_id= this.get32UUID();
//				pd.put("collect_id",collect_id);
// 				appStoreService.saveCollect(pd);
//				pd=appStoreService.findById(pd);
//				String collection_times=pd.getString("collection_times");
// 				pd.put("collection_times", Integer.parseInt(collection_times)+1+"");
//				appStoreService.edit(pd);
//			}
// 		} catch (Exception e) {
//			result="0";
// 			message="系统失败";
//			e.printStackTrace();
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", collect_id);
//		return map;
//	}
//	
//	/**
//	 * 取消收藏:store_id,collection
//	 *魏汉文20160629
//	 */
//	@RequestMapping(value="/deleteCollocation")
//	@ResponseBody
//	public Object deleteCollocation() {
//		logBefore(logger, "收藏");
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="取消收藏成功";
//		PageData pd = new PageData();
//		try {
//			pd=this.getPageData();
//			System.out.println(pd);
//			if(pd.getString("collect_id") == null || pd.getString("collect_id").equals("")){
//				result="0";
//				message="collect_id不能为空";
//			}else{
//				PageData collectpd=appStoreService.findCollectId(pd);
//				PageData spd=new PageData();
//				spd=appStoreService.findById(collectpd);
// 				if(spd==null){
// 					message="商家ID不能为空";
// 					result="0";
// 				}else{
// 					appStoreService.deleteCollect(pd);//取消收藏
// 					String collection_times=spd.getString("collection_times");
// 					spd.put("collection_times", Integer.parseInt(collection_times)-1+"");
// 					appStoreService.edit(spd);
// 				}
// 			}
//  		} catch (Exception e) {
//			result="0";
// 			message="取消收藏失败";
//			e.printStackTrace();
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");
//		return map;
//	}
	
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService appStore_redpacketsService;
	@Resource(name="storepc_wealthhistoryService")
	private Storepc_wealthhistoryService storepc_wealthhistoryService;
	
	/**
	 * 收藏列表：member_id
	 *魏汉文20160629
	 */
	@RequestMapping(value="/listCollectionById")
	@ResponseBody
	public Object listCollectionById() {
//		logBefore(logger, "收藏");
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> storeList=new ArrayList<PageData>();
  		String result = "1";
		String message="收藏列表";
		PageData pd = new PageData();
		try {
				pd=this.getPageData();
				double  longitude1=0;//用户经度
				double latitude1=0;//用户纬度
				//判断是否定位了
				pd=appMemberService.findById(pd);
				if(pd == null){
					map.put("result", "0");
					map.put("message", "缺少必要参数");
					map.put("data", "");
					return map;
				}
 				boolean isdingwei=true;
				if(pd.getString("longitude") == null || pd.getString("longitude").equals("") || pd.getString("longitude").equals("0.000000")  || pd.getString("latitude") == null || pd.getString("latitude").equals("") || pd.getString("latitude").equals("0.000000") ){
					isdingwei=false;
				}else{
					longitude1=Double.parseDouble(pd.getString("longitude"));
		 			latitude1=Double.parseDouble(pd.getString("latitude"));
				}
 				List<PageData> allstoreList=appStoreService.listCollectionById(pd);
				for(PageData e :  allstoreList){
 							String collect_id=e.getString("collect_id");
 							String distance=e.getString("distance");
 						    e=appStoreService.findById(e);
						    if(e == null){
 								continue;
						    }
						    if(!isdingwei){
						    	e.put("distance", e.getString("distance")+"km");
							}else{
								if(Double.parseDouble(distance )-Const.maxjuli > 0 ){
									e.put("distance", Const.maxjuli+"km+");
								}else{
									e.put("distance", distance+"km");
								}
							}
						    //判断是否开通zk
 							if(appStorepc_marketingService.getZKById(e) == null){
								e.put("zkstatus", "0");
							}else{
								e.put("zkstatus", "1");
							}
 							//获取营销规则
							List<PageData> marketlist=appStorepc_marketingService.listAllMarketing(e);
							e.put("marketlist", marketlist);
 							e.put("collect_id", collect_id);
 							//判断是否有红包
							pd.put("store_id", e.getString("store_id"));
							Map<String,Object> redmap=TongYong.storeAndMemberByRed(pd);//包括会员id和商家id
							boolean flag=(boolean) redmap.get("flag");//判断是否还有符合的红包
							if(flag){
								e.put("haveRed", "1");
							}else{
								e.put("haveRed", "0");
							}
							storeList.add(e);
 				}
  		} catch (Exception e) {
			result="0";
 			message="成功收藏失败";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", storeList);
 		return map;
	}
	
	
	/**
	 * 消费过的商家列表
	 * 魏汉文20160630
	 */
	@RequestMapping(value="/saleOrderForStoreByMem")
	@ResponseBody
	public Object saleOrderForStoreByMem(){
//		logBefore(logger, "消费过的商家列表");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			List<PageData> storeList=appStoreService.saleOrderForStoreByMem(pd);
			if(storeList.size() == 0){
				map.put("data","");
			}else{
				map.put("data",storeList);
			}
 		} catch(Exception e){
			result="0";
			message="获取失败";
			map.put("data","");
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
 		return AppUtil.returnObject(pd, map);
	}
	

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
		

	/**
	 * 获取服务商
	 */
	@RequestMapping(value="/getSp")
	@ResponseBody
	public Object getSp() {
//		logBefore(logger, " 获取服务商");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try {
			pd=this.getPageData();
			pd= appStoreService.findByIdOne(pd);	//列出Store列表
 		} catch (Exception e) {
			result="0";
 			message="获取失败";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	
	/**
	 * 注册商家
	 */
	@RequestMapping(value="/saveStore")
	@ResponseBody
	public Object saveStore() {
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
 			pd=TongYong.saveStore(pd);
  		} catch (Exception e) {
			result="0";
			message="获取失败";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		return map;
	}
	
	
	@Resource(name="storepcService")
	private StorepcService storepcService;
 
	
	
	
}
