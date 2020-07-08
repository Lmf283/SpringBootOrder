<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<title>个人信息-管理员管理</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/materialdesignicons.min.css" rel="stylesheet">
<link href="/css/style.min.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery.min.js"></script>
</head>
<body>
<script type="text/javascript">
	$(function () {
		var userInfo=${userInfo};
		$("#username").val(userInfo.userName);
		$("#password").val(userInfo.passWord);
		$("#phone").val(userInfo.tel);
		
		
	});
</script>
<div>
<div class="card">
	<div class="card-header">
	用户个人信息
	</div>
	 <div class="card-body">
		 <form method="get" href="/user/index" class="site-form">
	          <div class="form-group">
	            <label for="username">用户名</label>
	            <input type="text" class="form-control" name="username" id="username"  disabled="disabled">
	          </div>
	          <div class="form-group">
	            <label for="password">密码</label>
	            <input type="text" class="form-control" name="password" id="password"  disabled="disabled">
	          </div>
	          <div class="form-group">
	            <label for="phone">联系方式</label>
	            <input type="text" class="form-control" name="phone" id="phone"  disabled="disabled">
	          </div>
	          <button type="button" class="mdi mdi-keyboard-return" id="backStep" onclick="javascript:history.go(-1)">返回</button>
        </form>
 </div>
</div>
	
</div>
		
</body>
</html>