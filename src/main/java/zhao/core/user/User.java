package zhao.core.user;

import zhao.task.TaskConsumer;
import zhao.utils.HttpUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户信息封装类对象。
 */
public interface User {

    /**
     * 检查cookie中是否包含用户，如果包含就返回用户在本系统中的注册，如果不包含就执行自定义的处理类。
     *
     * @return 返回当前请求对象中cookie对应的用户对象，如果当前用户没有登录信息，就执行 task 然后返回 def 用户信息。
     */
    static User checkCookieUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, TaskConsumer taskConsumer) throws IOException {
        if (httpServletRequest != null) {
            final Cookie cookie = HttpUtils.checkUserIsLogin(httpServletRequest);
            if (cookie != null) {
                return OrdinaryUser.$(cookie.getValue());
            }
        }
        taskConsumer.run(httpServletRequest, httpServletResponse);
        return OrdinaryUser.DEFAULT_USER;
    }

    /**
     * @return 当前用户的权限是否具有管理者权限。
     */
    boolean isManager();

    /**
     * @return 当前用户对应的昵称。
     */
    String name();

    /**
     * @return 当前用户对应的密码。
     */
    String passWord();

    /**
     * @return 当前用户对应的训练数据存储空间目录路径。
     */
    String getTrainDir();

    /**
     * @return 当前用户对应的模型数据存储空间目录路径。
     */
    String getModelDir();

    /**
     * @return 当前用户对应的json数据文件存储空间目录路径。
     */
    String getJsonDir();

    /**
     *
     * @return 当前用户对象数据 对应的 json 字符串
     */
    String toJson();

}
