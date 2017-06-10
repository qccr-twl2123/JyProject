
//测试代码
//package com.tianer.util.quartz;
//
//import java.math.BigDecimal;
//import java.util.TimerTask;
//
//import com.pingplusplus.Pingpp;
//import com.pingplusplus.exception.APIConnectionException;
//import com.pingplusplus.exception.APIException;
//import com.pingplusplus.exception.AuthenticationException;
//import com.pingplusplus.exception.ChannelException;
//import com.pingplusplus.exception.InvalidRequestException;
//import com.pingplusplus.model.Transfer;
//import com.tianer.util.DateUtil;
//import com.tianer.util.PageData;
//import com.tianer.util.PingppUtil;
//import com.tianer.util.ServiceHelper;
//
//public class TransferTask  extends TimerTask{
//	private String transfers_id;//提现id
//	
//	public TransferTask(String transfers_id){
//		this.transfers_id = transfers_id;
//	}
//	
//	@Override
//	public void run() {
//		System.out.println();
//		Transfer transfer = null;
//		PageData pd = new PageData();
//		try {
//			transfer = Transfer.retrieve(transfers_id);
//			if ("failed".equals(transfer.getStatus())) {
//				pd.put("withdraw_transfersid", transfer.getId());
//				pd = ServiceHelper.getLawerAccountService().getWithdrawalApplication(pd);
//				BigDecimal total_fee = new BigDecimal(transfer.getAmount());
//				total_fee = total_fee.divide(new BigDecimal("100"));
//				pd.put("lawer_id", pd.get("withdraw_lawerid"));
//				pd = ServiceHelper.getLawerAccountService().getAccountByLawerId(pd);
//				pd.put("bal", total_fee.add((BigDecimal) pd.get("bal")));
//				pd.put("update_time", DateUtil.getTime());
//				ServiceHelper.getLawerAccountService().updateLawerBal(pd);
//				System.out.println("失败");
//			}else {
//				System.out.println("成功");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void main(String[] args) {
//		Pingpp.apiKey = PingppUtil.apiKey;
//		Transfer transfer = null;
//		try {
//			transfer = Transfer.retrieve("tr_nPyXv9nbf9qHzH8m18vDez9G");
//		} catch (AuthenticationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidRequestException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (APIConnectionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (APIException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ChannelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(transfer.toString());
//	}
//}
