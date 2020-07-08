<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
<link href="/css/materialdesignicons.min.css" ref="stylesheet">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/style.min.css" rel="stylesheet">
<link href="/css/messenger.css" rel="stylesheet">
<link href="/css/messenger-theme-future.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/messenger.min.js"></script>
<script type="text/javascript" src="/js/messenger-theme-future.js"></script>
<style type="text/css">
 .register{
 	margin:0 auto;
 	text-align: center;
 	background-color: #fff;
 }
 .form-control{
	padding-left: 38px;
    padding-right: 12px;
 }
 .btn{
	 background-color: #33cabb;
	border-color: #33cabb;
	color: #fff !important;
	width: 48%;
	margin-top: 14px;
 }
</style>
</head>
<body>
	<div class="register">
			<div class="card-header"><h4>管理员注册表单</h4></div>
              <div class="card-body">
              
                <form class="form-horizontal"  id="regisForm" action="/user/regist" method="post" onsubmit="return check()">
                  <div class="form-group">
                    <label class="col-md-3 control-label" for="example-hf-email">用户名</label>
                    <div class="col-md-7">
                      <input class="form-control" type="text" id="userName" name="userName" placeholder="请输入用户名.." />
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-md-3 control-label" for="example-hf-password">密码</label>
                    <div class="col-md-7">
                      <input class="form-control" type="password" id="passWord" name="passWord" placeholder="请输入密码.." />
                    </div>
                  </div>
                  <div class="form-group">
                    	<label class="col-md-3 control-label" for="example-hf-password">电话号码</label>
                    <div class="col-md-7">
                      <input class="form-control" type="tel" id="phone" name="phone" placeholder="请输入电话号码.." />
                    </div>
                  </div>
                  <div class="form-group">
                      <button class="btn" type="submit" >
                      	注册</button>
                      <div><a href="/user/login">已有账号请登陆</a></div>
                  </div>
                </form>

              </div>
    </div>

<script type="text/javascript">
	$._messengerDefaults = {
	    extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	}
	var msg = "${result.msg}";
	if(msg != ""){
		$.globalMessenger().post({
			message: msg,
			type: 'info',
			ideAfter: 1,
		    showCloseButton: true,
		});
	}
	function check() {
		var userName = $("#userName").val();
		var passWord = $("#passWord").val();
		var phone = $("#phone").val();
		var len = phone.length;
		if(userName ==''){
			$.globalMessenger().post({
				message: '用户名不能为空',
				type: 'error',
                hideAfter: 0.5,
                showCloseButton: true,
			});
			$("#userName").focus();
			return false;
		}else if (passWord.length < 6) {
			$.globalMessenger().post({
				message: "密码不能小于6位数",
				type: 'error',
				ideAfter: 1,
                showCloseButton: true,
			});
			$("#passWord").focus();
			return false;
		}
		else if (phone.length<11 || phone.length>11) {
			$.globalMessenger().post({
				message: "手机号码的位数"+len,
				type: 'error',
				ideAfter: 1,
                showCloseButton: true,
			});
			$("#phone").focus();
			return false;
		}
		return true;
	}
</script>
</body>
</html>