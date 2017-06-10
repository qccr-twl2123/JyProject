package com.tianer.service.storeapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;

/**
 * 
* 类名称：storeapp_redpacketsService   
* 类描述：   app银行卡详情
* 创建人：邢江涛 
* 创建时间：2016年6月29日 
 */
@Service("storeapp_bankcardService")
public class Storeapp_bankcardService {
	

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/*
	 *银行卡详情
	 */
	public List<PageData> listbank(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTStorepc_bankcardMapper.findbankinfo", pd);
	}
	
	/*
	 *银行卡详情
	 */
	public PageData findbankById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStorepc_bankcardMapper.findbankById", pd);
	}

	/*
	 *银行卡信息
	 */
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTStorepc_bankcardMapper.list", pd);
	}
	
	/*
	* 解除银行卡
	*/
	public void deleteBank(PageData pd)throws Exception{
		dao.delete("XJTStorepc_bankcardMapper.deleteBank", pd);
	}
	
	/*
	 * 添加银行卡
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTStorepc_bankcardMapper.save", pd);
	}
 	
	/*
	 *我的银行卡数量
	 */
	public String bankCount(PageData pd)throws Exception{
		return (String)dao.findForObject("XJTStorepc_bankcardMapper.bnakCount", pd);
	}
	
 	
	/*
	 *我的支付宝数量
	 */
	public String AlipayCount(PageData pd)throws Exception{
		return (String)dao.findForObject("XJTStorepc_bankcardMapper.AlipayCount", pd);
	}
	
}
