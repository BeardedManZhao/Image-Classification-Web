package zhao.core;

import org.apache.commons.io.IOUtils;
import zhao.core.user.User;
import zhao.task.TaskConsumer;
import zhao.task.VoidTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

/**
 * 网站设置更新服务，当网站配置页面被访问修改以及提交之后，本服务类将会启动
 */
@WebServlet(name = "JsonServlet", value = "/JsonServlet")
public class JsonServlet extends HttpServlet {

    private final static HashMap<String, TaskConsumer> hash = new HashMap<>();

    static {
        hash.put("notFind", (request, response) -> {
            final PrintWriter writer = response.getWriter();
            writer.println('{');
            writer.append("'serverTime' : ").println(System.currentTimeMillis());
            writer.append(',').println("'message':'没有找到您需要的json信息。'");
            writer.println('}');
        });

        hash.put("user", (request, response) -> {
            final User user = User.checkCookieUser(request, response, VoidTask.VOID_TASK);
            final PrintWriter writer = response.getWriter();
            writer.println('{');
            writer.append("'user' : ").println(user.toJson());
            writer.append(",'serverTime' : ").println(System.currentTimeMillis());
            writer.println(",'message':'ok'");
            writer.println('}');
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
        final User user = User.checkCookieUser(request, response, VoidTask.VOID_TASK);
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
