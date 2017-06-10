package com.tianer.service.storeapp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.util.PageData;


/** 
 * 
* 类名称：LoginappService   
* 类描述：   登录app
* 创建人：邢江涛
* 创建时间：2016年6月30日 
 */
@Service("loginappService")
public class LoginappService {


	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/*
	 *登陆验证
	 */
	public PageData findByLogin(PageData pd)throws Exception{
		return (PageData)dao.findForObject("XJTlogin.findByLogin", pd);
	}
	
	/*
	* 退出登录
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("XJTlogin.edit", pd);
	}
	
	/*
	 *查出用户密码
	 */
	public String findPowd(PageData pd) throws Exception{
		return (String)dao.findForObject("XJTlogin.findPowd", pd);
	}
	
	
	/*
	 *账户信息
	 */
	public PageData account(PageData pd) throws Exception{
		return (PageData)dao.findForObject("XJTlogin.accountinfo", pd);
	}
	/*
	 * 老板忘记密码
	 */
	public void editPowd(PageData pd) throws Exception {
		dao.update("XJTlogin.editPowd", pd);
 	}
	/*
	 * 操作员忘记密码
	 */
	public void editPowdByOprator(PageData pd) throws Exception {
		dao.update("XJTlogin.editPowdByOprator", pd);
 	}
}
