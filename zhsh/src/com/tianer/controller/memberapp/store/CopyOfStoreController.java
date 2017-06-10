//package com.tianer.controller.app.store;
//
//
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tianer.controller.base.BaseController;
//import com.tianer.entity.Page;
//import com.tianer.util.AppUtil;
//import com.tianer.util.Const;
//import com.tianer.util.Distance;
//import com.tianer.util.FileUpload;
//import com.tianer.util.PageData;
//import com.tianer.util.SmsUtil;
//import com.tianer.util.ErWerMa.OneEr;
//import com.tianer.service.app.AppMemberService;
//import com.tianer.service.app.AppOrderService;
//import com.tianer.service.app.AppPay_historyService;
//import com.tianer.service.app.AppStoreService;
//import com.tianer.service.app.AppStore_redpacketsService;
//import com.tianer.service.app.AppStorepc_marketingService;
//import com.tianer.service.storepc.liangqin.basemanage.StoreManageService;
//import com.tianer.service.storepc.stotr.StorepcService;
//
///** 
// * 类名称：StoreController
// * 创建人：刘耀耀
// * 创建时间：2016-06-217
// */
//@Controller("appStoreController")
//@RequestMapping(value="/app_store")
//public class CopyOfStoreController extends BaseController {
//	
//	@Resource(name="appStoreService")
//	private AppStoreService appStoreService;
//	@Resource(name="appMemberService")
//	private AppMemberService appMemberService;
//	
//	
//	/**
//	 * 成为商店的VIP会员
//	 * 魏汉文20160629
//	 */
//	@RequestMapping(value="/getVipForStore")
//	@ResponseBody
//	public Object edit() {
//		logBefore(logger, "修改成为会员");
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="领取VIP成功";
//		PageData pd = new PageData();
//		try {
//			pd = this.getPageData();
//			if(pd.getString("member_id") == null || pd.getString("member_id").equals("")){
//  					map.put("result", "0");
//					map.put("message", "请前往登陆");
//					map.put("data", "");
//					return map;
// 			}else{
//				List<PageData> vipList=appMemberService.getStoreVipById(pd);
//				//判断是否已是当前商店的会员
//				if(vipList.size() != 0){
//	 				message="你已经是该商店的VIP";
//				}else{
//					//获取商店的VIP图片地址
//					PageData e=new PageData();
//					e= appStoreService.findVipImage(pd);
//					if(e != null){
//						pd.put("font_color", e.getString("font_color"));
//						pd.put("image_url", e.getString("image_url"));
//						appMemberService.addStoreVip(pd);
//					}
// 				}
//			}
// 		} catch (Exception e) {
//			result="0";
// 			message="系统错误";
//			e.printStackTrace();
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	
//	@Resource(name="appStorepc_marketingService")
//	private AppStorepc_marketingService appStorepc_marketingService;
//	@Resource(name="storeManageService")
//	private StoreManageService storeManageService;
//	
//	/**
//	 * 商家详情
//	 * 魏汉文20160629
//	 */
//	@RequestMapping(value="/findById")
//	@ResponseBody
//	public Object findById(){
//		logBefore(logger, "列表Store");
//		Map<String,Object> map = new HashMap<String,Object>();
//		List<PageData> three=new ArrayList<PageData>();
// 		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
// 		try{
//				pd = this.getPageData();
//				String member_id=pd.getString("member_id");
//	 		    pd= appStoreService.findByIdOne(pd);	//列出Store列表
//	 		    if(pd== null ){
//	 		    	map.put("result", "0");
//	 				map.put("message","商家信息不存在" );
//	 				map.put("data", "");
//	 				return map;
//	 		    }
//	 		    //获取支付方式
//	 		    PageData waypd=new PageData();
//	 		    waypd=appStoreService.getPayWayById(pd);
//	 		    if(waypd != null){
//	 		    	 String[] str1=waypd.getString("way_type").split(",");
//	 		    	 String pay_type="";
//	 		    	for(int i=0;i<str1.length ;i++){
//	 		    		if(!str1[i].equals("0")){
//	 		    				 pay_type+= Const.paytype[Integer.parseInt(str1[i])]+",";
//	 		    		}
//	  	 		    }
//	 		    	if(!pay_type.equals("")){
//	 		    		pay_type=pay_type.substring(0, pay_type.length()-1);
//	 		    	}
//	 		    	 pd.put("paytype", pay_type);
//	 		    	 String pay_way=waypd.getString("way_status");
//	 	 		    String[] str=pay_way.split(",");
//	 	 		    pay_way="";
//	 	 		    for(int i=0;i<str.length ;i++){
//	 	 		    	if(!str[i].equals("0")){
//	 	 		    		pay_way+= Const.payway[ Integer.parseInt(str[i])]+",";
//	  	 		    	}
//	  	 		    }
//	 	 		  if(!pay_way.equals("")){
//	 	 			  pay_way=pay_way.substring(0, pay_way.length()-1);
//			    	}
//	 	 		    pd.put("pay_way", pay_way);
//	 		    }else{
//	 		    	 pd.put("paytype", "");
//	 		    	 pd.put("pay_way", "");
//	 		    }
//	 		 //获取商家的大小图片
//	 			List<PageData> imageList=storeManageService.findImage(pd);
//				if(imageList.size() != 0){
//					PageData imgpd=new PageData();
//					imgpd=imageList.get(0);
//					String address1=imgpd.getString("address1");
//					if(address1 != null && !address1.equals("")){
//						String[] addre1=address1.split(",");
//						for(int i=0 ; i<addre1.length ;i++){
//							String[] str=addre1[i].split("@");
//							if(!str[0].equals("img/base_tp.png") && !str[0].equals("") && str.length>0 ){
//									PageData adpd=new PageData();
//									adpd.put("image_url", str[0]);
//									if(str.length == 2){
//										adpd.put("text", str[1]);
//									}else{
//										adpd.put("text", "");
//									}
// 									three.add(adpd);
// 									adpd=null;
//							}
//	 					}
//						pd.put("imageList", three);
//					}
//				}
//	   			//获取营销规则
//	 		    PageData e = new PageData();
//				List<PageData> marketlist=appStorepc_marketingService.listAllById(pd);
//				pd.put("marketlist", marketlist);
////				String add="";
////				String reduce="";
////				String time="";
////				String n="";
////				String number="";
////				String score="";
////				String zk="";
//				double minjfrate=0;
//				String minjfratestr="";
//				double minzkrate=0;
//				for(PageData e2 : marketlist){
//					/*
//					 * 1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数,6-积分，7折扣
//					 */
//					String marketing_type=e2.getString("marketing_type");
//					String marketing_id=e2.getString("marketing_id");
//					String grantrule=e2.getString("grantrule");
//					if(marketing_type.equals("1")){
////						add+=grantrule+",";
//					}else if(marketing_type.equals("2")){
////						reduce+=grantrule+",";
//					}else if(marketing_type.equals("3")){
////						time+=grantrule+",";
//						String number2=grantrule.substring(grantrule.indexOf("打")+1, grantrule.lastIndexOf("折"));
// 	 					double zk1=Double.parseDouble(number2);
// 	 					if(zk1 < minzkrate || minzkrate==0){
// 	 						minzkrate=zk1;
// 	 					}
//					}else if(marketing_type.equals("4")){
////						n+=grantrule+",";
//					}else if(marketing_type.equals("5")){
////						number+=grantrule+",";
//					}else if(marketing_type.equals("6")){
////									score+=grantrule+",";
//									e2.put("store_scoreway_id", marketing_id);
//									PageData jfpd=new PageData();
//									jfpd=appStorepc_marketingService.getJfById(e2);
//									double jf1=0;
//									if(jfpd != null){
//			 		  	 				String jftype=jfpd.getString("change_type");
//			 		  	 				String jfgrantrule=jfpd.getString("grantrule");
//					 	 				if(jftype.equals("1")){
//								 	 					String zdrate=jfpd.getString("oneback_rate");
//								 	 					jf1=Double.parseDouble(zdrate);
//								 	 					if(jf1 < minjfrate || minjfrate==0){
//								 	 						minjfrate=jf1;
//								 	 					}
//								 	 					minjfratestr=minjfrate+"%";
//					 	 				}else if(jftype.equals("5")){//满多少增多少
//						 	 					String[] str=jfgrantrule.split(",");
//					 	 							for(int i=0; i<str.length ;i++){
//					 	 									String content=str[i];
//						  		  							String number2=content.substring(content.indexOf("送")+1, content.lastIndexOf("%"));
//					  		  								jf1=Double.parseDouble(number2);
//	  		 					  		  					if(jf1 < minjfrate || minjfrate==0){
//									 	 						minjfrate=jf1;
//									 	 					}
//					 	 							}
//					 	 							minjfratestr=minjfrate+"%";
//			 		 	 				}else if(jftype.equals("4")){
// 								 	 					minjfratestr=jfgrantrule;
//					 	 				}else if(jftype.equals("3")){
//					 	 					String mdjf=jfpd.getString("threemin_rate");
//					 	 					jf1=Double.parseDouble(mdjf);
//					 	 					if(jf1 < minjfrate || minjfrate==0){
//					 	 						minjfrate=jf1;
//					 	 					}
//					 	 					minjfratestr=minjfrate+"%";
//					 	 				}else if(jftype.equals("2")){//满多少增多少
//					 	 					String number2=jfgrantrule.substring(jfgrantrule.indexOf("：")+1, jfgrantrule.lastIndexOf("-"));
//					 	 					jf1=Double.parseDouble(number2);
//					 	 					if(jf1 < minjfrate || minjfrate==0){
//					 	 						minjfrate=jf1;
//					 	 					}
//					 	 					minjfratestr=minjfrate+"%";
//					 	 				}
//									}
//									jfpd=null;
//						}else if(marketing_type.equals("7")){
////								zk+=grantrule+",";
//								e.put("store_discountway_id", marketing_id);
//								//获取所有启用的折扣
//								PageData zkpd=appStorepc_marketingService.getZKById(e);
//								if(zkpd != null){
//										String zkgrantrule=zkpd.getString("grantrule");
// 									    double zk1=0;
//										if(zkpd.getString("discount_type").equals("1")){//整店折扣
//	   											zk1=Double.parseDouble(zkpd.getString("onealldiscount_rate"));
//	   			 								if(minzkrate > zk1 || minzkrate==0 ){
//	   			 									 minzkrate=zk1;
//	   			 								}
//										}else if(zkpd.getString("discount_type").equals("4")){//满多少折多少
//													String[] str=zkgrantrule.split(",");
//					 	 							for(int i=0; i<str.length ;i++){ 
//					  		  								 String number2=str[i].substring(str[i].indexOf("打")+1, str[i].lastIndexOf("折"));
//					  		 								 zk1=Double.parseDouble(number2);
//				  		 								 if(minzkrate > zk1 || minzkrate==0){
//			   			 									 minzkrate=zk1;
//			   			 								 }
//					 	 							}
//										}else if(zkpd.getString("discount_type").equals("2")){//分类折扣
//												zk1=Double.parseDouble(zkpd.getString("twoproductdiscount_rate"));
//				 								if(minzkrate > zk1 || minzkrate==0){
//				 									 minzkrate=zk1;
//				 								}
//									   }
//								}
//								zkpd=null;
// 					}
//							
//				}
////				if(!add.equals("")){
////					add=add.substring(0, add.length()-1);
////				}
////				pd.put("mz", add );
////				if(!reduce.equals("")){
////					reduce=reduce.substring(0, reduce.length()-1);
////				}
////				pd.put("mj", reduce );
////				if(!time.equals("")){
////					time=time.substring(0, time.length()-1);
////				}
////				pd.put("sdyx", time );
////				if(!n.equals("")){
////					n=n.substring(0, n.length()-1);
////				}
////				pd.put("mnjn", n );
////				if(!number.equals("")){
////					number=number.substring(0, number.length()-1);
////				}
////				pd.put("gmcs", number );
////				if(!score.equals("")){
////					score=score.substring(0, score.length()-1);
////				}
////				pd.put("jfsy", score );
////				if(!zk.equals("")){
////					zk=zk.substring(0, zk.length()-1);
////				}
////				pd.put("zk", zk );
//	 			pd.put("minjfrate",  minjfratestr);
//				pd.put("minzkrate",  minzkrate+"折");
//				pd.put("member_id", member_id);
//				PageData  collectpd=new PageData();
//			collectpd=appStoreService.getCollectionById(pd);
//			if(collectpd == null){
//				pd.put("collect", "");
//			}else{
//				pd.put("collect", collectpd.getString("collect_id"));
//			}
//			List<PageData> vipList=appMemberService.getStoreVipById(pd);
//			//判断是否已是当前商店的会员
//			if(vipList.size() != 0){
//				pd.put("vip", "1");
//			}else{
//				pd.put("vip", "0");
//			}
// 		} catch(Exception e1){
//			result = "0";
//			message="系统错误";
//			logger.error(e1.toString(), e1);
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", pd);
//		return map;
//	}
//	
//	
//	/**
//	 * 点赞
//	 * 刘耀耀
//	 * 2016.6.17
//	 *魏汉文20160629
//	 */
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
//		return AppUtil.returnObject(pd, map);
//	}
//	
//	
//	@Resource(name="appPay_historyService")
//	private AppPay_historyService appPay_historyService;
//	@Resource(name="appOrderService")
//	private AppOrderService appOrderService;
//	
//
//	
//	
//	
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
//		return AppUtil.returnObject(pd, map);
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
//	
//	@Resource(name="appStore_redpacketsService")
//	private AppStore_redpacketsService appStore_redpacketsService;
//	
//	/**
//	 * 收藏列表：member_id
//	 *魏汉文20160629
//	 */
//	@RequestMapping(value="/listCollectionById")
//	@ResponseBody
//	public Object listCollectionById() {
//		logBefore(logger, "收藏");
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
//		Map<String,Object> map = new HashMap<String,Object>();
//		List<PageData> storeList=new ArrayList<PageData>();
// 		String result = "1";
//		String message="收藏列表";
//		PageData pd = new PageData();
//		try {
//				pd=this.getPageData();
//				double  longitude1=0;//用户经度
//				double latitude1=0;//用户纬度
//				//判断是否定位了
//				pd=appMemberService.findById(pd);
//				if(pd == null){
//					map.put("result", "0");
//					map.put("message", "缺少必要参数");
//					map.put("data", "");
//					return map;
//				}
//				boolean isdingwei=true;
//				if(pd.getString("longitude") == null || pd.getString("longitude").equals("")){
//					isdingwei=false;
//				}else{
//					longitude1=Double.parseDouble(pd.getString("longitude"));
//		 			latitude1=Double.parseDouble(pd.getString("latitude"));
//				}
// 				List<PageData> allstoreList=appStoreService.listCollectionById(pd);
//				for(PageData e :  allstoreList){
// 							String collect_id=e.getString("collect_id");
//						    e=appStoreService.findById(e);
//	 						double longitude2=Double.parseDouble(e.getString("longitude"));//商家经度
//							double latitude2=Double.parseDouble(e.getString("latitude"));//商家纬度
//							if(isdingwei){
//									double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
//										e.put("distance", df.format(overdistance/1000));
//							}else{
//									e.put("distance", "--");
//							}
//							//判断是否开通zk
// 							PageData ispd=appStorepc_marketingService.getZKById(e);
//							if(ispd == null){
//								e.put("zkstatus", "0");
//							}else{
//								e.put("zkstatus", "1");
//							}
// 							//获取营销规则
//							List<PageData> marketlist=appStorepc_marketingService.listAllMarketing(e);
//							String add="";
//							String reduce="";
//							String time="";
//							String n="";
//							String number="";
//							String score="";
//							String zk="";
//							for(PageData e2 : marketlist){
//										/*
//										 * 1-满赠，2-满减，3-时段营销，4-买N减N，5-购买次数，6-积分收益,7-折扣
//										 */
//										String marketing_type=e2.getString("marketing_type");
//										String grantrule=e2.getString("grantrule");
//										if(marketing_type.equals("1")){
//											add+=grantrule+",";
//										}else if(marketing_type.equals("2")){
//											reduce+=grantrule+",";
//										}else if(marketing_type.equals("3")){
//											time+=grantrule+",";
//										}else if(marketing_type.equals("4")){
//											n+=grantrule+",";
//										}else if(marketing_type.equals("5")){
//											number+=grantrule+",";
//										}else if(marketing_type.equals("6")){
//											score+=grantrule+",";
//										}else if(marketing_type.equals("7")){
//											zk+=grantrule+",";
//										}
//							}
//							if(!add.equals("")){
//	  							add=add.substring(0, add.length()-1);
//	  						}
//	  						if(!reduce.equals("")){
//	  							reduce=reduce.substring(0, reduce.length()-1);
//	  						}
//	  						if(!time.equals("")){
//	  							time=time.substring(0, time.length()-1);
//	  						}
//	  						if(!n.equals("")){
//	  							n=n.substring(0, n.length()-1);
//	  						}
//	  						if(!number.equals("")){
//	  							number=number.substring(0, number.length()-1);
//	  						}
//	  						if(!score.equals("")){
//	  							score=score.substring(0, score.length()-1);
//	  						}
//	  						if(!zk.equals("")){
//	  							zk=zk.substring(0, zk.length()-1);
//	  						}
// 							e.put("mz", add );
//							e.put("mj", reduce );
//							e.put("sdyx", time );
//							e.put("mnjn", n );
//							e.put("gmcs", number );
//							e.put("jfsy", score );
//							e.put("zk", zk );
//							e.put("collect_id", collect_id);
//							//判断是否有红包
//							e.put("member_id", pd.getString("member_id"));
//							String rednumber=appStore_redpacketsService.listCountRedPackage(e);
//							if(rednumber == null || rednumber.equals("")){
//								rednumber="0";
//							}
// 							if(Integer.parseInt(rednumber) >0){
//								e.put("haveRed", "1");
//							}else{
//								e.put("haveRed", "0");
//							}
//							storeList.add(e);
//				}
// 		} catch (Exception e) {
//			result="0";
// 			message="成功收藏失败";
//			e.printStackTrace();
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", storeList);
//		return map;
//	}
//	
//	
//	/**
//	 * 消费过的商家列表
//	 * 魏汉文20160630
//	 */
//	@RequestMapping(value="/saleOrderForStoreByMem")
//	@ResponseBody
//	public Object saleOrderForStoreByMem(){
//		logBefore(logger, "消费过的商家列表");
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{ 
//			pd=this.getPageData();
//			List<PageData> storeList=appStoreService.saleOrderForStoreByMem(pd);
//			if(storeList.size() == 0){
//				map.put("data","");
//			}else{
//				map.put("data",storeList);
//			}
// 		} catch(Exception e){
//			result="0";
//			message="获取失败";
//			map.put("data","");
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message",message);
// 		return AppUtil.returnObject(pd, map);
//	}
//	
//
//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
//	}
//		
//
//	/**
//	 * 获取服务商
//	 */
//	@RequestMapping(value="/getSp")
//	@ResponseBody
//	public Object getSp() {
//		logBefore(logger, " 获取服务商");
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try {
//			pd=this.getPageData();
//			pd= appStoreService.findByIdOne(pd);	//列出Store列表
// 		} catch (Exception e) {
//			result="0";
// 			message="获取失败";
//			e.printStackTrace();
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", pd);
//		return map;
//	}
//	
//	/**
//	 * 注册商家
//	 */
//	@RequestMapping(value="/saveStore")
//	@ResponseBody
//	public Object saveStore() {
//		logBefore(logger, "注册商家");
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try {
//			pd = this.getPageData();
//			//判断是否注册过
//			PageData e=new PageData();
//			e=storepcService.listNamePwd(pd);
//			if( e == null){
//					String store_id="";//商家id 8位数字
//					boolean flag=true;
//		 			while(flag){
//						store_id=this.get8UID();//商家id 8位数字
//						PageData storepd = new PageData();
//						storepd = storepcService.getStoreId(store_id);
//						if(storepd != null && !storepd.equals("")){
//		 					continue;
//						}else{
//							flag=false;
//		  				}
//		 			}
//					pd.put("store_id", store_id);	//注册商家主键
//					pd.put("store_file_id", store_id);	//注册商家档案主键
//					pd.put("store_vip_id",(int) (Math.random()*4+1));
//					if(pd.getString("store_name") != null && !pd.getString("store_name").equals("")){
//						storepcService.save(pd);
//					}
//					//生成二维码图片
//					String imagename=store_id;
//					String tuiguangUrl="http://www.jiuyuvip.com/zhsh/html_member/goRegister.do?recommended="+store_id+"&recommended_type=1&recommended_phone="+store_id;
//		 			OneEr.print(tuiguangUrl,  ""+pd.getString("store_name") , imagename,Const.ErWeiMa);
//		 			String path_url=Const.ErWeiMa+ "/"+imagename+".png";
//	//	 			是否需要将图片上传至云服务器
//		 			String currentPath = AppUtil.getuploadRootUrl(); //获取文件跟补录
//					String filePath = "/storeErFile";//文件上传路径
//					String cityFilename =  FileUpload.fileUpFile(path_url, currentPath+filePath, imagename);//字符拼接，上传到服务器上
//					path_url= AppUtil.getuploadRootUrlIp()+filePath+"/"+cityFilename;
//	//				System.out.println("商家推广图片地址:"+path_url);
//					//发送短信
//					SmsUtil.sendZhuCeStore(pd.getString("registertel_phone"));
// 		 	}else{
//		 		System.out.println("当前账号已注册");
//		 	}
//		} catch (Exception e) {
//			result="0";
//			message="获取失败";
//			e.printStackTrace();
//		}
//		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");
//		return map;
//	}
//	
//	
//	@Resource(name="storepcService")
//	private StorepcService storepcService;
// 
//	
//	
//}
