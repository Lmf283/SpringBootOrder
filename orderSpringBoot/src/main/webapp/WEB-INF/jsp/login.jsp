<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>登录页面 - 点餐后台管理系统</title>
<link rel="icon" href="favicon.ico" type="image/ico">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/materialdesignicons.min.css" rel="stylesheet">
<link href="/css/style.min.css" rel="stylesheet">
<link href="/css/messenger.css" rel="stylesheet">
<link href="/css/messenger-theme-future.css" rel="stylesheet">
<script type="text/javascript" src="/js/messenger.min.js"></script>
<script type="text/javascript" src="/js/messenger-theme-future.js"></script>
<style>
.lyear-wrapper {
    position: relative;
}
.lyear-login {
    display: flex !important;
    min-height: 100vh;
    align-items: center !important;
    justify-content: center !important;
}
.login-center {
    background: #fff;
    min-width: 38.25rem;
    padding: 2.14286em 3.57143em;
    border-radius: 5px;
    margin: 2.85714em 0;
}
.login-header {
    margin-bottom: 1.5rem !important;
}
.login-center .has-feedback.feedback-left .form-control {
    padding-left: 38px;
    padding-right: 12px;
}
.login-center .has-feedback.feedback-left .form-control-feedback {
    left: 0;
    right: auto;
    width: 38px;
    height: 38px;
    line-height: 38px;
    z-index: 4;
    color: #dcdcdc;
}
.login-center .has-feedback.feedback-left.row .form-control-feedback {
    left: 15px;
}
</style>
</head>
  
<body>
<div class="row lyear-wrapper">
  <div class="lyear-login">
    <div class="login-center">
      
      <form action="/user/login" method="post" onsubmit="return check()">
        <div class="form-group has-feedback feedback-left">
          <input type="text" placeholder="请输入您的用户名" class="form-control" name="userName" id="userName" />
          <span class="mdi mdi-account form-control-feedback" aria-hidden="true"></span>
        </div>
        <div class="form-group has-feedback feedback-left">
          <input type="password" placeholder="请输入密码" class="form-control" id="passWord" name="passWord" />
          <span class="mdi mdi-lock form-control-feedback" aria-hidden="true"></span>
        </div>
        <div class="form-group">
          <button class="btn btn-block btn-primary" type="submit" >立即登录</button>
          <h4><a href="/user/register">去注册</a></h4>
        </div>
      </form>
      <hr>
      
    </div>
  </div>
</div>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
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
		if(userName==""||passWord ==""){
			alert("用户名或密码为空");
			return false;
		}
		return true;
	}
</script>
</body>

</html>