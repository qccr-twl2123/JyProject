/**
 * 
 */
package com.tianer.service.storepc.liangqin.shopmanage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.entity.storepc.liangqin.Goods;
import com.tianer.entity.storepc.liangqin.GoodsAndCate;
import com.tianer.entity.storepc.liangqin.Goods_Category;
import com.tianer.util.PageData;

/**
 * 类名称: CategoryManageService 
 * 类描述: 类别管理
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-21 下午2:16:35	
 * 版本号: v1.0
 */
@Service("/categoryManageService")
public class CategoryManageService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	@SuppressWarnings("unchecked")
	public List<PageData> findAllSmalllistPage(Page page) throws Exception{
 		return (List<PageData>) dao.findForList("LQGoods_CategoryMapper.findAllSmalllistPage", page);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> findAllSmallListAll(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("LQGoods_CategoryMapper.findAllSmallListAll", pd);
	}
	
	/**
	 * 
	 * 方法名:findAllBig
	 * 描述:查询所有的大类
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findAllBig(PageData pd) throws Exception{
 		return (List<PageData>) dao.findForList("LQGoods_CategoryMapper.findAllBig", pd);
	}
	@SuppressWarnings("unchecked")
	public String countBig(PageData pd) throws Exception{
		return (String) dao.findForObject("LQGoods_CategoryMapper.countBig", pd);
	}
	

	
	@SuppressWarnings("unchecked")
	public List<PageData> findAllSmal(Page page) throws Exception{
		return (List<PageData>) dao.findForList("LQGoods_CategoryMapper.findAllSmall", page);
	}
	@SuppressWarnings("unchecked")
	public String countSmall(PageData pd) throws Exception{
		return (String) dao.findForObject("LQGoods_CategoryMapper.countSmall", pd);
	}

	
	//-------------------------------------------------------------------
	/*
	 * 查看排序是冲突
	 */
	@SuppressWarnings("unchecked")
	public PageData findCaBySort(PageData pd) throws Exception{
		return (PageData) dao.findForObject("LQGoods_CategoryMapper.findCaBySort", pd);
	}
	/*
	 * 查看详情
	 */
	@SuppressWarnings("unchecked")
	public PageData findByCateId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("LQGoods_CategoryMapper.findByCateId", pd);
	}
	/*
	 * 查看详情
	 */
	@SuppressWarnings("unchecked")
	public PageData findGoodsById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("LQGoods_CategoryMapper.findGoodsById", pd);
	}
	
	/*
	 * 查看详情
	 */
	@SuppressWarnings("unchecked")
	public Integer selectCateXiaoGoods(PageData pd) throws Exception{
		return (Integer) dao.findForObject("LQGoods_CategoryMapper.selectCateXiaoGoods", pd);
	}
	/*
	* 删除类别
	*/
	public void deleteCate(PageData pd)throws Exception{
		dao.update("LQGoods_CategoryMapper.deleteCate", pd);
		dao.update("LQGoods_CategoryMapper.deleteCateXiao", pd);
		dao.update("LQGoods_CategoryMapper.deleteCateXiaoGoods", pd);
	}

	/*
	 * 修改类别
	 */
	public void editCate(PageData pd) throws Exception {
		dao.update("LQGoods_CategoryMapper.editCate", pd);
	}
	
	/*
	 * 修改积分率
	 */
	public void editRate(PageData pd) throws Exception {
		dao.update("LQGoods_CategoryMapper.editRate", pd);
	}
	/*
	 * 获取小类列表
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findCateListById(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("LQGoods_CategoryMapper.findCateListById", pd);
	}
	/**
	 * 
	 * 方法名:addBig
	 * 描述:新增大类
	 *
	 */
	public void addBig(PageData pd) throws Exception{
		dao.save("LQGoods_CategoryMapper.addBig", pd);
	}
	/*
	 * 将小类存入goods中
	 */
	public void addGoods(PageData pd) throws Exception{
		dao.save("LQGoodsMapper.addSmallToGoods", pd);
	}
	
	
	/*
	 * 方法名:editGoods修改商品
	*/
	public void editGoods(PageData pd) throws Exception {
		dao.update("LQGoodsMapper.editGoods", pd);
 	}
	
	/*
	 * 方法名:editGoods修改商品
	 */
	public void updateJfRate(PageData pd) throws Exception {
		dao.update("LQGoodsMapper.updateJfRate", pd);
	}
	
	/*
	 * 删除商品
	 */
	public void deleteGoods(PageData pd)throws Exception{
		dao.update("LQGoodsMapper.deleteGoods", pd);
		//====
		dao.delete("LQGoodsMapper.delRenqi", pd);
		dao.delete("LQGoodsMapper.delTejia", pd);
 	}
 	public void delRenqi(PageData pd)throws Exception{
		dao.delete("LQGoodsMapper.delRenqi", pd);
	}
 	public void delTejia(PageData pd)throws Exception{
		dao.delete("LQGoodsMapper.delTejia", pd);
	}
 	
 	
 	
	/*
	 * 统计商品
	 */
	@SuppressWarnings("unchecked")
	public String countGoods(PageData pd) throws Exception{
		return (String) dao.findForObject("LQGoodsMapper.countGoods", pd);
	}
	
	
	/**
	 * 
	 * 方法名:addSmall
	 * 描述:新增小类
 	 */
 	public void addSmall(PageData  pd) throws Exception{
		dao.save("LQGoods_CategoryMapper.addSmall", pd);
 	}
 	/**
	 * 
	 * 描述:今天特价
 	 */
 	public void savetejia(PageData  pd) throws Exception{
		dao.save("LQGoods_CategoryMapper.savetejia", pd);
 	}
 	/**
	 * 
	 * 描述:人气榜
 	 */
 	public void saverenqi(PageData  pd) throws Exception{
		dao.save("LQGoods_CategoryMapper.saverenqi", pd);
 	}
 	
 	public PageData findRenqi(PageData  pd) throws Exception {
		return (PageData)dao.findForObject("LQGoods_CategoryMapper.findRenqi", pd);
	}
 	public PageData findTejia(PageData  pd) throws Exception {
		return (PageData)dao.findForObject("LQGoods_CategoryMapper.findTejia", pd);
	}
 	
 	public String countRenqi(PageData  pd) throws Exception {
 		return (String)dao.findForObject("LQGoods_CategoryMapper.countRenqi",pd);
 	}
 	public String countTejia(PageData  pd) throws Exception {
 		return (String)dao.findForObject("LQGoods_CategoryMapper.countTejia",pd);
 	}
 	
 	
 	/**
 	 * 人气榜数据
 	 * @param page
 	 * @return
 	 * @throws Exception
 	 */
 	public List<PageData> renqiList(Page page) throws Exception {
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.renqilistPage", page);
	}
 	
 	/**
 	 * 今日特价数据
 	 * @param page
 	 * @return
 	 * @throws Exception
 	 */
 	public List<PageData> tejiaList(Page page) throws Exception {
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.tejialistPage", page);
	}

	/**
	 * 根据大类名称查找对应的大类ID
	 */
	public Goods_Category findBigIdByName(String name) throws Exception{
		return (Goods_Category) dao.findForObject("LQGoods_CategoryMapper.findBigIdByName", name);
	}
	
	
	/**
	 * 将新建的小类存入goods_category里面
	 */
	public void addSmallToCategory(Goods_Category gc)throws Exception{
		dao.save("LQGoods_CategoryMapper.addSmallToCategory", gc);
	}
//	/**
//	 * 将小类存入goods中
//	 */
//	public void addSmallToGoods(Goods gs) throws Exception{
//		dao.save("LQGoodsMapper.addSmallToGoods", gs);
//	}
	/**
	 * 方法名:findSmallIdByName
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public Goods_Category findSmallIdByName(String goods_name) throws Exception {
		return (Goods_Category)dao.findForObject("LQGoods_CategoryMapper.findSmallIdByName", goods_name);		
	}
	/**
	 * 方法名:findSmallByName
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public PageData findSmallByName(String goods_name) throws Exception {
		return (PageData)dao.findForObject("LQGoods_CategoryMapper.findSmallByName", goods_name);
	}
	/**
	 * (最好别用)
	 */
	public List<PageData> findAll(Page page) throws Exception{
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.findAll", page);
	}
	/**
	 * 方法名:findEach
	 * 描述:查找大类对应的小类的详细信息
	 * @throws Exception 
	 *
	*/
	public List<PageData> findEach(Page page) throws Exception {
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.findEachlistPage", page);
	}
	
	public List<PageData> findEachAllGoods(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.findEachAllGoods", pd);
	}
	
	public List<PageData> findAllEach(Page page) throws Exception {
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.findAllEach", page);
	}
	/**
	 * 方法名:findSortByConlistPage
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public List<PageData> findSortByConlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.findSortByConlistPage", page);
	}
	/**
	 * 方法名:findSortByStolistPage
	 * 描述:TODO
	 *
	*/
	public List<PageData> findSortByStolistPage(Page page) throws Exception{
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.findSortByStolistPage", page);
	}
	/**
	 * 方法名:findSortBySorlistPage
	 * 描述:TODO
	 *
	*/
	public List<PageData> findSortBySorlistPage(Page page) throws Exception{
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.findSortBySorlistPage", page);
	}
	/**
	 * 方法名:findGoodsByTimelistPage
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public List<PageData> findGoodsByTimelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("LQGoods_CategoryMapper.findGoodsByTimelistPage", page);
	}
	/**
	 * 方法名:findAllCCC
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public List<GoodsAndCate> excelListAll(PageData pd) throws Exception {
		return (List<GoodsAndCate>)dao.findForList("LQGoods_CategoryMapper.excelListAll", pd);
	}
	/**
	 * 方法名:updateStockNum
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public void updateStockNum(Goods gd) throws Exception {
		dao.update("LQGoodsMapper.updateStockNum", gd);
 	}


}
