package com.tianer.service.business.store;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("storeService")
public class StoreService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("StoreMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("StoreMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("StoreMapper.edit", pd);
	}
	
	/*
	 * 修改综合评分值0805
	 */
//	public void editComplexscore(PageData pd)throws Exception{
//		dao.update("StoreMapper.editComplexscore", pd);
//	}
	
	/*
	 * 修改次数0805
	 */
	public void editNumber(PageData pd)throws Exception{
		dao.update("StoreMapper.editNumber", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.datalistPage", page);
	}
	
	

	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.listAll", pd);
	}
	
	
	/*
	 *通过手机号码获取商家信息
	 *魏汉文20160608
	 */
	public List<PageData> listAllByPhone(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.listAllByPhone", pd);
	}
	/*
	 *通过手机号码/商家ID号获取商家信息
	 *魏汉文20160608
	 */
	public List<PageData> listAllByPhoneForLike(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.listAllByPhoneForLike", pd);
	}

	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StoreMapper.findById", pd);
	}	

	/*
	* 通过id或者name获取数据
	* 刘耀耀 2016.06.12
	*/
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StoreMapper.findByName", pd);
	}
	
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StoreMapper.deleteAll", ArrayDATA_IDS);
	}
	

	/*
	* 通过id获取数据魏汉文20160608（人脉查找）
	*/
	public PageData detailById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StoreMapper.detailById", pd);
	}
	
	
	/*
	 *通过商家获取所有红包魏汉文20160613
	 */
	public List<PageData> getRedPackageListStore(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.getRedPackageListStore", pd);
	}
	
	
	/*
	 * 通过红包ID获取数据魏汉文20160613
	 */
	public PageData findByRedPackageId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StoreMapper.findByRedPackageId", pd);
	}	
	
	
	/*
	*通过服务商获取该服务商下的商家魏汉文20160615
	*/
	public List<PageData> getStoreList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.getStoreList", pd);
	}
	/*
	*通过id获取vip_id刘耀耀20160620
	*/
	public List<PageData> findVipId(PageData pd) throws Exception {
		return   (List<PageData>) dao.findForObject("StoreMapper.getVidId", pd);
	}

	public List<PageData> findNamePwd(PageData pd) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 *商家筛选列表20160802
	 */
	public List<PageData> getStorelistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.getStorelistPage", page);
	}
	
	
	/*
	 *商家筛选列表20160802
	 */
	public String count(String storeId)throws Exception{
		return (String)dao.findForObject("StoreMapper.count", storeId);
	}
	
	/*
	 * 获取所有的pk商家
	 */
	public List<PageData> listAllPkStore(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoreMapper.listAllPkStore", pd);
	}
	
}

