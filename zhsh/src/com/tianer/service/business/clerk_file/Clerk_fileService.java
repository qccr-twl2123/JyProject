package com.tianer.service.business.clerk_file;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("clerk_fileService")
public class Clerk_fileService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Clerk_fileMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Clerk_fileMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Clerk_fileMapper.edit", pd);
	}
	
	/*
	*列表
	*魏汉文20160608
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Clerk_fileMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*魏汉文20160608
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Clerk_fileMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Clerk_fileMapper.findById", pd);
	}
	
	
	/*
	 * 通登陆获取数据
	 */
	public PageData findByLogin(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Clerk_fileMapper.findByLogin", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Clerk_fileMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 获取当前表的最大值ID
	* 魏汉文20160608
	*/
	public String getMaxId()throws Exception{
		return (String)dao.findForObject("Clerk_fileMapper.getMaxId", null);
	}
	
	
	/*
	*获取所有业务员
	*魏汉文20160608
	*/
	public List<PageData> listAllCr(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Clerk_fileMapper.listAllCr", pd);
	}
	
}

