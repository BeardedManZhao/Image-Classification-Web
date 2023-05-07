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
    final File file = new File(path);
    final String[] list = file.list();
    final boolean checkDirConDir = file.exists() && list != null && list.length != 0;
%>

<style>
    form {
        background-color: rgba(255, 228, 196, 0.19);
    }
</style>

<html lang="zh">
<head>
    <link href='image/Logo.svg' rel='SHORTCUT ICON'/>
    <link rel="stylesheet" type="text/css" href="css/Theme.css">
    <meta charset="GBK">
    <title>��ʼѵ��</title>
</head>
<body>

<%=
checkDirConDir ? "<form action=\"" + Conf.TRAIN_SERVLET + "\" onsubmit=\"return check()\">\n" +
        " <p>����" + name + "��ѵ�������ϴ���ɡ�</p>\n" +
        " <p>��ǰѵ��Ŀ¼��" + path + " </p>\n" +
        "    <label>\n" +
        "        ģ��ѵ��������\n" +
        "        <input id='train_epochs_id' name='train_epochs' type='number' title='ѵ������' alt='ѵ������' required='required'>\n" +
        "    </label><br>\n" +
        "    <label>\n" +
        "        ����ͼ��ߴ磺\n" +
        "        <input id='width' name='image_w' type='number' title='���' alt='���' placeholder='���' required='required'>\n" +
        "        <input id='height' name='image_h' type='number' title='�߶�' alt='�߶�' placeholder='�߶�' required='required'>\n" +
        "    </label><br>\n" +
        "    <label>\n" +
        "        ģ�;��������\n" +
        "        <input id='convolutional_count' name='convolutional_count' type='number' title='�������' alt='�������' required='required'>\n" +
        "    </label><br>\n" +
        "    <label>\n" +
        "        ��ʼ���������\n" +
        "        <input id='filters' name='filters' type=\"number\" placeholder=\"��һ������еľ������\" alt='�������' required='required'>\n" +
        "    </label><br>\n" +
        "    <label>\n" +
        "        ÿ������ȱȣ�\n" +
        "        <input id='filtersB' name='filtersB' type='number' placeholder='�����������ֵ���ɵȱ�' alt='��������ȱ�' required='required'>\n" +
        "    </label><br>" +
        "    <label>\n" +
        "        ���������ͣ���������ģ��<input id='ms_performance' value='performance' name='model_selection' type='radio' checked>\n" +
        "        ��������ģ��<input id='ms_accurate' value='accurate' name='model_selection' type='radio'>\n" +
        "    </label><br>" +
        "<button>��ʼѵ��</button>\n</form>" : " "
%>

<form action="<%=Conf.C10_TRAIN_SERVLET%>" onsubmit="return checkTrain_epochs('train_epochs_id') && check()">
    <p>����<%=name%>��CIFAR-10�������ݼ�׼����ɡ�</p>
    <p>��ǰѵ��Ŀ¼��cifar10</p>
    <label>
        ģ��ѵ��������
        <input id='train_epochs_id' name='train_epochs' type='number' title='ѵ������' alt='ѵ������' required='required'>
    </label>
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

    /**
     *
     * @returns {boolean} ��ָ��id��Ӧ�ؼ��е� value ���Դ��� 0 ��ʱ�򷵻� true
     */
    function checkTrain_epochs(id) {
        if (document.getElementById(id).value > 0) {
            return true
        } else {
            alert('��ȷ������ѵ������ ���� 0��')
            return false;
        }
    }
</script>