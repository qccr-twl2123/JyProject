//package com.tianer.util;
//
//import cn.jpush.api.JPushClient;
//import cn.jpush.api.common.resp.APIConnectionException;
//import cn.jpush.api.common.resp.APIRequestException;
//import cn.jpush.api.push.PushResult;
//import cn.jpush.api.push.model.Message;
//import cn.jpush.api.push.model.Options;
//import cn.jpush.api.push.model.Platform;
//import cn.jpush.api.push.model.PushPayload;
//import cn.jpush.api.push.model.audience.Audience;
//import cn.jpush.api.push.model.audience.AudienceTarget;
//import cn.jpush.api.push.model.notification.AndroidNotification;
//import cn.jpush.api.push.model.notification.IosNotification;
//import cn.jpush.api.push.model.notification.Notification;
//
//public class JPushClientUtilSD {
//
//    // demo App defined in resources/jpush-api.conf 
//	private static final String appKey ="d5f987fa8cca75849809f808";//长兴生活(极光账号里的key和secret)给供货商和跑腿用的
//	private static final String masterSecret ="95ea0605f393340de9a1f5e7";
//	
//	public static final String TITLE = "标题123";
//    public static final String ALERT = "警告";
//    public static final String MSG_CONTENT = "内容123";
//    public static final String REGISTRATION_ID = "0a159567433";//0a159567433 
//    public static final String TAG = "tag_api";
//    public static final String  ALIAS= "191e35f7e0438bb0be5";//15266668888，13732120730,18668130876
//
//	public static void main(String[] args) {
//		pushMessage(TITLE,MSG_CONTENT,ALIAS);
//	}
//	
//	/**
//	 * 
//	* 方法名：pushMessage
//	* 作者：whw
//	* 时间：2015年12月25日
//	* 描述：给供货商和跑腿用的
//	* 返回类型：json
//	 */
//	public static void pushMessage(String title,String content,String alias) {
//	    //HttpProxy proxy = new HttpProxy("localhost", 3128);
//	    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
//        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
//        
//        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = buildPushObject_android_and_ios(title,content,alias);
//        
//        try {
//        	//System.out.println(payload);
//            PushResult result = jpushClient.sendPush(payload);
//            System.out.println(result);
//            
//            /*
//             * {"platform":["ios","android"],
//             * 	"audience":{"registration_id":["000593995ef"]},
//             * 	"notification":{"alert":"内容123",
//             * 					"ios":{"alert":"内容123","badge":"+1","sound":""},
//             * 					"android":{"alert":"内容123","title":"标题123"}
//             * 					},
//             * 	"message":{"msg_content":"内容123"},
//             * 	"options":{"sendno":1253636092,"apns_production":false}
//             * }
//             * 
//             * 
//             * {"msg_id":4281519184,"sendno":1793276250}
//             */
//            
//        } catch (APIConnectionException e) {
//        	e.printStackTrace();
//        	System.out.println(e.toString());
//        } catch (APIRequestException e) {
//           e.printStackTrace();
//           System.out.println(e.toString());
//        }
//	}
//	
//	public static PushPayload buildPushObject_all_all_alert() {
//	    return PushPayload.alertAll(ALERT);
//	}
//	
//    public static PushPayload buildPushObject_all_alias_alert() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.alias("alias1"))
//                .setNotification(Notification.alert(ALERT))
//                .build();
//    }
//    
//    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.android())
//                .setAudience(Audience.tag("tag1"))
//                .setNotification(Notification.android(ALERT, TITLE, null))
//                .build();
//    }
//    
//    
//    //推送安卓和ios
////    public static PushPayload buildPushObject_android_and_ios(String title,String content,String alias) {
//    public static PushPayload buildPushObject_android_and_ios(String title,String content,String registration_id) {
//         return PushPayload.newBuilder()
//                .setPlatform(Platform.android_ios())
////                .setAudience(Audience.alias(alias))//使用别名
//                .setAudience(Audience.registrationId(registration_id))//使用手机唯一标识
//                .setNotification(Notification.newBuilder()
//                		.setAlert(content)
//                		.addPlatformNotification(AndroidNotification.newBuilder()
//                				.setTitle(title)
////                				.setBuilderId(2) 
//                				.build())
//                		.addPlatformNotification(IosNotification.newBuilder()
//                 				.incrBadge(0)//条数
////                				.setContentAvailable(true)//背景样式
////                				.setSound("")//声音样式
////                				.addExtra("extra_key", "extra_value")
//                				.build())
//                		.build())
//                .setMessage(Message.newBuilder()
//                        .setMsgContent(content)
////                        .addExtra("from", "JPush")
//                        .build())
//                .build();
//    }
//    
//    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.ios())
//                .setAudience(Audience.tag_and("tag1", "tag_all"))
//                .setNotification(Notification.newBuilder()
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                .setAlert(ALERT)
//                                .setBadge(5)
//                                .setSound("happy")
//                                .addExtra("from", "JPush")
//                                .build())
//                        .build())
//                 .setMessage(Message.content(MSG_CONTENT))
//                 .setOptions(Options.newBuilder()
//                         .setApnsProduction(true)
//                         .build())
//                 .build();
//    }
//    
//    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.android_ios())
//                .setAudience(Audience.newBuilder()
//                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
//                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
//                        .build())
//                .setMessage(Message.newBuilder()
//                        .setMsgContent(MSG_CONTENT)
//                        .addExtra("from", "JPush")
//                        .build())
//                .build();
//    }
//    
//    
//}
//
