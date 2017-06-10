package com.tianer.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 九鱼极光推送
 * @author Administrator
 *
 */
public class JPushClientUtil {

 	private static final String storeappKey ="6eb1d3b1f712d1d624676a1e"; 
 	private static final String storemasterSecret = "56a091b0154ddf17491d1b21";
 	
	private static final String memberappKey ="46edd984de8d8aa68d2fb45c"; 
	private static final String membermasterSecret = "78ea5f130837d3d2470fa9e4";
	
//	private static final String storeappKey ="1";// 
//	private static final String storemasterSecret = "1";
//	
//	private static final String memberappKey ="1";// 
//	private static final String membermasterSecret = "1";
	
	public static final String  TITLE = "九鱼标题";
    public static final String  ALERT = "九鱼警告";
    public static final String  MSG_CONTENT = "欢迎来到九鱼中心，呦呦呦";
    public static final String  type = "3";
    public static final String  id = "33391593";
//  public static final String  REGISTRATION_ID = "0a159567433"; 
//  public static final String  TAG = "tag_api";
    public static final String  ALIAS=  "jy15260282340c811"; 
    public static final String  ALIAS2= "jy182369932264zfz"; 
    public static final String  ALIAS1= "jy1345353182761vo"; 
    public static final String  ALIAS4= "33391593"; 
    public static final String  ALIAS3= "47883919"; 
    public static final String  ALIAS6= "15514203"; 
    public static final String  ALIAS5= "28483703"; 

	public static void main(String[] args) {
		storepushMessage(TITLE,MSG_CONTENT,ALIAS6,type,id,"");
//		storepushMessage(TITLE,MSG_CONTENT,ALIAS4,type,id,"");
//		memberpushMessage(TITLE,MSG_CONTENT,ALIAS2,type,id,"");
//		memberpushMessage(TITLE,MSG_CONTENT,ALIAS,type,id,"");
//		memberpushMessage(TITLE,MSG_CONTENT,ALIAS1,type,id,"");
 	}
	
	/**
	 * 
	* 方法名：storepushMessage
	* 作者：商家推送
	* 时间：2015年12月25日
	* 描述：给用户用的
	* 返回类型：json
	 */
	public static void storepushMessage(String title,String content,String alias,String type,String id,String other) {
          JPushClient jpushClient = new JPushClient(storemasterSecret, storeappKey, 3);
          PushPayload payload = buildPushObject_android_and_ios(title,content,alias, type,id,other);
        
        try {
//        	System.out.println(payload);
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("推送至商家"+alias+"成功"+result);
             /*
             * {"platform":["ios","android"],
             * 	"audience":{"registration_id":["000593995ef"]},
             * 	"notification":{"alert":"内容123",
             * 					"ios":{"alert":"内容123","badge":"+1","sound":""},
             * 					"android":{"alert":"内容123","title":"标题123"}
             * 					},
             * 	"message":{"msg_content":"内容123"},
             * 	"options":{"sendno":1253636092,"apns_production":false}
             * }
             * 
             * 
             * {"msg_id":4281519184,"sendno":1793276250}
             */
         } catch (APIConnectionException e) {
//        	e.printStackTrace();
        	System.out.println("推送商家"+alias+"出错：忽略"+e.toString());
        } catch (APIRequestException e) {
//           e.printStackTrace();
           System.out.println("推送商家"+alias+"出错：忽略"+e.toString());
        }
	}
	
	/**
	 * 
	* 方法名：memberpushMessage
	* 作者：会员推送
	* 时间：2015年12月25日
	* 描述：给用户用的
	* 返回类型：json
	 */
	public static void memberpushMessage(String title,String content,String alias,String type,String id,String other) {
          JPushClient jpushClient = new JPushClient(membermasterSecret, memberappKey, 3);
          PushPayload payload = buildPushObject_android_and_ios(title,content,alias,type,id,other);// 对android和ios设备发送,同时指定离线消息保存时间
         try {
//        	System.out.println(payload);
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("推送至会员"+alias+"成功"+result);
             /*
             * {"platform":["ios","android"],
             * 	"audience":{"registration_id":["000593995ef"]},
             * 	"notification":{"alert":"内容123",
             * 					"ios":{"alert":"内容123","badge":"+1","sound":""},
             * 					"android":{"alert":"内容123","title":"标题123"}
             * 					},
             * 	"message":{"msg_content":"内容123"},
             * 	"options":{"sendno":1253636092,"apns_production":false}
             * }
             * 
             * 
             * {"msg_id":4281519184,"sendno":1793276250}
             */
         } catch (APIConnectionException e) {
//        	e.printStackTrace();
        	System.out.println("推送至会员"+alias+"失败"+e.toString());
        } catch (APIRequestException e) {
//           e.printStackTrace();
           System.out.println("推送至会员"+alias+"失败"+e.toString());
        }
	}
	
	/**
	 * 推送给所有人安装过app的
	 * @return
	 */
	public static PushPayload buildPushObject_all_all_alert() {
	    return PushPayload.alertAll(ALERT);
	}
	
	/**
	 * 推送给所有指定别名的
	 * @return
	 */
    public static PushPayload buildPushObject_all_alias_alert(String alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(ALERT))
                .build();
    }
    
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(ALERT, TITLE, null))
                 .build();
    }
    
    
    //推送安卓和ios
    public static PushPayload buildPushObject_android_and_ios(String title,String content,String alias,String type,String id,String other) {
         return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))//(别名)推送设备对象，表示一条推送可以被推送到哪些设备列表。确认推送设备对象，JPush 提供了多种方式，比如：别名、标签、注册ID、分群、广播等
//                .setAudience(Audience.registrationId(registration_id))//使用手机唯一标识
                .setNotification(Notification.newBuilder()//“通知”对象，是一条推送的实体内容对象之一（另一个是“消息”），是会作为“通知”推送到客户端的。
                		.setAlert(content)
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle(title)
                 				.setBuilderId(2) 
                  				.addExtra("type", type)
                 				.addExtra("go_id", id)
                 				.addExtra("other", other)
                				.build())
                		.addPlatformNotification(IosNotification.newBuilder()
//                 				.incrBadge(0)//条数
                				.setContentAvailable(true)//背景样式
                				.setSound("happy")//声音样式：default默认
                 				.addExtra("type", type)
                 				.addExtra("go_id", id)
                 				.addExtra("other", other)
                				.build())
                		.build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(title)
                        .setTitle(content)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }
    
    
    
    
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }
    
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(MSG_CONTENT)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }
    
    
}

