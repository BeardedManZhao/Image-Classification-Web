package zhao.task;

import zhao.Conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跳转到 login 页面的封装 task
 *
 * @author zhao
 */
public class ToLogin implements TaskConsumer {

    public final static ToLogin TO_LOGIN = new ToLogin();

    @Override
    public void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        try {
            httpServletResponse.sendRedirect(Conf.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
