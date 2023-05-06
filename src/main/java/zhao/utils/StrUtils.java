package zhao.utils;

/**
 * @author zhao
 */
public class StrUtils {
    public static String ifNull(String value, String newValue) {
        return value == null ? newValue : value;
    }
}
