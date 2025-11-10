package controller.feature;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Employee;
import dal.EmployeeRoleDBContext;
import dal.EmployeeDBContext;

public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Employee account = (Employee) session.getAttribute("auth");
        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        EmployeeRoleDBContext erDB = new EmployeeRoleDBContext();
        account.setRoles(erDB.getRolesByEmployeeId(account.getId()));
        String roleName = account.getRoles().isEmpty() ? "Chưa có" : account.getRoles().get(0).getName();
        
        String divisionName = new EmployeeDBContext().getDivisionNameByEmployeeId(account.getId());



        req.setAttribute("account", account);
        req.setAttribute("roleName", roleName);
        req.setAttribute("divisionName", divisionName);


        req.getRequestDispatcher("/feature/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
