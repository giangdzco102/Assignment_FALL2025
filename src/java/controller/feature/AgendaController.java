package controller.feature;

import dal.EmployeeRoleDBContext;
import dal.LeaveApplicationDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import model.Employee;
import model.LeaveApplication;

public class AgendaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Employee account = (Employee) session.getAttribute("auth");
        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Kiểm tra quyền truy cập
        List<String> features = (List<String>) session.getAttribute("featureURLs");
        if (features == null || !features.contains("/feature/agenda")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập.");
            return;
        }

        LeaveApplicationDBContext db = new LeaveApplicationDBContext();
        EmployeeRoleDBContext erDB = new EmployeeRoleDBContext();
        account.setRoles(erDB.getRolesByEmployeeId(account.getId()));

        boolean isManager = account.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("manager"));

        List<LeaveApplication> leaves;
        if (isManager) {
            // Lấy tất cả đơn APPROVED của nhân viên thuộc quản lý
            leaves = db.getLeavesApproveForManager(account.getId());
        } else {
            // Lấy tất cả đơn APPROVED của chính nhân viên
            leaves = db.getLeavesByEmployee(account.getId());
        }

        // --- Tạo map ngày -> tên nhân viên nghỉ để JSP hover ---
        Map<String, List<String>> leaveMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (LeaveApplication la : leaves) {
            if (!"APPROVED".equalsIgnoreCase(la.getStatus())) {
                continue;
            }

            Calendar start = Calendar.getInstance();
            start.setTime(la.getFrom());
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            start.set(Calendar.MILLISECOND, 0);

            Calendar end = Calendar.getInstance();
            end.setTime(la.getTo());
            end.set(Calendar.HOUR_OF_DAY, 0);
            end.set(Calendar.MINUTE, 0);
            end.set(Calendar.SECOND, 0);
            end.set(Calendar.MILLISECOND, 0);

            // lặp từ start đến end
            while (!start.after(end)) {
                String dayKey = sdf.format(start.getTime());
                if (isManager && la.getCreatedBy() != null) {
                    leaveMap.computeIfAbsent(dayKey, k -> new ArrayList<>()).add(la.getCreatedBy().getName());
                }
                start.add(Calendar.DAY_OF_MONTH, 1); // tăng 1 ngày
            }
        }

        req.setAttribute("leaves", leaves);
        req.setAttribute("isManager", isManager); // để JSP xử lý hover
        req.setAttribute("leaveMap", leaveMap);   // để JSP hiển thị tooltip
        req.getRequestDispatcher("/feature/agenda.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
