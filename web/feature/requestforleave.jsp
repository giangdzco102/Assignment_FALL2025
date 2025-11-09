<%@page import="model.Employee"%>
<%
    Employee employee = (Employee) session.getAttribute("auth");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Nộp đơn xin nghỉ</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
    </head>
    <body class="bg-gray-100 min-h-screen flex flex-col">
        <!-- HEADER -->
        <jsp:include page="../component/header.jsp" />

        <!-- MAIN CONTENT -->
        <main class="flex-1 p-6">
            <div class="max-w-xl mx-auto bg-white shadow rounded p-6">
                <h2 class="text-2xl font-bold mb-6 text-center text-gray-800">Đơn xin nghỉ</h2>

                <form id="leaveForm" action="${pageContext.request.contextPath}/feature/requestforleave" 
                      method="post" class="space-y-4">
                    
                    <div>
                        <label class="block font-semibold mb-1">Từ ngày:</label>
                        <input type="date" id="fromDate" name="from" required 
                               class="border rounded p-2 w-full" />
                    </div>

                    <div>
                        <label class="block font-semibold mb-1">Đến ngày:</label>
                        <input type="date" id="toDate" name="to" required 
                               class="border rounded p-2 w-full" />
                    </div>

                    <div>
                        <label class="block font-semibold mb-1">Lý do:</label>
                        <textarea name="reason" rows="3" 
                                  class="border rounded p-2 w-full" 
                                  placeholder="Nhập lý do xin nghỉ..."></textarea>
                    </div>

                    <div class="flex justify-center">
                        <button type="submit" 
                                class="bg-blue-500 hover:bg-blue-600 text-white px-6 py-2 rounded shadow">
                            Nộp đơn
                        </button>
                    </div>
                </form>
            </div>
        </main>

        <script>
            const form = document.getElementById('leaveForm');
            form.addEventListener('submit', function (event) {
                const from = document.getElementById('fromDate').value;
                const to = document.getElementById('toDate').value;
                const today = new Date().toISOString().split('T')[0];

                if (from < today || to < today) {
                    alert("Không thể chọn ngày trong quá khứ!");
                    event.preventDefault();
                    return;
                }

                if (from > to) {
                    alert("Ngày bắt đầu không thể sau ngày kết thúc!");
                    event.preventDefault();
                    return;
                }
            });
        </script>

        <jsp:include page="../component/footer.jsp" />
    </body>
</html>
