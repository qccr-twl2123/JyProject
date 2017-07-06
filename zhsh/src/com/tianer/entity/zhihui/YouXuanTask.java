package com.tianer.entity.zhihui;

import java.util.Date;
import java.util.TimerTask;

import com.tianer.controller.tongyongUtil.TongYong;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.ServiceHelper;
import com.tianer.util.StringUtil;
 
/**
 * 
* 类名称：优选档期
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class YouXuanTask extends TimerTask{
 
 	private String youxuandq_id;//档期ID
 	private String city_file_id;//城市ID
	 
 	
 

	public   YouXuanTask(){
		
	}

	public   YouXuanTask(String youxuandq_id,String city_file_id) {
 		this.youxuandq_id = youxuandq_id;
 		this.city_file_id = city_file_id;
	}

	
	@Override
	public void run() {
			/*
			 * 在定时器到时间的时候 
			 * 1.获取详情
			 * 2.判断结束时间与当前时间
			 * 3.切换下一期的商品，
			 * 当期isover=99,  //0-刚生成，1-正在进行，99-已结束
			 * 当期的商品goods_status=98  //0-商品提交待审核，1-九鱼编辑编辑等待商家支付，2-审核通过/等待支付上线 ，
			 *  4-正在上线，97-驳回，98-库存已售完，99-已结束
			 */
  		 	PageData pd=new PageData();
 		 	try { 
 		 		synchronized ( this ) { 
 		 			pd.put("youxuandq_id", youxuandq_id);
 			 		pd.put("city_file_id", city_file_id);
 	 		 		pd.put("isover", "1");//0-刚生成，1-正在进行，99-已结束
 			 		pd=ServiceHelper.getYouXuanService().finddetailDangqi(pd);
 			 		if(pd != null){
 			 			System.out.println(youxuandq_id+"优选档期的到期处理=========换档期");
 			 			String city_file_id=pd.getString("city_file_id");
 		 		 		String enddate=pd.getString("enddate");
 						String opentime=pd.getString("opentime");
 	 		 			long n1=(new Date()).getTime();
 			 			String kqtime=enddate+" "+opentime;
 			 			long n2=DateUtil.fomatDate1(kqtime).getTime();
 			 			if(n2 <= n1){
 			 				//开始处理--到时间了
 			 				PageData one=new PageData();
 			 				one.put("youxuandq_id", youxuandq_id);
 			 				one.put("city_file_id", city_file_id);
 			 				one.put("isover", "99");
 			 				one.put("goods_status", "99");
 			 				ServiceHelper.getYouXuanService().editDangqiAndGoods(one);
 			 				one.clear();
 		 	 				//开始下一期的商品
 			 				String n=(Integer.parseInt(youxuandq_id)+1)+"";
 			 				n=StringUtil.buZero(n, 3-n.length());
 			 				one.put("youxuandq_id", n);
 			 				one.put("city_file_id", city_file_id);
 			 				one.put("isover", "0");
 			 				one.put("city_file_id", city_file_id);
 			 				one=ServiceHelper.getYouXuanService().finddetailDangqi(one);
 			 				if(one != null){
 			 					one.put("isover", "1");
 			 					one.put("goods_status", "4");
 			 					ServiceHelper.getYouXuanService().editDangqiAndGoods(one);
 			 					one=null;
 			 				}
 	 		 			}
 			 		}
 		 		}
  		 	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				(new TongYong()).dayinerro(e);
			}
	}
    
	public static void main(String[] age){
		String n="003";
		System.out.println(Integer.parseInt(n)+1);
	}

}
