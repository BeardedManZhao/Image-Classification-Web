<%@ page import="zhao.Conf" %>
<%@ page import="java.io.File" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/5/2
  Time: 19:10
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=GBK" %>
<%
    // 获取到当前用户的名称
    String name = "zhao", path = Conf.TRAIN_DIR + '/' + name;
    // 检查训练数据集目录中是否包含指定的目录
    final boolean checkDirConDir = new File(path).exists();
    if (!checkDirConDir) {
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.sendRedirect(Conf.TRAIN_UP_HTML);
    }
%>
<html lang="zh">
<head>
    <meta charset="GBK">
    <title>开始训练</title>
</head>
<body>
<form action="<%=Conf.TRAIN_SERVLET%>" onsubmit="return check()">
    <p>训练数据上传完成。</p>
    <p>当前训练目录：<%=path%>
    </p>
    <button>开始训练</button>
</form>

<a href="index.jsp">回到主页</a>
</body>
</html>

<script>
    let status = 1;

    /**
     * 检查是否已经点击过了提交
     */
    function check() {
        if (status === 0) {
            alert("请您稍等，训练正在进行中.....");
            return false;
        } else {
            status = 0;
            alert("即将开始训练!!!!")
            return true;
        }
    }
</script>