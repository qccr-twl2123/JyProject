package com.tianer.service.htmlwx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("htmlWxService")
public class HtmlWxService {

	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	

	/*
	 *获取商家列表
	 */
	public List<PageData> getStorelistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HtmlWxMapper.getStorelistPage", page);
	}
	
	
	
	
	
	
	
	
	

	/*
	* 修改
	* 刘耀耀
	* 2016.06.20
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("HtmlWxMapper.edit", pd);
	}
	
  
	
	
 
	
	/*
	 * 通过推荐号码获取手机号魏汉文20160620
	 */
	public PageData findIdByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("HtmlWxMapper.findIdByPhone", pd);
	}	
	
	 public void delete() throws Exception {
		dao.delete("HtmlWxMapper.deleteZan", null);
	}
 
	
	/*
	*新增收藏记录
	* 魏汉文20160629
	*/
	public void saveCollect(PageData pd) throws Exception {
		dao.save("HtmlWxMapper.saveCollect", pd);
	}
	 

}

