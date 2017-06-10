/**
 * 
 */
package com.tianer.service.storepc.liangqin.homepage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.storepc.liangqin.Contacts;
import com.tianer.entity.storepc.liangqin.Order;
import com.tianer.entity.storepc.liangqin.Pay_History;
import com.tianer.entity.storepc.liangqin.Store;
import com.tianer.entity.storepc.liangqin.Store_Scoreway;
import com.tianer.entity.storepc.liangqin.Store_Wealth;
import com.tianer.entity.storepc.liangqin.Store_Wealthhistory;
import com.tianer.entity.storepc.liangqin.Withdraw_Approval;

/**
 * 类名称: BriefSummaryService 
 * 类描述: 简要概括的业务层
 * 公司: 天尔西安研发中心
 * 创建人: 梁秦
 * 创建时间: 2016-6-14 上午12:43:09	
 * 版本号: v1.0
 */
@Service("briefSummaryService")
public class BriefSummaryService {

	@Resource(name="daoSupport")
	private DaoSupport dao;
	/**
	 * 查出可提现金额
	 */
	public Withdraw_Approval findWithdrawMoney(String phone) throws Exception{
		return (Withdraw_Approval)dao.findForObject("LQWithdraw_ApprovalMapper.findByPhone", phone);
	}
	
	/**
	 * 查出冻结金额
	 */
	public Store_Wealth findFreezeMoney(String Id) throws Exception{
		return (Store_Wealth)dao.findForObject("LQStore_WealthMapper.findStoreWealthByStoreId", Id);
	}
	
	/**
	 *  查出积分方式
	 */
	public Store_Scoreway findScoreWay(String Id) throws Exception{
		return (Store_Scoreway)dao.findForObject("LQStore_ScorewayMapper.findMemberChangeType", Id);
	}
	
	/**
	 * 查出商家信息
	 */
	public Store findStore(String Id) throws Exception{
		return (Store)dao.findForObject("LQStoreMapper.findSomeProperties", Id);
	}
	
	/**
	 * 查询商家积分
	 */
	public String findScoreByStoreId(String Id) throws Exception{
		int sum = 0;
		List<Store_Wealthhistory> list = (List<Store_Wealthhistory>)dao.findForList("LQStore_WealthhistoryMapper.findScoreByStoreId", Id);
		for (Store_Wealthhistory store_Wealthhistory : list) {
			sum += Integer.parseInt(store_Wealthhistory.getLast_wealth());
		}
		return sum+"";
	}
	
	/**
	 * 查询商家货贷(按余额处理了~)
	 */
	public String findMoneyByStoreId(String Id) throws Exception{
		int sum = 0;
		List<Store_Wealthhistory> list = (List<Store_Wealthhistory>)dao.findForList("LQStore_WealthhistoryMapper.findMoneyByStoreId", Id);
		for (Store_Wealthhistory store_Wealthhistory : list) {
			sum += Integer.parseInt(store_Wealthhistory.getLast_wealth());
		}
		return sum+"";
	}
	
	/**
	 * 查询商家的红包数量
	 */
	public String findRedPacketsByStoreId(String Id) throws Exception{
		return (String)dao.findForObject("LQStore_RedpacketsMapper.findRedPlatformMapper", Id);
	}
	
	/**
	 * 查询商家的被收藏数
	 */
	public String findCollectTimes(String store_id) throws Exception{
		return (String)dao.findForObject("LQStore_CollectMapper.findCollectTimes", store_id);
	}
	
	/**
	 * 查询商家的评论数量
	 */
	public String findCommentAmount(String store_id)throws Exception{
		return (String)dao.findForObject("LQCommentMapper.findCommentAmount", store_id);
	}
	
	/**
	 * 查询商家的评论数量
	 */
	public String findZanAmount(String store_id)throws Exception{
		return (String)dao.findForObject("LQCommentMapper.findZanAmount", store_id);
	}
	
	
	/**
	 * 查询商家的赠送积分
	 */
	public String findIntegral(String storeId)throws Exception{		
		return (String) dao.findForObject("LQStore_WealthhistoryMapper.findIntegral", storeId);
	}
	
	/**
	 * 查询商家的交易笔数
	 */
	public String findDealTimes(String storeId)throws Exception{
		return (String)dao.findForObject("LQStore_WealthhistoryMapper.findDealTimes", storeId);
	}
	/**
	 * 查询商家的营业金额
	 */
	public String findBusinessVolume(String storeId)throws Exception{
		int sum = 0;
		List<Order> list = (List<Order>)dao.findForList("LQOrderMapper.findBusinessVolume", storeId);
		for(Order order : list){
			sum += Integer.parseInt(order.getActual_money());
		}
		String sumstr = sum + "";
		return sumstr;	
	}
	
	/**
	 * 今天收积分
	 */
	public String findGetScore(String storeId)throws Exception{
		int sum = 0;
		List<Store_Wealthhistory> list = (List<Store_Wealthhistory>)dao.findForList("LQStore_WealthhistoryMapper.findGetScore", storeId);
		for(Store_Wealthhistory swt : list){
			sum += Integer.parseInt(swt.getLast_wealth());
		}
		return sum + "";
	}
	
	/**
	 * 今天充值
	 */
	public String findRecharge(String storeId)throws Exception{
		int sum = 0;
		List<Store_Wealthhistory> list = (List<Store_Wealthhistory>)dao.findForList("LQStore_WealthhistoryMapper.findRecharge", storeId);
		for(Store_Wealthhistory swt : list){
			sum += Integer.parseInt(swt.getLast_wealth());
		}
		return sum + "";
	}
	
	/**
	 * 今天提现
	 */
	public String findWithdraw(String storeId)throws Exception{
		int sum = 0;
		List<Store_Wealthhistory> list = (List<Store_Wealthhistory>)dao.findForList("LQStore_WealthhistoryMapper.findWithdraw", storeId);
		for(Store_Wealthhistory swt : list){
			sum += Integer.parseInt(swt.getLast_wealth());
		}
		return sum + "";
	}
	
	
	
	
	/**
	 * 一度人脉收益
	 */
	@SuppressWarnings("unchecked")
	public String findOneListMoney(String parent_id) throws Exception{
		int sum_one = 0;
		int sum_temp = 0;
		List<Contacts> list = (List<Contacts>) dao.findForList("LQContactsMapper.findOneList", parent_id);
		for(int i = 0; i < list.size(); i++)
		{
			List<Pay_History> moneyList = (List<Pay_History>) dao.findForList("LQPay_HistoryMapper.findMoneyByPhone", list.get(i).getContacts_id());
			for(int j = 0; j < moneyList.size(); j++)
			{
				sum_temp += Integer.parseInt(moneyList.get(j).getMoney());
			}
			sum_one += sum_temp;
		}
		return sum_one + "";
	}
	
	/**
	 * 二度人脉收益
	 */
	@SuppressWarnings("unchecked")
	public String findTwoListMoney(String parent_id) throws Exception{
		int sum = 0;
		int sum_temp = 0;
		List<Contacts> list = (List<Contacts>) dao.findForList("LQContactsMapper.findOneList", parent_id);
		for(int i = 0; i < list.size(); i++)
		{
			List<Contacts> new_list = (List<Contacts>) dao.findForList("LQContactsMapper.findTwoList", list.get(i).getContacts_id());
			for(int j = 0; j < new_list.size(); j++)
			{
				List<Pay_History> new_list_money = (List<Pay_History>)dao.findForList("LQPay_HistoryMapper.findMoneyByPhone", list.get(j).getContacts_id());
				for(int k = 0; k < new_list_money.size(); k++)
				{
					sum_temp += Integer.parseInt(new_list_money.get(k).getMoney());
				}
				sum += sum_temp;
			}
		}
		return sum+"";
	}
	
	/**
	 * 累计推广收益
	 */
	public String findTotalSpreadMoney(String store_id) throws Exception{
		return null;
	}

	/**
	 * 方法名:findPayTimes
	 * 描述:TODO
	 * @throws Exception 
	 *
	*/
	public String findPayTimes(String storeId) throws Exception {
		return (String) dao.findForObject("LQOrder_GoodsMapper.findPayTimes", storeId);
	}
}
