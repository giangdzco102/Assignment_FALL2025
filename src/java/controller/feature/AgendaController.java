package controller.feature;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Employee;
import java.util.List;

public class AgendaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("auth");
        if (employee == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Kiểm tra quyền truy cập
        List<String> features = (List<String>) session.getAttribute("featureURLs");
        if (features == null || !features.contains("/feature/agenda")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this page.");
            return;
        }

        req.getRequestDispatcher("/feature/agenda.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
