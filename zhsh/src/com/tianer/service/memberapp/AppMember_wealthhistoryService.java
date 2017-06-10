package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("appMember_wealthhistoryService")
public class AppMember_wealthhistoryService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 *列表
	 */
	public List<PageData> datalistPage(Page   page)throws Exception{
		return (List<PageData>)dao.findForList("AppMember_wealthhistoryMapper.datalistPage", page);
	}
	
	/*
	*列表
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppMember_wealthhistoryMapper.listAll", pd);
	}
	
	/*
	 *获取最大的ID
	 */
	public String getMaxMemberWealthId(PageData pd)throws Exception{
		return (String)dao.findForObject("AppMember_wealthhistoryMapper.getMaxMemberWealthId", pd);
	}
  	
 	/*
	* 新增财富历史记录魏汉文20160705
	*/
 	public void saveWealthhistory(PageData pd) throws Exception {
			dao.save("AppMember_wealthhistoryMapper.saveWealthhistory", pd);
  	}
 	
 	
// 	/*
// 	 * 新增提现记录魏汉文20160705
// 	 */
// 	public void saveWealthhistoryByTx(PageData pd) throws Exception {
// 		dao.save("AppMember_wealthhistoryMapper.saveWealthhistoryByTx", pd);
// 	}
 	
	
 	
 	/*
	* 更新状态
	*/
 	public void updateWealthhistory(PageData pd) throws Exception {
			dao.update("AppMember_wealthhistoryMapper.updateWealthhistory", pd);
  	}
	
}

