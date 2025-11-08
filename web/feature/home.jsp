<%@page import="java.util.List"%>
<%@page import="model.Employee"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home - Welcome</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex flex-col">

    <!-- HEADER -->
    <jsp:include page="../component/header.jsp" />

    <!-- MAIN CONTENT -->
    <main class="flex-grow container mx-auto p-6">
        <h1 class="text-3xl font-bold mb-4">Home Page</h1>
        <p class="text-gray-700">
            This is your dashboard. Use the menu above to navigate based on your role permissions.
        </p>
    </main>

    <jsp:include page="../component/footer.jsp" />
</body>
</html>
