package com.tianer.service.memberapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;


@Service("appMemberService")
public class AppMemberService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增会员魏汉文
	*/
	public void save(PageData pd)throws Exception{
		dao.save("AppMemberMapper.save", pd);
		dao.delete("LQVIPMapper.deleteVipByPhone", pd);//删除线下vip
   	}
  	
	/*
	 * 新增VIP魏汉文20160629
	 */
	public void addStoreVip(PageData pd)throws Exception{
		dao.save("AppMemberMapper.addStoreVip", pd);
	}
   	
	/*
	 * 新增人脉关系魏汉文
	 */
	public void saveContact(PageData pd)throws Exception{
		dao.save("AppMemberMapper.saveContact", pd);
	}
	
	
	/*
	 * 新增会员红包魏汉文
	 */
	public void saveRedForMember(PageData pd)throws Exception{
		dao.save("AppMemberMapper.saveRedForMember", pd);
 	}
	
	
	/*
	* 修改魏汉文20160623
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("AppMemberMapper.edit", pd);
	}
	
	
	/*
	* 修改魏汉文20160630
	*/
	public void editPawd(PageData pd)throws Exception{
		dao.update("AppMemberMapper.editPawd", pd);
	}
	
  
	/*
	 *通过手机号码获取会员信息
	 *魏汉文20160608
	 */
	public PageData detailByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.detailByPhone", pd);
	}
	
	
	/*
	 *通过会员的微信openid获取会员信息
	 *魏汉文20160608
	 */
	public PageData getByOpenid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.getByOpenid", pd);
	}
	

	/*
	 *通过会员的微信unionid获取会员信息
	 *魏汉文20160608
	 */
	public PageData getByUnionid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.getByUnionid", pd);
	}

	/*
	 *通过展示的会员ID获取会员信息
	 */
	public Integer detailByShowLookId(PageData pd)throws Exception{
		return (Integer)dao.findForObject("AppMemberMapper.detailByShowLookId", pd);
	}
	
	/*
	 *通过手机号码获取会员积分
	 *魏汉文20160608
	 */
	public PageData  getIntegerByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.getIntegerByPhone", pd);
	}
	
	
	
	/*
	 *登陆验证
	 *魏汉文20160608
	 */
	public PageData findByLogin(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findByLogin", pd);
	}
	
	
	
	/*
	 *人脉联系人详情
	 *魏汉文20160608
	 */
	public PageData contactMember(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.contactMember", pd);
	}
	
	/*
	 *详情
	 *魏汉文20160608
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findById", pd);
	}
	
	/*
	 *个人红包详情
	 *魏汉文20160623
	 */
	public PageData findRePackagedById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findRePackagedById", pd);
	}


	/*
	 *查出用户密码
	 *刘耀耀20160623
	 */
	public String findPowd(PageData pd) throws Exception{
		return (String)dao.findForObject("AppMemberMapper.findPowd", pd);
	}
	/*
	 *查出用户密码
	 *刘耀耀20160623
	 */
	public String findTel(PageData pd) throws Exception{
		return (String)dao.findForObject("AppMemberMapper.findTel", pd);
	}
	
	/*
	 *财富详情
	 *魏汉文20160624
	 */
	public PageData findWealthById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findWealthById", pd);
	}
	
	

	/*
	 *用户账号信息
	 *刘耀耀20160624
	 */
	public List<PageData> findAccount(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppMemberMapper.findAccount", pd);
	}
	
	
	/*
	 *用户账号信息
	 *刘耀耀20160624
	 */
	public List<PageData> contactMemberList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppMemberMapper.contactMemberList", pd);
	}
	
	/*
	 *获取所有用户ID
	 *魏汉文20160624
	 */
	public List<PageData> listAllMember(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppMemberMapper.listAllMember", pd);
	}
	
	/*
	 *获取所有区域的会员
	 *魏汉文20160624
	 */
	public List<PageData> listAllMemberByCity(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppMemberMapper.listAllMemberByCity", pd);
	}
	
	/*
	* 更新会员的红包个数0822
	*/
	public void updateMemberRedNumber(PageData pd)throws Exception{
		dao.update("AppMemberMapper.updateMemberRedNumber", pd);
	}
	
	
	/*
	 *个人收货地址列表
	 *魏汉文20160624
	 */
	public List<PageData> listAllAddressById(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppMemberMapper.listAllAddressById", pd);
	}
	
	/*
	 * 新增个人收货地址
	 * 魏汉文20160624
	 */
	public void addAddressById(PageData pd)throws Exception{
		dao.save("AppMemberMapper.addAddressById", pd);
	}
	
	/*
	 * 修改个人收货地址
	 * 魏汉文20160624
	 */
	public void editAddressById(PageData pd)throws Exception{
		dao.save("AppMemberMapper.editAddressById", pd);
	}
	
	/*
	 * c查看个人收货地址详情
	 * 魏汉文20160624
	 */
	public PageData  findAddressById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findAddressById", pd);
	}


	/*
	 * 点击我的查询
	 * 刘耀耀20160627
	 */

	public List<PageData> listPersonal(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppMemberMapper.listPersonal", pd);
	}
	
	/*
	 *获取我的VIP信息
	 *魏汉文20160629
	 */
	public List<PageData> getStoreVipById(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppMemberMapper.getStoreVipById", pd);
	}
	
	
	/*
	 *更新我的VIP信息
	 *魏汉文20160629
	 */
	public void updateStoreVipById(PageData pd) throws Exception {
		   dao.update("AppMemberMapper.updateStoreVipById", pd);
	}
	


	/*
	*获取我的VIP列表
	*/
	public List<PageData> listAllVipImage(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppMemberMapper.listAllVipImage", pd);
	}
	
	/*
	* 新增会员魏汉文
	*/
	public void deleteVIPTwo(PageData pd)throws Exception{
 		dao.delete("LQVIPMapper.deleteVIPTwo", pd);//删除我的vip
    }
	
	//======================推荐人存储的关系表=====================================
	/*
	 *获取推荐我的人的电话
	 */
	public List<PageData> listAllTuiJian(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppMemberMapper.listAllTuiJian", pd);
	}
	/*
	 *推荐人详情
	 */
	public PageData findDetailTuiJian(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findDetailTuiJian", pd);
	}
 	
	/*
	 * 新增推荐信息魏汉文20160630
	 */
	public void saveTuiJian(PageData pd)throws Exception{
		dao.save("AppMemberMapper.saveTuiJian", pd);
 	}
	
	//=====================================================================================
	
	
 	
	/*
	 *积分红包详情
	 *魏汉文20160702
	 */
	public PageData findJfRedById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findJfRedById", pd);
	}

	
	
	/*
	 * 更新积分红包 魏汉文20160702 
	 */
	public void editJfRed(PageData pd)throws Exception{
		dao.save("AppMemberMapper.editJfRed", pd);
	}
	
	/*
	 * 新增获取积分红包 魏汉文20160702 
	 */
	public void saveJfMrRed(PageData pd)throws Exception{
		dao.save("AppMemberMapper.saveJfMrRed", pd);
	}

	
	
	/*
	 *获得积分红包的历史记录列表魏汉文20160702
	 */
	public List<PageData> findJfRedHistoreList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppMemberMapper.findJfRedHistoryList", pd);
	}
	
	
	
	/*
	 *获得积分红包的历史记录详情
	 *魏汉文20160702
	 */
	public PageData findJfRedHistoryById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findJfRedHistoryById", pd);
	}

	/*
	 * 获取用户当前余额
	 */
	public String findBalance(PageData pd) throws Exception {
		return (String) dao.findForObject("AppMemberMapper.findBalance", pd);
	}
	
	/*
	 * 获取用户当前积分
	 */
	public String findIntegrale(PageData  pd) throws Exception {
		return (String) dao.findForObject("AppMemberMapper.findIntegrale", pd);
	}
	
	/*
	 *更新我的信息
	 *魏汉文20160629
	 */
	public void updateMemberById(PageData pd) throws Exception {
		dao.update("AppMemberMapper.updateMemberById", pd);
	}
	
	/*
	 * 短信登陆验证
	 *	刘耀耀
	 */
	public PageData findSMSLogin(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AppMemberMapper.findSMSLogin", pd);
	}
	/*
	 * 添加推荐人
	 *	刘耀耀
	 */

	public void saveTuijian(PageData pd) throws Exception {
		dao.save("AppMemberMapper.saveTuijian", pd);
	}

	/*
	 *根据用户红包id获取商家详情
	 */
	public PageData redfindById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.redfindById", pd);
	}

	/*
	 * 根据推荐的手机号来判断是否是会员
	 */
	public PageData findStorephone(String phone)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findStorephone", phone);
	}
	
	/*
	 * 通过id获取手机号码
	 */
	public PageData  findByPhone(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.findByPhone", pd);
	}
	
	/*
	*会员分页列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppMemberMapper.datalistPage", page);
	}
	

	/*
	 *测试数据
	 */
	public List<PageData> ceshilist(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppMemberMapper.ceshilist", pd);
	}
	
	//==================统计支付方式使用次数============================
	public String  alipaynumber(PageData pd)throws Exception{
		return (String)dao.findForObject("AppMemberMapper.alipaynumber", pd);
	}
	public String wxpaynumber(PageData pd)throws Exception{
		return (String)dao.findForObject("AppMemberMapper.wxpaynumber", pd);
	}
	public String nowpaynumber(PageData pd)throws Exception{
		return (String)dao.findForObject("AppMemberMapper.nowpaynumber", pd);
	}
	public String integralpaynumber(PageData pd)throws Exception{
		return (String)dao.findForObject("AppMemberMapper.integralpaynumber", pd);
	}
	public String balancepaynumber(PageData pd)throws Exception{
		return (String)dao.findForObject("AppMemberMapper.balancepaynumber", pd);
	}
	//==================会员总消费金额============================
	public String allsalemoneybyid(PageData pd)throws Exception{
		return (String)dao.findForObject("AppMemberMapper.allsalemoneybyid", pd);
	}
	
	//===================微信端专用=============================
	/*
	* 更新微信的调用凭证
	*/
	public void editWx(PageData pd)throws Exception{
		dao.update("AppMemberMapper.editWx", pd);
	}
  	/*
	 * 获取微信的调用凭证
	 */
	public PageData getWxAccess(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppMemberMapper.getWxAccess", pd);
	}
 
	/*
	 * 微信登录修改一些信息
	 */
	public void editHtmlLogin(PageData pd)throws Exception{
		dao.update("AppMemberMapper.editHtmlLogin", pd);
	}
	
	
	
	
}

