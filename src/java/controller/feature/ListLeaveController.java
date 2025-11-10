package controller.feature;

import dal.EmployeeRoleDBContext;
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

        EmployeeRoleDBContext erDB = new EmployeeRoleDBContext();
        employee.setRoles(erDB.getRolesByEmployeeId(employee.getId()));

        boolean isDivisionHead = employee.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("divisionhead"));

        boolean isManager = employee.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("manager"));

        LeaveApplicationDBContext db = new LeaveApplicationDBContext();

        if (isDivisionHead) {
            List<LeaveApplication> allLeaves = db.getLeavesForDivisionHead(employee.getId());
            req.setAttribute("allLeaves", allLeaves);
            req.setAttribute("roleView", "divisionhead");
        } 
        else if (isManager) {
            List<LeaveApplication> myLeaves = db.getLeavesByEmployee(employee.getId());
            List<LeaveApplication> staffLeaves = db.getLeavesByManager(employee.getId());
            req.setAttribute("myLeaves", myLeaves);
            req.setAttribute("staffLeaves", staffLeaves);
            req.setAttribute("roleView", "manager");
        } 
        else {
            List<LeaveApplication> leaves = db.getLeavesByEmployee(employee.getId());
            req.setAttribute("leaves", leaves);
            req.setAttribute("roleView", "employee");
        }

        req.getRequestDispatcher("/feature/listleave.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
