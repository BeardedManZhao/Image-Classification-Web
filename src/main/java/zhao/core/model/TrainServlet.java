package zhao.core.model;

import org.apache.commons.io.IOUtils;
import zhao.Conf;
import zhao.utils.ExeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * 训练服务类，在数据上传成功之后将会跳转到该类对应的门户页面
 */
@WebServlet(name = "TrainServlet", value = "/TrainServlet")
public class TrainServlet extends HttpServlet {

    private final static String name = "zhao";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("GBK");
        final PrintWriter writer = response.getWriter();
        writer.write("<!DOCTYPE html>");
        writer.write(" <html lang=\"zh\">");
        writer.write(" <head>");
        writer.write(" <meta charset=\"GBK\">");
        writer.write(" <title>训练结果</title>");
        writer.write(" </head>");
        writer.write("<h2>训练日志展示</h2>\n");
        writer.write("<hr>\n");
        writer.write("<pre>");
        // 调用demo.py中的method1方法
        final String s = Conf.TRAIN_DIR + '/' + name + " " + Conf.MODEL_DIR + '/' + name + "/Model ";
        final InputStream inputStream = ExeUtils.exePy(Conf.TRAIN_PYTHON_PATH, s + Conf.MODEL_DIR + '/' + name + "/classList.txt");
        IOUtils.copy(inputStream, response.getWriter(), "GBK");
        writer.write("</pre>");
        writer.write("<br>\n");
        writer.println("模型已经保存至您的个人目录中，您可以随时进行模型的使用。");
        writer.write(s);
    }
}
