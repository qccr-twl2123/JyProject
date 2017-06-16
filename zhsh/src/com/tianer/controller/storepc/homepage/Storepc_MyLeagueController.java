package com.tianer.controller.storepc.homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.Page;
import com.tianer.entity.storepc.liangqin.Store_Union;
import com.tianer.service.memberapp.AppFriendService;
import com.tianer.service.memberapp.AppStoreService;
import com.tianer.service.storepc.liangqin.homepage.MyLeagueService;
import com.tianer.util.AppUtil;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;


/**
 * 类名称: Storepc_MyLeagueController 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-14 上午9:40:03	
 * 版本号: v1.0
 */	
@Controller
@RequestMapping(value="/storepc_myleague")
public class Storepc_MyLeagueController extends BaseController{
	
	@Resource(name="myLeagueService")
	private MyLeagueService myLeagueService;
	String temp = "1";

	@RequestMapping(value="/showMessage")  
	public ModelAndView showMessage(Page page) throws Exception{
		ModelAndView modelAndView = this.getModelAndView();
		modelAndView.setViewName("/storepc/business_homepage5");
		return modelAndView;
	}
	
	
	
	@RequestMapping(value="/deleteMember")
	public ModelAndView deleteMember(Page page) throws Exception{
		String fk_store_id = this.getRequest().getParameter("store_id");
		myLeagueService.deleteStoreUnion(fk_store_id);
		return new ModelAndView("redirect:/storepc_myleague/showMessage.do");
	}
	
	@RequestMapping(value="/showMessage2")  
	public ModelAndView showMessage2(Page page) throws Exception{
		ModelAndView modelAndView = this.getModelAndView();
		modelAndView.setViewName("/storepc/business_homepage6");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/shutUnion")
	public ModelAndView shutUnion() throws Exception{
		String fk_store_id = this.getRequest().getParameter("id");
		myLeagueService.deleteStoreUnion(fk_store_id);
		return new ModelAndView("redirect:/storepc_myleague/showMessage.do");
	}
	
	
	@RequestMapping(value="/addUnion")
	public ModelAndView addUnion() throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		try {
			String union_name = this.getRequest().getParameter("union_name");
			String start_union = this.getRequest().getParameter("start_union");
			String end_union = this.getRequest().getParameter("end_union");
			String invite_desc = this.getRequest().getParameter("invite_desc");
 			Store_Union union = new Store_Union();
			union.setStore_union_id(BaseController.getTimeID());
			union.setFk_store_id("1");//假定store_id为1，实际可以从session中拿到
			union.setLeader_store_id("1");//1表示盟主
			union.setInvite_desc(invite_desc);
			union.setName(union_name);
			union.setStarttime(start_union);
			union.setUnion_status("1");//1表示邀请待处理
			union.setType("1");//1表示担当类型为盟主
			union.setAddunion_time(DateUtil.getDay());
			union.setEndtime(end_union);
			myLeagueService.newStoreUnion(union);
		} catch (Exception e) {
			// TODO: handle exception
		}
 		modelAndView.setViewName("storepc/business_success");
		return modelAndView;
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Resource(name="appFriendService")
	private AppFriendService appFriendService;

	@Resource(name="appStoreService")
	private AppStoreService appStoreService;
	
	/**
	 * 
	* 方法名称:：goOwnLeader 
	* 方法描述：去往我是盟主列表
	* 创建人：魏汉文
	* 创建时间：2016年7月12日 下午7:31:27
	 */
	@RequestMapping(value="/goOwnLeader")
	public ModelAndView goOwnLeader(Page page) throws Exception{
		//logBefore(logger, "去往我是盟主列表");
		ModelAndView mv = this.getModelAndView();
		List<PageData> showList=new ArrayList<PageData>();;//所有我参加的联盟信息
		PageData pd = new PageData();
 		try{
			pd = this.getPageData();//默认会有store_id
			List<PageData> leaderList=myLeagueService.unionForLeader(pd);//所有联盟信息
			page.setPd(pd);
			List<PageData> leaderStoreList=myLeagueService.unionlistPageForLeader(page);//所有联盟下的盟友信息
			for(PageData e :leaderList){
					List<PageData> getList=new ArrayList<PageData>();
					boolean flag=false;
		 			for(PageData e1 : leaderStoreList){
		 					String store_union_id=e1.getString("store_union_id");
		 					String addtime=e1.get("addunion_time").toString();
		 					String su_all_id=e1.get("su_all_id").toString();
		 					e1=appStoreService.findById(e1);
		 					if(e1 != null){
			 						//获取一级二级人脉人数
				 					PageData allpd=new PageData();
				 					allpd.put("contacts_parent_id", e1.getString("store_id"));
				 			 		List<PageData>	varList = appFriendService.listContacts(allpd);	//获得所有的一级人脉
				 			 		int first=varList.size();
		 		 			 		int two=0;
				 			 		for(PageData e2 :  varList){
				 							 if(e2.getString("contacts_type").equals("2")){//会员
				 				 							//获取二度人脉
				 											e.put("contacts_parent_id", e.getString("id"));
				 											logger.info("获取二度人脉");
				 											varList= appFriendService.listContacts(e);	//获得二度人脉列表
				 											two+=varList.size();
				 								}
				 					}
				 			 		e1.put("firstnumber", first+"");
				 			 		e1.put("twonumber", two+"");
				 			 		e1.put("addtime", addtime);
				 			 		e1.put("su_all_id", su_all_id);
				 			 		//判断是否是在这个联盟里
									if(store_union_id.equals(e.getString("store_union_id"))){
												flag=true;
												getList.add(e1);
									}
									allpd=null;
									varList=null;
		 					}
 					}
		 			if(flag){
		 				e.put("getList", getList);
	 					showList.add(e);
		 			}
		 			getList=null;
			}
			mv.addObject("leaderList",leaderList);
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
   		mv.addObject("pd",pd);
  		mv.setViewName("/storepc/business_homepage5");
		return mv;
	}
	
	/**
	 * 
	* 方法名称:：goOtherLeader 
	* 方法描述：去往我是盟友列表
	* 创建人：魏汉文
	* 创建时间：2016年7月12日 下午7:31:27
	 */
	@RequestMapping(value="/goOtherLeader")
	public ModelAndView goOtherLeader(Page page) throws Exception{
		//logBefore(logger, "去往我是盟友列表");
		ModelAndView mv = this.getModelAndView();
		List<PageData> showList=new ArrayList<PageData>();;//所有我参加的联盟信息
		PageData pd = new PageData();
 		try{
			pd = this.getPageData();//默认会有store_id
			List<PageData> leaderList=myLeagueService.unionForStore(pd);//所有我参加的联盟信息
			page.setPd(pd);
			List<PageData> leaderStoreList=myLeagueService.unionlistPageForStore(page);//所有我参加的联盟下的所有盟友信息
			for(PageData e :leaderList){
					List<PageData> getList=new ArrayList<PageData>();
					boolean flag=false;
		 			for(PageData e1 : leaderStoreList){
		 					String store_union_id=e1.getString("store_union_id");
		 					String addtime=e1.get("addunion_time").toString();
		 					String su_all_id=e1.get("su_all_id").toString();
 		 					e1=appStoreService.findById(e1);
		 					if(e1 != null){
		 						//获取一级二级人脉人数
			 					PageData allpd=new PageData();
			 					allpd.put("contacts_parent_id", e1.getString("store_id"));
			 			 		List<PageData>	varList = appFriendService.listContacts(allpd);	//获得所有的一级人脉
			 			 		int first=varList.size();
				 			 		int two=0;
			 			 		for(PageData e2 :  varList){
			 							 if(e2.getString("contacts_type").equals("2")){//会员
			 				 							//获取二度人脉
			 											e.put("contacts_parent_id", e.getString("id"));
			 											logger.info("获取二度人脉");
			 											varList= appFriendService.listContacts(e);	//获得二度人脉列表
			 											two+=varList.size();
			 								}
			 					}
			 			 		e1.put("firstnumber", first+"");
			 			 		e1.put("twonumber", two+"");
			 			 		e1.put("addtime", addtime);
			 			 		e1.put("su_all_id", su_all_id);
			 			 		//判断是否是在这个联盟里
								if(store_union_id.equals(e.getString("store_union_id"))){
											flag=true;
											getList.add(e1);
								}
								allpd=null;
								varList=null;
		 					}
		 					
					}
		 			if(flag){
		 				e.put("getList", getList);
	 					showList.add(e);
		 			}
		 			getList=null;
 			}
			mv.addObject("leaderList",showList);
 		} catch(Exception e){
			logger.error(e.toString(), e);
		}
   		mv.addObject("pd",pd);
  		mv.setViewName("/storepc/business_homepage6");
		return mv;
	}
	
  	
	/**
	 * 
	* 方法名称:：delUnionByStore 
	* 方法描述：删除联盟的盟友关系
	* 创建人：魏汉文
	* 创建时间：2016年7月12日 下午8:34:18
	 */
	@RequestMapping(value="/delUnionByStore")
	@ResponseBody
	public Object delUnionByStore() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			myLeagueService.delUnionByStore(pd);
		} catch (Exception e) {
			 result = "0";
			 message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
 		return AppUtil.returnObject(pd, map);
	}
	
	
	/**
	 * 
	 * 方法名称:：delUnionByLeader 
	 * 方法描述：删除联盟 
	 * 创建人：魏汉文
	 * 创建时间：2016年7月12日 下午8:34:18
	 */
	@RequestMapping(value="/delUnionByLeader")
	@ResponseBody
	public Object delUnionByLeader() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="删除成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			myLeagueService.delUnionByLeader(pd);
		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", "");
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 
	 * 方法名称:：findStore 
	 * 方法描述：搜索商家 
	 * 创建人：魏汉文
	 * 创建时间：2016年7月12日 下午8:34:18
	 */
	@RequestMapping(value="/findStore")
	@ResponseBody
	public Object findStore() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			List<PageData> storeList=myLeagueService.findByLikeContent(pd);
			map.put("data", storeList);
 		} catch (Exception e) {
			result = "0";
			message="系统异常";
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("message", message);
 		return AppUtil.returnObject(pd, map);
	}
	
	
	
	/**
	 * 
	* 方法名称:：addStoreUnion 
	* 方法描述：新增商家联盟
	* 创建人：魏汉文
	* 创建时间：2016年7月13日 上午10:14:37
	 */
	@RequestMapping(value="/addStoreUnion")
	public ModelAndView addStoreUnion() throws Exception{
		//logBefore(logger, "去往我是盟友列表");
		ModelAndView mv = this.getModelAndView();
		String store_id="";
		PageData pd = new PageData();
		try{
			pd=this.getPageData();
			store_id=pd.getString("store_id");
			//新增联盟
			String store_union_id=BaseController.getTimeID();
			pd.put("leader_store_id", store_id);
			pd.put("store_union_id", store_union_id);
			myLeagueService.saveLeaderUnion(pd);
			if(!pd.getString("allstore_id").equals("0")){
				String[] allstore_id=pd.getString("allstore_id").split("@");
				for(int i=0 ; i <allstore_id.length ; i++){
					pd.put("store_id", allstore_id[i]);
					pd.put("union_status", "2");
					myLeagueService.saveStoreUnion(pd);
				}
			}
 		}catch (Exception e) {
			e.printStackTrace();
		}
 		mv.setViewName("redirect:goOwnLeader.do?store_id="+store_id);
		return mv;
	}
	

	/**
	 * 
	* 方法名称:：goAddStoreUnion 
	* 方法描述：去新增商家联盟页面
	* 创建人：魏汉文
	* 创建时间：2016年7月13日 上午10:14:37
	 */
	@RequestMapping(value="/goAddStoreUnion")
	public ModelAndView goAddStoreUnion() throws Exception{
		//logBefore(logger, "去往我是盟友列表");
		ModelAndView mv = this.getModelAndView();
 		PageData pd = new PageData();
		try{
			pd=this.getPageData();
			pd=appStoreService.findById(pd);
			if(pd== null){
				pd=new PageData();	
			}
			 mv.addObject("pd", pd);
  		}catch (Exception e) {
			e.printStackTrace();
		}
  		mv.setViewName("/storepc/business_homepage7");
		return mv;
	}
	
	
	
	
	
	
	
	
	
	

}
