package com.tianer.service.storepc.store_operator;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("storepcOperator_fileService")
public class Storepc_operator_fileService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	* 刘耀耀2013.07.14
	*/
	public void save(PageData pd)throws Exception{
		dao.save("LyyOperator_fileMapper.save", pd);
	}
	

	
	/*
	* 修改
	* 刘耀耀2013.07.14
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("LyyOperator_fileMapper.edit", pd);
	}
	
	/*
	* 通过id获取数据
	* 刘耀耀：pc
	* 2016.07.14
	* 
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LyyOperator_fileMapper.findById", pd);
	}
	/*
	* 通过商家id获取数据
	* 刘耀耀：pc班次
	* 2016.07.14
	* 
	*/
	public List<PageData> list(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("LyyOperator_fileMapper.list", pd);
		
	}
	/*
	* 删除班次
	* 刘耀耀：pc班次
	* 2016.07.14
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("LyyOperator_fileMapper.delete", pd);
	}

	/*
	* 添加班次
	* 刘耀耀：pc班次
	* 2016.07.14
	*/

	public void saveShift(PageData pd) throws Exception {
		dao.save("LyyOperator_fileMapper.saveShift", pd);
	}

}

