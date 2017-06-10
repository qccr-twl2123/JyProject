package com.tianer.service.business.operator_file;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("operator_fileService")
public class Operator_fileService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Operator_fileMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Operator_fileMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Operator_fileMapper.edit", pd);
	}
	
	/*
	*列表
	*魏汉文：pc总后台
	*
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Operator_fileMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Operator_fileMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	* 魏汉文：pc总后台
	* 
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Operator_fileMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Operator_fileMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 获取当前表的最大值ID
	* 魏汉文：pc总后台
	* 
	*/
	public String getMaxId()throws Exception{
		return (String)dao.findForObject("Operator_fileMapper.getMaxId", null);
	}
	

	/*
	*获取所有操作员的角色
	*/
	public List<PageData> opRolelistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Operator_fileMapper.opRolelistPage", page);
	}
	
	
	/*
	* 通过登陆获取数据
	* 魏汉文：pc总后台
	* 
	*/
	public PageData findByLogin(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Operator_fileMapper.findByLogin", pd);
	}
	
}

