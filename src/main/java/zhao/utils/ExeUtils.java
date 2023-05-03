package zhao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

/**
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
}
