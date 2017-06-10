package com.tianer.service.storepc.store_wealth;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 积分充值
 * @author 邢江涛
 *
 */
@Service("storepc_wealthService")
public class Storepc_wealthService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**
	 * 查询全部
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String sumStoreWealth(PageData pd) throws Exception{
		return (String) dao.findForObject("XJTStore_wealthMapper.sumStoreWealth", pd);
	}

 


}
