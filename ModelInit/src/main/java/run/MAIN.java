package run;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 网站模型功能初始化模块，该模型是网站初始化任务对应的启动组件。
 *
 * @author 赵凌宇
 * 2023/5/10 17:31
 */
public class MAIN {

    /**
     * @param args 编译后的文件路径 以及装载好的新路径。
     * @throws IOException 装载失败异常抛出
     */
    public static void main(String[] args) throws IOException {
        Pattern pattern = Pattern.compile("\\.cpython-\\d*\\.");
        String outPath, inPath = "__pycache__";
        if (args.length < 2) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the numerical value of the WEB system configuration item [NN_PATH]");
            System.out.print("NN_PATH: ");
            outPath = scanner.nextLine();
        } else {
            outPath = args[1];
            inPath = args[0];
            System.out.println("NN_PATH: " + outPath);
        }
        {
            final File file1 = new File(outPath);
            final boolean ok1 = file1.exists() && file1.isDirectory();
            if (!(ok1)) {
                if (file1.mkdirs()) {
                    System.out.println("info >>> create dir : " + outPath);
                }
            } else {
                System.out.println("error >>> dir not exists: " + outPath);
            }
        }
        // 获取到当前目录中的 __pycache__ 中的所有文件
        File cache = new File(inPath);
        File[] files = cache.listFiles();
        if (files != null && files.length != 0 && cache.exists() && cache.isDirectory()) {
            System.out.println("info >>> 接收到信息，开始查询子文件。");
            for (File file : files) {
                String name = file.getName();
                System.out.print("info >>> 装载文件：" + name);
                FileOutputStream fileOutputStream = new FileOutputStream(outPath + '/' + pattern.matcher(name).replaceFirst("."));
                FileInputStream fileInputStream = new FileInputStream(file);
                IOUtils.copy(fileInputStream, fileOutputStream);
                fileOutputStream.close();
                fileInputStream.close();
                System.out.println("\t==>\tok");
            }
            System.out.println("info >>> 神经网络系统覆写成功!!!!");
        } else {
            System.out.println("error >>> 神经网络系统模块未编译!!!!!");
        }
    }
}
