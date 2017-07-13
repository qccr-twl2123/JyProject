				// 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器  
	    	    var useragent = navigator.userAgent;  
	    	    if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {  
	    	        // 这里警告框会阻塞当前页面继续加载  
	    	        //alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');  
	    	        // 以下代码是用javascript强行关闭当前页面  
	    	        var opened = window.open('about:blank', '_self');  
	    	        opened.opener = null;  
	    	        opened.close();  
	    	    }  
			


				//微信js
   				var len=16;
 				var now_url=location.href.split('#')[0];
  				len = len || 32;
 	 			var $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
 	 			var maxPos = $chars.length;
 	 			var noncestr = '';
 	 			for (i = 0; i < len; i++) {
 	 			　　　　noncestr += $chars.charAt(Math.floor(Math.random() * maxPos));
 	 			}
 	 			var timestamp=Math.round(new Date().getTime()/1000);
   				$.ajax({
    	         	type:"post",
    	         	url: base_inf.base_herf+"html_member/getSignatureAjax.do", 
    	         	data:{
    	         		"timestamp":timestamp,
    	         		"noncestr":noncestr ,
    	         		"now_url":now_url 
    	         	},
    		        dataType:"json",
    		        asyn:false,
    		        success: function(data){
      		        	var signature=data.data;
     		        	wx.config({
     		        	      debug: false,
     		        	      appId: data.appId,
     		        	      timestamp: timestamp,
     		        	      nonceStr: noncestr,
     		        	      signature: signature,
     		        	      jsApiList: [
     		        	        'checkJsApi',
     		        	        'onMenuShareTimeline',
     		        	        'onMenuShareAppMessage',
     		        	        'onMenuShareQQ',
     		        	        'onMenuShareWeibo',
     		        	        'onMenuShareQZone',
     		        	        'hideMenuItems',
     		        	        'showMenuItems',
     		        	        'hideAllNonBaseMenuItem',
     		        	        'showAllNonBaseMenuItem',
     		        	        'translateVoice',
     		        	        'startRecord',
     		        	        'stopRecord',
     		        	        'onVoiceRecordEnd',
     		        	        'playVoice',
     		        	        'onVoicePlayEnd',
     		        	        'pauseVoice',
     		        	        'stopVoice',
     		        	        'uploadVoice',
     		        	        'downloadVoice',
     		        	        'chooseImage',
     		        	        'previewImage',
     		        	        'uploadImage',
     		        	        'downloadImage',
     		        	        'getNetworkType',
     		        	        'openLocation',
     		        	        'getLocation',
     		        	        'hideOptionMenu',
     		        	        'showOptionMenu',
     		        	        'closeWindow',
     		        	        'scanQRCode',
     		        	        'chooseWXPay',
     		        	        'openProductSpecificView',
     		        	        'addCard',
     		        	        'chooseCard',
     		        	        'openCard'
     		        	      ]
     		        	  });
      		        	 
     		        }
    		   });
   				
   				wx.error(function (res) {
   					console.log(res.errMsg); 
   				});
   				
   				
   				wx.ready(function () {
   					console.log("success"); 
   				});
  	 		
  			

  		//扫一扫优惠买单
  		function saoyisao(){
  			wx.scanQRCode({
			      needResult: 1,
			      scanType: ["qrCode","barCode"],
		 	      success: function (res) {
		 	    	 var str=res.resultStr
  			         if(res.resultStr.indexOf("@") == "8"){
 			        	var store_id=res.resultStr.split("@")[0];
 			        	var desk_no=res.resultStr.split("@")[1];
  			        	window.location.href=base_inf.base_herf+"html_member/sysyhmd.do?desk_no="+desk_no+"&store_id="+store_id;
 			         }
  			      }
			});
  		}
  		
  		//微信定位
  		function wxdingwei(){
  			wx.ready(function () {
  			//微信定位
				wx.getLocation({
 				    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
 				    success: function (res) {
 				        var wxlat = res.latitude; // 纬度，浮点数，范围为90 ~ -90
 				        var wxlng = res.longitude; // 经度，浮点数，范围为180 ~ -180。
 				        var speed = res.speed; // 速度，以米/每秒计
 				        var accuracy = res.accuracy; // 位置精度
 				     	gogogo3(wxlng,wxlat);
	 				}
				});
   			});
   		}
  		
  		//微信坐标管理转换
	    function gogogo3(wxlng,wxlat){
	    		$.ajax({
		         	type:"post",
		         	url:"https://api.map.baidu.com/geoconv/v1/?coords="+wxlng+","+wxlat+"&from=1&to=5&ak=KUS9Zfra9SBVjiljB1vDpofLkH8bXuL9", 
			        dataType:"jsonp",
			        success: function(data){
	 		        	var status=data.status;
	 		        	var result=data.result;
	 		        	if(status == 0){
	 		        		window.lat=result[0].y;
	   		        		window.lng=result[0].x;
 		 		        	pcd(lng,lat);
	 		        	}else{
	 		        		gogogo2();
	 		        	}
	 		        }
			   });
 	    }
	    
	    //开始微信支付
	    function startWxPay(total_fee,attach,body){
	    	$.ajax({
	         	type:"post",
	         	url:base_inf.base_herf+"html_member/wxpayorder.do", 
		        dataType:"json",
		        success: function(data){
 		        	 
 		        }
		   });
	    }
	    
	    
	    
	    
	    //发起一个微信支付请求payment_type:1-优惠买单支付，2-正常商品提货券支付，3-优惠买单支付，4-充值
	    function wxpay(payment_type_,appId_,timestamp_,nonceStr_,package_,signType_,paySign_,out_trade_no_){
	    	wx.chooseWXPay({
	    	    timestamp: timestamp_, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
	    	    nonceStr: nonceStr_, // 支付签名随机串，不长于 32 位
	    	    package: package_, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
	    	    signType: signType_, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
	    	    paySign: paySign_, // 支付签名
	    	    success: function (res) {
	    	    	 console.log(res); 
	    	        // 支付成功后的回调函数
	    	    	if(payment_type == "1"){
	    	    		
	    	    	}else if(payment_type == "2"){
	    	    		
	    	    	}else if(payment_type == "3"){
	    	    		
	    	    	}else{
	    	    		
	    	    	}
 	    	    }
	    	});
 	    }
	    
	    //公众号发起微信支付
	    function onBridgeReady(payment_type_,appId_,timestamp_,nonceStr_,package_,signType_,paySign_,out_trade_no_){
	    	   WeixinJSBridge.invoke(
	    	       'getBrandWCPayRequest', {
	    	           "appId":appId_,     //公众号名称，由商户传入     
	    	           "timeStamp":timestamp_,         //时间戳，自1970年以来的秒数     1395712654 
	    	           "nonceStr":nonceStr_, //随机串     
	    	           "package":package_,     
	    	           "signType":signType_,         //微信签名方式：     
	    	           "paySign":paySign_ //微信签名 
	    	       },
	    	       function(res){   
	    	    	   console.log(res); 
	    	           if(res.err_msg == "get_brand_wcpay_request:ok" ) {// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	    	        	    // 支付成功后的回调函数
		   	    	    	if(payment_type == "1"){
		   	    	    		window.location.href='html_member/findById.do?ordertype=2&order_id='+out_trade_no_;
 		   	    	    	}else if(payment_type == "2"){
 		   	    	    		window.location.href='html_member/findById.do?ordertype=3&order_id='+out_trade_no_;
		   	    	    	}else if(payment_type == "3"){
		   	    	    		window.location.href='html_member/payOkGoJsp.do?orderno='+out_trade_no_;
		   	    	    	}else{
		   	    	    		window.location.href=base_inf.base_herf+"html_me/goMe.do?type=11";
		   	    	    	}
	    	        	   
	    	           }     
	    	       }
	    	   ); 
	    	}
	    	if (typeof WeixinJSBridge == "undefined"){
	    	   if( document.addEventListener ){
	    	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	    	   }else if (document.attachEvent){
	    	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	    	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	    	   }
	    	}else{
	    	   onBridgeReady();
	    	}
	    	
	    