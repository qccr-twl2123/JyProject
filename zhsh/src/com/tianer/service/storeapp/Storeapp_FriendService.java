package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("storeAppFriendService")
public class Storeapp_FriendService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*获取朋友列表魏汉文20160706(用来人脉查询判断)
	*/
	public List<PageData> listAllFriend(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("storetAppFriendMapper.listAllFriend", pd);
	}
	
	
	/*
	 *商家人脉全部列表 刘耀耀20160706
	 */
	public List<PageData> listContacts(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("storetAppFriendMapper.listContacts", pd);
	}
	
	
}

