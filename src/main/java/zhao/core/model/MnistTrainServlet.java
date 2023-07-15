package zhao.core.model;

import zhao.Conf;
import zhao.core.user.OrdinaryUser;
import zhao.core.user.User;
import zhao.utils.ExeUtils;
import zhao.utils.HttpUtils;
import zhao.utils.StrUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static zhao.utils.HttpUtils.intiCheck;

@WebServlet(name = "MnistTrainServlet", value = "/MnistTrainServlet")
public class MnistTrainServlet extends TrainServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final User user = intiCheck(request, response);
        if (user.equals(OrdinaryUser.DEFAULT_USER)) return;
        if (!HttpUtils.checkCanTrain(response)) {
            return;
        }

        final PrintWriter writer = response.getWriter();
        final String train_epochs = StrUtils.ifNull(request.getParameter("train_epochs"), "1");
        {
            final File file = new File(user.getModelDir());
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    response.getWriter().write("<script>alert('您的个人模型空间损坏，请更换个人空间尝试继续。')</script>");
                }
            }
        }
        trainReturn(user, writer, user1 -> {
            // 获取到用户的模型存储目录
            String savePath = user1.getModelDir() + "/Model";
            // 获取到用户的类别文件存储目录
            String classPath = user1.getModelDir() + "/classList.txt";
            // 调用 python 开始训练 并获取到结果数据输入流
            try {
                return ExeUtils.exePy(
                        Conf.MNIST_PYTHON_PATH,
                        train_epochs + ' ' +
                                savePath + ' ' +
                                classPath + ' ' +
                                user1.getModelDir() + "/outputJson.json"
                );
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

}
