package controller.feature;

import dal.LeaveApplicationDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import model.Employee;
import model.LeaveApplication;

public class RequestForLeaveController extends HttpServlet {

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
        if (features == null || !features.contains("/feature/requestforleave")) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this page.");
            return;
        }

        // Nếu là lần đầu mở trang (chưa có form)
        if (req.getParameter("from") == null) {
            req.getRequestDispatcher("/feature/requestforleave.jsp").forward(req, resp);
            return;
        }

        try {
            // Parse dữ liệu form
            String fromStr = req.getParameter("from");
            String toStr = req.getParameter("to");
            String reason = req.getParameter("reason");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date from = sdf.parse(fromStr);
            Date to = sdf.parse(toStr);

            // Tạo đối tượng LeaveApplication
            LeaveApplication leave = new LeaveApplication();
            leave.setCreatedBy(employee);
            leave.setCreateTime(new Date());
            leave.setFrom(from);
            leave.setTo(to);
            leave.setReason(reason);
            leave.setStatus("PENDING");
            if (employee.getManager() != null) {
                leave.setProcessedBy(employee.getManager());
            }

            // Lưu vào DB
            LeaveApplicationDBContext db = new LeaveApplicationDBContext();
            db.insert(leave);

            // Sau khi nộp thành công → chuyển hướng về danh sách đơn nghỉ
            resp.sendRedirect(req.getContextPath() + "/feature/listleave");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Lỗi khi nộp đơn nghỉ");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
