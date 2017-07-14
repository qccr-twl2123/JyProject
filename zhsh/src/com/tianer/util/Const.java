package com.tianer.util;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.tianer.entity.system.AppValidation;

/**
 * 项目名称：
 * @author:tianer
 * 
*/
public class Const {
	
	//模板
	/*
	 * cast(cast((ifnull(a.sale_money,'0')) as decimal(10,2)) as char)  sale_money
	 * date_format(a.createtime ,'%Y-%m-%d %H:%i:%s') as createtime,
	 * ServiceHelper.getAppPcdService().saveLog(order_id, message,"10");
	 * onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" 
	 * (cast(round( 6378.138 * 2 * asin( sqrt( pow( sin(( abs(b.latitude  * pi() / 180 - #{latitude} * pi() / 180) ) / 2 ), 2 ) + cos(b.latitude  * pi() / 180) * cos( #{latitude} * pi() / 180) * pow( sin(( abs(b.longitude * pi() / 180 - #{longitude}  * pi() / 180) ) / 2 ), 2 ))) ,3) as  decimal(10,1))) as char 
	 * 手机验证码用shiro-seesion，图形验证码用http-session存储
	 * 
	 * to_days(now()) &gt; to_days(endtime) 
	 * 
	 */
	
	public static final String SESSION_STORE_ZHUCECODE = "session_store_zhucecode";//商家注册验证码
	public static final String SESSION_STORE_CARDCODE = "session_store_cardcode";//商家添加/删除卡片验证码
	
	public static final String SESSION_MEMBER_FORGETPASSWORDCODE = "session_member_forgetpasswordcode";//会员忘记密码验证码
	public static final String SESSION_MEMBER_ZHUCECODE = "session_member_zhucecode";//会员注册验证码
	public static final String SESSION_MEMBER_CARDCODE = "session_member_cardcode";//会员添加/删除卡片验证码
 	
	
	
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";//图形验证码
	
 	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_ZHUSER = "sessionZhUser";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
 		
	public static final String SESSION_menuList = "menuList";			//当前菜单
	public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
	
	public static final String SESSION_QX = "QX";
 	public static final String SESSION_userpds = "userpds";			
	
	public static final String SESSION_USERROL = "USERROL";				//用户对象
	public static final String SESSION_USERNAME = "USERNAME";			//用户名
	
	//会员pc
	public static final String SESSION_MEMBER_USER = "session_member_user";
	//商家pc
	public static final String SESSION_STORE_USER = "session_store_user";
	public static final String SESSION_STORE_MAP = "menuMap";			//当前菜单
	public static final String SESSION_STORE_QX = "store_qx";
	//总后台pc
	public static final String SESSION_MAP = "session_map";			//当前菜单
	//h5会员
	public static final String SESSION_H5_USER = "session_h5_user";
	//-------------
	
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/login_toLogin.do";				//原装登录地址
	public static final String STORELOGIN = "/storepc/goLogin.do";				//原装登录地址
	public static final String MEMBERLOGIN = "/memberpc/goMemberOne.do";				//原装登录地址
	public static final String ZhiHui_LOGIN = "/zhihui_index.do";
	
	public static final String JYIP = "admin00/head/JYIP.txt";	//禁用ip文档
	public static final String SYSNAME = "admin00/head/SYSNAME.txt";	//系统名称路径
	public static final String PAGE	   = "admin00/head/PAGE.txt";		//分页条数配置路径
	public static final String EMAIL   = "admin00/head/EMAIL.txt";		//邮箱服务器配置路径
	public static final String PSIYAO   = "your_rsa_private_key.pem";		//P++私钥
	public static final String HUANXIN   = "config.properties";		//环信公钥私钥
	public static final String SMS1   = "admin00/head/SMS1.txt";		//短信账户配置路径1
	public static final String SMS2   = "admin00/head/SMS2.txt";		//总后台账户登陆账号密码
	public static final String ALGORITHM   = "admin00/head/SMS2.txt";	//公式
	
	public static final String FILEPATH = "uploadify/uploads/";			//文件上传路径
	public static final String AVATARFILEPATH = "uploadify/uploads/avatar/";	//头像文件上传路径
	public static final String AREAFILEPATH = "uploadify/uploads/area/";	//头像文件上传路径
	public static final String ErWeiMa = "c:/";	//头像文件上传路径
 	
    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(websocket)|(zhihui)|(store)|(member)|(html)|(one)|(you)|(zhao)|(fa)|(jiuyu)|(back)|(#)).*";	//都不拦截
 	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	

	public static final String GetUrl="https://www,jiuyuvip.com//";
	
	/**
	 * APP Constants
	 */
	//app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries","uname","passwd","title","full_name","company_name","countries_code","area_code","telephone","mobile"};
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍","邮箱帐号","密码","称谓","名称","公司名称","国家编号","区号","电话","手机号"};
	
	//app登录接口_请求协议中的参数
	public static final String[] APP_LOGIN_PARAM_ARRAY = new String[]{"uname","passwd"};
	public static final String[] APP_LOGIN_VALUE_ARRAY = new String[]{"邮箱账号","密码"};
	
	//app登录状态接口_请求协议中的参数
	public static final String[] APP_LOGINSTATUS_PARAM_ARRAY = new String[]{"app_id","status"};
	public static final String[] APP_LOGINSTATUS_VALUE_ARRAY = new String[]{"app登录用户ID","登录状态"};	
	
	//忘记密码,查找用户账户是否存在接口_请求协议中的参数
	public static final String[] APP_FORGOTPASSWORD_PARAM_ARRAY = new String[]{"uname"};
	public static final String[] APP_FORGOTPASSWORD_VALUE_ARRAY = new String[]{"邮箱账号"};
	
	//APP参数访问控制
	public static Map<String,HashMap<String, AppValidation>> APPVALIDATION = new HashMap<String,HashMap<String, AppValidation>>();
	
	public static final String role1 = "总部";			//角色名称用于判断
	public static final String role2 = "子公司";	
	public static final String role3= "城市经理";	
	public static final String role4 = "服务商";	
	public static final String role5 = "业务员";	
	
	public static final String redMin = "20";	//最低红包个数
	public static final double appdistance = 30000;	//30000米方位内的商家
	public static final double loginday = 7;	//最近一星期登陆过得商家
	
	public static final String[] payway = new String[]{"0","支付宝","微信","pos机","apple Pay"};
	public static final String[] paytype = new String[]{"0","到店消费","网购自提"};

	
	public static final String redtwo_type = "2";	//平均
 	/*
	 * 	随机：现金红包选择随机金额时，上下限不能超过平均值的50%，
	 * 例如500元100个红包，平均值是5元，选择随机金额时，最低可以领到2.5元，最高7.5元，在2.5到7.5之间随机，到小数点后两位
	 */
	public static final String redone_type = "1";	
	
	/*
	 * 商家商品的分类头三个
	 */
	public static final String goodssort_one = "领红包";	
	public static final String goodssort_two = "今日特价";	
	public static final String goodssort_three = "人气榜";	
	/*
	 * 推荐好友四个状态
	 */
	public static final String friendone = "1";//未注册，可推荐
	public static final String friendtwo = "2";//已注册，不可推荐
	public static final String friendthree = "3";//未注册，正在推荐中。。。
	public static final String friendfour = "4";//已注册，推荐成功
	
	
	/*
	 * 人脉积分值
	 */
	public static final int firstcontact = 5;	//一级人脉应获取到的分值
	public static final int twocontact = 3;	//二级人脉应获取到的分值
	
	
	/*
	 * 推荐好友四个状态
	 */
	public static final String contactone   =   "1";//聊聊呗
	public static final String contacttwo   =   "2";// 申请互动
	public static final String contactthree =   "3";// 接收互动
	
	
	
	/*
	 * 红包类型
	 */
	public static final String redpackage_typeone =   "1";// 现金红包
	public static final String redpackage_typetwo =   "2";// 折扣红包
	
	/*
	 * 红包状态
	 */
	public static final String putong_redpackage_status =   "0";// 普通红包
	/*
	 * 红包状态
	 */
	public static final String kaiji_redpackage_status =   "0";// 开机红包
	/*
	 * 红包状态
	 */
	public static final String fujin_redpackage_status =   "0";// 附近红包
	/*
	 * 红包状态
	 */
	public static final String liaotian_redpackage_status =   "3";// 聊天红包
	/*
	 * 红包状态
	 */
	public static final String send_redpackage_status =   "0";// 发送的红包(营销)
	/*
	 * 红包状态
	 */
	public static final String zeng_redpackage_status =   "0";// 赠送的红包
	
	
	//private static final String[] sys_menu=new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35"};
	
	/*
	 * 红包关键字
	 */
	public static final String[] redconent = new String[]{"满","元","减","打","折"};//满多少元打几折，满多少元减几元
	
	/*
	 * 红包发送范围
	 */
	public static final String[] srp_opentype = new String[]{"无范围","一度人脉","二度人脉","收藏本店会员","500M以内","1KM以内","消费过的会员","2KM以内","5KM以内","所在县市","所有城市","我的盟友","本店会员"};
	
 
	
	/*
		交易	综合分值（下标）
		1.通过系统完成一笔交易	+5		
				充值	
		2.完成二次充值且金额500元以上	+30 
		3.完成首次充值且金额100元以上	+20
				单笔交易返会员积分
		4.≤5	                +3	
		5.>5且≤10	        +5	
		6.>10且≤30	    +8	
		7.>30且≤100	    +12
		8.>100	            +20	
		 		会员评价	
		9.五星好评	+5
		10.四星好评	+1
		11.三星好评	0
		12.两星好评	-10
		13.一星好评	-20
				投诉	
		14.会员投诉且确认成立后	-100	
	 */
	public static final String[] complexscore =new String[]{"0","5","30","20","3","5","8","12","20","5","1","0","-10","-20","-100"}  ;
	public static final double[] xingcomplexscore =new double[]{0,200,1000} ;//0-200一星，200-100二星，1000以上三星
	public static final String[] xingcomplexscoregoodsnumber =new String[]{"30","70","150"} ;//0-200一星，200-100二星，1000以上三星

	/*
	 * 爆屏商品结束提货卷的时间,当前是一天后
	 */
	public static final String endnumberdate   =   "7";
	

	/*
	 * 优选商品结束提货卷的时间,当前是一天后
	 */
	public static final String youxuanendnumberdate   =   "30";
	
	/*
	 * 公司账号
	 */
	public static final String jiuyupay   =   "jiuyupay";//平台支付
	public static final String jiuyunumber   =   "Jiuyu";//交易对象
	public static final String jiuyuname   =   "九鱼平台";//交易对象名
	
	
	public static final int getKaiJiRed   =   10;//结束提货卷的时间,当前是一天后
	
	/*
	 * 魅力值(下标)
		1. 新注册会员	+10		             			
		2.邀请好友注册每人	+2							
		3邀请的好友注册成功每人	+3							
		4分享到社交平台每次	+2							
		5完成一笔交易	+5	
		6评价一次	+2	
		7追评一次	+3	
		8绑定银行卡	+20
		9首次充值	+10
	 */
	public static final String[] charm_number =new String[]{"0","10","2","3","2","5","2","3","20","10"}  ;
	
	/*
	 * 魅力值的星星（下标）
	    0-50	一颗星
		50-99	二颗星
		100-199	三颗星
		200-499	四颗星
		500-999	五颗星
		1000-2000	一颗钻
	 */
	public static final double[] xingcharm_number=new double[]{0,50,100,200,500,1000,2000,3000,4000,5000} ;//0-200一星，200-100二星，1000以上三星





/*
 * 下标
 * 0-系统获取的收益（由以下分成）
   1-业务员	0%	与该商家签约的人员称为业务员，系统初始设定后不可更改
   2-服务商	0%	为该商家提供服务的机构或个人，服务商是可以变更的，在“商家关系调整”中一旦调整了服务商，则从调整那一刻开始后面发生的订单其收益给到调整后的服务商
   3-往上一级人脉	50%	推荐小D注册的商家或个人，暂时称为“小C”，系统锁定后不可更改
   4-往上二级人脉	50%	推荐小C注册的商家或个人，暂称为“小B”，系统锁定后不可更改
   5-子公司-----以上四个角色分配剩余的给到子公司，如果某一项没有锁定任何机构或个人，则将该角色应分配的给到子公司
 */
public static final double[] orderShouyiMoney=new double[]{0.2,0,0,0.5,0.5,0} ; 






/*
 * 最多的商品数量
 */
public static final String maxgoodsnumber = "100";
/*
 * profit_type的文字说明
 */
public static final String[] storeorderprofit_type =new String[]{"0","积分提现","积分充值","消费收款","积分/余额支付","第三方支付","抢积分红包","返会员积分","发积分红包","首次购买服务","服务续费","返推广积分","人脉推广收益","优选商品编辑费","优选商品上架费","导流收益","导流支出","昨日人脉收益","商品超出限制扣费","退还积分红包"}  ;


/*
* 同一个手机号最多商家注册数量
 */
public static final int maxStorenumber =  5 ;

/*
 * 在那个机器上支付
 */
public static final String[] payjiqi =new String[]{"C端现金支付","银联支付","B端现金收银","支付宝支付","微信支付","苹果支付","PC端现金收银","平台支付","积分红包支付","优惠买单支付","提货券支付"}  ;

/*
 * 过期时间处理
 */
public static final String youxuangoods_times = "0.5";//优选购物车多少小时后过期
public static final String goods_times = "1";//优选购物车多少小时后过期
		
/*
 * 展示导流条件
 */
public static final double minWealthShow =  100 ;//余额最少多少就可以展示导流
public static final int minWealthZhuCe =  500 ;//余额最少多少就可以添加进导流
		
		
		/**
		 * 短信限制
		 */
		public static List<String> xzphone=new ArrayList<String>();//手机号码的限制List
		public static Map<String,Integer> numberphone=new HashMap<String,Integer>();//手机号码的限制Map(每天清空次数)
		public static final int phone_sendmessage_number=8;//每个手机号码的每天次数限制
 		
		public static final int maxjuli =  99 ;//最远多少距离进行
		
		public static final String SESSION_ORDER="seesion_onlyorder";//防止表单重复提交订单
		
		/*
		 * 未支付服务费/交易扣点的说辞
		 */
		public static final String not_payStore_message="您的店铺尚未完成初始设置，请及时到官网商家后台处理后使用。";
		
		
		/**
		 * 登录信息
		 */
		public static final String SESSION_LOGIN="session_login";// 登录需要的验证Key
		public static List<String> xzloginnumber=new ArrayList<String>();//登录账号的限制List
		public static Map<String,Integer> clicklogin_number=new HashMap<String,Integer>();//登录账号的限制Map(每天清空次数)
		public static final int limit_loginerrornumber=5;// 每个账号最多登录几次
		public static final int limit_logintime=10;//每个账号最多登录几次
		
		
		
		/**
		 * 红包的有效时间(单位毫秒)
		 */
		public static final long zzredpakagetime = 24*60*60*1000  ;//转增商家红包的有效时间24*60*60*1000 
 		public static final long jfguoqi =  24*60*60*1000 ;// 积分红包过多少天过期24*60*60*1000 
 		
 		
 		/**
 		 * 接口汇总
 		 */
 		//商家
 		public static final String STORELANJIEJIEKOU="/storepc_bankcard/list.do,/storepc/goStore.do,/storepc/goShQyStore.do,/storepc_briefsummary/showMessage.do,/storepc_fifteenmarketchart/showChart.do,/storepc_daoliu/StoredaoliulistPage.do,/storepc_daoliu/StoreTuiGuanglistPage.do,/storepc_vip/goMyVIP.do,/storepc_marketing/goZhifu.do,/storepc_marketing/goDiscount.do,/storepc_marketing/goIntegral.do,/storepc_redpackets/list.do,/storepc_marketing/goGive.do,/storepc_marketingtype/list.do,/storepc_marketingtype/list.do,/storepc_marketingtype/list.do,/storepc_marketingtype/list.do,/storepc_marketing/list.do,/storepc_marketingeffect/list.do";
 		//总后台
 		public static final String ZHIHUILANJIEJIEKOU="/zhihuiReportForm/jingYingFenXiByStore.do,/zhihui_subsidiary/list.do,/zhihuiWaterRecordController/liushuilist.do,/zhihuicity_marketing/list.do,/zhihui_menu_role/list.do,/zhihuiReportForm/integralIncome.do,/zhaoshang/datalistPageCompay.do,/zhihui_city_file/list.do,/zhihui_citymanager/list.do,/zhihui_sp_file/list.do,/zhihui_clerk_file/list.do,/zhihui_operator_file/list.do,/zhihuiz_store_file/list.do,/zhihuiz_store_file/notlist.do,/zhihui_member/list.do,/zhihuiz_store_file/listStoreRelations.do,/zhihui_friend/contactsList.do,/zhihuiWaterRecordController/orderSolelist.do,/zhihuiWaterRecordController/listTxPage.do,/zhihuiWaterRecordController/listTxPage.do,/zhihuiWaterRecordController/listTxPage.do,/zhihuiWaterRecordController/listTxPage.do,/zhihuiWaterRecordController/listTxPage.do,/zhihui_service_performance/list.do,/zhihui_service_performance/listServiceClerk.do,/zhihuicount_allmoney/countList.do,/zhihuicity_marketing/listServiceClerk.do,/zhihui_sort_score/list.do,/zhihui_pc_advert/list.do,/zhihui_app_advert/list.do,/zhihui_sort_chain/list.do,/youxuan/youxuancsgl.do,/youxuan/datalistPageGoods.do,/youxuan/datalistPageGoods.do,/youxuan/datalistPageGoods.do,/fapiao/fapiaolist.do,/fapiao/fapiaolist.do,/fapiao/fapiaolist.do,/zhihui_menu_qx/list.do,/zhihui_menu_marketing/golist.do,/zhihuiReportForm/storeBond.do,/zhihuiReportForm/serviceProviderBond.do,/youxuan/datalistPageGoodsSaleInFor.do,/youxuan/datalistPageGoodsFee.do,/storepc_daoliu/daoliuList.do,/zhihuiReportForm/renmaiByStoreList.do,/zhihui_send_notifications/list.do";
  		//微信
 		public static final String WEIXINLANJIEJIEKOU=".*/((html_member)|(html_me)).*";
  		//不拦截
 		public static final String NOTLANJIEJIEKOU="/html_member/goErrorJsp.do,/html_member/setJiChuAccess_token.do,/html_member/htmlWxLogin.do,/html_member/htmlFindCode.do,/html_member/ajaxYanZhengWxLogin.do,/html_member/toLoginWx.do,/html_member/goMyYouXuanText.do,/html_member/goStoreInforByApp.do,/html_member/gouShouYe.do,/html_member/goYouXuanDescInfor.do,/html_member/goMyYouXuanfengxaingDetail.do,/html_member/toLogin.do,/html_member/getOpenid.do,/html_member/getSignatureAjax.do,/html_member/twoRegister.do,/html_member/goRegister.do";
 		
 		
 		
 		public static final long tokentime= 120 ;//token的time的有效时间秒为单位
  		
 		//只接收post请求的接口
 		public static final String ONLYPOSTURL="/memberpc/uMp.do,/memberpc//gFpMc.do,/memberpc/gFP2.do,/memberpc/yZLN.do,/storepc/sS_P.do";
 		
 		
 		public static final String notform="/jsp/formerror.jsp";//错误的报错页面
 		
 		
}
