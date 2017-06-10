package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("appStore_redpacketsService")
public class AppStore_redpacketsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	*查出红包列表
	*刘耀耀
	*2016.06.22
	*魏汉文20160629
	*/
	public List<PageData> list(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStore_redpacketsMapper.list", pd);
	}
	
	/*
	*查出商家发红包红包列表
 	*/
	public List<PageData> listIsRedlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppStore_redpacketsMapper.listIsRedlistPage", page);
	}

	/*
	*修改（领取红包）
	*刘耀耀
	*2016.06.22
	*/

	public Object edit(PageData pd) throws Exception {
		return dao.update("AppStore_redpacketsMapper.edit", pd);
	}

	/*
	*查出指定商家红包的个数
	*刘耀耀
	*2016.06.22
	*/

	public String findNumber(PageData pd) throws Exception {
		return (String) dao.findForObject("AppStore_redpacketsMapper.getNumber", pd);
	}
	
	/*
	 *查出商家红包个数
	 */
 	public String listCountRedPackage(PageData pd) throws Exception {
		return (String) dao.findForObject("AppStore_redpacketsMapper.listCountRedPackage", pd);
	}
	
	/*
	 * 获取红包详情魏汉文20160622
	 */
	public PageData findRedById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppStore_redpacketsMapper.findRedById", pd);
	}
	
	/*
	 * 获取红包精简详情魏汉文20160623
	 */
	public PageData findJJRedById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppStore_redpacketsMapper.findJJRedById", pd);
	}
	
	
	/*
	 *附近红包列表
	 *0707
	 */
	public List<PageData> listAllFjRed(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStore_redpacketsMapper.listAllFjRed", pd);
	}

	/*
	 * 获取用户是否领取商家红包
	 */
	public List<PageData> findRedByMem(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppStore_redpacketsMapper.findRedByMem", pd);
	}
	
	/*
	 * 获取商家红包种类
	 */
	public List<PageData> findRedByStore(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppStore_redpacketsMapper.findRedByStore", pd);
	}
	
	/*
	 * 搜索商家
	 */
	public List<PageData> findAllStorelistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("AppStore_redpacketsMapper.findAllStorelistPage", page);
	}
	
	 
	
}

