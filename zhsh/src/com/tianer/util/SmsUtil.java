package com.tianer.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 通过短信接口发送短信
 * 阿里大鱼短信文档
 * 魏汉文
 */
public class SmsUtil {
	
	private static final String url="http://gw.api.taobao.com/router/rest";
 	private static String appkey="csshih";
	private static String secret="csshih";
	
//	private static  final String appkey="23401024";
//	private static  final String secret="a0cafe6907c857094299fd13a8eec193";
	
 	private static  final String product="九鱼网";
	private static  final String jiuyudizhi="www.baidu.com";
	
	/**
	 * 发送短信的通用模板
	 * @param phone 电话号码
	 * @param json  模板
	 * @param smsFreeSignName 
	 * @param smsTemplateCode
	 * TongYongSendMessage("normal", "九鱼网", phone, "SMS_66120191", json);
	 */
	public static void TongYongSendMessage(String smsType,String smsFreeSignName ,String phone,String smsTemplateCode,String json){
		try {
 			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setSmsType(smsType);
			req.setSmsFreeSignName(smsFreeSignName);
			req.setRecNum(phone);
			req.setSmsTemplateCode(smsTemplateCode);
			req.setSmsParamString(json);
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(phone+"发送信息"+rsp.getBody());
		} catch (ApiException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println(phone+"发送失败"+e.toString());
		}
	}
	
	
	
	/**
	 * 发送短信 
	 * 您的校验证码为${code},请及时输入
 	 */
	public static void TongYongCode(String phone,String code){ 
		//短信模板的内容
		String json="{\"code\":\""+code+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_11530812", json);
	}
	
	/**
	 * 您已成功注册成为九鱼网会员，初始密码${password}，账号为您手机号。 下载链接http://xz.jiuyuvip.com/
	 * @param phone
 	 * @param password
	 */
	public static void ZhuCeForPassword(String phone,String password ){ 
		//短信模板的内容
		String json="{\"password\":\""+password+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_68190065", json);
	}
	 
	
	/**
	 * 发送短信 
	 * ${name}商家已推荐您注册九鱼网会员，每笔消费必有优惠。ID号为手机号码，初始密码123456，关注微信公众号“九鱼网”立即体验
		SMS_66120191

	 */
	public static void saveMemberByStore(String phone,String name){//电话，姓名，产品名，模板
		//短信模板的内容
		String json="{\"name\":\""+name+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_66120191", json);
	}
	
	/**
	 * 发送短信 
	 * 您正在使用积分付款操作，验证码是${name}，在确认本次消费是您本人知晓并同意的情况下，将验证码告知商家收银员。否则请不用理会。(SMS_16230531)
	 */
	public static void payShouYinCode(String phone,String code){//电话，姓名，产品名，模板
		//短信模板的内容
		String json="{\"name\":\""+code+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_16230531", json);
	}
	
	/**
	 *  模板类型: 短信通知
		模板名称: 成为城市经理通知
		模板ID: SMS_53640120
		模板内容: 亲爱的${name}，已为你开通城市经理帐号，登录ID：${number}初始密码：${password}请妥善保存。
		申请说明: 成为城市经理的短信通知
	 */
	public static void chengshijingli(String phone,String citymanager_name,String citymanager_id,String password){//电话，姓名，产品名，模板
		//短信模板的内容
		String json="{\"name\":\""+citymanager_name+"\",\"number\":\""+citymanager_id+"\",\"password\":\""+password+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_53640120", json);
	}
	
	
	/**
	 * 重置密码
	 * 对不起，您的ID号${UseID}，用户类型：${name}，密码已被系统重置，暂不能登录，如有疑问请询问系统管理员(SMS_16435017)
	 */
	public static void ChongzhiPassword(String phone,String id,String name){//电话，姓名，产品名，模板
		//短信模板的内容
		String json="{\"UseID\":\""+id+"\",\"name\":\""+name+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_16435017", json);
	}
	
	

	/**
	 * sendStoreSp
	 * 发送短信 
	 * 尊敬的{name1}您有一个开店申请！商店名称：{name2},请及时处理！(SMS_50940148)
	 */
	public static void sendStoreSp(String phone,String principal,String store_name){ 
		String json="{\"name1\":\""+principal+"\",\"name2\":\""+store_name+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_50940148", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_50940148");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//			} catch (ApiException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	}
	
	
	/**
	 * sendPassSpSMS_12800105
	 * 发送短信 
	 * 您的服务商资格已通过审核，ID号FW000001，初始密码******，请及时登录修改密码(SMS_14885707)
	 */
	public static void sendPassSp(String phone,String password,String id){//电话 
		String json="{\"name\":\""+id+"\",\"password\":\""+password+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_14885707", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_14885707");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//			} catch (ApiException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	}
	
	/**
	 * sendLessScode
	 * 发送短信 
	 * 重要消息，你的积分以及到达零界点，请及时充值！！(SMS_12790078)
	 */
	public static void sendLessScode(String phone){//电话 
		String json="";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_12790078", json);
//		try {
//			
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_12790078");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * sendPassTx
	 * 发送短信 
	 * 你的提现已审核通过，我们将在1至2个工作日存入你的账户(SMS_12855097)
	 */
	public static void sendPassTx(String phone){//电话 
		String json="";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_12855097", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_12855097");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * sendZhuCeStore
	 * 发送短信 
	 * 模板内容:您的开店申请已成功提交，工作人员将在5个工作日内上门服务，请保持手机畅通。(SMS_14690621)
	 */
	public static void sendZhuCeStore(String phone){//电话 
		String json="";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_14690621", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_14690621");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
 
	
 	/**
 	 * 
 	* 方法名称:：sendZcCode 
 	* 方法描述：注册校验证码为${code}，请在${time}秒内输入。模板ID: SMS_11530813
 	* 创建人：魏汉文
 	* 创建时间：2016年7月6日 上午11:34:32
 	 */
	public static void sendZcCode(String phone,String code){//电话，验证码
  		String json="{\"code\":\""+code+"\",\"time\":\""+60+"\"}";
  		TongYongSendMessage("normal", "九鱼网", phone, "SMS_11530813", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_11530813");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//			} catch (ApiException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	}
	
	
	/**
	 * 
	 * 方法名称:：sendFee 
	 * 方法描述：尊敬的${store}，你的商品已超出限制，系统收费${num}，请注意！模板ID: SMS_12860059
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:32
	 */
	public static void sendFee(String phone,String store,String num){//电话，验证码
		String json="{\"store\":\""+store+"\",\"num\":\""+num+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_12860059", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_12860059");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	/**	
	 * 
	* 方法名称:：sendWjmmCode 
	* 方法描述：您正在找回登录密码，校验码${code},请在${time}秒内输入。。模板ID: SMS_11565827
	* 创建人：魏汉文
	* 创建时间：2016年7月6日 上午11:34:22
	 */
	public static void sendWjmmCode(String phone,String code){//电话，验证码
		String json="{\"code\":\""+code+"\",\"time\":\""+60+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_11565827", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_11565827");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	/**	
	 * 
	 * 方法名称:：ownForCode 
	 * 方法描述：验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！。模板ID: SMS_11215008
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:22
	 */
	public static void ownForCode(String phone,String code){//电话，验证码
		String json="{\"code\":\""+code+"\",\"product\":\""+phone+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_11215008", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_11215008");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**	
	 * 
	 * 方法名称:：TXNumberCode 
	 * 方法描述：您正在设置/添加提现账号，校验码${code},请确认是您本人操作。如非本人操作请及时修改帐户密码。SMS_61635348
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:22
	 */
	public static void TXNumberCode(String phone,String code){//电话，验证码
		String json="{\"code\":\""+code+"\"}"; 
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_61635348", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_61635348");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	/**	
	 * 
	 * 方法名称:：JbTXNumberCode 
	 * 方法描述：您正在设置/解绑提现账号，校验码${code},请确认是您本人操作。如非本人操作请及时修改帐户密码
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:22
	 */
	public static void JbTXNumberCode(String phone,String code){//电话，验证码
		String json="{\"code\":\""+code+"\"}"; 
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_62475236", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_62475236");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	/**	
	 * 
	 * 方法名称:：loginCode 
	 * 方法描述：验证码${code}，您正在登录${product}，若非本人操作，请勿泄露。。模板ID: SMS_11215006
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:22
	 */
	public static void loginCode(String phone,String code){//电话，验证码
		String json="{\"code\":\""+code+"\",\"product\":\""+product+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_11215006", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_11215006");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 
	* 方法名称:：sendXgCode 
	* 方法描述：修改密码  验证码${code}，您正在尝试修改${product}登录密码，请妥善保管账户信息。。。模板ID: SMS_11215002
	* 创建人：魏汉文
	* 创建时间：2016年7月6日 上午11:34:03
	 */
	public static void sendXgCode(String phone,String code){//电话，验证码
		String json="{\"code\":\""+code+"\",\"product\":\""+product+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_11215002", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_11215002");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 
	 * 方法名称:：inforEdit 
	 * 方法描述：验证码${code}，您正在尝试变更${product}重要信息，请妥善保管账户信息。。。模板ID: SMS_11215001
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:03
	 */
	public static void inforEdit(String phone,String code){//电话，验证码
		String json="{\"code\":\""+code+"\",\"product\":\""+phone+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_11215001", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_11215001");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 
	 * 方法名称:：sendCzMm 
	 * 方法描述：新建操作员成功，商家名称:${name}，您的登录ID号${Idnumber}，初始密码${password}；请保存好本信息，并及时修改密码，以确保您的账户安全。
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:03
	 */
	public static void sendCzMm(String phone,String password,String store_name,String id){//电话，验证码
		String json="{\"name\":\""+store_name+"\",\"Idnumber\":\""+id+"\",\"password\":\""+password+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_16750464", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_16750464");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
 
	
	/**
	 * 
	 * 方法名称:：suiJiPassword 
	 * 方法描述：系统为您设置了随机密码：${pwd}，请登录后重新修改密码。。模板ID: SMS_11525799
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:03
	 */
	public static void suiJiPassword(String phone,String pwd){//电话，验证码
		String json="{\"pwd\":\""+pwd+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_11525799", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_11525799");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	/**
	 * 商家帮会员注册号码
	 * 方法名称:：sendMem 
	 * 方法描述：模板内容:您已成功注册九鱼网的${name}商家的会员，初始密码${password}， 下载链接http://xz.jiuyuvip.com/
	 * 模板ID: SMS_14495034
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:03
	 */
	public static void sendMem(String phone,String password,String store_name){//电话，密码
		String json="{\"name\":\""+store_name+"\",\"password\":\""+password+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_14495034", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_14495034");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	/**
	 * 
	 * 方法名称:：商家帮会员注册获取验证码 
	 * 方法描述：您正在注册${storename}的会员，如确定是您本人在场，请将验证码${code}告知工作人员模板ID: SMS_11770064
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:03
	 */
	public static void getCodeByStoreForMem(String phone,String code,String storename){//电话，密码
		String json="{\"storename\":\""+storename+"\",\"code\":\""+code+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_11770064", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_11770064");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	/**
	 * 
	 * 方法名称:：sendStore 
	 * 方法描述：您的上线已审核通过，登录ID号${name},初始密码${password}，请登录商家后台及时修改模板ID: SMS_14740585
	 * 创建人：魏汉文
	 * 创建时间：2016年7月6日 上午11:34:03
	 */
	public static void sendStore(String phone,String store_id,String password){//电话，密码
		String json="{\"name\":\""+store_id+"\",\"password\":\""+password+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_14740585", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_14740585");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	
	
	
	
	/**
	 * 
	* 方法名称:：TjFrinendSave 
	* 尊敬的尊敬的${name},${othername}推荐你注册九鱼网app,地址：http://zc.jiuyuvip.com/ SMS_14755623
	 */
	public static void TjFrinendSave(String phone,String tjphone,String name){//电话，姓名，
		//短信模板的内容
		String json="{\"name\":\""+phone+"\",\"othername\":\""+name+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_14755623", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setSmsParamString(json);
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_14755623");
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 
	 * 方法名称:：TixianNotPass 
	 * 对不起，您的提现申请因${name}被驳回，请完善并检验帐户信息后重新提  SMS_17790061
	 */
	public static void TixianNotPass(String phone,String name){ 
		String json="{\"name\":\""+name+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_17790061", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_17790061");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//		} catch (ApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	
	/**
	 * 提现通过
	 * ${name}的提现申请已成功处理，到帐金额${money}元，根据银行处理进度在1-3个工作日内到帐，请留意银行短信通知或在提现记录中查询。SMS_17840145
	 */
	public static void TixianPass(String phone,String money,String name){//电话，姓名，产品名，模板
		//短信模板的内容
		String json="{\"name\":\""+name+"\",\"money\":\""+money+"\"}";
		TongYongSendMessage("normal", "九鱼网", phone, "SMS_17840145", json);
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName("九鱼网");
//			req.setRecNum(phone);
//			req.setSmsTemplateCode("SMS_17840145");
//			req.setSmsParamString(json);
//			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
//			} catch (ApiException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	}
	
 		/**
 		 * 充值成功提醒--尊敬的${name},您的手机号为${phone}的账户已成功充值${money},如有疑问，请联系客服，谢谢（用户验证SMS_7311163）
 		 * @param phone
 		 * @param money
 		 * @param name
 		 */
		public static void sendCZOK(String phone,String money,String name){//电话，姓名，
			//短信模板的内容
			String json="{\"money\":\""+money+"\",\"name\":\""+name+"\",\"phone\":\""+phone+"\"}";
			TongYongSendMessage("normal", "九鱼网", phone, "SMS_7311163", json);
//			try {
//				TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//				AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//				req.setSmsType("normal");
//				req.setSmsFreeSignName("九鱼网");
//				req.setSmsParamString(json);
//				req.setRecNum(phone);
//				req.setSmsTemplateCode("SMS_7311163");
//				AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//				System.out.println(rsp.getBody());
//			} catch (ApiException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//			}
		}
		
		
		/**
 		 *  领头羊专有信息模板
 		 */
		public static void LTYSendMessage(String phone,String name,String time,String number){//电话，姓名，时间，数量
 			String json="{\"name\":\""+name+"\",\"number\":\""+number+"\",\"time\":\""+time+"\"}";
//			TongYongSendMessage("normal", "九鱼网", phone, "SMS_7311163", json);
 		}
	
	
	// =================================================================================================
	public static void main(String [] args) {
		
	}
	
	
}

