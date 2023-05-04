<%@ page import="zhao.Conf" %>
<%@ page import="java.io.File" %>
<%@ page import="zhao.core.user.OrdinaryUser" %>
<%@ page import="zhao.core.user.User" %>
<%@ page import="zhao.task.ToLogin" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/5/2
  Time: 19:10
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=GBK" %>
<%
    // ��ȡ����ǰ�û�������
    final User user = User.checkCookieUser(request, response, ToLogin.TO_LOGIN);
    if (user.equals(OrdinaryUser.DEFAULT_USER)) {
        // ���� def ����ǰ�û�û�е�¼ ֱ�ӽ���
        return;
    }
    String name = user.name(), path = user.getTrainDir();
    // ���ѵ�����ݼ�Ŀ¼���Ƿ����ָ����Ŀ¼
    final boolean checkDirConDir = new File(path).exists();
    if (!checkDirConDir) {
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.sendRedirect(Conf.TRAIN_UP_HTML);
    }
%>
<html lang="zh">
<head>
    <meta charset="GBK">
    <title>��ʼѵ��</title>
</head>
<body>
<form action="<%=Conf.TRAIN_SERVLET%>" onsubmit="return check()">
    <p>����<%=name%>��ѵ�������ϴ���ɡ�</p>
    <p>��ǰѵ��Ŀ¼��<%=path%>
    </p>
    <button>��ʼѵ��</button>
</form>

<a href="index.jsp">�ص���ҳ</a>
</body>
</html>

<script>
    let status = 1;

    /**
     * ����Ƿ��Ѿ���������ύ
     */
    function check() {
        if (status === 0) {
            alert("�����Եȣ�ѵ�����ڽ�����.....");
            return false;
        } else {
            status = 0;
            alert("������ʼѵ��!!!!")
            return true;
        }
    }
</script>