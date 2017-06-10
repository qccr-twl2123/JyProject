package com.tianer.entity.zhihui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;





import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.SmsUtil;
 
/**
 * 
* 类名称：MessageTask   
* 类描述：短信的定时
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class MessageTask extends TimerTask{
 
 	private String phone;//电话
  	
	public MessageTask(){
		
	}
	
  	
	public MessageTask(String phone  ) {
		super();
		this.phone = phone;
  	}


	@Override
	public void run() {
		try {
			//获取电话集合--移除当前的电话
 			for(int i=0;i<Const.xzphone.size();i++){  
	            if(Const.xzphone.get(i).equals(phone)){  
	            	Const.xzphone.remove(i);  
	            }  
	        } 
			//获取电话获取验证码次数map---更新次数
 			if(Const.numberphone.get(phone) != null){
 				int number=Const.numberphone.get(phone);
 	 			number=number+1;
 	 			Const.numberphone.put(phone, number);
 			}
  		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("获取短信倒计时"+e.toString());
		}
	}
    
	public static void main(String[] age){
		 
	}

}
