<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Division, model.Employee, model.Role" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Tạo tài khoản nhân viên</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
    </head>
    <body class="bg-gray-50 min-h-screen flex flex-col font-sans text-gray-800">
        <jsp:include page="../component/header.jsp" />

        <main class="flex-grow flex justify-center items-center py-10 px-4 sm:px-6">
            <div class="bg-white shadow-lg rounded-2xl p-6 sm:p-10 w-full max-w-md sm:max-w-lg">
                <h2 class="text-2xl sm:text-3xl font-bold text-center mb-8 text-gray-900">
                    Tạo tài khoản nhân viên mới
                </h2>

                <% if (request.getAttribute("success") != null) { %>
                <p class="text-green-800 bg-green-100 border border-green-300 font-medium text-center mb-5 p-3 rounded-lg">
                    <%= request.getAttribute("success") %>
                </p>
                <% } %>

                <% if (request.getAttribute("error") != null) { %>
                <p class="text-red-800 bg-red-100 border border-red-300 font-medium text-center mb-5 p-3 rounded-lg">
                    <%= request.getAttribute("error") %>
                </p>
                <% } %>

                <form action="${pageContext.request.contextPath}/feature/createaccount" method="post" class="space-y-5">
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1.5">Họ và tên</label>
                        <input type="text" name="name" required
                               class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg
                               focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none transition" />
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1.5">Tên đăng nhập</label>
                        <input type="text" name="username" required
                               class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg
                               focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none transition" />
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1.5">Mật khẩu</label>
                        <input type="password" name="password" required
                               class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg
                               focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none transition" />
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1.5">Phòng ban</label>
                        <select name="ID_division"
                                class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg
                                focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none transition">
                            <option value="">-- Chọn phòng ban --</option>
                            <%
                                List<Division> divisions = (List<Division>) request.getAttribute("divisions");
                                if (divisions != null) {
                                    for (Division d : divisions) {
                            %>
                            <option value="<%= d.getId() %>"><%= d.getName() %></option>
                            <% }} %>
                        </select>
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1.5">Quản lý</label>
                        <select name="ID_manager"
                                class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg
                                focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none transition">
                            <option value="">-- Chọn quản lý --</option>
                            <%
                                List<Employee> managers = (List<Employee>) request.getAttribute("managers");
                                if (managers != null) {
                                    for (Employee m : managers) {
                            %>
                            <option value="<%= m.getId() %>"><%= m.getName() %></option>
                            <% }} %>
                        </select>
                    </div>

                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-1.5">Vai trò</label>
                        <select name="RoleID"
                                class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg
                                focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none transition">
                            <option value="">-- Chọn vai trò --</option>
                            <%
                                List<Role> roles = (List<Role>) request.getAttribute("roles");
                                if (roles != null) {
                                    for (Role r : roles) {
                            %>
                            <option value="<%= r.getId() %>"><%= r.getName() %></option>
                            <% }} %>
                        </select>
                    </div>

                    <div class="pt-3">
                        <button type="submit"
                                class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-3 rounded-lg
                                transition focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
                            Tạo tài khoản
                        </button>
                    </div>
                </form>
            </div>
        </main>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
