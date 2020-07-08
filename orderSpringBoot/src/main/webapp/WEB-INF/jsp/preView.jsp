<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>点餐浏览页面</title>
<link href="/css/goods.css" rel="stylesheet">
<link href="/css/lbt.css" rel="stylesheet">
<script src="https://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<style type="text/css">
.shop {
    position: fixed;
    bottom: 0px;
    width: 100%;
    height: 50px;
    background: #070f1a;
    transition: all 0.5s ease 0s;
}
.shop .shopico {
    position: relative;
    width: 70px;
    height: 70px;
    border-radius: 50%;
    float: left;
    background: #070f1a;
    margin: -15px 0 0 10px;
}
.shop .shopico i {
    width: 50px;
    height: 50px;
    background: url(/images/shop.png) no-repeat;
    background-size: contain;
    display: inline-block;
    margin: 8px 0 0 10px;
    overflow: hidden;
    border-radius: 50px;
}
.shop .shopprice {
    float: left;
    line-height: 50px;
    font-size: 18px;
    font-weight: bold;
    color: #FFF;
    margin-left: 10px;
}
.shop .shopbut {
    background: #f55331;
    float: right;
    color: #FFF;
    font-size: 16px;
    font-weight: bold;
    line-height: 50px;
    padding: 0 20px;
}
.shop .numspan {
    position: absolute;
    top: -5px;
    right: 5px;
    width: 20px;
    height: 20px;
    text-align: center;
    font-size: 12px;
    line-height: 20px;
    color: #FFF;
    border-radius: 50%;
    background: -webkit-linear-gradient(left top, #f07c49, #ff0000); /* Safari 5.1 - 6.0 */
    background: -o-linear-gradient(bottom right, #f07c49, #ff0000); /* Opera 11.1 - 12.0 */
    background: -moz-linear-gradient(bottom right, #f07c49, #ff0000); /* Firefox 3.6 - 15 */
    background: linear-gradient(to bottom right, #f07c49, #ff0000); /* 标准的语法 */
}
.mask {
    width: 100%;
    background: #000;
    opacity: 0.5;
    top: 0;
    height: 100%;
    display: none;
    position: fixed;
}
.popup {
    position: fixed;
    width: 100%;
    height: 300px;
    background: #FFF;
    bottom: -300px;
    transition: all 0.5s ease 0s;
    
}
.popup .uptitle {
    height: 40px;
    line-height: 40px;
    padding: 0 15px;
    border-bottom: solid 1px #f9f9f9;
}
.popup .uptitle span {
    font-size: 16px;
    color: #000;
}
.popup .uptitle .tb {
    height: 16px;
    line-height: 20px;
    font-size: 13px;
    float: right;
    margin: 0;
    background: url(/images/del.png) no-repeat left center;
    background-size: contain;
    padding-left: 20px;
    color: #a1a1a1;
    margin: 13px 0 0 0;
}
.popup .uplist {
    width: 100%;
    height: 270px;
    overflow-y: scroll;
}
.popup .uplist ul li {
    width: 100%;
    height: auto;
    overflow: hidden;
    margin: 10px 0;
}
.popup .uplist .uppic {
    width: 80px;
    height: auto;
    float: left;
    margin: 10px 10px 10px 15px;
}
.popup .uplist .listtitle {
    width: 40%;
    float: left;
    margin: 10px 0 0 0;
    line-height: 25px;
}
.popup .uplist .listtitle h1 {
    font-size: 16px;
    font-weight: bold;
    color: #000;
}
.popup .uplist .listtitle h2 {
    font-size: 14px;
    font-weight: bold;
    color: #f60002;
}
.popup .uplist .listright {
    width: 30%;
    height: auto;
    float: right;
    margin: 20px 0 0 0;
}
.popup .uplist .listright span {
    display: block;
    width: 30px;
    height: 30px;
    float: left;
}
.popup .uplist .listright p {
    width: 30px;
    float: left;
    font-size: 14px;
    text-align: center;
    line-height: 30px;
}
.addnum {
    background: url(/images/jiah.png) no-repeat;
    background-size: contain;
}
.lessnum {
    background: url(/images/jianh.png) no-repeat;
    background-size: contain;
}
</style>
<!-- 前后台数据交互 -->
<script type="text/javascript">
$(function(){
	var openid = "${openid}";
	getCart(openid);
});

function getCart(openid) {
	
	$.ajax({
		type:"get",
		url:"/cart/getCart?openid="+openid,
		success:function(data){
			var uplist_ul =$(".uplist ul");
			uplist_ul.find("li").remove();
			var amount = 0;
			var quantity=0;
			debugger
			for(var i=0;i<data.length;i++){
				if(data[i].quantity<=0){
					
				}else{
					uplist_ul.append('<li><div class="uppic"> <img src='+data[i].icon+'> </div><div class="listtitle"><h1>'+data[i].name+'</h1> <h2>￥'+data[i].price+'</h2></div><div class="listright"> <span class="addnum" name="'+ data[i].id +'" onclick="add(this)"></span><p>'+data[i].quantity+'</p><span class="lessnum" name="'+ data[i].id +'" onclick="sub(this)"></span> </div></li>');
					amount= amount+data[i].price*data[i].quantity;
					quantity=data[i].quantity+quantity; 
				}
				
			}
			
			$("#amount").html("￥"+amount);
			$("#quantity").html(quantity);
			return data;
		}
	})
}
function add(obj) {
	var id =$(obj).attr("name");
	 var openid="${openid}";
	 var data = $.ajax({
		type:"post",
		async:false,
		url:"/cart/add?openid="+openid+"&id="+id,
		success:function(data){
			
			var uplist_ul =$(".uplist ul");
			uplist_ul.find("li").remove();
			var amount = 0;
			var quantity=0;
			debugger
			for(var i=0;i<data.length;i++){
				if(data[i].quantity<=0){
					
				}else{
					uplist_ul.append('<li><div class="uppic"> <img src='+data[i].icon+'> </div><div class="listtitle"><h1>'+data[i].name+'</h1> <h2>￥'+data[i].price+'</h2></div><div class="listright"> <span class="addnum" name="'+ data[i].id +'" onclick="add(this)"></span><p>'+data[i].quantity+'</p><span class="lessnum" name="'+ data[i].id +'" onclick="sub(this)"></span> </div></li>');
					amount= amount+data[i].price*data[i].quantity;
					quantity=data[i].quantity+quantity; 
				}
				
			}
			
			$("#amount").html("￥"+amount);
			$("#quantity").html(quantity);
			return data;
		}	
	});
	 
}
function sub(obj) {
	var id =$(obj).attr("name");
	 var openid="${openid}";
	 var data = $.ajax({
			type:"get",
			async:false,
			url:"/cart/sub?openid="+openid+"&id="+id,
			success:function(data){
				var uplist_ul =$(".uplist ul");
				uplist_ul.find("li").remove();
				var amount = 0;
				var quantity=0;
				debugger
				for(var i=0;i<data.length;i++){
					uplist_ul.append('<li><div class="uppic"> <img src='+data[i].icon+'> </div><div class="listtitle"><h1>'+data[i].name+'</h1> <h2>￥'+data[i].price+'</h2></div><div class="listright"> <span class="addnum" name="'+ data[i].id +'" onclick="add(this)"></span><p>'+data[i].quantity+'</p><span class="lessnum" name="'+ data[i].id +'" onclick="sub(this)"></span> </div></li>');
					amount= amount+data[i].price*data[i].quantity;
					quantity=data[i].quantity+quantity; 
				}
				
				$("#amount").html("￥"+amount);
				$("#quantity").html(quantity);
				return data;
			}	
		});
}
function clearCart(obj) {
	var openid="${openid}";
	 var data = $.ajax({
		type:"get",
		url:"/cart/clearCart?openid="+openid,
		success:function(data){
			$(".uplist ul").empty();
			$("#amount").html("");
			$("#quantity").html("");
			}

	});
	 
}

</script>
</head>

<body>
	<div class="header">
	  <div class="lbt">
	     <ul class="imgs">
	        <li><a><img src="/images/1.jpg" style="height:150px;"></a></li>
	        <li><a><img src="/images/2.jpg" style="height:150px;"></a></li>
	        <li><a><img src="/images/3.jpg" style="height:150px;"></a></li>
   		 </ul>
	  </div>
	  <div class="bulletin"><span class="bulletin-title"></span>公告信息公告信息公告信息公告信息公告信息</div>
	</div>
	 <div class="swiper-container">
	   <div class="swiper-wrapper">
	    <div class="swiper-slide">
	      <div class="content" style="height: 2216px;">
	         <div id="left" class="left">
			 	<ul>
			 	  <c:forEach items="${productVOs}" var="item">
			 	  	<li class="active">${item.cateName}</li>
			 	  </c:forEach>
			 	</ul>
     		</div>
     		 <div id="right" class="right" >
		       <ul>
		       	<c:forEach items="${productVOs}" var="item">
		       	  <li>
		              <div class="class-title">${item.cateName}</div>
		               <c:forEach items="${item.foods}" var="food">
			              <div>
			                <div class="item" >
			                  <div class="item-left"><a href="/product/detail?openid=${openid}&id=${food.id}">
			                    <div class="item-img"><img src="${food.foodIcon}"></div>
			                    </a>
			                  </div>
			                  <div class="item-right">
			                    <div class="title">${food.foodName}</div>
			                    <div class="price">¥ ${food.foodPrice} 元</div>
			                  </div>
			                  <div name="${food.id}" class="item-ico addnum" onclick="add(this)"></div>
			                </div>
			              </div>
			           </c:forEach>
		          </li>
		        </c:forEach>
		      </ul>
		     </div>
    
	      </div>
	    </div>
	   </div>
	 </div>
	<div class="mask"></div>
	<div class="popup">
	  <div class="uptitle"> <span>已选菜品</span>
	    <div class="tb" onclick="clearCart(this)">清空</div>
	  </div>
	  <div class="uplist">
	    <ul>
	    </ul>
	  </div>
</div>
<div class="shop">
  <div class="shopico"> <i></i>
    <div id="quantity" class="numspan"></div>
  </div>
  <div id="amount" class="shopprice"></div>
  <div class="shopbut"><a href="/cart/goCreateOrder?openid=${openid}">提交订单</a></div>
</div>
<!--轮播图js逻辑  -->
<script type="text/javascript">
	var index=0;
	  $(".num li").mousemove(function () {
          $(this).addClass("current").siblings().removeClass("current");
          index=$(this).index();
          $(".imgs li").eq(index).fadeIn(1000).siblings().fadeOut(1000);
  });
	  var time=setInterval(move,1000);
	    function move() {
	        index++;
	        if (index==3){
	            index=0
	        }
	        $(".num li").eq(index).addClass("current").siblings().removeClass("current");
	        $(".imgs li").eq(index).fadeIn(1000).siblings().fadeOut(1000);
	    };
	    $(".lbt").hover(function () {
	        clearInterval(time);
	    },
	    function () {
	        time=setInterval(move,1000);
	    });    
</script>  
<!--分类导航  -->     
<script type="text/javascript">
  $(function(){
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
    });
	//购物车点击
	$('.shop').click(function(){
      $('.mask').show();
	  $('.popup').css("bottom","50px");
    });
	$('.mask').click(function(){
      $('.mask').hide();
	  $('.popup').css("bottom","-300px");
    });
  })
  </script>

     
</body>
</html>