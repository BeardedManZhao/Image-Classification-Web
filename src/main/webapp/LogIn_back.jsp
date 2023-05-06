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
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>初始化或进入您的空间</title>
    <style>
        html {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

        body {
            width: 100%;
            height: 100%;
            font-family: 'Open Sans', sans-serif;
            margin: 0;
            background-color: #4A374A;
        }

        #login {
            position: relative;
            top: 50%;
            left: 50%;
            margin: -150px 0 0 -150px;
            width: 300px;
            height: 300px;
        }

        #login h1 {
            color: #fff;
            text-shadow: 0 0 10px;
            letter-spacing: 1px;
            text-align: center;
        }

        h1 {
            font-size: 2em;
            margin: 0.67em 0;
        }

        input {
            width: 278px;
            height: 18px;
            margin-bottom: 10px;
            outline: none;
            padding: 10px;
            font-size: 13px;
            color: #fff;
            text-shadow: 1px 1px 1px;
            border-top: 1px solid #312E3D;
            border-left: 1px solid #312E3D;
            border-right: 1px solid #312E3D;
            border-bottom: 1px solid #56536A;
            border-radius: 4px;
            background-color: #2D2D3F;
        }

        #but1 {
            width: 145px;
            min-height: 20px;
            display: block;
            background-color: #4a78d4a1;
            border: 1px solid #3762bc;
            color: #fff;
            padding: 9px 14px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;
            position: absolute;
            right: -1px;
        }

        #but2 {
            width: 145px;
            min-height: 20px;
            display: block;
            background-color: #4a78d4a1;
            border: 1px solid #3761bcc2;
            color: #fff;
            padding: 9px 14px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;
            position: absolute;
        }
    </style>
</head>
<link rel="stylesheet" type="text/css" href="css/backTheme.css">

<body>
<div id="login">
    <h1>Image Classification</h1>
    <form method="post" action="<%=Conf.LOGIN_SERVLET%>" onsubmit="return checkData()">
        <label for="userName">
            <input type="text" required="required" placeholder="用户名" name="userName" id="userName"/>
        </label>
        <label for="password">
            <input type="password" required="required" placeholder="密码" name="password" id="password"/>
        </label>
        <button id="but2" type="button" onmouseout="mouseOut('but2')" onmouseover="mouseIn('but2')"
                onclick="back()">离开页面
        </button>
        <button id="but1" type="submit" onmouseout="mouseOut('but1')" onmouseover="mouseIn('but1')">进入空间</button>
    </form>
</div>
<script>
    function mouseIn(c) {
        const x = document.getElementById(c);
        x.style.backgroundColor = '#4a78d4';
    }

    function mouseOut(c) {
        const x = document.getElementById(c);
        x.style.backgroundColor = "#4a78d4a1";
    }

    function back() {
        history.back();
    }

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
</body>

</html>