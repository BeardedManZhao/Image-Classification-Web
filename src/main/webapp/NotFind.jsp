<%@ page import="zhao.core.user.User" %>
<%@ page import="zhao.task.VoidTask" %>
<%@ page import="zhao.core.user.OrdinaryUser" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/5/5
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" %>

<%
    final User user = User.checkCookieUser(request, response, VoidTask.VOID_TASK);
    final boolean isLogIn = user != OrdinaryUser.DEFAULT_USER;
%>

<html>
<head>
    <link href='image/Logo.svg' rel='SHORTCUT ICON'/>
    <link rel="stylesheet" type="text/css" href="css/Theme.css">
    <title>没有资源</title>
</head>
<body>
<div>
    <img id="logo" width="120" height="90" src="image/Logo.svg" alt="Image-Classification-Web">
    <h2 id="web-title">哎呀呀!!!!</h2>
    <hr>
    <p>您好 <%=isLogIn ? user.name() : "图像分类系统的访问者"%>, 此处没有找到相关的资源!!!!!</p>
</div>

<a class="button" href="index.jsp">返回主页</a>
</body>
</html>
