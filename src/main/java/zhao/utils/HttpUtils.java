package zhao.utils;

import org.apache.commons.io.IOUtils;
import zhao.Conf;
import zhao.core.user.User;
import zhao.task.ToLogin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Http 协议处理工具类
 *
 * @author zhao
 */
public final class HttpUtils {
    /**
     * 检查当前用户是否已经登录，如果登录就返回 cookie 对象
     *
     * @param httpServletRequest 需要被检查的请求
     * @return 如果当前用于已登录，则返回 cookie 反之返回null
     */
    public static Cookie checkUserIsLogin(HttpServletRequest httpServletRequest) {
        final Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Conf.WEN_COOKIE.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 转换编码 同时检查登录情况
     *
     * @param request  请求对象
     * @param response 回复对象
     * @return 检查到的用户或 default 用户
     * @throws UnsupportedEncodingException 不支持编码转换错误
     */
    public static User intiCheck(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setCharacterEncoding("GBK");
        request.setCharacterEncoding("GBK");
        return User.checkCookieUser(request, response, ToLogin.TO_LOGIN);
    }

    /**
     * 弹出提示框并且直接返回到上一级中。
     *
     * @param printWriter 回复数据流
     * @param alertStr    提示框中的数据
     */
    public static void alertBack(PrintWriter printWriter, String alertStr) {
        printWriter.println("<script>");
        printWriter.println("alert('" + alertStr + "');");
        printWriter.println("window.history.back()");
        printWriter.println("</script>");
    }

    /**
     * 检查神经网络系统当前是否正常运行，如果不正常就返回一句话。
     */
    public static boolean checkCanTrain(HttpServletResponse response) throws IOException {
        // 检查神经网络的状态 如果不正常就直接返回一句话
        if (!Conf.checkNeural_network_status()) {
            HttpUtils.alertBack(response.getWriter(), "神经网络系统当前不可用，系统编码：" + Conf.Neural_network_status);
            return false;
        }
        return true;
    }


    /**
     * 使用指定的路径对应的json文件，在页面中构建一个损失函数与精度数值对应的图表。
     *
     * @param writer 页面数据流
     * @param file   包含记录数据的json文件
     * @throws IOException IO错误
     */
    public static void makeLossAccBar(PrintWriter writer, File file) throws IOException {
        // 构建图表 首先获取到文件对象 如果文件存在 再继续构建
        if (file.exists()) {
            // 文件存在 开始构建图表盒子以及数据流
            writer.println("<div style=\"width: 100%; height: 500px\" id=\"vis\"></div>");
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            writer.println("<script>");
            writer.println("const d = document.getElementById('vis')");
            writer.write("lossAccBar(d, '训练数据', ");
            IOUtils.copy(bufferedInputStream, writer);
            writer.println(')');
            writer.println("</script>");
            bufferedInputStream.close();
        }
    }

    /**
     * 使用用户空间中存储的json文件，在页面中构建一个损失函数与精度数值对应的图表。
     *
     * @param user   指定的用户对象
     * @param writer 页面数据流
     * @throws IOException IO错误
     */
    public static void makeLossAccBar(User user, PrintWriter writer) throws IOException {
        makeLossAccBar(writer, new File(user.getModelDir() + "/outputJson.json"));
    }
}
