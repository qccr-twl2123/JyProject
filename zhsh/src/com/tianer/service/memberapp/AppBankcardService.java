package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


/**
 * 账户设置
 * @author 刘耀耀
 * 2016.07.11
 *
 */
@Service("appBankcardService")
public class AppBankcardService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增银行卡
	 * 刘耀耀
	 * 2016.07.11
	 */
	public void save(PageData pd)throws Exception{
		dao.save("appBankcardMapper.save", pd);
	}
	
	/**
	 * 查询全部银行卡
	 * 刘耀耀
	 * 2016.07.11
	 */

	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("appBankcardMapper.listAll", pd);
	}
	
	
	/**
	 * 添加支付宝
	 */
	public void saveAlipay(PageData pd)throws Exception{
		dao.save("appBankcardMapper.saveAlipay", pd);
	}
	
	/**
	 * 删除支付宝
	 */
	public void deleteAlipay(PageData pd)throws Exception{
		dao.delete("appBankcardMapper.deleteAlipay", pd);
	}
	
	/**
	 * 查询全部支付宝账号
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAlipay(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("appBankcardMapper.listAlipay", pd);
	}

}
