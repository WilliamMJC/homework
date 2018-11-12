<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/layui/css/layui.css" media="all" />
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/css/font_eolqem241z66flxr.css" media="all" />
<title>Insert title here</title>
</head>
<body class="childrenBody">
	<form class="layui-form" action="${pageContext.servletContext.contextPath}/sysback/user/addTeacher" method="post">
		<div class="layui-form-item">
			<label class="layui-form-label">教师登录名</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input loginName" name="loginName" lay-verify="required" placeholder="请输入用户名">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">学校</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input schoolName" name="schoolName" lay-verify="required" placeholder="请输入个人邮箱">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">个人邮箱</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input privEmail" name="privEmail" lay-verify="required|email" placeholder="请输入个人邮箱">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">作业邮箱</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input email" name="email" lay-verify="required|email" placeholder="请输入作业邮箱">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">邮箱授权码</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input emailPwd" name="emailPwd" lay-verify="required" placeholder="请输入授权码">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">登录密码</label>
			<div class="layui-input-block">
				<input type="password" class="layui-input loginPwd" name="loginPwd" lay-verify="required" placeholder="请输入密码">
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="addTeacher">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
		</div>
	</form>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/static/sysback/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/static/sysback/js/newsAdd.js"></script>
</body>
</html>