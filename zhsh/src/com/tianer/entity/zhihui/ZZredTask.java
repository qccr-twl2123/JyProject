package com.tianer.entity.zhihui;

import java.util.TimerTask;

import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
 
/**
 * 
* 类名称：转增红包定时器处理
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class ZZredTask extends TimerTask{
 
  	private String redpackage_id;//
    

	public   ZZredTask(){
		
	}

	public   ZZredTask(String redpackage_id ) {
 		this.redpackage_id = redpackage_id;
 	}

	
	@Override
	public void run() {
			/*
			 * 在定时器到时间的时候 
 			 */
  		 	PageData e=new PageData();
 		 	try {
  		 		synchronized ( this ) { 
    		 		e.put("redpackage_id", redpackage_id);
    		 		e.put("iszhuanzeng", "1");
    	 		 	if(ServiceHelper.getAppMember_redpacketsService().findById(e)!= null){ 
    	 		 		e.put("iszhuanzeng", "0");
   	 		 			e.put("ishiyong", "0");
   	 		 			ServiceHelper.getAppMember_redpacketsService().editRedStatus(e);
   	 		 		}
 	            } 
   		 	} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
    
	public static void main(String[] age){
		String n="003";
		System.out.println(Integer.parseInt(n)+1);
	}

}
