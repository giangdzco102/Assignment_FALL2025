<%@page import="java.util.List"%>
<%@page import="model.Employee"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Employee employee = (Employee) session.getAttribute("auth");
    if (employee == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    List<String> features = (List<String>) session.getAttribute("featureURLs");
%>

<header class="bg-gray-800 text-white shadow">
    <div class="container mx-auto flex items-center p-4 justify-between">
        <!-- Logo -->
        <div class="flex items-center">
            <img src="${pageContext.request.contextPath}/public/assets/logo-embedded-chat-header@3x.png" alt="Logo Công ty" class="h-10 w-10">
        </div>

        <!-- Desktop menu (>= md) giữ nguyên layout cũ -->
        <nav class="hidden md:flex flex-grow justify-between items-center ml-4">
            <div class="flex items-center space-x-4">
                <span class="font-bold">Xin chào, <%= employee.getName() %>!</span>
            </div>
            <div class="flex space-x-6 items-center">
                <% if (features != null) {
                    for (String url : features) { 
                        String name = url;
                        switch(url) {
                            case "/feature/home": name = "Trang chủ"; break;
                            case "/feature/agenda": name = "Lịch nghỉ"; break;
                            case "/feature/requestforleave": name = "Xin nghỉ"; break;
                            case "/feature/listleave": name = "Danh sách đơn"; break;
                            case "/feature/review": name = "Duyệt đơn"; break;
                            case "/feature/createaccount": name = "Tạo tài khoản"; break;
                            case "/feature/listaccount": name = "Danh sách tài khoản"; break;
                        }
                %>
                <a href="<%= request.getContextPath() + url %>" class="hover:text-gray-300 font-medium"><%= name %></a>
                <%  } } %>
                <a href="<%= request.getContextPath() + "/logout" %>" class="bg-red-500 px-3 py-1 rounded hover:bg-red-600">Đăng xuất</a>
            </div>
        </nav>

        <!-- Mobile menu (< md) -->
        <div class="md:hidden relative">
            <button id="hamburger" class="focus:outline-none ml-4">
                <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                     xmlns="http://www.w3.org/2000/svg">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M4 6h16M4 12h16M4 18h16"></path>
                </svg>
            </button>

            <div id="mobileMenu" class="absolute right-0 mt-2 w-64 bg-gray-800 text-white shadow-lg rounded hidden flex-col p-4 space-y-2 z-50">
                <span class="font-bold">Xin chào, <%= employee.getName() %>!</span>
                <% if (features != null) {
                    for (String url : features) { 
                        String name = url;
                        switch(url) {
                            case "/feature/home": name = "Trang chủ"; break;
                            case "/feature/agenda": name = "Lịch nghỉ"; break;
                            case "/feature/requestforleave": name = "Xin nghỉ"; break;
                            case "/feature/listleave": name = "Danh sách đơn"; break;
                            case "/feature/review": name = "Duyệt đơn"; break;
                            case "/feature/createaccount": name = "Tạo tài khoản"; break;
                            case "/feature/listaccount": name = "Danh sách tài khoản"; break;
                        }
                %>
                <a href="<%= request.getContextPath() + url %>" class="hover:text-gray-300 block"><%= name %></a>
                <%  } } %>
                <a href="<%= request.getContextPath() + "/logout" %>" class="bg-red-500 px-3 py-1 rounded hover:bg-red-600 block text-center">Đăng xuất</a>
            </div>
        </div>
    </div>
</header>

<script>
    const hamburger = document.getElementById('hamburger');
    const mobileMenu = document.getElementById('mobileMenu');

    hamburger.addEventListener('click', () => {
        mobileMenu.classList.toggle('hidden');
    });

    document.addEventListener('click', (e) => {
        if (!hamburger.contains(e.target) && !mobileMenu.contains(e.target)) {
            mobileMenu.classList.add('hidden');
        }
    });
</script>
