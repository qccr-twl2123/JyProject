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
		return (List<PageData>)dao.findForList("Storeapp_operatorMapper.listAll", pd);
	}
	
	/*
	 * 获取操作员列表
	 */
	public List<PageData> getListOpratorById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Storeapp_operatorMapper.getListOpratorById", pd);
	}
	
	/*
	* 删除成员
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Storeapp_operatorMapper.delete", pd);
	}
	
	/*
	 * 添加成员
	 */
	public void save(PageData pd)throws Exception{
		dao.save("Storeapp_operatorMapper.save", pd);
	}
 
	

}
