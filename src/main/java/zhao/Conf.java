package zhao;


/**
 * 配置信息对象
 *
 * @author zhao
 */
public final class Conf {

    public final static String WEN_COOKIE = "Image-Classification-Web";

    public final static String TRAIN_SERVLET = "TrainServlet";

    public final static String TRAIN_UP_SERVLET = "TrainUpServlet";

    public final static String TRAIN_UP_HTML = "TrainUp.html";

    public final static String TRAIN_JSP = "Train.jsp";

    public final static String USE_MODEL_SERVLET = "UseModelServlet";

    public final static String USE_MODEL_HTML = "useModel.html";

    /**
     * python训练脚本文件的路径
     */
    public final static String TRAIN_PYTHON_PATH = "E:/MyProject/Python_Project/pythonProject1/train.py";

    /**
     * python使用脚本文件的路径
     */
    public final static String USING_PYTHON_PATH = "E:/MyProject/Python_Project/pythonProject1/use.py";

    /**
     * 训练数据集存储目录
     */
    public final static String TRAIN_DIR = "F:/trainDir";

    /**
     * 训练数据模型存储目录
     */
    public final static String MODEL_DIR = "F:/modelDir";
}
