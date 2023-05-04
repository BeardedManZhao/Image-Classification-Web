package zhao.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TaskConsumer {

    void run(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}