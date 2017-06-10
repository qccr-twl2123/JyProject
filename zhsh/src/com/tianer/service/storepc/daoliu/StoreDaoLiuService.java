package com.tianer.service.storepc.daoliu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 充值记录（商家财富记录表）
 * @author 邢江涛
 *
 */
@Service("storeDaoLiuService")
public class StoreDaoLiuService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;


	/**
	 *  发布广告招商信息/推广需求
	 */
	public void saveDaoLiu(PageData pd)throws Exception{
		dao.save("WhwDaoLiuMapper.saveDaoLiu", pd);
	}
	
	
	/**
	 * 我是广告主--- 导流列表 tb_daoliubymember
	 */
	public List<PageData> daoliulistPage(Page page)throws Exception{
  		return (List<PageData>)dao.findForList("WhwDaoLiuMapper.daoliulistPage", page);
	}
	
	/**
	 * 我是推广商--- 导流列表 tb_daoliubymember
	 */
	public List<PageData> daoliuTuiGuanglistPage(Page page)throws Exception{
  		return (List<PageData>)dao.findForList("WhwDaoLiuMapper.daoliuTuiGuanglistPage", page);
	}
	
	

	/**
	 * 未合作之前的导流的记录详情 tb_daoliu
	 */
	public PageData findByIdDaoLiu(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WhwDaoLiuMapper.findByIdDaoLiu", pd);
	}

	
	//=============================================================
	
	
	/**
	 *  添加合作商家的记录//并删除 tb_daoliurecord
	 */
	public void saveHzStore(PageData pd)throws Exception{
		dao.delete("WhwDaoLiuMapper.deleteHzStore", pd);
		dao.save("WhwDaoLiuMapper.saveHzStore", pd);
 	}
	/**
	 * 判断是否有这个申请记录 tb_daoliurecord
	 */
	public PageData detailThisHzStore(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WhwDaoLiuMapper.detailThisHzStore", pd);
	}
	/**
	 *合作商家的导流详情--根据 tb_daoliurecord
	 */
	public PageData daoliuByHzStoreDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject("WhwDaoLiuMapper.daoliuByHzStoreDetail", pd);
	}
	

	

	
	
 	/**
	 * 商家的导流列表 
	 */
	public List<PageData> daoliuByHzStorelistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WhwDaoLiuMapper.daoliuByHzStorelistPage", page);
	}
	/**
	 * 商家合作的导流列表
	 */
	public List<PageData> daoliuByHzStorelistAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WhwDaoLiuMapper.daoliuByHzStorelistAll", pd);
	}
	/**
	 *  更新导流过期的合作信息
	 */
	public void updateDaoliurecordTime(PageData pd)throws Exception{
		dao.update("WhwDaoLiuMapper.updateDaoliurecordTime", pd);
	}
	
	/**
	 * 商家合作的导流数量
	 */
	public String countAllHzStore(PageData pd)throws Exception{
		return (String)dao.findForObject("WhwDaoLiuMapper.countAllHzStore", pd);
	}
	
	//-----------------------------
	
	/**
	 *  添加会员的点击记录 tb_daoliubymember
	 */
	public void saveMemberClick(PageData pd)throws Exception{
		dao.save("WhwDaoLiuMapper.saveMemberClick", pd);
	}
	
	
	/**
	 * 会员的点击导流列表 
	 */
	public List<PageData>daoliuByMemberlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WhwDaoLiuMapper.daoliuByMemberlistPage", page);
	}
	/**
	 * 统计当页会员的点击导流列表
	 */
	public PageData sumdaoliuByMemberNowPage(Page page) throws Exception {
		return (PageData) dao.findForObject("WhwDaoLiuMapper.sumdaoliuByMemberNowPage", page);
	}

	
	/**
	 * 统计所有的会员的点击导流列表
	 */
	public PageData allsumdaoliuByMember(Page page) throws Exception {
		return (PageData) dao.findForObject("WhwDaoLiuMapper.allsumdaoliuByMember", page);
	}

	/**
	 * 商家合计收益总计
	 */
	public String sumAllHzStore(PageData pd)throws Exception{
		return (String)dao.findForObject("WhwDaoLiuMapper.sumAllHzStore", pd);
	}
	
	
	/**
	 * 查看当前会员今天的点击次数 
	 */
	public Integer countNowDayClickNumber(PageData pd)throws Exception{
		return (Integer)dao.findForObject("WhwDaoLiuMapper.countNowDayClickNumber", pd);
	}
	

	

	
	
	
	//-----------------------------app------------------------------------
	/**
	 * 商家合作的导流列表--获取商家id
	 */
	public List<PageData> listAllStoreByStoreId(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WhwDaoLiuMapper.listAllStoreByStoreId", pd);
	}
  	/**
	 *  更新合作商家的记录
	 */
	public void updateDaoliurecordById(PageData pd)throws Exception{
		dao.update("WhwDaoLiuMapper.updateDaoliurecordById", pd);
	}

 	/**
	 *  删除合作商家的记录
	 */
	public void deleteDaoliurecordById(PageData pd)throws Exception{
		dao.delete("WhwDaoLiuMapper.deleteDaoliurecordById", pd);
	}
	 
}
