package zhao;


import zhao.core.user.ManagerUser;
import zhao.core.user.OrdinaryUser;

import java.util.logging.Logger;

/**
 * 配置信息对象
 *
 * @author zhao
 */
public final class Conf {

    public final static String WEN_COOKIE = "Image-Classification-Web";
    public final static String HOME = "/IMW";
    public final static String LOGIN_SERVLET = "LoginServlet";
    public final static String TRAIN_SERVLET = "TrainServlet";
    public final static String C10_TRAIN_SERVLET = "C10TrainServlet";
    public final static String MNIST_TRAIN_SERVLET = "MnistTrainServlet";
    public final static String TRAIN_UP_SERVLET = "TrainUpServlet";
    public final static String TRAIN_RM_SERVLET = "TrainRmServlet";
    public final static String TRAIN_UP_HTML = "TrainUp.html";
    public final static String TRAIN_JSP = "Train.jsp";
    public final static String USE_MODEL_SERVLET = "UseModelServlet";
    public final static String USE_MODEL_HTML = "useModel.html";
/*
    /**
     * 服务器的软件路径
     * /
    public final static String TOMCAT_PATH = "E:\\RunTime\\Apache-tomcat-9.0.56\\bin";
    /**
     * python训练脚本文件的路径
     * /
    public final static String TRAIN_PYTHON_PATH = "D:\\MyGitHub\\Image-Classification-Web\\pyLib\\train.py";
    /**
     * python cifar10_train.py 训练脚本文件的路径
     * /
    public final static String C10_TRAIN_PYTHON_PATH = "D:\\MyGitHub\\Image-Classification-Web\\pyLib\\cifar10_train.py";
    /**
     * python cifar10_train.py 训练脚本文件的路径
     * /
    public final static String MNIST_PYTHON_PATH = "D:\\MyGitHub\\Image-Classification-Web\\pyLib\\mnist_train.py";
    /**
     * python使用脚本文件的路径
     * /
    public final static String USING_PYTHON_PATH = "D:\\MyGitHub\\Image-Classification-Web\\pyLib\\use.py";
    /**
     * 训练数据集存储目录
     * /
    public final static String TRAIN_DIR = "F:/trainDir";
    /**
     * 训练数据模型存储目录
     * /
    public final static String MODEL_DIR = "F:/modelDir";
    /**
     * 模型使用时的图像暂存文件目录。
     * /
    public final static String IMAGE_USE_DIR = "F:\\IMW_IMAGE\\USE\\";
*/

    /**
     * 服务器的软件路径
     */
    public final static String TOMCAT_PATH = "D:\\Runtime\\TomCat_Server\\Apache-tomcat-9.0.56\\bin";
    /**
     * python训练脚本文件的路径
     */
    public final static String TRAIN_PYTHON_PATH = "D:\\MyGithub\\Image-Classification-Web\\pyLib\\train.py";
    /**
     * python cifar10_train.py 训练脚本文件的路径
     */
    public final static String C10_TRAIN_PYTHON_PATH = "D:\\MyGithub\\Image-Classification-Web\\pyLib\\cifar10_train.py";
    /**
     * python mnist_train.py 训练脚本文件的路径
     */
    public final static String MNIST_PYTHON_PATH = "D:\\MyGithub\\Image-Classification-Web\\pyLib\\mnist_train.py";
    /**
     * python使用脚本文件的路径
     */
    public final static String USING_PYTHON_PATH = "D:\\MyGithub\\Image-Classification-Web\\pyLib\\use.py";
    /**
     * 训练数据集存储目录
     */
    public final static String TRAIN_DIR = "D:/IMC-Z/trainDir";
    /**
     * 训练数据模型存储目录
     */
    public final static String MODEL_DIR = "D:/IMC-Z/modelDir";
    /**
     * 模型使用时的图像暂存文件目录。
     */
    public final static String IMAGE_USE_DIR = "D:/IMC-Z/IMW_IMAGE/USE/";

    /**
     * 模型训练时的个人空间目录路径地址
     */
    public final static String IMAGE_TRAIN_DIR = "/IMW/trainDir/";
    /**
     * 网站系统配置页面路径
     */
    public final static String WEB_CONFIG_JSP = "WebConf.jsp";
    /**
     * 网站系统配置数据更新服务
     */
    public final static String CONF_UPDATE_SERVLET = "ConfUpdateServlet";
    /**
     * 网站系统使用的日志打印器。
     */
    public final static Logger LOGGER = Logger.getLogger("IMC-Z");
    /**
     * 初始化或进入个人空间的资源名称
     */
    public static String LOGIN = "LogIn.jsp";
    /**
     * 网站运行时系统类型。
     */
    public static String SYSTEM_TYPE = "WIN";
    /**
     * 最大用户同时在线数量 可变网站设置参数
     */
    public static int USER_MAX_COUNT = 5;

    static {
        // 初始化管理者
        OrdinaryUser.USER_HASH_MAP.put("root", new ManagerUser("root", "zhao-123123123"));
        LOGGER.info("管理者初始化成功 = " + OrdinaryUser.USER_HASH_MAP.get("root"));
    }
}
