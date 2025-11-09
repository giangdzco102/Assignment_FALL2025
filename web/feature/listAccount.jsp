<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Employee, model.Role" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Employee Account List</title>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="bg-gray-50 min-h-screen">
        <jsp:include page="../component/header.jsp" />

        <div class="max-w-6xl mx-auto py-8">
            <h2 class="text-3xl font-bold text-center mb-6">Employee Account List</h2>

            <% if (request.getAttribute("message") != null) { %>
            <p class="text-green-600 text-center mb-4">
                <%= request.getAttribute("message") %>
            </p>
            <% } %>

            <% if (request.getAttribute("error") != null) { %>
            <p class="text-red-600 text-center mb-4">
                <%= request.getAttribute("error") %>
            </p>
            <% } %>

            <div class="overflow-x-auto">
                <table class="min-w-full bg-white shadow rounded-lg overflow-hidden">
                    <thead class="bg-gray-100">
                        <tr>
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700">ID</th>
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700">Name</th>
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700">Username</th>
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700">Division</th>
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700">Manager</th>
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700">Roles</th>
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700">Action</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <%
                            List<Employee> employees = (List<Employee>) request.getAttribute("employees");
                            if (employees != null && !employees.isEmpty()) {
                                for (Employee e : employees) {
                        %>
                        <tr class="hover:bg-gray-50">
                            <td class="px-6 py-3 text-sm text-gray-700"><%= e.getId() %></td>
                            <td class="px-6 py-3 text-sm text-gray-700"><%= e.getName() %></td>
                            <td class="px-6 py-3 text-sm text-gray-700"><%= e.getUsername() %></td>
                            <td class="px-6 py-3 text-sm text-gray-700"><%= e.getDivision() != null ? e.getDivision().getName() : "" %></td>
                            <td class="px-6 py-3 text-sm text-gray-700"><%= e.getManager() != null ? e.getManager().getName() : "" %></td>
                            <td class="px-6 py-3 text-sm text-gray-700">
                                <%
                                    if (e.getRoles() != null && !e.getRoles().isEmpty()) {
                                        for (int i = 0; i < e.getRoles().size(); i++) {
                                            Role r = e.getRoles().get(i);
                                            out.print(r.getName());
                                            if (i < e.getRoles().size() - 1) out.print(", ");
                                        }
                                    }
                                %>
                            </td>
                            <td class="px-6 py-3">
                                <form action="<%= request.getContextPath() %>/feature/listaccount" method="post" class="inline">
                                    <input type="hidden" name="id" value="<%= e.getId() %>" />
                                    <button type="submit" class="bg-red-500 hover:bg-red-600 text-white text-sm font-medium px-3 py-1 rounded"
                                            onclick="return confirm('Bạn có chắc muốn xóa?');">
                                        Delete
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <% 
                                }
                            } else { 
                        %>
                        <tr>
                            <td colspan="7" class="text-center px-6 py-4 text-gray-500">No employees found.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
