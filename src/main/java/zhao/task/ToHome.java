package zhao.task;

import zhao.Conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 将当前页面跳转到主页的封装 task
 *
 * @author zhao
 */
public class ToHome implements TaskConsumer {

    public final static TaskConsumer TO_HOME = new ToHome();

    @Override
    public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        try {
            httpServletResponse.sendRedirect(Conf.HOME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
