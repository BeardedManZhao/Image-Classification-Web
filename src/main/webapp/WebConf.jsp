<%@ page import="zhao.core.user.User" %>
<%@ page import="zhao.task.ToFZF" %>
<%@ page import="zhao.Conf" %>
<%@ page import="zhao.task.VoidTask" %>
<%@ page import="zhao.utils.HTMLUtils" %>
<%@ page import="zhao.core.user.OrdinaryUser" %><%--
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
    // 如果是管理者就查询当前用户空间中的所有空间信息
    StringBuilder stringBuilder = new StringBuilder();
    for (User value : OrdinaryUser.USER_HASH_MAP.values()) {
        HTMLUtils.appRowToTable(stringBuilder, value.name(), value.getTrainDir(), value.getModelDir(), String.valueOf(value.isManager()));
    }
%>

<style>
    #user_space_table {
        background-color: rgba(13, 54, 53, 0.51);
    }

    #user_space_id td {
        background-color: rgba(212, 220, 217, 0.3);
    }
</style>

<html>
<head>
    <link href='image/Logo.svg' rel='SHORTCUT ICON'/>
    <link rel="stylesheet" type="text/css" href="css/Theme.css">
    <link rel="stylesheet" type="text/css" href="css/webStatus.css">
    <title>配置网站</title>
</head>
<body id="body">
<div id="title">
    <img id="logo" width="120" height="90" src="image/Logo.svg" alt="Image-Classification-Web">
    <h2 id="web-title">你好 <%=user.name()%> 在这里进行WEB页面的配置</h2>
</div>
<br>
<div id="user_space_id">
    <h3>当前正在使用的用户空间</h3>
    <table id="user_space_table">
        <tr>
            <td>
                用户名称
            </td>
            <td>
                训练数据目录
            </td>
            <td>
                模型存储目录
            </td>
            <td>
                具有管理权限
            </td>
        </tr>
        <%=stringBuilder.toString()%>
    </table>
</div>
<br>
<div id="data">
    <h3>配置模块</h3>
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
            网站运行状态
            <span id="running_status" class="running_status" style="font-family: 'icomoon', serif"></span>
            ：
            running<input name="web_status" type="radio" value="running" checked>
            closed <input name="web_status" type="radio" value="closed">
        </label>
        <br>
        <button type="button" onclick="window.history.back()">退出配置页面</button>
        <button type="submit" onclick="checkRunningStatus('web_status', 'running_status')">保存配置信息</button>
    </form>
    <hr>
    <p>配置信息将会被系统进行热加载，保存配置信息之后立刻生效。</p>
</div>
</body>
</html>

<script src="js/checkStatic.js" type="text/javascript"></script>
