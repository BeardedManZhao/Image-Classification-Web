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
    <title>û����Դ</title>
</head>
<body>
<div>
    <h2>��ѽѽ!!!!</h2>
    <hr>
    <p>���� <%=isLogIn ? user.name() : "ͼ�����ϵͳ�ķ�����"%>, �˴�û���ҵ���ص���Դ!!!!!</p>
</div>
</body>
</html>
