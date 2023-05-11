package zhao.utils;

/**
 * 字符串操作工具类
 *
 * @author zhao
 */
public class StrUtils {
    /**
     * 判断字符串对象是否为空 如果为空就返回新的字符串数值。
     *
     * @param value    需要被判断的字符串对象
     * @param newValue 如果为空则返回的字符串对象
     * @return 经过校验，绝对不会为空的字符串对象
     */
    public static String ifNull(String value, String newValue) {
        return value == null ? newValue : value;
    }
}
