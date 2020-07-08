<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta  charset=UTF-8">
<title>头部信息</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/materialdesignicons.min.css" rel="stylesheet">
<link href="/css/style.min.css" rel="stylesheet">
<link href="/css/messenger.css" rel="stylesheet">
<link href="/css/messenger-theme-future.css" rel="stylesheet">
</head>
<body>
<header class="lyear-layout-header" >
      <nav class="navbar navbar-default">
        <div class="topbar">
          <div class="topbar-left">
            <div class="lyear-aside-toggler">
              <span class="lyear-toggler-bar"></span>
              <span class="lyear-toggler-bar"></span>
              <span class="lyear-toggler-bar"></span>
            </div>
            <span class="navbar-page-title"> 点餐系统的后台首页 </span>
          </div>
          
          <ul class="topbar-right">
            <li class="dropdown dropdown-profile">
              <a href="javascript:void(0)" data-toggle="dropdown">
                <span>用户 <span class="caret"></span></span>
              </a>
              <ul class="dropdown-menu dropdown-menu-right">
                <li> <a id="uInfo" href="/user/userInfo?op=userInfo" ><i class="mdi mdi-account"></i> 个人信息</a> </li>
                <li> <a  href="/user/userInfo?op=editPsw"><i class="mdi mdi-lock-outline"></i> 修改密码</a> </li>
                <li> <a  href="/user/login"><i class="mdi mdi-logout-variant"></i> 退出登录</a> </li>
              </ul>
            </li>
          </ul>
          
        </div>
      </nav>
      
    </header>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/js/main.min.js"></script>
<script type="text/javascript" src="/js/messenger.min.js"></script>
<script type="text/javascript" src="/js/messenger-theme-future.js"></script>
<script type="text/javascript">
$._messengerDefaults = {
	    extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
	}
</script>
</body>
</html>