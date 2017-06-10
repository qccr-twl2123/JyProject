package com.tianer.service.storepc.store_discountway;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;

/***
 * 折扣方式
 * @author 邢江涛
 *
 */
@Service("storepc_discountwayService")
public class Storepc_discountwayService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/***
	 * 显示列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStore_discountwayMapper.listAll", pd);
	}
		
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTStore_discountwayMapper.delete", pd);
		dao.save("XJTStore_discountwayMapper.save", pd);
	}
	
	/***
	 * 详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData) dao.findForObject("XJTStore_discountwayMapper.findById", pd);
	}
	
	/**
	 * 删除折扣
	 */
	public void deleteById(PageData pd)throws Exception{
		dao.delete("XJTStore_discountwayMapper.deleteById", pd);
	}
	
	
	/**
	 * 更新营销状态为到期的时候 
	 * @param pd
	 * @throws Exception
	 */
	public void updateOverTime(PageData pd)throws Exception{
		dao.update("XJTStore_discountwayMapper.updateOverTime", pd);
	}
	/**
	 * 更新营销状态为到期的时候
	 * 刘耀耀 
	 * 2016.07.19
	 * @param pd
	 * @throws Exception
	 */
	public void updateTime(PageData pd)throws Exception{
		dao.update("XJTStore_discountwayMapper.updateTime", pd);
	}
	

}
