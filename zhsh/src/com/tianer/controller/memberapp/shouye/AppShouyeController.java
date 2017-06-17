package com.tianer.controller.memberapp.shouye;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.tianer.service.business.app_advert.App_advertService;
import com.tianer.service.business.cm_all.Cm_allService;
import com.tianer.service.business.menu_text.Menu_textService;
import com.tianer.service.business.pcd.PcdService;
import com.tianer.service.memberapp.AppCity_fileService;
import com.tianer.service.memberapp.AppGoodsService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppMember_redpacketsService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.memberapp.AppStore_redpacketsService;
import com.tianer.service.memberapp.AppStorepc_marketingService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.Distance;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;

/** 
 * 类名称：AppShouyeController
 * 创建人：魏汉文(首页所需的接口)
 * 创建时间：2016-06-20
 */
@Controller("appShouyeController")
@RequestMapping(value="/app_shouye")
public class AppShouyeController extends BaseController {
	
	@Resource(name="appGoodsService")
	private AppGoodsService goodsService;
	
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	@Resource(name="appStorepc_marketingService")
	private AppStorepc_marketingService appStorepc_marketingService;
	
	@Resource(name="appCity_fileService")
	private AppCity_fileService appCity_fileService;
	
	@Resource(name="cm_allService")
	private Cm_allService cm_allService;
	
	@Resource(name="appStore_redpacketsService")
	private AppStore_redpacketsService appStore_redpacketsService;
	
	@Resource(name="pcdService")
	private PcdService pcdService;
	
	@Resource(name="storepc_wealthhistoryService")
	private Storepc_wealthhistoryService storepc_wealthhistoryService;
	
	

 	public DecimalFormat df2=TongYong.df2;
	
	/**
	 * 首页商品列表列表,member_id,经度，纬度，省市区名称，
	 * 魏汉文
	 * @return 
	 * app_shouye/kjListGoods.do
	 */
	@RequestMapping(value="/kjListGoods")
	@ResponseBody
	public Object kjListGoods(){
 		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1= new HashMap<String,Object>();
		List<PageData> redpackageList=new ArrayList<PageData>();//用来存储红包list
		List<PageData> allstoreList=new ArrayList<PageData>();//用来存储商家List
 		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd=this.getPageData();
  			//更新当前用户的位置：经度纬度，省市区
			try {
				//获取省
				if(pd.getString("province_name") == null || pd.getString("province_name").equals("")){
					PageData propd=pcdService.findProvinceByIdByCity(pd);
						if(propd !=null){
							pd.put("province_name", propd.getString("name"));
						}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
 			appMemberService.edit(pd);
			 PageData _pd=new PageData();
  			 _pd=appMemberService.findById(pd);//会员的详细信息
			//获取经度纬度与所有商家距离进行比较
//			double distance=Const.appdistance;//先显示3Km范围内
			double  longitude1=0;//用户经度
			double latitude1=0;//用户纬度
			//判断是否定位了
			boolean isdingwei=true;
			if(pd.getString("longitude") == null || pd.getString("latitude") == null|| pd.getString("longitude").equals("") || pd.getString("latitude").equals("")){
				isdingwei=false;
				pd.put("longitude", "120");
				pd.put("latitude", "30");
			}else{
				longitude1=Double.parseDouble(pd.getString("longitude"));
	 			latitude1=Double.parseDouble(pd.getString("latitude"));
			}
			String now_page=pd.getString("page");
   			pd.put("endnumber", 10);
 			if(now_page == null || now_page.equals("") || now_page.equals("0")){
 				now_page="1"; 
 			}
 			pd.put("startnumber", (Integer.parseInt(now_page)-1)*10);
			List<PageData> storeList=appStoreService.getStoreList(pd);
  			for(int i=0;i<storeList.size() ;i++){
							PageData e=new PageData();
							e=storeList.get(i);
							//判断是否开通zk
 							PageData ispd=appStorepc_marketingService.getZKById(e);
							if(ispd == null){
								e.put("zkstatus", "0");
							}else{
								e.put("zkstatus", "1");
							}
							//判断是否有红包
							pd.put("store_id", e.getString("store_id"));
							Map<String,Object> redmap=TongYong.storeAndMemberByRed(pd);//包括会员id和商家id
							boolean flag=(boolean) redmap.get("flag");//判断是否还有符合的红包
							if(flag){
								e.put("haveRed", "1");
							}else{
								e.put("haveRed", "0");
							}
							if(!isdingwei){
								e.put("distance", e.getString("distance")+"km");
							}else{
								if(Double.parseDouble(e.getString("distance") )-Const.maxjuli > 0 ){
									e.put("distance", Const.maxjuli+"km+");
								}else{
									e.put("distance", e.getString("distance")+"km");
								}
							}
  							//获取营销规则
							List<PageData> marketlist=appStorepc_marketingService.listAllMarketing(e);
							e.put("marketlist", marketlist);
   							allstoreList.add(e);
  							e=null;
			}
   			storeList=null;
  			//获取当省市区的name获取城市档案ID，再获取营销参数：1.会员领开机红包的频数和刷新时间，2.会员获取开机红包的来源
	        PageData citypd=appCity_fileService.findIdByPcd(pd);
 	        //判断是否有当前的城市营销参数
	        if(citypd != null ){
				        	boolean flag=true;//用来判断是否满足步骤
				 			Date now=new Date();
							long nowtime=now.getTime();
							String nowday=DateUtil.getDay();
							//红包发送时间
							List<PageData> fourList=cm_allService.listAllRedTime(citypd);
							for(PageData e :fourList){
								  String refresh_type=e.getString("refresh_type");
								  String onerefresh_time=e.get("onerefresh_time").toString();
				 				  String tworefresh_time=e.get("tworefresh_time").toString();
				 				  if(refresh_type.equals("1")){
									  		nowday=nowday+" "+onerefresh_time;
									  		Date onerefreshtime=DateUtil.fomatDate1(nowday);
									  		long onetime=onerefreshtime.getTime();
									  		if(nowtime < onetime ){
									  			flag=false;
									  		}
								  }else if(refresh_type.equals("2")){
										    String nowday1=nowday+" "+onerefresh_time;
										    String nowday2=nowday+" "+tworefresh_time;
									  		Date onerefreshtime=DateUtil.fomatDate1(nowday1);
									  		Date tworefreshtime=DateUtil.fomatDate1(nowday2);
									  		long onetime=onerefreshtime.getTime();
									  		long twotime=tworefreshtime.getTime();
									  		if(_pd ==null ){
									  			flag=false;
 									  		}else{
											  			if(onetime<= nowtime && nowtime <= twotime ){//第一次的开始获取时间
												  				if(_pd.getString("onetime_red").equals("1")){
												  						flag=false;
												  				}else{
												  					pd.put("onetime_red", "1");
							 					  					appMemberService.edit(pd);
												  				}
												  		}else if(nowtime > twotime){//第二次开机获取的时间
													  			if(_pd.getString("twotime_red").equals("1")){
												  						flag=false;
												  				}else{
												  					pd.put("twotime_red", "1");
												  					appMemberService.edit(pd);
												  				}
												  		}else{
												  			flag=false;
												  		}
									  		}
 								  }else{
									  flag=false;
								  }
							}
							fourList=null;
							
							//是否满足第一步开始时间的红包要求
							double twodistance=0;
							String twoscore="0-0";
							String twoscorestatus="0";
							String twotime="0";
							if(flag){
								//获取会员获取开机红包的来源
								List<PageData> twoList=cm_allService.listAllKaijiRed(citypd);
				 				for(int i=0; i<twoList.size() ;i++){
										PageData e=new PageData();
										e=twoList.get(i);
										String soure_type=e.getString("source_type");//1-设置1的商家，2-小于等于多少m的商家，3-最近一周刚上线的商家，4-综合分值商家
										String check_status=e.getString("check_status");
										String parameter=e.getString("parameter");
										if(soure_type.equals("1") && check_status.equals("0")){
												flag=false;
												break ; 
										}else if(soure_type.equals("2") && check_status.equals("1")){
 												if(isdingwei){
													double d=Double.parseDouble(parameter);
													if(twodistance < d ){
														twodistance=d;
													}
												}else{
													flag=false;
													break ; 
												}
				 						}else if(soure_type.equals("3") && check_status.equals("1")){
				 								twotime="1";
				 						}else if(soure_type.equals("4") && check_status.equals("1")){
				 								twoscorestatus="1";
				  								int str1=Integer.parseInt(parameter.split("-")[0]);
				 								int str2=Integer.parseInt(twoscore.split("-")[0]);
				 								if(str1 >= str2){
				 									twoscore=parameter;
				 								}
										}
										e=null;
								}
				 				twoList=null;
				 			}
							
					//步骤2是否成立
					if(flag){
						BaseController.lock.lock();
						  try {
 								//获取营销中的商家红包0-普通红包，1-开机红包，2-附近红包
								pd.put("redpackage_status", Const.kaiji_redpackage_status);
								List<PageData> oneList=cm_allService.listAllRedStore(citypd);
								for(int i=0;i<oneList.size() ;i++){
										PageData e=new PageData();
										e=oneList.get(i);
										double longitude2=Double.parseDouble(e.getString("longitude"));//商家经度
										double latitude2=Double.parseDouble(e.getString("latitude"));//商家纬度
										String logindate=DateUtil.getDay();
										boolean ifreduce=false;
										if(twotime.equals("1")){//判断是否满足要求一个星期内登陆的商家
												long day=DateUtil.getDaySub(logindate, nowday);
												if( day > Const.loginday){
				 									ifreduce=true;
												}
										}
										double overdistance=Distance.getResult(latitude1, latitude2, longitude1, longitude2);//会员与商家间的距离
										if(overdistance > twodistance && twodistance != 0){
				 								ifreduce=true;
										}
										if(twoscorestatus.equals("1")){//排序分值判断满足条件的商家
												String[] str=twoscore.split("-");
												double d1=Double.parseDouble(str[0])/100;
												double d2=Double.parseDouble(str[1])/100;
												int countstore=Integer.parseInt(appStoreService.countStore());
												int startnumber=(int)(d1*countstore);
												int endnumber=(int)(d2*countstore);
												citypd.put("startnumber", startnumber);
												citypd.put("endnumber", endnumber-startnumber);
												List<PageData> okStoreList=appStoreService.getStoreList(citypd);
												boolean f=true;
												for(PageData e2 : okStoreList){
														if(e2.getString("store_id").equals(e.getString("store_id"))){
															f=false;
														}
												}
												if(f){
				 										ifreduce=true;
												}
										}
										if(ifreduce){
											oneList.remove(i);
											i=i-1;
										}
										e=null;
				 				}
								//一步步筛选下可以发至开机红包的红包oneList
								for(int i=0;i<oneList.size() ;i++){
									   if(redpackageList.size() > Const.getKaiJiRed){//最多获取10个开机红包
										   break;
									   }
										PageData e=new PageData();
										e=oneList.get(i);
										String redpackage_type=e.getString("redpackage_type");
										String choice_type=e.getString("choice_type");
										double money=Double.parseDouble(e.getString("money"));//红包总金钱
										int redpackage_number=Integer.parseInt(e.getString("redpackage_number"));//总红包
										//平均金钱保留两位小数
										double overget_money=Double.parseDouble(e.getString("overget_money"));//已领金钱总金钱
										int overget_number=Integer.parseInt(e.getString("overget_number"));//已领红包
										if(overget_number == redpackage_number && overget_money >= money ){//判断是否还有红包
												cm_allService.deleteRedStore(e);
										}else{
											PageData e2=new PageData();
											e2.put("store_redpackets_id", e.getString("store_redpackets_id"));
											//判断当前红包为什么类型红包
											double x1=0;
											if(redpackage_type.equals("1")){//现金红包
												    if(redpackage_number-overget_number == 1){
												    	x1=money-overget_money;
												    	e2.put("overget_money",df2.format(money) );
														e2.put("overget_number", redpackage_number);
							 							appStore_redpacketsService.edit(e2);//更新红包金钱以及数量
												    }else{
												    	if(choice_type.equals("1")){
															double lessmoney=money-overget_money;
															int lessnumber=redpackage_number-overget_number;
															//获取平均值
															double pjmoney=lessmoney/lessnumber;
															double minpjmoney=pjmoney/2;
															double maxpjmoney=pjmoney/2+pjmoney;
															x1=StringUtil.getSuiJi(minpjmoney, maxpjmoney);
															e2.put("overget_money",df2.format(overget_money+x1) );
															e2.put("overget_number", overget_number+1+"");
								 							appStore_redpacketsService.edit(e2);//更新红包金钱以及数量
														}else{
															//获取平均值
															x1=money/redpackage_number;
															e2.put("overget_money",df2.format(overget_money+x1) );
															e2.put("overget_number", overget_number+1+"");
										 					appStore_redpacketsService.edit(e2);//更新红包金钱以及数量
														}
												    }
 													//获取红包详情
													e2=appStore_redpacketsService.findRedById(e2);
													String srp_usercondition_id=e2.getString("srp_usercondition_id");
													String redpackage_content="";
													if(srp_usercondition_id.equals("1")){
														redpackage_content=e2.getString("srp_usercondition_content")+"减"+x1+"元";
														srp_usercondition_id="1";
													}else if(srp_usercondition_id.equals("2")){
														redpackage_content=e2.getString("srp_usercondition_content")+"减"+x1+"元";
														srp_usercondition_id="21";
													}else if(srp_usercondition_id.equals("3")){
														redpackage_content=e2.getString("srp_usercondition_content")+"减"+x1+"元";
														srp_usercondition_id="3";
													}
													e2.put("redpackage_id",BaseController.getTimeID());
													e2.put("member_id", pd.getString("member_id"));
													e2.put("redpackage_money", df2.format(x1));
													e2.put("redpackage_content",redpackage_content );
													e2.put("store_redpackets_id", e2.getString("store_redpackets_id"));
													e2.put("redpackage_type",srp_usercondition_id);
													e2.put("enddate", e2.get("endtime").toString());
													e2.put("startdate", e.get("starttime").toString());
													e2.put("set_id", e2.getString("store_id"));
													e2.put("set_type", "1");
													appMemberService.saveRedForMember(e2);//新增红包信息至会员
											}else if(redpackage_type.equals("2")){//折扣红包
													//更新红包金钱以及数量
													e2.put("overget_number", overget_number+1+"");
						 							appStore_redpacketsService.edit(e2);
						 							e2=appStore_redpacketsService.findRedById(e2);
													String srp_usercondition_id=e2.getString("srp_usercondition_id");
													String redpackage_content="";
													if(srp_usercondition_id.equals("1")){
														redpackage_content=e2.getString("srp_usercondition_name")+"打"+df2.format(money)+"折";
														srp_usercondition_id="1";
													}else if(srp_usercondition_id.equals("2")){
														redpackage_content=e2.getString("srp_usercondition_name")+"打"+df2.format(money)+"折";
														srp_usercondition_id="21";
													}else if(srp_usercondition_id.equals("3")){
														redpackage_content=e2.getString("srp_usercondition_name")+"打"+money+"折";
														srp_usercondition_id="3";
													}
													if(choice_type.equals("2")){
														srp_usercondition_id=srp_usercondition_id+"1";
													}
													e2.put("redpackage_id",BaseController.getTimeID());
													e2.put("member_id", pd.getString("member_id"));
													e2.put("redpackage_money", df2.format(money));
													e2.put("redpackage_content",redpackage_content );
													e2.put("store_redpackets_id", e2.getString("store_redpackets_id"));
													e2.put("redpackage_type",srp_usercondition_id);
													e2.put("enddate", e2.get("endtime").toString());
													e2.put("startdate", e.get("starttime").toString());
													e2.put("set_id", e2.getString("store_id"));
													e2.put("set_type", "1");
													appMemberService.saveRedForMember(e2);//新增红包信息至会员
											}
											PageData e3 = new PageData();
											e3=appMemberService.findRePackagedById(e2);
											redpackageList.add(e3);
											e3=null;
											e2=null;
										}
										e=null;
								}
							oneList=null;
	 				  	
						} catch (Exception e) {
							// TODO: handle exception
							logger.error(e.toString(), e);
						}finally{
							 BaseController.lock.unlock();
						}
					}
	        }else{
	        	message="当前城市未开通";
	        }
	        
  			//获取广告位的集合
	        if(citypd != null){
	        	citypd.put("advert_type", "1");
				List<PageData> advertList=app_advertService.listAllAdvert(citypd);
				map1.put("advertList", advertList);
				advertList=null;
	        }else{
	        	map1.put("advertList", new ArrayList<PageData>());
	        }
	        //设置筛选集合
	        List<PageData> shaixuanList=new ArrayList<PageData>();
 	        PageData shaxuanpd=new PageData();
 	        shaxuanpd.put("sx_id", "0");
	        shaxuanpd.put("name", "筛选");
	        shaixuanList.add(shaxuanpd);
	        shaxuanpd.clear();
	        shaxuanpd.put("sx_id", "1");
	        shaxuanpd.put("name", "首单立减");
	        shaixuanList.add(shaxuanpd);
	        shaxuanpd.clear();
	        shaxuanpd.put("sx_id", "2");
	        shaxuanpd.put("name", "折扣商品");
	        shaixuanList.add(shaxuanpd);
	        shaxuanpd.clear();
	        shaxuanpd.put("sx_id", "3");
	        shaxuanpd.put("name", "满返代金券");
	        shaixuanList.add(shaxuanpd);
	        shaxuanpd=null;
	        map1.put("shaixuanList", shaixuanList);
	        shaixuanList=null;
 	        //设置排序集合
	        List<PageData> paixuList=new ArrayList<PageData>();
 	        PageData paixupd=new PageData();
 	        paixupd.put("px_id", "1");
 	        paixupd.put("name", "智能排序");
	        paixuList.add(paixupd);
	        paixupd.clear();
	        paixupd.put("px_id", "2");
	        paixupd.put("name", "距离从近到远");
	        paixuList.add(paixupd);
	        paixupd.clear();
	        paixupd.put("px_id", "3");
	        paixupd.put("name", "人气从高到低");
	        paixuList.add(paixupd);
	        paixupd.clear();
	        paixupd.put("px_id", "4");
	        paixupd.put("name", "积分率从高到低");
	        paixuList.add(paixupd);
	        paixupd.clear();
	        paixupd.put("px_id", "5");
	        paixupd.put("name", "销售量从高到低");
	        paixuList.add(paixupd);
	        paixupd=null;
	        map1.put("paixuList", paixuList);
	        paixuList=null;
			//获取城市一级分类数据
			pd.put("sort_parent_id", "0");
			pd.put("sort_type", "1");
			List<PageData>	cityList = appCity_fileService.listAllCitySort(pd);//列出City_file列表
 			//到最后一步才能发出所有数据
			map1.put("cityList", cityList);
			cityList=null;
			map1.put("redpackageList", redpackageList);
			redpackageList=null;
			map1.put("allstoreList", allstoreList);
			allstoreList=null;
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
	
	@Resource(name="app_advertService")
	private App_advertService app_advertService;
	
	
	
	
	/**
	 * 获得一级二级的分类信息
	 * 魏汉文20160623
	 */
	@RequestMapping(value="/listAllCitySort")
	@ResponseBody
	public Object listAllCitySort() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			//获得一级分类
			pd.put("sort_parent_id", "0");
			pd.put("sort_type", "1");
			List<PageData> firstList=appCity_fileService.listAllCitySort(pd);
			for(PageData e : firstList){
	 				//获得二级分类
					pd.put("sort_parent_id", e.getString("city_file_sort_id"));
					pd.put("sort_type", "2");
					List<PageData> twoList=appCity_fileService.listAllCitySort(pd);
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
			map.put("data", sslist);
			sslist=null;
			firstList=null;
			flpd1=null;
			pd=null;
 		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	
	
	/**
	 * 获得所有商家
	 * 魏汉文20160623
	 * pauxu:  1-智能排序（综合分值为基数的），2-距离近到远，3-人气高到低，4-积分率从高到底，5-销售量从高到底
	 * shaixuan:    1-首单立减（满减），2-折扣商品（折扣），3-满返代金卷（满赠）
	 * app_shouye/listAllStore.do
	 */
	@RequestMapping(value="/listAllStore")
	@ResponseBody
	public Object listAllStore() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> allstoreList=new ArrayList<PageData>();//用来存储商家List
 		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
  			//判断是否定位了
			boolean isdingwei=true;
			if(pd.getString("longitude") == null || pd.getString("longitude").equals("") || pd.getString("longitude").equals("0.000000")  || pd.getString("latitude") == null || pd.getString("latitude").equals("") || pd.getString("latitude").equals("0.000000") ){
				isdingwei=false;
				pd.put("longitude", "120");
				pd.put("latitude", "30");
			} 
			String now_page=pd.getString("page");
    		pd.put("endnumber", 10);
 			if(now_page == null || now_page.equals("") || now_page.equals("0")){
 				now_page="1";
 			}
 			pd.put("startnumber", (Integer.parseInt(now_page)-1)*10);
 			List<PageData> storeList=appStoreService.getStoreList(pd);
 			PageData e=null;;
  			for(int i=0;i<storeList.size() ;i++){
 							e=storeList.get(i);
							//判断是否开通zk
 							PageData ispd=appStorepc_marketingService.getZKById(e);
							if(ispd == null){
								e.put("zkstatus", "0");
							}else{
								e.put("zkstatus", "1");
							}
							//判断是否有红包
							pd.put("store_id", e.getString("store_id"));
							Map<String,Object> redmap=TongYong.storeAndMemberByRed(pd);//包括会员id和商家id
							boolean flag=(boolean) redmap.get("flag");//判断是否还有符合的红包
							if(flag){
								e.put("haveRed", "1");
							}else{
								e.put("haveRed", "0");
							}
							if(!isdingwei){
								e.put("distance", e.getString("distance")+"km");
							}else{
								if(Double.parseDouble(e.getString("distance") )-Const.maxjuli > 0 ){
									e.put("distance", Const.maxjuli+"km+");
								}else{
									e.put("distance", e.getString("distance")+"km");
								}
							}
   							//获取营销规则
							List<PageData> marketlist=appStorepc_marketingService.listAllMarketing(e);
							e.put("marketlist", marketlist); 
							allstoreList.add(e);
							e=null;
 			}
  			storeList=null;
 			map.put("data", allstoreList);
 			allstoreList=null;
 			//设置筛选集合
	        List<PageData> shaixuanList=new ArrayList<PageData>();
 	        PageData shaxuanpd=new PageData();
 	        shaxuanpd.put("sx_id", "0");
	        shaxuanpd.put("name", "筛选");
	        shaixuanList.add(shaxuanpd);
	        shaxuanpd.clear();
	        shaxuanpd.put("sx_id", "1");
	        shaxuanpd.put("name", "首单立减");
	        shaixuanList.add(shaxuanpd);
	        shaxuanpd.clear();
	        shaxuanpd.put("sx_id", "2");
	        shaxuanpd.put("name", "折扣商品");
	        shaixuanList.add(shaxuanpd);
	        shaxuanpd.clear();
	        shaxuanpd.put("sx_id", "3");
	        shaxuanpd.put("name", "满返代金券");
	        shaixuanList.add(shaxuanpd);
	        shaxuanpd=null;
	        map.put("shaixuanList", shaixuanList);
	        shaixuanList=null;
 	        //设置排序集合
	        List<PageData> paixuList=new ArrayList<PageData>();
 	        PageData paixupd=new PageData();
 	        paixupd.put("px_id", "1");
 	        paixupd.put("name", "智能排序");
	        paixuList.add(paixupd);
	        paixupd.clear();
	        paixupd.put("px_id", "2");
	        paixupd.put("name", "距离从近到远");
	        paixuList.add(paixupd);
	        paixupd.clear();
	        paixupd.put("px_id", "3");
	        paixupd.put("name", "人气从高到低");
	        paixuList.add(paixupd);
	        paixupd.clear();
	        paixupd.put("px_id", "4");
	        paixupd.put("name", "积分率从高到低");
	        paixuList.add(paixupd);
	        paixupd.clear();
	        paixupd.put("px_id", "5");
	        paixupd.put("name", "销售量从高到低");
	        paixuList.add(paixupd);
	        paixupd=null;
	        map.put("paixuList", paixuList);
	        paixuList=null;
 			pd=null;
 		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
  		return map;
	}
	
	@Resource(name="appMember_redpacketsService")
	private AppMember_redpacketsService member_redpacketsService;
	
	
	/**
	 * .抢附近红包随机四个
	 * 魏汉文20160624
	 */
	@RequestMapping(value="/getFuJinRed")
	@ResponseBody
	public Object getFuJinRed() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
 		List<PageData> allredList=new ArrayList<PageData>();//用来存储红包List
		List<PageData> fourredList=new ArrayList<PageData>();//用来存储红包List
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			double  longitude1=0;
			double latitude1=0;
			//判断是否定位了
 			if(pd.getString("longitude") == null || pd.getString("longitude").equals("") || pd.getString("latitude") == null || pd.getString("latitude").equals("")){
				map.put("result", "1");
		    	map.put("message","请先定位");
		    	map.put("data", "0");
		    	return map;
			}else{
				longitude1=Double.parseDouble(pd.getString("longitude"));//用户经度
		 		latitude1=Double.parseDouble(pd.getString("latitude"));//用户纬度
		 		//修改定位后的信息
		 		if(pd.getString("member_id") != null && !pd.getString("member_id").equals("")){
		 			appMemberService.edit(pd);
		 		}
 			}
 			//获取当省市区的name获取城市档案ID，再获取营销参数：1.会员领开机红包的频数和刷新时间，2.会员获取开机红包的来源
 			PageData citypd=appCity_fileService.findIdByPcd(pd);
	        if(citypd == null){
		        	map.put("result", "1");
		    		map.put("message","暂无当前营销城市");
		    		map.put("data", "");
		    		return map;
	        } 
// 			double distance=0;
			String number="4";
 			//获取会员获取附近红包的来源
			//rule_status：1-综合分高到低，2-综合分低到高，3-距离远到底，4-距离近到远
// 			List<PageData> threeList=cm_allService.listAllfujinRed(citypd);//营销设置里面
// 			for(PageData e :  threeList){
//	 				String rule_type=e.getString("rule_type");
//	 				String rule_status=e.getString("rule_status");
//	 				if(rule_type.equals("3")){//距离
//	 					distance=Double.parseDouble(e.getString("parameter"));
//	 				}
//	 				if(rule_type.equals("1")){
//	 					number=rule_status;
//	 				}
// 			}
// 			threeList=null;
 			
 			//获取营销中的商家红包：0-普通红包，1-开机红包，2-附近红包
	        citypd.put("redpackage_status", Const.fujin_redpackage_status);
	    	List<PageData> oneList=appStore_redpacketsService.listAllFjRed(citypd);
			//获取当前用户的所有红包
			List<PageData>	memredList =member_redpacketsService.listAllById(pd);
 			//除去已领取的红包
			for(int i=0 ; i<oneList.size() ;i++ ){
				 PageData e=oneList.get(i);
				 String store_redpackets_id=e.getString("store_redpackets_id");
				 boolean flag=false;
 				 for(int j=0  ;  j<memredList.size() ;j++ ){
					 PageData f=memredList.get(j);
					 if(store_redpackets_id.equals(f.getString("store_redpackets_id"))){
						 flag=true;
						 break;
					 }
					 f=null;
				 }
 				 if(flag){
 					 oneList.remove(i);
 					 --i;
					 continue;
 				 }else{
 					 //判断是否符合条件
 					if(!TongYong.getRedByMemberIsOk(ServiceHelper.getAppMemberService().findById(pd),e)){
 						 oneList.remove(i);
 						 --i;
 						 continue;
 					}
 				 }
 //				System.out.println(store_redpackets_id);
 				e=null;
			}
			//-----------
  			for(int i=0;i<oneList.size() ;i++){
  						boolean isred=false;//用来判断是否符合
						PageData e=oneList.get(i);
						//判断红包是否符合要求
						double money=Double.parseDouble(e.getString("money"));//总金钱
						int redpackage_number=Integer.parseInt(e.getString("redpackage_number"));//总红包
						double overget_money=Double.parseDouble(e.getString("overget_money"));//已领金钱总金钱
						int overget_number=Integer.parseInt(e.getString("overget_number"));//已领红包
						String redpackage_type=e.getString("redpackage_type");
						String choice_type=e.getString("choice_type");
						String everymoney="";
						if(overget_number == redpackage_number  ){//判断是否还有红包
							isred=true;
					   }else{
							   if(redpackage_number-overget_number == 1){
								   	    everymoney=df2.format(money-overget_money);
								}else{
 										if(redpackage_type.equals("1")){
												if(choice_type.equals("1")){//随机金额
													//获取平均值
													double pjmoney=(money-overget_money)/(redpackage_number-overget_number);
													double minpjmoney=pjmoney/2;
													double maxpjmoney=pjmoney/2+pjmoney;
													 everymoney=df2.format( StringUtil.getSuiJi(minpjmoney, maxpjmoney) );
												}else if(choice_type.equals("2")){
													 everymoney=df2.format(money/redpackage_number);//每个用户可获取金钱money
												}
										}else if(redpackage_type.equals("2")){
											everymoney=money+"";
										}
 								}
					   }
 						//判断商家是否符合要求
						String overdistance="0";
						if(e.getString("longitude") == null || e.getString("longitude").equals("")){
							overdistance="--";
							isred=true;
						}else{
							double longitude2=Double.parseDouble(e.getString("longitude"));//商家经度
							double latitude2=Double.parseDouble(e.getString("latitude"));//商家纬度
							 double juli=Distance.getResult(latitude1, latitude2, longitude1, longitude2);
							 overdistance=TongYong.df2.format(juli/1000);//千米为单位
//							if(distance < overdistance){
//								isred=true;
//							}
						}
  						if(isred){
							oneList.remove(i);
							i=i-1;
						}else{
							PageData redpd=appStore_redpacketsService.findJJRedById(e);
							redpd.put("money", StringUtil.getMoveLastZero(everymoney));
							redpd.put("distance", overdistance);
							allredList.add(redpd);
						}
  						e=null;
 			}
			
			//处理排序
 			int rednumber=allredList.size();
			if(rednumber > 4){
 				for (int j = 0; j < rednumber+10; j++) {
					int h=(int)(Math.random()*rednumber);
					if(h<=rednumber-1){
						PageData redpd=allredList.get(h);
						String store_id=redpd.getString("store_id");
						Object o=map2.get(store_id);
						if(o == null){
							map2.put(store_id, redpd);
							fourredList.add(redpd);
						}
						if(fourredList.size() == 4){
							break;
						}
					}
 				}
    				 
			}else{
				for (PageData redpd : allredList) {
					String store_id=redpd.getString("store_id");
					Object o=map2.get(store_id);
					if(o == null){
						map2.put(store_id, redpd);
						fourredList.add(redpd);
					}
				}
   			}
//   			
//			//筛选条件下出来的fourredList
//			if(number.equals("1")){
//				Collections.sort(fourredList,new Comparator<PageData>(){  
// 		            @Override
//					public int compare(PageData  arg0, PageData arg1) {  
// 		                return arg1.getString("complex_score").compareTo(arg0.getString("complex_score"));  
// 		            }  
// 		        });  
//			}else if(number.equals("2")){
//				Collections.sort(fourredList,new Comparator<PageData>(){  
// 		            @Override
//					public int compare(PageData  arg0, PageData arg1) {  
// 		                return arg0.getString("complex_score").compareTo(arg1.getString("complex_score"));  
// 		            }  
// 		        });  
//			}else if(number.equals("3")){
//				Collections.sort(fourredList,new Comparator<PageData>(){  
// 		            @Override
//	 		       	public int compare(PageData  arg0, PageData arg1) {  
//	 		            	String distance1=arg1.getString("distance");
//	 		            	String distance2=arg0.getString("distance");
//	 		            	int n1=distance1.length();
//	 		            	int n2=distance2.length();
//	 		            	if(n2 > n1){
//	 		            		distance1=StringUtil.buZero(distance1, n2-n1);
//	 		            	}else if(n1>n2){
//	 		            		distance2=StringUtil.buZero(distance2, n1-n2);
//	 		            	}
//	 		                return distance2.compareTo(distance1);  
//	 		            }  
//	 		        });  
//			}else 
//				
			if(number.equals("4")){
				Collections.sort(fourredList,new Comparator<PageData>(){  
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
 		                return distance1.compareTo(distance2);  
 		            }  
 		        });  
			}
				map1.put("redList", fourredList);
 				//获取广告位的集合
 	        	citypd.put("advert_type", "2");
				List<PageData> advertList=app_advertService.listAllAdvert(citypd);
				map1.put("advertList", advertList);
				if(advertList.size() > 0){
					map1.put("advertpd", advertList.get(0));
				}else{
					map1.put("advertpd", new PageData());
				}
				citypd=null;
				advertList=null;
				fourredList=null;
				pd=null;
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
	
	
	
	
	
	
	@Resource(name="menu_textService")
	private Menu_textService menu_textService;
	
	/**
	 * type"各种文本说明：
	 *  1.余额说明
		 2.星级说明
		 3.提现说明
		 4.购买说明
		 5.魅力值说明
		 6.红包说明
		 7.优惠说明
	 * 魏汉文20160623
	 */
	@RequestMapping(value="/textDesc")
	@ResponseBody
	public Object textDesc() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
				pd=menu_textService.findByType(pd);
				if(pd == null ){
					pd = new PageData();
				}
 		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
  		return map;
	}
	
	
	
	/**
	 * 领取现金/折扣红包针对--商家详情红包，附近红包
	 * app_shouye/getRedPackage.do
	 * 
	 * member_id  会员ID
	 * store_redpackets_id 商家发的红包ID
	 * money  领取金额/折扣
	 */
	@RequestMapping(value="/getRedPackage")
	@ResponseBody
	public  Object getRedPackage() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
  		String result = "1";
		String message="领取成功";
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
  				if(pd.getString("member_id") == null || pd.getString("member_id").equals("")){
					map.put("result", "0");
					map.put("message", "请先前往登录");
					map.put("data", "");
			  		return map;
				}
  				BaseController.lock.lock();
 				//判断是否已经领取过当前红包
				pd.put("isok", "0");
				PageData redpd=appMemberService.findRePackagedById(pd);
				if(redpd != null){
					map.put("result", "0");
					map.put("message", "很抱歉，你已领取过当前红包");
					map.put("data", "");
			  		return map;
				}
  				//-----------------------------------------------
				String getmoney=pd.getString("money");//领取的红包金额/折扣数
				//获取红包ID
				PageData e=new PageData();
				e=appStore_redpacketsService.findRedById(pd);
				if(e != null && getmoney != null){
					getmoney=StringUtil.getMoveFloatLastZero(getmoney);//去除小数点后的0
					//判断当前红包是否充足
 					double overget_money=Double.parseDouble(e.getString("overget_money"));//已领金钱总金钱
					int redpackage_number=Integer.parseInt(e.getString("redpackage_number"));//总红包
					int overget_number=Integer.parseInt(e.getString("overget_number"));//已领红包
					if(redpackage_number == overget_number  ){
						  result="0";
						  message="很抱歉，你晚了一步，红包已被领完";
					}else{
 						String srp_usercondition_id=e.getString("srp_usercondition_id");
						String srp_usercondition_content=e.getString("srp_usercondition_content");
						if( srp_usercondition_content == null || srp_usercondition_content.equals("") ){
							srp_usercondition_content="";
						}
						String redpackage_type=e.getString("redpackage_type");
						String choice_type=e.getString("choice_type");
 						if(redpackage_type.equals("1")){
 							srp_usercondition_content=srp_usercondition_content+"减"+getmoney+"元";
 						}else if(redpackage_type.equals("2")){
 							srp_usercondition_content=srp_usercondition_content+"打"+getmoney+"折";
 							if(choice_type.equals("2")){
								srp_usercondition_id=srp_usercondition_id+"1";
							}
 						}
 						//新增红包---会员
 						PageData e2=new PageData();
						e2.put("redpackage_id","member"+BaseController.getTimeID());
						e2.put("member_id", pd.getString("member_id"));
						e2.put("redpackage_money", getmoney);
						e2.put("redpackage_content", srp_usercondition_content);
						e2.put("store_redpackets_id", e.getString("store_redpackets_id"));
						e2.put("redpackage_type", srp_usercondition_id);
						e2.put("enddate", e.get("endtime").toString());
						e2.put("startdate", e.get("starttime").toString());
						e2.put("set_id", e.getString("store_id"));
						e2.put("set_type", "1");//商家
						appMemberService.saveRedForMember(e2);
						//更新会员拥有的红包数量
						ServiceHelper.getAppMemberService().updateMemberRedNumber(e2);
						e2=null;
					   //修改红包数量以及金钱
					    e.put("overget_number", Integer.parseInt(e.getString("overget_number"))+1+"");
						e.put("overget_money",overget_money+Double.parseDouble(getmoney)+"");
						appStore_redpacketsService.edit(e);
 	 				}
				}
				e=null;
  		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}finally{
			BaseController.lock.unlock();
 		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
  		return map;
	}
	
	
	/**
	 * 推荐好友注册
	 * app_shouye/tuijianRegister.do
	 * 
	 * id 当前用户ID
	 * content 推荐内容
	 * be_phone 被推荐人的手机号码
 	 */
	@RequestMapping(value="/tuijianRegister")
	@ResponseBody
	public Object tuijianRegister() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
 		String result = "1";
		String message="推荐等待确认中";
		PageData pd = new PageData();
		try{ 
			pd = this.getPageData();
			String member_id=pd.getString("id");
			String content=pd.getString("content");
			String be_phone=pd.getString("be_phone");
			pd.put("phone", be_phone);
 			///判断是否注册过
 			if(appMemberService.detailByPhone(pd) != null){
				map.put("result", "0");
				map.put("message", "当前推荐手机号已注册，请重新填写");
				map.put("data", "");
 		 		return map;
			}
 			pd.remove("phone");
 			pd.put("member_id", member_id);
 			if(appMemberService.findById(pd) != null){
 				//判断是否已经推荐过
 				if(appMemberService.findDetailTuiJian(pd) == null){
 					SmsUtil.TjFrinendSave(be_phone, appMemberService.findById(pd).getString("phone"),content);
 					//魅力值：0-50一星会员 50-99 二星会员 100-199 三星会员 200-499  四星会员 500-999 五星会员 1000-2000 一钻会员
 					TongYong.charm_numberAdd(member_id, Const.charm_number[2]);
 	 	 			//将邀请信息存入数据库中
 					pd.put("type", "2");
 					appMemberService.saveTuiJian(pd);
 				} 
 			}else{
				map.put("result", "0");
				map.put("message", "请先前往登录，再推荐好友注册");
				map.put("data", "");
 		 		return map;
			}
 		}catch(Exception e){
			 result = "0";
			 message="系统错误";
			logger.error(e.toString(), e);
		}
 		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
   		return map;
	}
	
	
	
	/**
	 * 扫一扫二维码，前往商家支付（store_id）
	 * 魏汉文20160623
	 */
	@RequestMapping(value="/storePay")
	@ResponseBody
	public Object gostorePay(){
 		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1= new HashMap<String,Object>();
 		List<PageData> yingxiaoList=new ArrayList<PageData>();//用来存储商家List
		String result = "1";
		String message="前往支付页面";
		PageData pd = new PageData();
		try{ 
				pd = this.getPageData();
				String member_id=pd.getString("member_id");
				pd=appStoreService.findNameById(pd);
				map1.put("storeInfor", pd);
 				//获取营销规则
 				List<PageData> marketlist=appStorepc_marketingService.listAllById(pd);
  				String add="";
 				String reduce="";
 				String zk="";
 				for(PageData e2 : marketlist){
							/*
							 * **1-满赠，*2-满减，3-时段营销，4-买N减N（针对商品），5-累计次数/购买金额--增，6-积分收益，*7-折扣设置
							 */
							String marketing_type=e2.getString("marketing_type");
							String grantrule=e2.getString("grantrule");
							if(marketing_type.equals("1")){
 								add+=grantrule+",";
							}else if(marketing_type.equals("2")){
								reduce+=grantrule+",";
							}
//							else if(marketing_type.equals("3")){
//								time+=grantrule+",";
//							}else if(marketing_type.equals("4")){
//								n+=grantrule+",";
//							}else if(marketing_type.equals("5")){
//								number+=grantrule+",";
//							}else if(marketing_type.equals("6")){
//								score+=grantrule+",";
//							}
							else if(marketing_type.equals("7")){
								zk+=grantrule+",";
							}
				}
 				marketlist=null;
 				PageData newpd=new PageData();
 				if(!add.equals("")){
 					newpd.put("content", add.subSequence(0, add.length()-2));
 					newpd.put("number", "0");
  					yingxiaoList.add(newpd);
 				}
 				newpd=null;
 				newpd=new PageData();
 				if(!reduce.equals("")){
 					newpd.put("content", reduce.subSequence(0, reduce.length()-2));
 					newpd.put("number", "0");
  					yingxiaoList.add(newpd);
 				}
// 				if(!time.equals("")){
// 					newpd.put("content", time.subSequence(0, time.length()-2));
// 					newpd.put("number", "0");
//  					yingxiaoList.add(newpd);
// 				}
// 				if(!n.equals("")){
// 					newpd.put("content", n.subSequence(0, n.length()-2));
// 					newpd.put("number", "0");
//  					yingxiaoList.add(newpd);
// 				}
// 				if(!number.equals("")){
// 					newpd.put("content", number.subSequence(0, number.length()-2));
// 					newpd.put("number", "0");
//  					yingxiaoList.add(newpd);
// 				}
// 				if(!score.equals("")){
// 					newpd.put("content", score.subSequence(0, score.length()-2));
// 					newpd.put("number", "0");
//  					yingxiaoList.add(newpd);
// 				}
 				newpd=null;
 				newpd=new PageData();
 				if(!zk.equals("")){
 					newpd.put("content", zk.subSequence(0, zk.length()-2));
 					newpd.put("number", "0");
  					yingxiaoList.add(newpd);
 				}
 				newpd=null;
 				newpd=new PageData();
 				map1.put("yingxiaoList", yingxiaoList);//优惠
 				yingxiaoList=null;
 				//个人财富
 				pd.put("member_id", member_id);
 				PageData mpd=appMemberService.findWealthById(pd);
 				map1.put("member", mpd);
 				mpd=null;
 		} catch(Exception e){
			result = "0";
			message="系统错误";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
		pd=null;
		return map;
	}
 
	
}
