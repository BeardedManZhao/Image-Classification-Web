package zhao.core.model;

import org.apache.commons.io.IOUtils;
import zhao.Conf;
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

    private final static String name = "zhao";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("GBK");
        final Part image = request.getParts().iterator().next();
        // 获取到指定的文件存储目录
        String path = "F:/modelDir/" + name + "/image";
        // 将文件保存
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(image.getInputStream());
        IOUtils.copy(bufferedInputStream, bufferedOutputStream);
        bufferedInputStream.close();
        bufferedOutputStream.close();
        // 开始使用脚本
        final String s = Conf.MODEL_DIR + '/' + name;
        final InputStream inputStream = ExeUtils.exePy(Conf.USING_PYTHON_PATH, s + "/Model " + s + "/image " + Conf.MODEL_DIR + '/' + name + "/classList.txt");
        IOUtils.copy(inputStream, response.getWriter(), "GBK");
        inputStream.close();
    }
}
