<%@ page import="zhao.core.user.User" %>
<%@ page import="zhao.task.ToFZF" %>
<%@ page import="zhao.Conf" %>
<%@ page import="zhao.task.VoidTask" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/5/7
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    // 检查当前用户是否为管理者 如果不是管理者就直接回复 404
    final User user = User.checkCookieUser(request, response, VoidTask.VOID_TASK);
    if (!user.isManager()) {
        ToFZF.TO_FZF.run(request, response);
        return;
    }
%>

<html>
<head>
    <link href='image/Logo.svg' rel='SHORTCUT ICON'/>
    <link rel="stylesheet" type="text/css" href="css/Theme.css">
    <title>配置网站</title>
</head>
<body>
<div id="title">
    <img id="logo" width="120" height="90" src="image/Logo.svg" alt="Image-Classification-Web">
    <h2 id="web-title">你好 <%=user.name()%> 在这里进行WEB页面的配置</h2>
</div>

<div id="data">
    <form action="<%=Conf.CONF_UPDATE_SERVLET%>" method="post">
        <label>
            允许的最大空间数量：
            <input type="number" value="<%=Conf.USER_MAX_COUNT%>" name="maxSpaceNum" alt="允许的最大空间数量设置"
                   placeholder="运行时能够容纳的最大空间数量">
        </label>
        <br>
        <label>
            图分类系统空间资源：
            <input type="text" value="<%=Conf.LOGIN%>" name="loginJsp" alt="登录页面对应的资源"
                   placeholder="进入空间页面对应的资源名称">
        </label>
        <br>
        <label>
            网站状态：
            running<input name="web_status" type="radio" value="running" checked>
            closed <input name="web_status" type="radio" value="closed">
        </label>
        <br>
        <button type="button" onclick="window.history.back()">退出配置页面</button>
        <button type="submit">保存配置信息</button>
    </form>
    <hr>
    <p>配置信息将会被系统进行热加载，保存配置信息之后立刻生效。</p>
</div>
</body>
</html>
