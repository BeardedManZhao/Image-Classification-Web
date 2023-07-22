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
    public final static String USE_MODEL_SELECT_HTML = "useModelSelect.html";
    public final static String USE_MODEL_BATCH_SERVLET = "UseModelBatchServlet";
    public final static String NN_OVER_SERVLET = "NNOverServlet";
    public final static String NN_ERROR_HTML = "NeuralNetworkError.html";
    public final static String JSON_SERVLET = "JsonServlet";

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
     * 网站中产生的json文件存储位置
     */
    public final static String JSON_DIR = WORK_DIR + "/jsonDir";
    /**
     * python训练脚本文件的路径
     */
    public final static String TRAIN_PYTHON_PATH = NN_PATH + "/train.pyc";
    /**
     * python cifar10_train.py 训练脚本文件的路径
     */
    public final static String C10_TRAIN_PYTHON_PATH = NN_PATH + "/cifar10_train.pyc";
    /**
     * python mnist_train.py 训练脚本文件的路径
     */
    public final static String MNIST_PYTHON_PATH = NN_PATH + "/mnist_train.pyc";
    /**
     * python使用脚本文件的路径
     */
    public final static String USING_PYTHON_PATH = NN_PATH + "/use.pyc";
    /**
     * python批量使用脚本文件的路径
     */
    public final static String USING_BATCH_PYTHON_PATH = NN_PATH + "/useBatch.pyc";
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
     * 模型使用是的批图像暂存文件目录。
     */
    public final static String IMAGE_USE_BATCH_DIR = WORK_DIR + "/IMW_IMAGE/batchUse/";

    /**
     * 模型使用时的图像暂存文件目录。
     */
    public final static String IMAGE_USE_DIR_PATH = "/IMW/IMW_IMAGE/use/";

    /**
     * 模型使用是的批图像暂存文件目录。
     */
    public final static String IMAGE_USE_BATCH_DIR_PATH = "/IMW/IMW_IMAGE/batchUse/";

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
    public final static boolean NEURAL_NETWORK_READY;

    /**
     * web中所有的配置项目被封装成的一个json数据的通用部分 这部分在程序启动的时候就会初始化完毕。
     * 必须要通过函数 getConfJson 获取到这部分数据！！！
     */
    private final static String CONF_JSON;

    /**
     * 神经网络系统当前的状态
     * -1 = 神经网络系统功能不完善或崩溃。
     * 0 = 神经网络系统功能正常运行。
     * 1 = 神经网络系统功能被禁用。
     */
    public static byte Neural_network_status;

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
        NEURAL_NETWORK_READY = file.exists() && file.isDirectory() && files != null && files.length > 0;
        if (NEURAL_NETWORK_READY) {
            LOGGER.info("读取到神经网络系统文件，并装载到WEB服务。");
            Neural_network_status = 0;
        } else {
            LOGGER.warning("神经网络系统文件不存在，需要覆写，请调用网站自带脚本文件进行覆写系统。");
            Neural_network_status = -1;
        }
        // 初始化 配置项 的 json 字符串
        CONF_JSON = '{' +
                "'WEN_COOKIE' : " + WEN_COOKIE + "'," +
                "'HOME' : '" + HOME + "'," +
                "'LOGIN_SERVLET' : '" + LOGIN_SERVLET + "'," +
                "'TRAIN_SERVLET' : '" + TRAIN_SERVLET + "'," +
                "'C10_TRAIN_SERVLET' : '" + C10_TRAIN_SERVLET + "'," +
                "'MNIST_TRAIN_SERVLET' : '" + MNIST_TRAIN_SERVLET + "'," +
                "'TRAIN_UP_SERVLET' : '" + TRAIN_UP_SERVLET + "'," +
                "'TRAIN_RM_SERVLET' : '" + TRAIN_RM_SERVLET + "'," +
                "'TRAIN_UP_HTML' : '" + TRAIN_UP_HTML + "'," +
                "'TRAIN_JSP' : '" + TRAIN_JSP + "'," +
                "'USE_MODEL_SERVLET' : '" + USE_MODEL_SERVLET + "'," +
                "'USE_MODEL_SELECT_HTML' : '" + USE_MODEL_SELECT_HTML + "'," +
                "'USE_MODEL_BATCH_SERVLET' : '" + USE_MODEL_BATCH_SERVLET + "'," +
                "'NN_OVER_SERVLET' : '"  + NN_OVER_SERVLET + "'," +
                "'NN_ERROR_HTML' : '" + NN_ERROR_HTML + "'," +
                "'JSON_SERVLET' : '" + JSON_SERVLET + "'," +
                "'TOMCAT_PATH' : '" + TOMCAT_PATH + "'," +
                "'NN_PATH' : '" + NN_PATH + "'," +
                "'WORK_DIR' : '" + WORK_DIR + "'," +
                "'JSON_DIR : '" + JSON_DIR + "'," +
                "'TRAIN_PYTHON_PATH' : '" + TRAIN_PYTHON_PATH + "'," + 
                "'C10_TRAIN_PYTHON_PATH' : '" + C10_TRAIN_PYTHON_PATH + "'," +
                "'MNIST_PYTHON_PATH' : '" + MNIST_PYTHON_PATH + "'," +
                "'USING_PYTHON_PATH' : '" + USING_PYTHON_PATH + "'," +
                "'TRAIN_DIR' : '" + TRAIN_DIR + "'," + 
                "'MODEL_DIR' : '" + MODEL_DIR + "'," +
                "'IMAGE_USE_DIR' : '" + IMAGE_USE_DIR + "'," +
                "'WEB_CONFIG_JSP' : '" + WEB_CONFIG_JSP + "'," +
                "'CONF_UPDATE_SERVLET' : '" + CONF_UPDATE_SERVLET + "'," +
                "'NEURAL_NETWORK_READY' : " + NEURAL_NETWORK_READY + ",";
    }

    /**
     * 更新神经网络系统的状态
     *
     * @param newStatus 新的神经网络系统的状态字符串。
     */
    public static void updateNeural_network_status(String newStatus) {
        switch (newStatus) {
            case "running":
                Neural_network_status = 0;
                break;
            case "disable":
                Neural_network_status = 1;
                break;
            default:
                Neural_network_status = -1;
                break;
        }
    }

    /**
     * 检查神经网络系统运行状态是否良好，如果不良好就返回false。
     *
     * @return 布尔数值，true 代表神经网络正常。
     */
    public static boolean checkNeural_network_status() {
        return Neural_network_status == 0;
    }

    /**
     * 检查神经网络系统运行状态是否良好。
     *
     * @return 布尔数值，true 代表神经网络崩溃。
     */
    public static boolean checkNeural_network_statusIsClose() {
        return Neural_network_status == -1;
    }

    /**
     *
     * @return 完整的 web配置项
     */
    public static String getConfJson(){
        return CONF_JSON +
                "'Neural_network_status' : " + Neural_network_status + "," +
                "'LOGIN' : '" + LOGIN + "'," +
                "'SYSTEM_TYPE' : '" + SYSTEM_TYPE + "'," +
                "'USER_MAX_COUNT' : '" + USER_MAX_COUNT +
                '}';
    }

}
