/**
 * 
 */
package com.tianer.service.storepc.liangqin.homepage;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.storepc.liangqin.Member;
import com.tianer.entity.storepc.liangqin.Order;
import com.tianer.entity.storepc.liangqin.Pay_History;
import com.tianer.entity.storepc.liangqin.Store;

/**
 * 类名称: CashAndPickupService 
 * 类描述: 收银和取货的业务层
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-11 下午7:05:37	
 * 版本号: v1.0
 */

@Service("cashAndPickupService")
public class CashAndPickupService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	/**
	 * 按总金额确认收银，添加到订单中
	 */
	public void addSumToOrder(Order order) throws Exception{
		dao.save("LQOrderMapper.add", order);
	}	
	
	/**
	 * 根据用户的手机号，查到用户信息，返回用户实体
	 */	
	public Member findMemberByPhone(String phone) throws Exception{
		return (Member) dao.findForObject("LQMemberMapper.findMemberByPhone", phone);
	}
	
	/**
	 * 
	 * 方法名:saveMemberScore
	 * 描述:添加用户积分
	 *
	 */
	public void saveMemberScore(Member member) throws Exception{
		dao.save("LQMemberMapper.saveMemberScore", member);
	}
	
	/**
	 * 
	 * 方法名:findStoreByPhone
	 * 描述:根据手机号查找商家
	 *
	 */
	public Store findStoreByPhone(String phone) throws Exception{
		return (Store)dao.findForObject("LQStoreMapper.findStoreById", phone);
	}
	
	/**
	 * 将收银页面的信息添加到支付记录表中
	 */
	public void addToPayHistory(Pay_History pay_History) throws Exception{
		dao.save("LQPay_HistoryMapper.save", pay_History);
	}

	/**
	 * 方法名:findOrderByNum
	 * 描述:TODO
	 *
	*/
	public Order findOrderByNum(String num) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
