package com.tianer.service.baobiao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 
* 类名称：BaoBiaoService   
* 报表统计服务层
 */
@Service("baoBiaoService")
public class BaoBiaoService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	 
	/*
	*列表
	*/
	public List<PageData> baoBiaoTypeTotol(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BaoBiaoMapper.baoBiaoTypeTotol", pd);
	}
	 
	
}

