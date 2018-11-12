<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>首页--收发作业后台管理</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/css/font_eolqem241z66flxr.css" media="all" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/css/main.css" media="all" />
</head>
<body class="childrenBody">
	<div class="panel_box row">
		<div class="panel col">
			<a href="javascript:;" data-url="page/message/message.html">
				<div class="panel_icon" style="background-color:#009688;">
					<i class="layui-icon" data-icon="&#xe613;">&#xe613;</i>
				</div>
				<div class="panel_word newMessage">
					<span></span>
					<cite>学生总数</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/user/allUsers.html">
				<div class="panel_icon" style="background-color:#FF5722;">
					<i class="iconfont icon-dongtaifensishu" data-icon="icon-dongtaifensishu"></i>
				</div>
				<div class="panel_word userAll">
					<span></span>
					<cite>新增用户</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/user/allUsers.html">
				<div class="panel_icon" style="background-color:#009688;">
					<i class="layui-icon" data-icon="&#xe613;">&#xe613;</i>
				</div>
				<div class="panel_word userAll">
					<span></span>
					<cite>教师总数</cite>
				</div>
			</a>
		</div>
		<%-- <div class="panel col">
			<a href="javascript:;" data-url="page/img/images.html">
				<div class="panel_icon" style="background-color:#5FB878;">
					<i class="layui-icon" data-icon="&#xe64a;">&#xe64a;</i>
				</div>
				<div class="panel_word imgAll">
					<span></span>
					<cite>教师总数</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/news/newsList.html">
				<div class="panel_icon" style="background-color:#F7B824;">
					<i class="iconfont icon-wenben" data-icon="icon-wenben"></i>
				</div>
				<div class="panel_word waitNews">
					<span></span>
					<cite>作业总数</cite>
				</div>
			</a>
		</div>
		<div class="panel col max_panel">
			<a href="javascript:;" data-url="page/news/newsList.html">
				<div class="panel_icon" style="background-color:#2F4056;">
					<i class="iconfont icon-text" data-icon="icon-text"></i>
				</div>
				<div class="panel_word allNews">
					<span></span>
					<em>课程总数</em>
					<cite>课程总数</cite>
				</div>
			</a>
		</div> --%>
	</div>
	<div class="row">
		<div class="sysNotice col">			
		</div>
		<div class="sysNotice col">
			<blockquote class="layui-elem-quote title">最新作业<i class="iconfont icon-new1"></i></blockquote>
			<table class="layui-table" lay-skin="line">
				<colgroup>
					<col>
					<col width="110">
				</colgroup>
				<tbody class="hot_news"></tbody>
			</table> 
		</div>
		<div class="sysNotice col">
			<blockquote class="layui-elem-quote title">最新用户<i class="iconfont icon-new1"></i></blockquote>
			<table class="layui-table" lay-skin="line">
				<colgroup>
					<col>
					<col width="110">
				</colgroup>
				<tbody class="hot_news"></tbody>
			</table> 
		</div>
	</div> 

	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/static/sysback/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/static/sysback/js/main.js"></script>
</body>
</html>