package com.tianer.service.memberPc;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("pcTongYongService")
public class PcTongYongService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	 
	
	/*
	* 通过省市区获取详情
	*/
	public PageData getCityMarketingForPCD(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PcTongYong.getCityMarketingForPCD", pd);
	}
	
	
	/*
	*PC页面的广告列表
	*/
	public List<PageData> allPcPictureById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PcTongYong.allPcPictureById", pd);
	}
	
	/*
	 *当前城市的关键字
	 */
	public List<PageData> listAllcontent(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PcTongYong.listAllcontent", pd);
	}
	
	/*
	*获取所有市 
	*/
	public List<PageData> listAllCity(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PcTongYong.listAllCity", pd);
	}
	
	/*
	*通过市ID获取所有的区域
	*/
	public List<PageData> listAllArea(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PcTongYong.listAllArea", pd);
	}
	
	/*
	*获取当前区域的所有一级分类
	*/
	public List<PageData> listAllCitySort( PageData pd  )throws Exception{
		return (List<PageData>)dao.findForList("PcTongYong.listAllCitySort", pd);
	}
	

	/*
	 *获取商家的所有营销规则（首页获取前两条）
 	 */
	public List<PageData> listAllMarketingLimitTwo(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PcTongYong.listAllMarketingLimitTwo", pd);
	}
	
	/*
	 *获取商家的所有营销规则 
	 */
	public List<PageData> listAllMarketing(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PcTongYong.listAllMarketing", pd);
	}
	

	/*
	* 修改会员记录
	*/
	public void editMember(PageData pd)throws Exception{
		dao.update("PcTongYong.editMember", pd);
	}
	
	/*
	 *查看会员详情
	 */
	public PageData detailMemberById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PcTongYong.detailMemberById", pd);
	}
	/*
	* 更新收藏次数
  	*/
	public void editCollectNumber(PageData pd)throws Exception{
		dao.update("AppStoreMapper.editCollectNumber", pd);
	}
	

	/*
	 *获取推荐我的人的电话
	 */
	public List<PageData> listAllTuiJian(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PcTongYong.listAllTuiJian", pd);
	}
	
	
	/* 
	 *	通过商家ID查询出详情图。。。。。
 	 *
	*/
 	public List<PageData> findImageByStore(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("PcTongYong.findImageByStore", pd);
	}
 	
 	/*
	*新增收藏记录
	* 魏汉文20160629
	*/
	public void saveCollect(PageData pd) throws Exception {
		dao.save("PcTongYong.saveCollect", pd);
	}
	
	/*
	 *取消收藏魏汉文20160629
	 */
	public void deleteCollect(PageData pd) throws Exception {
		dao.delete("PcTongYong.deleteCollect", pd);
	}
 	
 	/*
	 * 获取商家收藏信息魏汉文20160629
	 */
	public PageData getCollectionById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PcTongYong.getCollectionById", pd);
	}	
 	
 	
	/*
	*评价列表分页
	*/
	public List<PageData> datalistPageByComment(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PcTongYong.datalistPageByComment", page);
	}
	
	/*
	 *获取商家的---积分手段
 	 */
	public PageData getJfByIdByStore(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PcTongYong.getJfByIdByStore", pd);
	}
	
}

