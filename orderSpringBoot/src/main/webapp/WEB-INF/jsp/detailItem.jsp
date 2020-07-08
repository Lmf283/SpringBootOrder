<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品详情界面</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/style.min.css" rel="stylesheet">
<style type="text/css">
.addnum {
    background: url(/images/jiah.png) no-repeat;
    background-size: contain;
    height: 40px;
    display: inline-block;
    float: right;
    width: 40px;
    }
#price{
color: red;
}
img {
	width: 100%;
	height: 400px;
}
.pro-price{
display:inline-block;
float: left;
padding-top: 15px;
}

</style>
<script type="text/javascript" src="/js/jquery.min.js"></script>
</head>
<script type="text/javascript">
function add(obj) {
	var domain = document.domain;

	var id =$(obj).attr("name");
	var openid="${openid}";
	$.ajax({
		type:"post",
		url:"/cart/add?openid="+openid+"&id="+id,
		success:function(){
			window.location.href="/buyer/list?openid="+openid;
		}
	})
}

</script>
<body>
	<div class="card">
		<div class="card-header"><img src="${product.productIcon}"></div>
		<div class="card-body">
			<div class="pro-name"><h4>${product.productName}</h4></div>
			<div class="pro-price"><h4  id="price">￥ ${product.productPrice} 元</h4> 
				
			</div>
			<div name="${product.productId}" class="addnum" onclick="add(this)"></div>
		</div>
	</div>
	<div class="card">
	<div class="card-header">
		<h5><span>菜品的描述:</span></h5>
	</div>
	<div class="card-body">
		
		<div>${product.productDescription}</div>
	</div>
	</div>
</body>
</html>