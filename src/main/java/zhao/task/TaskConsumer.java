package zhao.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 附加任务处理操作类接口
 */
public interface TaskConsumer {

    /**
     * 需要被执行的附加任务
     *
     * @param httpServletRequest  http请求对象
     * @param httpServletResponse http 回复对象
     */
    void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException;
}