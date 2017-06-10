package com.tianer.service.storepc.store_marketingtype;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;

/**
 * 营销类型（买n减1）
 * @author 邢江涛
 *
 */
@Service("storepc_marketingtypeService")
public class Storepc_marketingtypeService {
	
	@Resource(name = "daoSupport")
	public DaoSupport dao;
	
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTStore_marketingtypeMapper.save", pd);
	}
	
	/**
	 * 更新营销状态为到期的时候 
	 * 刘耀耀
	 * 2016.7.19
	 * @param pd
	 * @throws Exception
	 */
	public void updateTime(PageData pd)throws Exception{
		dao.update("XJTStore_marketingtypeMapper.updateTime", pd);
	}
	/**
	 * 更新营销状态为到期的时候 
	 * @param pd
	 * @throws Exception
	 */
	public void updateOverTime(PageData pd)throws Exception{
		dao.update("XJTStore_marketingtypeMapper.updateOverTime", pd);
	}
	
	/**
	 * 查询全部
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllByType(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_marketingtypeMapper.listAllByType", pd);
	}
	
	
	/**
	 * 删除类型
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("XJTStore_marketingtypeMapper.delete", pd);
	}
	
	/*
	*查看详情
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStore_marketingtypeMapper.findById", pd);
	}
	
	
}
