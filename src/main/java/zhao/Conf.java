package zhao;


/**
 * 配置信息对象
 *
 * @author zhao
 */
public final class Conf {

    public final static String WEN_COOKIE = "Image-Classification-Web";

    public final static String HOME = "/IMW";

    public final static String LOGIN = "LogIn.jsp";

    public final static String LOGIN_SERVLET = "LoginServlet";

    public final static String TRAIN_SERVLET = "TrainServlet";

    public final static String C10_TRAIN_SERVLET = "C10TrainServlet";

    public final static String TRAIN_UP_SERVLET = "TrainUpServlet";

    public final static String TRAIN_RM_SERVLET = "TrainRmServlet";

    public final static String TRAIN_UP_HTML = "TrainUp.html";

    public final static String TRAIN_JSP = "Train.jsp";

    public final static String USE_MODEL_SERVLET = "UseModelServlet";

    public final static String USE_MODEL_HTML = "useModel.html";

    /**
     * python训练脚本文件的路径
     */
    public final static String TRAIN_PYTHON_PATH = "D:\\MyGitHub\\Image-Classification-Web\\pyLib\\train.py";

    /**
     * python cifar10_train.py 训练脚本文件的路径
     */
    public final static String C10_TRAIN_PYTHON_PATH = "D:\\MyGitHub\\Image-Classification-Web\\pyLib\\cifar10_train.py";

    /**
     * python使用脚本文件的路径
     */
    public final static String USING_PYTHON_PATH = "D:\\MyGitHub\\Image-Classification-Web\\pyLib\\use.py";

    /**
     * 训练数据集存储目录
     */
    public final static String TRAIN_DIR = "F:/trainDir";

    /**
     * 训练数据模型存储目录
     */
    public final static String MODEL_DIR = "F:/modelDir";

    /**
     * 模型使用时的图像暂存文件目录。
     */
    public final static String IMAGE_USE_DIR = "F:\\IMW_IMAGE\\USE\\";

    /**
     * 模型训练时的个人空间目录路径地址
     */
    public final static String IMAGE_TRAIN_DIR = "/IMW/trainDir/";

    /**
     * 最大用户同时在线数量
     */
    public final static int USER_MAX_COUNT = 5;
}
