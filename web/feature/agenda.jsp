<%@page import="model.Employee"%>
<%
    Employee employee = (Employee) session.getAttribute("auth");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Agenda</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex flex-col">
    <!-- HEADER -->
    <jsp:include page="../component/header.jsp" />
    
    <main class="flex-grow container mx-auto p-6">
        <h1 class="text-2xl font-bold mb-4">Agenda Page</h1>
        <p>Here is your agenda. Employee can view this page.</p>
    </main>
    
    <jsp:include page="../component/footer.jsp" />
</body>
</html>
