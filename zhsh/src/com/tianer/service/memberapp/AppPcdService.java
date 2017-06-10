package com.tianer.service.memberapp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


@Service("appPcdService")
public class AppPcdService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 新增log日志
	 */
	public void saveLog(String order_id,String message,String type)throws Exception{
		PageData logpd=new PageData();
		logpd.put("order_id", order_id);
		logpd.put("message", message);
		logpd.put("type", type);
 		dao.save("AppPcdMapper.saveLog", logpd);
	}
	
	
	/*
	* 查询市
	*/
	public List<PageData> listcity(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppPcdMapper.listcity", pd);
	}
	
	
	/*
	* 查询区
	*/
	public List<PageData> listarea(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppPcdMapper.listarea", pd);
	}
	
	
	/*
	* 修改现金/折扣红包状态
	*/
	public void edit(PageData pd) throws Exception {
		dao.update("AppPcdMapper.memberRedpackage", pd);//修改会员红包的过期状态
 		dao.update("AppPcdMapper.storeRedpackage", pd);//修改商家红包的过期状态
 		dao.update("AppPcdMapper.updateTypeTime", pd);//修改营销类型表的过期状态
		dao.update("AppPcdMapper.updateMarkTime", pd);//修改营销表的过期状态
		dao.update("AppPcdMapper.updateDiscountwayTime", pd);//修改折扣表的过期状态
//		dao.update("AppPcdMapper.updateUnionTime", pd);//修改联盟状态
		dao.update("AppPcdMapper.updatePCImage", pd);//修改app轮播图状态
		dao.update("AppPcdMapper.updateAPPImage", pd);//修改pc轮播图状态
	}
	
	/*
	* 查询所有已过期的积分
	*/
 	public List<PageData> allPassTimeJfRed(PageData pd) throws Exception {
		List<PageData> s=(List<PageData>)dao.findForList("AppPcdMapper.allPassTimeJfRed", pd);
  		return s;
	}
 	
	
	/*
	* 查看积分红包的详情
	*/
 	public PageData JfRedDetailById(PageData pd) throws Exception {
 		PageData s=(PageData)dao.findForObject("AppPcdMapper.JfRedDetailById", pd);
  		return s;
	}
	
	
	/*
	* 修改积分红包状态
	*/
	public void updatePassTimeJfRed(PageData pd) throws Exception {
		dao.update("AppPcdMapper.updatePassTimeJfRed", pd); 
	}
	
	
	/*
	 * 修改======推送状态
	 */
	public void ChangeTuiSongSatatus(PageData pd) throws Exception {
		dao.update("AppPcdMapper.ChangeOrderTuiSongSatatus", pd); 
		dao.update("AppPcdMapper.ChangeMemberRedPackageTuiSongSatatus", pd); 
	}

	
	/*
	 * 查询月销售
	 */
	public List<PageData> listAllStoreByOrderForFY(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppPcdMapper.listAllStoreByOrderForFY", pd);
	}
	
	/*
	 * 查询月销售
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppPcdMapper.listAll", pd);
	}
	
	
	
	/*
	 * 通过省市区获取城市营销ID
	 */
	public PageData    getCityForName(PageData pd) throws Exception {
		return ( PageData )dao.findForObject("AppPcdMapper.getCityForName", pd);
	}
	

	
	
	/*
	 * 获取app版本号
	 */
	public PageData    getappbanbenhao(PageData pd) throws Exception {
		return ( PageData )dao.findForObject("AppPcdMapper.getappbanbenhao", pd);
	}
	
	
	
	/*
	 * 红包个数
	 */
	public List<PageData> countNotUserRed2(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppPcdMapper.countNotUserRed2", pd);
	}
	public List<PageData> countNotUserRed1(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppPcdMapper.countNotUserRed1", pd);
	}
	
	/*
	 * 统计未提货的提货券个数
	 */
	public List<PageData> countNotUserOrder(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppPcdMapper.countNotUserOrder", pd);
	}
	
	
	/*
	 * 获取商家省市区
	 */
	public PageData    getpcdBystore(PageData pd) throws Exception {
		return ( PageData )dao.findForObject("AppPcdMapper.getpcdBystore", pd);
	}
	/*
	 * 获取商家操作员省市区
	 */
	public PageData    getpcdBystoreoprator(PageData pd) throws Exception {
		return ( PageData )dao.findForObject("AppPcdMapper.getpcdBystoreoprator", pd);
	}
	/*
	 * 获取会员省市区
	 */
	public PageData    getpcdBymember(PageData pd) throws Exception {
		return ( PageData )dao.findForObject("AppPcdMapper.getpcdBymember", pd);
	}
	/*
	 * 获取服务商省市区
	 */
	public PageData    getpcdBysp(PageData pd) throws Exception {
		return ( PageData )dao.findForObject("AppPcdMapper.getpcdBysp", pd);
	}
	
	/*
	 * 获取推荐人的信息
	 */
	public PageData    getrecommendedByStoreId(PageData pd) throws Exception {
		return ( PageData )dao.findForObject("AppPcdMapper.getrecommendedByStoreId", pd);
	}
	public PageData    getrecommendedByMemberId(PageData pd) throws Exception {
		return ( PageData )dao.findForObject("AppPcdMapper.getrecommendedByMemberId", pd);
	}
	
	
	
	/*
	 * 获取服务商的月销售记录数量
	 */
	public String   CountSpMonthGetMoney(PageData pd) throws Exception {
		return ( String )dao.findForObject("AppPcdMapper.CountSpMonthGetMoney", pd);
	}
	
	
	
}

