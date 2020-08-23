import Control.FunnelController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/settings")
public class SettingServlet extends HttpServlet {

    FunnelController funnelController;

    public SettingServlet() throws SQLException {
        funnelController = new FunnelController();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = resp.getWriter();

        try {
            String setting = funnelController.getSettings();
            printWriter.write(setting);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        printWriter.close();
    }

}
