package zhao.utils;

/**
 * @author zhao
 */
public final class HTMLUtils {

    /**
     * 将一行数据追加到指定的字符串对象中
     *
     * @param tableHTML 需要被追加的 tableHtml的字符串缓冲区
     * @param rowData   行数据数组
     * @return 缓冲区
     */
    public static StringBuilder appRowToTable(StringBuilder tableHTML, String... rowData) {
        tableHTML.append('\n')
                .append("<tr>");
        for (String rowDatum : rowData) {
            tableHTML
                    .append("<td>")
                    .append(rowDatum)
                    .append("</td>");
        }
        return tableHTML.append("</tr>\n");
    }
}
