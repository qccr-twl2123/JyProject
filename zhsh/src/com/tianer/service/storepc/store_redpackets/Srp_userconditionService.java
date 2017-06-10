package com.tianer.service.storepc.store_redpackets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


/**
 * 红包使用条件
 * @author 邢江涛
 *
 */
@Service("srp_userconditionService")
public class Srp_userconditionService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查看
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAll() throws Exception{
		return (List<PageData>) dao.findForList("XJTSrp_userconditionMapper.listAll",null);
	}

}
