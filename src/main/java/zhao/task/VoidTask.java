package zhao.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 无任何处理的逻辑实现类
 *
 * @author zhao
 */
public final class VoidTask implements TaskConsumer {

    public final static VoidTask VOID_TASK = new VoidTask();

    @Override
    public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }
}
