package zhao.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 向客户端回复 500 号错误信息。
 *
 * @author zhao
 */
public class ToFZZ implements TaskConsumer {

    public final static ToFZZ TO_FZZ = new ToFZZ();

    /**
     * 需要被执行的附加任务
     *
     * @param httpServletRequest  http请求对象
     * @param httpServletResponse http 回复对象
     */
    @Override
    public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
