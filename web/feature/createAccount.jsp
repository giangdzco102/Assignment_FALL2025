<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Division, model.Employee, model.Role" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Account</title>
        <script src="https://cdn.tailwindcss.com"></script>

    </head>
    <body>
        <jsp:include page="../component/header.jsp" />


        <h2>Create New Employee Account</h2>

        <% if (request.getAttribute("success") != null) { %>
        <p style="color:green;"><%= request.getAttribute("success") %></p>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
        <% } %>

        <form action="${pageContext.request.contextPath}/feature/createaccount" method="post">

            <label>Name:</label><br>
            <input type="text" name="name" required><br><br>

            <label>Username:</label><br>
            <input type="text" name="username" required><br><br>

            <label>Password:</label><br>
            <input type="password" name="password" required><br><br>

            <label>Division:</label><br>
            <select name="ID_division">
                <option value="">-- Select Division --</option>
                <%
                    List<model.Division> divisions = (List<model.Division>) request.getAttribute("divisions");
                    for (model.Division d : divisions) {
                %>
                <option value="<%= d.getId() %>"><%= d.getName() %></option>
                <% } %>
            </select><br><br>

            <label>Manager:</label><br>
            <select name="ID_manager">
                <option value="">-- Select Manager --</option>
                <%
                    List<model.Employee> managers = (List<model.Employee>) request.getAttribute("managers");
                    for (model.Employee m : managers) {
                %>
                <option value="<%= m.getId() %>"><%= m.getName() %></option>
                <% } %>
            </select><br><br>

            <label>Role:</label><br>
            <select name="RoleID">
                <option value="">-- Select Role --</option>
                <%
                    List<model.Role> roles = (List<model.Role>) request.getAttribute("roles");
                    for (model.Role r : roles) {
                %>
                <option value="<%= r.getId() %>"><%= r.getName() %></option>
                <% } %>
            </select><br><br>

            <input type="submit" value="Create Account">
        </form>
        <jsp:include page="../component/footer.jsp" />

    </body>
</html>
