package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


/** 
 * 
* 类名称：Storeapp_operatorController   
* 类描述：  管理内部人员
* 创建人：邢江涛 
* 创建时间：2016年6月30日 
 */
@Service("storeapp_operatorService")
public class Storeapp_operatorService {
	

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 查看内部人员
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTStoreapp_operatorMapper.listAll", pd);
	}
	
	/*
	* 删除成员
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("XJTStoreapp_operatorMapper.delete", pd);
	}
	
	/**
	 * 添加成员
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTStoreapp_operatorMapper.save", pd);
	}
	

}
