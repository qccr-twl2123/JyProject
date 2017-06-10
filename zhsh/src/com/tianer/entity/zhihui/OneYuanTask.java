package com.tianer.entity.zhihui;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
 
/**
 * 
* 类名称：优选购物车
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class OneYuanTask extends TimerTask{
 
  	private String order_id;//
    

	public   OneYuanTask(){
		
	}

	public   OneYuanTask(String order_id ) {
 		this.order_id = order_id;
 	}

	
	@Override
	public void run() {
			/*
			 * 在定时器到时间的时候 
			 * 查看当前订单是否已经购买
			 */
			System.out.println("优品到期时间");
 		 	PageData pd=new PageData();
 		 	try { 
 		 		pd.put("order_id", order_id);
 		 		
 		 	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	public static void main(String[] age){
		String n="003";
		System.out.println(Integer.parseInt(n)+1);
	}

}
