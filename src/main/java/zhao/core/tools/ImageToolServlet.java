package zhao.core.tools;

import zhao.Conf;
import zhao.algorithmMagic.operands.matrix.ColorMatrix;
import zhao.algorithmMagic.operands.matrix.ImageMatrix;
import zhao.algorithmMagic.utils.dataContainer.KeyValue;
import zhao.algorithmMagic.utils.transformation.Transformation;
import zhao.utils.HttpUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 图像工具处理类 TODO 需要修正 烂尾啦
 *
 * @author zhao
 */
@WebServlet(name = "ImageToolServlet", value = "/ImageToolServlet")
public class ImageToolServlet extends HttpServlet {

    private final static Pattern split = Pattern.compile(".*?->.*");

    /**
     * 针对不同的处理需求提供不同的处理方式
     */
    private final static HashMap<String, Transformation<KeyValue<ColorMatrix, String[]>, ColorMatrix>> hash = new HashMap<>();

    static {
        hash.put("as::colorReversal", (Transformation<KeyValue<ColorMatrix, String[]>, ColorMatrix>) colorMatrixKeyValue -> colorMatrixKeyValue.getKey().colorReversal(false));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取到 框架名称::函数
        final String s = req.getParameter("useFrameWork") + "::" + req.getParameter("function");
        // 获取到其对应的处理类并进行处理
        final Transformation<KeyValue<ColorMatrix, String[]>, ColorMatrix> transformation = hash.get(s);
        if (transformation == null) {
            final PrintWriter writer = resp.getWriter();
            HttpUtils.alertBack(writer, "您请求的函数或框架不存在或不被支持：" + s);
            return;
        }
        // 开始处理任务 首先读取图像数据
        final ImageMatrix image = ImageMatrix.parse(ImageIO.read(req.getPart("image").getInputStream()));
        // 然后开始进行处理 并将处理结果输出到 /IMW/IMW_IMAGE/use/ImageTool 虚拟路径对应的文件路径
        transformation.function(
                new KeyValue<>(image, split.split(req.getParameter("args")))
        ).save(Conf.IMAGE_USE_DIR + "ImageTool");
    }
}
