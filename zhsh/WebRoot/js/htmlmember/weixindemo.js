  				//微信定位
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
     		        	      appId: 'wx62d81eec40f745b4',
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
  			        	window.location.href=base_inf.base_herf+"html_member/sysyhmd.do?desk_no="+desk_no+"&store_id="+store_id+"&member_id="+base_inf.member_id;
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
 // 				    alert(wxlat+"&"+wxlng);
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
//	   		        		console.log(lat+"-"+lng);
 		 		        	pcd(lng,lat);
	 		        	}else{
	 		        		gogogo2();
	 		        	}
	 		        }
			   });
	    		
	    	}