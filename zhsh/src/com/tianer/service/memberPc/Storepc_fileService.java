package com.tianer.service.memberPc;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("storepc_fileService")
public class Storepc_fileService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 *通过以及分类获取列表 
	 */
	public List<PageData> listAllForCitysort(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Storepc_fileMapper.listAllForCitysort", pd);
	}
	
	/*
	 *商家筛选列表-排序，筛选分类---
	 */
	public List<PageData> getStorelistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Storepc_fileMapper.getStorelistPage", page);
	}
	
	/*
	 *商家详情
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Storepc_fileMapper.findById", pd);
	}
	
}

