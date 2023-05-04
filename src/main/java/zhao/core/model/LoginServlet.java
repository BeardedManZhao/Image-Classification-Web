package zhao.core.model;

import zhao.Conf;
import zhao.core.user.OrdinaryUser;
import zhao.core.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录处理类 在这里负责将传递进来的用户信息封装成 cookie 提供给客户端，并保存用户信息。
 */
@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<script>history.back()</script>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("GBK");
        final String userName = request.getParameter("userName");
        final String password = request.getParameter("password");
        final PrintWriter writer = response.getWriter();
        if (userName == null || password == null) {
            writer.println("<script>");
            writer.println("alert('无法处理进入空间的请求');");
            writer.println("window.location.href='" + Conf.HOME + '\'');
            writer.println("'</script>");
        } else {
            final User user = OrdinaryUser.$(userName, password);
            // 判断 用户是否正确
            if (user == OrdinaryUser.DEFAULT_USER) {
                // 不正确
                writer.println("<script>");
                writer.println("alert('请检查您的登录信息!!!!');");
                writer.println("window.history.back()");
                writer.println("</script>");
            } else {
                writer.println("<script>");
                // 检查当前已创建的用户空间数量是否超出最大值
                if (OrdinaryUser.getUserLen() > Conf.USER_MAX_COUNT) {
                    // 超出最大值了
                    writer.println("alert('抱歉，当前已创建空间数量达到上限，无法继续创建空间数量。')");
                    writer.println("</script>");
                    return;
                }
                // 创建 cookie
                final Cookie cookie = new Cookie(Conf.WEN_COOKIE, user.toString());
                response.addCookie(cookie);
                writer.println("alert('欢迎来到您的空间!!!');");
                writer.println("window.location.href='" + Conf.HOME + '\'');
                writer.println("</script>");
            }
        }
    }
}
