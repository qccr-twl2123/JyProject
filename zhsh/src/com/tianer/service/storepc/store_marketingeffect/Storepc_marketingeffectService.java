package com.tianer.service.storepc.store_marketingeffect;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 营销效果记录
 * @author 邢江涛
 *
 */
@Service("storepc_marketingeffectService")
public class Storepc_marketingeffectService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTStore_marketingeffectMapper.datalistPage", page);
	}
	
	
	/*
	*列表
	*/
	public List<PageData> listMarke(PageData pd)throws Exception{
		System.out.println(pd);
		return (List<PageData>)dao.findForList("XJTStore_marketingeffectMapper.listMarke", pd);
	}
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTStore_marketingeffectMapper.save", pd);
	}
	
	/**
	 * 更新
	 * @param pd
	 * @throws Exception
	 */
	public void update(PageData pd)throws Exception{
		dao.update("XJTStore_marketingeffectMapper.update", pd);
	}
	
	
	/*
	*查看详情
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStore_marketingeffectMapper.findById", pd);
	}
	
}
