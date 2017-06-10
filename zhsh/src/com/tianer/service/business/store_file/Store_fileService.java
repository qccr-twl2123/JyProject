package com.tianer.service.business.store_file;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("store_fileService")
public class Store_fileService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Store_fileMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Store_fileMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Store_fileMapper.edit", pd);
		dao.update("StoreMapper.edit", pd);
	}
 	
	/*
	* 修改商家的服务商
	* 魏汉文20160615
	*/
	public void editStoreSp(PageData pd)throws Exception{
		dao.update("Store_fileMapper.editStoreSp", pd);
	}
	
	
	/*
	*列表
	*魏汉文20160607
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Store_fileMapper.datalistPage", page);
	}
	
	
	/*
	 *商家关系调整列表
	 *魏汉文20160607
	 */
	public List<PageData> StoreRelationslistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Store_fileMapper.StoreRelationslistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Store_fileMapper.listAll", pd);
	}
	/*
	 *通过城市区域获取列表 
	 */
	public List<PageData> listStoreForName(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Store_fileMapper.listStoreForName", pd);
	}
	/*
	 *通过以及分类获取列表 
	 */
	public List<PageData> listAllForCitysort(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Store_fileMapper.listAllForCitysort", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Store_fileMapper.findById", pd);
	}
	
	/*
	 *  城市营销详情的第六种营销策略
	 */
	public PageData getCityForName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Store_fileMapper.getCityForName", pd);
	}
	
	/*
	 * 城市档案分类营销详情
	 */
	public PageData getStartNumberForStore(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Store_fileMapper.getStartNumberForStore", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Store_fileMapper.deleteAll", ArrayDATA_IDS);
	}
	

	
}

