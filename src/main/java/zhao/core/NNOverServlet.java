package zhao.core;

import zhao.Conf;
import zhao.core.user.User;
import zhao.task.VoidTask;
import zhao.utils.FSUtils;
import zhao.utils.RunUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 神经网络模型覆写服务类
 *
 * @author zhao
 */
@WebServlet(name = "NNOverServlet", value = "/NNOverServlet")
public class NNOverServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Conf.NN_ERROR_HTML).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("GBK");
        resp.setCharacterEncoding("GBK");
        final PrintWriter writer = resp.getWriter();
        String path1 = req.getParameter("runPath");

        // 首先回复基本页面
        writer.println("<body>");
        writer.println("<link rel=\"stylesheet\" href=\"css/terminal.css\">");
        writer.println("<style>table {color: #72c6cb;}</style>");
        writer.println("<div id='terminal' class='terminal'>");
        writer.println("init >>> 您好，神经网络系统自动覆写程序执行，如果最终返回“(^_^)”，则代表成功！！！");
        writer.println("<br>");

        // 获取到 path1 的目录
        final File file = new File(path1);
        if (file.exists()) {
            String path = file.getParent();
            // 然后回复终端内的结果文字
            // 检查当前用户是否为管理者 如果不是管理者就直接回复 权限不够的信息
            final User user = User.checkCookieUser(req, resp, VoidTask.VOID_TASK);
            if (!user.isManager()) {
                writer.println("error >>> Please use the administrator account for the override operation of the neural network system.");
            } else {
                writer.println("run >>> " + path1);
                writer.println("<br>");
                // 准备执行命令 将脚本路径作为命令就好
                RunUtils.runCommand(path1 + ' ' + path, null);
                writer.println("ok!!!<br>");
                try {
                    Thread.sleep(1024);
                } catch (InterruptedException e) {
                    writer.println(e);
                }
                String path2 = req.getParameter("runJar");
                path2 = "java -jar " + path2 + ' ' + path + "/__pycache__" + ' ' + Conf.NN_PATH;
                writer.println("run >>> " + path2);
                // 由于这里使用的是 compileNNPy 因此需要手动的调用 ModelInit 程序
                RunUtils.runCommand(path2, null);
                writer.println("<br>");
                writer.println("ok!!!<br>");
                try {
                    Thread.sleep(1024);
                } catch (InterruptedException e) {
                    writer.println(e);
                    return;
                }
                // 检索目录
                if (FSUtils.seeDirGetHtmlTable(Conf.NN_PATH, writer, "\n")) {
                    writer.println("<br>");
                    writer.println("ok!!!<br>");
                    writer.println("!!!(^_^)!!!");
                    writer.append("<br>神经网络系统覆写成功，您可以 <a href='").append(Conf.HOME).append("'>前往主页</a>啦!!!");
                    Conf.Neural_network_status = 0;
                } else {
                    writer.println("error >>> 覆写失败，请在服务器中手动使用 “compile.bat/compile.sh” 脚本进行神经网络覆写。");
                    writer.println("<br>");
                    writer.println("!!!('_')!!!");
                }

            }
        } else {
            writer.println("error >>> 文件不存在，您提供的路径有问题，我们无法找到您提供的文件!!!!!");
            writer.println(file.getPath());
            writer.println("<br>");
            writer.println("!!!('_')!!!");
        }
        writer.println("</div></body>");
    }
}
