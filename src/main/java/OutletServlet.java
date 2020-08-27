import Control.FunnelController;
import Control.DatabaseConnect;
import Model.Id;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/outlets")
public class OutletServlet extends HttpServlet {


    FunnelController funnelController;
    private static final String NAME = "name";
    private static final String PASSWORD = "333178";
    private DatabaseConnect dbConnect;


    public OutletServlet() throws SQLException {
        funnelController = new FunnelController();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String name = req.getParameter(NAME);

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = resp.getWriter();
        String statJson = null;
        try {
            statJson=funnelController.newMet(name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        printWriter.write(statJson);


    }
}
