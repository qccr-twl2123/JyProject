
 
//全部分类的展示隐藏效果
function nav_mouseover(){
  $(".xl_list").show()
}
function li_mouseover(e){
  if($(e).children("ul").children().length>2){
      $(".two_list").css("width","320px")
  }else{
      $(".two_list").css("width","auto")
  }
  var ele;
  if($(e.tagName)[0].nodeName=="LI"){
      ele=$(e)
  }else{
      ele=$(e).parents("li")
  }
  ele.find(".two_list").show();
  ele.css("color","#e90000")
}
function li_mouseleave(e){
  var ele;
  if($(e.tagName)[0].nodeName=="LI"){
      ele=$(e)
  }else{
      ele=$(e).parents("li")
  }
  ele.find(".two_list").hide();
  ele.css("color","#333")
}
function nav_mouseleave(){
  $(".xl_list").hide()
}
function two_over(e){
    $(e.target).parents(".two_list").parent("li").children(".item_color").css("color","#e90000")
}
function two_leave(){
    $(".xl_list .item_color").css("color","")
}
//星级载入
function xjzr(){
    $.fn.raty.defaults.path = 'js/memberpc/library/lib/img';
    for (var i=0;i<$(".star_box").length;i++){
        var star_id= $($(".star_box")[i]).attr("id");
        var star_num=$($(".star_box")[i]).attr("str_num");
        if($("#"+star_id).children().length>0){
            $("#"+star_id).html("");
            $("#"+star_id).raty({
                num :5,
                readOnly: true,
                halfShow: true,
                score   : star_num,
                hints   : star_num+"分",   /*鼠标移入是显示分值*/
                noRatedMsg:"暂无评分"       /*鼠标移入  无评分时*/
            });
        }else{
            $("#"+star_id).raty({
                num :5,
                readOnly: true,
                halfShow: true,
                score   : star_num,
                hints   : star_num+"分",
                noRatedMsg:"暂无评分"
            });
        }
    }
}

//商家展示的效果
function list_hover(e){
    var ele;
    if($(e)[0].nodeName=="LI"){
        ele=$(e)
    }else{
        ele=$(e).parents("li")
    }
    ele.css("box-shadow","0px 0px 8px #ccc")
}
function list_leave(e){
    var ele;
    if($(e)[0].nodeName=="LI"){
        ele=$(e)
    }else{
        ele=$(e).parents("li")
    }
    ele.css("box-shadow","")
}

 
//点击搜索
function search_click(){
    var content=$("#sj_search").val();
    window.location=base_inf.base_href+"memberpclistAllStore.do?city_name="+$("#loc_shi option:selected").text()+"&area_name="+$("#loc_qu option:selected").text()+"&paixu=&shaixuan=&content="+content+"&city_file_sort_id=&sort_name=全部分类&sort_type=0"
}


//全部分类搜索
function checkedByCitySort(ele){
	event.stopPropagation();
   var content=$("#sj_search").val();
   var id=$(ele).attr("s_id");
   var name=$(ele).attr("s_name");
   var type=$(ele).attr("s_type");
   window.location=base_inf.base_href+"memberpclistAllStore.do?city_name="+$("#loc_shi option:selected").text()+"&area_name="+$("#loc_qu option:selected").text()+"&paixu=&shaixuan=&content="+content+"&city_file_sort_id="+id +"&sort_name="+name+"&sort_type="+type
}

//筛选,排序
function checked(shaixuan,paixu){
	 var content=$("#sj_search").val();
	 var city_file_sort_id=$(".city_file_sort_id").val();
	 var sort_name=$(".sort_name").val();
	 var sort_type=$(".sort_type").val();
	window.location=base_inf.base_href+"memberpclistAllStore.do?city_name="+$("#loc_shi option:selected").text()+"&area_name="+$("#loc_qu option:selected").text()+"&paixu="+paixu+"&shaixuan="+shaixuan+"&content="+content+"&city_file_sort_id="+city_file_sort_id+"&sort_name="+sort_name+"&sort_type="+sort_type;
}


//获取区域
function addsearcharea(){
  	$.ajax({
		  url:"memberpcListAllArea.do",
		  data:"city_id="+ $("#loc_shi").val(),
		  type:"post",
		  dataType:"json",
			  success:function(data){
			  	var list=data.data;
			  	$("#loc_qu option").remove()
			  	$("#loc_qu").html("<option value='请选择'>请选择</option>");
 			  	if(list.length>0){
				  	for(var i=0;i<list.length;i++){
				  		$("#loc_qu").append("<option value='"+list[i].area_id+"'>"+list[i].area_name+"</option>");
				  	}
		  		}
		  },
		  error:function(a){
		  	alert("异常");
		  }
	});
}
//刷新当前首页
function shaixin(){
	window.location=base_inf.base_href+"memberpclistAllStore.do?city_name="+$("#loc_shi option:selected").text()+"&area_name="+$("#loc_qu option:selected").text()+"&paixu=&shaixuan=&content=&city_file_sort_id=&sort_name=全部分类&sort_type=0"
}

//回到首页
function goindex(){
    window.location=base_inf.base_href;
}

//前往商家详情页
function gostore(ele){
	 window.location=base_inf.base_href+"memberpc/storeInfoList.do?city_name="+$("#loc_shi option:selected").text()+"&area_name="+$("#loc_qu option:selected").text()+"&sk="+$(ele).attr("s_id")+"&sc=1&r_cl=0&timestamp="+new Date().getTime();
}


$(function() {
    //二维码显示
    $(".show_ewm").last().parent().mouseenter(function () {
        $(".ewm_appdown").show()
    });
    $(".show_ewm").last().parent().mouseleave(function () {
        $(".ewm_appdown").hide()
    });
      xjzr();//星级载入
 });