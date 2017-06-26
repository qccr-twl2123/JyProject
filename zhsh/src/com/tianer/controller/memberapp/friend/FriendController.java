package com.tianer.controller.memberapp.friend;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.controller.memberapp.tongyongUtil.TongYong;
import com.tianer.service.memberapp.AppFriendService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.GetFirstZiMu;
import com.tianer.util.PageData;

/** 
 * 类名称：FriendController
 * 创建人：刘耀耀
 * 创建时间：2016-06-30
 */
@Controller("appFriendController")
@RequestMapping(value="/app_friend")
public class FriendController extends BaseController {
	
	@Resource(name="appFriendService")
	private AppFriendService appFriendService;
	
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	
	/**
	 * 推荐手机联系人列表
	 * 魏汉文20160630
	 * app_friend/tuiJianPhoneList.do?id=jy15260282340c811&phoneList=+86\\U00a0156-0002-7477,156-1756-5691,+86\U00a0187-9027-7170
	 * id  phoneList
	 */
	@RequestMapping(value="/tuiJianPhoneList")
	@ResponseBody
	public Object tuiJianList(){
//		//logBefore(logger, "推荐手机联系人列表");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1= new HashMap<String,Object>();
		List<PageData> phoneList=new ArrayList<PageData>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
 			String id=pd.getString("id");
			String sss=pd.getString("phoneList");
 			String[] str=sss.split(",");
 			PageData e=null;
 			for(int i=0;i<str.length;i++){
					String isphone=str[i];
					String chuli_phone=isphone.replace("+", " ").replaceAll("-", " ").replaceAll(" 86", "").replaceAll(" 86", "").replaceAll("\\\\U00a0", " ").replaceAll(" ", "");
					e=new PageData();
 					e.put("phone",chuli_phone);
					//判断推荐的人是否注册过
 					if(appMemberService.detailByPhone(e) == null){
							boolean flag=false;
 							pd.put("be_phone", chuli_phone);
							List<PageData> tuijianList=appMemberService.listAllTuiJian(pd);
							for(PageData tjpd : tuijianList){
								if(tjpd.getString("id").equals(id)){
									flag=true;
								}
 							}
							if(flag){
								e.put("status", Const.friendthree);//已推荐，未注册
							}else{
								e.put("status", Const.friendone);//未推荐，未注册
							}
 					}else{
							if(appMemberService.detailByPhone(e).getString("recommended").equals(id)){
								e.put("status", Const.friendfour);//已注册，推荐成功
							}else{
								e.put("status", Const.friendtwo);//已注册，推荐失败
							}
	 				}
 					e.put("phone", isphone);
					phoneList.add(e);
					e=null;
			}
			//循环遍历放进kEY中
			for(PageData e1 : phoneList){
				if(e1.getString("phone").contains("86 ") || e1.getString("phone").contains("86\\")){
					e1.put("phone", "+"+e1.getString("phone").trim());
				}
				map1.put(e1.getString("phone"), e1.getString("status"));
			}
			phoneList=null;
			pd=null;
  		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
		return map;
	}
	
	
	
	@Resource(name="storepc_wealthhistoryService")
	private Storepc_wealthhistoryService storepc_wealthhistoryService;
	
	/**
	 * 会员人脉全部列表
	 * 魏汉文20160701
	 * 
	 */
	@RequestMapping(value="/listContacts")
	@ResponseBody
	public Object listContacts(){
//		//logBefore(logger, "列表Contacts");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1= new HashMap<String,Object>();
		List<PageData>	firstcontacts =new ArrayList<PageData>();
		List<PageData>	twocontacts =new ArrayList<PageData>();
		String result = "1";
		String message="查询成功";
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String member_id=pd.getString("member_id");
			//获取一度人脉二度人脉消费所获取到的积分
			PageData w_mpd=appMemberService.findById(pd);
			if(w_mpd == null){
				map.put("result", "0");
				map.put("message", "请先前往登录");
				map.put("data", "");
				return map;
			}
			map1.put("firstcountScore", w_mpd.getString("onecontactintegral"));
			map1.put("twocountScore", w_mpd.getString("twocontactintegral"));
    		pd.put("onecontacts_id", member_id);
   			if(storepc_wealthhistoryService.getContantSumMoney(pd) != null){
   				String firstmoney=storepc_wealthhistoryService.getContantSumMoney(pd).getString("sumonecontacts_getmoney");
   				pd.remove("onecontacts_id");
   				pd.put("twocontacts_id", member_id);
   				if(storepc_wealthhistoryService.getContantSumMoney(pd) != null){
   					String twomoney=storepc_wealthhistoryService.getContantSumMoney(pd).getString("sumtwocontacts_getmoney");
   					map1.put("twocountScore",twomoney);
   				}else{
   					map1.put("twocountScore","0");
   				}
    			map1.put("firstcountScore", firstmoney);
   			}else{
   				map1.put("firstcountScore", "0");
   	  			map1.put("twocountScore","0");
   			}
 			PageData cpd1=new PageData();
			cpd1.put("contacts_parent_id", member_id);
 			List<PageData>	varList = appFriendService.listContacts(cpd1);	//获得所有的一级人脉
   			for(PageData e :  varList){
	   				String contacts_id=e.getString("contacts_id");
	   				String contacts_parent_id=e.getString("contacts_parent_id");
	   				String contacts_type=e.getString("contacts_type");
	 				 if(contacts_type.equals("1")){//商家
	 					 //System.out.println("会员的一度人脉是商家。。。NO");
	 					 continue;
	   				 }else{//会员
	   					 	e.clear();
 		 					e.put("member_id",contacts_id);
							e=appMemberService.contactMember(e);
							if(e != null){
								if(e.getString("name").length() == 11){
									e.put("name", e.getString("name").substring(0, 3)+"****"+e.getString("name").substring(7, 11));
								}
								//member为邀请人e.member为被邀请人
								pd.put("be_invite_id",contacts_id);
								pd.put("invite_id", contacts_parent_id);
								List<PageData>	frindList = appFriendService.listAllFriend(pd);	//列出contacts列表
								if(frindList.size() == 0){
									//store为邀请人.member为被邀请人
										pd.put("invite_id", contacts_id);
										pd.put("be_invite_id", contacts_parent_id);
										frindList = appFriendService.listAllFriend(pd);	//列出contacts列表
										if(frindList.size() == 0){
											e.put("status", Const.contacttwo);
										}else{
											String friend_status=frindList.get(0).getString("friend_status");
											if(friend_status.equals("2")){
												e.put("status", Const.contactone);
											}else if(friend_status.equals("1")){
												e.put("status", Const.contactthree);
											}
										}
								}else{
										String friend_status=frindList.get(0).getString("friend_status");
										if(friend_status.equals("2")){
											e.put("status", Const.contactone);
										}else if(friend_status.equals("1")){
											e.put("status", Const.contacttwo);
										}
								}
								e.remove("contacts_parent_id");
								e.put("type", "2");
								firstcontacts.add(e);
	 							//获取二度人脉
								e=null;
								e=new PageData();
 								e.put("contacts_parent_id",contacts_id);
 								List<PageData> twoContacts= appFriendService.listContacts(e);	//获得二度人脉列表
  		 						for(PageData twopd: twoContacts){
	 		 								String twocontacts_id=twopd.getString("contacts_id");
	 		 								String twocontacts_parent_id=twopd.getString("contacts_parent_id");
	 		 								String twocontacts_type=twopd.getString("contacts_type");
											if(twocontacts_type.equals("1")){//商家
												//System.out.println("会员二度人脉是商家。。。NO");
												continue;
	 		 								}else{//会员
	 		 										twopd.clear();
 		 											twopd.put("member_id", twocontacts_id);
 		 											twopd=appMemberService.contactMember(twopd);
		 											if(twopd != null ){
			 												if(twopd.getString("name").length() == 11){
			 													twopd.put("name", twopd.getString("name").substring(0, 3)+"****"+twopd.getString("name").substring(7, 11));
			 												}
			 												//当前member为邀请人.member为被邀请人
															pd.put("be_invite_id", twocontacts_id);
															pd.put("invite_id",twocontacts_parent_id);
															List<PageData>	twoList = appFriendService.listAllFriend(pd);	//列出contacts列表
															if(twoList.size() == 0){
																//member为邀请人.当前member为被邀请人
																	pd.put("invite_id",twocontacts_id);
																	pd.put("be_invite_id",twocontacts_parent_id);
																	twoList = appFriendService.listAllFriend(pd);	//列出contacts列表
																	if(twoList.size() == 0){
																		twopd.put("status", Const.contacttwo);
																	}else{
																		String friend_status=twoList.get(0).getString("friend_status");
																		if(friend_status.equals("2")){
																			twopd.put("status", Const.contactone);
																		}else if(friend_status.equals("1")){
																			twopd.put("status", Const.contactthree);
																		}
 																	}
															}else{
																	String friend_status=twoList.get(0).getString("friend_status");
																	if(friend_status.equals("2")){
																		twopd.put("status", Const.contactone);
																	}else if(friend_status.equals("1")){
																		twopd.put("status", Const.contacttwo);
																	}
															}
			 												twopd.put("type", "2");
 															twocontacts.add(twopd);
		 								      }
 					 				}
									twopd=null;
								}
 		 						e=null;
							}
  					}
  			}
  			map1.put("firstcontacts", firstcontacts);
  			map1.put("twocontacts", twocontacts);
  			map1.put("firstcount", firstcontacts.size()+"");
   			map1.put("twocount", twocontacts.size()+"");
 		} catch(Exception e){
			result = "0";
			map.put("data", "");
			message="查询异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
		return map;
	}
	
	
     /**
      * 
     * 方法名称:：myFriendList 
     * 方法描述：我的好友列表
     * 创建人：魏汉文
     * 创建时间：2016年7月1日 下午6:45:11
     * app_friend/myFriendList.do
      */
	@RequestMapping(value="/myFriendList")
	@ResponseBody
	public Object myFriendList(){
//		//logBefore(logger, "我的好友列表");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
   		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try{ 
			pd=this.getPageData();
			//获取推荐人信息(判断是否有登录)
 			if( pd.getString("member_id") == null ||  pd.getString("member_id").equals("")){
				result="0";
				message="请先前往登录！";
				map.put("data",  pd.getString("member_id"));
				map.put("result", result);
				map.put("message", message);
				return map;
			}else{
					PageData mpd=appMemberService.findById(pd);
					if(mpd != null ){
							PageData recompd=new PageData();
						    String recommended_type=mpd.getString("recommended_type");
						    String recommended=mpd.getString("recommended");
							if(recommended_type != null && recommended_type.equals("1")){
 								recompd.put("store_id", recommended);
								recompd=appStoreService.contactStore(recompd);
								if(recompd != null){
 									recompd.put("type", "1");
									map1.put("user", recompd);
 								}else{
									recompd=new PageData();
									recompd.put("type", "1");
	 								recompd.put("image_url", "");
	 								recompd.put("name", "");
	 								recompd.put("id",recommended );
									map1.put("user", recompd);
								}
  							}else if(recommended_type.equals("2")){
 								recompd.put("member_id", recommended);
 								recompd=appMemberService.contactMember(pd);
								if(recompd != null){
									recompd.put("type", "2");
									map1.put("user", recompd);
								}else{
									recompd=new PageData();
									recompd.put("type", "1");
	 								recompd.put("image_url", "");
	 								recompd.put("name", "");
	 								recompd.put("id",recommended );
									map1.put("user", recompd);
								}
 							}else{
 								recompd.put("type", "2");
 								recompd.put("image_url", mpd.getString("image_url"));
 								recompd.put("name", mpd.getString("name"));
 								recompd.put("id", mpd.getString("member_id"));
								map1.put("user", recompd);
							}
 					}else{
 						result="0";
 						message="当前用户不存在！";
 						map.put("data",  pd.getString("member_id"));
 						map.put("result", result);
 						map.put("message", message);
 						return map;
 					}
			}
 			List<PageData>	allList=TongYong.getFriendList(pd.getString("member_id"));
			try {
				if(allList.size() > 0){
					GetFirstZiMu obj1 = new GetFirstZiMu();
					Map<String,List<PageData>> mapTwo=obj1.sortTwo(allList,"name");
		 			List<Map<String,List<PageData>> >	endList=new ArrayList<Map<String,List<PageData>> >();
		 			endList.add(mapTwo);
		 			map1.put("endList", endList);
				}else{
					map1.put("endList", new ArrayList<PageData>());
				}
 			} catch (Exception e) {
				// TODO: handle exception
				//System.out.println("出错账号"+pd.getString("member_id"));
				logger.error(e.toString(), e);
			}
 			map1.put("alllist", allList);
    		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
		return map;
	}
	
	/**
	 * 申请互动
	 * 魏汉文20160704
	 */
	@RequestMapping(value="/saveFriend")
	@ResponseBody
	public Object saveFriend() throws Exception{
//		//logBefore(logger, "申请互动");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="等待通过中";
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData>	invite_List = appFriendService.myFriendList(pd);	//获得所有被邀请好友的列表
		if(invite_List.size() == 0){
			pd.put("friend_status", "2");
			appFriendService.saveFriend(pd);
		}else{
			message="已经申请互动过了";
		}
  		map.put("result", result);
		map.put("message", message);
		map.put("data", "");		
		return map;
	}
	

	/**
	 *通过 好友请求 ，成为好友
	 */
	@RequestMapping(value="/updateFriend")
	@ResponseBody
	public Object updateFriend() throws Exception{
		//logBefore(logger, "申请互动");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="通过申请";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
	 		appFriendService.updateFriend(pd);
		} catch (Exception e) {
			// TODO: handle exception
		}
  		map.put("result", result);
		map.put("message", message);
		map.put("data", "");		
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 分享的url地址：跳去会员注册页面
	 * 魏汉文20160630
	 */
	@RequestMapping(value="/showUrlForOther")
	@ResponseBody
	public Object showUrlForOther(){
//		//logBefore(logger, "分享的url地址：跳去注册");
		Map<String,Object> map = new HashMap<String,Object>();
  		String result = "1";
		String message="分享成功";
		PageData pd = new PageData();
		try{ 
 			String url="www.jiuyuvip.com/zhsh/memberpc/goMemberRegister.do";//会员注册地址
			map.put("data", url);
		} catch(Exception e){
			result = "0";
			message="查询失败";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
 		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 
	 * 查询好友:content,id 
	   修改 接口：app_friend/selFriend.do
	   传字段：     content=搜索字段，id=当前登录账号id
	   
	   判断依据： message_status=0：表示未注册会员
	   			message_status=1：已注册会员/商家，但是未成为好友
	   			message_status=2：已经是好友关系
	 */
	@RequestMapping(value="/selFriend")
	@ResponseBody
	public Object selFriend() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="";
		String message_status="";//0-无，1-为成为好友，2-已经是好友 
		String sousuo_id="";
		PageData pd = new PageData();
		PageData mpd = new PageData();
		PageData spd = new PageData();
 		try {
			pd = this.getPageData();
			String id=pd.getString("id");
			boolean flag=false;
 			pd.put("mpd", "");
			pd.put("spd", "");
  			mpd = appFriendService.selmFriend(pd);	//会员信息
			if(mpd == null){
 				spd = appFriendService.selsFriend(pd);  //商家信息
		 		if(spd != null){
		 			pd.put("spd", spd);
					sousuo_id=spd.getString("store_id");
 	 			}else{
 	 				flag=true;
 	 			}
 			}else{
 				pd.put("mpd", mpd);
 				sousuo_id=mpd.getString("member_id");
  			}
			if(flag){
				 message="当前账号未注册";
				 message_status="0";
			}else{
				message_status="1";
				if(sousuo_id.equals(id)){
		 			message_status="2";
		 			message="当前账号是自己";
		 		}else{
		 			message="当前账号不是好友关系";
		 			//判断之间的关系
			 		PageData isfriend=new PageData();
			 		isfriend.put("invite_id", id);
			 		isfriend.put("be_invite_id", sousuo_id);
			 		isfriend=appFriendService.bothFriend(isfriend);
			 		if(isfriend != null){
			 			String friend_status=isfriend.getString("friend_status");
			 			if(friend_status.equals("2")){
			 				message_status="2";
			 				message="当前账号已经是好友关系";
			 			} 
			 		}else{
			 			isfriend=new PageData();
			 			isfriend.put("invite_id", sousuo_id);
				 		isfriend.put("be_invite_id", id);
				 		isfriend=appFriendService.bothFriend(isfriend);
				 		if(isfriend != null){
				 			String friend_status=isfriend.getString("friend_status");
				 			if(friend_status.equals("2")){
				 				message_status="2";
				 				message="当前账号已经是好友关系";
				 			} 
				 		}
			 		}
		 		}
			}
  	 		pd.put("message_status", message_status);
 		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
 		}
   		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	
	/**
	 * 
	 * 查询好友:content,id 
	 *   修改 接口：app_friend/newSearchFriend.do
	 *  传字段：content=搜索字段，id=当前登录账号id
	 *  
	 *  判断依据：    message_status=0：表示未注册会员
	 *  			message_status=1：已注册会员/商家，但是未成为好友
	 *  			message_status=2：已经是好友关系
	 */
	@RequestMapping(value="/newSearchFriend")
	@ResponseBody
	public Object NewSearchFriend() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="";
		String message_status="";//0-无，1-为成为好友，2-已经是好友 
		String sousuo_id="";
		PageData pd = new PageData();
		PageData mpd = new PageData();
		PageData spd = new PageData();
 		try { 
 			
 		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
 		}
   		map.put("result", result);
		map.put("message", message);
		map.put("data", pd);
		return map;
	}
	
	/**
	 * 添加好友
	 */
	@RequestMapping(value="/tjFriend")
	@ResponseBody
	public Object tjFriend() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="申请成功，请等待回应";
		PageData pd = new PageData();
 		try {
				pd = this.getPageData();
				if(pd.getString("invite_id").equals(pd.getString("be_invite_id"))){
					map.put("result", "0");
					map.put("message", "不能添加自己为好友");
					map.put("data", "");		
					return map;
				}
				PageData pgpd = appFriendService.findByFriend(pd);
				if(pgpd == null){
					pd.put("friend_status", "1");
					appFriendService.saveFriend(pd);
				}else{ 
					if(pgpd.getString("friend_status").equals("1")){
						//删除这条记录
						appFriendService.deleteFriendTongzhi(pd);
						//添加这条记录
						pd.put("friend_status", "1");
						appFriendService.saveFriend(pd);
					}
 				}
   		} catch (Exception e) {
			result = "0";
			message="添加失败，请联系管理员";
			e.printStackTrace();
		}
  		map.put("result", result);
		map.put("message", message);
		map.put("data", "");		
		return map;
	}
	
	/**
	 * 拒绝好友
	 */
	@RequestMapping(value="/refuseFriend")
	@ResponseBody
	public Object refuseFriend() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="拒绝成功";
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			appFriendService.refuseFriend(pd);
		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
  		map.put("result", result);
		map.put("message", message);
		map.put("data", "");		
		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 删除通知信息
	 * app_friend/deleteFriendTongzhi.do
	 * 
	 * friend_id  当前信息的唯一标识
	 */
	@RequestMapping(value="/deleteFriendTongzhi")
	@ResponseBody
	public Object deleteFriendTongzhi() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除成功";
		PageData pd = new PageData();
 		try {
			pd = this.getPageData();
			appFriendService.deleteFriendTongzhi(pd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
 		}
  		map.put("result", result);
		map.put("message", message);
		map.put("data", "");		
		return map;
	}
	
	/**
	 * 验证消息列表
	 * app_friend/listFriend.do?id=
	 * id  当前登录人的ID，商家-store_id，会员-member_id
	 * 
	 * 返回数据：
	 * mList2,sList2  表示当前用户==是被邀请人====数据为邀请你人的信息
	 * mList1,sList1  表示当前用户==是邀请人======数据为被邀请人信息
	 */
	@RequestMapping(value="/listFriend")
	@ResponseBody
	public Object listFriend() throws Exception{
 		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		String result = "1";
		String message="查询成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//我是邀请人
			pd.put("invite_id", pd.getString("id"));
			pd.put("friend_status","2");
			List<PageData>	mList1 = appFriendService.listmFriend(pd);	//会员
			if(mList1.size() == 0){
				map1.put("mList1", "");
			}else{
				for (PageData e : mList1) {
					String name=e.getString("name");
 					if(name != null && name.length()==11){
						e.put("name", name.substring(0, 3)+"****"+name.substring(7, 11));
					}
 				}
				map1.put("mList1", mList1); 
			}
   			List<PageData>	sList1 = appFriendService.listsFriend(pd);	//商家
 			if(sList1.size() == 0){
 				map1.put("sList1", "");
			}else{
 				map1.put("sList1", sList1);
			}
 			//我是被邀请人
 			pd.remove("invite_id");
 			pd.remove("friend_status");
			pd.put("be_invite_id", pd.getString("id"));
			List<PageData>	mList2 = appFriendService.listmFriend(pd);	//会员
			if(mList2.size() == 0){
				map1.put("mList2", "");
			}else{
				for (PageData e : mList1) {
					String name=e.getString("name");
 					if(name != null &&  name.length()==11){
						e.put("name", name.substring(0, 3)+"****"+name.substring(7, 11));
					}
 				}
				map1.put("mList2", mList2);
			}
			List<PageData>	sList2 = appFriendService.listsFriend(pd);	//商家
 			if(sList2.size() == 0){
 				map1.put("sList2", "");
			}else{
				map1.put("sList2", sList2);
			}
		} catch (Exception e) {
			result = "0";
			message="查询失败";
			e.printStackTrace();
		}
  		map.put("result", result);
  		map.put("message", message);
		map.put("data", map1);	
		map.put("url", "listFriend.do");	
		return map;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
