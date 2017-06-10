package com.tianer.service.business.app_advert;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 
* 类名称：App_advertService   
* 类描述：   app广告位
* 创建人：魏汉文  
* 创建时间：2016年6月23日 下午1:42:10
 */
@Service("app_advertService")
public class App_advertService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 新增广告位的个数
	 */
	public void lastsaveAdvert(PageData pd)throws Exception{
		dao.save("App_advertMapper.lastsaveAdvert", pd);
	}
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("App_advertMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("App_advertMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("App_advertMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("App_advertMapper.datalistPage", page);
	}
	
	/*
	*列表
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("App_advertMapper.listAll", pd);
	}

	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("App_advertMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("App_advertMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	/*
	 *列表魏汉文20160623
	 */
	public List<PageData> listAllAdvert(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("App_advertMapper.listAllAdvert", pd);
	}
	
}

