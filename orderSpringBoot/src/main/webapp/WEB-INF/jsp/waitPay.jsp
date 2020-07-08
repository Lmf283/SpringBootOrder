<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/js/jquery.min.js"></script>
<title>付款页面</title>
</head>
<script type="text/javascript">
$(function(){
	var openid='abcd';
	alert("进入支付页面");
	debugger;
	$.ajax({
		type:"get",
		url:"/buyer/waitPay?openid="+openid,
		success:function(data){
			alert("返回未支付的订单列表");
		}
	});
});
</script>
<body>
	<div class="table-responsive">
  <table class="table">
  	<thead>
      <tr>
        <th>订单id</th>
        <th>图片</th>
        <th>总金额</th>
        <th>数量</th>
      </tr>
    </thead>
    <tbody id="cartTable" name="itemList">
    </tbody>
  </table>

</body>
</html>