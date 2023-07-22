package zhao.core.model;

import org.apache.commons.io.IOUtils;
import zhao.Conf;
import zhao.core.user.OrdinaryUser;
import zhao.core.user.User;
import zhao.task.ToLogin;
import zhao.utils.ExeUtils;
import zhao.utils.FSUtils;
import zhao.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Collection;

/**
 * 使用模型跳转页面
 */
@WebServlet(name = "UseModelBatchServlet", value = "/UseModelBatchServlet")
@MultipartConfig
public class UseModelBatchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("GBK");
        final User user = User.checkCookieUser(request, response, ToLogin.TO_LOGIN);
        if (user.equals(OrdinaryUser.DEFAULT_USER) && (!HttpUtils.checkCanTrain(response))) {
            // 若是 def 代表当前用户没有登录 直接结束
            return;
        }
        // 先检查或创建个人使用模型的目录
        final PrintWriter writer = response.getWriter();
        final String name = user.name();
        final File file1 = new File(Conf.IMAGE_USE_BATCH_DIR + name);
        if (!file1.exists()) {
            if (!file1.mkdirs()) {
                throw new IOException("个人文件目录无法成功创建：" + file1.getAbsolutePath());
            }
        } else {
            // 如果目录存在就清理下目录
            final boolean b = FSUtils.deleteDir(file1);
            if (b) {
                if (!file1.mkdir()) {
                    HttpUtils.alertBack(writer, "您的模型使用目录无法创建！");
                    return;
                }
            } else {
                HttpUtils.alertBack(writer, "您的模型使用目录中有残留文件，清理这些文件失败！");
                return;
            }
        }
        final Collection<Part> image = request.getParts();
        // 获取到指定的文件存储目录
        String path = file1.getPath();
        // 将文件保存
        extractedFiles(path, image);
        // 返回被识别的图像
        writer
                .append("<!DOCTYPE html>")
                .append(" <html lang=\"zh\">")
                .append(" <head>")
                .append(" <meta charset=\"GBK\">")
                .append(" <title>批识别结果</title>\n")
                .println("    <link href=\"css/Theme.css\" rel=\"stylesheet\" type=\"text/css\">");
        writer.println("    <link href=\"css/terminal.css\" rel=\"stylesheet\" type=\"text/css\">");
        writer.append(" </head>")
                .append("<h2>批识别日志展示</h2>\n<hr>\n")
                .append("<pre class='terminal'>");

        // 开始使用脚本
        final String s = Conf.MODEL_DIR + '/' + name;
        final InputStream inputStream = ExeUtils.exePy(
                Conf.USING_BATCH_PYTHON_PATH,
                s + "/Model " +
                        path + ' ' +
                        request.getParameter("w") + ' ' +
                        request.getParameter("h") + ' ' +
                        request.getParameter("c") + ' '
        );
        IOUtils.copy(inputStream, writer, "GBK");
        writer.write("</pre>");
        writer.write("<hr>");
        String p = Conf.IMAGE_USE_BATCH_DIR_PATH + name + "/classificationResults.json";
        writer.println("<form action='" + p + "' enctype='multipart/form-data' method='post'>\n");
        writer.println("    <button type='submit'>点击查看分类结果的JSON文件报告</button>");
        writer.println("</form>");
        writer.println("<a class='button' href='" + p + "' download=\"res.json\">开始下载分类结果的JSON文件报告</a>");
        writer.println("<script type=\"text/javascript\" src=\"js/checkStatic.js\"></script>");
        inputStream.close();
    }

    /**
     * 将文件从 Part 中提取出来并输出到指定的类别目录中。
     *
     * @param path       当前数据存储目录。
     * @param collection 包含存储 Part 对象的集合。
     * @throws IOException 输出数据时发生的异常。
     */
    private void extractedFiles(String path, Collection<Part> collection) throws IOException {
        for (Part part : collection) {
            if ("image".equals(part.getName())) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path + '/' + part.getSubmittedFileName()));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(part.getInputStream());
                IOUtils.copy(bufferedInputStream, bufferedOutputStream);
                bufferedInputStream.close();
                bufferedOutputStream.close();
            }
        }
    }
}
