package com.tianer.controller.storeapp.Storeapp_friend;


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
import com.tianer.service.memberapp.AppFriendService;
import com.tianer.service.memberapp.AppMemberService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storeapp.Storeapp_FriendService;
import com.tianer.service.storepc.store_wealthhistory.Storepc_wealthhistoryService;
import com.tianer.util.AppUtil;
import com.tianer.util.Const;
import com.tianer.util.PageData;

/** 
 * 类名称：FriendController
 * 创建人：刘耀耀
 * 创建时间：2016-07-06
 */
@Controller("Storeapp_friendController")
@RequestMapping(value="/storeapp_friend")
public class Storeapp_friendController extends BaseController {
	
	@Resource(name="storeAppFriendService")
	private Storeapp_FriendService storeAppFriendService;
	
	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	@Resource(name="appMemberService")
	private AppMemberService appMemberService;
	
	
	/**
	 * 
	* 方法名称:：listContacts 
	* 方法描述： 商家人脉全部列表
	* 创建人：魏汉文
	* 创建时间：2016年7月7日 上午11:53:41
	 */
	@RequestMapping(value="/listContacts")
	@ResponseBody
	public Object listContacts(){
//		logBefore(logger, "列表Contacts");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1= new HashMap<String,Object>();
		List<PageData>	firstcontacts =new ArrayList<PageData>();
		List<PageData>	twocontacts =new ArrayList<PageData>();
		String result = "1";
		String message="查询成功";
 		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String store_id=pd.getString("store_id");
			PageData cpd1=new PageData();
			cpd1.put("contacts_parent_id", store_id);
 			List<PageData>	varList = storeAppFriendService.listContacts(cpd1);	//获得所有的一级人脉
   			for(PageData e :  varList){
 				 if(e.getString("contacts_type").equals("2")){//会员
		 					e.put("member_id", e.getString("contacts_id"));
							e=appMemberService.findById(e);
							if(e != null){
								if(e.getString("name").length() == 11){
									e.put("name", e.getString("name").substring(0, 3)+"****"+e.getString("name").substring(7, 11));
								}
								//member为被邀请人e.member为邀请人
								pd.put("be_invite_id", e.getString("id"));
								pd.put("invite_id", store_id);
								List<PageData>	frindList = storeAppFriendService.listAllFriend(pd);	//列出contacts列表
								if(frindList.size() == 0){
									//store为邀请人.member为被邀请人
										pd.put("invite_id", e.getString("id"));
										pd.put("be_invite_id", store_id);
										frindList=null;
										frindList = storeAppFriendService.listAllFriend(pd);	//列出contacts列表
										if(frindList.size() == 0){
											e.put("status", Const.contacttwo);
										}else{
											String friend_status=frindList.get(0).getString("friend_status");
											if(friend_status.equals("1")){
												e.put("status", Const.contactone);
											}else if(friend_status.equals("2")){
												e.put("status", Const.contactthree);
											}
										}
								}else{
										String friend_status=frindList.get(0).getString("friend_status");
										if(friend_status.equals("1")){
											e.put("status", Const.contactone);
										}else if(friend_status.equals("2")){
											e.put("status", Const.contacttwo);
										}
								}
								e.remove("contacts_parent_id");
								e.put("type", "2");
								firstcontacts.add(e);
	 							//获取二度人脉
								e.put("contacts_parent_id", e.getString("member_id"));
								logger.info("获取二度人脉");
								varList=null;
								varList= storeAppFriendService.listContacts(e);	//获得二度人脉列表
								e.remove("contacts_parent_id");
 		 						for(PageData m: varList){
 		 							    PageData mspd=new PageData();
										if(m.getString("contacts_type").equals("2")){//会员
													mspd.put("member_id", m.getString("contacts_id"));
													mspd=appMemberService.findById(mspd);
		 											if(mspd != null ){
			 												if(mspd.getString("name").length() == 11){
			 													mspd.put("name", mspd.getString("name").substring(0, 3)+"****"+mspd.getString("name").substring(7, 11));
			 												}
			 												//当前member为邀请人.member为被邀请人
															pd.put("be_invite_id", mspd.getString("id"));
															pd.put("invite_id",store_id);
															List<PageData>	twoList = storeAppFriendService.listAllFriend(pd);	//列出contacts列表
															if(twoList.size() == 0){
																//member为邀请人.当前member为被邀请人
																	pd.put("invite_id", mspd.getString("id"));
																	pd.put("be_invite_id",store_id);
																	twoList=null;
																	twoList = storeAppFriendService.listAllFriend(pd);	//列出contacts列表
																	if(twoList.size() == 0){
																		mspd.put("status", Const.contacttwo);
																	}else{
																		String friend_status=twoList.get(0).getString("friend_status");
																		if(friend_status.equals("1")){
																			mspd.put("status", Const.contactone);
																		}else if(friend_status.equals("2")){
																			mspd.put("status", Const.contactthree);
																		}
 																	}
															}else{
																	String friend_status=twoList.get(0).getString("friend_status");
																	if(friend_status.equals("1")){
																		mspd.put("status", Const.contactone);
																	}else if(friend_status.equals("2")){
																		mspd.put("status", Const.contacttwo);
																	}
															}
															mspd.put("type", "2");
 															twocontacts.add(mspd);
		 								      }
		 									mspd=null;
 					 				}
								}
							}
  					}
  			}
  			map1.put("firstcontacts", firstcontacts);
  			map1.put("twocontacts", twocontacts);
  			map1.put("firstcount", firstcontacts.size()+"");
   			map1.put("twocount", twocontacts.size()+"");
   			//人脉收益
   			pd.put("twocontacts_id", store_id);
	   		pd.put("onecontacts_id", store_id);
   			if(storepc_wealthhistoryService.getContantSumMoney(pd) != null){
   				String firstmoney=storepc_wealthhistoryService.getContantSumMoney(pd).getString("sumonecontacts_getmoney");
   				String twomoney=storepc_wealthhistoryService.getContantSumMoney(pd).getString("sumtwocontacts_getmoney");
   				map1.put("twocountScore",  firstmoney);
   				map1.put("firstcountScore",twomoney);
   			}else{
   				map1.put("twocountScore",  "0");
   				map1.put("firstcountScore","0");
   			}
 		} catch(Exception e){
			result = "0";
			map.put("data", "");
			message="查询异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", map1);
		return AppUtil.returnObject(pd, map);
	}
	
	
	@Resource(name="storepc_wealthhistoryService")
	private Storepc_wealthhistoryService storepc_wealthhistoryService;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
 
	@Resource(name="appFriendService")
	private AppFriendService appFriendService;
	
	/**
	 * 申请互动
	 * 魏汉文20160704
	 */
	@RequestMapping(value="/saveFriend")
	@ResponseBody
	public Object saveFriend() throws Exception{
//		logBefore(logger, "申请互动");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="等待通过中";
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData>	invite_List = appFriendService.myFriendList(pd);	//获得所有被邀请好友的列表
			if(invite_List.size() == 0){
				pd.put("friend_status", "2");
				appFriendService.saveFriend(pd);
			}else{
				message="已在申请互动中";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
   		map.put("result", result);
		map.put("message", message);
		map.put("data", "");		
		return map;
	}
	
	
	/**
	 *通过申请互动
	 * 魏汉文20160704
	 */
	@RequestMapping(value="/updateFriend")
	@ResponseBody
	public Object updateFriend() throws Exception{
//		logBefore(logger, "申请互动");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="通过申请";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
	 		appFriendService.updateFriend(pd);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
  		map.put("result", result);
		map.put("message", message);
		map.put("data", "");		
		return map;
	}
	
	
	/**
	 *邀请成为本店会员:会员电话，和商家ID
	 * 魏汉文20160704
	 */
	@RequestMapping(value="forStoreVip")
	@ResponseBody
	public Object forStoreVip() throws Exception{
//		logBefore(logger, "邀请等待回复中");
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="邀请等待回复中";
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
  		map.put("result", result);
		map.put("message", message);
		map.put("data", "");		
		return AppUtil.returnObject(pd, map);
	}
	
}
