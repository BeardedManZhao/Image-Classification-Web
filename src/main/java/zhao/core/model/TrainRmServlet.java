package zhao.core.model;

import zhao.Conf;
import zhao.utils.FSUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 个人空间数据集清理策略措施服务类。
 */
@WebServlet(name = "TrainRmServlet", value = "/TrainRmServlet")
public class TrainRmServlet extends HttpServlet {

    private final String name = "zhao";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("GBK");
        // 获取到当前用户空间列表
        final File file = new File(Conf.TRAIN_DIR + '/' + name);
        final PrintWriter writer = response.getWriter();
        // 开始删除
        if (FSUtils.deleteDir(file)) {
            writer.println("<script>alert('清理完成!!!');history.back()</script>");
        } else {
            writer.println("<script>alert('清理失败!!!');history.back()</script>");
        }
    }
}
