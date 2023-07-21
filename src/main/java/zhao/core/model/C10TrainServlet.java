package zhao.core.model;

import zhao.Conf;
import zhao.core.user.OrdinaryUser;
import zhao.core.user.User;
import zhao.utils.ExeUtils;
import zhao.utils.FSUtils;
import zhao.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static zhao.utils.HttpUtils.intiCheck;

@WebServlet(name = "C10TrainServlet", value = "/C10TrainServlet")
public class C10TrainServlet extends TrainServlet {

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
        String train_epochs;
        {
            // 检查用户模型空间 and json 空间
            if ((!FSUtils.checkOrMkdirs(response, new File(user.getModelDir()))) ||
                    (!FSUtils.checkOrMkdirs(response, new File(user.getJsonDir())))) {
                return;
            }
            final String train_epochs1 = request.getParameter("train_epochs");
            train_epochs = train_epochs1 != null ? train_epochs1 : "1";
        }
        trainReturn(user, writer, user1 -> {
            // 获取到用户的模型存储目录
            String savePath = user1.getModelDir() + "/Model";
            // 获取到用户的类别文件存储目录
            String classPath = user1.getModelDir() + "/classList.txt";
            // 调用 python 开始训练 并获取到结果数据输入流
            try {
                return ExeUtils.exePy(
                        Conf.C10_TRAIN_PYTHON_PATH,
                        train_epochs + ' ' +
                                savePath + ' ' +
                                classPath + ' ' +
                                user1.getJsonDir() + "/lossAcc.json"
                );
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

}
