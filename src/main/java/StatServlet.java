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

@WebServlet("/stats")
public class StatServlet extends HttpServlet {

    private static final String SPLIT = ",";
    private static final String OUTLETS = "outlets";
    private static final String BRANDS = "brands";
    private static final String TARGET = "target";
    private static final String CATEGORY = "category";
    private static final String DATE_START = "date_start";
    private static final String DATE_END = "date_end";
    FunnelController funnelController;

    public StatServlet() throws SQLException {
        funnelController = new FunnelController();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String str = req.getParameter(OUTLETS);
        List<String> sources = null;
        if (str!=null)
        {
            sources = Arrays.asList(str.split(SPLIT));
        }
        str = req.getParameter(BRANDS);
        List<String> brands=null;
        if (str!=null)
        {
            brands = Arrays.asList(str.split(SPLIT));
        }
        String clientType = req.getParameter(TARGET);
        String category = req.getParameter(CATEGORY);
        String dateStart = req.getParameter(DATE_START);
        String dateEnd = req.getParameter(DATE_END);

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = resp.getWriter();

        if(clientType==null||dateStart==null||dateEnd==null)
        {
            resp.getWriter().print(HttpServletResponse.SC_BAD_REQUEST);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }else
        {
            try {
                String statJson = funnelController.getStats(sources, clientType, brands, category, dateStart, dateEnd);
                printWriter.write(statJson);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        printWriter.close();
    }
}
