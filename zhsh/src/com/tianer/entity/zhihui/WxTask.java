package com.tianer.entity.zhihui;

import java.util.TimerTask;

import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.util.ping.util.WxpubOAuth;
 
/**
 * 设置微信的基础的access_token和jsapi_ticket
 */
public class WxTask extends TimerTask{
  
 	
	public WxTask(){
		
	}
	 
	@Override
	public void run() {
 		 	try {
 		 		//设置Access_token
 		 		WxpubOAuth.setJiChuAccess_token();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				(new TongYong()).dayinerro(e);
			}
	}
    
	public static void main(String[] age){
		 
	}

}
