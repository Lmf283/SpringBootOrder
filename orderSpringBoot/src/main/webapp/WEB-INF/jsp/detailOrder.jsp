<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/style.min.css" rel="stylesheet">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<title>订单详情</title>

<style type="text/css">
img {
width: 40px;
height: 40px;
}
.info{
	margin-top: 20px;
}
</style>
<script type="text/javascript" src="/js/jquery.min.js"></script>

</head>
<body>

	<%-- <div class="content">
		<div class="head">
			<a href="javascript:history.go(-1)">返回</a>
			<div>订单编号：${orderDTO.orderId }</div>
			<div id="status"></div>
		</div>
		<div class="midden">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>菜品id</th>
						<th>菜品名称</th>
						<th>菜品图片</th>
						<th>价格</th>
						<th>数量</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${orderDTO.detailList}" var="item">
						<tr>
							<td>${item.productId}</td>
							<td>${item.productName}</td>
							<td><img  src="${item.productIcon}"/></td>
							<td>${item.productPrice}</td>
							<td>${item.productQuantity}</td>
						</tr>	
					</c:forEach>
				</tbody>
			</table>
			<div id="amount">订单的总金额：${orderDTO.orderAmount }</div>
			<div>买家联系方式：${orderDTO.buyerPhone }</div>
			<div>买家名称：${orderDTO.buyerName }</div>
			<div>地址：${orderDTO.buyerAddress }</div>
		</div>
		<div class="footer">
			<div>订单的创建时间：${orderDTO.createTime }</div>
		</div>
	</div>
			 --%><div class="card">
              <div class="card-header">
              	<div>订单编号:${orderDTO.orderId}</div>
              	<div>状态:
              	<c:if test="${orderDTO.orderStatus == 0}">新订单</c:if>
              	
              	<c:if test="${orderDTO.orderStatus == 1}">
              	已取消
              	</c:if>
              	<c:if test="${orderDTO.orderStatus ==2}">
              	已完成
              	</c:if>
              	</div>
              </div>
              <div class="card-body">
                <table class="table">
                  <thead>
                    <tr>
                      <th>菜品的图片</th>
                      <th>名字</th>
                      <th>价格</th>
                      <th>数量</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${orderDTO.detailList}" var="detail">
                    <tr>
                      <td><img src="${detail.productIcon}"></td>
                      <td>${detail.productName}</td>
                      <td>${detail.productPrice}</td>
                      <td>${detail.productQuantity}</td>
                      <td><a href="/product/detail?openid=${orderDTO.buyerOpenid}&id=${detail.productId}">查看菜品信息</a></td>
                    </tr>
                 </c:forEach>
                  </tbody>
                </table>
                <div class="info">总金额：￥<span style="color: red;"> ${orderDTO.orderAmount}</span></div>
                <div class="info">买家联系方式：${orderDTO.buyerPhone }</div>
				<div class="info">买家名称：${orderDTO.buyerName }</div>
				<div class="info">地址：${orderDTO.buyerAddress }</div>
				<div class="info">订单的创建时间：${orderDTO.createTime }</div>
              </div>
            </div>
</body>
</html>