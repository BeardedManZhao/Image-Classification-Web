package zhao.core.user;

import java.io.PrintWriter;
import java.util.Map;

/**
 * 管理者用户类
 *
 * @author zhao
 */
public class ManagerUser extends OrdinaryUser {

    public ManagerUser(String name, String password) {
        super(name, password, true);
    }

    /**
     * 将所有用户的 json 数据写到流数据中。
     *
     * @param printWriter 指定的流数据
     */
    public static void writeUsersJson(PrintWriter printWriter) {
        printWriter.println('{');
        int len = USER_HASH_MAP.size();
        int index = 0;
        for (Map.Entry<String, User> entry : OrdinaryUser.USER_HASH_MAP.entrySet()) {
            String name = entry.getKey();
            User user = entry.getValue();
            printWriter
                    .append("\"").append(name).append("\":")
                    .append(user.toJson());
            if (++index < len) {
                printWriter.println(',');
            }
        }
        printWriter.println('}');
    }
}
