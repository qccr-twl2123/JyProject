package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("appOrderService")
public class AppOrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	


	/*
	 *列表
	 */
	public List<PageData> datalistPageByOrder(Page   page)throws Exception{
		return (List<PageData>)dao.findForList("AppOrderMapper.datalistPageByOrder", page);
	}

	/*
	*列表(全部)
	*刘耀耀
	*2016.06.21
	*/
	public List<PageData> list(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppOrderMapper.list", pd);
	}
	
	/*
	*删除（假删除）
	*/

	public void delete(PageData pd) throws Exception {
			dao.update("AppOrderMapper.deleteOrder", pd);
  	}
	
	/*
	*历史订单
	*刘耀耀
	*2016.06.21
	*/
	public List<PageData> listhistory(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppOrderMapper.history", pd);
	}
	/*
	*更多历史订单
	*刘耀耀
	*2016.06.21
	*/
	public List<PageData> listhistorym(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppOrderMapper.historym", pd);
	}


	

	/*
	 *提货卷列表fenye
	 */
	public List<PageData> datalistPageByTiHuo(Page   page)throws Exception{
		return (List<PageData>)dao.findForList("AppOrderMapper.datalistPageByTiHuo", page);
	}
	
	/*
	 * 提货卷列表
	*魏汉文20160630
	*/
	public List<PageData> tihuoList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppOrderMapper.tihuoList", pd);
	}
	
	
	
	/*
	 * 提货卷等待提货列表
	 *魏汉文20160630
	 */
	public List<PageData> tihuoListByendTime(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppOrderMapper.tihuoListByendTime", pd);
	}
	
	
	/*
	 * 提货卷详情
	*魏汉文20160630
	*/
	public  PageData  tihuoByOrderId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppOrderMapper.tihuoByOrderId", pd);
	}
	
	
	/*
	 * 提货卷详情
	 *魏汉文20160630
	 */
	public  Integer  countStoreMember(PageData pd)throws Exception{
		return (Integer)dao.findForObject("AppOrderMapper.countStoreMember", pd);
	}
	
	/*
	*新增订单
	*魏汉文20160705
	*/
 	public void saveOrder(PageData pd) throws Exception {
			dao.save("AppOrderMapper.saveOrder", pd);
  	}
 
	/*
	 *获取状态为0的订单
 	 */
	public List<PageData> getStatusZero(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppOrderMapper.getStatusZero", pd);
	}
  	/*
 	 *删除未支付的订单=====获取order_id
  	 */
 	public void deleteStatusZero(PageData pd) throws Exception {
 		dao.delete("AppOrderMapper.deleteStatusZero", pd);
 	}
 	/*
 	 * 删除订单以及订单中关联的的商品，order_id
 	 */
 	public void deleteOrderShop(PageData pd) throws Exception {
 		dao.delete("AppOrderMapper.deleteOrderShop", pd);
 		dao.delete("AppOrderMapper.deleteOrderGoods", pd);
 	}
  	/*
 	 *删除订单中关联的的商品====通过order_goods_id
  	 */
 	public void deleteStatusZeroGoods(PageData pd) throws Exception {
 		dao.delete("AppOrderMapper.deleteStatusZeroGoods", pd);
 	}
 	/*
 	 *删除订单中关联的的商品===通过order_id
 	 */
 	public void deleteOrderGoods(PageData pd) throws Exception {
 		dao.delete("AppOrderMapper.deleteOrderGoods", pd);
 	}

 	
 	/*
	*新增订单商品表
	*魏汉文20160705
	*/
 	public void saveOrderGoods(PageData pd) throws Exception {
			dao.save("AppOrderMapper.saveOrderGoods", pd);
  	}
 	
 	
	/*
	 * 订单详情
	*魏汉文20160630
	*/
	public  PageData  findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppOrderMapper.findById", pd);
	}
	
	/*
	 * 订单详情
	 *魏汉文20160630
	 */
	public  PageData  findByPayOverId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppOrderMapper.findByPayOverId", pd);
	}
	
 	
 	/*
 	 *修改订单状态
 	 *魏汉文20160705
 	 */
 	public void editStatus(PageData pd) throws Exception {
 		dao.delete("AppOrderMapper.editStatus", pd);
 	}
	
 	
	/*
	*历史订单数量 
	*/
	public PageData listhistoryNumberByStore(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AppOrderMapper.listhistoryNumberByStore", pd);
	}
	
	/*
	 * 订单详情
	 */
	public PageData findByOrderId(PageData pd)throws Exception{
		return (PageData) dao.findForObject("AppOrderMapper.findByOrderId", pd);
	}
 	
	
	/*
	 * 获取当前商品的完成订单的购买数量
	 */
	public PageData getOrderNumberByGoods(PageData pd)throws Exception{
		return (PageData) dao.findForObject("AppOrderMapper.getOrderNumberByGoods", pd);
	}
	
 	
 	/*
 	 *获取没有关联到的商品
 	 */
 	public List<PageData> getAllOrderGoods(PageData pd) throws Exception {
 		return (List<PageData>)dao.findForList("AppOrderMapper.getAllOrderGoods", pd);
 	}
 	
 	/*
 	 *关联号==============================================================================================================================
 	 */
 	public void saveguanlian(PageData pd) throws Exception {
 		dao.save("AppOrderMapper.saveguanlian", pd);
 	}
 	public void deleteguanlian(PageData pd) throws Exception {
 		dao.delete("AppOrderMapper.deleteguanlian", pd);
 	}
 	public void updateguanlian(PageData pd) throws Exception {
 		dao.update("AppOrderMapper.updateguanlian", pd);
 	}
 	public PageData getguanlianById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppOrderMapper.getguanlianById", pd);
	}
 	public PageData sumguanlianById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppOrderMapper.sumguanlianById", pd);
	}
 	//============================================================================================================================
	
	/*
 	 *库存占用的订单号==============================================================================================================================
 	 */
 	public void savekuncunOrder(PageData pd) throws Exception {
 		dao.save("AppOrderMapper.savekuncunOrder", pd);
 	}
 	public void deletekuncunOrder(PageData pd) throws Exception {
 		dao.delete("AppOrderMapper.deletekuncunOrder", pd);
 	}
    public List<PageData> allkuncunOrder(PageData pd) throws Exception {
 		return (List<PageData>)dao.findForList("AppOrderMapper.allkuncunOrder", pd);
 	}
 	
 	public PageData detailkuncunOrde(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppOrderMapper.detailkuncunOrder", pd);
	}
 	//============================================================================================================================
	
 	
}

