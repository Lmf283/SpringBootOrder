<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>提交订单页面</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/style.min.css" rel="stylesheet">
<link href="/css/materialdesignicons.min.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.js"></script>
<style type="text/css">
 .table img{
 	width:80px;
 	height: 60px;
 }
</style>
</head>
<body>
<script type="text/javascript">
$(function(){
	var amount=0;
	var openid = "${openid}";
	$.ajax({
		type:"get",
		async:false,
		url:"/cart/getCart?openid="+openid,
		success:function(data){
			var cartList =$('#cartTable')
			
			for(var i=0;i<data.length;i++){
				cartList.append('<tr><td>'+data[i].id+'</td><td><img src='+data[i].icon+'  /></td><td>'+data[i].name+'</td><td>'+data[i].price+'</td><td>'+data[i].quantity+'</td></tr>');
				amount=amount+data[i].price*data[i].quantity;
			}
			$('.table tr').find('th:eq(0)').hide();
			$('.table tr').find('td:eq(0)').hide();
			$('#items').val(JSON.stringify(data));
			$('#openid').val(openid);
			debugger;
			$('#amount').innerText("总金额：￥"+amount);
		}
	});	
});


</script>
<div id="loading" style="color:#ffffff; display:none; position:absolute; top:80px; left:3em;"></div><div id="loading" style="color:#ffffff; display:none; position:absolute; top:80px; left:3em;"></div>
<form action="/buyer/create" method="post">
<div>
<h3 style="text-align: center; ">待付款表单</h3>
</div>
	<div>
		<label>姓名：</label>
		<input type="text" name="name"></input>
	</div>
	<div>
		<label>电话：</label>
		<input type="tel" name="phone"></input>
	</div>
	<div>
		<label>地址：</label>
		<input type="text" name="address"></input>
	</div>
	<div>
	  	<input type="hidden" id="openid" name="openid"></input>
		<input type="hidden" id="items" name="items"></input>
	</div>
 
<div>
<div class="table-responsive">
  <table class="table">
  	<thead>
      <tr>
        <th>菜品id</th>
        <th>图片</th>
        <th>菜品名</th>
        <th>价格</th>
        <th>数量</th>
      </tr>
    </thead>
    <tbody id="cartTable" name="itemList">
    </tbody>
  </table>
 </div>
 <div>
 	<label id="amount"></label>
 	
 </div>
<input type="submit" name="submit" value="提交订单" ></input>

</div>

</form>

</body>
</html>