<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Employee, model.Role" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Account List</title>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            table {
                border-collapse: collapse;
                width: 90%;
                margin: 20px auto;
            }
            th, td {
                border: 1px solid #ccc;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            h2 {
                text-align: center;
            }
            a.button {
                background: #28a745;
                color: white;
                padding: 6px 12px;
                text-decoration: none;
                border-radius: 4px;
            }
            a.delete-btn {
                background: #dc3545;
                color: white;
                padding: 5px 10px;
                text-decoration: none;
                border-radius: 4px;
            }
            a.delete-btn:hover {
                background: #b02a37;
            }
        </style>
        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body>
        <jsp:include page="../component/header.jsp" />

        <h2>Employee Account List</h2>
        <% if (request.getAttribute("message") != null) { %>
        <p style="color: green; text-align: center;">
            <%= request.getAttribute("message") %>
        </p>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
        <p style="color: red; text-align: center;">
            <%= request.getAttribute("error") %>
        </p>
        <% } %>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Username</th>
                <th>Division</th>
                <th>Manager</th>
                <th>Roles</th>
                <th>Action</th> <!-- 👈 thêm cột Action -->
            </tr>
            <%
                List<Employee> employees = (List<Employee>) request.getAttribute("employees");
                if (employees != null && !employees.isEmpty()) {
                    for (Employee e : employees) {
            %>
            <tr>
                <td><%= e.getId() %></td>
                <td><%= e.getName() %></td>
                <td><%= e.getUsername() %></td>
                <td><%= e.getDivision() != null ? e.getDivision().getName() : "" %></td>
                <td><%= e.getManager() != null ? e.getManager().getName() : "" %></td>
                <td>
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
                <td>
                    <form action="<%= request.getContextPath() %>/feature/listaccount" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= e.getId() %>" />
                        <button type="submit" onclick="return confirm('Bạn có chắc muốn xóa?');">Delete</button>
                    </form>

                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr><td colspan="7" style="text-align:center;">No employees found.</td></tr>
            <% } %>
        </table>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
