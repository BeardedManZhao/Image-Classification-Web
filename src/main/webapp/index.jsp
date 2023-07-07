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
    // ���Ȼ�ȡ����ǰ�û�����Ϣ�����û�е�¼��ֱ����ת����¼ҳ��
    // ��鵱ǰ�û��Ƿ��Ѿ���¼��û�е�¼����ת����¼ҳ��
    final User user = User.checkCookieUser(request, response, ToLogin.TO_LOGIN);
    if (user.equals(OrdinaryUser.DEFAULT_USER)) {
        // ���� def ����ǰ�û�û�е�¼ ֱ�ӽ���
        return;
    }
    // ��ȡ����ǰ�û��� name
    final String name = user.name();
    // �������ǰ�û��ĸ��˿ռ�Ŀ¼�Ƿ�����ļ�����
    final File file = new File(user.getTrainDir());
    // ��Ŀ¼�е������������ֱ��չʾ����
    StringBuilder stringBuilder = new StringBuilder(128);
    boolean isHaveFile;
    if (file.exists()) {
        final File[] files = file.listFiles();
        if (files != null && files.length != 0) {
            // ����ļ����ھͿ�ʼ���ļ��µ����е�����д�����
            stringBuilder.append("<table id='dir_table' class='theme-table'>");
            HTMLUtils.appRowToTable(stringBuilder, "Name", "type", "size");
            for (File file1 : files) {
                HTMLUtils.appRowToTable(stringBuilder, file1.getName(), file1.isDirectory() ? "dir" : "file", file1.length() + " Byte");
            }
            stringBuilder.append("</table>");
            isHaveFile = true;
        } else {
            isHaveFile = false;
            stringBuilder.append("<p>û�������ĸ��˿ռ����ҵ��ļ����ݣ��������Ƚ���ѵ�����ݵ��ϴ���</p>");
        }
    } else {
        isHaveFile = false;
        stringBuilder.append("<p>���ĸ��˿ռ�û�����ݣ����ϴ�ѵ�����ݡ�</p>");
    }

    // �жϵ�ǰ�û��ռ��Ƿ���ģ��
    final String s = user.getModelDir();
    final File file1 = new File(s + "/Model");
    boolean isHaveModel = file1.exists() && file1.isDirectory();
    // �������ǰ�û���ģ��֧�����
    final File file2 = new File(s + "/classList.txt");
    StringBuilder classStr = new StringBuilder();
    // �������ǰ�û���ѵ�����ݰ������
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
    <title>ͼ�����ϵͳ</title>
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
    <h2 id="web-title"><%=user.isManager() ? "������ " : "��� "%> <%=name%> ��ӭʹ��ͼ�����ϵͳ</h2>
</div>
<hr>
<div>
    <h3>���ĸ��˿ռ�Ŀ¼</h3>
    <div id="dir">
        <%=stringBuilder.toString()%>
    </div>

    <br>

    <div>
        <table id="model" class="theme-table">
            <tr>
                <td>
                    ģ��·��
                </td>
                <td>
                    ģ�ʹ�С
                </td>
                <td>
                    ģ�������Ϣ
                </td>
                <td>
                    ѵ�������Ϣ
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
<a class='button' href="<%=Conf.TRAIN_UP_HTML%>" target='_blank'>�ϴ�ѵ�����ݼ�</a>
<%=
isHaveFile ? "<a class='button' href='" + Conf.TRAIN_RM_SERVLET + "'>����������ݼ�</a>\n" +
        "<a class='button' href='" + Conf.IMAGE_TRAIN_DIR + '/' + name + "'>ǰ�����˿ռ�Ŀ¼</a>" : '\n'
%>
<%=
user.isManager() ? "<a class='button' href='" + Conf.WEB_CONFIG_JSP + "'>������վϵͳ����</a>" : '\n'
%>
<br>
<form class="form_button" action="<%=Conf.TRAIN_JSP%>" target="_blank">
    <button>��ʼѵ��ģ��</button>
</form>
<form class="form_button" action="<%=Conf.USE_MODEL_HTML%>" target="_blank">
    <%=
    isHaveModel ? "<button>ʹ��ѵ���õ�ģ��</button>" :
            "<a disabled=\"disabled\">�����Ƚ���ģ�͵�ѵ��</a>"
    %>
</form>
<button id="backColorUpdate" onclick="closeOrOpenBackColor('backColorUpdate');">�رձ�����</button>
<form class="form_button" action="about.html" target="_blank">
    <button>����IMW</button>
</form>
</body>
</html>