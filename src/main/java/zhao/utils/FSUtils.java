package zhao.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件系统操作数据集类
 *
 * @author zhao
 */
public final class FSUtils {

    /**
     * 检查 1 级别目录中是否包含 2 级别目录
     *
     * @param dir1 1级别目录的名称
     * @param dir2 2级别目录的名称
     * @return 如果 1 包含 2 返回· true
     */
    public static boolean checkDirConDir(String dir1, String dir2) {
        return checkDirConDir(new File(dir1), dir2);
    }

    /**
     * 检查 1 级别目录中是否包含 2 级别目录
     *
     * @param dir1 1级别目录的名称
     * @param dir2 2级别目录的名称
     * @return 如果 1 包含 2 返回· true
     */
    public static boolean checkDirConDir(File dir1, String dir2) {
        if (dir1.exists() && dir1.isDirectory()) {
            File[] files = dir1.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (dir2.equals(file.getName())) return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除一个目录
     *
     * @param dir 需要被删除的目录
     * @return 删除之后的结果
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children == null) return true;
            for (String child : children) {
                if (!deleteDir(new File(dir, child))) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 将文件中的数据追加到指定的缓冲区对象中
     *
     * @param file2    需要被读取的文件对象
     * @param classStr 追加的缓冲区
     * @throws IOException error
     */
    public static void extractedData(File file2, StringBuilder classStr) throws IOException {
        if (file2.exists() && file2.isFile()) {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
            while (bufferedReader.ready()) {
                classStr.append(bufferedReader.readLine()).append('\t');
            }
            bufferedReader.close();
        }
    }
}
