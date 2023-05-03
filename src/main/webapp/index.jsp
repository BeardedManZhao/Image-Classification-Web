<%@ page import="zhao.Conf" %>
<%@ page import="java.io.File" %>
<%@ page import="zhao.utils.HTMLUtils" %>
<%@ page import="zhao.utils.FSUtils" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/5/2
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%!
    final String name = "zhao";
%>

<%
    // 计算出当前用户的个人空间目录是否具有文件数据
    final File file = new File(Conf.TRAIN_DIR + '/' + name);
    // 将目录中的所有类别数据直接展示出来
    StringBuilder stringBuilder = new StringBuilder(128);
    boolean isHaveFile;
    if (file.exists()) {
        final File[] files = file.listFiles();
        if (files != null && files.length != 0) {
            // 如果文件存在就开始将文件下的所有的数据写出表格
            stringBuilder.append("<table id='dir'>");
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
    final String s = Conf.MODEL_DIR + '/' + name;
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

<style>
    table {
        text-align: center;
        background-color: chocolate;
        align-items: center;
    }

    td {
        background-color: #eedcc5;
    }
</style>

<html>
<head>
    <title>图像分类系统</title>
</head>
<body>
<h2>你好 <%=name%> 欢迎使用图像分类系统</h2>
<hr>
<h3>您的个人空间目录</h3>
<div id="dir">
    <%=stringBuilder.toString()%>
</div>

<br>

<div>
    <table id="model">
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
                <%=file1.getAbsolutePath()%>
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
<hr>
<a href="<%=Conf.TRAIN_UP_HTML%>">上传训练数据集</a>
<a href='<%=Conf.TRAIN_RM_SERVLET%>'>清理个人数据集</a>
<form action="<%=Conf.TRAIN_SERVLET%>" onsubmit="return checkTrain()">
    <%=
    isHaveFile ? "<button>开始训练模型</button>" :
            "<a disabled=\"disabled\">请您先进行数据的上传</a>"
    %>
</form>
<form action="<%=Conf.USE_MODEL_HTML%>">
    <%=
    isHaveModel ? "<button>使用训练好的模型</button>" :
            "<a disabled=\"disabled\">请您先进行模型的训练</a>"
    %>
</form>
</body>
</html>


<script>
    let status = 1;

    /**
     * 检查是否已经点击过了训练提交
     */
    function checkTrain() {
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