package zhao.utils;


import java.io.File;

/**
 * 文件系统操作数据集类
 *
 * @author zhao
 */
public final class FSUtils {
//
//    /**
//     * 检查当前用户是否已经登录，如果登录就返回 cookie 对象
//     * @param httpServletRequest 需要被检查的请求
//     * @return 如果当前用于已登录，则返回 cookie 反之返回null
//     */
//    public static Cookie checkUserIsLogin(HttpServletRequest httpServletRequest){
//        for (Cookie cookie : httpServletRequest.getCookies()) {
//            if (Conf.WEN_COOKIE.equals(cookie.getName())){
//                return cookie;
//            }
//        }
//        return null;
//    }

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
}
