/**
 * 
 */
package com.tianer.service.storepc.liangqin.homepage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

/**
 * 类名称: VIPService 
 * 类描述: TODO
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-17 上午9:01:47	
 * 版本号: v1.0
 */
@Service("vipService")
public class VIPService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 线上VIP魏汉文0713
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findExcelVIPlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("LQVIPMapper.findExcelVIPlistPage", page);
	}
	
	/**
	 * 线下VIP魏汉文0713
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findExcelVIPallList(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("LQVIPMapper.findExcelVIPallList", pd);
	}
	
	/**
	 * 线下VIP魏汉文0713
	 */
	public String allVipImageSum(Page page)throws Exception{
		return (String) dao.findForObject("LQVIPMapper.allVipImageSum", page);
	}
	
	/**
	 * 获取已经是商家会员的vip魏汉文0713
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> allVipImagelistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("LQVIPMapper.allVipImagelistPage", page);
	}
	
	/**
	 * 获取已经是商家会员的vip魏汉文0713
	 */
	public String findExcelVIPSum(Page page)throws Exception{
		return (String) dao.findForObject("LQVIPMapper.findExcelVIPSum", page);
	}
	
	/**
	 *删除线下vip通过电话魏0713
	 */
	public void deleteVipByPhone(PageData pd) throws Exception{
		dao.delete("LQVIPMapper.deleteVipByPhone", pd);
	}
	
	/**
	 *删除线下vip魏0713
	 */
	public void deleteVIPOne(PageData pd) throws Exception{
		dao.delete("LQVIPMapper.deleteVIPOne", pd);
	}
	/**
	 * 删除线上vip魏0713
	 */
	public void deleteVIPTwo(PageData pd) throws Exception{
		dao.delete("LQVIPMapper.deleteVIPTwo", pd);
	}
	
	/**
	 * 更新线上会员名称
	 */
	public void editMemberOne(PageData pd) throws Exception{
		dao.update("LQVIPMapper.editMemberOne", pd);
	}
	
//	/**
//	 * 更新线下会员名称
//	 */
//	public void editMemberTwo(PageData pd) throws Exception{
//		dao.update("LQVIPMapper.editMemberTwo", pd);
//	}
	
	/**
	 * 修改线下会员通过导入的表格
	 */
	public void editMemberByStore(PageData pd) throws Exception{
		dao.update("LQVIPMapper.editMemberByStore", pd);
	}
	
	/**
	 *  获取商家的一度人脉的人脉信息
	 */
	public  PageData  findExcelVIPFindById(PageData pd)throws Exception{
		return ( PageData ) dao.findForObject("LQVIPMapper.findExcelVIPFindById", pd);
	}
	
	/**
	 * 导入excel，添加进数据库中
	 */
	public void insetList(PageData pd) throws Exception{
//		System.out.println(pd.toString());
		dao.save("LQVIPMapper.add", pd);
	}
	
	/**
	 *  获取商家的一度人脉的人脉信息
	 */
	public List<PageData> renmaiYiListlistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("LQVIPMapper.renmaiYiListlistPage", page);
	}
	
	
	/**
	 *  获取商家的一度人脉的人脉信息
	 */
	public String renmaiYiSum(Page page)throws Exception{
		return (String) dao.findForObject("LQVIPMapper.renmaiYiSum", page);
	}

}
