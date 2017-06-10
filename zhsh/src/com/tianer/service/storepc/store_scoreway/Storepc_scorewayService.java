package com.tianer.service.storepc.store_scoreway;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;

/**
 * 积分方式
 * @author 邢江涛
 *
 */
@Service("storepc_scorewayService")
public class Storepc_scorewayService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
//	/**
//	 * 显示全部
//	 * @param pd  
//	 * @return 
//	 * @throws Exception
//	 */
//	public List<PageData> listAll(PageData pd)throws Exception{
//		return (List<PageData>)dao.findForList("XJTStorescoreWayModelMapper.listAll", pd);
//	}
	
	
	/**
	 * 查看商家积分设置详情
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTStorescoreWayModelMapper.findById", pd);
	}
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		//删除
		dao.delete("XJTStorescoreWayModelMapper.deleteBystoreid", pd);
		//新增
		dao.save("XJTStorescoreWayModelMapper.save", pd);
	}

	
	/**
	* 删除积分方式数据
	*/
	public void deleteBystoreid(PageData pd)throws Exception{
		dao.delete("XJTStorescoreWayModelMapper.deleteBystoreid", pd);
	}
	
	/**
	 * 删除积分方式数据
	 */
	public void deleteById(PageData pd)throws Exception{
		dao.delete("XJTStorescoreWayModelMapper.deleteById", pd);
	}
	
	/**
	 * 显示全部
	 * @param pd  
	 * @return 
	 * @throws Exception
	 */
	public List<PageData> list(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("XJTGoods_Mapper.list", pd);
	}
	
	
	/**
	 * 新增
	 * @param pd
	 * @throws Exception
	 */
	public void saveGoods(PageData pd)throws Exception{
		dao.save("XJTGoods_Mapper.save", pd);
	}
 
	
}
