package com.tianer.util.huanxin;


import com.tianer.util.huanxin.api.ChatGroupAPI;
import com.tianer.util.huanxin.api.ChatMessageAPI;
import com.tianer.util.huanxin.api.ChatRoomAPI;
import com.tianer.util.huanxin.api.FileAPI;
import com.tianer.util.huanxin.api.IMUserAPI;
import com.tianer.util.huanxin.api.SendMessageAPI;
import com.tianer.util.huanxin.comm.ClientContext;
import com.tianer.util.huanxin.comm.EasemobRestAPIFactory;
import com.tianer.util.huanxin.comm.body.IMUserBody;
import com.tianer.util.huanxin.comm.wrapper.BodyWrapper;
import com.tianer.util.huanxin.comm.wrapper.ResponseWrapper;

/**
 * 
* 类名称：HuanXin   
* 类描述：   注册环信账号
* 创建人：魏汉文  
* 创建时间：2016年7月8日 下午1:38:04
 */
public class HuanXin   {

 		public static void main(String[] args) throws Exception {
// 			regirstHx("jy152602823405","jy152602823405","jy152602823405");
 			addFriendHx("33391593","jy182369932264zfz");
// 			deleteFriendSingle("01101332","33391593");
// 			getFriends("33391593");
		}
		

//        ResponseWrapper fileResponse = (ResponseWrapper) file.uploadFile(new File(StringUtil.getUrl()+Const.HUANXIN));
//        String uuid = ((ObjectNode) fileResponse.getResponseBody()).get("entities").get(0).get("uuid").asText();
//        String shareSecret = ((ObjectNode) fileResponse.getResponseBody()).get("entities").get(0).get("share-secret").asText();
//        InputStream in = (InputStream) ((ResponseWrapper) file.downloadFile(uuid, shareSecret, false)).getResponseBody();
//        FileOutputStream fos = new FileOutputStream(StringUtil.getUrl()+Const.HUANXIN);
//        byte[] buffer = new byte[1024];
//        int len1 = 0;
//        while ((len1 = in.read(buffer)) != -1) {
//            fos.write(buffer, 0, len1);
//        }
//        fos.close();

 

		// Create a IM user
//		BodyWrapper userBody = new IMUserBody("12345", "123456", "HelloWorld");//用户名（ID），密码，名称
//		user.createNewIMUserSingle(userBody);//新增用户
//        ResetPasswordBody restpass
// 		user.modifyIMUserPasswordWithAdminToken("12345",JsonNodeFactory.instance.objectNode().put("newpassword", "12345"));//修改密码
 	// Get a IM user
//		user.getIMUsersByUserName("12345");
		
		
		

		// Create some IM users
//		List<IMUserBody> users = new ArrayList<IMUserBody>();
//		users.add(new IMUserBody("User002", "123456", null));
//		users.add(new IMUserBody("User003", "123456", null));
//		BodyWrapper usersBody = new IMUsersBody(users);
//		user.createNewIMUserBatch(usersBody);
//		
//		// Get a IM user
//		user.getIMUsersByUserName("User001");
//		
//		// Get a fake user
//		user.getIMUsersByUserName("FakeUser001");
//		
//		// Get 12 users
//		user.getIMUsersBatch(null, null);
			
			
			/**
			 * 
			* 方法名称:：regirstHx 
			* 方法描述：注册环信账号
			* 创建人：魏汉文
			* 创建时间：2016年7月8日 下午4:11:25
			 */
			public static void regirstHx(String number,String password ,String name){
//				System.out.println(number+"**********"+password+"**********"+name);
				try{
					EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
 					IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
					//注册
					BodyWrapper userBody = new IMUserBody(number, password, name);//用户名（ID），密码，名称
					user.createNewIMUserSingle(userBody);//新增用户
 				}catch(Exception e){
					System.out.println(e.toString());
				}
  			}
			
			/**
			 * 
			 * 方法名称:：addFriendHx 
			 * 方法描述：环信添加好友
			 * 创建人：魏汉文
			 * 创建时间：2016年7月8日 下午4:11:25
			 */
			public static void addFriendHx(String addID,String BeaddId){
				System.out.println(addID+"%"+BeaddId);
				try{
					EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
					IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
//					ChatMessageAPI chat = (ChatMessageAPI)factory.newInstance(EasemobRestAPIFactory.MESSAGE_CLASS);
//					FileAPI file = (FileAPI)factory.newInstance(EasemobRestAPIFactory.FILE_CLASS);
//					SendMessageAPI message = (SendMessageAPI)factory.newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
//					ChatGroupAPI chatgroup = (ChatGroupAPI)factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
//					ChatRoomAPI chatroom = (ChatRoomAPI)factory.newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
					//新增好友
 					user.addFriendSingle(BeaddId,addID ); //用户名。好友用户名
 				}catch(Exception e){
					System.out.println(e.toString());
				}
			}
			/**
			 * 
			 * 方法名称:：deleteFriendSingle 
			 * 方法描述：环信删除好友
			 * 创建人：魏汉文
			 * 创建时间：2016年7月8日 下午4:11:25
			 */
			public static void deleteFriendSingle(String addID,String BeaddId){
				System.out.println(addID+"%"+BeaddId);
				try{
					EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
					IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
//					ChatMessageAPI chat = (ChatMessageAPI)factory.newInstance(EasemobRestAPIFactory.MESSAGE_CLASS);
//					FileAPI file = (FileAPI)factory.newInstance(EasemobRestAPIFactory.FILE_CLASS);
//					SendMessageAPI message = (SendMessageAPI)factory.newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
//					ChatGroupAPI chatgroup = (ChatGroupAPI)factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
//					ChatRoomAPI chatroom = (ChatRoomAPI)factory.newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
					//删除好友
 					user.deleteFriendSingle(BeaddId,addID ); //用户名。好友用户名
				}catch(Exception e){
					System.out.println(e.toString());
				}
			}
			/**
			 * 
			 * 方法名称:：getFriends 
			 * 方法描述：环信删除好友
			 * 创建人：魏汉文
			 * 创建时间：2016年7月8日 下午4:11:25
			 */
			public static void getFriends(String BeaddId){
				System.out.println("%"+BeaddId);
				try{
					EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
					IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
//					ChatMessageAPI chat = (ChatMessageAPI)factory.newInstance(EasemobRestAPIFactory.MESSAGE_CLASS);
//					FileAPI file = (FileAPI)factory.newInstance(EasemobRestAPIFactory.FILE_CLASS);
//					SendMessageAPI message = (SendMessageAPI)factory.newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
//					ChatGroupAPI chatgroup = (ChatGroupAPI)factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
//					ChatRoomAPI chatroom = (ChatRoomAPI)factory.newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
					//删除好友
					ResponseWrapper responsewrapper = (ResponseWrapper) user.getFriends(BeaddId ); //用户名 
					System.out.println(responsewrapper.toString());
				}catch(Exception e){
					System.out.println(e.toString());
				}
			}
			
			 

	}
 

 