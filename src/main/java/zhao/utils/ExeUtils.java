package zhao.utils;

import zhao.Conf;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

/**
 * 执行工具类
 *
 * @author zhao
 */
public final class ExeUtils {
    public final static Runtime runtime = Runtime.getRuntime();

    /**
     * 执行一个python脚本并获取其中的数据流对象
     *
     * @param pythonPath 需要被执行的python脚本
     * @param args       启动参数
     * @return 数据流
     * @throws IOException error
     */
    public static InputStream exePy(String pythonPath, String args) throws IOException {
        final Process process = runtime.exec("python " + pythonPath + " " + args);
        return new SequenceInputStream(process.getInputStream(), process.getErrorStream());
    }

    /**
     * 关闭网站服务器
     *
     * @param isLinux 如果使用 true 代表使用Linux的方式关闭服务器。
     * @throws IOException 关闭 error。
     */
    public static void closeWeb(boolean isLinux) throws IOException {
        if (isLinux) {
            runtime.exec(Conf.TOMCAT_PATH + "/shutdown.sh");
        } else {
            runtime.exec(Conf.TOMCAT_PATH + "/shutdown.bat");
        }
    }
}
