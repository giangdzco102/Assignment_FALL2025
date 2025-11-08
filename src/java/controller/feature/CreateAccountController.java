package controller.feature;

import dal.DivisionDBContext;
import dal.EmployeeDBContext;
import dal.EmployeeRoleDBContext;
import dal.RoleDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Employee;
import model.EmployeeRole;
import model.Division;
import model.Role;

public class CreateAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DivisionDBContext divDB = new DivisionDBContext();
        EmployeeDBContext empDB = new EmployeeDBContext();
        RoleDBContext roleDB = new RoleDBContext();

        // Lấy danh sách Division, Manager và Role
        List<Division> divisions = divDB.GetListDivisions();
        List<Employee> managers = empDB.list();
        List<Role> roles = roleDB.list();

        req.setAttribute("divisions", divisions);
        req.setAttribute("managers", managers);
        req.setAttribute("roles", roles);

        req.getRequestDispatcher("/feature/createAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");

            String name = req.getParameter("name");
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String managerId = req.getParameter("ID_manager");
            String divisionId = req.getParameter("ID_division");
            String roleId = req.getParameter("RoleID");

            // --- Tạo Employee ---
            Employee emp = new Employee();
            emp.setName(name);
            emp.setUsername(username);
            emp.setPassword(password);

            if (managerId != null && !managerId.isEmpty()) {
                Employee manager = new Employee();
                manager.setId(Long.parseLong(managerId));
                emp.setManager(manager);
            }

            if (divisionId != null && !divisionId.isEmpty()) {
                Division div = new Division();
                div.setId(Long.parseLong(divisionId));
                emp.setDivision(div);
            }

            // Insert employee
            EmployeeDBContext empDB = new EmployeeDBContext();
            empDB.insert(emp);

            // --- Gán role ---
            if (roleId != null && !roleId.isEmpty()) {
                EmployeeRole er = new EmployeeRole();
                er.setEmployee(emp);
                Role role = new Role();
                role.setId(Long.parseLong(roleId));
                er.setRole(role);

                EmployeeRoleDBContext erDB = new EmployeeRoleDBContext();
                erDB.insert(er);
            }

            req.setAttribute("success", "Account created successfully!");
            doGet(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error: " + e.getMessage());
            doGet(req, resp);
        }
    }
}
