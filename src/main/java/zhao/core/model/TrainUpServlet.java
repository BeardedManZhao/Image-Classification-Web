package zhao.core.model;

import org.apache.commons.io.IOUtils;
import zhao.Conf;
import zhao.core.user.OrdinaryUser;
import zhao.core.user.User;
import zhao.task.ToLogin;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 训练数据上传服务类
 */
@WebServlet(name = "TrainUpServlet", value = "/TrainUpServlet")
@MultipartConfig
public class TrainUpServlet extends HttpServlet {

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
        final File file1 = new File(user.getModelDir());
        if (!file1.exists()) {
            if (!file1.mkdirs()) {
                throw new IOException("个人文件目录无法成功创建：" + file1.getAbsolutePath());
            }
        }
        // key 类别名称  value 对应类别的所有的part
        HashMap<String, ArrayList<Part>> hashMap = new HashMap<>();
        for (Part part : request.getParts()) {
            String name = part.getName();
            ArrayList<Part> parts = hashMap.get(name);
            if (parts != null) {
                parts.add(part);
            } else {
                ArrayList<Part> objects = new ArrayList<>();
                objects.add(part);
                hashMap.put(name, objects);
            }
        }
        // 保存文件
        final PrintWriter writer = response.getWriter();
        final BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(user.getModelDir() + "/tempClassList.txt"), "GBK"
                )
        );
        final int size = hashMap.size();
        int index1 = 0;
        for (Map.Entry<String, ArrayList<Part>> entry : hashMap.entrySet()) {
            final String key = entry.getKey();
            // 获取到当前文件类别对应的文件夹名称
            String path = user.getTrainDir() + '/' + key;
            File file = new File(path);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (!mkdirs)
                    writer.println("<script>alert('文件上传失败，服务器内文件目录无法创建：" + path + "');history.back()</script>");
            }
            bufferedWriter.write(key);
            if (++index1 < size) {
                bufferedWriter.newLine();
            }
            // 开始将当前类别的所有的文件数据上传到指定目录
            int index = 0;
            for (Part part : entry.getValue()) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path + '/' + (++index) + ".jpg"));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(part.getInputStream());
                IOUtils.copy(bufferedInputStream, bufferedOutputStream);
                bufferedInputStream.close();
                bufferedOutputStream.close();
            }
        }
        bufferedWriter.flush();
        bufferedWriter.close();
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.sendRedirect(Conf.TRAIN_JSP);
    }
}
