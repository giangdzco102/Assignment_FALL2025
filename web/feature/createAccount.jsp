<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Division, model.Employee, model.Role" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Create Account</title>
    <script src="https://cdn.tailwindcss.com"></script>
  </head>
  <body class="bg-gray-50 min-h-screen flex flex-col font-sans text-gray-800">
    <jsp:include page="../component/header.jsp" />

    <main class="flex-grow flex justify-center items-center py-12 px-4">
      <div class="bg-white shadow-xl rounded-xl p-10 w-full max-w-lg">
        <h2 class="text-3xl font-bold text-center mb-8 text-gray-900">
          Create New Employee Account
        </h2>

        <%-- 
          Cải thiện thông báo thành công: 
          Thêm nền, viền và bo góc.
        --%>
        <% if (request.getAttribute("success") != null) { %>
        <p class="text-green-800 bg-green-100 border border-green-300 font-medium text-center mb-5 p-3 rounded-lg">
          <%= request.getAttribute("success") %>
        </p>
        <% } %>
        
        <%-- 
          Cải thiện thông báo lỗi: 
          Tương tự như trên nhưng với màu đỏ.
        --%>
        <% if (request.getAttribute("error") != null) { %>
        <p class="text-red-800 bg-red-100 border border-red-300 font-medium text-center mb-5 p-3 rounded-lg">
          <%= request.getAttribute("error") %>
        </p>
        <% } %>

        <form action="${pageContext.request.contextPath}/feature/createaccount" 
              method="post" class="space-y-6">

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1.5">
              Full Name
            </label>
            <input type="text" name="name" required
                   class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg 
                          focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none 
                          transition-all duration-200" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1.5">
              Username
            </label>
            <input type="text" name="username" required
                   class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg 
                          focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none 
                          transition-all duration-200" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1.5">
              Password
            </label>
            <input type="password" name="password" required
                   class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg 
                          focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none 
                          transition-all duration-200" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1.5">
              Division
            </label>
            <select name="ID_division" 
                    class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg 
                           focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none 
                           transition-all duration-200">
              <option value="">-- Select Division --</option>
              <%
                  List<model.Division> divisions = (List<model.Division>) request.getAttribute("divisions");
                  if (divisions != null) {
                      for (model.Division d : divisions) {
              %>
              <option value="<%= d.getId() %>"><%= d.getName() %></option>
              <% }} %>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1.5">
              Manager
            </label>
            <select name="ID_manager"
                    class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg 
                           focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none 
                           transition-all duration-200">
              <option value="">-- Select Manager --</option>
              <%
                  List<model.Employee> managers = (List<model.Employee>) request.getAttribute("managers");
                  if (managers != null) {
                      for (model.Employee m : managers) {
              %>
              <option value="<%= m.getId() %>"><%= m.getName() %></option>
              <% }} %>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1.5">
              Role
            </label>
            <select name="RoleID"
                    class="w-full px-4 py-2.5 bg-gray-50 border border-gray-300 rounded-lg 
                           focus:ring-2 focus:ring-blue-500 focus:bg-white focus:outline-none 
                           transition-all duration-200">
              <option value="">-- Select Role --</option>
              <%
                  List<model.Role> roles = (List<model.Role>) request.getAttribute("roles");
                  if (roles != null) {
                      for (model.Role r : roles) {
              %>
              <option value="<%= r.getId() %>"><%= r.getName() %></option>
              <% }} %>
            </select>
          </div>

          <div class="pt-2">
            <button type="submit"
                    class="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold px-6 py-3 rounded-lg 
                           transition-all duration-200 focus:outline-none focus:ring-2 
                           focus:ring-blue-500 focus:ring-offset-2">
              Create Account
            </button>
          </div>
        </form>
      </div>
    </main>

    <jsp:include page="../component/footer.jsp" />
  </body>
</html>s