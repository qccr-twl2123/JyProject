package com.tianer.entity.zhihui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;






import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
 
/**
 * 
* 类名称：MsgTask   
* 类描述：   城市营销详情的城市营销超出商品限度的费用表
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class StoreFeeTihuoTask extends TimerTask{
 
 	private String store_id;//产品名字
	 
 	
	public StoreFeeTihuoTask(){
		
	}

	public StoreFeeTihuoTask(String store_id) {
		super();
		this.store_id = store_id;
	}





	@Override
	public void run() {
			//在定时器到时间的时候：1.获取在当前省市区的所有商家，2，判断商品是否超出，超出的按每天收费收取，更新商家的财富
 //		    DecimalFormat    df   = new DecimalFormat("######0.00"); 
		 	PageData pd=new PageData();
 		 	//获取当前城市下的所有商家
		 	try { 
 		 		synchronized ( this ) { 
 		 			pd.put("store_id", store_id);
 			 		pd=ServiceHelper.getAppStoreService().findById(pd);
		 			if(pd != null && !pd.getString("biaozhun_status").equals("2")){
//		 				System.out.println(pd.getString("store_name")+"商家保证金到期时间，修改状态");
			 			String endtime=pd.get("endtime").toString();
			 			//设置定时器
	 					long l1=DateUtil.fomatDate(DateUtil.getDay()).getTime();
						long l2=DateUtil.fomatDate(endtime).getTime();
						if(l2 == l1 || l2 < l1){
							pd.put("biaozhun_stauts", "2");
							pd.put("merchant_status", "4");
							ServiceHelper.getAppStoreService().editEarnestType(pd);
						}
			 		}
		 		}
		 		
 		 	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	public static void main(String[] age){
		long l1=DateUtil.fomatDate(DateUtil.getDay()).getTime();
		long l2=DateUtil.fomatDate("2016-10-11").getTime();
		System.out.println(l1+"$"+l2);
	}

}
