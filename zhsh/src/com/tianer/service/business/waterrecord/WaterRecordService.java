package com.tianer.service.business.waterrecord;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tianer.dao.DaoSupport;
import com.tianer.entity.Page;
import com.tianer.util.PageData;

@Service("waterRecordService")
public class WaterRecordService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 新增流水
	 */
	public void saveWaterRecord(PageData pd)throws Exception{
		dao.save("WaterRecordMapper.saveWaterRecord", pd);
	}
	
	/**
	 * 修改流水
	 */
	public void editWaterRecord(PageData pd)throws Exception{
		dao.update("WaterRecordMapper.editWaterRecord", pd);
	}
	
	
	/**
	*  清空支付历史记录魏汉文20160705
	*/
	public void deleteStatusZero(PageData pd)throws Exception{
		dao.delete("WaterRecordMapper.deleteStatusZero", pd);
	}
	
	/**
	 *  删除指定订单
	 */
	public void deleteWater(PageData pd)throws Exception{
		dao.delete("WaterRecordMapper.deleteWater", pd);
	}
	
 	
 	/**
 	 * 获取当前记录的详情
 	 */
 	public PageData findByIdWaterRecord(PageData  pd) throws Exception {
 		return (PageData) dao.findForObject("WaterRecordMapper.findByIdWaterRecord", pd);
 	}
 
	
	/**
	 * 获取流水列表--全部
	 */
 	public List<PageData> listAllWaterRecord(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("WaterRecordMapper.listAllWaterRecord", pd);
	}
 	
	
	/**
	 * 获取流水列表--分页
	 */
 	public List<PageData> datalistPageWaterRecord(Page page) throws Exception {
		return (List<PageData>) dao.findForList("WaterRecordMapper.datalistPageWaterRecord", page);
	}
 	
 	
 	/**
 	 * 统计当前页的金额总和
 	 */
 	public PageData sumNowPageWaterRecord(Page page) throws Exception {
 		return (PageData) dao.findForObject("WaterRecordMapper.sumNowPageWaterRecord", page);
 	}
 
 	
 	/**
 	 * 统计筛选条件下的所有数据的流水金额总和
 	 */
 	public PageData sumAllPageWaterRecord(Page  page) throws Exception {
 		return (PageData) dao.findForObject("WaterRecordMapper.sumAllPageWaterRecord", page);
 	}
	
   	
	/**
	 *流水记录到处
	 */
	public List<PageData> ExcellistAllHistory(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WaterRecordMapper.ExcellistAllHistory", page);
	}
	
	/**
	 *提现记录导出
	 */
	public List<PageData> listAllForExcel(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WaterRecordMapper.listAllForExcel", pd);
	}
 	

   	
 	/**
 	 * 判断当前用户当天是否提现过
 	 */
 	public String isTiXianForDay(PageData pd) throws Exception {
 		return (String) dao.findForObject("WaterRecordMapper.isTiXianForDay", pd);
 	}
 	
 	/**
	*获取所有消费统计
	*魏汉文20160617
	*/
	public List<PageData> countAllMoney(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WaterRecordMapper.countAllMoney", page);
	}
	
	
	/**
	*获取所有个人当日最高消费统计
	*魏汉文20160617
	*/
	public List<PageData> countAllMoneyByOne(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WaterRecordMapper.countAllMoneyByOne", page);
	}
	
	/**
	 * 查询全部
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> list(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("WaterRecordMapper.list", pd);
	}
	
	/**
	 * 已提现总额orderPriceCount
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String tixianPriCount(PageData pd) throws Exception{
		return (String) dao.findForObject("WaterRecordMapper.tixianPriCount", pd);
	}

	/**
	 * 已支付订单总额
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String orderPriceCount(PageData pd) throws Exception{
		return (String) dao.findForObject("WaterRecordMapper.orderPriceCount", pd);
	}
 
	
	/**
	* 批量通过
	*/
	public void updateAll(PageData pd)throws Exception{
		dao.update("WaterRecordMapper.updateAll", pd);
	}
	
	
 	/**
 	 * 判断当前订单是否支付完成
 	 */
 	public PageData findWaterRecordIsPayOk(PageData  pd) throws Exception {
 		return (PageData) dao.findForObject("WaterRecordMapper.findWaterRecordIsPayOk", pd);
 	}
	
 
	
}
