<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.LeaveApplication" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Agenda</title>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="bg-gray-100 min-h-screen flex flex-col">

        <jsp:include page="../component/header.jsp" />

        <main class="flex-grow container mx-auto p-6">
            
            <%
        List<LeaveApplication> leaves = (List<LeaveApplication>) request.getAttribute("leaves");
        Boolean isManagerObj = (Boolean) request.getAttribute("isManager");
        boolean isManager = (isManagerObj != null) ? isManagerObj : false;

        if (leaves == null) leaves = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        %>

        <div class="grid grid-cols-7 gap-2 text-center font-bold mb-2">
            <div>Sun</div><div>Mon</div><div>Tue</div><div>Wed</div>
            <div>Thu</div><div>Fri</div><div>Sat</div>
        </div>

        <div class="grid grid-cols-7 gap-2">
            <%-- padding cho ngày đầu tháng --%>
            <%
            for (int i = 1; i < firstDayOfWeek; i++) { %>
            <div class="p-2"></div>
            <% } %>

            <%-- hiển thị các ngày trong tháng --%>
            <%
            for (int day = 1; day <= daysInMonth; day++) {
                boolean isLeave = false;
                StringBuilder tooltip = new StringBuilder();

                Calendar currentDay = Calendar.getInstance();
                currentDay.set(year, month, day, 0, 0, 0);
                currentDay.set(Calendar.MILLISECOND, 0);

                for (LeaveApplication la : leaves) {
                    if (!"APPROVED".equalsIgnoreCase(la.getStatus())) continue;

                    Calendar start = Calendar.getInstance();
                    start.setTime(la.getFrom());
                    start.set(Calendar.HOUR_OF_DAY,0); start.set(Calendar.MINUTE,0);
                    start.set(Calendar.SECOND,0); start.set(Calendar.MILLISECOND,0);

                    Calendar end = Calendar.getInstance();
                    end.setTime(la.getTo());
                    end.set(Calendar.HOUR_OF_DAY,0); end.set(Calendar.MINUTE,0);
                    end.set(Calendar.SECOND,0); end.set(Calendar.MILLISECOND,0);

                    if (!currentDay.before(start) && !currentDay.after(end)) {
                        isLeave = true;
                        if (isManager && la.getCreatedBy() != null) {
                            tooltip.append(la.getCreatedBy().getName()).append(", ");
                        }
                        // Không break để gom tất cả nhân viên cùng ngày
                    }
                }

                String tooltipStr = tooltip.length() > 0 ? tooltip.substring(0, tooltip.length() - 2) : "";
            %>
            <div class="relative group">
                <div class="p-2 rounded <%= isLeave ? "bg-red-400 text-white" : "bg-white text-gray-700" %>">
                    <%= day %>
                </div>
                <% if (isLeave && isManager && !tooltipStr.isEmpty()) { %>
                <div class="absolute hidden bg-gray-800 text-white text-sm p-1 rounded mt-1 z-10 group-hover:block">
                    <%= tooltipStr %>
                </div>
                <% } %>
            </div>


            <% } %>
        </div>
        </main>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>