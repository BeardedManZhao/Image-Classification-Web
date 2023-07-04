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
    <title>û����Դ</title>
</head>
<body>
<div>
    <img id="logo" width="120" height="90" src="image/Logo.svg" alt="Image-Classification-Web">
    <h2 id="web-title">��ѽѽ!!!!</h2>
    <hr>
    <p>���� <%=isLogIn ? user.name() : "ͼ�����ϵͳ�ķ�����"%>, �˴�û���ҵ���ص���Դ!!!!!</p>
</div>

<a class="button" href="index.jsp">������ҳ</a>
</body>
</html>
