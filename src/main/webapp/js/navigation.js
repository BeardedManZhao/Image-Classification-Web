/**
 * ����һ��������
 * @param name �������еı���
 */
function makeNavigation(name) {
    document.write("<div class='logo_title two_pages'>\n" +
        "    <div class='left'>\n" +
        "        <img alt='Image-Classification-Web' id='logo' src='image/Logo.svg'>\n" +
        "        <h3 id='web-title'>" + name + "</h3>\n" +
        "    </div>\n" +
        "\n" +
        "    <div class='right navigation'>\n" +
        "        <ul>\n" +
        "            <li onclick=\"closeOrOpenBackColor('backColorUpdate'); return false;\">\n" +
        "                <a id='backColorUpdate' href='#'>�رձ�����</a>\n" +
        "            </li>\n" +
        "            <li>\n" +
        "                <a href='index.jsp'>ϵͳ��ҳ</a>\n" +
        "            </li>\n" +
        "\n" +
        "            <li>\n" +
        "                <a href='TrainUp.html'>�ϴ�ѵ������</a>\n" +
        "            </li>\n" +
        "\n" +
        "            <li>\n" +
        "                <a href='about.html'>��������</a>\n" +
        "            </li>\n" +
        "        </ul>\n" +
        "    </div>\n" +
        "</div>")
}


/* ����������
<div class='logo_title two_pages'>
    <div class='left'>
        <img alt='Image-Classification-Web' id='logo' src='image/Logo.svg'>
        <h3 id='web-title'>ʹ��ѵ���õ�ģ��</h3>
    </div>

    <div class='right navigation'>
        <ul>
            <li onclick="closeOrOpenBackColor('backColorUpdate'); return false;">
                <a id='backColorUpdate' href='#'>�رձ�����</a>
            </li>
            <li>
                <a href='index.jsp'>ϵͳ��ҳ</a>
            </li>

            <li>
                <a href='TrainUp.html'>�ϴ�ѵ������</a>
            </li>

            <li>
                <a href='about.html'>��������</a>
            </li>
        </ul>
    </div>
</div>
*/