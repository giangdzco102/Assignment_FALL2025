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
            <%
                String roleView = (String) request.getAttribute("roleView");
            %>

            <%-- === DIVISION HEAD === --%>
            <% if ("divisionhead".equalsIgnoreCase(roleView)) { 
                List<LeaveApplication> allLeaves = (List<LeaveApplication>) request.getAttribute("allLeaves");
            %>
            <h2 class="text-2xl font-bold mb-4">📋 Tất cả đơn nghỉ (của Manager và nhân viên)</h2>

            <!-- BẢNG lớn -->
            <div class="overflow-x-auto hidden md:block">
                <table class="min-w-full bg-white border border-gray-300 shadow-md rounded-lg">
                    <thead class="bg-blue-100">
                        <tr>
                            <th class="border px-4 py-2 text-left">Người gửi</th>
                            <th class="border px-4 py-2 text-left">Từ ngày</th>
                            <th class="border px-4 py-2 text-left">Đến ngày</th>
                            <th class="border px-4 py-2 text-left">Lý do</th>
                            <th class="border px-4 py-2 text-left">Trạng thái</th>
                            <th class="border px-4 py-2 text-left">Người duyệt</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (LeaveApplication la : allLeaves) { %>
                        <tr class="hover:bg-gray-50">
                            <td class="border px-4 py-2"><%= la.getCreatedBy() != null ? la.getCreatedBy().getName() : "-" %></td>
                            <td class="border px-4 py-2"><%= la.getFrom() %></td>
                            <td class="border px-4 py-2"><%= la.getTo() %></td>
                            <td class="border px-4 py-2"><%= la.getReason() %></td>
                            <td class="border px-4 py-2 font-medium
                                <%= "APPROVED".equalsIgnoreCase(la.getStatus()) ? "text-green-600" : 
                                    "REJECTED".equalsIgnoreCase(la.getStatus()) ? "text-red-600" : 
                                    "text-yellow-600" %>">
                                <%= la.getStatus() %>
                            </td>
                            <td class="border px-4 py-2"><%= la.getProcessedBy() != null ? la.getProcessedBy().getName() : "-" %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <!-- MOBILE -->
            <div class="grid gap-3 md:hidden">
                <% for (LeaveApplication la : allLeaves) { %>
                <div class="bg-white rounded-lg shadow p-4 border border-gray-200">
                    <p><strong>👤 Người gửi:</strong> <%= la.getCreatedBy() != null ? la.getCreatedBy().getName() : "-" %></p>
                    <p><strong>📅 Từ:</strong> <%= la.getFrom() %></p>
                    <p><strong>📅 Đến:</strong> <%= la.getTo() %></p>
                    <p><strong>📝 Lý do:</strong> <%= la.getReason() %></p>
                    <p class="font-medium
                        <%= "APPROVED".equalsIgnoreCase(la.getStatus()) ? "text-green-600" : 
                            "REJECTED".equalsIgnoreCase(la.getStatus()) ? "text-red-600" : 
                            "text-yellow-600" %>">
                        <strong>Trạng thái:</strong> <%= la.getStatus() %>
                    </p>
                    <p><strong>👔 Người duyệt:</strong> <%= la.getProcessedBy() != null ? la.getProcessedBy().getName() : "-" %></p>
                </div>
                <% } %>
            </div>

            <%-- === MANAGER === --%>
            <% } else if ("manager".equalsIgnoreCase(roleView)) { 
                List<LeaveApplication> myLeaves = (List<LeaveApplication>) request.getAttribute("myLeaves");
                List<LeaveApplication> staffLeaves = (List<LeaveApplication>) request.getAttribute("staffLeaves");
            %>

            <h2 class="text-2xl font-bold mb-4">📋 Đơn nghỉ của bạn</h2>
            <div class="overflow-x-auto hidden md:block mb-8">
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
                        <% for (LeaveApplication la : myLeaves) { %>
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
                            <td class="border px-4 py-2"><%= la.getProcessedBy() != null ? la.getProcessedBy().getName() : "-" %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <div class="grid gap-3 md:hidden mb-8">
                <% for (LeaveApplication la : myLeaves) { %>
                <div class="bg-white rounded-lg shadow p-4 border border-gray-200">
                    <p><strong>📅 Từ:</strong> <%= la.getFrom() %></p>
                    <p><strong>📅 Đến:</strong> <%= la.getTo() %></p>
                    <p><strong>📝 Lý do:</strong> <%= la.getReason() %></p>
                    <p class="font-medium
                        <%= "APPROVED".equalsIgnoreCase(la.getStatus()) ? "text-green-600" : 
                            "REJECTED".equalsIgnoreCase(la.getStatus()) ? "text-red-600" : 
                            "text-yellow-600" %>">
                        <strong>Trạng thái:</strong> <%= la.getStatus() %>
                    </p>
                    <p><strong>👔 Người duyệt:</strong> <%= la.getProcessedBy() != null ? la.getProcessedBy().getName() : "-" %></p>
                </div>
                <% } %>
            </div>

            <h2 class="text-2xl font-bold mb-4">👥 Đơn nghỉ của nhân viên</h2>
            <div class="overflow-x-auto hidden md:block">
                <table class="min-w-full bg-white border border-gray-300 shadow-md rounded-lg">
                    <thead class="bg-blue-100">
                        <tr>
                            <th class="border px-4 py-2 text-left">Nhân viên</th>
                            <th class="border px-4 py-2 text-left">Từ ngày</th>
                            <th class="border px-4 py-2 text-left">Đến ngày</th>
                            <th class="border px-4 py-2 text-left">Lý do</th>
                            <th class="border px-4 py-2 text-left">Trạng thái</th>
                            <th class="border px-4 py-2 text-left">Người duyệt</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (LeaveApplication la : staffLeaves) { %>
                        <tr class="hover:bg-gray-50">
                            <td class="border px-4 py-2"><%= la.getCreatedBy() != null ? la.getCreatedBy().getName() : "-" %></td>
                            <td class="border px-4 py-2"><%= la.getFrom() %></td>
                            <td class="border px-4 py-2"><%= la.getTo() %></td>
                            <td class="border px-4 py-2"><%= la.getReason() %></td>
                            <td class="border px-4 py-2 font-medium
                                <%= "APPROVED".equalsIgnoreCase(la.getStatus()) ? "text-green-600" : 
                                    "REJECTED".equalsIgnoreCase(la.getStatus()) ? "text-red-600" : 
                                    "text-yellow-600" %>">
                                <%= la.getStatus() %>
                            </td>
                            <td class="border px-4 py-2"><%= la.getProcessedBy() != null ? la.getProcessedBy().getName() : "-" %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <div class="grid gap-3 md:hidden">
                <% for (LeaveApplication la : staffLeaves) { %>
                <div class="bg-white rounded-lg shadow p-4 border border-gray-200">
                    <p><strong>👤 Nhân viên:</strong> <%= la.getCreatedBy() != null ? la.getCreatedBy().getName() : "-" %></p>
                    <p><strong>📅 Từ:</strong> <%= la.getFrom() %></p>
                    <p><strong>📅 Đến:</strong> <%= la.getTo() %></p>
                    <p><strong>📝 Lý do:</strong> <%= la.getReason() %></p>
                    <p class="font-medium
                        <%= "APPROVED".equalsIgnoreCase(la.getStatus()) ? "text-green-600" : 
                            "REJECTED".equalsIgnoreCase(la.getStatus()) ? "text-red-600" : 
                            "text-yellow-600" %>">
                        <strong>Trạng thái:</strong> <%= la.getStatus() %>
                    </p>
                    <p><strong>👔 Người duyệt:</strong> <%= la.getProcessedBy() != null ? la.getProcessedBy().getName() : "-" %></p>
                </div>
                <% } %>
            </div>

            <%-- === EMPLOYEE === --%>
            <% } else { 
                List<LeaveApplication> leaves = (List<LeaveApplication>) request.getAttribute("leaves");
            %>
            <h2 class="text-2xl font-bold mb-6 flex items-center justify-between">
                📋 Danh sách đơn nghỉ của bạn
                <a href="${pageContext.request.contextPath}/feature/requestforleave" 
                   class="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600">
                    + Thêm đơn
                </a>
            </h2>

            <% if (leaves == null || leaves.isEmpty()) { %>
            <p class="text-gray-600">Bạn chưa có đơn nghỉ nào.</p>
            <% } else { %>

            <div class="overflow-x-auto hidden md:block">
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
                        <% for (LeaveApplication la : leaves) { %>
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
                            <td class="border px-4 py-2"><%= la.getProcessedBy() != null ? la.getProcessedBy().getName() : "-" %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <div class="grid gap-3 md:hidden">
                <% for (LeaveApplication la : leaves) { %>
                <div class="bg-white rounded-lg shadow p-4 border border-gray-200">
                    <p><strong>📅 Từ:</strong> <%= la.getFrom() %></p>
                    <p><strong>📅 Đến:</strong> <%= la.getTo() %></p>
                    <p><strong>📝 Lý do:</strong> <%= la.getReason() %></p>
                    <p class="font-medium
                        <%= "APPROVED".equalsIgnoreCase(la.getStatus()) ? "text-green-600" : 
                            "REJECTED".equalsIgnoreCase(la.getStatus()) ? "text-red-600" : 
                            "text-yellow-600" %>">
                        <strong>Trạng thái:</strong> <%= la.getStatus() %>
                    </p>
                    <p><strong>👔 Người duyệt:</strong> <%= la.getProcessedBy() != null ? la.getProcessedBy().getName() : "-" %></p>
                </div>
                <% } %>
            </div>
            <% } %>
            <% } %>
        </main>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
