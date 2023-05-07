package zhao.utils;

/**
 * 字符串操作工具类
 *
 * @author zhao
 */
public class StrUtils {
    public static String ifNull(String value, String newValue) {
        return value == null ? newValue : value;
    }
}
