<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Employee"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Home - Welcome</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body class="bg-gray-100 min-h-screen flex flex-col">

        <!-- HEADER -->
        <jsp:include page="../component/header.jsp" />

        <!-- MAIN CONTENT -->
        <!-- MAIN CONTENT -->
        <main class="flex-1 p-6">
            <div class="max-w-4xl mx-auto bg-white shadow rounded p-6">
                <h2 class="text-2xl font-bold mb-4">Thông tin cá nhân</h2>
                <div class="flex justify-start mb-6">
                    <img src="${pageContext.request.contextPath}/public/assets/avar.jpg" 
                         alt="Avatar" 
                         class="w-24 h-24 rounded-full object-cover border-2 border-gray-300">
                </div>
                <%
    Employee account = (Employee) request.getAttribute("account");
    String roleName = (String) request.getAttribute("roleName");
    String divisionName = (String) request.getAttribute("divisionName");
                %>

                <div class="grid grid-cols-2 gap-4">
                    <div>
                        <span class="font-semibold text-gray-600">Tên nhân viên:</span>
                        <span class="text-gray-800"><%= account.getName() %></span>
                    </div>
                    <div>
                        <span class="font-semibold text-gray-600">Bộ phận:</span>
                        <span class="text-gray-800"><%= divisionName %></span>
                    </div>
                    <div>
                        <span class="font-semibold text-gray-600">Chức vụ:</span>
                        <span class="text-gray-800"><%= roleName %></span>
                    </div>
                    <div>
                        <span class="font-semibold text-gray-600">Username:</span>
                        <span class="text-gray-800"><%= account.getUsername() %></span>
                    </div>
                </div>
            </div>
        </main>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
