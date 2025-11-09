<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.LeaveApplication" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Lịch Nghỉ - Agenda</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
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

            <!-- Tiêu đề -->
            <h2 class="text-3xl font-bold text-center text-gray-800 mb-6">
                Lịch nghỉ - <%= month + 1 %>/<%= year %>
            </h2>

            <!-- Lịch -->
            <div class="bg-white shadow rounded-lg p-4">
                <div class="grid grid-cols-7 gap-2 text-center font-semibold text-gray-700 border-b pb-2 mb-2">
                    <div>CN</div><div>T2</div><div>T3</div><div>T4</div>
                    <div>T5</div><div>T6</div><div>T7</div>
                </div>

                <div class="grid grid-cols-7 gap-2 text-center">
                    <% for (int i = 1; i < firstDayOfWeek; i++) { %>
                        <div></div>
                    <% } %>

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

                                if (!currentDay.before(start) && !currentDay.after(end)) {
                                    isLeave = true;
                                    if (isManager && la.getCreatedBy() != null) {
                                        tooltip.append(la.getCreatedBy().getName()).append(", ");
                                    }
                                }
                            }

                            String tooltipStr = tooltip.length() > 0 ? tooltip.substring(0, tooltip.length() - 2) : "";
                    %>
                        <div class="relative group">
                            <div class="p-3 rounded-lg text-sm font-medium 
                                        <%= isLeave ? "bg-red-500 text-white shadow" : "bg-gray-50 text-gray-700 border" %> 
                                        hover:bg-gray-200 transition">
                                <%= day %>
                            </div>
                            <% if (isLeave && isManager && !tooltipStr.isEmpty()) { %>
                                <div class="absolute hidden group-hover:block bg-gray-800 text-white text-xs p-2 rounded mt-1 left-1/2 transform -translate-x-1/2 z-10 shadow-lg">
                                    <%= tooltipStr %>
                                </div>
                            <% } %>
                        </div>
                    <% } %>
                </div>
            </div>
        </main>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
