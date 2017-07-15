package com.tianer.controller.base;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.entity.Page;
import com.tianer.util.DateUtil;
import com.tianer.util.EbotongSecurity;
import com.tianer.util.Logger;
import com.tianer.util.PageData;
import com.tianer.util.UuidUtil;

public class BaseController {
	
	public Logger logger = Logger.getLogger(this.getClass());
	protected static Lock lock = new ReentrantLock();//锁， lock.lock();，  解锁lock.unlock();

	private static final long serialVersionUID = 6357869213649815390L;
	private static DecimalFormat    df   = new DecimalFormat("######0.00"); 
	
 	
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();	
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public static String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
 	
	
	/**
	 * 得到八位的id
	 */
	public static String get8UID(){
			String str = "";
 			for(int i = 0; i < 8; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	/**
	 * 要求外部订单号必须唯一。
	 * @return
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	
	/**
	 * 得到四位的id
	 */
	public String get4UID(){
			String str = "";
 			for(int i = 0; i < 4; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	
	/**
	 * 充值单号
	 */
	public static String getCZUID(String usernumber){
   			//组合为编号:支付人编号+时间戳+随机的6位数
 			String str ="CZ"+usernumber+DateUtil.getDayshms()+String.valueOf((int)((Math.random()*9+1)*100000));
// 			String str =String.valueOf((int)((Math.random()*9+1)*100000));
		return str;
	}
	
	
	/**
	 * 红包id
	 */
	public static String getREDUID(String usernumber){
			//组合为编号
			String str = "RED"+ usernumber+DateUtil.getDayshms()+String.valueOf((int)((Math.random()*9+1)*100000));
//			String str = String.valueOf((int)((Math.random()*9+1)*100000));
		return str;
	}
	
	
	/**
	 * 支付单号
	 */
	public  static String getXFUID(String usernumber){
		String chars1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String str = chars1.charAt((int)(Math.random() * 26))+chars1.charAt((int)(Math.random() * 26))+ usernumber+DateUtil.getDayshms()+String.valueOf((int)((Math.random()*9+1)*100000));
//		String str = String.valueOf((int)((Math.random()*9+1)*100000));
 		return str;
	}
	
	/**
	 * 提现单号单号
	 */
	public static String getTXUID(String usernumber){
		String str = "TX"+ usernumber+DateUtil.getDayshms()+String.valueOf((int)((Math.random()*9+1)*100000));
//		String str = String.valueOf((int)((Math.random()*9+1)*100000));
		return str;
	}
	
	
	/**
	 * 得到十位的id
	 */
	public static String get10UID(){
			String str = "";
 			for(int i = 0; i < 10; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	/**
	 * 得到十一位的id
	 */
	public static String get11UID(){
			String str = "";
 			for(int i = 0; i < 11; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}

	/**
	 * 得到十二位的id
	 */
	public static String get12UID(){
			String str = "";
 			for(int i = 0; i < 12; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	/**
	 * 获取member_id
	 */
	public static String getMemberID(String phone){
		String str=DateUtil.getDayshms()+get12UID();
//		String str=get32UUID();
//		String str="jy"+phone+BaseController.get4ZMSZ();
		return str;
	}
	/**
	 * 获取show_lookid用来展示的ID
	 * 前面三位为字母（26个字母循环），后面五位是数字
	 */
	public static String getShowKookId(){
		String str = "";
		String chars1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String chars2 = "12345678901234567890";
		str="";
		for(int i = 0; i < 3; i++){
			str += chars1.charAt((int)(Math.random() * 26));
		}
		for(int i = 0; i < 5; i++){
			str += chars2.charAt((int)(Math.random() * 10));
		}
		return str;
	}
	
	/**
	 * 获取only_store_id
	 */
	public static String getOnlyStoreID(String info){
		String chars1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String str=DateUtil.getDayshms()+get12UID()+chars1.charAt((int)(Math.random() * 26))+chars1.charAt((int)(Math.random() * 26));
 		return str;
	}
	
	
	/**
	 * 得到随机九位字母
	 */
	public String get9ZM(){
		String chars = "abcdefghijklmnopqrstuvwxyz";
 		String str = "";
 		for(int i = 0; i < 9; i++){
			str += chars.charAt((int)(Math.random() * 26));
		}
		return str;
	}
	
	/**
	 * 得到随机4位字母或数字
	 */
	public static String get4ZMSZ(){
		String chars = "abcdefghijklmnopqrstuvwxyz12345678901234567890";
 		String str = "";
 		for(int i = 0; i < 4; i++){
			str += chars.charAt((int)(Math.random() * 46));
		}
		return str;
	}
	
	/**
	 * 得到时间类型id
	 */
	public static String getTimeID(){
			String str =DateUtil.getDayshms()+ String.valueOf( (int)((Math.random()*9+1)*100000));
		return str;
	}
	
	
	/**
	 * 得到反馈类型id
	 */
	public static String getFanKuiID(){
			String str ="FK"+ DateUtil.getDayshms()+  (int)((Math.random()*9+1)*1000);
		return str;
	}
	
	/**
	 * 得到6位的id
	 */
	public static String get6UID(){
			String str = "";
			str += (int)(Math.random()*9+1);
			for(int i = 0; i < 5; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	/**
	 * 得到4位的随机数的整数
	 */
	public static int get4Integer(){
			int n=(int)((Math.random()*9+1)*1000);
		return n;
	}
	
	/**
	 * 获取密码
	 */
	public static String getMiMaNumber(){
			String str = "";
			str += (int)(Math.random()*9+1);
			for(int i = 0; i < 5; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	/**
	 * 加密
	 */
	public static String jiami(String str){
			str=BaseController.get4ZMSZ()+EbotongSecurity.ebotongEncrypto(str);
		return str;
	}
	/**
	 * 解密
	 */
	public static String jiemi(String str){
		try {
			str=EbotongSecurity.ebotongDecrypto(str.substring(4, str.length()-1));
		} catch (Exception e) {
			// TODO: handle exception
			str="";
		}
 		return str;
	}
	
	
	//主函数
	public static void main(String[] msg){
		System.out.println(getOutTradeNo( ));;
  	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
}
