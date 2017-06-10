package com.tianer.service.storepc.store_way;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 消费/支付
 * @author 邢江涛
 *
 */
@Service("storepc_wayService")
public class Storepc_wayService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.delete("XJTStore_wayMapper.delete", pd);
		dao.save("XJTStore_wayMapper.save", pd);
	}
	
	/**
	 * 删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("XJTStore_wayMapper.delete", pd);
	}
	

	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTStore_wayMapper.datalistPage", page);
	}
	
	

	/*
	*详情
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStore_wayMapper.findById", pd);
	}
}
