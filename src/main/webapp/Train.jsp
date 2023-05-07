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
    // 获取到当前用户的名称
    final User user = User.checkCookieUser(request, response, ToLogin.TO_LOGIN);
    if (user.equals(OrdinaryUser.DEFAULT_USER)) {
        // 若是 def 代表当前用户没有登录 直接结束
        return;
    }
    String name = user.name(), path = user.getTrainDir();
    // 检查训练数据集目录中是否包含指定的目录
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
    <title>开始训练</title>
</head>
<body>

<%=
checkDirConDir ? "<form action=\"" + Conf.TRAIN_SERVLET + "\" onsubmit=\"return check()\">\n" +
        " <p>您好" + name + "，训练数据上传完成。</p>\n" +
        " <p>当前训练目录：" + path + " </p>\n" +
        "    <label>\n" +
        "        模型训练次数：\n" +
        "        <input id='train_epochs_id' name='train_epochs' type='number' title='训练次数' alt='训练次数' required='required'>\n" +
        "    </label><br>\n" +
        "    <label>\n" +
        "        输入图像尺寸：\n" +
        "        <input id='width' name='image_w' type='number' title='宽度' alt='宽度' placeholder='宽度' required='required'>\n" +
        "        <input id='height' name='image_h' type='number' title='高度' alt='高度' placeholder='高度' required='required'>\n" +
        "    </label><br>\n" +
        "    <label>\n" +
        "        模型卷积层数：\n" +
        "        <input id='convolutional_count' name='convolutional_count' type='number' title='卷积层数' alt='卷积层数' required='required'>\n" +
        "    </label><br>\n" +
        "    <label>\n" +
        "        初始卷积核数：\n" +
        "        <input id='filters' name='filters' type=\"number\" placeholder=\"第一卷积层中的卷积核数\" alt='卷积核数' required='required'>\n" +
        "    </label><br>\n" +
        "    <label>\n" +
        "        每层核数等比：\n" +
        "        <input id='filtersB' name='filtersB' type='number' placeholder='卷积核数量数值生成等比' alt='卷积核数等比' required='required'>\n" +
        "    </label><br>" +
        "    <label>\n" +
        "        神经网络类型：性能优先模型<input id='ms_performance' value='performance' name='model_selection' type='radio' checked>\n" +
        "        精度优先模型<input id='ms_accurate' value='accurate' name='model_selection' type='radio'>\n" +
        "    </label><br>" +
        "<button>开始训练</button>\n</form>" : " "
%>

<form action="<%=Conf.C10_TRAIN_SERVLET%>" onsubmit="return checkTrain_epochs('train_epochs_id') && check()">
    <p>您好<%=name%>，CIFAR-10内置数据集准备完成。</p>
    <p>当前训练目录：cifar10</p>
    <label>
        模型训练次数：
        <input id='train_epochs_id' name='train_epochs' type='number' title='训练次数' alt='训练次数' required='required'>
    </label>
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

    /**
     *
     * @returns {boolean} 当指定id对应控件中的 value 属性大于 0 的时候返回 true
     */
    function checkTrain_epochs(id) {
        if (document.getElementById(id).value > 0) {
            return true
        } else {
            alert('请确保您的训练次数 大于 0。')
            return false;
        }
    }
</script>