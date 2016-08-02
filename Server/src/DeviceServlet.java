import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;

/**
 * Created by MinhThao on 28/07/2016.
 */
public class DeviceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s_id = request.getParameter("id");
        int id = parseInt(s_id);
        String s_state = request.getParameter("state");
        int state = parseInt(s_state);

        System.out.println("changed");
        Server.getInstance().setDeviceState(id, state);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
