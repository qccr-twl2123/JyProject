package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("appStoreService")
public class AppStoreService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
	* 修改
	* 刘耀耀
	* 2016.06.20
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("AppStoreMapper.edit", pd);
	}
	

	/*
	* 更新收藏次数
  	*/
	public void editCollectNumber(PageData pd)throws Exception{
		dao.update("AppStoreMapper.editCollectNumber", pd);
	}
 	/*
	 * 更新赞次数
 	 */
	public void editZanNumber(PageData pd)throws Exception{
		dao.update("AppStoreMapper.editZanNumber", pd);
	}
	
	
	/*
	 * 修改
	 * 刘耀耀
	 * 2016.06.20
	 */
	public void editFeeByCity(PageData pd)throws Exception{
		dao.update("AppStoreMapper.editFeeByCity", pd);
	}

	
	/*
	 *列表one
	 */
	public List<PageData> contactStoreList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.contactStoreList", pd);
	}
	

	
	/*
	 *列表two
	 */
	public List<PageData> contactStoreForOpratorList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.contactStoreForOpratorList", pd);
	}
	

  
	
	/*
	 * 通过推荐号码获取手机号魏汉文20160620
	 */
	public PageData findIdByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findIdByPhone", pd);
	}	
	
	/*
	 * 获取商家详情魏汉文20160622
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findById", pd);
	}	
	
	/*
	 * 获取商家的银行卡魏汉文20160622
	 */
	public PageData findBankById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findBankById", pd);
	}	
	/*
	 * 获取商家的密码
	 */
	public String findPassword(String s)throws Exception{
		return (String)dao.findForObject("AppStoreMapper.findPassword", s);
	}	
	
	/*
	 * 获取app商家详情魏汉文20160629
	 */
	public PageData findByIdOne(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findByIdOne", pd);
	}	
	

	/*
	 * 获取服务商信息
	 */
	public PageData findSpById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findSpById", pd);
	}	
	
	
	/*
	 *人脉获取的精简商家魏汉文20160701
	 */
	public PageData contactStore(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.contactStore", pd);
	}	
	
	/*
	 *人脉获取的精简商家的操作员魏汉文20160701
	 */
	public PageData contactStoreForOprator(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.contactStoreForOprator", pd);
	}	
	
	/*
	 * 获取app商家的vip图片的地址魏汉文20160629
	 */
	public PageData findVipImage(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findVipImage", pd);
	}	
	
	/*
	 * 获取商家名称魏汉文20160624
	 */
	public PageData findNameById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findNameById", pd);
	}	
	
	/*
	 * 获取商家详情精简型魏汉文20160622
	 */
	public PageData findByIdForSack(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findByIdForSack", pd);
	}	

	
	/*
	 *查询消费支付方式魏汉文20160622
	 */
	public PageData getPayWayById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.getPayWayById", pd);
	}	
	
	
	/*
	 *查询消费支付方式魏汉文20160622
	 */
	public PageData findCollectId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findCollectId", pd);
	}	

	/*
	*通过id获取vip_id刘耀耀20160620
	*/
	public String findVipId(PageData pd) throws Exception {
		return   (String) dao.findForObject("AppStoreMapper.getVidId", pd);
	}
	
	/*
	 *获取商家总数魏汉文20160622
	 */
	public String countStore() throws Exception {
		return   (String) dao.findForObject("AppStoreMapper.countStore", null);
	}
	
	/*
	 *获取商家总数魏汉文20160622
	 */
	public String countStoreTwo(PageData pd) throws Exception {
		return   (String) dao.findForObject("AppStoreMapper.countStore", pd);
	}

	
	
	/*
	 *获取所有商家的信息列表
	 *汉文20160621
	 */
	public List<PageData> getStoreList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.getStoreList", pd);
	}
	
	
	
	
	/*
	* 批量获取商家商品
	* 魏汉文20160621
	*/
	public List<PageData> selectAllGoods(String[] array)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.selectAllGoods", array);
 	}
	/*
	* 点赞记录
	* 刘耀耀20160622
	*/
	public void saveZan(PageData pd) throws Exception {
		dao.save("AppStoreMapper.saveZan", pd);
	}
	
	/*
	* 获取攒的列表
	* 刘耀耀20160622
	*/
	public List<PageData> findZan(PageData pd) throws Exception {
		return  (List<PageData>) dao.findForList("AppStoreMapper.listZan", pd);
	}
	
	/*
	*通过id获取赞的数量刘耀耀20160623
	*/
	public String findNumber(PageData pd) throws Exception {
		return   (String) dao.findForObject("AppStoreMapper.getZan", pd);
	}
	
	/*
	*情况当天的赞刘耀耀20160623
	*/
	public void delete() throws Exception {
		dao.delete("AppStoreMapper.deleteZan", null);
	}

	
	
	/*
	 * 获取商家收藏信息魏汉文20160629
	 */
	public PageData getCollectionById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.getCollectionById", pd);
	}	
	
	/*
	*新增收藏记录
	* 魏汉文20160629
	*/
	public void saveCollect(PageData pd) throws Exception {
		dao.save("AppStoreMapper.saveCollect", pd);
	}
	
	/*
	 *取消收藏魏汉文20160629
	 */
	public void deleteCollect(PageData pd) throws Exception {
		dao.delete("AppStoreMapper.deleteCollect", pd);
	}
	
	/*
	* 获取收藏的列表
	* 魏汉文20160629
	*/
	public List<PageData> listCollectionById(PageData pd) throws Exception {
		return  (List<PageData>) dao.findForList("AppStoreMapper.listCollectionById", pd);
	}
	
	
	/*
	 * 会员消费过的商家列表
	 * 魏汉文20160629
	 */
	public List<PageData> saleOrderForStoreByMem(PageData pd) throws Exception {
		return  (List<PageData>) dao.findForList("AppStoreMapper.saleOrderForStoreByMem", pd);
	}
	
	
	/*
	 * 获取商家财富详情魏汉文20160622
	 */
	public PageData findWealthById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findWealthById", pd);
	}	
    	/*
	 * 修改商家积分的总余额 money和store_id
	 */
	public void editJFWealthById(PageData pd) throws Exception{
		dao.update("AppStoreMapper.editJFWealthById", pd);
	}
	/* 
	 * 获取商家的总余额 store_id
	 */
	public String sumStoreWealth(PageData pd) throws Exception{
		return (String)dao.findForObject("AppStoreMapper.sumStoreMoney", pd);
	}
// 	/*
//	 * 获取商家财富总余额魏汉文 store_id
//	 */
//	public String sumStoreMoney(PageData pd)throws Exception{
//		return (String)dao.findForObject("AppStoreMapper.sumStoreMoney", pd);
//	}	
 	/*
	 * 获取商家财富历史记录详情魏汉文20160622
	 */
	public PageData findWealthHistoryById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findWealthHistoryById", pd);
	}	
	

	/*
	 * 修改商家财富详情魏汉文20160622
	 */
	public void editWealthById(PageData pd)throws Exception{
		dao.update("AppStoreMapper.editWealthById", pd);
	}
	
	
	/*
	 *修改商家财富历史状态魏汉文20160622
	 */
	public void editWealthHistoryStatus(PageData pd)throws Exception{
		dao.update("AppStoreMapper.editWealthHistoryStatus", pd);
	}
	
	/*
	 *修改商家财富历史状态魏汉文20160622
	 */
	public void editWealthHistoryStatusTwo(PageData pd)throws Exception{
		dao.update("AppStoreMapper.editWealthHistoryStatusTwo", pd);
	}
	
	
	
	/*
	 *修改商家保证金的状态
 	 */
	public void editEarnestType(PageData pd) throws Exception {
		dao.update("AppStoreMapper.editEarnestType", pd);
	}
	
	/*
	 *人脉联系人详情
	 *刘耀耀20160706
	 */
	public PageData contactSrore(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.contactSrore", pd);
	}


	
	
	/*
	*新增商家的财富历史记录表
	* 魏汉文20160629
	*/
	public void saveWealthhistory(PageData pd) throws Exception {
		dao.save("AppStoreMapper.saveWealthhistory", pd);
	}
	
	
	
	
	/*
	 *获取所有已经购买服务费的商家
	 */
	public List<PageData> listAllOkStore(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.listAllOkStore", pd);
	}
	
	/*
	 *获取所有注册的商家
	 */
	public List<PageData> listAllStore(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.listAllStore", pd);
	}
 	
 	
	
	/*
	 *统计当前用户的一度二度人脉个数
	 */
	public String countNowUserContacts(String user_id)throws Exception{
		return (String)dao.findForObject("AppStoreMapper.countNowUserContacts", user_id);
	}
	/*
	 *统计当前商家总共消费的订单总数
	 */
	public String countNowStoreOrders(String store_id)throws Exception{
		return (String)dao.findForObject("AppStoreMapper.countNowStoreOrders", store_id);
	}
	/*
	 *统计当前商家总共消费金额
	 */
	public String sumNowStoreOrders(String store_id)throws Exception{
		return (String)dao.findForObject("AppStoreMapper.sumNowStoreOrders", store_id);
	}
	
	
	/*
	 *新增商家的人脉收益记录表
	 */
	public void saveStoreRenMaiJf(PageData pd) throws Exception {
		dao.save("AppStoreMapper.saveStoreRenMaiJf", pd);
	}
 	/*
	 *新增商家的人脉收益关联的订单记录表
	 */
	public void saveStoreRenMaiJf_order(PageData pd) throws Exception {
		dao.save("AppStoreMapper.saveStoreRenMaiJf_order", pd);
	}
	/*
	 * 判断当前的记录是否已经生成
	 */
	public PageData findByStoreRenMaiJf(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppStoreMapper.findByStoreRenMaiJf", pd);
	}
	
	
	/*
	 *获取最近距离的12位商家
	 */
	public List<PageData> listJlMinMoreStore(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.listJlMinMoreStore", pd);
	}
 	

	
	/*
	 *获取所有注册的商家以及面(没有用到)
	 */
	public List<PageData> listPasswordStore(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.listPasswordStore", pd);
	}
	/*
	 *获取所有注册的操作员以及面(没有用到)
	 */
	public List<PageData> listPasswordStoreOparator(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.listPasswordStoreOparator", pd);
	}
	
	
	
	
	/**
	 *   617以下接口开始更新
	 */
  	/*
	 *带分页的商家
	 */
	public List<PageData> getStorelistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppStoreMapper.getStorelistPage", page);
	}
	
	
	
	
	

}

