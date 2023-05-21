package zhao.core;

import zhao.Conf;
import zhao.core.user.User;
import zhao.task.ToFZF;
import zhao.task.VoidTask;
import zhao.utils.ExeUtils;
import zhao.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 网站设置更新服务，当网站配置页面被访问修改以及提交之后，本服务类将会启动
 */
@WebServlet(name = "ConfUpdateServlet", value = "/ConfUpdateServlet")
public class ConfUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("GBK");
        // 检查当前用户是否为管理者 如果不是管理者就直接回复 404
        final User user = User.checkCookieUser(request, response, VoidTask.VOID_TASK);
        if (!user.isManager()) {
            ToFZF.TO_FZF.run(request, response);
            return;
        }
        final String maxSpaceNum = request.getParameter("maxSpaceNum");
        final String web_status = request.getParameter("web_status");
        final String loginJsp = request.getParameter("loginJsp");
        // 将当前管理者用户的更新操作写到日志中
        Conf.LOGGER.info(user.name() + " 管理者：更新了网站中的配置信息，配置情况如下所示: =======");
        Conf.LOGGER.info("USER_MAX_COUNT: " + Conf.USER_MAX_COUNT + " -> " + maxSpaceNum);
        Conf.USER_MAX_COUNT = Integer.parseInt(maxSpaceNum);

        Conf.LOGGER.info("LOGIN: " + Conf.LOGIN + " -> " + loginJsp);
        Conf.LOGIN = loginJsp;
        if ("closed".equals(web_status)) {
            // 管理员手动关闭网站
            Conf.LOGGER.info("web_status: running -> " + web_status);
            ExeUtils.closeWeb("linux".equalsIgnoreCase(Conf.SYSTEM_TYPE));
        }
        HttpUtils.alertBack(response.getWriter(), "配置更新完成!!!");
    }
}
