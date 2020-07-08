<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="nav.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>点餐系统首页</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/materialdesignicons.min.css" rel="stylesheet">
<link href="/css/style.min.css" rel="stylesheet">
<script type="text/javascript" src="/js/echarts.js"></script>
<style type="text/css">
.label:empty {
    display: none;
}

</style>
</head>
<body>
<div class="lyear-layout-container">
    <div class="lyear-layout-content">
    <div class="mainImg">
    <img alt="" src="/images/main.jpg">
    </div>
   </div>
</div>
<script type="text/javascript">
$(function () {
	 /* $.ajax({
		type:"get",
		async:false,
		url:"/sell/list",
		success:function(data){
			debugger;
			for(var i=0;i<data.length;i++){
				$("#right ul").append('<div class="class-title" id='+i+'>'+data[i].cateName+'</div>');
				var content=data[i].foods;
				debugger;
				for(var j=0;j<content.length;j++){
					debugger;
					$("#"+i).append('<div>'
			                +'<div class="item" >'
			                +' <div class="item-left">'
			                +' <div class="item-img"><img src="'+content[j].foodIcon+'"></div>'
			                    
			                +' </div>'
			                +' <div class="item-right">'
			                +' <div class="title">'+content[j].foodName+'</div>'
			                +' <div class="price">¥ '+content[j].foodPrice+' 元</div>'
			                +'</div>'
			                +' <div  class="item-ico addnum" ></div>'
			                +'</div>'
			                +'</div>');
				}
				
			}
		
			
		}
	});  */
	var msg="";
	var loginMsg="${result.msg}"
	var editMsg="${msg}";
	if(loginMsg!=null){
		msg=loginMsg;
	}else if(loginMsg!=null){
		msg=editMsg;
	}
	
	if(msg!=""){
		$.globalMessenger().post({
			message: msg,
			type: 'info',
			ideAfter: 1,
		    showCloseButton: true,
		});
	}
	
	/* 
	 $('.content').css('height',$('.right').height());
    $('.left ul li').eq(0).addClass('active');
    $(window).scroll(function(){
      if($(window).scrollTop() >= 150){
        $('.swiper-container-ul').css('position','fixed');
        $('.left').css('position','fixed');
        $('.right').css('margin-left',$('.left').width());
      }else {
        $('.swiper-container-ul').css('position','');
        $('.left').css('position','');
        $('.right').css('margin-left','');
      };
      //滚动到标杆位置,左侧导航加active
      $('.right ul li').each(function(){
        var target = parseInt($(this).offset().top-$(window).scrollTop()-150);
		//alert(target);
        var i = $(this).index();
        if (target<=0) {
          $('.left ul li').removeClass('active');
          $('.left ul li').eq(i).addClass('active');
        }
      });
    });
    $('.left ul li').click(function(){
      var i = $(this).index('.left ul li');
      $('body, html').animate({scrollTop:$('.right ul li').eq(i).offset().top-40},500);
    }); */
}); 
</script>

</body>
</html>