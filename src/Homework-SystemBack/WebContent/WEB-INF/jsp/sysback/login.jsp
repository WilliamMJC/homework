<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>收发作业管理登录</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/static/sysback/favicon.ico" type="image/x-icon"/>

    <!--layui-->
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/libs/layui/css/layui.css">

    <link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/static/sysback/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/css/font.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/css/xadmin.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/static/sysback/css/style.css">


    <script src="${pageContext.servletContext.contextPath}/static/sysback/libs/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/static/sysback/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/static/sysback/js/xadmin.js"></script>


</head>
<body>

<!-- particles.js container -->
<div id="particles-js"></div>


<div class="login">

    <form class="layui-form">
        <div class="layui-form-item">
            <div class="message">收发作业管理登录</div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-black">
                <input type="text" name="username" required lay-verify="username" placeholder="请输入帐号" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-black">
                <input type="password" name="password" required lay-verify="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
            <div></div>
        </div>


        <!-- <div class="layui-form-item">
            <div class="layui-input-black">
                <input type="text" name="capval" class="layui-input" required lay-verify="required" placeholder="验证码" maxlength="4" minlength="4"/>
                <img id="captcha_img" alt="验证码" src="/admin/login/captcha" onclick="this.src='/admin/login/captcha?'+Math.random()">
            </div>
        </div> -->
        <!--<div class="layui-form-item">-->
        <!--<div class="layui-input-black">-->
        <!--<input type="checkbox" name="" title="记住密码" lay-skin="primary">-->
        <!--</div>-->
        <!--</div>-->
        <button class="layui-btn" lay-submit="" lay-filter="login" style="width:100%; background-color: #393d47">登录
        </button>
    </form>
</div>

<script>

    //一般直接写在一个js文件中
    layui.use(['layer', 'form'], function () {
        var layer = layui.layer
            , form = layui.form;

        var $ = layui.$;

        form.verify({
            username: function (value) {
                if (value.length < 4) {
                    return '帐号必须5到16位';
                }
            }
            , password: [/(.+){5,12}$/, '密码必须5到12位']
        });

        //监听提交
        form.on('submit(login)', function (data) {

            console.log(JSON.stringify(data.field));
            $.ajax({
                type: "POST",
                url: '${pageContext.servletContext.contextPath}/sysback/user/login',
                data: data.field,
                dataType : "text",
                success: function (data) {

                    if ("success"=== data) {
                        layer.msg('登录成功', function () {
                            location.href = '${pageContext.servletContext.contextPath}/sysback/user/index'
                        });
                    } else {
                        layer.msg("登录名或密码错误");
                        

                    }

                    console.log(data);
                },
                error: function (data, textStatus, errorThrown) {
                    layer.msg("登录失败");
                    /* $("#captcha_img").attr("src", '/admin/login/captcha?'+Math.random()); */
                }
            });

            return false;
        });


    });

</script>
</body>

<!--一个好看的动画背景-->
<script src="${pageContext.servletContext.contextPath}/static/sysback/libs/particles/particles.js"></script>
<script src="${pageContext.servletContext.contextPath}/static/sysback/libs/particles/app.js"></script>
</html>