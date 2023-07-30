package zhao.core;

import org.apache.commons.io.IOUtils;
import zhao.Conf;
import zhao.core.user.ManagerUser;
import zhao.core.user.User;
import zhao.task.TaskConsumer;
import zhao.task.ToLogin;
import zhao.task.VoidTask;
import zhao.utils.RunUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

/**
 * 网站 json 数据获取服务，在此服务中可以获取到前端页面需要展示的json数据。
 */
@WebServlet(name = "JsonServlet", value = "/JsonServlet")
public class JsonServlet extends HttpServlet {

    private final static HashMap<String, TaskConsumer> hash = new HashMap<>();

    static {
        hash.put("notFind", (request, response) -> {
            final PrintWriter writer = response.getWriter();
            writer.println('{');
            writer.append("\"serverTime\" : ").append(String.valueOf(System.currentTimeMillis())).println(',');
            writer.println("\"message\":\"没有找到您需要的json信息。\"");
            writer.println('}');
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        });

        hash.put("haveNoRight", (request, response) -> {
            final PrintWriter writer = response.getWriter();
            writer.println('{');
            writer.append("\"serverTime\" : ").append(String.valueOf(System.currentTimeMillis())).println(',');
            writer.println("\"message\":\"您的权限不足够执行此操作!!!!\"");
            writer.println('}');
        });

        hash.put("user", (request, response) -> {
            final User user = User.checkCookieUser(request, response, VoidTask.VOID_TASK);
            final PrintWriter writer = response.getWriter();
            writer.println('{');
            writer.append("\"user\" : ").append(user.toJson()).println(',');
            writer.append("\"serverTime\" : ").append(String.valueOf(System.currentTimeMillis())).println(',');
            writer.println("\"message\":\"ok\"");
            writer.println('}');
        });

        hash.put("confJson", (request, response) -> {
            final PrintWriter writer = response.getWriter();
            writer.write(Conf.getConfJson());
        });

        hash.put("allUsers", (request, response) -> {
            // 检查当前用户是否为管理者 如果不是管理者就直接回复 无法找到
            final User user = User.checkCookieUser(request, response, VoidTask.VOID_TASK);
            if (!user.isManager()) {
                hash.get("notFind").run(request, response);
                return;
            }
            // 检查之后发现是管理者 就直接进行所有用户数据的回复
            ManagerUser.writeUsersJson(response.getWriter());
        });

        hash.put("runCommand", (request, response) -> {
            // 检查当前用户是否为管理者 如果不是管理者就直接回复 没有权限
            final User user = User.checkCookieUser(request, response, VoidTask.VOID_TASK);
            if (!user.isManager()) {
                hash.get("haveNoRight").run(request, response);
                return;
            }
            // 如果是管理者就进行命令执行与数据回复
            final PrintWriter writer = response.getWriter();
            // 输出数据
            writer.println('{');
            writer.append("\"serverTime\" : ").append(String.valueOf(System.currentTimeMillis())).println(',');
            writer.append("\"message\":\"");
            // 执行命令
            RunUtils.runCommand(request.getParameter("command"), writer);
            writer.append("\"\n").println('}');
        });
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml;charset=GBK"); // 避免乱码
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("GBK");
        // 获取当前请求是否有参数(要获取的json文件类型)
        final String jsonName = request.getParameter("jsonName");
        // 判断这个参数是否属于预先准备的指令
        {
            final TaskConsumer taskConsumer = hash.get(jsonName);
            if (taskConsumer != null) {
                // 属于 直接执行
                taskConsumer.run(request, response);
                return;
            }
        }
        // 如果不属于就检查当前用户是否已经登录
        final User user = User.checkCookieUser(request, response, ToLogin.TO_LOGIN);
        // 开始进行逻辑的执行 首先获取到用户对应的 json 文件目录存储空间 以及需要的文件数据
        final File file = new File(user.getJsonDir() + '/' + jsonName);
        if (file.exists()) {
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            IOUtils.copy(bufferedInputStream, response.getWriter());
            bufferedInputStream.close();
        } else {
            hash.get("notFind").run(request, response);
        }
    }
}
