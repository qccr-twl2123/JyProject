package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;

/**
 * 管理我的营销（营销记录）
 * 魏汉文
 */
@Service("appStorepc_marketingService")
public class AppStorepc_marketingService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	 *获取商家的所有营销规则（首页获取前两条）
	 *魏汉文20160622
	 */
	public List<PageData> listAllMarketing(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStore_marketingMapper.listAllMarketing", pd);
	}
	
	/*
	 *获取商家的所有营销规则（所有）
	 *魏汉文20160628
	 */
	public List<PageData> listAllById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStore_marketingMapper.listAllById", pd);
	}
	
	
	/*
	 *获取商家的---折扣手段
	 *魏汉文20160628
	 */
	public PageData getZKById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStore_marketingMapper.getZKById", pd);
	}
	
	/*
	 *获取商家的---积分手段
	 *魏汉文20160628
	 */
	public PageData getJfById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStore_marketingMapper.getJfById", pd);
	}
	
	
	/*
	 *获取商家的所有积分
	 *魏汉文20160629
	 */
	public List<PageData> listAllJFById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStore_marketingMapper.listAllJFById", pd);
	}
	

//	/*
//	 *获取商家的所有折扣
//	 *魏汉文20160622
//	 */
//	public List<PageData> listAllZKById(PageData pd)throws Exception{
//		return (List<PageData>)dao.findForList("AppStore_marketingMapper.listAllZKById", pd);
//	}
	
	
}
