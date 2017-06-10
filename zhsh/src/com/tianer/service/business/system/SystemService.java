package com.tianer.service.business.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("systemService")
public class SystemService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("SystemMapper.save", pd);
 
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("SystemMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("SystemMapper.edit", pd);
 	}

	
	/*
	*列表
	*魏汉文：pc总后台
	*
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SystemMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*魏汉文：pc总后台
	*
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SystemMapper.listAll", pd);
	}
	
	/*
	*角色列表(全部)
	*魏汉文：pc总后台
	*
	*/
	public List<PageData> roleListAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SystemMapper.roleListAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SystemMapper.findById", pd);
	}

	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SystemMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 获取当前表的最大值ID
	*/
	public String getMaxId()throws Exception{
		return (String)dao.findForObject("SystemMapper.getMaxId", null);
	}
	
	
	/*
	 * 修改总管理用户名密码
	 */
	public void editAdmin(PageData pd)throws Exception{
		dao.update("SystemMapper.editAdmin", pd);
	}
	
	
	/*
	 * 通过id获取数据
	 */
	public PageData findByIdAdmin(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SystemMapper.findByIdAdmin", pd);
	}
	
}

