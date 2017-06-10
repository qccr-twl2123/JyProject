package com.tianer.service.business.store_shift;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("store_shiftService")
public class Store_shiftService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增魏汉文0715
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Store_shiftMapper.save", pd);
	}
	
	/*
	* 删除魏汉文0715
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Store_shiftMapper.delete", pd);
	}
	
	/*
	* 修改魏汉文0715
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Store_shiftMapper.edit", pd);
	}
	
//	/*
//	*列表
//	*/
//	public List<PageData> list(Page page)throws Exception{
//		return (List<PageData>)dao.findForList("Store_shiftMapper.datalistPage", page);
//	}
	
	/*
	*列表(全部)魏汉文0715
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Store_shiftMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Store_shiftMapper.findById", pd);
	}
	
//	/*
//	* 批量删除
//	*/
//	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
//		dao.delete("Store_shiftMapper.deleteAll", ArrayDATA_IDS);
//	}
//	
}

