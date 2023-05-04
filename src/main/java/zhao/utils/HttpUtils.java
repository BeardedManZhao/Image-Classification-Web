package zhao.utils;

import zhao.Conf;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
}
