<%@ page import="java.util.List" %>
<%@ page import="model.LeaveApplication" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Duyệt đơn nghỉ</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
    </head>
    <body class="bg-gray-100 min-h-screen flex flex-col">
        <jsp:include page="../component/header.jsp" />

        <main class="flex-grow container mx-auto p-6">
            <h2 class="text-2xl font-bold mb-6">📋 Danh sách đơn nghỉ cần duyệt</h2>

            <%
                List<LeaveApplication> leaves = (List<LeaveApplication>) request.getAttribute("leaves");
                if (leaves == null || leaves.isEmpty()) {
            %>
            <p class="text-gray-600">Hiện không có đơn nào cần duyệt.</p>
            <%
                } else {
            %>
            <div class="overflow-x-auto">
                <table class="min-w-full bg-white border border-gray-300 shadow-md rounded-lg">
                    <thead class="bg-blue-100">
                        <tr>
                            <th class="border px-4 py-2 text-left">Nhân viên</th>
                            <th class="border px-4 py-2 text-left">Từ ngày</th>
                            <th class="border px-4 py-2 text-left">Đến ngày</th>
                            <th class="border px-4 py-2 text-left">Lý do</th>
                            <th class="border px-4 py-2 text-left">Trạng thái</th>
                            <th class="border px-4 py-2 text-center">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (LeaveApplication la : leaves) {
                                String status = la.getStatus();
                                String statusText = "Đang chờ duyệt";
                                String colorClass = "text-yellow-600";

                                if ("APPROVED".equalsIgnoreCase(status)) {
                                    statusText = "Đã duyệt";
                                    colorClass = "text-green-600";
                                } else if ("REJECTED".equalsIgnoreCase(status)) {
                                    statusText = "Từ chối";
                                    colorClass = "text-red-600";
                                }
                        %>
                        <tr class="hover:bg-gray-50">
                            <td class="border px-4 py-2"><%= la.getCreatedBy().getName() %></td>
                            <td class="border px-4 py-2"><%= la.getFrom() %></td>
                            <td class="border px-4 py-2"><%= la.getTo() %></td>
                            <td class="border px-4 py-2"><%= la.getReason() %></td>
                            <td class="border px-4 py-2 font-medium <%= colorClass %>">
                                <%= statusText %>
                            </td>
                            <td class="border px-4 py-2 text-center">
                                <form method="post" action="<%= request.getContextPath() %>/feature/review" class="inline-flex gap-2">
                                    <input type="hidden" name="leaveId" value="<%= la.getId() %>"/>
                                    <button type="submit" name="action" value="APPROVE" 
                                            class="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600">
                                        Duyệt
                                    </button>
                                    <button type="submit" name="action" value="REJECT" 
                                            class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">
                                        Từ chối
                                    </button>
                                </form>
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
