package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("lyymember_vipcardService")
public class LyyMember_vipcardService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/*
	 *我的会员分页列表
	 */
	public List<PageData> listPageByvipmember(Page page)throws Exception{
		return (List<PageData>)dao.findForList("LyyMember_vipcardMapper.listPageByvipmember", page);
	}
	/*
	*列表排序
	*刘耀耀
	*2016.07.05
	*/
	public List<PageData> list(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LyyMember_vipcardMapper.list", pd);
	}
	
	
	public String orderLasttime(String store_id)throws Exception{
		return (String)dao.findForObject("LyyMember_vipcardMapper.orderLasttime", store_id);
	}
}

