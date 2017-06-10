package com.tianer.service.business.pc_advert;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("pc_advertService")
public class Pc_advertService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 新增
	 */
	public void lastsaveAdvert(PageData pd)throws Exception{
		dao.save("Pc_advertMapper.lastsaveAdvert", pd);
	}
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Pc_advertMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Pc_advertMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Pc_advertMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Pc_advertMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Pc_advertMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Pc_advertMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Pc_advertMapper.deleteAll", ArrayDATA_IDS);
	}
	
//	/*
//	*列表
//	*/
//	public List<PageData> selectAll(PageData pd)throws Exception{
//		return (List<PageData>)dao.findForList("Pc_advertMapper.selectAll", pd);
//	}
	
}

