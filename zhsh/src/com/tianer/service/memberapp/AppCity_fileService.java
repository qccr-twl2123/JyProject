package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


@Service("appCity_fileService")
public class AppCity_fileService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
 
	
	/*
	*列表魏汉文暂时未用
	*/
	public List<PageData> listAllArea( PageData pd  )throws Exception{
		return (List<PageData>)dao.findForList("AppCity_fileMapper.listAllArea", pd);
	}
	
	/*
	* 通过省市区获取Id  魏汉文20160622
	*/
	public PageData findIdByPcd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppCity_fileMapper.findIdByPcd", pd);
	}
	

	
	
	/*
	 * 通过城市档案ID获取营销参数ID数据魏汉文20160622
	 */
	public PageData findByCityId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppCity_fileMapper.findByCityId", pd);
	}
	
	
	/*
	*列表魏汉文20160623
	*/
	public List<PageData> listAllCitySort( PageData pd  )throws Exception{
		return (List<PageData>)dao.findForList("AppCity_fileMapper.listAllCitySort", pd);
	}
	
	
	/*
	 *市列表魏汉文20160623
	 */
	public List<PageData> listAllCity( PageData pd  )throws Exception{
		return (List<PageData>)dao.findForList("AppCity_fileMapper.listAllCity", pd);
	}
	
	
	/*
	 *省列表魏汉文20160623
	 */
	public List<PageData> listAll( PageData pd  )throws Exception{
		return (List<PageData>)dao.findForList("AppCity_fileMapper.listAll", pd);
	}
	
	
}

