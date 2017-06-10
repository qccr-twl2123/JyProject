package com.tianer.entity.zhihui;

import java.util.TimerTask;

import com.tianer.controller.memberapp.tongyongUtil.TongYong;
 
/**
 * 
* 类名称：MessageTask   
* 类描述：短信的定时
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class LoginNumberTask extends TimerTask{
 
 	private String loginnumber;//登录账号
  	
	public LoginNumberTask(){
		
	}
	
  	
	public LoginNumberTask(String loginnumber  ) {
		super();
		this.loginnumber = loginnumber;
  	}


	@Override
	public void run() {
		try {
			//清除当前账号的登录信息
			TongYong.clearLoginNumber(loginnumber);
   		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("登录控制时间"+e.toString());
		}
	}
    
	public static void main(String[] age){
		 
	}

}
