package com.tianer.service.storepc.store_bankcard;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


/**
 * 账户设置
 * @author 邢江涛
 *
 */
@Service("storepc_bankcardService")
public class Storepc_bankcardService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增银行卡
	 * 刘耀耀
	 * 2016.07.13
	 */
	public void save(PageData pd)throws Exception{
		dao.save("XJTStorepc_bankcardMapper.save", pd);
	}
	
	
	/**
	 * 删除银行卡
	 */
	public void deleteBank(PageData pd)throws Exception{
		dao.delete("XJTStorepc_bankcardMapper.deleteBank", pd);
	}
	
	
	/**
	 * 添加支付宝
	 */
	public void saveAlipay(PageData pd)throws Exception{
		dao.save("XJTStorepc_bankcardMapper.saveAlipay", pd);
	}
	
	/**
	 * 删除支付宝
	 */
	public void deleteAlipay(PageData pd)throws Exception{
		dao.delete("XJTStorepc_bankcardMapper.deleteAlipay", pd);
	}
	
	/**
	 * 查询 支付宝详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findbyAlipay(PageData pd) throws Exception{
		return (PageData ) dao.findForObject("XJTStorepc_bankcardMapper.findbyAlipay", pd);
	}
	
	/**
	 * 查询全部支付宝账号
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAlipay(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("XJTStorepc_bankcardMapper.listAlipay", pd);
	}

	/**
	 * 查询全部
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("XJTStorepc_bankcardMapper.list", pd);
	}
	
	/**
	 * 查询全部
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByNumber(PageData pd) throws Exception{
		return  (PageData) dao.findForList("XJTStorepc_bankcardMapper.findByNumber", pd);
	}
	/**
	 * 修改银行卡是不是默认的
	 * 刘耀耀
	 * 2016.07.13
	 */
	public void update(PageData pd)throws Exception{
		dao.update("XJTStorepc_bankcardMapper.update", pd);
	}
	/**
	 * 修改银行卡是不是默认的
	 * 刘耀耀
	 * 2016.07.13
	 */
	public List<PageData> list(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("XJTStorepc_bankcardMapper.list", pd); 
	}

}
