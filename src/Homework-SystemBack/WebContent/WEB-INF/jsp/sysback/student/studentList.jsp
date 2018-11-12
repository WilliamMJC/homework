<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta charset="utf-8">
	<title>学生列表</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/css/font_eolqem241z66flxr.css" media="all" />
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/css/news.css" media="all" />
</head>
<body class="childrenBody">
	<blockquote class="layui-elem-quote news_search">
		<div class="layui-inline">
		    <div class="layui-input-inline">
		    	<input type="text" value="" placeholder="请输入关键字" class="layui-input search_input">
		    </div>
		       <a class="layui-btn search_btn layui-btn-small">查询</a>
		</div>
		<div class="layui-inline" style='float:right'>
			<a class="layui-btn layui-btn-danger batchDel layui-btn-small">删除</a>
		</div>
		<div class="layui-inline" style='float:right'>
			<a class="layui-btn layui-btn-normal newsAdd_btn layui-btn-small">添加学生</a>
		</div>	
	</blockquote>
	<div class="layui-form news_list">
	  	<table class="layui-table">
		    <colgroup>
				<col width="4%">
				<col width="10%">
				<col width="15%">
				<col width="15%">
				<col width="14%">
				<col width="14%">
				<col width="6%">
				<col width="19%">
		    </colgroup>
		    <thead>
				<tr>
					<th><input type="checkbox" name="" lay-skin="primary" lay-filter="allChoose" id="allChoose"></th>
					<th style="text-align:left;">学生名称</th>
					<th>学号</th>
					<th>邮箱</th>
					<th>最近登录时间</th>
					<th>注册时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr> 
		    </thead>
		    <tbody class="news_content"></tbody>
		</table>
	</div>
	<div id="page"></div>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/static/sysback/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/static/sysback/js/studentList.js"></script>
</body>
</html>