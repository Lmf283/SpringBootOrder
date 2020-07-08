<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码-管理员管理</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/materialdesignicons.min.css" rel="stylesheet">
<link href="/css/style.min.css" rel="stylesheet">
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>

</head>
<body>
<script type="text/javascript">
$(function () {
	var userInfo=${userInfo};
	alert("进入测试");
	$("#old-password").val(userInfo.passWord);
	$("#ID").val(userInfo.userId);
});
</script>
<script type="text/javascript">

function check(){
	var oldPsw = $("#old-password").val();
	var newPsw = $("#new-password").val();
	if(newPsw == oldPsw){
		alert("旧密码和新密码一致，请重新输入！");
		$("#new-password").focus();
		return false;
	}
	var confirmPsw = $("#confirm-password").val();
	if(newPsw != confirmPsw){
		alert("输入的两次密码不一致");
		$("#confirm-password").focus();
		return false;
	}
	return true;
}
</script>
		<div class="card">
			<div class="card-header">
			用户修改密码
			</div>
              <div class="card-body">
                
                <form method="post" action="/user/editPsw" class="site-form" onsubmit="return check();">
                <input type="hidden" id="ID" name="ID">
                  <div class="form-group">
                    <label for="old-password">旧密码</label>
                    <input type="text" class="form-control" name="oldpwd" id="old-password" placeholder="输入账号的原登录密码">
                  </div>
                  <div class="form-group">
                    <label for="new-password">新密码</label>
                    <input type="password" class="form-control" name="newpwd" id="new-password" placeholder="输入新的密码">
                  </div>
                  <div class="form-group">
                    <label for="confirm-password">确认新密码</label>
                    <input type="password" class="form-control" name="confirmpwd" id="confirm-password" placeholder="请确认新密码">
                  </div>
                  <button type="submit" class="btn btn-primary">修改密码</button>
                </form>
              </div>
            </div>
</body>
</html>