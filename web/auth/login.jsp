<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Đăng nhập - CÔNG TY X</title>

    <!-- favicon -->
    <link
      rel="icon"
      type="image/png"
      href="${pageContext.request.contextPath}/public/assets/logo-embedded-chat-header@3x.png"
    />

    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>
  </head>

  <body class="bg-gray-100 flex items-center justify-center min-h-screen flex-col gap-6">
    
    <!-- Logo -->
    <img src="${pageContext.request.contextPath}/public/assets/logo-site.png" alt="Logo" class="" />

    <!-- Login box -->
    <div class="bg-white p-8 rounded-2xl shadow-lg w-full max-w-sm">
      <h2 class="text-2xl font-bold text-center mb-6 text-gray-700">
        Đăng nhập
      </h2>

      <!-- Hiển thị thông báo lỗi (nếu có) -->
      <c:if test="${not empty message}">
        <div class="mb-4 p-3 text-sm text-red-600 bg-red-50 border border-red-200 rounded-lg">
          ${message}
        </div>
      </c:if>

      <!-- Form đăng nhập -->
      <form action="${pageContext.request.contextPath}/login" method="POST">
        <div class="mb-4">
          <input
            name="username"
            type="text"
            placeholder="Nhập tài khoản..."
            class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            required
          />
        </div>

        <div class="mb-6">
          <input
            name="password"
            type="password"
            placeholder="Nhập mật khẩu..."
            class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            required
          />
        </div>

        <button
          type="submit"
          class="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition"
        >
          Đăng nhập
        </button>
      </form>

      <!-- Đăng nhập Google -->
      <div class="mt-6 text-center">
        <p class="text-gray-500 mb-3">Hoặc</p>
        <button
          class="w-full flex items-center justify-center gap-2 border py-2 rounded-lg hover:bg-gray-50 transition"
        >
          <img
            src="https://www.svgrepo.com/show/475656/google-color.svg"
            alt="Google"
            class="w-5 h-5"
          />
          <span>Đăng nhập bằng Google</span>
        </button>
      </div>
    </div>
  </body>
</html>
