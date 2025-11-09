<%-- 
    Document   : review
    Created on : Nov 9, 2025, 9:41:24 PM
    Author     : ASUS
--%>
<%@ page import="java.util.List" %>
<%@ page import="model.LeaveApplication" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.tailwindcss.com"></script>

    </head>
    <body class="bg-gray-100 min-h-screen flex flex-col">
        <jsp:include page="../component/header.jsp" />

        <main class="flex-grow container mx-auto p-6">
            <h2 class="text-2xl font-bold mb-4">Đơn nghỉ cần duyệt</h2>

            <%
                List<LeaveApplication> leaves = (List<LeaveApplication>) request.getAttribute("leaves");
                if (leaves == null || leaves.isEmpty()) {
            %>
            <p>Hiện không có đơn nào cần duyệt.</p>
            <%
                } else {
            %>
            <table class="min-w-full border border-gray-300">
                <thead class="bg-gray-100">
                    <tr>
                        <th class="border px-4 py-2">Nhân viên</th>
                        <th class="border px-4 py-2">Từ ngày</th>
                        <th class="border px-4 py-2">Đến ngày</th>
                        <th class="border px-4 py-2">Lý do</th>
                        <th class="border px-4 py-2">Trạng thái</th>
                        <th class="border px-4 py-2">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (LeaveApplication la : leaves) {
                    %>
                    <tr>
                        <td class="border px-4 py-2"><%= la.getCreatedBy().getName() %></td>
                        <td class="border px-4 py-2"><%= la.getFrom() %></td>
                        <td class="border px-4 py-2"><%= la.getTo() %></td>
                        <td class="border px-4 py-2"><%= la.getReason() %></td>
                        <td class="border px-4 py-2"><%= la.getStatus() %></td>
                        <td class="border px-4 py-2">
                            <form method="post" action="<%= request.getContextPath() %>/feature/review" class="inline">
                                <input type="hidden" name="leaveId" value="<%= la.getId() %>"/>
                                <button type="submit" name="action" value="APPROVE" class="bg-green-500 text-white px-2 py-1 rounded">Duyệt</button>
                                <button type="submit" name="action" value="REJECT" class="bg-red-500 text-white px-2 py-1 rounded">Từ chối</button>
                            </form>
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
        </main>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
