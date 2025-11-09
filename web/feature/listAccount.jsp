<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Employee, model.Role" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Employee Account List</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
    </head>
    <body class="bg-gray-50 min-h-screen flex flex-col">
        <jsp:include page="../component/header.jsp" />

        <main class="flex-1 max-w-6xl mx-auto w-full p-4 sm:p-6">
            <h2 class="text-2xl sm:text-3xl font-bold text-center mb-6 text-gray-800">
                Employee Account List
            </h2>

            <% if (request.getAttribute("message") != null) { %>
            <p class="text-green-600 text-center mb-4 font-medium"><%= request.getAttribute("message") %></p>
            <% } %>

            <% if (request.getAttribute("error") != null) { %>
            <p class="text-red-600 text-center mb-4 font-medium"><%= request.getAttribute("error") %></p>
            <% } %>

            <!-- Responsive Table Container -->
            <div class="overflow-x-auto bg-white shadow-md rounded-lg">
                <table class="min-w-full text-sm text-gray-700">
                    <thead class="bg-gray-100 text-gray-700 uppercase text-xs sm:text-sm">
                        <tr>
                            <th class="px-4 py-3 text-left">ID</th>
                            <th class="px-4 py-3 text-left">Name</th>
                            <th class="px-4 py-3 text-left">Username</th>
                            <th class="px-4 py-3 text-left">Division</th>
                            <th class="px-4 py-3 text-left">Manager</th>
                            <th class="px-4 py-3 text-left">Roles</th>
                            <th class="px-4 py-3 text-center">Action</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        <%
                            List<Employee> employees = (List<Employee>) request.getAttribute("employees");
                            if (employees != null && !employees.isEmpty()) {
                                for (Employee e : employees) {
                        %>
                        <tr class="hover:bg-gray-50">
                            <td class="px-4 py-3"><%= e.getId() %></td>
                            <td class="px-4 py-3"><%= e.getName() %></td>
                            <td class="px-4 py-3 break-words"><%= e.getUsername() %></td>
                            <td class="px-4 py-3"><%= e.getDivision() != null ? e.getDivision().getName() : "" %></td>
                            <td class="px-4 py-3"><%= e.getManager() != null ? e.getManager().getName() : "" %></td>
                            <td class="px-4 py-3">
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
                            <td class="px-4 py-3 text-center">
                                <form action="<%= request.getContextPath() %>/feature/listaccount" method="post" class="inline">
                                    <input type="hidden" name="id" value="<%= e.getId() %>" />
                                    <button type="submit"
                                            class="bg-red-500 hover:bg-red-600 text-white font-medium px-3 py-1.5 rounded-md text-xs sm:text-sm transition"
                                            onclick="return confirm('Bạn có chắc muốn xóa?');">
                                        Delete
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <% } } else { %>
                        <tr>
                            <td colspan="7" class="text-center px-6 py-4 text-gray-500">
                                No employees found.
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </main>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
