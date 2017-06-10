package com.tianer.service.storepc.stotr;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;
import com.tianer.util.StringUtil;


@Service("storepcService")
public class StorepcService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/*
	 *商家登录判断是否存在商家
	 *刘耀耀
	 *2016.07.08 
	 */
	public PageData listNamePwd(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LQStoreMapper.listNamePwd", pd);
	}
	
	/*
	 *获取当前商家的注册数量
	 */
	public Integer getNumberForStore(PageData pd) throws Exception {
		return (Integer) dao.findForObject("LQStoreMapper.getNumberForStore", pd);
	}
	/*
	 *商家登录判断是否存在商家
	 *刘耀耀
	 *2016.07.08 
	 */
	public PageData listNamePwdById(PageData pd) throws Exception {
 		return (PageData) dao.findForObject("LQStoreMapper.listNamePwdById", pd);
	}
	
	/*
	 *商家登录判断是否存在操作员
	 *魏汉文 
	 */
	public PageData getOperateLogin(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LQStoreMapper.getOperateLogin", pd);
	}
	
	/*
	 *商家注册
	 *刘耀耀
	 *2016.07.08 
	 */
	public void save(PageData pd) throws Exception {
		dao.save("LQStoreMapper.save", pd);
		dao.save("LQStore_FileMapper.save", pd);
		pd.put("wealth_type", "1");
		pd.put("store_wealth_id",StringUtil.get32UUID());
		dao.save("LQStoreMapper.saveWealth", pd);
		/*pd.put("wealth_type", "2");
		pd.put("store_wealth_id",StringUtil.get32UUID());
		dao.save("LQStoreMapper.saveWealth", pd);*/
		//新增图片
		dao.save("Store_imageMapper.save", pd);
		//默认新增一个操作员
		/*
		 * operator_name,	
			operator_position,	
			operator_status,	
			operator_phone,	
			operator_password,	
			sy_competence,	
			yx_competence,	
			hd_competence,
			cw_competence,	
			sp_competence,	
  			store_shift_id,	
			store_id,	
			createdate,
			store_operator_id
		 */
		pd.put("operator_name", pd.getString("store_name"));
		pd.put("operator_position","2");
		pd.put("store_operator_id","jy"+pd.getString("store_id"));
		pd.put("operator_status","1");
		pd.put("operator_phone", pd.getString("registertel_phone"));
  		dao.save("Store_operatorMapper.save", pd);
 	}
	/*
	 *修改登录状态
	 *魏0714
	 */
	public void updateStatus(PageData pd) throws Exception {
		dao.update("LQStoreMapper.updateStatus", pd);
	}
	
	/*
	 *修改操作员登录时间
	 *魏0714
	 */
	public void updateTime(PageData pd) throws Exception {
		dao.update("LQStoreMapper.updateTime", pd);
	}
	
	
	/*
	 *获取密码
	 *魏汉文 
	 */
	public String findPassword(String str) throws Exception {
		return (String) dao.findForObject("AppStoreMapper.findPassword", str);
	}
	
	
	/*
	 *商家详情
	 *魏汉文 
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppStoreMapper.findById", pd);
	}
	
	/*
	 *通过手机号判断是否有该商家
 	 */
	public PageData findByPhone(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppStoreMapper.findByPhone", pd);
	}
	
	/*
	 *通过手机号判断是否有改操作员
 	 */
	public PageData findByPhoneByOprator(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppStoreMapper.findByPhoneByOprator", pd);
	}
	
	/*
	 *修改商家状态
	 *刘耀耀
	 */
	public void editType(PageData pd) throws Exception {
			dao.update("AppStoreMapper.editType", pd);
	}
	
	/*
	 *修改商家属性
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("AppStoreMapper.edit", pd);
	}

	
	

	/*
	 *商家信息（判断是否存在）
	 */
	public PageData getStoreId(String storeId) throws Exception {
		return (PageData) dao.findForObject("LQStoreMapper.getStoreId", storeId);
	}
	
	/*
	 *商家信息（判断是否存在）
	 */
	public PageData findByStorephone(String storeId) throws Exception {
		return (PageData) dao.findForObject("LQStoreMapper.findByStorephone", storeId);
	}
}

