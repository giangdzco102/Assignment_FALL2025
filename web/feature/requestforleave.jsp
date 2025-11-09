<%@page import="model.Employee"%>
<%
    Employee employee = (Employee) session.getAttribute("auth");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <h2 class="text-2xl font-bold mb-4">Nộp đơn xin nghỉ</h2>

        <form action="${pageContext.request.contextPath}/feature/requestforleave" method="post" class="space-y-4">
            <div>
                <label>Từ ngày:</label>
                <input type="date" name="from" required class="border rounded p-2" />
            </div>
            <div>
                <label>Đến ngày:</label>
                <input type="date" name="to" required class="border rounded p-2" />
            </div>
            <div>
                <label>Lý do:</label><br/>
                <textarea name="reason" rows="3" class="border rounded p-2 w-96"></textarea>
            </div>
            <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded">Nộp đơn</button>
        </form>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
