package com.tianer.service.storeapp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


/**
 * 
* 类名称：ChatRedService   
* 类描述：   app群聊发红包
* 创建人：邢江涛 
* 创建时间：2016年7月4日 
 */
@Service("ChatRedService")
public class ChatRedService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 发送积分/红包
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("XJTStore_wealthMapper.edit", pd);
	}
	
	
//	/*
//	* 当前积分数
//	*/
//	public String coun(PageData pd)throws Exception{
//		return (String) dao.findForObject("XJTStore_wealthMapper.integral", pd);
//	}

	/*
	 *  查出商家vip图片和颜色 刘耀耀 2016.7.21 
	 */
	
	public PageData findById(PageData pd) throws Exception {
		return  (PageData) dao.findForObject("XJTStore_wealthMapper.findById", pd);
	}
}
