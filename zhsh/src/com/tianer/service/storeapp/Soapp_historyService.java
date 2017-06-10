package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


/**
 * 
* 类名称：storeapp_redpacketsService   
* 类描述：   app班次汇总
* 创建人：邢江涛 
* 创建时间：2016年6月30日 
 */
@Service("soapp_historyService")
public class Soapp_historyService {
	
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/*
	*班次汇总
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTStorepc_imageMapper.listAll", pd);
	}
	
	/*
	*班次详情
	*/
	public List<PageData> nowlistPageAll(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTStorepc_imageMapper.nowlistPageAll", page);
	}
	
	/*
	*获取现金的总和
	*/
	public String listxianjin(String id)throws Exception{
		return (String) dao.findForObject("XJTStorepc_imageMapper.xianjinCount", id);
	}

	/*
	*获取第三方的总和
	*/
	public String listdisanfang(String id)throws Exception{
		return (String) dao.findForObject("XJTStorepc_imageMapper.disanfangCount", id);
	}
	
	
	/*
	*获取积分的总和
	*/
	public String listjifen(String id)throws Exception{
		return (String) dao.findForObject("XJTStorepc_imageMapper.jifenCount", id);
	}
	
	
	/*
	*获取提货券的总和
	*/
	public String listtihuoCount(String id)throws Exception{
		return  (String) dao.findForObject("XJTStorepc_imageMapper.tihuoquanCount", id);
	}
	
	/*
	*获取提货券的总和
	*/
	public String listtihuoSum(String id)throws Exception{
		return  (String) dao.findForObject("XJTStorepc_imageMapper.tihuoquanSum", id);
	}
	
	/*
	* 操作员信息
	*/
	public List<PageData> listoperator(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTStorepc_imageMapper.listoperator", pd);
	}
	
	/*
	* 操作员信息
	*/
	public List<PageData> shift(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTStorepc_imageMapper.shift", pd);
	}

}
