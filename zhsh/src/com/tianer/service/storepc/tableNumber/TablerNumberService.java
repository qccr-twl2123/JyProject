package com.tianer.service.storepc.tableNumber;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 桌号设置
 * @author 邢江涛
 *
 */
@Service("tablerNumberService")
public class TablerNumberService {

	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 新增桌号
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTTableNumberMapper.save", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTTableNumberMapper.datalistPage", page);
	}
	
	/*
	 *列表
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTTableNumberMapper.listAll", pd);
	}
	
	/*
	*列表
	*/
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTTableNumberMapper.findByName", pd);
	}
	
	/**
	 * 删除桌号
	 */
	public void delete(PageData pd)throws Exception{
		dao.save("XJTTableNumberMapper.delete", pd);
	}
	
	/**
	 * 删除桌号
	 */
	public void update(PageData pd)throws Exception{
		dao.save("XJTTableNumberMapper.update", pd);
	}
}
