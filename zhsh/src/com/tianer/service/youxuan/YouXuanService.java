package com.tianer.service.youxuan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


/**
 * 优选商品的集合
 */
@Service("youXuanService")
public class YouXuanService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	//------------------------------------------------商品-----------------------------------------------
 	/**
	 * 新增优选商品
	 */
	public void saveGoods(PageData pd)throws Exception{
		dao.save("YouXuanMapper.saveGoods", pd);
	}

	
	/**
	 * 修改优选商品
	 */
	public void editGoods(PageData pd)throws Exception{
		dao.update("YouXuanMapper.editGoods", pd);
	}
 	/**
	 * 商品详情
	 */
	public  PageData  findByIdGoods(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.findByIdGoods", pd);
	}
	
	/**
	 * 优选商品的列表
	 */
 	public List<PageData> datalistPageGoods(Page page) throws Exception {
		return (List<PageData>) dao.findForList("YouXuanMapper.datalistPageGoods", page);
	}

 	
	
	/**
	 * 所有优选商品的列表
	 */
 	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YouXuanMapper.listAll", pd);
  	}
 	
 	/**
	 * 统计当前页的商品编辑费用总和
	 */
	public  PageData  sumNowPageGoods(Page page) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.sumNowPageGoods", page);
	}
	/**
	 * 统计所有的商品数字总和
	 */
	public  PageData  sumallGoods(Page page) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.sumallGoods", page);
	}
	
	
	/**
	 * 获取当前商品的揭晓进度
	 */
	public  String  jxjdByGoods(PageData pd) throws Exception{
		return ( String ) dao.findForObject("YouXuanMapper.jxjdByGoods", pd);
	}

 	//------------------------------------------------商品规格-----------------------------------------------
	/**
	 * 新增规格
	 */
	public void saveGoodsgg(PageData pd)throws Exception{
		dao.save("YouXuanMapper.saveGoodsgg", pd);
	}
	/**
	 * 删除规格
	 */
	public void deletegg(PageData pd)throws Exception{
		dao.update("YouXuanMapper.deletegg", pd);
	}
	/**
	 * 修改规格的数量以及金额
	 */
	public void editGoodsgg(PageData pd)throws Exception{
		dao.update("YouXuanMapper.editGoodsgg", pd);
	}
 	/**
	 * 规格详情
	 */
	public  PageData  finddetailgg(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.finddetailgg", pd);
	}
	/**
	 * 所有商品的规格
	 */
	public List<PageData> listAllGoodsgg(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YouXuanMapper.listAllGoodsgg", pd);
	}
	

 	/**
 	 * 优选商品规格的列表---分页
 	 */
 	public List<PageData> datalistPageGoodsgg(Page page) throws Exception {
 		return (List<PageData>) dao.findForList("YouXuanMapper.datalistPageGoodsgg", page);
 	}
	/**
	 * 统计当前页的规格商品数字总和
	 */
	public  PageData  sumNowPageGoodsgg(Page page) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.sumNowPageGoodsgg", page);
	}
	/**
	 * 统计所有的规格商品数字总和
	 */
	public  PageData  sumallGoodsgg(Page page) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.sumallGoodsgg", page);
	}
	/**
	 * 更新当前商品剩余的库存量
	 */
	public void updateYouXuanGoodsBuyNumber(PageData pd)throws Exception{
		dao.update("YouXuanMapper.updateYouXuanGoodsBuyNumber", pd);
	}
	
	/**
	 * 当前商品的剩余库存,剩下出售的商品数量
	 */
	public  Integer  lesssale_number(PageData pd) throws Exception{
		return ( Integer ) dao.findForObject("YouXuanMapper.lesssale_number", pd);
	}
	
	
	/**
	 * 单价的最大值
	 */
	public  Double  maxSaleMoney(PageData pd) throws Exception{
		return ( Double ) dao.findForObject("YouXuanMapper.maxSaleMoney", pd);
	}
	
	
	/**
	 * 单价的最小值
	 */
	public  Double  minSaleMoney(PageData pd) throws Exception{
		return ( Double ) dao.findForObject("YouXuanMapper.minSaleMoney", pd);
	}
   	
	
	//------------------------------------------------商品介绍-------------------------------------------------
	/**
	 * 新增介绍
	 */
	public void saveGoodsjj(PageData pd)throws Exception{
		dao.save("YouXuanMapper.saveGoodsjj", pd);
	}
	/**
	 * 删除介绍
	 */
	public void deletejj(PageData pd)throws Exception{
		dao.delete("YouXuanMapper.deletejj", pd);
	}
	/**
	 * 介绍更新
	 */
	public void editGoodsjj(PageData pd)throws Exception{
		dao.update("YouXuanMapper.editGoodsjj", pd);
	}
	/**
	 * 介绍详情
	 */
	public  PageData  finddetailjj(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.finddetailjj", pd);
	}
	/**
	 * 所有商品的介绍
	 */
	public List<PageData> listAllGoodsjj(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("YouXuanMapper.listAllGoodsjj", pd);
	}
	
	/**
	 * 修改商品销售信息（金额/数量） 魏汉文
	 */
	public  PageData  editGoodsAllSale(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.editGoodsAllSale", pd);
	}
	
 	//----------------------------------------------------商品档期-------------------------------------------------
  	/**
	 * 新增档期
	 */
	public void saveDangQi(PageData pd)throws Exception{
		dao.save("YouXuanMapper.saveDangQi", pd);
	}

	/**
	 * 修改档期
	 */
	public void editDangqi(PageData pd)throws Exception{
		dao.update("YouXuanMapper.editDangqi", pd);
	}
  	/**
	 * 档期详情
	 */
	public  PageData  finddetailDangqi(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.finddetailDangqi", pd);
	}


	/**
	 * 定时
	 */
	public  PageData  finddetailDangqiDingShi(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.finddetailDangqiDingShi", pd);
	}


  	/**
 	 * 所有档期列表
 	 */
 	public List<PageData> listAllDangqi(PageData pd) throws Exception {
 		return (List<PageData>) dao.findForList("YouXuanMapper.listAllDangqi", pd);
 	}
 	
	/**
	 * 到时间修改档期以及商品的状态
	 */
	public void editDangqiAndGoods(PageData pd)throws Exception{
		dao.update("YouXuanMapper.editDangqi", pd);
		dao.update("YouXuanMapper.editGoodsForDq", pd);
	}
	
	/**
	 * 获取最的商品ID
	 */
	public  String  getMaxId(PageData pd) throws Exception{
		return ( String ) dao.findForObject("YouXuanMapper.getMaxId", pd);
	}

 	
	
	
	//---------------------------APP接口-------------------------
	/**
	 * 获取当前的档期
	 */
	public  PageData  getnowDq(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("YouXuanMapper.getnowDq", pd);
	}
  	
 	
	/**
	 * app优选首页商品的列表--分页
	 */
 	public List<PageData> AppdatalistPageGoods(Page page) throws Exception {
		return (List<PageData>) dao.findForList("YouXuanMapper.AppdatalistPageGoods", page);
	}
 	
	/**
	 * 优选添加商品订单
	 */
	public void saveYouxuanOrder(PageData pd)throws Exception{
		dao.save("YouXuanMapper.saveYouxuanOrder", pd);
	}
	

}
