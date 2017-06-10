package com.tianer.service.business.sp_file;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("sp_fileService")
public class Sp_fileService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("Sp_fileMapper.save", pd);
	}
	
	/*
	* 新增
	*/
	public void saveList(PageData pd,List<PageData> list)throws Exception{
		dao.save("Sp_fileMapper.save", pd);
		for(PageData e : list){
			dao.save("Service_performanceMapper.save", e);
		}
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("Sp_fileMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("Sp_fileMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("Sp_fileMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Sp_fileMapper.listAll", pd);
	}
	
	/*
	 *列表(全部)
	 */
	public String listAllCount(PageData pd)throws Exception{
		return (String)dao.findForObject("Sp_fileMapper.listAllCount", pd);
	}
	

	
	/*
	 * 通过登陆获取数据魏汉文
	 */
	public PageData findByLogin(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Sp_fileMapper.findByLogin", pd);
	}
	
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Sp_fileMapper.findById", pd);
	}
	
	
	/*
	*获取最大值得数据
	*/
	public String getMaxID(PageData pd)throws Exception{
		return (String)dao.findForObject("Sp_fileMapper.getMaxID", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("Sp_fileMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	 *获取所有的服务商通过城市
	 */
	public List<PageData> getSpListBycity(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Sp_fileMapper.getSpListBycity", pd);
	}
	
	/*
	*获取所有服务商
	*魏汉文20160608
	*
	*/
	public List<PageData> listAllSp(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("Sp_fileMapper.listAllSp", pd);
	}
	
	/*
	 * 通过登陆获取数据魏汉文
	 */
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("Sp_fileMapper.findByName", pd);
	}
	
}

