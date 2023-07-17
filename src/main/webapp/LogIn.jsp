<%@ page import="zhao.core.user.User" %>
<%@ page import="zhao.task.VoidTask" %>
<%@ page import="zhao.core.user.OrdinaryUser" %>
<%@ page import="zhao.Conf" %>
<%@ page import="zhao.task.ToHome" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/5/4
  Time: 12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    // 检查当前用户是否已经登录，若登录就跳转到主页 反之则 保持现在的状态
    final User user = User.checkCookieUser(request, response, VoidTask.VOID_TASK);
    if (!(user == OrdinaryUser.DEFAULT_USER)) {
        // 非 def 代表当前用户已经登录
        ToHome.TO_HOME.run(request, response);
        return;
    }
    // 如果没有跳转就代表当前用户没有登录，在这里对当前页面中的所有数据进行处理
    final String loginS = Conf.LOGIN_SERVLET;
%>

<style>
    body {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    form {
        align-content: center;
    }

    #b_div {
        width: 50%;
        height: 50%;
        margin: 0 auto;
        text-align: center;
        color: #3a3838;
        opacity: 70%;
        transition: all 0.7s;
    }

    #b_div:hover {
        opacity: 100%;
    }

    #b_div div {
        line-height: 50%;
        margin: 5%;
    }
</style>

<html>
<head>
    <link href='image/Logo.svg' rel='SHORTCUT ICON'/>
    <link rel="stylesheet" type="text/css" href="css/terminal.css">
    <link rel="stylesheet" type="text/css" href="css/Theme.css">
    <style>
        div {
            color: #BFDDE0;
        }
    </style>
    <title>初始化或进入您的空间</title>
</head>
<body>
<div id="b_div" class="terminal">
    <div>
        <img id="logo" width="120" height="90" src="image/Logo.svg" alt="Image-Classification-Web">
        <h2 id="web-title">进入您的空间</h2>
    </div>
    <div>
        <form onsubmit="return checkData()" method="post" action="<%=loginS%>">
            <label>
                请输入用户昵称：
                <input id="username" name="userName" type="text" alt="输入用户昵称"/>
            </label>
            <br>
            <label>
                请输入用户密码：
                <input id="password" name="password" type="password" alt="输入用户密码">
            </label>
            <br>
            <button>进入空间</button>
        </form>
    </div>
</div>
</body>
</html>

<script>
    const re = new RegExp(".+?:.+");

    function checkData() {
        let un = document.getElementById("username").value
        let pa = document.getElementById("password").value
        if (un.length >= 4 && pa.length >= 4) {
            if ((!un.match(re)) || (!pa.match(re))) {
                return true;
            } else {
                alert("您的用户名和密码不能包含字符[:].")
                return false;
            }
        } else {
            alert("您的用户名与密码要长度要大于等与 4.")
            return false;
        }
    }
</script>
