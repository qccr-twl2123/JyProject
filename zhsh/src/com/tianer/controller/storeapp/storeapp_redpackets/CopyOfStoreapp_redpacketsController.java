//package com.tianer.controller.storepc.app.xjt.storeapp_redpackets;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.Map.Entry;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.tianer.controller.app.store_redpackets.Store_redpacketsController;
//import com.tianer.controller.base.BaseController;
//import com.tianer.controller.storepc.store_redpackets.Storepc_redpacketsController;
//import com.tianer.entity.Page;
//import com.tianer.service.app.AppStoreService;
//import com.tianer.service.storepc.app.xjt.ChatRedService;
//import com.tianer.service.storepc.app.xjt.Storeapp_redpacketsService;
//import com.tianer.service.storepc.app.xjt.Storeapp_wealthhistoryService;
//import com.tianer.service.storepc.store_redpackets.Storepc_redpacketsService;
//import com.tianer.util.AppUtil;
//import com.tianer.util.Const;
//import com.tianer.util.DateUtil;
//import com.tianer.util.Distance;
//import com.tianer.util.MD5;
//import com.tianer.util.PageData;
//import com.tianer.util.SmsUtil;
//import com.tianer.util.StringUtil;
//
///** 
//* 类名称：Storeapp_redpacketsController
//* 创建人：邢江涛  app端发红包接口
//* 创建时间：2016-06-28 
//*/
//@Controller("storeapp_redpacketsController")
//@RequestMapping(value="/storeapp_redpackets")
//public class CopyOfStoreapp_redpacketsController extends BaseController{
//	
//	@Resource(name="storeapp_redpacketsService")
//	private Storeapp_redpacketsService redpacketsService;
//	@Resource(name = "storeapp_wealthhistoryService")
//	private Storeapp_wealthhistoryService wealthhistoryService;
//	@Resource(name = "ChatRedService")
//	private ChatRedService chatRedService;
//	@Resource(name="appStoreService")
//	private AppStoreService appStoreService;
//	
//  
//	
//	/**
//	 * 查询进入页面时需要显示的数据
//	 * @param page
//	 * @return
//	 */
//	@RequestMapping(value="/listAll")
//	@ResponseBody
//	public Object listAll(Page page){
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			page.setPd(pd);
//			//显示用户范围下拉框内容
//			List<PageData>	varList = redpacketsService.listuser(pd);
//			for(PageData e : varList){
//				//用户范围id 
//				List<PageData> rangeList = redpacketsService.listrange(e);
//				e.put("sortList", rangeList);
//				rangeList=null;
//			}
//			//显示使用条件的下拉框内容
//			List<PageData>	conditionList = redpacketsService.listAll(pd);
//			if(conditionList.size() == 0||varList.size() == 0){
//				message="获取成功";
//				map.put("data", "");
//			}else{
//				map.put("varList", varList);
//				map.put("conditionList", conditionList);
//				varList=null;
//				conditionList=null;
//			}
//		} catch(Exception e){
//			result="0";
//			message="获取异常";
// 			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message",message);
//		return map;
//	}
//	
//	
//	/**
//	 * 查询红包列表
//	 * @param page
//	 * @return
//	 */
//	@RequestMapping(value="/listRedpackets")
//	@ResponseBody
//	public Object listRedpackets(){
//		Map<String,Object> map = new HashMap<String,Object>();
//		String result = "1";
//		String message="获取成功";
//		PageData pd = new PageData();
//		try{
//			pd = this.getPageData();
//			pd.put("store_id", pd.getString("store_id"));
//			//红包列表
//			List<PageData>	varList = redpacketsService.listRedpackets(pd);
//			for (PageData pageData : varList) {
// 				String id = pageData.getString("store_redpackets_id");
//				//过期红包数量
//				String guoqiCount = redpacketsService.guoqiCount(id);
//				pageData.put("guoqiCount", guoqiCount);
//				//已使用红包数量
//				String shiyongCount = redpacketsService.shiyongCount(id);
//				pageData.put("shiyongCount", shiyongCount);
//				//发送范围
//				String content="";
//				if(pageData.getString("srp_opentype_id") != null && pageData.getString("srp_opentype_id").equals("")){
//						String[] str=pageData.getString("srp_opentype_id").split(",");
//						if(str.length > 0){
//							int n = 0;
//							for(int i=0; i<str.length;i++){
//								 String sp=str[i];
//								 if( sp != null && !sp.equals("")){
//									 n=Integer.parseInt(sp);
//								 }
//			 					 content+= Const.srp_opentype[n]+",";
//			 				}
//						}
//						pageData.put("content", content);
//				}
//   			}
//			if(varList.size() == 0||varList.size() == 0){
//				message="获取成功";
//				map.put("data", "");
//			}else{
//				map.put("data", varList);
//				varList=null;
//			}
//		} catch(Exception e){
//			result="0";
//			message="获取异常";
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message",message);
//		return map;
//	}
//	
//	@Resource(name = "storepc_redpacketsService")
//	private Storepc_redpacketsService storepcRedpacketsService;
//	
//	/**
//	 * 发送红包
//	 */
//	@RequestMapping(value="/save")
//	@ResponseBody
//	public Object save() throws Exception{
//		Map<String,Object> map = new HashMap<String,Object>();
//		Map<String , Object> phoneMap = new HashMap<String, Object>();
//		String result = "1";
//		String message="发送成功";
//		PageData pd = new PageData();
//		try {
//			pd = this.getPageData();
// 			//获取当天日期，格式为yyyyMMdd
//			DateUtil date = new DateUtil();
//			String da = DateUtil.getDays();
//			//获取随机6位数
//			int num = (int)((Math.random()*9+1)*100000);
//			//组合为红包编号
//			String id = da +""+ num;
//			pd.put("store_redpackets_id", id);//红包编号
//			//红包类型
////			String redpackage_type = pd.getString("redpackage_type");
////			pd.put("redpackage_type", redpackage_type);
//			//发放金额
//			String money = pd.getString("money");
//			//判断发放金额是否为空
//			if(money == null || money.equals("")){
//					map.put("result", "0");
//					map.put("message", "发放金额不能为空");
//					map.put("data", "");
//			 		return AppUtil.returnObject(pd, map);
//			}
//			pd.put("money", money);
//			//商家id
//			String store_id = pd.getString("store_id");
//			pd.put("store_id", store_id);
// 			//红包个数
//			String redpackage_number = pd.getString("redpackage_number");
//			//判断红包个数是否为空
//			if(redpackage_number == null || redpackage_number.equals("")){
//					map.put("result", "0");
//					map.put("message", "红包个数不能为空");
//					map.put("data", "");
//			 		return AppUtil.returnObject(pd, map);
//			}
//			pd.put("redpackage_number", redpackage_number);
//			//0-普通红包，1-开机红包，2-附近红包，3-聊天红包，4-发送的红包,5赠送红包
//			pd.put("redpackage_status", Const.send_redpackage_status);
// 			//发放规则
//			String choice_type = pd.getString("choice_type");
//			pd.put("choice_type", choice_type);
//			//判断是现金红包还是折扣红包
//			String redpackage_type = pd.getString("redpackage_type");
//			if(redpackage_type.equals("2")){
//				if(money != null && !money.equals("")){
//					double m = Double.parseDouble(money);
//					pd.put("money", m/10);
//				}
//			}
//			//用户范围(无，不做保存)
//			//发放范围
//			String srp_opentype_id = pd.getString("srp_opentype_id");
//			pd.put("srp_opentype_id", srp_opentype_id);
//			String[] str = srp_opentype_id.split(",");
//			List<PageData> renmaiYiList = storepcRedpacketsService.renmaiYiList(pd);//一度人脉集合
//			String code=StringUtil.getSixRandomNum();
//			for (int i = 0; i < str.length; i++) {
//				//一度人脉
//				if(str[i].contains("1")){
//					if(renmaiYiList.size() > 0){
//						for (int j = 0; j < renmaiYiList.size(); j++) {
//								String phone = renmaiYiList.get(j).getString("phone");
//								String member_id = renmaiYiList.get(j).getString("contacts_id"); 
//								if(member_id != null && !member_id.equals("")){
//									phoneMap.put(member_id, phone);
//								}
// 						}
//					}
//				}
//				if(str[i].contains("2")){//二度人脉
//					if(renmaiYiList.size() > 0){
//						for (int j = 0; j < renmaiYiList.size(); j++) {
//							String memberId = renmaiYiList.get(j).getString("contacts_id");
//							PageData twomempd=new PageData();
//							twomempd.put("member_id", memberId);
//							//根据一度人脉的人脉id查询二度人脉的人脉信息
//							List<PageData> renmaiErList = storepcRedpacketsService.renmaiErList(twomempd);
//							if(renmaiErList.size() > 0 ){
//								for (int k = 0; k < renmaiErList.size(); k++) {
//									String phone = renmaiErList.get(k).getString("phone");
//									String member_id = renmaiErList.get(k).getString("contacts_id");
//									if(member_id != null && !member_id.equals("")){
//										phoneMap.put(member_id, phone);
//									}
// 								}
//							}
//							twomempd=null;
//						}
//					}
//				}
//				if(str[i].contains("3")){//收藏本 店会员
//					List<PageData> shoucangList = storepcRedpacketsService.shoucangList(pd);
//					if(shoucangList.size() > 0){
//						for (int j = 0; j < shoucangList.size(); j++) {
//							String phone = shoucangList.get(j).getString("phone");
//							String member_id = shoucangList.get(j).getString("member_id");
//							if(member_id != null && !member_id.equals("")){
//								phoneMap.put(member_id, phone);
//							}
//						}
//					}
//					shoucangList=null;
//				}
//				if(str[i].contains("4")){//500米以内
//					List<PageData> wuList = jingweidu(pd);
//					if(wuList.size() > 0){
//						for (int j = 0; j < wuList.size(); j++) {
//							if(wuList.get(j).get("distance").toString().equals("500")){
//								String phone = wuList.get(j).getString("phone");
//								String member_id = wuList.get(j).getString("member_id");
//								if(member_id != null && !member_id.equals("")){
//									phoneMap.put(member_id, phone);
//								}
//							}
//						}
//					}
//					wuList=null;
//				}
//				if(str[i].contains("5")){//1000米以内
//					List<PageData> yiqList = jingweidu(pd);
//					if(yiqList.size() > 0){
//						for (int j = 0; j < yiqList.size(); j++) {
//							if(yiqList.get(j).get("distance").toString().equals("1000")){
//								String phone = yiqList.get(j).getString("phone");
//								String member_id = yiqList.get(j).getString("member_id");
//								if(member_id != null && !member_id.equals("")){
//									phoneMap.put(member_id, phone);
//								}
//							}
//						}
//					}
//					yiqList=null;
//				}
//				if(str[i].contains("6")){//消费过得会员
//					List<PageData> xiaofeiList = storepcRedpacketsService.xiaofeiList(pd);
//					if(xiaofeiList.size() > 0){
//						for (int j = 0; j < xiaofeiList.size(); j++) {
//							String phone = xiaofeiList.get(j).getString("phone");
//							String member_id = xiaofeiList.get(j).getString("member_id");
//							if(member_id != null && !member_id.equals("")){
//								phoneMap.put(member_id, phone);
//							}
//						}
//					}
//					xiaofeiList=null;
//				}
//				if(str[i].contains("7")){//2000米以内
//					List<PageData> erqList = jingweidu(pd);
//					if(erqList.size() > 0 ){
//						for (int j = 0; j < erqList.size(); j++) {
//							if(erqList.get(j).get("distance").toString().equals("2000")){
//								String phone = erqList.get(j).getString("phone");
//								String member_id = erqList.get(j).getString("member_id");
//								if(member_id != null && !member_id.equals("")){
//									phoneMap.put(member_id, phone);
//								}
//							}
//						}
//					}
//					erqList=null;
//				}
//				if(str[i].contains("8")){//5000米以内
//					List<PageData> wuqList = jingweidu(pd);
//					if(wuqList.size() > 0 ){
//						for (int j = 0; j < wuqList.size(); j++) {
//							if(wuqList.get(j).get("distance").toString().equals("5000")){
//								String phone = wuqList.get(j).getString("phone");
//								String member_id = wuqList.get(j).getString("member_id");
//								if(member_id != null && !member_id.equals("")){
//									phoneMap.put(member_id, phone);
//								}
//							}
//						}
//					}
//					wuqList=null;
//				}
//				if(str[i].contains("9")){//所在县市
//					List<PageData> areaList = storepcRedpacketsService.areaList(pd);
//					if(areaList.size() > 0){
//						for (int j = 0; j < areaList.size(); j++) {
//							String phone = areaList.get(j).getString("phone");
//							String member_id = areaList.get(j).getString("member_id");
//							if(member_id != null && !member_id.equals("")){
//								phoneMap.put(member_id, phone);
//							}
//						}
//					}
//					areaList=null;
//				}
//				if(str[i].contains("10")){//所在城市
//					List<PageData> cityList = storepcRedpacketsService.cityList(pd);
//					if(cityList.size() > 0){
//						for (int j = 0; j < cityList.size(); j++) {
//							String phone = cityList.get(j).getString("phone");
//							String member_id = cityList.get(j).getString("member_id");
//							if(member_id != null && !member_id.equals("")){
//								phoneMap.put(member_id, phone);
//							}
//						}
//					}
//					cityList=null;
//				}
//				if(str[i].contains("11")){//我的盟友的会员
//					String mystore_id=pd.getString("mystore_id");
//					PageData storepd=new PageData();
//						if(!mystore_id.equals("")){
//								storepd.put("store_id", mystore_id);
//								List<PageData> oneUnionList = storepcRedpacketsService.renmaiYiList(storepd);//一度人脉集合
//								if(oneUnionList.size() > 0){
//									for (int j = 0; j < oneUnionList.size(); j++) {
//											String phone = oneUnionList.get(j).getString("phone");
//											String member_id = oneUnionList.get(j).getString("contacts_id"); 
//											if(member_id != null && !member_id.equals("")){
//												phoneMap.put(member_id, phone);
//												storepd.put("member_id", member_id);
//												//根据一度人脉的人脉id查询二度人脉的人脉信息
//												List<PageData> renmaiErList = storepcRedpacketsService.renmaiErList(storepd);
//												if(renmaiErList.size() > 0 ){
//													for (int k = 0; k < renmaiErList.size(); k++) {
//														phone = renmaiErList.get(k).getString("phone");
//														member_id = renmaiErList.get(k).getString("contacts_id");
//														if(member_id != null && !member_id.equals("")){
//															phoneMap.put(member_id, phone);
//														}
//					 								}
//												}
//												renmaiErList=null;
//											}
//			 						}
//								}
//								oneUnionList=null;
//						}
//						storepd=null;
//		}
//				if(str[i].contains("12")){//本店会员
//					List<PageData> memberList = storepcRedpacketsService.memberList(pd);
//					if(memberList.size() > 0){
//						for (int j = 0; j < memberList.size(); j++) {
//							String phone = memberList.get(j).getString("phone");
//							String member_id = memberList.get(j).getString("member_id");
//							phoneMap.put(member_id, phone);
//						}
//					}
//					memberList=null;
//				}
//			}
//			  Set<Entry<String, Object>> entryseSet = phoneMap.entrySet();
//			  for (Entry<String, Object> entry:entryseSet) {
////				   System.out.println(entry.getKey()+","+entry.getValue());
//				   String phone = (String) entry.getValue();
//				   //给会员集合发送短信通知
////				   SmsUtil.sendZcCode(phone, code);
//			  }	
////			//使用条件
////			String srp_usercondition_id = pd.getString("srp_usercondition_id");
////			pd.put("srp_usercondition_id", srp_usercondition_id);
////			//有效开始日期
////			String starttime = pd.getString("starttime");
////			pd.put("starttime", starttime);
////			//有效结束日期
////			String endtime = pd.getString("endtime");
////			pd.put("endtime", endtime);
// 			redpacketsService.save(pd);
//  		} catch (Exception e) {
//			 result = "0";
//			 message="系统异常";
//			e.printStackTrace();
//		}
// 		map.put("result", result);
//		map.put("message", message);
//		map.put("data", "");
// 		return map;
//	}
//	
//	
//	/**
//	 * 
//	* 方法名称:：isRedForMember 
//	* 方法描述：发送范围的筛选
//	* 创建人：魏汉文
//	* 创建时间：2016年7月25日 上午10:19:55
//	 */
//			public boolean isRedForMember(String srp_opentype_id,String nowmember_id,String store_id){
//				Map<String , Object> phoneMap = new HashMap<String, Object>();
//				boolean flag=false;
//		 		PageData pd=new PageData();
//		 		try{
//				 			pd.put("store_id", store_id);
//					 		//发放范围
//					  		String[] str = srp_opentype_id.split(",");
//							List<PageData> renmaiYiList = storepcRedpacketsService.renmaiYiList(pd);//一度人脉集合
//							for (int i = 0; i < str.length; i++) {
// 								//一度人脉
//								if(str[i].contains("1")){
//									if(renmaiYiList.size() > 0){
//										for (int j = 0; j < renmaiYiList.size(); j++) {
//											String phone = renmaiYiList.get(j).get("phone").toString();
//											String member_id = renmaiYiList.get(j).get("contacts_id").toString(); 
//											phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								if(str[i].contains("2")){//二度人脉
//									if(renmaiYiList.size() > 0){
//										for (int j = 0; j < renmaiYiList.size(); j++) {
//											String memberId = renmaiYiList.get(j).getString("contacts_id");
//											PageData twomempd=new PageData();
//											twomempd.put("member_id", memberId);
//											//根据一度人脉的人脉id查询二度人脉的人脉信息
//											List<PageData> renmaiErList = storepcRedpacketsService.renmaiErList(twomempd);
//											if(renmaiErList.size() > 0 ){
//												for (int k = 0; k < renmaiErList.size(); k++) {
//													String phone = renmaiErList.get(k).getString("phone");
//													String member_id = renmaiErList.get(k).getString("contacts_id");
//													if(member_id != null && !member_id.equals("")){
//														phoneMap.put(member_id, phone);
//													}
//				 								}
//											}
//										}
//									}
//								}
//								if(str[i].contains("3")){//收藏本 店会员
//									List<PageData> shoucangList = storepcRedpacketsService.shoucangList(pd);
//									if(shoucangList.size() > 0){
//										for (int j = 0; j < shoucangList.size(); j++) {
//											String phone = shoucangList.get(j).get("phone").toString();
//											String member_id = shoucangList.get(j).get("member_id").toString();
//											phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								if(str[i].contains("4")){//500米以内
//									List<PageData> wuList = jingweidu(pd);
//									for (int j = 0; j < wuList.size(); j++) {
//										if(wuList.get(j).get("distance").toString().equals("500")){
//											String phone = wuList.get(j).get("phone").toString();
//											String member_id = wuList.get(j).get("member_id").toString();
//											phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								if(str[i].contains("5")){//1000米以内
//									List<PageData> yiqList = jingweidu(pd);
//									for (int j = 0; j < yiqList.size(); j++) {
//										if(yiqList.get(j).get("distance").toString().equals("1000")){
//											String phone = yiqList.get(j).get("phone").toString();
//											String member_id = yiqList.get(j).get("member_id").toString();
//											phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								if(str[i].contains("6")){//消费过得会员
//									List<PageData> xiaofeiList = storepcRedpacketsService.xiaofeiList(pd);
//									if(xiaofeiList.size() > 0){
//										for (int j = 0; j < xiaofeiList.size(); j++) {
//											String phone = xiaofeiList.get(j).get("phone").toString();
//											String member_id = xiaofeiList.get(j).get("member_id").toString();
//											phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								if(str[i].contains("7")){//2000米以内
//									List<PageData> erqList = jingweidu(pd);
//									for (int j = 0; j < erqList.size(); j++) {
//										if(erqList.get(j).get("distance").toString().equals("2000")){
//											String phone = erqList.get(j).get("phone").toString();
//											String member_id = erqList.get(j).get("member_id").toString();
//											phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								if(str[i].contains("8")){//5000米以内
//									List<PageData> wuqList = jingweidu(pd);
//									for (int j = 0; j < wuqList.size(); j++) {
//										if(wuqList.get(j).get("distance").toString().equals("5000")){
//											String phone = wuqList.get(j).get("phone").toString();
//											String member_id = wuqList.get(j).get("member_id").toString();
//											phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								if(str[i].contains("9")){//所在县市
//									List<PageData> areaList = storepcRedpacketsService.areaList(pd);
//									if(areaList.size() > 0){
//										for (int j = 0; j < areaList.size(); j++) {
//											String phone = areaList.get(j).get("phone").toString();
//											String member_id = areaList.get(j).get("member_id").toString();
//											phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								if(str[i].contains("10")){//所在城市
//									List<PageData> cityList = storepcRedpacketsService.cityList(pd);
//									if(cityList.size() > 0){
//										for (int j = 0; j < cityList.size(); j++) {
//											String phone = cityList.get(j).get("phone").toString();
//											String member_id = cityList.get(j).get("member_id").toString();
//											 phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								if(str[i].contains("11")){//我的盟友的会员
//									String mystore_id=pd.getString("mystore_id");
//									PageData storepd=new PageData();
//		 							if(!mystore_id.equals("")){
//			 								storepd.put("store_id", mystore_id);
//			 								List<PageData> oneUnionList = storepcRedpacketsService.renmaiYiList(storepd);//一度人脉集合
//			 								if(oneUnionList.size() > 0){
//			 									for (int j = 0; j < oneUnionList.size(); j++) {
//			 											String phone = oneUnionList.get(j).getString("phone");
//			 											String member_id = oneUnionList.get(j).getString("contacts_id"); 
//			 											if(member_id != null && !member_id.equals("")){
//			 												phoneMap.put(member_id, phone);
//			 												storepd.put("member_id", member_id);
//			 												//根据一度人脉的人脉id查询二度人脉的人脉信息
//			 												List<PageData> renmaiErList = storepcRedpacketsService.renmaiErList(storepd);
//			 												if(renmaiErList.size() > 0 ){
//			 													for (int k = 0; k < renmaiErList.size(); k++) {
//			 														phone = renmaiErList.get(k).getString("phone");
//			 														member_id = renmaiErList.get(k).getString("contacts_id");
//			 														if(member_id != null && !member_id.equals("")){
//			 															phoneMap.put(member_id, phone);
//			 														}
//			 					 								}
//			 												}
//			 											}
//			 			 						}
//			 								}
//		 							}
//						}
//								if(str[i].contains("12")){//本店会员
//									List<PageData> memberList = storepcRedpacketsService.memberList(pd);
//									if(memberList.size() > 0){
//										for (int j = 0; j < memberList.size(); j++) {
//											String phone = memberList.get(j).get("phone").toString();
//											String member_id = memberList.get(j).get("member_id").toString();
//											phoneMap.put(member_id, phone);
//										}
//									}
//								}
//								Set<Entry<String, Object>> entryseSet = phoneMap.entrySet();
//								  for (Entry<String, Object> entry:entryseSet) {
//									   System.out.println(entry.getKey()+","+entry.getValue());
//									   //判断这个用户是否满足
//									   if(entry.getKey().equals(nowmember_id)){
//										   flag=true;
//									   }
//								  }	
//  						}
//		 		}catch(Exception e){
//		 			System.out.println(e.toString());
//		 			logger.error(e.toString());
//		 		}
// 				return flag;
//		}
//			
//			
//			/**
//			 * 距离
//			 * @param pd
//			 * @return
//			 * @throws Exception
//			 */
//			public List<PageData> jingweidu(PageData pd) throws Exception{
//				
//				List<PageData> jiluList = new ArrayList<PageData>();//存储距离和手机号
//				PageData sjjw = storepcRedpacketsService.sjjwList(pd);
//				if(sjjw != null){
//					//判断是否定位了
//							boolean isdingwei = true;
//							double sjlongitude = 0.0;
//							double sjlatitude = 0.0;
//							double distance = 0;
//							if(sjjw.getString("longitude") == null || sjjw.getString("longitude").equals("")){
//								isdingwei = false;
//							}else{
//								sjlongitude = Double.parseDouble(sjjw.getString("longitude"));//商家经度
//								sjlatitude = Double.parseDouble(sjjw.getString("latitude"));//商家纬度
//							}
//		 					List<PageData> hyjw = storepcRedpacketsService.hyjwList(pd);
//							if(hyjw.size() > 0){
//								for (int i = 0; i < hyjw.size(); i++) {
//									PageData e = new PageData();
//									e = hyjw.get(i);
//									if(e != null){
//											if(e.getString("longitude") != null && !e.getString("longitude").equals("")){
//														double hylongitude = Double.parseDouble(e.getString("longitude"));//会员经度
//														double hylatitude = Double.parseDouble(e.getString("latitude"));//会员纬度
//														if(isdingwei){
//																double overdistance=Distance.getResult(sjlongitude, hylongitude, sjlatitude, hylatitude);
//																if(distance < overdistance){
//																	hyjw.remove(i);
//																	i=i-1;
//																}else{
//																		e.put("phone", hyjw.get(i).get("phone").toString());
//																		e.put("member_id", hyjw.get(i).get("member_id").toString());
//																		e.put("distance", overdistance+"");
//																		jiluList.add(e);
//																	}
//														}else{
//															e.put("distance", "--");
//														}
//											}
//		 								}
//									}
//									
//							}
//				}
//		 		return jiluList;
//			}
//	
//}
