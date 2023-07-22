package zhao.utils;

import dialogue.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * 设备运行工具类
 *
 * @author zhao
 */
public final class RunUtils {

    public final static Runtime RUNTIME = Runtime.getRuntime();

    /**
     * 在本机运行一个命令，并将结果返回到指定的数据输出流中
     *
     * @param command     需要运行的命令
     * @param printWriter 运行结果返回的输出打印流。
     */
    public static void runCommand(String command, PrintWriter printWriter) {
        final Process exec;
        try {
            exec = RUNTIME.exec(command);
            if (printWriter != null) {
                final InputStream errorStream = exec.getErrorStream();
                org.apache.commons.io.IOUtils.copy(errorStream, printWriter);
                final String stringByStream = IOUtils.getStringByStream(errorStream);
                if (stringByStream.length() == 0) {
                    final InputStream inputStream = exec.getInputStream();
                    org.apache.commons.io.IOUtils.copy(inputStream, printWriter);
                    IOUtils.close(inputStream);
                }
                IOUtils.close(errorStream);
            }
        } catch (IOException e) {
            printWriter.println(e);
        }
    }
}
