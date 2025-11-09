<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.LeaveApplication"%>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Danh sách đơn nghỉ</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
    </head>
    <body class="bg-gray-100 min-h-screen flex flex-col">

        <jsp:include page="../component/header.jsp" />

        <main class="flex-grow container mx-auto p-6">
            <h2 class="text-2xl font-bold mb-6 flex items-center justify-between">
                📋 Danh sách đơn nghỉ của bạn
                <a href="${pageContext.request.contextPath}/feature/requestforleave" 
                   class="bg-green-500 text-white px-2 py-1 rounded hover:bg-green-600">
                     Thêm đơn
                </a>
            </h2>

            <%
                List<LeaveApplication> leaves = (List<LeaveApplication>) request.getAttribute("leaves");
                if (leaves == null || leaves.isEmpty()) {
            %>
            <p class="text-gray-600">Bạn chưa có đơn nghỉ nào.</p>
            <%
                } else {
            %>

            <div class="overflow-x-auto">
                <table class="min-w-full bg-white border border-gray-300 shadow-md rounded-lg">
                    <thead class="bg-blue-100">
                        <tr>
                            <th class="border px-4 py-2 text-left">Từ ngày</th>
                            <th class="border px-4 py-2 text-left">Đến ngày</th>
                            <th class="border px-4 py-2 text-left">Lý do</th>
                            <th class="border px-4 py-2 text-left">Trạng thái</th>
                            <th class="border px-4 py-2 text-left">Người duyệt</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (LeaveApplication la : leaves) {
                        %>
                        <tr class="hover:bg-gray-50">
                            <td class="border px-4 py-2"><%= la.getFrom() %></td>
                            <td class="border px-4 py-2"><%= la.getTo() %></td>
                            <td class="border px-4 py-2"><%= la.getReason() %></td>
                            <td class="border px-4 py-2 font-medium 
                                <%= "APPROVED".equalsIgnoreCase(la.getStatus()) ? "text-green-600" : 
                                    "REJECTED".equalsIgnoreCase(la.getStatus()) ? "text-red-600" : 
                                    "text-yellow-600" %>">
                                <%= la.getStatus() %>
                            </td>
                            <td class="border px-4 py-2">
                                <%= (la.getProcessedBy() != null) ? la.getProcessedBy().getName() : "-" %>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <%
                }
            %>
        </main>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
