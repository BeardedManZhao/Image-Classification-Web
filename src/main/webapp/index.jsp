<%@ page import="zhao.Conf" %>
<%@ page import="java.io.File" %>
<%@ page import="zhao.utils.HTMLUtils" %>
<%@ page import="zhao.utils.FSUtils" %>
<%@ page import="zhao.core.user.User" %>
<%@ page import="zhao.core.user.OrdinaryUser" %>
<%@ page import="zhao.task.ToLogin" %>
<%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/5/2
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=GBK" %>

<%
    if (Conf.checkNeural_network_statusIsClose()) {
        request.getRequestDispatcher(Conf.NN_ERROR_HTML).forward(request, response);
        return;
    }
    // 首先获取到当前用户的信息，如果没有登录就直接跳转到登录页面
    // 检查当前用户是否已经登录，没有登录就跳转到登录页面
    final User user = User.checkCookieUser(request, response, ToLogin.TO_LOGIN);
    if (user.equals(OrdinaryUser.DEFAULT_USER)) {
        // 若是 def 代表当前用户没有登录 直接结束
        return;
    }
    // 获取到当前用户的 name
    final String name = user.name();
    // 计算出当前用户的个人空间目录是否具有文件数据
    final File file = new File(user.getTrainDir());
    // 将目录中的所有类别数据直接展示出来
    StringBuilder stringBuilder = new StringBuilder(128);
    boolean isHaveFile;
    if (file.exists()) {
        final File[] files = file.listFiles();
        if (files != null && files.length != 0) {
            // 如果文件存在就开始将文件下的所有的数据写出表格
            stringBuilder.append("<table id='dir_table' class='theme-table'>");
            HTMLUtils.appRowToTable(stringBuilder, "Name", "type", "size");
            for (File file1 : files) {
                HTMLUtils.appRowToTable(stringBuilder, file1.getName(), file1.isDirectory() ? "dir" : "file", file1.length() + " Byte");
            }
            stringBuilder.append("</table>");
            isHaveFile = true;
        } else {
            isHaveFile = false;
            stringBuilder.append("<p>没有在您的个人空间中找到文件数据，您可以先进行训练数据的上传。</p>");
        }
    } else {
        isHaveFile = false;
        stringBuilder.append("<p>您的个人空间没有数据，请上传训练数据。</p>");
    }

    // 判断当前用户空间是否有模型
    final String s = user.getModelDir();
    final File file1 = new File(s + "/Model");
    boolean isHaveModel = file1.exists() && file1.isDirectory();
    // 计算出当前用户的模型支持类别
    final File file2 = new File(s + "/classList.txt");
    StringBuilder classStr = new StringBuilder();
    // 计算出当前用户的训练数据包含类别
    final File file3 = new File(s + "/tempClassList.txt");
    StringBuilder tempClassStr = new StringBuilder();
    FSUtils.extractedData(file2, classStr.append('['));
    classStr.append(']');
    FSUtils.extractedData(file3, tempClassStr.append('['));
    tempClassStr.append(']');
%>

<html>
<head>
    <link href='image/Logo.svg' rel='SHORTCUT ICON'/>
    <link rel="stylesheet" type="text/css" href="css/Theme.css">
    <title>图像分类系统</title>
    <style>
        .form_button {
            display: inline-block;
        }
    </style>
    <script src="js/updateTheme.js" type="text/javascript"></script>
</head>
<body id="body">
<div class="logo_title">
    <img id="logo" width="120" height="90" src="image/Logo.svg" alt="Image-Classification-Web">
    <h2 id="web-title"><%=user.isManager() ? "管理者 " : "你好 "%> <%=name%> 欢迎使用图像分类系统</h2>
</div>
<hr>
<div>
    <h3>您的个人空间目录</h3>
    <div id="dir">
        <%=stringBuilder.toString()%>
    </div>

    <br>

    <div>
        <table id="model" class="theme-table">
            <tr>
                <td>
                    模型路径
                </td>
                <td>
                    模型大小
                </td>
                <td>
                    模型类别信息
                </td>
                <td>
                    训练类别信息
                </td>
            </tr>
            <tr>
                <td>
                    <%=file1.getPath()%>
                </td>
                <td>
                    <%=file1.length()%>
                </td>
                <td>
                    <%=classStr.toString()%>
                </td>
                <td>
                    <%=tempClassStr.toString()%>
                </td>
            </tr>
        </table>
    </div>
</div>
<hr>
<a class='button' href="<%=Conf.TRAIN_UP_HTML%>" target='_blank'>上传训练数据集</a>
<%=
isHaveFile ? "<a class='button' href='" + Conf.TRAIN_RM_SERVLET + "'>清理个人数据集</a>\n" +
        "<a class='button' href='" + Conf.IMAGE_TRAIN_DIR + '/' + name + "'>前往个人空间目录</a>" : '\n'
%>
<%=
user.isManager() ? "<a class='button' href='" + Conf.WEB_CONFIG_JSP + "'>进行网站系统配置</a>" : '\n'
%>
<br>
<form class="form_button" action="<%=Conf.TRAIN_JSP%>" target="_blank">
    <button>开始训练模型</button>
</form>
<form class="form_button" action="<%=Conf.USE_MODEL_HTML%>" target="_blank">
    <%=
    isHaveModel ? "<button>使用训练好的模型</button>" :
            "<a disabled=\"disabled\">请您先进行模型的训练</a>"
    %>
</form>
<button id="backColorUpdate" onclick="closeOrOpenBackColor('backColorUpdate');">关闭背景灯</button>
<form class="form_button" action="about.html" target="_blank">
    <button>关于IMW</button>
</form>
</body>
</html>