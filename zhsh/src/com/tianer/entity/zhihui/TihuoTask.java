package com.tianer.entity.zhihui;

 
import java.util.TimerTask;

import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.util.PageData;
 
 
/**
 * 
* 类名称：MsgTask   
* 类描述：   城市营销详情的城市营销超出商品限度的费用表
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class TihuoTask extends TimerTask{
 
 	private String tihuo_id;//产品名字
	 
 	
	public TihuoTask(){
		
	}

	public TihuoTask(String tihuo_id) {
		super();
		this.tihuo_id = tihuo_id;
	}





	@Override
	public void run() {
  		 	PageData pd=new PageData();
 		 	try { 
		 		 pd.put("tihuo_id", tihuo_id);
		 		 TongYong.TiHuoReturnOrder(pd,"0");
		 	} catch (Exception e) {
 				e.printStackTrace();
			}
	}
    
	public static void main(String[] age){
	}

}
