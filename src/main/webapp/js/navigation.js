/**
 * 构建一个导航栏
 * @param name 导航栏中的标题
 */
function makeNavigation(name) {
    document.write("<!-- 导航栏开始 -->\n" +
        "<div class=\"logo_title two_pages\">\n" +
        "    <div class=\"left\">\n" +
        "        <img alt=\"Image-Classification-Web\" id=\"logo\" src=\"image/Logo.svg\">\n" +
        "        <h3 id=\"web-title\">" + name + "</h3>\n" +
        "    </div>\n" +
        "\n" +
        "    <div class=\"right navigation\">\n" +
        "        <ul>\n" +
        "            <li>\n" +
        "                <a href=\"index.jsp\">系统主页</a>\n" +
        "            </li>\n" +
        "\n" +
        "            <li>\n" +
        "                <a href=\"TrainUp.html\">上传训练数据</a>\n" +
        "            </li>\n" +
        "\n" +
        "            <li>\n" +
        "                <a href=\"about.html\">关于我们</a>\n" +
        "            </li>\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</div>\n" +
        "<!-- 导航栏结束 -->")
}


/* 导航栏代码
<div class="logo_title two_pages">
    <div class="left">
        <img alt="Image-Classification-Web" id="logo" src="image/Logo.svg">
        <h3 id="web-title">使用训练好的模型</h3>
    </div>

    <div class="right navigation">
        <ul>
            <li>
                <a href="index.jsp">系统主页</a>
            </li>

            <li>
                <a href="TrainUp.html">上传训练数据</a>
            </li>

            <li>
                <a href="about.html">关于我们</a>
            </li>
        </ul>
    </div>
</div>
*/