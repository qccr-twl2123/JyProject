package com.tianer.service.business.red_platform;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("red_platformService")
public class Red_platformService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	* 魏汉文20160614
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Red_platformMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Red_platformMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Red_platformMapper.edit", pd);
	}
	
	/*
	*列表
	*魏汉文20160614
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Red_platformMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Red_platformMapper.listAll", pd);
	}
	
	/*
	 *获取所有条件列表
	 *魏汉文20160614
	 */
	public List<PageData> listAllUserCondition(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Red_platformMapper.listAllUserCondition", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Red_platformMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Red_platformMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

