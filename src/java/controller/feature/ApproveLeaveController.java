package controller.feature;

import dal.LeaveApplicationDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Employee;
import model.LeaveApplication;

public class ApproveLeaveController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Employee manager = (Employee) session.getAttribute("auth");
        if (manager == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }


        // Lấy danh sách tất cả đơn pending của nhân viên thuộc quản lý manager
        LeaveApplicationDBContext db = new LeaveApplicationDBContext();
        List<LeaveApplication> leaves = db.getLeavesForManager(manager.getId());

        // Gắn vào request để JSP hiển thị
        req.setAttribute("leaves", leaves);
        req.getRequestDispatcher("/feature/review.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Employee manager = (Employee) session.getAttribute("auth");
        if (manager == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            long leaveId = Long.parseLong(req.getParameter("leaveId"));
            String action = req.getParameter("action"); // "APPROVE" hoặc "REJECT"

            LeaveApplicationDBContext db = new LeaveApplicationDBContext();
            LeaveApplication leave = db.getLeaveById(leaveId);

            if (leave != null) {
                // Chỉ cho manager của nhân viên duyệt
                if (leave.getCreatedBy().getManager() != null
                        && leave.getCreatedBy().getManager().getId() == manager.getId()) {

                    leave.setProcessedBy(manager);
                    if ("APPROVE".equalsIgnoreCase(action)) {
                        leave.setStatus("APPROVED");
                    } else if ("REJECT".equalsIgnoreCase(action)) {
                        leave.setStatus("REJECTED");
                    }

                    db.updateStatus(leave);
                }
            }

            // Sau khi xử lý, chuyển hướng về lại trang review
            resp.sendRedirect(req.getContextPath() + "/feature/review");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Lỗi khi duyệt đơn");
        }
    }
}
