package com.tianer.service.business.citymanager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("citymanagerService")
public class CityManagerService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("CityManagerMapper.save", pd);
	}
	
	/*
	* 新增地域同时删除地域
	*/
	public void savePcd(PageData pd,List<PageData> array)throws Exception{
		dao.delete("CityManagerMapper.deletePcd", pd);
 		for (int i = 0; i < array.size(); i++) {
			dao.save("CityManagerMapper.savePcd", array.get(i));
		}
 	}
	
	/*
	* 删除地域
	*/
	public void deletePcd(PageData pd)throws Exception{
		dao.delete("CityManagerMapper.deletePcd", pd);
	}
	
	/*
	 *指定城市经理的地域列表
	 */
	public List<PageData> findSubPcdList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CityManagerMapper.findSubPcdList", pd);
	}
	
	/*
	 *查看全市的数据
	 */
	public List<PageData> cityAll(String city_id)throws Exception{
		return (List<PageData>)dao.findForList("CityManagerMapper.cityAll", city_id);
	}
	
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("CityManagerMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("CityManagerMapper.edit", pd);
 	}
	

	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CityManagerMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CityManagerMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CityManagerMapper.findById", pd);
	}
	
	/*
	 * 登录验证
	 */
	public PageData findByLogin(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CityManagerMapper.findByLogin", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CityManagerMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 获取当前表的最大值ID
	* *魏汉文20160608
	*/
	public String getMaxId()throws Exception{
		return (String)dao.findForObject("CityManagerMapper.getMaxId", null);
	}
	
	
	/*
	*获得所有子公司精简
	*魏汉文20160608
	*/
	public List<PageData> listAllSub(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CityManagerMapper.listAllSub", pd);
	}
	
	/*
	* 新增
	* 魏汉文20160602
	*/
	public void saveCity(PageData pd)throws Exception{
			dao.save("City_fileMapper.save", pd);
	}
}

