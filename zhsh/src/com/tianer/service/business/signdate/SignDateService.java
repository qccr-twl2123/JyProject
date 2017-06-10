package com.tianer.service.business.signdate;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


@Service("signdateService")
public class SignDateService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增gt
	*/
	public void save(PageData pd)throws Exception{
		dao.save("SignDateMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("SignDateMapper.delete", pd);
	}
	
	/*
	* 修改gt
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("SignDateMapper.edit", pd);
	}
	/*
	* 通过id获取数据gt
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SignDateMapper.findById", pd);
	}
}

