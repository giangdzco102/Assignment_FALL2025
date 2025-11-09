<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.LeaveApplication"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách đơn nghỉ</title>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body>
        <jsp:include page="../component/header.jsp" />

        <h2 class="text-2xl font-bold mb-4 flex items-center justify-between">
            📋 Danh sách đơn nghỉ của bạn
            <a href="${pageContext.request.contextPath}/feature/requestforleave" 
               class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">
                Thêm đơn
            </a>
        </h2>

        <%
            List<LeaveApplication> leaves = (List<LeaveApplication>) request.getAttribute("leaves");
            if (leaves == null || leaves.isEmpty()) {
        %>
        <p>Bạn chưa có đơn nghỉ nào.</p>
        <%
            } else {
        %>
        <table class="min-w-full bg-white border border-gray-300">
            <thead class="bg-gray-100">
                <tr>
                    <th class="border px-4 py-2">Từ ngày</th>
                    <th class="border px-4 py-2">Đến ngày</th>
                    <th class="border px-4 py-2">Lý do</th>
                    <th class="border px-4 py-2">Trạng thái</th>
                    <th class="border px-4 py-2">Người duyệt</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (LeaveApplication la : leaves) {
                %>
                <tr>
                    <td class="border px-4 py-2"><%= la.getFrom() %></td>
                    <td class="border px-4 py-2"><%= la.getTo() %></td>
                    <td class="border px-4 py-2"><%= la.getReason() %></td>
                    <td class="border px-4 py-2"><%= la.getStatus() %></td>
                    <td class="border px-4 py-2">
                        <%= (la.getProcessedBy() != null) ? la.getProcessedBy().getName() : "-" %>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%
            }
        %>

    </body>
</html>
