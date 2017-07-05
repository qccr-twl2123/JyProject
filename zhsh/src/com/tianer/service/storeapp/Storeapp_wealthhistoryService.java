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
 */
@Service("storeapp_wealthhistoryService")
public class Storeapp_wealthhistoryService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
   	
	
	/*
	*获取所有的财富流水
	*/
	public List<PageData> weallistPage(Page page)throws Exception{
  		return (List<PageData>)dao.findForList("Storeapp_wealthhistoryMapper.weallistPage", page);
	}
	
	/*
	*获取所有的财富流水
	*/
	public PageData DetailWaterOrderById(PageData pd)throws Exception{
  		return (PageData)dao.findForObject("Storeapp_wealthhistoryMapper.DetailWaterOrderById", pd);
	}
	
	
}
