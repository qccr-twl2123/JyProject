package com.tianer.service.tongyon;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


/**
 * 通用的服务层
 * @author Administrator
 *
 */
@Service("tYAllSortService")
public class TYAllSortService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 存储app的第三方登录信息
	*/
	public void saveThreeLogin(PageData pd)throws Exception{
		dao.save("TYAllSortMapper.saveThreeLogin", pd);
    }
	
	/*
	 * 存储获取验证码的信息
	 */
	public void saveMessageCode(PageData pd)throws Exception{
		dao.save("TYAllSortMapper.saveMessageCode", pd);
    }
 	
	
	
	
	/*
	 * 通过的商品以及订单信息=================================================================================
	 * 
	 */
	/*
	 *获取订单的所有商品魏汉文20160630
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllGoodsByOrder(PageData  pd)throws Exception{
		return (List<PageData>)dao.findForList("TYAllSortMapper.listAllGoodsByOrder", pd);
	}
 
	/*
	 *正常商品查看详情
	 */
	public  PageData  findByIdByGoods(PageData  pd)throws Exception{
		return ( PageData )dao.findForObject("TYAllSortMapper.findByIdByGoods", pd);
	}
	/*
	 * 优选商品的规格详情
	 */
	public  PageData  finddetailgg(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("TYAllSortMapper.finddetailgg", pd);
	}
	/*
	 * 查询一元购商品的详情包括
	 */
	public  PageData  findByIdWithMember(PageData pd) throws Exception{
		return ( PageData ) dao.findForObject("TYAllSortMapper.findByIdWithMember", pd);
	}
	/*
	 * 获取商家详情魏汉文20160622
	 */
	public PageData findByIdByStore(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TYAllSortMapper.findByIdByStore", pd);
	}	
	
	
	
	//==================领头羊活动报名信息================================================================================
	public void saveLTYZhaoShangInfor(PageData pd)throws Exception{
		dao.save("TYAllSortMapper.saveLTYZhaoShangInfor", pd);
	}
 	public List<PageData> listAllLTYZhaoShangInfor(PageData  pd)throws Exception{
		return (List<PageData>)dao.findForList("TYAllSortMapper.listAllLTYZhaoShangInfor", pd);
	}
 	public PageData detailLTYZhaoShangInforByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TYAllSortMapper.detailLTYZhaoShangInforByPhone", pd);
	}	
	
	
	
	
}
