package zhao.utils;

import zhao.Conf;
import zhao.core.user.User;
import zhao.task.ToLogin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Http 协议处理工具类
 *
 * @author zhao
 */
public final class HttpUtils {
    /**
     * 检查当前用户是否已经登录，如果登录就返回 cookie 对象
     *
     * @param httpServletRequest 需要被检查的请求
     * @return 如果当前用于已登录，则返回 cookie 反之返回null
     */
    public static Cookie checkUserIsLogin(HttpServletRequest httpServletRequest) {
        final Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Conf.WEN_COOKIE.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 转换编码 同时检查登录情况
     *
     * @param request  请求对象
     * @param response 回复对象
     * @return 检查到的用户或 default 用户
     * @throws UnsupportedEncodingException 不支持编码转换错误
     */
    public static User intiCheck(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setCharacterEncoding("GBK");
        request.setCharacterEncoding("GBK");
        return User.checkCookieUser(request, response, ToLogin.TO_LOGIN);
    }

    /**
     * 弹出提示框并且直接返回到上一级中。
     *
     * @param printWriter 回复数据流
     * @param alertStr    提示框中的数据
     */
    public static void alertBack(PrintWriter printWriter, String alertStr) {
        printWriter.println("<script>");
        printWriter.println("alert('" + alertStr + "');");
        printWriter.println("window.history.back()");
        printWriter.println("</script>");
    }

}
