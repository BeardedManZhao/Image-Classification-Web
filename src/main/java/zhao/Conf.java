package zhao;


import zhao.core.user.ManagerUser;
import zhao.core.user.OrdinaryUser;

import java.io.File;
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

    /**
     * 服务器的软件路径 TODO 配置的时候需要修改
     */
    public final static String TOMCAT_PATH = "E:\\RunTime\\Apache-tomcat-9.0.56\\bin";

    /**
     * 神经网络程序存储目录 TODO 配置的时候需要修改
     */
    public final static String NN_PATH = "D:\\MyGithub\\Image-Classification-Web\\pyLib\\exe";

    /**
     * 网站核心运行目录，该目录将会作为系统重的运行核心数据存储目录 TODO 配置的时候需要修改
     */
    public final static String WORK_DIR = "F:";

    /**
     * python训练脚本文件的路径
     */
    public final static String TRAIN_PYTHON_PATH = NN_PATH + "\\train.pyc";
    /**
     * python cifar10_train.py 训练脚本文件的路径
     */
    public final static String C10_TRAIN_PYTHON_PATH = NN_PATH + "\\cifar10_train.pyc";
    /**
     * python mnist_train.py 训练脚本文件的路径
     */
    public final static String MNIST_PYTHON_PATH = NN_PATH + "\\mnist_train.pyc";
    /**
     * python使用脚本文件的路径
     */
    public final static String USING_PYTHON_PATH = NN_PATH + "\\use.pyc";
    /**
     * 训练数据集存储目录
     */
    public final static String TRAIN_DIR = WORK_DIR + "/trainDir";
    /**
     * 训练数据模型存储目录
     */
    public final static String MODEL_DIR = WORK_DIR + "/modelDir";
    /**
     * 模型使用时的图像暂存文件目录。
     */
    public final static String IMAGE_USE_DIR = WORK_DIR + "/IMW_IMAGE/USE/";

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
     * web中需要的神经网络模型构建是否成功
     */
    public final static boolean modelIsOk;
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
        // 检查神经网络系统是否覆写完毕
        File file = new File(NN_PATH);
        File[] files = file.listFiles();
        modelIsOk = file.exists() && file.isDirectory() && files != null && files.length > 0;
        if (modelIsOk) {
            LOGGER.info("读取到神经网络系统文件，并装载到WEB服务。");
        } else {
            LOGGER.warning("神经网络系统文件不存在，需要覆写，请调用网站自带脚本文件进行覆写系统。");
        }
    }
}
