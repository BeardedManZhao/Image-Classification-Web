package zhao.core.model;

import org.apache.commons.io.IOUtils;
import zhao.Conf;
import zhao.core.user.OrdinaryUser;
import zhao.core.user.User;
import zhao.task.ToLogin;
import zhao.utils.ExeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

/**
 * 使用模型跳转页面
 */
@WebServlet(name = "UseModelServlet", value = "/UseModelServlet")
public class UseModelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("GBK");
        final User user = User.checkCookieUser(request, response, ToLogin.TO_LOGIN);
        if (user.equals(OrdinaryUser.DEFAULT_USER)) {
            // 若是 def 代表当前用户没有登录 直接结束
            return;
        }
        final Part image = request.getParts().iterator().next();
        // 获取到指定的文件存储目录
        final String name = user.name();
        String path = Conf.IMAGE_USE_DIR + name;
        // 将文件保存
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(image.getInputStream());
        IOUtils.copy(bufferedInputStream, bufferedOutputStream);
        bufferedInputStream.close();
        bufferedOutputStream.close();
        final PrintWriter writer = response.getWriter();
        // 返回被识别的图像
        writer.write("<!DOCTYPE html>");
        writer.write(" <html lang=\"zh\">");
        writer.write(" <head>");
        writer.write(" <meta charset=\"GBK\">");
        writer.write(" <title>训练结果</title>");
        writer.write(" </head>");
        writer.write("<h2>被识别的图像</h2><hr>");
        writer.write("<img src='" + "/IMW/IMW_IMAGE/use/" + name + "' alt=\"被识别的图像\">");
        writer.write("<hr><h2>识别结果展示</h2>\n");
        writer.write("<hr>\n");
        writer.write("<pre>");
        // 开始使用脚本
        final String s = Conf.MODEL_DIR + '/' + name;
        final InputStream inputStream = ExeUtils.exePy(
                Conf.USING_PYTHON_PATH,
                s + "/Model " +
                        path + ' ' +
                        s + "/classList.txt");
        IOUtils.copy(inputStream, writer, "GBK");
        writer.write("</pre>");
        writer.write("<hr>");
        writer.write("<form action=\"UseModelServlet\" enctype=\"multipart/form-data\" method=\"post\">\n" +
                "    <input Content-Type=\"image/*\" accept=\"image/*\" name=\"image\" title=\"上传需要被识别的图像\" type=\"file\">\n" +
                "    <button>开始识别</button>\n" +
                "</form>");
        inputStream.close();
    }
}
