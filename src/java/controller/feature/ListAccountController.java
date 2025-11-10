package controller.feature;

import dal.EmployeeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Employee;

public class ListAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EmployeeDBContext empDB = new EmployeeDBContext();
        List<Employee> employees = empDB.listWithRoles();  

        req.setAttribute("employees", employees);
        req.getRequestDispatcher("/feature/listAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        EmployeeDBContext empDB = new EmployeeDBContext();

        try {
            long id = Long.parseLong(idParam);
            Employee e = empDB.get(id);
            if (e != null) {
                empDB.delete(e);
                req.setAttribute("success", "Xóa tài khoản thành công!");
            } else {
                req.setAttribute("error", "Tài khoản không tồn tại!");
            }
        } catch (NumberFormatException ex) {
            req.setAttribute("error", "ID không hợp lệ!");
        } catch (Exception ex) {
            req.setAttribute("error", "Xảy ra lỗi khi xóa tài khoản!");
            ex.printStackTrace();
        }

        List<Employee> employees = empDB.listWithRoles();
        req.setAttribute("employees", employees);

        req.getRequestDispatcher("/feature/listAccount.jsp").forward(req, resp);
    }
}
