package controller.auth;

import dal.EmployeeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Employee;
import java.util.List;

public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Kiểm tra rỗng
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("message", "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
            return;
        }

        EmployeeDBContext db = new EmployeeDBContext();
        Employee employee = db.get(username, password);

        if (employee != null) {
            HttpSession session = req.getSession();
            session.setAttribute("auth", employee);

            // Lấy role và feature của employee
            List<String> urls = db.getFeatureURLsByEmployee(employee.getId());
            session.setAttribute("featureURLs", urls);
            
            boolean isAdmin = db.isAdmin(employee.getId());
            
            if (isAdmin) {
                resp.sendRedirect(req.getContextPath() + "/feature/listaccount");
            } else {
                resp.sendRedirect(req.getContextPath() + "/feature/home");
            }
        } else {
            // Sai mật khẩu hoặc tài khoản không tồn tại
            req.setAttribute("message", "Sai tài khoản hoặc mật khẩu!");
            req.getRequestDispatcher("auth/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("auth/login.jsp").forward(req, resp);
    }

}
