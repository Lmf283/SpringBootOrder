<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>我的订单</title>
<link href="/css/style.min.css" rel="stylesheet">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery.min.js"></script>
<style type="text/css">
.orderTitle{
	display:inline-block;
}
.uppic {
    width: 80px;
    height: auto;
    display:inline-block;
    margin: 10px 10px 10px 15px;
}
.listtitle {
    width: 40%;
   display:inline-block;
    margin: 10px 0 0 0;
    line-height: 25px;
}
.listright {
    width: 30%;
    height: auto;
    display:inline-block;
    margin: 20px 0 0 0;
}
.listtitle h1 {
    font-size: 16px;
    font-weight: bold;
    color: #000;
}
.listtitle h2 {
    font-size: 14px;
    font-weight: bold;
    color: #f60002;
}
.listright p {
    width: 52px;
    display:inline-block;
    font-size: 14px;
    text-align: center;
    line-height: 30px;
}
li{
	list-style: none;
}
img{
	width: 60px;
    height: 60px;
}
</style>
</head>
<body>

<!-- <script type="text/javascript">
$(function(){
   var openid = '${openid}';
   alert(openid);
	$.ajax({
		type:"get",
		url:"/buyer/findAll?openid="+openid,
		success:function(data){
			alert("开始查找所有订单");
			var allorder = $("#allOrder");debugger;
			for(var i in data){
				var orderId = i.orderId;
				var status=i.orderStatus;
				switch (status) {
				case "0":
					status = "新订单";
					break;
				case "1":
					status = "已取消";
					break;
				default:
					status = "已完成";
				}
				var payStatus = i.payStatus;
				
			}
			for(var key in data){
				var head= key.split('-');
				var orderId =head[0];
				var status = head[1];
				var amount= head[2];
				var quantity=0;
				if(status =="0"){
					status ="未支付";
				}else{
					status="已支付";
				}
				 detail = data[key];
				 allorder.append('<div class="card">'+
							'<div class="card-header">'+
							'<div>订单编号:${item.orderId}</div>'+
							'<div>状态:</div><a href="/buyer/findOrder?id='+orderId+'">查看详情</a>'+
							'</div>'+
							'<div class="card-body">'+
							'</div>'+
						'</div>');
				allorder.append('<div style="border:1px solid red;margin: 0 0 100px 0 ;"><div  class="orderTitle"><h3>订单编号：  '+orderId+'</h3></div><div class="orderTitle" style="float:right;"><h4>支付状态：'+status+'</h4><a href="/buyer/findOrder?id='+orderId+'">查看详情</a></div><div><ul id='+orderId+'></ul></div><div id="'+orderId+'amount"></div></div>');
				for(var i=0;i<detail.length;i++){
					quantity=quantity+detail[i].productQuantity;
					$("#"+orderId).append('<li><div class="uppic"> <img src='+detail[i].productIcon+'> </div><div class="listtitle"><h1>'+detail[i].productName+'</h1> <h2>￥'+detail[i].productPrice+'</h2></div><div class="listright"><p>数量：'+detail[i].productQuantity+'</p></div></li>');
				}
				$("#"+orderId+"amount").append('<h4>总金额：'+amount+'</h4><h5>数量：'+quantity+'</h5>');
		  	}
		}
	});
})

</script>
	 --><%-- <div id="allOrder">
	<c:forEach items="${orders}" var="item">
		<div class="card">
			<div class="card-header">
			<div>订单编号:${item.orderId}</div>
			<div>状态:</div><a href="/buyer/findOrder?id='+orderId+'">查看详情</a>
			</div>
			<div class="card-body">
			<c:forEach items="${item.detailList}" var="detail">
			
			</c:forEach>
			</div>
		</div>
	</c:forEach>
	</div> --%>
	<c:forEach items="${orders}" var="item">
	<div class="card">
              <div class="card-header">
              	<div>订单编号:${item.orderId}</div>
              	<div>状态:
              	<c:if test="${item.orderStatus == 0}"><a href="/buyer/cancel?openid=${item.buyerOpenid}&orderid=${item.orderId}">取消订单</a></c:if>
              	
              	<c:if test="${item.orderStatus == 2}">
              	已取消
              	</c:if>
              	<c:if test="${item.orderStatus ==1}">
              	已完成
              	</c:if>
              	</div>
				<a href="/buyer/findOrder?id=${item.orderId}">查看详情</a>
              </div>
              <div class="card-body">
                <table class="table">
                  <thead>
                    <tr>
                      <th>菜品的图片</th>
                      <th>名字</th>
                      <th>价格</th>
                      <th>数量</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${item.detailList}" var="detail">
                    <tr>
                      <td><img src="${detail.productIcon}"></td>
                      <td>${detail.productName}</td>
                      <td>${detail.productPrice}</td>
                      <td>${detail.productQuantity}</td>
                    </tr>
                 </c:forEach>
                  </tbody>
                </table>
                <div>总金额：￥ ${item.orderAmount}</div>
              </div>
            </div>
             </c:forEach>
</body>
</html>