package com.tianer.service.business.pcd;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("pcdService")
public class PcdService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("PcdMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("PcdMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("PcdMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PcdMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PcdMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PcdMapper.findById", pd);
	}

	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PcdMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 查询市
	*/
	public List<PageData> listcity(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PcdMapper.listcity", pd);
	}
	
	/*
	* 查询区
	*/
	public List<PageData> listarea(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("PcdMapper.listarea", pd);
	}
	
	/*
	* 通过名称获取ID获取数据
	*/
	public String getIdByName(PageData pd)throws Exception{
		String s="";
		List<PageData> list=(List<PageData>)dao.findForList("PcdMapper.getIdByName", pd);
		if(list.size() > 0){
			s=list.get(0).getString("id");
		}
		return s;
	}
	
	
	/*
	 * 通过市名称ID获取数据 20160801
	 */
	public PageData findByIdByCity(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PcdMapper.findByIdByCity", pd);
	}
	
	/*
	 * 通过市名称ID获取数据 20160801
	 */
	public PageData findProvinceByIdByCity(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PcdMapper.findProvinceByIdByCity", pd);
	}
	
}

