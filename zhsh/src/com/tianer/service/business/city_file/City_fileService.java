package com.tianer.service.business.city_file;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("city_fileService")
public class City_fileService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("City_fileMapper.save", pd);
	}
	
	
	/*
	* 新增
	* 魏汉文20160602
	*/
	public void saveList(PageData pd,List<PageData> list)throws Exception{
			dao.save("City_fileMapper.save", pd);
			for(PageData e : list){
				dao.save("City_file_sortMapper.save", e);
			}
	}
	
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("City_fileMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("City_fileMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("City_fileMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("City_fileMapper.listAll", pd);
	}
 
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("City_fileMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("City_fileMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	 * 批量修改状态
	 */
	public void editStatusChoose(PageData pd)throws Exception{
		dao.update("City_fileMapper.editStatusChoose", pd);
	}
	
	/*
	*  获取最大值Id
	*   魏汉文20160602
	*/
	public String getMaxID(PageData pd)throws Exception{
		return (String)dao.findForObject("City_fileMapper.getMaxID", pd);
	}
	
	
	/*
	*获取所有省魏汉文20160613
	*/
	public List<PageData> listAllProvince(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("City_fileMapper.listAllProvince", pd);
	}
	
	/*
	*获取所有市魏汉文20160613
	*/
	public List<PageData> listAllCity(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("City_fileMapper.listAllCity", pd);
	}
	
	/*
	*获取所有区魏汉文20160613
	*/
	public List<PageData> listAllArea(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("City_fileMapper.listAllArea", pd);
	}
	
	/*
	*获取1/2级分类魏汉文20160614
	*/
	public List<PageData> listAllCitySort(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("City_fileMapper.listAllCitySort", pd);
	}
	
	
	/**
	* 1010新增城市营销收费标准
	*/
	public void addCityFileFee(PageData pd)throws Exception{
		dao.save("City_fileMapper.addCityFileFee", pd);
	}
	
	
	/**
	* 删除城市营销收费标准
	*/
	public void updateCityFileFee(PageData pd)throws Exception{
		dao.update("City_fileMapper.updateCityFileFee", pd);
	}
	
	/**
	*获取城市营销收费标准列表
	*/
	public List<PageData> listAllCityFee(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("City_fileMapper.listAllCityFee", pd);
	}
	
	
	/**
	* 通过id获取数据
	*/
	public PageData getCityFeeDetail(PageData pd)throws Exception{
		return (PageData)dao.findForObject("City_fileMapper.getCityFeeDetail", pd);
	}
	
	
	/*
	 *获取所有已开通城市的信息
	 */
	public List<PageData> listAllCityOpen(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("City_fileMapper.listAllCityOpen", pd);
	}
	
	
}

