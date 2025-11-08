<%@page import="java.util.List"%>
<%@page import="model.Employee"%>
<%
    Employee employee = (Employee) session.getAttribute("auth");
    if (employee == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    List<String> features = (List<String>) session.getAttribute("featureURLs");
%>

<header class="bg-gray-800 text-white shadow">
    <div class="container mx-auto flex justify-between items-center p-4">
        <div class="text-lg font-bold">
            Welcome, <%= employee.getName() %>!
        </div>
        <nav class="space-x-4">
            <% if (features != null) {
                for (String url : features) { 
                    String name = url;
                    switch(url) {
                        case "/feature/home": name = "Home"; break;
                        case "/feature/agenda": name = "Agenda"; break;
                        case "/feature/requestforleave": name = "Request For Leave";
                        case "/feature/review": name = "Review";
                        case "/feature/createaccount": name = "Create Account"; break;
                        case "/feature/listaccount": name = "List Account"; break;

                    }
            %>
            <a href="<%= request.getContextPath() + url %>" class="hover:text-gray-300 font-medium">
                <%= name %>
            </a>
            <%  } 
               } %>
            <a href="<%= request.getContextPath() + "/logout" %>" class="bg-red-500 px-3 py-1 rounded hover:bg-red-600">Logout</a>
        </nav>
    </div>
</header>
