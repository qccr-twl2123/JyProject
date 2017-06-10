package com.tianer.service.zhaoshang;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


/**
 * 招商的集合
 */
@Service("zhaoShangService")
public class ZhaoShangService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	//------------------------------------------------招商-----------------------------------------------
 	/**
	 *  
	 */
	public void saveCompay(PageData pd)throws Exception{
		dao.save("ZhaoShangMapper.saveCompay", pd);
	}
	/**
	 *  
	 */
	public void editCompay(PageData pd)throws Exception{
		dao.update("ZhaoShangMapper.editCompay", pd);
	}
	
	/**
	 *  
	 */
	public void deleteCompay(PageData pd)throws Exception{
		dao.update("ZhaoShangMapper.deleteCompay", pd);
	}


	 
 	/**
	 *  
	 */
	public  PageData  findByIdCompay(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("ZhaoShangMapper.findByIdCompay", pd);
	}
	
	/**
	 * 
	 */
 	public List<PageData> datalistPageCompay(Page page) throws Exception {
		return (List<PageData>) dao.findForList("ZhaoShangMapper.datalistPageCompay", page);
	}

 	
	
	/**
	 *  
	 */
 	public List<PageData> listAllCompay(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ZhaoShangMapper.listAllCompay", pd);
  	}
 	
 	 

}
