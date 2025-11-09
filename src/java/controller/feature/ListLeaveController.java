package controller.feature;

import dal.LeaveApplicationDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Employee;
import model.LeaveApplication;

public class ListLeaveController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("auth");
        if (employee == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        LeaveApplicationDBContext db = new LeaveApplicationDBContext();
        List<LeaveApplication> leaves = db.getLeavesByEmployee(employee.getId());

        req.setAttribute("leaves", leaves);
        req.getRequestDispatcher("/feature/listleave.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
