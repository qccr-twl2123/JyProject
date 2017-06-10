package com.tianer.service.storepc.store_marketing;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 管理我的营销（营销记录）
 * @author 邢江涛
 *
 */
@Service("storepc_marketingService")
public class Storepc_marketingService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTStore_marketingMapper.datalistPage", page);
	}
	
	
	/*
	*列表
	*/
	public List<PageData> marketlist(Page page)throws Exception{
		return (List<PageData>)dao.findForList("XJTStore_marketingMapper.marketlistPage", page);
	}
	
	/*
	 *列表
	 */
	public PageData findYxByOpenForOne(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStore_marketingMapper.findYxByOpenForOne", pd);
	}
	
	/*
	*列表
	*/
	public Object count(PageData pd)throws Exception{
		return dao.findForObject("XJTStore_marketingMapper.count", pd);
	}
	
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("XJTStore_marketingMapper.edit", pd);
	}
	
	
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("XJTStore_marketingMapper.delete", pd);
	}
	


	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
 		dao.save("XJTStore_marketingMapper.save", pd);//新增
	}
	
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void savescore(PageData pd)throws Exception{
		dao.save("XJTStore_marketingMapper.savescore", pd);//新增
	}
	
	/*
	* 删除
	*/
	public void deleteByType(PageData pd)throws Exception{
		dao.delete("XJTStore_marketingMapper.deleteByType", pd);
	}
	/*
	* 修改营销过期状态
	* 刘耀耀
	* 2016.07.19
	*/
	public void editTime(PageData pd)throws Exception{
		dao.delete("XJTStore_marketingMapper.updateTime", pd);
	}
	
	/*
	*列表
	*/
	public PageData selectoperator(PageData pd)throws Exception{
		return (PageData) dao.findForObject("XJTStore_marketingMapper.selectoperator", pd);
	}
	
	/*
	*列表
	*/
	public PageData selectclerk(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStore_marketingMapper.selectclerk", pd);
	}
	
	/*
	*列表
	*/
	public PageData selectsp(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStore_marketingMapper.selectsp", pd);
	}
	
	/*
	*列表
	*/
	public PageData selectsubsidiary(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStore_marketingMapper.selectsubsidiary", pd);
	}
	
	public void updateOperator(PageData pd)throws Exception{
		dao.delete("XJTStore_marketingMapper.updateOperator", pd);
	}
	
	public void updateClerk(PageData pd)throws Exception{
		dao.delete("XJTStore_marketingMapper.updateClerk", pd);
	}
	
	public void updateSp(PageData pd)throws Exception{
		dao.delete("XJTStore_marketingMapper.updateSp", pd);
	}
	
	public void updateSubsidiary(PageData pd)throws Exception{
		dao.delete("XJTStore_marketingMapper.updateSubsidiary", pd);
	}
	
	public PageData findBySort(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStore_marketingMapper.findBySort", pd);
	}
	/*
	* 修改排序
	*/
	public void updateSort(PageData pd)throws Exception{
		dao.update("XJTStore_marketingMapper.updateSort", pd);
	}

}
