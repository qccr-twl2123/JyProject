package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("appFriendService")
public class AppFriendService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*获取朋友列表魏汉文20160701(用来人脉查询判断)
	*/
	public List<PageData> listAllFriend(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppFriendMapper.listAllFriend", pd);
	}
	
	/*
	 *获取朋友列表魏汉文20160701（好友列表）
	 */
	public List<PageData> myFriendList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppFriendMapper.myFriendList", pd);
	}
	
	
	/*
	 *会员人脉全部列表 魏汉文20160701
	 */
	public List<PageData> listContacts(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppFriendMapper.listContacts", pd);
	}
	
	/*
	*  新增朋友魏汉文20160704
	*/
	public void saveFriend(PageData pd)throws Exception{
		dao.save("AppFriendMapper.saveFriend", pd);
	}
	
	
	/*
	*  更新朋友状态魏汉文20160704
	*/
	public void updateFriend(PageData pd)throws Exception{
		dao.save("AppFriendMapper.updateFriend", pd);
 		//删除所有申请好友请求的消息
		dao.delete("AppFriendMapper.deleteAllFriendTongzhi", pd);
	}
	
	/* 
	 *查询好友
	 */
	public PageData selmFriend(PageData pd)throws Exception{
		return (PageData) dao.findForObject("AppFriendMapper.selmFriend", pd);
	}
	/* 
	 *查询好友
	 */
	public PageData selsFriend(PageData pd)throws Exception{
		return (PageData) dao.findForObject("AppFriendMapper.selsFriend", pd);
	}
	
	/* 
	 *查询好友关系
	 */
	public PageData bothFriend(PageData pd)throws Exception{
		return (PageData) dao.findForObject("AppFriendMapper.bothFriend", pd);
	}
 
	
	public PageData findByFriend(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppFriendMapper.findByFriend", pd);
	}
	
	public void refuseFriend(PageData pd)throws Exception{
		dao.update("AppFriendMapper.refuseFriend", pd);
	}
	
	public void deleteFriendTongzhi(PageData pd)throws Exception{
		dao.delete("AppFriendMapper.deleteFriendTongzhi", pd);
	}
	
	public List<PageData> listmFriend(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppFriendMapper.listmFriend", pd);
	}
	
	public List<PageData> listsFriend(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppFriendMapper.listsFriend", pd);
	}
}

